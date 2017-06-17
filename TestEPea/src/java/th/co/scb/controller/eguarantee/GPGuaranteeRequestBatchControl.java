/**
 * 
 */
package th.co.scb.controller.eguarantee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.ConfigTable;
import th.co.scb.db.eguarantee.GPGuaranteeTable;
import th.co.scb.model.ETime;
import th.co.scb.model.LogEBXML;
import th.co.scb.model.RegistrationId;
//import th.co.scb.model.eguarantee.EGuaranteeALSOffline;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.GPGuaranteeALSOffline;
import th.co.scb.service.EBXMLConfig;
import th.co.scb.service.ETimeService;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.service.RegistrationIdService;
//import th.co.scb.service.eguarantee.EGuaranteeService;
import th.co.scb.service.eguarantee.GPGuaranteeService;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
import th.co.scb.util.Constants;
import th.co.scb.util.XMLUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
 
/**
 * @author s51486
 *
 */
public class GPGuaranteeRequestBatchControl extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeRequestBatchControl.class);
	
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resCode = "";
		String resMsg = "";
		
		String responseMsg = "";
		String requestor = (request.getParameter("requestor") != null ? request.getParameter("requestor") : "");
		
		LogEBXML logEBXML = null;
		
		LogEBMXLService logEBXMLService = null;
		GPGuaranteeService gpGuaranteeService = null;
		ETimeService eTimeService = null;
		GPGuarantee gpGuarantee = new GPGuarantee();
		try{
			
			logEBXMLService = new LogEBMXLService();
			gpGuaranteeService = new GPGuaranteeService();
			eTimeService = new ETimeService();
			
			//UR59040034 Add eGP Pending Review & Resend Response function
			int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
            gpGuarantee = gpGuaranteeService.getGPGuaranteeWithSeqById(id);
            ETime eTime = eTimeService.isTimeOffHostALS();
            logEBXML = logEBXMLService.getXmlInputByTxtRefAndSeq(gpGuarantee.getTxRef(), gpGuarantee.getSeq());
            if (logEBXML == null) {
                System.out.println("@process Request : Data not found?");
                throw new Exception("Data not found");
            }
			
			
			if(requestor.toLowerCase().equals("resend")) {
				
	            System.out.print("----- [Request for Resending XML Output] -----");
                System.out.print("iGpGuaranteeId : " + request.getParameter("iGpGuaranteeId"));
                gpGuarantee = gpGuaranteeService.processGPguaranteeWebService(logEBXML, eTime, request);
                
                responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
                System.out.println("responseMsg : " + responseMsg);
                //keep log return result to EBXML
                logEBXML.setXmlOutput(responseMsg);
                keepLogOutputEBXML(logEBXML);
                System.out.print("----- [Call URL EBXML] -----");
                callURLEBXMLWithRetry(responseMsg, gpGuarantee, null, logEBXML);
	            
			} else {

				System.out.print("----- [Request for Execution GPTransaction to ALS] -----");
				System.out.println("logEBXML : Customs Ref No.: " + logEBXML.getCustomsRefNo());
				System.out.println("logEBXML : Issue Type : " + logEBXML.getIssueType());
				System.out.println("logEBXML : ID: " + logEBXML.getId());
				System.out.println("logEBXML : Lg No.: " + logEBXML.getLgNo());
				gpGuarantee = gpGuaranteeService.processGPguaranteeWebService(logEBXML, eTime, request);
				
				
				if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)) {
					resCode = "01";
					resMsg = "als-successfully!";
				} else {
					resCode = "02";
					resMsg = gpGuarantee.getXmlOutput();
				}
				
				if(!Constants.GPGuarantee.CANCEL_ISSUE.equals(gpGuarantee.getIssueType())){
					responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
	                System.out.println("responseMsg : " + responseMsg);
	                //keep log return result to EBXML
	                logEBXML.setXmlOutput(responseMsg);
	                keepLogOutputEBXML(logEBXML);
	                System.out.print("----- [Call URL EBXML] -----");
	                callURLEBXMLWithRetry(responseMsg, gpGuarantee, null, logEBXML);
				}	
			}
			
		}catch (Exception e) {
			// ---: handle exception
			resCode = "99";
			resMsg = "Exception -->" + e.getMessage();
		}
		
		if(!requestor.toLowerCase().equals("resend")) {
			//----------- response --------------
			response.setContentType("text/xml");
			OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
	        out.write(genXMLResponse(resCode, resMsg, gpGuarantee));
	        out.close();
		}
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	/*protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resCode = "";
		String resMsg = "";
		//------- Add by Tana L. @11042016
		//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
		int gpId = 0;
		
		LogEBXML logEBXML = null;
		GPGuaranteeALSOffline gpGuaranteeALSOffline = null;
		
		LogEBMXLService logEBXMLService = null;
		GPGuaranteeService gpGuaranteeService = null;
		GPGuarantee gpGuarantee = new GPGuarantee();
		try{
			
			logEBXMLService = new LogEBMXLService();
			gpGuaranteeService = new GPGuaranteeService();
			
			//---------- get request & keep log  ---------
			logEBXML = logEBXMLService.readInputGPGuaranteeXMLFile(request, true, false);
			long id = logEBXMLService.getIdByTxRef(logEBXML.getCustomsRefNo());
			logEBXML.setId(id);
			if( logEBXML == null ){
				logger.error("@process Request : Data not found…");
                throw new Exception("Data not found");
			}
			
			System.out.println("logEBXML : Customs Ref No.: " + logEBXML.getCustomsRefNo());
			System.out.println("logEBXML : Issue Type : " + logEBXML.getIssueType());
			System.out.println("logEBXML : ID: " + logEBXML.getId());
			System.out.println("logEBXML : Lg No.: " + logEBXML.getLgNo());
			
			//-------- Process ------
			gpGuaranteeALSOffline = gpGuaranteeService.processEguaranteeBatch(logEBXML, request);
			
			//System.out.println("eGuaranteeALSOffline.getMessageCode() : " + eGuaranteeALSOffline.getMessageCode());
			if(gpGuaranteeALSOffline != null && "0001".equals(gpGuaranteeALSOffline.getResCode())){
				resCode = "01";
				resMsg = "als-successfully!";
				
				//R58060012 : edit by bussara.b @20150616
				//R58060012 : edit by siwat.n @20150625
				if(Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())
						|| Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(logEBXML.getIssueType())){
					System.out.println("Batch Request is Setup and ALS approved : straight through send response to WSG");
					//get object from xml  
					gpGuarantee = gpGuaranteeService.ebxmlToObject(logEBXML.getXmlInput(), null);
					if(Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())){
						gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);	
					}else{
						gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
					}
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
					gpGuarantee.setMsgCode(null);
					gpGuarantee.setXmlOutput(null);
					gpGuarantee.setAccountNo(gpGuaranteeALSOffline.getAccountNo());
					gpGuarantee.setLgNo(gpGuaranteeALSOffline.getLgNo());
					gpGuarantee.setProjName(gpGuaranteeALSOffline.getProjName());
					gpGuarantee.setProjOwnName(gpGuaranteeALSOffline.getProjOwnName());
					gpGuarantee.setGuaranteeAmt(gpGuaranteeALSOffline.getGuaranteeAmt());
					gpGuarantee.setIssueType(logEBXML.getIssueType());
					System.out.println("gpGuarantee LG NO. : " + gpGuarantee.getLgNo());
					System.out.println("gpGuarantee Issue Type : " + gpGuarantee.getIssueType());
					gpGuaranteeService.updateStatusBatch(gpGuarantee, true);
					ETimeService eTimeService = new ETimeService(); 
					ETime eTime = eTimeService.findETime(Constants.ALS_SYSTEM_NAME);
					gpGuarantee.setProcessDate(eTime.getProcessDate());
					
					//------- Add by Tana L. @11042016
					//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
					gpId = gpGuaranteeService.getIdByLgNo(gpGuarantee);
					gpGuarantee.setId(gpId);
					
					gpGuarantee = gpGuaranteeService.setGPGuaranteeForResponse(gpGuarantee);
					responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
					System.out.println("Batch responseMsg : " + responseMsg);
					//keep log return result to EBXML
					logEBXML.setXmlOutput(responseMsg);
					System.out.println("Batch logEBXML ID: " + String.valueOf(logEBXML.getId()));
					keepLogOutputEBXML(logEBXML);

					//call URL EBXML
					System.out.println("Batch call URL EBXML");
					//callURLEBXML(responseMsg, gpGuarantee, null, logEBXML);
					callURLEBXMLWithRetry(responseMsg, gpGuarantee, null, logEBXML); //update by Malinee T. UR58100048 @20160104
					
					// call function to send mail to MQ
					System.out.println("Batch send mail to MQ");
					gpGuaranteeService = new GPGuaranteeService();
					//gpGuaranteeService.sendMailtoMQ(gpGuarantee, request);
					//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
					//send mail in case of success
					System.out.println("=========== process success - send mail to customer ============");
					if(Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())){
						gpGuaranteeService.sendMailtoMQ(gpGuarantee, request);
					}else if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())){
						gpGuaranteeService.sendExtendMailtoMQ(gpGuarantee, request);
					}
				}else{
					System.out.println("Batch Request is Cancel or Claim and ALS approved : not send response to WSG (already sent response in online mode)");
				}
			}else{
				resCode = "02";
				resMsg = gpGuaranteeALSOffline.getResMsg();
				
				//R58060012 : edit by bussara.b @20150616
				if(Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())){
					System.out.println("Batch Request is Setup and ALS rejected : update status in gp_guarantee");
					gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					gpGuarantee.setMsgCode(gpGuaranteeALSOffline.getResCode());
					gpGuarantee.setXmlOutput(gpGuaranteeALSOffline.getResMsg());
					gpGuarantee.setLgNo(gpGuaranteeALSOffline.getLgNo());
					gpGuarantee.setIssueType(logEBXML.getIssueType());
					System.out.println("gpGuarantee LG NO. : " + gpGuarantee.getLgNo() + " will be set to blank");
					System.out.println("gpGuarantee Issue Type : " + gpGuarantee.getIssueType());
					//clear LG No.
					gpGuaranteeService.updateStatusBatch(gpGuarantee, false);
				}else if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(logEBXML.getIssueType())){
					System.out.println("Batch Request is Extend and ALS rejected : update status in gp_guarantee");
					gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					gpGuarantee.setMsgCode(gpGuaranteeALSOffline.getResCode());
					gpGuarantee.setXmlOutput(gpGuaranteeALSOffline.getResMsg());
					gpGuarantee.setLgNo(gpGuaranteeALSOffline.getLgNo());
					gpGuarantee.setIssueType(logEBXML.getIssueType());
					System.out.println("gpGuarantee LG NO. : " + gpGuarantee.getLgNo() + " will be set to blank");
					System.out.println("gpGuarantee Issue Type : " + gpGuarantee.getIssueType());
					//do not clear LG No.
					gpGuaranteeService.updateStatusBatch(gpGuarantee, true);
				}else{
					System.out.println("Batch Request is Cancel or Claim and ALS rejected : not send response to WSG (already sent response in online mode)");
				}
			}
			
			
			
		}catch(EGuaranteeMQMessageException ex){
			logger.error("Error process GPguarantee Batch : "+ex.getMessage());
			// ---: handle exception
			resCode = "99";
			resMsg = "MQException -->" + ex.getMessage();
		}catch (Exception e) {
			// ---: handle exception
			resCode = "99";
			resMsg = "Exception -->" + e.getMessage();
		}
		
		//----------- response --------------
		response.setContentType("text/xml");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        out.write(genXMLResponse(resCode, resMsg, gpGuaranteeALSOffline));
        out.close();
	}*/
	
	//changed from
	/*
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resCode = "";
		String resMsg = "";
		
		//------- Add by Tana L. @11042016
		//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
		int gpId = 0;
		
		LogEBXML logEBXML = null;
		GPGuaranteeALSOffline gpGuaranteeALSOffline = null;
		
		LogEBMXLService logEBXMLService = null;
		GPGuaranteeService gpGuaranteeService = null;
		GPGuarantee gpGuarantee = new GPGuarantee();
		try{
			
			logEBXMLService = new LogEBMXLService();
			gpGuaranteeService = new GPGuaranteeService(); 
			
			//---------- get request & keep log  ---------
			logEBXML = logEBXMLService.readInputGPGuaranteeXMLFile(request, true, false);
			long id = logEBXMLService.getIdByTxRef(logEBXML.getCustomsRefNo());
			logEBXML.setId(id);
			if( logEBXML == null ){
				logger.error("@process Request : Data not found…");
                throw new Exception("Data not found");
			}
			
			System.out.println("logEBXML : Customs Ref No.: " + logEBXML.getCustomsRefNo());
			System.out.println("logEBXML : Issue Type : " + logEBXML.getIssueType());
			System.out.println("logEBXML : ID: " + logEBXML.getId());
			System.out.println("logEBXML : Lg No.: " + logEBXML.getLgNo());
			
			//-------- Process ------
			gpGuaranteeALSOffline = gpGuaranteeService.processEguaranteeBatch(logEBXML, request);
			
			//System.out.println("eGuaranteeALSOffline.getMessageCode() : " + eGuaranteeALSOffline.getMessageCode());
			if(gpGuaranteeALSOffline != null && "0001".equals(gpGuaranteeALSOffline.getResCode())){
				resCode = "01";
				resMsg = "als-successfully!";
				
				//R58060012 : edit by bussara.b @20150616
				//R58060012 : edit by siwat.n @20150625
				if(Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())){
					System.out.println("Batch Request is Setup and ALS approved : straight through send response to WSG");
					//get object from xml  
					gpGuarantee = gpGuaranteeService.ebxmlToObject(logEBXML.getXmlInput(), null);

					gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
					gpGuarantee.setMsgCode(null);
					gpGuarantee.setXmlOutput(null);
					gpGuarantee.setAccountNo(gpGuaranteeALSOffline.getAccountNo());
					gpGuarantee.setLgNo(gpGuaranteeALSOffline.getLgNo());
					gpGuarantee.setIssueType(logEBXML.getIssueType());
					System.out.println("gpGuarantee LG NO. : " + gpGuarantee.getLgNo());
					System.out.println("gpGuarantee Issue Type : " + gpGuarantee.getIssueType());
					gpGuaranteeService.updateStatusBatch(gpGuarantee, true);
					ETimeService eTimeService = new ETimeService(); 
					ETime eTime = eTimeService.findETime(Constants.ALS_SYSTEM_NAME);
					gpGuarantee.setProcessDate(eTime.getProcessDate());
					
					//------- Add by Tana L. @11042016
					//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
					gpId = gpGuaranteeService.getIdByLgNo(gpGuarantee);
					gpGuarantee.setId(gpId);
					
					gpGuarantee = gpGuaranteeService.setGPGuaranteeForResponse(gpGuarantee);
					String responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
					System.out.println("Batch responseMsg : " + responseMsg);
					//keep log return result to EBXML
					logEBXML.setXmlOutput(responseMsg);
					System.out.println("Batch logEBXML ID: " + String.valueOf(logEBXML.getId()));
					keepLogOutputEBXML(logEBXML);

					//call URL EBXML
					System.out.println("Batch call URL EBXML");
					//callURLEBXML(responseMsg, gpGuarantee, null, logEBXML);
					callURLEBXMLWithRetry(responseMsg, gpGuarantee, null, logEBXML); //update by Malinee T. UR58100048 @20160104
					
					// call function to send mail to MQ
					System.out.println("Batch send mail to MQ");
					gpGuaranteeService = new GPGuaranteeService();
					gpGuaranteeService.sendMailtoMQ(gpGuarantee, request);
				}else{
					System.out.println("Batch Request is Cancel or Claim and ALS approved : not send response to WSG (already sent response in online mode)");
				}
			}else{
				resCode = "02";
				resMsg = gpGuaranteeALSOffline.getResMsg();
				
				//R58060012 : edit by bussara.b @20150616
				if(Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())){
					System.out.println("Batch Request is Setup and ALS rejected : update status in gp_guarantee");
					gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
					gpGuarantee.setMsgCode(gpGuaranteeALSOffline.getResCode());
					gpGuarantee.setXmlOutput(gpGuaranteeALSOffline.getResMsg());
					gpGuarantee.setLgNo(gpGuaranteeALSOffline.getLgNo());
					gpGuarantee.setIssueType(logEBXML.getIssueType());
					System.out.println("gpGuarantee LG NO. : " + gpGuarantee.getLgNo() + " will be set to blank");
					System.out.println("gpGuarantee Issue Type : " + gpGuarantee.getIssueType());
					gpGuaranteeService.updateStatusBatch(gpGuarantee, false);
				}else{
					System.out.println("Batch Request is Cancel or Claim and ALS rejected : not send response to WSG (already sent response in online mode)");
				}
			}
			
		}catch(EGuaranteeMQMessageException ex){
			logger.error("Error process GPguarantee Batch : "+ex.getMessage());
			// ---: handle exception
			resCode = "99";
			resMsg = "MQException -->" + ex.getMessage();
		}catch (Exception e) {
			// ---: handle exception
			resCode = "99";
			resMsg = "Exception -->" + e.getMessage();
		}
		
		//----------- response --------------
		response.setContentType("text/xml");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        out.write(genXMLResponse(resCode, resMsg, gpGuaranteeALSOffline));
        out.close();
	}
	*/
	
	private void keepLogOutputEBXML(LogEBXML logEBXML) throws Exception{
		try{
			LogEBMXLService logEBXMLService = new LogEBMXLService();
        	logEBXMLService.updateLogEBXMLXmlOutput(logEBXML);
        }catch (Exception e) {
			// --: handle exception
        	//e.printStackTrace();
        	throw  new Exception(e.getMessage());
		}
	}
	
	private void keepLogResponseFromEBXML(LogEBXML logEBXML) throws Exception{
		try{
			LogEBMXLService logEBXMLService = new LogEBMXLService();
        	logEBXMLService.updateXmlResponseFromEBXml(logEBXML);
        }catch (Exception e) {
			// --: handle exception
        	//e.printStackTrace();
        	throw  new Exception(e.getMessage());
		}
	}
	
	private boolean callURLEBXML(String responseMsg, GPGuarantee gpGuarantee, RegistrationId registrationId, LogEBXML logEBXML) throws Exception {
		 boolean result = false;
		EBXMLConfig ebxmlConfig = new EBXMLConfig();
		
		String toservlet = ebxmlConfig.getUrlGP();
		
		String xmlFileReturnName = genXMLFileReturnName(gpGuarantee, registrationId);
		System.out.println("file Name : " + xmlFileReturnName);
		
		//-- String to file---
        File f = new File(xmlFileReturnName);
        Writer exportFile = null;

        HttpClient httpclient = new DefaultHttpClient();
        try {

            HttpPost httppost = new HttpPost(toservlet);
 
            exportFile = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");  
            responseMsg = responseMsg.replaceAll("\\r\\n|\\r|\\n", "");
            exportFile.write(responseMsg.trim());
            exportFile.flush();


            FileBody fileBody = new FileBody(f);
 
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", fileBody);
            reqEntity.addPart("filename", new StringBody(xmlFileReturnName, Charset.forName("UTF-8")));
            //System.out.println("eGuarantee.getCustomsRef() : " + eGuarantee.getCustomsRef());
            //reqEntity.addPart("Msgid", new StringBody(eGuarantee.getCustomsRef()));
            
            httppost.setEntity(reqEntity); 
 
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
 
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
            	
            	String responseContent = EntityUtils.toString(response.getEntity());
                System.out.println("Response content : " + responseContent);
                
                //update log
                logEBXML.setXmlResponseFromEbxml(responseContent);
                keepLogResponseFromEBXML(logEBXML);
                
                //add by Malinee T. UR58100048 @20160104
                //determine acknowledge status
                String TAG_RESPCODE = "respCode";
                String TAG_RESPMSG = "respMsg";
                String TAG_TRANXID = "tranxId";
                Date egpAckDtm = Calendar.getInstance(Locale.US).getTime();
                String egpAckCode = XMLUtil.getTagValue(TAG_RESPCODE, responseContent);
                
                gpGuarantee.setEgpAckDtm(egpAckDtm);
                gpGuarantee.setEgpAckCode(egpAckCode);
                gpGuarantee.setEgpAckMsg(XMLUtil.getTagValue(TAG_RESPMSG, responseContent));
                gpGuarantee.setEgpAckTranxId(XMLUtil.getTagValue(TAG_TRANXID, responseContent));
                
                //gpGuarantee.set
                if("0".equals(egpAckCode)){
                    gpGuarantee.setEgpAckStatus("SC");
                    result = true;
                }else{
                    gpGuarantee.setEgpAckStatus("NS");
                    result = false;
                }
                updateGpGuaranteeEgpAcknowledge(gpGuarantee);

            }
            EntityUtils.consume(resEntity);
        }catch (Exception e) {
			// --: handle exception
        	e.printStackTrace();
        	logger.error(e.toString());
        	logger.error(e.getMessage());
        	throw new Exception("Error : Call Back EBXML : " + e.getMessage());
        } finally {
        	if(exportFile != null){
        		try{
        			exportFile.close();
        		}catch (Exception e) {
					// --: handle exception
				}
        	}
            try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        return result;
	}
	
	private String genXMLFileReturnName(GPGuarantee gpGuarantee, RegistrationId registrationId){
		
		StringBuilder str = new StringBuilder();
		str.append(gpGuarantee.getProjNo());
		str.append("_");
		str.append(gpGuarantee.getTxRef());
		str.append(".xml");
		System.out.println("genXMLFileReturnName : " + str.toString());
		
		
		return str.toString();
		
	}
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	private String genXMLResponse(String resCode, String resMsg, GPGuarantee gpGuarantee){
		
		String xmlRes = "<gpGuaranteeResponse><txRef>"+gpGuarantee.getTxRef()+"</txRef><AccountNo>"+gpGuarantee.getAccountNo()+"</AccountNo><lgNo>"+gpGuarantee.getLgNo()+"</lgNo><Rescode>"+resCode+"</Rescode><ResMsg>"+resMsg+"</ResMsg></gpGuaranteeResponse>";
		
		return xmlRes;
		
	}
	/*
	private String genXMLResponse(String resCode, String resMsg, GPGuaranteeALSOffline gpGuaranteeALSOffline){
		
		String xmlRes = "<gpGuaranteeResponse><txRef>"+gpGuaranteeALSOffline.getTxRef()+"</txRef><AccountNo>"+gpGuaranteeALSOffline.getAccountNo()+"</AccountNo><lgNo>"+gpGuaranteeALSOffline.getLgNo()+"</lgNo><Rescode>"+resCode+"</Rescode><ResMsg>"+resMsg+"</ResMsg></gpGuaranteeResponse>";
		
		return xmlRes;
		
	}*/
	
	//add by Malinee T. UR58100048 @20160104
	private void updateGpGuaranteeEgpAcknowledge(GPGuarantee gpGuarantee) throws Exception {
		try {
	        GPGuaranteeService service = new GPGuaranteeService();
	        service.updateGpGuaranteeEgpAcknowledge(gpGuarantee);
	    } catch (Exception e) {
	        throw new Exception(e.getMessage());
	    }
	}
	//add by Malinee T. UR58100048 @20160104
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
	//add by Malinee T. UR58100048 @20160104
	private void callURLEBXMLWithRetry(String responseMsg, GPGuarantee gpGuarantee, RegistrationId registrationId, LogEBXML logEBXML) throws Exception {
	    boolean egpResult = false;
	    ConfigTable configTable = new ConfigTable();
	    int delayTime = configTable.getConfigIntNoConn("DELAY_TIME");
	    int retryTime = configTable.getConfigIntNoConn("RETRY_TIME");
	    egpResult = callURLEBXML(responseMsg, gpGuarantee, registrationId, logEBXML);
	    if(!egpResult){
	        if(retryTime > 0){
            	//UR58120031 Phase 2
                //for(int i = 1; i <= 3; i++){
            	for(int i = 1; i <= retryTime; i++){
	                delay(delayTime);
	                boolean retryResult = callURLEBXML(responseMsg, gpGuarantee, registrationId, logEBXML);
	                if(retryResult){
	                    break;
	                }
	            }
	        }
	    }
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// --- Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -- Auto-generated method stub
		processRequest(request, response);
	}
}
