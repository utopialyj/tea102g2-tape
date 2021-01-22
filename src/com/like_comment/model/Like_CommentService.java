package com.like_comment.model;

import java.sql.Timestamp;
import java.util.List;

public class Like_CommentService {

	private Like_CommentDAO_interface dao;
	
	public Like_CommentService() {
		dao = new Like_CommentJDBCDAO();
	}
	
	
	public Like_CommentVO addLikeCom(String cmnt_lk_memid, String cmnt_code, Timestamp cmnt_lk_date) {
		Like_CommentVO lcVO = new Like_CommentVO();

		lcVO.setCmnt_lk_memid(cmnt_lk_memid);
		lcVO.setCmnt_code(cmnt_code);
		lcVO.setCmnt_lk_date(cmnt_lk_date);
		
		dao.insert(lcVO);
		
		return lcVO;
	}
	
	
	public Like_CommentVO updateLikeCom(String cmnt_lk_uid, String cmnt_lk_memid, String cmnt_code, Timestamp cmnt_lk_date) {
		Like_CommentVO lcVO = new Like_CommentVO();
		
		lcVO.setCmnt_lk_uid(cmnt_lk_memid);
		lcVO.setCmnt_lk_memid(cmnt_lk_memid);
		lcVO.setCmnt_code(cmnt_code);
		lcVO.setCmnt_lk_date(cmnt_lk_date);
		
		dao.update(lcVO);
		
		return lcVO;
	}
	
	
	public Like_CommentVO getOneLikeCom(String cmnt_lk_uid) {
		return dao.findByPrimaryKey(cmnt_lk_uid);
	}
	
	public List<Like_CommentVO> getAll(){
		return dao.getAll();
	}
	
	public void deleteCmntLike(String cmnt_code, String cmnt_lk_memid) {
		dao.delete(cmnt_code, cmnt_lk_memid);
	}
	
	public List<String> getLikeMembersByCmntcode(String cmnt_code){
		return dao.getLikeMembersByCmntcode(cmnt_code);
	}
	
	
	public int getLikeCmntNum(String cmnt_code) {
		int sum ;
		sum = (dao.getLikeMembersByCmntcode(cmnt_code)).size();
		return sum;
	}
}
