package org.fly.utils.cache.nkv;

import org.apache.commons.lang3.StringUtils;
import org.fly.utils.cache.CacheException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * NKV缓存的封装
 * 
 *  
 */
public class NkvCache implements Cache, InitializingBean {

    /**
     * 缓存名称，这里当做nkvClient的prefix来用
     */
    private String name;

    /**
     * 缓存失效时间，以秒为单位
     */
    private int exp;

    /**
     * 缓存客户端
     */
    private INkvClient nkvClient;

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#getNativeCache()
     */
    @Override
    public Object getNativeCache() {
        return this;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#get(java.lang.Object)
     */
    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object result = nkvClient.get(name, key.toString());
        if (result != null) {
            wrapper = new SimpleValueWrapper(result);
        }
        return wrapper;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)j
     */
    @Override
    public void put(Object key, Object value) {
        // value为null则删除
        if (null == value) {
            evict(key);
            return;
        }
        // put之前先做一把get？还是直接put(0)
        nkvClient.set(name, key.toString(), value, exp);
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#evict(java.lang.Object)
     */
    @Override
    public void evict(Object key) {
        nkvClient.delete(name, key.toString());
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#clear()
     */
    @Override
    public void clear() {
        throw new CacheException("nkv cache not support clear operation!");
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (exp <= 0) {
            throw new CacheException("NkvCache expiration time must be grant than zero!");
        }
        if (StringUtils.isBlank(name)) {
            throw new CacheException("NkvCache name could not be blank!");
        }
        if (nkvClient == null) {
            throw new CacheException("NkvCache nkvClient could not be null!");
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#get(java.lang.Object, java.lang.Class)
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        return nkvClient.get(name, key.toString(), type);
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#putIfAbsent(java.lang.Object, java.lang.Object)
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        nkvClient.setIfNotExist(name, key.toString(), value, exp);
        return new SimpleValueWrapper(value);
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.Cache#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public INkvClient getNkvClient() {
        return nkvClient;
    }

    public void setNkvClient(INkvClient nkvClient) {
        this.nkvClient = nkvClient;
    }
}
