/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author s51486
 *
 */
public class GPGuarantee {
	
	private	int			id;
	private	String		txRef;
	private	String		dtm;
	private String		projNo;
	private String		deptCode;
	private String		vendorTaxId;
	private String		vendorName;
	private String		compId;
	private String		userId;
	private int			SeqNo;
	private String		considerDesc;
	private BigDecimal	considerMoney;
	private BigDecimal	guaranteeAmt;
	private	BigDecimal	amtReq;
	private String		contractNo;
	private String		contractDate;
	private BigDecimal	guaranteePrice;
	private BigDecimal	guaranteePercent;
	private BigDecimal	advanceGuaranteePrice;
	private BigDecimal	advancePayment;
	private BigDecimal	worksGuaranteePrice;
	private BigDecimal	worksGuaranteePercent;
	private String		collectionPhase;
	private String		endDate;
	private String		startDate;
	private String		bondType;
	private String		projName;
	private BigDecimal	projAmt;
	private String		ProjOwnName;
	private String		costCenter;
	private String		costCenterName;
	private String		documentNo;
	private String		documentDate;
	private String		expireDate;
	private String		statusLG;
	private String		statusDesc;
	private BigDecimal	appvAmt;
	private String		appvDate;
	private String		lgNo;
	private String		bankCode;
	private String		branchCode;
	private String		branchName;
	private String		bankAddr;
	private String		authSigName;
	private String		position;
	private String		witnessName1;
	private String		witnessName2;
	
	private	String		alsOnline;
	private	Date		processDate;
	private String		issueType;
	private	String		issueTypeDesc;

	private String		transactionStatus;
	private	String		msgCode;
	private	String		xmlOutput;
	private String		accountNo;
	private	String		bankTransactionNo;
	
	
	
	
	//tmp
	private boolean		isBatch;
	private String		ocCode; //add by Apichart H.@20150512
	
	//R58060012 : edit by bussara.b @20150611
	private String		reviewStatus;
	private Date		reviewDtm;
	private String		reviewBy;
	private String		reviewReason;
	private String		approveStatus;
	private Date		approveDtm;
	private String		approveDtmStr;
	private String		approveBy;
	private String		approveReason;
	
	//add by Narong W.@20150617
	private String      caMsgCode;
        private String      caMsgDescription;
        
        //add by Narong W.@20150824
        private String      branch;
    
    //add by Malinee T. UR58100048 @20151224
    private	String		egpAckStatus;
    private	Date		egpAckDtm;
    private	String		egpAckDtmStr;
    private	String		egpAckTranxId;
    private	String		egpAckCode;
    private	String		egpAckMsg;
    
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //added
    private	String		extendDate;
    private	String		oldEndDate;
    private	String		newEndDate;
    
    private	String		prevEndDate;
    
    //UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
  	private int seq;
  	private int resendCount;
  	private boolean latestExtend;
  	
  	
	public String getPrevEndDate() {
		return prevEndDate;
	}
	public void setPrevEndDate(String prevEndDate) {
		this.prevEndDate = prevEndDate;
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
	/**
	 * @return the extendDate
	 */
	public String getExtendDate() {
		return extendDate;
	}
	/**
	 * @param extendDate the extendDate to set
	 */
	public void setExtendDate(String extendDate) {
		this.extendDate = extendDate;
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
	public String getTxRef() {
		return txRef;
	}
	public void setTxRef(String txRef) {
		this.txRef = txRef;
	}
	public String getDtm() {
		return dtm;
	}
	public void setDtm(String dtm) {
		this.dtm = dtm;
	}
	public String getProjNo() {
		return projNo;
	}
	public void setProjNo(String projNo) {
		this.projNo = projNo;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getVendorTaxId() {
		return vendorTaxId;
	}
	public void setVendorTaxId(String vendorTaxId) {
		this.vendorTaxId = vendorTaxId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getSeqNo() {
		return SeqNo;
	}
	public void setSeqNo(int seqNo) {
		SeqNo = seqNo;
	}
	public String getConsiderDesc() {
		return considerDesc;
	}
	public void setConsiderDesc(String considerDesc) {
		this.considerDesc = considerDesc;
	}
	public BigDecimal getConsiderMoney() {
		return considerMoney;
	}
	public void setConsiderMoney(BigDecimal considerMoney) {
		this.considerMoney = considerMoney;
	}
	public BigDecimal getGuaranteeAmt() {
		return guaranteeAmt;
	}
	public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractDate() {
		return contractDate;
	}
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}
	public BigDecimal getGuaranteePrice() {
		return guaranteePrice;
	}
	public void setGuaranteePrice(BigDecimal guaranteePrice) {
		this.guaranteePrice = guaranteePrice;
	}
	public BigDecimal getGuaranteePercent() {
		return guaranteePercent;
	}
	public void setGuaranteePercent(BigDecimal guaranteePercent) {
		this.guaranteePercent = guaranteePercent;
	}
	public BigDecimal getAdvanceGuaranteePrice() {
		return advanceGuaranteePrice;
	}
	public void setAdvanceGuaranteePrice(BigDecimal advanceGuaranteePrice) {
		this.advanceGuaranteePrice = advanceGuaranteePrice;
	}
	public BigDecimal getAdvancePayment() {
		return advancePayment;
	}
	public void setAdvancePayment(BigDecimal advancePayment) {
		this.advancePayment = advancePayment;
	}
	public BigDecimal getWorksGuaranteePrice() {
		return worksGuaranteePrice;
	}
	public void setWorksGuaranteePrice(BigDecimal worksGuaranteePrice) {
		this.worksGuaranteePrice = worksGuaranteePrice;
	}
	public BigDecimal getWorksGuaranteePercent() {
		return worksGuaranteePercent;
	}
	public void setWorksGuaranteePercent(BigDecimal worksGuaranteePercent) {
		this.worksGuaranteePercent = worksGuaranteePercent;
	}
	public String getCollectionPhase() {
		return collectionPhase;
	}
	public void setCollectionPhase(String collectionPhase) {
		this.collectionPhase = collectionPhase;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getBondType() {
		return bondType;
	}
	public void setBondType(String bondType) {
		this.bondType = bondType;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public BigDecimal getProjAmt() {
		return projAmt;
	}
	public void setProjAmt(BigDecimal projAmt) {
		this.projAmt = projAmt;
	}
	public String getProjOwnName() {
		return ProjOwnName;
	}
	public void setProjOwnName(String projOwnName) {
		ProjOwnName = projOwnName;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getStatusLG() {
		return statusLG;
	}
	public void setStatusLG(String statusLG) {
		this.statusLG = statusLG;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public BigDecimal getAppvAmt() {
		return appvAmt;
	}
	public void setAppvAmt(BigDecimal appvAmt) {
		this.appvAmt = appvAmt;
	}
	public String getAppvDate() {
		return appvDate;
	}
	public void setAppvDate(String appvDate) {
		this.appvDate = appvDate;
	}
	public String getLgNo() {
		return lgNo;
	}
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	public String getAuthSigName() {
		return authSigName;
	}
	public void setAuthSigName(String authSigName) {
		this.authSigName = authSigName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getWitnessName1() {
		return witnessName1;
	}
	public void setWitnessName1(String witnessName1) {
		this.witnessName1 = witnessName1;
	}
	public String getWitnessName2() {
		return witnessName2;
	}
	public void setWitnessName2(String witnessName2) {
		this.witnessName2 = witnessName2;
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
	 * @return the issueType
	 */
	public String getIssueType() {
		return issueType;
	}
	/**
	 * @param issueType the issueType to set
	 */
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	
	/**
	 * @return the issueTypeDesc
	 */
	public String getIssueTypeDesc() {
		return issueTypeDesc;
	}
	/**
	 * @param issueTypeDesc the issueTypeDesc to set
	 */
	public void setIssueTypeDesc(String issueTypeDesc) {
		this.issueTypeDesc = issueTypeDesc;
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
	 * @return the msgCode
	 */
	public String getMsgCode() {
		return msgCode;
	}
	/**
	 * @param msgCode the msgCode to set
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	/**
	 * @return the xmlOutput
	 */
	public String getXmlOutput() {
		return xmlOutput;
	}
	/**
	 * @param xmlOutput the xmlOutput to set
	 */
	public void setXmlOutput(String xmlOutput) {
		this.xmlOutput = xmlOutput;
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
	 * @return the amtReq
	 */
	public BigDecimal getAmtReq() {
		return amtReq;
	}
	/**
	 * @param amtReq the amtReq to set
	 */
	public void setAmtReq(BigDecimal amtReq) {
		this.amtReq = amtReq;
	}
	
	
	/**
	 * @return the isBatch
	 */
	public boolean isBatch() {
		return isBatch;
	}
	/**
	 * @param isBatch the isBatch to set
	 */
	public void setIsBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// -- Auto-generated method stub
		String str = "txRef : " + this.txRef +
					"\n dtm : " + this.dtm+
					"\n projNo : " + this.projNo+
					"\n deptCode : " + this.deptCode+
					"\n vendorTaxId : " + this.vendorTaxId+
					"\n vendorName : " + this.vendorName+
					"\n compId : " + this.compId+
					"\n userId : " + this.userId+
					"\n SeqNo : " + this.SeqNo+
					"\n considerDesc : " + this.considerDesc+
					"\n considerMoney : " + this.considerMoney+
					"\n guaranteeAmt : " + this.guaranteeAmt+
					"\n contractNo : " + this.contractNo+
					"\n contractDate : " + this.contractDate+
					"\n guaranteePrice : " + this.guaranteePrice+
					"\n guaranteePercent : " + this.guaranteePercent+
					"\n advanceGuaranteePrice : " + this.advanceGuaranteePrice+
					"\n advancePayment : " + this.advancePayment+
					"\n worksGuaranteePrice : " + this.worksGuaranteePrice+
					"\n worksGuaranteePercent : " + this.worksGuaranteePercent+
					"\n collectionPhase : " + this.collectionPhase+
					"\n endDate : " + this.endDate+
					"\n startDate : " + this.startDate+
					"\n bondType : " + this.bondType+
					"\n projName : " + this.projName+
					"\n projAmt : " + this.projAmt+
					"\n ProjOwnName : " + this.ProjOwnName+
					"\n costCenter : " + this.costCenter+
					"\n costCenterName : " + this.costCenterName+
					"\n documentNo : " + this.documentNo+
					"\n documentDate : " + this.documentDate+
					"\n expireDate : " + this.expireDate+
					"\n statusLG : " + this.statusLG+
					"\n statusDesc : " + this.statusDesc+
					"\n appvAmt : " + this.appvAmt+
					"\n appvDate : " + this.appvDate+
					"\n lgNo : " + this.lgNo+
					"\n bankCode : " + this.bankCode+
					"\n branchCode : " + this.branchCode+
					"\n branchName : " + this.branchName+
					"\n bankAddr : " + this.bankAddr+
					"\n authSigName : " + this.authSigName+
					"\n position : " + this.position+
					"\n witnessName1 : " + this.witnessName1+
					"\n witnessName2 : " + this.witnessName2+
					"\n alsOnline : " + this.alsOnline +
					"\n processDate : " + this.processDate  +
					"\n issueType : " + this.issueType+
					"\n issueTypeDesc : " + this.issueTypeDesc+
					"\n transactionStatus : " + this.transactionStatus+
					"\n msgCode : " + this.msgCode+
					"\n accountNo : " + this.accountNo+
					"\n xmlOutput : " + this.xmlOutput+
					"\n bankTransactionNo : " + this.bankTransactionNo +
					"\n amtReq : " + this.amtReq +
					"\n isBatch : " + this.isBatch +
					"";
		
		return str;
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
	 * @return the reviewStatus
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}
	/**
	 * @param reviewStatus the reviewStatus to set
	 */
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	/**
	 * @return the reviewDtm
	 */
	public Date getReviewDtm() {
		return reviewDtm;
	}
	/**
	 * @param reviewDtm the reviewDtm to set
	 */
	public void setReviewDtm(Date reviewDtm) {
		this.reviewDtm = reviewDtm;
	}
	/**
	 * @return the reviewBy
	 */
	public String getReviewBy() {
		return reviewBy;
	}
	/**
	 * @param reviewBy the reviewBy to set
	 */
	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
	}
	/**
	 * @return the reviewReason
	 */
	public String getReviewReason() {
		return reviewReason;
	}
	/**
	 * @param reviewReason the reviewReason to set
	 */
	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}
	/**
	 * @return the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}
	/**
	 * @param approveStatus the approveStatus to set
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	/**
	 * @return the approveDtm
	 */
	public Date getApproveDtm() {
		return approveDtm;
	}
	/**
	 * @param approveDtm the approveDtm to set
	 */
	public void setApproveDtm(Date approveDtm) {
		this.approveDtm = approveDtm;
	}
	/**
	 * @return the approveDtmStr
	 */
	public String getApproveDtmStr() {
		return approveDtmStr;
	}
	/**
	 * @param approveDtmStr the approveDtmStr to set
	 */
	public void setApproveDtmStr(String approveDtmStr) {
		this.approveDtmStr = approveDtmStr;
	}
	/**
	 * @return the approveBy
	 */
	public String getApproveBy() {
		return approveBy;
	}
	/**
	 * @param approveBy the approveBy to set
	 */
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	/**
	 * @return the approveReason
	 */
	public String getApproveReason() {
		return approveReason;
	}
	/**
	 * @param approveReason the approveReason to set
	 */
	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
	}
	/**
	 * @param isBatch the isBatch to set
	 */
	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}
	
	public String getCaMsgCode() {
        return caMsgCode;
    }

    public void setCaMsgCode(String caMsgCode) {
        this.caMsgCode = caMsgCode;
    }

    public String getCaMsgDescription() {
        return caMsgDescription;
    }

    public void setCaMsgDescription(String caMsgDescription) {
        this.caMsgDescription = caMsgDescription;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
	public String getEgpAckStatus() {
		return egpAckStatus;
	}
	public void setEgpAckStatus(String egpAckStatus) {
		this.egpAckStatus = egpAckStatus;
	}
	public Date getEgpAckDtm() {
		return egpAckDtm;
	}
	public void setEgpAckDtm(Date egpAckDtm) {
		this.egpAckDtm = egpAckDtm;
	}
	public String getEgpAckDtmStr() {
		return egpAckDtmStr;
	}
	public void setEgpAckDtmStr(String egpAckDtmStr) {
		this.egpAckDtmStr = egpAckDtmStr;
	}
	public String getEgpAckTranxId() {
		return egpAckTranxId;
	}
	public void setEgpAckTranxId(String egpAckTranxId) {
		this.egpAckTranxId = egpAckTranxId;
	}
	public String getEgpAckCode() {
		return egpAckCode;
	}
	public void setEgpAckCode(String egpAckCode) {
		this.egpAckCode = egpAckCode;
	}
	public String getEgpAckMsg() {
		return egpAckMsg;
	}
	public void setEgpAckMsg(String egpAckMsg) {
		this.egpAckMsg = egpAckMsg;
	}
	/**
  	 * @return the seq
  	 */
  	public int getSeq() {
  		return seq;
  	}
  	/**
  	 * @param seq the seq to set
  	 */
  	public void setSeq(int seq) {
  		this.seq = seq;
  	}
	public int getResendCount() {
		return resendCount;
	}
	public void setResendCount(int resendCount) {
		this.resendCount = resendCount;
	}
	public boolean isLatestExtend() {
		return latestExtend;
	}
	public void setLatestExtend(boolean latestExtend) {
		this.latestExtend = latestExtend;
	}
    

	
}
