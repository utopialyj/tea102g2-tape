package com.relationship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;


public class RelationshipService {
	private RelationshipInterface dao;

	public RelationshipService() {
		dao = new RelationshipJDBCDAO();
	}
	

	public RelationshipVO addRelationship(String frdinv_memid, String frdbeinv_memid, Integer frdinv_sts) {
		RelationshipVO relationshipVO = new RelationshipVO();

		relationshipVO.setFrdinv_memid(frdinv_memid);
		relationshipVO.setFrdbeinv_memid(frdbeinv_memid);
		relationshipVO.setFrdinv_sts(frdinv_sts);
		String key = dao.insert(relationshipVO);
		relationshipVO.setFrdrela_uid(key);
		
		return relationshipVO;

	}

	public RelationshipVO updateRelationship(String frdrela_uid, String frdinv_memid, String frdbeinv_memid, Integer frdinv_sts) {
		RelationshipVO relationshipVO = new RelationshipVO();

		relationshipVO.setFrdrela_uid(frdrela_uid);
		relationshipVO.setFrdinv_memid(frdinv_memid);
		relationshipVO.setFrdbeinv_memid(frdbeinv_memid);
		relationshipVO.setFrdinv_sts(frdinv_sts);
		dao.insert(relationshipVO);

		return relationshipVO;

	}
	public void deleteRelationship(String frdrela_uid) {
		dao.delete(frdrela_uid);
	}

	public RelationshipVO getOneRelationship(String frdrela_uid) {
		return dao.findByPrimaryKey(frdrela_uid);
	}
	public List<RelationshipVO> getAll(){
		return dao.getAll();
	}
	
	public Set<RelationshipVO>findBySTS(String frdinv_memid,Integer frdinv_sts) {
		return dao.findBySTS(frdinv_memid,frdinv_sts);
}
	
	public void delete(String memUid) {
		
	}
	public void del(String memUid, String target){
		dao.del(memUid, target);
	}
	public void updateSts(String memUid, String target) {
		dao.updateSts(memUid, target);
	}
	public List<RelationshipVO> notice(String memUid) {
		return dao.notice(memUid);
	}
	public List<RelationshipVO> check(String memUid, String target) {
		return dao.check(memUid, target);
	}
}