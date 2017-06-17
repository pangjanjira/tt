/**
 * 
 */
package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.DeclarationTable;
import th.co.scb.model.eguarantee.Declaration;
import th.co.scb.util.ConnectDB;

/**
 * @author s51486
 *
 */
public class DeclarationService {
	
	private static final Logger logger = LoggerFactory.getLogger(DeclarationService.class);
	
	public boolean isDupDeclarateion(Declaration declaration) throws Exception {
		
		ConnectDB connectDB = null;
		boolean isDup = false;
		
		try{
			
			connectDB = new ConnectDB();
			
			DeclarationTable declarationTable = new DeclarationTable(connectDB);
			isDup = declarationTable.isDupDeclarateion(declaration);

		}catch(Exception ex){
			logger.error("Error is Dup Declarateion : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return isDup;
	}
	
	public void insertDeclaration(Declaration declaration) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			DeclarationTable declarationTable = new DeclarationTable(connectDB);
			declarationTable.add(declaration);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Insert Declaration : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
	}
	
	public void removeDeclaration(Declaration declaration) throws Exception {
		
		ConnectDB connectDB = null;
		
		try{
			
			connectDB = new ConnectDB();
			connectDB.beginTransaction();
			
			DeclarationTable declarationTable = new DeclarationTable(connectDB);
			declarationTable.remove(declaration);
			
			connectDB.commit();
			
		}catch(Exception ex){
			
			connectDB.rollback();
			logger.error("Error Remove Declaration : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
	}
	
}
