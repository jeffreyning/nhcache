package com.nh.cache.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.nh.cache.base.NhCacheConst;
import com.nh.cache.base.NhCacheHolderFactory;
import com.nh.cache.base.NhCacheObject;

/**
 * 
 * @author ninghao
 * 
 */
public class LoadCacheTimer {
	public String sysId = "";
	public String prefix = "";
	public String holderName = "default";
	public String tableName="";

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean checkFlag = false;

	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

	public LoadCacheUtil4Db loadCacheUtil4Db;

	public LoadCacheUtil4Db getLoadCacheUtil4Db() {
		return loadCacheUtil4Db;
	}

	public void setLoadCacheUtil4Db(LoadCacheUtil4Db loadCacheUtil4Db) {
		this.loadCacheUtil4Db = loadCacheUtil4Db;
	}

	public void doJob() throws Exception {
		Set<String> keySet = loadCacheUtil4Db.listCacheKeys(tableName,prefix);
		if (keySet == null) {
			return;
		}
		for (String key : keySet) {
			// String realKey=key.replaceFirst(NhCacheConst.CACHE_PREFIX, "");
			String realKey = key;
			if (checkFlag == true) {
				String version = null;
				NhCacheObject nhCacheObject = NhCacheHolderFactory.getHolder(
						holderName).getCacheObject(realKey);
				if (nhCacheObject != null) {
					version = nhCacheObject.getCacheVersion();
				}
				int checkStatus = loadCacheUtil4Db.checkCacheVersion(tableName,realKey,
						version);
				if (checkStatus != 1) {
					continue;
				}
			}

			NhCacheObject remoteNhCacheObject = loadCacheUtil4Db
					.queryCacheObject(tableName, key, key);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sf.format(new Date());
			remoteNhCacheObject.setCacheTime(dateStr);
			NhCacheHolderFactory.getHolder(holderName).setCacheObject(
					remoteNhCacheObject);

		}
	}

}

