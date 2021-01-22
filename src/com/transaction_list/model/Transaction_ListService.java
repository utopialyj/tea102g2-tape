package com.transaction_list.model;

import java.sql.Date;
import java.util.List;

public class Transaction_ListService {
	private Transaction_ListDAO_interface dao;
	
	public Transaction_ListService() {
		dao = new Transaction_ListJDBCDAO();
	}
	
	public Transaction_ListVO addTransaction_List(String trans_type, 
			Integer trans_ant, Date trans_date, String trans_memid) {
		Transaction_ListVO transaction_listVO = new Transaction_ListVO(
				"", trans_type, trans_ant, trans_date, trans_memid);
		dao.insert(transaction_listVO);
		
		return transaction_listVO;
	}
	
	public Transaction_ListVO updateTransaction_List(String trans_code, String trans_type, 
			Integer trans_ant, Date trans_date, String trans_memid) {
		Transaction_ListVO transaction_listVO = new Transaction_ListVO(
				trans_code, trans_type, trans_ant, trans_date, trans_memid);
		dao.update(transaction_listVO);
		
		return transaction_listVO;
	}
	
	public void deleteTransaction_List(String trans_code) {
		dao.delete(trans_code);
	}
	
	public Transaction_ListVO getOneTransaction_List(String trans_code) {
		return dao.findByPrimaryKey(trans_code);
	}
	
	public List<Transaction_ListVO> getAll(){
		return dao.getAll();
	}
	
}
