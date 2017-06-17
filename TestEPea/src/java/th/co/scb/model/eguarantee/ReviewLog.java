/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.model.eguarantee;

/**
 *
 * @author s60982
 */
public class ReviewLog {
    private int gpGuaranteeId;
    private String reviewStatus;
    private String accountNo;
    private String reviewDtm;
    private String reviewBy;
    private String reviewReason;

    public ReviewLog(int gpGuaranteeId, String reviewStatus, String accountNo, String reviewDtm, String reviewBy, String reviewReason) {
        this.gpGuaranteeId = gpGuaranteeId;
        this.reviewStatus = reviewStatus;
        this.accountNo = accountNo;
        this.reviewDtm = reviewDtm;
        this.reviewBy = reviewBy;
        this.reviewReason = reviewReason;
    }
       
    public int getGpGuaranteeId() {
        return gpGuaranteeId;
    }

    public void setGpGuaranteeId(int gpGuaranteeId) {
        this.gpGuaranteeId = gpGuaranteeId;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getReviewDtm() {
        return reviewDtm;
    }

    public void setReviewDtm(String reviewDtm) {
        this.reviewDtm = reviewDtm;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }
    
    
}
