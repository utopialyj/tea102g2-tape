package com.actrnk.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;

public class ActRnkJDBCDAO implements ActRnkDAO {
	public static final String INSERT = 
			"INSERT INTO ACTRNK VALUES('RNK'||LPAD(SEQ_RNKUID.NEXTVAL, 7, '0'), ?, ?, ?, ?, ?)";
	public static final String UPDATE = 
			"UPDATE ACTRNK SET RNKUID = ?, MEMID = ?, ACTID = ?, STAR = ?, CMMT = ?, STS = ? WHERE RNKUID = ?";
	public static final String DELETE = 
			"DELETE FROM ACTRNK WHERE RNKUID = ?";
	public static final String FIND_BY_PK = 
			"SELECT * FROM ACTRNK WHERE RNKUID = ?";
	public static final String GET_ALL = 
			"SELECT * FROM ACTRNK";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(ActRnkVO actrnk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			String[] cols = {"RNKUID"};
			pstmt = con.prepareStatement(INSERT, cols);
			
			pstmt.setString(1, actrnk.getMemid());
			pstmt.setString(2, actrnk.getActid());
			pstmt.setInt(3, actrnk.getStar());
			pstmt.setString(4, actrnk.getCmmt());
			pstmt.setString(5, actrnk.getSts());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1);
				System.out.println(key + " INSERTED.");
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();
						
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(ActRnkVO actrnk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, actrnk.getRnkuid());
			pstmt.setString(2, actrnk.getMemid());
			pstmt.setString(3, actrnk.getActid());
			pstmt.setInt(4, actrnk.getStar());
			pstmt.setString(5, actrnk.getCmmt());
			pstmt.setString(6, actrnk.getSts());
			pstmt.setString(7, actrnk.getRnkuid());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String rnkuid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rnkuid);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public ActRnkVO findByPK(String rnkuid) {
		ActRnkVO actrnk = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1, rnkuid);			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				actrnk = new ActRnkVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getInt(4), rs.getString(5), rs.getString(6));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return actrnk;
	}

	@Override
	public List<ActRnkVO> getAll() {
		List<ActRnkVO> list = new ArrayList<ActRnkVO>();
		ActRnkVO actrnk = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				actrnk = new ActRnkVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getInt(4), rs.getString(5), rs.getString(6));
				list.add(actrnk);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

}
