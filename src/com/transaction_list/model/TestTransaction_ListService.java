package com.transaction_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestTransaction_ListService {

	public static void main(String[] args) {
		Transaction_ListService tls = new Transaction_ListService();
		
		System.out.println("------------getAll------------");
		List<Transaction_ListVO> list = tls.getAll();
		for (Transaction_ListVO coupon_listVO : list) {
			System.out.println(coupon_listVO);
		}
		
		System.out.println("------------addTransaction_List");
		tls.addTransaction_List("加點 儲值", 1000, Date.valueOf(LocalDate.now()), "MEM0000001");
		
		System.out.println("------------updateTransaction_List");
		tls.updateTransaction_List("1", "加點 儲值", 1000, Date.valueOf(LocalDate.now()), "MEM0000001");
		
		System.out.println("------------getOneTransaction_List");
		Transaction_ListVO coupon_infoVO1 = tls.getOneTransaction_List("1");
		System.out.println(coupon_infoVO1);
		
//		tls.deleteGift_List("3");
	}

}
