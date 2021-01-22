package com.deposit_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Deposit_ListJDBCDAO implements Deposit_ListDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO DEPOSIT_LIST VALUES(DEPOSIT_LIST_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM DEPOSIT_LIST";
	public static final String GET_ONE_STMT = "SELECT * FROM DEPOSIT_LIST WHERE DEPOSIT_CODE = ?";
	public static final String DELETE = "DELETE FROM DEPOSIT_LIST WHERE DEPOSIT_CODE = ?";
	public static final String UPDATE = "UPDATE DEPOSIT_LIST SET DEPOSIT_CODE = ?, DEPOSIT_NUM = ?, DEPOSIT_DATE = ?, DEPOSIT_MEMEID = ?, "
			+ "PAY_TYPE = ?, CREDIT_NUM = ?, CREDIT_EXPIRY_YY = ?, CREDIT_EXPIRY_MM = ?, CREDIT_SECURITY_NUM = ?, "
			+ "ATM_ID = ?, PAYPMENT_STS = ? WHERE DEPOSIT_CODE = ?";

	@Override
	public void insert(Deposit_ListVO deposit_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			String[] cols = { "DEPOSIT_CODE" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setInt(1, deposit_listVO.getDeposit_num());
			pstmt.setDate(2, deposit_listVO.getDeposit_date());
			pstmt.setString(3, deposit_listVO.getDeposit_memid());
			pstmt.setString(4, deposit_listVO.getPay_type());
			pstmt.setString(5, deposit_listVO.getCredit_num());
			pstmt.setInt(6, deposit_listVO.getCredit_expiry_yy());
			pstmt.setInt(7, deposit_listVO.getCredit_expiry_mm());
			pstmt.setInt(8, deposit_listVO.getCredit_security_num());
			pstmt.setString(9, deposit_listVO.getAtm_id());
			pstmt.setString(10, deposit_listVO.getPayment_sts());

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
	public void update(Deposit_ListVO deposit_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, deposit_listVO.getDeposit_code());
			pstmt.setInt(2, deposit_listVO.getDeposit_num());
			pstmt.setDate(3, deposit_listVO.getDeposit_date());
			pstmt.setString(4, deposit_listVO.getDeposit_memid());
			pstmt.setString(5, deposit_listVO.getPay_type());
			pstmt.setString(6, deposit_listVO.getCredit_num());
			pstmt.setInt(7, deposit_listVO.getCredit_expiry_yy());
			pstmt.setInt(8, deposit_listVO.getCredit_expiry_mm());
			pstmt.setInt(9, deposit_listVO.getCredit_security_num());
			pstmt.setString(10, deposit_listVO.getAtm_id());
			pstmt.setString(11, deposit_listVO.getPayment_sts());
			pstmt.setString(12, deposit_listVO.getDeposit_code());

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
	public void delete(String deposit_code) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, deposit_code);

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
	public Deposit_ListVO findByPrimaryKey(String deposit_code) {
		Deposit_ListVO deposit_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, deposit_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				deposit_listVO = new Deposit_ListVO(rs.getString(1), rs.getInt(2), rs.getDate(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10),
						rs.getString(11));
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
		return deposit_listVO;
	}

	@Override
	public List<Deposit_ListVO> getAll() {
		List<Deposit_ListVO> list = new ArrayList<Deposit_ListVO>();
		Deposit_ListVO deposit_listVO = null;

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
				deposit_listVO = new Deposit_ListVO(rs.getString(1), rs.getInt(2), rs.getDate(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10),
						rs.getString(11)); // Store the row in the list
				list.add(deposit_listVO);
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
