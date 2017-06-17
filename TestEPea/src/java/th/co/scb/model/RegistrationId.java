/**
 * 
 */
package th.co.scb.model;

/**
 * @author s51486
 *
 */
public class RegistrationId {
	
	private int		id;
	private	String	customsRegId;
	private String	bankRegId;
	
	
	
	public RegistrationId() {
		super();
		// -- Auto-generated constructor stub
	}
	
	public RegistrationId(int id, String customsRegId, String bankRegId) {
		super();
		this.id = id;
		this.customsRegId = customsRegId;
		this.bankRegId = bankRegId;
	
		// -- Auto-generated constructor stub
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
	 * @return the customsRegId
	 */
	public String getCustomsRegId() {
		return customsRegId;
	}
	/**
	 * @param customsRegId the customsRegId to set
	 */
	public void setCustomsRegId(String customsRegId) {
		this.customsRegId = customsRegId;
	}
	/**
	 * @return the bankRegId
	 */
	public String getBankRegId() {
		return bankRegId;
	}
	/**
	 * @param bankRegId the bankRegId to set
	 */
	public void setBankRegId(String bankRegId) {
		this.bankRegId = bankRegId;
	}
	
	

}
