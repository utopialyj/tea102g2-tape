package com.actapl.model;

import java.util.List;

public class TestActAplJDBCDAO {

	public static void main(String[] args) {
		ActAplDAO dao = new ActAplJDBCDAO();
		
//		System.out.println("------------getAll------------");
//		List<ActAplVO> list = dao.getAll();
//		for (ActAplVO actapl : list) {
//			System.out.println(actapl);
//		}
		
//		System.out.println("------------insert------------");
//		ActAplVO actapl1 = new ActAplVO("", "MEM0000001", "ACT0000001", "測試用", "不同意");
//		dao.insert(actapl1);
	
		
		//	
		System.out.println("------------update------------");
		//ActAplVO actapl2 = new ActAplVO("APL0000001", "MEM0000002", "ACT0000001", "測試測試測試", "下架");
		ActAplVO actapl2 = new ActAplVO("APL0000001", "MEM0000015", "ACT0000001", "測試測試測試", "下架");
		dao.update(actapl2);
		
//		System.out.println("------------findByPK----------");
//		ActAplVO actapl3 = dao.findByPK("APL0000001");
//		System.out.println(actapl3);
		
//		dao.delete("APL0000002");
	}

}
