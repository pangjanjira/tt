/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.model.eguarantee;
//add by Mayurachat L@22062015

import java.util.Date;

public class ControlAccount {
    private String accountNo;
    private String msgCode;
    private String msgDescription;
    private String active_flag;
    private Date create_dtm;
    private String create_dtm_str;
    private String create_by;
    private Date update_dtm;
    private String update_dtm_str;
	private String update_by;
    private Integer id;

    public ControlAccount(String accountNo, String msgCode, String msgDescription, String active_flag, Date create_dtm, String create_by, Date update_dtm, String update_by) {
        this.accountNo = accountNo;
        this.msgCode = msgCode;
        this.msgDescription = msgDescription;
        this.active_flag = active_flag;
        this.create_dtm = create_dtm;
        this.create_by = create_by;
        this.update_dtm = update_dtm;
        this.update_by = update_by;
    }

    public ControlAccount() {
        this.accountNo = null;
        this.msgCode = null;
        this.msgDescription = null;
        this.active_flag = null;
        this.create_dtm = null;
        this.create_by = null;
        this.update_dtm = null;
        this.update_by = null;
        this.create_dtm_str = null;
        this.update_dtm_str = null;
    }
    

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgDescription() {
        return msgDescription;
    }

    public void setMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public Date getCreate_dtm() {
        return create_dtm;
    }

    public void setCreate_dtm(Date create_dtm) {
        this.create_dtm = create_dtm;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getUpdate_dtm() {
        return update_dtm;
    }

    public void setUpdate_dtm(Date update_dtm) {
        this.update_dtm = update_dtm;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
	 * @return the create_dtm_str
	 */
	public String getCreate_dtm_str() {
		return create_dtm_str;
	}

	/**
	 * @param create_dtm_str the create_dtm_str to set
	 */
	public void setCreate_dtm_str(String create_dtm_str) {
		this.create_dtm_str = create_dtm_str;
	}

	/**
	 * @return the update_dtm_str
	 */
	public String getUpdate_dtm_str() {
		return update_dtm_str;
	}

	/**
	 * @param update_dtm_str the update_dtm_str to set
	 */
	public void setUpdate_dtm_str(String update_dtm_str) {
		this.update_dtm_str = update_dtm_str;
	}
    
          
    
}
