package com.chat.jedis;

import java.util.ArrayList;
import java.util.List;

import com.chat.model.ChatMessage;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	static Gson gson = new Gson();
	
	private static JedisPool pool = util.JedisPoolUtil.getJedisPool();
	
	public static String getLastMsg(String sender, String receiver) {
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		if (jedis.llen(senderKey) == 0 ) {
			return null;
		}
		String lastMsgJson = jedis.lindex(senderKey, -1);
		jedis.close();
		
		return lastMsgJson;
	}

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(senderKey, 0, -1);
		List<String> readData = new ArrayList<String>();
		int unreadCount = 0;
		for (int i = 0; i < historyData.size(); i++){
			ChatMessage historyMsg = gson.fromJson(historyData.get(i), ChatMessage.class);
			if (historyMsg.getRead().equals("no")) {
				historyMsg.setRead("yes");
				unreadCount++;
				String readMsg = gson.toJson(historyMsg);
				readData.add(readMsg);
			}
		}
		for (int i = 0; i < readData.size(); i++) {
			jedis.lset(senderKey, historyData.size()-unreadCount , readData.get(i));
			unreadCount--;
		}
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}

}
