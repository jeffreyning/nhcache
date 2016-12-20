package com.nh.cache.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	
	public static final int MIN = 60;
	
	public static final int DAY = 86400;
	
	public static final int WEEK = 604800;
	
	public static final int MONTH = 30 * 24 * 60 * 60;
	
	public static final int HOUR = 3600;
	
	public static void closeConn(Jedis jedis) {
		if (jedis != null) {
			RedisPool.returnJedis(jedis);
		}
	}

	public static Long expire(String key){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.expire(key, RedisPool.exTime);
			return result;
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}
	

	public static String setEx(String key,String value,int seconds){
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.setex(key,seconds,value);
			return result;
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
		} finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}

	public static String set(String key,String str){
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.setex(key,RedisPool.exTime,str);
			return result;
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
		} finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}
	
	public static  void sets(String key,String str){
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedis();
			jedis.set(key, str);
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
			logger.info("set error");
		} finally{
			RedisPool.returnJedis(jedis);
		}
	}
	
	public static  String get(String key){
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.get(key);
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
			logger.info("get value error! check the key, key="+key);
		}finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}
	
	public static Long del(String key){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.del(key);
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
			logger.info("can not del a key, check exsits first");
		}finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}
	
	public static  boolean exists(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();;
			return j.exists(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return false;
	}

	public static void setex(String key, String value, int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.setex(key, seconds, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void hset(String key, String field, String value) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.hset(key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}

	public static void hset(String key, String field, String value, int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.hset(key, field, value);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static String hget(String key, String field) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.hget(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("hget error");
		} finally {
			closeConn(j);
		}
	}
	
	public static long hdel(String key, String... fields) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.hdel(key, fields);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("hdel error");
		} finally {
			closeConn(j);
		}
	}
	
	public static boolean hexists(String key, String field) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.hexists(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("hexsts error");
		} finally {
			closeConn(j);
		}
	}
	

	public static List<String> get(String... key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			List<String> list = new ArrayList<String>();
			for (String k : key) {
				String v = j.get(k);
				if (v!=null&& !v.equals(""))
					list.add(v);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}

	public static void lpush(String key, int seconds, String... values) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.lpush(key, values);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void rpush(String key, int seconds, String... values) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.rpush(key, values);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void rpush(String key, String... values) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.rpush(key, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}

	public static List<String> lrange(String key, long start, long end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			List<String> list = j.lrange(key, start, end);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static long llen(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.llen(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return -1;
	}
	
	public static String lpop(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.lpop(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}

	public static List<String> lpop(String key, int size) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			List<String> list = null;
			for (int i = 0; i < size; i++) {
				String str = j.lpop(key);
				if (list == null)
					list = new ArrayList<String>(size);
				list.add(str);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long counterIncr(String key, int expire) {
		Jedis j = null;
		try {
			Long count = null;
			j = RedisPool.getJedis();
			count = j.incr(key);
			if (count == 1) {
				j.expire(key, expire);
			}
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long counterIncrBy(String key, Long value, int expire) {
		Jedis j = null;
		try {
			Long count = null;
			j = RedisPool.getJedis();
			count = j.incrBy(key, value);
			if (count == 1) {
				j.expire(key, expire);
			}
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long getTTL(String key) {
		Jedis j = null;
		try {
			Long expireSeconds = null;
			j = RedisPool.getJedis();
			expireSeconds = j.ttl(key);
			return expireSeconds;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}

	public static Long lpush(String key, String... values) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.lpush(key, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long hincrby(String key,String field,long increment){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.hincrBy(key, field, increment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("hincrby出错");
		} finally {
			closeConn(j);
		}
	}
	
	public static Map<String,String> hgetAndRemove(String key){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			Map<String,String> result = j.hgetAll(key);
			j.del(key);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException("hgetAndRemove出错");
		} finally {
			closeConn(j);
		}
	}
	
	public static void zadd(String key, double score,String member,int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.zadd(key, score, member);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void zadd(String key, double score,String member) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.zadd(key, score, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static long zcard(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zcard(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return 0;
	}
	
	public static Double zscore(String key,String member) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zscore(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return 0.0;
	}
	
	public static void zadd(String key, Map<String,Double> scoreMembers,int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.zadd(key, scoreMembers);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static Set<String> zrange(String key, int start,int end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static ScanResult<Tuple> zscan(String key,String cursor) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zscan(key, cursor);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Collection<Tuple> zrangeWithScores(String key, int start,int end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Set<String> zrevrangeByScore(String key, int max,int min) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Set<String> zrevrangeByScore(String key, String max,String min,int offset,int count) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Collection<Tuple> zrevrangeByScoreWithScores(String key, Double max, Double min) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long incr(String key,int expire){
		Jedis j = null;
		long count;
		try {
			j = RedisPool.getJedis();
			count = j.incr(key);
			if(expire != 0){
				j.expire(key, expire);
			}
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long decr(String key){
		Jedis j = null;
		long count;
		try {
			j = RedisPool.getJedis();
			count = j.decr(key);
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Map<String,String> hgetAll(String key){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.hgetAll(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("hgetAll出错");
		} finally {
			closeConn(j);
		}
	}
	
	
	public static void ltrim(String key, long start, long end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.ltrim(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void set(String key, String value,int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.set(key, value);
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void zincrby(String key,double score,String member){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.zincrby(key, score,member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static Set<Tuple> zrevrangeWithScores(String key,int start,int end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrangeWithScores(key, start, end);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static void zrem(String key,String... members) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.zrem(key, members);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static Set<String> zrevrange(String key,int start,int end) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static boolean sIsmember(String key,String value) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.sismember(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return false;
	}
	
	public static Long scard(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.scard(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return 0l;
	}
	
	public static Set<String> sMembers(String key) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.smembers(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static void sadd(String key,String value,int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.sadd(key, value);
			if(seconds != 0){
				j.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void sadd(String key,int seconds ,String... value) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.sadd(key, value);
			if(seconds != 0){
				j.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void sadd(String key ,String... value) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.sadd(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static ScanResult<String> sscan(String key ,String cursor) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.sscan(key, cursor);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static void expire(String key,int seconds) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static void srem(String key,String... members) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			j.srem(key, members);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
	}
	
	public static String srandmember(String key){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.srandmember(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static Long zrank(String key,String member) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrank(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	} 
	
	public static Long zrevrank(String key,String member) {
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			return j.zrevrank(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static String brpop(String key,int timeout){
		Jedis j = null;
		try {
			j = RedisPool.getJedis();
			List<String> retList= j.brpop(timeout, key);
			if(retList!=null){
				String temp=retList.get(1);
				return temp;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeConn(j);
		}
		return null;
	}
	
	public static  Set<String> getKeys(String patten){
		Jedis jedis = null;
		Set<String> result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.keys(patten);
		} catch (JedisConnectionException  e) {
			RedisPool.returnBrokenResource(jedis);
			logger.info("get value error! check the patten, patten="+patten);
		}finally{
			RedisPool.returnJedis(jedis);
		}
		return result;
	}
}

