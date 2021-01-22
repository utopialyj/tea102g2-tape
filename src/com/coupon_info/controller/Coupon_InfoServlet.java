package com.coupon_info.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.coupon_info.model.*;

@WebServlet("/back-end/coupon_info/coupon_info.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class Coupon_InfoServlet extends HttpServlet {
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
				String str = req.getParameter("cpon_code");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入優惠券編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_info/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String cpon_code = null;
				try {
					cpon_code = new String(str);
				} catch (Exception e) {
					errorMsgs.add("優惠券編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_info/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
				Coupon_InfoVO coupon_infoVO = coupon_infoSvc.getOneCoupon_Info(cpon_code);
				if (coupon_infoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_info/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("coupon_infoVO", coupon_infoVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/coupon_info/listOneCoupon_Info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_info/select_page.jsp");
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
				String cpon_code = new String(req.getParameter("cpon_code"));
				
				/***************************2.開始查詢資料****************************************/
				Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
				Coupon_InfoVO coupon_infoVO = coupon_infoSvc.getOneCoupon_Info(cpon_code);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("coupon_infoVO", coupon_infoVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/coupon_info/update_coupon_info_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_info/listAllCoupon_Info.jsp");
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
				String cpon_code = new String(req.getParameter("cpon_code"));
				
				String product_name = req.getParameter("product_name");
				String product_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)( )]{2,30}$";
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!product_name.trim().matches(product_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字、空格和_ , 且長度必需在2到30之間");
	            }

				String product_brand = req.getParameter("product_brand").trim();
				if (product_brand == null || product_brand.trim().length() == 0) {
					errorMsgs.add("商品品牌請勿空白");
				}
				
				Integer exc_condition = null;
				try {
					exc_condition = new Integer(req.getParameter("exc_condition").trim());
				} catch (NumberFormatException e) {
					exc_condition = 0;
					errorMsgs.add("兌換條件請填數字.");
				}
				
				Integer exe_deadline = null;
				try {
					exe_deadline = new Integer(req.getParameter("exe_deadline").trim());
				} catch (NumberFormatException e) {
					exe_deadline = 0;
					errorMsgs.add("兌換期限請填數字.");
				}
				
				String product_context = new String(req.getParameter("product_context").trim());
				
				String cpon_type = req.getParameter("cpon_type").trim();
				if (cpon_type == null || cpon_type.trim().length() == 0) {
					errorMsgs.add("優惠類別請勿空白");
				}
				
				String cpon_area_code = req.getParameter("cpon_area_code").trim();
				
				Integer exc_count = null;
				try {
					exc_count = new Integer(req.getParameter("exc_count").trim());
				} catch (NumberFormatException e) {
					exc_count = 0;
					errorMsgs.add("可兌換數量請填數字.");
				}
				
				Integer exced_count = null;
				try {
					exced_count = new Integer(req.getParameter("exced_count").trim());
				} catch (NumberFormatException e) {
					exced_count = 0;
					errorMsgs.add("已被兌換數量請填數字.");
				}
				
				String shelf_sts = req.getParameter("shelf_sts").trim();
				if (shelf_sts == null || shelf_sts.trim().length() == 0) {
					errorMsgs.add("商品上架狀態請勿空白");
				}
				
				java.sql.Date onshelf_date = null;
				try {
					onshelf_date = java.sql.Date.valueOf(req.getParameter("onshelf_date").trim());
				} catch (IllegalArgumentException e) {
					onshelf_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入上架日期!");
				}
				
				java.sql.Date cpon_offshelf_date = java.sql.Date.valueOf(req.getParameter("cpon_offshelf_date").trim());
	
				byte[] product_photo = null;
				try {
					Part part = req.getPart("product_photo");
					InputStream in = part.getInputStream();
//未傳入圖片不變更原本圖片					// if in.available() = 0
					product_photo = new byte[in.available()];
					in.read(product_photo);
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Coupon_InfoVO coupon_infoVO = new Coupon_InfoVO();
				coupon_infoVO.setCpon_code(cpon_code);
				coupon_infoVO.setProduct_name(product_name);
				coupon_infoVO.setProduct_brand(product_brand);
				coupon_infoVO.setExc_condition(exc_condition);
				coupon_infoVO.setExe_deadline(exe_deadline);
				coupon_infoVO.setProduct_photo(product_photo);
				coupon_infoVO.setProduct_context(product_context);
				coupon_infoVO.setCpon_type(cpon_type);
				coupon_infoVO.setCpon_area_code(cpon_area_code);
				coupon_infoVO.setExc_count(exc_count);
				coupon_infoVO.setExced_count(exced_count);
				coupon_infoVO.setShelf_sts(shelf_sts);
				coupon_infoVO.setOnshelf_date(onshelf_date);
				coupon_infoVO.setCpon_offshelf_date(cpon_offshelf_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coupon_infoVO", coupon_infoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_info/update_coupon_info_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
				coupon_infoVO = coupon_infoSvc.updateCoupon_Info(cpon_code, product_name, product_brand, exc_condition, exe_deadline, product_photo, 
						product_context, cpon_type, cpon_area_code, exc_count, exced_count, shelf_sts, onshelf_date, cpon_offshelf_date);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("coupon_infoVO", coupon_infoVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/coupon_info/listOneCoupon_Info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_info/update_coupon_info_input.jsp");
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
				String product_name = req.getParameter("product_name");
				String product_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)( )]{2,30}$";
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!product_name.trim().matches(product_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字、空格和_ , 且長度必需在2到30之間");
	            }
				
				String product_brand = req.getParameter("product_brand").trim();
				if (product_brand == null || product_brand.trim().length() == 0) {
					errorMsgs.add("商品品牌請勿空白");
				}
				
				Integer exc_condition = null;
				try {
					exc_condition = new Integer(req.getParameter("exc_condition").trim());
				} catch (NumberFormatException e) {
					exc_condition = 0;
					errorMsgs.add("兌換條件請填數字.");
				}
				
				Integer exe_deadline = null;
				try {
					exe_deadline = new Integer(req.getParameter("exe_deadline").trim());
				} catch (NumberFormatException e) {
					exe_deadline = 0;
					errorMsgs.add("兌換期限請填數字.");
				}
				
				
				String product_context = new String(req.getParameter("product_context").trim());
				
				String cpon_type = req.getParameter("cpon_type").trim();
				if (cpon_type == null || cpon_type.trim().length() == 0) {
					errorMsgs.add("優惠類別請勿空白");
				}
				
				String cpon_area_code = req.getParameter("cpon_area_code").trim();
				
				Integer exc_count = null;
				try {
					exc_count = new Integer(req.getParameter("exc_count").trim());
				} catch (NumberFormatException e) {
					exc_count = 0;
					errorMsgs.add("可兌換數量請填數字.");
				}
				
				Integer exced_count = null;
				try {
					exced_count = new Integer(req.getParameter("exced_count").trim());
				} catch (NumberFormatException e) {
					exced_count = 0;
					errorMsgs.add("已被兌換數量請填數字.");
				}
				
				String shelf_sts = req.getParameter("shelf_sts").trim();
//				if (shelf_sts == null || shelf_sts.trim().length() == 0) {
//					errorMsgs.add("商品上架狀態請勿空白");
//				}
				
				java.sql.Date onshelf_date = null;
				try {
					onshelf_date = java.sql.Date.valueOf(req.getParameter("onshelf_date").trim());
				} catch (IllegalArgumentException e) {
					onshelf_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入上架日期!");
				}
				
				java.sql.Date cpon_offshelf_date = null;
				if ( req.getParameter("cpon_offshelf_date").trim().length() != 0) {
					cpon_offshelf_date = java.sql.Date.valueOf(req.getParameter("cpon_offshelf_date").trim());
				}

				
				byte[] product_photo = null;
				try {
					Part part = req.getPart("product_photo");
					InputStream in = part.getInputStream();
					product_photo = new byte[in.available()];
					in.read(product_photo);
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Coupon_InfoVO coupon_infoVO = new Coupon_InfoVO();
				coupon_infoVO.setProduct_name(product_name);
				coupon_infoVO.setProduct_brand(product_brand);
				coupon_infoVO.setExc_condition(exc_condition);
				coupon_infoVO.setExe_deadline(exe_deadline);
				coupon_infoVO.setProduct_photo(product_photo);
				coupon_infoVO.setProduct_context(product_context);
				coupon_infoVO.setCpon_type(cpon_type);
				coupon_infoVO.setCpon_area_code(cpon_area_code);
				coupon_infoVO.setExc_count(exc_count);
				coupon_infoVO.setExced_count(exced_count);
				coupon_infoVO.setShelf_sts(shelf_sts);
				coupon_infoVO.setOnshelf_date(onshelf_date);
				coupon_infoVO.setCpon_offshelf_date(cpon_offshelf_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coupon_infoVO", coupon_infoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coupon_info/addCoupon_Info.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
				coupon_infoVO = coupon_infoSvc.addCoupon_Info(product_name, product_brand, exc_condition, exe_deadline, product_photo, 
						product_context, cpon_type, cpon_area_code, exc_count, exced_count, shelf_sts, onshelf_date, cpon_offshelf_date);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/coupon_info/listAllCoupon_Info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_info/addCoupon_Info.jsp");
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
				String cpon_code = new String(req.getParameter("cpon_code"));
				
				/***************************2.開始刪除資料***************************************/
				Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
				coupon_infoSvc.deleteCoupon_Info(cpon_code);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/coupon_info/listAllCoupon_Info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coupon_info/listAllCoupon_Info.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
