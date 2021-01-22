package com.black_list.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.black_list.model.Black_ListService;
import com.black_list.model.Black_ListVO;


@WebServlet("/Black_ListServlet")
public class Black_ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOne_For_Display".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
					
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
			try {
				String str = req.getParameter("blk_uid");
				if(str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸黑名單編號");
					
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/Black_List/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				String blk_uid = null;
				try {
					blk_uid = new String(str);
					
				}catch(Exception e) {
					errorMsgs.add("格式不正確");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/Black_List/select_page.jsp");
					failureView.forward(req, res);
					
					return;//程式中斷
				}
				
				
				/***************************2.開始查詢資料*****************************************/
				
				
				Black_ListService black_listSvc = new Black_ListService();
				Black_ListVO black_listVO = black_listSvc.getOneBlack_List(blk_uid);
				if(black_listVO == null) {
					errorMsgs.add("查無資料");
					
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/Black_List/select_page.jsp");
					failureView.forward(req, res);
					
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("black_listVO", black_listVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/Black_List/listOneBlack_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
			
				/***************************其他可能的錯誤處理*************************************/
				
			}catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
				.getRequestDispatcher("/back-end/Black_List/select_page.jsp");
				failureView.forward(req,res);
			
			}
		}
//		來自listAllBlack_List.jsp的請求
		if("getOne_For_Update".equals(action)) {
			
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數****************************************/
				
				String blk_uid = new String(req.getParameter("blk_uid"));
				/***************************2.開始查詢資料****************************************/
				
				Black_ListService black_listSvc = new Black_ListService();
				Black_ListVO black_listVO = black_listSvc.getOneBlack_List(blk_uid);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				
				req.setAttribute("black_listVO", black_listVO); // 資料庫取出的empVO物件,存入req
				String url ="/back-end/Black_List/update_Black_List_input.jsp";
				RequestDispatcher failureView =req.getRequestDispatcher(url);
				failureView.forward(req, res);					
				/***************************其他可能的錯誤處理**********************************/
								
		}catch(Exception e) {
			errorMsgs.add("無法取得要修改的資料:" +e.getMessage());
			RequestDispatcher failureView =req
					.getRequestDispatcher("/back-end/Black_List/listAllBlack_List.jsp");
			failureView.forward(req, res);			
		}
		}
		
		if ("update".equals(action)) { // 來自update_Black_List_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String blk_uid = new String(req.getParameter("blk_uid").trim());
			
				String blk_memid = req.getParameter("blk_memid");
			
				String blk_memidReg = "[M][E][M][0-9]{7}";
				if (blk_memid == null || blk_memid.trim().length() == 0) {
					errorMsgs.add("黑人家的: 請勿空白");
				
				} else if(!blk_memid.trim().matches(blk_memidReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("黑人家的會員編號必須開頭為大寫MEM與7碼數字");

	            }
				
				String beblk_memid = req.getParameter("beblk_memid").trim();
				String beblk_memidReg = "[M][E][M][0-9]{7}";
				if (beblk_memid == null || blk_memid.trim().length() == 0) {
					errorMsgs.add("被黑的: 請勿空白");
					
				} else if(!beblk_memid.trim().matches(beblk_memidReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("被黑的會員編號必須開頭為大寫MEM與7碼數字");
					
	            }
			
				

				Black_ListVO black_listVO = new Black_ListVO();
				black_listVO.setBlk_uid(blk_uid);
				black_listVO.setBlk_memid(blk_memid);
				black_listVO.setBeblk_memid(beblk_memid);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("black_listVO", black_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/Black_List/update_Black_List_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Black_ListService Black_ListSvc = new Black_ListService();
				black_listVO = Black_ListSvc.updateBlack_List(blk_uid, blk_memid, beblk_memid);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("black_listVO", black_listVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/Black_List/listOneBlack_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/Black_List/update_Black_List_input.jsp");
				failureView.forward(req, res);
			}
		}
		

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String blk_memid = req.getParameter("blk_memid");
			
				String blk_memidReg = "[M][E][M][0-9]{7}";
				if (blk_memid == null || blk_memid.trim().length() == 0) {
					errorMsgs.add("黑人家的: 請勿空白");
			
				} else if(!blk_memid.trim().matches(blk_memidReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("黑人家的會員編號必須開頭為大寫MEM與7碼數字");
			
	            }
				
				String beblk_memid = req.getParameter("beblk_memid").trim();
				String beblk_memidReg = "[M][E][M][0-9]{7}";
				if (beblk_memid == null || beblk_memid.trim().length() == 0) {
					errorMsgs.add("被黑的: 請勿空白");
					
				} else if(!beblk_memid.trim().matches(beblk_memidReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("被黑的會員編號必須開頭為大寫MEM與7碼數字");
					
	            }
				

				Black_ListVO black_listVO = new Black_ListVO();
			
				black_listVO.setBlk_memid(blk_memid);
				black_listVO.setBeblk_memid(beblk_memid);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("black_listVO", black_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/Black_List/addBlack_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Black_ListService black_listSvc = new Black_ListService();
				black_listVO = black_listSvc.addBlack_List(beblk_memid, beblk_memid);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/Black_List/listAllBlack_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/Black_List/addBlack_List.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String blk_uid = new String(req.getParameter("blk_uid"));
				
				/***************************2.開始刪除資料***************************************/
				Black_ListService black_listSvc = new Black_ListService();
				black_listSvc.deleteBlack_List(blk_uid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/Black_List/listAllBlack_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/Black_List/listAllBlack_List.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
