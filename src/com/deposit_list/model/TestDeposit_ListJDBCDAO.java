package com.deposit_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestDeposit_ListJDBCDAO {

	public static void main(String[] args) {
		Deposit_ListDAO_interface dao = new Deposit_ListJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<Deposit_ListVO> list = dao.getAll();
		for (Deposit_ListVO deposit_listVO : list) {
			System.out.println(deposit_listVO);
		}
		
		System.out.println("------------insert------------");
		Deposit_ListVO deposit_listVO1 = new Deposit_ListVO("", 50, Date.valueOf(LocalDate.now()), 
				"MEM0000001", "ATM", null, 0, 0, 0, "1111222233334444", "已付款");
		dao.insert(deposit_listVO1);

		System.out.println("------------update------------");
		Deposit_ListVO deposit_listVO2 = new Deposit_ListVO("1", 10, Date.valueOf(LocalDate.now()), 
				"MEM0000001", "信用卡", "1111-2222-3333-4444", 25, 12, 333, null, "已付款");
		dao.update(deposit_listVO2);
		
		System.out.println("------------findByPK----------");
		Deposit_ListVO deposit_listVO3 = dao.findByPrimaryKey("1");
		System.out.println(deposit_listVO3);
		
//		dao.delete("2");
	}

}
