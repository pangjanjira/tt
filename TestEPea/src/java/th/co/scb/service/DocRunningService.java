/**
 * 
 */
package th.co.scb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.DocRunningTable;
import th.co.scb.model.ETime;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class DocRunningService {
	
	private static final Logger logger = LoggerFactory.getLogger(DocRunningService.class);
	
	public String genLGNo(ETime eTime) throws Exception {
		
		ConnectDB connectDB = null;
		String lgNo = "";
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			DocRunningTable docRunningTable = new DocRunningTable(connectDB);
			lgNo = docRunningTable.genLGNo(eTime);
			
			connectDB.commit();

		}catch(Exception ex){
			connectDB.rollback();
			logger.error("Error gen L/G No. : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return lgNo;
	}
	
	public String genBankTransactionNo(ETime eTime) throws Exception {
		
		ConnectDB connectDB = null;
		String lgNo = "";
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			DocRunningTable docRunningTable = new DocRunningTable(connectDB);
			lgNo = docRunningTable.genBankTransactionNo(eTime);
			
			connectDB.commit();

		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error gen Bank Transaction No : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return lgNo;
	}
}
