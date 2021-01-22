package com.actcol.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import util.Util;

public class ActColJDBCDAO implements ActColDAO {
	public static final String INSERT = "INSERT INTO ACTCOL VALUES('COL'||LPAD(SEQ_COLUID.NEXTVAL, 7, '0'), ?, ?)";
	public static final String UPDATE = "UPDATE ACTCOL SET COLUID = ?, MEMID = ?, ACTID = ? WHERE COLUID = ?";
	public static final String DELETE = "DELETE FROM ACTCOL WHERE COLUID = ?";
	public static final String FIND_BY_PK = "SELECT * FROM ACTCOL WHERE COLUID = ?";
	public static final String GET_ALL = "SELECT * FROM ACTCOL";

	// 搜尋會員收藏哪些活動
	public static final String GET_ONE_COL = "SELECT * FROM ACTCOL WHERE MEMID = ?";

	// 重複參加
	public static final String isRepeat_COL = "SELECT  COUNT( DISTINCT ACTID ) FROM ACTCOL WHERE MEMID = ? AND ACTID = ?";
	
	
	//活動編號 + 會員編號  = 收藏編號
	public static final String GET_ONE = "SELECT * FROM ACTCOL WHERE MEMID = ? AND ACTID = ?";
	
	//排除過時的活動收藏
	//收藏清單  = 序號 + 會員編號 + 活動編號 
	//過期活動VO_清單  可得 活動編號 ， 加上會員編號 得 過期收藏VO
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<ActColVO> getOne(String memid, String actid) {
		List<ActColVO> colArr = new ArrayList<ActColVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, memid);
			pstmt.setString(2, actid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				colArr.add(new ActColVO(rs.getString(1), rs.getString(2), rs.getString(3)));
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
		return colArr;

		
	}
	

	// true: 代表報名過 、false: 沒報名過
	@Override
	public boolean isRepeat_COL(String memid, String actid) {
		boolean OK = true;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(isRepeat_COL);
			pstmt.setString(1, memid);
			pstmt.setString(2, actid);

			rs = pstmt.executeQuery();

			Integer ans = 0;
			while (rs.next()) {
				ans = rs.getInt(1);
				//System.out.println("ans 是多少 ? " + ans);
			}
			if (ans == 0)
				OK = false;

			// System.out.println("OK 是 ? " + OK);

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
		
		return OK;
	}

	@Override
	public List<ActColVO> getOneCol(String memid) {
		List<ActColVO> list = new ArrayList<ActColVO>();
		ActColVO actcol = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_ONE_COL);
			pstmt.setString(1, memid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				actcol = new ActColVO(rs.getString("COLUID"), rs.getString("MEMID"), rs.getString("ACTID"));
				list.add(actcol);
//System.out.println(rs.getString("COLUID"));
//System.out.println(rs.getString("MEMID"));
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

	@Override
	public void insert(ActColVO actcol) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			String[] cols = { "COLUID" };
			pstmt = con.prepareStatement(INSERT, cols);

			pstmt.setString(1, actcol.getMemid());
			pstmt.setString(2, actcol.getActid());
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
	public void update(ActColVO actcol) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, actcol.getColuid());
			pstmt.setString(2, actcol.getMemid());
			pstmt.setString(3, actcol.getActid());
			pstmt.setString(4, actcol.getColuid());
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
	public void delete(String coluid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, coluid);

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
	public ActColVO findByPK(String coluid) {
		ActColVO actcol = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);

			pstmt.setString(1, coluid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				actcol = new ActColVO(rs.getString(1), rs.getString(2), rs.getString(3));
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
		return actcol;
	}

	@Override
	public List<ActColVO> getAll() {
		List<ActColVO> list = new ArrayList<ActColVO>();
		ActColVO actcol = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				actcol = new ActColVO(rs.getString(1), rs.getString(2), rs.getString(3));
				list.add(actcol);
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
