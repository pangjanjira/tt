/**
 * 
 */
package th.co.scb.model.mq;

/**
 * @author s51486
 *
 */
public class AccountALSRq extends MqRequestMessage{
	
	private String	EffDt;
	private String	TaxId;

	
	/**
	 * @return the taxId
	 */
	public String getTaxId() {
		return TaxId;
	}
	/**
	 * @return the effDt
	 */
	public String getEffDt() {
		return EffDt;
	}
	/**
	 * @param effDt the effDt to set
	 */
	public void setEffDt(String effDt) {
		EffDt = effDt;
	}
	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		TaxId = taxId;
	}
	
	

}
