package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.eguarantee.GPApprovalLogTable;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.util.ConnectDB;

/**
 * @author s27947
 *
 */
public class GPApprovalLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeService.class);
	
	public void insert(GPGuarantee gpGuarantee) throws Exception {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB();
			System.out.println("GPApprovalLogService<insert> new connection : "+connectDB.hashCode());
			GPApprovalLogTable gpApprovalLogTable = new GPApprovalLogTable(connectDB);
			gpApprovalLogTable.add(gpGuarantee);
		}catch(Exception ex) {
			logger.error("Error Insert GP_APPROVAL_LOG : "+ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally {
        	if (connectDB != null) {
        		System.out.println("GPApprovalLogService<insert> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
        }
		
	}	

}
