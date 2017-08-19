/**
 * 
 */
package org.fly.utils.cache.nkv;

import org.apache.commons.lang3.ArrayUtils;
import org.fly.exception.ApplicationException;
import org.fly.exception.CoreErrors;
import org.fly.utils.cache.CacheException;
import org.fly.utils.cache.IHomeCacheClient;
import org.fly.utils.serializer.Serializer;

import com.google.common.primitives.Ints;
import com.netease.backend.nkv.client.NkvClient.NkvOption;
import com.netease.backend.nkv.client.Result;
import com.netease.backend.nkv.client.error.NkvException;
import com.netease.backend.nkv.client.impl.DefaultNkvClient;

/**
 * 封装了NkvClient相关缓存操作的代理类
 * 
 *  
 */
public class INkvClient implements IHomeCacheClient {

    /** 底层的nkv客户端 */
    private DefaultNkvClient nkvClient;

    /** nkv客户端所需要的namespace字段 */
    private String namespace;

    /** 超时时间 */
    private long timeout;

    /** 序列化和反序列化器 */
    private Serializer<Object> serializer;

    /** 相关常量 */
    private static final short FORCE_UPDATE = 0;
    private static final long DEFAULT_TIMEOUT = 2000;
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String DEL = "delete";
    private static final String SET_COUNT = "setCount";
    private static final String GET_COUNT = "getCount";
    private static final String DECR = "decr";
    private static final String INCR = "incr";

    /**
     * 
     * @param master
     *            master地址
     * @param slave
     *            slave地址
     * @param group
     *            要操作的group
     * @param namespace
     *            要操作的namespace
     */
    public INkvClient(String master, String slave, String group, String namespace) {
        this(master, slave, group, namespace, DEFAULT_TIMEOUT);
    }

    /**
     * 
     * @param master
     *            master地址
     * @param slave
     *            slave地址
     * @param group
     *            要操作的group
     * @param namespace
     *            要操作的namespace
     * @param timeout
     *            超时时间，单位毫秒
     */
    public INkvClient(String master, String slave, String group, String namespace, long timeout) {
        this.namespace = namespace;
        this.timeout = timeout;
        nkvClient = new DefaultNkvClient();
        nkvClient.setMaster(master);
        nkvClient.setSlave(slave);
        nkvClient.setGroup(group);
        try {
            nkvClient.init(timeout);
        } catch (NkvException e) {
            throw new CacheException("DefaultNkvClient init fail", e);
        }
    }

    /**
     * -------------------incrVersion----------------------------
     */

    /**
     * 递增一下版本号
     * 
     * @param version
     *            版本号
     * @return
     */
    public short incrVersion(short version) {
        if (version < Short.MAX_VALUE) {
            return (short) (version + 1);
        } else {
            return 2;
        }
    }

    /**
     * 检查nkv操作的逻辑结果是否正常
     * 
     * @param nkvResult
     */
    private <T> void checkResult(Result<T> nkvResult) {
        if (!nkvResult.isSuccess()) {
            throw CoreErrors.NKV_RESULT_FAIL.exp(nkvResult.getCode().toString());
        }
    }

    /**
     * 转换结果
     * 
     * @param result
     * @return
     */
    private <T> NkvResult<T> convert(Result<T> result) {
        return new NkvResult<>(result);
    }

    /**
     * 转换结果,主要是处理byte[]类型的参数，需要反序列化一下
     * 
     * @param result
     * @return
     */
    private NkvResult<Object> convertAnDserialize(Result<byte[]> result) {
        NkvResult<Object> nkvResult = new NkvResult<Object>(result.getCode(), result.getVersion(), serializer.deserialize(result.getResult()));
        nkvResult.setOriginResult(result);
        return nkvResult;
    }

    /**
     * -------------------set----------------------------
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#set(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void set(String key, Object value) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            result = nkvClient.put(namespace, serializer(key), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#set(java.lang.String,
     * java.lang.Object, int)
     */
    @Override
    public void set(String key, Object value, int exp) {
        NkvOption opt = new NkvOption(timeout, FORCE_UPDATE, exp);
        Result<Void> result = null;
        try {
            result = nkvClient.put(namespace, serializer(key), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /**
     * 放入缓存，指定key的前缀
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            key的值
     */
    public void set(String prefix, String key, Object value) {
        NkvOption opt = new NkvOption(timeout);
        set(prefix, key, value, opt);
    }

    /**
     * 放入缓存，指定key的前缀和nkv相关配置
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            key的值
     * @param opt
     *            opt中的expire单位是秒
     */
    public void set(String prefix, String key, Object value, NkvOption opt) {
        Result<Void> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.put(namespace, serializer(finalKey), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /**
     * 放入缓存，指定key的前缀和过期时间
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            key的值
     * @param exp
     *            过期时间，单位是秒
     */
    public void set(String prefix, String key, Object value, int exp) {
        NkvOption opt = new NkvOption(timeout, FORCE_UPDATE, exp);
        set(prefix, key, value, exp, opt);
    }

    /**
     * 放入缓存，指定key的前缀和过期时间和nkv相关配置
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            key的值
     * @param exp
     *            过期时间，单位是秒
     * @param opt
     *            nkv操作的选项
     */
    public void set(String prefix, String key, Object value, int exp, NkvOption opt) {
        Result<Void> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.put(namespace, serializer(finalKey), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /**
     * 放入缓存，并返回nkv返回的结果
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            key的值
     * @param opt
     *            opt中的expire单位是秒
     * @return
     */
    public NkvResult<Void> setResult(String prefix, String key, Object value, NkvOption opt) {
        try {
            String finalKey = prefix + key;
            return convert(nkvClient.put(namespace, serializer(finalKey), serializer(value), opt));
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
    }

    /**
     * -------------------get----------------------------
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#get(java.lang.String)
     */
    @Override
    public Object get(String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<byte[]> result = null;
        try {
            result = nkvClient.get(namespace, serializer(key), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, GET);
        }
        checkResult(result);

        if (null != result.getResult()) {
            return deserialize(result.getResult());
        }
        return null;
    }

    /**
     * 从缓存中获取对象，指定key的前缀
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @return
     */
    public Object get(String prefix, String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<byte[]> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.get(namespace, serializer(finalKey), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, GET);
        }
        checkResult(result);

        if (null != result.getResult()) {
            return deserialize(result.getResult());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.I`HomeCacheClient#get(java.lang.String,
     * java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, Class<T> t) {
        return (T) get(key);
    }

    /**
     * 从缓存中获取对象，指定key的前缀和value的具体类型
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param <T>
     * @param t
     *            value的类型
     * @return
     */
    public <T> T get(String prefix, String key, Class<T> t) {
        return (T) get(prefix, key);
    }

    /**
     * 从缓存中获取对象，返回nkv返回的result
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @return
     */
    public NkvResult<Object> getResult(String prefix, String key) {
        NkvOption opt = new NkvOption(timeout);
        try {
            String finalKey = prefix + key;
            return convertAnDserialize(nkvClient.get(namespace, serializer(finalKey), opt));
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, GET);
        }
    }

    /**
     * -------------------delete----------------------------
     */
    /*
     * 
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#delete(java.lang.String)
     */
    @Override
    public void delete(String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            result = nkvClient.remove(namespace, serializer(key), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, DEL);
        }
        checkResult(result);
    }
    
    /**
     * 删除key
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     */
    public void delete(String prefix, String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.remove(namespace, serializer(finalKey), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, DEL);
        }
        checkResult(result);
    }


    /**
     * 删除key，当key不存在的时候，抛出异常
     * @param key 操作的key
     * @return
     * @throws ApplicationException
     */
    public void deleteIfNotExists(String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            result = nkvClient.remove(namespace, serializer(key), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, DEL);
        }
        if (result.getCode().errno() != 0) {
            throw CoreErrors.NKV_RESULT_FAIL.exp(result.getCode().toString());
        }
    } 
    
    /**
     * 删除key，当key不存在的时候，抛出异常
     * @param prefix 前缀
     * @param key 操作的key
     */
    public void deleteIfNotExists(String prefix, String key) {
        String finalKey = prefix + key;
        deleteIfNotExists(finalKey);
    }
    /**
     * -------------------setCount----------------------------
     */
    /**
     * 类似set接口，不过value为int类型，用于计数器之类的场景
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param count
     *            计数器的值
     * @return
     */
    public NkvResult<Void> setCount(String prefix, String key, int count) {
        NkvOption opt = new NkvOption(timeout);
        try {
            String finalKey = prefix + key;
            return convert(nkvClient.setCount(namespace, serializer(finalKey), count, opt));
        } catch (Exception ex) {
            throw CoreErrors.NKV_EXCEPTION.exp(ex, SET_COUNT);
        }
    }

    /**
     * -------------------getCount----------------------------
     */
    /**
     * 对应setCount接口，获取计算器的值
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @return
     */
    public int getCount(String prefix, String key) {
        NkvOption opt = new NkvOption(timeout);
        Result<byte[]> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.get(namespace, serializer(finalKey), opt);
        } catch (Exception ex) {
            throw CoreErrors.NKV_EXCEPTION.exp(ex, GET_COUNT);
        }
        checkResult(result);

        byte[] values = result.getResult();
        // nkv保存counter的value的时候，是倒序保存byte数组的，所以这里需要reverse一下
        ArrayUtils.reverse(values);
        return Ints.fromByteArray(values);
    }

    /**
     * -------------------decr----------------------------
     */

    /**
     * 对某个key的值做减法
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            要减去多少
     * @param initValue
     *            若key不存在的时候，初始的值
     * @return 做完减法操作后的值
     */
    public int decr(String prefix, String key, int value, int initValue) {
        NkvOption opt = new NkvOption(timeout);
        return decr(prefix, key, value, initValue, opt);
    }

    /**
     * 对某个key的值做减法
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            要减去多少
     * @param initValue
     *            若key不存在的时候，新建key并指定为此初始值，然后再做减法
     * @param opt
     *            对decr方法，opt中只有timeout和expire是有用的
     * @return 做完减法操作后的值
     */
    public int decr(String prefix, String key, int value, int initValue, NkvOption opt) {
        Result<Integer> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.decr(namespace, serializer(finalKey), value, initValue, opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, DECR);
        }
        checkResult(result);

        return result.getResult();
    }

    /**
     * -------------------incr----------------------------
     */

    /**
     * 对某个key的值做加法
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            要加上多少
     * @param initValue
     *            若key不存在的时候，新建key并指定为此初始值，然后再做加法
     * @return 做完减法操作后的值
     */
    public int incr(String prefix, String key, int value, int initValue) {
        NkvOption opt = new NkvOption(timeout);
        return incr(prefix, key, value, initValue, opt);
    }

    /**
     * 对某个key的值做加法
     * 
     * @param prefix
     *            前缀
     * @param key
     *            操作的key
     * @param value
     *            要加上多少
     * @param initValue
     *            若key不存在的时候，新建key并指定为此初始值，然后再做加法
     * @param opt
     *            对incr方法，opt中只有timeout和expire是有用的
     * @return 做完减法操作后的值
     */
    public int incr(String prefix, String key, int value, int initValue, NkvOption opt) {
        Result<Integer> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.incr(namespace, serializer(finalKey), value, initValue, opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, INCR);
        }
        checkResult(result);

        return result.getResult();
    }

    /**
     * -------------------setIfNotExist----------------------------
     */

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#setIfNotExist(java.lang
     * .String, java.lang.Object)
     */
    @Override
    public void setIfNotExist(String key, Object value) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            result = nkvClient.putIfNoExist(namespace, serializer(key), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /**
     * 放入缓存，当该key已存在或网络出错，统一都抛出PlatformException
     * 
     * @param prefix 指定key的前缀
     * @param key 放入nkv中的key
     * @param value 值
     */
    public void setIfNotExist(String prefix, String key, Object value) {
        NkvOption opt = new NkvOption(timeout);
        Result<Void> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.putIfNoExist(namespace, serializer(finalKey), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.core.cache.IHomeCacheClient#setIfNotExist(java.lang
     * .String, java.lang.Object, int)
     */
    @Override
    public void setIfNotExist(String key, Object value, int exp) {
        NkvOption opt = new NkvOption(timeout, FORCE_UPDATE, exp);
        Result<Void> result = null;
        try {
            result = nkvClient.putIfNoExist(namespace, serializer(key), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }

    /**
     * 放入缓存，当该key已存在或网络出错，统一都抛出PlatformException 
     * @param prefix 指定key的前缀
     * @param key 放入nkv中的key
     * @param value 值
     * @param exp 过期时间
     */
    public void setIfNotExist(String prefix, String key, Object value, int exp) {
        NkvOption opt = new NkvOption(timeout, FORCE_UPDATE, exp);
        Result<Void> result = null;
        try {
            String finalKey = prefix + key;
            result = nkvClient.putIfNoExist(namespace, serializer(finalKey), serializer(value), opt);
        } catch (Exception e) {
            throw CoreErrors.NKV_EXCEPTION.exp(e, SET);
        }
        checkResult(result);
    }
    
    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public byte[] serializer(Object obj) {
        return serializer.serialize(obj);
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public Object deserialize(byte[] bytes) {
        return serializer.deserialize(bytes);
    }

    /**
     * @return the nkvClient
     */
    public DefaultNkvClient getNkvClient() {
        return nkvClient;
    }

    /**
     * @param serializer
     *            the serializer to set
     */
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public String getNamespace() {
        return namespace;
    }

    public long getTimeout() {
        return timeout;
    }

    public Serializer<Object> getSerializer() {
        return serializer;
    }

    public void setNkvClient(DefaultNkvClient nkvClient) {
        this.nkvClient = nkvClient;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
