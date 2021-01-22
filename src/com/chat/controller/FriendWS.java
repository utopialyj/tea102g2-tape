package com.chat.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.chat.jedis.JedisHandleMessage;
import com.chat.model.ChatMessage;
import com.chat.model.State;
import com.google.gson.Gson;
import com.member_info.model.Member_InfoVO;

@ServerEndpoint(value = "/FriendWS/{mem_id}", configurator = ServletAwareConfig.class)
public class FriendWS {
	private EndpointConfig config;
	Gson gson = new Gson();
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, Session userSession, EndpointConfig config)
			throws IOException {
		this.config = config;

		/* save the new user in the map */
		sessionsMap.put(mem_id, userSession);
		/* send current online users to the new user */
		Set<String> userNames = sessionsMap.keySet();
		State stateMessage = new State("online", mem_id, userNames);
		String stateMessageJson = gson.toJson(stateMessage);

		userSession.getAsyncRemote().sendText(stateMessageJson);  // 1/user

		// get the friends from HttpSession
		HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
		List<Member_InfoVO> friends = (List<Member_InfoVO>) httpSession.getAttribute("friends");

		// for friends list sort by last msg time
		Map<Long, String> friendsMap = new TreeMap<Long, String>();

		for (int i = 0; i < friends.size(); i++) {
			String fmemid = friends.get(i).getMem_id();
			
			// send current online users to online friends
			if (sessionsMap.containsKey(fmemid)) {
				sessionsMap.get(fmemid).getAsyncRemote().sendText(stateMessageJson); // 1/friend
			}

			// get last message with each friend
			String lastMsgJson = JedisHandleMessage.getLastMsg(mem_id, fmemid);
			if (lastMsgJson != null) {
				ChatMessage lastMsg = gson.fromJson(lastMsgJson, ChatMessage.class);

				friendsMap.put(lastMsg.getTime(), fmemid);

				// check if there are new messages
				if (chkNewMsg(mem_id, lastMsg)) {
					State newMsg = new State("new", fmemid, userNames);
					String newMsgJson = gson.toJson(newMsg);

					userSession.getAsyncRemote().sendText(newMsgJson);  // 0+/user per new msg friend
				}
			}
		}

		Collection<String> friendsSorted = friendsMap.values();

		sortFriendList(mem_id, userSession, friendsSorted);  // 1/user

		String text = String.format("Session ID = %s, chat connected; mem_id = %s%nusers: %s", userSession.getId(), mem_id,
				userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		
		// for online status...
//		if ("online".equals(chatMessage.getType()) || "busy".equals(chatMessage.getType())
//				|| "offline".equals(chatMessage.getType())) {
//			State state = gson.fromJson(message, State.class);
//			List<String> list = (List<String>) state.getUsers();
//			if ("offline".equals(state.getType())) {
//				Set<String> online = new HashSet<String>();
//				for (String key : sessionsMap.keySet()) {
//					online.add(key);
//				}
//				online.remove(state.getUser());
//				state.setUsers(online);
//			} else {
//				state.setUsers(sessionsMap.keySet());
//			}
//			userSession.getAsyncRemote().sendText(gson.toJson(state));
//			for (String friend : list) {
//				if (sessionsMap.containsKey(friend)) {
//					sessionsMap.get(friend).getAsyncRemote().sendText(gson.toJson(state));
//				}
//			}
//			return;
//		}

		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		Long time = chatMessage.getTime();
		String read = chatMessage.getRead();

		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg, time, read);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));

//				try {
//					userSession.getBasicRemote().sendText(gson.toJson(cmHistory));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

//				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}

		if ("read".equals(chatMessage.getType())) {
			JedisHandleMessage.getHistoryMsg(sender, receiver);
			return;
		}

		JedisHandleMessage.saveChatMessage(sender, receiver, message);
		try {
			userSession.getBasicRemote().sendText(message); // 1/user
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collection<String> friend = new HashSet<String>();
		friend.add(receiver);
		sortFriendList(sender, userSession, friend); // 1/user

		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message); // 1/friend

			Collection<String> sortReceiverFriend = new HashSet<String>();
			sortReceiverFriend.add(sender);
			sortFriendList(receiver, receiverSession, sortReceiverFriend); // 1/friend
		}
//		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String mem_id : userNames) {
			if (sessionsMap.get(mem_id).equals(userSession)) {
				userNameClose = mem_id;
				sessionsMap.remove(mem_id);
				break;
			}
		}
		if (userNameClose != null) {
			State stateMessage = new State("offline", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				synchronized (session) {
					session.getAsyncRemote().sendText(stateMessageJson);
				}
			}
		}
		String text = String.format("session ID = %s, chat disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}

	private boolean chkNewMsg(String sender, ChatMessage lastMsg) {
		if (lastMsg.getSender().equals(sender)) {
			return false;
		} else {
			if (lastMsg.getRead().equals("yes")) {
				return false;
			} else {
				return true;
			}
		}
	}

	private void sortFriendList(String mem_id, Session userSession, Collection<String> friends) {
		State sort = new State("sort", mem_id, friends);
		String sortJson = gson.toJson(sort);
		try {
			Thread.sleep(500);
			try {
				userSession.getBasicRemote().sendText(sortJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}