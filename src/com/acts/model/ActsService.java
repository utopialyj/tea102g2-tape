package com.acts.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.actapl.model.ActAplDAO;
import com.actapl.model.ActAplService;
import com.actapl.model.ActAplVO;
import com.member_info.model.Member_InfoVO;

public class ActsService {
	private ActsDAO dao;
	private ActAplService aplSvc;
	
	public ActsService() {
		dao = new ActsJDBCDAO();
	}

	// 偷懶insert
//	public ActsVO addAct(ActsVO act) {
//		dao.insert(act);
//		return act;
//}
	
	
	

//	aas.getOneApl(memid);//這個會員的所有報名
//	
	
	
	//過時的ActVO_轉_ActApl
	public List<ActAplVO> ActVO_Tranfs_ActApl(List<ActsVO> pastList, String memid){
		List<ActAplVO> resultList = new ArrayList<ActAplVO>();
		ActAplVO temp = null;
		ActAplService aas = new ActAplService(); 
		ActsService as = new ActsService();
		for(ActsVO avo : pastList) {
			temp = aas.get_OneApl_by_memid_actid(memid, avo.getActid());
			if(temp != null) {
			resultList.add(temp );
			}
		}
		return resultList;
	}
	
	
	
	/*---------------------過期的主辦活動 -------------------*/
	public List<ActsVO> overTime_own_Act(String memid){
		return dao.overTime_own_Act(memid);
	}

	public ActsVO addAct(String memid, String shsts, Timestamp time, String title, String sts, String type, String cont,
			byte[] pic, Integer peop, String areacd, Integer hot, String loc, String store, Integer bgt, Integer pts,
			String rpsts) {
		ActsVO act = new ActsVO("", memid, shsts, time, title, sts, type, cont, pic, peop, areacd, hot, loc, store, bgt,
				pts, rpsts);
		dao.insert(act);

		return act;
	}

	public ActsVO updateAct(String actid, String memid, String shsts, Timestamp time, String title, String sts,
			String type, String cont, byte[] pic, Integer peop, String areacd, Integer hot, String loc, String store,
			Integer bgt, Integer pts, String rpsts) {
		ActsVO act = new ActsVO(actid, memid, shsts, time, title, sts, type, cont, pic, peop, areacd, hot, loc, store,
				bgt, pts, rpsts);
		dao.update(act);

		return act;
	}

	public void deleteAct(String actid) {
		dao.delete(actid);
	}

	public ActsVO getOneAct(String actid) {
		return dao.findByPK(actid);
	}

	public List<ActsVO> getAll() {
		return dao.getAll();
	}

	
	
	/*---------------------主辦活動扣除100點數 -------------------*/
	public void drop_mem_pt(Member_InfoVO memVO) {
		dao.drop_mem_pt(memVO);
	}
	
	
	/*---------------------取得主辦者的大頭貼---------------------*/
	public byte[] getHeadShot_FromAct(ActsVO avo) {
		return dao.getHeadShot_FromAct(avo);
	}
	
	
	/*----------------------取得報名人數-------------------------*/
	public int getAplPson(ActsVO act) {
		int APL_ACT_PERSON = dao.aplPson(act);
		return APL_ACT_PERSON;
	}

	/*----------------------取得收藏人數-------------------------*/
	public int getColPson(ActsVO act) {
		int COL_ACT_PERSON = dao.colPson(act);
		return COL_ACT_PERSON;
	}

	/*-----------------------暨收藏又報名人數-----------------------------*/
	public int getBothColPson(ActsVO act) {
		int BOTH_COL_APL_PERSON = dao.bothColApl(act);
		return BOTH_COL_APL_PERSON;
	}

	/*----------------------取得活動熱度-------------------------*/
	public int getHot(ActsVO act) {
		int APL_ACT_PERSON = dao.aplPson(act);
		int BOTH_COL_APL_PERSON = dao.bothColApl(act);
		int COL_ACT_PERSON = dao.colPson(act);
		return APL_ACT_PERSON + COL_ACT_PERSON - BOTH_COL_APL_PERSON;
	}

	/*---------------------時間、地區、類型 搜尋--------------------------------*/
	public List<ActsVO> allLimSearch(String start_time, String end_time, String areaCode, String type) {
		return dao.allLimSearch(start_time, end_time, areaCode, type);
	}

	/*--------------------不限制類型、地區---------------------------------------------*/
	public List<ActsVO> noLimOnTypeArea(String start_time, String end_time) {
		return dao.noLimOnTypeArea(start_time, end_time);
	}

	/*--------------------不限制類型-------------------------------------------------*/
	public List<ActsVO> noLimOnType(String start_time, String end_time, String areaCode) {
		return dao.noLimOnType(start_time, end_time, areaCode);
	}

	/*--------------------不限制地區-------------------------------------------------*/
	public List<ActsVO> noLimOnArea(String start_time, String end_time, String type) {
		return dao.noLimOnArea(start_time, end_time, type);
	}

	/*--------------------會員主辦的所有活動---------------------------------------------*/
	public List<ActsVO> getHoldActs(String memid) {
		return dao.getHoldActs(memid);
	}
	
	
	/*-------------------活動取得所有報名會員--------------------------------------------*/
	public List<ActAplVO> getAllAplFromAct(String actid){
		return dao.getAllAplFromAct(actid);
	}
	
	/*---------------------所有不同意成員--------------------------------------------*/
	public List<ActAplVO> get_All_Disagree(String actid){
		return dao.get_All_Disagree(actid);
	}
	
	
	//過時活動
	public List<ActsVO> overtime_Act(){
		return dao.overTimeAct();
	}
	
	
	
	//篩掉 過時的活動
	public List<ActsVO> get_all_by_time(){
		return dao.getAllTimeCorrect();
	}
	
	
	//報名過的活動VO清單
	public List<ActsVO> get_all_inAct(String memid){
		aplSvc = new  ActAplService();
		
		//會員的報名清單
		List<ActAplVO> apl_list = aplSvc.getOneApl(memid); 
			//System.out.println("apl_list is null ?" + apl_list == null);
		
		//會員報名的(活動)編號清單
		List<String> apl_actid = new ArrayList<String>();
		for(ActAplVO aplvo : apl_list) {
			apl_actid.add(aplvo.getActid());
		}
		
		
		List<ActsVO> inActVOList = new ArrayList<ActsVO>();//會員報名過的活動VO清單
		
		for(String actid : apl_actid) {
			inActVOList.add(dao.findByPK(actid));
		}
		
		return inActVOList;
				
	}
	
	
	
	
	
	
	
	
	//篩掉 過時、報名過的活動 
	public List<ActsVO> get_all_can_join(String memid){
		aplSvc = new ActAplService();
		
		//扣掉過時
		List<ActsVO> time_ok_list = dao.getAllTimeCorrect();//過時活動不顯示
			//System.out.println("time_ok_list is OK");
		
		//會員的報名清單
		List<ActAplVO> apl_list = aplSvc.getOneApl(memid); 
			//System.out.println("apl_list is null ?" + apl_list == null);
		
		//會員報名的(活動)編號清單
		List<String> apl_actid = new ArrayList<String>();
		for(ActAplVO aplvo : apl_list) {
			apl_actid.add(aplvo.getActid());
		}
		
		
		List<ActsVO> inActVOList = new ArrayList<ActsVO>();//會員報名過的活動VO清單
		
		for(String actid : apl_actid) {
			inActVOList.add(dao.findByPK(actid));
		}
		
		if(time_ok_list != null &&  inActVOList != null) {
			time_ok_list.removeAll(inActVOList);
		}
		
		return time_ok_list;
	}
	
	
	List<String> getActid_From_listVO(List<ActsVO> list){
		List<String> actidList = null; 
		
		for(ActsVO avo : list) {
			actidList.add(avo.getActid());
		}
		
		return actidList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Map<String, String> getApprovedActs(String memid) {
		Map<String, String> actsMap = new Hashtable<String, String>();
		ActsService actsSvc = new ActsService();
		ActAplService ActsAplSvc = new ActAplService();
		
		List<ActAplVO> listAll = ActsAplSvc.getAll();
		for (ActAplVO actaplVO : listAll) {
			if (memid.equals(actaplVO.getMemid()) && "同意".equals(actaplVO.getSts())) {
				ActsVO actsVO = actsSvc.getOneAct(actaplVO.getActid());
				actsVO.getTitle();
				actsMap.put(actsVO.getActid(), actsVO.getTitle());
			}
		}
		List<ActsVO> holdActs = getHoldActs(memid);
		
		for (ActsVO act : holdActs) {
			actsMap.put(act.getActid(), act.getTitle());
		}
		
		return actsMap;
	}
	
	public static void main(String[] args) {
		ActsService actsSvc = new ActsService();
		System.out.println(actsSvc.getApprovedActs("MEM0000001"));
	}
}
