package com.actapl.model;

import java.util.List;
import java.util.Map;

import com.actcol.model.ActColVO;
import com.acts.model.ActsVO;
import com.member_info.model.Member_InfoVO;

public interface ActAplDAO {
	public void insert(ActAplVO actapl);
	public void update(ActAplVO actapl);
	public void delete(String apluid);
	public ActAplVO findByPK(String apluid);
	public List<ActAplVO> getAll();
	public List<ActAplVO> getOneApl(String memid);
	
	
	//撈出會員成功報名的所有活動編號
	public List<String> get_One_All_Success_Actid(String memid);
	
	
	//會員ID + 活動編號  ==> 報名編號
	public ActAplVO get_OneApl_by_memid_actid(String memid, String actid);
	
	
	//參加的時候扣除參與點數
	public void drop_apl_point(Member_InfoVO memVO ,String actid);
	
	
	//檢查是不是重複參加，true:重複參加
	public boolean isRepeat_APL(String memid, String actid);
	
	//PK. & 報名狀態 修改
	public boolean update_sts(ActAplVO avo, String sts);
}
 