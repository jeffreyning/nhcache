package com.nh.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author ninghao
 * 
 */
public class NhMemCacheHolder implements INhCacheHolder {
	public Map getCacheHolder() {
		return cacheHolder;
	}

	public void setCacheHolder(Map cacheHolder) {
		this.cacheHolder = cacheHolder;
	}

	private Map cacheHolder = new HashMap();

	public  void setCacheObject(NhCacheObject cacheObj) {
		String key = cacheObj.getCacheKey();
		cacheHolder.put(key, cacheObj);
	}

	/*
	 * public static void setCacheObject4key(String key,NhCacheObject cacheObj){
	 * cacheHolder.put(key, cacheObj); }
	 */
	public  NhCacheObject getCacheObject(String key) {
		NhCacheObject nhCacheObject = (NhCacheObject) cacheHolder.get(key);
		return nhCacheObject;
	}
	
	public void setCacheObjectList(List<NhCacheObject> cacheObjList) {
		for(NhCacheObject cacheObj:cacheObjList){
			String key = cacheObj.getCacheKey();
			cacheHolder.put(key, cacheObj);
		}
	}

	@Override
	public Map getCacheMap() {
		// TODO Auto-generated method stub
		return cacheHolder;
	}
}

