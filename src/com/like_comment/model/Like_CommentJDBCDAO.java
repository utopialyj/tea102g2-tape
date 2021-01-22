package com.like_comment.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;
import com.post.model.PostVO;

public class Like_CommentJDBCDAO implements Like_CommentDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO like_comment VALUES('CMNT_LK'||LPAD(SEQ_CMNT_LK_UID.NEXTVAL, 7, '0'), ?, ?,? )";
	private static final String GET_ALL_STMT = "SELECT cmnt_lk_uid,cmnt_lk_memid,cmnt_code,cmnt_lk_date FROM like_comment ORDER BY cmnt_lk_uid";
	private static final String GET_ONE_STMT = "SELECT cmnt_lk_uid,cmnt_lk_memid,cmnt_code,cmnt_lk_date FROM like_comment WHERE cmnt_lk_uid =?";
	private static final String UPDATE = "UPDATE like_comment SET cmnt_lk_memid=?, cmnt_code=?, cmnt_lk_date=? WHERE cmnt_lk_uid = ?";
	private static final String DELETE = "DELETE FROM like_comment WHERE cmnt_code = ? AND cmnt_lk_memid = ?";
	private static final String GET_CMNT_LIKE_MEMBERS = "SELECT cmnt_lk_memid FROM like_comment WHERE cmnt_code = ?";

	public void insert(Like_CommentVO lcVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lcVO.getCmnt_lk_memid());
			pstmt.setString(2, lcVO.getCmnt_code());
			pstmt.setTimestamp(3, lcVO.getCmnt_lk_date());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (Exception se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public List<Like_CommentVO> getAll() {
		List<Like_CommentVO> list = new ArrayList<Like_CommentVO>();
		Like_CommentVO lkVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lkVO = new Like_CommentVO();
				lkVO.setCmnt_lk_uid(rs.getString("cmnt_lk_uid"));
				lkVO.setCmnt_lk_memid(rs.getString("cmnt_lk_memid"));
				lkVO.setCmnt_code(rs.getString("cmnt_code"));
				lkVO.setCmnt_lk_date(rs.getTimestamp("cmnt_lk_date"));

				list.add(lkVO);
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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

	public Like_CommentVO findByPrimaryKey(String cmnt_lk_uid) {

		Like_CommentVO lcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cmnt_lk_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lcVO = new Like_CommentVO();
				lcVO.setCmnt_lk_uid(rs.getString("cmnt_lk_uid"));
				lcVO.setCmnt_lk_memid(rs.getString("cmnt_lk_memid"));
				lcVO.setCmnt_code(rs.getString("cmnt_code"));
				lcVO.setCmnt_lk_date(rs.getTimestamp("cmnt_lk_date"));

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return lcVO;
	}

	public void update(Like_CommentVO like_CommentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, like_CommentVO.getCmnt_lk_memid());
			pstmt.setString(2, like_CommentVO.getCmnt_code());
			pstmt.setTimestamp(3, like_CommentVO.getCmnt_lk_date());
			pstmt.setString(4, like_CommentVO.getCmnt_lk_uid());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
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

	public void delete(String cmnt_code, String cmnt_lk_memid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cmnt_code);
			pstmt.setString(2, cmnt_lk_memid);

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

//取得回覆點讚的人	
	public List<String> getLikeMembersByCmntcode(String cmnt_code) {

		Member_InfoVO member_infoVO = null;
		Member_InfoService member_infoSvc = null;
		String memid = null;
		List<String> like_cmnt_members = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_CMNT_LIKE_MEMBERS);

			pstmt.setString(1, cmnt_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				member_infoVO = new Member_InfoVO();
				member_infoSvc = new Member_InfoService();

				memid = rs.getString("lk_memid");

				member_infoVO = (Member_InfoVO) member_infoSvc.getOneM_Info(memid);
				like_cmnt_members.add(member_infoVO.getMem_id());

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return like_cmnt_members;
	}

	public static void main(String[] args) {

		Like_CommentJDBCDAO dao = new Like_CommentJDBCDAO();

		Timestamp d = new Timestamp(System.currentTimeMillis());
		Like_CommentVO lcVO1 = new Like_CommentVO();

		lcVO1.setCmnt_lk_memid("MEM0000002");
		lcVO1.setCmnt_code("CMNT0000002");
		lcVO1.setCmnt_lk_date(d);

		dao.insert(lcVO1);

		List<Like_CommentVO> list = dao.getAll();
		for (Like_CommentVO like : list) {
			System.out.println(like.getCmnt_lk_uid());
			System.out.println(like.getCmnt_lk_memid());
			System.out.println(like.getCmnt_code());
			System.out.println(like.getCmnt_lk_date());

			System.out.println();
		}

		Like_CommentVO lcVO2 = dao.findByPrimaryKey("CMNT_LK0000001");
		System.out.println("+++++++++++++++++++");
		System.out.println(lcVO2.getCmnt_lk_uid() + ",");
		System.out.println(lcVO2.getCmnt_lk_memid() + ",");
		System.out.println(lcVO2.getCmnt_code() + ",");
		System.out.println(lcVO2.getCmnt_lk_date());

		System.out.println("------------------------------");

		Like_CommentVO lcVO3 = new Like_CommentVO();
		lcVO3.setCmnt_lk_uid("CMNT_LK0000003");
		lcVO3.setCmnt_lk_memid("MEM0000001");
		lcVO3.setCmnt_code("CMNT0000001");
		lcVO3.setCmnt_lk_date(d);

		dao.update(lcVO3);

	}
}
