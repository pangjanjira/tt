/**
 * 
 */
package th.co.scb.db.eguarantee;

import java.util.Map;

import th.co.scb.model.eguarantee.Declaration;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class DeclarationTable {
	
	private ConnectDB connectDB;
	
	public DeclarationTable(ConnectDB connectDB) {
		this.connectDB = connectDB;
		System.out.println("DeclarationTable received connection : "+connectDB.hashCode());	
	}

	public boolean isDupDeclarateion(Declaration declaration){
		
		boolean isDup = false;
		
		StringBuilder sql = new StringBuilder();
		 sql.append(" select * from ").append(Constants.TableName.DECLARATION);
		 sql.append(" where declaration_no = ? ");
		 sql.append(" and declaration_seq = ? ");
	    	
		 //System.out.println("sql : " + sql.toString());
	    	
		 Map<String, Object> result = connectDB.querySingle(sql.toString(), declaration.getDeclarationNo(), declaration.getDeclarationSeq());
		 if(result != null){
			 isDup = true;
		 }
		
		return isDup;
	}
	
	public void add(Declaration declaration){
		
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into ").append(Constants.TableName.DECLARATION).append("(declaration_no, declaration_seq, tran_date) ");
    	sql.append(" values(?,?, now()) ");
    	
    	//System.out.println("sql : " + sql.toString());
    	
    	int id = connectDB.insert(sql.toString(), declaration.getDeclarationNo(), declaration.getDeclarationSeq());
    	
    	declaration.setId(id);

    }
	
	public void remove(Declaration declaration){
		
    	StringBuilder sql = new StringBuilder();
    	sql.append(" delete from ").append(Constants.TableName.DECLARATION);
    	sql.append(" where declaration_no = ? ");
		sql.append(" and declaration_seq = ? ");
    	
    	//System.out.println("sql : " + sql.toString());
    	
    	connectDB.execute(sql.toString(), declaration.getDeclarationNo(), declaration.getDeclarationSeq());

    }
	
}
