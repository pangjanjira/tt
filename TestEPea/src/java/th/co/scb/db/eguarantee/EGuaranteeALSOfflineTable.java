/**
 * 
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.EGuaranteeALSOffline;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.StringUtil;

/**
 * @author s51486
 *
 */
public class EGuaranteeALSOfflineTable {
	
	private ConnectDB connectDB;
	
	public EGuaranteeALSOfflineTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public void add(EGuaranteeALSOffline eGuaranteeALSOffline) throws Exception{
		
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(Constants.TableName.E_GUARANTEE_ALS_OFFLINE);
		sql.append(" (customs_ref_no, bank_acct_no, status, res_code, res_message, ");
		sql.append("  add_date) ");
		sql.append(" VALUES(?,?,?,?,?, now() )");
		
		System.out.println("sql : " + sql.toString());
		
		try{
			int id = connectDB.insert(sql.toString(), 
									eGuaranteeALSOffline.getCustomsRef(),
									eGuaranteeALSOffline.getDebtorBankAccNo(),
									eGuaranteeALSOffline.getTransactionStatus(),
									eGuaranteeALSOffline.getMessageCode(),
									StringUtil.subString(eGuaranteeALSOffline.getMessageDescript(),200)
			                   );
			
			eGuaranteeALSOffline.setId(id);
		}catch (Exception e) {
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}
		
	}
}
