package com.liujy.utils.common;

import java.nio.charset.Charset;

/**
 * 定义一些公用的常量
 * 
 *  
 */
public class BaseConst {

    /**
     * 秒
     */
    public static final long SECOND = 1000;

    /**
     * 分钟
     */
    public static final long MINUTE = 60 * SECOND;

    /**
     * 小时
     */
    public static final long HOUR = 60 * MINUTE;

    /**
     * 天
     */
    public static final long DAY = 24 * HOUR;

    /**
     * 月
     */
    public static final long MONTH = 30 * DAY;

    /**
     * 系统换行符
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 系统路径分割符
     */
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    /**
     * 字符编码
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * GBK编码
     */
    public static final Charset GBK_CHARSET = Charset.forName("gbk");

    /**
     * UTF-8编码
     */
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    
    /**
     * KB
     */
    public static final int KB = 1024;
    
    /**
     * MB
     */
    public static final int MB = 1024 * KB;
}
