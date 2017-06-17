package th.co.scb.db.eguarantee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import th.co.scb.model.eguarantee.MasMenu;
import th.co.scb.model.eguarantee.MasUserProfile;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/*
 * UR58060060 Phase 3.2 CADM Function
 */
public class MasUserProfileTable {
	
    private ConnectDB connectDB;
    
    public MasUserProfileTable() {
    	
    }
    
    private List<MasMenu> getAuthorizedMenu(String userId, ConnectDB conn){
    	System.out.println("MasUserProfileTable<getAuthorizedMenu> receive connection : "+connectDB.hashCode());
    	List<MasMenu> result = new ArrayList<MasMenu>();
    	try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT m.* FROM ").append(Constants.TableName.MAS_MENU).append(" m ");
            sql.append("	INNER JOIN ").append(Constants.TableName.MAS_FUNCTION).append(" f ");
            sql.append(" 		ON f.func_code = m.func_code ");
            sql.append(" 		AND f.active_flag='1' ");
            sql.append("	INNER JOIN ").append(Constants.TableName.MAS_LEVEL_FUNCTION).append(" lf ");
            sql.append(" 		ON lf.func_code = f.func_code ");
            sql.append(" 		AND lf.active_flag='1' ");
            sql.append("	INNER JOIN ").append(Constants.TableName.MAS_USER_APP).append(" ua ");
            sql.append(" 		ON lf.level_code = ua.level_code ");
            sql.append(" 		AND ua.active_flag='1' ");
            sql.append(" 		AND ua.user_id=? ");
            sql.append(" WHERE m.active_flag='1' ");
            List<Map<String, Object>> rs = conn.queryList(sql.toString(), userId);
            for (Map<String, Object> row :rs ){
            	MasMenu item = new MasMenu();
            	item.setMenuId(row.get("menu_id") != null ? row.get("menu_id").toString() : "");
            	item.setTitle(row.get("title") != null ? row.get("title").toString() : "");
            	item.setLink(row.get("link") != null ? row.get("link").toString() : "");
            	item.setIcon(row.get("icon") != null ? row.get("icon").toString() : "");
            	item.setFuncCode(row.get("func_code") != null ? row.get("func_code").toString() : "");
            }
        } finally {
            //nothing
        	//conn is passed from getUserProfile
        }
    	return result;
    }
    
    
    public MasUserProfile getUserProfile(String userId) {
    	this.connectDB = new ConnectDB();
    	System.out.println("MasUserProfileTable<getUserProfile> new connection : "+connectDB.hashCode());
        MasUserProfile result = null;
        
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT up.* FROM ").append(Constants.TableName.MAS_USER_PROFILE).append(" up ");
            sql.append(" WHERE up.user_id=? ");
            sql.append(" AND up.status='1' ");
            List<Map<String, Object>> rs = this.connectDB.queryList(sql.toString(), userId);
            for (Map<String, Object> row :rs ){
            	result = new MasUserProfile();
            	result.setUserId(row.get("user_id") != null ? row.get("user_id").toString() : "");
            	result.setTitleTh(row.get("title_th") != null ? row.get("title_th").toString() : "");
            	result.setNameTh(row.get("name_th") != null ? row.get("name_th").toString() : "");
            	result.setTitleEn(row.get("title_en") != null ? row.get("title_en").toString() : "");
            	result.setNameEn(row.get("name_en") != null ? row.get("name_en").toString() : "");
            	result.setCompName(row.get("comp_name") != null ? row.get("comp_name").toString() : "");
            	result.setOcCode(row.get("oc_code") != null ? row.get("oc_code").toString() : "");
            	result.setOrgType(row.get("org_type") != null ? row.get("org_type").toString() : "");
            	result.setOrgNameEn(row.get("org_name_en") != null ? row.get("org_name_en").toString() : "");
            	result.setCorpNameEn(row.get("corp_name_en") != null ? row.get("corp_name_en").toString() : "");
            	result.setJobNameEn(row.get("job_name_en") != null ? row.get("job_name_en").toString() : "");
            	result.setEmail(row.get("email") != null ? row.get("email").toString() : "");
            	result.setTelNo(row.get("tel_no") != null ? row.get("tel_no").toString() : "");
            	result.setTeamCode(row.get("team_code") != null ? row.get("team_code").toString() : "");
            	result.setStatus(row.get("status") != null ? row.get("status").toString() : "");
            	result.setStatusDesc(row.get("status_desc") != null ? row.get("status_desc").toString() : "");
            	if(row.get("update_dtm") != null){
            		try{
            			result.setUpdateDtm((Date)row.get("update_dtm"));
            		}catch(Exception e){
            			//nothing
            		}
            	}
            	

            }
            
            //get authorized functions
            List<MasMenu> authorizedMenu = getAuthorizedMenu(userId, this.connectDB);
            if(result != null && authorizedMenu != null){
            	result.setAuthorizedMenu(authorizedMenu);
            }

        } finally {
            if (this.connectDB != null) {
            	System.out.println("MasUserProfileTable<getUserProfile> close connection : "+connectDB.hashCode());
            	this.connectDB.close();
            }
        }
        return result;
    }

}
