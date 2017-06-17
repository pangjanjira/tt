/**
 * 
 */
package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.TranOfflineTable;
import th.co.scb.model.eguarantee.TranOffline;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class TranOfflineService {
	
	private static final Logger logger = LoggerFactory.getLogger(TranOfflineService.class);
	
	public void insertTranOffline(TranOffline tranOffline) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			TranOfflineTable tranOfflineTable = new TranOfflineTable(connectDB);
			tranOfflineTable.add(tranOffline);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Insert Tran_offline : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
	}

}
