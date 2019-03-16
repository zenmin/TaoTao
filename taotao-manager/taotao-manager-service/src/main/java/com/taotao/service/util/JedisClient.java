package com.taotao.service.util;


/**
* @Title JedisClient
* @Description 本类主要功能是单机版和集群版的接口
* @Company null
* @author 曾敏
* @date 2017年9月19日下午5:16:31
*/
public interface JedisClient {

	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
}
