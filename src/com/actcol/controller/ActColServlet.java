package com.actcol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.actapl.model.ActAplService;
import com.actapl.model.ActAplVO;
import com.actcol.model.ActColService;
import com.actcol.model.ActColVO;
import com.google.gson.Gson;
import com.member_info.model.Member_InfoVO;

@WebServlet("/front-end/actcol/ActColController")
public class ActColServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		if(action == null)  action = (String)session.getAttribute("action");
		Member_InfoVO memVO = (Member_InfoVO)session.getAttribute("member_infoVO");

		
		System.out.println("------------------------------------");
		System.out.println("轉到ActColController");
		System.out.println("這裡是Controller的開頭");
		System.out.println("action  = " + action);
		System.out.println("------------------------------------");
		
		
//if("repeat_check".equals(action)) {
//String actid = req.getParameter("actid");
//System.out.println("repeat_check 取道的actid是 " + actid);
//System.out.println(req.getQueryString());
//PrintWriter out = res.getWriter();
//Gson gson = new Gson();
//
//ActAplService actAplSvc = new ActAplService();
//Map<String, String> msgs = new HashMap<String, String>();
//if (actAplSvc.isRepeat_APL(memVO.getMem_id(), actid)) {
//	msgs.put("nope",  "repeat_col");
//	out.write(gson.toJson(msgs));
//} else {
//	msgs.put("yes", "ok_col");
//		out.write(gson.toJson(msgs));
//	}
//	
//}
		
		
		
		
		
		
		
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String coluid = req.getParameter("coluid");
				if (coluid == null || (coluid.trim()).length() == 0) {
					errorMsgs.add("請輸入收藏編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actcol/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ActColService actColSvc = new ActColService();
				ActColVO actColVO = actColSvc.getOneActCol(coluid);
				if (actColVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actcol/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				System.out.println("準備轉交");
				req.setAttribute("actColVO", actColVO); // 資料庫取出的ActAplVO物件,存入req
				String url = "listOneActCol.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneActRnk.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actcol/select_page.jsp");
				failureView.forward(req, res);
			}
		} // if getOne_For_Display -END
		
		
		if ("insert".equals(action)) { // 來自addActRnk.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//String memid = req.getParameter("memid");
				String memid = memVO.getMem_id();
				
				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號以MEM開頭，長度10碼");
				}

				String actid = (String) session.getAttribute("actid");
				String actidReg = "^[ACT + (0-9)]{10}$";
				if (actid == null || actid.trim().length() == 0) {
					errorMsgs.add("活動編號請勿空白");
				}else if(!actid.trim().matches(actidReg)) {
					errorMsgs.add("會員編號以ACT開頭，長度10碼");
				}


				ActColVO actColVO = new ActColVO();
				actColVO.setMemid(memid);
				actColVO.setActid(actid);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("actColVO", actColVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frond-end/actcol/addActCol.jsp");
					failureView.forward(req, res);
					return;
				}

//				/*************************** 2.開始新增資料 ***************************************/
				ActColService actColSvc = new ActColService();
				if(!actColSvc.isRepeat_COL(memid, actid))
					actColVO = actColSvc.addActCol( memid,  actid);

//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//System.out.println("資料送進資料庫");
session.removeAttribute("action");
session.removeAttribute("actid");
				
				String url = req.getContextPath();
				url += "/front-end/acts/manage_self_col.jsp";
				res.sendRedirect(url); // 重導避免表單重複輸出
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/actcol/addActCol.jsp");
				failureView.forward(req, res);
			}
		} // if-insert-end
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllActsCol.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String coluid = req.getParameter("coluid");
				
				/***************************2.開始查詢資料****************************************/
				ActColService actColSvc = new ActColService();

				ActColVO actColVO = actColSvc.getOneActCol(coluid);

				/***************************3.查詢完成,準備轉交(Send the Success view)************/

				req.setAttribute("actColVO", actColVO);         // 資料庫取出的ActColVO物件,存入req
				String url = "/back_end/actcol/update_actcol_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_actapl_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/actcol/listAllActsCol.jsp");
				failureView.forward(req, res);
			}
		}//getOne_For_Update-e
		
		
		if ("update".equals(action)) { // 來自update_actrnk_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			ActColVO actColVO = new ActColVO();

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				System.out.println("取參數前");
				String coluid = req.getParameter("coluid");
				
				
				String actid = req.getParameter("actid");
				String actidReg = "^[ACT + (0-9)]{10}$";
				if (actid == null || actid.trim().length() == 0) {
					errorMsgs.add("活動編號請勿空白");
				}else if(!actid.trim().matches(actidReg)) {
					errorMsgs.add("會員編號以ACT開頭，長度10碼");
				}
				 
				
				
				String memid = req.getParameter("memid");
				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號以MEM開頭，長度10碼");
				}




				actColVO.setColuid(coluid);
				actColVO.setMemid(memid);
				actColVO.setActid(actid);
				req.setAttribute("actColVO", actColVO); 

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("有錯誤訊息 準備跳轉");
					req.setAttribute("actColVO", actColVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actcol/update_actcol_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				ActColService actColSvc = new ActColService();
				// APLUID = ?, MEMID = ?, ACTID = ?, RSON = ?, STS = ?
				actColVO = actColSvc.updateActCol(coluid,  memid,  actid);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/actcol/listAllActsCol.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} 
			//Exception
			catch (RuntimeException e) {
				req.setAttribute("actColVO", actColVO);//actAplService 方法也是會出現 SQL錯誤的 
				errorMsgs.add( e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actcol/update_actcol_input.jsp");
				failureView.forward(req, res);
			}
		}//update-end
		
		if ("delete".equals(action)) { // 來自listAllActsApl.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				String coluid = req.getParameter("coluid");
				
				/***************************2.開始刪除資料***************************************/
				ActColService actColSvc = new ActColService();
				actColSvc.deleteActCol(coluid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/acts/manage_self_col.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/actapl/listAllActsCol.jsp");
				failureView.forward(req, res);
			}
		}//delete-end
		
		
	}

}
