/**
 * 
 */
package th.co.scb.db;

import java.util.Date;
import java.util.Map;

import th.co.scb.model.ETime;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class ETimeTable {
	
	private ConnectDB connectDB;
	
	public ETimeTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public ETime findETime(String systemName){
		
		ETime eTime = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from ").append(Constants.TableName.E_TIME);
		sql.append(" where system_name = ? ");
	    	
		System.out.println("sql : " + sql.toString());
	    	 
		Map<String, Object> result = connectDB.querySingle(sql.toString(), systemName);
	   	if(result != null){

	   		eTime = new ETime(((Long)result.get("id")).intValue(), 
	   					(String)result.get("system_name"), 
   						(String)result.get("start_time"), 
   						(String)result.get("end_time"),
   						(Date)result.get("process_date")
   					);
	   	}
   	
	   	return eTime;
	}

}
