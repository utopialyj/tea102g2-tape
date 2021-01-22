package com.actrnk.model;

import java.util.List;

public interface ActRnkDAO {
	public void insert(ActRnkVO actrnk);
	public void update(ActRnkVO actrnk);
	public void delete(String rnkuid);
	public ActRnkVO findByPK(String rnkuid);
	public List<ActRnkVO> getAll();
	
}
