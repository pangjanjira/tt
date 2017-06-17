/**
 * 
 */
package th.co.scb.model.mq;

/**
 * @author s51486
 *
 */
public class AccountALSResponse {
	
	private String  customsRefNo;
	private	String	statusCode;
	private String	statusDesc;
	private String	acctId;
	private String	bankId;
	private String	acctCur;
	private String	prodCode;
	private String	branchId;
	private String  ocCode;
	
	/**
	 * @return the customsRefNo
	 */
	public String getCustomsRefNo() {
		return customsRefNo;
	}
	/**
	 * @param customsRefNo the customsRefNo to set
	 */
	public void setCustomsRefNo(String customsRefNo) {
		this.customsRefNo = customsRefNo;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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
	 * @return the acctId
	 */
	public String getAcctId() {
		return acctId;
	}
	/**
	 * @param acctId the acctId to set
	 */
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	/**
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	/**
	 * @return the acctCur
	 */
	public String getAcctCur() {
		return acctCur;
	}
	/**
	 * @param acctCur the acctCur to set
	 */
	public void setAcctCur(String acctCur) {
		this.acctCur = acctCur;
	}
	/**
	 * @return the prodCode
	 */
	public String getProdCode() {
		return prodCode;
	}
	/**
	 * @param prodCode the prodCode to set
	 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	
}
