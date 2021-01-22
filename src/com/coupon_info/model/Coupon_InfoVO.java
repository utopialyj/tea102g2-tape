package com.coupon_info.model;
import java.sql.Date;

public class Coupon_InfoVO implements java.io.Serializable {
	private String cpon_code;
	private String product_name;
	private String product_brand;
	private Integer exc_condition;
	private Integer exe_deadline;
	private byte[] product_photo;
	private String product_context;
	private String cpon_type;
	private String cpon_area_code;
	private Integer exc_count;
	private Integer exced_count;
	private String shelf_sts;
	private Date onshelf_date;
	private Date cpon_offshelf_date;
	
	public Coupon_InfoVO() {
	}
	public Coupon_InfoVO(String cpon_code, String product_name, String product_brand, Integer exc_condition,
			Integer exe_deadline, byte[] product_photo, String product_context, String cpon_type, String cpon_area_code,
			Integer exc_count, Integer exced_count, String shelf_sts, java.sql.Date onshelf_date, java.sql.Date cpon_offshelf_date) {
		this.cpon_code = cpon_code;
		this.product_name = product_name;
		this.product_brand = product_brand;
		this.exc_condition = exc_condition;
		this.exe_deadline = exe_deadline;
		this.product_photo = product_photo;
		this.product_context = product_context;
		this.cpon_type = cpon_type;
		this.cpon_area_code = cpon_area_code;
		this.exc_count = exc_count;
		this.exced_count = exced_count;
		this.shelf_sts = shelf_sts;
		this.onshelf_date = onshelf_date;
		this.cpon_offshelf_date = cpon_offshelf_date;
	}

	@Override
	public String toString() {
		return "Coupon_InfoVO [cpon_code=" + cpon_code + ", product_name=" + product_name + ", product_brand="
				+ product_brand + ", exc_condition=" + exc_condition + ", exe_deadline=" + exe_deadline
				+ ", product_photo=" + "[not display here]" + ", product_context=" + product_context
				+ ", cpon_type=" + cpon_type + ", cpon_area_code=" + cpon_area_code + ", exc_count=" + exc_count
				+ ", exced_count=" + exced_count + ", shelf_sts=" + shelf_sts + ", onshelf_date=" + onshelf_date
				+ ", cpon_offshelf_date=" + cpon_offshelf_date + "]";
	}

	public String getCpon_code() {
		return cpon_code;
	}

	public void setCpon_code(String cpon_code) {
		this.cpon_code = cpon_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}

	public Integer getExc_condition() {
		return exc_condition;
	}

	public void setExc_condition(Integer exc_condition) {
		this.exc_condition = exc_condition;
	}

	public Integer getExe_deadline() {
		return exe_deadline;
	}

	public void setExe_deadline(Integer exe_deadline) {
		this.exe_deadline = exe_deadline;
	}

	public byte[] getProduct_photo() {
		return product_photo;
	}

	public void setProduct_photo(byte[] product_photo) {
		this.product_photo = product_photo;
	}

	public String getProduct_context() {
		return product_context;
	}

	public void setProduct_context(String product_context) {
		this.product_context = product_context;
	}

	public String getCpon_type() {
		return cpon_type;
	}

	public void setCpon_type(String cpon_type) {
		this.cpon_type = cpon_type;
	}

	public String getCpon_area_code() {
		return cpon_area_code;
	}

	public void setCpon_area_code(String cpon_area_code) {
		this.cpon_area_code = cpon_area_code;
	}

	public Integer getExc_count() {
		return exc_count;
	}

	public void setExc_count(Integer exc_count) {
		this.exc_count = exc_count;
	}

	public Integer getExced_count() {
		return exced_count;
	}

	public void setExced_count(Integer exced_count) {
		this.exced_count = exced_count;
	}

	public String getShelf_sts() {
		return shelf_sts;
	}

	public void setShelf_sts(String shelf_sts) {
		this.shelf_sts = shelf_sts;
	}

	public java.sql.Date getOnshelf_date() {
		return onshelf_date;
	}

	public void setOnshelf_date(java.sql.Date onshelf_date) {
		this.onshelf_date = onshelf_date;
	}

	public java.sql.Date getCpon_offshelf_date() {
		return cpon_offshelf_date;
	}

	public void setCpon_offshelf_date(java.sql.Date cpon_offshelf_date) {
		this.cpon_offshelf_date = cpon_offshelf_date;
	}
	
}
