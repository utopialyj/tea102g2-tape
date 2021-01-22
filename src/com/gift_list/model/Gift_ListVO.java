package com.gift_list.model;

import java.sql.Date;

public class Gift_ListVO {
	private String gift_code;
	private String send_memid;
	private String receive_memid;
	private Date gift_date;
	private String gift_cpon_num;
	
	public Gift_ListVO() {
	}
	public Gift_ListVO(String gift_code, String send_memid, String receive_memid, Date gift_date, String gift_cpon_num) {
		this.gift_code = gift_code;
		this.send_memid = send_memid;
		this.receive_memid = receive_memid;
		this.gift_date = gift_date;
		this.gift_cpon_num = gift_cpon_num;
	}
	
	@Override
	public String toString() {
		return "Gift_ListVO [gift_code=" + gift_code + ", send_memid=" + send_memid + ", receive_memid=" + receive_memid
				+ ", gift_date=" + gift_date + ", gift_cpon_num=" + gift_cpon_num + "]";
	}
	
	public String getGift_code() {
		return gift_code;
	}
	public void setGift_code(String gift_code) {
		this.gift_code = gift_code;
	}
	public String getSend_memid() {
		return send_memid;
	}
	public void setSend_memid(String send_memid) {
		this.send_memid = send_memid;
	}
	public String getReceive_memid() {
		return receive_memid;
	}
	public void setReceive_memid(String receive_memid) {
		this.receive_memid = receive_memid;
	}
	public Date getGift_date() {
		return gift_date;
	}
	public void setGift_date(Date gift_date) {
		this.gift_date = gift_date;
	}
	public String getGift_cpon_num() {
		return gift_cpon_num;
	}
	public void setGift_cpon_num(String gift_cpon_num) {
		this.gift_cpon_num = gift_cpon_num;
	}
	
}
