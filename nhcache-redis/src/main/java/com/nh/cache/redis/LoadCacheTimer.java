package com.nh.cache.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.nh.cache.base.NhCacheConst;
import com.nh.cache.base.NhCacheHolder;
import com.nh.cache.base.NhCacheObject;

/**
 * 
 * @author ninghao
 *
 */
public class LoadCacheTimer {
public String sysId="";
public String prefix="";

public void doJob(){
	Set<String> keySet=LoadCacheUtil.listCacheKeys(sysId, prefix);
	if(keySet==null){
		return;
	}
	for(String key:keySet){
		String realKey=key.replaceFirst(NhCacheConst.CACHE_PREFIX, "");
		NhCacheObject nhCacheObject= NhCacheHolder.getCacheObject(realKey);
		String version=null;
		if(nhCacheObject!=null){
			version=nhCacheObject.getCacheVersion();
		}
		int checkStatus=LoadCacheUtil.checkCacheVersion(realKey, version);
		if(checkStatus==1){
			NhCacheObject remoteNhCacheObject=LoadCacheUtil.queryCacheObject(realKey);
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr=sf.format(new Date());
			remoteNhCacheObject.setCacheTime(dateStr);
			NhCacheHolder.setCacheObject(remoteNhCacheObject);
		}
	}
}
}
