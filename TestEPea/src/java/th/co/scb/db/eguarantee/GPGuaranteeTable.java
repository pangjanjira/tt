/**
 * 
 */
package th.co.scb.db.eguarantee;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.AccountALS;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;

/**
 * @author s51486
 *
 */
public class GPGuaranteeTable {
	
	private ConnectDB connectDB;
	
	public GPGuaranteeTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
        System.out.println("GPGuaranteeTable received connection : "+connectDB.hashCode());	
	}
	
	public int add(GPGuarantee gpGuarantee) throws Exception{
		int id=0;
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(Constants.TableName.GP_GUARANTEE);
		sql.append(" (	");
			sql.append(" issue_type, tx_ref, dtm, proj_no, dept_code, ");
			sql.append(" vendor_tax_id, vendor_name, comp_id, user_id, seq_no, ");
			sql.append(" consider_desc, consider_money,	guarantee_amt,	contract_no, contract_date, ");
			sql.append(" guarantee_price, guarantee_percent, advance_guarantee_price, advance_payment, works_guarantee_price, ");
			sql.append(" works_guarantee_percent, collection_phase, end_date, start_date, bond_type, ");
			sql.append(" proj_name, proj_amt, proj_own_name, cost_center, cost_center_name, ");
			sql.append(" document_no, document_date, expire_date, status_lg, appv_amt, ");
			sql.append(" appv_date, lg_no, status, als_online, process_date, ");
			sql.append(" add_date, xml_output, account_no, msg_code, amt_req,  ");
			sql.append(" occode, "); //update by Apichart H. 20150513
			sql.append(" review_status, review_dtm, review_by, review_reason, approve_status, ");
			sql.append(" approve_dtm, approve_by, approve_reason ");
			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
			//added
			sql.append(" , extend_date ");
			
		sql.append(" ) "); 
		sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,");
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//changed from
		//sql.append(" ?,?,?,?,?, ?,?,?,?,?,  ?,?,?,?,?, now(),?,?,?,?,?,  ?,?,?,?,?,  ?,?,?)");
		//changed to
		sql.append(" ?,?,?,?,?, ?,?,?,?,?,  ?,?,?,?,?, now(),?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?)");
		System.out.println("sql : " + sql.toString());
		
		try{
			id = connectDB.insert(sql.toString(), 
									gpGuarantee.getIssueType(),
									gpGuarantee.getTxRef(),
									gpGuarantee.getDtm(),
									gpGuarantee.getProjNo(),
									gpGuarantee.getDeptCode(),
									gpGuarantee.getVendorTaxId(),
									gpGuarantee.getVendorName(),
									gpGuarantee.getCompId(),
									gpGuarantee.getUserId(),
									gpGuarantee.getSeqNo(),
									gpGuarantee.getConsiderDesc(),
									gpGuarantee.getConsiderMoney(),
									gpGuarantee.getGuaranteeAmt(),
									gpGuarantee.getContractNo(),
									gpGuarantee.getContractDate(),
									gpGuarantee.getGuaranteePrice(),
									gpGuarantee.getGuaranteePercent(),
									gpGuarantee.getAdvanceGuaranteePrice(),
									gpGuarantee.getAdvancePayment(),
									gpGuarantee.getWorksGuaranteePrice(),
									gpGuarantee.getWorksGuaranteePercent(),
									gpGuarantee.getCollectionPhase(),
									gpGuarantee.getEndDate(),
									gpGuarantee.getStartDate(),
									gpGuarantee.getBondType(),
									gpGuarantee.getProjName(),
									gpGuarantee.getProjAmt(),
									gpGuarantee.getProjOwnName(),
									gpGuarantee.getCostCenter(),
									gpGuarantee.getCostCenterName(),
									gpGuarantee.getDocumentNo(),
									gpGuarantee.getDocumentDate(),
									gpGuarantee.getExpireDate(),
									gpGuarantee.getStatusLG(),
									gpGuarantee.getAppvAmt(),
									gpGuarantee.getAppvDate(),
									gpGuarantee.getLgNo(),
									gpGuarantee.getTransactionStatus(),
									gpGuarantee.getAlsOnline(),
									gpGuarantee.getProcessDate(),
									gpGuarantee.getXmlOutput(),
									gpGuarantee.getAccountNo(),
									gpGuarantee.getMsgCode(),
									gpGuarantee.getAmtReq(),
									gpGuarantee.getOcCode(),
									gpGuarantee.getReviewStatus(),
									gpGuarantee.getReviewDtm(),
									gpGuarantee.getReviewBy(),
									gpGuarantee.getReviewReason(),
									gpGuarantee.getApproveStatus(),
									gpGuarantee.getApproveDtm(),
									gpGuarantee.getApproveBy(),
									gpGuarantee.getApproveReason()
									//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
									//added
									, gpGuarantee.getExtendDate()
			                   );
			
			gpGuarantee.setId(id);
		}catch (Exception e) {
    		throw  new Exception(e.getMessage());
    	}
		return id;
	}
	
	public void updateStatusCancel(GPGuarantee gpGuarantee){
		 
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
    	sql.append(" set status = ? ");
    	sql.append(" where lg_no = ? ");
    	sql.append(" and issue_type = ? ");
    	//sql.append(" and status = ? ");
    	System.out.println("sql : " + sql.toString());
    	//connectDB.execute(sql.toString(), Constants.EGuarantee.STATUS_CANCEL , gpGuarantee.getLgNo(), Constants.GPGuarantee.SETUP_ISSUE, Constants.EGuarantee.STATUS_SUCCESS);
    	connectDB.execute(sql.toString(), Constants.EGuarantee.STATUS_CANCEL , gpGuarantee.getLgNo(), Constants.GPGuarantee.SETUP_ISSUE);
	}
	
	public void updateStatusClaim(GPGuarantee gpGuarantee){
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
    	sql.append(" set status = ? ");
    	sql.append(" where lg_no = ? ");
    	sql.append(" and issue_type = ? ");
    	//sql.append(" and status = ? ");
    	System.out.println("sql : " + sql.toString());
    	//connectDB.execute(sql.toString(), Constants.EGuarantee.STATUS_CLAIM , gpGuarantee.getLgNo(), Constants.GPGuarantee.SETUP_ISSUE, Constants.EGuarantee.STATUS_SUCCESS);
    	connectDB.execute(sql.toString(), Constants.EGuarantee.STATUS_CLAIM , gpGuarantee.getLgNo(), Constants.GPGuarantee.SETUP_ISSUE);
    }
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public void updateStatusExtendExpiryDate(GPGuarantee gpGuarantee){
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
    	sql.append(" set status = ? ");
    	sql.append(" where lg_no = ? ");
    	sql.append(" and issue_type = ? ");
    	System.out.println("sql : " + sql.toString());
    	connectDB.execute(sql.toString(), Constants.EGuarantee.STATUS_EXTEND_EXPIRY_DATE , gpGuarantee.getLgNo(), Constants.GPGuarantee.SETUP_ISSUE);
    }
		
	//R58060012 : add by bussara.b @20150615
	public void updateApproveStatus(GPGuarantee gpGuarantee) {
		StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
    	sql.append(" set approve_status = ? ");
    	sql.append("    ,account_no    = ? ");
    	sql.append("    ,approve_dtm    = ? ");
    	sql.append("    ,approve_by     = ? ");
    	sql.append("    ,approve_reason     = ? ");
    	sql.append("    ,status_lg     = '00' ");
    	sql.append("    ,occode     = ? ");
    	sql.append(" where id = ? ");
    	
    	connectDB.execute(sql.toString(),
    			          gpGuarantee.getApproveStatus(),
    			          gpGuarantee.getAccountNo(),
    			          gpGuarantee.getApproveDtm(),
    			          gpGuarantee.getApproveBy(),
    			          gpGuarantee.getApproveReason(),
    			          gpGuarantee.getOcCode(),
    			          gpGuarantee.getId());
	}	
	
	//R58060012 : add by bussara.b @20150611
	public void updateApprovalProcess(GPGuarantee gpGuarantee) {
		
		StringBuilder sql = new StringBuilder();
    	sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
        sql.append(" set approve_status = ? ");
    	sql.append("    ,account_no     = ? ");
    	sql.append("    ,approve_dtm    = ? ");
    	sql.append("    ,approve_by     = ? ");
    	sql.append("    ,approve_reason = ? ");
    	sql.append("    ,lg_no = ? ");
    	sql.append("    ,status         = ? ");
    	sql.append("    ,status_lg      = ? ");
    	sql.append("    ,msg_code       = ? ");
    	sql.append("    ,xml_output     = ? ");
    	sql.append("    ,process_date     = ? ");
    	sql.append(" where id = ? ");
    	
    	connectDB.execute(sql.toString(),
    			          gpGuarantee.getApproveStatus(),
    			          gpGuarantee.getAccountNo(),
    			          gpGuarantee.getApproveDtm(),
    			          gpGuarantee.getApproveBy(),
    			          gpGuarantee.getApproveReason(),
    			          gpGuarantee.getLgNo(),
    			          gpGuarantee.getTransactionStatus(),
    			          gpGuarantee.getStatusLG(),
    			          gpGuarantee.getMsgCode(),
    			          gpGuarantee.getXmlOutput(),
    			        //Fix IM 2014-08-24 - 3. update process_date for the approve/reject records can be seen on reports
    			          gpGuarantee.getProcessDate(),
    			          gpGuarantee.getId());
		
	}
	
	//R58060012 : add by bussara.b @20150616
	public void updateApproveStatus(int gpGuaranteeId) {
		
		StringBuilder sql = new StringBuilder();
    	sql.append(" update gp_guarantee a inner join gp_approval_log b on (a.id = b.gp_guarantee_id) ");
    	sql.append("                       inner join (select max(approve_dtm) as approve_dtm) ");
    	sql.append("                                   from   gp_approval_log ");
    	sql.append("                                   where  gp_guarantee_id = ?) c on (b.approve_dtm = c.approve_dtm) ");
    	sql.append(" set    a.approve_status  = b.approve_status ");
    	sql.append("       ,a.approve_dtm     = b.approve_dtm ");
    	sql.append("       ,a.approve_by      = b.approve_by ");
    	sql.append(" where  b.gp_guarantee_id = ? ");
    	
    	connectDB.execute(sql.toString(), gpGuaranteeId, gpGuaranteeId);
		
	}
	
	//R58060012 : add by bussara.b @20150616
	public void updateStatusBatch(GPGuarantee gpGuarantee, boolean isSuccess) {
			
		StringBuilder sql = new StringBuilder();
	    sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
	    sql.append(" set   status_lg  = ? ");
	    sql.append("      ,status     = ? ");
	    sql.append("      ,msg_code   = ? ");
	    if(!isSuccess){
	    	sql.append("      ,lg_no = '' ");
	    }
	    sql.append("      ,xml_output = ? ");
	    sql.append(" where lg_no      = ? ");
	    sql.append(" and   issue_type = ? ");
	    
	    int updatedRows = connectDB.execute(sql.toString(),
	    		          gpGuarantee.getStatusLG(),
	    		          gpGuarantee.getTransactionStatus(),
	    		          gpGuarantee.getMsgCode(),
	    		          gpGuarantee.getXmlOutput(),
	    		          gpGuarantee.getLgNo(),
	    		          gpGuarantee.getIssueType());
	    System.out.println("updateStatusBatch: " + String.valueOf(updatedRows) + " rows updated.");
	}
	
	//R58060012 : add by pariwat.s @20150622
	public String getTxRefById(GPGuarantee gpGuarantee,int id) {
		
		StringBuilder sql = new StringBuilder();
	    sql.append(" select tx_ref from ").append(Constants.TableName.GP_GUARANTEE);
	    sql.append(" where id = ? ");	    
    	Map<String, Object> result = connectDB.querySingle(sql.toString(), id);
       	
	   	return (result != null ? (String)result.get("tx_ref") : null);
	}
	
	//------- Add by Tana L. @11042016
	//UR58120031 Phase 2 (fix update egp_ack_* in gp_guarantee)
		public int getIdByLgNo(String lgNo, String issueType) {
			
			StringBuilder sql = new StringBuilder();
		    sql.append(" select id from ").append(Constants.TableName.GP_GUARANTEE);
		    sql.append(" where lg_no = ? ");
		    sql.append(" and issue_type = ? ");
		    sql.append(" order by id desc ");
	    	Map<String, Object> result = connectDB.querySingle(sql.toString(), lgNo, issueType);
	       	
		   	return (result != null ? (Integer)result.get("id") : null);
		}
	
	public AccountALS getSetupAccountFromLgNo(String lgNo) {
		AccountALS output = null;
		StringBuilder sql = new StringBuilder();
	    sql.append(" select ifnull(account_no, '') as account_no, ifnull(occode, '') as occode from ").append(Constants.TableName.GP_GUARANTEE);
	    sql.append(" where issue_type='0004' and lg_no = ? "); 
    	Map<String, Object> result = connectDB.querySingle(sql.toString(), lgNo);
       	if(result != null){
       		output = new AccountALS();
       		output.setAccountNo((String)result.get("account_no"));
       		output.setOcCode((String)result.get("occode"));
       	}
	   	return output;
	}	

	public GPGuarantee getGPGuaranteeById(int id){
		GPGuarantee output = new GPGuarantee();
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, issue_type, tx_ref, dtm, proj_no, dept_code, account_no ");
		sql.append(" , vendor_tax_id, vendor_name, comp_id, user_id, seq_no ");
		sql.append(" , consider_desc, consider_money, guarantee_amt, amt_req ");
		sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
		sql.append(" , advance_guarantee_price, advance_payment ");
		sql.append(" , works_guarantee_price, works_guarantee_percent ");
		sql.append(" , collection_phase, end_date, start_date, bond_type ");
		sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
		sql.append(" , document_no, document_date, expire_date ");
		sql.append(" , status_lg, appv_amt, appv_date, lg_no ");
		sql.append(" , status, msg_code, als_online, process_date, add_date, xml_output, occode ");
		sql.append(" , review_status, review_dtm, review_by, review_reason ");
		//sql.append(" , approve_status, approve_dtm, approve_by, approve_reason ");
		sql.append(" , approve_status, approve_dtm, approve_by, approve_reason, extend_date ");
		sql.append(" from ");
		sql.append(Constants.TableName.GP_GUARANTEE);
		sql.append(" where id = ? ");
		System.out.println("sql : " + sql.toString());
		Map<String, Object> result = connectDB.querySingle(sql.toString(),id);
		
		 if(result != null){
			 output = new GPGuarantee();
			 output.setId(id);
			 output.setIssueType((String)result.get("issue_type"));
			 output.setTxRef((String)result.get("tx_ref"));
			 output.setDtm((String)result.get("dtm"));
			 output.setProjNo((String)result.get("proj_no"));
			 output.setDeptCode((String)result.get("dept_code"));
			 output.setAccountNo((String)result.get("account_no"));
			 //System.out.println("Account No.:" + output.getAccountNo());
			 
			 output.setVendorTaxId((String)result.get("vendor_tax_id"));
			 output.setVendorName((String)result.get("vendor_name"));
			 output.setCompId((String)result.get("comp_id"));
			 output.setUserId((String)result.get("user_id"));
			 
			 int SeqNo = 0;
			 try{
				 SeqNo = (Integer)result.get("seq_no");
			 }catch(Exception e){
				 System.out.println("Error while casting seq_no");
			 }
			 output.setSeqNo(SeqNo);
			 output.setConsiderDesc((String)result.get("consider_desc"));
			 
			 BigDecimal ConsiderMoney = result.get("consider_money") != null ? (BigDecimal)result.get("consider_money") : new BigDecimal(0);
			 output.setConsiderMoney(ConsiderMoney);
			 
			 BigDecimal GuaranteeAmt = result.get("guarantee_amt") != null ? (BigDecimal)result.get("guarantee_amt") : new BigDecimal(0);
			 output.setGuaranteeAmt(GuaranteeAmt);
			 
			 BigDecimal AmtReq = result.get("amt_req") != null ? (BigDecimal)result.get("amt_req") : new BigDecimal(0);
			 output.setAmtReq(AmtReq);
			 
			 output.setContractNo((String)result.get("contract_no"));
			 output.setContractDate((String)result.get("contract_date"));
			 
			 BigDecimal GuaranteePrice = result.get("guarantee_price") != null ? (BigDecimal)result.get("guarantee_price") : new BigDecimal(0);
			 output.setGuaranteePrice(GuaranteePrice);
			 
			 BigDecimal GuaranteePercent = result.get("guarantee_percent") != null ? (BigDecimal)result.get("guarantee_percent") : new BigDecimal(0);
			 output.setGuaranteePercent(GuaranteePercent);
			 
			 BigDecimal AdvanceGuaranteePrice = result.get("advance_guarantee_price") != null ? (BigDecimal)result.get("advance_guarantee_price") : new BigDecimal(0);
			 output.setAdvanceGuaranteePrice(AdvanceGuaranteePrice);
			 
			 BigDecimal AdvancePayment = result.get("advance_payment") != null ? (BigDecimal)result.get("advance_payment") : new BigDecimal(0);
			 output.setAdvancePayment(AdvancePayment);
			 
			 BigDecimal WorksGuaranteePrice = result.get("works_guarantee_price") != null ? (BigDecimal)result.get("works_guarantee_price") : new BigDecimal(0);
			 output.setWorksGuaranteePrice(WorksGuaranteePrice);
			 
			 BigDecimal WorksGuaranteePercent = result.get("works_guarantee_percent") != null ? (BigDecimal)result.get("works_guarantee_percent") : new BigDecimal(0);
			 output.setWorksGuaranteePercent(WorksGuaranteePercent);
			 
			 output.setCollectionPhase((String)result.get("collection_phase"));
			 output.setEndDate((String)result.get("end_date"));
			 output.setStartDate((String)result.get("start_date"));
			 output.setBondType((String)result.get("bond_type"));
			 output.setProjName((String)result.get("proj_name"));
			 
			 BigDecimal ProjAmt = result.get("proj_amt") != null ? (BigDecimal)result.get("proj_amt") : new BigDecimal(0);
			 output.setProjAmt(ProjAmt);
			 
			 output.setProjOwnName((String)result.get("proj_own_name"));
			 output.setCostCenter((String)result.get("cost_center"));
			 output.setCostCenterName((String)result.get("cost_center_name"));
			 output.setDocumentNo((String)result.get("document_no"));
			 output.setDocumentDate((String)result.get("document_date"));
			 output.setExpireDate((String)result.get("expire_date"));
			 output.setStatusLG((String)result.get("status_lg"));
			 
			 BigDecimal AppvAmt = result.get("appv_amt") != null ? (BigDecimal)result.get("appv_amt") : new BigDecimal(0);
			 output.setAppvAmt(AppvAmt);
			 
			 //output.setAppvDate((String)result.get("appv_date"));
			 output.setLgNo((String)result.get("lg_no"));
			 output.setTransactionStatus((String)result.get("status"));
			 output.setMsgCode((String)result.get("msg_code"));
			 output.setAlsOnline((String)result.get("als_online"));
			 
			 //output.setProcessDate((java.util.Date)result.get("process_date"));
			 output.setXmlOutput((String)result.get("xml_output"));
			 output.setOcCode((String)result.get("occode"));
			 output.setReviewStatus((String)result.get("review_status"));

			 //output.setReviewDtm((java.util.Date)result.get("review_dtm"));
			 output.setReviewBy((String)result.get("review_by"));
			 output.setReviewReason((String)result.get("review_reason"));
			 output.setApproveStatus((String)result.get("approve_status"));
			 
			 //output.setApproveDtm((java.util.Date)result.get("approve_dtm"));
			 output.setApproveBy((String)result.get("approve_by"));
			 output.setApproveReason((String)result.get("approve_reason"));
			 
			 output.setExtendDate((String)result.get("extend_date"));
		 }
		 
		 return output;
	}
	
	public boolean isDupGPGuaranteeTxn(GPGuarantee gpGuarantee, String type) {
		boolean isDup = false;
		try{
			StringBuilder sql = new StringBuilder();
		    sql.append(" select count(*) as counter from ").append(Constants.TableName.GP_GUARANTEE);
		    sql.append(" where issue_type='0004' ");
		    if("approved_success".equalsIgnoreCase(type)){
		    	sql.append(" and (approve_status='AP' and status_lg='01') ");
		    }else if("rejected".equalsIgnoreCase(type)){
		    	sql.append(" and (approve_status='RJ' and msg_code<>'AM05') ");
		    }else if("completed".equalsIgnoreCase(type)){
		    	sql.append(" and ((approve_status='AP' and status_lg='01') or (approve_status='RJ' and IFNULL(msg_code, '-')<>'AM05')) ");
		    	sql.append(" and id<>? ");
		    }
		   //sql.append(" and tx_ref=? "); update by Malinee T. UR58100048 @20160105
		    sql.append(" and proj_no=? ");
		    sql.append(" and vendor_tax_id=? ");
		    sql.append(" and seq_no=? ");
		    sql.append(" and bond_type=? ");

		    //add by Malinee T. UR58100048 @20160104
		    sql.append(" and guarantee_amt = ?");
		    sql.append(" and end_date = ?");
		    
		    Map<String, Object> result = null;
		    
		    // update by Malinee T. UR58100048 @20160104
//		    if("completed".equalsIgnoreCase(type)){
//                result = connectDB.querySingle(sql.toString(), gpGuarantee.getId(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType());
//		    }else{
//                result = connectDB.querySingle(sql.toString(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType());
//		    }
		    
		    if("completed".equalsIgnoreCase(type)){
		        //result = connectDB.querySingle(sql.toString(), gpGuarantee.getId(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate());
		    	result = connectDB.querySingle(sql.toString(), gpGuarantee.getId(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate());
		    }else{
		       // result = connectDB.querySingle(sql.toString(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate());
		    	result = connectDB.querySingle(sql.toString(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(), gpGuarantee.getBondType(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate());
		    }
		    
	       	if(result != null){
	       		if((Long)result.get("counter") > 0){
	       			isDup = true;
	       		}
	       	}
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	   	return isDup;
	}

    //R58060060 : add by Apichart.H @20151019
    public void updateReviewStatus(GPGuarantee gpGuarantee) {
    	
        //R58060060 : 2015-11-27 : Fix UAT Siwat.N
        //update occode field

        StringBuilder sql = new StringBuilder();
        sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
        sql.append(" set review_status = ? ");
        sql.append("    ,account_no    = ? ");
        sql.append("    ,review_dtm    = ? ");
        sql.append("    ,review_by     = ? ");
        sql.append("    ,review_reason     = ? ");
        sql.append("    ,approve_status = 'PA'");
        //added
        sql.append("    ,occode     = ? ");
        //protect duplicate approval
        sql.append(" where id = ? ");

        connectDB.execute(sql.toString(),
                gpGuarantee.getReviewStatus(),
                gpGuarantee.getAccountNo(),
                gpGuarantee.getReviewDtm(),
                gpGuarantee.getReviewBy(),
                gpGuarantee.getReviewReason(),
                //added
                gpGuarantee.getOcCode(),
                
                gpGuarantee.getId());

    }
    
    //UR59040034 Add eGP Pending Review & Resend Response function
    public void updateResendCount(GPGuarantee gpGuarantee) {

        StringBuilder sql = new StringBuilder();
        sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
        sql.append(" set resend_count = resend_count+1 ");
        sql.append(" where id = ? ");

        connectDB.execute(sql.toString(),
                gpGuarantee.getId());
    }
    
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //added
	public GPGuarantee getGPGuaranteeByLGNo(String lgNo){
		GPGuarantee output = new GPGuarantee();
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, issue_type, tx_ref, dtm, proj_no, dept_code, account_no ");
		sql.append(" , vendor_tax_id, vendor_name, comp_id, user_id, seq_no ");
		sql.append(" , consider_desc, consider_money, guarantee_amt, amt_req ");
		sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
		sql.append(" , advance_guarantee_price, advance_payment ");
		sql.append(" , works_guarantee_price, works_guarantee_percent ");
		sql.append(" , collection_phase, end_date, start_date, bond_type ");
		sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
		sql.append(" , document_no, document_date, expire_date ");
		sql.append(" , status_lg, appv_amt, appv_date, lg_no ");
		sql.append(" , status, msg_code, als_online, process_date, add_date, xml_output, occode ");
		sql.append(" , review_status, review_dtm, review_by, review_reason ");
		//sql.append(" , approve_status, approve_dtm, approve_by, approve_reason ");
		sql.append(" , approve_status, approve_dtm, approve_by, approve_reason, extend_date ");
		sql.append(" from ");
		sql.append(Constants.TableName.GP_GUARANTEE);
		sql.append(" where lg_no = ? ");
		//UR58120031 Phase 2
		sql.append(" and issue_type='0004' ");
		System.out.println("sql : " + sql.toString());
		Map<String, Object> result = connectDB.querySingle(sql.toString(), lgNo);
		if(result != null){
			output = new GPGuarantee();
			output.setId((Integer)result.get("id"));
			output.setIssueType((String)result.get("issue_type"));
			output.setTxRef((String)result.get("tx_ref"));
			output.setDtm((String)result.get("dtm"));
			output.setProjNo((String)result.get("proj_no"));
			output.setDeptCode((String)result.get("dept_code"));
			output.setAccountNo((String)result.get("account_no"));
			output.setVendorTaxId((String)result.get("vendor_tax_id"));
			output.setVendorName((String)result.get("vendor_name"));
			output.setCompId((String)result.get("comp_id"));
			output.setUserId((String)result.get("user_id"));
			
			int SeqNo = 0;
			try{
				SeqNo = (Integer)result.get("seq_no");
			}catch(Exception e){
				System.out.println("Error while casting seq_no");
			}
			output.setSeqNo(SeqNo);
			output.setConsiderDesc((String)result.get("consider_desc"));
			
			BigDecimal ConsiderMoney = result.get("consider_money") != null ? (BigDecimal)result.get("consider_money") : new BigDecimal(0);
			output.setConsiderMoney(ConsiderMoney);
			
			BigDecimal GuaranteeAmt = result.get("guarantee_amt") != null ? (BigDecimal)result.get("guarantee_amt") : new BigDecimal(0);
			output.setGuaranteeAmt(GuaranteeAmt);
			
			BigDecimal AmtReq = result.get("amt_req") != null ? (BigDecimal)result.get("amt_req") : new BigDecimal(0);
			output.setAmtReq(AmtReq);
			
			output.setContractNo((String)result.get("contract_no"));
			output.setContractDate((String)result.get("contract_date"));
			
			BigDecimal GuaranteePrice = result.get("guarantee_price") != null ? (BigDecimal)result.get("guarantee_price") : new BigDecimal(0);
			output.setGuaranteePrice(GuaranteePrice);
			BigDecimal GuaranteePercent = result.get("guarantee_percent") != null ? (BigDecimal)result.get("guarantee_percent") : new BigDecimal(0);
			output.setGuaranteePercent(GuaranteePercent);
			
			BigDecimal AdvanceGuaranteePrice = result.get("advance_guarantee_price") != null ? (BigDecimal)result.get("advance_guarantee_price") : new BigDecimal(0);
			output.setAdvanceGuaranteePrice(AdvanceGuaranteePrice);
			 
			BigDecimal AdvancePayment = result.get("advance_payment") != null ? (BigDecimal)result.get("advance_payment") : new BigDecimal(0);
			output.setAdvancePayment(AdvancePayment);
			 
			BigDecimal WorksGuaranteePrice = result.get("works_guarantee_price") != null ? (BigDecimal)result.get("works_guarantee_price") : new BigDecimal(0);
			output.setWorksGuaranteePrice(WorksGuaranteePrice);
			
			BigDecimal WorksGuaranteePercent = result.get("works_guarantee_percent") != null ? (BigDecimal)result.get("works_guarantee_percent") : new BigDecimal(0);
			output.setWorksGuaranteePercent(WorksGuaranteePercent);
			 
			output.setCollectionPhase((String)result.get("collection_phase"));
			output.setEndDate((String)result.get("end_date"));
			output.setStartDate((String)result.get("start_date"));
			output.setBondType((String)result.get("bond_type"));
			output.setProjName((String)result.get("proj_name"));
			
			BigDecimal ProjAmt = result.get("proj_amt") != null ? (BigDecimal)result.get("proj_amt") : new BigDecimal(0);
			output.setProjAmt(ProjAmt);
			
			output.setProjOwnName((String)result.get("proj_own_name"));
			output.setCostCenter((String)result.get("cost_center"));
			output.setCostCenterName((String)result.get("cost_center_name"));
			output.setDocumentNo((String)result.get("document_no"));
			output.setDocumentDate((String)result.get("document_date"));
			output.setExpireDate((String)result.get("expire_date"));
			output.setStatusLG((String)result.get("status_lg"));
			BigDecimal AppvAmt = result.get("appv_amt") != null ? (BigDecimal)result.get("appv_amt") : new BigDecimal(0);
			output.setAppvAmt(AppvAmt);
			output.setLgNo((String)result.get("lg_no"));
			output.setTransactionStatus((String)result.get("status"));
			output.setMsgCode((String)result.get("msg_code"));
			output.setAlsOnline((String)result.get("als_online"));
			output.setXmlOutput((String)result.get("xml_output"));
			output.setOcCode((String)result.get("occode"));
			output.setReviewStatus((String)result.get("review_status"));
			output.setReviewBy((String)result.get("review_by"));
			output.setReviewReason((String)result.get("review_reason"));
			output.setApproveStatus((String)result.get("approve_status"));
			output.setApproveBy((String)result.get("approve_by"));
			output.setApproveReason((String)result.get("approve_reason"));
			output.setExtendDate((String)result.get("extend_date"));
		}		 
		return output;
	}
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //added
	public void addSetupTxnHistoryFromLGNo(String actionBy, String lgNo) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO gp_guarantee_h ");
		sql.append(" (	");
			sql.append(" action_type, action_dtm, action_by ");
			sql.append(" , gp_guarantee_id, issue_type, tx_ref, dtm, proj_no ");
			sql.append(" , dept_code, account_no, vendor_tax_id, vendor_name, comp_id, user_id ");
			sql.append(" , seq_no, consider_desc, consider_money, guarantee_amt, amt_req ");
			sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
			sql.append(" , advance_guarantee_price, advance_payment, works_guarantee_price, works_guarantee_percent ");
			sql.append(" , collection_phase, end_date, start_date, bond_type ");
			sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
			sql.append(" , document_no, document_date, expire_date, extend_date, status_lg ");
			sql.append(" , appv_amt, appv_date, lg_no, `status`, msg_code, als_online, process_date, add_date ");
			sql.append(" , xml_output, occode, review_status, review_dtm, review_by, review_reason ");
			sql.append(" , approve_status, approve_dtm, approve_by, approve_reason ");
			sql.append(" , egp_ack_status, egp_ack_dtm, egp_ack_tranx_id, egp_ack_code, egp_ack_msg) ");
		sql.append(" SELECT ");
			sql.append(" ? AS action_type, NOW() AS action_dtm, ? AS action_by ");
			sql.append(" , id AS gp_guarantee_id, issue_type, tx_ref, dtm, proj_no ");
			sql.append(" , dept_code, account_no, vendor_tax_id, vendor_name, comp_id, user_id ");
			sql.append(" , seq_no, consider_desc, consider_money, guarantee_amt, amt_req ");
			sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
			sql.append(" , advance_guarantee_price, advance_payment, works_guarantee_price, works_guarantee_percent ");
			sql.append(" , collection_phase, end_date, start_date, bond_type ");
			sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
			sql.append(" , document_no, document_date, expire_date, extend_date, status_lg ");
			sql.append(" , appv_amt, appv_date, lg_no, `status`, msg_code, als_online, process_date, add_date ");
			sql.append(" , xml_output, occode, review_status, review_dtm, review_by, review_reason ");
			sql.append(" , approve_status, approve_dtm, approve_by, approve_reason ");
			sql.append(" , egp_ack_status, egp_ack_dtm, egp_ack_tranx_id, egp_ack_code, egp_ack_msg ");
		sql.append(" FROM gp_guarantee ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND issue_type='0004' ");
		sql.append(" AND lg_no=? ");
		System.out.println("sql : " + sql.toString());
		
		try{
			int id = connectDB.insert(sql.toString()
					, Constants.GPGuarantee.ACTION_EXTEND_EXPIRY_DATE
					, actionBy
					, lgNo);
		}catch (Exception e) {
    		throw  new Exception(e.getMessage());
    	}
		
	}
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //added
    public void updateSetupTxnExpireDateAndStatus(String extendDate, String lgNo) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append(Constants.TableName.GP_GUARANTEE);
        sql.append(" SET end_date = ? ");
        sql.append(" , `status` = ? ");
        sql.append(" WHERE lg_no = ? ");
        sql.append(" AND issue_type = '0004' ");
        
        

        connectDB.execute(sql.toString(),
                extendDate
                , Constants.EGuarantee.STATUS_EXTEND_EXPIRY_DATE
                , lgNo);

    }
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //added
    public void updateExtendTxnApprovalStatus(GPGuarantee gpGuarantee) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append(Constants.TableName.GP_GUARANTEE);
        sql.append(" SET approve_status = ? ");
    	sql.append("    ,approve_dtm    = ? ");
    	sql.append("    ,approve_by     = ? ");
    	sql.append("    ,approve_reason = ? ");
    	sql.append("    ,status         = ? ");
    	sql.append("    ,status_lg      = ? ");
    	sql.append("    ,msg_code       = ? ");
    	sql.append("    ,xml_output     = ? ");
    	sql.append("    ,process_date     = ? ");
    	sql.append(" where id= ? ");
        connectDB.execute(sql.toString(),
		          gpGuarantee.getApproveStatus(),
		          gpGuarantee.getApproveDtm(),
		          gpGuarantee.getApproveBy(),
		          gpGuarantee.getApproveReason(),
		          gpGuarantee.getTransactionStatus(),
		          gpGuarantee.getStatusLG(),
		          gpGuarantee.getMsgCode(),
		          gpGuarantee.getXmlOutput(),
		          gpGuarantee.getProcessDate(),
		          gpGuarantee.getId());

    }
    
    //UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
  	private GPGuarantee setRowToBean(int id, Map<String, Object> result){
  		GPGuarantee output = new GPGuarantee();
  		int seq = 1;
  		try{
  			Long seqLong = (Long)result.get("row_no");
  			seq = seqLong.intValue();
  			System.out.println("seq: " + String.valueOf(seq));
  		}catch(Exception e){
  			//nothing
  			System.out.println("set seq error: " + e.getMessage());
  		}
  		output.setSeq(seq);
  		output.setId(id);
  		output.setIssueType((String)result.get("issue_type"));
  		output.setTxRef((String)result.get("tx_ref"));
  		output.setDtm((String)result.get("dtm"));
  		output.setProjNo((String)result.get("proj_no"));
  		output.setDeptCode((String)result.get("dept_code"));
  		output.setAccountNo((String)result.get("account_no"));
  		//System.out.println("Account No.:" + output.getAccountNo());
  		 
  		output.setVendorTaxId((String)result.get("vendor_tax_id"));
  		output.setVendorName((String)result.get("vendor_name"));
  		output.setCompId((String)result.get("comp_id"));
  		output.setUserId((String)result.get("user_id"));
  		 
  		int SeqNo = 0;
  		try{
  			SeqNo = (Integer)result.get("seq_no");
  		}catch(Exception e){
  			System.out.println("Error while casting seq_no");
  		}
  		output.setSeqNo(SeqNo);
  		output.setConsiderDesc((String)result.get("consider_desc"));
  		 
  		BigDecimal ConsiderMoney = result.get("consider_money") != null ? (BigDecimal)result.get("consider_money") : new BigDecimal(0);
  		output.setConsiderMoney(ConsiderMoney);
  		 
  		BigDecimal GuaranteeAmt = result.get("guarantee_amt") != null ? (BigDecimal)result.get("guarantee_amt") : new BigDecimal(0);
  		output.setGuaranteeAmt(GuaranteeAmt);
  		 
  		BigDecimal AmtReq = result.get("amt_req") != null ? (BigDecimal)result.get("amt_req") : new BigDecimal(0);
  		output.setAmtReq(AmtReq);
  		 
  		output.setContractNo((String)result.get("contract_no"));
  		output.setContractDate((String)result.get("contract_date"));
  		 
  		BigDecimal GuaranteePrice = result.get("guarantee_price") != null ? (BigDecimal)result.get("guarantee_price") : new BigDecimal(0);
  		output.setGuaranteePrice(GuaranteePrice);
  		 
  		BigDecimal GuaranteePercent = result.get("guarantee_percent") != null ? (BigDecimal)result.get("guarantee_percent") : new BigDecimal(0);
  		output.setGuaranteePercent(GuaranteePercent);
  		 
  		BigDecimal AdvanceGuaranteePrice = result.get("advance_guarantee_price") != null ? (BigDecimal)result.get("advance_guarantee_price") : new BigDecimal(0);
  		output.setAdvanceGuaranteePrice(AdvanceGuaranteePrice);
  		 
  		BigDecimal AdvancePayment = result.get("advance_payment") != null ? (BigDecimal)result.get("advance_payment") : new BigDecimal(0);
  		output.setAdvancePayment(AdvancePayment);
  		 
  		BigDecimal WorksGuaranteePrice = result.get("works_guarantee_price") != null ? (BigDecimal)result.get("works_guarantee_price") : new BigDecimal(0);
  		output.setWorksGuaranteePrice(WorksGuaranteePrice);
  		 
  		BigDecimal WorksGuaranteePercent = result.get("works_guarantee_percent") != null ? (BigDecimal)result.get("works_guarantee_percent") : new BigDecimal(0);
  		output.setWorksGuaranteePercent(WorksGuaranteePercent);
  		 
  		output.setCollectionPhase((String)result.get("collection_phase"));
  		output.setEndDate((String)result.get("end_date"));
  		output.setStartDate((String)result.get("start_date"));
  		output.setBondType((String)result.get("bond_type"));
  		output.setProjName((String)result.get("proj_name"));
  		 
  		BigDecimal ProjAmt = result.get("proj_amt") != null ? (BigDecimal)result.get("proj_amt") : new BigDecimal(0);
  		output.setProjAmt(ProjAmt);
  		 
  		output.setProjOwnName((String)result.get("proj_own_name"));
  		output.setCostCenter((String)result.get("cost_center"));
  		output.setCostCenterName((String)result.get("cost_center_name"));
  		output.setDocumentNo((String)result.get("document_no"));
  		output.setDocumentDate((String)result.get("document_date"));
  		output.setExpireDate((String)result.get("expire_date"));
  		output.setStatusLG((String)result.get("status_lg"));
  		
  		BigDecimal AppvAmt = result.get("appv_amt") != null ? (BigDecimal)result.get("appv_amt") : new BigDecimal(0);
  		output.setAppvAmt(AppvAmt);
  		 
  		//output.setAppvDate((String)result.get("appv_date"));
  		output.setLgNo((String)result.get("lg_no"));
  		output.setTransactionStatus((String)result.get("status"));
  		output.setMsgCode((String)result.get("msg_code"));
  		output.setAlsOnline((String)result.get("als_online"));
  		 
  		//output.setProcessDate((java.util.Date)result.get("process_date"));
  		output.setXmlOutput((String)result.get("xml_output"));
  		output.setOcCode((String)result.get("occode"));
  		output.setReviewStatus((String)result.get("review_status"));
  		
  		//output.setReviewDtm((java.util.Date)result.get("review_dtm"));
  		output.setReviewBy((String)result.get("review_by"));
  		output.setReviewReason((String)result.get("review_reason"));
  		output.setApproveStatus((String)result.get("approve_status"));
  		 
  		//output.setApproveDtm((java.util.Date)result.get("approve_dtm"));
  		output.setApproveBy((String)result.get("approve_by"));
  		output.setApproveReason((String)result.get("approve_reason"));
  		 
  		output.setExtendDate((String)result.get("extend_date"));
  		return output;
  	}
  	
  	//UR59040034 Add eGP Pending Review & Resend Response function
  	//get logEBXML with the correct sequence
  	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
  	public GPGuarantee getGPGuaranteeWithSeqById(int id){
  		GPGuarantee output = new GPGuarantee();
  		GPGuarantee initGp = getGPGuaranteeById(id);
  		StringBuilder sql = new StringBuilder();
  		sql.append(" select @rownum:=@rownum+1 AS row_no ");
  		sql.append(" , id, issue_type, tx_ref, dtm, proj_no, dept_code, account_no ");
  		sql.append(" , vendor_tax_id, vendor_name, comp_id, user_id, seq_no ");
  		sql.append(" , consider_desc, consider_money, guarantee_amt, amt_req ");
  		sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
  		sql.append(" , advance_guarantee_price, advance_payment ");
  		sql.append(" , works_guarantee_price, works_guarantee_percent ");
  		sql.append(" , collection_phase, end_date, start_date, bond_type ");
  		sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
  		sql.append(" , document_no, document_date, expire_date ");
  		sql.append(" , status_lg, appv_amt, appv_date, lg_no ");
  		sql.append(" , status, msg_code, als_online, process_date, add_date, xml_output, occode ");
  		sql.append(" , review_status, review_dtm, review_by, review_reason ");
  		sql.append(" , approve_status, approve_dtm, approve_by, approve_reason, extend_date ");
  		sql.append(" from ");
  		sql.append(Constants.TableName.GP_GUARANTEE);
  		sql.append(" , (SELECT @rownum:=0) r ");
  		sql.append(" where tx_ref = ? ");
  		System.out.println("sql : " + sql.toString());
  		List<Map<String, Object>> resultList = connectDB.queryList(sql.toString(), initGp.getTxRef());
  		if(resultList != null){
  			for(Map<String, Object> result : resultList){
  				int oId = (Integer)result.get("id");
  				System.out.println("gp_guarantee.id = " + String.valueOf(oId) + " - input.id= " + String.valueOf(id));
  				if(oId == id){
  					output = new GPGuarantee();
  					output = setRowToBean(id, result);
  					System.out.println("gp_guarantee.tx_ref = " + output.getTxRef());
  					System.out.println("gp_guarantee.seq = " + String.valueOf(output.getSeq()));
  				}
  			}
  		}
  		return output;
  	}
  	
  //UR59040034 Add eGP Pending Review & Resend Response function
  	public GPGuarantee getResendGPGuarantee(int id){
		GPGuarantee output = new GPGuarantee();
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, issue_type, tx_ref, dtm, proj_no, dept_code, account_no ");
		sql.append(" , vendor_tax_id, vendor_name, comp_id, user_id, seq_no ");
		sql.append(" , consider_desc, consider_money, guarantee_amt, amt_req ");
		sql.append(" , contract_no, contract_date, guarantee_price, guarantee_percent ");
		sql.append(" , advance_guarantee_price, advance_payment ");
		sql.append(" , works_guarantee_price, works_guarantee_percent ");
		sql.append(" , collection_phase, end_date, start_date, bond_type ");
		sql.append(" , proj_name, proj_amt, proj_own_name, cost_center, cost_center_name ");
		sql.append(" , document_no, document_date, expire_date ");
		sql.append(" , status_lg, appv_amt, appv_date, lg_no ");
		sql.append(" , status, msg_code, als_online, process_date, add_date, xml_output, occode ");
		sql.append(" , review_status, review_dtm, review_by, review_reason ");
		sql.append(" , approve_status, approve_dtm, approve_by, approve_reason, extend_date ");
		sql.append(" from ");
		sql.append(Constants.TableName.GP_GUARANTEE);
		sql.append(" where id = ? ");
		System.out.println("sql : " + sql.toString());
		Map<String, Object> result = connectDB.querySingle(sql.toString(),id);
		
		 if(result != null){
			 output = new GPGuarantee();
			 output.setId(id);
			 output.setIssueType((String)result.get("issue_type"));
			 output.setTxRef((String)result.get("tx_ref"));
			 output.setDtm((String)result.get("dtm"));
			 output.setProjNo((String)result.get("proj_no"));
			 output.setDeptCode((String)result.get("dept_code"));
			 output.setAccountNo((String)result.get("account_no"));
			 //System.out.println("Account No.:" + output.getAccountNo());
			 
			 output.setVendorTaxId((String)result.get("vendor_tax_id"));
			 output.setVendorName((String)result.get("vendor_name"));
			 output.setCompId((String)result.get("comp_id"));
			 output.setUserId((String)result.get("user_id"));
			 
			 int SeqNo = 0;
			 try{
				 SeqNo = (Integer)result.get("seq_no");
			 }catch(Exception e){
				 System.out.println("Error while casting seq_no");
			 }
			 output.setSeqNo(SeqNo);
			 output.setConsiderDesc((String)result.get("consider_desc"));
			 
			 BigDecimal ConsiderMoney = result.get("consider_money") != null ? (BigDecimal)result.get("consider_money") : new BigDecimal(0);
			 output.setConsiderMoney(ConsiderMoney);
			 
			 BigDecimal GuaranteeAmt = result.get("guarantee_amt") != null ? (BigDecimal)result.get("guarantee_amt") : new BigDecimal(0);
			 output.setGuaranteeAmt(GuaranteeAmt);
			 
			 BigDecimal AmtReq = result.get("amt_req") != null ? (BigDecimal)result.get("amt_req") : new BigDecimal(0);
			 output.setAmtReq(AmtReq);
			 
			 output.setContractNo((String)result.get("contract_no"));
			 output.setContractDate((String)result.get("contract_date"));
			 
			 BigDecimal GuaranteePrice = result.get("guarantee_price") != null ? (BigDecimal)result.get("guarantee_price") : new BigDecimal(0);
			 output.setGuaranteePrice(GuaranteePrice);
			 
			 BigDecimal GuaranteePercent = result.get("guarantee_percent") != null ? (BigDecimal)result.get("guarantee_percent") : new BigDecimal(0);
			 output.setGuaranteePercent(GuaranteePercent);
			 
			 BigDecimal AdvanceGuaranteePrice = result.get("advance_guarantee_price") != null ? (BigDecimal)result.get("advance_guarantee_price") : new BigDecimal(0);
			 output.setAdvanceGuaranteePrice(AdvanceGuaranteePrice);
			 
			 BigDecimal AdvancePayment = result.get("advance_payment") != null ? (BigDecimal)result.get("advance_payment") : new BigDecimal(0);
			 output.setAdvancePayment(AdvancePayment);
			 
			 BigDecimal WorksGuaranteePrice = result.get("works_guarantee_price") != null ? (BigDecimal)result.get("works_guarantee_price") : new BigDecimal(0);
			 output.setWorksGuaranteePrice(WorksGuaranteePrice);
			 
			 BigDecimal WorksGuaranteePercent = result.get("works_guarantee_percent") != null ? (BigDecimal)result.get("works_guarantee_percent") : new BigDecimal(0);
			 output.setWorksGuaranteePercent(WorksGuaranteePercent);
			 
			 output.setCollectionPhase((String)result.get("collection_phase"));
			 output.setEndDate((String)result.get("end_date"));
			 output.setStartDate((String)result.get("start_date"));
			 output.setBondType((String)result.get("bond_type"));
			 output.setProjName((String)result.get("proj_name"));
			 
			 BigDecimal ProjAmt = result.get("proj_amt") != null ? (BigDecimal)result.get("proj_amt") : new BigDecimal(0);
			 output.setProjAmt(ProjAmt);
			 
			 output.setProjOwnName((String)result.get("proj_own_name"));
			 output.setCostCenter((String)result.get("cost_center"));
			 output.setCostCenterName((String)result.get("cost_center_name"));
			 output.setDocumentNo((String)result.get("document_no"));
			 output.setDocumentDate((String)result.get("document_date"));
			 output.setExpireDate((String)result.get("expire_date"));
			 output.setStatusLG((String)result.get("status_lg"));
			 
			 BigDecimal AppvAmt = result.get("appv_amt") != null ? (BigDecimal)result.get("appv_amt") : new BigDecimal(0);
			 output.setAppvAmt(AppvAmt);
			 
			 output.setAppvDate(new SimpleDateFormat("yyyy-MM-dd").format(result.get("appv_date")));
			 output.setLgNo((String)result.get("lg_no"));
			 output.setTransactionStatus((String)result.get("status"));
			 output.setMsgCode((String)result.get("msg_code"));
			 output.setAlsOnline((String)result.get("als_online"));
			 
			 output.setProcessDate((java.util.Date)result.get("process_date"));
			 output.setXmlOutput((String)result.get("xml_output"));
			 output.setOcCode((String)result.get("occode"));
			 output.setReviewStatus((String)result.get("review_status"));

			 output.setReviewDtm((java.util.Date)result.get("review_dtm"));
			 output.setReviewBy((String)result.get("review_by"));
			 output.setReviewReason((String)result.get("review_reason"));
			 output.setApproveStatus((String)result.get("approve_status"));
			 
			 output.setApproveDtm((java.util.Date)result.get("approve_dtm"));
			 output.setApproveBy((String)result.get("approve_by"));
			 output.setApproveReason((String)result.get("approve_reason"));
			 
			 output.setExtendDate((String)result.get("extend_date"));
		 }
		 
		 return output;
	}
    
}
