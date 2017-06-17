package th.co.scb.db.eguarantee;

import java.util.List;
import java.util.Map;


import th.co.scb.model.eguarantee.BUCode;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

//New class by Apichart H.@20150513
public class BUCodeTable {
	
	private ConnectDB connectDB;
	
	public BUCodeTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("BUCodeTable received connection : "+connectDB.hashCode());		
	}
	
	public BUCode findByBuCode(BUCode buCode){
		ConnectDB connectDB = new ConnectDB();
		System.out.println("BUCodeTable<findByBuCode> new connection : "+connectDB.hashCode());
		BUCode bu = new BUCode();
		try{
			
			
			StringBuilder sql = new StringBuilder();
			 sql.append(" select * from ").append(Constants.TableName.BU_CODE);
			 sql.append(" where bu_code = ? ");
			 
			 System.out.println("sql : " + sql.toString());
		    
			 List<Map<String, Object>> result = null;
			 
			 Map<String, Object> rs = connectDB.querySingle(sql.toString(), buCode.getBuCode());
			 if(rs != null){
				 bu.setBuCode((String)rs.get("bu_code"));
				 bu.setBuDesc((String)rs.get("bu_desc"));
			 }
			 
		}finally{
			if(connectDB != null){
				System.out.println("BUCodeTable<findByBuCode> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
		}
		return bu;
	}
}
