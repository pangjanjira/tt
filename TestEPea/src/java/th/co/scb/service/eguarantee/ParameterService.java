package th.co.scb.service.eguarantee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import th.co.scb.model.eguarantee.ControlAccount;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Mayurachat L@23062015
public class ParameterService {
    
    private ConnectDB connectDB;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);

    public ParameterService() {
        //this.connectDB = connectDB;
    }

    public List<ControlAccount> findMsgCode() throws Exception {
        List<ControlAccount> returnList = new ArrayList<ControlAccount>();
		try{
			
			connectDB = new ConnectDB();
			ControlAccount control;

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT msg_code, msg_description ");
			sql.append(" FROM ").append(Constants.TableName.CONTROL_MSG_CODE);
			sql.append(" ORDER BY msg_code ASC ");

			System.out.println("sql message: "+sql);
			List<Map<String, Object>> result;
			result = connectDB.queryList(sql.toString());
			for (Map<String, Object> row : result) {
				control = new ControlAccount();
				control.setMsgCode(row.get("msg_code").toString());
				control.setMsgDescription(row.get("msg_description").toString());
				returnList.add(control);
			}
		}catch(Exception ex){
			logger.error("Error connect CONTROL_MSG_CODE table: "+ex.getMessage());
            throw ex;
		}finally {
        	if (connectDB != null) {
        		connectDB.close();
        	}
        }
        return returnList;
    }
}
