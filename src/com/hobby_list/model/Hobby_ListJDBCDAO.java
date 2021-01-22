package com.hobby_list.model;

import java.util.*;
import java.sql.*;

public class Hobby_ListJDBCDAO implements Hobby_ListInterface {

	private static final String INSERT_STMT = "INSERT INTO HOBBY_LIST(HOB_CODE,HOB) VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT HOB_CODE,HOB FROM HOBBY_LIST order by HOB_CODE";
	private static final String GET_ONE_STMT = "SELECT HOB_CODE,HOB FROM HOBBY_LIST WHERE HOB_CODE = ?";
	private static final String UPDATE = "UPDATE HOBBY_LIST SET HOB_CODE=?, HOB = ? where HOB_CODE=?";
	private static final String DELETE = "DELETE FROM HOBBY_LIST where HOB_CODE = ?";

	@Override
	public void insert(Hobby_ListVO hobby_listVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, hobby_listVO.getHob_code());
			pstmt.setString(2, hobby_listVO.getHob());
			pstmt.executeUpdate();
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
	public void update(Hobby_ListVO hobby_listVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, hobby_listVO.getHob_code());
			pstmt.setString(2, hobby_listVO.getHob());
			pstmt.setString(3, hobby_listVO.getHob_code());

			pstmt.executeUpdate();

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
	public Hobby_ListVO findByPrimaryKey(String hob_code) {

		Hobby_ListVO hobby_listVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, hob_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				hobby_listVO = new Hobby_ListVO();
				hobby_listVO.setHob_code(rs.getString("hob_code"));
				hobby_listVO.setHob(rs.getString("hob"));

			}
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
		return hobby_listVO;
	}

	@Override
	public List<Hobby_ListVO> getAll() {
		List<Hobby_ListVO> list = new ArrayList<Hobby_ListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Hobby_ListVO hobby_listVO = new Hobby_ListVO();

				hobby_listVO.setHob_code(rs.getString("hob_code"));
				hobby_listVO.setHob(rs.getString("hob"));
				list.add(hobby_listVO);
			}
			pstmt.executeUpdate();
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
				} catch (Exception se) {
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

	@Override
	public void delete(String hob_code) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, hob_code);

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

	public static void main(String[] args) {
		Hobby_ListJDBCDAO dao = new Hobby_ListJDBCDAO();

		Hobby_ListVO hobby_listVO1 = new Hobby_ListVO();
		hobby_listVO1.setHob_code("26");
		hobby_listVO1.setHob("籃球");
		dao.insert(hobby_listVO1);

		Hobby_ListVO hobby_listVO2 = new Hobby_ListVO();
		hobby_listVO2.setHob_code("26");
		hobby_listVO2.setHob("電影");
		dao.update(hobby_listVO2);

		Hobby_ListVO hobby_listVO3 = dao.findByPrimaryKey("12");
		System.out.print(hobby_listVO3.getHob_code() + ",");
		System.out.println(hobby_listVO3.getHob());
		System.out.println("------------------------");

		List<Hobby_ListVO> list = dao.getAll();
		for (Hobby_ListVO aHobby_ListVO : list) {
			System.out.print(aHobby_ListVO.getHob_code() + ",");
			System.out.println(aHobby_ListVO.getHob());

		}
		dao.delete("895");
	}
}
