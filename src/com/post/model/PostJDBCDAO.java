package com.post.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.post_photo.model.Post_PhotoJDBCDAO;
import com.post_photo.model.Post_PhotoVO;

public class PostJDBCDAO implements PostDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO post(post_uid,post_memid,post_sts,post_public_lv,edit_date,post_context,post_location,lk_num)VALUES('PS'|| lpad(seq_post_uid.NEXTVAL, 7, '0'),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT post_uid,post_memid, post_sts,post_public_lv,to_char(edit_date,'YYYY-MM-DD HH24:MI:SS')edit_date,post_context,post_location,lk_num FROM post ORDER BY post_uid";
	private static final String GET_ONE_STMT = "SELECT post_uid,post_memid, post_sts,post_public_lv,to_char(edit_date,'YYYY-MM-DD HH24:MI:SS')edit_date,post_context,post_location,lk_num FROM post WHERE post_uid = ?";
	private static final String UPDATE = "UPDATE post SET post_memid=?, post_sts=?, post_public_lv=?, edit_date=?, post_context=?, post_location=?, lk_num=? WHERE post_uid = ?";
	private static final String DELETE = "UPDATE post SET post_sts=2 WHERE post_uid = ?";
	private static final String GET_STS_ONE_AND_THERE = "SELECT*FROM post WHERE (post_sts IN (1, 3)) ORDER BY edit_date";
	private static final String GET_MEMBER_POST = "SELECT*FROM post WHERE (post_sts IN (1, 3)) AND post_memid =? ORDER BY post_uid DESC";

	public void insert(PostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
//				String data = getLongString("item/a.txt");

			pstmt.setString(1, postVO.getPost_memid());
			pstmt.setInt(2, postVO.getPost_sts());
			pstmt.setInt(3, postVO.getPost_public_lv());
			pstmt.setTimestamp(4, postVO.getEdit_date());
			pstmt.setString(5, postVO.getPost_context());
			pstmt.setString(6, postVO.getPost_location());
			pstmt.setInt(7, postVO.getLk_num());

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

	// 新增動態連動新增照片
	public void insertWithPhoto(PostVO postVO, List<Post_PhotoVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);

			con.setAutoCommit(false);

			String[] cols = { "post_uid" };

			pstmt = con.prepareStatement(INSERT_STMT, cols);
//					String data = getLongString("item/a.txt");

			pstmt.setString(1, postVO.getPost_memid());
			pstmt.setInt(2, postVO.getPost_sts());
			pstmt.setInt(3, postVO.getPost_public_lv());
			pstmt.setTimestamp(4, postVO.getEdit_date());
			pstmt.setString(5, postVO.getPost_context());
			pstmt.setString(6, postVO.getPost_location());
			pstmt.setInt(7, postVO.getLk_num());

			pstmt.executeUpdate();

			String key = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getString(1);
				System.out.println("自增主鍵=" + key + "(剛新增成功的動態編號)");
			} else {
				System.out.println("No Keys Where Generated.");
			}

			rs.close();

			Post_PhotoJDBCDAO dao = new Post_PhotoJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for (Post_PhotoVO aPhoto : list) {
				aPhoto.setPost_code(new String(key));
				dao.insert2(aPhoto, con);
			}

			con.commit();
			con.setAutoCommit(true);

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

	public List<PostVO> getStsOneThere() {
		List<PostVO> list = new ArrayList<PostVO>();
		PostVO postVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_STS_ONE_AND_THERE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new PostVO();
				postVO.setPost_uid(rs.getString("post_uid"));
				postVO.setPost_memid(rs.getString("post_memid"));
				postVO.setPost_sts(rs.getInt("post_sts"));
				postVO.setPost_public_lv(rs.getInt("post_public_lv"));
				postVO.setEdit_date(rs.getTimestamp("edit_date"));
				postVO.setPost_context(rs.getString("post_context"));
				postVO.setPost_location(rs.getString("post_location"));
				postVO.setLk_num(rs.getInt("lk_num"));
				list.add(postVO);
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

	// 以memid取得貼文狀態為1跟3的動態，並以貼文編號倒序排列
	public List<PostVO> getMemberPost(String member_id) {

		List<PostVO> list = new ArrayList<PostVO>();
		PostVO postVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_MEMBER_POST);

			pstmt.setString(1, member_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new PostVO();
				postVO.setPost_uid(rs.getString("post_uid"));
				postVO.setPost_memid(rs.getString("post_memid"));
				postVO.setPost_sts(rs.getInt("post_sts"));
				postVO.setPost_public_lv(rs.getInt("post_public_lv"));
				postVO.setEdit_date(rs.getTimestamp("edit_date"));
				postVO.setPost_context(rs.getString("post_context"));
				postVO.setPost_location(rs.getString("post_location"));
				postVO.setLk_num(rs.getInt("lk_num"));
				list.add(postVO);
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

	public List<PostVO> getAll() {
		List<PostVO> list = new ArrayList<PostVO>();
		PostVO postVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new PostVO();
				postVO.setPost_uid(rs.getString("post_uid"));
				postVO.setPost_memid(rs.getString("post_memid"));
				postVO.setPost_sts(rs.getInt("post_sts"));
				postVO.setPost_public_lv(rs.getInt("post_public_lv"));
				postVO.setEdit_date(rs.getTimestamp("edit_date"));
				postVO.setPost_context(rs.getString("post_context"));
				postVO.setPost_location(rs.getString("post_location"));
				postVO.setLk_num(rs.getInt("lk_num"));
				list.add(postVO);
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

	public PostVO findByPrimaryKey(String post_uid) {

		PostVO postVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, post_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new PostVO();
				postVO.setPost_uid(rs.getString("post_uid"));
				postVO.setPost_memid(rs.getString("post_memid"));
				postVO.setPost_sts(rs.getInt("post_sts"));
				postVO.setPost_public_lv(rs.getInt("post_public_lv"));
				postVO.setEdit_date(rs.getTimestamp("edit_date"));
				postVO.setPost_context(rs.getString("post_context"));
				postVO.setPost_location(rs.getString("post_location"));
				postVO.setLk_num(rs.getInt("lk_num"));
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
		return postVO;
	}

	public void update(PostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, postVO.getPost_memid());
			pstmt.setInt(2, postVO.getPost_sts());
			pstmt.setInt(3, postVO.getPost_public_lv());
			pstmt.setTimestamp(4, postVO.getEdit_date());
			pstmt.setString(5, postVO.getPost_context());
			pstmt.setString(6, postVO.getPost_location());
			pstmt.setInt(7, postVO.getLk_num());
			pstmt.setString(8, postVO.getPost_uid());

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

	public void delete(String post_uid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, post_uid);

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

	// 1/13新增
	// 以memid取得貼文狀態為1跟3的動態，並以貼文編號倒序排列
	public List<PostVO> getPostToWall(List<String> member_id) {

		List<PostVO> list = new ArrayList<PostVO>();
		PostVO postVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String GET_POST_WALL = "SELECT * FROM post WHERE (post_sts IN (1, 3)) AND post_memid IN (";
		StringBuilder queryBuilder = new StringBuilder(GET_POST_WALL);
		try {

			for (int i = 0; i < member_id.size(); i++) {
				queryBuilder.append("?");
				if (i != member_id.size() - 1)
					queryBuilder.append(",");
			}
			queryBuilder.append(")ORDER BY post_uid DESC");

			GET_POST_WALL = queryBuilder.toString();

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_POST_WALL);

			for (int i = 1; i <= member_id.size(); i++) {
				pstmt.setString(i, member_id.get(i - 1).toString());
			}
			// System.out.println(GET_POST_WALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new PostVO();
				postVO.setPost_uid(rs.getString("post_uid"));
				postVO.setPost_memid(rs.getString("post_memid"));
				postVO.setPost_sts(rs.getInt("post_sts"));
				postVO.setPost_public_lv(rs.getInt("post_public_lv"));
				postVO.setEdit_date(rs.getTimestamp("edit_date"));
				postVO.setPost_context(rs.getString("post_context"));
				postVO.setPost_location(rs.getString("post_location"));
				postVO.setLk_num(rs.getInt("lk_num"));
				list.add(postVO);
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

	public static void main(String[] args) throws IOException {

		PostJDBCDAO dao = new PostJDBCDAO();

		String data = getLongString("item/a.txt");
		Timestamp d = new Timestamp(System.currentTimeMillis());
		PostVO postVO1 = new PostVO();
		postVO1.setPost_memid("MEM0000002");
		postVO1.setPost_sts(1);
		postVO1.setPost_public_lv(1);
		postVO1.setEdit_date(d);
//		postVO1.setEdit_date(java.sql.Timestamp.valueOf("2020-10-10 13:12:12"));
		postVO1.setPost_context(data);
		postVO1.setPost_location("1");
		postVO1.setLk_num(1);
		dao.insert(postVO1);

		byte[] pic = getPictureByteArray("item/images.jpg");
		List<Post_PhotoVO> testList = new ArrayList<Post_PhotoVO>();
		Post_PhotoVO post_PhotoXX = new Post_PhotoVO();
		post_PhotoXX.setPhoto(pic);
		post_PhotoXX.setPhoto_sts(1);

		testList.add(post_PhotoXX);
		dao.insertWithPhoto(postVO1, testList);

		List<PostVO> list = dao.getAll();
		for (PostVO aPost : list) {
			System.out.println(aPost.getPost_uid());
			System.out.println(aPost.getPost_memid());
			System.out.println(aPost.getPost_sts());
			System.out.println(aPost.getPost_public_lv());
			System.out.println(aPost.getEdit_date());
			System.out.println(aPost.getPost_context());
			System.out.println(aPost.getPost_location());
			System.out.println(aPost.getLk_num());
			System.out.println();
		}

		List<PostVO> list2 = dao.getStsOneThere();
		for (PostVO bPost : list2) {
			System.out.println(bPost.getPost_uid());
			System.out.println(bPost.getPost_memid());
			System.out.println(bPost.getPost_sts());
			System.out.println(bPost.getPost_public_lv());
			System.out.println(bPost.getEdit_date());
			System.out.println(bPost.getPost_context());
			System.out.println(bPost.getPost_location());
			System.out.println(bPost.getLk_num());
			System.out.println();
		}

		PostVO postVO2 = dao.findByPrimaryKey("PS0000001");
		System.out.println("+++++++++++++++++++");
		System.out.println(postVO2.getPost_uid() + ",");
		System.out.println(postVO2.getPost_memid() + ",");
		System.out.println(postVO2.getPost_sts() + ",");
		System.out.println(postVO2.getPost_public_lv() + ",");
		System.out.println(postVO2.getEdit_date() + ",");
		System.out.println(postVO2.getPost_context() + ",");
		System.out.println(postVO2.getPost_location() + ",");
		System.out.println(postVO2.getLk_num());
		System.out.println("------------------------------");

		PostVO postVO3 = new PostVO();
		postVO3.setPost_uid("PS0000002");
		postVO3.setPost_memid("MEM0000001");
		postVO3.setPost_sts(2);
		postVO3.setPost_public_lv(2);
		postVO3.setEdit_date(d);
		postVO3.setPost_context("新增文章");
		postVO3.setPost_location("老檀香");
		postVO3.setLk_num(2);
		dao.update(postVO3);

		dao.delete("PS0000003");
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	public static InputStream getPictureStream(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		return fis;
	}

	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}

}
