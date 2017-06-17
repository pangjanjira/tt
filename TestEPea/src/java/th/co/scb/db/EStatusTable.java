/**
 * 
 */
package th.co.scb.db;

import java.util.Map;

import th.co.scb.model.EStatus;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class EStatusTable {
	private ConnectDB connectDB;
	
	public EStatusTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	public EStatus findEstatus(EStatus eStatusPk){
    	
		EStatus eStatus = null;
	    	
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.E_STATUS);
		 sql.append(" where type_status = ? ");
		 sql.append(" and code_status = ? ");
	    	
		 System.out.println("sql : " + sql.toString());
	    	 
	   	Map<String, Object> result = connectDB.querySingle(sql.toString(), eStatusPk.getType(), eStatusPk.getCode());
	   	if(result != null){

	   		eStatus = new EStatus(((Long)result.get("id")).intValue(), 
   						(String)result.get("type_status"), 
   						(String)result.get("code_status"),
   						(String)result.get("desc_en"),
   						(String)result.get("desc_th")
   					);
	   	}
   	
	   	return eStatus;
   }
	
}
