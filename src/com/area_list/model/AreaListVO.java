package com.area_list.model;

public class AreaListVO implements java.io.Serializable{
	private String area_code;
	private String area;
	@Override
	public String toString() {
		return "AreaListVO [area_code=" + area_code + ", area=" + area + "]";
	}

	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
