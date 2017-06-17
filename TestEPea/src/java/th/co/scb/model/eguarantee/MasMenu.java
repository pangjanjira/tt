/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class MasMenu {
	private String menuId;
	private String title;
	private int orderNo;
	private String link;
	private String icon;
	private String funcCode;
	private String activeFlag;
	private Date updateDtm;
	
	public MasMenu() {
		super();
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the orderNo
	 */
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the funcCode
	 */
	public String getFuncCode() {
		return funcCode;
	}

	/**
	 * @param funcCode the funcCode to set
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the updateDtm
	 */
	public Date getUpdateDtm() {
		return updateDtm;
	}

	/**
	 * @param updateDtm the updateDtm to set
	 */
	public void setUpdateDtm(Date updateDtm) {
		this.updateDtm = updateDtm;
	}
	
	

}
