package th.co.scb.db.eguarantee;

import java.util.List;
import java.util.Map;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/*
 * UR58060060 Phase 3.2 CADM Function
 */
public class MasUserAppTable {
	
    private ConnectDB connectDB;
    
    public MasUserAppTable() {
    	
    }
    
    public boolean checkAuthorization(String userId, String funcCode) {
    	this.connectDB = new ConnectDB();
    	System.out.println("MasUserAppTable<checkAuthorization> new connection : "+connectDB.hashCode());		
        boolean result = false;
        
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ua.* FROM ").append(Constants.TableName.MAS_USER_APP).append(" ua ");
            sql.append(" INNER JOIN ").append(Constants.TableName.MAS_LEVEL_FUNCTION).append(" lf ");
            sql.append(" ON lf.level_code=ua.level_code ");
            sql.append(" AND lf.active_flag='1' ");
            sql.append(" AND lf.func_code=? ");
            sql.append(" WHERE ua.user_id=? AND ua.active_flag='1' ");
            List<Map<String, Object>> rs = this.connectDB.queryList(sql.toString(), funcCode, userId);
            for (Map<String, Object> row :rs ){
            	result = true;
            	break;
            }

        } finally {
            if (this.connectDB != null) {
            	System.out.println("MasUserAppTable<checkAuthorization> close connection : "+connectDB.hashCode());
            	this.connectDB.close();
            }
        }
        return result;
    }
    
    public boolean checkApproverUser(String userId) {
    	this.connectDB = new ConnectDB();
    	System.out.println("MasUserAppTable<checkApproverUser> new connection : "+connectDB.hashCode());		
        boolean result = false;
        
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ua.* FROM ").append(Constants.TableName.MAS_USER_APP).append(" ua ");
            sql.append(" WHERE ua.user_id=? AND ua.active_flag='1' AND ");
            sql.append(" ua.level_code IN ('L00002','L00004','L00005') ");
            Map<String, Object> rs = this.connectDB.querySingle(sql.toString(), userId);
            if (rs != null){
            	result = true;
            }

        } finally {
            if (this.connectDB != null) {
            	System.out.println("MasUserAppTable<checkApproverUser> close connection : "+connectDB.hashCode());
            	this.connectDB.close();
            }
        }
        return result;
    }
    
    public boolean checkUserExisted(String userId) {
        this.connectDB = new ConnectDB();
    	System.out.println("MasUserAppTable<checkUserExisted> new connection : "+connectDB.hashCode());
        boolean result = false;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ua.* FROM ").append(Constants.TableName.MAS_USER_APP).append(" ua ");
            sql.append(" INNER JOIN ").append(Constants.TableName.MAS_USER_PROFILE).append(" up ");
            sql.append(" ON up.user_id=ua.user_id AND up.status='1' ");
            sql.append(" WHERE ua.user_id=? ");
            sql.append(" AND ua.active_flag='1' ");
            List<Map<String, Object>> rs = this.connectDB.queryList(sql.toString(), userId);
            for (Map<String, Object> row :rs ){
            	result = true;
            	break;
            }

        } finally {
            if (this.connectDB != null) {
            	System.out.println("MasUserAppTable<checkUserExisted> close connection : "+connectDB.hashCode());
            	this.connectDB.close();
            }
        }
        return result;
    }

}
