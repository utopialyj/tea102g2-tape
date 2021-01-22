package com.post.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;
import com.post_photo.model.Post_PhotoVO;





public class PostService {
 
	private PostDAO_interface dao;
	
	public PostService() {
		dao = new PostJDBCDAO();
	}
	
//	public PostVO addPost(String post_memid, Integer post_sts, Integer post_public_lv, Timestamp edit_date, String post_context, String post_location, Integer lk_num ) {
//		PostVO postVO  = new PostVO();
//		
//		postVO.setPost_memid(post_memid);
//		postVO.setPost_sts(post_sts);
//		postVO.setPost_public_lv(post_public_lv);
//		postVO.setEdit_date(edit_date);
//		postVO.setPost_context(post_context);
//		postVO.setPost_location(post_location);
//		postVO.setLk_num(lk_num);
//	
//		dao.insert(postVO);
//
//		return postVO;
//		
//	}
	
//	public PostVO addPost(String post_memid, Integer post_sts, Integer post_public_lv, Timestamp edit_date, String post_context, String post_location, Integer lk_num, byte[] photo, Integer photo_sts) {
//		PostVO postVO  = new PostVO();
//		Post_PhotoVO post_PhotoVO = new Post_PhotoVO();
//		List<Post_PhotoVO> photoList = new ArrayList<Post_PhotoVO>();
//		
//		postVO.setPost_memid(post_memid);
//		postVO.setPost_sts(post_sts);
//		postVO.setPost_public_lv(post_public_lv);
//		postVO.setEdit_date(edit_date);
//		postVO.setPost_context(post_context);
//		postVO.setPost_location(post_location);
//		postVO.setLk_num(lk_num);
//		post_PhotoVO.setPhoto(photo);
//		post_PhotoVO.setPhoto_sts(photo_sts);
//		photoList.add(post_PhotoVO);
//		dao.insertWithPhoto(postVO, photoList);
//
//		return postVO;//要改嗎?
//	}
	
	
	public void addPost(PostVO postVO, List<Post_PhotoVO> post_PhotosVO) {
		if(post_PhotosVO.isEmpty()){
		dao.insert(postVO);

		}else {
		dao.insertWithPhoto(postVO, post_PhotosVO);
		}
	}
	
	
	
	
	public PostVO updatePost(String post_uid, String post_memid, Integer post_sts,
			Integer post_public_lv, Timestamp edit_date, String post_context, String post_location, Integer lk_num) {

		PostVO postVO = new PostVO();
		postVO.setPost_uid(post_uid);
		postVO.setPost_memid(post_memid);
		postVO.setPost_sts(post_sts);
		postVO.setPost_public_lv(post_public_lv);
		postVO.setEdit_date(edit_date);
		postVO.setPost_context(post_context);
		postVO.setPost_location(post_location);
		postVO.setLk_num(lk_num);
		dao.update(postVO);
		
		return postVO;
	}
	
	public PostVO getOnePost(String post_uid) {
		return dao.findByPrimaryKey(post_uid);
	}

	public List<PostVO> getAll() {
		return dao.getAll();
	}
	
	
	public List<PostVO> getStsOneThere(){
		return dao.getStsOneThere();
	}
	
	public void deletePost(String post_uid) {
		dao.delete(post_uid);
	}
	
	public List<PostVO> getMemberPost(String member_id){
		return dao.getMemberPost(member_id);
	}
	
	public List<PostVO> getMembers_Id(List<Member_InfoVO> members){
		List<String> members_id = new ArrayList();

		for(Member_InfoVO memberVO : members) {
			String id = memberVO.getMem_id();
			members_id.add(id);
		}
		
		List<PostVO> friends_Posts = new ArrayList();	
		for(String memberid : members_id) {
			List<PostVO> postVO = getMemberPost(memberid);
			Iterator objs = postVO.iterator();
			for(int i = 0; i < postVO.size() ; i++) {
				friends_Posts.add(postVO.get(i));
			}
		}
		
		return friends_Posts;
	}
	
	public Member_InfoVO getMemberByPostCode(String post_code) {
		Member_InfoService memberSvc = new Member_InfoService();
		Member_InfoVO memberVO = new Member_InfoVO();
		PostVO postVO = getOnePost(post_code);

		String memid = postVO.getPost_memid();
	
		try{
			memberVO = memberSvc.getOneM_Info(memid);
		}catch(Exception e) {
			System.out.println("PostService出問題");
		};
		
		
		return memberVO;
	}
	
	//1/13新增
	public List<PostVO> getPostToWall(List<Member_InfoVO> memberVO){
		List<String> list = new ArrayList();
		for(Member_InfoVO memberInfoVO : memberVO) {
			
			list.add(memberInfoVO.getMem_id());
		}
			
		return dao.getPostToWall(list);
		
	}

}
