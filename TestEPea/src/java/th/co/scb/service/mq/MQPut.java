/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.service.mq;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import java.io.IOException;


public class MQPut extends MQConnector {
    private String putQueueName;
    private String REPLY_TO_QUEUE;
    
    public MQPut(MQConfig mqcfg) {
        super(mqcfg);
        super.initMq();
        putQueueName = mqcfg.getReqQueue();
        REPLY_TO_QUEUE = mqcfg.getResQueue();
        try {
            super.openQueue(putQueueName, MQC.MQOO_OUTPUT);
        } catch (MQException ex) {
            ex.printStackTrace();
        }
    }
    
    public byte[] putMessageToQueue(String msg) throws MQException {
        try {
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            MQMessage mqMsg = new MQMessage();
            mqMsg.replyToQueueName = REPLY_TO_QUEUE;
            //start set correlationId (header request)
            String cid = "AMQ!NEW_SESSION_CORRELID";
            mqMsg.correlationId = cid.getBytes();
            mqMsg.format = "MQSTR";
            mqMsg.encoding = 546;
            mqMsg.characterSet = 874;
            //end set correlationId (header request)
            mqMsg.write(msg.getBytes("TIS-620"));
            mqQueue.put(mqMsg, pmo);
//            
//            System.out.println(" correlid "+mqMsg.correlationId);
//            System.out.println(" messageid "+ mqMsg.messageId);
            return mqMsg.messageId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String putGetMessageToQueue(String msg,int timeout) throws MQException {
        try {
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            MQMessage mqMsg = new MQMessage();
            //put
            mqMsg.replyToQueueName = REPLY_TO_QUEUE;
            //start set correlationId (header request)
            String cid = "AMQ!NEW_SESSION_CORRELID";
            mqMsg.correlationId = cid.getBytes();
            //mqMsg.correlationId = msgid;
            mqMsg.format = "MQSTR";
            mqMsg.characterSet = 874;
            //end set correlationId (header request)
            mqMsg.write(msg.getBytes());

            mqQueue.put(mqMsg, pmo);
            byte[] msgid = mqMsg.messageId;
            
            //get
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
            byte[] message = new byte[len];
            mqMsg.characterSet = 874;
            //mqMsg.readFully(message, 0, len);
            String x = mqMsg.readString(len);
            return x;
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
