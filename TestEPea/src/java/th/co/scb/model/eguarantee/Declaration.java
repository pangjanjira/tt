/**
 * 
 */
package th.co.scb.model.eguarantee;

/**
 * @author s51486
 *
 */
public class Declaration {
	
	private	int		id;
	private	String	declarationNo;
	private	String	declarationSeq;

	public Declaration(String declarationNo, String declarationSeq) {
		this(0, declarationNo, declarationSeq);
	}
	
	public Declaration(int id, String declarationNo, String declarationSeq) {
		super();
		this.id = id;
		this.declarationNo = declarationNo;
		this.declarationSeq = declarationSeq;
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
	 * @return the declarationNo
	 */
	public String getDeclarationNo() {
		return declarationNo;
	}
	/**
	 * @param declarationNo the declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}
	/**
	 * @return the declarationSeq
	 */
	public String getDeclarationSeq() {
		return declarationSeq;
	}
	/**
	 * @param declarationSeq the declarationSeq to set
	 */
	public void setDeclarationSeq(String declarationSeq) {
		this.declarationSeq = declarationSeq;
	}
	
	
}
