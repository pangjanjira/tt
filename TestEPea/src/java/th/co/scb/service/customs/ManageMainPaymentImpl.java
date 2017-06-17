/**
 * 
 */
package th.co.scb.service.customs;

import th.co.scb.model.RegistrationId;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositCancelNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeRefundNotificationType;
import th.co.scb.model.customs.mainpayment.DocumentHeaderType;
import th.co.scb.model.customs.mainpayment.ObjectFactory;
import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.util.Constants;


/**
 * @author s51486
 *
 */
public class ManageMainPaymentImpl implements ManageMainPayment{
	
	private ObjectFactory factory;
	
	private DocumentHeaderType createDocumentHeaderType(ObjectFactory factory, RegistrationId registrationId){

		DocumentHeaderType dt = factory.createDocumentHeaderType();
		dt.setRegistrationID(registrationId.getCustomsRegId());

    	return dt;
	}
	

	public BankToCustomerGuaranteeDepositNotificationType createBankToCustomerGuaranteeDepositNotificationType(EGuarantee eguarantee, RegistrationId registrationId){
		factory = new ObjectFactory();
		
		BankToCustomerGuaranteeDepositNotificationType bg = factory.createBankToCustomerGuaranteeDepositNotificationType();
    	
		bg.setDocumentHeader(createDocumentHeaderType(factory, registrationId));
		ManageBankToCustomsDeposit mcd = new ManageBankToCustomsDepositImpl();
    	bg.setPaymentNotification(mcd.createDocument(eguarantee));
    	//bg.setSignature(new ManageCoreSchema().createSignatureType());
    	
    	return bg;
	}
	
	@Override
	public BankToCustomerGuaranteeDepositCancelNotificationType createBankToCustomerGuaranteeDepositCancelNotificationType(EGuarantee eguarantee, RegistrationId registrationId){
		factory = new ObjectFactory();
		
		BankToCustomerGuaranteeDepositCancelNotificationType bg = factory.createBankToCustomerGuaranteeDepositCancelNotificationType();
    	
		bg.setDocumentHeader(createDocumentHeaderType(factory, registrationId));
		ManageBankToCustomsDepositCancel mcdc = new ManageBankToCustomsDepositCancelImpl();
    	bg.setPaymentNotification(mcdc.createDocument(eguarantee));
    	
    	return bg;
	}
	
	@Override
	public BankToCustomerGuaranteeRefundNotificationType createBankToCustomerGuaranteeRefundNotificationType(EGuarantee eguarantee, RegistrationId registrationId){
		factory = new ObjectFactory();
		
		BankToCustomerGuaranteeRefundNotificationType bg = factory.createBankToCustomerGuaranteeRefundNotificationType();
    	
		bg.setDocumentHeader(createDocumentHeaderType(factory, registrationId));
		ManageBankToCustomerGuaranteeRefund mcr = new ManageBankToCustomerGuaranteeRefundImpl();
    	bg.setPaymentNotification(mcr.createDocument(eguarantee));
    	
    	return bg;
	}
}
