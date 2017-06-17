/**
 * 
 */
package th.co.scb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.RegistrationIdTable;
import th.co.scb.model.RegistrationId;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class RegistrationIdService {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationIdService.class);
	
	public RegistrationId findRegistrationId() throws Exception {
		
		ConnectDB connectDB = null;
		RegistrationId registrationId = null;
		
		try{
			
			connectDB = new ConnectDB();
			
			RegistrationIdTable registrationIdTable = new RegistrationIdTable(connectDB);
			registrationId = registrationIdTable.findRegistrationId();

		}catch(Exception ex){
			logger.error("Error find Registration ID : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return registrationId;
		
	}

}
