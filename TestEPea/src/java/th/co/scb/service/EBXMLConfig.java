/**
 * 
 */
package th.co.scb.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class EBXMLConfig {

    private Properties 	prop;
    private FileInputStream file;
    
    private String 		url;
    private String		urlGP;
    
	static{
		
	}
	
	public EBXMLConfig(){
		
		try{
			//to load application's properties, we use this class
			prop = new Properties();
			
			ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
			String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.EBXML_PROPERTIES;
			//System.out.println("path : "+path);
			//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
			//load the file handle
		    file = new FileInputStream(path);
		    
		    //load all the properties from this file
			prop.load(file);
			
			this.url = prop.getProperty(Constants.ConfigFile.EBXML_URL);
			this.urlGP = prop.getProperty(Constants.ConfigFile.EBXML_GP_URL);
			
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

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the urlGP
	 */
	public String getUrlGP() {
		return urlGP;
	}

	/**
	 * @param urlGP the urlGP to set
	 */
	public void setUrlGP(String urlGP) {
		this.urlGP = urlGP;
	}

    
}
