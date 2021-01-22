package com.member_authroity.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member_authroity.model.Member_AuthroityDAO_interface;
import com.member_info.model.Member_InfoVO;

public class Member_AuthroityJDBCDAO implements Member_AuthroityDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO Member_Authroity (verify_level,chat_auth,post_auth,meat_auth,point_auth,join_event_auth,host_auth,log_auth) VALUES (?, ?, ?, ?, ?, ?,?,?)";
	private static final String GET_ALL_STMT = "SELECT verify_level,chat_auth,post_auth,meat_auth,point_auth,join_event_auth,host_auth,log_auth FROM Member_Authroity order by verify_level";
	private static final String GET_ONE_STMT = "SELECT verify_level,chat_auth,post_auth,meat_auth,point_auth,join_event_auth,host_auth,log_auth FROM Member_Authroity where verify_level = ?";
	private static final String DELETE = "DELETE FROM Member_Authroity where verify_level = ?";
	private static final String UPDATE = "UPDATE Member_Authroity set chat_auth=?, post_auth=?, meat_auth=?, point_auth=?, join_event_auth=?, host_auth=?,log_auth=? where verify_level = ?";

	@Override
	public void insert(Member_AuthroityVO member_authroityVO) throws IOException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, member_authroityVO.getVerify_level());
			pstmt.setInt(2, member_authroityVO.getChat_auth());
			pstmt.setInt(3, member_authroityVO.getPost_auth());
			pstmt.setInt(4, member_authroityVO.getMeat_auth());
			pstmt.setInt(5, member_authroityVO.getPoint_auth());
			pstmt.setInt(6, member_authroityVO.getJoin_event_auth());
			pstmt.setInt(7, member_authroityVO.getHost_auth());
			pstmt.setInt(8, member_authroityVO.getLog_auth());

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
	public void update(Member_AuthroityVO member_authroityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, member_authroityVO.getChat_auth());
			pstmt.setInt(2, member_authroityVO.getPost_auth());
			pstmt.setInt(3, member_authroityVO.getMeat_auth());
			pstmt.setInt(4, member_authroityVO.getPoint_auth());
			pstmt.setInt(5, member_authroityVO.getJoin_event_auth());
			pstmt.setInt(6, member_authroityVO.getHost_auth());
			pstmt.setInt(7, member_authroityVO.getLog_auth());
			pstmt.setInt(8, member_authroityVO.getVerify_level());

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
	public void delete(Integer verify_level) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			System.out.println("test1");
			pstmt.setInt(1, verify_level);
			System.out.println("test2");
			pstmt.executeUpdate();
			System.out.println("test3");

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
	public Member_AuthroityVO findByPrimaryKey(Integer verify_level) {

		Member_AuthroityVO member_authroityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, verify_level);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				member_authroityVO = new Member_AuthroityVO();
				member_authroityVO.setVerify_level(rs.getInt("verify_level"));
				member_authroityVO.setChat_auth(rs.getInt("chat_auth"));
				member_authroityVO.setPost_auth(rs.getInt("post_auth"));
				member_authroityVO.setMeat_auth(rs.getInt("meat_auth"));
				member_authroityVO.setPoint_auth(rs.getInt("point_auth"));
				member_authroityVO.setJoin_event_auth(rs.getInt("join_event_auth"));
				member_authroityVO.setHost_auth(rs.getInt("host_auth"));
				member_authroityVO.setLog_auth(rs.getInt("log_auth"));

//		return null;
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
		return member_authroityVO;
	}

	@Override
	public List<Member_AuthroityVO> getAll() {
		List<Member_AuthroityVO> list = new ArrayList<Member_AuthroityVO>();
		Member_AuthroityVO member_authroityVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member_authroityVO = new Member_AuthroityVO();
				member_authroityVO.setVerify_level(rs.getInt("verify_level"));
				member_authroityVO.setChat_auth(rs.getInt("chat_auth"));
				member_authroityVO.setPost_auth(rs.getInt("post_auth"));
				member_authroityVO.setMeat_auth(rs.getInt("meat_auth"));
				member_authroityVO.setPoint_auth(rs.getInt("point_auth"));
				member_authroityVO.setJoin_event_auth(rs.getInt("join_event_auth"));
				member_authroityVO.setHost_auth(rs.getInt("host_auth"));
				member_authroityVO.setLog_auth(rs.getInt("log_auth"));

				list.add(member_authroityVO);
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

	public static void main(String[] args) throws IOException {
		Member_AuthroityJDBCDAO dao = new Member_AuthroityJDBCDAO();

		// 新增
//	Member_AuthroityVO member_authroityVO1 = new Member_AuthroityVO();
//	member_authroityVO1.setVerify_level(0);
//	member_authroityVO1.setChat_auth(1);
//	member_authroityVO1.setPost_auth(1);
//	member_authroityVO1.setMeat_auth(1);
//	member_authroityVO1.setPoint_auth(1);
//	member_authroityVO1.setJoin_event_auth(1);
//	member_authroityVO1.setHost_auth(1);
//	member_authroityVO1.setLog_auth(1);
//	
//	dao.insert(member_authroityVO1);

		// 修改
//	Member_AuthroityVO member_authroityVO2 = new Member_AuthroityVO();
//	
//	member_authroityVO2.setVerify_level(0);
//	member_authroityVO2.setChat_auth(0);
//	member_authroityVO2.setPost_auth(0);
//	member_authroityVO2.setMeat_auth(0);
//	member_authroityVO2.setPoint_auth(0);
//	member_authroityVO2.setJoin_event_auth(0);
//	member_authroityVO2.setHost_auth(0);
//	member_authroityVO2.setLog_auth(0);
//	
//	dao.update(member_authroityVO2);

		// 刪除
//	dao.delete(0);

		// 查詢by主鍵
		Member_AuthroityVO member_authroityVO3 = dao.findByPrimaryKey(1);
		System.out.print(member_authroityVO3.getVerify_level() + ",");
		System.out.print(member_authroityVO3.getChat_auth() + ",");
		System.out.print(member_authroityVO3.getPost_auth() + ",");
		System.out.print(member_authroityVO3.getMeat_auth() + ",");
		System.out.print(member_authroityVO3.getPoint_auth() + ",");
		System.out.print(member_authroityVO3.getJoin_event_auth() + ",");
		System.out.print(member_authroityVO3.getHost_auth() + ",");
		System.out.print(member_authroityVO3.getLog_auth());
		System.out.println("---------------------");

//查詢全部
//	List<Member_AuthroityVO> list = dao.getAll();
//	for (Member_AuthroityVO aMem : list) {
//		System.out.print(aMem.getVerify_level() + ",");
//		System.out.print(aMem.getChat_auth() + ",");
//		System.out.print(aMem.getPost_auth() + ",");
//		System.out.print(aMem.getMeat_auth() + ",");
//		System.out.print(aMem.getPoint_auth() + ",");
//		System.out.print(aMem.getJoin_event_auth() + ",");
//		System.out.print(aMem.getHost_auth() + ",");
//		System.out.print(aMem.getLog_auth() );
//		System.out.println();
//	}

	}

}
