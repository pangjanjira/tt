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
public class AccountALS {
	
	private int 	id;
	private	String	bank;
	private	String	branch;
	private	String	accountNo;
	private	String	taxId;
	private	String	accountName;
	private	String	ocCode;
	private	BigDecimal	lineAmt;
	private	BigDecimal	avaliableAmt;
        
    private	String	purpose;
    private	String	subPurpose;
    private	String	activeFlag;
    private	Date	createDtm;
    private	String	createDtmStr;
    private	String	createBy;
    private	Date	updateDtm;
    private	String	updateDtmStr;
    private	String	updateBy;

	
	public AccountALS() {
		super();
	}
	
	
	/**
	 * @param accountNo
	 */
	public AccountALS(String accountNo) {
		super();
		this.accountNo = accountNo;
	}
	
	public AccountALS(String bank, String branch, String ocCode, String accountNo,
			String taxId, String accountName, BigDecimal lineAmt, BigDecimal avaliableAmt) {
		this(0, bank, branch, ocCode, accountNo, taxId, accountName, lineAmt, avaliableAmt);
	}

	public AccountALS(int id, String bank, String branch,String ocCode, String accountNo,
			String taxId, String accountName, BigDecimal lineAmt, BigDecimal avaliableAmt) {
		super();
		this.id = id;
		this.bank = bank;
		this.branch = branch;
		this.accountNo = accountNo;
		this.taxId = taxId;
		this.accountName = accountName;
		this.ocCode = ocCode;
		this.lineAmt = lineAmt;
		this.avaliableAmt = avaliableAmt;
	}
		
	//add  by 61096 //update by Apichart H.@20150518
	public AccountALS(String bankid, String ocCode, String accountno, String taxId){
		this.accountNo = accountno;
		this.bank = bankid;
		this.ocCode = ocCode;
		this.taxId = taxId;
		
	}
	
	//add by 61962 

	public AccountALS(BigDecimal creditLine,BigDecimal availbal,String purposeCode,String purposeSubCode){
		this.lineAmt = creditLine;
		this.avaliableAmt = availbal;
		this.purpose = purposeCode;
		this.subPurpose = purposeSubCode;
	}
	
	public AccountALS(String bank, String branch, String ocCode, String accountNo,
			String taxId, String accountName, BigDecimal lineAmt, BigDecimal avaliableAmt, String purpose, String subPurpose, String activeFlag, Date createDtm, String createBy, Date updateDtm, String updateBy) {
		this(0, bank, branch, ocCode, accountNo, taxId, accountName, lineAmt, avaliableAmt, purpose, subPurpose, activeFlag, createDtm, createBy, updateDtm, updateBy);
	}

	public AccountALS(int id, String bank, String branch,String ocCode, String accountNo,
			String taxId, String accountName, BigDecimal lineAmt, BigDecimal avaliableAmt, String purpose, String subPurpose, String activeFlag, Date createDtm, String createBy, Date updateDtm, String updateBy) {
		super();
		this.id = id;
		this.bank = bank;
		this.branch = branch;
		this.accountNo = accountNo;
		this.taxId = taxId;
		this.accountName = accountName;
		this.ocCode = ocCode;
		this.lineAmt = lineAmt;
		this.avaliableAmt = avaliableAmt;
		this.purpose = purpose;
		this.subPurpose = subPurpose;
		this.activeFlag = activeFlag;
		this.createDtm = createDtm;
		this.createBy = createBy;
		this.updateDtm = updateDtm;
		this.updateBy = updateBy;
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
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
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
	 * @return the taxId
	 */
	public String getTaxId() {
		return taxId;
	}
	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	 * @return the lineAmt
	 */
	public BigDecimal getLineAmt() {
		return lineAmt;
	}
	/**
	 * @param lineAmt the lineAmt to set
	 */
	public void setLineAmt(BigDecimal lineAmt) {
		this.lineAmt = lineAmt;
	}
	/**
	 * @return the avaliableAmt
	 */
	public BigDecimal getAvaliableAmt() {
		return avaliableAmt;
	}
	/**
	 * @param avaliableAmt the avaliableAmt to set
	 */
	public void setAvaliableAmt(BigDecimal avaliableAmt) {
		this.avaliableAmt = avaliableAmt;
	}

        public String getActiveFlag() {
            return activeFlag;
        }

        public void setActiveFlag(String activeFlag) {
            this.activeFlag = activeFlag;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public Date getCreateDtm() {
            return createDtm;
        }

        public void setCreateDtm(Date createDtm) {
            this.createDtm = createDtm;
        }

        public String getCreateDtmStr() {
            return createDtmStr;
        }

        public void setCreateDtmStr(String createDtmStr) {
            this.createDtmStr = createDtmStr;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }
        
        public String getSubPurpose() {
            return subPurpose;
        }

        public void setSubPurpose(String subPurpose) {
            this.subPurpose = subPurpose;
        }


        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public Date getUpdateDtm() {
            return updateDtm;
        }

        public void setUpdateDtm(Date updateDtm) {
            this.updateDtm = updateDtm;
        }

        public String getUpdateDtmStr() {
            return updateDtmStr;
        }

        public void setUpdateDtmStr(String updateDtmStr) {
            this.updateDtmStr = updateDtmStr;
        }
	
	
}
