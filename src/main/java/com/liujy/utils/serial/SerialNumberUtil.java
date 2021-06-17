package com.liujy.utils.serial;


import java.util.Date;

import com.liujy.utils.date.DateUtil;


public class SerialNumberUtil {
    /**
     * 生成流水号
     * @param prefix 类型前缀
     * @param val 	  序列号 
     * @return
     */
    public static String generateSerialNo(String prefix,long val){
    	StringBuilder strBuilder = new StringBuilder(prefix);
        String str = String.format("%09d", val);
        String date = DateUtil.format(new Date(),DateUtil.YYYY_MM_DD);
        strBuilder.append(date).append(str);
        return  strBuilder.toString();
    }
}

