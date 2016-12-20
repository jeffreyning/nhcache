package com.nh.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nh.cache.base.NhCacheConst;
import com.nh.cache.base.NhCacheObject;

public class LoadCacheUtil {

public static NhCacheObject queryCacheObject(String key){
	String realKey=NhCacheConst.CACHE_PREFIX+key;
	Map<String,String> cacheMap=RedisUtil.hgetAll(realKey);
	String nhCacheVersion=cacheMap.get(NhCacheConst.CACHE_VERSION);
	NhCacheObject nhCacheObject=new NhCacheObject();
	nhCacheObject.setCacheVersion(nhCacheVersion);
	nhCacheObject.setCahceKey(key);
	nhCacheObject.cacheMap=cacheMap;
	
	String nhCacheType=cacheMap.get(NhCacheConst.CACHE_TYPE);
	nhCacheObject.setCahceKey(nhCacheType);
	nhCacheObject.setCacheSource("redis");
	
	return nhCacheObject;
}

public static int checkCacheVersion(String key,String version){
	if(version==null){
		version="";
	}
	String realKey=NhCacheConst.CACHE_PREFIX+key;
	boolean existFlag=RedisUtil.exists(realKey);
	if(existFlag==false){
		return -2;
	}
	String remoteVersion=RedisUtil.hget(realKey, NhCacheConst.CACHE_VERSION);
	if(remoteVersion==null || remoteVersion.equals("")){
		return -1;
	}
	if(version.equals(remoteVersion)){
		return 0;
	}
	return 1;
}

public static Set<String> listCacheKeys(String sysId,String prefix){
	if(sysId==null){
		sysId="";
	}
	if(prefix==null){
		prefix="";
	}
	String findKey=NhCacheConst.CACHE_PREFIX+sysId+"_"+prefix+"*";
	Set<String> keys=RedisUtil.getKeys(findKey);
	return keys;
}

}
