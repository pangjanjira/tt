/**
 * 
 */
package th.co.scb.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import th.co.scb.service.ConfigFileLocationConfig;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class DatabaseConfig {
	
	private Properties prop;
	private FileInputStream file;
	
	public DatabaseConfig(){
		try{
			
			//to load application's properties, we use this class
			prop = new Properties();

			ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
			String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.DATABASE_PROPERTIES;
			//System.out.println("path : "+path);
			//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
			
			//load the file handle
		    file = new FileInputStream(path);
		    
		  //load all the properties from this file
			prop.load(file);
	
		}catch (Exception e) {
			// e: handle exception
			e.printStackTrace();
		}finally{
			try{
				file.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getJdbcDriver() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_JDBC_DRIVER);
	}
	
	public String getDbName() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_DB_NAME);
	}
	
	public String getDbLookupName() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_DB_LOOKUP_NAME);
	}
	
	public String getDbUrl() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_DB_URL);
	}
	
	public String getDbUserName() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_DB_USERNAME);
	}
	
	public String getDbPassword() {
		return prop.getProperty(Constants.ConfigFile.DATABASE_DB_PASSWORD);
	}
	
}
