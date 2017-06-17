/**
 * 
 */
package th.co.scb.db.eguarantee;

import th.co.scb.model.eguarantee.EGuarantee;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class EGuaranteeTable {
	
	private ConnectDB connectDB;
	
	public EGuaranteeTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("EGuaranteeTable received connection : "+connectDB.hashCode());		
	}
	
	public void add(EGuarantee eGuarantee) throws Exception{
		
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(Constants.TableName.E_GUARANTEE);
		sql.append(" (payment_method, customs_name, customs_ref_no, customs_tran_date, deposit_amount, ");
		sql.append("  declaration_no, declaration_seq_no, relate_date, company_name, bank_acct_no, ");
		sql.append("  comp_tax_no, bank_guarantee_no, status, msg_code, als_online, ");
		//sql.append("  process_date, add_date, customs_transmit_datetime, original_customs_ref_no) ");
		//sql.append(" VALUES(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?, now(),?,? )");
		sql.append("  process_date, add_date, customs_transmit_datetime, original_customs_ref_no, xml_output) "); //boy add xml_output
		sql.append(" VALUES(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?, now(),?,?,? )"); //boy add 1 ?
		
		System.out.println("sql : " + sql.toString());
		
		try{
			int id = connectDB.insert(sql.toString(), 
									eGuarantee.getPaymentMethod(),
									eGuarantee.getCustomsName(),
									eGuarantee.getCustomsRef(),
									eGuarantee.getRequestDate(),
									eGuarantee.getDepositAmount(),
									
									eGuarantee.getDeclarationNo(),
									eGuarantee.getDeclarationSeqNo(),
									eGuarantee.getRelateDate(),
									eGuarantee.getDebtorCompanyName(),
									eGuarantee.getDebtorBankAccNo(),
									
									eGuarantee.getDebtorCompanyTaxNo(),
									eGuarantee.getBankGuaranteeNo(),
									eGuarantee.getTransactionStatus(),
									eGuarantee.getMessageCode(),
									eGuarantee.getAlsOnline(),
									
									eGuarantee.getProcessDate(),
									eGuarantee.getCustomsTransDate(),
									eGuarantee.getOriginalCustomsRef(),
									eGuarantee.getXmlOutput()
			                   );
			
			eGuarantee.setId(id);
		}catch (Exception e) {
    		// --: handle exception
    		throw  new Exception(e.getMessage());
    	}
		
	}
	
	public void updateStatusCancel(EGuarantee eGuarantee){
		 
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.E_GUARANTEE);
    	sql.append(" set status = ? ");
    	sql.append(" where bank_guarantee_no = ? ");
    	sql.append(" and payment_method = ? ");
    	sql.append(" and status = ? ");
    	
    	System.out.println("sql : " + sql.toString());
    	
    	connectDB.execute(sql.toString(), "CC", eGuarantee.getBankGuaranteeNo(), "G1N", "SC");

    }
	
}
