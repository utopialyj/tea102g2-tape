package com.coupon_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Coupon_ListJDBCDAO implements Coupon_ListDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO COUPON_LIST VALUES(COUPON_LIST_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM COUPON_LIST";
	public static final String GET_ONE_STMT = "SELECT * FROM COUPON_LIST WHERE CPON_NUM = ?";
	public static final String DELETE = "DELETE FROM COUPON_LIST WHERE CPON_NUM = ?";
	public static final String UPDATE = "UPDATE COUPON_LIST SET CPON_NUM = ?, CPON_CODE = ?, CPON_NUM_BDATE = ?, "
			+ "EXC_STS = ?, CPON_EXPIRYDATE = ?, EXC_MEMID = ? WHERE CPON_NUM = ?";

	@Override
	public void insert(Coupon_ListVO coupon_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			String[] cols = { "CPON_NUM" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, coupon_listVO.getCpon_code());
			pstmt.setDate(2, coupon_listVO.getCpon_num_bdate());
			pstmt.setString(3, coupon_listVO.getExc_sts());
			pstmt.setDate(4, coupon_listVO.getCpon_expirydate());
			pstmt.setString(5, coupon_listVO.getExc_memid());

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
	public void update(Coupon_ListVO coupon_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, coupon_listVO.getCpon_num());
			pstmt.setString(2, coupon_listVO.getCpon_code());
			pstmt.setDate(3, coupon_listVO.getCpon_num_bdate());
			pstmt.setString(4, coupon_listVO.getExc_sts());
			pstmt.setDate(5, coupon_listVO.getCpon_expirydate());
			pstmt.setString(6, coupon_listVO.getExc_memid());
			pstmt.setString(7, coupon_listVO.getCpon_num());

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
	public void delete(String cpon_num) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cpon_num);

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
	public Coupon_ListVO findByPrimaryKey(String cpon_num) {
		Coupon_ListVO coupon_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cpon_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				coupon_listVO = new Coupon_ListVO(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getString(4),
						rs.getDate(5), rs.getString(6));
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
		return coupon_listVO;
	}

	@Override
	public List<Coupon_ListVO> getAll() {
		List<Coupon_ListVO> list = new ArrayList<Coupon_ListVO>();
		Coupon_ListVO coupon_listVO = null;

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
				coupon_listVO = new Coupon_ListVO(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getString(4),
						rs.getDate(5), rs.getString(6)); // Store the row in the list
				list.add(coupon_listVO);
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
