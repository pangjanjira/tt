package th.co.scb.service.mq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.ibm.mq.MQException;

public class Message {

	private static final Logger log = LoggerFactory.getLogger(Message.class);
	private MQGet mqg;
	private MQPut mqp;
	private String res_code;
	private String res_msg;
	private String data;

	private String session;
	private String userid;
	private String SERVICE_NAME;

	private String USER_LOGON;
	private String USER_PASSWORD;
//	private String req_queue;
//	private String res_queue;
//	private String qmanager;
	private int timeout = 30000;

	public Message(MQConfig mqcfg) {
		this.SERVICE_NAME = mqcfg.getServiceName();
		this.USER_LOGON = mqcfg.getUserId();
		this.USER_PASSWORD = mqcfg.getPassword();
//		this.req_queue = mqcfg.getReq_queue();
//		this.res_queue = mqcfg.getRes_queue();
//		this.qmanager = mqcfg.getQmanager();
		this.timeout = mqcfg.getTimeout();
		this.mqg = new MQGet(mqcfg);
		this.mqp = new MQPut(mqcfg);
	}

	public boolean send(String message) {
		try {
			byte[] msgId = mqp.putMessageToQueue(message);
			String msgIdText = new String(msgId);
			if (log.isDebugEnabled()) {
				log.debug("MQ:Message.send(), put-messageId={} ...\n{}", msgIdText, message);
			}
			String res = mqg.getMessageFromQueue(msgId, timeout < 1000 ? 20000 : timeout);
			if (log.isDebugEnabled()) {
				log.debug("MQ:Message.send(), got-messageId={} ...\n{}", msgIdText, res);
			}
//			System.out.println("res==>" + res);
			if (res != null) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				try {
					builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new ByteArrayInputStream(res.getBytes("UTF-8")));
//                  Document doc = builder.parse(new ByteArrayInputStream(res.getBytes("ASCII")));
					data = this.formatXml(res);
//					System.out.println("out==>" + data);
					if (log.isTraceEnabled()) {
						log.trace("data={}", data);
					}
					res_code = doc.getElementsByTagName("res_code").item(0).getTextContent();
//					System.out.println("out==>" + res_code);
					if (log.isTraceEnabled()) {
						log.trace("res_code={}", res_code);
					}
					res_msg = doc.getElementsByTagName("res_msg").item(0).getTextContent();
//					System.out.println("out==>" + res_msg);
					if (log.isTraceEnabled()) {
						log.trace("res_msg={}", res_msg);
					}
					if (doc.getElementsByTagName("session").item(0) != null) {
						session = doc.getElementsByTagName("session").item(0).getTextContent();
					}
					if (doc.getElementsByTagName("user_id").item(0) != null) {
						userid = doc.getElementsByTagName("user_id").item(0).getTextContent();
					}
//					System.out.println("add log ==> "+insertMQLog(message, data, res_code));
//					System.out.println("add log ==> " + log);
				} catch (Exception ex) {
					//Logger.log(Level.ERROR, "Error while parsing message: " + ex.getMessage());
					//System.out.println(ex.getMessage());
					//insertMQLog(message, ex.getMessage(), "99");
					log.warn("format mq-response failed", ex);
					if (log.isDebugEnabled() == false) {
						log.warn("MQ:Message.send(), format response failed.\n>>>>>>>>>>{}\n<<<<<<<<<<\n{}", message, res);
					}
				}
				return true;
			} else {
				res_code = "2033";
				res_msg = "No message";
				data = "";
//              insertMQLog(message, "No message", res_code);
				return false;
			}
		} catch (MQException ex) {
			//Logger.log(Level.ERROR, ex.reasonCode + ": " + ex.getMessage());
//			System.out.println(ex.reasonCode + ": " + ex.getMessage());
//        	insertMQLog(message, ex.getMessage(), "99");
			log.warn("send() failed", ex);
			return false;
		}
	}

	public boolean sendonly(String message) {
//		boolean log = false;
		try {
//			MQMessage mqMsg = new MQMessage();
			mqp.putMessageToQueue(message);
//          log = insertMQLog(message, "", "00");
			
			return true;
		} catch (MQException ex) {
			System.out.println(ex.reasonCode + ": " + ex.getMessage());
//        	log = insertMQLog(message, ex.getMessage(), "99");
			return false;
		}
	}

	public boolean signOn() {
		if (send("<DocRq><ServiceName>" + SERVICE_NAME + "</ServiceName><SignonPasswdRq><user_logon>" + USER_LOGON + "</user_logon><crypt_type>0</crypt_type><passwd>" + USER_PASSWORD + "</passwd><gen_session>T</gen_session><lang>E</lang></SignonPasswdRq></DocRq>")) {
			System.out.println(data);
			//return Session.update(data);
			return true;
		} else {
			return false;
		}
	}

	public String get_res_code() {
		return res_code;
	}

	public String get_res_msg() {
		return res_msg;
	}

	public String get_data() {
		return data;
	}

	public String getSession() {
		return session;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void close() {
		try {
			mqp.closeQueue();
			mqg.closeQueue();
		} catch (MQException ex) {
			log.warn("close queue failed", ex);
		}
	}

	public String flushGetQueue() {
		String msg = "";
		while (true) {
			try {
//				MQMessage mqMsg = new MQMessage();
				msg = mqg.getMessageFromQueue(null, 3000);
				System.out.println("res queue name: " + mqg.getGetQueueName());
				System.out.println("flushing message: " + msg);
				if (msg == null)
					break;
				//Logger.log(Level.DEBUG, "flushing message: " + msg);
			} catch (MQException ex) {
				//Logger.log(Level.ERROR, ex.reasonCode + ": " + ex.getMessage());
			}
		}
		return msg;
	}

	private String formatXml(String xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);
			Source source = new DOMSource(doc);
			transformer.transform(source, result);
			writer.close();
			String formatted = writer.toString();
			return formatted;
		} catch (TransformerException ex) {
			//Logger.log(Level.ERROR, ex.getMessage());
		} catch (SAXException ex) {
			//Logger.log(Level.ERROR, ex.getMessage());
		} catch (IOException ex) {
			//Logger.log(Level.ERROR, ex.getMessage());
		} catch (ParserConfigurationException ex) {
			//Logger.log(Level.ERROR, ex.getMessage());
		}
		return null;
	}

}
