/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.service.mq;

import java.io.IOException;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;

@SuppressWarnings("deprecation")
public class MQGet extends MQConnector {
	private String getQueueName;

	public String getGetQueueName() {
		return getQueueName;
	}

	public MQGet(MQConfig mqcfg) {
		super(mqcfg);
		super.initMq();
		getQueueName = mqcfg.getResQueue();
		try {
			super.openQueue(getQueueName, MQC.MQOO_INPUT_AS_Q_DEF);
		} catch (MQException ex) {
			ex.printStackTrace();
		}
	}

	public String getMessageFromQueue(byte[] msgid, int timeout) throws MQException {
		try {
			MQMessage mqMsg = new MQMessage();
			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_WAIT;
			gmo.waitInterval = timeout;
			//String cid = "AMQ!NEW_SESSION_CORRELID";
			mqMsg.correlationId = msgid;
			// Get a message from the queue
			mqMsg.characterSet = 874;
			mqQueue.get(mqMsg, gmo);
			//Extract the message data
			int len = mqMsg.getDataLength();
//			byte[] message = new byte[len];
			mqMsg.characterSet = 874;
			//mqMsg.readFully(message, 0, len);
			String x = mqMsg.readString(len);
			return x;
//            return new String(new String(message).getBytes("ISO8859_1"));
		} catch (MQException mqe) {
			int reason = mqe.reasonCode;
			if (reason == 2033)//no messages
			{
				return null;
			} else {
				throw mqe;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
