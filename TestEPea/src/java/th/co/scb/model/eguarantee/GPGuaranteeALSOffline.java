/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.math.BigDecimal;
/**
 * @author s51486
 *
 */
public class GPGuaranteeALSOffline {
	
	private	int		id;
	private	String	txRef;
	private	String	accountNo;
	private	String	status;
	private	String	resCode;
	private	String	resMsg;
	private	String	lgNo;
	private	String	projName;
	private	String	projOwnName;
	private	BigDecimal	guaranteeAmt;
	
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
	 * @return the txRef
	 */
	public String getTxRef() {
		return txRef;
	}
	/**
	 * @param txRef the txRef to set
	 */
	public void setTxRef(String txRef) {
		this.txRef = txRef;
	}
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	 * @return the resCode
	 */
	public String getResCode() {
		return resCode;
	}
	/**
	 * @param resCode the resCode to set
	 */
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	/**
	 * @return the resMsg
	 */
	public String getResMsg() {
		return resMsg;
	}
	/**
	 * @param resMsg the resMsg to set
	 */
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	/**
	 * @return the lgNo
	 */
	public String getLgNo() {
		return lgNo;
	}
	/**
	 * @param lgNo the lgNo to set
	 */
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getProjOwnName() {
		return projOwnName;
	}
	public void setProjOwnName(String projOwnName) {
		this.projOwnName = projOwnName;
	}
	public BigDecimal getGuaranteeAmt() {
		return guaranteeAmt;
	}
	public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}


	

}
