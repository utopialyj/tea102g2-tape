package com.relationship.model;

public class RelationshipVO implements java.io.Serializable{

	private String frdrela_uid;
	private String frdinv_memid;
	private String frdbeinv_memid;
	private Integer frdinv_sts;
	public String getFrdrela_uid() {
		return frdrela_uid;
	}
	public void setFrdrela_uid(String frdrela_uid) {
		this.frdrela_uid = frdrela_uid;
	}
	public String getFrdinv_memid() {
		return frdinv_memid;
	}
	public void setFrdinv_memid(String frdinv_memid) {
		this.frdinv_memid = frdinv_memid;
	}
	public String getFrdbeinv_memid() {
		return frdbeinv_memid;
	}
	public void setFrdbeinv_memid(String frdbeinv_memid) {
		this.frdbeinv_memid = frdbeinv_memid;
	}
	public Integer getFrdinv_sts() {
		return frdinv_sts;
	}
	public void setFrdinv_sts(Integer frdinv_sts) {
		this.frdinv_sts = frdinv_sts;
	}
	@Override
	public String toString() {
		return "RelationshipVO [frdrela_uid=" + frdrela_uid + ", frdinv_memid=" + frdinv_memid + ", frdbeinv_memid="
				+ frdbeinv_memid + ", frdinv_sts=" + frdinv_sts + "]";
	}
	
}
