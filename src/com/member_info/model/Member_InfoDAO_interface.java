package com.member_info.model;

import java.io.IOException;
import java.util.List;


public interface Member_InfoDAO_interface {
	public void insert(Member_InfoVO member_infoVO);
    public void update(Member_InfoVO member_infoVO);
    public void delete(String mem_id);
    public Member_InfoVO findByPrimaryKey(String mem_id);
    public List<Member_InfoVO> getAll();
    public Member_InfoVO findLogin(String m_email );
    public Member_InfoVO findPic(String mem_id);
	public void updateFromEMAILBack(String m_email);
	public Member_InfoVO findPaswd(String m_email);
	public void updatePaswd(Member_InfoVO member_infoVO);
	
	
    
    
	// 12/28新增
    public List<Member_InfoVO> getFriends(String mem_id);
	//1/5新增
	public void update_profile(Member_InfoVO member_infoVO);
	//1/7新增
	public void update_headshot(String Mem_id, byte[] headshot);
}
