/**
 * 
 */
package th.co.scb.util;

import java.io.FileInputStream;
import java.util.Properties;

import th.co.scb.service.ConfigFileLocationConfig;

/**
 * @author s51486
 *
 */
public class ConfigXMLCustomsGP {
	
	private Properties prop;
	private FileInputStream file;

	public ConfigXMLCustomsGP() {
		super();
		try{
			
			//to load application's properties, we use this class
			prop = new Properties();
			
			ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
			String path = configFileLocationConfig.getLocation() + Constants.ConfigGPFile.CUSTOMS_GP_PROPERTIES;
			//System.out.println("path : "+path);
			//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config_xml_customs.properties");
			
			//load the file handle
		    file = new FileInputStream(path);
		    
		    //load all the properties from this file
			prop.load(file);
			
		}catch (Exception e) {
			// e: handle exception
			e.printStackTrace();
		}	
	}
	
	public String getSetupIssueMessageRoot(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_SETPUP_ISSUE_MESSAGE_ROOT); 
	}
	
	public String getCancelIssueMessageRoot(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CANCEL_ISSUE_MESSAGE_ROOT); 
	}
	
	public String getClaimIssueMessageRoot(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CLAIM_ISSUE_MESSAGE_ROOT); 
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	public String getExtendExpiryDateIssueMessageRoot(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_EXTEND_EXPIRY_DATE_ISSUE_MESSAGE_ROOT); 
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	public String getExtendDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_EXTEND_DATE); 
	}
	
	public String getTransRef(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_TRANS_REF); 
	}
	
	public String getTransDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_TRANS_DATE); 
	}
	
	public String getProjectNo(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_PROJECT_NO); 
	}
	
	public String getDeptCode(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_DEPT_CODE); 
	}
	
	public String getVendorTaxId(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_VENDOR_TAX_ID); 
	}
	
	public String getVendorName(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_VENDOR_NAME); 
	}
	
	public String getCompId(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_COMP_ID); 
	}
	
	public String getUserId(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_USER_ID); 
	}
	
	public String getSeqNo(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_SEQ_NO); 
	}
	
	public String getConsiderDesc(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CONSIDER_DESC); 
	}
	
	public String getConsiderMoney(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CONSIDER_MONEY); 
	}
	
	public String getGuaranteeAmt(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_GUARANTEE_AMT); 
	}
	
	public String getContractNo(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CONTRACT_NO); 
	}
	
	public String getContractDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_CONTRACT_DATE); 
	}
	
	public String getGuaranteePrice(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_GUARANTEE_PRICE); 
	}
	
	public String getGuaranteePercent(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_GUARANTEE_PERCENT); 
	}
	
	public String getAdvanceGuaranteePrice(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_ADV_GUARANTEE_PRICE); 
	}
	
	public String getAdvancePayment(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_ADV_PAYMENT); 
	}
	
	public String getWorksGuaranteePrice(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_WORKS_GUARANTEE_PRICE); 
	}
	
	public String getWorksGuaranteePercent(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_WORKS_GUARANTEE_PERCENT); 
	}
	
	public String getCollectionPhase(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_COLLECTION_PHASE); 
	}
	
	public String getEndDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_END_DATE); 
	}
	
	public String getStartDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_START_DATE); 
	}
	
	public String getBondType(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_BOND_TYPE); 
	}
	
	public String getProjectName(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_PROJECT_NAME); 
	}
	
	public String getProjectAmt(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_PROJECT_AMT); 
	}
	
	public String getProjectOwnName(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_PROJECT_OWN_NAME); 
	}
	
	public String getCostCenter(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_COST_CENTER); 
	}
	
	public String getCostCenterName(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_COST_CENTER_NAME); 
	}
	
	public String getDocumentNo(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_DOCUMENT_NO); 
	}
	
	public String getDocumentDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_DOCUMENT_DATE); 
	}
	
	public String getExpireDate(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_EXPIRE_DATE); 
	}
	
	public String getLgNo(){
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_LG_NO); 
	}
	
	public String getAmtReq(){  
		return prop.getProperty(Constants.ConfigGPFile.CUSTOMS_GP_AMT_REQ); 
	}
}
