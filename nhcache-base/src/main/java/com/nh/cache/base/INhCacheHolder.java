package com.nh.cache.base;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author ninghao
 *
 */
public interface INhCacheHolder {
	public void setCacheObject(NhCacheObject cacheObj) throws Exception ;
	public NhCacheObject getCacheObject(String key)  throws Exception;
	public void setCacheObjectList(List<NhCacheObject> cacheObjList)  throws Exception ;
	public Map getCacheMap();
}
