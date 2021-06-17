package com.liujy.utils.string;

import java.util.regex.Pattern;


public class MyStringUtil {
	
    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
            return false;
        } else {
            return true;
        }
    }

    
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String nulltoStr(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
            return "";
        }
        return str;
    }
    
    /**
     * 是否是整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str){ 
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
        return pattern.matcher(str).matches();    
     } 
    
}
