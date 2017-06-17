/**
 * 
 */
package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.EGuaranteeALSOfflineTable;
import th.co.scb.model.eguarantee.EGuaranteeALSOffline;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class EGuaranteeALSOfflineService {
	
	private static final Logger logger = LoggerFactory.getLogger(EGuaranteeALSOfflineService.class);
	
	public void insert(EGuaranteeALSOffline eGuaranteeALSOffline) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			EGuaranteeALSOfflineTable eGuaranteeALSOfflineTable = new EGuaranteeALSOfflineTable(connectDB);
			eGuaranteeALSOfflineTable.add(eGuaranteeALSOffline);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Insert eGuaranteeALSOffline : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
	}
}
