package com.liujy.utils.exception;

/**
 * 逻辑异常的父类
 * 
 */
public class LogicException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public LogicException() {
        super();
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     */
    public LogicException(int code, String message) {
        super(code, message);
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     */
    public LogicException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 
     * @param message
     *            错误信息
     */
    public LogicException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     */
    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     * @param args
     *            额外参数
     */
    public LogicException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            额外参数
     */
    public LogicException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * 
     * @param message
     *            错误信息
     * @param args
     *            额外参数
     */
    public LogicException(String message, Object[] args) {
        super(message, args);
    }

    /**
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            额外参数
     */
    public LogicException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

}
