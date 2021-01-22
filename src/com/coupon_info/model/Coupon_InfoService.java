package com.coupon_info.model;

import java.util.List;

public class Coupon_InfoService {
	private Coupon_InfoDAO_interface dao;
	
	public Coupon_InfoService() {
		dao = new Coupon_InfoJDBCDAO();
	}
	
	public Coupon_InfoVO addCoupon_Info(String product_name, String product_brand, Integer exc_condition, Integer exe_deadline, 
			byte[] product_photo, String product_context, String cpon_type, String cpon_area_code, Integer exc_count, 
			Integer exced_count, String shelf_sts, java.sql.Date onshelf_date, java.sql.Date cpon_offshelf_date) {
		Coupon_InfoVO coupon_infoVO = new Coupon_InfoVO("", product_name, product_brand, exc_condition, exe_deadline, product_photo, 
				product_context, cpon_type, cpon_area_code, exc_count, exced_count, shelf_sts, onshelf_date, cpon_offshelf_date);
		dao.insert(coupon_infoVO);
		
		return coupon_infoVO;
	}
	
	public Coupon_InfoVO updateCoupon_Info(String copn_code, String product_name, String product_brand, Integer exc_condition, 
			Integer exe_deadline, byte[] product_photo, String product_context, String cpon_type, String cpon_area_code, 
			Integer exc_count, Integer exced_count, String shelf_sts, java.sql.Date onshelf_date, java.sql.Date cpon_offshelf_date) {
		Coupon_InfoVO coupon_infoVO = new Coupon_InfoVO(copn_code, product_name, product_brand, exc_condition, exe_deadline, product_photo, 
				product_context, cpon_type, cpon_area_code, exc_count, exced_count, shelf_sts, onshelf_date, cpon_offshelf_date);
		dao.update(coupon_infoVO);
		
		return coupon_infoVO;
	}
	
	public void deleteCoupon_Info(String copn_code) {
		dao.delete(copn_code);
	}
	
	public Coupon_InfoVO getOneCoupon_Info(String copn_code) {
		return dao.findByPrimaryKey(copn_code);
	}
	
	public List<Coupon_InfoVO> getAll(){
		return dao.getAll();
	}
	
}
