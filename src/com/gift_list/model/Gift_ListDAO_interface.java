package com.gift_list.model;

import java.util.List;

public interface Gift_ListDAO_interface {
	public void insert(Gift_ListVO gift_listVO);
	public void update(Gift_ListVO gift_listVO);
	public void delete(String gift_code);
	public Gift_ListVO findByPrimaryKey(String gift_code);
	public List<Gift_ListVO> getAll();
	
}
