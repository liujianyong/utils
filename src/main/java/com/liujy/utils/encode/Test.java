package com.liujy.utils.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "http://pmp-proxy.sf-express.com/pmp_int/pmp/http/xhc/onJobStatusReq.ht?jsonData=%5B%7B%22cardNo%22%3A%22132201198604082487%22%2C%22cardType%22%3A%2201%22%2C%22searchDate%22%3A%2220170911%22%7D%5D";
        System.out.println(URLDecoder.decode(s,"utf-8"));
    }
}
