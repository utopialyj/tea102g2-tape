package com.member_hobby.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Member_HobbyService {
	private Member_HobbyInterface dao;

	public Member_HobbyService() {
		dao = new Member_HobbyJDBCDAO();
	}

	public Member_HobbyVO addMember_Hobby(String hob_memid, String hob_code) {
		Member_HobbyVO member_hobbyVO = new Member_HobbyVO();

		member_hobbyVO.setHob_memid(hob_memid);
		member_hobbyVO.setHob_code(hob_code);
		dao.insert(member_hobbyVO);

		return member_hobbyVO;

	}

	public Member_HobbyVO updateMember_Hobby(String mem_hob_uid, String hob_memid, String hob_code) {
		Member_HobbyVO member_hobbyVO = new Member_HobbyVO();

		member_hobbyVO.setMem_hob_uid(mem_hob_uid);
		member_hobbyVO.setHob_memid(hob_memid);
		member_hobbyVO.setHob_code(hob_code);
		dao.insert(member_hobbyVO);

		return member_hobbyVO;

	}

	public void delete(String memUid, String hob_code) {
		dao.delete(memUid, hob_code);
	}

	public Member_HobbyVO getOneMember_Hobby(String mem_hob_uid) {
		return dao.findByPrimaryKey(mem_hob_uid);
	}

	public Map<String, List<String>> find_hob_code(List<String> hob_code) {
		return dao.find_hob_code(hob_code);
	}

	public List<Member_HobbyVO> getAll() {
		return dao.getAll();
	}

	public List<String> findmemhob(String hob_memid) {
		return dao.findmemhob(hob_memid);
	}

	public List<Member_HobbyVO> findHobCode(String hob_memid) {
		return dao.findHobCode(hob_memid);
	}
	
	public String findhehe(List<Map.Entry<String, List<String>>> sortedMap) {

		String a = null;
		return a;
	}

	public List<String> getMatchMemberList(String memUid) {
		Member_HobbyService hobSvc = new Member_HobbyService();
//			用自己ID取得自己的興趣
		List<String> list = hobSvc.findmemhob(memUid);
//		用自己的興趣找符合興趣的其他會員和他們的興趣
		LinkedHashMap<String, List<String>> map = (LinkedHashMap) hobSvc.find_hob_code(list);
//		Map<符合對象的ID, 他們的所有興趣>
		ArrayList<String> arr = new ArrayList<String>();
		for (String key : map.keySet()) {

//			用key遍歷所有會員的興趣
			arr = (ArrayList<String>) map.get(key);

//			所有會員的興趣和自己的興趣做交集
			arr.retainAll(list);
			map.put(key, arr);

		}
//		去除自己的資料
		map.remove(memUid);

//			將Map 轉為List <Map.Entry> 並依照Value 的List 長度排序
		List<Map.Entry<String, List<String>>> slist = new ArrayList(map.entrySet());
		slist.sort(new Comparator<Map.Entry<String, List<String>>>() {
			@Override
			public int compare(Map.Entry<String, List<String>> e1, Map.Entry<String, List<String>> e2) {
				Integer size1 = new Integer(e1.getValue().size());
				Integer size2 = new Integer(e2.getValue().size());

//			依照 List.size() 降序排序
				return size2.compareTo(size1);
			}
		});

//			將排序好的 List 依照順序放進 LinkedHashMap
		Map<String, List<String>> sortedMap = new LinkedHashMap();
		for (Map.Entry<String, List<String>> e : slist) {
			sortedMap.put(e.getKey(), e.getValue());
		}

		List<String> a = new ArrayList<String>();
		for (String memberid : sortedMap.keySet()) {

			a.add(memberid);

		}
		return a;
	}

	public List<String> getMatchWithHobby(String memUid, List<String> hobbylist) {
		Member_HobbyService hobSvc = new Member_HobbyService();
//			用自己ID取得自己的興趣
		List<String> list = hobSvc.findmemhob(memUid);
		if (list.isEmpty()) {
			list.add("erroe");
		}
		System.out.println(list);
//		過濾的興趣找符合興趣的其他會員和他們的興趣
		LinkedHashMap<String, List<String>> map = (LinkedHashMap) hobSvc.find_hob_code(hobbylist);
//		Map<符合對象的ID, 他們的所有興趣>
		ArrayList<String> arr = new ArrayList<String>();
		for (String key : map.keySet()) {

//			用key遍歷所有會員的興趣
			arr = (ArrayList<String>) map.get(key);

//			所有會員的興趣和自己的興趣做交集
			arr.retainAll(list);
			map.put(key, arr);

		}
//		去除自己的資料
		map.remove(memUid);

//			將Map 轉為List <Map.Entry> 並依照Value 的List 長度排序
		List<Map.Entry<String, List<String>>> slist = new ArrayList(map.entrySet());
		slist.sort(new Comparator<Map.Entry<String, List<String>>>() {
			@Override
			public int compare(Map.Entry<String, List<String>> e1, Map.Entry<String, List<String>> e2) {
				Integer size1 = new Integer(e1.getValue().size());
				Integer size2 = new Integer(e2.getValue().size());

//			依照 List.size() 降序排序
				return size2.compareTo(size1);
			}
		});

//			將排序好的 List 依照順序放進 LinkedHashMap
		Map<String, List<String>> sortedMap = new LinkedHashMap();
		for (Map.Entry<String, List<String>> e : slist) {
			sortedMap.put(e.getKey(), e.getValue());
		}

		List<String> a = new ArrayList<String>();
		for (String memberid : sortedMap.keySet()) {

			a.add(memberid);

		}
		System.out.println(a);
		return a;
	}

	
	public void addHob(String memUid, String hob_code) {

		
		
		dao.addHob(memUid, hob_code);
	}

}
