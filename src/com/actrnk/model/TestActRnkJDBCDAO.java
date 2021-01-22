package com.actrnk.model;

import java.util.List;

public class TestActRnkJDBCDAO {

	public static void main(String[] args) {
		ActRnkDAO dao = new ActRnkJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<ActRnkVO> list = dao.getAll();
		for (ActRnkVO actrnk : list) {
			System.out.println(actrnk);
		}
		
		System.out.println("------------insert------------");
		ActRnkVO actrnk1 = new ActRnkVO("", "MEM0000001", "ACT0000001", 5, "測試測試測試", "主辦者");
		dao.insert(actrnk1);
		
		System.out.println("------------update------------");
		ActRnkVO actrnk2 = new ActRnkVO("RNK0000001", "MEM0000002", "ACT0000001", 3, "測試測試測試", "參與者");
		dao.update(actrnk2);
		
		System.out.println("------------findByPK----------");
		ActRnkVO actrnk3 = dao.findByPK("RNK0000001");
		System.out.println(actrnk3);
		
//		dao.delete("RNK0000002");
	}

}
