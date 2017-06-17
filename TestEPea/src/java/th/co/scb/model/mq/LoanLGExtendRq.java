/**
 * 
 */
package th.co.scb.model.mq;

/**
 * @author s69601
 * UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
 *
 */
public class LoanLGExtendRq extends MqRequestMessage{
	
	private String	docNo;		
	private String	effDt;
	private String	tranCode = "RT00";
	private String	tranFlag = "1";
	private String	extendDataElementName = "RT308";
	private String	extendDataElementValue = "";
	
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getEffDt() {
		return effDt;
	}
	public void setEffDt(String effDt) {
		this.effDt = effDt;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTranFlag() {
		return tranFlag;
	}
	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
	}
	public String getExtendDataElementName() {
		return extendDataElementName;
	}
	public void setExtendDataElementName(String extendDataElementName) {
		this.extendDataElementName = extendDataElementName;
	}
	public String getExtendDataElementValue() {
		return extendDataElementValue;
	}
	public void setExtendDataElementValue(String extendDataElementValue) {
		this.extendDataElementValue = extendDataElementValue;
	}
	
	
	

}
