package com.post.model;

import java.util.List;

import com.post_photo.model.Post_PhotoVO;

public interface PostDAO_interface {
	public void insert(PostVO postVO);
	public void update(PostVO postVO);
	public void delete(String post_uid);
	public PostVO findByPrimaryKey(String post_uid);
	public List<PostVO> getAll();
	public List<PostVO> getStsOneThere();
	public void insertWithPhoto(PostVO postVO, List<Post_PhotoVO> list);
	public List<PostVO> getMemberPost(String member_id);
	//1/13新增
	public List<PostVO> getPostToWall(List<String> member_id);
	
}
