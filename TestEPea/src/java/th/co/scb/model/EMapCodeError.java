/**
 * 
 */
package th.co.scb.model;

/**
 * @author s51486
 *
 */
public class EMapCodeError {
	
	private int		id;
	private	String	eguaCode;
	private String	eguaCodeDesc;
	private	String	mqCode;
	
	public EMapCodeError(){
		super();
	}
	
	public EMapCodeError(int id, String eguaCode, String mqCode) {
		super();
		this.id = id;
		this.eguaCode = eguaCode;
		this.mqCode = mqCode;
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
	 * @return the eguaCode
	 */
	public String getEguaCode() {
		return eguaCode;
	}
	/**
	 * @param eguaCode the eguaCode to set
	 */
	public void setEguaCode(String eguaCode) {
		this.eguaCode = eguaCode;
	}
	/**
	 * @return the mqCode
	 */
	public String getMqCode() {
		return mqCode;
	}
	/**
	 * @param mqCode the mqCode to set
	 */
	public void setMqCode(String mqCode) {
		this.mqCode = mqCode;
	}

	/**
	 * @return the eguaCodeDesc
	 */
	public String getEguaCodeDesc() {
		return eguaCodeDesc;
	}

	/**
	 * @param eguaCodeDesc the eguaCodeDesc to set
	 */
	public void setEguaCodeDesc(String eguaCodeDesc) {
		this.eguaCodeDesc = eguaCodeDesc;
	}
	
	
}
