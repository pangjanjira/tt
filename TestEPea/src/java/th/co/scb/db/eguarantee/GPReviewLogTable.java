/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.util.ConnectDB;

/**
 *
 * @author s60982
 */
public class GPReviewLogTable {

    private ConnectDB connectDB;

    public GPReviewLogTable(ConnectDB connectDB) {
        this.connectDB = connectDB;
        System.out.println("GPReviewLogTable received connection : "+connectDB.hashCode());		
    }

    public void add(GPGuarantee gpGuarantee) {

        StringBuilder sql = new StringBuilder();
        sql.append(" insert into gp_review_log ");
        //UR58120031 new and old expire date add by Tana L. @12022016
        //sql.append(" (gp_guarantee_id, review_status, account_no, review_dtm, review_by, review_reason) ");
        sql.append(" (gp_guarantee_id, review_status, account_no, review_dtm, review_by, review_reason, old_end_date, new_end_date) ");
        //sql.append(" VALUES(?,?,?,?,?,?) ");
        sql.append(" VALUES(?,?,?,?,?,?,?,?) ");

        connectDB.execute(sql.toString(),
                gpGuarantee.getId(),
                gpGuarantee.getReviewStatus(),
                gpGuarantee.getAccountNo(),
                gpGuarantee.getReviewDtm(),
                gpGuarantee.getReviewBy(),
                gpGuarantee.getReviewReason(),
        		//UR58120031 new and old expire date add by Tana L. @12022016
				gpGuarantee.getOldEndDate(),
				gpGuarantee.getNewEndDate());

    }
}
