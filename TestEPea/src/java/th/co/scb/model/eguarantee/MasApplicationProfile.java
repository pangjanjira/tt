/**
 * 
 */
package th.co.scb.model.eguarantee;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class MasApplicationProfile {
	private String appCode;
	private String appName;
	private String appDesc;
	private String contactDesc;
	
	public MasApplicationProfile() {
		super();
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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appDesc
	 */
	public String getAppDesc() {
		return appDesc;
	}

	/**
	 * @param appDesc the appDesc to set
	 */
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	/**
	 * @return the contactDesc
	 */
	public String getContactDesc() {
		return contactDesc;
	}

	/**
	 * @param contactDesc the contactDesc to set
	 */
	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}
	
	

}
