package com.coupon_info.model;

import java.util.List;

public interface Coupon_InfoDAO_interface {
	public void insert(Coupon_InfoVO coupon_infoVO);
	public void update(Coupon_InfoVO coupon_infoVO);
	public void delete(String cpon_code);
	public Coupon_InfoVO findByPrimaryKey(String cpon_code);
	public List<Coupon_InfoVO> getAll();
	
}
