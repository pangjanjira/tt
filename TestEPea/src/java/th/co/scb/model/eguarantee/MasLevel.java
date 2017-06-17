/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class MasLevel {
	private String appCode;
	private String levelCode;
	private String levelDesc;
	private String activeFlag;
	private Date updateDtm;
	
	public MasLevel() {
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
	 * @return the levelCode
	 */
	public String getLevelCode() {
		return levelCode;
	}

	/**
	 * @param levelCode the levelCode to set
	 */
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	/**
	 * @return the levelDesc
	 */
	public String getLevelDesc() {
		return levelDesc;
	}

	/**
	 * @param levelDesc the levelDesc to set
	 */
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the updateDtm
	 */
	public Date getUpdateDtm() {
		return updateDtm;
	}

	/**
	 * @param updateDtm the updateDtm to set
	 */
	public void setUpdateDtm(Date updateDtm) {
		this.updateDtm = updateDtm;
	}
	
	
}
