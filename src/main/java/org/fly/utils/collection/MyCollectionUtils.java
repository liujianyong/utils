package org.fly.utils.collection;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * MyCollectionUtils
 * @version 1.0
 */
public class MyCollectionUtils {
    /**
     * collection 复制
     *      转换为指定  clazz类对应的集合
     * @param sources 源集合
     * @param clazz 类型
     * @param <E> 源类型
     * @param <T> 指定类型
     * @return copy得到的集合
     */
    public static <E, T> List<T> collectionCopy(Collection<E> sources, Class<T> clazz) {
        if(CollectionUtils.isEmpty(sources)){
            return null;
        }
        List<T> targets = new ArrayList<T>();
        for(E e : sources){
            try {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(e, t);
                targets.add(t);
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return targets;
    }

    /**
     * 集合转map:分别将集合对象 属性 转为 map中key/value
     * @param values 集合
     * @param function<K, V, T> 处理函数
     *  K ： map 的 key 类型
     *  V ： map 的 value 类型
     *  T ：传入的对象类型
     * @return map
     */
    public static <K, V, T> Map<K, V> collectionToMap(Collection<T> values, MyCollectionUtils.Functions<K, V, ? super T> function) {
        checkNotNull(function);
        if(CollectionUtils.isEmpty(values)){
            return null;
        }
        Map<K, V> map = new HashMap<K, V>();
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            map.put(function.applyKey(t), function.applyValue(t));
        }
        return map;
    }
    /**
     * 集合转map
     * @param values 集合
     * @param function 处理函数
     * @return map
     */
    public static <K, T> Map<K, T> collectionToMap(Collection<T> values, MyCollectionUtils.Function<K, ? super T> function) {
        checkNotNull(function);
        if(CollectionUtils.isEmpty(values)){
            return null;
        }
        Map<K, T> map = new HashMap<K, T>();
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            map.put(function.applyKey(value), value);
        }
        return map;
    }
    /**
     * 集合转分组map
     * @param values 集合
     * @param function 处理函数
     * @return map
     *      按照key进行分组，并返回map集合
     */
    public static <K, T> Map<K, List<T>> collectionToMapGroup(Collection<T> values, MyCollectionUtils.Function<K, ? super T> function) {
        checkNotNull(function);
        if(CollectionUtils.isEmpty(values)){
            return null;
        }
        Map<K, List<T>> map = new HashMap<K, List<T>>();
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            T v = iterator.next();
            K k = function.applyKey(v);
            List<T> c = map.containsKey(k) ? map.get(k) : new ArrayList<T>();
            c.add(v);
            map.put(k, c);
        }
        return map;
    }

    /**
     * 获取集合中 对象 某个属性的list集合
     * @param values 集合
     * @param function 处理函数
     * @return 属性值集合
     */
    public static <K, T> List<K> extractPropertyList(Collection<T> values, Function<K, ? super T> function){
        checkNotNull(function);
        if(CollectionUtils.isEmpty(values)){
            return null;
        }
        List<K> list = new ArrayList<>();
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            T v = iterator.next();
            list.add(function.applyKey(v));
        }
        return list;
    }

    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
    public abstract interface Function<K, T> {
        public abstract K applyKey(T t);
    }
    public abstract interface Functions<K, V, T> {
        public abstract K applyKey(T t);
        public abstract V applyValue(T t);
    }
}
