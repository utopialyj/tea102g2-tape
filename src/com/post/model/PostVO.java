package com.post.model;

import java.sql.Clob;
import java.sql.Timestamp;

public class PostVO implements java.io.Serializable{
	private String post_uid;
	private String post_memid;
	private Integer post_sts;
	private Integer post_public_lv;
	private Timestamp edit_date;
	private String post_context;
	private String post_location;
	private Integer lk_num;
	
	
	public String getPost_uid() {
		return post_uid;
	}
	public void setPost_uid(String post_uid) {
		this.post_uid = post_uid;
	}
	public String getPost_memid() {
		return post_memid;
	}
	public void setPost_memid(String post_memid) {
		this.post_memid = post_memid;
	}
	public Integer getPost_sts() {
		return post_sts;
	}
	public void setPost_sts(Integer post_sts) {
		this.post_sts = post_sts;
	}
	public Integer getPost_public_lv() {
		return post_public_lv;
	}
	public void setPost_public_lv(Integer post_public_lv) {
		this.post_public_lv = post_public_lv;
	}
	public Timestamp getEdit_date() {
		return edit_date;
	}
	public void setEdit_date(Timestamp edit_date) {
		this.edit_date = edit_date;
	}
	public String getPost_context() {
		return post_context;
	}
	public void setPost_context(String post_context) {
		this.post_context = post_context;
	}
	public String getPost_location() {
		return post_location;
	}
	public void setPost_location(String post_location) {
		this.post_location = post_location;
	}
	public Integer getLk_num() {
		return lk_num;
	}
	public void setLk_num(Integer lk_num) {
		this.lk_num = lk_num;
	}
	
	
	

	
	


	
}
