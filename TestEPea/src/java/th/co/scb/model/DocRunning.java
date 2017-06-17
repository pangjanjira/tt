package th.co.scb.model;

public class DocRunning {
	private	String	name;
	private	int		runningNo;

	public DocRunning(String name, int runningNo) {
		super();
		this.name = name;
		this.runningNo = runningNo;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the runningNo
	 */
	public int getRunningNo() {
		return runningNo;
	}
	/**
	 * @param runningNo the runningNo to set
	 */
	public void setRunningNo(int runningNo) {
		this.runningNo = runningNo;
	}
	
	
}
