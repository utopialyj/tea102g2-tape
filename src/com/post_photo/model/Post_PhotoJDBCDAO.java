package com.post_photo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.post.model.PostVO;

import sun.security.action.GetLongAction;

public class Post_PhotoJDBCDAO implements Post_PhotoDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO post_photo(photo_uid,photo,post_code,photo_sts) VALUES('PHOTO'||LPAD(SEQ_PHOTO_UID.NEXTVAL, 7, '0'),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT photo_uid, photo, post_code,photo_sts FROM post_photo ORDER BY photo_uid";
	private static final String GET_ONE_STMT = "SELECT photo_uid, photo, post_code,photo_sts FROM post_photo WHERE photo_uid=?";
	private static final String UPDATE = "UPDATE post_photo SET photo=?, post_code=?, photo_sts=? WHERE photo_uid=?";
	private static final String DELETE = "UPDATE post_photo SET photo_sts=2 WHERE photo_uid = ?";
//	private static final String GET_ONE_BY_POSTCODE = "SELECT*FROM POST_PHOTO WHERE (photo_sts IN (1, 3)) AND post_code =? ORDER BY photo_uid";
	private static final String GET_PHOTO_UID_BY_PHOTO = "SELECT photo_uid FROM post_photo WHERE photo=?";
	private static final String GET_ONE_BY_POSTCODE = "SELECT*FROM post_photo WHERE (photo_sts IN (1, 3)) AND post_code =? ORDER BY photo_uid";

	public void insert(Post_PhotoVO post_photoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, post_photoVO.getPhoto());
			pstmt.setString(2, post_photoVO.getPost_code());
			pstmt.setInt(3, post_photoVO.getPhoto_sts());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public void insert2(Post_PhotoVO post_photoVO, java.sql.Connection con) {

		PreparedStatement pstmt = null;
		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, post_photoVO.getPhoto());
			pstmt.setString(2, post_photoVO.getPost_code());
			pstmt.setInt(3, post_photoVO.getPhoto_sts());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					System.out.println("Transaction is being");
					System.out.println("rollback error occured.(post_photoJDBCDAO_insertwithphoto)");

					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured." + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}

	public List<Post_PhotoVO> getAll() {
		List<Post_PhotoVO> list = new ArrayList<Post_PhotoVO>();
		Post_PhotoVO post_photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				post_photoVO = new Post_PhotoVO();
				post_photoVO.setPhoto_uid(rs.getString("photo_uid"));
				post_photoVO.setPhoto(rs.getBytes("photo"));
				post_photoVO.setPost_code(rs.getString("post_code"));
				post_photoVO.setPhoto_sts(rs.getInt("photo_sts"));

				list.add(post_photoVO);
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

	public Post_PhotoVO findByPrimaryKey(String photo_uid) {

		Post_PhotoVO post_photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, photo_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				post_photoVO = new Post_PhotoVO();
				post_photoVO.setPhoto_uid(rs.getString("photo_uid"));
				post_photoVO.setPhoto(rs.getBytes("photo"));
				post_photoVO.setPost_code(rs.getString("post_code"));
				post_photoVO.setPhoto_sts(rs.getInt("photo_sts"));

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
		return post_photoVO;
	}

	public void update(Post_PhotoVO post_photoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, post_photoVO.getPhoto());
			pstmt.setString(2, post_photoVO.getPost_code());
			pstmt.setInt(3, post_photoVO.getPhoto_sts());
			pstmt.setString(4, post_photoVO.getPhoto_uid());

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

	// 搜尋某篇動態的所有照片
//	public List<byte[]> findByPostCode(String post_code) {
//
//		List<byte[]> photo = new ArrayList();
//		Post_PhotoVO post_photoVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_BY_POSTCODE);
//
//			pstmt.setString(1, post_code);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				post_photoVO = new Post_PhotoVO();
//				post_photoVO.setPhoto(rs.getBytes("photo"));
//				post_photoVO.setPost_code(rs.getString("post_code"));
//
//		
//				photo.add(post_photoVO.getPhoto());
//				
//			}
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return photo;
//	}

	// 用照片取得照片uid
	public String findByPhoto(byte[] photo) {

		String photo_uid = null;
		Post_PhotoVO post_photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_PHOTO_UID_BY_PHOTO);

			pstmt.setBytes(1, photo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				post_photoVO = new Post_PhotoVO();
				post_photoVO.setPhoto_uid(rs.getString("phot_uid"));

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
		return photo_uid;
	}

	// 用動態編號取得此動態的所有照片VO
	public List<Post_PhotoVO> findByPostCode(String post_code) {

		List<Post_PhotoVO> list = new ArrayList();
		Post_PhotoVO post_photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_BY_POSTCODE);

			pstmt.setString(1, post_code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				post_photoVO = new Post_PhotoVO();
				post_photoVO.setPhoto_uid(rs.getString("photo_uid"));
				post_photoVO.setPhoto(rs.getBytes("photo"));
				post_photoVO.setPost_code(rs.getString("post_code"));

				list.add(post_photoVO);

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

	public void delete(String photo_uid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(util.Util.DRIVER);
			con = DriverManager.getConnection(util.Util.URL, util.Util.USER, util.Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, photo_uid);

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

	public static void main(String[] args) throws IOException {

		Post_PhotoJDBCDAO dao = new Post_PhotoJDBCDAO();

		byte[] pic = getPictureByteArray("item/images.jpg");
		Post_PhotoVO post_photoVO1 = new Post_PhotoVO();
		post_photoVO1.setPhoto(pic);
		post_photoVO1.setPost_code("PS0000001");
		post_photoVO1.setPhoto_sts(1);
		dao.insert(post_photoVO1);

		List<Post_PhotoVO> list = dao.getAll();
		for (Post_PhotoVO aPost : list) {
			System.out.println(aPost.getPhoto_uid());
			System.out.println(aPost.getPhoto());
			System.out.println(aPost.getPost_code());
			System.out.println(aPost.getPhoto_sts());
			System.out.println();
		}

		Post_PhotoVO post_photoVO2 = dao.findByPrimaryKey("PHOTO0000003");
		System.out.println("+++++++++++++++++++");
		System.out.println(post_photoVO2.getPhoto_uid() + ",");
		System.out.println(post_photoVO2.getPhoto() + ",");
		System.out.println(post_photoVO2.getPost_code() + ",");
		System.out.println(post_photoVO2.getPhoto_sts() + ",");

		System.out.println("------------------------------");

		byte[] pic2 = getPictureByteArray("item/images2.jpg");
		Post_PhotoVO post_photoVO3 = new Post_PhotoVO();
		post_photoVO3.setPhoto_uid("PHOTO0000001");
		post_photoVO3.setPhoto(pic2);
		post_photoVO3.setPost_code("PS0000002");
		post_photoVO3.setPhoto_sts(2);

		dao.update(post_photoVO3);

		dao.delete("PHOTO0000004");

		System.out.println("%%%%%%%%%%%%%%%%%%");

//		List<byte[]> photo = dao.findByPostCode("PS0000001");
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		
//		Iterator objs = photo.iterator();
//		while(objs.hasNext())
//		System.out.println(objs.next());
//		
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}

//	public static InputStream getPictureStream(String path) throws IOException {
//		FileInputStream fis = new FileInputStream(path);
//
//		return fis;
//	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

}
