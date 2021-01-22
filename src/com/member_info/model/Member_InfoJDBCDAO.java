package com.member_info.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.member_info.model.Member_InfoJDBCDAO;
import com.member_info.model.Member_InfoVO;

import oracle.net.aso.e;

public class Member_InfoJDBCDAO implements Member_InfoDAO_interface {

	// 新增
	private static final String INSERT_STMT = "INSERT INTO Member_Info (mem_id,m_email,m_paswd,m_phone,identity_number,idphoto_f,idphoto_b,verify_lv,user_name,gender,m_birthday,horoscope,blood_type,specialty,headshot,school,company,intro,area_code,points,meat_area,meat_minage,meat_maxage) VALUES ('MEM' || lpad(member_info_seq.NEXTVAL,7,'0'), ?, ?, ?, ?, ?, ?,2,?,?,?,?,?,?,?,?,?,?,?,1000,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT mem_id,m_email,m_paswd,m_phone,identity_number,idphoto_f,idphoto_b,verify_lv,user_name,gender,m_birthday,horoscope,blood_type,specialty,headshot,school,company,intro,area_code,points,meat_area,meat_minage,meat_maxage FROM Member_Info order by mem_id";
	// 查一個
	private static final String GET_ONE_STMT = "SELECT mem_id,m_email,m_paswd,m_phone,identity_number,idphoto_f,idphoto_b,verify_lv,user_name,gender,m_birthday,horoscope,blood_type,specialty,headshot,school,company,intro,area_code,points,meat_area,meat_minage,meat_maxage FROM Member_Info where mem_id = ?";
	// 刪除
	private static final String DELETE = "DELETE FROM Member_Info where mem_id = ?";
	// 更改
	private static final String UPDATE = "UPDATE Member_Info set m_email = ?,m_phone = ?,identity_number = ?,idphoto_f= ?,idphoto_b= ?, verify_lv = ?, user_name = ?, gender = ?, m_birthday = ?, horoscope = ?, blood_type = ?, specialty = ?, headshot= ?, school = ?, company = ?, intro= ?, area_code = ?,points = ?, meat_area = ?, meat_minage = ?, meat_maxage = ? where mem_id = ?";
	// 登入：取帳密做驗證
	private static final String GET_LOGIN = "SELECT * FROM Member_Info where m_email = ?";
//			"SELECT mem_id,m_email,m_paswd,verify_lv,user_name FROM Member_Info where m_email = ?";
	// 修改密碼：取舊密碼做驗證
	private static final String GET_PASWD = "SELECT m_paswd FROM Member_Info where m_email = ?";
	// 更改密碼
	private static final String UPDATE_PASWD =

			"UPDATE Member_Info set m_paswd = ?where m_email = ?";
	// 更改權限
	private static final String UPDATE_AUTHORITY = "UPDATE Member_Info SET verify_lv=? WHERE m_email=?";

	// 用主鍵取得照片
	private static final String GET_ONE_PICS = "SELECT idphoto_f, idphoto_b, headshot FROM Member_Info WHERE mem_id=?";

	@Override
	public void insert(Member_InfoVO member_infoVO) {
		System.out.println(1);
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println(2);

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member_infoVO.getM_email());
			pstmt.setString(2, member_infoVO.getM_paswd());
			pstmt.setString(3, member_infoVO.getM_phone());
			pstmt.setString(4, member_infoVO.getIdentity_number());
			pstmt.setBytes(5, member_infoVO.getIdphoto_f());
			pstmt.setBytes(6, member_infoVO.getIdphoto_b());
//			pstmt.setInt(7, member_infoVO.getVerify_lv());
			pstmt.setString(7, member_infoVO.getUser_name());
			pstmt.setString(8, member_infoVO.getGender());
			pstmt.setDate(9, member_infoVO.getM_birthday());
			pstmt.setString(10, member_infoVO.getHoroscope());
			pstmt.setString(11, member_infoVO.getBlood_type());
			pstmt.setString(12, member_infoVO.getSpecialty());
			pstmt.setBytes(13, member_infoVO.getHeadshot());
			pstmt.setString(14, member_infoVO.getSchool());
			pstmt.setString(15, member_infoVO.getCompany());
			pstmt.setString(16, member_infoVO.getIntro());
			pstmt.setString(17, member_infoVO.getArea_code());
//			pstmt.setInt(19, member_infoVO.getPoints());
			pstmt.setString(18, member_infoVO.getMeat_area());
			pstmt.setObject(19, member_infoVO.getMeat_minage());
//			(19, member_infoVO.getMeat_minage());
			pstmt.setObject(20, member_infoVO.getMeat_maxage());
//			Int(20, member_infoVO.getMeat_maxage());
			System.out.println(3);
			pstmt.executeUpdate();
			System.out.println(4);
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(Member_InfoVO member_infoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, member_infoVO.getM_email());
//			pstmt.setString(2, member_infoVO.getM_paswd());
			pstmt.setString(2, member_infoVO.getM_phone());
			pstmt.setString(3, member_infoVO.getIdentity_number());
			pstmt.setBytes(4, member_infoVO.getIdphoto_f());
			pstmt.setBytes(5, member_infoVO.getIdphoto_b());
			pstmt.setInt(6, member_infoVO.getVerify_lv());
			pstmt.setString(7, member_infoVO.getUser_name());
			pstmt.setString(8, member_infoVO.getGender());
			pstmt.setDate(9, member_infoVO.getM_birthday());
			pstmt.setString(10, member_infoVO.getHoroscope());
			pstmt.setString(11, member_infoVO.getBlood_type());
			pstmt.setString(12, member_infoVO.getSpecialty());
			pstmt.setBytes(13, member_infoVO.getHeadshot());
			pstmt.setString(14, member_infoVO.getSchool());
			pstmt.setString(15, member_infoVO.getCompany());
			pstmt.setString(16, member_infoVO.getIntro());
			pstmt.setString(17, member_infoVO.getArea_code());
			pstmt.setInt(18, member_infoVO.getPoints());
			pstmt.setString(19, member_infoVO.getMeat_area());
			pstmt.setInt(20, member_infoVO.getMeat_minage());
			pstmt.setInt(21, member_infoVO.getMeat_maxage());
			pstmt.setString(22, member_infoVO.getMem_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public Member_InfoVO findByPrimaryKey(String mem_id) {

		Member_InfoVO member_infoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				member_infoVO = new Member_InfoVO();
				member_infoVO.setMem_id(rs.getString("mem_id"));
				member_infoVO.setM_email(rs.getString("m_email"));
				member_infoVO.setM_paswd(rs.getString("m_paswd"));
				member_infoVO.setM_phone(rs.getString("m_phone"));
				member_infoVO.setIdentity_number(rs.getString("identity_number"));
				member_infoVO.setIdphoto_f(rs.getBytes("idphoto_f"));
				member_infoVO.setIdphoto_b(rs.getBytes("idphoto_b"));
				member_infoVO.setVerify_lv(rs.getInt("verify_lv"));
				member_infoVO.setUser_name(rs.getString("user_name"));
				member_infoVO.setGender(rs.getString("gender"));
				member_infoVO.setM_birthday(rs.getDate("m_birthday"));
				member_infoVO.setHoroscope(rs.getString("horoscope"));
				member_infoVO.setBlood_type(rs.getString("blood_type"));
				member_infoVO.setSpecialty(rs.getString("specialty"));
				member_infoVO.setHeadshot(rs.getBytes("headshot"));
				member_infoVO.setSchool(rs.getString("school"));
				member_infoVO.setCompany(rs.getString("company"));
				member_infoVO.setIntro(rs.getString("intro"));
				member_infoVO.setArea_code(rs.getString("area_code"));
				member_infoVO.setPoints(rs.getInt("points"));
				member_infoVO.setMeat_area(rs.getString("meat_area"));
				member_infoVO.setMeat_minage(rs.getInt("meat_minage"));
				member_infoVO.setMeat_maxage(rs.getInt("meat_maxage"));

//				pstmt.setString(21, member_infoVO.getMeat_minage());
//				pstmt.setString(22, member_infoVO.getMeat_maxage());

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return member_infoVO;
	}

//
//	
	@Override
	public List<Member_InfoVO> getAll() {
		List<Member_InfoVO> list = new ArrayList<Member_InfoVO>();
		Member_InfoVO member_infoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member_infoVO = new Member_InfoVO();
				member_infoVO.setMem_id(rs.getString("mem_id"));
				member_infoVO.setM_email(rs.getString("m_email"));
				member_infoVO.setM_paswd(rs.getString("m_paswd"));
				member_infoVO.setM_phone(rs.getString("m_phone"));
				member_infoVO.setIdentity_number(rs.getString("identity_number"));
				member_infoVO.setIdphoto_f(rs.getBytes("idphoto_f"));
				member_infoVO.setIdphoto_b(rs.getBytes("idphoto_b"));
				member_infoVO.setVerify_lv(rs.getInt("verify_lv"));
				member_infoVO.setUser_name(rs.getString("user_name"));
				member_infoVO.setGender(rs.getString("gender"));
				member_infoVO.setM_birthday(rs.getDate("m_birthday"));
				member_infoVO.setHoroscope(rs.getString("horoscope"));
				member_infoVO.setBlood_type(rs.getString("blood_type"));
				member_infoVO.setSpecialty(rs.getString("specialty"));
				member_infoVO.setHeadshot(rs.getBytes("headshot"));
				member_infoVO.setSchool(rs.getString("school"));
				member_infoVO.setCompany(rs.getString("company"));
				member_infoVO.setIntro(rs.getString("intro"));
				member_infoVO.setArea_code(rs.getString("area_code"));
				member_infoVO.setPoints(rs.getInt("points"));
				member_infoVO.setMeat_area(rs.getString("meat_area"));
				member_infoVO.setMeat_minage(rs.getInt("meat_minage"));
				member_infoVO.setMeat_maxage(rs.getInt("meat_maxage"));
				list.add(member_infoVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;

	}

	// =================登入驗證方法=============================
	@Override
	public Member_InfoVO findLogin(String m_email) {

		Member_InfoVO member_infoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_LOGIN);

			pstmt.setString(1, m_email);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				member_infoVO = new Member_InfoVO();
				member_infoVO.setMem_id(rs.getString("mem_id"));
				member_infoVO.setM_email(rs.getString("m_email"));
				member_infoVO.setM_paswd(rs.getString("m_paswd"));
				member_infoVO.setM_phone(rs.getString("m_phone"));
				member_infoVO.setIdentity_number(rs.getString("identity_number"));
				member_infoVO.setIdphoto_f(rs.getBytes("idphoto_f"));
				member_infoVO.setIdphoto_b(rs.getBytes("idphoto_b"));
				member_infoVO.setVerify_lv(rs.getInt("verify_lv"));
				member_infoVO.setUser_name(rs.getString("user_name"));
				member_infoVO.setGender(rs.getString("gender"));
				member_infoVO.setM_birthday(rs.getDate("m_birthday"));
				member_infoVO.setHoroscope(rs.getString("horoscope"));
				member_infoVO.setBlood_type(rs.getString("blood_type"));
				member_infoVO.setSpecialty(rs.getString("specialty"));
				member_infoVO.setHeadshot(rs.getBytes("headshot"));
				member_infoVO.setSchool(rs.getString("school"));
				member_infoVO.setCompany(rs.getString("company"));
				member_infoVO.setIntro(rs.getString("intro"));
				member_infoVO.setArea_code(rs.getString("area_code"));
				member_infoVO.setPoints(rs.getInt("points"));
				member_infoVO.setMeat_area(rs.getString("meat_area"));
				member_infoVO.setMeat_minage(rs.getInt("meat_minage"));
				member_infoVO.setMeat_maxage(rs.getInt("meat_maxage"));

//			
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return member_infoVO;
	}

	// =================查密碼方法=============================
	@Override
	public Member_InfoVO findPaswd(String m_email) {

		Member_InfoVO member_infoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_PASWD);

			pstmt.setString(1, m_email);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				member_infoVO = new Member_InfoVO();

				member_infoVO.setM_paswd(rs.getString("m_paswd"));

//				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return member_infoVO;
	}

	@Override
	public void updatePaswd(Member_InfoVO member_infoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_PASWD);

			pstmt.setString(1, member_infoVO.getM_paswd());
			pstmt.setString(2, member_infoVO.getM_email());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// 業者點選email確認驗證後
	@Override
	public void updateFromEMAILBack(String m_email) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_AUTHORITY);

			pstmt.setInt(1, 3);
//				pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			pstmt.setString(2, m_email);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		Member_InfoJDBCDAO dao = new Member_InfoJDBCDAO();

		byte[] pic = getPic("items/images.png");
		byte[] pic2 = getPic("items/brush.png");
		byte[] pic3 = getPic("items/koala.jpg");

		// �s�W
//		Member_InfoVO member_infoVO1 = new Member_InfoVO();
//		member_infoVO1.setM_email("testing@JDBC.com");
//		member_infoVO1.setM_paswd("123456");
//		member_infoVO1.setM_phone("0900123456");
//		member_infoVO1.setIdentity_number("123455");
//		member_infoVO1.setIdphoto_f(pic);
//		member_infoVO1.setIdphoto_b(pic);
//		member_infoVO1.setVerify_lv(1);
//		member_infoVO1.setUser_name("TestJDBC");
//		member_infoVO1.setGender("male");
//		member_infoVO1.setM_birthday(java.sql.Date.valueOf("2005-07-27"));
//		member_infoVO1.setHoroscope("LEO");
//		member_infoVO1.setBlood_type("O");
//		member_infoVO1.setSpecialty("painting");
//		member_infoVO1.setHeadshot(pic);
//		member_infoVO1.setSchool("NCU");
//		member_infoVO1.setCompany("xxxx");
//		member_infoVO1.setIntro("ddddddddd");
//		member_infoVO1.setArea_code("01");
//		member_infoVO1.setPoints(20);
//		member_infoVO1.setMeat_area("01");
//		member_infoVO1.setMeat_minage("18");
//		member_infoVO1.setMeat_maxage("20");
//
//
//		
//		
//		dao.insert(member_infoVO1);

//		// �ק�
//		Member_InfoVO member_infoVO2 = new Member_InfoVO();
//		member_infoVO2.setMem_id("MEM0000005");
//		member_infoVO2.setM_email("testing2@JDBC.com");
//		member_infoVO2.setM_paswd("123456");
//		member_infoVO2.setM_phone("090000003");
//		member_infoVO2.setIdentity_number("A000000003");
//		member_infoVO2.setIdphoto_f(pic);
//		member_infoVO2.setIdphoto_b(pic2);
//		member_infoVO2.setVerify_lv(1);
//		member_infoVO2.setUser_name("Gamma");
//		member_infoVO2.setGender("female");
//		member_infoVO2.setM_birthday(java.sql.Date.valueOf("2002-01-01"));
//		member_infoVO2.setHoroscope("Capricorn");
//		member_infoVO2.setBlood_type("AB");
//		member_infoVO2.setSpecialty("cooking");
//		member_infoVO2.setHeadshot(pic3);
//		member_infoVO2.setSchool("NCCU");
//		member_infoVO2.setCompany("Citi bank");
//		member_infoVO2.setIntro("JDBC測試-更新part");
//		member_infoVO2.setArea_code("01");
//		member_infoVO2.setPoints(0);
//		member_infoVO2.setMeat_area("01");
//		member_infoVO2.setMeat_minage("18");
//		member_infoVO2.setMeat_maxage("20");
//
//
//		
//		dao.update(member_infoVO2);
//
//		// �R��
//		dao.delete("MEM0000005");
//
//		// �d��
//		Member_InfoVO member_infoVO3 = dao.findByPrimaryKey("MEM0000005");
//		System.out.print(member_infoVO3.getMem_id() + ",");
//		System.out.print(member_infoVO3.getM_email() + ",");
//		System.out.print(member_infoVO3.getM_paswd() + ",");
//		System.out.print(member_infoVO3.getM_phone() + ",");
//		System.out.print(member_infoVO3.getIdentity_number() + ",");
//		System.out.print(member_infoVO3.getIdphoto_f() + ",");
//		System.out.print(member_infoVO3.getIdphoto_b() + ",");
//		System.out.print(member_infoVO3.getVerify_lv() + ",");
//		System.out.print(member_infoVO3.getUser_name() + ",");
//		System.out.print(member_infoVO3.getGender() + ",");
//		System.out.print(member_infoVO3.getM_birthday() + ",");
//		System.out.print(member_infoVO3.getHoroscope() + ",");
//		System.out.print(member_infoVO3.getBlood_type() + ",");
//		System.out.print(member_infoVO3.getSpecialty() + ",");
//		System.out.print(member_infoVO3.getHeadshot() + ",");
//		System.out.print(member_infoVO3.getSchool() + ",");
//		System.out.print(member_infoVO3.getCompany() + ",");
//		System.out.print(member_infoVO3.getIntro() + ",");
//		System.out.print(member_infoVO3.getArea_code() + ",");
//		System.out.print(member_infoVO3.getPoints() + ",");
//		System.out.print(member_infoVO3.getMeat_area() + ",");
//		System.out.print(member_infoVO3.getMeat_minage() + ",");
//		System.out.print(member_infoVO3.getMeat_maxage());
//		System.out.println("---------------------");
//
//		// �d��
//		List<Member_InfoVO> list = dao.getAll();
//		for (Member_InfoVO aMem : list) {
//			System.out.print(aMem.getMem_id() + ",");
//			System.out.print(aMem.getM_email() + ",");
//			System.out.print(aMem.getM_paswd() + ",");
//			System.out.print(aMem.getM_phone() + ",");
//			System.out.print(aMem.getIdentity_number() + ",");
//			System.out.print(aMem.getIdphoto_f() + ",");
//			System.out.print(aMem.getIdphoto_b() + ",");
//			System.out.print(aMem.getVerify_lv() + ",");
//			System.out.print(aMem.getUser_name() + ",");
//			System.out.print(aMem.getGender() + ",");
//			System.out.print(aMem.getM_birthday() + ",");
//			System.out.print(aMem.getHoroscope() + ",");
//			System.out.print(aMem.getBlood_type() + ",");
//			System.out.print(aMem.getSpecialty() + ",");
//			System.out.print(aMem.getHeadshot() + ",");
//			System.out.print(aMem.getSchool() + ",");
//			System.out.print(aMem.getCompany() + ",");
//			System.out.print(aMem.getIntro() + ",");
//			System.out.print(aMem.getArea_code() + ",");
//			System.out.print(aMem.getPoints() + ",");
//			System.out.print(aMem.getMeat_area() + ",");
//			System.out.print(aMem.getMeat_minage() + ",");
//			System.out.print(aMem.getMeat_maxage());
//			System.out.println();
//		}

		Member_InfoVO member_infoVO3 = dao.findLogin("FIRSTONE@GMAIL.COM");
		System.out.print(member_infoVO3.getMem_id() + ",");
		System.out.print(member_infoVO3.getM_email() + ",");
		System.out.print(member_infoVO3.getM_paswd() + ",");
//		System.out.print(member_infoVO3.getM_phone() + ",");
//		System.out.print(member_infoVO3.getIdentity_number() + ",");
//		System.out.print(member_infoVO3.getIdphoto_f() + ",");
//		System.out.print(member_infoVO3.getIdphoto_b() + ",");
		System.out.print(member_infoVO3.getVerify_lv() + ",");
		System.out.print(member_infoVO3.getUser_name() + ",");
//		System.out.print(member_infoVO3.getGender() + ",");
//		System.out.print(member_infoVO3.getM_birthday() + ",");
//		System.out.print(member_infoVO3.getHoroscope() + ",");
//		System.out.print(member_infoVO3.getBlood_type() + ",");
//		System.out.print(member_infoVO3.getSpecialty() + ",");
//		System.out.print(member_infoVO3.getHeadshot() + ",");
//		System.out.print(member_infoVO3.getSchool() + ",");
//		System.out.print(member_infoVO3.getCompany() + ",");
//		System.out.print(member_infoVO3.getIntro() + ",");
//		System.out.print(member_infoVO3.getArea_code() + ",");
//		System.out.print(member_infoVO3.getPoints() + ",");
//		System.out.print(member_infoVO3.getMeat_area() + ",");
//		System.out.print(member_infoVO3.getMeat_minage() + ",");
//		System.out.print(member_infoVO3.getMeat_maxage());
		System.out.println("---------------------");
	}

	// 使用byte[]方式
	public static byte[] getPic(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	// =================單純找圖片=============================
	@Override
	public Member_InfoVO findPic(String mem_id) {
		Member_InfoVO member_infoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_PICS);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member_infoVO = new Member_InfoVO();
				member_infoVO.setIdphoto_f(rs.getBytes("idphoto_f"));
				member_infoVO.setIdphoto_b(rs.getBytes("idphoto_b"));
				member_infoVO.setHeadshot(rs.getBytes("headshot"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return member_infoVO;
	}

	// 12/28新增
	private static final String GET_FRIENDS = "SELECT * FROM MEMBER_INFO WHERE MEM_ID IN (SELECT FRDBEINV_MEMID FROM RELATIONSHIP WHERE FRDINV_MEMID = ? and FRDINV_STS= 1)";

	@Override
	public List<Member_InfoVO> getFriends(String mem_id) {
		List<Member_InfoVO> list = new ArrayList<Member_InfoVO>();
		Member_InfoVO member_infoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_FRIENDS);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member_infoVO = new Member_InfoVO();
				member_infoVO.setMem_id(rs.getString("mem_id"));
				member_infoVO.setM_email(rs.getString("m_email"));
				member_infoVO.setM_paswd(rs.getString("m_paswd"));
				member_infoVO.setM_phone(rs.getString("m_phone"));
				member_infoVO.setIdentity_number(rs.getString("identity_number"));
				member_infoVO.setIdphoto_f(rs.getBytes("idphoto_f"));
				member_infoVO.setIdphoto_b(rs.getBytes("idphoto_b"));
				member_infoVO.setVerify_lv(rs.getInt("verify_lv"));
				member_infoVO.setUser_name(rs.getString("user_name"));
				member_infoVO.setGender(rs.getString("gender"));
				member_infoVO.setM_birthday(rs.getDate("m_birthday"));
				member_infoVO.setHoroscope(rs.getString("horoscope"));
				member_infoVO.setBlood_type(rs.getString("blood_type"));
				member_infoVO.setSpecialty(rs.getString("specialty"));
				member_infoVO.setHeadshot(rs.getBytes("headshot"));
				member_infoVO.setSchool(rs.getString("school"));
				member_infoVO.setCompany(rs.getString("company"));
				member_infoVO.setIntro(rs.getString("intro"));
				member_infoVO.setArea_code(rs.getString("area_code"));
				member_infoVO.setPoints(rs.getInt("points"));
				member_infoVO.setMeat_area(rs.getString("meat_area"));
				member_infoVO.setMeat_minage(rs.getInt("meat_minage"));
				member_infoVO.setMeat_maxage(rs.getInt("meat_maxage"));
				list.add(member_infoVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	// 1/5新增 個人頁面更新資料用
	private static final String UPDATE_PROFILE = "UPDATE Member_Info set user_name = ?, gender = ?, horoscope = ?, blood_type = ?, specialty = ?, school = ?, company = ?, intro= ? where mem_id = ?";

	@Override
	public void update_profile(Member_InfoVO member_infoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_PROFILE);

			pstmt.setString(1, member_infoVO.getUser_name());
			pstmt.setString(2, member_infoVO.getGender());
			pstmt.setString(3, member_infoVO.getHoroscope());
			pstmt.setString(4, member_infoVO.getBlood_type());
			pstmt.setString(5, member_infoVO.getSpecialty());
			pstmt.setString(6, member_infoVO.getSchool());
			pstmt.setString(7, member_infoVO.getCompany());
			pstmt.setString(8, member_infoVO.getIntro());
			pstmt.setString(9, member_infoVO.getMem_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// 1/5新增 個人頁面更新個人照片
	private static final String UPDATE_headshot = "UPDATE Member_Info set headshot= ? where mem_id = ?";

	@Override
	public void update_headshot(String Mem_id, byte[] headshot) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_headshot);

			pstmt.setBytes(1, headshot);
			pstmt.setString(2, Mem_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

}
