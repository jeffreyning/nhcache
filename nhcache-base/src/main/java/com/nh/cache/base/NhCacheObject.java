package com.nh.cache.base;

import java.util.Map;


/**
 * 
 * @author ninghao
 *
 */
public class NhCacheObject {
public String cahceKey;
public String cacheVersion;
public String cacheTime;
public Map cacheMap;
public String getCahceKey() {
	return cahceKey;
}
public void setCahceKey(String cahceKey) {
	this.cahceKey = cahceKey;
}
public String getCacheVersion() {
	return cacheVersion;
}
public void setCacheVersion(String cacheVersion) {
	this.cacheVersion = cacheVersion;
}
public String getCacheTime() {
	return cacheTime;
}
public void setCacheTime(String cacheTime) {
	this.cacheTime = cacheTime;
}
public Map getCacheMap() {
	return cacheMap;
}
public void setCacheMap(Map cacheMap) {
	this.cacheMap = cacheMap;
}
}
