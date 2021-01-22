package com.actrnk.model;

import java.util.List;

public class TestActRnkService {

	public static void main(String[] args) {
		ActRnkService ars = new ActRnkService();
		
		System.out.println("------------getAll------------");
		List<ActRnkVO> list = ars.getAll();
		for (ActRnkVO actrnk : list) {
			System.out.println(actrnk);
		}
		
		System.out.println("------------addActRnk---------");
		ars.addActRnk("MEM0000002", "ACT0000001", 4, "測試測試", "主辦者");
		
		System.out.println("------------updateActRnk------");
		ars.updateActRnk("RNK0000001", "MEM0000001", "ACT0000001", 3, "test", "參與者");
		
		System.out.println("------------getOneActRnk------");
		ActRnkVO actrnk1 = ars.getOneActRnk("RNK0000001");
		System.out.println(actrnk1);
		
		ars.deleteActRnk("RNK0000003");
	}

}
