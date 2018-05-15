package com.nh.cache.db;



import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nh.cache.base.NhCacheConst;
import com.nh.cache.base.NhCacheObject;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author ninghao
 * 
 */
public class LoadCacheUtil4Db {
	public static JdbcTemplate jdbcTemplate=null;
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LoadCacheUtil4Db.jdbcTemplate = jdbcTemplate;
	}
	public static Map<String,CacheSqlHolder> sqlHolderMap=new HashMap<String,CacheSqlHolder>();
	


	public static Map<String,CacheSqlHolder> getSqlHolderMap() {
		return sqlHolderMap;
	}

	public void setSqlHolderMap(Map<String,CacheSqlHolder> sqlHolderMap) {
		LoadCacheUtil4Db.sqlHolderMap = sqlHolderMap;
	}

	public void setSqlHolder4List(List<CacheSqlHolder> sqlHolderList){
		for(CacheSqlHolder cacheSqlHolder:sqlHolderList){
			String tableName=cacheSqlHolder.getTableName();
			sqlHolderMap.put(tableName, cacheSqlHolder);
		}
	}
	public static NhCacheObject queryCacheObject(String tableName,String key,String realKey){
		if(realKey==null || "".equals(realKey)){
			realKey=key;
		}
		CacheSqlHolder cacheSqlHolder=sqlHolderMap.get(tableName);
		String queryCacheObjectSql=cacheSqlHolder.getQueryCacheObjectSql();
		Object[] params=new Object[]{realKey};
		Map<String,Object> cacheMap= jdbcTemplate.queryForMap(queryCacheObjectSql,params);
		String nhCacheVersion=(String) cacheMap.get(NhCacheConst.CACHE_VERSION);
		NhCacheObject nhCacheObject=new NhCacheObject();
		nhCacheObject.setCacheVersion(nhCacheVersion);
		nhCacheObject.setCacheKey(key);
		nhCacheObject.cacheMap=cacheMap;
		
		String nhCacheType=(String) cacheMap.get(NhCacheConst.CACHE_TYPE);
		nhCacheObject.setCacheType(nhCacheType);
		String nhCacheData=(String) cacheMap.get(NhCacheConst.CACHE_DATA);
		nhCacheObject.setCacheData(nhCacheData);
		
		nhCacheObject.setCacheSource("db");
		
		return nhCacheObject;
	}

	public static Set<String> listCacheKeys(String tableName,String prefix){
		if(prefix==null){
			prefix="";
		}
		String findKey=null;
		CacheSqlHolder cacheSqlHolder=sqlHolderMap.get(tableName);
		String listCacheKeysSql=cacheSqlHolder.getListCacheKeysSql();
		Object[] params=new Object[]{prefix};
		
		List<Map<String,Object>> retList= jdbcTemplate.queryForList(listCacheKeysSql,params);
		Set<String> keys=new HashSet<String>();
		for(Map<String,Object> rowMap:retList){
			String keyName=(String) rowMap.get("key_name");
			keys.add(keyName);
		}
		return keys;
	}
	
	public int checkCacheVersion(String tableName,String key,String version){
		if(version==null){
			version="";
		}
		String realKey=key;
		NhCacheObject nObj=queryCacheObject(tableName,realKey, realKey);

		if(nObj==null){
			return -2;
		}
		String remoteVersion=nObj.getCacheVersion();
		if(remoteVersion==null || remoteVersion.equals("")){
			return -1;
		}
		if(version.equals(remoteVersion)){
			return 0;
		}
		return 1;
	}		
}
