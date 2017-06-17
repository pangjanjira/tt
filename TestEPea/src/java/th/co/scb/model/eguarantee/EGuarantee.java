package th.co.scb.model.eguarantee;

import java.math.BigDecimal;
import java.util.Date;

public class EGuarantee {
	
	private	int			id;
	private	String		paymentMethod;
	private	String		customsName;
	private String		customsRef;
	private	BigDecimal	depositAmount;
	private String		declarationNo;
	private	String		declarationSeqNo;
	private	String		relateDate;
	private	String		debtorCompanyName;
	private	String		debtorBankAccNo;
	private	String		debtorCompanyTaxNo;
	private	String		bankGuaranteeNo;//LG No.
	private	String		transactionStatus;
	private String		messageCode;
	private	String		requestDate;
	
	private	String		alsOnline;
	private	Date		processDate;
	private	String		customsTransDate;
	private	String		originalCustomsRef;
	
	//-------------- temp ----------
	private	String	messageRoot;
	private	String	messageDesciption;
	
	private	String	numberOfTrans;
	private String	debtorBankCode;
	private	String	debtorBankBranchCode;
	private	String	debtorCompanyBranch;
	
	private	String	paymentMethodDesc;
	private	String	transType;

	private	String	customsBankAcc;
	private String	customsBankCode;
	private	String	customsBranchCode;
	
	private	String	informPort;
	private	String	informNo;
	private	String	informDate;
	
	private	Date	bookingDate;
	private	String	bankTransactionNo;
	
	private	String	eguaranteeStatus;
	
	private	String	docName;
	private String	docReturnName;
	private	BigDecimal refundAmt;
	private String xmlOutput;
	
	//----------------------------

	/**
	 * @return the customsRef
	 */
	public String getCustomsRef() {
		return customsRef;
	}
	/**
	 * @param customsRef the customsRef to set
	 */
	public void setCustomsRef(String customsRef) {
		this.customsRef = customsRef;
	}
	
	/**
	 * @return the customsTransDate
	 */
	public String getCustomsTransDate() {
		return customsTransDate;
	}
	/**
	 * @param customsTransDate the customsTransDate to set
	 */
	public void setCustomsTransDate(String customsTransDate) {
		this.customsTransDate = customsTransDate;
	}

	/**
	 * @return the numberOfTrans
	 */
	public String getNumberOfTrans() {
		return numberOfTrans;
	}
	/**
	 * @param numberOfTrans the numberOfTrans to set
	 */
	public void setNumberOfTrans(String numberOfTrans) {
		this.numberOfTrans = numberOfTrans;
	}
	/**
	 * @return the depositAmount
	 */
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	/**
	 * @param depositAmount the depositAmount to set
	 */
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	/**
	 * @return the debtorBankCode
	 */
	public String getDebtorBankCode() {
		return debtorBankCode;
	}
	/**
	 * @param debtorBankCode the debtorBankCode to set
	 */
	public void setDebtorBankCode(String debtorBankCode) {
		this.debtorBankCode = debtorBankCode;
	}
	/**
	 * @return the debtorBankBranchCode
	 */
	public String getDebtorBankBranchCode() {
		return debtorBankBranchCode;
	}
	/**
	 * @param debtorBankBranchCode the debtorBankBranchCode to set
	 */
	public void setDebtorBankBranchCode(String debtorBankBranchCode) {
		this.debtorBankBranchCode = debtorBankBranchCode;
	}
	/**
	 * @return the debtorCompanyName
	 */
	public String getDebtorCompanyName() {
		return debtorCompanyName;
	}
	/**
	 * @param debtorCompanyName the debtorCompanyName to set
	 */
	public void setDebtorCompanyName(String debtorCompanyName) {
		this.debtorCompanyName = debtorCompanyName;
	}
	/**
	 * @return the debtorCompanyTaxNo
	 */
	public String getDebtorCompanyTaxNo() {
		return debtorCompanyTaxNo;
	}
	/**
	 * @param debtorCompanyTaxNo the debtorCompanyTaxNo to set
	 */
	public void setDebtorCompanyTaxNo(String debtorCompanyTaxNo) {
		this.debtorCompanyTaxNo = debtorCompanyTaxNo;
	}
	/**
	 * @return the debtorCompanyBranch
	 */
	public String getDebtorCompanyBranch() {
		return debtorCompanyBranch;
	}
	/**
	 * @param debtorCompanyBranch the debtorCompanyBranch to set
	 */
	public void setDebtorCompanyBranch(String debtorCompanyBranch) {
		this.debtorCompanyBranch = debtorCompanyBranch;
	}
	/**
	 * @return the debtorBankAccNo
	 */
	public String getDebtorBankAccNo() {
		return debtorBankAccNo;
	}
	/**
	 * @param debtorBankAccNo the debtorBankAccNo to set
	 */
	public void setDebtorBankAccNo(String debtorBankAccNo) {
		this.debtorBankAccNo = debtorBankAccNo;
	}
	/**
	 * @return the declarationNo
	 */
	public String getDeclarationNo() {
		return declarationNo;
	}
	/**
	 * @param declarationNo the declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}
	/**
	 * @return the declarationSeqNo
	 */
	public String getDeclarationSeqNo() {
		return declarationSeqNo;
	}
	/**
	 * @param declarationSeqNo the declarationSeqNo to set
	 */
	public void setDeclarationSeqNo(String declarationSeqNo) {
		this.declarationSeqNo = declarationSeqNo;
	}

	/**
	 * @return the originalCustomsRef
	 */
	public String getOriginalCustomsRef() {
		return originalCustomsRef;
	}
	/**
	 * @param originalCustomsRef the originalCustomsRef to set
	 */
	public void setOriginalCustomsRef(String originalCustomsRef) {
		this.originalCustomsRef = originalCustomsRef;
	}
	/**
	 * @return the bankGuaranteeNo
	 */
	public String getBankGuaranteeNo() {
		return bankGuaranteeNo;
	}
	/**
	 * @param bankGuaranteeNo the bankGuaranteeNo to set
	 */
	public void setBankGuaranteeNo(String bankGuaranteeNo) {
		this.bankGuaranteeNo = bankGuaranteeNo;
	}
	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}
	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}
	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}
	/**
	 * @return the requestDate
	 */
	public String getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return the customsBankAcc
	 */
	public String getCustomsBankAcc() {
		return customsBankAcc;
	}
	/**
	 * @param customsBankAcc the customsBankAcc to set
	 */
	public void setCustomsBankAcc(String customsBankAcc) {
		this.customsBankAcc = customsBankAcc;
	}
	/**
	 * @return the customsBankCode
	 */
	public String getCustomsBankCode() {
		return customsBankCode;
	}
	/**
	 * @param customsBankCode the customsBankCode to set
	 */
	public void setCustomsBankCode(String customsBankCode) {
		this.customsBankCode = customsBankCode;
	}
	/**
	 * @return the customsBranchCode
	 */
	public String getCustomsBranchCode() {
		return customsBranchCode;
	}
	/**
	 * @param customsBranchCode the customsBranchCode to set
	 */
	public void setCustomsBranchCode(String customsBranchCode) {
		this.customsBranchCode = customsBranchCode;
	}
	/**
	 * @return the informPort
	 */
	public String getInformPort() {
		return informPort;
	}
	/**
	 * @param informPort the informPort to set
	 */
	public void setInformPort(String informPort) {
		this.informPort = informPort;
	}
	/**
	 * @return the informNo
	 */
	public String getInformNo() {
		return informNo;
	}
	/**
	 * @param informNo the informNo to set
	 */
	public void setInformNo(String informNo) {
		this.informNo = informNo;
	}
	/**
	 * @return the informDate
	 */
	public String getInformDate() {
		return informDate;
	}
	/**
	 * @param informDate the informDate to set
	 */
	public void setInformDate(String informDate) {
		this.informDate = informDate;
	}
	/**
	 * @return the relateDate
	 */
	public String getRelateDate() {
		return relateDate;
	}
	/**
	 * @param relateDate the relateDate to set
	 */
	public void setRelateDate(String relateDate) {
		this.relateDate = relateDate;
	}
	/**
	 * @return the messageRoot
	 */
	public String getMessageRoot() {
		return messageRoot;
	}
	/**
	 * @param messageRoot the messageRoot to set
	 */
	public void setMessageRoot(String messageRoot) {
		this.messageRoot = messageRoot;
	}
	
	
	/**
	 * @return the paymentMethodDesc
	 */
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}
	/**
	 * @param paymentMethodDesc the paymentMethodDesc to set
	 */
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}

	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	/**
	 * @return the eguaranteeStatus
	 */
	public String getEguaranteeStatus() {
		return eguaranteeStatus;
	}
	/**
	 * @param eguaranteeStatus the eguaranteeStatus to set
	 */
	public void setEguaranteeStatus(String eguaranteeStatus) {
		this.eguaranteeStatus = eguaranteeStatus;
	}
	
	
	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}
	/**
	 * @param messageCode the messageCode to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	
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
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the bankTransactionNo
	 */
	public String getBankTransactionNo() {
		return bankTransactionNo;
	}
	/**
	 * @param bankTransactionNo the bankTransactionNo to set
	 */
	public void setBankTransactionNo(String bankTransactionNo) {
		this.bankTransactionNo = bankTransactionNo;
	}

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}
	/**
	 * @param docName the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	
	/**
	 * @return the docReturnName
	 */
	public String getDocReturnName() {
		return docReturnName;
	}
	/**
	 * @param docReturnName the docReturnName to set
	 */
	public void setDocReturnName(String docReturnName) {
		this.docReturnName = docReturnName;
	}
	
	
	/**
	 * @return the refundAmt
	 */
	public BigDecimal getRefundAmt() {
		return refundAmt;
	}
	/**
	 * @param refundAmt the refundAmt to set
	 */
	public void setRefundAmt(BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
	}
	
	/**
	 * @return the alsOnline
	 */
	public String getAlsOnline() {
		return alsOnline;
	}
	
	/**
	 * @param alsOnline the alsOnline to set
	 */
	public void setAlsOnline(String alsOnline) {
		this.alsOnline = alsOnline;
	}
	
	/**
	 * @return the processDate
	 */
	public Date getProcessDate() {
		return processDate;
	}
	
	/**
	 * @param processDate the processDate to set
	 */
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	
	
	/**
	 * @return the customsName
	 */
	public String getCustomsName() {
		return customsName;
	}
	/**
	 * @param customsName the customsName to set
	 */
	public void setCustomsName(String customsName) {
		this.customsName = customsName;
	}

	/**
	 * @return the messageDesciption
	 */
	public String getMessageDesciption() {
		return messageDesciption;
	}
	/**
	 * @param messageDesciption the messageDesciption to set
	 */
	public void setMessageDesciption(String messageDesciption) {
		this.messageDesciption = messageDesciption;
	}
	
	
	public String getXmlOutput() {
		return xmlOutput;
	}
	public void setXmlOutput(String xmlOutput) {
		this.xmlOutput = xmlOutput;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// -- Auto-generated method stub
		String str = "customsRef : " + this.customsRef +
					"\n customsName : " + this.customsName+
					"\n custmosTransDate : " + this.customsTransDate+
					"\n numberOfTrans : " + this.numberOfTrans +
					"\n depositAmount : " + this.depositAmount +
					"\n debtorBankCode : " + this.debtorBankCode +
					"\n debtorBankBranchCode : " + this.debtorBankBranchCode +
					"\n debtorCompanyName : " + this.debtorCompanyName +
					"\n debtorCompanyTaxNo : " + this.debtorCompanyTaxNo +
					"\n debtorCompanyBranch : " + this.debtorCompanyBranch +
					"\n debtorBankAccNo : " + this.debtorBankAccNo +
					"\n declarationNo : " + this.declarationNo +
					"\n declarationSeqNo : " + this.declarationSeqNo +
					"\n relateDate : " + this.relateDate +
					"\n originalCustomsRef : " + this.originalCustomsRef +
					"\n bankGuaranteeNo : " + this.bankGuaranteeNo+
					"\n paymentMethod : " + this.paymentMethod +
					"\n transType : " + this.transType +
					"\n requestDate : " + this.requestDate +
					"\n customsBankAcc : " + this.customsBankAcc +
					"\n customsBankCode : " + this.customsBankCode +
					"\n customsBranchCode : " + this.customsBranchCode +
					"\n informPort : " + this.informPort +
					"\n informNo : " + this.informNo +
					"\n informDate : " + this.informDate +
					"\n messageRoot : " + this.messageRoot +
					"\n paymentMethodDesc : " + this.paymentMethodDesc +
					"\n transactionStatus : " + this.transactionStatus +
					"\n eguaranteeStatus : " + this.eguaranteeStatus +
					"\n messageCode : " + this.messageCode +
					"\n id : " + this.id +
					"\n bankTransactionNo : " + this.bankTransactionNo +
					"\n bookingDate : " + this.bookingDate +
					"\n docName : " + this.docName +
					"\n docReturnName : " + this.docReturnName +
					"\n refundAmt : " + this.refundAmt +
					"\n alsOnline : " + this.alsOnline +
					"\n processDate : " + this.processDate  +
					"\n xmlOutput : " + this.xmlOutput +
					"";
		
		return str;
	}
	
	
}
