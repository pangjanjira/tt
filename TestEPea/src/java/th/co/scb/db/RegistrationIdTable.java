/**
 * 
 */
package th.co.scb.db;

import java.util.Map;

import th.co.scb.model.RegistrationId;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class RegistrationIdTable {
	
	private ConnectDB connectDB;
	
	public RegistrationIdTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public RegistrationId findRegistrationId(){
    	
		RegistrationId registrationId = null;
	    	
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.REGISTRATION_ID);
		 //sql.append(" where id = 1 ");
	    	
//		 System.out.println("sql : " + sql.toString());
	    	 
	   	Map<String, Object> result = connectDB.querySingle(sql.toString());
	   	if(result != null){

	   		registrationId = new RegistrationId(((Long)result.get("id")).intValue(), 
   						(String)result.get("customs_reg_id"), 
   						(String)result.get("bank_reg_id")
   					);
	   	}
   	
	   	return registrationId;
   }

}
