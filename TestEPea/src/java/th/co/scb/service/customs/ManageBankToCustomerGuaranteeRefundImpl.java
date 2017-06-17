/**
 * 
 */
package th.co.scb.service.customs;

import java.util.Date;
import java.util.List;

import th.co.scb.model.EStatus;
import th.co.scb.model.RegistrationId;
import th.co.scb.model.customs.g2n054.AccountIdentification4Choice;
import th.co.scb.model.customs.g2n054.AccountNotification2;
import th.co.scb.model.customs.g2n054.ActiveOrHistoricCurrencyAndAmount;
import th.co.scb.model.customs.g2n054.AmountAndCurrencyExchange3;
import th.co.scb.model.customs.g2n054.AmountAndCurrencyExchangeDetails3;
import th.co.scb.model.customs.g2n054.BankToCustomerDebitCreditNotificationV02;
import th.co.scb.model.customs.g2n054.BankTransactionCodeStructure4;
import th.co.scb.model.customs.g2n054.BranchAndFinancialInstitutionIdentification4;
import th.co.scb.model.customs.g2n054.BranchData2;
import th.co.scb.model.customs.g2n054.CashAccount16;
import th.co.scb.model.customs.g2n054.CashAccount20;
import th.co.scb.model.customs.g2n054.CreditDebitCode;
import th.co.scb.model.customs.g2n054.DateAndDateTimeChoice;
import th.co.scb.model.customs.g2n054.EntryDetails1;
import th.co.scb.model.customs.g2n054.EntryStatus2Code;
import th.co.scb.model.customs.g2n054.EntryTransaction2;
import th.co.scb.model.customs.g2n054.FinancialInstitutionIdentification7;
import th.co.scb.model.customs.g2n054.GenericAccountIdentification1;
import th.co.scb.model.customs.g2n054.GenericFinancialIdentification1;
import th.co.scb.model.customs.g2n054.GenericOrganisationIdentification1;
import th.co.scb.model.customs.g2n054.GroupHeader42;
import th.co.scb.model.customs.g2n054.ObjectFactory;
import th.co.scb.model.customs.g2n054.OrganisationIdentification4;
import th.co.scb.model.customs.g2n054.OrganisationIdentificationSchemeName1Choice;
import th.co.scb.model.customs.g2n054.Party6Choice;
import th.co.scb.model.customs.g2n054.PartyIdentification32;
import th.co.scb.model.customs.g2n054.ProprietaryBankTransactionCodeStructure1;
import th.co.scb.model.customs.g2n054.ReferredDocumentInformation3;
import th.co.scb.model.customs.g2n054.ReferredDocumentType1Choice;
import th.co.scb.model.customs.g2n054.ReferredDocumentType2;
import th.co.scb.model.customs.g2n054.RemittanceInformation5;
import th.co.scb.model.customs.g2n054.ReportEntry2;
import th.co.scb.model.customs.g2n054.ReturnReason5Choice;
import th.co.scb.model.customs.g2n054.ReturnReasonInformation10;
import th.co.scb.model.customs.g2n054.StructuredRemittanceInformation7;
import th.co.scb.model.customs.g2n054.TransactionAgents2;
import th.co.scb.model.customs.g2n054.TransactionDates2;
import th.co.scb.model.customs.g2n054.TransactionParty2;
import th.co.scb.model.customs.g2n054.TransactionReferences2;
import th.co.scb.model.customs.g2n054.Document;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.service.EStatusService;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.StringUtil;

/**
 * @author s51486
 *
 */
public class ManageBankToCustomerGuaranteeRefundImpl implements
		ManageBankToCustomerGuaranteeRefund {

	/* (non-Javadoc)
	 * @see th.co.scb.service.customs.ManageBankToCustomerGuaranteeRefund#createDocument()
	 */
	private ObjectFactory 	factory;
	private	Date			createDate;
	
	public Document createDocument(EGuarantee eguarantee){
		factory = new ObjectFactory();
		
		Document doc = factory.createDocument();
		createDate = new Date();
		
		doc.setBkToCstmrDbtCdtNtfctn(createBankToCustomerDebitCreditNotificationV02(factory, eguarantee));

		return doc;
		
	}
	
	private BankToCustomerDebitCreditNotificationV02 createBankToCustomerDebitCreditNotificationV02(ObjectFactory factory, EGuarantee eguarantee){
		
		BankToCustomerDebitCreditNotificationV02 bv2 = factory.createBankToCustomerDebitCreditNotificationV02();
		bv2.setGrpHdr(createGroupHeader42(factory, eguarantee));
		List<AccountNotification2> accountNotifiList = bv2.getNtfctn();
		accountNotifiList.add(createAccountNotification2(factory, eguarantee));

		return bv2;
	}
	
	private GroupHeader42 createGroupHeader42(ObjectFactory factory, EGuarantee eguarantee){
		
		GroupHeader42 gh = factory.createGroupHeader42();
		
		gh.setMsgId(eguarantee.getBankTransactionNo());//Bank transaction no
		gh.setCreDtTm(DateUtil.genXMLGregorianCalendar(createDate));
		
		return gh;
		
	}
	
	private AccountNotification2 createAccountNotification2(ObjectFactory factory, EGuarantee eguarantee){
		
		AccountNotification2 an2 = factory.createAccountNotification2();
		
		an2.setId(eguarantee.getBankTransactionNo());//Bank transaction no
		an2.setCreDtTm(DateUtil.genXMLGregorianCalendar(createDate));
		an2.setAcct(createCashAccount20(factory, eguarantee));
		List<ReportEntry2> reportEntryList = an2.getNtry();
		reportEntryList.add(createReportEntry2(factory, eguarantee));
		
		return an2;
		
	}
	
	
	private CashAccount20 createCashAccount20(ObjectFactory factory, EGuarantee eguarantee){
		
		CashAccount20 ca20 = factory.createCashAccount20();
		ca20.setId(createAccountIdentification4Choice(factory, eguarantee));
		
		return ca20;
		
	}
	
	private AccountIdentification4Choice createAccountIdentification4Choice(ObjectFactory factory, EGuarantee eguarantee){
		
		AccountIdentification4Choice aif = factory.createAccountIdentification4Choice();
		aif.setOthr(createGenericAccountIdentification1(factory, eguarantee));
		
		return aif;
	}
	
	private GenericAccountIdentification1 createGenericAccountIdentification1(ObjectFactory factory, EGuarantee eguarantee){
		GenericAccountIdentification1 gaif = factory.createGenericAccountIdentification1();
		gaif.setId(eguarantee.getDebtorBankAccNo());//Bank Account Number
		
		return gaif;
	}
	
	private ReportEntry2 createReportEntry2(ObjectFactory factory, EGuarantee eguarantee){
		ReportEntry2 re2 = factory.createReportEntry2();
		
		re2.setNtryRef(eguarantee.getBankTransactionNo());//Ref of bank
		re2.setAmt(createActiveOrHistoricCurrencyAndAmount(factory, eguarantee));
		re2.setCdtDbtInd(CreditDebitCode.CRDT);
		if(eguarantee.getEguaranteeStatus().equals(Constants.EGuarantee.STATUS_BOOK)){
			re2.setSts(EntryStatus2Code.BOOK);
		}else{
			re2.setSts(EntryStatus2Code.INFO);
		}
		if(eguarantee.getBookingDate() != null){
			re2.setBookgDt(createDateAndDateTimeChoice(factory, eguarantee));
		}
		re2.setBkTxCd(createBankTransactionCodeStructure4(factory, eguarantee));
		List<EntryDetails1> entryDetailList = re2.getNtryDtls();
		entryDetailList.add(createEntryDetails1(factory, eguarantee));
		
		return re2;
		
	}
	
	private ActiveOrHistoricCurrencyAndAmount createActiveOrHistoricCurrencyAndAmount(ObjectFactory factory, EGuarantee eguarantee){
		ActiveOrHistoricCurrencyAndAmount ahca = factory.createActiveOrHistoricCurrencyAndAmount();
		ahca.setCcy(Constants.THAI_CURRENCY);
		ahca.setValue(eguarantee.getDepositAmount());//Deposit Amt
		
		return ahca;
	}
	
	private DateAndDateTimeChoice createDateAndDateTimeChoice(ObjectFactory factory, EGuarantee eguarantee){
		
		DateAndDateTimeChoice dtc = factory.createDateAndDateTimeChoice();
		dtc.setDtTm(DateUtil.genXMLGregorianCalendar(eguarantee.getBookingDate()));
		
		return dtc;
		
	}
	
	private BankTransactionCodeStructure4 createBankTransactionCodeStructure4(ObjectFactory factory, EGuarantee eguarantee){
		BankTransactionCodeStructure4 btcs = factory.createBankTransactionCodeStructure4();
		
		btcs.setPrtry(createProprietaryBankTransactionCodeStructure1(factory, eguarantee));
		
		return btcs;
	}
	
	private ProprietaryBankTransactionCodeStructure1 createProprietaryBankTransactionCodeStructure1(ObjectFactory factory, EGuarantee eguarantee){
		
		ProprietaryBankTransactionCodeStructure1 pbtcs = factory.createProprietaryBankTransactionCodeStructure1();
		pbtcs.setCd(eguarantee.getDebtorBankCode());//Bank Code
		
		return pbtcs;
		
	}
	
	private EntryDetails1 createEntryDetails1(ObjectFactory factory, EGuarantee eguarantee){
		
		EntryDetails1 ed = factory.createEntryDetails1();
		List<EntryTransaction2> entryTransctionList = ed.getTxDtls();
		entryTransctionList.add(createEntryTransaction2(factory, eguarantee));
		
		return ed;
		
	}
	
	private EntryTransaction2 createEntryTransaction2(ObjectFactory factory, EGuarantee eguarantee){
		EntryTransaction2 et = factory.createEntryTransaction2();

		et.setRefs(createTransactionReferences2(factory, eguarantee));
		et.setAmtDtls(createAmountAndCurrencyExchange3(factory, eguarantee));
		et.setRltdPties(createTransactionParty2(factory, eguarantee));
		et.setRltdAgts(createTransactionAgents2(factory, eguarantee));
		et.setRmtInf(createRemittanceInformation5(factory, eguarantee));
		et.setRltdDts(createTransactionDates2(factory, eguarantee));
		if(eguarantee.getMessageCode() != null && eguarantee.getMessageCode().length() > 0){
			et.setRtrInf(createReturnReasonInformation10(factory, eguarantee));
		}
		return et;
		
	}
	
	private TransactionReferences2 createTransactionReferences2(ObjectFactory factory, EGuarantee eguarantee){
		TransactionReferences2 tr = factory.createTransactionReferences2();
		
		tr.setMsgId(Constants.Customs.DOC_RETURN_G2N);//name of return
		tr.setEndToEndId(eguarantee.getCustomsRef());//Customs Reference No
		
		return tr;
	}
	
	private AmountAndCurrencyExchange3 createAmountAndCurrencyExchange3(ObjectFactory factory, EGuarantee eguarantee){
		
		AmountAndCurrencyExchange3 aace = factory.createAmountAndCurrencyExchange3();
		aace.setInstdAmt(createAmountAndCurrencyExchangeDetails3(factory, eguarantee));
		
		return aace;
		
	}
	
	private AmountAndCurrencyExchangeDetails3 createAmountAndCurrencyExchangeDetails3(ObjectFactory factory, EGuarantee eguarantee){
		AmountAndCurrencyExchangeDetails3 acxd = factory.createAmountAndCurrencyExchangeDetails3();
		
		acxd.setAmt(createActiveOrHistoricCurrencyAndAmount(factory, eguarantee));
		
		return acxd;
	}
	
	private TransactionParty2 createTransactionParty2(ObjectFactory factory, EGuarantee eguarantee){
		TransactionParty2 tp = factory.createTransactionParty2();
		
		tp.setDbtr(createPartyIdentification32(factory, eguarantee));
		tp.setDbtrAcct(createCashAccount16(factory, eguarantee));
		
		return tp;
		
	}
	
	private PartyIdentification32 createPartyIdentification32(ObjectFactory factory, EGuarantee eguarantee){
		PartyIdentification32 pi = factory.createPartyIdentification32();
		
		pi.setNm(eguarantee.getDebtorCompanyName());//Company Name
		pi.setId(createParty6Choice(factory, eguarantee));
		
		return pi;
		
	}
	
	private Party6Choice createParty6Choice(ObjectFactory factory, EGuarantee eguarantee){
		Party6Choice pc = factory.createParty6Choice();
		
		pc.setOrgId(createOrganisationIdentification4(factory, eguarantee));
		
		return pc;
	}
	
	private OrganisationIdentification4 createOrganisationIdentification4(ObjectFactory factory, EGuarantee eguarantee){
		OrganisationIdentification4 oi = factory.createOrganisationIdentification4();
		
		List<GenericOrganisationIdentification1> organisationIdList = oi.getOthr();
		organisationIdList.add(createGenericOrganisationIdentification1(factory, Constants.Customs.COM_TAX_NO, eguarantee));
		organisationIdList.add(createGenericOrganisationIdentification1(factory, Constants.Customs.COM_BRANCH, eguarantee));
		
		return oi;
	}
	
	private GenericOrganisationIdentification1 createGenericOrganisationIdentification1(ObjectFactory factory, int type, EGuarantee eguarantee){
		GenericOrganisationIdentification1 goi = factory.createGenericOrganisationIdentification1();
		
		if(type == Constants.Customs.COM_TAX_NO){
			goi.setId(eguarantee.getDebtorCompanyTaxNo());//Company Tax No
		}else if(type == Constants.Customs.COM_BRANCH){
			goi.setId(eguarantee.getDebtorCompanyBranch());//Company Branch
		}
		goi.setSchmeNm(createOrganisationIdentificationSchemeName1Choice(factory, type, eguarantee));
		
		return goi;
	}
	
	private OrganisationIdentificationSchemeName1Choice createOrganisationIdentificationSchemeName1Choice(ObjectFactory factory, int type, EGuarantee eguarantee){
		OrganisationIdentificationSchemeName1Choice oisc = factory.createOrganisationIdentificationSchemeName1Choice();
		
		if(type == Constants.Customs.COM_TAX_NO){
			oisc.setPrtry(Constants.Customs.COM_TAX_NO_TEXT);
		}else if(type == Constants.Customs.COM_BRANCH){
			oisc.setPrtry(Constants.Customs.COM_BRANCH_TEXT);
		}
		
		return oisc;
	}
	
	private CashAccount16 createCashAccount16(ObjectFactory factory, EGuarantee eguarantee){
		CashAccount16 ca = factory.createCashAccount16();
		
		ca.setId(createAccountIdentification4Choice(factory, eguarantee));
		
		return ca;
	}
	
	private TransactionAgents2 createTransactionAgents2(ObjectFactory factory, EGuarantee eguarantee){
		
		TransactionAgents2 ta = factory.createTransactionAgents2();
		ta.setDbtrAgt(createBranchAndFinancialInstitutionIdentification4(factory, eguarantee));
		
		return ta;
		
	}
	
	private BranchAndFinancialInstitutionIdentification4 createBranchAndFinancialInstitutionIdentification4(ObjectFactory factory, EGuarantee eguarantee){
		
		BranchAndFinancialInstitutionIdentification4 bfi = factory.createBranchAndFinancialInstitutionIdentification4();
		
		bfi.setFinInstnId(createFinancialInstitutionIdentification7(factory, eguarantee));
		bfi.setBrnchId(createBranchData2(factory, eguarantee));
		
		return bfi;
	}
	
	private FinancialInstitutionIdentification7 createFinancialInstitutionIdentification7(ObjectFactory factory, EGuarantee eguarantee){
		FinancialInstitutionIdentification7 fii = factory.createFinancialInstitutionIdentification7();
		
		fii.setOthr(createGenericFinancialIdentification1(factory, eguarantee));
		
		return fii;
	}
	
	private GenericFinancialIdentification1 createGenericFinancialIdentification1(ObjectFactory factory, EGuarantee eguarantee){
		
		GenericFinancialIdentification1 gfi = factory.createGenericFinancialIdentification1();
		
		gfi.setId(eguarantee.getDebtorBankCode());//Bank Code
		
		return gfi;
	}
	
	private BranchData2 createBranchData2(ObjectFactory factory, EGuarantee eguarantee){
		
		BranchData2 bd = factory.createBranchData2();
		
		bd.setId(eguarantee.getDebtorBankBranchCode());//Bank Branch Code
		
		return bd;
		
	}
	
	private RemittanceInformation5 createRemittanceInformation5(ObjectFactory factory, EGuarantee eguarantee){
		
		RemittanceInformation5 ri = factory.createRemittanceInformation5();
		
		List<StructuredRemittanceInformation7> strdList = ri.getStrd();
		strdList.add(createStructuredRemittanceInformation7(factory, eguarantee));
		
		return ri;
		
	}
	
	private StructuredRemittanceInformation7 createStructuredRemittanceInformation7(ObjectFactory factory, EGuarantee eguarantee){
		StructuredRemittanceInformation7 sri = factory.createStructuredRemittanceInformation7();
		
		List<ReferredDocumentInformation3> referDocInfoList = sri.getRfrdDocInf();
		referDocInfoList.add(createReferredDocumentInformation3(factory, Constants.Customs.DECLARATION_NO, eguarantee));
		referDocInfoList.add(createReferredDocumentInformation3(factory, Constants.Customs.BANK_GUARANTEE_NO, eguarantee));
		//referDocInfoList.add(createReferredDocumentInformation3(factory, Constants.Customs.INFORM, eguarantee));
		
		List<ReferredDocumentInformation3> referDocInfo2List = sri.getRfrdDocInf2();
		referDocInfo2List.add(createReferredDocumentInformation3(factory, Constants.Customs.INFORM, eguarantee));
		
		return sri;
	}
	
	private ReferredDocumentInformation3 createReferredDocumentInformation3(ObjectFactory factory, int type, EGuarantee eguarantee){
		ReferredDocumentInformation3 rdi = factory.createReferredDocumentInformation3();
		
		rdi.setTp(createReferredDocumentType2(factory, type, eguarantee));
		if(type == Constants.Customs.DECLARATION_NO){
			rdi.setNb(eguarantee.getDeclarationNo());//Declaration No
		}else if(type == Constants.Customs.BANK_GUARANTEE_NO){
			String tmpBankGuarantee = StringUtil.nullToBlank(eguarantee.getBankGuaranteeNo());
			if(tmpBankGuarantee == null || tmpBankGuarantee.length() == 0){
				tmpBankGuarantee = "0";
			}
			rdi.setNb(tmpBankGuarantee);//Bank Guarantee No or LG No
		}else if(type == Constants.Customs.INFORM){
			rdi.setNb(eguarantee.getInformNo());//Inform No
			rdi.setRltdDt(DateUtil.convertDateStrToXMLGregorianCalendar(eguarantee.getInformDate()));//Inform Date
		}
		
		
		return rdi;
	}
	
	private ReferredDocumentType2 createReferredDocumentType2(ObjectFactory factory, int type, EGuarantee eguarantee){
		ReferredDocumentType2 rdt = factory.createReferredDocumentType2();
		
		rdt.setCdOrPrtry(createReferredDocumentType1Choice(factory, type, eguarantee));
		if(type == Constants.Customs.INFORM){
			rdt.setIssr(eguarantee.getInformPort());//INFORM PORT
		}
		
		return rdt;
		
	}
	
	private ReferredDocumentType1Choice createReferredDocumentType1Choice(ObjectFactory factory, int type, EGuarantee eguarantee){
		
		ReferredDocumentType1Choice rdt = factory.createReferredDocumentType1Choice();
		if(type == Constants.Customs.DECLARATION_NO){
			rdt.setPrtry(Constants.Customs.DECLARATION_NO_TEXT);//Declaration No
		}else if(type == Constants.Customs.BANK_GUARANTEE_NO){
			rdt.setPrtry(Constants.Customs.BANK_GUARANTEE_NO_TEXT);//Bank Guarantee No or LG No
		}else if(type == Constants.Customs.INFORM){
			rdt.setPrtry(Constants.Customs.INFORM_TEXT);//INFORM
		}

		return rdt;
		
	}
	
	private TransactionDates2 createTransactionDates2(ObjectFactory factory, EGuarantee eguarantee){
		TransactionDates2 td = factory.createTransactionDates2();
		
		td.setTxDtTm(DateUtil.convertDateStrToXMLGregorianCalendar(eguarantee.getCustomsTransDate()));//Customs Transmit Date/Time
		
		return td;
	}
	
	private ReturnReasonInformation10 createReturnReasonInformation10(ObjectFactory factory, EGuarantee eguarantee){
		
		ReturnReasonInformation10 rri = factory.createReturnReasonInformation10();

		rri.setRsn(createReturnReason5Choice(factory, eguarantee));
	
		List<String> messageDescList = rri.getAddtlInf();
		if(eguarantee.getMessageCode()!=null){
			
			EStatusService eStatusService = new EStatusService();
			EStatus eStatus = null;
			try{
				eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, eguarantee.getMessageCode()));
			}catch (Exception e) {
				// --: handle exception
				eStatus = new EStatus(eguarantee.getMessageCode(), "");
			}
			
			messageDescList.add(eStatus.getDescEn());//Message Description
		}

		return rri;
		
	}
	
	private ReturnReason5Choice createReturnReason5Choice(ObjectFactory factory, EGuarantee eguarantee){
		
		ReturnReason5Choice rr = factory.createReturnReason5Choice();
		
		rr.setCd(eguarantee.getMessageCode());//Message Status
		
		return rr;
		
	}

}
