package com.member_authroity.model;

import java.io.IOException;
import java.util.List;

import com.member_authroity.model.Member_AuthroityVO;

public interface Member_AuthroityDAO_interface {
	public void insert(Member_AuthroityVO member_authroityVO)throws IOException;
    public void update(Member_AuthroityVO member_authroityVO);
    public void delete(Integer verify_level);
    public Member_AuthroityVO findByPrimaryKey(Integer verify_level);
    public List<Member_AuthroityVO> getAll();
}
