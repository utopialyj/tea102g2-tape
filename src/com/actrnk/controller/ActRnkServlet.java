package com.actrnk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.actrnk.model.ActRnkService;
import com.actrnk.model.ActRnkVO;
import com.acts.model.ActsService;
import com.acts.model.ActsVO;

@WebServlet("/back_end/actrnk/ActRnkController")
public class ActRnkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println("------------------------------------");
		System.out.println("轉到ActRnkController");
		System.out.println("這裡是Controller的開頭");
		System.out.println("action  = " + action);
		System.out.println("req.getServletPaht() = " + req.getServletPath());
		System.out.println("req.getContextPath() = " + req.getContextPath());
		System.out.println("------------------------------------");
		
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String rnkuid = req.getParameter("rnkuid");
				if (rnkuid == null || (rnkuid.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ActRnkService actRnkSvc = new ActRnkService();
				ActRnkVO actRnkVO = actRnkSvc.getOneActRnk(rnkuid);
				if (actRnkVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				System.out.println("準備轉交");
				req.setAttribute("actRnkVO", actRnkVO); // 資料庫取出的ActRnkVO物件,存入req
				String url = "listOneActRnk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneActRnk.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/select_page.jsp");
				failureView.forward(req, res);
			}
		} // if getOne_For_Display -END
		
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllActsRnk.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String rnkuid = req.getParameter("rnkuid");
				
				/***************************2.開始查詢資料****************************************/
				ActRnkService actRnkSvc = new ActRnkService();

				ActRnkVO actRnkVO = actRnkSvc.getOneActRnk(rnkuid);

				/***************************3.查詢完成,準備轉交(Send the Success view)************/

				req.setAttribute("actRnkVO", actRnkVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back_end/actrnk/update_actrnk_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_actrnk_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/actrnk/listAllActsRnk.jsp");
				failureView.forward(req, res);
			}
		}//getOne_For_Update-end
		
		
		if ("update".equals(action)) { // 來自update_actrnk_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				System.out.println("取參數前");
				String rnkuid = req.getParameter("rnkuid");
				
			
				
				System.out.println("1");
				String actid = req.getParameter("actid");
				if (actid == null || actid.trim().length() == 0) {
					errorMsgs.add("活動編號請勿空白");
				}

				System.out.println("2");
				String memid = req.getParameter("memid");
				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號以MEM開頭，長度10碼");
				}

				System.out.println("3");
				String starStr  = req.getParameter("star");
				if(starStr == null || starStr.trim().length() == 0) {
					errorMsgs.add("評價星數請勿空白");
				}
				Integer star = null;
				try {
					star = new Integer(starStr);
				}catch(NumberFormatException e) {
					star = 0;
					errorMsgs.add("轉換出現錯勿" + e.getMessage());
				}

				System.out.println("4");
				String cmmt = req.getParameter("cmmt");
				
				System.out.println("5");
				String sts = req.getParameter("sts");
				System.out.println( "----- "+ sts + "---");
				if (sts == null || sts.trim().length() == 0) {
					errorMsgs.add("身分別請勿空白");
				}

				System.out.println("取參數後");

				ActRnkVO actRnkVO = new ActRnkVO();
				actRnkVO.setRnkuid(rnkuid);
				actRnkVO.setActid(actid);
				actRnkVO.setMemid(memid);
				actRnkVO.setStar(star);
				actRnkVO.setCmmt(cmmt);
				actRnkVO.setSts(sts);

				System.out.println(actRnkVO);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("有錯誤訊息 準備跳轉");
					req.setAttribute("actRnkVO", actRnkVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/update_actrnk_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
				ActRnkService actRnkSvc = new ActRnkService();
				actRnkVO = actRnkSvc.updateActRnk(rnkuid,  memid,  actid,  star,  cmmt, sts);
				

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				System.out.println("轉交前夕");
				String url = "/back_end/actrnk/listAllActsRnk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} 
			catch (Exception e) {
				errorMsgs.add("update轉交出錯" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/update_actrnk_input.jsp");
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
				String rnkuid = req.getParameter("rnkuid");
				
				/***************************2.開始刪除資料***************************************/
				ActRnkService actRnkSvc = new ActRnkService();
				actRnkSvc.deleteActRnk(rnkuid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back_end/actrnk/listAllActsRnk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/actrnk/listAllActsRnk.jsp");
				failureView.forward(req, res);
			}
		}//delete-end
		
		
		if ("insert".equals(action)) { // 來自addActRnk.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String actid = req.getParameter("actid");
				String actidReg = "^[ACT + (0-9)]{10}$";
				if (actid == null || actid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!actid.trim().matches(actidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("主辦者編號以ACT開頭，長度10碼");
				}

				String memid = req.getParameter("memid");
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}

				String starStr = req.getParameter("star");
				if (starStr == null || starStr.trim().length() == 0) {
					errorMsgs.add("評價星數請勿空白");
				}
				Integer star = null;
				try {
					star = new Integer(starStr);
				}catch(NumberFormatException e) {
					star = 0;
					errorMsgs.add("星數是1~5的數字");
				}
				
				

			
				
				


				String sts = req.getParameter("sts");
				if(sts == null || sts.trim().length() == 0) {
					errorMsgs.add("參加身分不得為空");
				}

				String cmmt = req.getParameter("cmmt");


				
					
					


				ActRnkVO actRnkVO = new ActRnkVO();
				actRnkVO.setMemid(memid);
				actRnkVO.setActid(actid);
				actRnkVO.setStar(star);
				actRnkVO.setCmmt(cmmt);
				actRnkVO.setSts(sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("actRnkVO", actRnkVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/addActRnk.jsp");
					failureView.forward(req, res);
					return;
				}

//				/*************************** 2.開始新增資料 ***************************************/
				ActRnkService actRnkSvc = new ActRnkService();
				actRnkVO = actRnkSvc.addActRnk( memid,  actid,  star,  cmmt,  sts);

//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/actrnk/listAllActsRnk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actrnk/addActRnk.jsp");
				failureView.forward(req, res);
			}
		} // if-insert-end
		
		
		
		
	}

}
