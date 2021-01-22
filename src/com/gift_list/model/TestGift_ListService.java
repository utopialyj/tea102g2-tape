package com.gift_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class TestGift_ListService {

	public static void main(String[] args) {
		Gift_ListService gls = new Gift_ListService();
		
		System.out.println("------------getAll------------");
		List<Gift_ListVO> list = gls.getAll();
		for (Gift_ListVO coupon_listVO : list) {
			System.out.println(coupon_listVO);
		}
		
		System.out.println("------------addGift_List------");
		gls.addGift_List("MEM0000001", "MEM0000002", Date.valueOf(LocalDate.now()), "1");
		
		System.out.println("------------updateGift_List---");
		gls.updateGift_List("1", "MEM0000001", "MEM0000002", Date.valueOf(LocalDate.now()), "1");
		
		System.out.println("------------getOneGift_List---");
		Gift_ListVO coupon_infoVO1 = gls.getOneGift_List("1");
		System.out.println(coupon_infoVO1);
		
//		gls.deleteGift_List("3");
	}

}
