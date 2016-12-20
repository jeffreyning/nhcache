package com.nh.cache.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ninghao
 *
 */
public class NhCacheHolder {
private static Map cacheHolder=new HashMap();
public static void setCacheObject(NhCacheObject cacheObj){
	String key=cacheObj.getCahceKey();
	cacheHolder.put(key, cacheObj);
}
/*public static void setCacheObject4key(String key,NhCacheObject cacheObj){
	cacheHolder.put(key, cacheObj);
}*/
public static NhCacheObject getCacheObject(String key){
	NhCacheObject nhCacheObject=(NhCacheObject) cacheHolder.get(key);
	return nhCacheObject;
}
}
