/**
 * 
 */
package th.co.scb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.LogEBXMLTable;
import th.co.scb.model.LogEBXML;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.XMLUtil;

/**
 * @author s51486
 *
 */
public class LogEBMXLService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogEBMXLService.class);
	
	public LogEBXML insertLogEBXML(LogEBXML logEBXML) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			logEBXML = logEBXMLTable.add(logEBXML);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Insert log EBXML : "+ex.getMessage());
			//System.out.println("\n " + DateUtil.getDateTimeX() + " -->:" + "Insert log EBXML Error : "+ex.getMessage()+"....\n");
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return logEBXML;
	}
	
	public void updateLogEBXMLXmlOutput(LogEBXML logEBXML) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			logEBXMLTable.updateXmlOutput(logEBXML);
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Update log EBXML (XML_OUTPUT) : "+ex.getMessage());
			//System.out.println("\n " + DateUtil.getDateTimeX() + " -->:" + "Update log EBXML Error : "+ex.getMessage()+"....\n");
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
	}
	
	public void updateXmlResponseToEBXml(LogEBXML logEBXML) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			logEBXMLTable.updateXmlResponseToEBXml(logEBXML);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Update log EBXML (XML_RESPONSE_TO_EBXML) : "+ex.getMessage());
			//System.out.println("\n " + DateUtil.getDateTimeX() + " -->:" + "Update log EBXML Error : "+ex.getMessage()+"....\n");
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
	}
	
	public void updateXmlResponseFromEBXml(LogEBXML logEBXML) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			logEBXMLTable.updateXmlResponseFromEBXml(logEBXML);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Update log EBXML (XML_RESPONSE_FROM_EBXML) : "+ex.getMessage());
			//System.out.println("\n " + DateUtil.getDateTimeX() + " -->:" + "Update log EBXML Error : "+ex.getMessage()+"....\n");
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
	}

	public LogEBXML readInputXMLFile(HttpServletRequest request, boolean isTimeOffHostALS, boolean flagInsertLogEbxml) throws Exception{

		return readInputXMLFile(request, isTimeOffHostALS, flagInsertLogEbxml, "");
		
	}
	
	public LogEBXML readInputGPGuaranteeXMLFile(HttpServletRequest request, boolean isTimeOffHostALS, boolean flagInsertLogEbxml) throws Exception{

		return readInputXMLFile(request, isTimeOffHostALS, flagInsertLogEbxml, Constants.GP_SYSTEM_NAME);
		
	}
	
	public LogEBXML readInputXMLFile(HttpServletRequest request, boolean isTimeOffHostALS, boolean flagInsertLogEbxml, String flagCust) throws Exception{
		
		LogEBXML logEBXML = new LogEBXML();
		//----------- Reading a request InputStream ---------------
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        String inputStr = "";
        String fileName = "";

        try {
        	//1. Receive Parameters from URL
            //POST file
            // get access to file that is uploaded from client
            Part p1 = request.getPart("file");
            InputStream inputStream = p1.getInputStream();
 
            // read filename which is sent as a part
            Part p2  = request.getPart("filename");
            Scanner s = new Scanner(p2.getInputStream());
            fileName = s.nextLine();    // read filename from stream
            
            //InputStream inputStream = request.getInputStream();

            if (inputStream.available()>0) {
            //if (inputStream != null) {
            	
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Constants.ENCODING));
                String b = "";
                
                bufferedReader.read();
                while ((b = bufferedReader.readLine()) != null) {
                    stringBuilder.append(b.trim());
                }
                
                
            } else {
                //stringBuilder.append("");
                logger.error("@readInputXMLFile : File not found…");
                throw new IOException("File not found");
            }
            
            inputStr = stringBuilder.toString();
//            System.out.println("inputStr : " + inputStr);
            
            if(inputStr != null && inputStr.length() > 0){
            	
            	if(inputStr.charAt(0) != '<'){
            		inputStr = "<"+ inputStr;
                }
            	
    	        String[] str = inputStr.split(";;;;;");
    	        String xmlStr = str[0];
    	        String type = "";
    	        String lgNo = "";
    	        
    	        String transactionNo = "";
    	        
    	        try{
    	        	type = str[1];
    	        }catch (Exception e) {
    				// --: handle exception
    	        	type = "web";
    			}
    	        
    	        try{
    	        	lgNo = str[2];
    	        }catch (Exception e) {   
    				// --: handle exception
    	        	lgNo = "";
    			}
    	        
    	        //R58060012 : edit by siwat.n @20150629
    	        try{
    	        	transactionNo = str[3];
    	        }catch (Exception e) {   
    				// --: handle exception
    	        	transactionNo = "";
    			}
    	        
    	        System.out.println("type : " + type);
    	        System.out.println("xmlStr : " + xmlStr);
    	        System.out.println("lgNo : " + lgNo);
    	        System.out.println("transactionNo : " + transactionNo);
    	        System.out.println("flagCust : " + flagCust);
    	        
    	        logEBXML.setType(type);
    	        logEBXML.setXmlInput(xmlStr);
    	        logEBXML.setLgNo(lgNo);
    	        logEBXML.setFileName(fileName);
    	
    	        String customsRefNo = "";
    	        if(flagCust.equals(Constants.GP_SYSTEM_NAME)){//Government Procurement
    	        	customsRefNo = XMLUtil.getTagValue("TxRef", xmlStr);
    	        	logEBXML.setFlagCust(Constants.GP_SYSTEM_NAME);
    	        	String transDate = XMLUtil.getTagValue("Dtm", xmlStr);
    	        	logEBXML.setTransDate(transDate);
    	        	logEBXML.setIssueType(getIssueType(xmlStr));
    	        	
    	        }else{
    	        	customsRefNo = XMLUtil.getTagValue("MsgId", xmlStr);
    	        }
    	        
    	        //R58060012 : edit by siwat.n @20150629
    	        logEBXML.setCustomsRefNo(customsRefNo);
    	        if(flagCust.equals(Constants.GP_SYSTEM_NAME)){
    	        	System.out.println("flagCust is LGGP");
    	        	if("batch".equals(type)){
    	        		System.out.println("run type is batch");
    	        		System.out.println("set customs_ref_no = tx_ref [" + transactionNo + "]");
    	        		logEBXML.setCustomsRefNo(transactionNo);
    	        	}else{
    	        		System.out.println("run type is not batch");
    	        	}
    	        }else{
    	        	System.out.println("flagCust is not LGGP");
    	        }
    	        
    	        if(isTimeOffHostALS == true){
            		logEBXML.setAlsOnline(Constants.StatusALSOnline.NO);
            	}else{
            		logEBXML.setAlsOnline(Constants.StatusALSOnline.YES);
            	}
    	        
    	        
    	        //-- keep log ebxml
    	        if(flagInsertLogEbxml == true){
    	        	insertLogEBXML(logEBXML);
    	        }
    	        
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
        	logger.error("@Read Input XMLFile : "+ex.getMessage()+"…");
        	throw  new Exception(ex.getMessage());
        	
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                	logger.error("@Read Input XMLFile : Error closing bufferedReader…");
                	throw  new IOException(ex.getMessage());
                }
            }
        }


		return logEBXML;
	}
	
	
	private String getIssueType(String xmlStr){
		
		String issueType = "";
		
		String tagValue = XMLUtil.getTagValue(Constants.GPGuarantee.SETUP_ISSUE_TAG_ROOT_RQ, xmlStr);
		if(tagValue != null && tagValue.length() > 0){
			System.out.println("getIssueType case 1: SETUP_ISSUE");
			issueType = Constants.GPGuarantee.SETUP_ISSUE;
		}else{
			tagValue = XMLUtil.getTagValue(Constants.GPGuarantee.CANCEL_ISSUE_TAG_ROOT_RQ, xmlStr);
			if(tagValue != null && tagValue.length() > 0){
				System.out.println("getIssueType case 2: CANCEL_ISSUE");
				issueType = Constants.GPGuarantee.CANCEL_ISSUE;
			}else{
				//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
				//changed from
				//System.out.println("getIssueType case 3: CLAIM_ISSUE");
				//issueType = Constants.GPGuarantee.CLAIM_ISSUE;
				//changed to
				tagValue = XMLUtil.getTagValue(Constants.GPGuarantee.CLAIM_ISSUE_TAG_ROOT_RQ, xmlStr);
				if(tagValue != null && tagValue.length() > 0){
					System.out.println("getIssueType case 3: CLAIM_ISSUE");
					issueType = Constants.GPGuarantee.CLAIM_ISSUE;
				}else{
					System.out.println("getIssueType case 4: EXTEND_EXPIRY_DATE_ISSUE");
					issueType = Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE;
				}
			}
		}
		
		return issueType;
		
	}
	
	public long getIdByTxRef(String txRef) throws Exception{
		ConnectDB connectDB = null;
		try{
			connectDB = new ConnectDB();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			long id = logEBXMLTable.getIdByTxtRef(txRef);
			return id;
		}catch(Exception ex) {
			logger.error("Error Select log EBXML (id) : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
	}

	//add by pariwat.s @22062015
	public LogEBXML getXmlInputByTxtRef(String txtref) throws Exception {
		
		ConnectDB connectDB = null;
		LogEBXML result = new LogEBXML();
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        String inputStr = "";
        String fileName = "";
        
		try {
			
			connectDB = new ConnectDB();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			long id = logEBXMLTable.getIdByTxtRef(txtref);
			InputStream inputStream = logEBXMLTable.getXmlInputByTxtRef(txtref);
			if(inputStream != null){
	            if (inputStream.available()>0) {
	                	
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Constants.ENCODING));
					String b = "";
					
					bufferedReader.read();
					while ((b = bufferedReader.readLine()) != null) {
					    stringBuilder.append(b.trim());
					}
	                    
					inputStr = stringBuilder.toString();
	                  
	                  if(inputStr != null && inputStr.length() > 0){
	                  	
	                  	if(inputStr.charAt(0) != '<'){
	                  		inputStr = "<"+ inputStr;
	                  	}
	                  	
	          	        String[] str = inputStr.split(";;;;;");
	          	        String xmlStr = str[0];
	          	        String type = "";
	          	        String lgNo = "";
	          	        
	          	        try{
	          	        	type = str[1];
	          	        }catch (Exception e) {
	          				// --: handle exception
	          	        	type = "web";
	          			}
	          	        
	          	        try{
	          	        	lgNo = str[2];
	          	        }catch (Exception e) {   
	          				// --: handle exception
	          	        	lgNo = "";
	          			}
	          	        
	          	        result.setId(id);
	          	        result.setType(type);
	          	        result.setXmlInput(xmlStr);
	          	        result.setLgNo(lgNo);
	          	        result.setFileName("");
	          	
	          	        String customsRefNo = "";
          	        	customsRefNo = XMLUtil.getTagValue("TxRef", xmlStr);
          	        	result.setFlagCust(Constants.GP_SYSTEM_NAME);
          	        	String transDate = XMLUtil.getTagValue("Dtm", xmlStr);
          	        	result.setTransDate(transDate);
          	        	result.setIssueType(getIssueType(xmlStr));
          	        	
          	        	result.setCustomsRefNo(customsRefNo);
          	        	result.setAlsOnline(Constants.StatusALSOnline.YES);
	          	        
	                  }
	                    
	                    
	                } else {
	                    logger.error("getXmlInputByTxtRef : File not found…");
	                    throw new IOException("File not found");
	                }
			}
			
			return result;
			
		}
		catch(Exception ex) {
			logger.error("Error Select log EBXML (XML_INPUT) : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }
		finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
		
	}
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//get logEBXML with the correct sequence
	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	public LogEBXML getXmlInputByTxtRefAndSeq(String txtref, int seq) throws Exception {
		ConnectDB connectDB = null;
		LogEBXML result = new LogEBXML();
		try {
			connectDB = new ConnectDB();
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			long id = logEBXMLTable.getIdByTxtRefAndSeq(txtref, seq);
			InputStream inputStream = logEBXMLTable.getXmlInputByTxtRefAndSeq(txtref, seq);
			result = setLogFromInputStream(inputStream, id);
			return result;
		}
		catch(Exception ex) {
			logger.error("Error Select log EBXML (XML_INPUT) : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }
		finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
	}
	
	//UR59040034 Add eGP Pending Review & Resend Response function
    //get logEBXML with the correct sequence
    //tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
    private LogEBXML setLogFromInputStream(InputStream inputStream, long id) throws Exception, IOException{
        LogEBXML result = new LogEBXML();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        String inputStr = "";
        String fileName = "";
        if(inputStream != null){
            if (inputStream.available()>0) {
                   
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Constants.ENCODING));
                String b = "";
               
                bufferedReader.read();
                while ((b = bufferedReader.readLine()) != null) {
                    stringBuilder.append(b.trim());
                }
                   
                inputStr = stringBuilder.toString();
                 
                  if(inputStr != null && inputStr.length() > 0){
                     
                      if(inputStr.charAt(0) != '<'){
                          inputStr = "<"+ inputStr;
                      }
                     
                    String[] str = inputStr.split(";;;;;");
                    String xmlStr = str[0];
                    String type = "";
                    String lgNo = "";
                   
                    try{
                        type = str[1];
                    }catch (Exception e) {
                        type = "web";
                    }
                    try{
                        lgNo = str[2];
                    }catch (Exception e) {
                        lgNo = "";
                    }
                   
                    result.setId(id);
                    result.setType(type);
                    result.setXmlInput(xmlStr);
                    result.setLgNo(lgNo);
                    result.setFileName("");
               
                    String customsRefNo = "";
                    customsRefNo = XMLUtil.getTagValue("TxRef", xmlStr);
                    result.setFlagCust(Constants.GP_SYSTEM_NAME);
                    String transDate = XMLUtil.getTagValue("Dtm", xmlStr);
                    result.setTransDate(transDate);
                    result.setIssueType(getIssueType(xmlStr));
                   
                    result.setCustomsRefNo(customsRefNo);
                    result.setAlsOnline(Constants.StatusALSOnline.YES);
                   
                  }
                   
                   
                } else {
                    logger.error("getXmlInputByTxtRef : File not found…");
                    throw new IOException("File not found");
                }
        }
       
        return result;
    }
    
    
   //UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
  	public LogEBXML getLogEBXMLByTxRefAndSeq(String txRef, int seq) throws Exception {
		ConnectDB connectDB = null;
		LogEBXML result = new LogEBXML();
		try{
			connectDB = new ConnectDB();
			
			LogEBXMLTable logEBXMLTable = new LogEBXMLTable(connectDB);
			result = logEBXMLTable.getLogEBXMLByTxRefAndSeq(txRef,seq);
			return result;
		}catch(Exception ex) {
			logger.error("Error getLogEBXMLByTxRefAndSeq : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
        }finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
	}
}

