package org.fly.utils.cache.nkv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.fly.utils.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;

/**
 * NKV缓存管理器
 * <p>
 * NKV缓存管理器实现了spring的缓存管理器接口，以支持spring的缓存注解。</br>
 * 对于简单应用，可以配置名称和过期时间即可 </br>  
 * 对于复杂应用，可以直接构建nkvCaches这个属性
 * 
 *  
 */
public class NkvCacheManager extends AbstractCacheManager {

    /**
     * NKV缓存列表
     */
    private List<NkvCache> nkvCaches;

    /**
     * framework封装的NKV客户端
     */
    private INkvClient nkvClient;

    /**
     * 缓存名称和过期时间配置集
     */
    private Map<String, Integer> cacheConfigurations;

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.support.AbstractCacheManager#loadCaches()
     */
    @Override
    protected Collection<? extends Cache> loadCaches() {
        return nkvCaches;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.cache.support.AbstractCacheManager#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        // 通过cacheConfigurations构建nkvCaches列表
        if (cacheConfigurations != null && !cacheConfigurations.isEmpty()) {
            if (nkvCaches == null) {
                nkvCaches = new ArrayList<NkvCache>();
            }
            List<NkvCache> configCaches = buildCaches(cacheConfigurations);
            nkvCaches.addAll(configCaches);
        }
        Assert.notEmpty(nkvCaches, "nkvCaches would not be empty!");

        List<String> cacheNames = new ArrayList<String>();
        for (NkvCache nkvCache : nkvCaches) {
            String cacheName = nkvCache.getName();

            // 检验是否有重复的名称
            if (cacheNames.contains(cacheName)) {
                throw new CacheException("cacheName '" + cacheName + "' has exists!");
            }
            cacheNames.add(cacheName);
        }

        super.afterPropertiesSet();
    }

    protected List<NkvCache> buildCaches(Map<String, Integer> cacheConfigurations) {
        List<NkvCache> caches = new ArrayList<NkvCache>();
        Iterator<Entry<String, Integer>> ite = cacheConfigurations.entrySet().iterator();

        while (ite.hasNext()) {
            Entry<String, Integer> entry = ite.next();
            String cacheName = entry.getKey();
            NkvCache nkvCache = new NkvCache();
            nkvCache.setNkvClient(nkvClient);
            nkvCache.setExp(entry.getValue());
            nkvCache.setName(cacheName);
            try {
                nkvCache.afterPropertiesSet();
                caches.add(nkvCache);
            } catch (CacheException e) {
                throw e;
            } catch (Exception e) {
                throw new CacheException("create nkvCache cache fail! nkvCache = [" + nkvCache + "]", e);
            }
        }
        return caches;
    }

    public void setNkvCaches(List<NkvCache> nkvCaches) {
        this.nkvCaches = nkvCaches;
    }

    public void setNkvClient(INkvClient nkvClient) {
        this.nkvClient = nkvClient;
    }

    public void setCacheConfigurations(Map<String, Integer> cacheConfigurations) {
        this.cacheConfigurations = cacheConfigurations;
    }
}
