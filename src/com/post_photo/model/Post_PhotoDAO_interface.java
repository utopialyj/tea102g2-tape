package com.post_photo.model;

import java.util.List;

import com.post.model.PostVO;

public interface Post_PhotoDAO_interface {
		public void insert(Post_PhotoVO post_photoVO);
		public void update(Post_PhotoVO post_photoVO);
		public Post_PhotoVO findByPrimaryKey(String photo_uid);
		public List<Post_PhotoVO> getAll();
		public void delete(String photo_uid);
		public List<Post_PhotoVO> findByPostCode(String post_code);
//		public List<byte[]> findByPostCode(String post_code);
		public void insert2(Post_PhotoVO post_PhotoVO, java.sql.Connection con);
		public String findByPhoto(byte[] photo);
		
		
}
