package com.tag_member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.like_comment.model.Like_CommentJDBCDAO;
import com.like_comment.model.Like_CommentVO;
import com.post.model.PostVO;

public class Tag_MemberJDBCDAO implements Tag_MemberDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO tag_member(tag_uid,tag_memid,post_code,tag_date) VALUES('TAG'||LPAD(SEQ_TAG_UID.NEXTVAL, 7, '0'),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT tag_uid,tag_memid, post_code,tag_date FROM tag_member ORDER BY tag_uid";
	private static final String GET_ONE_STMT = "SELECT tag_uid,tag_memid, post_code,tag_date FROM tag_member WHERE tag_uid = ?";
	private static final String UPDATE = "UPDATE tag_member SET tag_memid=?, post_code=?, tag_date=? WHERE tag_uid = ?";
	private static final String DELETE = "DELETE FROM tag_member Where tag_uid = ?";

	public void insert(Tag_MemberVO tag_memVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, tag_memVO.getTag_memid());
			pstmt.setString(2, tag_memVO.getPost_code());
			pstmt.setTimestamp(3, tag_memVO.getTag_date());

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

	public List<Tag_MemberVO> getAll() {
		List<Tag_MemberVO> list = new ArrayList<Tag_MemberVO>();
		Tag_MemberVO tmVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmVO = new Tag_MemberVO();
				tmVO.setTag_uid(rs.getString("tag_uid"));
				tmVO.setTag_memid(rs.getString("tag_memid"));
				tmVO.setPost_code(rs.getString("post_code"));
				tmVO.setTag_date(rs.getTimestamp("tag_date"));

				list.add(tmVO);
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

	public Tag_MemberVO findByPrimaryKey(String tag_uid) {

		Tag_MemberVO tmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, tag_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmVO = new Tag_MemberVO();
				tmVO.setTag_uid(rs.getString("tag_uid"));
				tmVO.setTag_memid(rs.getString("tag_memid"));
				tmVO.setPost_code(rs.getString("post_code"));
				tmVO.setTag_date(rs.getTimestamp("tag_date"));

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
		return tmVO;
	}

	public void update(Tag_MemberVO tmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, tmVO.getTag_memid());
			pstmt.setString(2, tmVO.getPost_code());
			pstmt.setTimestamp(3, tmVO.getTag_date());
			pstmt.setString(4, tmVO.getTag_uid());

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

	public void delete(String tag_uid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, tag_uid);

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
		Tag_MemberJDBCDAO dao = new Tag_MemberJDBCDAO();

		Timestamp d = new Timestamp(System.currentTimeMillis());
		Tag_MemberVO tmVO1 = new Tag_MemberVO();

		tmVO1.setTag_memid("MEM0000002");
		tmVO1.setPost_code("PS0000002");
		tmVO1.setTag_date(d);

		dao.insert(tmVO1);

		List<Tag_MemberVO> list = dao.getAll();
		for (Tag_MemberVO tagm : list) {
			System.out.println(tagm.getTag_uid());
			System.out.println(tagm.getTag_memid());
			System.out.println(tagm.getPost_code());
			System.out.println(tagm.getTag_date());

			System.out.println();
		}

		Tag_MemberVO tmVO2 = dao.findByPrimaryKey("TAG0000002");
		System.out.println("+++++++++++++++++++");
		System.out.println(tmVO2.getTag_uid() + ",");
		System.out.println(tmVO2.getTag_memid() + ",");
		System.out.println(tmVO2.getPost_code() + ",");
		System.out.println(tmVO2.getTag_date());

		System.out.println("------------------------------");

		Tag_MemberVO tmVO3 = new Tag_MemberVO();
		tmVO3.setTag_uid("TAG0000003");
		tmVO3.setTag_memid("MEM0000001");
		tmVO3.setPost_code("PS0000001");
		tmVO3.setTag_date(d);

		dao.update(tmVO3);

		dao.delete("TAG0000004");
	}

}
