/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class LogAccess {
	private int id;
	private String appCode;
	private String funcCode;
	private String userId;
	private Date accessDtm;
	private String accessStatus;
	private String ipAddress;
	
	public LogAccess() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return the funcCode
	 */
	public String getFuncCode() {
		return funcCode;
	}

	/**
	 * @param funcCode the funcCode to set
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the accessDtm
	 */
	public Date getAccessDtm() {
		return accessDtm;
	}

	/**
	 * @param accessDtm the accessDtm to set
	 */
	public void setAccessDtm(Date accessDtm) {
		this.accessDtm = accessDtm;
	}

	/**
	 * @return the accessStatus
	 */
	public String getAccessStatus() {
		return accessStatus;
	}

	/**
	 * @param accessStatus the accessStatus to set
	 */
	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
