/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class MasFunction {
	private String appCode;
	private String funcCode;
	private String funcDesc;
	private String activeFlag;
	private Date updateDtm;
	
	public MasFunction() {
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
	 * @return the funcDesc
	 */
	public String getFuncDesc() {
		return funcDesc;
	}

	/**
	 * @param funcDesc the funcDesc to set
	 */
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
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
