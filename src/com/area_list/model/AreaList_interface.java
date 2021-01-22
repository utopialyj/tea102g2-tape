package com.area_list.model;

import java.io.IOException;
import java.util.List;


public interface AreaList_interface {
	public void insert(AreaListVO area_listVO)throws IOException;
    public void update(AreaListVO area_listVO);
    public void delete(String area_code);
    public AreaListVO findByPrimaryKey(String area_code);
    public List<AreaListVO> getAll();
}
