package com.actcol.model;

import java.util.List;

public interface ActColDAO {
	public void insert(ActColVO actcol);
	public void update(ActColVO actcol);
	public void delete(String coluid);
	public ActColVO findByPK(String coluid);
	public List<ActColVO> getAll();
	public List<ActColVO> getOneCol(String memid);
	
	//重複收藏
		public boolean isRepeat_COL(String memid, String actid);
	
	//活動編號 + 會員編號  = 收藏編號	
	public List<ActColVO> getOne(String memid, String actid);
	
}
