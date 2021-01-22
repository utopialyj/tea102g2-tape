package com.coupon_list.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.coupon_list.model.Coupon_ListService;
import com.coupon_list.model.Coupon_ListVO;

@WebServlet("/back-end/coupon_list/coupon_list.do")
public class Coupon_ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

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
				String str = req.getParameter("cpon_num");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入優惠券序號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String cpon_num = null;
				try {
					cpon_num = new String(str);
				} catch (Exception e) {
					errorMsgs.add("優惠券序號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Coupon_ListService coupon_listSvc = new Coupon_ListService();
				Coupon_ListVO coupon_listVO = coupon_listSvc.getOneCoupon_List(cpon_num);
				if (coupon_listVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_list/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("coupon_listVO", coupon_listVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/coupon_list/listOneCoupon_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_list/select_page.jsp");
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
				String cpon_num = new String(req.getParameter("cpon_num"));
				
				/***************************2.開始查詢資料****************************************/
				Coupon_ListService coupon_listSvc = new Coupon_ListService();
				Coupon_ListVO coupon_listVO = coupon_listSvc.getOneCoupon_List(cpon_num);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("coupon_listVO", coupon_listVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/coupon_list/update_coupon_list_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_list/listAllCoupon_List.jsp");
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
				String cpon_num = new String(req.getParameter("cpon_num"));
				
				String cpon_code = req.getParameter("cpon_code").trim();
				if (cpon_code == null || cpon_code.trim().length() == 0) {
					errorMsgs.add("優惠券編號請勿空白");
				}
				
				java.sql.Date cpon_num_bdate = null;
				try {
					cpon_num_bdate = java.sql.Date.valueOf(req.getParameter("cpon_num_bdate").trim());
				} catch (IllegalArgumentException e) {
					cpon_num_bdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入序號產生日期!");
				}
				
				String exc_sts = req.getParameter("exc_sts").trim();
				
				java.sql.Date cpon_expirydate = null;
				try {
					cpon_expirydate = java.sql.Date.valueOf(req.getParameter("cpon_expirydate").trim());
				} catch (IllegalArgumentException e) {
					cpon_expirydate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入優惠券到期日!");
				}
				
				String exc_memid = req.getParameter("exc_memid").trim();
				if (exc_memid == null || exc_memid.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				Coupon_ListVO coupon_listVO = new Coupon_ListVO();
				coupon_listVO.setCpon_num(cpon_num);
				coupon_listVO.setCpon_code(cpon_code);
				coupon_listVO.setCpon_num_bdate(cpon_num_bdate);
				coupon_listVO.setExc_sts(exc_sts);
				coupon_listVO.setCpon_expirydate(cpon_expirydate);
				coupon_listVO.setExc_memid(exc_memid);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coupon_listVO", coupon_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_list/update_coupon_list_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Coupon_ListService coupon_listSvc = new Coupon_ListService();
				coupon_listVO = coupon_listSvc.updateCoupon_List(cpon_num, cpon_code, cpon_num_bdate, exc_sts, cpon_expirydate, exc_memid);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("coupon_listVO", coupon_listVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/coupon_list/listOneCoupon_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_list/update_coupon_list_input.jsp");
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
				String cpon_code = req.getParameter("cpon_code").trim();
				if (cpon_code == null || cpon_code.trim().length() == 0) {
					errorMsgs.add("優惠券編號請勿空白");
				}
				
				java.sql.Date cpon_num_bdate = null;
				try {
					cpon_num_bdate = java.sql.Date.valueOf(req.getParameter("cpon_num_bdate").trim());
				} catch (IllegalArgumentException e) {
					cpon_num_bdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入序號產生日期!");
				}
				
				String exc_sts = req.getParameter("exc_sts").trim();
				
				java.sql.Date cpon_expirydate = null;
				try {
					cpon_expirydate = java.sql.Date.valueOf(req.getParameter("cpon_expirydate").trim());
				} catch (IllegalArgumentException e) {
					cpon_expirydate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入優惠券到期日!");
				}
				
				String exc_memid = req.getParameter("exc_memid").trim();
				if (exc_memid == null || exc_memid.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				
				
				Coupon_ListVO coupon_listVO = new Coupon_ListVO();
				coupon_listVO.setCpon_code(cpon_code);;
				coupon_listVO.setCpon_num_bdate(cpon_num_bdate);;
				coupon_listVO.setExc_sts(exc_sts);;
				coupon_listVO.setCpon_expirydate(cpon_expirydate);
				coupon_listVO.setExc_memid(exc_memid);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coupon_listVO", coupon_listVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_list/addCoupon_List.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Coupon_ListService coupon_listSvc = new Coupon_ListService();
				coupon_listVO = coupon_listSvc.addCoupon_List(cpon_code, cpon_num_bdate, exc_sts, cpon_expirydate, exc_memid);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/coupon_list/listAllCoupon_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_list/addCoupon_List.jsp");
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
				String cpon_num = new String(req.getParameter("cpon_num"));
				
				/***************************2.開始刪除資料***************************************/
				Coupon_ListService coupon_listSvc = new Coupon_ListService();
				coupon_listSvc.deleteCoupon_List(cpon_num);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/coupon_list/listAllCoupon_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_list/listAllCoupon_List.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
