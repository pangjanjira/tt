/**
 * 
 */
package th.co.scb.service.customs;

import th.co.scb.model.RegistrationId;
import th.co.scb.model.customs.g1c054.Document;
import th.co.scb.model.eguarantee.EGuarantee;


/**
 * @author s51486
 *
 */
public interface ManageBankToCustomsDepositCancel {
	public Document createDocument(EGuarantee eguarantee);
}
