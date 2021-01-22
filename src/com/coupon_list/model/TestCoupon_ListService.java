package com.coupon_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestCoupon_ListService {

	public static void main(String[] args) {
		Coupon_ListService cls = new Coupon_ListService();
		
		System.out.println("------------getAll------------");
		List<Coupon_ListVO> list = cls.getAll();
		for (Coupon_ListVO coupon_listVO : list) {
			System.out.println(coupon_listVO);
		}
		
		System.out.println("------------addCoupon_List----");
		cls.addCoupon_List("1", Date.valueOf(LocalDate.now()), "尚未兌換", 
				Date.valueOf(LocalDate.now().plusDays(180)), "MEM0000001");
		
		System.out.println("------------updateCoupon_List-");
		cls.updateCoupon_List("1", "1", Date.valueOf(LocalDate.now()), "尚未兌換", 
				Date.valueOf(LocalDate.now().plusDays(180)), "MEM0000001");
		
		System.out.println("------------getCoupon_List----");
		Coupon_ListVO coupon_infoVO1 = cls.getOneCoupon_List("1");
		System.out.println(coupon_infoVO1);
		
//		cls.deleteCoupon_List("4");
	}

}
