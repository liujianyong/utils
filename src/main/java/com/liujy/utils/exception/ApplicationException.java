package com.liujy.utils.exception;

/**
 * 异常基类, 带异常码
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常错误码
     */
    protected int code;

    /**
     * 异常信息的参数
     */
    protected Object[] args;

    /**
     * 
     */
    public ApplicationException() {
        super();
    }

    /**
     * 
     * @param message
     *            错误信息
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     */
    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
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
    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 
     * @param message
     *            错误信息
     * @param args
     *            额外参数
     */
    public ApplicationException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    /**
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            额外参数
     */
    public ApplicationException(String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.args = args;
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
    public ApplicationException(int code, String message, Object[] args) {
        super(message);
        this.code = code;
        this.args = args;
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
    public ApplicationException(int code, String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

}
