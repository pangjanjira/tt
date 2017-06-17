/**
 * 
 */
package th.co.scb.model.eguarantee;

/**
 * @author s51486
 *
 */
public class TranGPOffline {
	
	private int		id;
	private	String	transactionNo;
	private	String	xml;
	private	String	lgNo;
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	private int gpGuaranteeId;
	
	/**
	 * @param transactionNo
	 * @param xml
	 * @param lgNo
	 */
	
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Change ALSOffline process
	public TranGPOffline(String transactionNo, String xml, String lgNo, int gpGuaranteeId) {
		super();
		this.transactionNo = transactionNo;
		this.xml = xml;
		this.lgNo = lgNo;
		this.gpGuaranteeId = gpGuaranteeId;
	}
	
	/*public TranGPOffline(String transactionNo, String xml, String lgNo) {
		super();
		this.transactionNo = transactionNo;
		this.xml = xml;
		this.lgNo = lgNo;
	}*/
	
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
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}
	/**
	 * @param transactionNo the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}
	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
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

	public int getGpGuaranteeId() {
		return gpGuaranteeId;
	}

	public void setGpGuaranteeId(int gpGuaranteeId) {
		this.gpGuaranteeId = gpGuaranteeId;
	}
	
	
}
