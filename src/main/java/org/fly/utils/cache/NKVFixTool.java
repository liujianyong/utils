package org.fly.utils.cache;

import org.apache.commons.lang3.StringUtils;
import org.fly.utils.cache.nkv.INkvClient;
import org.fly.utils.convert.ConvertUtils;
import org.fly.utils.serializer.HessianSerializer;

public class NKVFixTool {

    static INkvClient nkvClient;
    static String master = "10.166.49.108:5198";
    static String slave = "10.166.49.109:5198";
    static String group = "group01";
    static String namespace = "prod01";
    long timeout = 5000;
    static HessianSerializer<Object> serializer = new HessianSerializer<Object>();
    public static final String TRUE = "true";
    
    public static void main(String[] args) {
        String prefix = System.getProperty("prefix");
        if(StringUtils.isBlank(prefix)) {
            throw new RuntimeException("prefix can not be empty");
        }
        String keyOne = System.getProperty("key");
        if(StringUtils.isBlank(keyOne)) {
            throw new RuntimeException("key can not be empty");
        }
        String value = System.getProperty("value");
        if(StringUtils.isBlank(keyOne)) {
            throw new RuntimeException("value can not be empty");
        }
        Long valueLong = ConvertUtils.getLong(value);
        if(valueLong == null) {
            throw new RuntimeException("value should be number");
        }
        String sure = System.getProperty("sure");
        
        nkvClient = new INkvClient(master, slave, group, namespace);
        nkvClient.setSerializer(serializer);
        long keyOneValue = nkvClient.get(prefix, keyOne, Long.class);
        System.out.println("value before operated:" + keyOneValue);
        
        if(StringUtils.isNotBlank(sure) && sure.equals(TRUE)) {
            nkvClient.set(prefix, keyOne, valueLong);
            keyOneValue = nkvClient.get(prefix, keyOne, Long.class);
            System.out.println("value after operated:" + keyOneValue);
        }
    }

}
