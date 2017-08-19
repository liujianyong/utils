package org.fly.utils.cache.nkv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.netease.backend.nkv.client.Result;
import com.netease.backend.nkv.client.Result.ResultCode;

/**
 * 封装nkv请求的结果
 * 
 *  
 * 
 * @param <T>
 */
public class NkvResult<T> implements Serializable {

    private static final long serialVersionUID = 3403599729605537312L;

    private transient ResultCode code;

    private short version;

    private T result;

    private transient Result<T> originResult;

    /***
     * 
     */
    public NkvResult() {

    }

    /**
     * 封装nkv操作的返回
     * @param originResult nkv客户端返回的结果
     */
    public NkvResult(Result<T> originResult) {
        this.code = originResult.getCode();
        this.version = originResult.getVersion();
        this.result = originResult.getResult();
        this.originResult = originResult;
    }

    /**
     * 封装nkv操作的返回
     * @param code nkv返回结果对象中的错误代码
     * @param version 某个key的版本号
     * @param result nkv操作的返回结果
     */
    public NkvResult(ResultCode code, short version, T result) {
        super();
        this.code = code;
        this.version = version;
        this.result = result;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Result<T> getOriginResult() {
        return originResult;
    }

    public void setOriginResult(Result originResult) {
        this.originResult = originResult;
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		boolean hasCode = false;
		if (code != null) {
			hasCode = true;
		}
		oos.writeBoolean(hasCode);
		if (hasCode) {
			oos.writeInt(code.errno());
		}
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		boolean hasCode = ois.readBoolean();
		if (hasCode) {
			code = ResultCode.castResultCode(ois.readInt());
		}
	}
}
