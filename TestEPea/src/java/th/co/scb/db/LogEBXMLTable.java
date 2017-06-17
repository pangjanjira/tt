/**
 * 
 */
package th.co.scb.db;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.model.LogEBXML;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class LogEBXMLTable {
	
	private ConnectDB connectDB;
	
	private static final Logger logger = LoggerFactory.getLogger(LogEBXMLTable.class);

    public LogEBXMLTable(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }
    
    public LogEBXML add(LogEBXML logEBXML){
    	StringBuilder sql = new StringBuilder();
    	//UR59040034 Add eGP Pending Review & Resend Response function
    	Date dtm = Calendar.getInstance(Locale.US).getTime();
    	sql.append(" insert into ").append(Constants.TableName.LOG_EBXML).append("(customs_ref_no, xml_input, input_datetime, als_online, file_name, flag_cust) ");	
    	//sql.append(" values(?,?,now(), ?,?,?) ");
    	sql.append(" values(?,?,?, ?,?,?) ");
    	
    	//System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString()
    				, logEBXML.getCustomsRefNo()
    				, logEBXML.getXmlInput().getBytes()
    				, dtm
    				, logEBXML.getAlsOnline()
    				, logEBXML.getFileName()
    				, logEBXML.getFlagCust());
    	
    	logEBXML.setId(id);
    	
    	return logEBXML;
    	
    }
    
    public void updateXmlOutput(LogEBXML logEBXM){
    	//UR59040034 Add eGP Pending Review & Resend Response function
    	Date dtm = Calendar.getInstance(Locale.US).getTime();
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.LOG_EBXML);
    	sql.append(" set xml_output = ?, ");
    	//sql.append("   output_datetime = now() ");
    	sql.append("   output_datetime = ? ");
    	sql.append(" where id = ? ");
    	
    	//System.out.println("sql : " + sql.toString());
    	
    	int updatedRows = connectDB.execute(sql.toString()
    			, logEBXM.getXmlOutput().getBytes()
    			, dtm
    			, logEBXM.getId());
    	System.out.println("updateXmlOutput " + String.valueOf(updatedRows) + " rows updated.");

    }
    
    public void updateXmlResponseToEBXml(LogEBXML logEBXM){
    	//UR59040034 Add eGP Pending Review & Resend Response function
    	Date dtm = Calendar.getInstance(Locale.US).getTime();
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.LOG_EBXML);
    	sql.append(" set xml_resp_to_ebxml = ?, ");
    	//sql.append("   resp_to_eb_datetime = now() ");
    	sql.append("   resp_to_eb_datetime = ? ");
    	sql.append(" where id = ? ");
    	
    	//System.out.println("sql : " + sql.toString());
    	
    	connectDB.execute(sql.toString()
    			, logEBXM.getXmlResponseToEbxml().getBytes()
    			, dtm
    			, logEBXM.getId());

    }
    
    
    public void updateXmlResponseFromEBXml(LogEBXML logEBXM){
    	//UR59040034 Add eGP Pending Review & Resend Response function
    	Date dtm = Calendar.getInstance(Locale.US).getTime();
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.LOG_EBXML);
    	sql.append(" set xml_resp_from_ebxml = ?, ");
    	//sql.append("   resp_from_eb_datetime = now() ");
    	sql.append("   resp_from_eb_datetime = ? ");
    	sql.append(" where id = ? ");
    	connectDB.execute(sql.toString()
    			, logEBXM.getXmlResponseFromEbxml().getBytes()
    			, dtm
    			, logEBXM.getId());

    }
    
    //add by pariwat.s @22062015
    public InputStream getXmlInputByTxtRef(String txtref) {
    	InputStream result = null;
    	try{
	    	StringBuilder sql = new StringBuilder();
	    	sql.append(" select xml_input from ").append(Constants.TableName.LOG_EBXML);
	    	sql.append(" where customs_ref_no = ? ");	
	    	byte[] xmlByte = connectDB.querySingleBlob(sql.toString(), txtref);  	
	    	result = new ByteArrayInputStream(xmlByte);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	   	return result;
    }
    
    public long getIdByTxtRef(String txtref) {
    	long output = 0;
    	try{
	    	StringBuilder sql = new StringBuilder();
	    	sql.append(" select id from ").append(Constants.TableName.LOG_EBXML);
	    	sql.append(" where customs_ref_no = ? ");
	    	Map<String, Object> result = connectDB.querySingle(sql.toString(), txtref);  	
	    	output = (Long)result.get("id");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	   	return output;
    }
    
    //UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
      public InputStream getXmlInputByTxtRefAndSeq(String txtref, int seq) {
      	InputStream result = null;
      	try{
      		int limit = seq - 1;
  	    	StringBuilder sql = new StringBuilder();
  	    	sql.append(" select xml_input from ").append(Constants.TableName.LOG_EBXML);
  	    	sql.append(" where customs_ref_no = ? limit ").append(String.valueOf(limit)).append(",1 ");
  	    	System.out.println("getXmlInputByTxtRefAndSeq: " + sql.toString());
  	    	byte[] xmlByte = connectDB.querySingleBlob(sql.toString(), txtref);
  	    	if(xmlByte != null){
  	    		result = new ByteArrayInputStream(xmlByte);
  	    	}
      	}catch(Exception e){
      		e.printStackTrace();
      	}
  	   	return result;
      }
      
  	//UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
      public long getIdByTxtRefAndSeq(String txtref, int seq) {
      	long output = 0;
      	try{
      		int limit = seq - 1;
  	    	StringBuilder sql = new StringBuilder();
  	    	sql.append(" select id from ").append(Constants.TableName.LOG_EBXML);
  	    	sql.append(" where customs_ref_no = ? limit ").append(String.valueOf(limit)).append(",1 ");
  	    	System.out.println("getIdByTxtRefAndSeq: " + sql.toString());
  	    	Map<String, Object> result = connectDB.querySingle(sql.toString(), txtref);
  	    	if(result != null){
  	    		output = (Long)result.get("id");
  	    	}
      	}catch(Exception e){
      		e.printStackTrace();
      	}
  	   	return output;
      }
      
    //UR59040034 Add eGP Pending Review & Resend Response function
	//get logEBXML with the correct sequence
	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	    public LogEBXML getLogEBXMLByTxRefAndSeq(String txRef, int seq) {
	    	LogEBXML output = null;
	    	
	    	try{
	    		int limit = seq - 1;
		        StringBuilder gpSql = new StringBuilder();
		        gpSql.append("SELECT xml_input ");
		        gpSql.append(" , IFNULL(DATE_FORMAT(input_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS input_datetime ");
		        gpSql.append(" , xml_output ");
		        gpSql.append(" , IFNULL(DATE_FORMAT(output_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS output_datetime ");
		        gpSql.append(" , (CASE als_online WHEN 1 THEN 'Online' ELSE 'Offline' END) AS als_online ");
		        gpSql.append(" , xml_resp_to_ebxml ");
		        gpSql.append(" , IFNULL(DATE_FORMAT(resp_to_eb_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS resp_to_eb_datetime ");
		        gpSql.append(" , xml_resp_from_ebxml ");
		        gpSql.append(" , IFNULL(DATE_FORMAT(resp_from_eb_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS resp_from_eb_datetime ");
		        gpSql.append(" , customs_ref_no, IFNULL(flag_cust, '') AS flag_cust ");
		        gpSql.append(" FROM log_ebxml WHERE customs_ref_no = ? limit ").append(String.valueOf(limit)).append(",1 ");
		        ResultSet result = connectDB.queryRsSingle(gpSql.toString(), txRef);
		        
		        if(result != null){
		        	if (result.next()) {
			        	output = new LogEBXML();
			        	output.setCustomsRefNo(result.getString("customs_ref_no"));
			        	output.setFlagCust(result.getString("flag_cust"));
			        	output.setAlsOnline(result.getString("als_online"));
			        	output.setXmlInput(blobToString(result.getBlob("xml_input")));
			        	System.out.println("XMLInput : "+result.getBlob("xml_input"));
			        	System.out.println("XMLInput : "+output.getXmlInput());
			        	output.setInputDatetimeStr(result.getString("input_datetime"));
			        	output.setXmlOutput(blobToString(result.getBlob("xml_output")));
			        	output.setOutputDatetimeStr(result.getString("output_datetime"));
			        	output.setXmlResponseToEbxml(blobToString(result.getBlob("xml_resp_to_ebxml")));
			        	output.setResponseToEbxmlDatetimeStr(result.getString("resp_to_eb_datetime"));
			        	output.setXmlResponseFromEbxml(blobToString(result.getBlob("xml_resp_from_ebxml")));
			        	output.setResponseFromEbxmlDatetimeStr(result.getString("resp_from_eb_datetime"));
		                result.close();
		            }
		        }
		        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    	return output;
	    }
	    
		public String blobToString(Blob input){
			String output = "";
		try{
			if(input != null){
				StringBuilder stringBuilder = new StringBuilder();
				BufferedReader bufferedReader = null;
		    	byte[] inputByte = blobToByteArray(input);
		    	InputStream inputStream = new ByteArrayInputStream(inputByte);
		    	if(inputStream != null){
		    		if (inputStream.available()>0) {
		    			bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Constants.ENCODING));
		    			String b = "";
		        			bufferedReader.read();
		        			while ((b = bufferedReader.readLine()) != null) {
		        				stringBuilder.append(b.trim());
		        			}
		        			output = stringBuilder.toString();
		        		}
		        	}
		        	
		    	}	
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
			return output;
		}
	        
		public byte[] blobToByteArray(Blob input){
			byte[] result = null;
			try{
				result = input.getBytes(1, (int)input.length());	
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
}
