package com.black_list.model;

import java.util.List;


public interface Black_ListInterface {

	public void insert(Black_ListVO black_listVO);
	public void update(Black_ListVO black_listVO);
	public void delete(String blk_uid);
	public Black_ListVO findByPrimaryKey(String blk_uid);
	public List<Black_ListVO>getAll();
}
