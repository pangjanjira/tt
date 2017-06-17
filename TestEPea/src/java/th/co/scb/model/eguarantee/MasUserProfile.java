/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;
import java.util.List;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class MasUserProfile {
	private String userId;
	private String titleTh;
	private String nameTh;
	private String titleEn;
	private String nameEn;
	private String compName;
	private String ocCode;
	private String orgType;
	private String orgNameEn;
	private String corpNameEn;
	private String jobNameEn;
	private String email;
	private String telNo;
	private String teamCode;
	private String status;
	private String statusDesc;
	private Date updateDtm;
	
	private List<MasMenu> authorizedMenu;

	public MasUserProfile() {
		super();
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
	 * @return the titleTh
	 */
	public String getTitleTh() {
		return titleTh;
	}

	/**
	 * @param titleTh the titleTh to set
	 */
	public void setTitleTh(String titleTh) {
		this.titleTh = titleTh;
	}

	/**
	 * @return the nameTh
	 */
	public String getNameTh() {
		return nameTh;
	}

	/**
	 * @param nameTh the nameTh to set
	 */
	public void setNameTh(String nameTh) {
		this.nameTh = nameTh;
	}

	/**
	 * @return the titleEn
	 */
	public String getTitleEn() {
		return titleEn;
	}

	/**
	 * @param titleEn the titleEn to set
	 */
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	/**
	 * @return the nameEn
	 */
	public String getNameEn() {
		return nameEn;
	}

	/**
	 * @param nameEn the nameEn to set
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the ocCode
	 */
	public String getOcCode() {
		return ocCode;
	}

	/**
	 * @param ocCode the ocCode to set
	 */
	public void setOcCode(String ocCode) {
		this.ocCode = ocCode;
	}

	/**
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return the orgNameEn
	 */
	public String getOrgNameEn() {
		return orgNameEn;
	}

	/**
	 * @param orgNameEn the orgNameEn to set
	 */
	public void setOrgNameEn(String orgNameEn) {
		this.orgNameEn = orgNameEn;
	}

	/**
	 * @return the corpNameEn
	 */
	public String getCorpNameEn() {
		return corpNameEn;
	}

	/**
	 * @param corpNameEn the corpNameEn to set
	 */
	public void setCorpNameEn(String corpNameEn) {
		this.corpNameEn = corpNameEn;
	}

	/**
	 * @return the jobNameEn
	 */
	public String getJobNameEn() {
		return jobNameEn;
	}

	/**
	 * @param jobNameEn the jobNameEn to set
	 */
	public void setJobNameEn(String jobNameEn) {
		this.jobNameEn = jobNameEn;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * @return the teamCode
	 */
	public String getTeamCode() {
		return teamCode;
	}

	/**
	 * @param teamCode the teamCode to set
	 */
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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

	/**
	 * @return the authorizedMenu
	 */
	public List<MasMenu> getAuthorizedMenu() {
		return authorizedMenu;
	}

	/**
	 * @param authorizedFunctions the authorizedMenu to set
	 */
	public void setAuthorizedMenu(List<MasMenu> authorizedMenu) {
		this.authorizedMenu = authorizedMenu;
	}
	
}
