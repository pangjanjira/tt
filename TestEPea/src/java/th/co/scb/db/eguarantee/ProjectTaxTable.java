/**
 * 
 */
package th.co.scb.db.eguarantee;

import java.util.Map;

import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.model.eguarantee.ProjectTax;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class ProjectTaxTable {
	
	private ConnectDB connectDB;
	
	public ProjectTaxTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("ProjectTaxTable received connection : "+connectDB.hashCode());		
	}
	
	public ProjectTaxTable() {
	}
	
	public boolean isDupProjectTax(ProjectTax projectTax){
		ConnectDB connectDB = new ConnectDB(); // update 20150512 by Apichart Homkeaw
		System.out.println("ProjectTaxTable<isDupProjectTax> new connection : "+connectDB.hashCode());
		boolean isDup = false;
		try{
			
			
			StringBuilder sql = new StringBuilder();
			 sql.append(" select * from ").append(Constants.TableName.PROJECT_TAX);
			 sql.append(" where proj_no = ? ");
			 sql.append(" and vendor_tax_id = ? ");
			 sql.append(" and seq_no = ? ");
			 
			 // add key for check dup 2015-04-27
			 sql.append(" and bond_type = ?");
			 
			 //add by Malinee T. UR58100048 @20160104
			 sql.append(" and guarantee_amt = ?");
			 sql.append(" and end_date = ?");
		    	
			 System.out.println("sql : " + sql.toString());
		    
			 // Map<String, Object> result = connectDB.querySingle(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo());		
			 // Map<String, Object> result = connectDB.querySingle(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType());
			 // update by Malinee T. UR58100048 @20160104
			 Map<String, Object> result = connectDB.querySingle(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType(), projectTax.getGuaranteeAmt(), projectTax.getEndDateStr()); 
			 if(result != null){
				 System.out.println("result not null");
				 System.out.println("total size: " + String.valueOf(result.size()));
				 isDup = true;
			 }else{
				 System.out.println("result null");
			 }
			 
			
		}finally{
			//<start>
			if(connectDB != null){
				System.out.println("ProjectTaxTable<isDupProjectTax> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
			//<end> update by Apichart H.@20150512
		}
		 return isDup;
		
	}

	public void add(ProjectTax projectTax){
		ConnectDB connectDB = new ConnectDB(); // update 20150512 by Apichart Homkeaw
		System.out.println("ProjectTaxTable<add> new connection : "+connectDB.hashCode());
		try{
			StringBuilder sql = new StringBuilder();
			// add key for check dup 2015-04-27
			
			//sql.append(" insert into ").append(Constants.TableName.PROJECT_TAX).append("(proj_no, vendor_tax_id, seq_no, tran_date) ");
			//sql.append(" values(?,?,?, now()) ");
			System.out.println("insert or update project_tax");
			
			//update by Malinee T. UR58100048 @20160104
			//sql.append(" insert into ").append(Constants.TableName.PROJECT_TAX).append("(tx_ref, proj_no, vendor_tax_id, seq_no,bond_type, tran_date, lg_no) ");
			//sql.append(" values(?,?,?,?,?, now(), ?) on duplicate key update lg_no=values(lg_no) ");
			sql.append(" insert into ").append(Constants.TableName.PROJECT_TAX).append("(tx_ref, proj_no, vendor_tax_id, seq_no,bond_type, tran_date, lg_no, guarantee_amt, end_date) ");
			sql.append(" values(?,?,?,?,?, now(), ?, ?, ?) on duplicate key update lg_no=values(lg_no) ");
			
			//System.out.println("sql : " + sql.toString());
			
			// add condition for check dup 2015-04-27
			//int id = connectDB.insert(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo());
			
			//update by Malinee T. UR58100048 @20160104
			//int id = connectDB.insert(sql.toString(), projectTax.getTxRef(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType(), projectTax.getLgNo()); 
			int id = connectDB.insert(sql.toString(), projectTax.getTxRef(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType(), projectTax.getLgNo(), projectTax.getGuaranteeAmt(), projectTax.getEndDateStr()); 
			System.out.println("add project_tax: " + sql.toString() + " [" + projectTax.getTxRef() +"]"
					 + " [" + projectTax.getTxRef() +"]"
					 + " [" + projectTax.getProjectNo() +"]"
					 + " [" + projectTax.getVendorTaxId() +"]"
					 + " [" + projectTax.getSeqNo() +"]"
					 + " [" + projectTax.getBondType() +"]"
					 + " [" + projectTax.getLgNo() +"]");
			projectTax.setId(id);
		}catch(Exception e){
			//skip throw exception for the approve/reject second time
			System.out.println("approve/reject second time, update lg_no in project tax instead.");
		}finally{
			//<start>
			if(connectDB != null){
				System.out.println("ProjectTaxTable<add> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
			//<end> update by Apichart H.@20150512
		}
	}
	
	public void remove(ProjectTax projectTax){
		
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from ").append(Constants.TableName.PROJECT_TAX);
		sql.append(" where lg_no = ? "); //update by apichart h.@20150513
//		sql.append(" where proj_no = ? ");
//		sql.append(" and vendor_tax_id = ? ");
//		sql.append(" and seq_no = ? ");
		// add key for check dup 2014-04-27
//		sql.append(" and bond_type = ? ");
		
		//System.out.println("sql : " + sql.toString());
		
		// add condition for remove		
		//connectDB.execute(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo());
		//connectDB.execute(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType());
		
		connectDB.execute(sql.toString(), projectTax.getLgNo()); //update by apichart h.@20150513
	
	}
	
	//add by Apichart H.@20150512
        //update by apichart h.@20151019
	public void removeSetupIssue(ProjectTax projectTax){
		ConnectDB connectDB = new ConnectDB();
		System.out.println("ProjectTaxTable<removeSetupIssue> new connection : "+connectDB.hashCode());
		try{
			StringBuilder sql = new StringBuilder();
		
			sql.append(" delete from ").append(Constants.TableName.PROJECT_TAX);
//			sql.append(" where lg_no = ? "); //update by apichart h.@20150513
			sql.append(" where proj_no = ? ");
			sql.append(" and vendor_tax_id = ? ");
			sql.append(" and seq_no = ? ");
			// add key for check dup 2014-04-27
			sql.append(" and bond_type = ? ");
			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
			//added
			sql.append(" and guarantee_amt = ? ");
			sql.append(" and end_date = ? ");
			
			System.out.println("sql : " + sql.toString());
			System.out.println("ProjectNo : " + projectTax.getProjectNo());
			System.out.println("VendorTaxId : " + projectTax.getVendorTaxId());
			System.out.println("SeqNo : " + projectTax.getSeqNo());
			System.out.println("BondType : " + projectTax.getBondType());
			System.out.println("GuaranteeAmt : " + projectTax.getGuaranteeAmt());
			System.out.println("EndDateStr : " + projectTax.getEndDateStr());
			
			//connectDB.execute(sql.toString(), projectTax.getLgNo());
			// add condition for remove		
			//connectDB.execute(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo());
			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
			//changed
			//connectDB.execute(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType());
			connectDB.execute(sql.toString(), projectTax.getProjectNo(), projectTax.getVendorTaxId(), projectTax.getSeqNo(),projectTax.getBondType(),projectTax.getGuaranteeAmt(),projectTax.getEndDateStr());
		}finally{
			if(connectDB != null){
				System.out.println("ProjectTaxTable<removeSetupIssue> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
		}
	}
	
	public void removeProjectTaxByKeys(GPGuarantee gpGuarantee){
		ConnectDB connectDB = new ConnectDB();
		System.out.println("ProjectTaxTable<removeProjectTaxByKeys> new connection : "+connectDB.hashCode());
		try{
			StringBuilder sql = new StringBuilder();
		
			sql.append(" delete from ").append(Constants.TableName.PROJECT_TAX);
			sql.append(" where proj_no = ? ");
			sql.append(" and vendor_tax_id = ? ");
			sql.append(" and seq_no = ? ");
			sql.append(" and bond_type = ? ");
			sql.append(" and guarantee_amt = ? ");
			sql.append(" and end_date = ? ");
			System.out.println("sql : " + sql.toString());
			System.out.println("ProjNo : " + gpGuarantee.getProjNo());
			System.out.println("VendorTaxId : " + gpGuarantee.getVendorTaxId());
			System.out.println("SeqNo : " + gpGuarantee.getSeqNo());
			System.out.println("BondType : " + gpGuarantee.getBondType());
			System.out.println("GuaranteeAmt : " + gpGuarantee.getGuaranteeAmt());
			System.out.println("EndDate : " + gpGuarantee.getEndDate());
			
			connectDB.execute(sql.toString()
					, gpGuarantee.getProjNo()
					, gpGuarantee.getVendorTaxId()
					, gpGuarantee.getSeqNo()
					, gpGuarantee.getBondType()
					, gpGuarantee.getGuaranteeAmt()
					, gpGuarantee.getEndDate());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connectDB != null){
				System.out.println("ProjectTaxTable<removeProjectTaxByKeys> close connection : "+connectDB.hashCode());
        		connectDB.close();
        	}
		}
	}
	
	public boolean isDupProjectTax5Keys(GPGuarantee gpGuarantee){
		ConnectDB connectDB = new ConnectDB();
		System.out.println("ProjectTaxTable<isDupProjectTax5Keys> new connection : "+connectDB.hashCode());
		boolean isDup = false;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from ").append(Constants.TableName.PROJECT_TAX);
			sql.append(" where tx_ref = ? ");
			sql.append(" and proj_no	 = ? ");
			sql.append(" and vendor_tax_id = ? ");
			sql.append(" and seq_no = ? ");
			sql.append(" and bond_type = ?");
			
			//add by Malinee T. UR58100048 @20160104
			sql.append(" and guarantee_amt = ?");
		    sql.append(" and end_date = ?");
			
			System.out.println("sql : " + sql.toString());
			//update by Malinee T. UR58100048 @20160104
			//Map<String, Object> result = connectDB.querySingle(sql.toString(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType());
			Map<String, Object> result = connectDB.querySingle(sql.toString(), gpGuarantee.getTxRef(), gpGuarantee.getProjNo(), gpGuarantee.getVendorTaxId(), gpGuarantee.getSeqNo(),gpGuarantee.getBondType(), gpGuarantee.getGuaranteeAmt(), gpGuarantee.getEndDate());
			if(result != null){
				isDup = true;
			}
		}finally{
			if(connectDB != null){
				System.out.println("ProjectTaxTable<isDupProjectTax5Keys> close connection : "+connectDB.hashCode());
				connectDB.close();
			}
		}
		return isDup;
	}
	
	//add by Malinee T. UR58100048 @20160104
	public void updateGpGuaranteeEgpAcknowledge(GPGuarantee gpGuarantee) {
				
		StringBuilder sql = new StringBuilder();
	    sql.append(" update ").append(Constants.TableName.GP_GUARANTEE);
	    sql.append(" set egp_ack_status= ? ");
	    sql.append(",egp_ack_dtm= ? ");
	    sql.append(",egp_ack_tranx_id= ? ");
	    sql.append(",egp_ack_code= ? ");
	    sql.append(",egp_ack_msg= ? ");
	    sql.append(" where id = ? ");
	    
	    connectDB.execute(sql.toString(),
	            gpGuarantee.getEgpAckStatus(),
	            gpGuarantee.getEgpAckDtm(),
	            gpGuarantee.getEgpAckTranxId(),
	            gpGuarantee.getEgpAckCode(),
	            gpGuarantee.getEgpAckMsg(),
	            gpGuarantee.getId());
	}
	
	

}
