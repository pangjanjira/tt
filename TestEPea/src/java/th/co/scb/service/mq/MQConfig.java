/**
 * 
 */
package th.co.scb.service.mq;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import th.co.scb.service.ConfigFileLocationConfig;
import th.co.scb.util.Constants;

/**
 * @author s51486
 *
 */
public class MQConfig {

    private Properties 	prop;
    private FileInputStream file;
    
    private String 		serviceName;
    private String		userId;
    private String 		password;
    private String 		host;
    private String 		channel;
    private int 		port;
    private String 		qmanager;
    private String 		reqQueue;
    private String 		resQueue;
    private String 		sessionUserid;
	private int 		timeout;
	private String		channelTable;
	private String		mode;
	private String		processdate;
	private String		effectivedate;
	static{
		
	}
	
	public MQConfig(){
		
		try{
			
			//to load application's properties, we use this class
			prop = new Properties();
			
			ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
			String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.MQ_PROPERTIES;
			//System.out.println("path : "+path);
			//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
			//load the file handle
		    file = new FileInputStream(path);
		    
		    //load all the properties from this file
			prop.load(file);
			
			this.serviceName = prop.getProperty(Constants.ConfigFile.MQ_SERVICE_NAME);
			this.userId = prop.getProperty(Constants.ConfigFile.MQ_USER);
			this.password = prop.getProperty(Constants.ConfigFile.MQ_PASSWORD);
			this.host = prop.getProperty(Constants.ConfigFile.MQ_HOST);
			this.channel = prop.getProperty(Constants.ConfigFile.MQ_CHANNEL);
			this.port = Integer.parseInt(prop.getProperty(Constants.ConfigFile.MQ_PORT));
			this.qmanager = prop.getProperty(Constants.ConfigFile.MQ_Q_MANAGER);
			this.reqQueue = prop.getProperty(Constants.ConfigFile.MQ_RQ_NAME);
			this.resQueue = prop.getProperty(Constants.ConfigFile.MQ_RS_NAME);
			this.sessionUserid = prop.getProperty(Constants.ConfigFile.MQ_SESSION_USERID);
			this.timeout = Integer.parseInt(prop.getProperty(Constants.ConfigFile.MQ_TIMEOUT));
			this.channelTable = prop.getProperty(Constants.ConfigFile.MQ_CHANNEL_TABLE);
			this.mode = prop.getProperty(Constants.ConfigFile.MQ_MODE);
			this.processdate = prop.getProperty(Constants.ConfigFile.MQ_PROCESS_DATE);
			this.effectivedate = prop.getProperty(Constants.ConfigFile.MQ_EFFECTIVE_DATE);
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
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the qmanager
	 */
	public String getQmanager() {
		return qmanager;
	}

	/**
	 * @param qmanager the qmanager to set
	 */
	public void setQmanager(String qmanager) {
		this.qmanager = qmanager;
	}

	/**
	 * @return the reqQueue
	 */
	public String getReqQueue() {
		return reqQueue;
	}

	/**
	 * @param reqQueue the reqQueue to set
	 */
	public void setReqQueue(String reqQueue) {
		this.reqQueue = reqQueue;
	}

	/**
	 * @return the resQueue
	 */
	public String getResQueue() {
		return resQueue;
	}

	/**
	 * @param resQueue the resQueue to set
	 */
	public void setResQueue(String resQueue) {
		this.resQueue = resQueue;
	}

	/**
	 * @return the sessionUserid
	 */
	public String getSessionUserid() {
		return sessionUserid;
	}

	/**
	 * @param sessionUserid the sessionUserid to set
	 */
	public void setSessionUserid(String sessionUserid) {
		this.sessionUserid = sessionUserid;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the channelTable
	 */
	public String getChannelTable() {
		return channelTable;
	}

	/**
	 * @param channelTable the channelTable to set
	 */
	public void setChannelTable(String channelTable) {
		this.channelTable = channelTable;
	}
    
	/**
	 * @return the Mode
			 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * @param Mode the Mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * @return the ProcessDate
			 */
	public String getProcessDate() {
		return processdate;
	}
	
	/**
	 * @param ProcessDate the ProcessDate to set
	 */
	public void setProcessDate(String processdate) {
		this.processdate = processdate;
	}
    
	/**
	 * @return the EffectiveDate
			 */
	public String getEffectiveDate() {
		return effectivedate;
	}
	
	/**
	 * @param EffectiveDate the EffectiveDate to set
	 */
	public void setEffectiveDate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
}
