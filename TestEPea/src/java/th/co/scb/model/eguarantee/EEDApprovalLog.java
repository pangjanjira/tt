/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.model.eguarantee;

/**
 *
 * @author s53689
 */
 //UR58120031 created by Tana L. @12022016
public class EEDApprovalLog {
    
    private int gpGuaranteeId;
    private String approveStatus;
    private String accountNo;
    private String approveDtm;
    private String approveBy;
    private String approveReason;
	private String oldEndDate;
    private String newEndDate;
    
	public EEDApprovalLog(int gpGuaranteeId, String approveStatus,
			String accountNo, String approveDtm, String approveBy,
			String approveReason, String oldEndDate, String newEndDate) {

		this.gpGuaranteeId = gpGuaranteeId;
		this.approveStatus = approveStatus;
		this.accountNo = accountNo;
		this.approveDtm = approveDtm;
		this.approveBy = approveBy;
		this.approveReason = approveReason;
		this.oldEndDate = oldEndDate;
		this.newEndDate = newEndDate;
	}

	public int getGpGuaranteeId() {
		return gpGuaranteeId;
	}

	public void setGpGuaranteeId(int gpGuaranteeId) {
		this.gpGuaranteeId = gpGuaranteeId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getApproveDtm() {
		return approveDtm;
	}

	public void setApproveDtm(String approveDtm) {
		this.approveDtm = approveDtm;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveReason() {
		return approveReason;
	}

	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
	}

	public String getOldEndDate() {
		return oldEndDate;
	}

	public void setOldEndDate(String oldEndDate) {
		this.oldEndDate = oldEndDate;
	}

	public String getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(String newEndDate) {
		this.newEndDate = newEndDate;
	}
	
	
    
}
