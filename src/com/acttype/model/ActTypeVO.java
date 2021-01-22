package com.acttype.model;


public class ActTypeVO  implements java.io.Serializable{
	private String typecode;
	private String acttype;
	
	
	
	@Override
	public String toString() {
		return "ActTypeVO [typecode=" + typecode + ", acttype=" + acttype + "]";
	}

	public ActTypeVO() {
		
	}
	
	public ActTypeVO(String typecode, String acttype) {
		super();
		this.typecode = typecode;
		this.acttype = acttype;
	}
	
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getActtype() {
		return acttype;
	}
	public void setActtype(String acttype) {
		this.acttype = acttype;
	}
	
	
}
