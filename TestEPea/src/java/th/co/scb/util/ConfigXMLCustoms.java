package th.co.scb.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import th.co.scb.service.ConfigFileLocationConfig;

/**
 * @author s51486
 *
 */
public class ConfigXMLCustoms {
	
	private Properties prop;
	private FileInputStream file;

	public ConfigXMLCustoms() {
		super();
		try{
			
			//to load application's properties, we use this class
			prop = new Properties();
			
			ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
			String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.CUSTOMS_PROPERTIES;
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
	
	
	//-------------------- Deposit ----------------------
	public String getDepositMessageRoot(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_MESSAGE_ROOT); 
	}
	
	public String getDepositCustomsName(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_NAME); 
	}

	public String getDepositCustomsRefNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_REF_NO); 
	}
	
	public String getDepositCustomsTransDateTime(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_TRANS_DATETIME); 
	}
	
	public String getDepositNoOfTrans(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_NUMBER_OF_TRANS); 
	}
	
	public String getDepositPaymentMethod(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_PAYMENT_METHOD); 
	}
	
	public String getDepositTransType(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_TRANS_TYPE); 
	}
	
	public String getDepositRequestDate(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_REQUEST_DATE); 
	}
	
	public String getDepositCustomsBankAcct(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_BANK_ACCT); 
	}
	
	public String getDepositCustomsBankCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_BANK_CODE); 
	}
	
	public String getDepositCustomsBranchCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_CUSTOMS_BRANCH_CODE); 
	}
	
	public String getDepositAmount(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEPOSIT_AMT); 
	}
	
	public String getDepositDebtorBankCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_BANK_CODE); 
	}
	
	public String getDepositDebtorBranchCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_BRANCH_CODE); 
	}
	
	public String getDepositDebtorCompName(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_COMP_NAME); 
	}
	
	public String getDepositDebtorCompTaxNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_COMP_TAX_NO); 
	}
	
	public String getDepositDebtorCompBranch(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_COMP_BRANCH); 
	}

	public String getDepositDebtorBankAcctNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DEBTOR_BANK_ACCT_NO); 
	}
	
	public String getDepositDeclarationNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DECLARATION_NO); 
	}
	
	public String getDepositRelateDate(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_RELATE_DATE); 
	}
	
	public String getDepositDeclarationSeqNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSIT_DECLARATION_SEQ_NO); 
	}
	

	//----------------- Deposit Cancel -------------------
	public String getDepositCancelMessageRoot(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_MESSAGE_ROOT); 
	}
	
	public String getDepositCancelCustomsRefNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_CUSTOMS_REF_NO); 
	}
	
	public String getDepositCancelCustomsTransDateTime(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_CUSTOMS_TRANS_DATETIME); 
	}
	
	public String getDepositCancelNoOfTrans(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_NUMBER_OF_TRANS); 
	}
	
	public String getDepositCancelOriginalCustomsRefNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_ORIGINAL_CUSTOMS_REF_NO); 
	}
	
	public String getDepositCancelAmount(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEPOSIT_AMOUNT); 
	}
	
	public String getDepositCancelDeclarationNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DECLARATION_NO); 
	}
	
	public String getDepositCancelRelateDate(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_RELATE_DATE); 
	}
	
	public String getDepositCancelDeclarationSeqNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DECLARATION_SEQ_NO); 
	}
	
	public String getDepositCancelBankGuaranteeNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_BANK_GUARANTEE_NO); 
	}
	
	public String getDepositCancelDebtorCompName(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_NAME); 
	}
	
	public String getDepositCancelDebtorCompTaxNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_TAX_NO); 
	}
	
	public String getDepositCancelDebtorCompBranch(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_BRANCH); 
	}
	
	public String getDepositCancelDebtorBankAcctNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_BANK_ACCT_NO); 
	}
	
	public String getDepositCancelDebtorBankCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_BANK_CODE); 
	}
	
	public String getDepositCancelDebtorBranchCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_DEPOSITCANCEL_DEBTOR_BRANCH_CODE); 
	}
	
	//------------------------- Refund --------------------
	public String getRefundMessageRoot(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_MESSAGE_ROOT); 
	}
	
	public String getRefundCustomsRefNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_CUSTOMS_REF_NO); 
	}
	
	public String getRefundCustomsTransDateTime(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_CUSTOMS_TRANS_DATETIME); 
	}
	
	public String getRefundNoOfTrans(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_NUMBER_OF_TRANS); 
	}
	
	public String getRefundOriginalCustomsRefNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_ORIGINAL_CUSTOMS_REF_NO); 
	}
	
	public String getRefundAmount(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEPOSIT_AMOUNT); 
	}
	
	public String getRefundDeclarationNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DECLARATION_NO); 
	}
	
	public String getRefundBankGuaranteeNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_BANK_GUARANTEE_NO); 
	}
	
	public String getRefundInformPort(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_INFORM_PORT); 
	}
	
	public String getRefundInformNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_INFORM_NO); 
	}
	
	public String getRefundInformDate(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_INFORM_DATE); 
	}
	
	public String getRefundDebtorCompName(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_COMP_NAME); 
	}
	
	public String getRefundDebtorCompTaxNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_COMP_TAX_NO); 
	}
	
	public String getRefundDebtorCompBranch(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_COMP_BRANCH); 
	}
	
	public String getRefundDebtorBankAcctNo(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_BANK_ACCT_NO); 
	}
	
	public String getRefundDebtorBankCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_BANK_CODE); 
	}
	
	public String getRefundDebtorBranchCode(){
		return prop.getProperty(Constants.ConfigFile.CUSTOMS_REFUND_DEBTOR_BRANCH_CODE); 
	}

}
