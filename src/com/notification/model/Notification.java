package com.notification.model;

import java.io.Serializable;

public class Notification implements Serializable{
	private Integer type;
	private String sender;
	private String receiver;
	private String url;
	private String actid;
	private String message;
	private Long time;
	private String read;
	
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public Notification(Integer type, String sender, String receiver, String url, String actid, String message,
			Long time,String read) {
		super();
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.url = url;
		this.actid = actid;
		this.message = message;
		this.time = time;
		this.read=read;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
}
