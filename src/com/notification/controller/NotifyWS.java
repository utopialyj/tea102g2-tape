package com.notification.controller;

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

import com.google.gson.Gson;
import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;
import com.notifiaction.jedis.JedisHandleNotification;
import com.notification.model.Notification;

@ServerEndpoint("/NotifyWS/{userName}")
public class NotifyWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(userName, userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();

		sendUnreadCount(userName);



		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}


	public void sendNotify(Integer type, String mem_id1, String mem_id2, String url, String actid, Long time,
			String read) {
		switch (type) {
		case 1:
			Member_InfoService member_InfoSvc = new Member_InfoService();
			Member_InfoVO member_InfoVO = member_InfoSvc.getOneM_Info(mem_id1);
			String user_name = member_InfoVO.getUser_name();
			String message = user_name + "提出了交友申請";

			Notification notification = new Notification(type, mem_id1, mem_id2, url, actid, message, time, read);
			String notificationJson = gson.toJson(notification);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson);

			sendUnreadCount(mem_id2);
			break;

		case 2:
			Member_InfoService member_InfoSvc2 = new Member_InfoService();
			Member_InfoVO member_InfoVO2 = member_InfoSvc2.getOneM_Info(mem_id1);
			String user_name2 = member_InfoVO2.getUser_name();
			String message2 = user_name2 + "接受了你的交友申請";

			Notification notification2 = new Notification(type, mem_id1, mem_id2, url, actid, message2, time, read);
			String notificationJson2 = gson.toJson(notification2);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson2);

			sendUnreadCount(mem_id2);
			break;

		case 3:
			Member_InfoService member_InfoSvc3 = new Member_InfoService();
			Member_InfoVO member_InfoVO3 = member_InfoSvc3.getOneM_Info(mem_id1);
			String user_name3 = member_InfoVO3.getUser_name();
			String message3 = user_name3 + "回覆了你的動態";

			Notification notification3 = new Notification(type, mem_id1, mem_id2, url, actid, message3, time, read);
			String notificationJson3 = gson.toJson(notification3);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson3);

			sendUnreadCount(mem_id2);
			break;
			
		case 4:
			Member_InfoService member_InfoSvc4 = new Member_InfoService();
			Member_InfoVO member_InfoVO4 = member_InfoSvc4.getOneM_Info(mem_id1);
			String user_name4 = member_InfoVO4.getUser_name();
			String message4 = user_name4 + "覺得你的動態讚";
			
			Notification notification4 = new Notification(type, mem_id1, mem_id2, url, actid, message4, time, read);
			String notificationJson4 = gson.toJson(notification4);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson4);
			
			sendUnreadCount(mem_id2);
			break;

		case 5:
			Member_InfoService member_InfoSvc5 = new Member_InfoService();
			Member_InfoVO member_InfoVO5 = member_InfoSvc5.getOneM_Info(mem_id1);
			String user_name5 = member_InfoVO5.getUser_name();
			String message5 = user_name5 + "許可了你的活動申請";

			Notification notification5 = new Notification(type, mem_id1, mem_id2, url, actid, message5, time, read);
			String notificationJson5 = gson.toJson(notification5);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson5);

			sendUnreadCount(mem_id2);
			break;
			
		case 6:
			Member_InfoService member_InfoSvc6 = new Member_InfoService();
			Member_InfoVO member_InfoVO6 = member_InfoSvc6.getOneM_Info(mem_id1);
			String user_name6 = member_InfoVO6.getUser_name();
			String message6 = user_name6 + "申請參加你的活動";
			
			Notification notification6 = new Notification(type, mem_id1, mem_id2, url, actid, message6, time, read);
			String notificationJson6 = gson.toJson(notification6);
			JedisHandleNotification.saveNotification(mem_id2, notificationJson6);
			
			sendUnreadCount(mem_id2);
			break;

		case 8:
			String message8 = "官方公告: " + mem_id2;
			
			Member_InfoService member_InfoSvc8 = new Member_InfoService();
			List<Member_InfoVO> allMember = member_InfoSvc8.getAll();
			
			for (Member_InfoVO oneMember : allMember) {
					
					String memberID = oneMember.getMem_id();
				
					Notification notification8 = new Notification(type, mem_id1, memberID, url, actid, message8, time, read);
					String notificationJson8 = gson.toJson(notification8);
					
					JedisHandleNotification.saveNotification(memberID, notificationJson8);
		
					sendUnreadCount(memberID);
				}
			break;
		default:
			break;
		}

	}
	
	public void sendUnreadCount(String userName) {
		Session userSession = sessionsMap.get(userName);
		int unreadCount = JedisHandleNotification.checkUnread(userName);
		if (unreadCount != 0) {
			String unreadCountString = Integer.toString(unreadCount);
			Notification newNTFCount = new Notification(9, null, userName, null, null,unreadCountString , null, null);
			String newNTFCountJson = gson.toJson(newNTFCount);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(newNTFCountJson);
			}
		}
	}
	

	@OnMessage
	public void onMessage(String notificationJson) {
		Notification notification = gson.fromJson(notificationJson, Notification.class);
		String mem_id = notification.getReceiver();
		Session receiverSession = sessionsMap.get(mem_id);

		if (notification.getType() == 0) {
			List<String> historyData = JedisHandleNotification.getHistoryMsg(notification.getReceiver());
			String historyJson = gson.toJson(historyData);
			Notification historyNTF = new Notification(0, "", "", "", "", historyJson, System.currentTimeMillis(),
					"");
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(historyNTF));
			}
			return;
		}

	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				break;
			}
		}


		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
}
