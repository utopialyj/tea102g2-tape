package com.actapl.model;

import java.io.Serializable;

public class ActAplVO implements Serializable {
	private String apluid;
	private String memid;
	private String actid;
	private String rson;
	private String sts;
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actid == null) ? 0 : actid.hashCode());
		result = prime * result + ((apluid == null) ? 0 : apluid.hashCode());
		result = prime * result + ((memid == null) ? 0 : memid.hashCode());
		result = prime * result + ((rson == null) ? 0 : rson.hashCode());
		result = prime * result + ((sts == null) ? 0 : sts.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActAplVO other = (ActAplVO) obj;
		if (actid == null) {
			if (other.actid != null)
				return false;
		} else if (!actid.equals(other.actid))
			return false;
		if (apluid == null) {
			if (other.apluid != null)
				return false;
		} else if (!apluid.equals(other.apluid))
			return false;
		if (memid == null) {
			if (other.memid != null)
				return false;
		} else if (!memid.equals(other.memid))
			return false;
		if (rson == null) {
			if (other.rson != null)
				return false;
		} else if (!rson.equals(other.rson))
			return false;
		if (sts == null) {
			if (other.sts != null)
				return false;
		} else if (!sts.equals(other.sts))
			return false;
		return true;
	}
	public ActAplVO() {
	}
	public ActAplVO(String apluid, String memid, String actid, String rson, String sts) {
		this.apluid = apluid;
		this.memid = memid;
		this.actid = actid;
		this.rson = rson;
		this.sts = sts;
	}

	@Override
	public String toString() {
		return "ActAplVO [apluid=" + apluid + ", memid=" + memid + ", actid=" + actid + ", rson=" + rson + ", sts="
				+ sts + "]";
	}

	public String getApluid() {
		return apluid;
	}

	public void setApluid(String apluid) {
		this.apluid = apluid;
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

	public String getRson() {
		return rson;
	}

	public void setRson(String rson) {
		this.rson = rson;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}
