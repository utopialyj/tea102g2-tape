package com.actcol.model;

import java.util.List;

public class TestActColJDBCDAO {

	public static void main(String[] args) {
		ActColDAO dao = new ActColJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<ActColVO> list = dao.getAll();
		for (ActColVO actcol : list) {
			System.out.println(actcol);
		}
		
		System.out.println("------------insert------------");
		ActColVO actcol1 = new ActColVO("", "MEM0000001", "ACT0000001");
		dao.insert(actcol1);
		
		System.out.println("------------update------------");
		ActColVO actcol2 = new ActColVO("COL0000001", "MEM0000002", "ACT0000001");
		dao.update(actcol2);
		
		System.out.println("------------findByPK----------");
		ActColVO actcol3 = dao.findByPK("COL0000001");
		System.out.println(actcol3);
		
//		dao.delete("COL0000002");
	}

}
