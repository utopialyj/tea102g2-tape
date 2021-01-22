package com.actapl.model;

import java.util.List;

public class TestActAplService {

	public static void main(String[] args) {
		ActAplService aas = new ActAplService();
//		
//		System.out.println("------------getAll------------");
//		List<ActAplVO> list = aas.getAll();
//		for (ActAplVO actapl : list) {
//			System.out.println(actapl);
//		}
//		
//		System.out.println("------------addActApl---------");
//		aas.addActApl("MEM0000002", "ACT0000001", "測試測試", "無");
//		
//		System.out.println("------------updateActApl------");
//		aas.updateActApl("APL0000001", "MEM0000001", "ACT0000001", "test", "na");
//		
//		System.out.println("------------getOneActApl------");
//		ActAplVO actapl1 = aas.getOneActApl("APL0000001");
//		System.out.println(actapl1);
//		
//		aas.deleteActApl("APL0000003");
		
		//System.out.println("----------getOneApl------------");
		//aas.getOneApl("MEM0000003");
		
		
		System.out.println("----------isRepeat_APL------------");
		System.out.println(aas.isRepeat_APL("MEM0000002","ACT0000004"));
		
		
	}

}
