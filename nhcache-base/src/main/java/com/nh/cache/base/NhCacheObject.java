package com.nh.cache.base;

import java.util.Map;


/**
 * 
 * @author ninghao
 *
 */
public class NhCacheObject {
public String cacheKey;
public String cacheVersion;
public String cacheTime;
public String cacheType;
public String cacheSource;
public String cacheRemark;
public String cacheExt1;
public String cacheExt2;
public String cacheExt3;
public String cacheData;
public Map cacheMap;

public String getCacheData() {
	return cacheData;
}
public void setCacheData(String cacheData) {
	this.cacheData = cacheData;
}
public String getCacheRemark() {
	return cacheRemark;
}
public void setCacheRemark(String cacheRemark) {
	this.cacheRemark = cacheRemark;
}
public String getCacheExt1() {
	return cacheExt1;
}
public void setCacheExt1(String cacheExt1) {
	this.cacheExt1 = cacheExt1;
}
public String getCacheExt2() {
	return cacheExt2;
}
public void setCacheExt2(String cacheExt2) {
	this.cacheExt2 = cacheExt2;
}
public String getCacheExt3() {
	return cacheExt3;
}
public void setCacheExt3(String cacheExt3) {
	this.cacheExt3 = cacheExt3;
}

public String getCacheType() {
	return cacheType;
}
public void setCacheType(String cacheType) {
	this.cacheType = cacheType;
}
public String getCacheSource() {
	return cacheSource;
}
public void setCacheSource(String cacheSource) {
	this.cacheSource = cacheSource;
}
public String getCacheKey() {
	return cacheKey;
}
public void setCacheKey(String cahceKey) {
	this.cacheKey = cahceKey;
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
