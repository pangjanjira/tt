import java.net.URL;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import th.co.scb.service.mq.MQConfig;

import com.ibm.mq.jms.MQQueueConnectionFactory;

/**
 * 
 */


/**
 * @author s51486
 */ 
public class TestSwithMQ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// -- Auto-generated method stub
		/*
		System.out.println("Start");
		MQQueueConnectionFactory qcf = null;
		QueueConnection connection = null;
		QueueSession session = null;
		//Queue rqQueue = null;
		Queue rsQueue = null;
		//QueueSender sender = null;
		QueueReceiver receiver = null;
		try {
			qcf = new MQQueueConnectionFactory();

			MQConfig mqConfig = new MQConfig();
			
			URL cctUrl = new URL("file:///C:/MQClient/AMQCLCHL.TAB");
			qcf.setCCDTURL(cctUrl);
			qcf.setQueueManager("*SMAINQM");
			qcf.setHostName(mqConfig.getHost());
			qcf.setPort(mqConfig.getPort());
			
		
			//put
			connection = qcf.createQueueConnection(mqConfig.getUserId(), mqConfig.getPassword());
			System.out.println("connected");
			
			//connection.
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
			TextMessage text = session.createTextMessage("Test");
			
			System.out.println(session.createBytesMessage().getClass().getName());
			rsQueue = session.createQueue("queue:///"+"BCM.CN.FAX.FW.LTQ"+"?targetClient=1");

			//get response
			System.out.println("Receiving...");
			receiver = session.createReceiver(rsQueue);//, selector);
			Message message = receiver.receive(5000);// wait 40s

			text = (TextMessage) message;
			System.out.println(text.getText());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (receiver != null) {
					receiver.close();
				}
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}

		}
		 * */
		

	}

}
