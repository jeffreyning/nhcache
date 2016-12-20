package com.nh.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool {
	public static int exTime = 6000;
	public static JedisPool jedisPool;
	
	
	public static JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		RedisPool.jedisPool = jedisPool;
	}

	public static Jedis getJedis() {
		return getPool().getResource();
	}
	
	public static void returnJedis(Jedis jedis) {
        if (jedis != null){
        	getPool().returnResourceObject(jedis);
        }
    }
	
	public static JedisPool getPool() {
		return jedisPool;
	}

	public static void returnBrokenResource(Jedis jedis) {
		if (jedis != null){
			getPool().returnBrokenResource(jedis);
        }
	}
	

}

