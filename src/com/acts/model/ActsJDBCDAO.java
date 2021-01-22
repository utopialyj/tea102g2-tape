package com.acts.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.actapl.model.ActAplVO;
import com.actcol.model.ActColVO;
import com.member_info.model.Member_InfoVO;

import util.Util;

public class ActsJDBCDAO implements ActsDAO {
	public static final String INSERT = "INSERT INTO ACTS VALUES('ACT'||LPAD(SEQ_ACTID.NEXTVAL, 7, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE = "UPDATE ACTS SET ACTID = ?, MEMID = ?, SHSTS = ?, TIME = ?, STS = ?, TITLE = ?, TYPE = ?, CONT = ?, PIC = ?, PEOP = ?, AREACD = ?, HOT = ?, LOC = ?, STORE = ?, BGT = ?, PTS = ?, RPSTS = ? WHERE ACTID = ?";
	public static final String DELETE = "DELETE FROM ACTS WHERE ACTID = ?";
	public static final String FIND_BY_PK = "SELECT * FROM ACTS WHERE ACTID = ?";
	public static final String GET_ALL = "SELECT * FROM ACTS ORDER BY ACTID";
	
	
	
	
	//過去報名的活動		
	public static final String PAST_JOIN_ACT = "SELECT * FROM ACTAPL WHERE MEMID = ? AND  TIME <= TO_TIMESTAMP(? ,'yyyy/MM/dd hh24:mi:ss')";
	
	//過去主辦的活動
	public static final String PAST_OWN_ACT = "SELECT * FROM ACTS WHERE MEMID = ? AND  TIME <= TO_TIMESTAMP(? ,'yyyy/MM/dd hh24:mi:ss')";
	
	
	/********************** 第二階段 *********************************/
	//保留"不同意"活動一個月
	//"不同意"是ACTAPL的表單狀態，同時ACTAPL也有提供ACTID。
	//由ACTID可以撈出對應的ACT時間。
	//"時間"是ACT的時間 ，以這個時間為基準往下一個月
	//noLimOnTypeArea方法的第二個參數
	public static final String KEEP_FOR_DISAGREE_ONEMONTH = "";
	/********************** 第二階段 *********************************/
	
	// 撈出收藏該活動有多少人?
	public static final String COL_ACT_PSON = " SELECT COUNT(DISTINCT MEMID) AS COL_ACT_PERSON FROM ACTCOL WHERE ACTID = ?";

	// 撈出報名該活動有多少人?
	public static final String APL_ACT_PSON = "SELECT COUNT(DISTINCT MEMID) AS APL_ACT_PERSON FROM ACTAPL WHERE ACTID = ?";

	// 暨收藏又報名有多少人?
	public static final String BOTH_COL_APL_PSON = "SELECT  DISTINCT ACTCOL.ACTID, ACTCOL.MEMID "
			+ "FROM ACTCOL INNER JOIN  ACTAPL" + " ON ACTAPL.MEMID = ACTCOL.MEMID WHERE ACTCOL.ACTID = ?";

	// 限制 時間、地區、類型 ; allLimSearch
	public static final String GET_BY_SEARCH = "SELECT * FROM ACTS " + "WHERE TIME BETWEEN "
			+ "TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') AND  TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') "
			+ "AND AREACD= ? AND TYPE=? ";

	// 限制 時間 ;noLimOnTypeArea
	public static final String GET_BY_TIME = "SELECT * FROM ACTS " + "WHERE TIME BETWEEN "
			+ "TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') AND  TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss')";

	// 限制 地區、時間 ; noLimOnType
	public static final String GET_BY_AREA = "SELECT * FROM ACTS " + "WHERE TIME BETWEEN "
			+ "TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') AND  TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') "
			+ "AND AREACD = ?";

	// 限制類型 ; noLimOnArea
	public static final String GET_BY_TYPE = "SELECT * FROM ACTS " + "WHERE TIME BETWEEN "
			+ "TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') AND  TO_TIMESTAMP(?, 'yyyy/MM/dd hh24:mi:ss') "
			+ "AND TYPE = ?";

	// 取得會員主辦的活動
	public static final String GET_HOLD_ACTS = "SELECT * FROM ACTS " + "WHERE MEMID = ?";

	// 活動撈出所有報名會員
	public static final String GET_ALL_APL_FROM_ACT = "SELECT * FROM ACTAPL WHERE ACTID = ? ";
	
	// > 現在 稱之"未來"
	//public static final String GET_ALL_TIMECORRECT = "SELECT * FROM ACTS WHERE " + " TIME > TO_TIMESTAMP(? ,'yyyy/MM/dd hh24:mi:ss')";
	
	//過時活動
	public static final String PAST_ACT = "SELECT * FROM ACTS WHERE TIME <= TO_TIMESTAMP(? ,'yyyy/MM/dd hh24:mi:ss')";
	
	
	//撈出主辦者的大頭貼
	public static final String HEADSHOT_FROM_ACT = "SELECT HEADSHOT FROM MEMBER_INFO WHERE MEM_ID = ?";
	
	//辦活動扣使用者點數
	public static final String DROP_MEM_PT = "UPDATE MEMBER_INFO SET POINTS = ? WHERE MEM_ID = ?";
	
	//主辦活動規定扣100點
	public static final String HOLD_ACT_POINTS = "100";
	
	//所有"不同意"參加的成員
	public static final String  GET_ALL_DISAGREE = "SELECT * FROM ACTAPL WHERE ACTID = ? AND STS  = '不同意' ";
	
	
///////////////////////////////////指令區//////////////////////////////////////////////////////////////////////	

	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

////////////////////////////////方法實作區////////////////////////////////////////////////////////////////////
	
	
	
	
	//過時的"主辦"活動
	@Override
	public List<ActsVO> overTime_own_Act(String memid) {
				//先取得現在時間
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String strDate = sdFormat.format(date);
					System.out.println("現在系統時間" + strDate);
				 
				List<ActsVO> list = new ArrayList<ActsVO>();
				ActsVO act = null;
				
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

					pstmt = con.prepareStatement(PAST_OWN_ACT);

					pstmt.setString(1, memid);
					pstmt.setString(2, strDate);

					rs = pstmt.executeQuery();
					while (rs.next()) {
						act = new ActsVO();
						act.setActid(rs.getString("ACTID"));
						act.setMemid(rs.getString("MEMID"));
						act.setShsts(rs.getString("SHSTS"));
						act.setTime(rs.getTimestamp("TIME"));
						act.setSts(rs.getString("STS"));
						act.setTitle(rs.getString("TITLE"));
						act.setType(rs.getString("TYPE"));
						act.setCont(rs.getString("CONT"));
						act.setPic(rs.getBytes("PIC"));
						act.setPeop(rs.getInt("PEOP"));
						act.setAreacd(rs.getString("AREACD"));
						act.setHot(rs.getInt("HOT"));
						act.setLoc(rs.getString("LOC"));
						act.setStore(rs.getString("STORE"));
						act.setBgt(rs.getInt("BGT"));
						act.setPts(rs.getInt("PTS"));
						act.setRpsts(rs.getString("RPSTS"));
						if (rs.getBytes("PIC") != null) {
							act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
						}

						list.add(act);
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
	
	
	
	@Override//所有"不同意"參加的成員
	public List<ActAplVO> get_All_Disagree(String actid) {
		List<ActAplVO> list = new ArrayList<ActAplVO>();
		ActAplVO apl = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_ALL_DISAGREE);

			pstmt.setString(1, actid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				apl = new ActAplVO();
				apl.setApluid(rs.getString("APLUID"));
				apl.setMemid(rs.getString("MEMID"));
				apl.setActid(rs.getString("ACTID"));
				apl.setRson(rs.getString("RSON"));
				apl.setSts(rs.getString("STS"));

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
	


		//主辦活動 扣除會員點數
		@Override
		public void drop_mem_pt(Member_InfoVO memVO) {
			
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

				pstmt = con.prepareStatement(DROP_MEM_PT);

				pstmt.setInt(1, memVO.getPoints() - new Integer(HOLD_ACT_POINTS));
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
			}
			
		}
	
	
	
	
	
	
	
	







	//撈出主辦者的大頭貼
	public byte[] getHeadShot_FromAct(ActsVO avo) {
		byte[] headShot = null;//裝大頭貼用 
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(HEADSHOT_FROM_ACT);

			pstmt.setString(1, avo.getMemid());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				headShot = rs.getBytes("HEADSHOT");
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
		
		
		
		return headShot;
	}
	
	
	
	
	
	
	
	
	
	
	//過時活動
	@Override
	public List<ActsVO> overTimeAct() {
		
		//先取得現在時間
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
			//System.out.println(strDate);
		
		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(PAST_ACT);

			pstmt.setString(1, strDate);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

				list.add(act);
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
	
	
	
	
	
	// 先找出所有過去的活動，再將其扣掉___<=____"去掉"過時活動
	@Override
	public List<ActsVO> getAllTimeCorrect() {
		
		//先取得現在時間
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
			//System.out.println(strDate);
		
		
		//取的所有活動
		ActsService actSvc = new ActsService();
		List<ActsVO> list = actSvc.getAll();
		ActsVO act = null;
		
//		for(ActsVO avo :list) {
//			System.out.println(avo.getActid() +"?" + avo.getTime());
//		}
//		System.out.println("===========================");
		
		
		List<ActsVO> listPast = new ArrayList(); 
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			//PAST_ACTGET_ALL_TIMECORRECT
			pstmt = con.prepareStatement(PAST_ACT); // <= 現在 

			pstmt.setString(1, strDate);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

				
				
				listPast.add(act);
				
			}
			
			
			//扣掉 過期的活動
			if(list != null && listPast != null)
				list.removeAll(listPast);
			
//			for(ActsVO avo :list) {
//			System.out.println(avo.getActid() +"?" + avo.getTime());
//			}
//			System.out.println("===========================");
				
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

	public  static void main(String args[]) 
	{
		//先取得現在時間
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String strDate = sdFormat.format(date);
					System.out.println(strDate);
		
	}
	
	
	
	
	@Override // 活動取得所有報名者
	public List<ActAplVO> getAllAplFromAct(String actid) {
		List<ActAplVO> list = new ArrayList<ActAplVO>();
		ActAplVO apl = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_ALL_APL_FROM_ACT);

			pstmt.setString(1, actid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				apl = new ActAplVO();
				apl.setApluid(rs.getString("APLUID"));
				;
				apl.setMemid(rs.getString("MEMID"));
				apl.setActid(rs.getString("ACTID"));
				apl.setRson(rs.getString("RSON"));
				apl.setSts(rs.getString("STS"));

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
	public List<ActsVO> getHoldActs(String memid) {
		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_HOLD_ACTS);

			pstmt.setString(1, memid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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
	public List<ActsVO> noLimOnArea(String start_time, String end_time, String type) {
		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_BY_TYPE);

			pstmt.setString(1, start_time);
			pstmt.setString(2, end_time);
			pstmt.setString(3, type);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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
	public List<ActsVO> noLimOnType(String start_time, String end_time, String areaCode) {
		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_BY_AREA);

			pstmt.setString(1, start_time);
			pstmt.setString(2, end_time);
			pstmt.setString(3, areaCode);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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

	// 時間搜尋
	public List<ActsVO> noLimOnTypeArea(String start_time, String end_time) {

		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_BY_TIME);

			pstmt.setString(1, start_time);
			pstmt.setString(2, end_time);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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

	// 地點搜尋
	@Override
	public List<ActsVO> allLimSearch(String start_time, String end_time, String areaCode, String type) {

		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_BY_SEARCH);

			pstmt.setString(1, start_time);
			pstmt.setString(2, end_time);
			pstmt.setString(3, areaCode);
			pstmt.setString(4, type);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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

	// 收藏人數
	@Override
	public int colPson(ActsVO act) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int colNum = 0;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(COL_ACT_PSON);

			pstmt.setString(1, act.getActid());

			rs = pstmt.executeQuery();

			rs.next();
			colNum = rs.getInt(1);

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

		return colNum;
	}

	// 報名人數
	@Override
	public int aplPson(ActsVO act) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int aplNum = 0;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(APL_ACT_PSON);

			pstmt.setString(1, act.getActid());

			rs = pstmt.executeQuery();

			rs.next();
			aplNum = rs.getInt(1);

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

		return aplNum;
	}

	// 暨報名又收藏
	@Override
	public int bothColApl(ActsVO act) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bothNum = 0;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(BOTH_COL_APL_PSON);

			pstmt.setString(1, act.getActid());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bothNum++;
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
		return bothNum;
	}

	@Override
	public void insert(ActsVO act) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			String[] cols = { "ACTID" };
			pstmt = con.prepareStatement(INSERT, cols);

			pstmt.setString(1, act.getMemid());
			pstmt.setString(2, act.getShsts());
			pstmt.setTimestamp(3, act.getTime());
			pstmt.setString(4, act.getSts());
			pstmt.setString(5, act.getTitle());
			pstmt.setString(6, act.getType());
			pstmt.setString(7, act.getCont());
			pstmt.setBytes(8, act.getPic());
			pstmt.setInt(9, act.getPeop());
			pstmt.setString(10, act.getAreacd());
			pstmt.setInt(11, act.getHot());
			pstmt.setString(12, act.getLoc());
			pstmt.setString(13, act.getStore());
			pstmt.setInt(14, act.getBgt());
			pstmt.setInt(15, act.getPts());
			pstmt.setString(16, act.getRpsts());

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
	public void update(ActsVO act) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, act.getActid());
			pstmt.setString(2, act.getMemid());
			pstmt.setString(3, act.getShsts());
			pstmt.setTimestamp(4, act.getTime());
			pstmt.setString(5, act.getSts());
			pstmt.setString(6, act.getTitle());
			pstmt.setString(7, act.getType());
			pstmt.setString(8, act.getCont());
			pstmt.setBytes(9, act.getPic());
			pstmt.setInt(10, act.getPeop());
			pstmt.setString(11, act.getAreacd());
			pstmt.setInt(12, act.getHot());
			pstmt.setString(13, act.getLoc());
			pstmt.setString(14, act.getStore());
			pstmt.setInt(15, act.getBgt());
			pstmt.setInt(16, act.getPts());
			pstmt.setString(17, act.getRpsts());
			pstmt.setString(18, act.getActid());

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
	public void delete(String actid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, actid);

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
	public ActsVO findByPK(String actid) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		ActsVO act = null;
		ActsVO act = new ActsVO();

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);

			pstmt.setString(1, actid);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8),rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
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
		return act;
	}

	@Override
	public List<ActsVO> getAll() {
		List<ActsVO> list = new ArrayList<ActsVO>();
		ActsVO act = null;
//		ActsVO act = new ActsVO();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				act = new ActsVO();
				act.setActid(rs.getString("ACTID"));
				act.setMemid(rs.getString("MEMID"));
				act.setShsts(rs.getString("SHSTS"));
				act.setTime(rs.getTimestamp("TIME"));
				act.setSts(rs.getString("STS"));
				act.setTitle(rs.getString("TITLE"));
				act.setType(rs.getString("TYPE"));
				act.setCont(rs.getString("CONT"));
				act.setPic(rs.getBytes("PIC"));
				act.setPeop(rs.getInt("PEOP"));
				act.setAreacd(rs.getString("AREACD"));
				act.setHot(rs.getInt("HOT"));
				act.setLoc(rs.getString("LOC"));
				act.setStore(rs.getString("STORE"));
				act.setBgt(rs.getInt("BGT"));
				act.setPts(rs.getInt("PTS"));
				act.setRpsts(rs.getString("RPSTS"));
				if (rs.getBytes("PIC") != null) {
					act.setBase64Image(Base64.getEncoder().encodeToString(rs.getBytes("PIC")));
				}

//				act = new ActsVO(rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7), 
//						rs.getString(8), rs.getBytes(9), rs.getInt(10), rs.getString(11), rs.getInt(12), 
//						rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), 
//						rs.getString(17));
				list.add(act);
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
