/**
 * 
 */
package th.co.scb.db;

import java.util.Map;

import th.co.scb.model.EMapCodeError;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class EMapCodeErrorTable {
	private ConnectDB connectDB;
	
	public EMapCodeErrorTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public EMapCodeError findEguaCode(EMapCodeError eMapCodeErrorPk){
    	
		EMapCodeError eMapCodeError = null;
	    	
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.E_MAP_CODE_ERROR);
		 	//sql.append("")
		 sql.append(" where mq_code = ? ");
	    	
		 System.out.println("sql : " + sql.toString());
	    	 
	   	Map<String, Object> result = connectDB.querySingle(sql.toString(), eMapCodeErrorPk.getMqCode());
	   	if(result != null){

	   		eMapCodeError = new EMapCodeError(((Long)result.get("id")).intValue(), 
   						(String)result.get("egua_code"), 
   						(String)result.get("mq_code")
   					);
	   	}
   	
	   	return eMapCodeError;
   }
}
