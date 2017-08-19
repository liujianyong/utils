package org.fly.utils.serializer;

/**
 * 序列化异常
 * 
 *  464281
 */
public class SerializeException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 7630404770412546869L;

	public SerializeException() {
		super();
	}

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializeException(Throwable cause) {
		super(cause);
	}
}
