/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import th.co.scb.model.eguarantee.ControlAccount;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.StringUtil;

//add by Mayurachat L@22062015
public class ControlAccountService {

    private ConnectDB connectDB;

    public List<ControlAccount> inquiryControlAcc(Map<String, String> jsonMap) throws ParseException {
        this.connectDB = new ConnectDB();
        ControlAccount control;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss" ,Locale.ENGLISH);
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ControlAccount> returnList = new ArrayList<ControlAccount>();
        try {
            String account = StringUtil.nullToBlank(jsonMap.get("account"));
            String messageCode = StringUtil.nullToBlank(jsonMap.get("messageCode"));
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id, account_no, msg_code, msg_description, active_flag ");
            sql.append(" ,IFNULL(DATE_FORMAT(create_dtm, '%Y-%m-%d'), '') AS create_dtm ,IFNULL(create_by,'') AS create_by ");
            sql.append(" ,IFNULL(DATE_FORMAT(update_dtm, '%Y-%m-%d'), '') AS update_dtm,IFNULL(update_by,'') AS update_by ");
            sql.append(" FROM ").append(Constants.TableName.CONTROL_ACCOUNT);
            sql.append(" WHERE 1=1 AND active_flag='Y'");
            if(!"".equals(account)){
                sql.append(" AND account_no =? ");
            }
            if(!"".equals(messageCode)){
                sql.append(" AND msg_code = ? ");
            }
            

            System.out.println("sql inquiry : " + sql.toString());

            List<Map<String, Object>> result = null;
            if(!"".equals(account) && !"".equals(messageCode)){
                result = connectDB.queryList(sql.toString(), jsonMap.get("account"), jsonMap.get("messageCode"));
            }else{
                if("".equals(account + "" + messageCode)){
                    result = connectDB.queryList(sql.toString());
                }else{
                    if(!"".equals(account)){
                        result = connectDB.queryList(sql.toString(), jsonMap.get("account"));
                    }
                    if(!"".equals(messageCode)){
                        result = connectDB.queryList(sql.toString(), jsonMap.get("messageCode"));
                    }   
                }
            }

            for (Map<String, Object> row : result) {
                control = new ControlAccount();
                control.setId((Integer)row.get("id"));
                control.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString() : "");
                control.setActive_flag(row.get("active_flag") != null ? row.get("active_flag").toString() : "");
                control.setMsgCode(row.get("msg_code") != null ? row.get("msg_code").toString() : "");
                control.setMsgDescription(row.get("msg_description") != null ? row.get("msg_description").toString() : "");
                control.setCreate_by(row.get("create_by").toString());
                control.setCreate_dtm(DateUtil.stringToDate(row.get("create_dtm").toString()));
                control.setUpdate_by((row.get("update_by").toString()));
                control.setUpdate_dtm(DateUtil.stringToDate(row.get("update_dtm").toString()));

                returnList.add(control);
                System.out.println("select control: " + control);

            }
        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return returnList;
    }

    public String deleteControl(Map<String, String> jsonMap) throws Exception {
        this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(Constants.TableName.CONTROL_ACCOUNT);
            sql.append(" SET active_flag  = 'N'");
            sql.append(" ,update_dtm = NOW()");
            sql.append(" ,update_by = ?");
            sql.append(" WHERE id =? AND active_flag = 'Y'");
            
            System.out.println("sql delete: "+sql.toString());
            int status = connectDB.execute(sql.toString(),jsonMap.get("update_by"),jsonMap.get("id"));

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
    
    public boolean checkDuplication(String account, String messageCode, ConnectDB connectDB){
        boolean output = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) as counter");
        sql.append(" FROM ").append(Constants.TableName.CONTROL_ACCOUNT);
        sql.append(" WHERE 1=1 AND active_flag='Y'");
        //sql.append(" AND account_no =? AND msg_code = ? ");
        //Map<String, Object> result = connectDB.querySingle(sql.toString(), account, messageCode);
        sql.append(" AND account_no =? ");
        Map<String, Object> result = connectDB.querySingle(sql.toString(), account);
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

    public String insertControl(Map<String, String> jsonMap) throws ParseException {
        this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        try {
            if(checkDuplication(jsonMap.get("addAccount"), jsonMap.get("addMessageCode"), connectDB)){
                updateStatus = "duplicate";
                return updateStatus;
            }
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT ").append(Constants.TableName.CONTROL_ACCOUNT);
            sql.append(" (account_no,msg_code ,msg_description, active_flag, create_dtm, create_by)");
            sql.append(" VALUES(?, ?, ?, 'Y', NOW(), ?)");
            System.out.println("insert sql: "+sql);
            int status = connectDB.execute(sql.toString(), jsonMap.get("addAccount"), jsonMap.get("addMessageCode"), jsonMap.get("addMessageDescription"), jsonMap.get("iCreateBy"));
            
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
        return updateStatus;

    }

    public String updateControl(Map<String, String> jsonMap) {
        this.connectDB = new ConnectDB();
        String updateStatus = "fail";
        try {
            //if(checkDuplication(jsonMap.get("addAccount"), jsonMap.get("addMessageCode"), connectDB)){
            //    updateStatus = "duplicate";
            //    return updateStatus;
            //}
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(Constants.TableName.CONTROL_ACCOUNT);
            
            sql.append(" SET account_no = ?,msg_code = ?,msg_description=?,update_dtm=NOW(),update_by=?");
            sql.append(" WHERE id =? AND active_flag = 'Y'");
            System.out.println("update sql:"+sql.toString());
            int status = connectDB.execute(sql.toString(),jsonMap.get("addAccount"),jsonMap.get("addMessageCode"),jsonMap.get("msgDesc"),jsonMap.get("updateBy"),jsonMap.get("id")); 

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
        return updateStatus;
    }
        public List<ControlAccount> findbyId(Map<String, String> jsonMap) throws ParseException {
        this.connectDB = new ConnectDB();
        ControlAccount control;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<ControlAccount> returnList = new ArrayList<ControlAccount>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id,account_no,msg_code");
            sql.append(" ,msg_description,active_flag, IFNULL(DATE_FORMAT(create_dtm, '%Y-%m-%d'), '') AS create_dtm, IFNULL(DATE_FORMAT(create_dtm, '%Y-%m-%d %H:%i:%s'), '') AS create_dtm_str");
            sql.append(" ,IFNULL(create_by,'') AS create_by,IFNULL(DATE_FORMAT(update_dtm, '%Y-%m-%d'), '') AS update_dtm,IFNULL(DATE_FORMAT(update_dtm, '%Y-%m-%d %H:%i:%s'), '') AS update_dtm_str,IFNULL(update_by,'') AS update_by");
            sql.append(" FROM ").append(Constants.TableName.CONTROL_ACCOUNT);
            sql.append(" WHERE id = ? AND active_flag ='Y'");
            

            System.out.println("sql findbyId: " + sql.toString());

            List<Map<String, Object>> result;
            result = connectDB.queryList(sql.toString(), jsonMap.get("id"));

            for (Map<String, Object> row : result) {
                control = new ControlAccount();
                control.setId((Integer)row.get("id"));
                control.setAccountNo(row.get("account_no") != null ? row.get("account_no").toString() : "");
                control.setActive_flag(row.get("active_flag") != null ? row.get("active_flag").toString() : "");
                control.setMsgCode(row.get("msg_code") != null ? row.get("msg_code").toString() : "");
                control.setMsgDescription(row.get("msg_description") != null ? row.get("msg_description").toString() : "");
                control.setCreate_by(row.get("create_by").toString());
                control.setCreate_dtm(DateUtil.stringToDate(row.get("create_dtm").toString()));
                control.setCreate_dtm_str(row.get("create_dtm_str").toString());
                control.setUpdate_by((row.get("update_by").toString()));
                control.setUpdate_dtm(DateUtil.stringToDate(row.get("update_dtm").toString()));
                control.setUpdate_dtm_str(row.get("update_dtm_str").toString());
                System.out.println("sql findID :"+control.toString());

                returnList.add(control);
            }
        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }
        return returnList;
    }
}
