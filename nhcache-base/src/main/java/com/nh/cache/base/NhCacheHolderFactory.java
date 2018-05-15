package com.nh.cache.base;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author ninghao
 *
 */
public class NhCacheHolderFactory {
public static Map holderMap=new HashMap();
static{
	holderMap.put("default", new NhMemCacheHolder());
}
public static Map getHolderMap() {
	return holderMap;
}
public void setHolderMap(Map holderMap) {
	NhCacheHolderFactory.holderMap = holderMap;
}
public static INhCacheHolder getHolder(String holderName){
	if(holderName==null|| "".equals(holderName)){
		return (INhCacheHolder) holderMap.get("default");
	}else{
		return (INhCacheHolder) holderMap.get(holderName);
	}

}
}