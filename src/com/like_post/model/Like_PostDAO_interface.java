package com.like_post.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.member_info.model.Member_InfoVO;


public interface Like_PostDAO_interface {
	public void insert(Like_PostVO like_PostVO);
	public void update(Like_PostVO like_PostVO);
	public Like_PostVO findByPrimaryKey(String lk_uid);
	public List<Like_PostVO> getAll();
	public void delete(String post_code, String lk_memid);
	public List<String> getLikeMembersByPostcode(String post_code);
}
	