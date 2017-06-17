package th.co.scb.service.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.model.LogMQ;
import th.co.scb.service.LogMQService;

public class MQMessageService {

	private static final Logger log = LoggerFactory
			.getLogger(MQMessageService.class);

	String currentSessionId;
	String currentUserId;
	MQConfig config;

	public String getSessionId() {
		return currentSessionId;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	private void renewSessionId(Message msg) {
		if (msg.signOn()) {
			currentSessionId = msg.getSession();
			currentUserId = msg.getUserid();
		} else {
			currentSessionId = "";
			currentUserId = config.getSessionUserid();
		}
	}

	public String sendMessage(String xmlRequestString, String reqQueuename,
			String resQueuename, String methodName)
			throws MQConnectorFailedException, EGuaranteeMQMessageException {
		return sendMessage(xmlRequestString, reqQueuename, resQueuename,
				methodName, "N/A", "Sender");

	}

	public String sendMessage(String xmlRequestString, String reqQueuename,
			String resQueuename, String methodName, String customsRefNo,
			String senderID) throws MQConnectorFailedException,
			EGuaranteeMQMessageException {

		config = new MQConfig();

		config.setReqQueue(reqQueuename);
		config.setResQueue(resQueuename);
		Message msg = new Message(config);
		try {
			if (currentSessionId == null || currentSessionId.length() == 0) {
				renewSessionId(msg);
			}
			int retryCount = 2;
			boolean resend = false;
			do {
				resend = false;
				if (msg.send((xmlRequestString
						.replace("@userid", currentUserId)).replace(
						"@sessionid", currentSessionId))) {
					if ("1002".equals(msg.get_res_code())) {
						if (--retryCount < 0) {
							break;
						}
						renewSessionId(msg);
						resend = true;
					} else {
						String d = msg.get_res_code();
						// for add mq log
						boolean l = insertMQLog(xmlRequestString,
								msg.get_data(), methodName, msg.get_res_code(),
								customsRefNo, senderID);
						System.out.println("insert log ==> " + l);
						if (!d.equals("0001")) {
							EGuaranteeMQMessageException em = new EGuaranteeMQMessageException();
							em.setReasonCode(msg.get_res_code().toString());
							em.setReasonMessage(msg.get_res_msg().toString());
							em.setDataReason(msg.get_data());
							throw em;
						}
						return msg.get_data();
					}
				} else {
					insertMQLog(xmlRequestString,
							"no response from MQ-server!", methodName, "99");
				}
				// throw new
				// MQConnectorFailedException("no response from MQ-server!");
			} while (resend);
			throw new MQConnectorFailedException("no response from MQ-server!");
		} finally {
			msg.close();
		}
	}

	public String sendMessage(String xmlRequestString, String methodName)
			throws Exception, MQConnectorFailedException,
			EGuaranteeMQMessageException {
		Message msg = new Message(config);
		try {
			if (currentSessionId == null || currentSessionId.length() == 0) {
				renewSessionId(msg);
			}
			int retryCount = 2;
			boolean resend = false;
			do {
				resend = false;
				if (msg.send((xmlRequestString
						.replace("@userid", currentUserId)).replace(
						"@sessionid", currentSessionId))) {
					if ("1002".equals(msg.get_res_code())) {
						if (--retryCount < 0) {
							break;
						}
						renewSessionId(msg);
						resend = true;
					} else {
						// return msg.get_data();
						String d = msg.get_res_code();
						// for add mq log
						boolean l = insertMQLog(xmlRequestString,
								msg.get_data(), methodName, msg.get_res_code());
						System.out.println("insert log ==> " + l);
						if (!d.equals("0001")) {
							EGuaranteeMQMessageException em = new EGuaranteeMQMessageException();
							em.setReasonCode(msg.get_res_code().toString());
							em.setReasonMessage(msg.get_res_msg().toString());
							em.setDataReason(msg.get_data());
							throw em;
						}
						// System.out.println("send1!!");
						return msg.get_data();
					}
					// System.out.println("send2!!");
				} else {
					insertMQLog(xmlRequestString,
							"no response from MQ-server!", methodName, "99");
				}
			} while (resend);
			throw new MQConnectorFailedException("no response from MQ-server!");
		} finally {
			msg.close();
		}
	}

	public String flushGetMessage(String resQueuename)
			throws MQConnectorFailedException {

		config = new MQConfig();

		config.setResQueue(resQueuename);
		Message msg = new Message(config);
		try {
			return msg.flushGetQueue();
		} finally {
			msg.close();
		}
	}

	public MQConfig getConfig() {
		return config;
	}

	public void setConfig(MQConfig config) {
		this.config = config;
	}

	public boolean insertMQLog(String xmlreq, String xmlres, String methodname,
			String rescode) {
		return insertMQLog(xmlreq, xmlres, methodname, rescode, "N/A", "sender");
	}

	public boolean insertMQLog(String xmlreq, String xmlres, String methodname,
			String rescode, String customsRefNo, String sendId) {

		log.debug("insert mq log");

		boolean result = false;

		String xmlReqLow = xmlreq.toLowerCase();
		String rquid = null;
		int first = xmlReqLow.indexOf("<rquid>");
		if (first > 0) {
			int last = xmlReqLow.indexOf("</rquid>", first + 7);
			if (last > first) {
				rquid = xmlReqLow.substring(first + 7, last);
			}
		}
		if (rquid == null) {
			rquid = "N/A";
		}

		try {
			LogMQ mqlog = new LogMQ();
			mqlog.setCustomsRefNo(customsRefNo);
			mqlog.setGetQname(config.getResQueue());
			mqlog.setMethodName(methodname);
			mqlog.setPutQname(config.getReqQueue());
			mqlog.setqManager(config.getQmanager());
			mqlog.setReturnCode(rescode);
			mqlog.setRqUid(rquid);
			mqlog.setSendId(sendId);
			mqlog.setXmlInput(xmlreq);
			mqlog.setXmlOutput(xmlres);

			LogMQService logMQService = new LogMQService();
			logMQService.insertLogMQ(mqlog);
			result = true;

		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("error insert log ==>"+e.getMessage());
			log.error("logging mq message failed", e);

		}

		return result;
	}

	public String sendMessageOnly(String xmlRequestString, String reqQueuename, String sendId,
			String methodName, String customsRefNo) throws MQConnectorFailedException,
			EGuaranteeMQMessageException {

		config = new MQConfig();
		config.setReqQueue(reqQueuename);
		Message msg = new Message(config);
		// for add mq log
		try {
			if (msg.sendonly(xmlRequestString)) {
				//send success
				//insertMQLog(xmlRequestString, "success", methodName, "00");
				//sendMessage(xmlRequestString, reqQueuename, "", "Setup Issue", "customsRefNo", "SMS");
				insertMQLog(xmlRequestString,"", methodName, "00",customsRefNo, sendId);
				
				return "send message success";
			}
			
			//send fail
			insertMQLog(xmlRequestString, "no response from MQ-server!",
					methodName, "99");
			System.out.println("xmlRequestString: " + xmlRequestString + "methodName" + methodName);
			throw new MQConnectorFailedException("no response from MQ-server!");
		} catch(MQConnectorFailedException mqe){
			insertMQLog(xmlRequestString, mqe.getMessage(),
					methodName, "99");
			System.out.println("xmlRequestString: " + xmlRequestString + "methodName" + methodName);
			System.out.println("MQE: " + mqe.getMessage());
			throw mqe;
		} catch(Exception e){
			System.out.println("xmlRequestString: " + xmlRequestString + "methodName" + methodName);
			insertMQLog(xmlRequestString, e.getMessage(),
					methodName, "99");
			System.out.println("E: " + e.getMessage());
			throw new MQConnectorFailedException("no response from MQ-server!");
		} finally {
			msg.close();
		}

	}

}
