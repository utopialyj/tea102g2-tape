package com.post_photo.model;

import java.util.Iterator;
import java.util.List;

public class Post_PhotoService {
	
	private Post_PhotoDAO_interface dao;
	
	public Post_PhotoService() {
		dao = new Post_PhotoJDBCDAO();
	}
	
	public Post_PhotoVO addPostPhoto(byte[] photo, String post_code, Integer photo_sts) {
		
		Post_PhotoVO post_photoVO = new Post_PhotoVO();
		post_photoVO.setPhoto(photo);
		post_photoVO.setPost_code(post_code);
		post_photoVO.setPhoto_sts(photo_sts);
		dao.insert(post_photoVO);
		
		return post_photoVO;
	}
	
public void addPostPhoto(List<byte[]> photos, String post_code, Integer photo_sts) {
	
	Post_PhotoVO post_photoVO = new Post_PhotoVO();
		for(byte[] photo : photos) {
		post_photoVO.setPhoto(photo);
		post_photoVO.setPost_code(post_code);
		post_photoVO.setPhoto_sts(photo_sts);
		dao.insert(post_photoVO);

		}

//		return post_photoVO;
	}
	
	
	public Post_PhotoVO updatePostPhoto(String photo_uid,byte[] photo, String post_code, Integer photo_sts) {
		
		Post_PhotoVO post_photoVO = new Post_PhotoVO();
		post_photoVO.setPhoto_uid(photo_uid);
		post_photoVO.setPhoto(photo);
		post_photoVO.setPost_code(post_code);
		post_photoVO.setPhoto_sts(photo_sts);
		
		dao.update(post_photoVO);
		
		return post_photoVO;
	}
	
	
	public Post_PhotoVO getOnePostPhoto(String photo_uid) {
		return dao.findByPrimaryKey(photo_uid);
	}
	
	
	public List<Post_PhotoVO> getAll(){
		return dao.getAll();
	}
	
	public void deletePostPhoto(String photo_uid) {
		dao.delete(photo_uid);
	}
	
//	public List<byte[]> findByPostCode(String post_code){
//		return dao.findByPostCode(post_code);
//	}
	
	public List<Post_PhotoVO> findByPostCode(String post_code){
		return dao.findByPostCode(post_code);
	}
	
	public String findByPhoto(byte[] photo) {
		return dao.findByPhoto(photo);
	}
	
}
