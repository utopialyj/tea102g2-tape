package com.like_post.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.like_comment.model.Like_CommentJDBCDAO;
import com.like_comment.model.Like_CommentVO;
import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;

public class Like_PostJDBCDAO implements Like_PostDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO like_post VALUES('POST_LK'||LPAD(SEQ_LK_UID.NEXTVAL,7,'0'), ?,?,?)";
	private static final String GET_ALL_STMT = "SELECT lk_uid,lk_memid,post_code,lk_date FROM like_post ORDER BY lk_uid";
	private static final String GET_ONE_STMT = "SELECT lk_uid,lk_memid,post_code,lk_date FROM like_post WHERE lk_uid =?";
	private static final String UPDATE = "UPDATE like_post SET lk_memid=?, post_code=?, lk_date=? WHERE lk_uid = ?";
	private static final String DELETE = "DELETE FROM like_post WHERE post_code = ? AND lk_memid = ?";
	private static final String GET_POST_LIKE_MEMBERS = "SELECT lk_memid FROM like_post WHERE post_code = ? ORDER BY lk_date DESC";

	public void insert(Like_PostVO lpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lpVO.getLk_memid());
			pstmt.setString(2, lpVO.getPost_code());
			pstmt.setTimestamp(3, lpVO.getLk_date());

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

	public List<Like_PostVO> getAll() {
		List<Like_PostVO> list = new ArrayList<Like_PostVO>();
		Like_PostVO lpVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lpVO = new Like_PostVO();
				lpVO.setLk_uid(rs.getString("lk_uid"));
				lpVO.setLk_memid(rs.getString("lk_memid"));
				lpVO.setPost_code(rs.getString("post_code"));
				lpVO.setLk_date(rs.getTimestamp("lk_date"));

				list.add(lpVO);
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

	public Like_PostVO findByPrimaryKey(String lk_uid) {

		Like_PostVO lpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, lk_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lpVO = new Like_PostVO();
				lpVO.setLk_uid(rs.getString("lk_uid"));
				lpVO.setLk_memid(rs.getString("lk_memid"));
				lpVO.setPost_code(rs.getString("post_code"));
				lpVO.setLk_date(rs.getTimestamp("lk_date"));

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
		return lpVO;
	}

	public void update(Like_PostVO like_PostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, like_PostVO.getLk_memid());
			pstmt.setString(2, like_PostVO.getPost_code());
			pstmt.setTimestamp(3, like_PostVO.getLk_date());
			pstmt.setString(4, like_PostVO.getLk_uid());

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

	public void delete(String post_code, String lk_memid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, post_code);
			pstmt.setString(2, lk_memid);

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

	public List<String> getLikeMembersByPostcode(String post_code) {

		Member_InfoVO member_infoVO = null;
		Member_InfoService member_infoSvc = null;
		String memid = null;
		List<String> like_post_members = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_POST_LIKE_MEMBERS);

			pstmt.setString(1, post_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				member_infoVO = new Member_InfoVO();
				member_infoSvc = new Member_InfoService();

				memid = rs.getString("lk_memid");

				member_infoVO = (Member_InfoVO) member_infoSvc.getOneM_Info(memid);
				like_post_members.add(member_infoVO.getMem_id());

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
		return like_post_members;
	}

	public static void main(String[] args) {
		Like_PostJDBCDAO dao = new Like_PostJDBCDAO();

		Timestamp d = new Timestamp(System.currentTimeMillis());
		Like_PostVO lpVO1 = new Like_PostVO();

		lpVO1.setLk_memid("MEM0000002");
		lpVO1.setPost_code("PS0000001");
		lpVO1.setLk_date(d);

		dao.insert(lpVO1);

		List<Like_PostVO> list = dao.getAll();
		for (Like_PostVO lp : list) {
			System.out.println(lp.getLk_uid());
			System.out.println(lp.getLk_memid());
			System.out.println(lp.getPost_code());
			System.out.println(lp.getLk_date());

			System.out.println();

			Like_PostVO lpVO2 = dao.findByPrimaryKey("POST_LK0000001");
			System.out.println("+++++++++++++++++++");
			System.out.println(lpVO2.getLk_uid() + ",");
			System.out.println(lpVO2.getLk_memid() + ",");
			System.out.println(lpVO2.getPost_code() + ",");
			System.out.println(lpVO2.getLk_date());

			System.out.println("------------------------------");

			Like_PostVO lpVO3 = new Like_PostVO();
			lpVO3.setLk_uid("POST_LK0000003");
			lpVO3.setLk_memid("MEM0000001");
			lpVO3.setPost_code("PS0000001");
			lpVO3.setLk_date(d);

			dao.update(lpVO3);

//			dao.delete("POST_LK0000004");

		}
	}
}
