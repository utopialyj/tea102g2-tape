package com.deposit_list.model;

import java.util.List;

public interface Deposit_ListDAO_interface {
	public void insert(Deposit_ListVO deposit_listVO);
	public void update(Deposit_ListVO deposit_listVO);
	public void delete(String deposit_code);
	public Deposit_ListVO findByPrimaryKey(String deposit_code);
	public List<Deposit_ListVO> getAll();
	
}
