package com.acttype.model;

import java.util.List;

import com.acttype.model.ActTypeVO;

public interface ActTypeDao {
	public void insert(ActTypeVO acttypeVO);
    public void update(ActTypeVO acttypeVO);
    public void delete(String typecode);
    public ActTypeVO findByPrimaryKey(String typecode);
    public List<ActTypeVO> getAll();
}
