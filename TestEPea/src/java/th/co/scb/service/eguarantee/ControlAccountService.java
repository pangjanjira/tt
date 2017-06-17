package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.ControlAccountTable;
import th.co.scb.util.ConnectDB;

/**
 * @author s27947
 *
 */
public class ControlAccountService {

	private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeService.class);
	
	public boolean isControlAccount(String accountNo) throws Exception {
		
		ConnectDB connectDB = null;
		
		try { 
			
			connectDB = new ConnectDB();
			
			ControlAccountTable controlAccountTable = new ControlAccountTable(connectDB);
			return controlAccountTable.isControlAccount(accountNo);
			
		}catch(Exception ex) {
			logger.error("Error check is CONTROL_ACCOUNT : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
		
	}
	
	public boolean isControlAccount(String accountNo, ConnectDB connectDB) throws Exception {
		try { 
			ControlAccountTable controlAccountTable = new ControlAccountTable(connectDB);
			return controlAccountTable.isControlAccount(accountNo);
			
		}catch(Exception ex) {
			logger.error("Error check is CONTROL_ACCOUNT : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }
	}
}
