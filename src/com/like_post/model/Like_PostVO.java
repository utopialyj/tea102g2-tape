package com.like_post.model;

import java.sql.Timestamp;

public class Like_PostVO implements java.io.Serializable{
	private String lk_uid;
	private String lk_memid;
	private String post_code;
	private Timestamp lk_date;
	
	
	
	public String getLk_uid() {
		return lk_uid;
	}
	public void setLk_uid(String lk_uid) {
		this.lk_uid = lk_uid;
	}
	public String getLk_memid() {
		return lk_memid;
	}
	public void setLk_memid(String lk_memid) {
		this.lk_memid = lk_memid;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public Timestamp getLk_date() {
		return lk_date;
	}
	public void setLk_date(Timestamp lk_date) {
		this.lk_date = lk_date;
	}
	
	
	
	
	
}
