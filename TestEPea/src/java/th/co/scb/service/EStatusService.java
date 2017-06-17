/**
 * 
 */
package th.co.scb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.EStatusTable;
import th.co.scb.model.EStatus;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class EStatusService {
	
	private static final Logger logger = LoggerFactory.getLogger(EStatusService.class);
	
	public EStatus findEstatus(EStatus eStatusPK) throws Exception {
		
		ConnectDB connectDB = null;
		EStatus eStatus = null;
		
		try{
			
			connectDB = new ConnectDB();
			
			EStatusTable eStatusTable = new EStatusTable(connectDB);
			eStatus = eStatusTable.findEstatus(eStatusPK);

		}catch(Exception ex){
			logger.error("Error find by E-Status : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return eStatus;
		
	}
}
