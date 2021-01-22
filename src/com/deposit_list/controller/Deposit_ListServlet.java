package com.deposit_list.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.deposit_list.model.Deposit_ListService;
import com.deposit_list.model.Deposit_ListVO;

@WebServlet("/back-end/deposit_list/deposit_list.do")
public class Deposit_ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 *
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("deposit_code");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入儲值訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/deposit_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String deposit_code = null;
				try {
					deposit_code = new String(str);
				} catch (Exception e) {
					errorMsgs.add("儲值訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/deposit_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Deposit_ListService deposit_listSvc = new Deposit_ListService();
				Deposit_ListVO deposit_listVO = deposit_listSvc.getOneDeposit_List(deposit_code);
				if (deposit_listVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/deposit_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("deposit_listVO", deposit_listVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/deposit_list/listOneDeposit_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/deposit_list/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String deposit_code = new String(req.getParameter("deposit_code"));
				
				/***************************2.開始查詢資料****************************************/
				Deposit_ListService deposit_listSvc = new Deposit_ListService();
				Deposit_ListVO deposit_listVO = deposit_listSvc.getOneDeposit_List(deposit_code);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("deposit_listVO", deposit_listVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/deposit_list/update_deposit_list_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/deposit_list/listAllDeposit_List.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String deposit_code = new String(req.getParameter("deposit_code"));
				
				Integer deposit_num = null;
				try {
					deposit_num = new Integer(req.getParameter("deposit_num").trim());
				} catch (NumberFormatException e) {
					deposit_num = 0;
					errorMsgs.add("儲值金額請填數字.");
				}
				
				java.sql.Date deposit_date = null;
				try {
					deposit_date = java.sql.Date.valueOf(req.getParameter("deposit_date").trim());
				} catch (IllegalArgumentException e) {
					deposit_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入儲值日期!");
				}
				
				String deposit_memid = req.getParameter("deposit_memid").trim();
				if (deposit_memid == null || deposit_memid.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				String pay_type = req.getParameter("pay_type").trim();
				
				String credit_num = req.getParameter("credit_num").trim();
				String credit_numReg = "^[(0-9)]{16}$";
				if (credit_num == null || credit_num.trim().length() == 0) {
//					errorMsgs.add("信用卡號: 請勿空白");
				} else if(!credit_num.trim().matches(credit_numReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信用卡號: 只能是數字, 且長度必需為16碼");
				}
				
				Integer credit_expiry_yy = 0;					
				if (!(req.getParameter("credit_expiry_yy").trim() == null || req.getParameter("credit_expiry_yy").trim().length() == 0)) {
					try {
						credit_expiry_yy = new Integer(req.getParameter("credit_expiry_yy").trim());
					} catch (NumberFormatException e) {
						credit_expiry_yy = 0;
						errorMsgs.add("信用卡到期（年）請填數字.");
					}
				}
				
				Integer credit_expiry_mm = 0;
				if (!(req.getParameter("credit_expiry_mm").trim() == null || req.getParameter("credit_expiry_mm").trim().length() == 0)) {
					try {
						credit_expiry_mm = new Integer(req.getParameter("credit_expiry_mm").trim());
					} catch (NumberFormatException e) {
						credit_expiry_mm = 0;
						errorMsgs.add("信用卡到期（月）請填數字.");
					}
				}
					
				
				Integer credit_security_num = 0;
				if (!(req.getParameter("credit_security_num").trim() == null || req.getParameter("credit_security_num").trim().length() == 0)) {
					try {
						credit_security_num = new Integer(req.getParameter("credit_security_num").trim());
					} catch (NumberFormatException e) {
						credit_security_num = 0;
						errorMsgs.add("信用卡安全碼請填數字.");
					}
				}
				
				String atm_id = req.getParameter("atm_id").trim();
				String atm_idReg = "^[(0-9)]{16}$";
				if (atm_id == null || atm_id.trim().length() == 0) {
//					errorMsgs.add("ATM 虛擬帳戶: 請勿空白");
				} else if(!atm_id.trim().matches(atm_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("ATM 虛擬帳戶: 只能是數字, 且長度必需為16碼");
				}
				
				String payment_sts = req.getParameter("payment_sts").trim();
//				if (payment_sts == null || payment_sts.trim().length() == 0) {
//					errorMsgs.add("收款狀態請勿空白");
//				}
				
				Deposit_ListVO deposit_listVO = new Deposit_ListVO();
				deposit_listVO.setDeposit_code(deposit_code);
				deposit_listVO.setDeposit_num(deposit_num);
				deposit_listVO.setDeposit_date(deposit_date);
				deposit_listVO.setDeposit_memid(deposit_memid);
				deposit_listVO.setPay_type(pay_type);
				deposit_listVO.setCredit_num(credit_num);
				deposit_listVO.setCredit_expiry_yy(credit_expiry_yy);
				deposit_listVO.setCredit_expiry_mm(credit_expiry_mm);
				deposit_listVO.setCredit_security_num(credit_security_num);
				deposit_listVO.setAtm_id(atm_id);
				deposit_listVO.setPayment_sts(payment_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("deposit_listVO", deposit_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/deposit_list/update_deposit_list_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Deposit_ListService deposit_listSvc = new Deposit_ListService();
				deposit_listVO = deposit_listSvc.updateDeposit_List(deposit_code, deposit_num, deposit_date, deposit_memid, pay_type, credit_num, credit_expiry_yy, credit_expiry_mm, credit_security_num, atm_id, payment_sts);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("deposit_listVO", deposit_listVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/deposit_list/listOneDeposit_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/deposit_list/update_deposit_list_input.jsp");
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
				Integer deposit_num = null;
				try {
					deposit_num = new Integer(req.getParameter("deposit_num").trim());
				} catch (NumberFormatException e) {
					deposit_num = 0;
					errorMsgs.add("儲值金額請填數字.");
				}
				
				java.sql.Date deposit_date = null;
				try {
					deposit_date = java.sql.Date.valueOf(req.getParameter("deposit_date").trim());
				} catch (IllegalArgumentException e) {
					deposit_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入儲值日期!");
				}
				
				String deposit_memid = req.getParameter("deposit_memid").trim();
				if (deposit_memid == null || deposit_memid.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				String pay_type = req.getParameter("pay_type").trim();
				
				String credit_num = req.getParameter("credit_num").trim();
				String credit_numReg = "^[(0-9)]{16}$";
				if (credit_num == null || credit_num.trim().length() == 0) {
//					errorMsgs.add("信用卡號: 請勿空白");
				} else if(!credit_num.trim().matches(credit_numReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信用卡號: 只能是數字, 且長度必需為16碼");
				}
				
				Integer credit_expiry_yy = 0;					
				if (!(req.getParameter("credit_expiry_yy").trim() == null || req.getParameter("credit_expiry_yy").trim().length() == 0)) {
					try {
						credit_expiry_yy = new Integer(req.getParameter("credit_expiry_yy").trim());
					} catch (NumberFormatException e) {
						credit_expiry_yy = 0;
						errorMsgs.add("信用卡到期（年）請填數字.");
					}
				}
				
				Integer credit_expiry_mm = 0;
				if (!(req.getParameter("credit_expiry_mm").trim() == null || req.getParameter("credit_expiry_mm").trim().length() == 0)) {
					try {
						credit_expiry_mm = new Integer(req.getParameter("credit_expiry_mm").trim());
					} catch (NumberFormatException e) {
						credit_expiry_mm = 0;
						errorMsgs.add("信用卡到期（月）請填數字.");
					}
				}
					
				
				Integer credit_security_num = 0;
				if (!(req.getParameter("credit_security_num").trim() == null || req.getParameter("credit_security_num").trim().length() == 0)) {
					try {
						credit_security_num = new Integer(req.getParameter("credit_security_num").trim());
					} catch (NumberFormatException e) {
						credit_security_num = 0;
						errorMsgs.add("信用卡安全碼請填數字.");
					}
				}
				
				String atm_id = req.getParameter("atm_id").trim();
				String atm_idReg = "^[(0-9)]{16}$";
				if (atm_id == null || atm_id.trim().length() == 0) {
//					errorMsgs.add("ATM 虛擬帳戶: 請勿空白");
				} else if(!atm_id.trim().matches(atm_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("ATM 虛擬帳戶: 只能是數字, 且長度必需為16碼");
				}
				
				String payment_sts = req.getParameter("payment_sts").trim();
//				if (payment_sts == null || payment_sts.trim().length() == 0) {
//					errorMsgs.add("收款狀態請勿空白");
//				}
				
				Deposit_ListVO deposit_listVO = new Deposit_ListVO();
				deposit_listVO.setDeposit_num(deposit_num);
				deposit_listVO.setDeposit_date(deposit_date);
				deposit_listVO.setDeposit_memid(deposit_memid);
				deposit_listVO.setPay_type(pay_type);
				deposit_listVO.setCredit_num(credit_num);
				deposit_listVO.setCredit_expiry_yy(credit_expiry_yy);
				deposit_listVO.setCredit_expiry_mm(credit_expiry_mm);
				deposit_listVO.setCredit_security_num(credit_security_num);
				deposit_listVO.setAtm_id(atm_id);
				deposit_listVO.setPayment_sts(payment_sts);
				


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("deposit_listVO", deposit_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/deposit_list/addDeposit_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Deposit_ListService deposit_listSvc = new Deposit_ListService();
				deposit_listVO = deposit_listSvc.addDeposit_List(deposit_num, deposit_date, deposit_memid, pay_type, credit_num, credit_expiry_yy, credit_expiry_mm, credit_security_num, atm_id, payment_sts);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/deposit_list/listAllDeposit_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/deposit_list/addDeposit_List.jsp");
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
				String deposit_code = new String(req.getParameter("deposit_code"));
				
				/***************************2.開始刪除資料***************************************/
				Deposit_ListService deposit_listSvc = new Deposit_ListService();
				deposit_listSvc.deleteDeposit_List(deposit_code);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/deposit_list/listAllDeposit_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/deposit_list/listAllDeposit_List.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
