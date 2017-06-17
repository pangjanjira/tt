package th.co.scb.db.eguarantee;

import java.util.Map;

import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class ControlAccountTable {
	
	private ConnectDB connectDB;

	public ControlAccountTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("ControlAccountTable received connection : "+connectDB.hashCode());	
	}
	
	public boolean isControlAccount(String accountNo){
	
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) as count from control_account ");
		sql.append(" where account_no  = ? ");
		sql.append(" and   active_flag = 'Y' ");

		Map<String, Object> result = connectDB.querySingle(sql.toString(), accountNo);
		
		return ((Long)result.get("count") > 0);
		
	}
}
