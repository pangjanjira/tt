/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.controller.eguarantee;

import java.io.FileInputStream;
import java.util.Properties;
import th.co.scb.service.ConfigFileLocationConfig;
import th.co.scb.util.Constants;

/**
 *
 * @author s60982
 */
public class ReportConfig {

    private Properties prop;
    private FileInputStream file;

    public ReportConfig() {
        try {

            //to load application's properties, we use this class
            prop = new Properties();

            ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
            String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.REPORT_PROPERTIES;
			//System.out.println("path : "+path);
            //InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);

            //load the file handle
            file = new FileInputStream(path);

            //load all the properties from this file
            prop.load(file);

        } catch (Exception e) {
            // e: handle exception
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getFolderPath() {
        return prop.getProperty(Constants.ConfigFile.REPORT_FOLDER_PATH);
    }

}
