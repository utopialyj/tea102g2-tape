package com.gift_list.model;

import java.sql.Date;
import java.util.List;


public class Gift_ListService {
	private Gift_ListDAO_interface dao;
	
	public Gift_ListService() {
		dao = new Gift_ListJDBCDAO();
	}
	
	public Gift_ListVO addGift_List(String send_memid, 
			String receive_memid, Date gift_date, String gift_cpon_num) {
		Gift_ListVO gift_listVO = new Gift_ListVO("", send_memid, 
				receive_memid, gift_date, gift_cpon_num);
		dao.insert(gift_listVO);
		
		return gift_listVO;
	}
	
	public Gift_ListVO updateGift_List(String gift_code, String send_memid, 
			String receive_memid, Date gift_date, String gift_cpon_num) {
		Gift_ListVO gift_listVO = new Gift_ListVO(gift_code, send_memid, 
				receive_memid, gift_date, gift_cpon_num);
		dao.update(gift_listVO);
		
		return gift_listVO;
	}
	
	public void deleteGift_List(String gift_code) {
		dao.delete(gift_code);
	}
	
	public Gift_ListVO getOneGift_List(String gift_code) {
		return dao.findByPrimaryKey(gift_code);
	}
	
	public List<Gift_ListVO> getAll(){
		return dao.getAll();
	}
	
}
