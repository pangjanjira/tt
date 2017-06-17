/**
 * 
 */
package th.co.scb.model.mq;

/**
 * @author s51486
 *
 */
public class LoanLGMaintainRq extends MqRequestMessage{
	
	private String	docNo;		
	private String	effDt;
	private String	tranCode = "8322";
	private String	tranAmt;	
	private String	limitType = "CCI";	
	private String	limitCode = "01";		
	private String	comment;		
	private String	chgStatusFlag;		
	private String	newStatus = "CL";
	private String	oldStatus = "AC";
	
	/**
	 * @return the docNo
	 */
	public String getDocNo() {
		return docNo;
	}
	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	/**
	 * @return the effDt
	 */
	public String getEffDt() {
		return effDt;
	}
	/**
	 * @param effDt the effDt to set
	 */
	public void setEffDt(String effDt) {
		this.effDt = effDt;
	}
	/**
	 * @return the tranCode
	 */
	public String getTranCode() {
		return tranCode;
	}
	/**
	 * @param tranCode the tranCode to set
	 */
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	/**
	 * @return the tranAmt
	 */
	public String getTranAmt() {
		return tranAmt;
	}
	/**
	 * @param tranAmt the tranAmt to set
	 */
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	/**
	 * @return the limitType
	 */
	public String getLimitType() {
		return limitType;
	}
	/**
	 * @param limitType the limitType to set
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	/**
	 * @return the limitCode
	 */
	public String getLimitCode() {
		return limitCode;
	}
	/**
	 * @param limitCode the limitCode to set
	 */
	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the chgStatusFlag
	 */
	public String getChgStatusFlag() {
		return chgStatusFlag;
	}
	/**
	 * @param chgStatusFlag the chgStatusFlag to set
	 */
	public void setChgStatusFlag(String chgStatusFlag) {
		this.chgStatusFlag = chgStatusFlag;
	}
	/**
	 * @return the newStatus
	 */
	public String getNewStatus() {
		return newStatus;
	}
	/**
	 * @param newStatus the newStatus to set
	 */
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	/**
	 * @return the oldStatus
	 */
	public String getOldStatus() {
		return oldStatus;
	}
	/**
	 * @param oldStatus the oldStatus to set
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}	
	
	
}
