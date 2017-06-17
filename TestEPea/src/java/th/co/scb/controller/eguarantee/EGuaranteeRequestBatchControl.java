package th.co.scb.controller.eguarantee;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.model.LogEBXML;
import th.co.scb.model.eguarantee.EGuaranteeALSOffline;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.service.eguarantee.EGuaranteeService;

/**
 * Servlet implementation class EGuaranteeRequestBatchControl
 */
//@WebServlet("/EGuaranteeRequestBatchControl")
public class EGuaranteeRequestBatchControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EGuaranteeRequestBatchControl.class);
 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		 
		String resCode = "";
		String resMsg = "";
		
		LogEBXML logEBXML = null;
		EGuaranteeALSOffline eGuaranteeALSOffline = null;
		
		LogEBMXLService logEBXMLService = null;
		EGuaranteeService eGuaranteeService = null;
		//EGuaranteeALSOfflineService eGuaranteeALSOfflineService =null;
		
		try{
			
			logEBXMLService = new LogEBMXLService();
			eGuaranteeService = new EGuaranteeService(); 
			//eGuaranteeALSOfflineService = new EGuaranteeALSOfflineService();
			
			//---------- get request & keep log  ---------
			logEBXML = logEBXMLService.readInputXMLFile(request, true, false);
			if( logEBXML == null ){
				logger.error("@process Request : Data not found…");
                throw new Exception("Data not found");
			}
			
			//-------- Process ------
			eGuaranteeALSOffline = eGuaranteeService.processEguaranteeBatch(logEBXML, request);
			
			//System.out.println("eGuaranteeALSOffline.getMessageCode() : " + eGuaranteeALSOffline.getMessageCode());
			if(eGuaranteeALSOffline != null && "0001".equals(eGuaranteeALSOffline.getMessageCode())){
				resCode = "01";
				resMsg = "als-successfully!";
			}else{
				resCode = "02";
				resMsg = eGuaranteeALSOffline.getMessageDescript();
			}
			
		}catch (Exception e) {
			// ---: handle exception
			resCode = "99";
			resMsg = "Exception -->" + e.getMessage();
		}
		
		//----------- response --------------
		response.setContentType("text/xml");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        out.write(genXMLResponse(resCode, resMsg, eGuaranteeALSOffline));
        out.close();
		
	}
	
	
	private String genXMLResponse(String resCode, String resMsg, EGuaranteeALSOffline eGuaranteeALSOffline){
		
		String xmlRes = "<eGuaranteeResponse><CustomRefNo>"+eGuaranteeALSOffline.getCustomsRef()+"</CustomRefNo><AccountNo>"+eGuaranteeALSOffline.getDebtorBankAccNo()+"</AccountNo><BankGuaranteeNo>"+eGuaranteeALSOffline.getBankGuaranteeNo()+"</BankGuaranteeNo><Rescode>"+resCode+"</Rescode><ResMsg>"+resMsg+"</ResMsg></eGuaranteeResponse>";
		
		return xmlRes;
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -- Auto-generated method stub
		processRequest(request, response);
	}

}
