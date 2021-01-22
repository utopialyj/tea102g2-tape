package com.acts.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TestActsJDBCDAO {
	public static void main(String[] args) {
		ActsDAO dao = new ActsJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<ActsVO> list = dao.getAll();
		for (ActsVO act : list) {
			System.out.println(act);
		}
		
//		System.out.println("------------insert------------");
//		ActsVO act1 = new ActsVO("",
//				"MEM0000001", "上架", Timestamp.from(Instant.now()), 
//				"未開始", "model程式撰寫", "STUDY", "來寫MVC model程式吧!", null, 
//				6, "01", 0, "B501", "TibaMe南京復興", 0, 0, "無");
//		dao.insert(act1);
//		
//		System.out.println("------------update------------");
//		ActsVO act2 = new ActsVO("ACT0000001",
//				"MEM0000002", "上架", Timestamp.from(Instant.now()), 
//				"未開始", "model程式撰寫", "STUDY", "test", null, 
//				6, "01", 0, "B501", "TibaMe南京復興", 0, 0, "無");
//		System.out.println("act2 圖片" + act2.getPic());
		
//		dao.update(act2);
//		
//		System.out.println("------------findByPK----------");
//		ActsVO act3 = dao.findByPK("ACT0000001");
//		System.out.println(act3);
		
//		dao.delete("ACT0000002");
	}
	
}
