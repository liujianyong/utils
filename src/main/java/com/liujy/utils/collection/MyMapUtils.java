package com.liujy.utils.collection;

import org.apache.commons.collections.MapUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * MyMapUtils
 * @version 1.0
 */
public class MyMapUtils {
    
    /**
     * beanToMap
     * 
     * @param t
     *            T
     * @param <T>
     *            t
     * @throws ClassNotFoundException
     *             异常
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public static <T> Map<String, Object> beanToMap(T t) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return MyMapUtils.beanToMap(t, false);
    }
    /**
     * beanToMap
     * 
     * @param t
     *            T
     * @param <T>
     *            t
     * @throws ClassNotFoundException
     *             异常
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public static <T> Map<String, Object> beanToMap(T t, boolean ignoreNull) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> c = Class.forName(t.getClass().getName());
        Method[] m = c.getMethods();
        for (int i = 0; i < m.length; i++) {
            String method = m[i].getName();
            if (method.startsWith("get") && !"getClass".equals(method)) {
                Object value = m[i].invoke(t);
                String key = method.substring(3);
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                if (value != null) {
                    map.put(key, value);
                } else if(!ignoreNull){
                    map.put(key, "");
                }
            }
        }
        return map;
    }
    /**
     * 按照指定集合转换map 
     * @param sourceMap 源集合
     * @param destMap 目标集合
     * @param keys 期望得到的 key 集合
     * @return 
     */
    public static void putAll(Map<String, Object> sourceMap, Map<String, Object> destMap, String...keys) {
        if(keys!=null && keys.length>0){
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if(!sourceMap.containsKey(key)){
                    continue;
                }
                destMap.put(key, sourceMap.get(key));
            }
        }else{
            destMap.putAll(sourceMap);
        }
    }
    /**
     * 获取map内部值
     * @param key
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Object getInner(K key, Map<?, Map<K, V>> map){
        if(MapUtils.isEmpty(map)){
            return null;
        }
        for(Map.Entry<?, Map<K, V>> entry : map.entrySet()){
            Map<K, V> innerMap = entry.getValue();
            if(innerMap.containsKey(key)){
                return innerMap.get(key);
            }
        }
        return null;
    }
    /**
     * 获取唯一map
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K, V> toOnlyMap(Map<?, Map<K, V>> map){
        Map<K, V> result = new HashMap<>();
        if(MapUtils.isEmpty(map)){
            return result;
        }
        for(Map.Entry<?, Map<K, V>> entry : map.entrySet()){
            result.putAll(entry.getValue());
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "test";
        Map<Long, Map<String, Object>> totalMap = new HashMap<Long, Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("test1", "test1");
        map1.put("test2", "test2");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("test3", "test1");
        map2.put("test4", "test2");
        map2.put("test", "test2");

        totalMap.put(1l, map2);

        System.out.println(MyMapUtils.getInner(key, totalMap));
    }
}
