/**
 * 
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.TranGPOffline;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class TranGPOfflineTable {
private ConnectDB connectDB;
	
	public TranGPOfflineTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("TranGPOfflineTable received connection : "+connectDB.hashCode());		
	}
	
	
	/*public void add(TranGPOffline tranGPOffline){
		
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into ").append(Constants.TableName.TRAN_GP_OFFLINE).append("(transaction_no, xml_input, lg_no) ");
    	sql.append(" values(?,?,?) ");
    	
    	System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString(), tranGPOffline.getTransactionNo(), tranGPOffline.getXml().getBytes(), tranGPOffline.getLgNo());
    	
    }*/
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	public void add(TranGPOffline tranGPOffline){
		
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into ").append(Constants.TableName.TRAN_GP_OFFLINE).append("(transaction_no, xml_input, lg_no, gp_guarantee_id) ");
    	sql.append(" values(?,?,?,?) ");
    	
    	System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString(), tranGPOffline.getTransactionNo(), tranGPOffline.getXml().getBytes(), tranGPOffline.getLgNo(), tranGPOffline.getGpGuaranteeId());

	}
}
