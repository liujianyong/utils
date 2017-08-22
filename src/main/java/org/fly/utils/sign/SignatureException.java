package org.fly.utils.sign;

import org.fly.exception.PlatformException;

/**
 * 签名出错的异常
 * 
 *  
 * 
 */
public class SignatureException extends PlatformException {

    private static final long serialVersionUID = 8298066329559240498L;

    /**
     * @param code
     *            错误代码
     * @param message
     *            错误信息
     * @param args
     *            额外的参数
     */
    public SignatureException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     *            错误代码
     * @param message
     *            错误信息
     * @param args
     *            额外的参数
     * @param cause
     *            异常堆栈
     */
    public SignatureException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     *            错误代码
     * @param message
     *            错误信息
     * @param cause
     *            异常堆栈
     */
    public SignatureException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     *            错误代码
     * @param message
     *            错误信息
     */
    public SignatureException(int code, String message) {
        super(code, message);
    }

    /**
     * @param message
     *            错误信息
     * @param args
     *            额外的参数
     */
    public SignatureException(String message, Object[] args) {
        super(message, args);
    }

    /**
     * @param message
     *            错误信息
     * @param args
     *            额外的参数
     * @param cause
     *            异常堆栈
     */
    public SignatureException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

    /**
     * @param message
     *            错误信息
     * @param cause
     *            异常堆栈
     */
    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     *            错误信息
     */
    public SignatureException(String message) {
        super(message);
    }

}
