package com.like_comment.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Like_CommentVO implements java.io.Serializable {
	private String cmnt_lk_uid;
	private String cmnt_lk_memid;
	private String cmnt_code;
	private Timestamp cmnt_lk_date;
	
	
	
	public String getCmnt_lk_uid() {
		return cmnt_lk_uid;
	}
	public void setCmnt_lk_uid(String cmnt_lk_uid) {
		this.cmnt_lk_uid = cmnt_lk_uid;
	}
	public String getCmnt_lk_memid() {
		return cmnt_lk_memid;
	}
	public void setCmnt_lk_memid(String cmnt_lk_memid) {
		this.cmnt_lk_memid = cmnt_lk_memid;
	}
	public String getCmnt_code() {
		return cmnt_code;
	}
	public void setCmnt_code(String cmnt_code) {
		this.cmnt_code = cmnt_code;
	}
	public Timestamp getCmnt_lk_date() {
		return cmnt_lk_date;
	}
	public void setCmnt_lk_date(Timestamp cmnt_lk_date) {
		this.cmnt_lk_date = cmnt_lk_date;
	}
	
	
	
	
	
	
}
