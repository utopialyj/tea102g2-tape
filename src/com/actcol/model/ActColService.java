package com.actcol.model;

import java.util.ArrayList;
import java.util.List;

import com.acts.model.ActsService;
import com.acts.model.ActsVO;

public class ActColService {
	private ActColDAO dao;
	private ActsService actSvc;
	
	public ActColService() {
		dao = new ActColJDBCDAO();
	}
	
	public ActColVO addActCol(String memid, String actid) {
		ActColVO actcol = new ActColVO("", memid, actid);
		dao.insert(actcol);
		
		return actcol;
	}
	
	public ActColVO updateActCol(String coluid, String memid, String actid) {
		ActColVO actcol = new ActColVO(coluid , memid, actid);
		dao.update(actcol);
		
		return actcol;
	}
	
	public void deleteActCol(String coluid) {
		dao.delete(coluid);
	}
	
	public ActColVO getOneActCol(String coluid) {
		return dao.findByPK(coluid);
	}
	
	public List<ActColVO> getAll(){
		return dao.getAll();
	}
	
	public List<ActColVO> getOneCol(String memid){
			return dao.getOneCol(memid);
	}
	
	//重複收藏
	public boolean isRepeat_COL(String memid, String actid) {
		return dao.isRepeat_COL(memid, actid);
	}
	
	//找到所有收藏編號，透過 ___會員編號 + 活動編號 => 收藏編號___
	public List<ActColVO> transform_colVO(String memid,List<ActsVO> actList){
		List <ActColVO> result = new ArrayList<ActColVO>();
		actSvc = new ActsService();
		
		for(ActsVO avo : actList) {
			List<ActColVO> temp = dao.getOne(memid, avo.getActid());
			if( temp != null )
				result.addAll(temp );//一個avo對應一個List<ActColVO>
		}
		
		return result;
	}
	
	public List<ActColVO> getOne(String memid, String actid){
		return dao.getOne(memid, actid);
	}
	
	
	
	
}
