package com.member_hobby.model;

import java.util.*;

import java.sql.*;

public class Member_HobbyJDBCDAO implements Member_HobbyInterface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@//localhost:1521/XE";
	String username = "TEA102G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MEMBER_HOBBY(MEM_HOB_UID,HOB_MEMID,HOB_CODE) VALUES ('MEM_HOB'||LPAD(SEQ_MEM_HOB_UID.NEXTVAL, 7, '0'),?,?)";
	private static final String GET_ALL_STMT = "SELECT MEM_HOB_UID,HOB_MEMID,HOB_CODE FROM MEMBER_HOBBY order by MEM_HOB_UID";
	private static final String GET_ONE_STMT = "SELECT MEM_HOB_UID,HOB_MEMID,HOB_CODE FROM MEMBER_HOBBY WHERE MEM_HOB_UID = ?";
//	private static final String FIND_HOB = "SELECT HOB_CODE FROM MEMBER_HOBBY where hob_memid='?';";
	private static final String UPDATE = "UPDATE MEMBER_HOBBY SET MEM_HOB_UID=?,HOB_MEMID = ? , HOB_CODE = ? where MEM_HOB_UID=?";
	private static final String DELETE = "DELETE FROM MEMBER_HOBBY where HOB_MEMID = ? AND HOB_CODE = ?";
//	private static final String memberjoin = "SELECT mem_id from member_info inner join  member_hobby on member_info.mem_id = member_hobby.hob_memid";
	private static final String findmemhob = "SELECT * FROM MEMBER_HOBBY WHERE HOB_MEMID = ? order by HOB_CODE";

	@Override
	public void addHob(String memUid,String hob_code) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memUid);
			pstmt.setString(2, hob_code);
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
	public List<Member_HobbyVO> findHobCode(String hob_memid) {
		List<Member_HobbyVO> list = new ArrayList<Member_HobbyVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(findmemhob);
			pstmt.setString(1, hob_memid);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Member_HobbyVO member_hobbyVO = new Member_HobbyVO();
				
				member_hobbyVO.setMem_hob_uid(rs.getString("mem_hob_uid"));
				member_hobbyVO.setHob_memid(rs.getString("hob_memid"));
				member_hobbyVO.setHob_code(rs.getString("hob_code"));
				
				list.add(member_hobbyVO);
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
	public List<String> findmemhob(String hob_memid) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(findmemhob);
			pstmt.setString(1, hob_memid);
//			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				list.add(rs.getString("HOB_CODE"));
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
	public void insert(Member_HobbyVO member_hobbyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member_hobbyVO.getHob_memid());
			pstmt.setString(2, member_hobbyVO.getHob_code());
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
	public void update(Member_HobbyVO member_hobbyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, member_hobbyVO.getMem_hob_uid());
			pstmt.setString(2, member_hobbyVO.getHob_memid());
			pstmt.setString(3, member_hobbyVO.getHob_code());
			pstmt.setString(4, member_hobbyVO.getMem_hob_uid());
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
	public Member_HobbyVO findByPrimaryKey(String mem_hob_uid) {

		Member_HobbyVO member_hobbyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_hob_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				member_hobbyVO = new Member_HobbyVO();
				member_hobbyVO.setMem_hob_uid(rs.getString("mem_hob_uid"));
				member_hobbyVO.setHob_memid(rs.getString("hob_memid"));
				member_hobbyVO.setHob_code(rs.getString("hob_code"));

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
		return member_hobbyVO;
	}

	@Override
	public List<Member_HobbyVO> getAll() {
		List<Member_HobbyVO> listgetall = new ArrayList<Member_HobbyVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member_HobbyVO member_hobbyVO = new Member_HobbyVO();

				member_hobbyVO.setMem_hob_uid(rs.getString("mem_hob_uid"));
				member_hobbyVO.setHob_memid(rs.getString("hob_memid"));
				member_hobbyVO.setHob_code(rs.getString("hob_code"));
				listgetall.add(member_hobbyVO);
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
		return listgetall;
	}

	@Override
	public void delete(String memUid, String hob_code) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, memUid);
			pstmt.setString(2, hob_code);

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

	//用一個或多個興趣找到符合的用戶和所有興趣
	
	@Override
	public Map<String, List<String>> find_hob_code(List<String> hob_code) {
		Map<String, List<String>> list = new LinkedHashMap<String,List<String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String FIND_HOB = "SELECT HOB_MEMID, HOB_CODE FROM MEMBER_HOBBY WHERE HOB_MEMID IN (SELECT HOB_MEMID FROM MEMBER_HOBBY WHERE HOB_CODE in(";
		StringBuilder queryBuilder = new StringBuilder(FIND_HOB);
		try {
			for (int i = 0; i < hob_code.size(); i++) {
				queryBuilder.append("?");
				if (i != hob_code.size() - 1)
					queryBuilder.append(",");
			}
			queryBuilder.append("))");

			FIND_HOB = queryBuilder.toString();

			
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, passwd);
			pstmt = con.prepareStatement(FIND_HOB);

			for (int i = 1; i <= hob_code.size(); i++) {
				pstmt.setString(i, hob_code.get(i - 1).toString());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String memId = rs.getString("hob_memid");
				String hobCode = rs.getString("hob_code");
				if (list.containsKey(memId)) {
					List<String> hobby = list.get(memId);
					hobby.add(hobCode);
					list.put(memId, hobby);
				} else {
					List<String> hobby = new ArrayList<>();
					hobby.add(hobCode);
					list.put(memId, hobby);
				}
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
		Member_HobbyJDBCDAO dao = new Member_HobbyJDBCDAO();
		
//		List<String> list = new ArrayList<String>();
//		list.add("01");
//		list.add("02");
//		list.add("10");
//		list.add("10");
//		list.add("11");
//		Object map = dao.find_hob_code(list);
//		System.out.println(map);
		
		
//		Member_HobbyVO member_hobbyVO1 = new Member_HobbyVO();
//		member_hobbyVO1.setHob_memid("MEM0000001");
//		member_hobbyVO1.setHob_code("03");
//		dao.insert(member_hobbyVO1);
//
//		Member_HobbyVO member_hobbyVO2 = new Member_HobbyVO();
//		member_hobbyVO2.setMem_hob_uid("MEM_HOB0000001");
//		member_hobbyVO2.setHob_memid("MEM0000001");
//		member_hobbyVO2.setHob_code("03");
//		dao.update(member_hobbyVO2);
//
//		Member_HobbyVO member_hobbyVO3 = dao.findByPrimaryKey("MEM_HOB0000003");
//		System.out.println(member_hobbyVO3);
//		System.out.print(member_hobbyVO3.getMem_hob_uid() + ",");
//		System.out.print(member_hobbyVO3.getHob_memid() + ",");
//		System.out.println(member_hobbyVO3.getHob_code());
//		System.out.println("------------------------");
//
//		List<Member_HobbyVO> listgetall = dao.getAll();
//		for (Member_HobbyVO aMember_HobbyVO : listgetall) {
//			System.out.print(aMember_HobbyVO.getMem_hob_uid() + ",");
//			System.out.print(aMember_HobbyVO.getHob_memid() + ",");
//			System.out.println(aMember_HobbyVO.getHob_code());
//		}
//		System.out.println("------------------------");
//
//		dao.delete("MEM_HOB0000001");
//
//		List<String> hob_code = new ArrayList<String>();
//		hob_code.add("01");
//		hob_code.add("03");
//		if (!hob_code.isEmpty()) {
//			List<Member_HobbyVO> list = dao.find_hob_code(hob_code);
//			for (Member_HobbyVO tMember_HobbyVO : list) {
//				System.out.println(tMember_HobbyVO);
//			}
//		}
		
		
//		List<Member_HobbyVO> obj = dao.findHobCode("MEM0000001");
//		System.out.println(obj);
		
		
		dao.delete("MEM0000006", "02");
	}
}