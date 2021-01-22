package com.tag_member.model;


import java.sql.Timestamp;

public class Tag_MemberVO implements java.io.Serializable{
	private String tag_uid;
	private String tag_memid;
	private String post_code;
	private Timestamp tag_date;
	
	
	public String getTag_uid() {
		return tag_uid;
	}
	public void setTag_uid(String tag_uid) {
		this.tag_uid = tag_uid;
	}
	public String getTag_memid() {
		return tag_memid;
	}
	public void setTag_memid(String tag_memid) {
		this.tag_memid = tag_memid;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public Timestamp getTag_date() {
		return tag_date;
	}
	public void setTag_date(Timestamp tag_date) {
		this.tag_date = tag_date;
	}
	
	
	
	
	
	
	
	
	
}
