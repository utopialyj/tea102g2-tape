package com.acts.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.actapl.model.ActAplVO;

public class TestActsService {
	public static void main(String[] args) {
		ActsService as = new ActsService();
		
//	 byte[] c = null;
//	 System.out.println(c == null);
	
//		System.out.println("------------getAll------------");
//		List<ActsVO> list = as.getAll();
//		for (ActsVO act : list) {
//			System.out.println(act);
//		}
//		
//		System.out.println("------------addAct------------");
//		as.addAct("MEM0000002", "上架", Timestamp.from(Instant.now()), "活動上架測試", "未開始", "測試", "測試測試測試", null, 9999, "01", 0, "TEA102G2", "TibaMe", 99999, 99999, "無");
//		
//		System.out.println("------------updateAct---------");
//		as.updateAct("ACT0000001", "MEM0000001", "上架", Timestamp.from(Instant.now()), "活動上架測試", "未開始", "測試", "測試測試測試", null, 9999, "01", 0, "TEA102G2", "TibaMe", 99999, 99999, "無");
//		
//		System.out.println("------------getOneAct---------");
//		ActsVO act1 = as.getOneAct("ACT0000001");
//		System.out.println(act1);
		
//		as.deleteAct("ACT0000002");
		
	 
//	 System.out.println("------------------透過getAll-----------------");
//	 System.out.println("------------------測試熱度計算-----------------");
//		List<ActsVO> list = as.getAll();
//		for (ActsVO act : list) {
//			System.out.print("活動熱度 " + as.getHot(act) + "\n");
//			System.out.println(act);
//			System.out.println("-------------------");
//		}
//	
		
//		ActsVO act1 = as.getOneAct("ACT0000001");
//		System.out.println("-------------------bothColApl-----------------");
//		System.out.println(as.getBothColPson(act1));
		
		
		
		
//		System.out.println("-------------------ColPson-----------------");
//		System.out.println(as.getColPson(act1));
		
		
//		System.out.println("-------------------AplPson-----------------");
//		System.out.println(as.getAplPson(act1));
		
		
//		System.out.println("-------------------getHot-----------------");
//		System.out.println(as.getHot(act1));
		
		
//		System.out.println("------------------allLimSearch----------------");
//		List<ActsVO> resultList = as.getActBySearch("2020/12/28 09:30:00", "2021-01-01 00:00:00", "15", "5");
//		for(ActsVO act : resultList) {
//			System.out.println(act);
//			System.out.println("-------------------");
//		}
		
		
		
//		System.out.println("-------------noLimOnTypeArea-------");
//		List<ActsVO> resultList = as.getActByTime("2020/12/30 09:30", "2021-12-30 15:00");
//		for(ActsVO act : resultList) {
//			System.out.println(act);
//			System.out.println("-------------------");
//		}
		
		
//		System.out.println("-------------noLimOnType-------");
//		List<ActsVO> resultList = as.noLimOnType("2020/12/30 09:30", "2021-12-30 15:00","15");
//		for(ActsVO act : resultList) {
//			System.out.println(act);
//			System.out.println("-------------------");
//		}
		
//System.out.println("-------------noLimOnArea-------");
//List<ActsVO> resultList = as.noLimOnArea("2019/12/30 09:30", "2021-12-30 15:00","1");
//for(ActsVO act : resultList) {
//	System.out.println(act);
//	System.out.println("-------------------");
//}
		
		
//		System.out.println("-------------getHoldActs-------");
//		List<ActsVO> resultList = as.getHoldActs("MEM0000004");
//		int i = 0;
//		for(ActsVO act : resultList) {
//			++i;
//			System.out.println("活動編號 = " + act.getActid()  + "\t"+ "活動主題 = "+ act.getTitle());
//		}
//		System.out.println("總共有" + i +"筆資料");

		
//System.out.println("-------------getAllAplFromAct-------");
//List<ActAplVO> resultList = as.getAllAplFromAct("ACT00000043");
//int i = 0;
//for(ActAplVO apl : resultList) {
//	++i;
//	System.out.println("活動編號 = " + apl.getActid()  + "\t"+ "會員編號 = "+ apl.getMemid() + "\t" + "報名理由=" + apl.getRson() + "\t" + "報名狀態 = " + apl.getSts());
//}
//System.out.println("總共有" + i +"筆資料");
		
		
//List<ActsVO> result = as.get_all_can_join("MEM0000004");
//if(result.size() != 0) {
//	for(ActsVO actvo: result) {
//		System.out.println(actvo);
//	}
//}
		
//SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//Date date = new Date();
//String strDate = sdFormat.format(date);
//	System.out.println(strDate);
//
//List<ActsVO> list = as.get_all_by_time();
////List<ActsVO> list = as.overtime_Act();
//for(ActsVO avo :list) {
//	System.out.println(avo.getActid() +"?" + avo.getTime());
//}
		
//List<ActAplVO> list1 = as.getAllAplFromAct("ACT0000010");
//System.out.println(list1);
//System.out.println("=========================");
//List<ActAplVO> list2 = as.get_All_Disagree("ACT0000010");
//System.out.println(list2);
//list1.removeAll(list2);
//System.out.println("list1.removeAll(list2) ?" +  list1);
		
//System.out.println("-------------overTime_own_Act-------");
//List<ActsVO> list = as.overTime_own_Act("MEM0000001");
//for(ActsVO avo :list) {
//	System.out.println(avo.getActid() +"?" + avo.getTime());
//}
		
		
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
			System.out.println(strDate);
			
		long time_diff = 1000*60*60*60;
		
	}//main-方法結束
}
