package com.coupon_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestCoupon_ListJDBCDAO {

	public static void main(String[] args) {
		Coupon_ListDAO_interface dao = new Coupon_ListJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<Coupon_ListVO> list = dao.getAll();
		for (Coupon_ListVO coupon_listVO : list) {
			System.out.println(coupon_listVO);
		}
		
		System.out.println("------------insert------------");
		Coupon_ListVO coupon_listVO1 = new Coupon_ListVO("", "1", Date.valueOf(LocalDate.now()), 
				"尚未兌換", Date.valueOf(LocalDate.now().plusDays(180)), "MEM0000001");
		dao.insert(coupon_listVO1);

		System.out.println("------------update------------");
		Coupon_ListVO coupon_listVO2 = new Coupon_ListVO("1", "1", Date.valueOf(LocalDate.now()), 
				"已兌換", Date.valueOf(LocalDate.now().plusDays(180)), "MEM0000001");
		dao.update(coupon_listVO2);
		
		System.out.println("------------findByPK----------");
		Coupon_ListVO coupon_listVO3 = dao.findByPrimaryKey("1");
		System.out.println(coupon_listVO3);
		
//		dao.delete("2");
	}

}
