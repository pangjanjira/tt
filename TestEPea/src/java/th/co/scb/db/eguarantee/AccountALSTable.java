/**
 * 
 */
package th.co.scb.db.eguarantee;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;



/**
 * @author s51486
 *
 */
public class AccountALSTable {
	
	private ConnectDB connectDB;
	
	public AccountALSTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("AccountALSTable received connection : "+connectDB.hashCode());
	}
	
        public AccountALS findByAccontNo(String accountNo, String purpose){
		 
		 AccountALS accountALS = null;
		 
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.ACCOUNT_ALS);
		 sql.append(" where account_no = ? and purpose = ? and active_flag='Y' ");
	    	
		 System.out.println("sql : " + sql.toString());
	    	 
		 Map<String, Object> result = connectDB.querySingle(sql.toString(), accountNo, purpose);
		 if(result != null){
			 accountALS = new AccountALS(((Long)result.get("id")).intValue(), 
							 	((String)result.get("bank")).trim(), 
							 	(String)result.get("branch"), 
							 	(String)result.get("occode"), 
							 	(String)result.get("account_no"), 
							 	(String)result.get("tax_id"), 
							 	(String)result.get("account_name"), 
							 	(BigDecimal)result.get("line_amt"), 
							 	(BigDecimal)result.get("avaliable_amt"));
		 }
		 
		 return accountALS;
	 }

        public AccountALS findByTaxIdALS(String taxId, String purpose){
		 
		 AccountALS accountALS = null;
		 
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.ACCOUNT_ALS);
		 sql.append(" where tax_id = ? and purpose = ? and active_flag='Y' ");
	    	
		 System.out.println("sql : " + sql.toString());
	    	 
		 Map<String, Object> result = connectDB.querySingle(sql.toString(), taxId, purpose);
		 if(result != null){
			  accountALS = new AccountALS(((Long)result.get("id")).intValue(), 
							 	((String)result.get("bank")).trim(), 
							 	(String)result.get("branch"), 
							 	(String)result.get("occode"), 
							 	(String)result.get("account_no"), 
							 	(String)result.get("tax_id"), 
							 	(String)result.get("account_name"), 
							 	(BigDecimal)result.get("line_amt"), 
							 	(BigDecimal)result.get("avaliable_amt"));
		 }
		 
		 return accountALS;
		 
	 }

	 
	 public AccountALS findByTaxId(String taxId, String purpose){
		 
		 AccountALS accountALS = null;
		 
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.ACCOUNT_ALS);
		 sql.append(" where tax_id = ? and purpose = ? and active_flag='Y' ");
	    	
		 System.out.println("sql : " + sql.toString());
		 Map<String, Object> result = connectDB.querySingle(sql.toString(), taxId, purpose);
		 result = connectDB.querySingle(sql.toString(), taxId, purpose);
		 if(result != null){
			 accountALS = new AccountALS(((Long)result.get("id")).intValue(), 
							 	((String)result.get("bank")).trim(), 
							 	(String)result.get("branch"), 
							 	(String)result.get("occode"), 
							 	(String)result.get("account_no"), 
							 	(String)result.get("tax_id"), 
							 	(String)result.get("account_name"), 
							 	(BigDecimal)result.get("line_amt"), 
							 	(BigDecimal)result.get("avaliable_amt"));
		 }
		 
		 return accountALS;
		 
	 }

	 
	 public void updateAvaliableAmt(AccountALS accountALS){
		 
	    	StringBuilder sql = new StringBuilder();
	    	sql.append(" update ").append(Constants.TableName.ACCOUNT_ALS);
	    	sql.append(" set avaliable_amt = ? ");
	    	sql.append(" where id = ? ");
	    	
	    	System.out.println("sql : " + sql.toString());
	    	
	    	connectDB.execute(sql.toString(), accountALS.getAvaliableAmt(), accountALS.getId());

	 }
	 
	 //Add by Apichart H.@20150518
        public void add(AccountALS accountALS){
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(Constants.TableName.ACCOUNT_ALS).append("(bank, occode, account_no, tax_id, purpose, active_flag, create_dtm, create_by) ");
		sql.append(" values(?,?,?,? ,?,?,?,?) ");	
		int id = connectDB.insert(sql.toString(), 
					accountALS.getBank(),
					accountALS.getOcCode(), 
					accountALS.getAccountNo(), 
					accountALS.getTaxId(), 

					accountALS.getPurpose(), 
					"Y", 
					accountALS.getCreateDtm(), 
					accountALS.getCreateBy()
					);
		accountALS.setId(id);		
	 }
        
   	 public List<AccountALS> findListByTaxId(String taxId, String purpose){
		 List<AccountALS> list = new ArrayList<AccountALS>();
		 
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.ACCOUNT_ALS);
		 sql.append(" where tax_id = ? and purpose=? and active_flag='Y' ");
	    	
		 System.out.println("sql : " + sql.toString());
	    	 
		 List<Map<String, Object>> result = connectDB.queryList(sql.toString(), taxId, purpose);
		 if(result != null){
			 for(Map<String, Object> row : result){
				 AccountALS accountALS = new AccountALS(((Long)row.get("id")).intValue(),
						 	((String)row.get("bank")).trim(), 
						 	(String)row.get("branch"), 
						 	(String)row.get("occode"), 
						 	(String)row.get("account_no"), 
						 	(String)row.get("tax_id"), 
						 	(String)row.get("account_name"), 
						 	(BigDecimal)row.get("line_amt"), 
						 	(BigDecimal)row.get("avaliable_amt"),
						 	(String)row.get("purpose"),
						 	(String)row.get("sub_purpose"),
						 	(String)row.get("active_flag"),
						 	(Date)row.get("create_dtm"),
						 	(String)row.get("create_by"),
						 	(Date)row.get("update_dtm"),
						 	(String)row.get("update_by")
						 	);
				 list.add(accountALS);
			 }
		 }
		 
		 return list;
	 }

	 public boolean isTaxIdHasMultipleAccount(String taxId){
		 boolean result = false;
		 List<AccountALS> list = new ArrayList<AccountALS>();
		 list = findListByTaxId(taxId, Constants.AccountPurpose.ACCOUNT_EGP);
		 if(list != null){
			 if(list.size() > 1){
				 System.out.println("-- tax id[" + taxId + "] has " + String.valueOf(list.size()) + " (multiple type 09) --");
				 result = true;
			 }else{
				 System.out.println("-- tax id[" + taxId + "] has " + String.valueOf(list.size()) + " (one type 09) --");
			 }
		 }else{
			 System.out.println("-- tax id[" + taxId + "] has no account (type 09) --");
		 }
		 return result;
	 }


}
