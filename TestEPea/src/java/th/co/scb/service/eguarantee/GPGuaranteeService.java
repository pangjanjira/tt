package th.co.scb.service.eguarantee;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;

import th.co.scb.db.BankInfoTable;
import th.co.scb.db.ConfigTable;
import th.co.scb.db.DocRunningTable;
import th.co.scb.db.eguarantee.AccountALSTable;
import th.co.scb.db.eguarantee.BUCodeTable;
import th.co.scb.db.eguarantee.GPGuaranteeTable;
import th.co.scb.db.eguarantee.ProjectTaxTable;
import th.co.scb.db.eguarantee.TranGPOfflineTable;
import th.co.scb.model.BankInfo;
import th.co.scb.model.EMapCodeError;
import th.co.scb.model.EStatus;
import th.co.scb.model.ETime;
import th.co.scb.model.LogEBXML;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.model.eguarantee.BUCode;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.GPGuaranteeALSOffline;
import th.co.scb.model.eguarantee.ProjectTax;
import th.co.scb.model.eguarantee.TranGPOffline;
import th.co.scb.model.mq.LoanLGResponse;
import th.co.scb.service.ALSMQService;
import th.co.scb.service.EMapCodeErrorService;
import th.co.scb.service.EStatusService;
import th.co.scb.service.ETimeService;
import th.co.scb.service.OffHostService;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
import th.co.scb.service.mq.MQMessageService;
import th.co.scb.util.ConfigXMLCustomsGP;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.StringUtil;
import th.co.scb.util.TemplateUtil;
import th.co.scb.util.XPathReader;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class GPGuaranteeService {

	private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeService.class);
	private int delayTime = 15;
	
	public GPGuarantee processGPguaranteeWebService(LogEBXML logEBXML, ETime eTime, HttpServletRequest request)throws Exception {
    	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    	//changed to
		ConnectDB connectDB = null;
		boolean isRollback = false;
		//UR59040034 Add eGP Pending Review & Resend Response function
		//Change ALSOffline process
		TranGPOffline tranGPOffline = null;
		
		GPGuarantee gpGuarantee = new GPGuarantee();
		GPReviewLogService gpReviewLogService = new GPReviewLogService();
		GPApprovalLogService gpApprovalLogService = new GPApprovalLogService();
		String requestor = (request.getParameter("requestor") != null ? request.getParameter("requestor") : "");
		System.out.println("=========== processGPguaranteeWebService =========");
        System.out.print("[Parameter]");
        System.out.print("requestor : " + request.getParameter("requestor"));
		try{
			//open connection
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<processGPguaranteeWebService> new connection."+connectDB.hashCode());
			connectDB.beginTransaction();
			ConfigTable configTable = new ConfigTable(connectDB);
			delayTime = configTable.getDelayTime();
			
			System.out.println("=========== do processing =========");
            if (requestor.toLowerCase().equals("")) {
            	//request from customer
            	System.out.print("----- [Request from Customer] -----");
            	System.out.print("XmlInput : " + logEBXML.getXmlInput());
            	gpGuarantee = processCustomerRequest(logEBXML, eTime, request, connectDB);
            	
                //commit transaction
    			connectDB.commit();
    			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
    			//if(Constants.GPGuarantee.CANCEL_ISSUE.equals(gpGuarantee.getIssueType())
    			//		|| Constants.GPGuarantee.CLAIM_ISSUE.equals(gpGuarantee.getIssueType())){
    			//	GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
    			//	gpGuaranteeService.insertGPGuarantee(gpGuarantee);
    			//}
    			GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
    			gpGuaranteeService.insertGPGuarantee(gpGuarantee);
    			
    			//UR59040034 Add eGP Pending Review & Resend Response function
    			//Change ALSOffline process
    			if (!gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE) && "0".equals(gpGuarantee.getAlsOnline()) 
    					&& gpGuarantee.getAccountNo() != null && !("".equals(gpGuarantee.getAccountNo())
    							&& gpGuarantee.getLgNo() != null && !("".equals(gpGuarantee.getLgNo())))){
    				
    				tranGPOffline = new TranGPOffline(gpGuarantee.getTxRef(), logEBXML.getXmlInput(), gpGuarantee.getLgNo(), gpGuarantee.getId());
    				addTranGPOffline(tranGPOffline);
    			}
    			//commit transaction
    			//connectDB.commit();
    			
    			//Fixing for UR58120031 add-extend-expire-date-log (Phase 2)
    			if (Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())
    					&& gpGuarantee.getAlsOnline().equals("1")) {
    	        	System.out.println("======== insert gp_review_log ==========");
        	        gpReviewLogService.insert(gpGuarantee);
        	        System.out.println("======== insert gp_approval_log ==========");
        	        gpApprovalLogService.insert(gpGuarantee);
    			}
    			
            } 
            //UR59040034 Add eGP Pending Review & Resend Response function
			//Change ALSOffline process
            else if (requestor.toLowerCase().equals("approval")) {
				//from approval screen
            	System.out.print("----- [Request from Approval] -----");
    	        System.out.print("iGpGuaranteeId : " + request.getParameter("iGpGuaranteeId"));
    	        System.out.print("iApprovalStatus : " + request.getParameter("iApprovalStatus"));
    	        System.out.print("iApprovalReason : " + request.getParameter("iApprovalReason"));
    	        System.out.print("iApprovalBy : " + request.getParameter("iApprovalBy"));
            	gpGuarantee = processApprovalRequest(logEBXML, eTime, request, connectDB);
                //commit transaction
    			connectDB.commit();
    			
            } else if (requestor.toLowerCase().equals("review")) {
            	//from review screen
                System.out.print("----- [Request from Review] -----");
                System.out.print("iGpGuaranteeId : " + request.getParameter("iGpGuaranteeId"));
    	        System.out.print("iReviewStatus : " + request.getParameter("iReviewStatus"));
    	        System.out.print("iReviewReason : " + request.getParameter("iReviewReason"));
    	        System.out.print("iReviewBy : " + request.getParameter("iReviewBy"));
                gpGuarantee = processReviewRequest(logEBXML, eTime, request, connectDB);
                //commit transaction
    			connectDB.commit();
            } 
          //UR59040034 Add eGP Pending Review & Resend Response function
            else if (requestor.toLowerCase().equals("resend")) {
            	//from inquiry screen
                System.out.print("----- [Request from Resend XML Output] -----");
                System.out.print("iGpGuaranteeId : " + request.getParameter("iGpGuaranteeId"));
                gpGuarantee = processResendResponse(request, connectDB);
                //commit transaction
    			connectDB.commit();
            }
		}catch(EGuaranteeMQMessageException ex){
			//rollback transaction
			isRollback = true;
			connectDB.rollback();
			logger.error("Error process GPguarantee WebService : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
		}catch(Exception ex){
			//rollback transaction
			isRollback = true;
			connectDB.rollback();
			logger.error("Error process GPguarantee WebService : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally{
        	if(isRollback){
        		if (Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())){
        			ProjectTaxTable projectTaxTable = new ProjectTaxTable(connectDB);
            		projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
        		}
        		//ProjectTaxTable projectTaxTable = new ProjectTaxTable(connectDB);
        		//projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getLgNo()));   
        	}else{
        		//commit transaction
        		connectDB.commit();
        	}
        	//close connection
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<processGPguaranteeWebService> close connection."+connectDB.hashCode());
        		connectDB.close();
        	}
        }
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setTransactionStatus(GPGuarantee gpGuarantee, String status, String statusLg, String msgCode, String xmlOutput){
		gpGuarantee.setTransactionStatus(status);
		gpGuarantee.setStatusLG(statusLg);
		gpGuarantee.setMsgCode(msgCode);
		gpGuarantee.setXmlOutput(xmlOutput);
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setReviewStatus(GPGuarantee gpGuarantee, String status, Date dtm, String user, String reason){
		gpGuarantee.setReviewStatus(status);
		gpGuarantee.setReviewDtm(dtm);
		gpGuarantee.setReviewBy(user);
		gpGuarantee.setReviewReason(reason);
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setApprovalStatus(GPGuarantee gpGuarantee, String status, Date dtm, String user, String reason){
		gpGuarantee.setApproveStatus(status);
		gpGuarantee.setApproveDtm(dtm);
		gpGuarantee.setApproveBy(user);
		gpGuarantee.setApproveReason(reason);
		return gpGuarantee;
	}
	
	
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		//issueType can be
		//	1. SETUP
		//	2. CANCEL
		//	3. CLAIM
		//	4. EXTEND_EXPIRY_DATE
		GPGuarantee gpGuarantee = new GPGuarantee();
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
        
		//prepare data
        gpGuarantee = gpGuaranteeService.ebxmlToObject(logEBXML.getXmlInput(), eTime);
        //System.out.println("gpGuarantee from XML : " + gpGuarantee);
        
    	//processing
		if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
			//extend
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			gpGuarantee = processExtendExpiryDateFromCustomerRequest(logEBXML, eTime, request, gpGuarantee, connectDB);
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			//gpGuaranteeService.insertGPGuarantee(gpGuarantee);
		}else if(Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())){
			//setup
			gpGuarantee = processSetupFromCustomerRequest(logEBXML, eTime, request, gpGuarantee, connectDB);
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			//gpGuaranteeService.insertGPGuarantee(gpGuarantee);
		}else{
			//cancel, claim
			gpGuarantee = processCancelClaimFromCustomerRequest(logEBXML, eTime, request, gpGuarantee, connectDB);
		}
		gpGuarantee = setGPGuaranteeForResponse(gpGuarantee, connectDB);
		return gpGuarantee;
	}
	
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processReviewRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		//issueType can be
		//	1. SETUP
		//	2. EXTEND_EXPIRY_DATE
		GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
		GPGuarantee gpGuarantee = new GPGuarantee();
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
        GPReviewLogService gpReviewLogService = new GPReviewLogService();
        //prepare data
        int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
        gpGuarantee = gpGuaranteeService.getGPGuaranteeById(id);
        gpGuarantee.setAccountNo(request.getParameter("iAccount"));
		gpGuarantee = setReviewStatus(gpGuarantee
				, request.getParameter("iReviewStatus")		//ReviewStatus
				, new Date()								//ReviewDtm
				, request.getParameter("iReviewBy")			//ReviewBy
				, request.getParameter("iReviewReason"));	//ReviewReason
		//update review status
		System.out.print("----- [Request from Review for iGpGuaranteeId(" + String.valueOf(id) + ") ] -----");
        gpGuaranteeService.updateReviewStatus(gpGuarantee);
        //UR58120031 new and old expire date add by Tana L. @12022016
        if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
        	GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(gpGuarantee.getLgNo());
            gpGuarantee.setNewEndDate(gpGuarantee.getExtendDate());
            gpGuarantee.setOldEndDate(setupTxn.getEndDate());
        }
        gpReviewLogService.insert(gpGuarantee);
        gpGuarantee = setGPGuaranteeForResponse(gpGuarantee, connectDB);
		return gpGuarantee;
	}
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	private GPGuarantee processResendResponse(HttpServletRequest request, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {

		GPGuarantee gpGuarantee = new GPGuarantee();
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
        //prepare data
        int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
        gpGuarantee = gpGuaranteeService.getResendGPGuarantee(id);
        
		
		System.out.print("----- [Request from Resend for iGpGuaranteeId(" + String.valueOf(id) + ") ] -----");
        gpGuaranteeService.updateResendCount(gpGuarantee);
        gpGuarantee = setGPGuaranteeForResponse(gpGuarantee, connectDB);
        //reset status gpGuarantee for sending response
      		if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
      			gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
      			gpGuarantee.setMsgCode(Constants.MessageCode.PLEASE_CONTACT_SCB);
      			EStatusService eStatusService = new EStatusService();
      			EStatus eStatus = null;
                  try {
                      eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, Constants.MessageCode.PLEASE_CONTACT_SCB));
                  } catch (Exception e) {
                      eStatus = new EStatus(gpGuarantee.getMsgCode(), "");
                      eStatus.setDescEn(Constants.StatusLGGP.PLEASE_CONTACT_SCB);
                  }
                  gpGuarantee.setXmlOutput(eStatus.getDescEn());
      		}
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processApprovalRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		//issueType can be
		//	1. SETUP
		//	2. EXTEND_EXPIRY_DATE
		GPGuarantee gpGuarantee = new GPGuarantee();
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
		//prepare data
		int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
		gpGuarantee = gpGuaranteeService.getGPGuaranteeById(id);
		gpGuarantee.setAccountNo(request.getParameter("iAccount"));
		gpGuarantee = setApprovalStatus(gpGuarantee
				, request.getParameter("iApprovalStatus")	//ApproveStatus
				, new Date()								//ApproveDtm
				, request.getParameter("iApprovalBy")		//ApproveBy
				, request.getParameter("iApprovalReason"));	//ApproveReason
		
		//update approval status before processing
		gpGuaranteeService.updateApproveStatus(gpGuarantee);
		//process approval
		System.out.print("----- [Request from Approval for iGpGuaranteeId(" + String.valueOf(id) + ") ] -----");
    	if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
    		//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
    		gpGuarantee = processExtendExpiryDateFromApprovalForm(logEBXML, eTime, request, gpGuarantee, connectDB);
    	}else if(Constants.GPGuarantee.CANCEL_ISSUE.equals(gpGuarantee.getIssueType())){		    		
    		gpGuarantee = processCancelFromApprovalForm(logEBXML, eTime, request, gpGuarantee, connectDB);
    	}else{
    		gpGuarantee = processSetupFromApprovalForm(logEBXML, eTime, request, gpGuarantee, connectDB);
    	}
    	//set response fields
    	gpGuarantee = setGPGuaranteeForResponse(gpGuarantee, connectDB);
		//reset status gpGuarantee for sending response
		if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
			gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
			gpGuarantee.setMsgCode(Constants.MessageCode.PLEASE_CONTACT_SCB);
			EStatusService eStatusService = new EStatusService();
			EStatus eStatus = null;
            try {
                eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, Constants.MessageCode.PLEASE_CONTACT_SCB));
            } catch (Exception e) {
                eStatus = new EStatus(gpGuarantee.getMsgCode(), "");
                eStatus.setDescEn(Constants.StatusLGGP.PLEASE_CONTACT_SCB);
            }
            gpGuarantee.setXmlOutput(eStatus.getDescEn());
		}
		return gpGuarantee;
	}
	
    private boolean isExtendAfterPrevEndDate(GPGuarantee gpGuarantee){
        boolean result = false;
        Date extendDate = DateUtil.stringToDate(gpGuarantee.getExtendDate());
        Date prevEndDate = DateUtil.stringToDate(gpGuarantee.getPrevEndDate());
        System.out.println("extendDate: " + String.valueOf(extendDate));
        System.out.println("prevEndDate: " + String.valueOf(prevEndDate));
        if (extendDate != null && prevEndDate != null){
            if(extendDate.after(prevEndDate)){
                System.out.println("extendDate -> true");
                result = true;
            }else{
                System.out.println("extendDate -> false 2");
            }
        }else{
            System.out.println("extendDate -> false 1");
        }
        return result;
    }
    
    
   //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
   //changed to
   private GPGuarantee processExtendExpiryDateFromCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
       System.out.println("----- processExtendExpiryDateFromCustomerRequest -----");
       GPGuarantee gpGuarantee = input;
       OffHostService offHostService = null;
       ALSMQService alsMQService = null;
       boolean isOffline = eTime.isTimeOffHostALS();
       try{
           offHostService = new OffHostService();
           alsMQService = new ALSMQService();
           GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);           
           System.out.println("======== LGNo. [" + gpGuarantee.getLgNo() + "] is EXTEND_EXPIRY_DATE_ISSUE ==========");
           System.out.println("======== (request from customer) ==========");
           //get gp_guarantee data from the setup txn
           gpGuarantee = getGPGuaranteeSetup(gpGuarantee, connectDB);
          
           //send SMS (for EXTEND)
           System.out.println("======== Send SMS (EXTEND_ISSUE) ==========");
           //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
           try{
               sendExtendSMStoMQ(gpGuarantee , request);
           }catch(Exception e){
               System.out.println("Error Send SMS (EXTEND_ISSUE): " + e.getMessage());
           }
          
           boolean isExtendAfterEndDate = isExtendAfterPrevEndDate(gpGuarantee);
          
           if(!isExtendAfterEndDate){
               System.out.println("======== Extend (" + gpGuarantee.getExtendDate() + ") Before of Equal EndDate (" + gpGuarantee.getPrevEndDate() + "), reject request ==========");
               System.out.println("======== set flag for auto rejected ==========");
               gpGuarantee = setStatusInvalidExtendDate(gpGuarantee);
               System.out.println("=========== process fail - do not send extend mail to customer ============");
           }else{
               System.out.println("======== Extend (" + gpGuarantee.getExtendDate() + ") after EndDate (" + gpGuarantee.getPrevEndDate() + "), process further ==========");
               System.out.println("======== set flag for auto approved ==========");
               gpGuarantee = setStatusAutoApproved(gpGuarantee);
              
               //check e_time for further processing
               if(isOffline == true){
                   System.out.println("=========== als offline (stored into tran_gp_offline) ============");
                   //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
                   gpGuarantee = offHostService.manageGPGuaranteeOffline(connectDB, gpGuarantee, logEBXML.getXmlInput(), eTime);
               }else{
                   System.out.println("=========== als online (straight through to ALS) ============");
                   //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
                   gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
               }
              
               //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
              
               //Fixing for UR58120031 add-extend-expire-date-log (Phase 2)
               GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(gpGuarantee.getLgNo());
               gpGuarantee.setNewEndDate(gpGuarantee.getExtendDate());
               gpGuarantee.setOldEndDate(setupTxn.getEndDate());
              
               //if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(gpGuarantee.getApproveStatus())) {
                   //System.out.println("======== insert gp_guarantee_h ==========");
                  // addSetupTxnHistoryFromLGNo(gpGuarantee.getApproveBy(), gpGuarantee.getLgNo(), connectDB);
               //}
              
               if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(gpGuarantee.getApproveStatus())
                       && gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)) {
                   System.out.println("======== insert gp_guarantee_h ==========");
                   addSetupTxnHistoryFromLGNo(gpGuarantee.getApproveBy(), gpGuarantee.getLgNo(), connectDB);
                   System.out.println("======== update expire_date of the setup txn ==========");
                   //updateSetupTxnExpireDateAndStatus(gpGuarantee);
                   //GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
                   gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(gpGuarantee.getExtendDate(), gpGuarantee.getLgNo());
               }
               //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
              
               //send mail in case of success
               if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
                   System.out.println("=========== process success - send extend mail to customer ============");
                   //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
                   try{
                       sendExtendMailtoMQ(gpGuarantee, request);
                   }catch(Exception e){
                       System.out.println("Error send extend mail to customer: " + e.getMessage());
                   }
               }else{
                   System.out.println("=========== process fail - do not send extend mail to customer ============");
               }
           }
       }catch(Exception e){
           System.out.println("processExtendExpiryDateFromCustomerRequest Error: " + e.getMessage());
           throw e;
       }
       return gpGuarantee;
   }
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	/*
	private GPGuarantee processExtendExpiryDateFromCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processExtendExpiryDateFromCustomerRequest -----");
		GPGuarantee gpGuarantee = input;
		OffHostService offHostService = null;
		ALSMQService alsMQService = null;
		boolean isOffline = eTime.isTimeOffHostALS();
		try{
			offHostService = new OffHostService();
			alsMQService = new ALSMQService();
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);			
			System.out.println("======== LGNo. [" + gpGuarantee.getLgNo() + "] is EXTEND_EXPIRY_DATE_ISSUE ==========");
	        System.out.println("======== (request from customer) ==========");
	        //get gp_guarantee data from the setup txn
	        gpGuarantee = getGPGuaranteeSetup(gpGuarantee, connectDB);
	        
	        //send SMS (for EXTEND)
	        System.out.println("======== Send SMS (EXTEND_ISSUE) ==========");
	        //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	        try{
	        	sendExtendSMStoMQ(gpGuarantee , request);
	        }catch(Exception e){
	        	System.out.println("Error Send SMS (EXTEND_ISSUE): " + e.getMessage());
	        }
	        
	        System.out.println("======== set flag for auto approved ==========");
	        gpGuarantee = setStatusAutoApproved(gpGuarantee);
	        
	        //check e_time for further processing
	        if(isOffline == true){
	        	System.out.println("=========== als offline (stored into tran_gp_offline) ============");
	        	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	        	gpGuarantee = offHostService.manageGPGuaranteeOffline(connectDB, gpGuarantee, logEBXML.getXmlInput(), eTime);
	        }else{
	        	System.out.println("=========== als online (straight through to ALS) ============");
	        	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	        	gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
	        }
	        
	        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	        
	      //Fixing for UR58120031 add-extend-expire-date-log (Phase 2)
	        GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(gpGuarantee.getLgNo());
	        gpGuarantee.setNewEndDate(gpGuarantee.getExtendDate());
	        gpGuarantee.setOldEndDate(setupTxn.getEndDate());
	        
	        //if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(gpGuarantee.getApproveStatus())) {
	        //	System.out.println("======== insert gp_guarantee_h ==========");
	        //	addSetupTxnHistoryFromLGNo(gpGuarantee.getApproveBy(), gpGuarantee.getLgNo(), connectDB);
	        //}
	        
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(gpGuarantee.getApproveStatus())
	        		&& gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)) {
	        	System.out.println("======== insert gp_guarantee_h ==========");
	        	addSetupTxnHistoryFromLGNo(gpGuarantee.getApproveBy(), gpGuarantee.getLgNo(), connectDB);
	        	System.out.println("======== update expire_date of the setup txn ==========");
	        	//updateSetupTxnExpireDateAndStatus(gpGuarantee);
	        	//GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
	        	gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(gpGuarantee.getExtendDate(), gpGuarantee.getLgNo());
	        }
	        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	        
	        //send mail in case of success
	        if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
	        	System.out.println("=========== process success - send extend mail to customer ============");
	        	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
		        try{
		        	sendExtendMailtoMQ(gpGuarantee, request);
		        }catch(Exception e){
		        	System.out.println("Error send extend mail to customer: " + e.getMessage());
		        }
	        }else{
	        	System.out.println("=========== process fail - do not send extend mail to customer ============");
	        }
		}catch(Exception e){
			System.out.println("processExtendExpiryDateFromCustomerRequest Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	*/
	//changed from
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	/*
	private GPGuarantee processExtendExpiryDateFromCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		GPGuarantee gpGuarantee = input;
		try{
			System.out.println("======== LGNo. [" + gpGuarantee.getLgNo() + "] is EXTEND_EXPIRY_DATE_ISSUE ==========");
	        System.out.println("======== (request from customer) ==========");
	        //get gp_guarantee data from the setup txn
	        gpGuarantee = getGPGuaranteeSetup(gpGuarantee, connectDB);
	        System.out.println("======== set flag for pending review (extend expiry date) ==========");
	        gpGuarantee = setStatusPendingReview(gpGuarantee, Constants.GPGuarantee.REVIEW_REASON_AUTO_PENDING_EXTEND_EXPIRY_DATE);
		}catch(Exception e){
			System.out.println("processExtendExpiryDateFromCustomerRequest Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	*/
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee getGPGuaranteeSetup(GPGuarantee input, ConnectDB connectDB){
		GPGuarantee gpGuarantee = input;
		GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
        GPGuarantee setupGuarantee = gpGuaranteeTable.getGPGuaranteeByLGNo(gpGuarantee.getLgNo());
        //setupGuarantee.getDeptCode() != null ? setupGuarantee.getDeptCode() : "-";
        gpGuarantee.setDeptCode(setupGuarantee.getDeptCode() != null ? setupGuarantee.getDeptCode() : "-");
        gpGuarantee.setAccountNo(setupGuarantee.getAccountNo());
        gpGuarantee.setUserId(setupGuarantee.getUserId()!= null ? setupGuarantee.getUserId() : "-");
        gpGuarantee.setSeqNo(setupGuarantee.getSeqNo());
        gpGuarantee.setConsiderDesc(setupGuarantee.getConsiderDesc());
        gpGuarantee.setConsiderMoney(setupGuarantee.getConsiderMoney());
        gpGuarantee.setGuaranteeAmt(setupGuarantee.getGuaranteeAmt());
        gpGuarantee.setAmtReq(setupGuarantee.getAmtReq());
        gpGuarantee.setContractNo(setupGuarantee.getContractNo() != null ? setupGuarantee.getContractNo() : "-");
        gpGuarantee.setContractDate(setupGuarantee.getContractDate() != null ? setupGuarantee.getContractDate() : "1900-01-01");
        //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
        gpGuarantee.setGuaranteePrice(setupGuarantee.getGuaranteePrice() != null ? setupGuarantee.getGuaranteePrice() : new BigDecimal(0));
        gpGuarantee.setGuaranteePercent(setupGuarantee.getGuaranteePercent() != null ? setupGuarantee.getGuaranteePercent() : new BigDecimal(0));
        
        gpGuarantee.setAdvanceGuaranteePrice(setupGuarantee.getAdvanceGuaranteePrice());
        gpGuarantee.setAdvancePayment(setupGuarantee.getAdvancePayment());
        gpGuarantee.setWorksGuaranteePrice(setupGuarantee.getWorksGuaranteePrice());
        gpGuarantee.setWorksGuaranteePercent(setupGuarantee.getWorksGuaranteePercent());
        gpGuarantee.setCollectionPhase(setupGuarantee.getCollectionPhase());
        gpGuarantee.setProjName(setupGuarantee.getProjName() != null ? setupGuarantee.getProjName() : "-");
        //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
        gpGuarantee.setProjAmt(setupGuarantee.getProjAmt() != null ? setupGuarantee.getProjAmt() : new BigDecimal(0));
        gpGuarantee.setProjOwnName(setupGuarantee.getProjOwnName() != null ? setupGuarantee.getProjOwnName() : "-");
        gpGuarantee.setCostCenter(setupGuarantee.getCostCenter());
        gpGuarantee.setCostCenterName(setupGuarantee.getCostCenterName());
        gpGuarantee.setDocumentNo(setupGuarantee.getDocumentNo());
        gpGuarantee.setDocumentDate(setupGuarantee.getDocumentDate());
        gpGuarantee.setExpireDate(setupGuarantee.getExpireDate());
        gpGuarantee.setAppvAmt(setupGuarantee.getAppvAmt());
        gpGuarantee.setAppvDate(setupGuarantee.getAppvDate());
        gpGuarantee.setOcCode(setupGuarantee.getOcCode());
        
        gpGuarantee.setPrevEndDate(setupGuarantee.getEndDate());
        return gpGuarantee;
	}
	
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processSetupFromCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processSetupFromCustomerRequest -----");
		GPGuarantee gpGuarantee = input;
		boolean isDupGPGuarantee = false;
		OffHostService offHostService = null;
		ALSMQService alsMQService = null;
		boolean isOffline = eTime.isTimeOffHostALS();
		try{
			offHostService = new OffHostService();
			alsMQService = new ALSMQService();
			System.out.println("======== issueType [" + gpGuarantee.getIssueType() + "] ==========");
			//determine isMultipleAccount
			boolean isMultipleAccount = false;
			if(isTaxIdHasMultipleAccount(connectDB, gpGuarantee)){
				isMultipleAccount = true;
			}
			if(isMultipleAccount){
		        System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is has multiple account ==========");
		        System.out.println("======== set flag for pending review (multiple account) ==========");
                gpGuarantee = setStatusPendingReview(gpGuarantee, Constants.GPGuarantee.REVIEW_REASON_AUTO_PENDING_MULTI_ACCT);
			}else{
				System.out.println("======== Not multiple account ==========");
				//get account
				AccountALS accountAls = getAccount(connectDB, isOffline, gpGuarantee, request);
				//determine process by account
				if(accountAls == null){
					//account not found
					//System.out.println("======== Account is null ==========");
					//gpGuarantee = setStatusInvalidCustomer(gpGuarantee, Constants.StatusLGGP.REJECT);
					
					//UR59040034 Add eGP Pending Review & Resend Response function
					System.out.println("======== Account is null ==========");
					System.out.println("======== set flag for pending review (account not found) ==========");
					gpGuarantee = setStatusPendingReview(gpGuarantee, Constants.GPGuarantee.REVIEW_REASON_AUTO_PENDING_NULL_ACCT);
				}else {
					//account found
					//send SMS (for SETUP)
					//check duplicate
					System.out.println("======== Account is not null ==========");
					gpGuarantee.setAccountNo(accountAls.getAccountNo());
					gpGuarantee.setOcCode(accountAls.getOcCode());
					System.out.println("======== Send SMS (SETUP_ISSUE) ==========");
					sendSMStoMQ(gpGuarantee , request);
					System.out.println("======== Check DUP in project_tax ==========");
					isDupGPGuarantee = isDupGPGuarantee(gpGuarantee);
					
					if(isDupGPGuarantee == true){
						System.out.println("======== DUP ==========");
						System.out.println("======== set flag for auto rejected ==========");
						gpGuarantee = setStatusDuplicateTransaction(gpGuarantee);
						//delay the duplicate record
						delay(50);
					}else {
						System.out.println("======== No-DUP ==========");
						ControlAccountService controlAccountService = new ControlAccountService();
						if (controlAccountService.isControlAccount(gpGuarantee.getAccountNo(), connectDB)) {
                            //set flag for pending review
                            System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is SETUP_ISSUE and is in control_account ==========");
                            System.out.println("======== set flag for pending review ==========");
                            gpGuarantee = setStatusPendingReview(gpGuarantee, Constants.GPGuarantee.REVIEW_REASON_AUTO_PENDING_CONTROL_ACCT);
						}else {
							//set flag for approved
							System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is not SETUP_ISSUE or is not in control_account ==========");
							System.out.println("======== set flag for auto approved ==========");
							gpGuarantee = setStatusAutoApproved(gpGuarantee);
							
							//check e_time for further processing
							if(eTime.isTimeOffHostALS() == true){
								System.out.println("=========== als offline (stored into tran_gp_offline) ============"); 
								gpGuarantee = offHostService.manageGPGuaranteeOffline(connectDB, gpGuarantee, logEBXML.getXmlInput(), eTime);
							}else{
								System.out.println("=========== als online (straight through to ALS) ============");
								gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
							}
							//send mail in case of success
							if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
								System.out.println("=========== process success - send mail to customer ============");
								sendMailtoMQ(gpGuarantee, request);
								
								//UR58120031 Phase 2
								//set AppvAmt, AppvDate
								gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
								gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));
							}
						}
					}
				}
			}
		}catch(EGuaranteeMQMessageException mqe){
			System.out.println("processSetupFromCustomerRequest MQError: " + mqe.getMessage());
			throw mqe;
		}catch(Exception e){
			System.out.println("processSetupFromCustomerRequest Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processCancelClaimFromCustomerRequest(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processCancelClaimFromCustomerRequest -----");
		GPGuarantee gpGuarantee = input;
		OffHostService offHostService = null;
		ALSMQService alsMQService = null;
		boolean isOffline = eTime.isTimeOffHostALS();
		try{
			offHostService = new OffHostService();
			alsMQService = new ALSMQService();
			System.out.println("======== issueType [" + gpGuarantee.getIssueType() + "] ==========");
			//set status
			gpGuarantee = setStatusAutoApproved(gpGuarantee);
			//get account
			AccountALS accountAls = getAccount(connectDB, isOffline, gpGuarantee, request);
			//determine process by account
			if(accountAls == null){
				//account not found
				//set error status
				System.out.println("======== Account is null ==========");
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
					gpGuarantee = setStatusInvalidCustomer(gpGuarantee, Constants.StatusLGGP.CANCLE);
				}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
					gpGuarantee = setStatusInvalidCustomer(gpGuarantee, Constants.StatusLGGP.CLAIM);
				}
			}else {
				//account found
				//send SMS (for SETUP)
				//check duplicate
				System.out.println("======== Account is not null ==========");
				gpGuarantee.setAccountNo(accountAls.getAccountNo());
				gpGuarantee.setOcCode(accountAls.getOcCode());
				
				//UR58120031 Phase 2
				gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
				gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));
				
				//set flag for approved
				System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is not SETUP_ISSUE or is not in control_account ==========");
				System.out.println("======== set flag for auto approved ==========");
				gpGuarantee = setStatusAutoApproved(gpGuarantee);
				
				//check e_time for further processing
				if(eTime.isTimeOffHostALS() == true){
					System.out.println("=========== als offline (stored into tran_gp_offline) ============"); 
					gpGuarantee = offHostService.manageGPGuaranteeOffline(connectDB, gpGuarantee, logEBXML.getXmlInput(), eTime);
				}else{
					System.out.println("=========== als online (straight through to ALS) ============");
					gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
				}
				//send mail in case of success
				if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
					System.out.println("=========== process success - send mail to customer ============");
					sendMailtoMQ(gpGuarantee, request);
				}
			}
		}catch(EGuaranteeMQMessageException mqe){
			System.out.println("processCancelClaimFromCustomerRequest MQError: " + mqe.getMessage());
			throw mqe;
		}catch(Exception e){
			System.out.println("processCancelClaimFromCustomerRequest Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setStatusPendingReview(GPGuarantee gpGuarantee, String pendingReason){
		System.out.println("----- setStatusPendingReview -----");
		gpGuarantee = setTransactionStatus(gpGuarantee
				, null			//TransactionStatus
				, null			//StatusLG
				, null			//MsgCode
				, null);		//XmlOutput
		gpGuarantee = setReviewStatus(gpGuarantee
						, Constants.GPGuarantee.REVIEW_PENDING					//ReviewStatus
						, new Date()											//ReviewDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
						, pendingReason);	//ReviewReason
		gpGuarantee = setApprovalStatus(gpGuarantee
						, null		//ApproveStatus
						, null		//ApproveDtm
						, null		//ApproveBy
						, null);	//ApproveReason
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setStatusDuplicateTransaction(GPGuarantee gpGuarantee){
		System.out.println("----- setStatusDuplicateTransaction -----");
		gpGuarantee = setTransactionStatus(gpGuarantee
				, Constants.EGuarantee.STATUS_UNSUCCESS			//TransactionStatus
				, Constants.StatusLGGP.REJECT					//StatusLG
				, Constants.MessageCode.DUP_TRANSACTION			//MsgCode
				, Constants.StatusLGGP.DUPLICATION);			//XmlOutput
		gpGuarantee = setReviewStatus(gpGuarantee
						, Constants.GPGuarantee.REVIEW_REJECTED					//ReviewStatus
						, new Date()											//ReviewDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
						, Constants.GPGuarantee.REVIEW_REASON_AUTO_REJECTED);	//ReviewReason
		gpGuarantee = setApprovalStatus(gpGuarantee
						, Constants.GPGuarantee.APPROVAL_REJECTED				//ApproveStatus
						, new Date()											//ApproveDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ApproveBy
						, Constants.GPGuarantee.APPROVAL_REASON_AUTO_REJECTED);	//ApproveReason
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setStatusInvalidCustomer(GPGuarantee gpGuarantee, String statusLG){
		System.out.println("----- setStatusInvalidCustomer -----");
		gpGuarantee = setTransactionStatus(gpGuarantee
				, Constants.EGuarantee.STATUS_UNSUCCESS			//TransactionStatus
				, statusLG										//StatusLG
				, Constants.MessageCode.INVALID_INFO_CUSTOMER	//MsgCode
				, Constants.StatusLGGP.INVALID_INFO_CUSTOMER);	//XmlOutput
		gpGuarantee = setReviewStatus(gpGuarantee
						, Constants.GPGuarantee.REVIEW_REJECTED					//ReviewStatus
						, new Date()											//ReviewDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
						, Constants.GPGuarantee.REVIEW_REASON_AUTO_REJECTED);	//ReviewReason
		gpGuarantee = setApprovalStatus(gpGuarantee
						, Constants.GPGuarantee.APPROVAL_REJECTED				//ApproveStatus
						, null													//ApproveDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ApproveBy
						, Constants.GPGuarantee.APPROVAL_REASON_AUTO_REJECTED);	//ApproveReason
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 2)
	private GPGuarantee setStatusInvalidExtendDate(GPGuarantee gpGuarantee){
		System.out.println("----- setStatusInvalidCustomer -----");
		gpGuarantee = setTransactionStatus(gpGuarantee
				, Constants.EGuarantee.STATUS_UNSUCCESS			//TransactionStatus
				, Constants.StatusLGGP.EXTEND_EXPIRY_DATE		//StatusLG
				, Constants.MessageCode.INVALID_INFO_CUSTOMER	//MsgCode
				, Constants.StatusLGGP.INVALID_EXTEND_DATE);	//XmlOutput
		gpGuarantee = setReviewStatus(gpGuarantee
						, Constants.GPGuarantee.REVIEW_REJECTED					//ReviewStatus
						, new Date()											//ReviewDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
						, Constants.GPGuarantee.REVIEW_REASON_AUTO_REJECTED);	//ReviewReason
		gpGuarantee = setApprovalStatus(gpGuarantee
						, Constants.GPGuarantee.APPROVAL_REJECTED				//ApproveStatus
						, new Date()											//ApproveDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ApproveBy
						, Constants.GPGuarantee.APPROVAL_REASON_AUTO_REJECTED);	//ApproveReason
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setStatusAutoApproved(GPGuarantee gpGuarantee){
		System.out.println("----- setStatusAutoApproved -----");
		gpGuarantee = setReviewStatus(gpGuarantee
						, Constants.GPGuarantee.REVIEW_APPROVED					//ReviewStatus
						, new Date()											//ReviewDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
						, Constants.GPGuarantee.REVIEW_REASON_AUTO_APPROVED);	//ReviewReason
		gpGuarantee = setApprovalStatus(gpGuarantee
						, Constants.GPGuarantee.APPROVAL_APPROVED				//ApproveStatus
						, new Date()											//ApproveDtm
						, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ApproveBy
						, Constants.GPGuarantee.APPROVAL_REASON_AUTO_APPROVED);	//ApproveReason
		return gpGuarantee;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	private GPGuarantee processExtendExpiryDateFromApprovalForm(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processExtendExpiryDateFromApprovalForm -----");
		GPGuarantee extendGpGuarantee = input;
		ALSMQService alsMQService = null;
		GPGuaranteeService gpGuaranteeService = null;
		GPApprovalLogService gpApprovalLogService = null;
		try{
			alsMQService = new ALSMQService();
			gpGuaranteeService = new GPGuaranteeService();
			gpApprovalLogService = new GPApprovalLogService();
	        System.out.println("======== LGNo. [" + extendGpGuarantee.getLgNo() + "] is EXTEND_EXPIRY_DATE_ISSUE ==========");
	        System.out.println("======== (request from approval form) ==========");
	        int extendGpGuaranteeId = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
	        String approveStatus = request.getParameter("iApprovalStatus");
	        Date   approveDtm    = new Date();
	        String approveBy     = request.getParameter("iApprovalBy");
	        String approveReason = request.getParameter("iApprovalReason");
	        
	        //insert gp_guarantee_h
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
	        	System.out.println("======== insert gp_guarantee_h ==========");
	        	addSetupTxnHistoryFromLGNo(approveBy, extendGpGuarantee.getLgNo(), connectDB);
	        }
	        //update status
	        System.out.println("======== set flag for manual approval (" + approveStatus + ") ==========");
	        GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
	        GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(extendGpGuarantee.getLgNo());
	        extendGpGuarantee.setId(extendGpGuaranteeId);
	        extendGpGuarantee.setIssueTypeDesc(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_DESC);
	        extendGpGuarantee.setProcessDate(eTime.getProcessDate());
	        extendGpGuarantee = setApprovalStatus(extendGpGuarantee
							, approveStatus		//ApproveStatus
							, approveDtm		//ApproveDtm
							, approveBy			//ApproveBy
							, approveReason);	//ApproveReason
	        
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
	        	//connect MQ
        		System.out.println("=========== approve extend request (als online) - continue... ============");
        		extendGpGuarantee = alsMQService.sendMQMessage(connectDB, extendGpGuarantee, request, eTime);
        		if(extendGpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
        			System.out.println("=========== als success, send mail ============");
        			try{
        				sendExtendMailtoMQ(extendGpGuarantee, request);
        			}catch(Exception e){
        				System.out.println("Error send extend mail to customer: " + e.getMessage());
        			}
        		}else{
        			System.out.println("=========== als fail, do not send mail ============");
        		}
	        }else{
	        	System.out.println("=========== reject extend request ============");
	        	//set flag for rejected request
	        	GPGuaranteeService service = new GPGuaranteeService();
	        	GPGuarantee prevGp = service.getGPGuaranteeById(extendGpGuaranteeId);
	        	extendGpGuarantee = setTransactionStatus(extendGpGuarantee
						, Constants.EGuarantee.STATUS_UNSUCCESS	//TransactionStatus
						, Constants.StatusLGGP.EXTEND_EXPIRY_DATE			//StatusLG
						, prevGp.getMsgCode()					//MsgCode
						, prevGp.getXmlOutput());				//XmlOutput
	        }

        	System.out.println("======== update extend txn approval and transaction status ==========");
        	gpGuaranteeTable.updateExtendTxnApprovalStatus(extendGpGuarantee);
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)
	        		&& extendGpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)) {
		        System.out.println("======== update expire_date of the setup txn ==========");	
		        gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(extendGpGuarantee.getExtendDate(), extendGpGuarantee.getLgNo());
		        //updateSetupTxnExpireDateAndStatus(extendGpGuarantee);
	        }
	        System.out.println("======== insert gp_approval_log ==========");
	        extendGpGuarantee.setNewEndDate(extendGpGuarantee.getExtendDate());
	        extendGpGuarantee.setOldEndDate(setupTxn.getEndDate());
	        gpApprovalLogService.insert(extendGpGuarantee);
		}catch(EGuaranteeMQMessageException mqe){
			System.out.println("processExtendExpiryDateFromApprovalForm MQError: " + mqe.getMessage());
			throw mqe;
		}catch(Exception e){
			System.out.println("processExtendExpiryDateFromApprovalForm Error: " + e.getMessage());
			throw e;
		}
		return extendGpGuarantee;
	}
	//changed from
	/*
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processExtendExpiryDateFromApprovalForm(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processExtendExpiryDateFromApprovalForm -----");
		GPGuarantee extendGpGuarantee = input;
		try{
	        System.out.println("======== LGNo. [" + extendGpGuarantee.getLgNo() + "] is EXTEND_EXPIRY_DATE_ISSUE ==========");
	        System.out.println("======== (request from approval form) ==========");
	        int extendGpGuaranteeId = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
	        String approveStatus = request.getParameter("iApprovalStatus");
	        Date   approveDtm    = new Date();
	        String approveBy     = request.getParameter("iApprovalBy");
	        String approveReason = request.getParameter("iApprovalReason");
	        
	        System.out.println("======== set flag for manual approval (" + approveStatus + ") ==========");
	        //update status
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
	        	addSetupTxnHistoryFromLGNo(approveBy, extendGpGuarantee.getLgNo());
	        }
	        
	        GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
	        GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(extendGpGuarantee.getLgNo());
	        GPApprovalLogService gpApprovalLogService = new GPApprovalLogService();
	        extendGpGuarantee.setId(extendGpGuaranteeId);
	        extendGpGuarantee.setProcessDate(eTime.getProcessDate());
	        extendGpGuarantee.setIssueTypeDesc(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_DESC);
	        extendGpGuarantee = setApprovalStatus(extendGpGuarantee
							, approveStatus		//ApproveStatus
							, approveDtm		//ApproveDtm
							, approveBy			//ApproveBy
							, approveReason);	//ApproveReason
	        String status = "";
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
	        	status = Constants.EGuarantee.STATUS_SUCCESS;
	        }else{
	        	status = Constants.EGuarantee.STATUS_UNSUCCESS;
	        }
        	extendGpGuarantee = setTransactionStatus(extendGpGuarantee
								, status									//TransactionStatus
								, Constants.StatusLGGP.EXTEND_EXPIRY_DATE	//StatusLG
								, null										//MsgCode
								, null);									//XmlOutput
        	System.out.println("======== update approval and transaction status ==========");
        	gpGuaranteeTable.updateExtendTxnApprovalStatus(extendGpGuarantee);
	        if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
		        System.out.println("======== insert gp_guarantee_h ==========");
		        //gpGuaranteeTable.addSetupTxnHistoryFromLGNo(approveBy, extendGpGuarantee.getLgNo());
		        //addSetupTxnHistoryFromLGNo(approveBy, extendGpGuarantee.getLgNo());
		        System.out.println("======== update expire_date of the setup txn ==========");	
		        gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(extendGpGuarantee.getExtendDate(), extendGpGuarantee.getLgNo());
	        }
	        //UR58120031 new expire date add by Tana L. @09022016
	        extendGpGuarantee.setNewEndDate(extendGpGuarantee.getExtendDate());
	        extendGpGuarantee.setOldEndDate(setupTxn.getEndDate());
	        gpApprovalLogService.insert(extendGpGuarantee);
		}catch(Exception e){
			System.out.println("processExtendExpiryDateFromApprovalForm Error: " + e.getMessage());
			throw e;
		}
		return extendGpGuarantee;
	}
	*/
	
	private void addSetupTxnHistoryFromLGNo(String approveBy, String lgNo, ConnectDB connectDB) throws Exception{
		//ConnectDB connectDB = null;
		try{
			//connectDB = new ConnectDB();
			//System.out.println("GPGuaranteeService<addSetupTxnHistoryFromLGNo> new connection."+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			gpGuaranteeTable.addSetupTxnHistoryFromLGNo(approveBy, lgNo);
		}catch(Exception e){
			System.out.println("addSetupTxnHistoryFromLGNo Error: " + e.getMessage());
			throw e;
		}finally{
			//if(connectDB != null){
				//System.out.println("GPGuaranteeService<addSetupTxnHistoryFromLGNo> close connection."+connectDB.hashCode());
				//connectDB.close();
			//}
		}
	}
	
	private void updateSetupTxnExpireDateAndStatus(GPGuarantee gpGuarantee) throws Exception{
		ConnectDB connectDB = null;
		try{
			connectDB = new ConnectDB();
			System.out.println("GPGuaranteeService<updateSetupTxnExpireDateAndStatus> new connection."+connectDB.hashCode());
        	GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
        	gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(gpGuarantee.getExtendDate(), gpGuarantee.getLgNo());
		}catch(Exception e){
			System.out.println("updateSetupTxnExpireDateAndStatus Error: " + e.getMessage());
			throw e;
		}finally{
			if(connectDB != null){
				System.out.println("GPGuaranteeService<updateSetupTxnExpireDateAndStatus> close connection."+connectDB.hashCode());
				connectDB.close();
			}
		}
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee processSetupFromApprovalForm(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processSetupFromApprovalForm -----");
		GPGuarantee gpGuarantee = input;
		ALSMQService alsMQService = null;
		GPGuaranteeService gpGuaranteeService = null;
		GPApprovalLogService gpApprovalLogService = null;
		try{
			alsMQService = new ALSMQService();
			gpGuaranteeService = new GPGuaranteeService();
			gpApprovalLogService = new GPApprovalLogService();
	        System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is SETUP_ISSUE ==========");
	        System.out.println("======== (request from approval form) ==========");
			String account = request.getParameter("iAccount");
            int gpGuaranteeId = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
			String approveStatus = request.getParameter("iApprovalStatus");
			Date   approveDtm    = new Date();
			String approveBy     = request.getParameter("iApprovalBy");
			String approveReason = request.getParameter("iApprovalReason");
			
			System.out.println("======== set flag for manual approval (" + approveStatus + ") ==========");
			gpGuarantee.setAccountNo(account);
			gpGuarantee.setId(gpGuaranteeId);
			gpGuarantee.setIssueTypeDesc(Constants.GPGuarantee.SETUP_ISSUE_DESC);
			gpGuarantee.setProcessDate(eTime.getProcessDate());
			gpGuarantee = setApprovalStatus(gpGuarantee
					, approveStatus		//ApproveStatus
					, approveDtm		//ApproveDtm
					, approveBy			//ApproveBy
					, approveReason);	//ApproveReason
			
			//UR58120031 Phase 2 Send SMS after Approval Request add by Tana L. @21042016
			if(!"".equals(gpGuarantee.getAccountNo())){
				System.out.println("======== Account selected ==========");
				if(!alreadySendSMS(gpGuarantee.getTxRef())) {
	    			boolean isOffline = eTime.isTimeOffHostALS();
	    			AccountALS accountAls = getAccount(connectDB, isOffline, gpGuarantee, request);
	    			//gpGuarantee.setAccountNo(accountAls.getAccountNo());
	    			if(accountAls != null){
	    				System.out.println("======== Send SMS (SETUP_ISSUE) ==========");
		    			gpGuarantee.setOcCode(accountAls.getOcCode());
		    			sendSMStoMQ(gpGuarantee , request);
	    			}else{
	    				System.out.println("======== Do not Send SMS (SETUP_ISSUE - not found account_als) ==========");
	    			}
	    		}
			}else{
				System.out.println("======== No account selected ==========");
			}
			
			if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
				//connect MQ
				System.out.println("=========== approve request (als online) - check dup completed txn... ============");
				boolean isDupWithCompleteReq = false;
				isDupWithCompleteReq = isDupGPGuaranteeCompletedTxn(gpGuarantee, connectDB);
				if(isDupWithCompleteReq){
					System.out.println("======== DUP (with gp_guarantee completed txn) ==========");
					//set massage code error
					gpGuarantee = setTransactionStatus(gpGuarantee
							, Constants.EGuarantee.STATUS_UNSUCCESS		//TransactionStatus
							, Constants.StatusLGGP.REJECT				//StatusLG
							, Constants.MessageCode.DUP_TRANSACTION		//MsgCode
							, Constants.StatusLGGP.DUPLICATION);		//XmlOutput
				}else{
					System.out.println("======== NO-DUP (with gp_guarantee completed txn) ==========");
					System.out.println("=========== approve request (als online) - continue... ============");
					gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
					if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){ // Transaction success
						System.out.println("=========== als success, send mail ============");
						sendMailtoMQ(gpGuarantee, request); // call function to send mail to MQ
					}else{
						System.out.println("=========== als fail, do not send mail ============");
					}
				}
			}else {
				System.out.println("=========== reject request ============");
				//remove project_tax
				ProjectTaxTable projectTaxTable = new ProjectTaxTable();
				projectTaxTable.removeProjectTaxByKeys(gpGuarantee);
				//set flag for rejected request
				GPGuaranteeService service = new GPGuaranteeService();
				GPGuarantee prevGp = service.getGPGuaranteeById(gpGuaranteeId);
				gpGuarantee = setTransactionStatus(gpGuarantee
								, Constants.EGuarantee.STATUS_UNSUCCESS	//TransactionStatus
								, Constants.StatusLGGP.REJECT			//StatusLG
								, prevGp.getMsgCode()					//MsgCode
								, prevGp.getXmlOutput());				//XmlOutput
			}
			System.out.println("=========== insert gp_approval_log ============");
			gpGuaranteeService.updateApprovalProcess(gpGuarantee);
			gpApprovalLogService.insert(gpGuarantee);
		}catch(EGuaranteeMQMessageException mqe){
			System.out.println("processSetupFromApprovalForm MQError: " + mqe.getMessage());
			throw mqe;
		}catch(Exception e){
			System.out.println("processSetupFromApprovalForm Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	
	private GPGuarantee processCancelFromApprovalForm(LogEBXML logEBXML, ETime eTime, HttpServletRequest request, GPGuarantee input, ConnectDB connectDB) throws Exception, EGuaranteeMQMessageException {
		System.out.println("----- processCancelFromApprovalForm -----");
		GPGuarantee gpGuarantee = input;
		ALSMQService alsMQService = null;
		GPGuaranteeService gpGuaranteeService = null;
		GPApprovalLogService gpApprovalLogService = null;
		try{
			alsMQService = new ALSMQService();
			gpGuaranteeService = new GPGuaranteeService();
			gpApprovalLogService = new GPApprovalLogService();
	        System.out.println("======== account [" + gpGuarantee.getAccountNo() + "] is CANCEL_ISSUE ==========");
	        System.out.println("======== (request from approval form) ==========");
			String account = request.getParameter("iAccount");
            int gpGuaranteeId = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
			String approveStatus = request.getParameter("iApprovalStatus");
			Date   approveDtm    = new Date();
			String approveBy     = request.getParameter("iApprovalBy");
			String approveReason = request.getParameter("iApprovalReason");
			
			System.out.println("======== set flag for manual approval (" + approveStatus + ") ==========");
			gpGuarantee.setAccountNo(account);
			gpGuarantee.setId(gpGuaranteeId);
			gpGuarantee.setIssueTypeDesc(Constants.GPGuarantee.SETUP_ISSUE_DESC);
			gpGuarantee.setProcessDate(eTime.getProcessDate());
			gpGuarantee = setApprovalStatus(gpGuarantee
					, approveStatus		//ApproveStatus
					, approveDtm		//ApproveDtm
					, approveBy			//ApproveBy
					, approveReason);	//ApproveReason
			
			if (Constants.GPGuarantee.APPROVAL_APPROVED.equals(approveStatus)) {
				//connect MQ
				System.out.println("=========== approve request (als online) - continue... ============");
				gpGuarantee = alsMQService.sendMQMessage(connectDB, gpGuarantee, request, eTime);
				connectDB.commit();
			}else {
				System.out.println("=========== reject request (No reject request for this case) ============");
			}
			System.out.println("=========== insert gp_approval_log ============");
			gpGuaranteeService.updateApprovalProcess(gpGuarantee);
			gpApprovalLogService.insert(gpGuarantee);
		}catch(EGuaranteeMQMessageException mqe){
			System.out.println("processCancelFromApprovalForm MQError: " + mqe.getMessage());
			throw mqe;
		}catch(Exception e){
			System.out.println("processCancelFromApprovalForm Error: " + e.getMessage());
			throw e;
		}
		return gpGuarantee;
	}
	
	public void sendMailtoMQ(GPGuarantee gpGuarantee,HttpServletRequest request) throws EGuaranteeMQMessageException{
		MQMessageService mq = null;
		try {
			System.out.println("Send e-mail to MQ ... ");
			String requestXML = genXMLMail(gpGuarantee, request);			
			mq = new MQMessageService();
			mq.sendMessageOnly(requestXML,Constants.ConfigFile.MQ_SMS_RQ_NAME, Constants.SENDER_ID_EMAIL,Constants.GPGuarantee.SETUP_ISSUE_DESC,gpGuarantee.getTxRef());
		}catch(EGuaranteeMQMessageException e) {
			System.out.println("Error send e-mail to MQ ... " + e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public void sendExtendMailtoMQ(GPGuarantee gpGuarantee,HttpServletRequest request) throws EGuaranteeMQMessageException{
		MQMessageService mq = null;
		try {
			System.out.println("Send extend e-mail to MQ ... ");
			String requestXML = genExtendXMLMail(gpGuarantee, request);			
			mq = new MQMessageService();
			mq.sendMessageOnly(requestXML,Constants.ConfigFile.MQ_SMS_RQ_NAME, Constants.SENDER_ID_EMAIL,Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_DESC,gpGuarantee.getTxRef());
		}catch(EGuaranteeMQMessageException e) {
			System.out.println("Error send extend e-mail to MQ ... " + e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String genXMLMail(GPGuarantee gpGuarantee , HttpServletRequest request){
		String xmlRes = "";
		String tempFile = null;
		try{
			//get template XML by issue_type
			if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){ 
				tempFile = Constants.TEMPLETE_XML_CONFIRM_MAIL;
			}else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){ 
				tempFile = Constants.TEMPLETE_XML_CANCEL_MAIL;
			}else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				tempFile = Constants.TEMPLETE_XML_CLAIM_MAIL;
			}
			TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			//SET Templete xml for mail
			template.setContext("ProductCode", Constants.FIELD_EGUARANTEE);
			template.setContext("AlertType", Constants.FIELD_ALERT_TYPE_LG);
			//template.setContext("SubAlert", Constants.FIELD_SUB_ALERT_NOTIFY);
			template.setContext("AccNo", StringUtil.nullToBlank(gpGuarantee.getAccountNo()));
			template.setContext("ProjName", StringUtil.nullToBlank(gpGuarantee.getProjName()));
			template.setContext("Beneficiary", StringUtil.nullToBlank(gpGuarantee.getProjOwnName()));	
			template.setContext("GuaranteeNo", StringUtil.nullToBlank(gpGuarantee.getLgNo()));	
			template.setContext("Amount", new DecimalFormat("#,##0.00").format(gpGuarantee.getGuaranteeAmt()));
					
			if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				
				template.setContext("SubAlert", Constants.FIELD_SUB_ALERT_CONFIRM);			
				
				//update by Apichart H.@20150511
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = sdf.parse(gpGuarantee.getStartDate());
				template.setContext("PeriodFrom", sdf.format(startDate));
				if(StringUtil.isBlank(gpGuarantee.getEndDate())){
					template.setContext("PeriodTo", "9999-12-31");
				}else{
					Date endDate = sdf.parse(gpGuarantee.getEndDate());
					template.setContext("PeriodTo", sdf.format(endDate));
				}
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
				template.setContext("SubAlert", Constants.FIELD_SUB_ALERT_CANCEL);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				template.setContext("SubAlert", Constants.FIELD_SUB_ALERT_CLAIM); 
				template.setContext("AmountClaim", new DecimalFormat("#,##0.00").format(gpGuarantee.getGuaranteeAmt())); //puy 
			}
			xmlRes = template.getXML();
		}catch(ParseException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return xmlRes;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public String genExtendXMLMail(GPGuarantee gpGuarantee , HttpServletRequest request){
		String xmlRes = "";
		String tempFile = null;
		try{
			//get template XML by issue_type
			tempFile = Constants.TEMPLETE_XML_EXTEND_EXPIRY_DATE_MAIL;
			TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			//SET Templete xml for mail
			template.setContext("ProductCode", Constants.FIELD_EGUARANTEE);
			template.setContext("AlertType", Constants.FIELD_ALERT_TYPE_LG);
			template.setContext("SubAlert", Constants.FIELD_SUB_ALERT_EXTEND_EXPIRY_DATE);
			template.setContext("AccNo", StringUtil.nullToBlank(gpGuarantee.getAccountNo()));
			template.setContext("ProjName", StringUtil.nullToBlank(gpGuarantee.getProjName()));
			template.setContext("Beneficiary", StringUtil.nullToBlank(gpGuarantee.getProjOwnName()));	
			template.setContext("GuaranteeNo", StringUtil.nullToBlank(gpGuarantee.getLgNo()));	
			template.setContext("Amount", new DecimalFormat("#,##0.00").format(gpGuarantee.getGuaranteeAmt()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sdf.parse(gpGuarantee.getStartDate());
			template.setContext("PeriodFrom", sdf.format(startDate));
			if(StringUtil.isBlank(gpGuarantee.getExtendDate())){
				template.setContext("PeriodTo", "9999-12-31");
			}else{
				Date endDate = sdf.parse(gpGuarantee.getExtendDate());
				template.setContext("PeriodTo", sdf.format(endDate));
			}
			
			xmlRes = template.getXML();
		}catch(Exception e){
			e.printStackTrace();
		}
		return xmlRes;
	}
	
	
	public void sendSMStoMQ(GPGuarantee gpGuarantee , HttpServletRequest request) {
		//Connect MQ
		System.out.println("Send sms to MQ ... ");
		MQMessageService mq = null;
		String requestXML = genXMLSMS(gpGuarantee,request);
		mq = new MQMessageService();
		try{
			mq.sendMessageOnly(requestXML,Constants.ConfigFile.MQ_SMS_RQ_NAME, Constants.SENDER_ID_SMS,Constants.GPGuarantee.SETUP_ISSUE_DESC,gpGuarantee.getTxRef());
        } catch (EGuaranteeMQMessageException e) {
			System.out.println("Error send sms to MQ ... " + e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public void sendExtendSMStoMQ(GPGuarantee gpGuarantee , HttpServletRequest request) {
		//Connect MQ
		System.out.println("Send extend sms to MQ ... ");
		MQMessageService mq = null;
		String requestXML = genXMLExtendSMS(gpGuarantee,request);
		mq = new MQMessageService();
		try{
			mq.sendMessageOnly(requestXML,Constants.ConfigFile.MQ_SMS_RQ_NAME
								, Constants.SENDER_ID_SMS
								, Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_DESC
								, gpGuarantee.getTxRef());
        } catch (EGuaranteeMQMessageException e) {
			System.out.println("Error send extend sms to MQ ... " + e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//<start>
	private String getBuDesc(GPGuarantee gpGuarantee){
		ConnectDB con = new ConnectDB();
        System.out.println("GPGuaranteeService<getBuDesc> new connection : "+con.hashCode());
		StringBuilder notifyTemplate = new StringBuilder();
		try{
			BUCodeTable buCodeTable = new  BUCodeTable(con);
			BUCode buCode = new BUCode();
			BUCode bu = null;
			if(gpGuarantee.getOcCode()==null||gpGuarantee.getOcCode().equals("")){
				buCode.setBuCode("");
				buCode.setBuDesc("");
			}else{
				buCode.setBuCode(String.valueOf(gpGuarantee.getOcCode().charAt(0)));
				bu =  new BUCode();
				bu = buCodeTable.findByBuCode(buCode);
			}
			
			notifyTemplate.append(Constants.FIELD_SUB_ALERT_NOTIFY);
			notifyTemplate.append("_");
			if(bu != null){
				notifyTemplate.append(bu.getBuDesc());
			}else{
				notifyTemplate.append(" ");
			}
		}finally{
			if(con != null){
            	System.out.println("GPGuaranteeService<getBuDesc> close connection : "+con.hashCode());
				con.close();
			}
		}
		return notifyTemplate.toString();
	}
	//<end> add by Apichart H.@20150513
	
	private String genXMLSMS(GPGuarantee gpGuarantee , HttpServletRequest request){
		String xmlRes = "";
		//GET Template xml
		String tempFile = Constants.TEMPLETE_XML_SMS;
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		//GET BU_DESC
		String buDesc = getBuDesc(gpGuarantee); //add by Apichart H.@20150513
		//SET Templete xml for SMS
		template.setContext("ProductCode", Constants.FIELD_EGUARANTEE);
		template.setContext("AlertType", Constants.FIELD_ALERT_TYPE_LG);
		template.setContext("SubAlert", buDesc); //update by Apichart H.@20150513
		template.setContext("AccNo", StringUtil.nullToBlank(gpGuarantee.getAccountNo()));
		template.setContext("ProjNo", StringUtil.nullToBlank(gpGuarantee.getProjNo()));
		template.setContext("Amount", new DecimalFormat("#,##0.00").format(gpGuarantee.getGuaranteeAmt()));
		
		xmlRes = template.getXML();
		
		return xmlRes;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	private String genXMLExtendSMS(GPGuarantee gpGuarantee , HttpServletRequest request){
		String xmlRes = "";
		try{
			//GET Template xml
			String tempFile = Constants.TEMPLETE_XML_EXTEND_EXPIRY_DATE_SMS;
			TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
			//GET BU_DESC
			String buDesc = getBuDesc(gpGuarantee) + "_EXTEND";
			//SET Templete xml for SMS
			template.setContext("ProductCode", Constants.FIELD_EGUARANTEE);
			template.setContext("AlertType", Constants.FIELD_ALERT_TYPE_LG);
			template.setContext("SubAlert", buDesc);
			template.setContext("AccNo", StringUtil.nullToBlank(gpGuarantee.getAccountNo()));
			template.setContext("ProjNo", StringUtil.nullToBlank(gpGuarantee.getProjNo()));
			template.setContext("Amount", new DecimalFormat("#,##0.00").format(gpGuarantee.getGuaranteeAmt()));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtil.isBlank(gpGuarantee.getEndDate())){
				template.setContext("OldExpiryDate", "9999-12-31");
			}else{
				Date endDate = sdf.parse(gpGuarantee.getEndDate());
				template.setContext("OldExpiryDate", sdf.format(endDate));
			}
			if(StringUtil.isBlank(gpGuarantee.getExtendDate())){
				template.setContext("NewExpiryDate", "9999-12-31");
			}else{
				Date endDate = sdf.parse(gpGuarantee.getExtendDate());
				template.setContext("NewExpiryDate", sdf.format(endDate));
			}
			template.setContext("LgNo", StringUtil.nullToBlank(gpGuarantee.getLgNo()));
			xmlRes = template.getXML();
		}catch(ParseException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return xmlRes;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	private GPGuarantee setGPGuaranteeForResponse(GPGuarantee gpGuarantee, ConnectDB connectDB) throws Exception{
		BankInfoTable bankInfoTable = new BankInfoTable(connectDB);
		BankInfo bankInfo = bankInfoTable.findBankInfo(new BankInfo(1));
		gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
		gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)
				|| gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
			//get from db
			gpGuarantee.setBankCode(bankInfo.getBankCode());
			gpGuarantee.setBranchCode(bankInfo.getBranchCode());
			gpGuarantee.setBranchName(bankInfo.getBranchName());
			gpGuarantee.setBankAddr(bankInfo.getBankAddr());
			//set blank
			gpGuarantee.setAuthSigName(Constants.BLANK);
			gpGuarantee.setPosition(Constants.BLANK);
			gpGuarantee.setWitnessName1(Constants.BLANK);
			gpGuarantee.setWitnessName2(Constants.BLANK);
		}
		return gpGuarantee;
	}
	//changed from
	/*
	private GPGuarantee setGPGuaranteeForResponse(GPGuarantee gpGuarantee, ConnectDB connectDB) throws Exception{
		
		BankInfoTable bankInfoTable = new BankInfoTable(connectDB);
		BankInfo bankInfo = bankInfoTable.findBankInfo(new BankInfo(1));
		
		gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
		gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));
		
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			//get from db
			gpGuarantee.setBankCode(bankInfo.getBankCode());
			gpGuarantee.setBranchCode(bankInfo.getBranchCode());
			gpGuarantee.setBranchName(bankInfo.getBranchName());
			gpGuarantee.setBankAddr(bankInfo.getBankAddr());
			//set blank
			gpGuarantee.setAuthSigName(Constants.BLANK);
			gpGuarantee.setPosition(Constants.BLANK);
			gpGuarantee.setWitnessName1(Constants.BLANK);
			gpGuarantee.setWitnessName2(Constants.BLANK);
		}
		
		return gpGuarantee;
	}
	*/
	
	public GPGuarantee setGPGuaranteeForResponse(GPGuarantee gpGuarantee) throws Exception{
		
		ConnectDB connectDB = new ConnectDB();
        System.out.println("GPGuaranteeService<setGPGuaranteeForResponse> new connection : "+connectDB.hashCode());
		try{
			BankInfoTable bankInfoTable = new BankInfoTable(connectDB);
			BankInfo bankInfo = bankInfoTable.findBankInfo(new BankInfo(1));
			
			gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
			gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));
			
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){

				//get from db
				gpGuarantee.setBankCode(bankInfo.getBankCode());
				gpGuarantee.setBranchCode(bankInfo.getBranchCode());
				gpGuarantee.setBranchName(bankInfo.getBranchName());
				gpGuarantee.setBankAddr(bankInfo.getBankAddr());
				
				//set blank
				gpGuarantee.setAuthSigName(Constants.BLANK);
				gpGuarantee.setPosition(Constants.BLANK);
				gpGuarantee.setWitnessName1(Constants.BLANK);
				gpGuarantee.setWitnessName2(Constants.BLANK);
		
			}	
		}catch(Exception e){
			
		}finally{
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<setGPGuaranteeForResponse> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
		}
		
		return gpGuarantee;
	}
	
	public boolean isDupGPGuarantee(GPGuarantee gpGuarantee) throws Exception{
		boolean isDup = false;
		//check setup-issue case only
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			ProjectTaxTable projectTaxTable = new ProjectTaxTable();
			 isDup = projectTaxTable.isDupProjectTax(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getLgNo(), gpGuarantee.getGuaranteeAmt(),gpGuarantee.getEndDate()));
		}
		return isDup;
	}
	
	public boolean isTaxIdHasMultipleAccount(ConnectDB connectDB, GPGuarantee gpGuarantee){
		AccountALSTable accountAlsTable = new AccountALSTable(connectDB);
		return accountAlsTable.isTaxIdHasMultipleAccount(gpGuarantee.getVendorTaxId());
	}
	
	public AccountALS getAccount(ConnectDB connectDB, boolean isOffline, GPGuarantee gpGuarantee,HttpServletRequest request) throws Exception, EGuaranteeMQMessageException{
		
		ALSMQService alsMQService = new ALSMQService();
		AccountALS accountALS = null;
		
		if(Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())){
			//for setup request
			//get account from account_als
			System.out.println("----- [get account for setup] -----");
			AccountALSTable accountAlsTable = new AccountALSTable(connectDB);
			accountALS = accountAlsTable.findByTaxId(gpGuarantee.getVendorTaxId(), Constants.AccountPurpose.ACCOUNT_EGP);
			//if not found --> get from ALS SYSTEM (via MQ)
			if (accountALS == null){
				if(isOffline){// als offline
					System.out.println("=========== als offline [account is null] ============");
				}else{
					System.out.println("=========== als online [get account from ALS] ============");
					accountALS = alsMQService.getAcctALS(gpGuarantee,request);
					if(accountALS != null){
						Locale locale = new Locale("en", "EN");
						Calendar c = Calendar.getInstance(locale);
                        accountALS.setPurpose("09");
						accountALS.setActiveFlag("Y");
						accountALS.setCreateDtm(c.getTime());
                        accountALS.setCreateBy("ONLINE");
                        accountAlsTable.add(accountALS);    
					}
				}
			}else {
				System.out.println("=========== found account in account_als [account is not null] ============");
			}
			
		}else{
			System.out.println("----- [get account for cancel/claim/extend_expiry_date from LGNO '" + gpGuarantee.getLgNo() + "'] -----");
			//for cancel / claim / extend_expiry_date request
			//get account from the setup transaction (gp_guarantee)
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			accountALS = gpGuaranteeTable.getSetupAccountFromLgNo(gpGuarantee.getLgNo());
		}
        delay(delayTime);
		return accountALS;
	}
	
	public String getStatusDesc(ConnectDB connectDB, GPGuarantee gpGuarantee){
		String statusDesc = "";
		return statusDesc;
	}
	
	public GPGuarantee ebxmlToObject(String xmlString) throws Exception{
		return ebxmlToObject(xmlString, null);
	}
	
	public GPGuarantee ebxmlToObject(String xmlString, ETime eTime) throws Exception {
		
		GPGuarantee gpGuarantee = null;
		ConfigXMLCustomsGP configXMLCustomsGP = new ConfigXMLCustomsGP();
		
		try{
			XPathReader reader = new XPathReader(xmlString);
			String xml = (String)reader.read(configXMLCustomsGP.getSetupIssueMessageRoot(), XPathConstants.STRING);
			//System.out.println("setup issue xml >> " + xml);
			if(xml !=null && xml.length() > 0){
				System.out.println("ยื่นหลักประกัน");
				gpGuarantee = getSetupIssue(reader, configXMLCustomsGP);
			}else{
				
				xml = (String)reader.read(configXMLCustomsGP.getCancelIssueMessageRoot(), XPathConstants.STRING);
				if(xml !=null && xml.length() > 0){
					System.out.println("คืนหลักประกัน");
					gpGuarantee = getCancelIssue(reader, configXMLCustomsGP);
				//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
				//changed from
				//}else{
				//	System.out.println("ยึดหลักประกัน");
				//	gpGuarantee = getClaimIssue(reader, configXMLCustomsGP);
				//}
				//changed to
				}else{
					xml = (String)reader.read(configXMLCustomsGP.getClaimIssueMessageRoot(), XPathConstants.STRING);
					if(xml !=null && xml.length() > 0){
						System.out.println("ยึดหลักประกัน");
						gpGuarantee = getClaimIssue(reader, configXMLCustomsGP);
					}else{
						System.out.println("Extend Expiry Date");
						gpGuarantee = getExtendExpiryDateIssue(reader, configXMLCustomsGP);
					}
				}
			}
			
			if(eTime != null){
				boolean isTimeOffHostALS = eTime.isTimeOffHostALS();
				if(isTimeOffHostALS == true){
					gpGuarantee.setAlsOnline(Constants.StatusALSOnline.NO);
					gpGuarantee.setProcessDate(eTime.getProcessDate());
	        	}else{
	        		gpGuarantee.setAlsOnline(Constants.StatusALSOnline.YES);
	        		gpGuarantee.setProcessDate(eTime.getProcessDate());
	        	}
				gpGuarantee.setIsBatch(Boolean.FALSE);
			}else{
				gpGuarantee.setIsBatch(Boolean.TRUE);
			}
			gpGuarantee.setAppvAmt(new BigDecimal(0));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error xml to object ... " + e.getMessage());
			throw  new Exception(e.getMessage());
		}
		return gpGuarantee;
	}
	
	private GPGuarantee getMainIssue(String messageRoot, XPathReader reader, ConfigXMLCustomsGP configXMLCustomsGP) throws Exception{
		GPGuarantee issue = new GPGuarantee();
		issue.setTxRef((String)reader.read(messageRoot.concat(configXMLCustomsGP.getTransRef()) , XPathConstants.STRING));
		issue.setDtm((String)reader.read(messageRoot.concat(configXMLCustomsGP.getTransDate()) , XPathConstants.STRING));
		issue.setProjNo((String)reader.read(messageRoot.concat(configXMLCustomsGP.getProjectNo()) , XPathConstants.STRING));
		issue.setDeptCode((String)reader.read(messageRoot.concat(configXMLCustomsGP.getDeptCode()) , XPathConstants.STRING));
		issue.setVendorTaxId((String)reader.read(messageRoot.concat(configXMLCustomsGP.getVendorTaxId()) , XPathConstants.STRING));
		issue.setVendorName((String)reader.read(messageRoot.concat(configXMLCustomsGP.getVendorName()) , XPathConstants.STRING));
		issue.setCompId((String)reader.read(messageRoot.concat(configXMLCustomsGP.getCompId()) , XPathConstants.STRING));
		issue.setUserId((String)reader.read(messageRoot.concat(configXMLCustomsGP.getUserId()) , XPathConstants.STRING));

		Integer seqNo = new Integer((String)reader.read(messageRoot.concat(configXMLCustomsGP.getSeqNo()) , XPathConstants.STRING));
		issue.setSeqNo(seqNo.intValue());
		
		issue.setConsiderDesc(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getConsiderDesc()) , XPathConstants.STRING)));	
		BigDecimal considerMoney = new BigDecimal(StringUtil.nullToZero((String)reader.read(messageRoot.concat(configXMLCustomsGP.getConsiderMoney()), XPathConstants.STRING)));
		issue.setConsiderMoney(considerMoney);
		
		
		BigDecimal guaranteeAmt = new BigDecimal((String)reader.read(messageRoot.concat(configXMLCustomsGP.getGuaranteeAmt()), XPathConstants.STRING));
		issue.setGuaranteeAmt(guaranteeAmt);

		issue.setContractNo((String)reader.read(messageRoot.concat(configXMLCustomsGP.getContractNo()) , XPathConstants.STRING));
		issue.setContractDate((String)reader.read(messageRoot.concat(configXMLCustomsGP.getContractDate()) , XPathConstants.STRING));
	
		BigDecimal guaranteePrice = new BigDecimal((String)reader.read(messageRoot.concat(configXMLCustomsGP.getGuaranteePrice()), XPathConstants.STRING));
		issue.setGuaranteePrice(guaranteePrice);
				
		BigDecimal guaranteePercent = new BigDecimal((String)reader.read(messageRoot.concat(configXMLCustomsGP.getGuaranteePercent()), XPathConstants.STRING));
		issue.setGuaranteePercent(guaranteePercent);

		BigDecimal advanceGuaranteePrice = new BigDecimal(StringUtil.nullToZero((String)reader.read(messageRoot.concat(configXMLCustomsGP.getAdvanceGuaranteePrice()), XPathConstants.STRING)));
		issue.setAdvanceGuaranteePrice(advanceGuaranteePrice);
		
		BigDecimal advancePayment = new BigDecimal(StringUtil.nullToZero((String)reader.read(messageRoot.concat(configXMLCustomsGP.getAdvancePayment()), XPathConstants.STRING)));
		issue.setAdvancePayment(advancePayment);
		
		BigDecimal worksGuaranteePrice = new BigDecimal(StringUtil.nullToZero((String)reader.read(messageRoot.concat(configXMLCustomsGP.getWorksGuaranteePrice()), XPathConstants.STRING)));
		issue.setWorksGuaranteePrice(worksGuaranteePrice);
		
		BigDecimal worksGuaranteePercent = new BigDecimal(StringUtil.nullToZero((String)reader.read(messageRoot.concat(configXMLCustomsGP.getWorksGuaranteePercent()), XPathConstants.STRING)));
		issue.setWorksGuaranteePercent(worksGuaranteePercent);
		
		issue.setCollectionPhase(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getCollectionPhase()) , XPathConstants.STRING)));
		issue.setEndDate(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getEndDate()) , XPathConstants.STRING)));
		issue.setStartDate(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getStartDate()) , XPathConstants.STRING)));
		issue.setBondType((String)reader.read(messageRoot.concat(configXMLCustomsGP.getBondType()) , XPathConstants.STRING));
		issue.setProjName((String)reader.read(messageRoot.concat(configXMLCustomsGP.getProjectName()) , XPathConstants.STRING));
		
		BigDecimal projAmt = new BigDecimal((String)reader.read(messageRoot.concat(configXMLCustomsGP.getProjectAmt()), XPathConstants.STRING));
		issue.setProjAmt(projAmt);
		
		issue.setProjOwnName((String)reader.read(messageRoot.concat(configXMLCustomsGP.getProjectOwnName()) , XPathConstants.STRING));
		issue.setCostCenter(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getCostCenter()) , XPathConstants.STRING)));
		issue.setCostCenterName(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getCostCenterName()) , XPathConstants.STRING)));
		issue.setExpireDate((String)reader.read(messageRoot.concat(configXMLCustomsGP.getExpireDate()) , XPathConstants.STRING));
		return issue;
	}
	
	private GPGuarantee getSetupIssue(XPathReader reader, ConfigXMLCustomsGP configXMLCustomsGP) throws Exception {
		GPGuarantee setupIssue = getMainIssue(configXMLCustomsGP.getSetupIssueMessageRoot(), reader, configXMLCustomsGP);
		setupIssue.setDocumentNo((String)reader.read(configXMLCustomsGP.getSetupIssueMessageRoot().concat(configXMLCustomsGP.getDocumentNo()) , XPathConstants.STRING));
		setupIssue.setDocumentDate((String)reader.read(configXMLCustomsGP.getSetupIssueMessageRoot().concat(configXMLCustomsGP.getDocumentDate()) , XPathConstants.STRING));
		setupIssue.setIssueType(Constants.GPGuarantee.SETUP_ISSUE);
		setupIssue.setIssueTypeDesc(Constants.GPGuarantee.SETUP_ISSUE_DESC);
		//System.out.println("setupIssue >> " + setupIssue.toString());
		return setupIssue;
	}
	
	private GPGuarantee getCancelIssue(XPathReader reader, ConfigXMLCustomsGP configXMLCustomsGP) throws Exception {
		GPGuarantee cancelIssue = getMainIssue(configXMLCustomsGP.getCancelIssueMessageRoot(), reader, configXMLCustomsGP);
		BigDecimal amtReq = new BigDecimal((String)reader.read(configXMLCustomsGP.getCancelIssueMessageRoot().concat(configXMLCustomsGP.getAmtReq()), XPathConstants.STRING));
		cancelIssue.setAmtReq(amtReq);
		cancelIssue.setLgNo((String)reader.read(configXMLCustomsGP.getCancelIssueMessageRoot().concat(configXMLCustomsGP.getLgNo()) , XPathConstants.STRING));
		cancelIssue.setIssueType(Constants.GPGuarantee.CANCEL_ISSUE);
		cancelIssue.setIssueTypeDesc(Constants.GPGuarantee.CANCEL_ISSUE_DESC);
		//System.out.println("cancelIssue >> " + cancelIssue);
		return cancelIssue;
	}
	
	private GPGuarantee getClaimIssue(XPathReader reader, ConfigXMLCustomsGP configXMLCustomsGP) throws Exception {
		GPGuarantee claimIssue = getMainIssue(configXMLCustomsGP.getClaimIssueMessageRoot(), reader, configXMLCustomsGP);
		BigDecimal amtReq = new BigDecimal((String)reader.read(configXMLCustomsGP.getClaimIssueMessageRoot().concat(configXMLCustomsGP.getAmtReq()), XPathConstants.STRING));
		claimIssue.setAmtReq(amtReq);
		claimIssue.setLgNo((String)reader.read(configXMLCustomsGP.getClaimIssueMessageRoot().concat(configXMLCustomsGP.getLgNo()) , XPathConstants.STRING));
		claimIssue.setIssueType(Constants.GPGuarantee.CLAIM_ISSUE);
		claimIssue.setIssueTypeDesc(Constants.GPGuarantee.CLAIM_ISSUE_DESC);
		//System.out.println("claimIssue >> " + claimIssue);
		return claimIssue;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee getExtendExpiryDateIssue(XPathReader reader, ConfigXMLCustomsGP configXMLCustomsGP) throws Exception {
		GPGuarantee issue = new GPGuarantee();
		String messageRoot = configXMLCustomsGP.getExtendExpiryDateIssueMessageRoot();
		issue.setTxRef((String)reader.read(messageRoot.concat(configXMLCustomsGP.getTransRef()) , XPathConstants.STRING));
		issue.setDtm((String)reader.read(messageRoot.concat(configXMLCustomsGP.getTransDate()) , XPathConstants.STRING));
		issue.setProjNo((String)reader.read(messageRoot.concat(configXMLCustomsGP.getProjectNo()) , XPathConstants.STRING));
		issue.setVendorTaxId((String)reader.read(messageRoot.concat(configXMLCustomsGP.getVendorTaxId()) , XPathConstants.STRING));
		issue.setVendorName((String)reader.read(messageRoot.concat(configXMLCustomsGP.getVendorName()) , XPathConstants.STRING));
		issue.setCompId((String)reader.read(messageRoot.concat(configXMLCustomsGP.getCompId()) , XPathConstants.STRING));
		issue.setEndDate(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getEndDate()) , XPathConstants.STRING)));
		issue.setStartDate(StringUtil.nullToBlank((String)reader.read(messageRoot.concat(configXMLCustomsGP.getStartDate()) , XPathConstants.STRING)));
		issue.setBondType((String)reader.read(messageRoot.concat(configXMLCustomsGP.getBondType()) , XPathConstants.STRING));
		issue.setLgNo((String)reader.read(messageRoot.concat(configXMLCustomsGP.getLgNo()) , XPathConstants.STRING));
		issue.setExtendDate((String)reader.read(messageRoot.concat(configXMLCustomsGP.getExtendDate()) , XPathConstants.STRING));
		issue.setIssueType(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE);
		issue.setIssueTypeDesc(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_DESC);
		System.out.println("extendExpiryDateIssue >> " + issue);
		return issue;
	}
	
	public void insertGPGuarantee(GPGuarantee gpGuarantee) throws Exception {
		ConnectDB connectDB = null;
		try{
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<insertGPGuarantee> new connection : "+connectDB.hashCode());
			//connectDB.beginTransaction();
			
			if(gpGuarantee.getTransactionStatus() == null){
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
			}
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			int id = gpGuaranteeTable.add(gpGuarantee);
			gpGuarantee.setId(id);
			//connectDB.commit();
		}catch(Exception ex){
			//connectDB.rollback();
			logger.error("Error Insert E-Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally{
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<insertGPGuarantee> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	
	public String objectToEbxmlReturnGP(GPGuarantee gpGuarantee, HttpServletRequest request){
		String xmlStr = "";
		String tempFile = "";
		//UR58120031 add by Tana.L@23022016
		if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
			tempFile = Constants.TEMPLETE_XML_EED_RETURN_TO_GP;
		}
		else {
			tempFile = Constants.TEMPLETE_XML_RETURN_TO_GP;
		}
		
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		try{
			if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
				template.setContext("rootTag", StringUtil.nullToBlankEscapeXML(getRootTag(gpGuarantee)));
				template.setContext("projNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getProjNo()));
				template.setContext("vendorTaxId", StringUtil.nullToBlankEscapeXML(gpGuarantee.getVendorTaxId()));
				template.setContext("vendorName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getVendorName()));
	            template.setContext("compId", StringUtil.nullToBlankEscapeXML(gpGuarantee.getCompId()));
	            template.setContext("endDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getEndDate()));
	            template.setContext("startDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getStartDate()));
	            template.setContext("bondType", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBondType()));
	            if("AP".equals(gpGuarantee.getApproveStatus())){
	            	template.setContext("statusLg", StringUtil.nullToBlankEscapeXML("11"));
	            }else{
	            	template.setContext("statusLg", StringUtil.nullToBlankEscapeXML("12"));
	            }
	            //template.setContext("statusLg", StringUtil.nullToBlankEscapeXML(gpGuarantee.getStatusLG()));
				if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_UNSUCCESS)){
					if(gpGuarantee.getMsgCode()!=null){
						EStatusService eStatusService = new EStatusService();
						EStatus eStatus = null;
						try{
							eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, gpGuarantee.getMsgCode()));
						}catch (Exception e) {
							//nothing
						}
						
						if(eStatus == null){
							eStatus = new EStatus(gpGuarantee.getMsgCode(), "");
							eStatus.setDescEn(Constants.StatusLGGP.UNABLE_TO_PROCESS);
						}
	                    template.setContext("statusDesc", StringUtil.nullToBlankEscapeXML(eStatus.getDescEn()));
					}else{
	                    template.setContext("statusDesc", StringUtil.nullToBlankEscapeXML(gpGuarantee.getXmlOutput()));
					}
				}else{
					template.setContext("statusDesc", Constants.BLANK);
				}
				template.setContext("appDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getAppvDate()));
	            template.setContext("lgNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getLgNo()));
	            template.setContext("extendDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getExtendDate()));
			}else{
	            template.setContext("rootTag", StringUtil.nullToBlankEscapeXML(getRootTag(gpGuarantee)));
	            template.setContext("txRef", StringUtil.nullToBlankEscapeXML(gpGuarantee.getTxRef()));
	            template.setContext("dtm", StringUtil.nullToBlankEscapeXML(gpGuarantee.getDtm()));
	            template.setContext("projNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getProjNo()));
	            template.setContext("deptCode", StringUtil.nullToBlankEscapeXML(gpGuarantee.getDeptCode()));
	            template.setContext("vendorTaxId", StringUtil.nullToBlankEscapeXML(gpGuarantee.getVendorTaxId()));
	            template.setContext("vendorName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getVendorName()));
	            template.setContext("compId", StringUtil.nullToBlankEscapeXML(gpGuarantee.getCompId()));
	            template.setContext("userId", StringUtil.nullToBlankEscapeXML(gpGuarantee.getUserId()));
				template.setContext("seqNo", gpGuarantee.getSeqNo());
	            template.setContext("considerDesc", StringUtil.nullToBlankEscapeXML(gpGuarantee.getConsiderDesc()));
				template.setContext("considerMoney", gpGuarantee.getConsiderMoney());
	            template.setContext("contractNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getContractNo()));
	            template.setContext("contractDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getContractDate()));
				template.setContext("guaranteePrice", gpGuarantee.getGuaranteePrice());
				template.setContext("guaranteePercent", gpGuarantee.getGuaranteePercent());
				template.setContext("advGraranteePrice", gpGuarantee.getAdvanceGuaranteePrice());
				template.setContext("advPayment", gpGuarantee.getAdvancePayment());
				template.setContext("workGuaranteePrice", gpGuarantee.getWorksGuaranteePrice());
				template.setContext("workGuaranteePercent", gpGuarantee.getWorksGuaranteePercent());
	            template.setContext("collectionPhase", StringUtil.nullToBlankEscapeXML(gpGuarantee.getCollectionPhase()));
	            template.setContext("endDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getEndDate()));
	            template.setContext("startDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getStartDate()));
	            template.setContext("bondType", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBondType()));
	            template.setContext("projectName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getProjName()));
				template.setContext("projectAmt", gpGuarantee.getProjAmt());
	            template.setContext("projectOwnName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getProjOwnName()));
	            template.setContext("costCenter", StringUtil.nullToBlankEscapeXML(gpGuarantee.getCostCenter()));
	            template.setContext("constCenterName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getCostCenterName()));
	            template.setContext("statusLg", StringUtil.nullToBlankEscapeXML(gpGuarantee.getStatusLG()));
				if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_UNSUCCESS)){
					if(gpGuarantee.getMsgCode()!=null){
						EStatusService eStatusService = new EStatusService();
						EStatus eStatus = null;
						try{
							eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, gpGuarantee.getMsgCode()));
						}catch (Exception e) {
							//nothing
						}
						
						if(eStatus == null){
							eStatus = new EStatus(gpGuarantee.getMsgCode(), "");
							eStatus.setDescEn(Constants.StatusLGGP.UNABLE_TO_PROCESS);
						}
	                    template.setContext("statusDesc", StringUtil.nullToBlankEscapeXML(eStatus.getDescEn()));
					}else{
	                    template.setContext("statusDesc", StringUtil.nullToBlankEscapeXML(gpGuarantee.getXmlOutput()));
					}
				}else{
					template.setContext("statusDesc", Constants.BLANK);
				}
				template.setContext("appvAmt", gpGuarantee.getAppvAmt());
	            template.setContext("appDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getAppvDate()));
	            template.setContext("expireDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getExpireDate()));
	            template.setContext("lgNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getLgNo()));
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
	                template.setContext("documentNo", StringUtil.nullToBlankEscapeXML(gpGuarantee.getDocumentNo()));
	                template.setContext("documentDate", StringUtil.nullToBlankEscapeXML(gpGuarantee.getDocumentDate()));
	                template.setContext("bankCode", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBankCode()));
	                template.setContext("branchCode", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBranchCode()));
	                template.setContext("branchName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBranchName()));
	                template.setContext("bankAddr", StringUtil.nullToBlankEscapeXML(gpGuarantee.getBankAddr()));
	                template.setContext("authSigName", StringUtil.nullToBlankEscapeXML(gpGuarantee.getAuthSigName()));
	                template.setContext("position", StringUtil.nullToBlankEscapeXML(gpGuarantee.getPosition()));
	                template.setContext("witnessName1", StringUtil.nullToBlankEscapeXML(gpGuarantee.getWitnessName1()));
	                template.setContext("witnessName2", StringUtil.nullToBlankEscapeXML(gpGuarantee.getWitnessName2()));
				}
				template.setContext("guaranteeAmt", gpGuarantee.getGuaranteeAmt());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		xmlStr = template.getXML();
		return xmlStr;
	}
	
	private String getRootTag(GPGuarantee gpGuarantee){
		String rootTag = "";
		if(Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())){
			rootTag = Constants.GPGuarantee.SETUP_ISSUE_TAG_ROOT_RS;
		}else if(Constants.GPGuarantee.CANCEL_ISSUE.equals(gpGuarantee.getIssueType())){
			rootTag = Constants.GPGuarantee.CANCEL_ISSUE_TAG_ROOT_RS;
		}else if(Constants.GPGuarantee.CLAIM_ISSUE.equals(gpGuarantee.getIssueType())){
			rootTag = Constants.GPGuarantee.CLAIM_ISSUE_TAG_ROOT_RS;
		}else if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
			rootTag = Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_TAG_ROOT_RS;
		}
		return rootTag;
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	public GPGuaranteeALSOffline processEguaranteeBatch(LogEBXML logEBXML, HttpServletRequest request)throws Exception {
		LoanLGResponse loanLGResponse = null;
		ConnectDB connectDB = null;
		ALSMQService alsMQService = null;
		GPGuarantee gpGuarantee = null;
		GPGuaranteeALSOffline gpGuaranteeALSOffline = new GPGuaranteeALSOffline();
		try{
			alsMQService =  new ALSMQService();
			connectDB = new ConnectDB();
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			GPReviewLogService gpReviewLogService = new GPReviewLogService();
			GPApprovalLogService gpApprovalLogService = new GPApprovalLogService();
            System.out.println("GPGuaranteeService<processEguaranteeBatch> new connection : "+connectDB.hashCode());
			connectDB.beginTransaction();
			//--- xmlInput from EBXML to Object
			gpGuarantee = ebxmlToObject(logEBXML.getXmlInput());
			gpGuarantee.setLgNo(logEBXML.getLgNo());
			ETimeService eTimeService = new ETimeService(); 
			ETime eTime = eTimeService.findETime(Constants.ALS_SYSTEM_NAME);
			gpGuarantee.setProcessDate(eTime.getProcessDate());
			System.out.println("gpGuarantee ProcessDate:" + gpGuarantee.getProcessDate());
			
			//---- set account no
			boolean isOffline = true;
			AccountALS accountAls = getAccount(connectDB,isOffline,gpGuarantee,request);
			gpGuarantee.setAccountNo(accountAls.getAccountNo());
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			loanLGResponse = alsMQService.sendMQMessage(connectDB, gpGuarantee, request);
			//check
			gpGuaranteeALSOffline.setTxRef(gpGuarantee.getTxRef());
			gpGuaranteeALSOffline.setAccountNo(gpGuarantee.getAccountNo());
			gpGuaranteeALSOffline.setLgNo(gpGuarantee.getLgNo());
			
			//Fixing for UR58120031 add-extend-expire-date-interface (Phase 2)
			if (Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
				GPGuarantee setupTxn = gpGuaranteeTable.getGPGuaranteeByLGNo(gpGuarantee.getLgNo());
				gpGuaranteeALSOffline.setProjName(setupTxn.getProjName());
				gpGuaranteeALSOffline.setProjOwnName(setupTxn.getProjOwnName());
				gpGuaranteeALSOffline.setGuaranteeAmt(setupTxn.getGuaranteeAmt());
		        gpGuarantee.setNewEndDate(gpGuarantee.getExtendDate());
		        gpGuarantee.setOldEndDate(setupTxn.getEndDate());
			}
			
			if(loanLGResponse != null && ("0001").equals(loanLGResponse.getResCode())){
				if(("00").equals(loanLGResponse.getResALSStatusCode().trim())){
					//TRANSACTION PROCESSED OK 
					//System.out.println("--------- in success ----------------");
					gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_SUCCESS);
					gpGuaranteeALSOffline.setResCode("0001");
					
					//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
			        if (Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
			        	System.out.println("======== insert gp_guarantee_h ==========");
			        	addSetupTxnHistoryFromLGNo(Constants.GPGuarantee.PROCESS_BY_SYSTEM, gpGuarantee.getLgNo(), connectDB);
			        	System.out.println("======== update expire_date of the setup txn ==========");
			        	//updateSetupTxnExpireDateAndStatus(gpGuarantee);
			        	//GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			        	gpGuaranteeTable.updateSetupTxnExpireDateAndStatus(gpGuarantee.getExtendDate(), gpGuarantee.getLgNo());
			        }
			        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
					
				}else{
					//System.out.println("--------- in not success ----------------");
					gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					String errorCode = loanLGResponse.getResALSErrorCode();
					String errorMsg = loanLGResponse.getResALSISQLMsg();
					String errorOnALS = "";
					if(errorCode != null && errorMsg != null){
						errorOnALS = StringUtil.nullToBlank(errorCode)+" - "+StringUtil.nullToBlank(errorMsg);
					}
					gpGuaranteeALSOffline.setResCode(mapCodeErrorCode(loanLGResponse.getResALSErrorCode()));
					gpGuaranteeALSOffline.setResMsg(errorOnALS);
					
					
				}
			}else{
				gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
				gpGuaranteeALSOffline.setResCode(loanLGResponse.getResCode());
				gpGuaranteeALSOffline.setResMsg(loanLGResponse.getResDesc());
			}
			
			//Fixing for UR58120031 add-extend-expire-date-interface (Phase 2)
			if (Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
				int id = getIdByLgNo(gpGuarantee);
				gpGuarantee.setId(id);
				gpGuarantee = setStatusAutoApproved(gpGuarantee);
		        System.out.println("======== insert gp_review_log ==========");
		        gpReviewLogService.insert(gpGuarantee);
		        System.out.println("======== insert gp_approval_log ==========");
		        gpApprovalLogService.insert(gpGuarantee);
			}
			
			connectDB.commit();
		}catch(EGuaranteeMQMessageException ex){
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			gpGuaranteeALSOffline.setResMsg("Error EGuaranteeMQMessageException @ process Eguarantee Batch ");
		}catch(Exception ex){
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			gpGuaranteeALSOffline.setResMsg("Error Exception @ process Eguarantee Batch ");
        }finally{
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<processEguaranteeBatch> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
		return gpGuaranteeALSOffline;
	}
	//changed from
	/*
	public GPGuaranteeALSOffline processEguaranteeBatch(LogEBXML logEBXML, HttpServletRequest request)throws Exception {
		LoanLGResponse loanLGResponse = null;
		ConnectDB connectDB = null;
		ALSMQService alsMQService = null;
		GPGuarantee gpGuarantee = null;
		GPGuaranteeALSOffline gpGuaranteeALSOffline = new GPGuaranteeALSOffline();
		try{
			alsMQService =  new ALSMQService();
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<processEguaranteeBatch> new connection : "+connectDB.hashCode());
			connectDB.beginTransaction();
			//--- xmlInput from EBXML to Object
			gpGuarantee = ebxmlToObject(logEBXML.getXmlInput());
			gpGuarantee.setLgNo(logEBXML.getLgNo());
			//set new contract Date --> is today
			//gpGuarantee.setContractDate(DateUtil.getDateFormatYYYYMMDD(new Date()));
			ETimeService eTimeService = new ETimeService(); 
			ETime eTime = eTimeService.findETime(Constants.ALS_SYSTEM_NAME);
			gpGuarantee.setProcessDate(eTime.getProcessDate());
			System.out.println("gpGuarantee ProcessDate:" + gpGuarantee.getProcessDate());
			
			//---- set account no
			boolean isOffline = true; //update by Apichart H.@20150518
			AccountALS accountAls = getAccount(connectDB,isOffline,gpGuarantee,request);
			gpGuarantee.setAccountNo(accountAls.getAccountNo());
			
			loanLGResponse = alsMQService.sendMQMessage(connectDB, gpGuarantee, request);

			//check
			gpGuaranteeALSOffline.setTxRef(gpGuarantee.getTxRef());
			gpGuaranteeALSOffline.setAccountNo(gpGuarantee.getAccountNo());
			gpGuaranteeALSOffline.setLgNo(gpGuarantee.getLgNo());
			//System.out.println("loanLGResponse.getResCode() : " + loanLGResponse.getResCode());
			if(loanLGResponse != null && ("0001").equals(loanLGResponse.getResCode())){
				//System.out.println("loanLGResponse.getResALSStatusCode() : " + loanLGResponse.getResALSStatusCode());
				if(("00").equals(loanLGResponse.getResALSStatusCode().trim())){//TRANSACTION PROCESSED OK 
					//System.out.println("--------- in success ----------------");
					gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_SUCCESS);
					gpGuaranteeALSOffline.setResCode("0001");
				}else{
					//System.out.println("--------- in not success ----------------");
					gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					String errorCode = loanLGResponse.getResALSErrorCode();
					String errorMsg = loanLGResponse.getResALSISQLMsg();
					String errorOnALS = "";
					if(errorCode != null && errorMsg != null){
						errorOnALS = StringUtil.nullToBlank(errorCode)+" - "+StringUtil.nullToBlank(errorMsg);
					}
					gpGuaranteeALSOffline.setResCode(mapCodeErrorCode(loanLGResponse.getResALSErrorCode()));
					gpGuaranteeALSOffline.setResMsg(errorOnALS);
				}
			}else{
				gpGuaranteeALSOffline.setStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
				gpGuaranteeALSOffline.setResCode(loanLGResponse.getResCode());
				gpGuaranteeALSOffline.setResMsg(loanLGResponse.getResDesc());
			}
			connectDB.commit();
		}catch(EGuaranteeMQMessageException ex){
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			gpGuaranteeALSOffline.setResMsg("Error EGuaranteeMQMessageException @ process Eguarantee Batch ");
		}catch(Exception ex){
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			gpGuaranteeALSOffline.setResMsg("Error Exception @ process Eguarantee Batch ");
        }finally{
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<processEguaranteeBatch> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
		return gpGuaranteeALSOffline;
	}
	*/
	
	private String mapCodeErrorCode(String errorMsgALSMQ){
		String errorCodeMap = "";
		System.out.println("------> errorMsgALSMQ : " +errorMsgALSMQ);
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
		
		//get message description
		EStatusService eStatusService = new EStatusService();
		EStatus eStatus = null;
		try{
			eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, errorCodeMap));
		}catch (Exception e) {
			// --: handle exception
			eStatus = new EStatus(errorCodeMap, "");
		}
		System.out.println("return code : " + errorCodeMap + "| description : " + eStatus.getDescEn());
		
		return errorCodeMap;
	}
	
	private String mapCodeErrorMessage(String errorMsgALSMQ){
		
		String errorCodeMap = "";
		System.out.println("------> errorMsgALSMQ : " +errorMsgALSMQ);
		
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
		
		//get message description
		EStatusService eStatusService = new EStatusService();
		EStatus eStatus = null;
		try{
			eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, errorCodeMap));
		}catch (Exception e) {
			// --: handle exception
			eStatus = new EStatus(errorCodeMap, "");
		}
		System.out.println("return code : " + errorCodeMap + "| description : " + eStatus.getDescEn());
		
		return eStatus.getDescEn();
	}
	
	//R58060012 : add by bussara.b @20150611
	public void updateApprovalProcess(GPGuarantee gpGuarantee) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateApprovalProcess> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			gpGuaranteeTable.updateApprovalProcess(gpGuarantee);
        } catch (Exception ex) {
			logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateApprovalProcess> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}

	//R58060012 : add by bussara.b @20150615
	public void updateApproveStatus(GPGuarantee gpGuarantee) throws Exception {
		
		ConnectDB connectDB = null;
		
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateApproveStatus> new connection : "+connectDB.hashCode());
            AccountALSTable accountAlsTable = new AccountALSTable(connectDB);
            AccountALS account = accountAlsTable.findByAccontNo(gpGuarantee.getAccountNo(), Constants.AccountPurpose.ACCOUNT_EGP);
            //UR59040034 Add eGP Pending Review & Resend Response function
            //gpGuarantee.setOcCode(account.getOcCode());
            gpGuarantee.setOcCode(account != null ? account.getOcCode() : "");
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			gpGuaranteeTable.updateApproveStatus(gpGuarantee);
        } catch (Exception ex) {
			logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateApproveStatus> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	
	//R58060012 : add by bussara.b @20150615
	public void updateApproveStatus(int gpGuaranteeId) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateApproveStatus> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			gpGuaranteeTable.updateApproveStatus(gpGuaranteeId);
        } catch (Exception ex) {
			logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateApproveStatus> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	
	//R58060012 : add by bussara.b @20150616
	public void updateStatusBatch(GPGuarantee gpGuarantee, boolean isSuccess) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateStatusBatch> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			gpGuaranteeTable.updateStatusBatch(gpGuarantee, isSuccess);
        } catch (Exception ex) {
			logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateStatusBatch> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}	
	
	//------- Add by Tana L. @11042016
	//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
	public int getIdByLgNo(GPGuarantee gpGuarantee) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<getIdByLgNo> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			return gpGuaranteeTable.getIdByLgNo(gpGuarantee.getLgNo(), gpGuarantee.getIssueType());
        } catch (Exception ex) {
			logger.error("Error getIdByLgNo : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<getIdByLgNo> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	
	//R58060012 : add by pariwat.s @20150622
		public String getTxRefById(GPGuarantee gpGuarantee) throws Exception {
			ConnectDB connectDB = null;
			try {
				connectDB = new ConnectDB();
	            System.out.println("GPGuaranteeService<getTxRefById> new connection : "+connectDB.hashCode());
				GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
				return gpGuaranteeTable.getTxRefById(gpGuarantee,gpGuarantee.getId());
	        } catch (Exception ex) {
				logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
	            throw  new Exception(ex.getMessage());
	        } finally {
	        	if (connectDB != null) {
	            	System.out.println("GPGuaranteeService<getTxRefById> close connection : "+connectDB.hashCode());
	        		connectDB.close();
	        	}
	        }
		}
	
	public GPGuarantee getGPGuaranteeById(int id) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<getGPGuaranteeById> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			return gpGuaranteeTable.getGPGuaranteeById(id);
		}catch(Exception ex) {
			logger.error("Error Update GP_Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<getGPGuaranteeById> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}

	public boolean isDupGPGuaranteeRejectedTxn(GPGuarantee gpGuarantee, ConnectDB connectDB) throws Exception{
		boolean isDup = false;
		//check setup-issue case only
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			isDup = gpGuaranteeTable.isDupGPGuaranteeTxn(gpGuarantee, "rejected");
		}
		return isDup;
	}

	public boolean isDupGPGuaranteeApprovedSuccessTxn(GPGuarantee gpGuarantee, ConnectDB connectDB) throws Exception{
		boolean isDup = false;
		//check setup-issue case only
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			isDup = gpGuaranteeTable.isDupGPGuaranteeTxn(gpGuarantee, "approved_success");
		}
		return isDup;
	}

	public boolean isDupGPGuaranteeCompletedTxn(GPGuarantee gpGuarantee, ConnectDB connectDB) throws Exception{
		boolean isDup = false;
		//check setup-issue case only
		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			isDup = gpGuaranteeTable.isDupGPGuaranteeTxn(gpGuarantee, "completed");
		}
		return isDup;
	}

    public boolean isDupGPGuaranteeCompletedTxn(GPGuarantee gpGuarantee) throws Exception{
        boolean isDup = false;
        ConnectDB connectDB = new ConnectDB();
        System.out.println("GPGuaranteeService<isDupGPGuaranteeCompletedTxn> new connection : "+connectDB.hashCode());
        //check setup-issue case only
        try {
	        if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
	            GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
	            isDup = gpGuaranteeTable.isDupGPGuaranteeTxn(gpGuarantee, "completed");
	        }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connectDB!=null){
            	System.out.println("GPGuaranteeService<isDupGPGuaranteeCompletedTxn> close connection : "+connectDB.hashCode());
                connectDB.close();
            }
        }
        return isDup;
    }
	    
	public void delay(int sec){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			System.out.println("=========== delay for " + String.valueOf(sec) + " sec ============");
			Calendar dc = Calendar.getInstance(Locale.US);
			System.out.println("----- BEGIN DELAY: " + sdf.format(dc.getTime()) + " -----");
			java.util.concurrent.TimeUnit.SECONDS.sleep(sec);
			dc = Calendar.getInstance(Locale.US);
			System.out.println("----- END DELAY: " + sdf.format(dc.getTime()) + " -----");
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public boolean isDupProjectTax5Keys(GPGuarantee gpGuarantee) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
			System.out.println("GPGuaranteeService<isDupProjectTax5Keys> new connection : "+connectDB.hashCode());
			ProjectTaxTable projectTax = new ProjectTaxTable(connectDB);
			return projectTax.isDupProjectTax5Keys(gpGuarantee);
		}catch(Exception ex) {
			logger.error("Error Inquiry GP_Guarantee : "+ex.getMessage());
		            throw  new Exception(ex.getMessage());            
		}finally {
			if (connectDB != null) {
            	System.out.println("GPGuaranteeService<isDupProjectTax5Keys> close connection : "+connectDB.hashCode());
				connectDB.close();
            }
        }
    }

    //R58060060 : add by Apichart.H @20151019
    public void updateReviewStatus(GPGuarantee gpGuarantee) throws Exception {
        ConnectDB connectDB = null;
        try {
            connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateReviewStatus> new connection : "+connectDB.hashCode());
            AccountALSTable accountAlsTable = new AccountALSTable(connectDB);
            AccountALS account = accountAlsTable.findByAccontNo(gpGuarantee.getAccountNo(), Constants.AccountPurpose.ACCOUNT_EGP);
         	//UR59040034 Add eGP Pending Review & Resend Response function
            //gpGuarantee.setOcCode(account.getOcCode());
            gpGuarantee.setOcCode(account != null ? account.getOcCode() : "");
            GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
            gpGuaranteeTable.updateReviewStatus(gpGuarantee);
        } catch (Exception ex) {
            logger.error("Error Update GP_Guarantee : " + ex.getMessage());
            throw new Exception(ex.getMessage());

        } finally {
            if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateReviewStatus> close connection : "+connectDB.hashCode());
                connectDB.close();
            }
        }

    }
    
  //UR59040034 Add eGP Pending Review & Resend Response function
    public void updateResendCount(GPGuarantee gpGuarantee) throws Exception {
        ConnectDB connectDB = null;
        try {
            connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateResendCount> new connection : "+connectDB.hashCode());
            GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
            gpGuaranteeTable.updateResendCount(gpGuarantee);
        } catch (Exception ex) {
            logger.error("Error Update GP_Guarantee : " + ex.getMessage());
            throw new Exception(ex.getMessage());

        } finally {
            if (connectDB != null) {
            	System.out.println("GPGuaranteeService<updateResendCount> close connection : "+connectDB.hashCode());
                connectDB.close();
            }
        }

    }
    
    //add by Malinee T. UR58100048 @20160104
    public void updateGpGuaranteeEgpAcknowledge(GPGuarantee gpGuarantee) throws Exception {
    	ConnectDB connectDB = null;
        try {
            connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateGpGuaranteeEgpAcknowledge> new connection : "+connectDB.hashCode());
            ProjectTaxTable projectTaxTable = new ProjectTaxTable(connectDB);
            projectTaxTable.updateGpGuaranteeEgpAcknowledge(gpGuarantee);
        } catch (Exception ex) {
            logger.error("Error Update GP_Guarantee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
            if (connectDB != null) {
                System.out.println("GPGuaranteeService<updateGpGuaranteeEgpAcknowledge> close connection : "+connectDB.hashCode());
                connectDB.close();
            }
        }
    }
    //UR58120031 Phase 2 Send SMS after Approval Request add by Tana L. @21042016
	public boolean alreadySendSMS(String txRef) throws Exception {
		boolean alreadySMS = true;
    	ConnectDB connectDB = null;
        try {
            connectDB = new ConnectDB();
            System.out.println("txRef : "+txRef);
            System.out.println("GPGuaranteeService<alreadySendSMS> new connection : "+connectDB.hashCode());
            StringBuilder sql = new StringBuilder();
    	    sql.append(" select * from ").append(Constants.TableName.LOG_MQ);
    	    sql.append(" where customs_ref_no = ? and send_id = 'SMS' ");     	    
        	Map<String, Object> result = connectDB.querySingle(sql.toString(), txRef);
        	if(result == null){
           		alreadySMS = false;
           	}
        } catch (Exception ex) {
            logger.error("Error alreadySendSMS : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        } finally {
            if (connectDB != null) {
                System.out.println("GPGuaranteeService<alreadySendSMS> close connection : "+connectDB.hashCode());
                connectDB.close();
            }
        }
        return alreadySMS;
    }
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//get logEBXML with the correct sequence
	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	public GPGuarantee getGPGuaranteeWithSeqById(int id) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<getGPGuaranteeWithSeqById> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			return gpGuaranteeTable.getGPGuaranteeWithSeqById(id);
		}catch(Exception ex) {
			logger.error("Error Inquiry GP_Guarantee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<getGPGuaranteeWithSeqById> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	public void addTranGPOffline(TranGPOffline tranGPOffline) throws Exception {
		ConnectDB connectDB = null;
		try{
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<updateTranGPOffline> new connection : "+connectDB.hashCode());
			//connectDB.beginTransaction();
			
            TranGPOfflineTable tranGPOfflineTable = new TranGPOfflineTable(connectDB);
			tranGPOfflineTable.add(tranGPOffline);
			//connectDB.commit();
		}catch(Exception ex){
			//connectDB.rollback();
			logger.error("Error Insert tranGPOffline : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally{
        	if(connectDB != null){
            	System.out.println("GPGuaranteeService<updateTranGPOffline> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	public GPGuarantee getResendGPGuarantee(int id) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
            System.out.println("GPGuaranteeService<getResendGPGuarantee> new connection : "+connectDB.hashCode());
			GPGuaranteeTable gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			return gpGuaranteeTable.getResendGPGuarantee(id);
		}catch(Exception ex) {
			logger.error("Error Inquiry GP_Guarantee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally {
        	if (connectDB != null) {
            	System.out.println("GPGuaranteeService<getResendGPGuarantee> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
		    
}
