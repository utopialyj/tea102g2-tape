package com.acts.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;

public class ActsVO implements Serializable {
	private String actid;
	private String memid;
	private String title;
	private String cont;
	private Timestamp time;
	private String shsts;
	private String sts;
	private String type;
	private byte[] pic;
	private Integer peop;
	private String areacd;
	private Integer hot;
	private String loc;
	private String store;
	private Integer bgt;
	private Integer pts;
	private String rpsts;
	
	
	private String base64Image;
	
	public String getBase64Image() {
		return Base64.getEncoder().encodeToString(this.pic);
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	
	
	
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actid == null) ? 0 : actid.hashCode());
		result = prime * result + ((areacd == null) ? 0 : areacd.hashCode());
		result = prime * result + ((base64Image == null) ? 0 : base64Image.hashCode());
		result = prime * result + ((bgt == null) ? 0 : bgt.hashCode());
		result = prime * result + ((cont == null) ? 0 : cont.hashCode());
		result = prime * result + ((hot == null) ? 0 : hot.hashCode());
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((memid == null) ? 0 : memid.hashCode());
		result = prime * result + ((peop == null) ? 0 : peop.hashCode());
		result = prime * result + Arrays.hashCode(pic);
		result = prime * result + ((pts == null) ? 0 : pts.hashCode());
		result = prime * result + ((rpsts == null) ? 0 : rpsts.hashCode());
		result = prime * result + ((shsts == null) ? 0 : shsts.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((sts == null) ? 0 : sts.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ActsVO other = (ActsVO) obj;
		if (actid == null) {
			if (other.actid != null)
				return false;
		} else if (!actid.equals(other.actid))
			return false;
		if (areacd == null) {
			if (other.areacd != null)
				return false;
		} else if (!areacd.equals(other.areacd))
			return false;
		if (base64Image == null) {
			if (other.base64Image != null)
				return false;
		} else if (!base64Image.equals(other.base64Image))
			return false;
		if (bgt == null) {
			if (other.bgt != null)
				return false;
		} else if (!bgt.equals(other.bgt))
			return false;
		if (cont == null) {
			if (other.cont != null)
				return false;
		} else if (!cont.equals(other.cont))
			return false;
		if (hot == null) {
			if (other.hot != null)
				return false;
		} else if (!hot.equals(other.hot))
			return false;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		if (memid == null) {
			if (other.memid != null)
				return false;
		} else if (!memid.equals(other.memid))
			return false;
		if (peop == null) {
			if (other.peop != null)
				return false;
		} else if (!peop.equals(other.peop))
			return false;
		if (!Arrays.equals(pic, other.pic))
			return false;
		if (pts == null) {
			if (other.pts != null)
				return false;
		} else if (!pts.equals(other.pts))
			return false;
		if (rpsts == null) {
			if (other.rpsts != null)
				return false;
		} else if (!rpsts.equals(other.rpsts))
			return false;
		if (shsts == null) {
			if (other.shsts != null)
				return false;
		} else if (!shsts.equals(other.shsts))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (sts == null) {
			if (other.sts != null)
				return false;
		} else if (!sts.equals(other.sts))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	public ActsVO() {
	}

	@Override
	public String toString() {
		return "ActsVO [actid=" + actid + ", memid=" + memid + ", shsts=" + shsts + ", time=" + time + ", title="
				+ title + ", sts=" + sts + ", type=" + type + ", cont=" + cont + ", pic=" + Arrays.toString(pic)
				+ ", peop=" + peop + ", areacd=" + areacd + ", hot=" + hot + ", loc=" + loc + ", store=" + store
				+ ", bgt=" + bgt + ", pts=" + pts + ", rpsts=" + rpsts + ", base64Image=" + base64Image + "]";
	}
	public ActsVO(String actid, String memid, String shsts, Timestamp time, String title, String sts, String type,
			String cont, byte[] pic,  Integer peop, String areacd, Integer hot, String loc, String store, Integer bgt, Integer pts,
			String rpsts) {
		this.actid = actid;
		this.memid = memid;
		this.shsts = shsts;
		this.time = time;
		this.title = title;
		this.sts = sts;
		this.type = type;
		this.cont = cont;
		this.pic = pic;
		this.peop = peop;
		this.areacd = areacd;
		this.hot = hot;
		this.loc = loc;
		this.store = store;
		this.bgt = bgt;
		this.pts = pts;
		this.rpsts = rpsts;
	}


	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getMemid() {
		return memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	public String getShsts() {
		return shsts;
	}

	public void setShsts(String shsts) {
		this.shsts = shsts;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public Integer getPeop() {
		return peop;
	}

	public void setPeop(Integer peop) {
		this.peop = peop;
	}

	public String getAreacd() {
		return areacd;
	}

	public void setAreacd(String areacd) {
		this.areacd = areacd;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Integer getBgt() {
		return bgt;
	}

	public void setBgt(Integer bgt) {
		this.bgt = bgt;
	}

	public Integer getPts() {
		return pts;
	}

	public void setPts(Integer pts) {
		this.pts = pts;
	}

	public String getRpsts() {
		return rpsts;
	}

	public void setRpsts(String rpsts) {
		this.rpsts = rpsts;
	}
	
}
