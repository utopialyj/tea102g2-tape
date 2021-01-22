package com.transaction_list.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestTransaction_ListJDBCDAO {

	public static void main(String[] args) {
		Transaction_ListDAO_interface dao = new Transaction_ListJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<Transaction_ListVO> list = dao.getAll();
		for (Transaction_ListVO transaction_listVO : list) {
			System.out.println(transaction_listVO);
		}
		
		System.out.println("------------insert------------");
		Transaction_ListVO transaction_listVO1 = new Transaction_ListVO("", "加點 儲值", 
				1000, Date.valueOf(LocalDate.now()), "MEM0000001");
		dao.insert(transaction_listVO1);

		System.out.println("------------update------------");
		Transaction_ListVO transaction_listVO2 = new Transaction_ListVO("1", "扣點 刷新配對", 
				-10, Date.valueOf(LocalDate.now()), "MEM0000001");
		dao.update(transaction_listVO2);
		
		System.out.println("------------findByPK----------");
		Transaction_ListVO transaction_listVO3 = dao.findByPrimaryKey("1");
		System.out.println(transaction_listVO3);
		
//		dao.delete("2");
	}

}
