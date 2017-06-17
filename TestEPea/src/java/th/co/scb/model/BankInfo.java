/**
 * 
 */
package th.co.scb.model;

/**
 * @author s51486
 *
 */
public class BankInfo {
	
	private int		id;
	private	String	bankCode;
	private String	branchCode;
	private String	branchName;
	private String 	bankAddr;
	
	public BankInfo() {
		super();
	}
	
	/**
	 * @param id
	 */
	public BankInfo(int id) {
		super();
		this.id = id;
	}
	
	
	/**
	 * @param bankCode
	 * @param branchCode
	 * @param branchName
	 * @param bankAddr
	 */
	public BankInfo(String bankCode, String branchCode, String branchName,
			String bankAddr) {
		super();
		this.bankCode = bankCode;
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.bankAddr = bankAddr;
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
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}
	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the bankAddr
	 */
	public String getBankAddr() {
		return bankAddr;
	}
	/**
	 * @param bankAddr the bankAddr to set
	 */
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	
	
}
