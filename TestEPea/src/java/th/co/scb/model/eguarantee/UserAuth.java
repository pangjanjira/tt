/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.model.eguarantee;

/**
 *
 * @author s49099
 */
public class UserAuth {
    private String userId;
    private String menuCode;
    private String authLevel;
    
    public UserAuth() {
    	
    }

    public UserAuth(String userId, String menuCode, String authLevel) {
        this.userId = userId;
        this.menuCode = menuCode;
        this.authLevel = authLevel;
    }

    public String getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(String authLevel) {
        this.authLevel = authLevel;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    
}
