/**
 * 
 */
package th.co.scb.service.mq;

/**
 * @author s51486
 *
 */
public class EGuaranteeMQMessageException extends Throwable {
	
	private String reasonCode;
	private String reasonMessage;
	private String dataReason;
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage()+"EGuaranteeMQMessageException Reason Code "+this.reasonCode+":: Reason Message "+this.reasonMessage+"Data:"+this.dataReason;
	}
	
	public String getDataReason() {
		return dataReason;
	}

	public void setDataReason(String dataReason) {
		this.dataReason = dataReason;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	/**
	 * @return the reasonMessage
	 */
	public String getReasonMessage() {
		return reasonMessage;
	}
	/**
	 * @param reasonMessage the reasonMessage to set
	 */
	public void setReasonMessage(String reasonMessage) {
		this.reasonMessage = reasonMessage;
	}



}
