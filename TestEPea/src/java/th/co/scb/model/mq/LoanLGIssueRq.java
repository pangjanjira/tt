/**
 * 
 */
package th.co.scb.model.mq;

/**
 * @author s51486
 *
 */
public class LoanLGIssueRq extends MqRequestMessage{
	
	private String	DocNo;
	private String	CustRefNo;
	private String	DecrNo;
	private String	DecrSeq;
	private String	TranAmt;
	private String	IssueDt;
	private String	ArrivalDt;
	private String	TaxId;
	private String	OpenAcctData;
	
	//for cdata
	
	
	/**
	 * @return the docNo
	 */
	public String getDocNo() {
		return DocNo;
	}
	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(String docNo) {
		DocNo = docNo;
	}
	/**
	 * @return the custRefNo
	 */
	public String getCustRefNo() {
		return CustRefNo;
	}
	/**
	 * @param custRefNo the custRefNo to set
	 */
	public void setCustRefNo(String custRefNo) {
		CustRefNo = custRefNo;
	}
	/**
	 * @return the decrNo
	 */
	public String getDecrNo() {
		return DecrNo;
	}
	/**
	 * @param decrNo the decrNo to set
	 */
	public void setDecrNo(String decrNo) {
		DecrNo = decrNo;
	}
	/**
	 * @return the decrSeq
	 */
	public String getDecrSeq() {
		return DecrSeq;
	}
	/**
	 * @param decrSeq the decrSeq to set
	 */
	public void setDecrSeq(String decrSeq) {
		DecrSeq = decrSeq;
	}
	/**
	 * @return the tranAmt
	 */
	public String getTranAmt() {
		return TranAmt;
	}
	/**
	 * @param tranAmt the tranAmt to set
	 */
	public void setTranAmt(String tranAmt) {
		TranAmt = tranAmt;
	}
	/**
	 * @return the issueDt
	 */
	public String getIssueDt() {
		return IssueDt;
	}
	/**
	 * @param issueDt the issueDt to set
	 */
	public void setIssueDt(String issueDt) {
		IssueDt = issueDt;
	}
	/**
	 * @return the arrivalDt
	 */
	public String getArrivalDt() {
		return ArrivalDt;
	}
	/**
	 * @param arrivalDt the arrivalDt to set
	 */
	public void setArrivalDt(String arrivalDt) {
		ArrivalDt = arrivalDt;
	}
	/**
	 * @return the taxId
	 */
	public String getTaxId() {
		return TaxId;
	}
	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		TaxId = taxId;
	}
	/**
	 * @return the openAcctData
	 */
	public String getOpenAcctData() {
		return OpenAcctData;
	}
	/**
	 * @param openAcctData the openAcctData to set
	 */
	public void setOpenAcctData(String openAcctData) {
		OpenAcctData = openAcctData;
	}
	
	

}
