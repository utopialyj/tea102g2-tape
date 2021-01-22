package com.chat.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.chat.jedis.ActsJedisHandleMessage;
import com.chat.model.ActsChatMessage;
import com.google.gson.Gson;

@ServerEndpoint(value="/ActsWS/{mem_id}")
public class ActsWS {
	Gson gson = new Gson();
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(mem_id, userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();
		
		String text = String.format("Session ID = %s, actChat connected; mem_id = %s%nusers: %s", userSession.getId(), mem_id, userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ActsChatMessage chatMessage = gson.fromJson(message, ActsChatMessage.class);
		String activity = chatMessage.getActivity();
		String sender = chatMessage.getSender();
		String time = chatMessage.getTime();

		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = ActsJedisHandleMessage.getHistoryMsg(activity);
			String historyMsg = gson.toJson(historyData);
			ActsChatMessage cmHistory = new ActsChatMessage("history", activity, sender, historyMsg, time);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
//				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		
		Collection<Session> sessions = sessionsMap.values();
		for(Session session : sessions) {
			session.getAsyncRemote().sendText(message);
		}
		
//		userSession.getAsyncRemote().sendText(message);
		ActsJedisHandleMessage.saveChatMessage(activity, message);
//		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		for (String mem_id : userNames) {
			if (sessionsMap.get(mem_id).equals(userSession)) {
				sessionsMap.remove(mem_id);
				break;
			}
		}
		String text = String.format("session ID = %s, actChat disconnected; close code = %d%nusers: %s", userSession.getId(), reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
	
	
}