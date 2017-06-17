/**
 * 
 */
package th.co.scb.model.eguarantee;

/**
 * @author s51486
 *
 */
public class TranOffline {
	private int		id;
	private	String	customsRefNo;
	private	String	xml;
	private	String	lgNo;
	
	public TranOffline(String customsRefNo, String xml, String lgNo) {
		this(0, customsRefNo, xml, lgNo);
	}
	
	public TranOffline(int id, String customsRefNo, String xml, String lgNo) {
		super();
		this.id = id;
		this.customsRefNo = customsRefNo;
		this.xml = xml;
		this.lgNo = lgNo;
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
	
}
