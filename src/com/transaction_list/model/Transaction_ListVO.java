package com.transaction_list.model;

import java.io.Serializable;
import java.sql.Date;

public class Transaction_ListVO implements Serializable {
	private String trans_code;
	private String trans_type;
	private Integer trans_ant;
	private Date trans_date;
	private String trans_memid;
	
	public Transaction_ListVO() {
	}
	public Transaction_ListVO(String trans_code, String trans_type, Integer trans_ant, Date trans_date,
			String trans_memid) {
		this.trans_code = trans_code;
		this.trans_type = trans_type;
		this.trans_ant = trans_ant;
		this.trans_date = trans_date;
		this.trans_memid = trans_memid;
	}
	
	@Override
	public String toString() {
		return "Transaction_ListVO [trans_code=" + trans_code + ", trans_type=" + trans_type + ", trans_ant="
				+ trans_ant + ", trans_date=" + trans_date + ", trans_memid=" + trans_memid + "]";
	}
	
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public Integer getTrans_ant() {
		return trans_ant;
	}
	public void setTrans_ant(Integer trans_ant) {
		this.trans_ant = trans_ant;
	}
	public Date getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}
	public String getTrans_memid() {
		return trans_memid;
	}
	public void setTrans_memid(String trans_memid) {
		this.trans_memid = trans_memid;
	}
	
}
