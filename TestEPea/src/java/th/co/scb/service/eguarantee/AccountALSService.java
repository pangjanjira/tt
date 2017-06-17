/**
 * 
 */
package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.AccountALSTable;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class AccountALSService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountALSService.class);
	
	public AccountALS findByAccontNo(String accountNo, String purpose) throws Exception {
		
		ConnectDB connectDB = null;
		AccountALS accountALS = null;
		try{
			
			connectDB = new ConnectDB();
			
			AccountALSTable accountALSTable = new AccountALSTable(connectDB);
			accountALS = accountALSTable.findByAccontNo(accountNo, purpose);
			
		}catch(Exception ex){
			logger.error("Error find by account no : " + ex.getMessage());
			throw  new Exception(ex.getMessage());
            
		}finally{
			if(connectDB != null){
				connectDB.close();
			}
		}
		return accountALS;
	}

	
	
	
}
