package th.co.scb.service.eguarantee;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.DeclarationTable;
import th.co.scb.db.eguarantee.EGuaranteeTable;
import th.co.scb.model.EMapCodeError;
import th.co.scb.model.EStatus;
import th.co.scb.model.ETime;
import th.co.scb.model.LogEBXML;
import th.co.scb.model.RegistrationId;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositCancelNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeRefundNotificationType;
import th.co.scb.model.customs.mainpayment.ObjectFactory;
import th.co.scb.model.eguarantee.Declaration;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.model.eguarantee.EGuaranteeALSOffline;
import th.co.scb.model.mq.LoanLGResponse;
import th.co.scb.service.ALSMQService;
import th.co.scb.service.EMapCodeErrorService;
import th.co.scb.service.EStatusService;
import th.co.scb.service.OffHostService;
import th.co.scb.service.customs.ManageMainPayment;
import th.co.scb.service.customs.ManageMainPaymentImpl;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
import th.co.scb.util.ConfigXMLCustoms;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.MyXMLStreamWriter;
import th.co.scb.util.StringUtil;
import th.co.scb.util.TemplateUtil;
import th.co.scb.util.XPathReader;


public class EGuaranteeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EGuaranteeService.class);
	
	public EGuarantee processEguaranteeWebService(LogEBXML logEBXML, ETime eTime, HttpServletRequest request)throws Exception {
		
//		System.out.println("=========== processEguaranteeWebService =========");
		EGuarantee eGuarantee = null;
		ConnectDB connectDB = null;
		
		OffHostService offHostService = null;
		ALSMQService alsMQService = null;
		
		boolean isDupEGuarantee = false;
		
		try{
			
			offHostService = new OffHostService();
			alsMQService = new ALSMQService();
			
			//--- xmlInput from EBXML to Object
			eGuarantee = ebxmlToObject(logEBXML.getXmlInput(), eTime);
			//System.out.println("eGuarantee : " + eGuarantee);

			//-- connect database
			connectDB = new ConnectDB();
			connectDB.beginTransaction();

			//check dup -- case deposit --> check case in method isDupEGuarantee
			isDupEGuarantee = isDupEGuarantee(connectDB, eGuarantee);
			if(isDupEGuarantee == true){
				logger.debug("======== DUP ==========");
				//set massage code error 
				eGuarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
				eGuarantee.setMessageCode(Constants.MessageCode.DUP_TRANSACTION);
			}else{
				logger.debug("======== No-DUP ==========");
				if(eTime.isTimeOffHostALS() == true){// als offline
					System.out.println("=========== als offline ============");
					//manage offline 
					eGuarantee = offHostService.manageEGuaranteeOffline(connectDB, eGuarantee, logEBXML.getXmlInput(), eTime);
				}else{// als online
					//connect MQ
					System.out.println("=========== als online ============");
					eGuarantee = alsMQService.sendMQMessage(connectDB, eGuarantee, request, eTime);
				}//end of check time als
			}

			connectDB.commit();
		
		}catch(EGuaranteeMQMessageException ex){
			
			connectDB.rollback();
			logger.error("Error process Eguarantee WebService : "+ex.getMessage());
            //throw  new Exception(ex.getMessage());
            
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error process Eguarantee WebService : "+ex.getMessage());
            //throw  new Exception(ex.getMessage());
            
        }finally{
        	
        	if(connectDB != null){
        		connectDB.close();
        	}

        }
		
		
		
		return eGuarantee;
		
	}
	
	public EGuaranteeALSOffline processEguaranteeBatch(LogEBXML logEBXML, HttpServletRequest request)throws Exception {
		
		LoanLGResponse loanLGResponse = null;
		
		ConnectDB connectDB = null;
		ALSMQService alsMQService = null;
		EGuarantee eGuarantee = null;
		
		EGuaranteeALSOffline eGuaranteeALSOffline = new EGuaranteeALSOffline();
		
		try{
			
			alsMQService =  new ALSMQService();
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();

			//--- xmlInput from EBXML to Object
			eGuarantee = ebxmlToObject(logEBXML.getXmlInput());
			eGuarantee.setBankGuaranteeNo(logEBXML.getLgNo());
			
			loanLGResponse = alsMQService.sendMQMessage(connectDB, eGuarantee, request);

			//check
			eGuaranteeALSOffline.setCustomsRef(eGuarantee.getCustomsRef());
			eGuaranteeALSOffline.setDebtorBankAccNo(eGuarantee.getDebtorBankAccNo());
			eGuaranteeALSOffline.setBankGuaranteeNo(eGuarantee.getBankGuaranteeNo());
			//System.out.println("loanLGResponse.getResCode() : " + loanLGResponse.getResCode());
			if(loanLGResponse != null && ("0001").equals(loanLGResponse.getResCode())){
				//System.out.println("loanLGResponse.getResALSStatusCode() : " + loanLGResponse.getResALSStatusCode());
				if(("00").equals(loanLGResponse.getResALSStatusCode().trim())){//TRANSACTION PROCESSED OK 
					//System.out.println("--------- in success ----------------");
					eGuaranteeALSOffline.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
					eGuaranteeALSOffline.setMessageCode("0001");
				}else{
					//System.out.println("--------- in not success ----------------");
					eGuaranteeALSOffline.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					eGuaranteeALSOffline.setMessageCode(loanLGResponse.getResALSErrorCode());
					
					String msgErrDescription = mapCodeErrorMessage(eGuaranteeALSOffline.getMessageCode());
					if(msgErrDescription == null){
						eGuaranteeALSOffline.setMessageDescript(loanLGResponse.getResALSISQLMsg());
					}else{
						eGuaranteeALSOffline.setMessageDescript(msgErrDescription);
					}
					
				}
			}else{
				
				//System.out.println("--------- in ????? ----------------");
				
				eGuaranteeALSOffline.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
				eGuaranteeALSOffline.setMessageCode(loanLGResponse.getResCode());
				eGuaranteeALSOffline.setMessageDescript(loanLGResponse.getResDesc());
			}

			connectDB.commit();
			
		}catch(EGuaranteeMQMessageException ex){
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			eGuaranteeALSOffline.setMessageDescript("Error EGuaranteeMQMessageException @ process Eguarantee Batch ");
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error process Eguarantee Batch : "+ex.getMessage());
			eGuaranteeALSOffline.setMessageDescript("Error Exception @ process Eguarantee Batch ");
            //throw  new Exception(ex.getMessage());
            
        }finally{
        	
        	/*eGuaranteeALSOffline.setCustomsRef(eGuarantee.getCustomsRef());
			eGuaranteeALSOffline.setDebtorBankAccNo(eGuarantee.getDebtorBankAccNo());
			eGuaranteeALSOffline.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
			eGuaranteeALSOffline.setMessageCode("99");*/
 
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return eGuaranteeALSOffline;
		
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
	
	public void insertEGuarantee(EGuarantee eGuarantee) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			//set status
			if(eGuarantee.getEguaranteeStatus().equals(Constants.EGuarantee.STATUS_BOOK)){
				eGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				eGuarantee.setBookingDate(new Date());
			}else{
				eGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
			}
			
			EGuaranteeTable eGuaranteeTable = new EGuaranteeTable(connectDB);
			eGuaranteeTable.add(eGuarantee);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Insert E-Guaranteee : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
	}
	
	public EGuarantee ebxmlToObject(String xmlString) {
		return ebxmlToObject(xmlString, null);
	}
	
	public EGuarantee ebxmlToObject(String xmlString, ETime eTime) {
		
		EGuarantee eguarantee = null;
		ConfigXMLCustoms configXMLCustoms = new ConfigXMLCustoms();
		
		try{
			XPathReader reader = new XPathReader(xmlString);
			
			String xml = (String)reader.read(configXMLCustoms.getDepositMessageRoot(), XPathConstants.STRING);
			if(xml !=null && xml.length() > 0){
				System.out.println("ตั้งภาระ");
				eguarantee = getEGuaranteeDeposit(reader, configXMLCustoms);
			}else{
				//System.out.println("get Issr Tag value : " + reader.getTagValue("Issr"));
				xml = (String)reader.read(configXMLCustoms.getRefundInformPort(), XPathConstants.STRING);
				if(xml !=null && xml.length() > 0){
					System.out.println("ลดภาระ");
					eguarantee = eGuaranteeRefund(reader, configXMLCustoms);
				}else{
					System.out.println("ยกเลิกภาระ");
					eguarantee = eGuaranteeDepositCancellation(reader, configXMLCustoms);
				}
			}
			
			if(eTime != null){
				boolean isTimeOffHostALS = eTime.isTimeOffHostALS();
				if(isTimeOffHostALS == true){
					eguarantee.setAlsOnline(Constants.StatusALSOnline.NO);
					eguarantee.setProcessDate(eTime.getProcessDate());
	        	}else{
	        		eguarantee.setAlsOnline(Constants.StatusALSOnline.YES);
	        		eguarantee.setProcessDate(new Date());
	        	}
			}
			
			
		}catch (Exception e) {
			// --: handle exception
			//outLog.append("\n " + DateUtil.getDateTimeX() + " -->:" + " xmlToObject Error : "+e.getMessage()+"....\n");
			logger.debug("Error xml to object ... " + e.getMessage());
			//throw  new Exception(e.getMessage());
		}

		return eguarantee;
	}
	
	public String objectToEbxmlReturn(EGuarantee eguarantee, HttpServletRequest request){
		
		String xmlStr = ""; 
		
		String tempFile = Constants.TEMPLETE_XML_RETURN_EBXML;
		
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		template.setContext("bankTransNo", eguarantee.getBankTransactionNo());
		template.setContext("bankTransDateTime", DateUtil.getDateFormatYYYYMMDDHHMM(new Date()));
		template.setContext("bankAcctNo", eguarantee.getDebtorBankAccNo());
		template.setContext("bankRef", eguarantee.getBankTransactionNo());
		
		template.setContext("status", eguarantee.getEguaranteeStatus());
		template.setContext("depositDateTime", DateUtil.getDateFormatYYYYMMDDHHMM(eguarantee.getBookingDate()));
		template.setContext("bankCode", eguarantee.getDebtorBankCode());
		
		template.setContext("messageId", eguarantee.getDocName());
		template.setContext("customsRefNo", eguarantee.getCustomsRef());
		template.setContext("depositAmt", eguarantee.getDepositAmount());
		template.setContext("debtorCompanyName", eguarantee.getDebtorCompanyName());
		
		ArrayList<Map<String, String>> orgIdList = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();

        map.put("id", eguarantee.getDebtorCompanyTaxNo());
        map.put("properties", "CMPTAXNUM");
        orgIdList.add(map);

        map = new HashMap<String, String>();
        map.put("id", eguarantee.getDebtorCompanyBranch());
        map.put("properties", "CMPBRN");
        orgIdList.add(map);
        
		template.setContext("OrgIdList", orgIdList);
		template.setContext("debtorBankAcctNo", eguarantee.getDebtorBankAccNo());
		template.setContext("debtorBankCode", eguarantee.getDebtorBankCode());
		template.setContext("debtorBankBranchCode", eguarantee.getDebtorBankBranchCode());
		
		ArrayList<Map<String, String>> refDocInfoList = new ArrayList<Map<String, String>>();
		
        map = new HashMap<String, String>();
        map.put("properties", "DCLNUM");
        map.put("number", eguarantee.getDeclarationNo());
        map.put("issuer", "");
        map.put("relatedDate", "");
        refDocInfoList.add(map);

        if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.REFUND)){
        	map = new HashMap<String, String>();
            map.put("properties", "BNKGRTNUM");
            map.put("number", eguarantee.getBankGuaranteeNo());
            map.put("issuer", "");
            map.put("relatedDate", "");
            refDocInfoList.add(map);
            
            map = new HashMap<String, String>();
            map.put("properties", "INFORM");
            map.put("number", eguarantee.getInformNo());
            map.put("issuer", eguarantee.getInformPort());
            map.put("relatedDate", eguarantee.getInformDate());
            refDocInfoList.add(map);
        }else{
        	map = new HashMap<String, String>();
            map.put("properties", "DCLSEQNUM");
            map.put("number", eguarantee.getDeclarationSeqNo());
            map.put("issuer", "");
            map.put("relatedDate", "");
            refDocInfoList.add(map);
             
            map = new HashMap<String, String>();
            map.put("properties", "BNKGRTNUM");
            map.put("number", StringUtil.nullToBlank(eguarantee.getBankGuaranteeNo()));
            map.put("issuer", "");
            map.put("relatedDate", "");
            refDocInfoList.add(map);
        }
        
		template.setContext("refDocInfoList", refDocInfoList);
		template.setContext("customsTransDateTime", eguarantee.getCustomsTransDate());
		template.setContext("messageStatus", "");
		template.setContext("messageDesc", "");
		
		if(eguarantee.getMessageCode()!=null){
			
			EStatusService eStatusService = new EStatusService();
			EStatus eStatus = null;
			try{
				eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, eguarantee.getMessageCode()));
			}catch (Exception e) {
				// --: handle exception
				eStatus = new EStatus(eguarantee.getMessageCode(), "");
			}
			
			template.setContext("messageStatus", eguarantee.getMessageCode());
			template.setContext("messageDesc", eStatus.getDescEn());
		}
		
		xmlStr = template.getXML();
		//System.out.println(template.getXML());
		
		return xmlStr;
		
	}
	
	public String objectToEbxmlReturnCustoms(EGuarantee eguarantee, HttpServletRequest request, RegistrationId registrationId){
		
		String xmlStr = "";
		ObjectFactory factoryMain = new ObjectFactory();

    	ManageMainPayment manageMainPayment = new ManageMainPaymentImpl();
    	BankToCustomerGuaranteeDepositNotificationType deposit = null;
    	BankToCustomerGuaranteeDepositCancelNotificationType depositCancel = null;
    	BankToCustomerGuaranteeRefundNotificationType refund = null;
    	
    	try{
        	
        	Writer writer = new StringWriter();

        	XMLOutputFactory xof = XMLOutputFactory.newInstance();
        	XMLStreamWriter xsw = xof.createXMLStreamWriter(writer);
        	xsw = new MyXMLStreamWriter(xsw);

	        JAXBContext context = JAXBContext.newInstance("th.co.scb.model.customs.mainpayment");
	        Marshaller marshaller = context.createMarshaller();

	        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        
	        JAXBElement<BankToCustomerGuaranteeDepositNotificationType> elementG1N = null;
	        JAXBElement<BankToCustomerGuaranteeDepositCancelNotificationType> elementG1C = null;
	        JAXBElement<BankToCustomerGuaranteeRefundNotificationType> elementG2N = null;
	        
	        if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
	        	deposit = manageMainPayment.createBankToCustomerGuaranteeDepositNotificationType(eguarantee, registrationId);
				elementG1N = factoryMain.createBankToCustomerGuaranteeDepositNotification(deposit);
				marshaller.marshal(elementG1N, xsw);
	        }else if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT_CANCEL)){
	        	depositCancel = manageMainPayment.createBankToCustomerGuaranteeDepositCancelNotificationType(eguarantee, registrationId);
				elementG1C = factoryMain.createBankToCustomerGuaranteeDepositCancelNotification(depositCancel);
				marshaller.marshal(elementG1C, xsw);
	        }else if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.REFUND)){
	        	refund = manageMainPayment.createBankToCustomerGuaranteeRefundNotificationType(eguarantee, registrationId);
				elementG2N = factoryMain.createBankToCustomerGuaranteeRefundNotification(refund);
				marshaller.marshal(elementG2N, xsw);
	        }
	        

	        xsw.close();
	        xmlStr = writer.toString();
	        xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlStr;
	        System.out.println("Gen XML >>> " + xmlStr);
	        
        }catch (Exception ex){
        	ex.printStackTrace();
        }
    	
		
		return xmlStr;
		
	}
	
	public String objectToXmlBatchReturn(EGuarantee eGuarantee, HttpServletRequest request) throws Exception{
		
		String xmlStr = "";
		String tempFile = Constants.TEMPLETE_XML_RETURN_BATCH;
		
		TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
		template.setContext("eguaranteeStatus", eGuarantee.getEguaranteeStatus());
		template.setContext("messageCode", "");
		template.setContext("messageDesc", "");
		
		if(eGuarantee.getMessageCode()!=null){
			
			EStatusService eStatusService = new EStatusService();
			EStatus eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, eGuarantee.getMessageCode()));
			
			template.setContext("messageCode", eGuarantee.getMessageCode());
			template.setContext("messageDesc", eStatus.getDescEn());
		}
		
		
		xmlStr = template.getXML();
		//System.out.println(template.getXML());
		
		return xmlStr;
		
	}
	
	public boolean isDupEGuarantee(ConnectDB connectDB, EGuarantee eGuarantee) throws Exception{
		boolean isDup = false;
		
		//check deposti case only
		if(eGuarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
			DeclarationTable declarationTable = new DeclarationTable(connectDB);
			isDup = declarationTable.isDupDeclarateion(new Declaration(eGuarantee.getDeclarationNo(), eGuarantee.getDeclarationSeqNo()));
		}
		
		return isDup;
	}
	
	
	public boolean isDupEGuarantee(EGuarantee eGuarantee) throws Exception{
		
		boolean isDup = true;
		
		if(eGuarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){
			
			DeclarationService declarationService = new DeclarationService();
			isDup = declarationService.isDupDeclarateion(new Declaration(eGuarantee.getDeclarationNo(), eGuarantee.getDeclarationSeqNo()));
			
		}else{// cancel or refund //-> no check dup
			isDup = false;
		}
		
		return isDup;
	}
	
	
	private EGuarantee getEGuaranteeDeposit(XPathReader reader, ConfigXMLCustoms configXMLCustoms){
		
		EGuarantee deposit = new EGuarantee();
		
		///------- GroupHeader
		deposit.setCustomsRef((String)reader.read(configXMLCustoms.getDepositCustomsRefNo(), XPathConstants.STRING));
		deposit.setCustomsName((String)reader.read(configXMLCustoms.getDepositCustomsName(), XPathConstants.STRING));
		deposit.setCustomsTransDate((String)reader.read(configXMLCustoms.getDepositCustomsTransDateTime(), XPathConstants.STRING));
		deposit.setNumberOfTrans((String)reader.read(configXMLCustoms.getDepositNoOfTrans(), XPathConstants.STRING));
		
		///-------- PaymentInfromation
		//deposit.setPaymentMethod((String)reader.read(configXMLCustoms.getDepositPaymentMethod(), XPathConstants.STRING));
		deposit.setPaymentMethod(Constants.EGuarantee.DEPOSIT);
		deposit.setPaymentMethodDesc(Constants.EGuarantee.DEPOSIT_DESC);
		deposit.setDocName(Constants.EGuarantee.DOC_NAME_DEPOSIT);
		deposit.setDocReturnName(Constants.EGuarantee.DOC_RETURN_NAME_DEPOSIT);
		deposit.setTransType((String)reader.read(configXMLCustoms.getDepositTransType(), XPathConstants.STRING));
		deposit.setRequestDate((String)reader.read(configXMLCustoms.getDepositRequestDate(), XPathConstants.STRING));
		deposit.setCustomsBankAcc((String)reader.read(configXMLCustoms.getDepositCustomsBankAcct(), XPathConstants.STRING));
		deposit.setCustomsBankCode((String)reader.read(configXMLCustoms.getDepositCustomsBankCode(), XPathConstants.STRING));
		deposit.setCustomsBranchCode((String)reader.read(configXMLCustoms.getDepositCustomsBranchCode(), XPathConstants.STRING));
		
		///---------- Direct Debit Trans Information
		BigDecimal depostiAmt = new BigDecimal((String)reader.read(configXMLCustoms.getDepositAmount(), XPathConstants.STRING));
		deposit.setDepositAmount(depostiAmt);
		deposit.setDebtorBankCode((String)reader.read(configXMLCustoms.getDepositDebtorBankCode(), XPathConstants.STRING));
		deposit.setDebtorBankBranchCode((String)reader.read(configXMLCustoms.getDepositDebtorBranchCode(), XPathConstants.STRING));
		deposit.setDebtorCompanyName((String)reader.read(configXMLCustoms.getDepositDebtorCompName(), XPathConstants.STRING));
		deposit.setDebtorCompanyTaxNo((String)reader.read(configXMLCustoms.getDepositDebtorCompTaxNo(), XPathConstants.STRING));
		deposit.setDebtorCompanyBranch((String)reader.read(configXMLCustoms.getDepositDebtorCompBranch(), XPathConstants.STRING));
		deposit.setDebtorBankAccNo((String)reader.read(configXMLCustoms.getDepositDebtorBankAcctNo(), XPathConstants.STRING));
		deposit.setDeclarationNo((String)reader.read(configXMLCustoms.getDepositDeclarationNo(), XPathConstants.STRING));
		deposit.setRelateDate((String)reader.read(configXMLCustoms.getDepositRelateDate(), XPathConstants.STRING));
		deposit.setDeclarationSeqNo((String)reader.read(configXMLCustoms.getDepositDeclarationSeqNo(), XPathConstants.STRING));
		

		//System.out.println("deposit >> " + deposit.toString());
		
		return deposit;
	}
	
	private EGuarantee eGuaranteeDepositCancellation(XPathReader reader, ConfigXMLCustoms configXMLCustoms){
		
		EGuarantee depositCancel = new EGuarantee();

		///------- GroupHeader
		depositCancel.setCustomsRef((String)reader.read(configXMLCustoms.getDepositCancelCustomsRefNo(), XPathConstants.STRING));
		depositCancel.setCustomsTransDate((String)reader.read(configXMLCustoms.getDepositCancelCustomsTransDateTime(), XPathConstants.STRING));
		depositCancel.setNumberOfTrans((String)reader.read(configXMLCustoms.getDepositCancelNoOfTrans(), XPathConstants.STRING));
		depositCancel.setOriginalCustomsRef((String)reader.read(configXMLCustoms.getDepositCancelOriginalCustomsRefNo(), XPathConstants.STRING));
		depositCancel.setPaymentMethod(Constants.EGuarantee.DEPOSIT_CANCEL);
		depositCancel.setPaymentMethodDesc(Constants.EGuarantee.DEPOSIT_CANCEL_DESC);
		depositCancel.setDocName(Constants.EGuarantee.DOC_NAME_DEPOSIT_CANCEL);
		depositCancel.setDocReturnName(Constants.EGuarantee.DOC_RETURN_NAME_DEPOSIT_CANCEL);
		
		//--------- Original Group Information
		BigDecimal depostiAmt = new BigDecimal((String)reader.read(configXMLCustoms.getDepositCancelAmount(), XPathConstants.STRING));
		depositCancel.setDepositAmount(depostiAmt);
		depositCancel.setDeclarationNo((String)reader.read(configXMLCustoms.getDepositCancelDeclarationNo(), XPathConstants.STRING));
		depositCancel.setRelateDate((String)reader.read(configXMLCustoms.getDepositCancelRelateDate(), XPathConstants.STRING));
		depositCancel.setDeclarationSeqNo((String)reader.read(configXMLCustoms.getDepositCancelDeclarationSeqNo(), XPathConstants.STRING));
		depositCancel.setBankGuaranteeNo((String)reader.read(configXMLCustoms.getDepositCancelBankGuaranteeNo(), XPathConstants.STRING));
		depositCancel.setDebtorCompanyName((String)reader.read(configXMLCustoms.getDepositCancelDebtorCompName(), XPathConstants.STRING));
		depositCancel.setDebtorCompanyTaxNo((String)reader.read(configXMLCustoms.getDepositCancelDebtorCompTaxNo(), XPathConstants.STRING));
		depositCancel.setDebtorCompanyBranch((String)reader.read(configXMLCustoms.getDepositCancelDebtorCompBranch(), XPathConstants.STRING));
		depositCancel.setDebtorBankAccNo((String)reader.read(configXMLCustoms.getDepositCancelDebtorBankAcctNo(), XPathConstants.STRING));
		depositCancel.setDebtorBankCode((String)reader.read(configXMLCustoms.getDepositCancelDebtorBankCode(), XPathConstants.STRING));
		depositCancel.setDebtorBankBranchCode((String)reader.read(configXMLCustoms.getDepositCancelDebtorBranchCode(), XPathConstants.STRING));
		
		//System.out.println("depositCancel >> " + depositCancel.toString());
		
		return depositCancel;
		
		
	}
	
	private EGuarantee eGuaranteeRefund(XPathReader reader, ConfigXMLCustoms configXMLCustoms){
		
		EGuarantee refund = new EGuarantee();
		
		///------- GroupHeader
		refund.setCustomsRef((String)reader.read(configXMLCustoms.getRefundCustomsRefNo(), XPathConstants.STRING));
		refund.setCustomsTransDate((String)reader.read(configXMLCustoms.getRefundCustomsTransDateTime(), XPathConstants.STRING));
		refund.setNumberOfTrans((String)reader.read(configXMLCustoms.getRefundNoOfTrans(), XPathConstants.STRING));
		refund.setOriginalCustomsRef((String)reader.read(configXMLCustoms.getRefundOriginalCustomsRefNo(), XPathConstants.STRING));
		refund.setPaymentMethod(Constants.EGuarantee.REFUND);
		refund.setPaymentMethodDesc(Constants.EGuarantee.REFUND_DESC);
		refund.setDocName(Constants.EGuarantee.DOC_NAME_REFUND);
		refund.setDocReturnName(Constants.EGuarantee.DOC_RETURN_NAME_REFUND);
		
		//--------- Original Group Information
		BigDecimal depostiAmt = new BigDecimal((String)reader.read(configXMLCustoms.getRefundAmount(), XPathConstants.STRING));
		refund.setDepositAmount(depostiAmt);
		refund.setDeclarationNo((String)reader.read(configXMLCustoms.getRefundDeclarationNo(), XPathConstants.STRING));
		refund.setBankGuaranteeNo((String)reader.read(configXMLCustoms.getRefundBankGuaranteeNo(), XPathConstants.STRING));
		refund.setInformPort((String)reader.read(configXMLCustoms.getRefundInformPort(), XPathConstants.STRING));
		refund.setInformNo((String)reader.read(configXMLCustoms.getRefundInformNo(), XPathConstants.STRING));
		refund.setInformDate((String)reader.read(configXMLCustoms.getRefundInformDate(), XPathConstants.STRING));
		refund.setDebtorCompanyName((String)reader.read(configXMLCustoms.getRefundDebtorCompName(), XPathConstants.STRING));
		refund.setDebtorCompanyTaxNo((String)reader.read(configXMLCustoms.getRefundDebtorCompTaxNo(), XPathConstants.STRING));
		refund.setDebtorCompanyBranch((String)reader.read(configXMLCustoms.getRefundDebtorCompBranch(), XPathConstants.STRING));
		refund.setDebtorBankAccNo((String)reader.read(configXMLCustoms.getRefundDebtorBankAcctNo(), XPathConstants.STRING));
		refund.setDebtorBankCode((String)reader.read(configXMLCustoms.getRefundDebtorBankCode(), XPathConstants.STRING));
		refund.setDebtorBankBranchCode((String)reader.read(configXMLCustoms.getRefundDebtorBranchCode(), XPathConstants.STRING));
		
		//System.out.println("refund >> " + refund.toString());
		
		return refund;
		
	}
	
		
}
