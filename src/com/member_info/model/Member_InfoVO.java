package com.member_info.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.Arrays;

public class Member_InfoVO implements Serializable{
	
	
//	// 12/28新增
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mem_id == null) ? 0 : mem_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member_InfoVO other = (Member_InfoVO) obj;
		if (mem_id == null) {
			if (other.mem_id != null)
				return false;
		} else if (!mem_id.equals(other.mem_id))
			return false;
		return true;
	}
	
	private String mem_id;
	private String m_email;
	private String m_paswd;
	private String m_phone;
	private String identity_number;
	private byte[] idphoto_f;
	private byte[] idphoto_b;
	private Integer verify_lv;
	private String user_name;
	private String gender;
	private Date m_birthday;
	private String horoscope;
	private String blood_type;
	private String specialty;
	private byte[] headshot;
	private String school;
	private String company;
	private String intro;
	private String area_code;
	private Integer points;
	private String meat_area;
	private Integer meat_minage;
	private Integer meat_maxage;
	
	
	
	
	
	
	
	
	
	// 12/28新增
	@Override
	public String toString() {
		return "Member_InfoVO [mem_id=" + mem_id + ", m_email=" + m_email + ", m_paswd=" + m_paswd + ", m_phone="
				+ m_phone + ", identity_number=" + identity_number + ", idphoto_f=" + Arrays.toString(idphoto_f)
				+ ", idphoto_b=" + Arrays.toString(idphoto_b) + ", verify_lv=" + verify_lv + ", user_name=" + user_name
				+ ", gender=" + gender + ", m_birthday=" + m_birthday + ", horoscope=" + horoscope + ", blood_type="
				+ blood_type + ", specialty=" + specialty + ", headshot=" + Arrays.toString(headshot) + ", school="
				+ school + ", company=" + company + ", intro=" + intro + ", area_code=" + area_code + ", points="
				+ points + ", meat_area=" + meat_area + ", meat_minage=" + meat_minage + ", meat_maxage=" + meat_maxage
				+ "]";
	}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_paswd() {
		return m_paswd;
	}
	public void setM_paswd(String m_paswd) {
		this.m_paswd = m_paswd;
	}
	public String getM_phone() {
		return m_phone;
	}
	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}
	public String getIdentity_number() {
		return identity_number;
	}
	public void setIdentity_number(String identity_number) {
		this.identity_number = identity_number;
	}
	public byte[] getIdphoto_f() {
		return idphoto_f;
	}
	public void setIdphoto_f(byte[] idphoto_f) {
		this.idphoto_f = idphoto_f;
	}
	public byte[] getIdphoto_b() {
		return idphoto_b;
	}
	public void setIdphoto_b(byte[] idphoto_b) {
		this.idphoto_b = idphoto_b;
	}
	public Integer getVerify_lv() {
		return verify_lv;
	}
	public void setVerify_lv(Integer verify_lv) {
		this.verify_lv = verify_lv;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getM_birthday() {
		return m_birthday;
	}
	public void setM_birthday(Date m_birthday) {
		this.m_birthday = m_birthday;
	}
	public String getHoroscope() {
		return horoscope;
	}
	public void setHoroscope(String horoscope) {
		this.horoscope = horoscope;
	}
	public String getBlood_type() {
		return blood_type;
	}
	public void setBlood_type(String blood_type) {
		this.blood_type = blood_type;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public byte[] getHeadshot() {
		return headshot;
	}
	public void setHeadshot(byte[] headshot) {
		this.headshot = headshot;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getMeat_area() {
		return meat_area;
	}
	public void setMeat_area(String meat_area) {
		this.meat_area = meat_area;
	}
	public Integer getMeat_minage() {
		return meat_minage;
	}
	public void setMeat_minage(Integer meat_minage) {
		this.meat_minage = meat_minage;
	}
	public Integer getMeat_maxage() {
		return meat_maxage;
	}
	public void setMeat_maxage(Integer meat_maxage) {
		this.meat_maxage = meat_maxage;
	}
	
	
	
	

	
	
	
//	public String getImage() {
//		
//	}
	





	

}
