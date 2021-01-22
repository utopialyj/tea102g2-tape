package com.actapl.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actcol.model.ActColVO;
import com.acts.model.ActsService;
import com.acts.model.ActsVO;
import com.member_info.model.Member_InfoVO;

public class ActAplService {
	private ActAplDAO dao;
	
	public ActAplService() {
		dao = new ActAplJDBCDAO();
	}
	
	
//	////////僅用Map即可, 不用再另外用List包起來///////
//	//先撈出所有成功報名編號
//	//再由編號撈出其對應的活動主題
//	public List<Map<String,String>> get_One_Success_Join(String memid){
//		ActsService actSvc = new ActsService();//從活動主題撈出活動VO，進而撈出活動主題
//		List<Map<String,String>> container = new ArrayList();
//		
//		Map<String,String> temp = null;
//		
//		List<String> success_actid = dao.get_One_All_Success_Actid(memid);
//		for(String actid: success_actid) {
//				temp = new HashMap<>();
//				temp.put(actid, actSvc.getOneAct(actid).getTitle());
//				container.add(temp);
//				
//				temp = null;//一個活動就一個Map，故裝填一次就得清空一次
//		}
//				
//		return container;
//	}

	
	// memid + actid ==> ActAplVO
	public ActAplVO get_OneApl_by_memid_actid(String memid, String actid) {
		return dao.get_OneApl_by_memid_actid(memid, actid);
	}
	
	
	//扣除參與點數
	public void drop_apl_point(Member_InfoVO memVO, String actid) {
		dao.drop_apl_point(memVO, actid);
	}
	
	public ActAplVO addActApl(String memid, String actid, String rson, String sts) {
		ActAplVO actapl = new ActAplVO("", memid, actid, rson, sts);
		dao.insert(actapl);
		
		return actapl;
	}
	
	public ActAplVO updateActApl(String apluid, String memid, String actid, String rson, String sts) // throws RuntimeException{
	{	ActAplVO actcol = new ActAplVO(apluid , memid, actid, rson, sts);
		dao.update(actcol);
		
		return actcol;
	}
	
	public void deleteActApl(String apluid) {
		dao.delete(apluid);
	}
	
	public ActAplVO getOneActApl(String apluid) {
		return dao.findByPK(apluid);
	}
	
	public List<ActAplVO> getAll(){
		return dao.getAll();
	}
	
	public List<ActAplVO> getOneApl(String memid){
		return dao.getOneApl(memid);
	}
	
	
	
	public ActAplVO findByPK(String apluid) {
		return dao.findByPK(apluid);
	};
	
	
	
	
	//重複參加
	public boolean isRepeat_APL(String memid, String actid) {
		return dao.isRepeat_APL(memid, actid);
	}
	
	//PK. & 報名狀態 修改
	public boolean update_sts(ActAplVO avo, String sts) {
		return dao.update_sts(avo, sts);
	}
	
	
	
	
	
	
	
	// 1/16方法改新增至ActsService
//	// 12/30新增
//	public List<String> getApprovedActs(String memid){
//		List<String> listActs = new ArrayList();
//		
//		List<ActAplVO> listAll = getAll();
//		for (ActAplVO actaplVO:listAll) {
//			if (memid.equals(actaplVO.getMemid())) {
//				if ("同意".equals(actaplVO.getSts())) {
//					listActs.add(actaplVO.getActid());
//				}
//			}
//		}
//		return listActs;
//	}
}
