/**
 * 
 */
package th.co.scb.model;

import java.util.Date;

/**
 * @author s51486
 *
 */
public class ETime {
	
	private	int		id;
	private	String	systemName;
	private	String	startTime;
	private	String	endTime;
	private	Date	processDate;
	private	boolean isTimeOffHostALS;
	
	public ETime(String	systemName, String startTime, String endTime, Date processDate) {
		this(0, systemName, startTime, endTime, processDate);
	}
	
	public ETime(int id, String	systemName, String startTime, String endTime, Date processDate) {
		super();
		this.id = id;
		this.systemName = systemName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.processDate = processDate;
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
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
	 * @return the isTimeOffHostALS
	 */
	public boolean isTimeOffHostALS() {
		return isTimeOffHostALS;
	}

	/**
	 * @param isTimeOffHostALS the isTimeOffHostALS to set
	 */
	public void setTimeOffHostALS(boolean isTimeOffHostALS) {
		this.isTimeOffHostALS = isTimeOffHostALS;
	}
	
	
}
