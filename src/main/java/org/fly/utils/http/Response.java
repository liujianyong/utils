package org.fly.utils.http;

import org.fly.utils.log.Log;
import org.fly.utils.log.LogOp;
import org.fly.utils.validate.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    protected int code = -1;

    protected String content;

    protected String errorMsg;

    protected long reqTime;

    protected byte[] data;

    /**
     * 判断请求是否成功的通用处理
     * 
     * @return
     */
    public boolean isSuc() {
        return isSuc(null);
    }

    /**
     * 判断请求是否成功的通用处理
     * 
     * @param requestId
     *            请求的唯一Id(可以是url,或其他的标识),会记录到日志中
     * @return
     */
    public boolean isSuc(String requestId) {
    	String innerRequestId = requestId; 
        if (innerRequestId == null || innerRequestId.trim().isEmpty()) {
            innerRequestId = "";
        } else {
            innerRequestId = "[" + innerRequestId + "]";
        }
        if (code != 200) {
            logger.error(Log.op(LogOp.HTTP_RESP_FAIL).kv("requestId", innerRequestId).toString());
            return false;
        }
        if (ValidateUtils.isNullOrEmpty(content)) {
            logger.error(Log.op(LogOp.HTTP_RESP_FAIL).kv("requestId", innerRequestId).toString());
            return false;
        }
        return true;
    }

    public static Response fail(String message) {
        Response resp = new Response();
        resp.errorMsg = message;
        return resp;
    }


    @Override
    public String toString() {
        return "Response [code=" + code + ", content=" + content + ", errorMsg=" + errorMsg + ", reqTime=" + reqTime + "]";
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @return the reqTime
     */
    public long getReqTime() {
        return reqTime;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }
}