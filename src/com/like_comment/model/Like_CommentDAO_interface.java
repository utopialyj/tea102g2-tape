package com.like_comment.model;

import java.util.List;



public interface Like_CommentDAO_interface {
	public void insert(Like_CommentVO like_CommentVO);
	public void update(Like_CommentVO like_CommentVO);
	public Like_CommentVO findByPrimaryKey(String cmnt_lk_uid);
	public List<Like_CommentVO> getAll();
	public void delete(String cmnt_code, String cmnt_lk_memid);
	public List<String> getLikeMembersByCmntcode(String cmnt_code);
}
