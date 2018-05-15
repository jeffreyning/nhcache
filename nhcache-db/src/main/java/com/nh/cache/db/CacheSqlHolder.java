package com.nh.cache.db;

/**
 * 
 * @author ninghao
 * 
 */
public class CacheSqlHolder {
public String tableName="";
public String getTableName() {
	return tableName;
}
public void setTableName(String tableName) {
	this.tableName = tableName;
}
public String queryCacheObjectSql="";
public String listCacheKeysSql="";
public String checkCacheVersionSql="";
public String getQueryCacheObjectSql() {
	return queryCacheObjectSql;
}
public void setQueryCacheObjectSql(String queryCacheObjectSql) {
	this.queryCacheObjectSql = queryCacheObjectSql;
}
public String getListCacheKeysSql() {
	return listCacheKeysSql;
}
public void setListCacheKeysSql(String listCacheKeysSql) {
	this.listCacheKeysSql = listCacheKeysSql;
}
public String getCheckCacheVersionSql() {
	return checkCacheVersionSql;
}
public void setCheckCacheVersionSql(String checkCacheVersionSql) {
	this.checkCacheVersionSql = checkCacheVersionSql;
}
}
