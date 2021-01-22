package com.gift_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Gift_ListJDBCDAO implements Gift_ListDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO GIFT_LIST VALUES(GIFT_LIST_SEQ.NEXTVAL, ?, ?, ?, ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM GIFT_LIST";
	public static final String GET_ONE_STMT = "SELECT * FROM GIFT_LIST WHERE GIFT_CODE = ?";
	public static final String DELETE = "DELETE FROM GIFT_LIST WHERE GIFT_CODE = ?";
	public static final String UPDATE = "UPDATE GIFT_LIST SET GIFT_CODE = ?, SEND_MEMID = ?, RECEIVE_MEMID = ?, "
			+ "GIFT_DATE = ?, GIFT_CPON_CODE = ? WHERE GIFT_CODE = ?";

	@Override
	public void insert(Gift_ListVO gift_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			String[] cols = { "GIFT_CODE" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, gift_listVO.getSend_memid());
			pstmt.setString(2, gift_listVO.getReceive_memid());
			pstmt.setDate(3, gift_listVO.getGift_date());
			pstmt.setString(4, gift_listVO.getGift_cpon_num());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1);
				System.out.println(key + " INSERTED.");
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
	public void update(Gift_ListVO gift_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, gift_listVO.getGift_code());
			pstmt.setString(2, gift_listVO.getSend_memid());
			pstmt.setString(3, gift_listVO.getReceive_memid());
			pstmt.setDate(4, gift_listVO.getGift_date());
			pstmt.setString(5, gift_listVO.getGift_cpon_num());
			pstmt.setString(6, gift_listVO.getGift_code());

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
	public void delete(String gift_code) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, gift_code);

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
	public Gift_ListVO findByPrimaryKey(String gift_code) {
		Gift_ListVO gift_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, gift_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				gift_listVO = new Gift_ListVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5));
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
		return gift_listVO;
	}

	@Override
	public List<Gift_ListVO> getAll() {
		List<Gift_ListVO> list = new ArrayList<Gift_ListVO>();
		Gift_ListVO gift_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				gift_listVO = new Gift_ListVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5)); // Store the row in the list
				list.add(gift_listVO);
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

}
