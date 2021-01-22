package com.hobby_list.model;

import java.util.List;


public class Hobby_ListService {

	private Hobby_ListInterface dao;

	public Hobby_ListService() {
		dao = new Hobby_ListJDBCDAO();
	}

	public Hobby_ListVO addHobby_List(String hob_code,String hob) {

		Hobby_ListVO hobby_listVO = new Hobby_ListVO();

		hobby_listVO.setHob_code(hob_code);
		hobby_listVO.setHob(hob);
	

		dao.insert(hobby_listVO);

		return hobby_listVO;
	}

	public Hobby_ListVO updateHobby_List(String hob_code,String hob) {

		Hobby_ListVO hobby_listVO = new Hobby_ListVO();


		hobby_listVO.setHob_code(hob_code);
		hobby_listVO.setHob(hob);
	
	
		dao.update(hobby_listVO);

		return hobby_listVO;
	}

	public void deleteHobby_List(String hob_code) {
		dao.delete(hob_code);
	}

	public Hobby_ListVO getOneHobby_List(String hob_code) {
		return dao.findByPrimaryKey(hob_code);
	}

	public List<Hobby_ListVO> getAll() {
		return dao.getAll();
	}
}
