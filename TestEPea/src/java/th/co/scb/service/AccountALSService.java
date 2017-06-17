/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.scb.service;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.StringUtil;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.service.eguarantee.GPGuaranteeService;
import th.co.scb.service.mq.EGuaranteeMQMessageException;
/**
 *
 * @author s61962
 */
public class AccountALSService {
    private ConnectDB connectDB;
    public List<AccountALS> inquiryAccALS(Map<String, String> jsonMap) throws ParseException {
        this.connectDB = new ConnectDB();
        AccountALS accountALS;
        List<AccountALS> returnList = new ArrayList<AccountALS>();
         try {
            String taxID = StringUtil.nullToBlank(jsonMap.get("taxID"));
            String accountNo = StringUtil.nullToBlank(jsonMap.get("accountNo"));
            String ocCode = StringUtil.nullToBlank(jsonMap.get("ocCode"));
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id,branch, occode, tax_id,account_no, account_name, line_amt,");
            sql.append(" avaliable_amt, active_flag, create_dtm,");
            sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            
            //sql.append(" avaliable_amt, active_flag ");
            //sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            
            
            sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);
            sql.append( " WHERE 1=1 AND active_flag='Y' AND purpose='09' ");
            if(!"".equals(taxID)){
                sql.append(" AND tax_id =? ");
            }
            if(!"".equals(accountNo)){
                 sql.append(" AND account_no =? ");
            }
            if(!"".equals(ocCode)){
                 sql.append(" AND occode =? ");
            }
            
            // System.out.println("taxID: " + taxID + "account NO. :" + accountNo + "OCCode : " + ocCode   + "SQL :: " + sql);
             List<Map<String, Object>> result = null;
             
            if(!"".equals(taxID) && !"".equals(accountNo) && !"".equals(ocCode)){
                result = connectDB.queryList(sql.toString(), jsonMap.get("taxID"), jsonMap.get("accountNo"),jsonMap.get("ocCode"));
            }else{
                if("".equals(taxID) && "".equals(accountNo) && "".equals(ocCode)){
                    result = connectDB.queryList(sql.toString());
                }else if("".equals(taxID) && !"".equals(accountNo) && !"".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(),jsonMap.get("accountNo"),jsonMap.get("ocCode"));
                }else if(!"".equals(taxID) && "".equals(accountNo) && !"".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(), jsonMap.get("taxID"), jsonMap.get("ocCode"));
                }else if(!"".equals(taxID) && !"".equals(accountNo) && "".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(), jsonMap.get("taxID"), jsonMap.get("accountNo"));
                }else if(!"".equals(taxID) && "".equals(accountNo) && "".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(), jsonMap.get("taxID"));
                }else if("".equals(taxID) && !"".equals(accountNo) && "".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(), jsonMap.get("accountNo"));
                }else if("".equals(taxID) && "".equals(accountNo) && !"".equals(ocCode)){
                    result = connectDB.queryList(sql.toString(), jsonMap.get("ocCode"));
                }
                
                
//                else{
//                    if(!"".equals(taxID)){
//                        result = connectDB.queryList(sql.toString(), jsonMap.get("taxID"));
//                    }
//                    if(!"".equals(accountNo)){
//                        result = connectDB.queryList(sql.toString(), jsonMap.get("accountNo"));
//                    }
//                    if(!"".equals(ocCode)){
//                        result = connectDB.queryList(sql.toString(), jsonMap.get("ocCode"));
//                    } 
//                }
            }

            for (Map<String, Object> row : result) {
                accountALS = new AccountALS();
                accountALS.setId(((Long)row.get("id")).intValue()); 
                accountALS.setBranch(row.get("branch") != null ? row.get("branch").toString() : ""); 
                accountALS.setOcCode(row.get("occode") != null ? row.get("occode").toString() : ""); 
                accountALS.setTaxId(row.get("tax_id") != null ? row.get("tax_id").toString() : "");
                accountALS.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString() : "");
                accountALS.setAccountName(row.get("account_name") != null ? row.get("account_name").toString() : "");
                accountALS.setLineAmt((BigDecimal)row.get("line_amt") != null ? (BigDecimal)row.get("line_amt") : BigDecimal.ZERO);  
                accountALS.setAvaliableAmt((BigDecimal)row.get("avaliable_amt") != null ? (BigDecimal)row.get("avaliable_amt") : BigDecimal.ZERO); 
                accountALS.setActiveFlag(row.get("active_flag") != null ? row.get("active_flag").toString() : "");
                
                if(row.get("create_dtm") != null){
                    //accountALS.setCreateDtm(DateUtil.stringToDate(row.get("create_dtm").toString()));
                	try{
                		accountALS.setCreateDtm((Date)row.get("create_dtm"));
                	}catch(Exception e){
                		//nothing
                	}
                }
                
                accountALS.setCreateBy(row.get("create_by") != null ? row.get("create_by").toString() : ""); 
                if(row.get("update_dtm") != null){
                	try{
                		accountALS.setUpdateDtm((Date)row.get("update_dtm"));
                	}catch(Exception e){
                		//nothing
                	}
                    
                }
                if(row.get("update_by") != null){
                    accountALS.setUpdateBy(row.get("update_by").toString());
                }
                
                
                accountALS.setPurpose(row.get("purpose") != null ? row.get("purpose").toString() : "");
                accountALS.setSubPurpose(row.get("sub_purpose") != null ? row.get("sub_purpose").toString() : "");
                
                
                
                
                
                returnList.add(accountALS);
                //System.out.println("select control: " + accountALS);
            }
         }finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return returnList;
    }
    public List<AccountALS> getAccALSDetail(Map<String, String> jsonMap) throws Exception{
        this.connectDB = new ConnectDB();
        AccountALS accountALS;
        List<AccountALS> returnList = new ArrayList<AccountALS>();
         try {
            
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id,branch, occode, tax_id,account_no, account_name, line_amt,");
            sql.append(" avaliable_amt, active_flag, create_dtm,");
            sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            
            //sql.append(" avaliable_amt, active_flag, create_by, ");
            //sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            //sql.append(" purpose ,sub_purpose");
            
            sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);
            sql.append(" WHERE 1=1 AND active_flag='Y' AND id =? ");
            
            
            
             System.out.println("SQL AcctDetail : " + sql);
             List<Map<String, Object>> result = null;
             
            if(!"".equals(jsonMap.get("id"))){
                result = connectDB.queryList(sql.toString(),jsonMap.get("id"));
            }

            for (Map<String, Object> row : result) {
                accountALS = new AccountALS();
                accountALS.setId(((Long)row.get("id")).intValue()); 
                accountALS.setBranch(row.get("branch") != null ? row.get("branch").toString() : ""); 
                accountALS.setOcCode(row.get("occode") != null ? row.get("occode").toString() : ""); 
                accountALS.setTaxId(row.get("tax_id") != null ? row.get("tax_id").toString() : "");
                accountALS.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString() : "");
                accountALS.setAccountName(row.get("account_name") != null ? row.get("account_name").toString() : "");
                accountALS.setLineAmt((BigDecimal)row.get("line_amt") != null ? (BigDecimal)row.get("line_amt") : BigDecimal.ZERO);  
                accountALS.setAvaliableAmt((BigDecimal)row.get("avaliable_amt") != null ? (BigDecimal)row.get("avaliable_amt") : BigDecimal.ZERO); 
                accountALS.setActiveFlag(row.get("active_flag") != null ? row.get("active_flag").toString() : "");
                
                if(row.get("create_dtm") != null){
                    //accountALS.setCreateDtm(DateUtil.stringToDate(row.get("create_dtm").toString()));
                	try{
                		accountALS.setCreateDtm((Date)row.get("create_dtm"));
                	}catch(Exception e){
                		//nothing
                	}
                }
                
                accountALS.setCreateBy(row.get("create_by") != null ? row.get("create_by").toString() : ""); 
                if(row.get("update_dtm") != null){
                	try{
                		accountALS.setUpdateDtm((Date)row.get("update_dtm"));
                	}catch(Exception e){
                		//nothing
                	}
                    
                }
                if(row.get("update_by") != null){
                    accountALS.setUpdateBy(row.get("update_by").toString());
                }
                
                
                accountALS.setPurpose(row.get("purpose") != null ? row.get("purpose").toString() : "");
                accountALS.setSubPurpose(row.get("sub_purpose") != null ? row.get("sub_purpose").toString() : "");
                
                
                
                
                
                returnList.add(accountALS);
                //System.out.println("select control: " + accountALS);
            }
         }finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return returnList;
        
    }

    public String deleteAcctALS(Map<String, String> jsonMap) throws Exception {
         this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(Constants.TableName.ACCOUNT_ALS);
            sql.append(" SET active_flag = 'N', ");
            sql.append(" update_dtm = NOW(),");
            sql.append(" update_by = ?");
            sql.append(" WHERE id =? AND account_no =?  AND active_flag = 'Y'");
            
            System.out.println("sql delete: "+sql.toString());
            int status = connectDB.execute(sql.toString(),jsonMap.get("userLogOn"),jsonMap.get("id"),jsonMap.get("accountNo"));

            if (status > 0) {
                updateStatus = "success";
            } else {
                updateStatus = "fail";
            }

        } catch (Exception e) {
            updateStatus = e.getMessage();
        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return updateStatus;
    }
    public List<AccountALS> findbyId(Map<String, String> jsonMap) throws ParseException {
        this.connectDB = new ConnectDB();
        AccountALS accountALS;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<AccountALS> returnList = new ArrayList<AccountALS>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id,branch, occode, tax_id,account_no, account_name, line_amt,");
            sql.append(" avaliable_amt, active_flag, create_dtm,");
            sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            
            //sql.append(" avaliable_amt, active_flag, ");
            //sql.append(" create_by, update_dtm, update_by, purpose ,sub_purpose");
            //sql.append(" purpose ,sub_purpose");
            
            sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);;
            sql.append(" WHERE 1=1 AND active_flag='Y' AND id =?");
            
        
             //System.out.println("SQL :: " + sql); 
             
             
             List<Map<String, Object>> result;
             result = connectDB.queryList(sql.toString(), jsonMap.get("id"));

            for (Map<String, Object> row : result) {
               accountALS = new AccountALS();
               accountALS.setId(((Long)row.get("id")).intValue()); 
               accountALS.setBranch(row.get("branch") != null ? row.get("branch").toString() : ""); 
               accountALS.setOcCode(row.get("occode") != null ? row.get("occode").toString() : ""); 
               accountALS.setTaxId(row.get("tax_id") != null ? row.get("tax_id").toString() : "");
               accountALS.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString() : "");
               accountALS.setAccountName(row.get("account_name") != null ? row.get("account_name").toString() : "");
               accountALS.setLineAmt((BigDecimal)row.get("line_amt") != null ? (BigDecimal)row.get("line_amt") : BigDecimal.ZERO);  
               accountALS.setAvaliableAmt((BigDecimal)row.get("avaliable_amt") != null ? (BigDecimal)row.get("avaliable_amt") : BigDecimal.ZERO); 
               accountALS.setActiveFlag(row.get("active_flag") != null ? row.get("active_flag").toString() : "");
               
               if(row.get("create_dtm") != null){
                   //accountALS.setCreateDtm(DateUtil.stringToDate(row.get("create_dtm").toString()));
               	try{
               		accountALS.setCreateDtm((Date)row.get("create_dtm"));
               	}catch(Exception e){
               		//nothing
               	}
               }
               
               accountALS.setCreateBy(row.get("create_by") != null ? row.get("create_by").toString() : ""); 
               if(row.get("update_dtm") != null){
               	try{
               		accountALS.setUpdateDtm((Date)row.get("update_dtm"));
               	}catch(Exception e){
               		//nothing
               	}
                   
               }
               if(row.get("update_by") != null){
                   accountALS.setUpdateBy(row.get("update_by").toString());
               }
               
               
               accountALS.setPurpose(row.get("purpose") != null ? row.get("purpose").toString() : "");
               accountALS.setSubPurpose(row.get("sub_purpose") != null ? row.get("sub_purpose").toString() : "");
                //System.out.println("sql findID :"+accountALS.toString());

                returnList.add(accountALS);
            }
        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return returnList;
    }
//public boolean checkDuplication(String accountNo, String taxId, ConnectDB connectDB){
    public boolean checkDuplication(String accountNo,String taxId, ConnectDB connectDB){
        boolean output = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) as counter");
        sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);;
        sql.append(" WHERE 1=1 AND active_flag='Y' AND purpose='09' ");
        //sql.append(" AND account_no =? AND msg_code = ? ");
        //Map<String, Object> result = connectDB.querySingle(sql.toString(), account, messageCode);
        sql.append(" AND account_no =? And tax_id = ? ");
        Map<String, Object> result = connectDB.querySingle(sql.toString(), accountNo,taxId);
        if(result != null){
            Long counter = (Long)result.get("counter");
            if(counter.intValue() > 0){
                output = true;
            }else{
                output = false;
            }
        }else{
            output = false;
        }
        
        return output;
    }
    
    public boolean checkDuplication(String accountNo,String taxId, String id, ConnectDB connectDB){
        boolean output = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) as counter");
        sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);;
        sql.append(" WHERE 1=1 AND active_flag='Y' AND purpose='09' ");
        //sql.append(" AND account_no =? AND msg_code = ? ");
        //Map<String, Object> result = connectDB.querySingle(sql.toString(), account, messageCode);
        sql.append(" AND account_no =? And tax_id = ? and id<>? ");
        Map<String, Object> result = connectDB.querySingle(sql.toString(), accountNo,taxId,id);
        if(result != null){
            Long counter = (Long)result.get("counter");
            if(counter.intValue() > 0){
                output = true;
            }else{
                output = false;
            }
        }else{
            output = false;
        }
        
        return output;
    }

	public AccountALS getAccountALSAcctNo(ConnectDB connectDB, boolean isOffline, GPGuarantee gpGuarantee,HttpServletRequest request) throws Exception, EGuaranteeMQMessageException{
	    ALSMQService alsMQService = new ALSMQService();
		AccountALS accountALS = null;
		
		if(isOffline){// als offline
			System.out.println("=========== als offline [account is null] ============");
		}else{
			System.out.println("=========== als online [get account from ALS] ============");
			accountALS = alsMQService.getAcctALSAcctNo(gpGuarantee,request);
			if(accountALS != null){
				//
				Locale locale = new Locale("en", "EN");
				Calendar c = Calendar.getInstance(locale);
				accountALS.setAccountNo(gpGuarantee.getAccountNo());
				accountALS.setActiveFlag("Y");
				accountALS.setCreateDtm(c.getTime());
				accountALS.setCreateBy(gpGuarantee.getApproveBy());
				AccountALS accountALSTax = alsMQService.getAcctALS(gpGuarantee,request);
				if(accountALSTax != null){
					accountALS.setTaxId(gpGuarantee.getVendorTaxId());
					accountALS.setOcCode(accountALSTax.getOcCode());
					accountALS.setBank(accountALSTax.getBank());
					accountALS.setBranch(accountALSTax.getBranch());
				}
			}
		}
		
		return accountALS;
	}

    public String insertAcctALS(Map<String, String> jsonMap, HttpServletRequest request) throws ParseException, Exception, EGuaranteeMQMessageException {
        this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        List<AccountALS> returnList = new ArrayList<AccountALS>();
        AccountALS accountALS;
        try {
            if(checkDuplication(jsonMap.get("addAccountNo"),jsonMap.get("addTaxID"),  connectDB)){
                //StringBuilder sql = new StringBuilder();
                //sql.append("SELECT tax_id");
                //sql.append(" FROM ").append(Constants.TableName.ACCOUNT_ALS);
                //sql.append(" WHERE 1=1 AND active_flag='Y'");
                //sql.append(" AND account_no =?");
                //Map<String, Object> result = connectDB.querySingle(sql.toString(), jsonMap.get("addAccountNo"));
                //updateStatus = "duplicate" +","+result.get("tax_id");
            	updateStatus = "duplicate" +","+jsonMap.get("addTaxID");
                return updateStatus;
            }
          
        GPGuarantee objGP = new GPGuarantee();
        objGP.setAccountNo(jsonMap.get("addAccountNo"));
        objGP.setVendorTaxId(jsonMap.get("addTaxID"));
        objGP.setIssueType("0004");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Locale locale = new Locale("en", "EN");
		Calendar c = Calendar.getInstance(locale);
        objGP.setDtm(sdf.format(c.getTime()));
        System.out.println("obj :" + objGP);
        AccountALS objCheckAcct = getAccountALSAcctNo(connectDB, false, objGP, request);
        if(objCheckAcct == null){
        	System.out.println("Account not found!!");
        	updateStatus = "Account not found";
        }else{
        	if(objCheckAcct.getTaxId() == null){
        		System.out.println("TaxId not found!!");
        		updateStatus = "TaxId not found";
        	}else{
        		 //addCreateBy
            	System.out.println("Data response >>BankCode : "+objCheckAcct.getBank()+ "  BranchCode : " + objCheckAcct.getBranch());
            	System.out.println("Data response >>LineAmt : "+objCheckAcct.getLineAmt()+ "  AvaliableAmt : " + objCheckAcct.getAvaliableAmt());
            	System.out.println("Data response >>purpose : "+objCheckAcct.getPurpose()+ "  Subpurpose : " + objCheckAcct.getSubPurpose());
            	System.out.println("Data response >>Create By : "+objCheckAcct.getCreateBy()+ "  TaxID : " + objCheckAcct.getTaxId());
                String Bank = objCheckAcct.getBank();
                String branch = objCheckAcct.getBranch();
                String occode  = objCheckAcct.getOcCode();
                String acctno = objCheckAcct.getAccountNo(); //jsonMap.get("addAccountNo");
                String acctname = "";
                String taxid = objCheckAcct.getTaxId(); //jsonMap.get("addTaxID");
                //Fix UAT Phase3-1
                String purpose = "09"; //objCheckAcct.getPurpose();
                String subPurpose = "0900"; //objCheckAcct.getSubPurpose();
                BigDecimal lineamt = objCheckAcct.getLineAmt();
                BigDecimal avaliableamt = objCheckAcct.getAvaliableAmt();
                String createby = jsonMap.get("addCreateBy");
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT ").append(Constants.TableName.ACCOUNT_ALS);;
                sql.append(" (bank,branch,occode,account_no, account_name, tax_id, line_amt,avaliable_amt,active_flag, create_dtm, create_by, purpose, sub_purpose)");
                sql.append(" VALUES(? , ?, ?, ?, ?, ?, ?, ?, 'Y', NOW(), ?, ?, ?)");
                System.out.println("insert sql: "+sql);
                 int status = connectDB.execute(sql.toString(), Bank, branch, occode, acctno
                            , acctname, taxid, lineamt, avaliableamt, createby, purpose, subPurpose);
                
                 if (status > 0) {
                    updateStatus = "success";
                } else {
                    updateStatus = "fail";
                }
        	}
           
        }
     
        
            
        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return updateStatus;
    }

    public String updateAcctALS(Map<String, String> jsonMap, HttpServletRequest request) throws ParseException, Exception, EGuaranteeMQMessageException {
         this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        try {
            if(checkDuplication(jsonMap.get("addAccountNo"), jsonMap.get("addTaxID"), jsonMap.get("id"), connectDB)){
                updateStatus = "duplicate";
                return updateStatus;
            }else{
            	ALSMQService alsMQService = new ALSMQService();
            	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            	Locale locale = new Locale("en", "EN");
            	Calendar c = Calendar.getInstance(locale);
            	GPGuarantee objGP = new GPGuarantee();
            	objGP.setAccountNo(jsonMap.get("addAccountNo"));
            	objGP.setVendorTaxId(jsonMap.get("addTaxID"));
            	objGP.setIssueType("0004");
            	objGP.setDtm(sdf.format(c.getTime()));
            	AccountALS accountALSTax = alsMQService.getAcctALS(objGP, request);
            	if(accountALSTax == null){
                    updateStatus = "notexisted";
                    return updateStatus;
            	}
            }
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(Constants.TableName.ACCOUNT_ALS);
            sql.append(" SET tax_id =?,update_dtm=now() ,update_by =?");
            sql.append(" WHERE id =?");
            System.out.println("update sql:"+sql.toString());
            int status = connectDB.execute(sql.toString()
            						,jsonMap.get("addTaxID")
            						,jsonMap.get("addCreateBy")
            						,jsonMap.get("id")); 

            if (status > 0) {
                updateStatus = "success";
            } else {
                updateStatus = "fail";
            }

        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return updateStatus; //To change body of generated methods, choose Tools | Templates.
    }
}
