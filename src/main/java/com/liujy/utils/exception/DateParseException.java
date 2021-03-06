package com.liujy.utils.exception;

/**
 * 日期格式化异常
 */
public class DateParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DateParseException() {
        super();
    }

    public DateParseException(String message) {
        super(message);
    }

    public DateParseException(Throwable t) {
        super(t);
    }

    public DateParseException(String message, Throwable t) {
        super(message, t);
    }

}
