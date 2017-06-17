/**
 * 
 */
package th.co.scb.model.eguarantee;

import java.util.Date;

/**
 * UR58060060 Phase 3.2 CADM Function
 *
 */
public class LogCadmProcess {
	private int id;
	private String fileName;
	private Date processDate;
	private String processName;
	private String result;
	private String resultMessage;
	private Date startDtm;
	private Date endDtm;
	
	public LogCadmProcess() {
		super();
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the processDate
	 */
	public Date getProcessDate() {
		return processDate;
	}

	/**
	 * @param processDate the processDate to set
	 */
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the resultMessage
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * @param resultMessage the resultMessage to set
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * @return the startDtm
	 */
	public Date getStartDtm() {
		return startDtm;
	}

	/**
	 * @param startDtm the startDtm to set
	 */
	public void setStartDtm(Date startDtm) {
		this.startDtm = startDtm;
	}

	/**
	 * @return the endDtm
	 */
	public Date getEndDtm() {
		return endDtm;
	}

	/**
	 * @param endDtm the endDtm to set
	 */
	public void setEndDtm(Date endDtm) {
		this.endDtm = endDtm;
	}
	
	
	
}
