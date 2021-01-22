package com.hobby_list.model;

import java.util.List;

public interface Hobby_ListInterface {
	
		public void insert(Hobby_ListVO hobby_listVO);
		public void update(Hobby_ListVO hobby_listVO);
		public void delete(String hob_code);
		public Hobby_ListVO findByPrimaryKey(String hob_code);
		public List<Hobby_ListVO>getAll();
	}

