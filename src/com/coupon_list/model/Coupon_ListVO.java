package com.coupon_list.model;

import java.sql.Date;

public class Coupon_ListVO {
	private String cpon_num;
	private String cpon_code;
	private Date cpon_num_bdate;
	private String exc_sts;
	private Date cpon_expirydate;
	private String exc_memid;
	
	public Coupon_ListVO() {
	}
	public Coupon_ListVO(String cpon_num, String cpon_code, Date cpon_num_bdate, String exc_sts, Date cpon_expirydate,
			String exc_memid) {
		this.cpon_num = cpon_num;
		this.cpon_code = cpon_code;
		this.cpon_num_bdate = cpon_num_bdate;
		this.exc_sts = exc_sts;
		this.cpon_expirydate = cpon_expirydate;
		this.exc_memid = exc_memid;
		
	}
	
	@Override
	public String toString() {
		return "Coupon_ListVO [cpon_num=" + cpon_num + ", cpon_code=" + cpon_code + ", cpon_num_bdate=" + cpon_num_bdate
				+ ", exc_sts=" + exc_sts + ", cpon_expirydate=" + cpon_expirydate + ", exc_memid=" + exc_memid + "]";
	}
	
	public String getCpon_num() {
		return cpon_num;
	}
	public void setCpon_num(String cpon_num) {
		this.cpon_num = cpon_num;
	}
	public String getCpon_code() {
		return cpon_code;
	}
	public void setCpon_code(String cpon_code) {
		this.cpon_code = cpon_code;
	}
	public Date getCpon_num_bdate() {
		return cpon_num_bdate;
	}
	public void setCpon_num_bdate(Date cpon_num_bdate) {
		this.cpon_num_bdate = cpon_num_bdate;
	}
	public String getExc_sts() {
		return exc_sts;
	}
	public void setExc_sts(String exc_sts) {
		this.exc_sts = exc_sts;
	}
	public Date getCpon_expirydate() {
		return cpon_expirydate;
	}
	public void setCpon_expirydate(Date cpon_expirydate) {
		this.cpon_expirydate = cpon_expirydate;
	}
	public String getExc_memid() {
		return exc_memid;
	}
	public void setExc_memid(String exc_memid) {
		this.exc_memid = exc_memid;
	}
	
}
