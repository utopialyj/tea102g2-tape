package com.transaction_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction_ListJDBCDAO implements Transaction_ListDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO TRANSACTION_LIST VALUES(TRANSACTION_LIST_SEQ.NEXTVAL, ?, ?, ?, ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM TRANSACTION_LIST";
	public static final String GET_ONE_STMT = "SELECT * FROM TRANSACTION_LIST WHERE TRANS_CODE = ?";
	public static final String DELETE = "DELETE FROM TRANSACTION_LIST WHERE TRANS_CODE = ?";
	public static final String UPDATE = "UPDATE TRANSACTION_LIST SET TRANS_CODE = ?, TRANS_TYPE = ?, "
			+ "TRANS_ANT = ?, TRANS_DATE = ?, TRANS_MEMID = ? WHERE TRANS_CODE = ?";

	@Override
	public void insert(Transaction_ListVO transaction_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			String[] cols = { "TRANS_CODE" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, transaction_listVO.getTrans_type());
			pstmt.setInt(2, transaction_listVO.getTrans_ant());
			pstmt.setDate(3, transaction_listVO.getTrans_date());
			pstmt.setString(4, transaction_listVO.getTrans_memid());

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
	public void update(Transaction_ListVO transaction_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, transaction_listVO.getTrans_code());
			pstmt.setString(2, transaction_listVO.getTrans_type());
			pstmt.setInt(3, transaction_listVO.getTrans_ant());
			pstmt.setDate(4, transaction_listVO.getTrans_date());
			pstmt.setString(5, transaction_listVO.getTrans_memid());
			pstmt.setString(6, transaction_listVO.getTrans_code());

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
	public void delete(String trans_code) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, trans_code);

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
	public Transaction_ListVO findByPrimaryKey(String trans_code) {
		Transaction_ListVO transaction_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, trans_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				transaction_listVO = new Transaction_ListVO(rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getDate(4), rs.getString(5));
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
		return transaction_listVO;
	}

	@Override
	public List<Transaction_ListVO> getAll() {
		List<Transaction_ListVO> list = new ArrayList<Transaction_ListVO>();
		Transaction_ListVO transaction_listVO = null;

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
				transaction_listVO = new Transaction_ListVO(rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getDate(4), rs.getString(5));
				list.add(transaction_listVO); // Store the row in the list
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
