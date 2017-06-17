/**
 * 
 */
package th.co.scb.model;

/**
 * @author s51486
 *
 */
public class EStatus {
	
	private int		id;
	private	String	type;
	private	String	code;
	private	String	descEn;
	private String	descTh;
	
	public EStatus( String type, String code) {
		super();
		this.type = type;
		this.code = code;
	}
	
	public EStatus( String type, String code, String descEn,
			String descTh) {
		this(0, type, code, descEn, descTh);
	}
	
	
	public EStatus(int id, String type, String code, String descEn,
			String descTh) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.descEn = descEn;
		this.descTh = descTh;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the descEn
	 */
	public String getDescEn() {
		return descEn;
	}
	/**
	 * @param descEn the descEn to set
	 */
	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}
	/**
	 * @return the descTh
	 */
	public String getDescTh() {
		return descTh;
	}
	/**
	 * @param descTh the descTh to set
	 */
	public void setDescTh(String descTh) {
		this.descTh = descTh;
	}
	
	
}
