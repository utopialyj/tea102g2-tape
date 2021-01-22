package com.actapl.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.actcol.model.ActColVO;
import com.acts.model.ActsService;
import com.acts.model.ActsVO;
import com.member_info.model.Member_InfoVO;

import util.Util;

public class ActAplJDBCDAO implements ActAplDAO {
	public static final String INSERT = 
			"INSERT INTO ACTAPL VALUES('APL'||LPAD(SEQ_APLUID.NEXTVAL, 7, '0'), ?, ?, ?, ?)";
	public static final String UPDATE = 
			"UPDATE ACTAPL SET APLUID = ?, MEMID = ?, ACTID = ?, RSON = ?, STS = ? WHERE APLUID = ?";
	public static final String DELETE = 
			"DELETE FROM ACTAPL WHERE APLUID = ?";
	public static final String FIND_BY_PK = 
			"SELECT * FROM ACTAPL WHERE APLUID = ?";
	public static final String GET_ALL = 
			"SELECT * FROM ACTAPL";
	

	
	//撈出某人成功報名的所有活動
	public static final String GET_ONE_ALL_SUCCESS_JOIN = "SELECT ACTID FROM ACTAPL WHERE MEMID = ? AND STS='同意' ";
	
	//memid + actid ==> apluid
	public static final String GET_ONEAPL_BY_MEMID_ACTID = "SELECT * FROM ACTAPL WHERE MEMID = ? AND ACTID = ?";
	
	//參與活動扣除點數
	public static final String DROP_APL_POINT = "UPDATE MEMBER_INFO SET POINTS = ? WHERE MEM_ID = ?";
	
	
	//會員報名哪些活動
	public static final String GET_ONE_APL = "SELECT * FROM ACTAPL WHERE MEMID = ?";
	
	
	//重複參加
	public static final String isRepeat_APL = "SELECT  COUNT( DISTINCT ACTID ) FROM ACTAPL WHERE MEMID = ? AND ACTID = ?"; 
	
	//PK. & 報名狀態 修改
	public static final String UPDATE_STS = "UPDATE ACTAPL SET STS = ? WHERE APLUID = ?";
	
	
/////////////////////////////////////////////////////////////////////////////////////	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
/////////////////////////////////////////////////方法實作/////////////////////////////////////////
	
	
	//撈出某人"成功"的活動編號
	public List<String> get_One_All_Success_Actid(String memid){
		List<String> all_success_actid = new ArrayList();//"成功活動編號"
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_ALL_SUCCESS_JOIN);
			
			pstmt.setString(1, memid);			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				all_success_actid.add(rs.getString(1));
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
		
		return all_success_actid;
		
	}
	
	
	
	//memid + actid ==> apluid
	@Override
	public ActAplVO get_OneApl_by_memid_actid(String memid, String actid) {
		ActAplVO actapl = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONEAPL_BY_MEMID_ACTID);
			
			pstmt.setString(1, memid);			
			pstmt.setString(2, actid);			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				actapl = new ActAplVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5));
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
		return actapl;
	}
	
	
	
	
	
	@Override
	public void drop_apl_point(Member_InfoVO memVO, String actid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ActsService as = null;
		ActsVO avo = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(DROP_APL_POINT);
			
			as = new ActsService();//透過 Service 反找出 活動VO，參數就不用傳遞 活動 VO ，在前端JSP 比起活動VO 活動編號更容易拿到。
			avo = as.getOneAct(actid);
			
			//memVO現有點數 扣除 活動參與點數
			//每次扣點都得從資料庫撈出新的VO 更新就的資料 
			pstmt.setInt(1, memVO.getPoints() - avo.getPts());
			pstmt.setString(2, memVO.getMem_id());

			rs = pstmt.executeQuery();

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
			if(as != null) {
				as = null;
			}
		}
		
		
	}
	


	@Override
	public boolean update_sts(ActAplVO avo, String sts) {
		boolean ok = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("修改前報名狀態_? " + avo.getSts());
			
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(UPDATE_STS);
			pstmt.setString(1, sts);
			pstmt.setString(2, avo.getApluid());

			pstmt.executeQuery();
			
			avo.setSts(sts);
			System.out.println("修改後報名狀態_? " + avo.getSts());
			
			ok = true;
			
			
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
		
		
		return ok;
	}
	
	
	
	// true: 代表報名過 、false: 沒報名過
	@Override
	public boolean isRepeat_APL(String memid, String actid) {
		boolean OK = true;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(isRepeat_APL);
			pstmt.setString(1, memid);
			pstmt.setString(2, actid);

			rs = pstmt.executeQuery();
			
			Integer ans = 0;
			while (rs.next()) {
				 ans = rs.getInt(1);
				 System.out.println("ans 是多少 ? " + ans);
			}
			if(ans == 0) OK = false;
			
			System.out.println("OK 是 ? " + OK);
			
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
	public List<ActAplVO> getOneApl(String memid) {
		List<ActAplVO> list = new ArrayList<ActAplVO>();
		ActAplVO apl = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(GET_ONE_APL);
			pstmt.setString(1, memid);

			
			rs = pstmt.executeQuery();
			while (rs.next()) {
					apl = new ActAplVO(rs.getString("APLUID"), rs.getString("MEMID"), rs.getString("ACTID")
							,rs.getString("rson"),rs.getString("STS")) ;
					list.add(apl);
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
	public void insert(ActAplVO actapl) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			String[] cols = {"APLUID"};
			pstmt = con.prepareStatement(INSERT, cols);
			
			pstmt.setString(1, actapl.getMemid());
			pstmt.setString(2, actapl.getActid());
			pstmt.setString(3, actapl.getRson());
			pstmt.setString(4, actapl.getSts());
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
	public void update(ActAplVO actapl) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, actapl.getApluid());
			pstmt.setString(2, actapl.getMemid());
			pstmt.setString(3, actapl.getActid());
			pstmt.setString(4, actapl.getRson());
			pstmt.setString(5, actapl.getSts());
			pstmt.setString(6, actapl.getApluid());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("(actApl_Update部分)A database error occured. " + e.getMessage());
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
	public void delete(String apluid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, apluid);
			
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
	public ActAplVO findByPK(String apluid) {
		ActAplVO actapl = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1, apluid);			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				actapl = new ActAplVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5));
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
		return actapl;
	}

	@Override
	public List<ActAplVO> getAll() {
		List<ActAplVO> list = new ArrayList<ActAplVO>();
		ActAplVO actapl = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				actapl = new ActAplVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5));
				list.add(actapl);
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
