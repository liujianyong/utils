package org.fly.utils.convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 *  一些数据转换
 *  
 * 
 */
public class ConvertUtils {

    /*==============================*/
    /*          private             */
    /*==============================*/
    private static String unSupportedMsg(Object obj){
		return "UnsupportedType - " + obj.getClass();
	}
		
    /*==============================*/
    /*          Integer             */
    /*==============================*/
	public static Integer getInteger(Object obj) {
		return getInteger(obj, null);
	}
	
	public static Integer getInteger(Object obj, Integer defaultValue) {
		Integer ret = defaultValue;
		if (obj == null) {
			return ret;
		}
		try {
			if (obj instanceof String) {
				ret = Integer.valueOf((String) obj);
			} else if (obj instanceof Integer) {
				ret = (Integer)obj;
			} else if(obj instanceof Number) {
				Number n = (Number) obj;
				ret = n.intValue();
			}
			else {
				throw new IllegalArgumentException(unSupportedMsg(obj));
			}
			
		} catch (NumberFormatException ex) {
		}
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static Integer getInteger(Map map, String key) {
		return getInteger(map, key, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static Integer getInteger(Map map, String key, Integer defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		Object obj = map.get(key);
		if (obj != null) {
			return getInteger(obj, defaultValue);
		}
		return defaultValue;
	}
	
	
    /*==============================*/
    /*          Long                */
    /*==============================*/
	public static Long getLong(Object obj) {
		return getLong(obj, null);
	}
	
	public static Long getLong(Object obj, Long defaultValue) {
		Long ret = defaultValue;
		if (obj == null) {
			return ret;
		}
		try {
			if (obj instanceof String) {
				ret = Long.valueOf((String) obj);
			} else if (obj instanceof Long) {
				ret = (Long) obj;
			} else if (obj instanceof Number) {
				Number n = (Number) obj;
				ret = n.longValue();
			} else {
				throw new IllegalArgumentException(unSupportedMsg(obj));
			}
		} catch (NumberFormatException ex) {
		}
		return ret;
	}

	@SuppressWarnings("rawtypes")
	public static Long getLong(Map map, String key) {
		return getLong(map, key, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static Long getLong(Map map, String key, Long defaultValue) {
		if(map == null){
			return defaultValue;
		}
		Object obj = map.get(key);
		if(obj != null) {
			return getLong(obj, defaultValue);
		}
		return defaultValue;
	}

    /*==============================*/
    /*          Double              */
    /*==============================*/
	public static Double getDouble(Object obj) {
		return getDouble(obj, null);
	}
	
	public static Double getDouble(Object obj, Double defaultValue) {
		Double ret = defaultValue;
		if (obj == null) {
			return ret;
		}
		try {			
			if (obj instanceof String) {
				ret = Double.valueOf((String) obj);
			} else if (obj instanceof Double) {
				ret = (Double) obj;
			} else if (obj instanceof Long) {
				// d = Double.valueOf(o +"");
				ret = (Long) (obj) / 1.0;// this is much faster
			} else if (obj instanceof Integer) {
				ret = (Integer) (obj) / 1.0;
			} else if(obj instanceof BigDecimal) {
				ret = ((BigDecimal)obj).doubleValue();
			} else {
				throw new IllegalArgumentException(unSupportedMsg(obj));
			}
		} catch (NumberFormatException ex) {
		}
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static Double getDouble(Map map, String key) {
		return getDouble(map, key, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static Double getDouble(Map map, String key, Double defaultValue) {
		if(map == null){
			return defaultValue;
		}
		Object obj = map.get(key);
		if(obj != null) {
			return getDouble(obj, defaultValue);
		}
		return defaultValue;
	}
	
    /*==============================*/
    /*          DoubleFormat        */
    /*==============================*/
	/**
	 * 默认保留小数点后2位
	 * 
	 * @param
	 * @return
	 */
	public static Double getDoubleFormat(Object o) {
		DecimalFormat df = new DecimalFormat("#.00");
		Double d = getDouble(o);
		return getDouble(df.format(d));
	}


    /*==============================*/
    /*          DoubleFormat        */
    /*==============================*/
	public static Boolean getBool(Object obj) {
		return getBool(obj, null);
	}
	
	public static Boolean getBool(Object obj, Boolean defaultValue) {
		Boolean ret = defaultValue;
		if (obj == null) {
			return ret;
		}
		try {
			if (obj instanceof String) {
				ret = Boolean.valueOf((String)obj);
			} else if (obj instanceof Long) {
				Long l = (Long)obj;
				ret = l.longValue() == 1L ? true : false;
			} else if (obj instanceof Integer) {
				Integer i = (Integer)obj;
				ret = i.intValue() == 1 ? true : false;
			} else {
				throw new IllegalArgumentException(unSupportedMsg(obj));
			}
		} catch (NumberFormatException ex) {
		}
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static Boolean getBool(Map map,String key){
		return getBool(map, key, null);
	}	

	@SuppressWarnings("rawtypes")
	public static Boolean getBool(Map map,String key, Boolean defaultValue){
		if (map == null) {
			return defaultValue;
		}
		Object obj = map.get(key);
		if (obj != null) {
			return getBool(obj, defaultValue);
		}
		return defaultValue;
	}
	
	/*==============================*/
    /*          String              */
    /*==============================*/
	public static String getString(Object obj) {
		return getString(obj, null);
	}
	
	public static String getString(Object obj, String defaultValue) {
		String ret = defaultValue;
		if(obj == null) {
			return ret;
		}
		ret = String.valueOf(obj);
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getString(Map map, String key) {
		return getString(map, key, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static String getString(Map map, String key, String defaultValue) {
		if(map == null) {
			return defaultValue;
		}
		Object obj = map.get(key);
		if(obj != null) {
			return getString(obj, defaultValue);
		}
		return defaultValue;
	}
}
