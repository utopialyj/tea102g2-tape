package com.chat.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ActsJedisHandleMessage {

	private static JedisPool pool = util.JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String activity) {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(activity, 0, -1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String activity, String message) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(activity, message);

		jedis.close();
	}

}
