/**
 * 
 */
package th.co.scb.service.customs;

import th.co.scb.model.RegistrationId;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositCancelNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeDepositNotificationType;
import th.co.scb.model.customs.mainpayment.BankToCustomerGuaranteeRefundNotificationType;
import th.co.scb.model.eguarantee.EGuarantee;

/**
 * @author s51486
 *
 */
public interface ManageMainPayment {
	
	public BankToCustomerGuaranteeDepositNotificationType createBankToCustomerGuaranteeDepositNotificationType(EGuarantee eguarantee,RegistrationId registrationId);
	public BankToCustomerGuaranteeDepositCancelNotificationType createBankToCustomerGuaranteeDepositCancelNotificationType(EGuarantee eguarantee, RegistrationId registrationId);
	public BankToCustomerGuaranteeRefundNotificationType createBankToCustomerGuaranteeRefundNotificationType(EGuarantee eguarantee, RegistrationId registrationId);
	
}
