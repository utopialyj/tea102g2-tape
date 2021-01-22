package com.member_authroity.model;

import java.sql.Date;

public class Member_AuthroityVO implements java.io.Serializable{
	private Integer verify_level;
	private Integer chat_auth;
	private Integer post_auth;
	private Integer meat_auth;
	private Integer point_auth;
	private Integer join_event_auth;
	private Integer host_auth;
	private Integer log_auth;
	
	public Integer getVerify_level() {
		return verify_level;
	}
	public void setVerify_level(Integer verify_level) {
		this.verify_level = verify_level;
	}
	public Integer getChat_auth() {
		return chat_auth;
	}
	public void setChat_auth(Integer chat_auth) {
		this.chat_auth = chat_auth;
	}
	public Integer getPost_auth() {
		return post_auth;
	}
	public void setPost_auth(Integer post_auth) {
		this.post_auth = post_auth;
	}
	public Integer getMeat_auth() {
		return meat_auth;
	}
	public void setMeat_auth(Integer meat_auth) {
		this.meat_auth = meat_auth;
	}
	public Integer getPoint_auth() {
		return point_auth;
	}
	public void setPoint_auth(Integer point_auth) {
		this.point_auth = point_auth;
	}
	public Integer getJoin_event_auth() {
		return join_event_auth;
	}
	public void setJoin_event_auth(Integer join_event_auth) {
		this.join_event_auth = join_event_auth;
	}
	public Integer getHost_auth() {
		return host_auth;
	}
	public void setHost_auth(Integer host_auth) {
		this.host_auth = host_auth;
	}
	public Integer getLog_auth() {
		return log_auth;
	}
	public void setLog_auth(Integer log_auth) {
		this.log_auth = log_auth;
	}



}
