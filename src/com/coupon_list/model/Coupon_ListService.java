package com.coupon_list.model;

import java.sql.Date;
import java.util.List;

public class Coupon_ListService {
	private Coupon_ListDAO_interface dao;
	
	public Coupon_ListService() {
		dao = new Coupon_ListJDBCDAO();
	}
	
	public Coupon_ListVO addCoupon_List(String cpon_code, Date cpon_num_bdate, 
			String exc_sts, Date cpon_expirydate, String exc_memid) {
		Coupon_ListVO coupon_listVO = new Coupon_ListVO("", cpon_code, 
				cpon_num_bdate, exc_sts, cpon_expirydate, exc_memid);
		dao.insert(coupon_listVO);
		
		return coupon_listVO;
	}
	
	public Coupon_ListVO updateCoupon_List(String cpon_num, String cpon_code, 
			Date cpon_num_bdate, String exc_sts, Date cpon_expirydate, String exc_memid) {
		Coupon_ListVO coupon_listVO = new Coupon_ListVO(cpon_num, cpon_code, 
				cpon_num_bdate, exc_sts, cpon_expirydate, exc_memid);
		dao.update(coupon_listVO);
		
		return coupon_listVO;
	}
	
	public void deleteCoupon_List(String cpon_num) {
		dao.delete(cpon_num);
	}
	
	public Coupon_ListVO getOneCoupon_List(String cpon_num) {
		return dao.findByPrimaryKey(cpon_num);
	}
	
	public List<Coupon_ListVO> getAll(){
		return dao.getAll();
	}
	
}
