package th.co.scb.controller.eguarantee;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.FileWriter;
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

import th.co.scb.model.ETime;
import th.co.scb.model.LogEBXML;
import th.co.scb.model.RegistrationId;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.service.DocRunningService;
import th.co.scb.service.EBXMLConfig;
import th.co.scb.service.ETimeService;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.service.RegistrationIdService;
import th.co.scb.service.eguarantee.EGuaranteeService;
//import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;

/**
 * 
 * Servlet implementation class EGuaranteeRequestControl
 * 
 */
//@WebServlet("/EGuaranteeRequestControl")
public class EGuaranteeRequestControl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EGuaranteeRequestControl.class);
	//private LogEBMXLService logEBXMLService = new LogEBMXLService();
	
	//private static final boolean IS_PRODUCTION = false;
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resCode = "";
		String resMsg = "";
		String responseMsg = "";
		
		LogEBXML logEBXML = null;
		EGuarantee eguarantee = null;
		
		ETimeService eTimeService = null;
		EGuaranteeService eGuaranteeService = null;
		LogEBMXLService logEBXMLService = null;
		DocRunningService docRunningService = null;
		RegistrationIdService registrationIdService = null;
		System.out.println("=== START EGuaranteeRequestControl.processRequest ===== ");
		try{
			
			eTimeService = new ETimeService();
			eGuaranteeService = new EGuaranteeService();
			logEBXMLService = new LogEBMXLService();
			docRunningService = new DocRunningService();
			registrationIdService = new RegistrationIdService();
			
			ETime eTime = eTimeService.isTimeOffHostALS();
			RegistrationId registrationId = registrationIdService.findRegistrationId();
			
			//---------- get request & keep log  ---------
			logEBXML = logEBXMLService.readInputXMLFile(request, eTime.isTimeOffHostALS(), true);
			if( logEBXML == null ){
				logger.error("@process Request : Data not foundÖ");
                throw new Exception("Data not found");
			}
			
			//-------- Process ------
			eguarantee = eGuaranteeService.processEguaranteeWebService(logEBXML, eTime, request);
			System.out.println("egua : " + eguarantee);
			
			//---- response ---------- 
			//gen transaction no.
			eguarantee.setBankTransactionNo(docRunningService.genBankTransactionNo(eTime));
			System.out.println(">>>>> eguarantee.getXmlOutput() = "+eguarantee.getXmlOutput());
			//insert into e_guarantee
			eGuaranteeService.insertEGuarantee(eguarantee);
			//responseMsg = eGuaranteeService.objectToEbxmlReturn(eguarantee, request);
			
			responseMsg = eGuaranteeService.objectToEbxmlReturnCustoms(eguarantee, request, registrationId);
			//System.out.println("xml new response from xsd's customs : " + tmpNewResponseMsg);
			
			//keep log return result to EBXML
			logEBXML.setXmlOutput(responseMsg);
			keepLogOutputEBXML(logEBXML);
			
			//System.out.println("xml response : " + responseMsg);
			//System.out.println("file Name : " + eguarantee.getDocReturnName()+"_"+eguarantee.getCustomsRef()+".xml");
			
			//call URL EBXML
			callURLEBXML(responseMsg, eguarantee, registrationId);

			resCode = "01";
			resMsg = eguarantee.getPaymentMethodDesc()+" is Processed!";

		}catch (Exception e) {
			// ---: handle exception
			resCode = "99";
			resMsg = "Exception -->" + e.getMessage();
			logger.error("Exception -->" + e.getMessage());
		}
		
		//----------- response --------------
		response.setContentType("text/xml");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        out.write(genXMLResponse(resCode, resMsg));
        out.close();
	}
	
	private String genXMLResponse(String resCode, String resMsg){
		
		String xmlRes = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><eGuaranteeResponse><Rescode>"+resCode+"</Rescode><ResMsg>"+resMsg+"</ResMsg></eGuaranteeResponse>";
		
		return xmlRes;
		
	}
	
	private String genXMLFileReturnName(EGuarantee eGuarantee, RegistrationId registrationId){
		
		StringBuilder str = new StringBuilder();
		str.append(eGuarantee.getPaymentMethod())
			.append("054_Action")
			.append(DateUtil.getDateTimeFileReturn())//current datetime
			.append("-");
		
		//√À— °√¡»ÿ≈°“°√
		str.append(registrationId.getCustomsRegId());
		
		
		str.append("_")
			.append(eGuarantee.getCustomsRef())//Customs Reference
			.append("_");
		
		//√À— ¢Õß‰∑¬æ“≥‘™¬Ï
		str.append(registrationId.getBankRegId());
		str.append(".xml");
		
		return str.toString();
		
	}
	
	private void callURLEBXML(String responseMsg, EGuarantee eGuarantee, RegistrationId registrationId) throws Exception {
		EBXMLConfig ebxmlConfig = new EBXMLConfig();
		
		//String toservlet = "http://10.248.3.115:8080/ebXMLeGuarantee/ReceiveResponseServlet";
		String toservlet = ebxmlConfig.getUrl();//+"&Msgid="+eGuarantee.getCustomsRef();
		
		// -- Auto-generated method stub
		//String yourFile = "e_guarantee_deposit_response.xml";
		//String yourFile = eGuarantee.getDocReturnName()+"_"+eGuarantee.getCustomsRef()+".xml";
		String xmlFileReturnName = genXMLFileReturnName(eGuarantee, registrationId);
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
            	
                System.out.println("Response content : " + EntityUtils.toString(response.getEntity()));

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
	}
	
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
