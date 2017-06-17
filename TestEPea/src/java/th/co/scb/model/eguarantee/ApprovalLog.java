/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.model.eguarantee;

/**
 *
 * @author s49099
 */
public class ApprovalLog {
    
    private int gpGuaranteeId;
    private String approveStatus;
    private String accountNo;
    private String approveDtm;
    private String approveBy;
    private String approveReason;

    public ApprovalLog(int gpGuaranteeId, String approveStatus, String accountNo, String approveDtm, String approveBy, String approveReason) {
        this.gpGuaranteeId = gpGuaranteeId;
        this.approveStatus = approveStatus;
        this.accountNo = accountNo;
        this.approveDtm = approveDtm;
        this.approveBy = approveBy;
        this.approveReason = approveReason;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public String getApproveDtm() {
        return approveDtm;
    }

    public void setApproveDtm(String approveDtm) {
        this.approveDtm = approveDtm;
    }

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public int getGpGuaranteeId() {
        return gpGuaranteeId;
    }

    public void setGpGuaranteeId(int gpGuaranteeId) {
        this.gpGuaranteeId = gpGuaranteeId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    
}
