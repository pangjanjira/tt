/**
 * 
 */
package th.co.scb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.DocRunningTable;
import th.co.scb.db.eguarantee.AccountALSTable;
import th.co.scb.db.eguarantee.DeclarationTable;
import th.co.scb.db.eguarantee.EGuaranteeTable;
import th.co.scb.db.eguarantee.GPGuaranteeTable;
import th.co.scb.db.eguarantee.ProjectTaxTable;
import th.co.scb.db.eguarantee.TranGPOfflineTable;
import th.co.scb.db.eguarantee.TranOfflineTable;
import th.co.scb.model.ETime;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.model.eguarantee.Declaration;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.ProjectTax;
import th.co.scb.model.eguarantee.TranGPOffline;
import th.co.scb.model.eguarantee.TranOffline;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class OffHostService {
	
	private static final Logger logger = LoggerFactory.getLogger(OffHostService.class);
	
	public EGuarantee manageEGuaranteeOffline(ConnectDB connectDB, EGuarantee eguarantee, String xmlInput, ETime eTime)throws Exception{
		
		logger.debug("======== in offline @ E-Guarantee ==========");

		DocRunningTable docRunningTable = null;
		AccountALSTable accountALSTable = null;
		TranOfflineTable tranOfflineTable = null;
		DeclarationTable declarationTable = null;
		EGuaranteeTable eGuaranteeTable = null;
		
		AccountALS accountALS = null;
		
		try{
			
			docRunningTable = new DocRunningTable(connectDB);
			accountALSTable = new AccountALSTable(connectDB);
			tranOfflineTable = new TranOfflineTable(connectDB);
			declarationTable = new DeclarationTable(connectDB);
			eGuaranteeTable = new EGuaranteeTable(connectDB);
			
			//get account
			accountALS = accountALSTable.findByAccontNo(eguarantee.getDebtorBankAccNo(), Constants.AccountPurpose.ACCOUNT_CUSTOMS);
			if(accountALS == null){// Invalid Account Number
				
				eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
				eguarantee.setMessageCode(Constants.MessageCode.INVALID_ACCT_NO);
				
			}else{
			
				if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT)){

					//check Bank Code
					if(!accountALS.getBank().equals(eguarantee.getDebtorBankCode())){
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
						eguarantee.setMessageCode(Constants.MessageCode.INVALID_BANK_CODE); 
						 
					
					//check Tax id
					}else if(!accountALS.getTaxId().equals(eguarantee.getDebtorCompanyTaxNo())){
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
						eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
						
					//check credit limit
					}else if(accountALS.getAvaliableAmt().compareTo(eguarantee.getDepositAmount()) == -1){//Insufficient Funds
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
						eguarantee.setMessageCode(Constants.MessageCode.INSUFFICIENT_FUNDS);
						
					}else{
					
						//if success gen L/G No.
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_BOOK);
						eguarantee.setBankGuaranteeNo(docRunningTable.genLGNo(eTime));
						
						//update avaliable amt @ account_als
						accountALS.setAvaliableAmt(accountALS.getAvaliableAmt().subtract(eguarantee.getDepositAmount()));
						accountALSTable.updateAvaliableAmt(accountALS);
						
						//insert declaration
						declarationTable.add(new Declaration(eguarantee.getDeclarationNo(), eguarantee.getDeclarationSeqNo()));
					}
 
				}else{//cancel or refund
					
					//check DeclarationNo, DeclarationSeqNo
				/*	if(declarationTable.isDupDeclarateion(new Declaration(eguarantee.getDeclarationNo(), eguarantee.getDeclarationSeqNo())) == false){//not found 
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
						eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
					}else{*/
					
						eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_BOOK);
						if(eguarantee.getPaymentMethod().equals(Constants.EGuarantee.DEPOSIT_CANCEL)){
							//delete declaration
							declarationTable.remove(new Declaration(eguarantee.getDeclarationNo(), eguarantee.getDeclarationSeqNo()));
							
							//update status e_guarantee
							eGuaranteeTable.updateStatusCancel(eguarantee);
						}
						
						//update avaliable amt @ account_als
						accountALS.setAvaliableAmt(accountALS.getAvaliableAmt().add(eguarantee.getDepositAmount()));
						accountALSTable.updateAvaliableAmt(accountALS);
					//}
				}
				
			}

			//if success --> insert transaction into table tran_offline
			if(eguarantee.getEguaranteeStatus().equals(Constants.EGuarantee.STATUS_BOOK)){//success
				tranOfflineTable.add(new TranOffline(eguarantee.getCustomsRef(), xmlInput, eguarantee.getBankGuaranteeNo()));
			}

			
		}catch (Exception e) {
			// --: handle exception
			logger.debug("Error Manage EGuarantee Offline ..... " + e.getMessage());
			
			//ไม่สามารถทำรายการได้
			eguarantee.setBankGuaranteeNo("");
			eguarantee.setEguaranteeStatus(Constants.EGuarantee.STATUS_INFO);
			eguarantee.setMessageCode(Constants.MessageCode.UNABLE_TO_PROCESS);
			
			//throw new Exception(e.getMessage());
        }

		return eguarantee;
	}	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//changed to
	public GPGuarantee manageGPGuaranteeOffline(ConnectDB connectDB, GPGuarantee gpGuarantee, String xmlInput, ETime eTime)throws Exception{
		logger.debug("======== in offline @ GP-Guarantee ==========");
		//AccountALSTable accountALSTable = null;
		DocRunningTable docRunningTable = null;
		ProjectTaxTable projectTaxTable = null;
		GPGuaranteeTable gpGuaranteeTable = null;
		//TranGPOfflineTable tranGPOfflineTable = null;
		boolean isRollback = false;
		try{
			//accountALSTable = new accountALSTable(connectDB);
			docRunningTable = new DocRunningTable(connectDB);
			projectTaxTable = new ProjectTaxTable(connectDB);
			gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			//tranGPOfflineTable = new TranGPOfflineTable(connectDB);
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				//if success gen L/G No.
				gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
				gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(),gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
			}else{
				//cancel or claim
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
					//delete prject_tax
					//UR59040034 Add eGP Pending Review & Resend Response function
					projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo()));
					//update status gp_guarantee
					gpGuaranteeTable.updateStatusCancel(gpGuarantee);
					gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
				}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
					//update status gp_guarantee
					gpGuaranteeTable.updateStatusClaim(gpGuarantee);
					gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
				}
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
			}
			//if success --> insert transaction into table tran_gp_offline
			//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
			//--> setup, cancel, extend_expiry_date issue only
			if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS) 
					&& (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE) 
							|| gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)
							|| gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE))
					){//success
				
				//UR59040034 Add eGP Pending Review & Resend Response function
    			//Change ALSOffline process
				//tranGPOfflineTable.add(new TranGPOffline(gpGuarantee.getTxRef(), xmlInput, gpGuarantee.getLgNo()));
				
				//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
				//reset status for setup and extend_expiry_date request
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)
						|| gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
					gpGuarantee.setStatusLG("");
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
				}
			}
		}catch (Exception e) {
			//handle exception
			isRollback = true;
			logger.debug("Error Manage GPGuarantee Offline ..... " + e.getMessage());
			//error then reject txn
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE)){
				//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
				gpGuarantee.setStatusLG(Constants.StatusLGGP.EXTEND_EXPIRY_DATE);
			}
			gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
        }finally{
        	if(isRollback){
        		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
        			projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        			gpGuarantee.setLgNo("");
        		//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
        		//}else{
        		}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
        			projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        		}        		        	
        	}
        }
		
		return gpGuarantee;
	}
	//changed from
	/*
	public GPGuarantee manageGPGuaranteeOffline(ConnectDB connectDB, GPGuarantee gpGuarantee, String xmlInput, ETime eTime)throws Exception{
		
		logger.debug("======== in offline @ GP-Guarantee ==========");
		
		AccountALSTable accountALSTable = null;
		DocRunningTable docRunningTable = null;
		ProjectTaxTable projectTaxTable = null;
		GPGuaranteeTable gpGuaranteeTable = null;
		TranGPOfflineTable tranGPOfflineTable = null;
		
		AccountALS accountALS = null;
		
		//update by Apichart H.@20150512
		boolean isRollback = false;
		try{
			
			docRunningTable = new DocRunningTable(connectDB);
			accountALSTable = new AccountALSTable(connectDB);
			projectTaxTable = new ProjectTaxTable(connectDB);
			gpGuaranteeTable = new GPGuaranteeTable(connectDB);
			tranGPOfflineTable = new TranGPOfflineTable(connectDB);
			
			//get account
			accountALS = accountALSTable.findByAccontNo(gpGuarantee.getAccountNo(), Constants.AccountPurpose.ACCOUNT_EGP);
			
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
				//R58060012 : delete by bussara.b @20150616
				//check credit limit
				//if(accountALS.getAvaliableAmt().compareTo(gpGuarantee.getGuaranteeAmt()) == -1){//Insufficient Funds
				//	
				//	gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
				//	gpGuarantee.setXmlOutput(Constants.StatusLGGP.INSUFFICIENT_FUNDS);
				//	//gpGuarantee.setStatusDesc(Constants.StatusLGGP.INSUFFICIENT_FUNDS);
				//	gpGuarantee.setMsgCode(Constants.MessageCode.INSUFFICIENT_FUNDS);
				//	gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
				//
				//}else{
				//	//if success gen L/G No.
				//	gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
				//	gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
				//	gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				//	
				//	//update avaliable amt @ account_als
				//	accountALS.setAvaliableAmt(accountALS.getAvaliableAmt().subtract(gpGuarantee.getGuaranteeAmt()));
				//	accountALSTable.updateAvaliableAmt(accountALS);
				//	
				//	//insert project_tax
				//	//projectTaxTable.add(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo()));
				//	//update by Apichart H.@20150512
				//	projectTaxTable.add(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo()));
				//}
				
				//if success gen L/G No.
				gpGuarantee.setStatusLG(Constants.StatusLGGP.APPROVE);
				gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
					
				//insert project_tax
				//projectTaxTable.add(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo()));
				//update by Apichart H.@20150512
				//projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo()));
				
				//update by Malinee T. UR58100048 @20160104
				projectTaxTable.add(new ProjectTax(gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo(),gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate()));
				
			}else{//cancel or claim
				
				
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
					
					//delete prject_tax
					//projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo()));
					projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(),gpGuarantee.getLgNo()));
					
					//update status gp_guarantee
					gpGuaranteeTable.updateStatusCancel(gpGuarantee);
					
					gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
					
					//R58060012 : delete by bussara.b @20150616
					//update avaliable amt @ account_als
					//accountALS.setAvaliableAmt(accountALS.getAvaliableAmt().add(gpGuarantee.getGuaranteeAmt()));
					//accountALSTable.updateAvaliableAmt(accountALS);
					
				}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
					
					//update status gp_guarantee
					gpGuaranteeTable.updateStatusClaim(gpGuarantee);
					
					gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
				}

				gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_SUCCESS);
				
			}
			
			//if success --> insert transaction into table tran_gp_offline 
			//--> setup and cancel issue only
			if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS) && (
					gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)||
					gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)
					)){//success
				
				tranGPOfflineTable.add(new TranGPOffline(gpGuarantee.getTxRef(), xmlInput, gpGuarantee.getLgNo()));
				
				//reset status for setup request
				if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
					gpGuarantee.setStatusLG("");
					//gpGuarantee.setLgNo(docRunningTable.genLGNo(eTime));
					gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);	
				}
				
			}

		}catch (Exception e) {
			isRollback = true;
			// --: handle exception
			logger.debug("Error Manage GPGuarantee Offline ..... " + e.getMessage());
			
			//ไม่สามารถทำรายการได้
			if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
//				gpGuarantee.setLgNo("");
				gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
			}else if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)){
				gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
			}
			gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
			//gpGuarantee.setStatusDesc(Constants.StatusLGGP.UNABLE_TO_PROCESS);
			//gpGuarantee.setMsgCode(Constants.MessageCode.INSUFFICIENT_FUNDS);

			
        }finally{
        	//update by Apichart H.@20150528
        	if(isRollback){
        		if(gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)){
        			projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        			gpGuarantee.setLgNo("");
        		}else{
        			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
        			//changed from
        			//projectTaxTable.removeSetupIssue(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        			projectTaxTable.remove(new ProjectTax(gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getLgNo()));
        		}        		        	
        	}
        }
		
		return gpGuarantee;
	}
	*/
	
}
