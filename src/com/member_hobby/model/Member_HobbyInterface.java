package com.member_hobby.model;

import java.util.List;
import java.util.Map;

public interface Member_HobbyInterface {

	public void insert(Member_HobbyVO member_hobby);

	public void update(Member_HobbyVO member_hobby);

	public void delete(String memUid, String hob_code);

	public Map<String, List<String>> find_hob_code(List<String> hob_code);

	public Member_HobbyVO findByPrimaryKey(String mem_hob_uid);

	public List<Member_HobbyVO> getAll();

	public List<String> findmemhob(String hob_memid);

	public void addHob(String memUid,String hob_code);
	
	public List<Member_HobbyVO> findHobCode(String hob_memid);
}
