/**
 * 
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.util.ConnectDB;

/**
 * @author bussara.b
 *
 */
public class GPApprovalLogTable {
	
	private ConnectDB connectDB;

	public GPApprovalLogTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("GPApprovalLogTable received connection : "+connectDB.hashCode());		
	}
	
	public void add(GPGuarantee gpGuarantee) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into gp_approval_log ");
		//UR58120031 new and old expire date add by Tana L. @12022016
		//sql.append(" (gp_guarantee_id, approve_status, account_no, approve_dtm, approve_by, approve_reason) "); 
		sql.append(" (gp_guarantee_id, approve_status, account_no, approve_dtm, approve_by, approve_reason, old_end_date, new_end_date) ");
		//sql.append(" VALUES(?,?,?,?,?,?) "); 
		sql.append(" VALUES(?,?,?,?,?,?,?,?) ");

		connectDB.execute(sql.toString(),
						  gpGuarantee.getId(),
						  gpGuarantee.getApproveStatus(),
						  gpGuarantee.getAccountNo(),
						  gpGuarantee.getApproveDtm(),
						  gpGuarantee.getApproveBy(),
						  gpGuarantee.getApproveReason(),
						  //UR58120031 new and old expire date add by Tana L. @12022016
						  gpGuarantee.getOldEndDate(),
						  gpGuarantee.getNewEndDate());

	}
	
}
