/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.db.eguarantee;

//New class by Mayurachat L.@20150618
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import th.co.scb.model.eguarantee.UserAuth;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

public class UserAuthTable {

    private ConnectDB connectDB;
    
    public UserAuthTable() {
    	
    }

    public List<UserAuth> findByUserAuth(UserAuth user_auth) {
    	this.connectDB = new ConnectDB();
    	System.out.println("UserAuthTable<findByUserAuth> new connection : "+connectDB.hashCode());
        List<UserAuth> returnList = new ArrayList<UserAuth>();
        UserAuth user = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" select user_id,menu_code,auth_level from ").append(Constants.TableName.USER_AUTH);
            sql.append(" where user_id = ? ");		 
            List<Map<String, Object>> rs = this.connectDB.queryList(sql.toString(), user_auth.getUserId());
             for (Map<String, Object> row :rs ){
                user = new UserAuth();
                user.setUserId(row.get("user_id").toString());
                user.setMenuCode(row.get("menu_code").toString());
                user.setAuthLevel(row.get("auth_level").toString());
                returnList.add(user);
            }

        } finally {
            if (this.connectDB != null) {
            	System.out.println("UserAuthTable<findByUserAuth> close connection : "+connectDB.hashCode());
            	this.connectDB.close();
            }
        }
        return returnList;
    }
}
