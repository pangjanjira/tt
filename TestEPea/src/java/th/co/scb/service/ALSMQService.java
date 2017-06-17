/**
 * 
 */
package th.co.scb.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.DocRunningTable;
import th.co.scb.db.eguarantee.DeclarationTable;
import th.co.scb.db.eguarantee.EGuaranteeTable;
import th.co.scb.db.eguarantee.GPGuaranteeTable;
import th.co.scb.db.eguarantee.ProjectTaxTable;
import th.co.scb.model.EMapCodeError;
import th.co.scb.model.ETime;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.model.eguarantee.Declaration;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.ProjectTax;
import th.co.scb.model.mq.AccountALSMSGResponse;
import th.co.scb.model.mq.AccountALSMSGRq;
import th.co.scb.model.mq.AccountALSResponse;
import th.co.scb.model.mq.AccountALSRq;
import th.co.scb.model.mq.LoanLGIssueRq;
import th.co.scb.model.mq.LoanLGMaintainRq;
import th.co.scb.model.mq.LoanLGResponse;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
import th.co.scb.service.mq.MQConfig;
import th.co.scb.service.mq.MQConnectorFailedException;
import th.co.scb.service.mq.MQMessageService;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.MqMessageUtils;
import th.co.scb.util.StringUtil;
import th.co.scb.util.TemplateUtil;
import th.co.scb.util.XMLUtil;

//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
import th.co.scb.model.mq.LoanLGExtendRq;

/**
 * @author s51486
 *
 */
public class ALSMQService {
	
	private static final Logger logger = LoggerFactory.getLogger(ALSMQService.class);
	private static final String DIFF_MONTH = "diff_month";
	private static final String FIRST_DUE_DATE = "first_due_date";
	
	private static String MODE_PROD = "prod";
	private static String MODE_DEV = "dev";
	
	
	public EGuarantee sendMQMessage(ConnectDB connectDB , EGuarantee eguarantee, HttpServletRequest request, ETime eTime) throws Exception, EGuaranteeMQMessageException, MQConnectorFailedException {
		
		logger.debug("======== in send MQ Message ==========");
		MQConfig mqConfig = new MQConfig();
		
		//connect MQ
		MQMessageService mq = null;
		
		DocRunningTable docRunningTable = null;
		DeclarationTable declarationTable = null;
		EGuaranteeTable eGuaranteeTable = null;
		
		String responseXML = "";
		
		try{
			
			mq = new MQMessageService();
			
			docRunningTable = new DocRunningTable(connectDB);
			declarationTable = new DeclarationTable(connectDB);
			eGuaranteeTable = new EGuaranteeTable(connectDB);
			
			//gen L/G No. --> case deposit only
			if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
				eguarantee.setBankGuaranteeNo(docRunningTable.genLGNo(eTime));
			}

			String requsetXML = reformatForMQ(eguarantee, request);
			//System.out.println("requsetXML >>> " + requsetXML);
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),eguarantee.getPaymentMethodDesc(), eguarantee.getCustomsRef(), Constants.EGUARANTEE_SYSTEM_NAME);
			//System.out.println("responseXML >>> " + responseXML);
			LoanLGResponse loanLGResponse = responseToObject(responseXML, eguarantee);
			
			
			if(("0001") .equals(loanLGResponse.getResCode()) && ("00").equals(loanLGResponse.getResALSStatusCode())){//TRANSACTION PROCESSED OK 
				//System.out.println(">>>>> in Case 0001");
				eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_BOOK);
				if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
					//add
					declarationTable.add(new Declaration(eguarantee.getDeclarationNo(), eguarantee.getDeclarationSeqNo()));
				}else if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT_CANCEL)){
					//delete declaration
					declarationTable.remove(new Declaration(eguarantee.getDeclarationNo(), eguarantee.getDeclarationSeqNo()));
					
					//update status e_guarantee
					eGuaranteeTable.updateStatusCancel(eguarantee);
				}
				
			}else{
				//System.out.println(">>>>> Case NS ");
				//if not succeess clear L/G No. --> case deposit
				if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
					eguarantee.setBankGuaranteeNo("");
				}
				//if error --> set status
				eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
				eguarantee.setMessageCode(mapCodeErrorMessage(loanLGResponse.getResALSErrorCode()));
				String errorOnALS = "";
				if(loanLGResponse != null ){
					String errorCode = loanLGResponse.getResALSErrorCode();
					String errorMsg = loanLGResponse.getResALSISQLMsg();
					if(errorCode != null && errorMsg != null){
						errorOnALS = StringUtil.nullToBlank(errorCode)+" - "+StringUtil.nullToBlank(errorMsg);
					}
				}
				//System.out.println(">>>>> errorOnALS = "+errorOnALS);
				eguarantee.setXmlOutput(errorOnALS);
			}

		}catch(EGuaranteeMQMessageException eg){
			logger.debug("ALS ERROR : EGuaranteeMQMessageException Error :"+eg);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			eguarantee.setBankGuaranteeNo("");
			eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
			eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
			//throw eg;
			
		}catch(MQConnectorFailedException m){
			logger.debug("ALS ERROR : MQConnectorFailedException Error :"+m);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			eguarantee.setBankGuaranteeNo("");
			eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
			eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
			//throw m;
			
		}catch (Exception e) {
			//e.printStackTrace();
			logger.debug("ALS ERROR : Exception Error :"+e);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			eguarantee.setBankGuaranteeNo("");
			eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
			eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
			//throw e;
			
        }

		return eguarantee;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	public GPGuarantee sendMQMessage(ConnectDB connectDB , GPGuarantee gpGuarantee, HttpServletRequest request, ETime eTime) throws Exception, EGuaranteeMQMessageException, MQConnectorFailedException {
		logger.debug("======== in send MQ Message ==========");
		MQConfig mqConfig = new MQConfig();
		//connect MQ
		MQMessageService mq = null;
		DocRunningTable docRunningTable = null;
		ProjectTaxTable projectTaxTable = null;
		GPGuaranteeTable gpGuaranteeTable = null;
		String responseXML = "";
		boolean isRollback = false;
		try{
			mq = new MQMessageService();
			docRunningTable = new DocRunningTable(connectDB);
			projectTaxTable = new ProjectTaxTable(connectDB);
			gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			//gen L/G No. --> case setup issue only
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
			}
			//connect MQ --> setup and cancel issue only
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				//update status gp_guarantee (setup txn)
				gpGuaranteeTable.updateStatusClaim(gpGuarantee);
				//update status gp_guarantee (claim txn)
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
				gpGuarantee.setMsgCode("");
				gpGuarantee.setXmlOutput("");
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				
			}else{
				String requsetXML = reformatForMQ(gpGuarantee, request);
				if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
					projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
				}
				responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.GP_SYSTEM_NAME);
				LoanLGResponse loanLGResponse = responseToObject(responseXML, gpGuarantee);
				
				if(("0001") .equals(loanLGResponse.getResCode()) && ("00").equals(loanLGResponse.getResALSStatusCode())){//TRANSACTION PROCESSED OK
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
						//update status gp_guarantee
						gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
					}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
						//delete prject_tax
						projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
						//update status gp_guarantee (setup txn)
						gpGuaranteeTable.updateStatusCancel(gpGuarantee);
						//update status gp_guarantee (cancel txn)
						gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
					}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
						//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
						//update status gp_guarantee (setup txn)
						//gpGuaranteeTable.updateStatusExtendExpiryDate(gpGuarantee);
						//update status gp_guarantee (extend txn)
						gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
					}
					//update info for success txn
					gpGuarantee.setMsgCode("");
					gpGuarantee.setXmlOutput("");
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				}else{
					isRollback = true;
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
						gpGuarantee.setLgNo("");
					}
					//if error --> set status
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
						gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
					}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
						gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
					}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
						//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
						gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
					}
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					String errorOnALS = "";
					if(loanLGResponse != null ){
						String errorCode = loanLGResponse.getResALSErrorCode();
						String errorMsg = loanLGResponse.getResALSISQLMsg();
						if(errorCode != null && errorMsg != null){
							errorOnALS = StringUtil.nullToBlank(errorCode)+" - "+StringUtil.nullToBlank(errorMsg);
						}
					}
					gpGuarantee.setMsgCode(mapCodeErrorMessage(loanLGResponse.getResALSErrorCode()));
					gpGuarantee.setXmlOutput(errorOnALS);
				}
			}
		}catch(EGuaranteeMQMessageException eg){
			isRollback = true;
			logger.debug("ALS ERROR : EGuaranteeMQMessageException Error :"+eg);
			gpGuarantee = setGPGuaranteeException(gpGuarantee);
		}catch(MQConnectorFailedException m){
			isRollback = true;
			logger.debug("ALS ERROR : MQConnectorFailedException Error :"+m);
			gpGuarantee = setGPGuaranteeException(gpGuarantee);
		}catch (Exception e) {
			isRollback = true;
			logger.debug("ALS ERROR : Exception Error :"+e);
			gpGuarantee = setGPGuaranteeException(gpGuarantee);
        }finally{
        	//<start>
        	if (isRollback) {
				// remove project tax
        		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
        			projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
        			gpGuarantee.setLgNo("");
        		//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
        		//}else{
        		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
        			projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        		}
        	}
        }
		
		return gpGuarantee;
	}
	//changed from
	/*
	public GPGuarantee sendMQMessage(ConnectDB connectDB , GPGuarantee gpGuarantee, HttpServletRequest request, ETime eTime) throws Exception, EGuaranteeMQMessageException, MQConnectorFailedException {
		
		
		logger.debug("======== in send MQ Message ==========");
		MQConfig mqConfig = new MQConfig();
		
		//connect MQ
		MQMessageService mq = null;
		
		DocRunningTable docRunningTable = null;
		ProjectTaxTable projectTaxTable = null;
		GPGuaranteeTable gpGuaranteeTable = null;
		
		String responseXML = "";
		
		//update by Apichart H.@20150512
		boolean isRollback = false;
		
		try{
			
			mq = new MQMessageService();
			
			docRunningTable = new DocRunningTable(connectDB);
			projectTaxTable = new ProjectTaxTable(connectDB);
			gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			
			//gen L/G No. --> case setup issue only
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
			}
			
			//connect MQ --> setup and cancel issue only
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				//set status
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				
				//update status gp_guarantee
				gpGuaranteeTable.updateStatusClaim(gpGuarantee);
				
			}else{
				String requsetXML = reformatForMQ(gpGuarantee, request);
				//update by Apichart H.@20150512
				if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
					// add projectTax
					//projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
					// update by Malinee T. UR58100048 @20160104
					projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
				}
				responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.GP_SYSTEM_NAME);
				//System.out.println("responseXML >>> " + responseXML);
				LoanLGResponse loanLGResponse = responseToObject(responseXML, gpGuarantee);
				
				
				if(("0001") .equals(loanLGResponse.getResCode()) && ("00").equals(loanLGResponse.getResALSStatusCode())){//TRANSACTION PROCESSED OK 
					
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
						//add
						//projectTaxTable.add(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo()));
						//projectTaxTable.add(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType()));
						gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
						gpGuarantee.setMsgCode("");
						gpGuarantee.setXmlOutput("");
					}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
						//delete prject_tax
						//projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo()));
						projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
						
						//update status gp_guarantee
						gpGuaranteeTable.updateStatusCancel(gpGuarantee);
						gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
					}
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				}else{
					
					isRollback = true;					
					//if not succeess clear L/G No. --> case setup issue
//					update by Apichart H.@20150528 (move condition to finally)
//					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
//						projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo())); //add by Apichart H.@20150528
//						gpGuarantee.setLgNo("");
//					}
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
						gpGuarantee.setLgNo("");
					}
					
					//if error --> set status
					gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
					if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
						gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
						
					}
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					String errorOnALS = "";
					if(loanLGResponse != null ){
						String errorCode = loanLGResponse.getResALSErrorCode();
						String errorMsg = loanLGResponse.getResALSISQLMsg();
						if(errorCode != null && errorMsg != null){
							errorOnALS = StringUtil.nullToBlank(errorCode)+" - "+StringUtil.nullToBlank(errorMsg);
						}
					}
					//gpGuarantee.setStatusDesc(mapErrorMessage(loanLGResponse.getResALSErrorCode()));
					gpGuarantee.setMsgCode(mapCodeErrorMessage(loanLGResponse.getResALSErrorCode()));
					gpGuarantee.setXmlOutput(errorOnALS);
					
				}
			}
			
		}catch(EGuaranteeMQMessageException eg){
			isRollback = true;
			logger.debug("ALS ERROR : EGuaranteeMQMessageException Error :"+eg);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			gpGuarantee = setGPGuaranteeException(gpGuarantee); //add by Apichart H.@20150528
//			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
//				gpGuarantee.setLgNo("");
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
//			}
//			gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
//			gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
//			gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			//throw eg;
			
		}catch(MQConnectorFailedException m){
			isRollback = true;
			logger.debug("ALS ERROR : MQConnectorFailedException Error :"+m);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			gpGuarantee = setGPGuaranteeException(gpGuarantee); //add by Apichart H.@20150528
//			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
//				gpGuarantee.setLgNo("");
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
//			}
//			gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
//			gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
//			gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			//throw m;
			
		}catch (Exception e) {
			isRollback = true;
			//e.printStackTrace();
			logger.debug("ALS ERROR : Exception Error :"+e);
			//à¹?à¸¡à¹?à¸ªà¸²à¸¡à¸²à¸£à¸–à¸—à¸³à¸£à¸²à¸¢à¸?à¸²à¸£à¹?à¸”à¹?
			gpGuarantee = setGPGuaranteeException(gpGuarantee); //add by Apichart H.@20150528
//			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
//				gpGuarantee.setLgNo("");
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
//			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
//				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
//			}
//			gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
//			gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
//			gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			//throw e;
			
        }finally{
        	//<start>
        	if (isRollback) {
				// remove project tax
        		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
        			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
        			//changed from
        			//projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        			projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
        			gpGuarantee.setLgNo("");
        		}else{
        			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
        			//changed from
        			//projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        			projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        		}
        	}
        	//<end> update by Apichart H.@20150528
        }
		
		return gpGuarantee;
	}
	*/
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	public GPGuarantee setGPGuaranteeException(GPGuarantee gpGuarantee){
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
		}
		gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
		gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
		gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
		return gpGuarantee;
	}
	//changed from
	/*
	//	add by Apichart H.@20150528
	public GPGuarantee setGPGuaranteeException(GPGuarantee gpGuarantee){
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
			gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
		}
		gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
		gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
		gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
		
		return gpGuarantee;
	}
	*/
	public LoanLGResponse sendMQMessage(ConnectDB connectDB , EGuarantee eguarantee, HttpServletRequest request) throws Exception, EGuaranteeMQMessageException, MQConnectorFailedException {
		
		logger.debug("======== in send MQ Message ==========");
		MQConfig mqConfig = new MQConfig();
		
		//connect MQ
		MQMessageService mq = null;
		String responseXML = "";
		
		LoanLGResponse loanLGResponse = new LoanLGResponse();
		
		try{
			
			mq = new MQMessageService();
			
			String requsetXML = reformatForMQ(eguarantee, request);
			System.out.println("requsetXML >>> " + requsetXML);
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),eguarantee.getPaymentMethodDesc(), eguarantee.getCustomsRef(), Constants.EGUARANTEE_SYSTEM_NAME);
			System.out.println("responseXML >>> " + responseXML);
			loanLGResponse = responseToObject(responseXML, eguarantee);
			

		}catch(EGuaranteeMQMessageException eg){
			logger.debug("MQ ERROR : EGuaranteeMQMessageException Error " + eg);
			loanLGResponse.setCustomsRefNo(eguarantee.getCustomsRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : EGuaranteeMQMessageException Error ");
			//throw eg;
			
		}catch(MQConnectorFailedException m){
			logger.debug("MQ ERROR : MQConnectorFailedException Error :"+m);
			
			loanLGResponse.setCustomsRefNo(eguarantee.getCustomsRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : MQConnectorFailedException Error ");
			//throw m;
			
		}catch (Exception e) {
			logger.debug("MQ ERROR : Exception Error :"+e);
			//throw e;
			loanLGResponse.setCustomsRefNo(eguarantee.getCustomsRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : Exception Error ");
			
        }

		return loanLGResponse;
	}
	
	public LoanLGResponse sendMQMessage(ConnectDB connectDB , GPGuarantee gpGuarantee, HttpServletRequest request) throws Exception, EGuaranteeMQMessageException, MQConnectorFailedException {
		
		logger.debug("======== in send MQ Message ==========");
		MQConfig mqConfig = new MQConfig();
		
		//connect MQ
		MQMessageService mq = null;
		String responseXML = "";
		
		LoanLGResponse loanLGResponse = new LoanLGResponse();
		
		try{
			
			mq = new MQMessageService();
			
			String requsetXML = reformatForMQ(gpGuarantee, request);
			System.out.println("requsetXML >>> " + requsetXML);
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.GP_SYSTEM_NAME);
			System.out.println("responseXML >>> " + responseXML);
			loanLGResponse = responseToObject(responseXML, gpGuarantee);
			

		}catch(EGuaranteeMQMessageException eg){
			logger.debug("MQ ERROR : EGuaranteeMQMessageException Error " + eg);
			loanLGResponse.setCustomsRefNo(gpGuarantee.getTxRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : EGuaranteeMQMessageException Error ");
			//throw eg;
			
		}catch(MQConnectorFailedException m){
			logger.debug("MQ ERROR : MQConnectorFailedException Error :"+m);
			
			loanLGResponse.setCustomsRefNo(gpGuarantee.getTxRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : MQConnectorFailedException Error ");
			//throw m;
			
		}catch (Exception e) {
			logger.debug("MQ ERROR : Exception Error :"+e);
			//throw e;
			loanLGResponse.setCustomsRefNo(gpGuarantee.getTxRef());
			loanLGResponse.setResCode("99");
			loanLGResponse.setResDesc("MQ ERROR : Exception Error ");
			
        }

		return loanLGResponse;
	}
	
	private LoanLGResponse responseToObject(String responseXML, EGuarantee eGuarantee){
		
		LoanLGResponse loanLGResponse = new LoanLGResponse();

		loanLGResponse.setCustomsRefNo(eGuarantee.getCustomsRef());
		loanLGResponse.setResCode(XMLUtil.getTagValue("res_code", responseXML));
		loanLGResponse.setResDesc(XMLUtil.getTagValue("res_msg", responseXML));
		loanLGResponse.setResALSStatusCode(XMLUtil.getTagValue("StatusCode", responseXML));
		loanLGResponse.setResALSStatusDesc(XMLUtil.getTagValue("StatusDesc", responseXML));
		loanLGResponse.setResALSErrorCode(XMLUtil.getTagValue("ErrorCode", responseXML));
		loanLGResponse.setResALSISQLMsg(XMLUtil.getTagValue("ISQLMsg", responseXML));
		
		 
		return loanLGResponse;
	}
	
	private LoanLGResponse responseToObject(String responseXML, GPGuarantee gpGuarantee){
		
		LoanLGResponse loanLGResponse = new LoanLGResponse();

		loanLGResponse.setCustomsRefNo(gpGuarantee.getTxRef());
		loanLGResponse.setResCode(XMLUtil.getTagValue("res_code", responseXML));
		loanLGResponse.setResDesc(XMLUtil.getTagValue("res_msg", responseXML));
		loanLGResponse.setResALSStatusCode(XMLUtil.getTagValue("StatusCode", responseXML));
		loanLGResponse.setResALSStatusDesc(XMLUtil.getTagValue("StatusDesc", responseXML));
		loanLGResponse.setResALSErrorCode(XMLUtil.getTagValue("ErrorCode", responseXML));
		loanLGResponse.setResALSISQLMsg(XMLUtil.getTagValue("ISQLMsg", responseXML));
		
		 
		return loanLGResponse;
	}

	private LoanLGMaintainRq copyValueEGuaranteeCancelOrRefund(EGuarantee eguarantee){
		
		LoanLGMaintainRq loanLGMaintainRq = new LoanLGMaintainRq();
		
		loanLGMaintainRq.setRqUID(MqMessageUtils.generateRqUID());
		
		loanLGMaintainRq.settBRANCH(eguarantee.getDebtorBankBranchCode()); 
		loanLGMaintainRq.setAcctId(eguarantee.getDebtorBankAccNo());
		
		loanLGMaintainRq.setDocNo(eguarantee.getBankGuaranteeNo());
		//loanLGMaintainRq.setEffDt(MqMessageUtils.formatDate(new Date()));
		loanLGMaintainRq.setEffDt(eguarantee.getCustomsTransDate().substring(0, 10));
		//System.out.println("loanLGMaintainRq.getEffDt() >> "  + loanLGMaintainRq.getEffDt());
		//loanLGMaintainRq.setEffDt("2010-12-24");//24/12/2010 
		
		loanLGMaintainRq.setTranAmt(eguarantee.getDepositAmount().toString());
		
		if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT_CANCEL)){
			loanLGMaintainRq.setChgStatusFlag("C");
		}else if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.REFUND)){
			loanLGMaintainRq.setChgStatusFlag(" ");
		}
		
		return loanLGMaintainRq; 
		
	}
	
	private LoanLGMaintainRq copyValueGPGuaranteeCancelIssue(GPGuarantee gpGuarantee){
		
		LoanLGMaintainRq loanLGMaintainRq = new LoanLGMaintainRq();
		
		loanLGMaintainRq.setRqUID(MqMessageUtils.generateRqUID());
		
		loanLGMaintainRq.settBRANCH(gpGuarantee.getBranchCode()); 
		loanLGMaintainRq.setAcctId(gpGuarantee.getAccountNo());
		loanLGMaintainRq.setAcctType(Constants.GP_SYSTEM_NAME);
		
		loanLGMaintainRq.setDocNo(gpGuarantee.getLgNo());
		//loanLGMaintainRq.setEffDt(MqMessageUtils.formatDate(new Date()));
		loanLGMaintainRq.setEffDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm()));
		//System.out.println("loanLGMaintainRq.getEffDt() >> "  + loanLGMaintainRq.getEffDt());
		//loanLGMaintainRq.setEffDt("2010-12-24");//24/12/2010 
		
		loanLGMaintainRq.setTranAmt(gpGuarantee.getGuaranteeAmt().toString());
		
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
			loanLGMaintainRq.setChgStatusFlag("C");
		//}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
		//	loanLGMaintainRq.setChgStatusFlag(" ");
		}
		
		return loanLGMaintainRq;
		
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	private LoanLGExtendRq copyValueGPGuaranteeExtendExpiryDateIssue(GPGuarantee gpGuarantee){
		LoanLGExtendRq loanLGExtendRq = new LoanLGExtendRq();
		loanLGExtendRq.setRqUID(MqMessageUtils.generateRqUID());
		loanLGExtendRq.settBRANCH(gpGuarantee.getBranchCode()); 
		loanLGExtendRq.setAcctId(gpGuarantee.getAccountNo());
		loanLGExtendRq.setAcctType(Constants.GP_SYSTEM_NAME);
		loanLGExtendRq.setDocNo(gpGuarantee.getLgNo());
		loanLGExtendRq.setEffDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm()));
		//send "new expiry date",Format YYYYMMDD
		System.out.print("Extend Date: " + gpGuarantee.getExtendDate());
		String newExpiryDate = DateUtil.convertFormatDateStringToYYYYMMDD(gpGuarantee.getExtendDate());
		System.out.print("Extend Date (converted): " + newExpiryDate);
		loanLGExtendRq.setExtendDataElementValue(newExpiryDate);
		return loanLGExtendRq;
	}

	private LoanLGIssueRq copyValueEGuaranteeDeposit(EGuarantee eguarantee, HttpServletRequest request){
		
		LoanLGIssueRq loanLGIssueRq = new LoanLGIssueRq();
		
		loanLGIssueRq.setRqUID(MqMessageUtils.generateRqUID());
		
		loanLGIssueRq.settBRANCH(eguarantee.getDebtorBankBranchCode());
		loanLGIssueRq.setAcctId(eguarantee.getDebtorBankAccNo());
		
		loanLGIssueRq.setDocNo(eguarantee.getBankGuaranteeNo());
		loanLGIssueRq.setCustRefNo(eguarantee.getCustomsRef());
		loanLGIssueRq.setDecrNo(eguarantee.getDeclarationNo());
		loanLGIssueRq.setDecrSeq(eguarantee.getDeclarationSeqNo());
		loanLGIssueRq.setTranAmt(eguarantee.getDepositAmount().toString());
		loanLGIssueRq.setIssueDt(eguarantee.getRequestDate());
		loanLGIssueRq.setArrivalDt(eguarantee.getRelateDate());
		loanLGIssueRq.setTaxId(eguarantee.getDebtorCompanyTaxNo());
		loanLGIssueRq.setOpenAcctData(genOpenAcctData(eguarantee, request));
		
		return loanLGIssueRq;
	}
	
	private LoanLGIssueRq copyValueGPGuaranteeSetupIssue(GPGuarantee gpGuarantee, HttpServletRequest request){
		
		LoanLGIssueRq loanLGIssueRq = new LoanLGIssueRq();
		
		loanLGIssueRq.setRqUID(MqMessageUtils.generateRqUID());
		
		loanLGIssueRq.settBRANCH(gpGuarantee.getBranchCode());
		loanLGIssueRq.setAcctId(gpGuarantee.getAccountNo());
		loanLGIssueRq.setAcctType(Constants.GP_SYSTEM_NAME);
		 
		loanLGIssueRq.setDocNo(gpGuarantee.getLgNo());
		//loanLGIssueRq.setCustRefNo(gpGuarantee.getTxRef());
		loanLGIssueRq.setCustRefNo(Constants.BLANK);
		loanLGIssueRq.setDecrNo(gpGuarantee.getProjNo());
		loanLGIssueRq.setDecrSeq(Integer.toString(gpGuarantee.getSeqNo())); 
		loanLGIssueRq.setTranAmt(gpGuarantee.getGuaranteeAmt().toString());
		loanLGIssueRq.setIssueDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm()));
		
		loanLGIssueRq.setArrivalDt(gpGuarantee.getContractDate());
		loanLGIssueRq.setTaxId(gpGuarantee.getVendorTaxId());
		loanLGIssueRq.setOpenAcctData(genOpenAcctData(gpGuarantee, request));
		
		return loanLGIssueRq;
	}
	 
	private	String genOpenAcctData(EGuarantee eguarantee, HttpServletRequest request){
		
		String cdata = "";
		
		String tempFile = Constants.TEMPLETE_OPEN_ACCT_CDATA;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		
		template.setContext("customsTransmitDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(eguarantee.getRequestDate()),10));
		//template.setContext("customsTransmitDate", "2010-12-23");
		template.setContext("bankGuaranteeNo", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(eguarantee.getBankGuaranteeNo()),15));
		template.setContext("depositAmount", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(eguarantee.getDepositAmount().toString()),13,2));
		//à¸§à¸±à¸?à¸?à¸³à¹€à¸?à¹?à¸² / à¸§à¸±à¸?à¸ªà¹?à¸?à¸­à¸­à¸?à¸?à¸­à¸?à¹?à¸?à¸?à¸?à¸ªà¸´à¸?à¸?à¹?à¸² + 1 à¸?à¸µ  
		//à¹?à¸¡à¹?à¸•à¹?à¸­à¸?à¸ªà¸?à¹?à¸?à¸§à¸±à¸? à¸¢à¸?à¹€à¸§à¹?à¸? à¹€à¸”à¸·à¸­à¸? 2 à¹?à¸«à¹?à¹€à¸?à¹?à¸?à¸§à¹?à¸² à¸?à¸µà¸?à¸µà¹? à¹€à¸?à¹?à¸? 28 à¸«à¸£à¸·à¸­ 29
		template.setContext("firstDueDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(MqMessageUtils.formatDate(DateUtil.addYear(DateUtil.stringToDate(eguarantee.getRelateDate()), 1))),10));
		//template.setContext("firstDueDate", "2011-12-23");
		template.setContext("contDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(eguarantee.getRelateDate()),10));
		//template.setContext("contDate", "2010-12-23");
		template.setContext("depositAmount13", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(eguarantee.getDepositAmount().toString()),11,2));
		template.setContext("bankAcctNo", MqMessageUtils.fillZeroBefore(StringUtil.nullToBlank(eguarantee.getDebtorBankAccNo()),14));
		template.setContext("declarationNo", MqMessageUtils.fillZeroBefore(StringUtil.nullToBlank(eguarantee.getDeclarationNo()),15));
		template.setContext("declarationSeq", MqMessageUtils.fillZeroBefore(StringUtil.nullToBlank(eguarantee.getDeclarationSeqNo()),15));
		template.setContext("taxId", MqMessageUtils.fillZeroBefore(StringUtil.nullToBlank(eguarantee.getDebtorCompanyTaxNo()),15));
		 
		template.setContext("customsName", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(eguarantee.getCustomsName()),40));
		template.setContext("companyName", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(eguarantee.getDebtorCompanyName()),40));
		
		cdata = template.getXML();
		
		return XMLUtil.addCDATA(cdata);
		
	}
	
	private	String genOpenAcctData(GPGuarantee gpGuarantee, HttpServletRequest request){
		
		String cdata = "";
		
		String tempFile = Constants.TEMPLETE_OPEN_ACCT_GP_CDATA;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());

		//template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(gpGuarantee.getContractDate()),10));
		//System.out.println("########################################");
		MQConfig mqConfig = new MQConfig();
		boolean devSuccess = true;
		//TODO BOOM 09042015
		if(MODE_DEV.equalsIgnoreCase(mqConfig.getMode())){
			//new code
			String configProcessDateStr = mqConfig.getProcessDate();
			//convert to date
			try{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US); 
			Date configProcessDate = df.parse(configProcessDateStr);
			template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(configProcessDate)),10));
			} catch(Exception e) {
				devSuccess = false;
				logger.debug("PROCESS DATE ERROR : Exception Error :"+e);
			}
		}
		
		if((MODE_DEV.equalsIgnoreCase(mqConfig.getMode()) && (!devSuccess)) 
				|| MODE_PROD.equalsIgnoreCase(mqConfig.getMode())){
			//existing code
			if(gpGuarantee.isBatch() == Boolean.TRUE){//run from batch -- offline
				//System.out.println("OFFLINE : SYSTEM DATE");
				System.out.println("offline gpGuarantee ProcessDate:" + gpGuarantee.getProcessDate());
				//template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(new Date())),10));
				template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate())),10));
			}else{//online
				//System.out.println("ONLINE : DTM TAG");
				//R58060012 : edit by siwat.n @20150629
				System.out.println("online gpGuarantee ProcessDate:" + gpGuarantee.getProcessDate());
				//change from dtm to process date (adding approval process made it possible that process date will not same as dtm)
				//template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.convertFormatDateString(gpGuarantee.getDtm())),10));
				template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate())),10));
			}
		}
		
		/*
		if(gpGuarantee.isBatch() == Boolean.TRUE){//run from batch -- offline
			//System.out.println("OFFLINE : SYSTEM DATE");
			//template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(new Date())),10));
			template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate())),10));
		}else{//online
			//System.out.println("ONLINE : DTM TAG");
			template.setContext("contractDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.convertFormatDateString(gpGuarantee.getDtm())),10));
		}
		*/
		//System.out.println("########################################");
		
		template.setContext("lgNo", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(gpGuarantee.getLgNo()),15));
		template.setContext("guaranteeAmt", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(gpGuarantee.getGuaranteeAmt().toString()),13,2));
		template.setContext("bondType", gpGuarantee.getBondType());
		boolean isOpenEnd = false;
		String endDate = gpGuarantee.getEndDate();
		if(endDate == null || endDate.length() == 0){
			endDate = "9999-12-31";
			isOpenEnd = true;
		}
		
		Hashtable<String, Object> firstDueDateHashtable =  getFirstDueDate(gpGuarantee);
		template.setContext("guaranteeAmt13", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(gpGuarantee.getGuaranteeAmt().toString()),11,2));
		if(isOpenEnd){
			//start date + 1
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(DateUtil.stringToDate(gpGuarantee.getStartDate()));
			Calendar firstDueCalendar = startCalendar;
			firstDueCalendar.add(Calendar.YEAR, 1);
			template.setContext("firstDueDate", DateUtil.getDateFormatYYYYMMDD((firstDueCalendar.getTime())));
		}else{
			template.setContext("firstDueDate", DateUtil.getDateFormatYYYYMMDD(((Date)firstDueDateHashtable.get(FIRST_DUE_DATE))));
		}
		template.setContext("startDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(gpGuarantee.getStartDate()),10));
		template.setContext("endDate", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(endDate),10));
		if(isOpenEnd){
			template.setContext("firstDueDateNumber", MqMessageUtils.fillZeroBefore("12", 3));
		}else{
			template.setContext("firstDueDateNumber", MqMessageUtils.fillZeroBefore(((Integer)firstDueDateHashtable.get(DIFF_MONTH)).toString(),3));
		}
		
		System.out.println("CDATA AccountNo: " + gpGuarantee.getAccountNo());
		template.setContext("accountNo", MqMessageUtils.fillZeroBefore(StringUtil.nullToBlank(gpGuarantee.getAccountNo()),14));
		template.setContext("projOwnName", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(gpGuarantee.getProjOwnName()),40));
		template.setContext("vendorName", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(gpGuarantee.getVendorName()),40));
		
		cdata = template.getXML();
		
		return XMLUtil.addCDATA(cdata);
		
	}
	
	private Hashtable<String, Object> getFirstDueDate(GPGuarantee gpGuarantee){
		
		Hashtable<String, Object> firstDueDateHashtable = new Hashtable<String, Object>();
		
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(DateUtil.stringToDate(gpGuarantee.getStartDate()));
		Calendar endCalendar = new GregorianCalendar();
		//endCalendar.setTime(DateUtil.stringToDate(gpGuarantee.getEndDate()));
		String endDate = gpGuarantee.getEndDate();
		if(endDate == null || endDate.length() == 0){
			endCalendar = startCalendar;
			endCalendar.add(Calendar.YEAR, 1);
		}else{
			endCalendar.setTime(DateUtil.stringToDate(gpGuarantee.getEndDate()));
		}
		Calendar firstDueDateCalendar = new GregorianCalendar();
		
		//get day
		int dayStart = startCalendar.get(Calendar.DAY_OF_MONTH);
		//System.out.println("dayStart : " + dayStart);
		
		int dayEnd = endCalendar.get(Calendar.DAY_OF_MONTH);
		//System.out.println("dayEnd : " + dayEnd);
		
		//check endofmonth
		int endOfMonthStart = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println("endOfMonthStart : " + endOfMonthStart);
		
		//int endOfMonthEnd = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println("endOfMonthEnd : " + endOfMonthEnd);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
		//System.out.println("diffYear : " + diffYear);
		//System.out.println("diffMonth : " + diffMonth);
		
		if((dayEnd > dayStart) && (dayStart != endOfMonthStart)){
			diffMonth++;
		}
		
		if(diffMonth < 3){
			diffMonth = 3;
		}
		
		//firstDueDateList.add(new Integer(diffMonth));
		firstDueDateHashtable.put(DIFF_MONTH, new Integer(diffMonth));
		//System.out.println("diffMonthSystem : " + diffMonth);
		
		//set first due date
		Date firstDueDate = DateUtil.addMonth(startCalendar.getTime(), diffMonth);
		//System.out.println("firstDueDate : " + getDateFormatDDMMYYYY(firstDueDate));
		if(dayStart == endOfMonthStart){
			//get endOfMonth firstDueDate
			
			firstDueDateCalendar.setTime(firstDueDate);
			int endOfMonthFirstDueDate = firstDueDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			//System.out.println("endOfMonthFirstDueDate : " + endOfMonthFirstDueDate);
			
			//set end of month
			firstDueDateCalendar.set(Calendar.DAY_OF_MONTH, endOfMonthFirstDueDate);
			//System.out.println("firstDueDate(EndofMonth) : " + getDateFormatDDMMYYYY(firstDueDateCalendar.getTime()));
			firstDueDate = firstDueDateCalendar.getTime();
		}
		//firstDueDateList.add(firstDueDate);
		firstDueDateHashtable.put(FIRST_DUE_DATE, firstDueDate);
		
		return firstDueDateHashtable;
		
		
	}
	

	private String reformatCancelOrRefundForMQ(EGuarantee eguarantee, HttpServletRequest request)throws Exception{

		LoanLGMaintainRq loanLGMaintainRq = copyValueEGuaranteeCancelOrRefund(eguarantee);
		
		
		return reformatCancelOrRefundForMQ(loanLGMaintainRq, request);
	}
	
	private String reformatCancelOrRefundForMQ(LoanLGMaintainRq loanLGMaintainRq, HttpServletRequest request)throws Exception{
		
		logger.debug("======== in reformat Cancel Or Refund For MQ ==========");
		String reformatStr = "";
		
		String tempFile = Constants.TEMPLETE_XML_MQ_CANCEL_OR_REFUND;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		
		template.setContext("serviceName", StringUtil.nullToBlank(loanLGMaintainRq.getServiceName()));
		template.setContext("userId", StringUtil.nullToBlank(loanLGMaintainRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(loanLGMaintainRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(loanLGMaintainRq.getLang()));
		
		template.setContext("spName", StringUtil.nullToBlank(loanLGMaintainRq.getSpName()));
		template.setContext("rqUID", StringUtil.nullToBlank(loanLGMaintainRq.getRqUID()));
		template.setContext("tUSER", StringUtil.nullToBlank(loanLGMaintainRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(loanLGMaintainRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(loanLGMaintainRq.gettSOURCE()));
		
		
		template.setContext("acctId", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGMaintainRq.getAcctId()), 20));
		template.setContext("acctType", StringUtil.nullToBlank(loanLGMaintainRq.getAcctType()));
		
		template.setContext("docNo", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGMaintainRq.getDocNo()),15));
		//TODO BOOM 09042015
		MQConfig mqConfig = new MQConfig();
		if(MODE_DEV.equalsIgnoreCase(mqConfig.getMode())){
			//new code
			String configEffectiveDateStr = mqConfig.getEffectiveDate();
			//convert to date
			try{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US); 
			Date configEffectiveDate = df.parse(configEffectiveDateStr);
			template.setContext("effDt", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(configEffectiveDate)),10));
			} catch(Exception e) {
				logger.debug("EFFECTIVE DATE ERROR : Exception Error :"+e);
				template.setContext("effDt", StringUtil.nullToBlank(loanLGMaintainRq.getEffDt()));
			}
		}else{
			template.setContext("effDt", StringUtil.nullToBlank(loanLGMaintainRq.getEffDt()));
		}
		//template.setContext("effDt", StringUtil.nullToBlank(loanLGMaintainRq.getEffDt()));
		//template.setContext("effDt", "2010-12-24");
		template.setContext("tranCode", StringUtil.nullToBlank(loanLGMaintainRq.getTranCode()));
		template.setContext("tranAmt", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(loanLGMaintainRq.getTranAmt()),13,2));
		template.setContext("limitType", StringUtil.nullToBlank(loanLGMaintainRq.getLimitType()));
		template.setContext("limitCode", StringUtil.nullToBlank(loanLGMaintainRq.getLimitCode()));
		template.setContext("comment", StringUtil.nullToBlank(loanLGMaintainRq.getComment()));
		
		template.setContext("chgStatusFlag", StringUtil.nullToBlank(loanLGMaintainRq.getChgStatusFlag()));
		template.setContext("newStatus", StringUtil.nullToBlank(loanLGMaintainRq.getNewStatus()));
		template.setContext("oldStatus", StringUtil.nullToBlank(loanLGMaintainRq.getOldStatus()));
		
		reformatStr = template.getXML();
		
		return reformatStr;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	private String reformatExtendExpiryDateIssueForMQ(GPGuarantee gpGuarantee, HttpServletRequest request)throws Exception{
		LoanLGExtendRq loanLGExtendRq = copyValueGPGuaranteeExtendExpiryDateIssue(gpGuarantee);
		return reformatExtendExpiryDateForMQ(loanLGExtendRq, request);
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	private String reformatExtendExpiryDateForMQ(LoanLGExtendRq loanLGExtendRq, HttpServletRequest request)throws Exception{
		logger.debug("======== in reformat Extend Expiry Date For MQ ==========");
		String reformatStr = "";
		String tempFile = Constants.TEMPLETE_XML_MQ_EXTEND;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		template.setContext("serviceName", StringUtil.nullToBlank(loanLGExtendRq.getServiceName()));
		template.setContext("userId", StringUtil.nullToBlank(loanLGExtendRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(loanLGExtendRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(loanLGExtendRq.getLang()));
		template.setContext("spName", StringUtil.nullToBlank(loanLGExtendRq.getSpName()));
		template.setContext("rqUID", StringUtil.nullToBlank(loanLGExtendRq.getRqUID()));
		template.setContext("tUSER", StringUtil.nullToBlank(loanLGExtendRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(loanLGExtendRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(loanLGExtendRq.gettSOURCE()));
		
		template.setContext("acctId", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGExtendRq.getAcctId()), 20));
		template.setContext("acctType", StringUtil.nullToBlank(loanLGExtendRq.getAcctType()));
		template.setContext("docNo", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGExtendRq.getDocNo()),15));
		MQConfig mqConfig = new MQConfig();
		if(MODE_DEV.equalsIgnoreCase(mqConfig.getMode())){
			//new code
			String configEffectiveDateStr = mqConfig.getEffectiveDate();
			//convert to date
			try{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US); 
			Date configEffectiveDate = df.parse(configEffectiveDateStr);
			template.setContext("effDt", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(DateUtil.getDateFormatYYYYMMDD(configEffectiveDate)),10));
			} catch(Exception e) {
				logger.debug("EFFECTIVE DATE ERROR : Exception Error :"+e);
				template.setContext("effDt", StringUtil.nullToBlank(loanLGExtendRq.getEffDt()));
			}
		}else{
			template.setContext("effDt", StringUtil.nullToBlank(loanLGExtendRq.getEffDt()));
		}
		template.setContext("tranCode", StringUtil.nullToBlank(loanLGExtendRq.getTranCode()));
		template.setContext("tranFlag", StringUtil.nullToBlank(loanLGExtendRq.getTranFlag()));
		template.setContext("extendDataElementName", StringUtil.nullToBlank(loanLGExtendRq.getExtendDataElementName()));	//"RT308"
		template.setContext("extendDataElementValue", StringUtil.nullToBlank(loanLGExtendRq.getExtendDataElementValue()));	//"new expiry date",Format YYYYMMDD
		
		reformatStr = template.getXML();
		return reformatStr;
	}
	
	private String reformatDepositForMQ(EGuarantee eguarantee, HttpServletRequest request)throws Exception{
		
		LoanLGIssueRq loanLGIssueRq = copyValueEGuaranteeDeposit(eguarantee, request);
		
		return reformatDepositForMQ(loanLGIssueRq, request);
	}
	
	private String reformatDepositForMQ(LoanLGIssueRq loanLGIssueRq, HttpServletRequest request)throws Exception{
		
		logger.debug("======== in reformat Deposit For MQ ==========");
		
		String reformatStr = "";
		
		String tempFile = Constants.TEMPLETE_XML_MQ_DEPOSIT;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		
		template.setContext("serviceName", StringUtil.nullToBlank(loanLGIssueRq.getServiceName()));
		template.setContext("userId", StringUtil.nullToBlank(loanLGIssueRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(loanLGIssueRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(loanLGIssueRq.getLang()));
		
		template.setContext("spName", StringUtil.nullToBlank(loanLGIssueRq.getSpName()));
		template.setContext("rqUID", StringUtil.nullToBlank(loanLGIssueRq.getRqUID()));
		template.setContext("tUSER", StringUtil.nullToBlank(loanLGIssueRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(loanLGIssueRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(loanLGIssueRq.gettSOURCE()));
		
		template.setContext("acctId", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGIssueRq.getAcctId()), 20));
		template.setContext("acctType", StringUtil.nullToBlank(loanLGIssueRq.getAcctType()));
		
		template.setContext("docNo", MqMessageUtils.fillBlankAfter(StringUtil.nullToBlank(loanLGIssueRq.getDocNo()),15));
		template.setContext("custRefNo", StringUtil.nullToBlank(loanLGIssueRq.getCustRefNo()));
		template.setContext("decrNo", StringUtil.nullToBlank(loanLGIssueRq.getDecrNo()));
		template.setContext("decrSeq", StringUtil.nullToBlank(loanLGIssueRq.getDecrSeq()));
		template.setContext("tranAmt", MqMessageUtils.formatDecimal(StringUtil.nullToBlank(loanLGIssueRq.getTranAmt()), 13, 2));
		template.setContext("issueDt", StringUtil.nullToBlank(loanLGIssueRq.getIssueDt()));
		template.setContext("arrivalDt", StringUtil.nullToBlank(loanLGIssueRq.getArrivalDt()));
		template.setContext("taxId", StringUtil.nullToBlank(loanLGIssueRq.getTaxId()));
		template.setContext("openAcctData", StringUtil.nullToBlank(loanLGIssueRq.getOpenAcctData()));
		
		reformatStr = template.getXML();
		logger.debug("======== out reformat Deposit For MQ ==========");
		return reformatStr;
	}
	
	private String reformatForMQ(EGuarantee eguarantee, HttpServletRequest request)throws Exception{
		
		logger.debug("======== in reformat For MQ ==========");
		String reformatStr = "";
		
		if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
			reformatStr = reformatDepositForMQ(eguarantee, request);
		}else{
			reformatStr = reformatCancelOrRefundForMQ(eguarantee, request);
		}

		return reformatStr;
		
	}
	
	
	private String mapCodeErrorMessage(String errorMsgALSMQ){
		
		String errorCodeMap = "";
		//System.out.println("------> errorMsgALSMQ : " +errorMsgALSMQ);
		if(errorMsgALSMQ!=null){

			EMapCodeErrorService eMapCodeErrorService = new EMapCodeErrorService();
			EMapCodeError eMapCodeErrorPk = new EMapCodeError();
			eMapCodeErrorPk.setMqCode(errorMsgALSMQ.trim());
			
			try{
				EMapCodeError eMapCodeError = eMapCodeErrorService.findEguaCode(eMapCodeErrorPk);
				if(eMapCodeError == null){
					errorCodeMap = Constants.MessageCode.UNABLE_TO_PROCESS;
				}else{
					errorCodeMap = eMapCodeError.getEguaCode();
				}
			}catch (Exception e) {
				// --: handle exception
				logger.error("Error Map Code Error Message : "+e.getMessage());
				errorCodeMap = Constants.MessageCode.UNABLE_TO_PROCESS;
			}
		}else{
			errorCodeMap = Constants.MessageCode.UNABLE_TO_PROCESS;
		}
		
		//System.out.println("return code : " + errorCodeMap);
		return errorCodeMap;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	private String reformatForMQ(GPGuarantee gpGuarantee, HttpServletRequest request)throws Exception{
		logger.debug("======== in reformat For MQ ==========");
		String reformatStr = "";		
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			reformatStr = reformatSetupIssueForMQ(gpGuarantee, request);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
			reformatStr = reformatCancelIssueForMQ(gpGuarantee, request);
		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			reformatStr = reformatExtendExpiryDateIssueForMQ(gpGuarantee, request);
		}
		return reformatStr;
	}
	//changed from
	/*
	private String reformatForMQ(GPGuarantee gpGuarantee, HttpServletRequest request)throws Exception{
		
		logger.debug("======== in reformat For MQ ==========");
		//logger.debug("gpGuarantee.getAccountNo()" + gpGuarantee.getAccountNo());
		String reformatStr = "";
		
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			reformatStr = reformatSetupIssueForMQ(gpGuarantee, request);
		}else{
			reformatStr = reformatCancelIssueForMQ(gpGuarantee, request);
		}

		return reformatStr;
		
	}
	*/
	
	private String reformatSetupIssueForMQ(GPGuarantee gpGuarantee, HttpServletRequest request)throws Exception{
		
		LoanLGIssueRq loanLGIssueRq = copyValueGPGuaranteeSetupIssue(gpGuarantee, request);
		
		return reformatDepositForMQ(loanLGIssueRq, request);
		
	}
	
	private String reformatCancelIssueForMQ(GPGuarantee gpGuarantee, HttpServletRequest request)throws Exception{
		LoanLGMaintainRq loanLGMaintainRq = copyValueGPGuaranteeCancelIssue(gpGuarantee);
		return reformatCancelOrRefundForMQ(loanLGMaintainRq, request);
	}
	
	//--------add by 61962 @2015-08-28----------
	public AccountALS getAcctALSTaxId(GPGuarantee gpGuarantee,HttpServletRequest request) throws EGuaranteeMQMessageException{
		AccountALS accountALS = null;
		MQConfig mqConfig = new MQConfig();
		AccountALSResponse accountALSResponse = null;
			
		//connect MQ
		MQMessageService mq = null;
			
		String responseXML = "";
		try {
				
			mq = new MQMessageService();
			
			AccountALSMSGRq acctALSMSGRq = copyValueInqueryAcctALSTaxId(gpGuarantee, request);
			String requsetXML = genXMLReqTaxIDForMQ(acctALSMSGRq,request); 
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.SENDER_ID_LGIN);
			accountALSResponse = responseAcctToObject(responseXML,gpGuarantee);

			if(accountALSResponse.getStatusCode().equals("00")){ 
		    	accountALS = new AccountALS(
		    			StringUtil.nullToBlank((String) accountALSResponse.getBankId()),
		    			StringUtil.nullToBlank((String) accountALSResponse.getOcCode()),
		    			StringUtil.nullToBlank((String) accountALSResponse.getAcctId()),
		    			StringUtil.nullToBlank((String) acctALSMSGRq.getTaxId())
		    			); 
		    }else{
		    	accountALS = null;
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return accountALS;
	}
	
	private BigDecimal stringToBigDecimal(String value){
		BigDecimal result = new BigDecimal(0);
		if(!"".equals(value)){
			try{
				result = new BigDecimal(value);
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		return result;
	}
        
        
        //--------add by 61962 @2015-08-28----------
	public AccountALS getAcctALSAcctNo(GPGuarantee gpGuarantee,HttpServletRequest request) throws EGuaranteeMQMessageException{
			
		AccountALS accountALS = null;
		MQConfig mqConfig = new MQConfig();
		AccountALSMSGResponse accountALSMQResponse = null;
			
		//connect MQ
		MQMessageService mq = null;
			
		String responseXML = "";
		try {
				
			mq = new MQMessageService();
			
			AccountALSMSGRq acctALSMQRq = copyValueInqueryAcctALSAcctNo(gpGuarantee, request);
			String requsetXML = genXMLReqAcctNoForMQ(acctALSMQRq,request); 
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.SENDER_ID_LGIN);
			accountALSMQResponse = responseAcctNoToObject(responseXML,gpGuarantee);

			if(accountALSMQResponse.getStatusCode().equals("00")){ 
				BigDecimal creditLine = new BigDecimal(0);
				String creditLineStr = StringUtil.nullToBlank((String) accountALSMQResponse.getCreditLine());
				creditLine = stringToBigDecimal(creditLineStr);
				BigDecimal availbal = new BigDecimal(0);
				String availbalStr = StringUtil.nullToBlank((String) accountALSMQResponse.getAvaliBal());
				availbal = stringToBigDecimal(availbalStr);
				String purposeCode = StringUtil.nullToBlank((String) accountALSMQResponse.getPurposeCode());
				String purposeSubCode = StringUtil.nullToBlank((String) accountALSMQResponse.getPurposeSubCode());
		    	accountALS = new AccountALS(
		    			creditLine,
		    			availbal,
		    			purposeCode,
		    			purposeSubCode
		    			); 
		    }else{
		    	accountALS = null;
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return accountALS;
	}
        
	//--------add by 61096 @2015-04-30----------
	public AccountALS getAcctALS(GPGuarantee gpGuarantee,HttpServletRequest request) throws EGuaranteeMQMessageException{
			
		AccountALS accountALS = null;
		MQConfig mqConfig = new MQConfig();
		AccountALSResponse accountALSResponse = null;
			
		//connect MQ
		MQMessageService mq = null;
			
		String responseXML = "";
		try {
				
			mq = new MQMessageService();
			
			AccountALSRq acctALSRq = copyValueInqueryAcctALS(gpGuarantee, request);
			String requsetXML = genXMLReqAcctForMQ(acctALSRq,request); 
			
			responseXML = mq.sendMessage(requsetXML,mqConfig.getReqQueue(),mqConfig.getResQueue(),gpGuarantee.getIssueTypeDesc(), gpGuarantee.getTxRef(), Constants.SENDER_ID_LGIN);
			accountALSResponse = responseAcctToObject(responseXML,gpGuarantee);

			if(accountALSResponse.getStatusCode().equals("00")){ 
				String accountNo = StringUtil.nullToBlank((String) accountALSResponse.getAcctId());
				if("00000000000000".equals(accountNo)){
					accountALS = null;
				}else{
			    	accountALS = new AccountALS(
			    			StringUtil.nullToBlank((String) accountALSResponse.getBankId()),
			    			StringUtil.nullToBlank((String) accountALSResponse.getOcCode()),
			    			StringUtil.nullToBlank((String) accountALSResponse.getAcctId()),
			    			StringUtil.nullToBlank((String) acctALSRq.getTaxId())
			    			); //update by Apichart H.@20150518
			    	accountALS.setBranch(accountALSResponse.getBranchId());
				}
		    }else{
		    	accountALS = null;
		    }
		} catch (Exception e) {
			accountALS = null;
			e.printStackTrace();
		}
			
		return accountALS;
	}
	private String genXMLReqAcctForMQ(AccountALSRq accountALSRq, HttpServletRequest request)throws Exception{
			
			
		String reformatStr = "";
			
		String tempFile = Constants.TEMPLETE_XML_REQ_ACCT_TO_MQ;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			
		template.setContext("serviceName", StringUtil.nullToBlank(accountALSRq.getServiceName()));
		
		//-<SignonSessionRq>
		template.setContext("userId", StringUtil.nullToBlank(accountALSRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(accountALSRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(accountALSRq.getLang()));
		//- </SignonSessionRq>
		
		//- <CustId>
		template.setContext("spName", StringUtil.nullToBlank(accountALSRq.getSpName()));
		//- </CustId>
		
		template.setContext("rqUID", StringUtil.nullToBlank(accountALSRq.getRqUID()));
		
		// - <LOGPARA>
		template.setContext("tUSER", StringUtil.nullToBlank(accountALSRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(accountALSRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(accountALSRq.gettSOURCE()));
		// - </LOGPARA>
			
		template.setContext("effDt",StringUtil.nullToBlank(accountALSRq.getEffDt()));
		template.setContext("taxId", StringUtil.nullToBlank(accountALSRq.getTaxId()));
			
		reformatStr = template.getXML();
		return reformatStr;
	}
        private String genXMLReqTaxIDForMQ(AccountALSMSGRq accountALSMSGRq, HttpServletRequest request)throws Exception{
			
			
		String reformatStr = "";
			
		String tempFile = Constants.TEMPLETE_XML_REQ_ACCTALS_TAXID_TO_MQ;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			
		template.setContext("serviceName", StringUtil.nullToBlank(accountALSMSGRq.getServiceName()));
		
		//-<SignonSessionRq>
		template.setContext("userId", StringUtil.nullToBlank(accountALSMSGRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(accountALSMSGRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(accountALSMSGRq.getLang()));
		//- </SignonSessionRq>
		
		//- <CustId>
		template.setContext("spName", StringUtil.nullToBlank(accountALSMSGRq.getSpName()));
		//- </CustId>
		
		template.setContext("rqUID", StringUtil.nullToBlank(accountALSMSGRq.getRqUID()));
		
		// - <LOGPARA>
		template.setContext("tUSER", StringUtil.nullToBlank(accountALSMSGRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(accountALSMSGRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(accountALSMSGRq.gettSOURCE()));
		// - </LOGPARA>
			
		template.setContext("effDt",StringUtil.nullToBlank(accountALSMSGRq.getEffDt()));
		template.setContext("taxId", StringUtil.nullToBlank(accountALSMSGRq.getTaxId()));
			
		reformatStr = template.getXML();
		return reformatStr;
	}
        private String genXMLReqAcctNoForMQ(AccountALSMSGRq accountALSMSGRq, HttpServletRequest request)throws Exception{
			
			
		String reformatStr = "";
			
		String tempFile = Constants.TEMPLETE_XML_REQ_ACCTALS_ACCTNO_TO_MQ;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			
		template.setContext("serviceName", StringUtil.nullToBlank(accountALSMSGRq.getServiceName()));
		
		//-<SignonSessionRq>
		template.setContext("userId", StringUtil.nullToBlank(accountALSMSGRq.getUserId()));
		template.setContext("session", StringUtil.nullToBlank(accountALSMSGRq.getSession()));
		template.setContext("lang", StringUtil.nullToBlank(accountALSMSGRq.getLang()));
		//- </SignonSessionRq>
		
		//- <CustId>
		template.setContext("spName", StringUtil.nullToBlank(accountALSMSGRq.getSpName()));
		//- </CustId>
		
		template.setContext("rqUID", StringUtil.nullToBlank(accountALSMSGRq.getRqUID()));
		
		// - <LOGPARA>
		template.setContext("tUSER", StringUtil.nullToBlank(accountALSMSGRq.gettUSER()));
		template.setContext("tBRANCH", StringUtil.nullToBlank(accountALSMSGRq.gettBRANCH()));
		template.setContext("tSOURCE", StringUtil.nullToBlank(accountALSMSGRq.gettSOURCE()));
		// - </LOGPARA>
			
		
		//template.setContext("taxId", StringUtil.nullToBlank(accountALSMSGRq.getTaxId()));
        template.setContext("acctid", StringUtil.nullToBlank(accountALSMSGRq.getAcctid()));
        template.setContext("accttype", StringUtil.nullToBlank(accountALSMSGRq.getAccttype()));
        template.setContext("effDt",StringUtil.nullToBlank(accountALSMSGRq.getEffDt()));
		
		reformatStr = template.getXML();
		System.out.println("genXML : " +reformatStr);
		return reformatStr;
	}
        private AccountALSMSGRq copyValueInqueryAcctALSTaxId(GPGuarantee gpGuarantee, HttpServletRequest request){
		
		AccountALSMSGRq accountALSMSGRq = new AccountALSMSGRq();
		
		accountALSMSGRq.setRqUID(MqMessageUtils.generateRqUID());
		accountALSMSGRq.settBRANCH(gpGuarantee.getBranchCode());
		
		accountALSMSGRq.setEffDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm())); // wait to confirm
		accountALSMSGRq.setTaxId(gpGuarantee.getVendorTaxId());
                //accountALSMSGRq.setAcctid(gpGuarantee.getAccountNo());
                
		
		return accountALSMSGRq;
	}
        private AccountALSMSGRq copyValueInqueryAcctALSAcctNo(GPGuarantee gpGuarantee, HttpServletRequest request){
		
		AccountALSMSGRq accountALSMSGRq = new AccountALSMSGRq();
		
		accountALSMSGRq.setRqUID(MqMessageUtils.generateRqUID());
		accountALSMSGRq.settBRANCH(gpGuarantee.getBranchCode());
		
		//accountALSMSGRq.setEffDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm())); // wait to confirm
		//accountALSMSGRq.setTaxId(gpGuarantee.getVendorTaxId());
                accountALSMSGRq.setAcctid(gpGuarantee.getAccountNo());
                accountALSMSGRq.setAccttype("");
                
		
		return accountALSMSGRq;
	}
	private AccountALSRq copyValueInqueryAcctALS(GPGuarantee gpGuarantee, HttpServletRequest request){
		
		AccountALSRq accountALSRq = new AccountALSRq();
		
		accountALSRq.setRqUID(MqMessageUtils.generateRqUID());
		accountALSRq.settBRANCH(gpGuarantee.getBranchCode());
		MQConfig mqConfig = new MQConfig();
		if(MODE_DEV.equalsIgnoreCase(mqConfig.getMode())){
			String configProcessDateStr = mqConfig.getProcessDate();
			accountALSRq.setEffDt(DateUtil.convertFormatDateString(configProcessDateStr));
		}else{
			accountALSRq.setEffDt(DateUtil.convertFormatDateString(gpGuarantee.getDtm()));
		}
		accountALSRq.setTaxId(gpGuarantee.getVendorTaxId());
		
		return accountALSRq;
	}	
	private AccountALSResponse responseAcctToObject(String responseXML, GPGuarantee gpGuarantee){
		
		AccountALSResponse accountALSResponse = new AccountALSResponse();

		accountALSResponse.setCustomsRefNo(gpGuarantee.getTxRef());
		accountALSResponse.setStatusCode(XMLUtil.getTagValue("StatusCode", responseXML));
		accountALSResponse.setStatusDesc(XMLUtil.getTagValue("StatusDesc", responseXML));
		accountALSResponse.setAcctId(XMLUtil.getTagValue("AcctId", responseXML));
		accountALSResponse.setBankId(XMLUtil.getTagValue("BankId", responseXML));
		accountALSResponse.setAcctCur(XMLUtil.getTagValue("AcctCur", responseXML));
		accountALSResponse.setProdCode(XMLUtil.getTagValue("ProdCode", responseXML));
		accountALSResponse.setOcCode(XMLUtil.getTagValue("OCCode", responseXML));
		//added
		accountALSResponse.setBranchId(XMLUtil.getTagValue("BranchId", responseXML));
		/*
		 * 
		 */
		
		 
		return accountALSResponse;
	}
	private AccountALSMSGResponse responseAcctNoToObject(String responseXML, GPGuarantee gpGuarantee){
		
		AccountALSMSGResponse accountALSMSGResponse = new AccountALSMSGResponse();

		accountALSMSGResponse.setCustomsRefNo(gpGuarantee.getTxRef());
		accountALSMSGResponse.setStatusCode(XMLUtil.getTagValue("StatusCode", responseXML));
		accountALSMSGResponse.setStatusDesc(XMLUtil.getTagValue("StatusDesc", responseXML));
                
		accountALSMSGResponse.setAcctType(XMLUtil.getTagValue("AcctType", responseXML));
                accountALSMSGResponse.setCreditLine(XMLUtil.getTagValue("CreditLine", responseXML));
                accountALSMSGResponse.setAvaliBal(XMLUtil.getTagValue("AvailBal", responseXML));
                accountALSMSGResponse.setOutstandingBal(XMLUtil.getTagValue("OutstandingBal", responseXML));
                accountALSMSGResponse.setOpenDt(XMLUtil.getTagValue("OpenDt", responseXML));
                accountALSMSGResponse.setDurationFrequency(XMLUtil.getTagValue("DurationFrequency", responseXML));
                accountALSMSGResponse.setDurationIncrement(XMLUtil.getTagValue("DurationIncrement", responseXML));
                accountALSMSGResponse.setLastPmtDt(XMLUtil.getTagValue("LastPmtDt", responseXML));
                accountALSMSGResponse.setNoPeriodUnPaid(XMLUtil.getTagValue("NoPeriodUnPaid", responseXML));
                accountALSMSGResponse.setAmtUnPaid(XMLUtil.getTagValue("AmtUnPaid", responseXML));
                accountALSMSGResponse.setDayUnPaid(XMLUtil.getTagValue("DayUnPaid", responseXML));
                accountALSMSGResponse.setTdrStatus(XMLUtil.getTagValue("TDRStatus", responseXML));
                accountALSMSGResponse.setInstStatus(XMLUtil.getTagValue("INSTStatus", responseXML));
                accountALSMSGResponse.setNoticeDt(XMLUtil.getTagValue("NoticeDt", responseXML));
                accountALSMSGResponse.setNextBillDt(XMLUtil.getTagValue("NextBillDt", responseXML));
                accountALSMSGResponse.setBhschdPymtAmt(XMLUtil.getTagValue("BHSchdPymtAmt", responseXML));
                accountALSMSGResponse.setNonAccrInt(XMLUtil.getTagValue("NonAccrInt", responseXML));
                accountALSMSGResponse.setPurposeCode(XMLUtil.getTagValue("PurposeCode", responseXML));
                accountALSMSGResponse.setPurposeSubCode(XMLUtil.getTagValue("PurposeSubCode", responseXML));
                
               
		/*
		 * 
		 */
		
		 
		return accountALSMSGResponse;
	}
}
