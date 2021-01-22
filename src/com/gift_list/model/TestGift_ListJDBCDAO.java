package com.gift_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestGift_ListJDBCDAO {

	public static void main(String[] args) {
		Gift_ListDAO_interface dao = new Gift_ListJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<Gift_ListVO> list = dao.getAll();
		for (Gift_ListVO gift_listVO : list) {
			System.out.println(gift_listVO);
		}
		
		System.out.println("------------insert------------");
		Gift_ListVO gift_listVO1 = new Gift_ListVO("", "MEM0000001", 
				"MEM0000002", Date.valueOf(LocalDate.now()), "1");
		dao.insert(gift_listVO1);

		System.out.println("------------update------------");
		Gift_ListVO gift_listVO2 = new Gift_ListVO("1", "MEM0000002", 
				"MEM0000001", Date.valueOf(LocalDate.now()), "1");
		dao.update(gift_listVO2);
		
		System.out.println("------------findByPK----------");
		Gift_ListVO gift_listVO3 = dao.findByPrimaryKey("1");
		System.out.println(gift_listVO3);
		
//		dao.delete("2");
	}

}
