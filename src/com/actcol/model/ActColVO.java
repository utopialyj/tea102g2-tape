package com.actcol.model;

import java.io.Serializable;

public class ActColVO implements Serializable {
	private String coluid;
	private String memid;
	private String actid;
	
	public ActColVO() {
	}
	public ActColVO(String coluid, String memid, String actid) {
		this.coluid = coluid;
		this.memid = memid;
		this.actid = actid;
	}

	@Override
	public String toString() {
		return "Actcol [coluid=" + coluid + ", memid=" + memid + ", actid=" + actid + "]";
	}
	
	
	
	
	
	//////////////////////hasCode && equals ////////////////////////////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actid == null) ? 0 : actid.hashCode());
		result = prime * result + ((coluid == null) ? 0 : coluid.hashCode());
		result = prime * result + ((memid == null) ? 0 : memid.hashCode());
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
		ActColVO other = (ActColVO) obj;
		if (actid == null) {
			if (other.actid != null)
				return false;
		} else if (!actid.equals(other.actid))
			return false;
		if (coluid == null) {
			if (other.coluid != null)
				return false;
		} else if (!coluid.equals(other.coluid))
			return false;
		if (memid == null) {
			if (other.memid != null)
				return false;
		} else if (!memid.equals(other.memid))
			return false;
		return true;
	}
	
	
	public String getColuid() {
		return coluid;
	}

	public void setColuid(String coluid) {
		this.coluid = coluid;
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
	
}
