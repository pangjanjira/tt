/**
 * 
 */
package th.co.scb.model;

/**
 * @author s51486
 *
 */
public class LogEBXML {
	
	private	long	id;
	private	String	type;
	private String 	fileName;
	private	String	customsRefNo;
	private	String	xmlInput;
	private	String	xmlOutput;
	private	String	lgNo;
	private	String	alsOnline;
	
	//add by vorakorn.j@17032015 for GP
	private String	xmlResponseToEbxml;
	private String	xmlResponseFromEbxml;
	private	String	flagCust;
	
	//tmp
	private	String	transDate;
	private	String	issueType;
	
	//add by siwat.n@20150625
	private	String	inputDatetimeStr;
	private	String	outputDatetimeStr;
	private	String	responseToEbxmlDatetimeStr;
	private	String	responseFromEbxmlDatetimeStr;

	public LogEBXML(){
		super();
	}
	
	public LogEBXML( String type, String customsRefNo, String xmlInput,
			String xmlOutput, String alsOnline, String fileName) {
		this(0, type, customsRefNo, xmlInput, xmlOutput, alsOnline, fileName);
	}
	
	
	public LogEBXML(int id, String type, String customsRefNo, String xmlInput,
			String xmlOutput, String alsOnline, String fileName) {
		super();
		this.id = id;
		this.type = type;
		this.customsRefNo = customsRefNo;
		this.xmlInput = xmlInput;
		this.xmlOutput = xmlOutput;
		this.alsOnline = alsOnline;
		this.fileName = fileName;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the customsRefNo
	 */
	public String getCustomsRefNo() {
		return customsRefNo;
	}
	/**
	 * @param customsRefNo the customsRefNo to set
	 */
	public void setCustomsRefNo(String customsRefNo) {
		this.customsRefNo = customsRefNo;
	}
	/**
	 * @return the xmlInput
	 */
	public String getXmlInput() {
		return xmlInput;
	}
	/**
	 * @param xmlInput the xmlInput to set
	 */
	public void setXmlInput(String xmlInput) {
		this.xmlInput = xmlInput;
	}
	/**
	 * @return the xmlOutput
	 */
	public String getXmlOutput() {
		return xmlOutput;
	}
	/**
	 * @param xmlOutput the xmlOutput to set
	 */
	public void setXmlOutput(String xmlOutput) {
		this.xmlOutput = xmlOutput;
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
	 * @return the lgNo
	 */
	public String getLgNo() {
		return lgNo;
	}

	/**
	 * @param lgNo the lgNo to set
	 */
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}

	/**
	 * @return the alsOnline
	 */
	public String getAlsOnline() {
		return alsOnline;
	}

	/**
	 * @param alsOnline the alsOnline to set
	 */
	public void setAlsOnline(String alsOnline) {
		this.alsOnline = alsOnline;
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

	public String getXmlResponseToEbxml() {
		return xmlResponseToEbxml;
	}

	public void setXmlResponseToEbxml(String xmlResponseToEbxml) {
		this.xmlResponseToEbxml = xmlResponseToEbxml;
	}

	public String getXmlResponseFromEbxml() {
		return xmlResponseFromEbxml;
	}

	public void setXmlResponseFromEbxml(String xmlResponseFromEbxml) {
		this.xmlResponseFromEbxml = xmlResponseFromEbxml;
	}

	public String getFlagCust() {
		return flagCust;
	}

	public void setFlagCust(String flagCust) {
		this.flagCust = flagCust;
	}

	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the issueType
	 */
	public String getIssueType() {
		return issueType;
	}

	/**
	 * @param issueType the issueType to set
	 */
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	
	/**
	 * @return the inputDatetimeStr
	 */
	public String getInputDatetimeStr() {
		return inputDatetimeStr;
	}

	/**
	 * @param inputDatetimeStr the inputDatetimeStr to set
	 */
	public void setInputDatetimeStr(String inputDatetimeStr) {
		this.inputDatetimeStr = inputDatetimeStr;
	}

	/**
	 * @return the outputDatetimeStr
	 */
	public String getOutputDatetimeStr() {
		return outputDatetimeStr;
	}

	/**
	 * @param outputDatetimeStr the outputDatetimeStr to set
	 */
	public void setOutputDatetimeStr(String outputDatetimeStr) {
		this.outputDatetimeStr = outputDatetimeStr;
	}

	/**
	 * @return the responseToEbxmlDatetimeStr
	 */
	public String getResponseToEbxmlDatetimeStr() {
		return responseToEbxmlDatetimeStr;
	}

	/**
	 * @param responseToEbxmlDatetimeStr the responseToEbxmlDatetimeStr to set
	 */
	public void setResponseToEbxmlDatetimeStr(String responseToEbxmlDatetimeStr) {
		this.responseToEbxmlDatetimeStr = responseToEbxmlDatetimeStr;
	}

	/**
	 * @return the responseFromEbxmlDatetimeStr
	 */
	public String getResponseFromEbxmlDatetimeStr() {
		return responseFromEbxmlDatetimeStr;
	}

	/**
	 * @param responseFromEbxmlDatetimeStr the responseFromEbxmlDatetimeStr to set
	 */
	public void setResponseFromEbxmlDatetimeStr(String responseFromEbxmlDatetimeStr) {
		this.responseFromEbxmlDatetimeStr = responseFromEbxmlDatetimeStr;
	}

	
}
