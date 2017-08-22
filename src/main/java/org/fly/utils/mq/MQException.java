/**
 * 
 */
package org.fly.utils.mq;

import org.fly.exception.PlatformException;

/**
 * mq相关的异常
 *  
 */
public class MQException extends PlatformException {

	private static final long serialVersionUID = 2194956010204150515L;
	
    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     */
	public MQException(int code, String message) {
		super(code, message);
	}

    /**
     * 
     * @param message
     *            错误信息
     */
	public MQException(String message) {
		super(message);
	}

    /**
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     */
    public MQException(String message, Throwable cause) {
        super(message, cause);
    }
}
