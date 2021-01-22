package com.deposit_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestDeposit_ListService {

	public static void main(String[] args) {
		Deposit_ListService gls = new Deposit_ListService();
		
		System.out.println("------------getAll------------");
		List<Deposit_ListVO> list = gls.getAll();
		for (Deposit_ListVO deposit_listVO : list) {
			System.out.println(deposit_listVO);
		}
		
		System.out.println("------------addDeposit_List---");
		gls.addDeposit_List(10, Date.valueOf(LocalDate.now()), "MEM0000001", 
				"信用卡", "1111-2222-3333-4444", 25, 12, 333, null, "已付款");
		
		System.out.println("------------updateDeposit_List");
		gls.updateDeposit_List("1", 50, Date.valueOf(LocalDate.now()), "MEM0000001", 
				"ATM", null, 0, 0, 0, "1111222233334444", "已付款");
		
		System.out.println("------------getOneDeposit_List");
		Deposit_ListVO deposit_listVO1 = gls.getOneDeposit_List("1");
		System.out.println(deposit_listVO1);
		
//		gls.deleteGift_List("3");
	}

}
