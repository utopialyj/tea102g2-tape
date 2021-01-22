package com.coupon_list.model;

import java.util.List;

public interface Coupon_ListDAO_interface {
	public void insert(Coupon_ListVO coupon_listVO);
	public void update(Coupon_ListVO coupon_listVO);
	public void delete(String cpon_num);
	public Coupon_ListVO findByPrimaryKey(String cpon_num);
	public List<Coupon_ListVO> getAll();
	
}
