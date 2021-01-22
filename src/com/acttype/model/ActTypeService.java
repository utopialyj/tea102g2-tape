package com.acttype.model;

import java.util.List;

public class ActTypeService {
	private ActTypeDao dao ;
	
	public ActTypeService() {
		dao = new ActTypeJDBCDAO();
	}
	
	public List<ActTypeVO> getAll(){
		return dao.getAll();
	}
}
