package com.tag_member.model;

import java.util.List;

import com.post_photo.model.Post_PhotoVO;

public interface Tag_MemberDAO_interface {
	public void insert(Tag_MemberVO tag_memberDAO);
	public void update(Tag_MemberVO tag_memberDAO);
	public Tag_MemberVO findByPrimaryKey(String tag_uid);
	public List<Tag_MemberVO> getAll();
	public void delete(String tag_uid);
}
