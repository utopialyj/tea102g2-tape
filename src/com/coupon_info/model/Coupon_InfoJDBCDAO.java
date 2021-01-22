package com.coupon_info.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Coupon_InfoJDBCDAO implements Coupon_InfoDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO COUPON_INFO VALUES(COUPON_INFO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM COUPON_INFO ORDER BY CPON_CODE DESC";
	public static final String GET_ONE_STMT = "SELECT * FROM COUPON_INFO WHERE CPON_CODE = ?";
	public static final String DELETE = "DELETE FROM COUPON_INFO WHERE CPON_CODE = ?";
	public static final String UPDATE = "UPDATE COUPON_INFO SET CPON_CODE = ?, PRODUCT_NAME = ?, PRODUCT_BRAND = ?, EXC_CONDITION = ?, "
			+ "EXC_DEADLINE = ?, PRODUCT_PHOTO = ?, PRODUCT_CONTEXT = ?, CPON_TYPE = ?, CPON_AREA_CODE = ?, "
			+ "EXC_COUNT = ?, EXCED_COUNT = ?, SHELF_STS = ?, ONSHELF_DATE = ?, CPON_OFFSHELF_DATE = ? "
			+ "WHERE CPON_CODE = ?";

	@Override
	public void insert(Coupon_InfoVO coupon_infoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			String[] cols = { "CPON_CODE" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, coupon_infoVO.getProduct_name());
			pstmt.setString(2, coupon_infoVO.getProduct_brand());
			pstmt.setInt(3, coupon_infoVO.getExc_condition());
			pstmt.setInt(4, coupon_infoVO.getExe_deadline());
			pstmt.setBytes(5, coupon_infoVO.getProduct_photo());
			pstmt.setString(6, coupon_infoVO.getProduct_context());
			pstmt.setString(7, coupon_infoVO.getCpon_type());
			pstmt.setString(8, coupon_infoVO.getCpon_area_code());
			pstmt.setInt(9, coupon_infoVO.getExc_count());
			pstmt.setInt(10, coupon_infoVO.getExced_count());
			pstmt.setString(11, coupon_infoVO.getShelf_sts());
			pstmt.setDate(12, coupon_infoVO.getOnshelf_date());
			pstmt.setDate(13, coupon_infoVO.getCpon_offshelf_date());

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
	public void update(Coupon_InfoVO coupon_infoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, coupon_infoVO.getCpon_code());
			pstmt.setString(2, coupon_infoVO.getProduct_name());
			pstmt.setString(3, coupon_infoVO.getProduct_brand());
			pstmt.setInt(4, coupon_infoVO.getExc_condition());
			pstmt.setInt(5, coupon_infoVO.getExe_deadline());
			pstmt.setBytes(6, coupon_infoVO.getProduct_photo());
			pstmt.setString(7, coupon_infoVO.getProduct_context());
			pstmt.setString(8, coupon_infoVO.getCpon_type());
			pstmt.setString(9, coupon_infoVO.getCpon_area_code());
			pstmt.setInt(10, coupon_infoVO.getExc_count());
			pstmt.setInt(11, coupon_infoVO.getExced_count());
			pstmt.setString(12, coupon_infoVO.getShelf_sts());
			pstmt.setDate(13, coupon_infoVO.getOnshelf_date());
			pstmt.setDate(14, coupon_infoVO.getCpon_offshelf_date());
			pstmt.setString(15, coupon_infoVO.getCpon_code());

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
	public void delete(String cpon_code) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cpon_code);

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
	public Coupon_InfoVO findByPrimaryKey(String cpon_code) {
		Coupon_InfoVO coupon_infoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cpon_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				coupon_infoVO = new Coupon_InfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5), rs.getBytes(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10),
						rs.getInt(11), rs.getString(12), rs.getDate(13), rs.getDate(14));
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
		return coupon_infoVO;
	}

	@Override
	public List<Coupon_InfoVO> getAll() {
		List<Coupon_InfoVO> list = new ArrayList<Coupon_InfoVO>();
		Coupon_InfoVO coupon_infoVO = null;

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
				coupon_infoVO = new Coupon_InfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5), rs.getBytes(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10),
						rs.getInt(11), rs.getString(12), rs.getDate(13), rs.getDate(14));
				list.add(coupon_infoVO); // Store the row in the list
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
