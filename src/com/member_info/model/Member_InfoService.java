package com.member_info.model;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.member_authroity.model.Member_AuthroityVO;

public class Member_InfoService {
	private Member_InfoDAO_interface dao;

	public Member_InfoService() {
		dao = new Member_InfoJDBCDAO();
	}
	
	public Member_InfoVO addM_Info(String m_email,String m_paswd,String m_phone,String identity_number,String user_name,String gender,Date m_birthday,String horoscope,String blood_type,String specialty,byte[] headshot,String school,String company,String intro,String area_code,String meat_area,Integer meat_minage,Integer meat_maxage) {
		Member_InfoVO member_infoVO = new Member_InfoVO();
		member_infoVO.setM_email(m_email);
		member_infoVO.setM_paswd(m_paswd);
		member_infoVO.setM_phone(m_phone);
		member_infoVO.setIdentity_number(identity_number);
//		member_infoVO.setIdphoto_f(idphoto_f);
//		member_infoVO.setIdphoto_b(idphoto_b);
//		member_infoVO.setVerify_lv(verify_lv);
		member_infoVO.setUser_name(user_name);
		member_infoVO.setGender(gender);
		member_infoVO.setM_birthday(m_birthday);
		member_infoVO.setHoroscope(horoscope);
		member_infoVO.setBlood_type(blood_type);
		member_infoVO.setSpecialty(specialty);
		member_infoVO.setHeadshot(headshot);
		member_infoVO.setSchool(school);
		member_infoVO.setCompany(company);
		member_infoVO.setIntro(intro);
		member_infoVO.setArea_code(area_code);
//		member_infoVO.setPoints(points);
		member_infoVO.setMeat_area(meat_area);
		member_infoVO.setMeat_minage(meat_minage);
		member_infoVO.setMeat_maxage(meat_maxage);

		
		
		dao.insert(member_infoVO);
		return member_infoVO;

	}
	public Member_InfoVO updateM_Info(String mem_id, String m_email,String m_phone,String identity_number,byte[] idphoto_f,byte[] idphoto_b,Integer verify_lv, String user_name,String gender,Date m_birthday,String horoscope,String blood_type,String specialty,byte[] headshot,String school,String company,String intro,String area_code,Integer points, String meat_area,Integer meat_minage,Integer meat_maxage) {
		Member_InfoVO member_infoVO2 = new Member_InfoVO();
		member_infoVO2.setMem_id(mem_id);
		member_infoVO2.setM_email(m_email);
//		member_infoVO2.setM_paswd(m_paswd);
		member_infoVO2.setM_phone(m_phone);
		member_infoVO2.setIdentity_number(identity_number);
		member_infoVO2.setIdphoto_f(idphoto_f);
		member_infoVO2.setIdphoto_b(idphoto_b);
		member_infoVO2.setVerify_lv(verify_lv);
		member_infoVO2.setUser_name(user_name);
		member_infoVO2.setGender(gender);
		member_infoVO2.setM_birthday(m_birthday);
		member_infoVO2.setHoroscope(horoscope);
		member_infoVO2.setBlood_type(blood_type);
		member_infoVO2.setSpecialty(specialty);
		member_infoVO2.setHeadshot(headshot);
		member_infoVO2.setSchool(school);
		member_infoVO2.setCompany(company);
		member_infoVO2.setIntro(intro);
		member_infoVO2.setArea_code(area_code);
		member_infoVO2.setPoints(points);
		member_infoVO2.setMeat_area(meat_area);
		member_infoVO2.setMeat_minage(meat_minage);
		member_infoVO2.setMeat_maxage(meat_maxage);

		dao.update(member_infoVO2);
		return member_infoVO2;

	}
	public void deleteM_Info(String mem_id,String m_email,String m_paswd,String m_phone,String identity_number,byte[] idphoto_f,byte[] idphoto_b,Integer verify_lv,String user_name,String gender,Date m_birthday,String horoscope,String blood_type,String specialty,byte[] headshot,String school,String company,String intro,String area_code,Integer points,String meat_area,String meat_minage,String meat_maxage) {
		dao.delete(mem_id);

	}

	public Member_InfoVO getOneM_Info(String mem_id) {
		return dao.findByPrimaryKey(mem_id);

	}
	
	public List<Member_InfoVO> getAll(){
		return dao.getAll();
	}
	public Member_InfoVO findLogin(String m_email) throws IOException {
		return dao.findLogin(m_email);

	}
	
	public Member_InfoVO findPaswd(String m_email) throws IOException {
		return dao.findPaswd(m_email);

	}
	public Member_InfoVO updatePaswd(String m_paswd,String m_email) {
		Member_InfoVO member_infoVO2 = new Member_InfoVO();
		
		member_infoVO2.setM_paswd(m_paswd);
		member_infoVO2.setM_email(m_email);
		
		

		dao.updatePaswd(member_infoVO2);
		return member_infoVO2;

	}
	//會員點選email確認驗證後
	public void updateFromEMAILBack(String m_email) {
		dao.updateFromEMAILBack(m_email);
		}

	public Member_InfoVO findPic(String mem_id) {
		return dao.findPic(mem_id);
	}
	
	
//	public static void main(String[] args) {
//		Member_InfoService mis = new Member_InfoService();
//		
//		mis.updateM_Info("MEM0000017", "xxx.xxxx0000@gmail.com", "333333", null, null, null, null, 1, null, null, null, null, null, null, null, null, null, null, null,1, null, null, null);
//	}
	
	
	
	
	
	
	
	// 12/28新增
	public List<Member_InfoVO> getFriends(String mem_id){
		return dao.getFriends(mem_id);
	}
	
	//1/5新增
	public void update_profile(Member_InfoVO member_infoVO) {
		dao.update_profile(member_infoVO);
	};
	
	
	//1/7新增
	public void update_headshot(String Mem_id, byte[] headshot) {
		dao.update_headshot(Mem_id, headshot);
	};
}
