/**
 * 
 */
package th.co.scb.db;

import java.util.Map;

import th.co.scb.model.BankInfo;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class BankInfoTable {
	
	private ConnectDB connectDB;
	
	public BankInfoTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public BankInfoTable() {
	}
	
	public BankInfo findBankInfo(BankInfo bankInfoPk){
		
		BankInfo bankInfo = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from ").append(Constants.TableName.BANK_INFO);
		sql.append(" where id = ? ");
	    	
		System.out.println("sql : " + sql.toString());
		System.out.println("id : " + bankInfoPk.getId());
	    	 
		Map<String, Object> result = connectDB.querySingle(sql.toString(), bankInfoPk.getId());
	   	if(result != null){

	   		bankInfo = new BankInfo((String)result.get("bank_code"), 
   						(String)result.get("branch_code"), 
   						(String)result.get("branch_name"),
   						(String)result.get("bank_addr")
   					);
	   	}
   	
	   	return bankInfo;
	}
	
	public BankInfo findBankInfoNoConn(BankInfo bankInfoPk){
		
		BankInfo bankInfo = null;
		ConnectDB connectDB = new ConnectDB();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from ").append(Constants.TableName.BANK_INFO);
			sql.append(" where id = ? ");
		    	
			System.out.println("sql : " + sql.toString());
		    	 
			Map<String, Object> result = connectDB.querySingle(sql.toString(), bankInfoPk.getId());
		   	if(result != null){

		   		bankInfo = new BankInfo((String)result.get("bank_code"), 
	   						(String)result.get("branch_code"), 
	   						(String)result.get("branch_name"),
	   						(String)result.get("bank_addr")
	   					);
		   	}	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connectDB != null){
        		connectDB.close();
        	}
		}
	   	return bankInfo;
	}

}
