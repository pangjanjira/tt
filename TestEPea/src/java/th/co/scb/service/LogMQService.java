/**
 * 
 */
package th.co.scb.service;

import th.co.scb.db.LogMQTable;
import th.co.scb.model.LogMQ;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.DateUtil;

/**
 * @author s51486
 *
 */
public class LogMQService {
	
	public void insertLogMQ(LogMQ logMQ) throws Exception {
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			System.out.println("LogMQService<insertLogMQ> new connection : "+connectDB.hashCode());
			connectDB.beginTransaction();
			
			LogMQTable logMQTable = new LogMQTable(connectDB);
			logMQTable.add(logMQ);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			System.out.println("\n " + DateUtil.getDateTimeX() + " -->:" + "Insert log MQ Error : "+ex.getMessage()+"....\n");
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		System.out.println("LogMQService<insertLogMQ> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
	}
}
