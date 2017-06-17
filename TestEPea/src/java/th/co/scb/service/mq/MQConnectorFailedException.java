package th.co.scb.service.mq;

public class MQConnectorFailedException extends Exception {

	private static final long serialVersionUID = 3451063819027149461L;

	public MQConnectorFailedException(String message) {
		super(message);
	}
}
