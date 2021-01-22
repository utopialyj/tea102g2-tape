package com.hobby_list.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hobby_list.model.Hobby_ListService;
import com.hobby_list.model.Hobby_ListVO;



@WebServlet("/Hobby_ListServlet")
public class Hobby_ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("listAllHobby".equals(action)) {
			
			String url = "/front-end/Match/Hobby_List/hobby.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		
			return;
		}
		
		
		if("getOne_For_Display".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
					
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
			try {
				String str = req.getParameter("hob_code");
				if(str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸興趣編號");
					
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Hobby_List/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				String hob_code = null;
				try {
					hob_code = new String(str);
					
				}catch(Exception e) {
					errorMsgs.add("格式不正確");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Hobby_List/select_page.jsp");
					failureView.forward(req, res);
					
					return;//程式中斷
				}
				
				
				/***************************2.開始查詢資料*****************************************/
				
				
				Hobby_ListService hobby_listSvc = new Hobby_ListService();
				Hobby_ListVO hobby_listVO = hobby_listSvc.getOneHobby_List(hob_code);
				if(hobby_listVO == null) {
					errorMsgs.add("查無資料");
					
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Hobby_List/select_page.jsp");
					failureView.forward(req, res);
					
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("hobby_listVO", hobby_listVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/Match/Hobby_List/listOneHobby_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
			
				/***************************其他可能的錯誤處理*************************************/
				
			}catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/Match/Hobby_List/select_page.jsp");
				failureView.forward(req,res);
			
			}
		}
//		來自listAllBlack_List.jsp的請求
		if("getOne_For_Update".equals(action)) {
			
			List<String>errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數****************************************/
				
				String hob_code = new String(req.getParameter("hob_code"));
				/***************************2.開始查詢資料****************************************/
				
				Hobby_ListService hobby_listSvc = new Hobby_ListService();
				Hobby_ListVO hobby_listVO = hobby_listSvc.getOneHobby_List(hob_code);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				
				req.setAttribute("hobby_listVO", hobby_listVO); // 資料庫取出的empVO物件,存入req
				String url ="/front-end/Match/Hobby_List/update_Hobby_List_input.jsp";
				RequestDispatcher failureView =req.getRequestDispatcher(url);
				failureView.forward(req, res);					
				/***************************其他可能的錯誤處理**********************************/
								
		}catch(Exception e) {
			errorMsgs.add("無法取得要修改的資料:" +e.getMessage());
			RequestDispatcher failureView =req
					.getRequestDispatcher("/front-end/Match/Hobby_List/listAllHobby_List.jsp");
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
				String hob_code = new String(req.getParameter("hob_code").trim());
			
				String hob_codeReg = "[0-9]{2}";
				if (hob_code == null || hob_code.trim().length() == 0) {
					errorMsgs.add("興趣編號: 請勿空白");
				
				} else if(!hob_code.trim().matches(hob_codeReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("興趣編號必須2碼數字");

	            }
				
				String hob = req.getParameter("hob").trim();
				if (hob == null || hob.trim().length() == 0) {
					errorMsgs.add("興趣: 請勿空白");
					
				}
			
				

				Hobby_ListVO hobby_listVO = new Hobby_ListVO();
				hobby_listVO.setHob_code(hob_code);
				hobby_listVO.setHob(hob);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("hobby_listVO", hobby_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Hobby_List/update_Hobby_List_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Hobby_ListService Hobby_ListSvc = new Hobby_ListService();
				hobby_listVO = Hobby_ListSvc.updateHobby_List(hob_code, hob);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("hobby_listVO", hobby_listVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/Match/Hobby_List/listOneHobby_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Hobby_List/update_Hobby_List_input.jsp");
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
				String hob_code = req.getParameter("hob_code");
			
				String hob_codeReg = "[0-9]{2}";
				if (hob_code == null || hob_code.trim().length() == 0) {
					errorMsgs.add("興趣編號: 請勿空白");
			
				} else if(!hob_code.trim().matches(hob_codeReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("興趣編號必須為2碼數字");
			
	            }
				
				String hob = req.getParameter("hob").trim();
				
				if (hob == null || hob.trim().length() == 0) {
					errorMsgs.add("興趣: 請勿空白");
					
				}			

				Hobby_ListVO hobby_listVO = new Hobby_ListVO();
			
				hobby_listVO.setHob_code(hob_code);
				hobby_listVO.setHob(hob);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("hobby_listVO", hobby_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Hobby_List/addHobby_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Hobby_ListService hobby_listSvc = new Hobby_ListService();
				hobby_listVO = hobby_listSvc.addHobby_List(hob_code, hob);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/Match/Hobby_List/listAllHobby_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Hobby_List/addHobby_List.jsp");
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
				String hob_code = new String(req.getParameter("hob_code"));
				
				/***************************2.開始刪除資料***************************************/
				Hobby_ListService hobby_listSvc = new Hobby_ListService();
				hobby_listSvc.deleteHobby_List(hob_code);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/Match/Hobby_List/listAllHobby_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Hobby_List/listAllHobby_List.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
