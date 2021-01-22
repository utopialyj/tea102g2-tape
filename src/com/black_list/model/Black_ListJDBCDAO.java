package com.black_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Black_ListJDBCDAO implements Black_ListInterface {

	private static final String INSERT_STMT = "INSERT INTO BLACK_LIST(BLK_UID,BLK_MEMID,BEBLK_MEMID) VALUES ('BLK'||LPAD(SEQ_BLK_UID.NEXTVAL, 7, '0'),?,?)";
	private static final String GET_ALL_STMT = "SELECT BLK_UID,BLK_MEMID,BEBLK_MEMID FROM BLACK_LIST order by BLK_UID";
	private static final String GET_ONE_STMT = "SELECT BLK_UID,BLK_MEMID,BEBLK_MEMID FROM BLACK_LIST WHERE BLK_UID = ?";
	private static final String UPDATE = "UPDATE BLACK_LIST SET BLK_UID=?, BLK_MEMID = ?,BEBLK_MEMID = ? where BLK_UID=?";
	private static final String DELETE = "DELETE FROM BLACK_LIST where BLK_UID = ?";

	@Override
	public void insert(Black_ListVO black_listVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, black_listVO.getBlk_memid());
			pstmt.setString(2, black_listVO.getBeblk_memid());

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
	public void update(Black_ListVO black_listVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, black_listVO.getBlk_uid());
			pstmt.setString(2, black_listVO.getBlk_memid());
			pstmt.setString(3, black_listVO.getBeblk_memid());
			pstmt.setString(4, black_listVO.getBlk_uid());

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
	public Black_ListVO findByPrimaryKey(String blk_uid) {

		Black_ListVO black_listVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, blk_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				black_listVO = new Black_ListVO();
				black_listVO.setBlk_uid(rs.getString("blk_uid"));
				black_listVO.setBlk_memid(rs.getString("blk_memid"));
				black_listVO.setBeblk_memid(rs.getString("beblk_memid"));

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
		return black_listVO;
	}

	@Override
	public List<Black_ListVO> getAll() {
		List<Black_ListVO> list = new ArrayList<Black_ListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Black_ListVO black_listVO = new Black_ListVO();

				black_listVO.setBlk_uid(rs.getString("blk_uid"));
				black_listVO.setBlk_memid(rs.getString("blk_memid"));
				black_listVO.setBeblk_memid(rs.getString("beblk_memid"));

				list.add(black_listVO);
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
	public void delete(String blk_uid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, blk_uid);

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

		Black_ListJDBCDAO dao = new Black_ListJDBCDAO();

		Black_ListVO black_listVO1 = new Black_ListVO();

		black_listVO1.setBlk_memid("MEM0000001");
		black_listVO1.setBeblk_memid("MEM0000002");

		dao.insert(black_listVO1);

		Black_ListVO black_listVO2 = new Black_ListVO();
		black_listVO2.setBlk_uid("BLK0000001");
		black_listVO2.setBlk_memid("MEM0000001");
		black_listVO2.setBeblk_memid("MEM0000002");

		dao.update(black_listVO2);

		Black_ListVO black_listVO3 = dao.findByPrimaryKey("BLK0000001");
		System.out.print(black_listVO3.getBlk_uid() + ",");
		System.out.print(black_listVO3.getBlk_memid() + ",");
		System.out.println(black_listVO3.getBeblk_memid());

		System.out.println("------------------------");

		List<Black_ListVO> list = dao.getAll();
		for (Black_ListVO aBlack_ListVO : list) {
			System.out.print(aBlack_ListVO.getBlk_uid() + ",");
			System.out.print(aBlack_ListVO.getBlk_memid() + ",");
			System.out.println(aBlack_ListVO.getBeblk_memid());

		}
		dao.delete("BLK0000001");
	}
}
