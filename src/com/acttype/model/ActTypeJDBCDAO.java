package com.acttype.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.acttype.model.ActTypeVO;
import com.area_list.model.AreaListVO;

import util.Util;


public class ActTypeJDBCDAO implements ActTypeDao{
	public static final String INSERT = 
			"INSERT INTO ACTTYPE VALUES(?, ?)";
	public static final String UPDATE = 
			"UPDATE ACTTYPE SET TYPECODE = ?, ACTTYPE = ?   WHERE TYPECODE = ?";
	public static final String DELETE = 
			"DELETE FROM ACTTYPE WHERE TYPECODE = ?";
	public static final String FIND_BY_PK = 
			"SELECT * FROM ACTTYPE WHERE TYPECODE = ?";
	public static final String GET_ALL = 
			"SELECT * FROM ACTTYPE";
	
	
//////////////////////////////////////////驅動註冊/////////////////////////////////	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
////////////////////////////////////////方法實作//////////////////////////////////////
	
	@Override
	public void insert(ActTypeVO acttypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, acttypeVO.getTypecode());
			pstmt.setString(2, acttypeVO.getActtype());
			pstmt.executeUpdate();

		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(ActTypeVO acttypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);


			pstmt.setString(1, acttypeVO.getTypecode());
			pstmt.setString(2, acttypeVO.getActtype());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String typecode) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, typecode);

			pstmt.executeUpdate();

		} 
		 catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public ActTypeVO findByPrimaryKey(String typecode) {
		ActTypeVO actTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);

			pstmt.setString(1, typecode);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				actTypeVO = new ActTypeVO();
				actTypeVO.setTypecode(rs.getString("typecode"));
				actTypeVO.setActtype(rs.getString("acttype"));
			}

		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return actTypeVO;
	}
	
	@Override
	public List<ActTypeVO> getAll() {
		List<ActTypeVO> list = new ArrayList<ActTypeVO>();
		ActTypeVO actTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				actTypeVO = new ActTypeVO();
				actTypeVO.setTypecode(rs.getString("typecode"));
				actTypeVO.setActtype(rs.getString("acttype"));
				
				
				list.add(actTypeVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
