/**
 * 
 */
package th.co.scb.util;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.DatabaseConfig;

/**
 * @author s51486
 *
 */
public class ConnectDB {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectDB.class);
	private Connection connect = null ;

    public ConnectDB(){
    	
    	DatabaseConfig dbConfig = new DatabaseConfig();
        try {
  
            Class.forName(dbConfig.getJdbcDriver());
            //connect = DriverManager.getConnection(dbConfig.getDbUrl(), dbConfig.getDbUserName() , dbConfig.getDbPassword());
            InitialContext ctx = new InitialContext();
            //logger.debug("-----1------");
            DataSource  db = (DataSource)ctx.lookup(dbConfig.getDbLookupName());
            //logger.debug("-----2------");
            if(db == null){
            	logger.debug(dbConfig.getDbName() +" Not Found !!! ");
            	throw new Exception(dbConfig.getDbName() +" Not Found ");
            }
            connect = db.getConnection();
            
        }
        catch (Exception e) {
        	//logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
 
    public Connection getConnect() {
        return connect;
    }
    
    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public int insert(String sql, Object... args) {
        try {
            PreparedStatement pstmt;
            pstmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
            
            pstmt.executeUpdate();
  
            ResultSet rs = pstmt.getGeneratedKeys();
            
            rs.next();
            int keyGen = rs.getInt(1);
            
            rs.close();
            pstmt.close();
            return keyGen;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public int execute(String sql, Object... args) {
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);

            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
            
            int count = pstmt.executeUpdate();
            
            pstmt.close();
            return count;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }

    public Map<String, Object> querySingle(String sql, Object... args) {
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
            
            ResultSet         rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            
            if (rs.next()) {
                Map<String, Object> map;
                map = new HashMap<String, Object>();
                
                for (int i=1; i<=md.getColumnCount(); i++) {
                    map.put(md.getColumnLabel(i), rs.getObject(i));
                }
                
                rs.close();
                pstmt.close();
                return map;
            }
            else {
                rs.close();
                pstmt.close();
                return null;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public ResultSet queryRsSingle(String sql, Object... args) {
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
            return pstmt.executeQuery();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    //add by pariwat.s @22062015
    public byte[] querySingleBlob(String sql, Object... args) {
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
        
            ResultSet rs = pstmt.executeQuery();            
            if (rs.next()) {
                Blob blob = rs.getBlob(1);
                byte[] bdata = blob.getBytes(1, (int)blob.length());
                return bdata;
            }
            else {
                rs.close();
                pstmt.close();
                return null;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }    
    
    
    public List<Map<String, Object>> queryList(String sql, Object... args) {
        try {
            List<Map<String, Object>> list;
            list = new ArrayList<Map<String, Object>>();
            
            PreparedStatement pstmt = connect.prepareStatement(sql);
            
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
                        
            ResultSet         rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            
            while (rs.next()) {
                Map<String, Object> map;
                map = new HashMap<String, Object>();
                
                for (int i=1; i<=md.getColumnCount(); i++) {
                    map.put(md.getColumnLabel(i), rs.getObject(i));
                    
                }
                
                list.add(map);
            }
            
            rs.close();
            pstmt.close();            
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }      
    
    public String queryJSON(String sql, Object... args) {
        //[{'column':'value','column':'value',...},{'column':'value','column':'value',...},...]
        try {
            StringBuilder result = new StringBuilder();
        
            PreparedStatement pstmt = connect.prepareStatement(sql);
            
            for (int i=0; i<args.length; i++) {
                pstmt.setObject(i+1, args[i]);
            }
            
            ResultSet         rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                result.append("{");
                
                for (int i=1; i<=md.getColumnCount(); i++) {
                    result
                            .append("'")
                            .append(md.getColumnLabel(i))
                            .append("':'")
                            .append(rs.getObject(i))
                            .append("',");
                }
                
                result.deleteCharAt(result.length()-1);
                result.append("},");
            }
            
            if (result.length() != 0) {
                result.insert(0, "[");
                result.deleteCharAt(result.length()-1);
                result.append("]");
            }

            rs.close();
            pstmt.close();
            return result.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }

    public void beginTransaction() {
        try {
          connect.setAutoCommit(false);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public void commit() {
        try {
          connect.commit();  
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public void rollback() {
        try {
          connect.rollback();  
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}
