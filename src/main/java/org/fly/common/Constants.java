package org.fly.common;

public class Constants {
	
	public final static String CHAR_SET="UTF-8";
	
	public final static String HTTPS_PRE = "https";
    public final static String HTTP_PRE = "http";
    
    /** appId标识 **/
    public final static String APP_ID_KEY = "appId";
    
    /** sourceNo标识 **/
    public final static String SOURCE_NO_KEY = "sourceNo";

    /** secretKey标识 **/
    public final static String APP_SECRET_KEY = "keySecret";
    
    /** customerId标识 **/
    public final static String CUSTOMER_KEY = "customerId";
    
    /** scope标识 **/
    public final static String SCOPE_KEY = "scope";
    
    /** appCode标识 **/
    public final static String APP_CODE_KEY = "appCode";

    /** urlKey标识 **/
    public final static String WIDGET_URL_KEY = "urlKey";

    /** accessToken标识 **/
    public final static String ACCESSTOKEN_KEY="accessToken";
    
    /** 时间戳标识 **/
    public final static String TIMESTAMP_KEY="timestamp";
    
    /** 签名标识 **/
    public final static String SIGNATURE_KEY ="signature";
    
    public static final String DOT = ".";
    
    public static final String SLASH = "/";
    
    /** 配置文件路径 **/
    public final static String PROP_FILE_PATH="/props/open-sdk.properties";
	
	public final static class ApiPlatformInfo{
	    public final static String DIRECT_URL="/auth/redirect_app";
		public final static String ACCESS_TOKEN_URL="/auth/access_token";
		public final static String SERVICE_ACCESS_TOKEN_URL="/auth/service_access_token";
		public final static String MEM_INFO_URL="/api/userInfo";
		public final static String MER_CREATE_ORDER="/api/mer_create_order";
		public final static String API_GENERAL_POST="/api/general_post";
		public final static String API_GENERAL_GET="/api/general_get";
		public final static String SERVER_GENERAL_POST="/service/general_post";
        public final static String SERVER_GENERAL_GET="/service/general_get";
	}
}
