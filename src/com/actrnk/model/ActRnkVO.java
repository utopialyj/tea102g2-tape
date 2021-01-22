package com.actrnk.model;

import java.io.Serializable;

public class ActRnkVO implements Serializable {
	private String rnkuid;
	private String memid;
	private String actid;
	private Integer star;
	private String cmmt;
	private String sts;
	
	public ActRnkVO() {
	}
	public ActRnkVO(String rnkuid, String memid, String actid, Integer star, String cmmt, String sts) {
		this.rnkuid = rnkuid;
		this.memid = memid;
		this.actid = actid;
		this.star = star;
		this.cmmt = cmmt;
		this.sts = sts;
	}
	
	@Override
	public String toString() {
		return "ActRnkVO [rnkuid=" + rnkuid + ", memid=" + memid + ", actid=" + actid + ", star=" + star + ", cmmt="
				+ cmmt + ", sts=" + sts + "]";
	}
	
	public String getRnkuid() {
		return rnkuid;
	}
	public void setRnkuid(String rnkuid) {
		this.rnkuid = rnkuid;
	}
	public String getMemid() {
		return memid;
	}
	public void setMemid(String memid) {
		this.memid = memid;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getCmmt() {
		return cmmt;
	}
	public void setCmmt(String cmmt) {
		this.cmmt = cmmt;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	
}
