package com.relationship.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class RelationshipJDBCDAO implements RelationshipInterface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@//localhost:1521/XE";
	String username = "TEA102G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO RELATIONSHIP(FRDRELA_UID,FRDINV_MEMID,FRDBEINV_MEMID,FRDINV_STS) VALUES ('SEQ_FRDRELA'||LPAD(SEQ_FRDRELA_UID.NEXTVAL, 7, '0'),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT FRDRELA_UID,FRDINV_MEMID,FRDBEINV_MEMID,FRDINV_STS FROM RELATIONSHIP order by FRDRELA_UID";
	private static final String GET_ONE_STMT = "SELECT FRDRELA_UID,FRDINV_MEMID,FRDBEINV_MEMID,FRDINV_STS FROM RELATIONSHIP WHERE FRDRELA_UID = ?";
	private static final String UPDATE = "UPDATE RELATIONSHIP SET FRDRELA_UID=?, FRDINV_MEMID = ?,FRDBEINV_MEMID = ?,FRDINV_STS = ? where FRDRELA_UID=?";
	private static final String DELETE = "DELETE FROM RELATIONSHIP where FRDRELA_UID = ?";
	private static final String GET_ONE_STS = "SELECT FRDBEINV_MEMID FROM RELATIONSHIP WHERE FRDINV_MEMID = ? and FRDINV_STS= ? ";
//	private static final String memberjoin = "SELECT mem_id from member_info inner join  relationship on member_info.mem_id = relationship.frdinv_memid where memid";
	private static final String del = "DELETE from relationship where frdinv_memid=? and frdbeinv_memid=?";
	private static final String updateSts="update relationship set FRDINV_STS=1 where frdinv_memid=? and frdbeinv_memid=?";
	private static final String notice = "SELECT FRDRELA_UID,FRDINV_MEMID,FRDBEINV_MEMID,FRDINV_STS FROM RELATIONSHIP WHERE FRDBEINV_MEMID = ? and frdinv_sts=0";
	private static final String check = "SELECT FRDRELA_UID,FRDINV_MEMID,FRDBEINV_MEMID,FRDINV_STS FROM RELATIONSHIP WHERE FRDINV_MEMID = ? and FRDBEINV_MEMID = ? and (frdinv_sts=0 or frdinv_sts=1)";
	
	@Override
	public List<RelationshipVO> check(String memUid, String target) {
		List<RelationshipVO> list = new ArrayList<RelationshipVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(check);
			pstmt.setString(1, memUid);
			pstmt.setString(2, target);
//			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RelationshipVO relationshipVO = new RelationshipVO();
				relationshipVO.setFrdrela_uid(rs.getString("FRDRELA_UID"));
				relationshipVO.setFrdinv_memid(rs.getString("FRDINV_MEMID"));
				relationshipVO.setFrdbeinv_memid(rs.getString("FRDBEINV_MEMID"));
				relationshipVO.setFrdinv_sts(rs.getInt("FRDINV_STS"));
				list.add(relationshipVO);
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
		return list;
	}
	
	@Override
	public List<RelationshipVO> notice(String memUid) {
		List<RelationshipVO> list = new ArrayList<RelationshipVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(notice);
			pstmt.setString(1, memUid);
//			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RelationshipVO relationshipVO = new RelationshipVO();
				relationshipVO.setFrdrela_uid(rs.getString("FRDRELA_UID"));
				relationshipVO.setFrdinv_memid(rs.getString("FRDINV_MEMID"));
				relationshipVO.setFrdbeinv_memid(rs.getString("FRDBEINV_MEMID"));
				relationshipVO.setFrdinv_sts(rs.getInt("FRDINV_STS"));
				list.add(relationshipVO);
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
		return list;
	}
	
	
	
	@Override
	public void updateSts(String memUid, String target) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(updateSts);

			pstmt.setString(1, memUid);
			pstmt.setString(2, target);
			
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
	public String insert(RelationshipVO relationshipVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String key = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);

			String[] cols = { "FRDRELA_UID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, relationshipVO.getFrdinv_memid());
			pstmt.setString(2, relationshipVO.getFrdbeinv_memid());
			pstmt.setInt(3, relationshipVO.getFrdinv_sts());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				key = rs.getString(1);
			}

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
		return key;
	}

	@Override
	public void update(RelationshipVO relationshipVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, relationshipVO.getFrdrela_uid());
			pstmt.setString(2, relationshipVO.getFrdinv_memid());
			pstmt.setString(3, relationshipVO.getFrdbeinv_memid());
			pstmt.setInt(4, relationshipVO.getFrdinv_sts());

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
	public RelationshipVO findByPrimaryKey(String frdrela_uid) {

		RelationshipVO relationshipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, frdrela_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				relationshipVO = new RelationshipVO();
				relationshipVO.setFrdrela_uid(rs.getString("frdrela_uid"));
				relationshipVO.setFrdinv_memid(rs.getString("frdinv_memid"));
				relationshipVO.setFrdbeinv_memid(rs.getString("frdbeinv_memid"));
				relationshipVO.setFrdinv_sts(rs.getInt("frdinv_sts"));

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
		return relationshipVO;
	}

	@Override
	public List<RelationshipVO> getAll() {
		List<RelationshipVO> list = new ArrayList<RelationshipVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RelationshipVO relationshipVO = new RelationshipVO();

				relationshipVO.setFrdrela_uid(rs.getString("frdrela_uid"));
				relationshipVO.setFrdinv_memid(rs.getString("frdinv_memid"));
				relationshipVO.setFrdbeinv_memid(rs.getString("frdbeinv_memid"));
				relationshipVO.setFrdinv_sts(rs.getInt("frdinv_sts"));
				list.add(relationshipVO);
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
	public void delete(String frdrela_uid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, frdrela_uid);

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
	public void del(String memUid, String target){
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("1");
		
		try {
		Class.forName(driver);
		con = DriverManager.getConnection(url, username, passwd);
		pstmt = con.prepareStatement(del);
		
		
		pstmt.setString(1, memUid);
		pstmt.setString(2, target);
		
		pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
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
	public Set<RelationshipVO> findBySTS(String frdinv_memid, Integer frdinv_sts) {
		Set<RelationshipVO> list = new LinkedHashSet<RelationshipVO>();
		RelationshipVO relationshipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(GET_ONE_STS);

			pstmt.setString(1, frdinv_memid);
			pstmt.setInt(2, frdinv_sts);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				relationshipVO = new RelationshipVO();
//				relationshipVO.setFrdinv_memid(rs.getString("frdinv_memid"));
				relationshipVO.setFrdbeinv_memid(rs.getString("frdbeinv_memid"));
//				relationshipVO.setFrdinv_sts(rs.getInt("frdinv_sts"));
				list.add(relationshipVO);
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
		return list;
	}

	public static void main(String[] args) {

		RelationshipJDBCDAO dao = new RelationshipJDBCDAO();

//		RelationshipVO relationshipVO1 = new RelationshipVO();
//		relationshipVO1.setFrdinv_memid("MEM0000001");
//		relationshipVO1.setFrdbeinv_memid("MEM0000002");
//		relationshipVO1.setFrdinv_sts(1);
//		dao.insert(relationshipVO1);
//
//		RelationshipVO relationshipVO2 = new RelationshipVO();
//		relationshipVO2.setFrdrela_uid("SEQ_FRDRELA0000005");
//		relationshipVO2.setFrdinv_memid("MEM0000002");
//		relationshipVO2.setFrdbeinv_memid("MEM0000002");
//		relationshipVO2.setFrdinv_sts(0);
//		dao.update(relationshipVO2);
//
//		RelationshipVO relationshipVO3 = dao.findByPrimaryKey("SEQ_FRDRELA0000003");
//		System.out.print(relationshipVO3.getFrdrela_uid() + ",");
//		System.out.print(relationshipVO3.getFrdinv_memid() + ",");
//		System.out.print(relationshipVO3.getFrdbeinv_memid() + ",");
//		System.out.println(relationshipVO3.getFrdinv_sts());
//		System.out.println("------------------------");
//
//		List<RelationshipVO> list = dao.getAll();
//		for (RelationshipVO aRelationshipVO : list) {
//			System.out.print(aRelationshipVO.getFrdrela_uid() + ",");
//			System.out.print(aRelationshipVO.getFrdinv_memid() + ",");
//			System.out.print(aRelationshipVO.getFrdbeinv_memid() + ",");
//			System.out.println(aRelationshipVO.getFrdinv_sts());
//
//		}
//		System.out.println("------------------------");
//		dao.delete("SEQ_FRDRELA0000001");
//
//		Set<RelationshipVO> alist = dao.findBySTS("MEM0000001", 1);
//		for (RelationshipVO yRelationshipVO : alist) {
//			System.out.println(yRelationshipVO);
//		}

//		dao.updateSts("MEM0000001", "MEM0000002");
		
		
//		dao.del("MEM0000001", "MEM0000003");
		
//		List<RelationshipVO> list = dao.notice("MEM0000003");
//		
//		for (RelationshipVO aRelationshipVO : list) {
//			System.out.print(aRelationshipVO.getFrdrela_uid() + ",");
//			System.out.print(aRelationshipVO.getFrdinv_memid() + ",");
//			System.out.print(aRelationshipVO.getFrdbeinv_memid() + ",");
//			System.out.println(aRelationshipVO.getFrdinv_sts());
//
//		}
		
		List<RelationshipVO> list= dao.check("MEM0000001","MEM0000002");
		for (RelationshipVO aRelationshipVO : list) {
			System.out.print(aRelationshipVO.getFrdrela_uid() + ",");
			System.out.print(aRelationshipVO.getFrdinv_memid() + ",");
			System.out.print(aRelationshipVO.getFrdbeinv_memid() + ",");
			System.out.println(aRelationshipVO.getFrdinv_sts());

		}
	}
}
