package com.post_comment.model;

import java.util.List;


public interface Post_CommentDAO_interface {
	public void insert(Post_CommentVO post_CommentVO);
	public void update(Post_CommentVO post_CommentVO);
	public Post_CommentVO findByPrimaryKey(String cmnt_uid);
	public List<Post_CommentVO> getAll();
	public void delete(String cmnt_uid);
	public List<Post_CommentVO> findByPost_Code(String post_code);
}
