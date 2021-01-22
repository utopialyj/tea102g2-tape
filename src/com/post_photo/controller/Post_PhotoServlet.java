package com.post_photo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.post_photo.model.Post_PhotoService;
import com.post_photo.model.Post_PhotoVO;


@WebServlet("/back-end/post_photo/post_photo.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class Post_PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		// 來自select_page.jsp的請求
		//顯示一筆指定資料
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("photo_uid");
				if(str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入動態照片編號");
				}
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String photo_uid = null;
				try {
					photo_uid = new String(str);
				}catch(Exception e) {
					errorMsgs.add("動態照片編號格式不正確");
				}
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				Post_PhotoVO post_PhotoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);
				if(post_PhotoVO == null) {
					errorMsgs.add("查無資料");
				}
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("post_PhotoVO", post_PhotoVO);
				String url = "/back-end/post_photo/listOnePostPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/

			}catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/select_page.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		//來自listAllPost_Photo.jsp的請求
		//選擇一筆做修改
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String photo_uid = new String(req.getParameter("photo_uid"));
				/***************************2.開始查詢資料****************************************/
				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				Post_PhotoVO post_PhotoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("post_PhotoVO", post_PhotoVO);
				String url = "/back-end/post_photo/update_post_photo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req,res);
				/***************************其他可能的錯誤處理**********************************/	
			}catch(Exception e) {
				errorMsgs.add("無法取得要修改的資料:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/listAllPost_Photo.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 來自update_post_photo_input.jsp的請求
		//送出修改資料
		if("update".equals(action)) {
			
			String photo_uid = new String(req.getParameter("photo_uid").trim());
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Post_PhotoService post_PhotoSvc = new Post_PhotoService();
			Post_PhotoVO post_PhotoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				byte[] photo = null;
				try {
					Part part = req.getPart("photo");
					InputStream in = part.getInputStream();
					
					photo = new byte[in.available()];
					in.read(photo);
					in.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				String post_code = new String(req.getParameter("post_code").trim());
				
				Integer photo_sts = new Integer(req.getParameter("photo_sts").trim());
				
//				Post_PhotoVO post_PhotoVO = new Post_PhotoVO();
				post_PhotoVO.setPhoto_uid(photo_uid);
				post_PhotoVO.setPhoto(photo);
				post_PhotoVO.setPost_code(post_code);
				post_PhotoVO.setPhoto_sts(photo_sts);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("post_PhotoVO", post_PhotoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/update_post_photo_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始修改資料*****************************************/
//				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				post_PhotoVO = post_PhotoSvc.updatePostPhoto(photo_uid, photo, post_code, photo_sts);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
			
				req.setAttribute("post_PhotoVO", post_PhotoVO);
				String url = "/back-end/post_photo/listOnePostPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				
			}catch(Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/update_post_photo_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 來自addEmp.jsp的請求  
		//新增一筆資料
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				byte[] photo = null;
				try {
					Part part = req.getPart("photo");
					InputStream in = part.getInputStream();
					photo = new byte[in.available()];
					in.read();
					in.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				String post_code = req.getParameter("post_code");
				String post_codeReg = "[P][S][0-9]{7}";
				if(post_code == null || post_code.trim().length()==0) {
					errorMsgs.add("動態編號請勿空白");
				}else if (!post_code.trim().matches(post_codeReg)) {
					errorMsgs.add("動態編號為PS(大寫)開頭與7碼0-9數字");							
				}
				
				Integer photo_sts = null;
				try {
				photo_sts = new Integer(req.getParameter("photo_sts").trim());
				}catch(NullPointerException e ) {
					photo_sts = 1;
					errorMsgs.add("請設置照片狀態");
				}
				
				Post_PhotoVO  post_PhotoVO = new Post_PhotoVO();
//				post_PhotoVO.setPhoto_uid(photo_uid);
				post_PhotoVO.setPhoto(photo);
				post_PhotoVO.setPost_code(post_code);
				post_PhotoVO.setPhoto_sts(photo_sts);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("post_PhotoVO", post_PhotoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/addPostPhoto.jsp");
					failureView.forward(req, res);
					
					return;
				}
				/***************************2.開始新增資料***************************************/
				Post_PhotoService post_PhotoSvc =  new Post_PhotoService();
				post_PhotoVO = post_PhotoSvc.addPostPhoto(photo, post_code, photo_sts);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url ="/back-end/post_photo/listAllPost_Photo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理**********************************/	
			
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post_photo/addPost_Photo.jsp");
				failureView.forward(req, res);
				
			}
		}
	}

}
