package com.actrnk.model;

import java.util.List;

public class ActRnkService {
	private ActRnkDAO dao;
	
	public ActRnkService() {
		dao = new ActRnkJDBCDAO();
	}
	
	public ActRnkVO addActRnk(String memid, String actid, Integer star, String cmmt, String sts) {
		ActRnkVO actrnk = new ActRnkVO("", memid, actid, star, cmmt, sts);
		dao.insert(actrnk);
		
		return actrnk;
	}
	
	public ActRnkVO updateActRnk(String apluid, String memid, String actid, Integer star, String cmmt, String sts) {
		ActRnkVO actrnk = new ActRnkVO(apluid , memid, actid, star, cmmt, sts);
		dao.update(actrnk);
		
		return actrnk;
	}
	
	public void deleteActRnk(String rnkuid) {
		dao.delete(rnkuid);
	}
	
	public ActRnkVO getOneActRnk(String rnkuid) {
		return dao.findByPK(rnkuid);
	}
	
	public List<ActRnkVO> getAll(){
		return dao.getAll();
	}
	
}
