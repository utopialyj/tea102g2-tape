package com.chat.model;

public class ActsChatMessage {
	private String type;
	private String activity;
	private String sender;
	private String message;
	private String time;

	public ActsChatMessage(String type, String activity, String sender, String message, String time) {
		this.type = type;
		this.activity = activity;
		this.sender = sender;
		this.message = message;
		this.time = time;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
