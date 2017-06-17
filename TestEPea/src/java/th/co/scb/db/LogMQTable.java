/**
 * 
 */
package th.co.scb.db;

import th.co.scb.model.LogMQ;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class LogMQTable {
	
	private ConnectDB connectDB;

    public LogMQTable(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }
    
    public void add(LogMQ logMQ){
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into ").append(Constants.TableName.LOG_MQ)
    		.append(" (customs_ref_no, tran_date, return_code, send_id, q_manager, ")
    		.append(" get_qname, put_qname, rq_uid, method_name, xml_input, xml_output )");
    	sql.append(" values(?,now(),?,?,?, ?,?,?,?,?,?) ");
    	
    	System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString(), logMQ.getCustomsRefNo(), 
    											logMQ.getReturnCode(),
    											logMQ.getSendId(),
    											logMQ.getqManager(),
    											
    											logMQ.getGetQname(),
    											logMQ.getPutQname(),
    											logMQ.getRqUid(),
    											logMQ.getMethodName(),
    											logMQ.getXmlInput().getBytes(), 
    											logMQ.getXmlOutput().getBytes());
    	
    	logMQ.setId(id);
    	
    }

}
