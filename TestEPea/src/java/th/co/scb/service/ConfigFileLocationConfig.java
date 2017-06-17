/**
 * 
 */
package th.co.scb.service;

import java.io.InputStream;
import java.util.Properties;

import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class ConfigFileLocationConfig {
	
	private Properties 	prop;
    private String 		location;
	static{
		
	}
	
	public ConfigFileLocationConfig(){
		
		try{
			
			prop = new Properties();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.ConfigFile.CONFIG_FILE_LOCATION_PROPERTIES);
			prop.load(inputStream);
			
			this.location = prop.getProperty(Constants.ConfigFile.CONFIG_FILE_LOCATION);
			
		}catch (Exception e) {
			// e: handle exception
			e.printStackTrace();
		}	
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


}
