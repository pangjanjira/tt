package th.co.scb.db;

import java.util.Date;
import java.util.Map;

import th.co.scb.model.DocRunning;
import th.co.scb.model.ETime;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;

public class DocRunningTable {
	
	private ConnectDB connectDB;

    public DocRunningTable(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }
    
    public DocRunning findDocRunning(String name) throws Exception{
    	
    	DocRunning docRunning = null;
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append(" select * from ").append(Constants.TableName.DOC_RUNNING);
    	sql.append(" where name = ? ");
    	
    	try{
	    	Map<String, Object> result = connectDB.querySingle(sql.toString(), name);
	    	
	    	if(result != null){
	    		docRunning = new DocRunning((String)result.get("name"), ((Long)result.get("running_no")).intValue());
	    	}
    	}catch (Exception e) {
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}
    	
    	return docRunning;
    }
    public DocRunning findUpdateDocRunning(String name) throws Exception{
    	
    	DocRunning docRunning = null;
    	ConnectDB conn = new ConnectDB();
    	System.out.println("DocRunningTable<findUpdateDocRunning> new connection : "+conn.hashCode());
    	
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append(" select * from ").append(Constants.TableName.DOC_RUNNING);
    	sql.append(" where name = ? ");
    	
    	try{
	    	Map<String, Object> result = conn.querySingle(sql.toString(), name);
	    	
	    	if(result != null){
	    		docRunning = new DocRunning((String)result.get("name"), ((Long)result.get("running_no")).intValue());
	    		updateDocRunning(name, conn);
	    	}
    	}
    	catch (Exception e) {
    	
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}finally{
    		if(conn != null){
    			System.out.println("DocRunningTable<findUpdateDocRunning> close connection : "+conn.hashCode());
    			conn.close();
    	    }
    		
    	}
    	
    	return docRunning;
    }
    
    public void updateDocRunning(String name) throws Exception{
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ").append(Constants.TableName.DOC_RUNNING);
    	sql.append(" set running_no = running_no + 1 ");
    	sql.append(" where name = ? ");
    	
    	try{
    		connectDB.execute(sql.toString(), name);
    	}catch (Exception e) {
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}
    	
    }
    
    public void updateDocRunning(String name, ConnectDB conn) throws Exception{
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ").append(Constants.TableName.DOC_RUNNING);
    	sql.append(" set running_no = running_no + 1 ");
    	sql.append(" where name = ? ");
    	
    	try{
    		conn.execute(sql.toString(), name);
    	}catch (Exception e) {
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}
    	
    }
    
    public String genBankTransactionNo(ETime eTime) throws Exception {
    	String bankTranNo = "";
    	
    	try{
    		
    		//get Running No
	    	DocRunning docRunning = findDocRunning(Constants.BANK_TRAN_NAME);
	    	
	    	Date dateTmp = null;
	    	if(eTime.isTimeOffHostALS()){
	    		dateTmp = eTime.getProcessDate();
	    	}else{
	    		dateTmp = new Date();
	    	}
	
	    	bankTranNo = "EGUA"+DateUtil.getDateFormatENYYYYMMDD(dateTmp) + fillZeroNumber(docRunning.getRunningNo(), Constants.BANK_TRAN_RUNNING_LENGTH) ;
	    	
	    	//update Running No +1
	    	updateDocRunning(Constants.BANK_TRAN_NAME);
	    	
    	}catch (Exception e) {
			// --: handle exception
    		bankTranNo = null;
    		throw  new Exception(e.getMessage());
		}
    	
    	return bankTranNo;
    }
    
    public String genLGNo(ETime eTime) throws Exception {
    	
    	String lgNo = "";
    	
    	try{
    		
	    	//get Running No
	    	DocRunning docRunning = findUpdateDocRunning(Constants.LG_NAME);
	    	
	    	Date dateTmp = null;
	    	if(eTime.isTimeOffHostALS()){
	    		dateTmp = eTime.getProcessDate();
	    	}else{ 
	    		dateTmp = new Date();
	    	}
	
	    	lgNo = DateUtil.getDateFormatENYYYYMMDD(dateTmp) + fillZeroNumber(docRunning.getRunningNo(), Constants.LG_RUNNING_LENGTH)+"00" ;
	    	System.out.println("LG : "  + lgNo + " >> " + lgNo.length());
	    	
	    	//update Running No +1
//	    	updateDocRunning(Constants.LG_NAME);
	    	
    	}catch (Exception e) {
			// --: handle exception
    		lgNo = null;
    		throw  new Exception(e.getMessage());
		}
    	
    	return lgNo;
    }
    
    public String fillZeroNumber(int number, int length){
    	
    	String str = Integer.toString(number);
    	int tmpLength = length-str.length();
    	for(int i = 0; i < tmpLength; i++){
    		str = "0" + str;
    	}
    	return str;
    }
    
    
    
}
