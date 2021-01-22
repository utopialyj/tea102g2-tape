package com.member_authroity.model;

import java.io.IOException;
import java.util.List;

public class Member_AuthroityService {
	private Member_AuthroityDAO_interface dao;

	public Member_AuthroityService() {
		dao = new Member_AuthroityJDBCDAO();
	}
	public Member_AuthroityVO addM_Auth(Integer verify_level,Integer chat_auth,Integer post_auth,Integer meat_auth, Integer point_auth,Integer join_event_auth,Integer host_auth,Integer log_auth) throws IOException{
		Member_AuthroityVO member_authroityVO = new Member_AuthroityVO();
		member_authroityVO.setVerify_level(verify_level);
		member_authroityVO.setChat_auth(chat_auth);
		member_authroityVO.setPost_auth(post_auth);
		member_authroityVO.setMeat_auth(meat_auth);
		member_authroityVO.setPoint_auth(point_auth);
		member_authroityVO.setJoin_event_auth(join_event_auth);
		member_authroityVO.setHost_auth(host_auth);
		member_authroityVO.setLog_auth(log_auth);
		dao.insert(member_authroityVO);
		
		return member_authroityVO;
	}
	public Member_AuthroityVO updateM_Auth(Integer verify_level,Integer chat_auth,Integer post_auth,Integer meat_auth, Integer point_auth,Integer join_event_auth,Integer host_auth,Integer log_auth) throws IOException {
		Member_AuthroityVO member_authroityVO = new Member_AuthroityVO();
		member_authroityVO.setVerify_level(verify_level);
		member_authroityVO.setChat_auth(chat_auth);
		member_authroityVO.setPost_auth(post_auth);
		member_authroityVO.setMeat_auth(meat_auth);
		member_authroityVO.setPoint_auth(point_auth);
		member_authroityVO.setJoin_event_auth(join_event_auth);
		member_authroityVO.setHost_auth(host_auth);
		member_authroityVO.setLog_auth(log_auth);
		dao.update(member_authroityVO);
		
		return member_authroityVO;
	}
	public void deleteM_Auth(Integer verify_level) throws IOException {
		dao.delete(verify_level);
	}
	public Member_AuthroityVO getOneM_Auth(Integer verify_level) throws IOException {
		return dao.findByPrimaryKey(verify_level);
	}
	public List<Member_AuthroityVO> getAll(){
		return dao.getAll();
	}
}
