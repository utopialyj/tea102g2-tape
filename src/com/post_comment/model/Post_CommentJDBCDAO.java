package com.post_comment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post_CommentJDBCDAO implements Post_CommentDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO post_comment VALUES('CMNT'||LPAD(SEQ_CMNT_UID.NEXTVAL, 7, '0'),?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT cmnt_uid,cmnt_memid,cmnt_date,cmnt_context,lk_count,post_code,cmnt_sts FROM post_comment ORDER BY cmnt_uid";
	private static final String GET_ONE_STMT = "SELECT cmnt_uid,cmnt_memid,cmnt_date,cmnt_context,lk_count,post_code,cmnt_sts FROM post_comment WHERE cmnt_uid=?";
	private static final String UPDATE = "UPDATE post_comment SET cmnt_memid=?, cmnt_date=?,cmnt_context=?,lk_count=?,post_code=?,cmnt_sts=? WHERE cmnt_uid = ?";
	private static final String DELETE = "DELETE FROM post_comment WHERE cmnt_uid = ?";
	private static final String GET_BY_POST_CODE = "SELECT*FROM post_comment WHERE (cmnt_sts IN 1) AND post_code =? ORDER BY cmnt_uid";

	public void insert(Post_CommentVO pcVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, pcVO.getCmnt_memid());
			pstmt.setTimestamp(2, pcVO.getCmnt_date());
			pstmt.setString(3, pcVO.getCmnt_context());
			pstmt.setInt(4, pcVO.getLk_count());
			pstmt.setString(5, pcVO.getPost_code());
			pstmt.setInt(6, pcVO.getCmnt_sts());

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

	public List<Post_CommentVO> getAll() {
		List<Post_CommentVO> list = new ArrayList<Post_CommentVO>();
		Post_CommentVO pmVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pmVO = new Post_CommentVO();
				pmVO.setCmnt_uid(rs.getString("cmnt_uid"));
				pmVO.setCmnt_memid(rs.getString("cmnt_memid"));
				pmVO.setCmnt_date(rs.getTimestamp("cmnt_date"));
				pmVO.setCmnt_context(rs.getString("cmnt_context"));
				pmVO.setLk_count(rs.getInt("lk_count"));
				pmVO.setPost_code(rs.getString("post_code"));
				pmVO.setCmnt_sts(rs.getInt("cmnt_sts"));

				list.add(pmVO);
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

	public Post_CommentVO findByPrimaryKey(String cmnt_uid) {

		Post_CommentVO pcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cmnt_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				pcVO = new Post_CommentVO();
				pcVO.setCmnt_uid(rs.getString("cmnt_uid"));
				pcVO.setCmnt_memid(rs.getString("cmnt_memid"));
				pcVO.setCmnt_date(rs.getTimestamp("cmnt_date"));
				pcVO.setCmnt_context(rs.getString("cmnt_context"));
				pcVO.setLk_count(rs.getInt("lk_count"));
				pcVO.setPost_code(rs.getString("post_code"));
				pcVO.setCmnt_sts(rs.getInt("cmnt_sts"));

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
		return pcVO;
	}

	public List<Post_CommentVO> findByPost_Code(String post_code) {

		List<Post_CommentVO> list = new ArrayList();
		Post_CommentVO pcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_BY_POST_CODE);

			pstmt.setString(1, post_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				pcVO = new Post_CommentVO();
				pcVO.setCmnt_uid(rs.getString("cmnt_uid"));
				pcVO.setCmnt_memid(rs.getString("cmnt_memid"));
				pcVO.setCmnt_date(rs.getTimestamp("cmnt_date"));
				pcVO.setCmnt_context(rs.getString("cmnt_context"));
				pcVO.setLk_count(rs.getInt("lk_count"));
				pcVO.setPost_code(rs.getString("post_code"));
				pcVO.setCmnt_sts(rs.getInt("cmnt_sts"));

				list.add(pcVO);
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
		return list;
	}

	public void update(Post_CommentVO pcVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, pcVO.getCmnt_memid());
			pstmt.setTimestamp(2, pcVO.getCmnt_date());
			pstmt.setString(3, pcVO.getCmnt_context());
			pstmt.setInt(4, pcVO.getLk_count());
			pstmt.setString(5, pcVO.getPost_code());
			pstmt.setInt(6, pcVO.getCmnt_sts());
			pstmt.setString(7, pcVO.getCmnt_uid());

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

	public void delete(String cmnt_uid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cmnt_uid);

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
		Post_CommentJDBCDAO dao = new Post_CommentJDBCDAO();

		Timestamp d = new Timestamp(System.currentTimeMillis());
		Post_CommentVO pcVO1 = new Post_CommentVO();

		pcVO1.setCmnt_memid("MEM0000001");
		pcVO1.setCmnt_date(d);
		pcVO1.setCmnt_context("1112222");
		pcVO1.setLk_count(3);
		pcVO1.setPost_code("PS0000001");
		pcVO1.setCmnt_sts(2);

		dao.insert(pcVO1);

		List<Post_CommentVO> list = dao.getAll();
		for (Post_CommentVO pcm : list) {
			System.out.println(pcm.getCmnt_uid());
			System.out.println(pcm.getCmnt_memid());
			System.out.println(pcm.getCmnt_date());
			System.out.println(pcm.getCmnt_context());
			System.out.println(pcm.getLk_count());
			System.out.println(pcm.getPost_code());
			System.out.println(pcm.getCmnt_sts());

			System.out.println();
		}

		Post_CommentVO pcVO2 = dao.findByPrimaryKey("CMNT0000001");
		System.out.println("+++++++++++++++++++");
		System.out.println(pcVO2.getCmnt_uid() + ",");
		System.out.println(pcVO2.getCmnt_memid() + ",");
		System.out.println(pcVO2.getCmnt_date() + ",");
		System.out.println(pcVO2.getCmnt_context());
		System.out.println(pcVO2.getLk_count() + ",");
		System.out.println(pcVO2.getPost_code() + ",");
		System.out.println(pcVO2.getCmnt_sts());

		System.out.println("------------------------------");

		Post_CommentVO pcVO3 = new Post_CommentVO();
		pcVO3.setCmnt_uid("CMNT0000003");
		pcVO3.setCmnt_memid("MEM0000002");
		pcVO3.setCmnt_date(d);
		pcVO3.setCmnt_context("ccccccccc");
		pcVO3.setLk_count(44);
		pcVO3.setPost_code("PS0000002");
		pcVO3.setCmnt_sts(2);

		dao.update(pcVO3);

		dao.delete("CMNT0000004");

	}
}
