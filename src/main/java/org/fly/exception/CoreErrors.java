package org.fly.exception;

import org.fly.common.IErrors;
import org.fly.common.OpResponse;

/**
 * core模块的异常枚举类
 * 
 *  
 * 
 */
public enum CoreErrors implements IErrors<OpResponse> {

    NKV_EXCEPTION(100, "nkv的%s操作异常"), NKV_RESULT_FAIL(101, "nkv操作结果出错, 错误信息:%s");

    private int code;

    private String message;

    private CoreErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public OpResponse parse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OpResponse parse(Object... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OpResponse parseMsg(String message, Object... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApplicationException exp() {
        return new PlatformException(code, message);
    }

    @Override
    public ApplicationException exp(Object... args) {
        String formatReason = String.format(message, args);
        return new PlatformException(code, formatReason, args);
    }

    @Override
    public ApplicationException exp(Throwable cause, Object... args) {
        String formatReason = String.format(message, args);
        return new PlatformException(code, formatReason, cause, args);
    }

    @Override
    public ApplicationException expMsg(String message, Object... args) {
        String formatReason = String.format(message, args);
        return new PlatformException(formatReason, args);
    }

    @Override
    public ApplicationException expMsg(String message, Throwable cause, Object... args) {
        String formatReason = String.format(message, args);
        return new PlatformException(code, formatReason, cause, args);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
