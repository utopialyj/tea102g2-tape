package com.post_comment.model;


import java.sql.Timestamp;

public class Post_CommentVO implements java.io.Serializable{
	private String cmnt_uid;
	private String cmnt_memid;
	private Timestamp cmnt_date;
	private String cmnt_context;
	private Integer lk_count;
	private String post_code;
	private Integer cmnt_sts;
	
	
	public String getCmnt_uid() {
		return cmnt_uid;
	}
	public void setCmnt_uid(String cmnt_uid) {
		this.cmnt_uid = cmnt_uid;
	}
	public String getCmnt_memid() {
		return cmnt_memid;
	}
	public void setCmnt_memid(String cmnt_memid) {
		this.cmnt_memid = cmnt_memid;
	}
	public Timestamp getCmnt_date() {
		return cmnt_date;
	}
	public void setCmnt_date(Timestamp cmnt_date) {
		this.cmnt_date = cmnt_date;
	}
	public String getCmnt_context() {
		return cmnt_context;
	}
	public void setCmnt_context(String cmnt_context) {
		this.cmnt_context = cmnt_context;
	}
	public Integer getLk_count() {
		return lk_count;
	}
	public void setLk_count(Integer lk_count) {
		this.lk_count = lk_count;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public Integer getCmnt_sts() {
		return cmnt_sts;
	}
	public void setCmnt_sts(Integer cmnt_sts) {
		this.cmnt_sts = cmnt_sts;
	}
	
	
	
	
	
	
}
