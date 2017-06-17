/**
 * 
 */
package th.co.scb.db;

import java.util.Date;
import java.util.Map;

import th.co.scb.model.eguarantee.MasConfig;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class ConfigTable {
	
	private ConnectDB connectDB;
	
	public ConfigTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}
	
	//add by Malinee T. UR58100048 @20160104
	public ConfigTable() {
		
	}
	
	public int getDelayTime(){
		int delayTime = 0;
		MasConfig masConfig = new MasConfig();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT IFNULL(config_value, '0') AS config_value FROM ").append(Constants.TableName.MAS_CONFIG);
		sql.append(" WHERE active_flag='Y' AND config_name='DELAY_TIME' ");
		System.out.println("sql : " + sql.toString());
		Map<String, Object> result = connectDB.querySingle(sql.toString());
	   	if(result != null){
	   		masConfig.setConfigName("DELAY_TIME");
	   		masConfig.setConfigValue((String)result.get("config_value"));
	   		delayTime = Integer.parseInt(masConfig.getConfigValue());
	   	}
   	
	   	return delayTime;
	}
	
	//add by Malinee T. UR58100048 @20160104
	public int getConfigIntNoConn(String configName){
		 int valueInt = 0;
		    ConnectDB conn = new ConnectDB();
		    System.out.println("ConfigTable<getConfigNoConn> new connection : " + conn.hashCode());
		    try{
		        MasConfig masConfig = new MasConfig();
		        StringBuilder sql = new StringBuilder();
		        sql.append(" SELECT IFNULL(config_value, '0') AS config_value FROM ").append(Constants.TableName.MAS_CONFIG);
		        sql.append(" WHERE active_flag='Y' AND config_name='").append(configName).append("' ");
		        System.out.println("sql : " + sql.toString());
		        Map<String, Object> result = conn.querySingle(sql.toString());
		           if(result != null){
		               masConfig.setConfigName(configName);
		               masConfig.setConfigValue((String)result.get("config_value"));
		              valueInt = Integer.parseInt(masConfig.getConfigValue());
		           }
		    }catch(Exception e){
		        //nothing
		    }finally{
		        if(conn != null){
		            System.out.println("ConfigTable<getConfigNoConn> close connection : " + conn.hashCode());
		            conn.close();
		        }
		    }
		    return valueInt;
	}

}
