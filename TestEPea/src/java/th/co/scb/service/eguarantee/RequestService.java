/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.service.eguarantee;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sun.swing.StringUIClientPropertyKey;
import th.co.scb.model.eguarantee.ApprovalLog;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.LogEBXML;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;

import java.sql.ResultSet;
import java.sql.Blob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import th.co.scb.controller.eguarantee.ReportConfig;
import th.co.scb.model.eguarantee.ReviewLog;
import th.co.scb.util.ExcelUtils;
import th.co.scb.util.StringUtil;
////UR58120031 EEDReviewLog and EEDApprovalLog add by Tana L. @12022016
import th.co.scb.model.eguarantee.EEDReviewLog;
import th.co.scb.model.eguarantee.EEDApprovalLog;
/**
 *
 * @author s49099
 */

public class RequestService {
    
    private ConnectDB connectDB;
    private StringBuilder gpGuaranteeSql;
    private ReportConfig reportConfig;
	
    public RequestService() {
    	
        reportConfig = new ReportConfig();
            gpGuaranteeSql = new StringBuilder();
            //gpGuaranteeSql.append(" select ");
            //UR59040034 Add eGP Pending Review & Resend Response function
        	//get logEBXML with the correct sequence
        	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
            gpGuaranteeSql.append(" select @rownum:=@rownum+1 AS row_no ");
            gpGuaranteeSql.append(" , gp.id, gp.issue_type, gp.issue_type, gp.tx_ref, DATE_FORMAT(STR_TO_DATE(gp.dtm,'%d/%m/%Y %H:%i:%s'), '%Y-%m-%d %H:%i:%s') dtm, gp.proj_no, gp.dept_code, gp.account_no ");
            gpGuaranteeSql.append(" , gp.vendor_tax_id, gp.vendor_name, gp.comp_id, gp.user_id, gp.seq_no ");
            gpGuaranteeSql.append(" , gp.consider_desc, gp.consider_money, gp.guarantee_amt, gp.amt_req ");
            gpGuaranteeSql.append(" , gp.contract_no, gp.contract_date, gp.guarantee_price, gp.guarantee_percent, gp.advance_guarantee_price, gp.advance_payment ");
            gpGuaranteeSql.append(" , gp.works_guarantee_price, gp.works_guarantee_percent, gp.collection_phase, gp.end_date, gp.start_date ");
            gpGuaranteeSql.append(" , gp.bond_type, gp.proj_name, gp.proj_amt, gp.proj_own_name, gp.cost_center, gp.cost_center_name ");
            gpGuaranteeSql.append(" , gp.document_no, gp.document_date, gp.expire_date, gp.extend_date "); //UR58120031 expire_date add by Tana L. @09022016
            gpGuaranteeSql.append(" , gp.status_lg, gp.appv_amt, gp.appv_date, gp.lg_no, gp.status, gp.msg_code ");
            //gpGuaranteeSql.append(" , gp.als_online, gp.process_date, gp.xml_output, gp.occode, gp.approve_status, gp.approve_dtm, gp.approve_by, gp.approve_reason ");
            gpGuaranteeSql.append(" , gp.als_online, gp.process_date, gp.xml_output, gp.occode, gp.approve_status, IFNULL(DATE_FORMAT(gp.approve_dtm, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') as approve_dtm, gp.approve_by, gp.approve_reason ");
            gpGuaranteeSql.append(" , b.bank_code, b.bank_addr, b.branch_code, b.branch_name ");
            gpGuaranteeSql.append(" , IFNULL(ca.msg_code, ' ') AS ca_msg_code ");
            gpGuaranteeSql.append(" , IFNULL(ca.msg_description, ' ') AS ca_msg_description ");
            gpGuaranteeSql.append(" , gp.review_status, gp.review_reason, gp.review_by, gp.review_dtm ");
            gpGuaranteeSql.append(" , gp.approve_status, gp.approve_reason, gp.approve_by, gp.approve_dtm ");
            gpGuaranteeSql.append(" , als.branch "); // Update by Narong : 20150824
            
            //add by Malinee T. UR58100048 @20151224
            gpGuaranteeSql.append(" , IFNULL(DATE_FORMAT(gp.egp_ack_dtm, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') as egp_ack_dtm ");
            gpGuaranteeSql.append(" , gp.egp_ack_status, egp_ack_tranx_id, egp_ack_code, egp_ack_msg ");
            
            //UR59040034 Add eGP Pending Review & Resend Response function
            gpGuaranteeSql.append(" , gp.resend_count ");
            
            gpGuaranteeSql.append(" from ").append(Constants.TableName.GP_GUARANTEE).append(" gp ");
            //UR59040034 Add eGP Pending Review & Resend Response function
            
            //UR58120031 Phase 2 (fix duplicate display)
            //gpGuaranteeSql.append(" LEFT JOIN ").append(Constants.TableName.ACCOUNT_ALS).append(" als ON als.account_no=gp.account_no and als.active_flag='Y' "); // Update by Narong : 20150824
            gpGuaranteeSql.append(" LEFT JOIN ").append(Constants.TableName.ACCOUNT_ALS).append(" als ON als.account_no=gp.account_no and als.purpose='09' and als.active_flag='Y' ");
            gpGuaranteeSql.append(" LEFT JOIN ").append(Constants.TableName.CONTROL_ACCOUNT).append(" ca ON ca.account_no=gp.account_no and ca.active_flag='Y' ");
            gpGuaranteeSql.append(" , (SELECT * FROM ").append(Constants.TableName.BANK_INFO).append(" ORDER BY id DESC LIMIT 1) b ");
            gpGuaranteeSql.append(" , (SELECT @rownum:=0) r ");
            gpGuaranteeSql.append(" WHERE 1=1 ");
    }
    
    public LogEBXML getLogEBXMLFromTxRef(String txRef, ConnectDB connectDB){
    	LogEBXML output = null;
    	
    	try{
	        StringBuilder gpSql = new StringBuilder();
	        //gpSql.append(" SELECT IFNULL(CAST(xml_input AS CHAR(100000) CHARACTER SET utf8), '') AS xml_input ");
	        gpSql.append("SELECT xml_input ");
	        gpSql.append(" , IFNULL(DATE_FORMAT(input_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS input_datetime ");
	        //gpSql.append(" , IFNULL(CAST(xml_output AS CHAR(100000) CHARACTER SET utf8), '') AS xml_output ");
	        gpSql.append(" , xml_output ");
	        gpSql.append(" , IFNULL(DATE_FORMAT(output_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS output_datetime ");
	        gpSql.append(" , (CASE als_online WHEN 1 THEN 'Online' ELSE 'Offline' END) AS als_online ");
	        //gpSql.append(" , IFNULL(CAST(xml_resp_to_ebxml AS CHAR(100000) CHARACTER SET utf8), '') AS xml_resp_to_ebxml ");
	        gpSql.append(" , xml_resp_to_ebxml ");
	        gpSql.append(" , IFNULL(DATE_FORMAT(resp_to_eb_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS resp_to_eb_datetime ");
	        //gpSql.append(" , IFNULL(CAST(xml_resp_from_ebxml AS CHAR(100000) CHARACTER SET utf8), '') AS xml_resp_from_ebxml ");
	        gpSql.append(" , xml_resp_from_ebxml ");
	        gpSql.append(" , IFNULL(DATE_FORMAT(resp_from_eb_datetime, '%Y-%m-%d %H:%i:%s'), '1900-01-01 00:00:00') AS resp_from_eb_datetime ");
	        gpSql.append(" , customs_ref_no, IFNULL(flag_cust, '') AS flag_cust ");
	        gpSql.append(" FROM log_ebxml WHERE customs_ref_no = ? ");
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
    
  //UR58120031 getParentId add by Tana L. @18022016
    public String getParentId(String id) {
    	String parentId = "";
    	try{
	    	this.connectDB = new ConnectDB();
	    	Map<String, Object> result;
	        StringBuilder gpSql = new StringBuilder();
	        gpSql.append(" select id from gp_guarantee ");
	        gpSql.append(" where issue_type = '0004' ");
	        gpSql.append(" and lg_no IN ( ");
	        gpSql.append(" select lg_no from gp_guarantee ");
	        gpSql.append(" where id = ? )");
	        result = connectDB.querySingle(gpSql.toString(), id);
	        parentId = result.get("id") != null ? result.get("id").toString().trim() : "Not Found";
    	}catch(Exception e){
    		e.printStackTrace();
        } finally {
        	if (this.connectDB != null) {
        		this.connectDB.close();
        	}     
    	}
	    return parentId;
    }
    
    //isDupApproval add by Tana L. @18042016
    //UR58120031 Phase 2 (protect appprove from multiple screen)
    public boolean isDupApproval(String id) {
    	boolean isDup = false;
    	try{
	    	this.connectDB = new ConnectDB();
	    	Map<String, Object> result;
	        StringBuilder gpSql = new StringBuilder();
	        gpSql.append(" select count(*) as counter from gp_guarantee ");
	        gpSql.append(" where id = ? ");
	        gpSql.append(" and ((approve_status='AP' and status_lg='01') ");
	        gpSql.append(" or (approve_status='RJ') ");
	        gpSql.append(" or (status_lg='00')) ");
	        result = connectDB.querySingle(gpSql.toString(), id);
	        if(result != null){
	       		if((Long)result.get("counter") > 0){
	       			isDup = true;
	       		}
	       	}
    	}catch(Exception e){
    		e.printStackTrace();
        } finally {
        	if (this.connectDB != null) {
        		this.connectDB.close();
        	}     
    	}
	    return isDup;
    }
    
    public List<Object> getMoniorDetail(String id){
        List<Object> objList = new ArrayList<Object>();
    	try{
	    	this.connectDB = new ConnectDB();
	    	//UR59040034 Add eGP Pending Review & Resend Response function
	    	//get logEBXML with the correct sequence
	    	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	    	int gpId = Integer.parseInt(id);
	    	GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
	    	GPGuarantee gPGuarantee = new GPGuarantee();
	    	GPGuarantee initGp = gpGuaranteeService.getGPGuaranteeById(gpId);
	    	LogEBMXLService logEBXMLService = new LogEBMXLService();
	    	
	    	List<Map<String, Object>> resultList;
	        StringBuilder gpSql = new StringBuilder();
	        gpSql.append(gpGuaranteeSql.toString());
	        //gpSql.append(" AND gp.id= ? ");
	        gpSql.append(" AND gp.tx_ref = ? ");
	        
	        System.out.println("gpSql : " + gpSql.toString());
			resultList = connectDB.queryList(gpSql.toString(), initGp.getTxRef());
			if(resultList != null){
				for(Map<String, Object> result : resultList){
					int oId = (Integer)result.get("id");
					System.out.println("gp_guarantee.id = " + String.valueOf(oId) + " - input.id= " + String.valueOf(id));
					if(oId == gpId){
						gPGuarantee = row2GPGuarantee(result);
						System.out.println("gp_guarantee.tx_ref = " + gPGuarantee.getTxRef());
						System.out.println("gp_guarantee.seq = " + String.valueOf(gPGuarantee.getSeq()));
					}
				}
			}
	        //GPGuarantee gPGuarantee = row2GPGuarantee(connectDB.querySingle(gpSql.toString(), id));
			
			
			//UR59040034 Add eGP Pending Review & Resend Response function
			//Get latest extend expiry date of gpGuarantee 
			if (gPGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)) {
				StringBuilder extendSql = new StringBuilder();
				extendSql.append(" SELECT MAX(gp.id) AS id FROM gp_guarantee gp ");
				extendSql.append(" WHERE gp.issue_type = '0007' ");
				extendSql.append(" AND gp.approve_status='AP' ");
				extendSql.append(" AND gp.status_lg='07' ");
				extendSql.append(" AND gp.status='SC' ");
				extendSql.append(" AND gp.lg_no = ? ");
				Map<String, Object> result = connectDB.querySingle(extendSql.toString(),gPGuarantee.getLgNo());
				if(result != null) {
					System.out.println("Latest ID : "+result.get("id"));
					if(result.get("id") != null) {
						if((Integer)result.get("id") == gPGuarantee.getId()) {
							gPGuarantee.setLatestExtend(true);
						}
					}
				}
			}
	        
	        StringBuilder logSql = new StringBuilder();
	        logSql.append(" select gp_guarantee_id, approve_status, account_no, DATE_FORMAT(approve_dtm, '%Y-%m-%d %H:%i:%s') approve_dtm, approve_by, approve_reason from ").append(Constants.TableName.GP_APPROVAL_LOG);
	        logSql.append(" where 1=1 ");
	        logSql.append(" and gp_guarantee_id= ? ");
	        logSql.append(" ORDER BY approve_dtm desc ");
	        List<Map<String, Object>> result;
	        result = connectDB.queryList(logSql.toString(), id);
	        
	        List<ApprovalLog> logList = new ArrayList<ApprovalLog>();
	        for(Map<String, Object> row : result){
	        	
	        	String approveStatus = row.get("approve_status") != null ? row.get("approve_status").toString().trim() : "";
	        	String approveStatusStr = "";
	        	if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_PENDING)){
	        		approveStatusStr = "Pending Approve";
	            } else if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_APPROVED)){
	            	approveStatusStr = "Approved";
	            } else if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_REJECTED)){
	            	approveStatusStr = "Rejected";
	            } else {
	            	approveStatusStr = approveStatus;
	            }      
	        	
	            ApprovalLog appLog = new ApprovalLog((row.get("gp_guarantee_id") != null ? (Integer)row.get("gp_guarantee_id") : 0),
	                                                 approveStatusStr,
	                                                 (row.get("account_no") != null ? row.get("account_no").toString().trim() : ""),
	                                                 (row.get("approve_dtm") != null ? row.get("approve_dtm").toString().trim() : ""),
	                                                 (row.get("approve_by") != null ? row.get("approve_by").toString().trim() : ""),
	                                                 (row.get("approve_reason") != null ? row.get("approve_reason").toString().trim() : ""));
	            logList.add(appLog);
	        }
	        
            StringBuilder reviewLogSql = new StringBuilder();
            reviewLogSql.append(" select gp_guarantee_id, review_status, account_no, DATE_FORMAT(review_dtm, '%Y-%m-%d %H:%i:%s') review_dtm, review_by, review_reason from ").append(Constants.TableName.GP_REVIEW_LOG);
            reviewLogSql.append(" where 1=1 ");
            reviewLogSql.append(" and gp_guarantee_id= ? ");
            reviewLogSql.append(" ORDER BY review_dtm desc ");
            result = connectDB.queryList(reviewLogSql.toString(), id);

            List<ReviewLog> reviewLogList = new ArrayList<ReviewLog>();
            for (Map<String, Object> row : result) {

                String reviewStatus = row.get("review_status") != null ? row.get("review_status").toString().trim() : "";
                String reviewStatusStr = "";
                if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_PENDING)) {
                    reviewStatusStr = "Pending For Review";
                } else if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_APPROVED)) {
                    reviewStatusStr = "Sent approver to approve";
                } else if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_REJECTED)) {
                    reviewStatusStr = "Sent approver to reject";
                } else {
                    reviewStatusStr = reviewStatus;
                }

                ReviewLog reviewLog = new ReviewLog((row.get("gp_guarantee_id") != null ? (Integer) row.get("gp_guarantee_id") : 0),
                        reviewStatusStr,
                        (row.get("account_no") != null ? row.get("account_no").toString().trim() : ""),
                        (row.get("review_dtm") != null ? row.get("review_dtm").toString().trim() : ""),
                        (row.get("review_by") != null ? row.get("review_by").toString().trim() : ""),
                        (row.get("review_reason") != null ? row.get("review_reason").toString().trim() : ""));
                reviewLogList.add(reviewLog);
            }

	        //LogEBXML logEBXML = this.getLogEBXMLFromTxRef(gPGuarantee.getTxRef(), this.connectDB);
            //UR59040034 Add eGP Pending Review & Resend Response function
	    	//get logEBXML with the correct sequence
	    	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	        LogEBXML logEBXML = logEBXMLService.getLogEBXMLByTxRefAndSeq(gPGuarantee.getTxRef(), gPGuarantee.getSeq());
                
                StringBuilder accountSql = new StringBuilder();
	        accountSql.append(" select account_no from ").append(Constants.TableName.ACCOUNT_ALS);
	        accountSql.append(" where 1=1 and active_flag='Y' ");
	        accountSql.append(" and purpose = ? ");
	        accountSql.append(" and tax_id = ? ");
	        accountSql.append(" ORDER BY account_no");
	        result = connectDB.queryList(accountSql.toString(), "09", gPGuarantee.getVendorTaxId());
                List<String> accountList = new ArrayList<String>();
	        for(Map<String, Object> row : result){
                    accountList.add(row.get("account_no") != null ? row.get("account_no").toString().trim() : "");
                }
                
	        objList.add(gPGuarantee);
	        objList.add(logList);
	        objList.add(logEBXML);
	        objList.add(accountList);
            objList.add(reviewLogList);
            
            //UR58120031 eedLog Condition add by Tana L. @15022016
            List<EEDReviewLog> eedReviewLogList = new ArrayList<EEDReviewLog>();
            List<EEDApprovalLog> eedApprovalLogList = new ArrayList<EEDApprovalLog>();
            
            if(gPGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
            	
            	//UR58120031 eedReviewLog add by Tana L. @12022016
                StringBuilder eedReviewLogSql = new StringBuilder();
                eedReviewLogSql.append(" select gp_guarantee_id, review_status, account_no, DATE_FORMAT(gp_review_log.review_dtm, '%Y-%m-%d %H:%i:%s')review_dtm, review_by, review_reason, old_end_date, new_end_date from ").append(Constants.TableName.GP_REVIEW_LOG);
                eedReviewLogSql.append(" where gp_guarantee_id IN ( ");
                eedReviewLogSql.append(" select id from gp_guarantee ");
                eedReviewLogSql.append(" where issue_type = '0007' ");
                eedReviewLogSql.append(" and status = 'SC' ");
                eedReviewLogSql.append(" and lg_no IN ( ");
                eedReviewLogSql.append(" select lg_no from gp_guarantee ");
                eedReviewLogSql.append(" where id = ? )) ");
                eedReviewLogSql.append(" ORDER BY review_dtm desc ");
                System.out.println(eedReviewLogSql);
                result = connectDB.queryList(eedReviewLogSql.toString(), id);

                
                for (Map<String, Object> row : result) {

                    String eedReviewStatus = row.get("review_status") != null ? row.get("review_status").toString().trim() : "";
                    String eedReviewStatusStr = "";
                    if (eedReviewStatus.equals(Constants.GPGuarantee.REVIEW_PENDING)) {
                    	eedReviewStatusStr = "Pending For Review";
                    } else if (eedReviewStatus.equals(Constants.GPGuarantee.REVIEW_APPROVED)) {
                    	eedReviewStatusStr = "Sent approver to approve";
                    } else if (eedReviewStatus.equals(Constants.GPGuarantee.REVIEW_REJECTED)) {
                    	eedReviewStatusStr = "Sent approver to reject";
                    } else {
                    	eedReviewStatusStr = eedReviewStatus;
                    }

                    EEDReviewLog eedReviewLog = new EEDReviewLog((row.get("gp_guarantee_id") != null ? (Integer) row.get("gp_guarantee_id") : 0),
                    		eedReviewStatusStr,
                            (row.get("account_no") != null ? row.get("account_no").toString().trim() : ""),
                            (row.get("review_dtm") != null ? row.get("review_dtm").toString().trim() : ""),
                            (row.get("review_by") != null ? row.get("review_by").toString().trim() : ""),
                            (row.get("review_reason") != null ? row.get("review_reason").toString().trim() : ""),
                            (row.get("old_end_date") != null ? row.get("old_end_date").toString().trim() : ""),
                            (row.get("new_end_date") != null ? row.get("new_end_date").toString().trim() : ""));
                    eedReviewLogList.add(eedReviewLog);
                }
                
              //UR58120031 eedApprovalLog add by Tana L. @12022016
                StringBuilder eedApprovalLogSql = new StringBuilder();
                eedApprovalLogSql.append(" select gp_guarantee_id, approve_status, account_no, DATE_FORMAT(gp_approval_log.approve_dtm, '%Y-%m-%d %H:%i:%s')approve_dtm, approve_by, approve_reason, old_end_date, new_end_date from ").append(Constants.TableName.GP_APPROVAL_LOG);
                eedApprovalLogSql.append(" where gp_guarantee_id IN ( ");
                eedApprovalLogSql.append(" select id from gp_guarantee ");
                eedApprovalLogSql.append(" where issue_type = '0007' ");
                eedApprovalLogSql.append(" and status = 'SC' ");
                eedApprovalLogSql.append(" and lg_no IN ( ");
                eedApprovalLogSql.append(" select lg_no from gp_guarantee ");
                eedApprovalLogSql.append(" where id = ? )) ");
                eedApprovalLogSql.append(" ORDER BY approve_dtm desc ");
                result = connectDB.queryList(eedApprovalLogSql.toString(), id);

                for (Map<String, Object> row : result) {

                    String eedApprovalStatus = row.get("approve_status") != null ? row.get("approve_status").toString().trim() : "";
                    String eedApprovalStatusStr = "";
                    if (eedApprovalStatus.equals(Constants.GPGuarantee.APPROVAL_PENDING)) {
                    	eedApprovalStatusStr = "Pending Approve";
                    } else if (eedApprovalStatus.equals(Constants.GPGuarantee.APPROVAL_APPROVED)) {
                    	eedApprovalStatusStr = "Approved";
                    } else if (eedApprovalStatus.equals(Constants.GPGuarantee.APPROVAL_REJECTED)) {
                    	eedApprovalStatusStr = "Rejected";
                    } else {
                    	eedApprovalStatusStr = eedApprovalStatus;
                    }

                    EEDApprovalLog eedApprovalLog = new EEDApprovalLog((row.get("gp_guarantee_id") != null ? (Integer) row.get("gp_guarantee_id") : 0),
                    		eedApprovalStatusStr,
                            (row.get("account_no") != null ? row.get("account_no").toString().trim() : ""),
                            (row.get("approve_dtm") != null ? row.get("approve_dtm").toString().trim() : ""),
                            (row.get("approve_by") != null ? row.get("approve_by").toString().trim() : ""),
                            (row.get("approve_reason") != null ? row.get("approve_reason").toString().trim() : ""),
                            (row.get("old_end_date") != null ? row.get("old_end_date").toString().trim() : ""),
                            (row.get("new_end_date") != null ? row.get("new_end_date").toString().trim() : ""));
                    eedApprovalLogList.add(eedApprovalLog);
                }
                
            }
            objList.add(eedReviewLogList);
            objList.add(eedApprovalLogList);
	        
    	}catch(Exception e){
    		e.printStackTrace();
        } finally {
        	if (this.connectDB != null) {
        		this.connectDB.close();
        	}     
    	}
        return objList;
    }
    
    private String getSqlDateString(String displayDate){
		String str_dd = displayDate.substring(0, 2);
		String str_mm = displayDate.substring(3, 5);
		String str_yy = displayDate.substring(6);
		String newDateStr = str_yy + "-"+ str_mm + "-"+ str_dd;
		return newDateStr;
    }
    
    //update by Malinee T. UR58100048 @20151224
    //UR58120031 requestType Parameter add by Tana L. @15022016
    //public List<GPGuarantee> inquiryReview(String taxId, String accountNo, String expireDateFrom, String expireDateTo) {
    public List<GPGuarantee> inquiryReview(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String egpStatus, String requestType) {
        List<GPGuarantee> list = new ArrayList<GPGuarantee>();
        try {
            this.connectDB = new ConnectDB();
            StringBuilder sql = new StringBuilder();
            sql.append(gpGuaranteeSql.toString());
            
          //UR58120031 add by Tana L. @15022016
            if (!requestType.isEmpty()) {
            	sql.append(" AND gp.issue_type ='" + requestType + "'  ");
            }
            else {
            	sql.append(" AND gp.issue_type IN ('0004','0007')  ");
            }
            
            //UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
            //changed from
            //sql.append(" AND ((gp.review_status='PR')  ");
            //sql.append(" OR gp.approve_status='AP' ");
            //sql.append(" AND gp.status_lg='03')");
            //changed to
            sql.append(" AND ( ");
            sql.append(" (gp.review_status='PR')  "); //pending review
            sql.append(" OR (gp.approve_status='AP' AND gp.status_lg='03')"); //setup error
            sql.append(" OR (gp.approve_status='AP' AND gp.status_lg='07' AND gp.status='NS')"); //extend error
            sql.append(" ) ");
            //2015-08-15 Add Search By Condition
            if (!taxId.isEmpty()) {
                sql.append(" AND gp.vendor_tax_id ='" + taxId + "'  ");
            }

            if (!accountNo.isEmpty()) {
                sql.append(" AND gp.account_no ='" + accountNo + "'  ");
            }

            String expireDateSqlFrom = "";
            String expireDateSqlTo = "";
            if (!expireDateFrom.isEmpty() && !expireDateTo.isEmpty()) {
                expireDateSqlFrom = getSqlDateString(expireDateFrom);
                expireDateSqlTo = getSqlDateString(expireDateTo);
                sql.append(" AND (gp.expire_date between '" + expireDateSqlFrom + "' and '" + expireDateSqlTo + "') ");
            }
            
            //add by Malinee T. UR58100048 @20151224
            if (!egpStatus.isEmpty()){
                sql.append (" AND gp.egp_ack_status = '").append(egpStatus.trim()).append("'");
            }
            
            
            //add order by
            sql.append(" ORDER BY DATE_FORMAT(STR_TO_DATE(gp.dtm,'%d/%m/%Y %H:%i:%s'), '%Y-%m-%d %H:%i:%s') DESC ");

            System.out.println("SQL inquiryReview: " + sql.toString());

            List<Map<String, Object>> result;
            result = connectDB.queryList(sql.toString());

            for (Map<String, Object> row : result) {
                list.add(row2GPGuarantee(row));
            }
            this.connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (this.connectDB != null) {
                this.connectDB.close();
            }
        }
        return list;
    }
    
  //update by Malinee T. UR58100048 @20151224
  //UR58120031 requestType Parameter add by Tana L. @15022016
    //public List<GPGuarantee> inquiryMonitor(String taxId, String accountNo, String expireDateFrom, String expireDateTo){
    public List<GPGuarantee> inquiryMonitor(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String egpStatus, String requestType){
        List<GPGuarantee> list = new ArrayList<GPGuarantee>();
    	try{
	    	this.connectDB = new ConnectDB();
	        StringBuilder sql = new StringBuilder();
	        sql.append(gpGuaranteeSql.toString());
	      //UR58120031 add by Tana L. @15022016
            if (!requestType.isEmpty()) {
            	sql.append(" AND gp.issue_type ='" + requestType + "'  ");
            }
            else {
            	sql.append(" AND gp.issue_type IN ('0004','0007')  ");
            }
            sql.append(" AND gp.review_status<>'PR' ");
            sql.append(" AND gp.approve_status='PA' ");
            //2015-08-15 Add Search By Condition
            if(!taxId.isEmpty()){
                sql.append(" AND gp.vendor_tax_id ='" + taxId + "'  ");
            }
            
            if(!accountNo.isEmpty()){
                sql.append(" AND gp.account_no ='" + accountNo + "'  ");
            }
            
            String expireDateSqlFrom = "";
            String expireDateSqlTo = "";
            if(!expireDateFrom.isEmpty() && !expireDateTo.isEmpty()){
            	expireDateSqlFrom = getSqlDateString(expireDateFrom);
            	expireDateSqlTo = getSqlDateString(expireDateTo);
            	sql.append(" AND (gp.expire_date between '" + expireDateSqlFrom + "' and '" + expireDateSqlTo + "') ");
            }
          
            //add by Malinee T. UR58100048 @20151224
            if (!egpStatus.isEmpty()){
                sql.append (" AND gp.egp_ack_status = '").append(egpStatus.trim()).append("'");
            }
            
            
	        //add order by
	        sql.append(" ORDER BY DATE_FORMAT(STR_TO_DATE(gp.dtm,'%d/%m/%Y %H:%i:%s'), '%Y-%m-%d %H:%i:%s') DESC ");
	        
	        System.out.println("SQL inquiryMonitor: " + sql.toString());
	               
	        List<Map<String, Object>> result;
	        result = connectDB.queryList(sql.toString());
	        
	        for(Map<String, Object> row : result){            
	            list.add(row2GPGuarantee(row));
	        }
	        this.connectDB.close();
    	}catch(Exception e){
    		e.printStackTrace();
        } finally {
        	if (this.connectDB != null) {
        		this.connectDB.close();
        	}     
    	}
        return list;
    }
    
    public List<GPGuarantee> inquiry(Map<String,String> jsonMap){
        List<GPGuarantee> list = new ArrayList<GPGuarantee>();
    	try{ 	
    		this.connectDB = new ConnectDB();
	        StringBuilder sql = new StringBuilder();
	        sql.append(gpGuaranteeSql.toString());
	        
	        if (!jsonMap.get("txRef").trim().equals("")){
	            sql.append (" AND gp.tx_ref = '").append(jsonMap.get("txRef").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("dtm").trim().equals("")){
	            String startTime = "STR_TO_DATE('"+ jsonMap.get("dtm").trim() +" 00:00:00','%d/%m/%Y %H:%i:%s')";
	            String endTime = "STR_TO_DATE('"+ jsonMap.get("dtm").trim() +" 23:59:59','%d/%m/%Y %H:%i:%s')";
	            sql.append (" AND (STR_TO_DATE(gp.dtm,'%d/%m/%Y %H:%i:%s') BETWEEN ").append(startTime).append(" AND ").append(endTime).append(")");
	        }
	        
	        if (!jsonMap.get("vendorTaxId").trim().equals("")){
	            sql.append (" AND gp.vendor_tax_id = '").append(jsonMap.get("vendorTaxId").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("vendorName").trim().equals("")){
	            sql.append (" AND gp.vendor_name like '").append(jsonMap.get("vendorName").trim()).append("%'");
	        }
	        
	        if (!jsonMap.get("lgNo").trim().equals("")){
	            sql.append (" AND gp.lg_no = '").append(jsonMap.get("lgNo").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("bondType").trim().equals("")){
	            sql.append (" AND gp.bond_type = '").append(jsonMap.get("bondType").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("projNo").trim().equals("")){
	            sql.append (" AND gp.proj_no = '").append(jsonMap.get("projNo").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("requestType").trim().equals("")){
	            sql.append (" AND gp.issue_type = '").append(jsonMap.get("requestType").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("alsStatus").trim().equals("")){
	            sql.append (" AND gp.status_lg = '").append(jsonMap.get("alsStatus").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("approveStatus").trim().equals("")){
	            sql.append (" AND gp.approve_status = '").append(jsonMap.get("approveStatus").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("approveBy").trim().equals("")){
	            sql.append (" AND gp.approve_by = '").append(jsonMap.get("approveBy").trim()).append("'");
	        }
	        
	        if (!jsonMap.get("approveDtmFrom").trim().equals("") && !jsonMap.get("approveDtmTo").trim().equals("")){
	            String startTime = "STR_TO_DATE('"+ jsonMap.get("approveDtmFrom").trim() +" 00:00:00','%d/%m/%Y %H:%i:%s')";
	            String endTime = "STR_TO_DATE('"+ jsonMap.get("approveDtmTo").trim() +" 23:59:59','%d/%m/%Y %H:%i:%s')";
	            sql.append (" AND approve_dtm BETWEEN ").append(startTime).append(" AND ").append(endTime);
	        }
	        
	        if (!jsonMap.get("accountNo").trim().equals("")){
	        	sql.append (" AND gp.account_no = '").append(jsonMap.get("accountNo").trim()).append("'");
	        }
	        
            String expireDateSqlFrom = "";
            String expireDateSqlTo = "";
//            if (!jsonMap.get("expireDateFrom").trim().equals("") && !jsonMap.get("expireDateTo").trim().equals("")) {
//                expireDateSqlFrom = getSqlDateString(jsonMap.get("expireDateFrom").trim());
//                expireDateSqlTo = getSqlDateString(jsonMap.get("expireDateTo").trim());
//                sql.append(" AND (gp.expire_date between '" + expireDateSqlFrom + "' and '" + expireDateSqlTo + "') ");
//            }
            
            //add by Malinee T. UR58100048 @20151224
            if (!jsonMap.get("egpStatus").trim().equals("")){
                sql.append (" AND gp.egp_ack_status = '").append(jsonMap.get("egpStatus").trim()).append("'");
            }
            
	        
	        //add order by
	        sql.append(" ORDER BY DATE_FORMAT(STR_TO_DATE(gp.dtm,'%d/%m/%Y %H:%i:%s'), '%Y-%m-%d %H:%i:%s') DESC ");
	        
	        List<Map<String, Object>> result;
	        result = connectDB.queryList(sql.toString());
	        
	        System.out.println("SQL inquiry: " + sql.toString());

	        
	        for(Map<String, Object> row : result){
	        	String issueType = row.get("issue_type") != null ? row.get("issue_type").toString().trim() : "";
	        	String approveStatus = row.get("approve_status") != null ? row.get("approve_status").toString().trim() : "";
	        	String statusLg = row.get("status_lg") != null ? row.get("status_lg").toString().trim() : "";
                boolean isDisplay = true;
                /*
                boolean isDisplay = false;
                if ("0004".equals(issueType)) {
                    if ("PA".equalsIgnoreCase(approveStatus)) {
                        isDisplay = false;
                    } else {
                        if ("AP".equalsIgnoreCase(approveStatus) && "03".equalsIgnoreCase(statusLg)) {
                            isDisplay = false;
                        } else {
                            isDisplay = true;
                        }
                    }
                } else {
                    isDisplay = true;
                }
                */
	        	if(isDisplay){
	        		list.add(row2GPGuarantee(row));
	        	}
	        }
	        
    	}catch(Exception e){
    		e.printStackTrace();
        } finally {
        	if (this.connectDB != null) {
        		this.connectDB.close();
            }
    	}
        return list;
    }
    
    
    private GPGuarantee row2GPGuarantee (Map<String, Object> row){
        GPGuarantee gpGuarantee = new GPGuarantee();
            
        //Set Integer Value
        gpGuarantee.setId(row.get("id") != null ? (Integer)row.get("id") : 0);
        gpGuarantee.setSeqNo(row.get("seq_no") != null ? (Integer)row.get("seq_no") : 0);
        
        //UR59040034 Add eGP Pending Review & Resend Response function
        gpGuarantee.setResendCount(row.get("resend_count") != null ? (Integer)row.get("resend_count") : 0);
        int seq = 1;
		try{
			Long seqLong = (Long)row.get("row_no");
			seq = seqLong.intValue();
			System.out.println("seq: " + String.valueOf(seq));
		}catch(Exception e){
			//nothing
			System.out.println("set seq error: " + e.getMessage());
		}
		gpGuarantee.setSeq(seq);
		gpGuarantee.setLatestExtend(false);

        //Set Date Value
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String processDateStr = row.get("process_date") != null ? row.get("process_date").toString().trim() : "1900-01-01";
        String approveDtmStr = row.get("approve_dtm") != null ? row.get("approve_dtm").toString().trim() : "1900-01-01 00:00:00";
        try {
            gpGuarantee.setProcessDate(dateFormat.parse(processDateStr));
        } catch (ParseException ex) {
        	//do nothing
        }
        
        try {
            gpGuarantee.setApproveDtm(datetimeFormat.parse(approveDtmStr));
        } catch (ParseException ex) {
        	//do nothing
        }
        gpGuarantee.setApproveDtmStr(approveDtmStr);

        //Set BigDecimal Value
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setParseBigDecimal(true);
        try {
            gpGuarantee.setConsiderMoney((BigDecimal)df.parse(row.get("consider_money") != null ? row.get("consider_money").toString().trim() : "0"));
            gpGuarantee.setGuaranteeAmt((BigDecimal)df.parse(row.get("guarantee_amt") != null ? row.get("guarantee_amt").toString().trim() : "0"));
            gpGuarantee.setAmtReq((BigDecimal)df.parse(row.get("amt_req") != null ? row.get("amt_req").toString().trim() : "0"));

            gpGuarantee.setGuaranteePrice((BigDecimal)df.parse(row.get("guarantee_price") != null ? row.get("guarantee_price").toString().trim() : "0"));
            gpGuarantee.setGuaranteePercent((BigDecimal)df.parse(row.get("guarantee_percent") != null ? row.get("guarantee_percent").toString().trim() : "0"));
            gpGuarantee.setAdvanceGuaranteePrice((BigDecimal)df.parse(row.get("advance_guarantee_price") != null ? row.get("advance_guarantee_price").toString().trim() : "0"));
            gpGuarantee.setAdvancePayment((BigDecimal)df.parse(row.get("advance_payment") != null ? row.get("advance_payment").toString().trim() : "0"));

            gpGuarantee.setWorksGuaranteePrice((BigDecimal)df.parse(row.get("works_guarantee_price") != null ? row.get("works_guarantee_price").toString().trim() : "0"));
            gpGuarantee.setWorksGuaranteePercent((BigDecimal)df.parse(row.get("works_guarantee_percent") != null ? row.get("works_guarantee_percent").toString().trim() : "0"));

            gpGuarantee.setProjAmt((BigDecimal)df.parse(row.get("proj_amt") != null ? row.get("proj_amt").toString().trim() : "0"));
            gpGuarantee.setAppvAmt((BigDecimal)df.parse(row.get("appv_amt") != null ? row.get("appv_amt").toString().trim() : "0"));

        } catch (ParseException ex) {
        }


        //Set String Value            
        gpGuarantee.setIssueType(row.get("issue_type") != null ? row.get("issue_type").toString().trim() : "");

        if (gpGuarantee.getIssueType().trim().equals("0004")) {
            gpGuarantee.setIssueTypeDesc("Setup");
        } else if (gpGuarantee.getIssueType().trim().equals("0005")) {
            gpGuarantee.setIssueTypeDesc("Cancel");
        } else if (gpGuarantee.getIssueType().trim().equals("0006")) {
            gpGuarantee.setIssueTypeDesc("Claim");
        } else if (gpGuarantee.getIssueType().trim().equals("0007")) {
            gpGuarantee.setIssueTypeDesc("Extend Expiry Date"); //UR58120031 IssueTypeDesc=EED add by Tana L. @09022016
        } else {
            gpGuarantee.setIssueTypeDesc(gpGuarantee.getIssueType().trim());
        }

        gpGuarantee.setTxRef(row.get("tx_ref") != null ? row.get("tx_ref").toString().trim() : "");
        gpGuarantee.setDtm(row.get("dtm") != null ? row.get("dtm").toString().trim() : "");
        gpGuarantee.setProjNo(row.get("proj_no") != null ? row.get("proj_no").toString().trim() : "");
        gpGuarantee.setDeptCode(row.get("dept_code") != null ? row.get("dept_code").toString().trim() : "");
        gpGuarantee.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString().trim() : "");
        gpGuarantee.setVendorTaxId(row.get("vendor_tax_id") != null ? row.get("vendor_tax_id").toString().trim() : "");
        gpGuarantee.setVendorName(row.get("vendor_name") != null ? row.get("vendor_name").toString().trim() : "");
        gpGuarantee.setCompId(row.get("comp_id") != null ? row.get("comp_id").toString().trim() : "");
        gpGuarantee.setUserId(row.get("user_id") != null ? row.get("user_id").toString().trim() : "");

        gpGuarantee.setConsiderDesc(row.get("consider_desc") != null ? row.get("consider_desc").toString().trim() : "");  

        gpGuarantee.setContractNo(row.get("contract_no") != null ? row.get("contract_no").toString().trim() : "");
        gpGuarantee.setContractDate(row.get("contract_date") != null ? row.get("contract_date").toString().trim() : "");
        gpGuarantee.setCollectionPhase(row.get("collection_phase") != null ? row.get("collection_phase").toString().trim() : "");

        gpGuarantee.setEndDate(row.get("end_date") != null ? row.get("end_date").toString().trim() : "");
        gpGuarantee.setStartDate(row.get("start_date") != null ? row.get("start_date").toString().trim() : "");

        String bondType = row.get("bond_type") != null ? row.get("bond_type").toString().trim() : "";
        if (bondType.equals("01")) {
            gpGuarantee.setBondType("หลักประกันซอง");
        } else if (bondType.equals("02")) {
            gpGuarantee.setBondType("หลักประกันสัญญา");
        } else if (bondType.equals("03")) {
            gpGuarantee.setBondType("หลักประกันจ่ายเงินล่วงหน้า");
        } else if (bondType.equals("04")) {
            gpGuarantee.setBondType("หลักประกันผลงาน");
        } else if (bondType.equals("05")) {
            gpGuarantee.setBondType("หลักประกันการจ่ายเงินล่วงหน้าก่อนการ");
        } else {
            gpGuarantee.setBondType(bondType);
        }

        gpGuarantee.setProjName(row.get("proj_name") != null ? row.get("proj_name").toString().trim() : "");
        gpGuarantee.setProjOwnName(row.get("proj_own_name") != null ? row.get("proj_own_name").toString().trim() : "");
        gpGuarantee.setCostCenter(row.get("cost_center") != null ? row.get("cost_center").toString().trim() : "");
        gpGuarantee.setCostCenterName(row.get("cost_center_name") != null ? row.get("cost_center_name").toString().trim() : "");
        gpGuarantee.setDocumentNo(row.get("document_no") != null ? row.get("document_no").toString().trim() : "");
        gpGuarantee.setDocumentDate(row.get("document_date") != null ? row.get("document_date").toString().trim() : "");
        gpGuarantee.setExpireDate(row.get("expire_date") != null ? row.get("expire_date").toString().trim() : "");
        gpGuarantee.setExtendDate(row.get("extend_date") != null ? row.get("extend_date").toString().trim() : ""); //UR58120031 setExtendDate add by Tana L. @09022016
        
        gpGuarantee.setStatusLG(row.get("status_lg") != null ? row.get("status_lg").toString().trim() : "");
        if (gpGuarantee.getStatusLG().equals("01")){
            gpGuarantee.setStatusDesc("Approved");
        } else if (gpGuarantee.getStatusLG().equals("03")){
            gpGuarantee.setStatusDesc("Rejected");
        } else if (gpGuarantee.getStatusLG().equals("05")){
            gpGuarantee.setStatusDesc("Claimed");
        } else if (gpGuarantee.getStatusLG().equals("06")){
            gpGuarantee.setStatusDesc("Canceled");
        } else if (gpGuarantee.getStatusLG().equals("07")){
        	//UR58120031 setStatusDesc add by Tana L. @26022016
        	gpGuarantee.setStatusDesc("Extend Expiry Date");
        } else if (gpGuarantee.getStatusLG().equals("00")){
        	gpGuarantee.setStatusDesc("Approving");
        } else {
            gpGuarantee.setStatusDesc(gpGuarantee.getStatusLG());
        }

        gpGuarantee.setAppvDate(row.get("appv_date") != null ? row.get("appv_date").toString().trim() : "");
        gpGuarantee.setLgNo(row.get("lg_no") != null ? row.get("lg_no").toString().trim() : "");
        
        String transactionStatus = row.get("status") != null ? row.get("status").toString().trim() : "";
        if (transactionStatus.equals("SC")) {
        	gpGuarantee.setTransactionStatus("Success");
        } else if (transactionStatus.equals("NS")) {
        	gpGuarantee.setTransactionStatus("Not Success");
        } else if (transactionStatus.equals("CC")) {
        	gpGuarantee.setTransactionStatus("Canceled");
        } else if (transactionStatus.equals("CL")) {
        	gpGuarantee.setTransactionStatus("Claimed");
        } else if (transactionStatus.equals("EE")) {
        	//UR58120031 setStatusDesc add by Tana L. @26022016
        	gpGuarantee.setTransactionStatus("Extend Expiry Date");
        } else {
        	gpGuarantee.setTransactionStatus(transactionStatus);
        }        
        
        gpGuarantee.setMsgCode(row.get("msg_code") != null ? row.get("msg_code").toString().trim() : "");
        gpGuarantee.setAlsOnline(row.get("als_online") != null ? row.get("als_online").toString().trim() : "");

        gpGuarantee.setXmlOutput(row.get("xml_output") != null ? row.get("xml_output").toString().trim() : "");
        gpGuarantee.setOcCode(row.get("occode") != null ? row.get("occode").toString().trim() : "");
        gpGuarantee.setCaMsgCode(row.get("ca_msg_code") != null ? row.get("ca_msg_code").toString().trim() : "");
        gpGuarantee.setCaMsgDescription(row.get("ca_msg_description") != null ? row.get("ca_msg_description").toString().trim() : "");
        
        String reviewStatus = row.get("review_status") != null ? row.get("review_status").toString().trim() : "";
        if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_PENDING)) {
            gpGuarantee.setReviewStatus("Pending Approve");
        } else if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_APPROVED)) {
            gpGuarantee.setReviewStatus("Approved");
        } else if (reviewStatus.equals(Constants.GPGuarantee.REVIEW_REJECTED)) {
            gpGuarantee.setReviewStatus("Rejected");
        } else {
            gpGuarantee.setReviewStatus(reviewStatus);
        }
        gpGuarantee.setReviewBy(row.get("review_by") != null ? row.get("review_by").toString().trim() : "");
        gpGuarantee.setReviewReason(row.get("review_reason") != null ? row.get("review_reason").toString().trim() : "");
        String reviewDtm = row.get("review_dtm") != null ? row.get("review_dtm").toString().trim() : "1900-01-01 00:00:00";
        try {
            gpGuarantee.setReviewDtm(datetimeFormat.parse(reviewDtm));
        } catch (ParseException ex) {
            // do nothing
        }
        String approveStatus = row.get("approve_status") != null ? row.get("approve_status").toString().trim() : "";
        if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_PENDING)){
        	gpGuarantee.setApproveStatus("Pending Approve");
        } else if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_APPROVED)){
        	gpGuarantee.setApproveStatus("Approved");
        } else if(approveStatus.equals(Constants.GPGuarantee.APPROVAL_REJECTED)){
        	gpGuarantee.setApproveStatus("Rejected");
        } else {
        	gpGuarantee.setApproveStatus(approveStatus);
        }
        
        gpGuarantee.setApproveBy(row.get("approve_by") != null ? row.get("approve_by").toString().trim() : ""); 
        gpGuarantee.setApproveReason(row.get("approve_reason") != null ? row.get("approve_reason").toString().trim() : ""); 
        String approveDtm = row.get("approve_dtm") != null ? row.get("approve_dtm").toString().trim() : "1900-01-01 00:00:00";
        try {
            gpGuarantee.setApproveDtm(datetimeFormat.parse(approveDtm));
        } catch (ParseException ex) {
            // do nothing
        }

        gpGuarantee.setBankCode(row.get("bank_code") != null ? row.get("bank_code").toString().trim() : "");
        gpGuarantee.setBankAddr(row.get("bank_addr") != null ? row.get("bank_addr").toString().trim() : ""); 
        gpGuarantee.setBranchCode(row.get("branch_code") != null ? row.get("branch_code").toString().trim() : ""); 
        gpGuarantee.setBranchName(row.get("branch_name") != null ? row.get("branch_name").toString().trim() : ""); 
        
        gpGuarantee.setBranch(row.get("branch") != null ? row.get("branch").toString().trim() : ""); 
        
        //add by Malinee T. UR58100048 @20151224
        gpGuarantee.setEgpAckStatus(row.get("egp_ack_status") != null ? row.get("egp_ack_status").toString().trim() : "");
        String egpAckDtmStr = row.get("egp_ack_dtm") != null ? row.get("egp_ack_dtm").toString().trim() : "1900-01-01 00:00:00";
        gpGuarantee.setEgpAckDtmStr(egpAckDtmStr);
        try {
            gpGuarantee.setEgpAckDtm(dateFormat.parse(egpAckDtmStr));
        } catch (ParseException ex) {
            //do nothing
        }
        gpGuarantee.setEgpAckTranxId(row.get("egp_ack_tranx_id") != null ? row.get("egp_ack_tranx_id").toString().trim() : "");
        gpGuarantee.setEgpAckCode(row.get("egp_ack_code") != null ? row.get("egp_ack_code").toString().trim() : "");
        gpGuarantee.setEgpAckMsg(row.get("egp_ack_msg") != null ? row.get("egp_ack_msg").toString().trim() : "");
        
        
        return gpGuarantee;
    }
    
    //update by Malinee T. UR58100048 @20151224
    //UR58120031 requestType Parameter add by Tana L. @15022016
    //public String inquiryReviewForExportExcel(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String username) {
    public String inquiryReviewForExportExcel(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String egpStatus, String requestType, String username) {
        deleteFileWithUsername(username);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = formatter.format(new Date());
        StringBuilder fileName = new StringBuilder();
        fileName.append("Review_").append(username).append("_").append(timestamp).append(".xls");
        String fullPathFileName = reportConfig.getFolderPath() + fileName.toString();
        String sheetName = "Review";
        String extension = "xls";
        try {
            //List<GPGuarantee> dataList = inquiryReview(taxId, accountNo, expireDateFrom, expireDateTo);
        	//List<GPGuarantee> dataList = inquiryReview(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus); //add by Malinee T. UR58100048 @20151224
        	List<GPGuarantee> dataList = inquiryReview(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus, requestType); //UR58120031 add by Tana L. @15022016
            Map<String, Object[]> data = convertListToMap(dataList, "review");
            Map<String, String> dataFormats = new TreeMap<String, String>();
            int rowNo = 1;
            for (String value : Constants.EXCEL_REVIEW_HEADER_TYPE) {
                dataFormats.put(String.valueOf(rowNo), value);
                rowNo += 1;
            }
            Map<String, Map> dataSheet = new TreeMap<String, Map>();
            dataSheet.put(sheetName, data);

            ExcelUtils excel = new ExcelUtils();
            boolean result = excel.generateExcelFile(fullPathFileName, extension, dataSheet, dataFormats);
        } catch (Exception ex) {

        }

        return fileName.toString();
    }

    //update by Malinee T. UR58100048 @20151224
    //UR58120031 requestType Parameter add by Tana L. @15022016
    //public String inquiryMonitorForExportExcel(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String username) {
    public String inquiryMonitorForExportExcel(String taxId, String accountNo, String expireDateFrom, String expireDateTo, String egpStatus, String requestType, String username) {
        deleteFileWithUsername(username);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = formatter.format(new Date());
        StringBuilder fileName = new StringBuilder();
        fileName.append("Approval_").append(username).append("_").append(timestamp).append(".xls");
        String fullPathFileName = reportConfig.getFolderPath() + fileName.toString();
        String sheetName = "Monitor";
        String extension = "xls";
        try {
        	
            //List<GPGuarantee> dataList = inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo);
        	//List<GPGuarantee> dataList = inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus);  //add by Malinee T. UR58100048 @20151224
        	List<GPGuarantee> dataList = inquiryMonitor(taxId, accountNo, expireDateFrom, expireDateTo, egpStatus, requestType);  //UR58120031 add by Tana L. @15022016
        	 
            Map<String, Object[]> data = convertListToMap(dataList, "monitor");
            Map<String, String> dataFormats = new TreeMap<String, String>();
            int rowNo = 1;
            for (String value : Constants.EXCEL_MONITOR_HEADER_TYPE) {
                dataFormats.put(String.valueOf(rowNo), value);
                rowNo += 1;
            }
            Map<String, Map> dataSheet = new TreeMap<String, Map>();
            dataSheet.put(sheetName, data);

            ExcelUtils excel = new ExcelUtils();
            boolean result = excel.generateExcelFile(fullPathFileName, extension, dataSheet, dataFormats);
        } catch (Exception ex) {

        }

        return fileName.toString();
    }

    public String inquiryRequestForExportExcel(Map<String, String> jsonMap, String username) {

        deleteFileWithUsername(username);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = formatter.format(new Date());
        StringBuilder fileName = new StringBuilder();
        fileName.append("Inquiry_").append(username).append("_").append(timestamp).append(".xls");
        String fullPathFileName = reportConfig.getFolderPath() + fileName.toString();
        String sheetName = "Request";
        String extension = "xls";

        try {
            List<GPGuarantee> dataList = inquiry(jsonMap);
            Map<String, Object[]> data = convertListToMap(dataList, "request");
            Map<String, String> dataFormats = new TreeMap<String, String>();
            int rowNo = 1;
            for (String value : Constants.EXCEL_HEADER_TYPE) {
                dataFormats.put(String.valueOf(rowNo), value);
                rowNo += 1;
            }
            Map<String, Map> dataSheet = new TreeMap<String, Map>();
            dataSheet.put(sheetName, data);

            ExcelUtils excel = new ExcelUtils();
            boolean result = excel.generateExcelFile(fullPathFileName, extension, dataSheet, dataFormats);
        } catch (Exception ex) {
        	
        	System.out.println(ex.getMessage());
        }
        return fileName.toString();
    }

    private Map<String, Object[]> convertListToMap(List<GPGuarantee> input, String reportType) throws Exception {
        Map<String, Object[]> output = new TreeMap<String, Object[]>();
        try {
            if (reportType.equals("monitor")) {
                output.put("1", Constants.EXCEL_MONITOR_HEADER_FIELDS);
            } else if (reportType.equals("review")) {
                output.put("1", Constants.EXCEL_REVIEW_HEADER_FIELDS);
            } else {
                output.put("1", Constants.EXCEL_HEADER_FIELDS);
            }
            int itemNo = 2;
            if (input != null) {
                for (GPGuarantee item : input) {
                    Object[] itemObj;
                    String itemNoStr = String.valueOf(itemNo);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    if (reportType.equals("monitor")) {
                        itemObj = new Object[]{
                            StringUtil.nullToBlank(item.getTxRef()), StringUtil.nullToBlank(item.getIssueTypeDesc()), StringUtil.nullToBlank(item.getVendorName()), StringUtil.nullToBlank(item.getAccountNo()), //UR58120031 getIssueTypeDesc add by Tana L. @18022016
                            item.getGuaranteeAmt(), StringUtil.nullToBlank(item.getMsgCode()),
                            StringUtil.nullToBlank(item.getExpireDate()), StringUtil.nullToBlank(item.getExtendDate()), StringUtil.nullToBlank(item.getDtm()), //UR58120031 getExtendDate add by Tana L. @10022016
                            StringUtil.nullToBlank(item.getProjNo()), StringUtil.nullToBlank(item.getProjName()),
                            StringUtil.nullToBlank(item.getStartDate()), StringUtil.nullToBlank(item.getEndDate()),
                            StringUtil.nullToBlank(item.getBondType()), StringUtil.nullToBlank(item.getXmlOutput()),
                            StringUtil.nullToBlank(item.getLgNo()), StringUtil.nullToBlank(item.getVendorTaxId()),
                            StringUtil.nullToBlank(item.getReviewStatus()), StringUtil.nullToBlank(item.getReviewReason()), StringUtil.nullToBlank(item.getReviewBy()), StringUtil.nullToBlank(formatter.format(item.getReviewDtm()))
                            , StringUtil.nullToBlank(item.getOcCode()), StringUtil.nullToBlank(item.getBranch())
                            //add by Malinee T. UR58100048 @20151225
                            , StringUtil.nullToBlank(item.getEgpAckStatus()) , StringUtil.nullToBlank(item.getEgpAckMsg())
                        };
                    } else if (reportType.equals("review")) {
                        itemObj = new Object[]{
                        		StringUtil.nullToBlank(item.getTxRef()), StringUtil.nullToBlank(item.getIssueTypeDesc()), StringUtil.nullToBlank(item.getVendorName()), StringUtil.nullToBlank(item.getAccountNo()), //UR58120031 getIssueTypeDesc add by Tana L. @18022016
                            item.getGuaranteeAmt(), StringUtil.nullToBlank(item.getMsgCode()),
                            StringUtil.nullToBlank(item.getExpireDate()), StringUtil.nullToBlank(item.getExtendDate()), StringUtil.nullToBlank(item.getDtm()), //UR58120031 getExtendDate add by Tana L. @10022016
                            StringUtil.nullToBlank(item.getProjNo()), StringUtil.nullToBlank(item.getProjName()),
                            StringUtil.nullToBlank(item.getStartDate()), StringUtil.nullToBlank(item.getEndDate()),
                            StringUtil.nullToBlank(item.getBondType()), StringUtil.nullToBlank(item.getXmlOutput()),
                            StringUtil.nullToBlank(item.getLgNo()), StringUtil.nullToBlank(item.getVendorTaxId()),
                            StringUtil.nullToBlank(item.getReviewStatus()), StringUtil.nullToBlank(item.getReviewReason()), StringUtil.nullToBlank(item.getReviewBy()), StringUtil.nullToBlank(formatter.format(item.getReviewDtm())),
                            StringUtil.nullToBlank(item.getApproveStatus()), StringUtil.nullToBlank(item.getApproveReason()), StringUtil.nullToBlank(item.getApproveBy()), StringUtil.nullToBlank(formatter.format(item.getApproveDtm()))
                            , StringUtil.nullToBlank(item.getOcCode()), StringUtil.nullToBlank(item.getBranch())
                            //add by Malinee T. UR58100048 @20151225
                            , StringUtil.nullToBlank(item.getEgpAckStatus()) , StringUtil.nullToBlank(item.getEgpAckMsg())
                        };
                    } else {
                        itemObj = new Object[]{
                        		StringUtil.nullToBlank(item.getTxRef()), StringUtil.nullToBlank(item.getVendorName()), StringUtil.nullToBlank(item.getAccountNo()), 
                            item.getGuaranteeAmt(), StringUtil.nullToBlank(item.getMsgCode()),
                            StringUtil.nullToBlank(item.getExpireDate()), StringUtil.nullToBlank(item.getExtendDate()), StringUtil.nullToBlank(item.getDtm()), //UR58120031 getExtendDate add by Tana L. @10022016
                            StringUtil.nullToBlank(item.getProjNo()), StringUtil.nullToBlank(item.getProjName()),
                            StringUtil.nullToBlank(item.getStartDate()), StringUtil.nullToBlank(item.getEndDate()),
                            StringUtil.nullToBlank(item.getBondType()), StringUtil.nullToBlank(item.getXmlOutput()),
                            StringUtil.nullToBlank(item.getIssueTypeDesc()), //UR58120031 getIssueTypeDesc add by Tana L. @18022016
                            StringUtil.nullToBlank(item.getLgNo()), StringUtil.nullToBlank(item.getVendorTaxId()),
                            StringUtil.nullToBlank(item.getReviewStatus()), StringUtil.nullToBlank(item.getReviewReason()), StringUtil.nullToBlank(item.getReviewBy()), StringUtil.nullToBlank(formatter.format(item.getReviewDtm())),
                            StringUtil.nullToBlank(item.getApproveStatus()), StringUtil.nullToBlank(item.getApproveReason()), StringUtil.nullToBlank(item.getApproveBy()), StringUtil.nullToBlank(formatter.format(item.getApproveDtm()))
                            , StringUtil.nullToBlank(item.getOcCode()), StringUtil.nullToBlank(item.getBranch())
                            //add by Malinee T. UR58100048 @20151225
                            , StringUtil.nullToBlank(item.getEgpAckStatus()) , StringUtil.nullToBlank(item.getEgpAckMsg())
                        };
                    }
                    
                    output.put(itemNoStr, itemObj);
                    itemNo += 1;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error in convertListToMap: " + e.getMessage());
        }
        //sort the map by rowNo
        Map<String, Object[]> sortedOutput = new TreeMap<String, Object[]>(output);

        return sortedOutput;
    }

    public void deleteFileWithUsername(String username) {
        StringBuilder filePath;
        String folderPath = reportConfig.getFolderPath();
        System.out.println("folderPath : "+folderPath);
        File folder = new File(folderPath);
        String[] files = folder.list();
        for (String file : files) {
            if (file.contains(username)) {
                filePath = new StringBuilder();
                filePath.append(folderPath).append(file);
                System.out.println("filePath : "+filePath.toString());
                File f = new File(filePath.toString());
                f.delete();
            }
        }

    }
}
