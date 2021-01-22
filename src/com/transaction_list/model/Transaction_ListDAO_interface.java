package com.transaction_list.model;

import java.util.List;

public interface Transaction_ListDAO_interface {
	public void insert(Transaction_ListVO transaction_listVO);
	public void update(Transaction_ListVO transaction_listVO);
	public void delete(String trans_code);
	public Transaction_ListVO findByPrimaryKey(String trans_code);
	public List<Transaction_ListVO> getAll();
	
}
