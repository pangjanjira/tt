package th.co.scb.service.mq;

import java.net.URL;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class MQConnector {

    private String QUEUE_MANAGER; // define name of queue manager
    private String HOST;
    private String CHANNEL;
    private String CHANNEL_TABLE;
    private int PORT;
    
    protected MQQueue mqQueue;
    protected MQQueueManager qMgr;

    public MQConnector(MQConfig mgcfg) {
        HOST = mgcfg.getHost();
        CHANNEL = mgcfg.getChannel();
        PORT = mgcfg.getPort();
        QUEUE_MANAGER = mgcfg.getQmanager();
        CHANNEL_TABLE = mgcfg.getChannelTable();
    }

    public String getCHANNEL() {
        return CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getQUEUE_MANAGER() {
        return QUEUE_MANAGER;
    }

    public void setQUEUE_MANAGER(String QUEUE_MANAGER) {
        this.QUEUE_MANAGER = QUEUE_MANAGER;
    }

    /**
	 * @return the cHANNEL_TABLE
	 */
	public String getCHANNEL_TABLE() {
		return CHANNEL_TABLE;
	}

	/**
	 * @param cHANNEL_TABLE the cHANNEL_TABLE to set
	 */
	public void setCHANNEL_TABLE(String cHANNEL_TABLE) {
		CHANNEL_TABLE = cHANNEL_TABLE;
	}

	public void initMq() {
        try {
            //System.out.println("init mq: channel: " + CHANNEL);
            MQEnvironment.channel = CHANNEL;
            MQEnvironment.hostname = HOST;
            MQEnvironment.port = PORT;
            MQEnvironment.CCSID = 874;
            MQException.log = null;
            //debug("Connecting to QueueManager " + qManager + " on " + qManagerHost);
            //qMgr = new MQQueueManager(QUEUE_MANAGER);
            URL cctUrl = new URL(CHANNEL_TABLE);
            qMgr = new MQQueueManager(QUEUE_MANAGER, cctUrl);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openQueue(String qName, int openOptions) throws MQException {
        // Set up the options on the queue we wish to open...
        // Note. All WebSphere MQ Options are prefixed with MQC in Java.
        // Now specify the queue that we wish to open,
        // and the open options...    
        
        try {
            //System.out.println("Opening queue: " + qName);
            mqQueue = qMgr.accessQueue(qName, openOptions);
        } catch (MQException mqe) {
            //check if MQ reason code 2045
            //means that opened queu is remote and it can not be opened as 
            //input queue
            //try to open as output only
            if (mqe.reasonCode == 2045) {
                openOptions = MQC.MQOO_OUTPUT;
                mqQueue = qMgr.accessQueue(qName, openOptions);
            }
        }
    }

    public void closeQueue() throws MQException {
        System.out.println("Closing queue and disconnecting QueueManager...");

        // Close the queue...
        mqQueue.close();
        // Disconnect from the queue manager
        qMgr.disconnect();

    }
}
