/**
 * 
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.TranOffline;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class TranOfflineTable {
	
	private ConnectDB connectDB;
	
	public TranOfflineTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("TranOfflineTable received connection : "+connectDB.hashCode());		
	}
	
	public void add(TranOffline tranOffline){
		
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into ").append(Constants.TableName.TRAN_OFFLINE).append("(customs_ref_no, xml_input, lg_no) ");
    	sql.append(" values(?,?,?) ");
    	
    	System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString(), tranOffline.getCustomsRefNo(), tranOffline.getXml().getBytes(), tranOffline.getLgNo());
    	
    	tranOffline.setId(id);

    }
}
