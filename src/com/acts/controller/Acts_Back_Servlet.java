package com.acts.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.acts.model.ActsService;
import com.acts.model.ActsVO;

import com.member_info.model.Member_InfoVO;
import com.actapl.model.ActAplService;
import com.member_info.model.Member_InfoVO;

@WebServlet("/front-end/acts/Acts_Back_Servlet")
@MultipartConfig()
public class Acts_Back_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member_InfoVO memVO = (Member_InfoVO)session.getAttribute("member_infoVO");//insert、update區塊 會用到
		

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
//System.out.println("------------------------------------");
//System.out.println("轉到Acts_Back_Servlet");
//System.out.println("這裡是Controller的開頭");
//System.out.println("action_hello = " + action);
//System.out.println("req.getServletPaht() = " + req.getServletPath());
//System.out.println("req.getContextPath() = " + req.getContextPath());
//System.out.println("------------------------------------");
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String actid = req.getParameter("actid");
				if (actid == null || (actid.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/acts/select_page.jsp");
//					/font_end/acts/select_page.jsp
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ActsService actsSvc = new ActsService();
				ActsVO actsVO = actsSvc.getOneAct(actid);
				if (actsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/acts/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("actsVO", actsVO); // 資料庫取出的actsVO物件,存入req
				String url = "listOneAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/acts/select_page.jsp");
				failureView.forward(req, res);
			}
		} // if getOne_For_Display -END

		if ("insert".equals(action)) { // 來自addActs.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//String memid = req.getParameter("memid");
				String memid = memVO.getMem_id();
				
				//檢查點數餘額足不足夠辦活動扣點
				if(memVO.getPoints() < 100) {
					errorMsgs.add("點數不足100，無法辦活動，請儲值。");
				}
				
				
				
				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("主辦者編號以MEM開頭，長度10碼");
				}

				String title = req.getParameter("title").trim();
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("活動主題請勿空白 點數不足夠");
				}

				String cont = req.getParameter("cont").trim();
				if (cont == null || cont.trim().length() == 0) {
					errorMsgs.add("活動內容請勿空白");
				}

			
				
				
				java.sql.Timestamp time = null;
//System.out.println("前端取得時間" + req.getParameter("time"));
				try {
					if(req.getParameter("time") == null || req.getParameter("time").length() == 0) {
						throw new IllegalArgumentException();
					}
					time = new java.sql.Timestamp(sdf.parse(req.getParameter("time")).getTime());
					System.out.println("前端處理過的時間" + time);
				} catch (IllegalArgumentException e) {
					time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("預設時間" + time);
					errorMsgs.add("請輸入活動時間!");
				}

				String shsts = "上架";//前端預設


				String sts = "未開始";
				String type = req.getParameter("type");

				if (type == null || type.trim().length() == 0) {
					errorMsgs.add("活動類型請勿空白");
				}

				
				Part part = req.getPart("pic");
				InputStream in = part.getInputStream();
				byte[] buffer = null;
				if(in.available() != 0) {
					buffer = new byte[in.available()];
					in.read(buffer);
					in.close();
				}else {
					/*
					 * 使用者若沒有上傳活動圖片
					 * 讀取專案夾裏頭的 "noPic圖片"，路徑 /TEA102G2/WebContent/back_end/acts/images/noPic.jpg
					 */
					
					
//					System.out.println("這裡開始0");					
//					File fi = new File(req.getContextPath() + "/back_end/acts/images/noPic.jpg");
//					File fi = new File("C:/Users/never/Downloads/noPic.jpg");
//					is = new FileInputStream(fi);
//					System.out.println("這裡開始1");
//					in.read(buffer);
					
//					String path = req.getParameter("pathNo");
//					File fi = new File(path);
//					in = new FileInputStream(fi);
//					in.read(buffer);
					
				
//						String path = req.getParameter("pathNo");
//						 buffer = path.getBytes();
//						 System.out.println(path);
					
					
					
					try {//使用者沒放圖片 ，幫他們塞入圖片
						in = getServletContext().getResourceAsStream("/front-end/acts/images/noPic.jpg");
						buffer = new byte[in.available()];
						in.read(buffer);
					} catch (Exception e) {
						errorMsgs.add("出錯了阿斯	" + e.getMessage());
						
					}
				}
				
				try {
					if (in != null) {
						in.close();
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}

				Integer peop = null;
				try {
					peop = new Integer(req.getParameter("peop").trim());
				} catch (NumberFormatException e) {
					peop = 0;
					errorMsgs.add("參與人數請填數字");
				}

				String areacd = req.getParameter("areacd");
				if("地區".equals(areacd)) {
					errorMsgs.add("請選擇地區");
				}

				Integer hot = new Integer(0);

				String loc = req.getParameter("loc");
				if (loc == null || loc.trim().length() == 0) {
					errorMsgs.add("地點請勿空白");
				}
				
				
				String store = req.getParameter("store");

				Integer bgt = null;
				try {
					bgt = new Integer(req.getParameter("bgt").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				Integer pts = null;
				try {
					pts = new Integer(req.getParameter("pts").trim());
				} catch (NumberFormatException e) {
					pts = 0;
					errorMsgs.add("參與所需點數請填數字");
				}

				String rpsts = "無";

				ActsVO actsVO = new ActsVO();
				actsVO.setMemid(memid);
				actsVO.setTitle(title);
				actsVO.setCont(cont);
				actsVO.setTime(time);
				actsVO.setShsts(shsts);
				actsVO.setSts(sts);
				actsVO.setType(type);
				actsVO.setPic(buffer);
				actsVO.setPeop(peop);
				actsVO.setAreacd(areacd);
				actsVO.setHot(hot);
				actsVO.setLoc(loc);
				actsVO.setStore(store);
				actsVO.setBgt(bgt);
				actsVO.setPts(pts);
				actsVO.setRpsts(rpsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("actsVO", actsVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/acts/addActs.jsp");
					failureView.forward(req, res);
					return;
				}
//				/*************************** 2.開始新增資料 ***************************************/
				ActsService actsSvc = new ActsService();
				actsSvc.drop_mem_pt(memVO);
				actsVO = actsSvc.addAct(memid, shsts,  time,  title,  sts,  type,
				 cont, buffer,  peop,  areacd,  hot,  loc,  store,  bgt,  pts, rpsts);
				//actsVO = actsSvc.addAct(actsVO);

//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = req.getContextPath();
				 url += "/front-end/acts/manage_own_acts.jsp";
				res.sendRedirect(url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/acts/addActs.jsp");
				failureView.forward(req, res);
			}
		} // if-insert-end
		
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String actid = req.getParameter("actid");
				
				/***************************2.開始查詢資料****************************************/
				ActsService actsSvc = new ActsService();
				ActsVO actsVO = actsSvc.getOneAct(actid);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("actsVO", actsVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back_end/acts/update_act_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/acts/listAllActs.jsp");
				failureView.forward(req, res);
			}
		}//getOne_For_Update-end
		
		
		
		

		if ("update".equals(action)) { // 來自update_act_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			ActsVO actsVO = null;

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String actid = req.getParameter("actid");//前端update的Form接收來的參數
				String memid = memVO.getMem_id();
				/* 
				System.out.println("actid" + actid);
				System.out.println("memid" + memid);
				
				System.out.println("危險區域-------------------------------");
				System.out.println("++++++" + req.getParameter("time") + "++++++");
				System.out.println("危險區域-------------------------------");
				*/
				

				String title = req.getParameter("title").trim();
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("活動主題請勿空白");
				}

				String cont = req.getParameter("cont").trim();
				if (cont == null || cont.trim().length() == 0) {
					errorMsgs.add("活動內容請勿空白");
				}

				java.sql.Timestamp time = null;
				try {
					if(req.getParameter("time") == null || req.getParameter("time").length() == 0) {
						throw new IllegalArgumentException();
					}
						time = new java.sql.Timestamp(sdf.parse(req.getParameter("time")).getTime());
					
				} catch (IllegalArgumentException | ParseException e) {
					time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入活動時間!");
				}

				String shsts = "上架";
				if (shsts == null || shsts.trim().length() == 0) {
					errorMsgs.add("上下架請勿空白");
				}


				String sts = "未開始";
				String type = req.getParameter("type");

				if (type == null || type.trim().length() == 0) {
					errorMsgs.add("活動類型請勿空白");
				}

				
				
				ActsService actsSvc = new ActsService();
				ActsVO avo = actsSvc.getOneAct(actid);
//System.out.println("actid ? " + actid);
//System.out.println("avo ? " + avo);
				
				Part part = req.getPart("pic");
				
				InputStream in = part.getInputStream();
				
				byte[] pic = null;
				if(in.available() != 0) {
					pic = new byte[in.available()];
					in.read(pic);						
				}else if(avo.getPic() != null){ //若沒上傳則維持原圖
					pic = actsSvc.getOneAct(actid).getPic();
				}else {
					try {//使用者原本就沒放圖，此時幫他們塞一張，noPic的圖片
						in = getServletContext().getResourceAsStream("/front-end/acts/images/noPic.jpg");
						pic = new byte[in.available()];
						in.read(pic);
					} catch (Exception e) {
						errorMsgs.add("出錯了阿斯	" + e.getMessage());
						
					}
				}
				
				
				in.close();
				
				
				//關閉InputStream
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					}

				System.out.println("圖片結束");
				
				Integer peop = null;
				try {
					peop = new Integer(req.getParameter("peop").trim());
				} catch (NumberFormatException e) {
					peop = avo.getPeop();
					errorMsgs.add("參與人數請填數字");
				}
				
//System.out.println("人數");
				String areacd = req.getParameter("areacd");
				if("地區".equals(areacd)) {
					areacd = avo.getAreacd();
					errorMsgs.add("請選擇地區");
				}
//System.out.println("地區");
				Integer hot = avo.getHot();
//System.out.println("熱度");
				
				String loc = req.getParameter("loc");
				if (loc == null || loc.trim().length() == 0) {
					errorMsgs.add("地點請勿空白");
				}
//System.out.println("地點");
				
				String store = req.getParameter("store");
//System.out.println("店家");
				Integer bgt = null;
				try {
					bgt = new Integer(req.getParameter("bgt").trim());
				} catch (NumberFormatException e) {
					bgt = avo.getBgt();
					e.printStackTrace();
				}
//System.out.println("預算");

				Integer pts = avo.getPts();
//System.out.println("點數 ?" + pts);

				String rpsts = "無";
				
				actsVO = new ActsVO();
				actsVO.setActid(actid);
				actsVO.setMemid(memid);
				actsVO.setTitle(title);
				actsVO.setCont(cont);
				actsVO.setTime(time);
				actsVO.setShsts(shsts);
				actsVO.setSts(sts);
				actsVO.setType(type);
				actsVO.setPic(pic);
				actsVO.setPeop(peop);
				actsVO.setAreacd(areacd);
				actsVO.setHot(hot);
				actsVO.setLoc(loc);
				actsVO.setStore(store);
				actsVO.setBgt(bgt);
				actsVO.setPts(pts);
				actsVO.setRpsts(rpsts);
				
//System.out.println("actsVO 裝填完");
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("actsVO", actsVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/acts/update_act.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
//System.out.println("準備update方法");				
				 actsSvc.updateAct(actid, memid,  shsts,  time,  title,  sts,  type,
						 cont,  pic,  peop,  areacd,  hot,  loc,  store,  bgt,  pts, rpsts);

//System.out.println("update方法執行完畢");
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				
				
				
String url = req.getContextPath(); 
url += "/front-end/acts/manage_own_acts.jsp";
res.sendRedirect(url);
//System.out.println("執行重導");
return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} 
			catch (RuntimeException e) {
//System.out.println("RumtimeException 跳轉頁面");
				req.setAttribute("actsVO", actsVO);//actAplService 方法也是會出現 SQL錯誤的 
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/acts/update_act.jsp");
				failureView.forward(req, res);
			}
		}//update-end
		
		
		if ("delete".equals(action)) { // 來自listAllActs.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				String actid = req.getParameter("actid");
				
				/***************************2.開始刪除資料***************************************/
				ActsService actsSvc = new ActsService();
				actsSvc.deleteAct(actid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back_end/acts/listAllActs.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/acts/listAllActs.jsp");
				failureView.forward(req, res);
			}
		}//delete-end
		

	}// doPost-end

}// class-end
