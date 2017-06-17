/**
 * 
 */
package th.co.scb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.EMapCodeErrorTable;
import th.co.scb.model.EMapCodeError;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class EMapCodeErrorService {
	
	private static final Logger logger = LoggerFactory.getLogger(EMapCodeErrorService.class);
	
	public EMapCodeError findEguaCode(EMapCodeError eMapCodeErrorPk) throws Exception {
		
		ConnectDB connectDB = null;
		EMapCodeError eMapCodeError = null;
		
		try{
			
			connectDB = new ConnectDB();
			
			EMapCodeErrorTable eMapCodeErrorTable = new EMapCodeErrorTable(connectDB);
			eMapCodeError = eMapCodeErrorTable.findEguaCode(eMapCodeErrorPk);

		}catch(Exception ex){
			logger.error("Error find Eguarantee Code : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return eMapCodeError;
		
	}
}
