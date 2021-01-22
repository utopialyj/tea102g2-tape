package com.deposit_list.model;

import java.io.Serializable;
import java.sql.Date;

public class Deposit_ListVO implements Serializable {
	private String deposit_code;
	private Integer deposit_num;
	private Date deposit_date;
	private String deposit_memid;
	private String pay_type;
	private String credit_num;
	private Integer credit_expiry_yy;
	private Integer credit_expiry_mm;
	private Integer credit_security_num;
	private String atm_id;
	private String payment_sts;
	
	public Deposit_ListVO() {
	}
	public Deposit_ListVO(String deposit_code, Integer deposit_num, Date deposit_date, String deposit_memid,
			String pay_type, String credit_num, Integer credit_expiry_yy, Integer credit_expiry_mm,
			Integer credit_security_num, String atm_id, String payment_sts) {
		super();
		this.deposit_code = deposit_code;
		this.deposit_num = deposit_num;
		this.deposit_date = deposit_date;
		this.deposit_memid = deposit_memid;
		this.pay_type = pay_type;
		this.credit_num = credit_num;
		this.credit_expiry_yy = credit_expiry_yy;
		this.credit_expiry_mm = credit_expiry_mm;
		this.credit_security_num = credit_security_num;
		this.atm_id = atm_id;
		this.payment_sts = payment_sts;
	}
	
	@Override
	public String toString() {
		return "Deposit_ListVO [deposit_code=" + deposit_code + ", deposit_num=" + deposit_num + ", deposit_date="
				+ deposit_date + ", deposit_memid=" + deposit_memid + ", pay_type=" + pay_type + ", credit_num="
				+ credit_num + ", credit_expiry_yy=" + credit_expiry_yy + ", credit_expiry_mm=" + credit_expiry_mm
				+ ", credit_security_num=" + credit_security_num + ", atm_id=" + atm_id + ", payment_sts=" + payment_sts
				+ "]";
	}
	
	public String getDeposit_code() {
		return deposit_code;
	}
	public void setDeposit_code(String deposit_code) {
		this.deposit_code = deposit_code;
	}
	public Integer getDeposit_num() {
		return deposit_num;
	}
	public void setDeposit_num(Integer deposit_num) {
		this.deposit_num = deposit_num;
	}
	public Date getDeposit_date() {
		return deposit_date;
	}
	public void setDeposit_date(Date deposit_date) {
		this.deposit_date = deposit_date;
	}
	public String getDeposit_memid() {
		return deposit_memid;
	}
	public void setDeposit_memid(String deposit_memid) {
		this.deposit_memid = deposit_memid;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getCredit_num() {
		return credit_num;
	}
	public void setCredit_num(String credit_num) {
		this.credit_num = credit_num;
	}
	public Integer getCredit_expiry_yy() {
		return credit_expiry_yy;
	}
	public void setCredit_expiry_yy(Integer credit_expiry_yy) {
		this.credit_expiry_yy = credit_expiry_yy;
	}
	public Integer getCredit_expiry_mm() {
		return credit_expiry_mm;
	}
	public void setCredit_expiry_mm(Integer credit_expiry_mm) {
		this.credit_expiry_mm = credit_expiry_mm;
	}
	public Integer getCredit_security_num() {
		return credit_security_num;
	}
	public void setCredit_security_num(Integer credit_security_num) {
		this.credit_security_num = credit_security_num;
	}
	public String getAtm_id() {
		return atm_id;
	}
	public void setAtm_id(String atm_id) {
		this.atm_id = atm_id;
	}
	public String getPayment_sts() {
		return payment_sts;
	}
	public void setPayment_sts(String payment_sts) {
		this.payment_sts = payment_sts;
	}
		
}
