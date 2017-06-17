/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.math.BigDecimal;

/**
 * @author s51486
 *
 */
public class ProjectTax {
	
	private	int		id;
	private String	projectNo;
	private	String	vendorTaxId;
	private	int		seqNo;
	private String bondType;
	private String lgNo;
	private String txRef;
	private BigDecimal guaranteeAmt; //add by Malinee T. UR58100048 @20160104
	private String endDateStr; //add by Malinee T. UR58100048 @20160104
	
	
	/**
	 * @param projectNo
	 * @param taxNo
	 * @param seqNo
	 */
	public ProjectTax(String projectNo, String vendorTaxId, int seqNo,String bondType, String lgNo) {
		super();
		this.projectNo = projectNo;
		this.vendorTaxId = vendorTaxId;
		this.seqNo = seqNo;
		this.bondType = bondType; 
		this.lgNo = lgNo;
	}
	
	public ProjectTax(String txRef, String projectNo, String vendorTaxId, int seqNo,String bondType, String lgNo) {
		super();
		this.txRef = txRef;
		this.projectNo = projectNo;
		this.vendorTaxId = vendorTaxId;
		this.seqNo = seqNo;
		this.bondType = bondType; 
		this.lgNo = lgNo;
	}
	
	//add by Malinee T. UR58100048 @20160104
	public ProjectTax(String txRef,String projectNo, String vendorTaxId, int seqNo,
			String bondType, String lgNo,BigDecimal guaranteeAmt, String endDateStr) {
		super();
		this.txRef = txRef;
		this.projectNo = projectNo;
		this.vendorTaxId = vendorTaxId;
		this.seqNo = seqNo;
		this.bondType = bondType;
		this.lgNo = lgNo;
		this.guaranteeAmt = guaranteeAmt;
		this.endDateStr = endDateStr;
	}
	//add by Malinee T. UR58100048 @20160104
	public ProjectTax(String projectNo, String vendorTaxId, int seqNo,
			String bondType, String lgNo, BigDecimal guaranteeAmt,
			String endDateStr) {
		super();
		this.projectNo = projectNo;
		this.vendorTaxId = vendorTaxId;
		this.seqNo = seqNo;
		this.bondType = bondType;
		this.lgNo = lgNo;
		this.guaranteeAmt = guaranteeAmt;
		this.endDateStr = endDateStr;
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
	 * @return the projectNo
	 */
	public String getProjectNo() {
		return projectNo;
	}
	/**
	 * @param projectNo the projectNo to set
	 */
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	/**
	 * @return the taxNo
	 */

	/**
	 * @return the seqNo
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @return the vendorTaxId
	 */
	public String getVendorTaxId() {
		return vendorTaxId;
	}
	/**
	 * @param vendorTaxId the vendorTaxId to set
	 */
	public void setVendorTaxId(String vendorTaxId) {
		this.vendorTaxId = vendorTaxId;
	}
	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return the bondType
	 */
	public String getBondType() {
		return bondType;
	}
	/**
	 * @param bondType the bondType to set
	 */
	public void setBondType(String bondType) {
		this.bondType = bondType;
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
	
	public String getTxRef() {
		return txRef;
	}
	public void setTxRef(String txRef) {
		this.txRef = txRef;
	}

	public BigDecimal getGuaranteeAmt() {
		return guaranteeAmt;
	}

	public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	
	
	
	
}
