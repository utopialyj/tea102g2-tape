package com.tag_member.model;

import java.sql.Timestamp;
import java.util.List;

public class Tag_MemberService {
	
	private Tag_MemberDAO_interface dao;
	
	public Tag_MemberService() {
		dao = new Tag_MemberJDBCDAO();
	}
	
	public Tag_MemberVO addTagMember(String tag_memid, String post_code, Timestamp tag_date) {
		Tag_MemberVO tmVO = new Tag_MemberVO();

		tmVO.setTag_memid(tag_memid);
		tmVO.setPost_code(post_code);
		tmVO.setTag_date(tag_date);

		dao.insert(tmVO);
		
		return tmVO;
	}
	
	public Tag_MemberVO updateTagMember(String tag_uid, String tag_memid, String post_code, Timestamp tag_date) {
		
		Tag_MemberVO tmVO = new Tag_MemberVO();
		tmVO.setTag_uid(tag_uid);
		tmVO.setTag_memid(tag_memid);
		tmVO.setPost_code(post_code);
		tmVO.setTag_date(tag_date);
		
		dao.update(tmVO);
		
		return tmVO;
	}
	
	
	public Tag_MemberVO getOneTagMember(String tag_uid) {
		return dao.findByPrimaryKey(tag_uid);
	}
	
	public List<Tag_MemberVO> getAll(){
		return dao.getAll();
	}
	
	
	public void deleteTagMem(String tag_uid) {
		dao.delete(tag_uid);
	}
}
