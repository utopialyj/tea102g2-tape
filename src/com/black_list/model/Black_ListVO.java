package com.black_list.model;

public class Black_ListVO implements java.io.Serializable{

	private String blk_uid;
	private String blk_memid;
	private String beblk_memid;
	
	
	public String getBlk_uid() {
		return blk_uid;
	}
	public void setBlk_uid(String blk_uid) {
		this.blk_uid = blk_uid;
	}
	public String getBlk_memid() {
		return blk_memid;
	}
	public void setBlk_memid(String blk_memid) {
		this.blk_memid = blk_memid;
	}
	public String getBeblk_memid() {
		return beblk_memid;
	}
	public void setBeblk_memid(String beblk_memid) {
		this.beblk_memid = beblk_memid;
	}
	
}
