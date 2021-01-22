package com.like_post.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.member_info.model.Member_InfoVO;

public class Like_PostService {

	private Like_PostDAO_interface dao;
	
	public Like_PostService() {
		dao = new Like_PostJDBCDAO();
	}
	
	
	public Like_PostVO addLikePost(String lk_memid, String post_code, Timestamp lk_date) {
		Like_PostVO lpVO = new Like_PostVO();

		lpVO.setLk_memid(lk_memid);
		lpVO.setPost_code(post_code);
		lpVO.setLk_date(lk_date);

		dao.insert(lpVO);
		
		return lpVO;
		
	}
	
	
	public Like_PostVO addLikePost(Like_PostVO lpVO) {
		
		dao.insert(lpVO);
		
		return lpVO;
		
	}
	
	
	public Like_PostVO updateLikePost(String lk_uid, String lk_memid, String post_code, Timestamp lk_date) {
		Like_PostVO lpVO = new Like_PostVO();

		lpVO.setLk_memid(lk_uid);
		lpVO.setLk_memid(lk_memid);
		lpVO.setPost_code(post_code);
		lpVO.setLk_date(lk_date);

		dao.update(lpVO);
		
		return lpVO;
	}
	
	
	public Like_PostVO getOneLikePost(String lk_uid) {
		return dao.findByPrimaryKey(lk_uid);
	}
	
	public List<Like_PostVO> getAll(){
		return dao.getAll();
	}
	
	public void deleteLikePost(String post_code, String lk_memid) {
		dao.delete(post_code, lk_memid);
	}
	
	public List<String> getLikeMembersByPostcode(String post_code){
		return dao.getLikeMembersByPostcode(post_code);
	}
	
	
	public int getLikeNum(String post_code) {
		int sum ;
		sum = (dao.getLikeMembersByPostcode(post_code)).size();
		return sum;
	}
	
	
//	public void yOrnPostLike(Like_PostVO like_PostVO, Set<String> like_members) {
//		int a =0;
//		String member = like_PostVO.getLk_memid();
//		
//		for(String mem: like_members) {
//			if(mem.matches(regex)){
//				deleteLikePost(like_PostVO.getPost_code(), like_PostVO.getLk_memid());
//				
//			}else {
//				addLikePost(like_PostVO);
//				System.out.println("+1");
//			}
//		}
		
		
//	}
}
