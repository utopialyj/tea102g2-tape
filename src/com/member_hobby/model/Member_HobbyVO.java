package com.member_hobby.model;

public class Member_HobbyVO implements java.io.Serializable{

	private String mem_hob_uid;
	private String hob_memid;
	private String hob_code;
	public String getMem_hob_uid() {
		return mem_hob_uid;
	}
	public void setMem_hob_uid(String mem_hob_uid) {
		this.mem_hob_uid = mem_hob_uid;
	}
	public String getHob_memid() {
		return hob_memid;
	}
	public void setHob_memid(String hob_memid) {
		this.hob_memid = hob_memid;
	}
	public String getHob_code() {
		return hob_code;
	}
	public void setHob_code(String hob_code) {
		this.hob_code = hob_code;
	}
	
	@Override
	public String toString() {
		return "Member_HobbyVO [hob_memid=" + hob_memid + ", hob_code=" + hob_code + "]";
	}
	
	
	
}
