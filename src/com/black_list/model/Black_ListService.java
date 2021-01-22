package com.black_list.model;

import java.util.List;

public class Black_ListService {

	private Black_ListInterface dao;

	public Black_ListService() {
		dao = new Black_ListJDBCDAO();
	}

	public Black_ListVO addBlack_List(String blk_memid, String beblk_memid) {
		Black_ListVO black_listVO = new Black_ListVO();

		
		black_listVO.setBlk_memid(beblk_memid);
		black_listVO.setBeblk_memid(beblk_memid);
		dao.insert(black_listVO);

		return black_listVO;

	}

	public Black_ListVO updateBlack_List(String blk_uid, String blk_memid, String beblk_memid) {
		Black_ListVO black_listVO = new Black_ListVO();

		black_listVO.setBlk_uid(blk_uid);
		black_listVO.setBlk_memid(blk_memid);
		black_listVO.setBeblk_memid(beblk_memid);
		dao.insert(black_listVO);

		return black_listVO;

	}
	public void deleteBlack_List(String hob_code) {
		dao.delete(hob_code);
	}

	public Black_ListVO getOneBlack_List(String blk_uid) {
		return dao.findByPrimaryKey(blk_uid);
	}
	public List<Black_ListVO> getAll(){
		return dao.getAll();
	}
}
