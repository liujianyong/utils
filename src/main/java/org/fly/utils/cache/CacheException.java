package org.fly.utils.cache;

/**
 * 缓存异常
 * 
 *  464281
 */
public class CacheException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 4543596505445947947L;

    /**
	 * 
	 */
    public CacheException() {
        super();
    }

    /**
     * @param message
     *            错误信息
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * @param message
     *            错误信息
     * @param cause
     *            异常堆栈
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     *            异常堆栈
     */
    public CacheException(Throwable cause) {
        super(cause);
    }
}
