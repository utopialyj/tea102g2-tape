package com.relationship.model;

import java.util.List;
import java.util.Set;

public interface RelationshipInterface {
	public String insert(RelationshipVO relationshipVO);
	public void update(RelationshipVO relationshipVO);
	public void delete(String frdrela_uid);
	public RelationshipVO findByPrimaryKey(String frdrela_uid);
	public List<RelationshipVO> getAll();
	public Set<RelationshipVO> findBySTS(String frdinv_memid,Integer frdinv_sts);
	public void del(String memUid, String target);
	public void updateSts(String memUid, String target);
	public List<RelationshipVO> notice(String memUid);
	public List<RelationshipVO> check(String memUid, String target);
}
