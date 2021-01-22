package com.acts.model;

import java.sql.Timestamp;
import java.util.List;
import com.actapl.model.ActAplVO;
import com.member_info.model.Member_InfoVO;

public interface ActsDAO {
	public void insert(ActsVO act);
	public void update(ActsVO act);
	public void delete(String actid);
	public ActsVO findByPK(String actid);
	public List<ActsVO> getAll();
	

	
	/*=======過時主辦活動====================================*/
	public List<ActsVO> overTime_own_Act(String memid);
	
	/*?????????????????? 撈出報名中的===================================*/
	
	
	/*?????????????????? 過時的報名===================================*/
	//<(小於)系統時間的 活動就算是過時
	//場上對應每一個報名的actVO已經有了，
	
	/*???????????????????"不同意"的活動幫其保留一個月======================*/
	//public List<ActAplVO> keep_for_diagree_oneMonth(String memid);

	
	/*================"不同意"參加的所有人==================*/ 
	  
	public List<ActAplVO> get_All_Disagree(String actid);
	
	
	/*====================舉辦活動扣除會員100點================*/
	//seesion就存了一個 memVO 直接用VO 做點數調整
	public void drop_mem_pt(Member_InfoVO memVO);
	
	/*======================從活動清單撈出主辦者的大頭貼=======*/
	public byte[] getHeadShot_FromAct(ActsVO avo);
	
	
	/*======================過時活動=======================*/
	public List<ActsVO> overTimeAct();
	
	
	/*====================過時活動不顯示======================*/
	public List<ActsVO> getAllTimeCorrect();
	
	/*=====================暨收藏又報名的人數========================*/
	public int bothColApl(ActsVO act);
	
	/*=====================收藏人數========================*/
	public int colPson(ActsVO act);
	
	/*====================報名人數========================*/
	public int aplPson(ActsVO act);
	
	/*===================時間、地區、類型 整合搜尋==========================*/
	public List<ActsVO> allLimSearch(String start_time,String end_time,String areaCode, String type);
	
	/*================== 類型、地區不受限  ============================*/
	public List<ActsVO> noLimOnTypeArea(String start_time, String end_time);
	
	/*==================類型 不受限 ============================*/
	public List<ActsVO> noLimOnType(String start_time, String end_time, String areaCode);
	
	
	/*================== 類型 搜尋 ============================*/
	public List<ActsVO> noLimOnArea(String start_time, String end_time, String type);
	
	/*================== 會員主辦活動  ============================*/
	public List<ActsVO> getHoldActs(String memid);
	
	/*===================活動撈所有報名的會員=====================*/
	public List<ActAplVO> getAllAplFromAct(String actid);
	
	
	
	
}	
