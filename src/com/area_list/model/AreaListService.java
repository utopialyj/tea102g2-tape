package com.area_list.model;

import java.io.IOException;
import java.util.List;

public class AreaListService {
	private AreaList_interface dao;
	public AreaListService() {
		dao=new AreaListJDBCDAO();
	}
	public AreaListVO addArea_list(String area_code,String area) throws IOException {
		AreaListVO area_listVO1=new AreaListVO();
		area_listVO1.setArea_code(area_code);
		area_listVO1.setArea(area);
		dao.insert(area_listVO1);
		return area_listVO1;

	}
	
	public AreaListVO updateArea_list(String area_code,String area) throws IOException {
		AreaListVO area_listVO2=new AreaListVO();
		area_listVO2.setArea_code(area_code);
		area_listVO2.setArea(area);
		dao.update(area_listVO2);
		return area_listVO2;

	}
	public void deleteArea_list(String area_code) throws IOException {
		dao.delete(area_code);
	}
	public AreaListVO getOneArea_list(String area_code) throws IOException {
		return dao.findByPrimaryKey(area_code);

	}
	public List<AreaListVO> getAll(){
		return dao.getAll();
	}
	

}
