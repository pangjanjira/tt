/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.LogAccess;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 *
 * @author s60982
 */
public class LogAccessTable {

    private ConnectDB connectDB;

    public LogAccessTable(ConnectDB connectDB) {
        this.connectDB = connectDB;
        System.out.println("LogAccessTable received connection : "+connectDB.hashCode());		
    }

    public void add(LogAccess logAccess) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into ").append(Constants.TableName.LOG_ACCESS).append("(app_code, func_code, user_id, access_dtm, access_status, ip_address) ");
        sql.append(" values(?,?,?,?,?,?) ");
        int id = connectDB.insert(
                sql.toString(),
                logAccess.getAppCode(),
                logAccess.getFuncCode(),
                logAccess.getUserId(),
                logAccess.getAccessDtm(),
                logAccess.getAccessStatus(),
                logAccess.getIpAddress()
        );
        logAccess.setId(id);
    }
}
