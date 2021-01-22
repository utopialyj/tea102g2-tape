package com.notifiaction.jedis;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.notification.model.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleNotification {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)
	static Gson gson = new Gson();
	private static JedisPool pool = util.JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String receiver) {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		//列出指定範圍內的元素，0為第一個,-1為最後一個
		List<String> historyData = jedis.lrange(receiver, 0, -1);
		List<String> readData = new ArrayList<String>();
		int unreadCount = 0;
		//跑迴圈將每個元素run過一遍
		for (int i = 0; i < historyData.size(); i++){
			//將原本是json形式的元素轉成gson以利java運作
			Notification historyMsg = gson.fromJson(historyData.get(i), Notification .class);

			if (historyMsg.getRead().equals("no")) {

				historyMsg.setRead("yes");
			
				unreadCount++;

				String readMsg = gson.toJson(historyMsg);

				readData.add(readMsg);
			}
		}
		for (int i = 0; i < readData.size(); i++) {

			jedis.lset(receiver, historyData.size()-unreadCount , readData.get(i));
		
			unreadCount--;
		}
		jedis.close();
		return historyData;
	}

	public static void saveNotification(String mem_id2, String notificationJSON) {
		// 對雙方來說，都要各存著歷史聊天記錄
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(mem_id2, notificationJSON);

		jedis.close();
	}
	
	public static int checkUnread(String mem_id) {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		//列出指定範圍內的元素，0為第一個,-1為最後一個
		List<String> historyData = jedis.lrange(mem_id, 0, -1);
		int unreadCount = 0;
		//跑迴圈將每個元素run過一遍
		for (int i = 0; i < historyData.size(); i++){
			Notification historyMsg = gson.fromJson(historyData.get(i), Notification .class);
			if (historyMsg.getRead().equals("no")) {
				unreadCount++;
			}
		}
		jedis.close();
		return unreadCount;
	}

}
