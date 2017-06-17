/**
 * 
 */
package th.co.scb.model.mq;

import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class MqRequestMessage {
	
	private	String	serviceName = Constants.EGUARANTEE_SYSTEM_NAME;
	private	String	userId = "@userid";
	private	String	session = "@sessionid";
	private	String	lang = "E";
	private	String	spName = Constants.EGUARANTEE_SYSTEM_NAME;
	private	String	rqUID;
	private	String	tUSER;// = Constants.EGUARANTEE_SYSTEM_NAME;//"9067R001";
	private	String	tBRANCH;
	private	String	tSOURCE = Constants.EGUARANTEE_SYSTEM_NAME;
	private	String	acctId;
	private	String	acctType = "LGGS";  
	
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the session
	 */
	public String getSession() {
		return session;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(String session) {
		this.session = session;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the spName
	 */
	public String getSpName() {
		return spName;
	}
	/**
	 * @param spName the spName to set
	 */
	public void setSpName(String spName) {
		this.spName = spName;
	}
	/**
	 * @return the rqUID
	 */
	public String getRqUID() {
		return rqUID;
	}
	/**
	 * @param rqUID the rqUID to set
	 */
	public void setRqUID(String rqUID) {
		this.rqUID = rqUID;
	}
	/**
	 * @return the tUSER
	 */
	public String gettUSER() {
		return tUSER;
	}
	/**
	 * @param tUSER the tUSER to set
	 */
	public void settUSER(String tUSER) {
		this.tUSER = tUSER;
	}
	/**
	 * @return the tBRANCH
	 */
	public String gettBRANCH() {
		return tBRANCH;
	}
	/**
	 * @param tBRANCH the tBRANCH to set
	 */
	public void settBRANCH(String tBRANCH) {
		this.tBRANCH = tBRANCH;
	}
	/**
	 * @return the tSOURCE
	 */
	public String gettSOURCE() {
		return tSOURCE;
	}
	/**
	 * @param tSOURCE the tSOURCE to set
	 */
	public void settSOURCE(String tSOURCE) {
		this.tSOURCE = tSOURCE;
	}
	/**
	 * @return the acctId
	 */
	public String getAcctId() {
		return acctId;
	}
	/**
	 * @param acctId the acctId to set
	 */
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	/**
	 * @return the acctType
	 */
	public String getAcctType() {
		return acctType;
	}
	/**
	 * @param acctType the acctType to set
	 */
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	
	
}
