package com.nh.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author ninghao
 * 
 */
public class NhCacheHolder {
	public static Map getCacheHolder() {
		return cacheHolder;
	}

	public void setCacheHolder(Map cacheHolder) {
		NhCacheHolder.cacheHolder = cacheHolder;
	}

	private static Map cacheHolder = new HashMap();

	public static void setCacheObject(NhCacheObject cacheObj) {
		String key = cacheObj.getCahceKey();
		cacheHolder.put(key, cacheObj);
	}

	/*
	 * public static void setCacheObject4key(String key,NhCacheObject cacheObj){
	 * cacheHolder.put(key, cacheObj); }
	 */
	public static NhCacheObject getCacheObject(String key) {
		NhCacheObject nhCacheObject = (NhCacheObject) cacheHolder.get(key);
		return nhCacheObject;
	}
	
	public void setCacheObjectList(List<NhCacheObject> cacheObjList) {
		for(NhCacheObject cacheObj:cacheObjList){
			String key = cacheObj.getCahceKey();
			cacheHolder.put(key, cacheObj);
		}
	}
}
