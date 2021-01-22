package com.member_authroity.cotroller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member_authroity.model.Member_AuthroityService;
import com.member_authroity.model.Member_AuthroityVO;

/**
 * Servlet implementation class Member_AuthroityServlet
 */
@WebServlet("/Member_AuthroityServlet")
public class Member_AuthroityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    
    public Member_AuthroityServlet() {
        super();
    }

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = request.getParameter("verify_level");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("權限格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_authroity/auth_select_page.jsp");
					failureView.forward(request, response);
					return;//程式中斷
				}
				
				Integer verify_level = null;
				try {
					verify_level = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_authroity/auth_select_page.jsp");
					failureView.forward(request, response);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Member_AuthroityService authSvc = new Member_AuthroityService();
				Member_AuthroityVO member_authroityVO = authSvc.getOneM_Auth(verify_level);
				if (member_authroityVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_authroity/auth_select_page.jsp");
					failureView.forward(request, response);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				request.setAttribute("member_authroityVO", member_authroityVO); // 資料庫取出的member_authroityVO物件,存入req
				String url = "/back-end/member_authroity/listOneAuth.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(request, response);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_authroity/auth_select_page.jsp");
				failureView.forward(request, response);
			}
		}
//		
//		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer verify_level = new Integer(request.getParameter("verify_level"));
				System.out.println(verify_level+"aaaaaaa");
//				System.out.println("a");

				/***************************2.開始查詢資料****************************************/
				Member_AuthroityService authSvc = new Member_AuthroityService();
				Member_AuthroityVO member_authroityVO = authSvc.getOneM_Auth(verify_level);
//				System.out.println("b");

				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				request.setAttribute("member_authroityVO", member_authroityVO);         // 資料庫取出的member_authroityVO物件,存入req
				String url = "/back-end/member_authroity/update_auth.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(request, response);
//				System.out.println("c");

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) { 
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
			e.printStackTrace();
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_authroity/listAllAuthroity.jsp");
				failureView.forward(request, response);
			}
		}
						

//		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
//			System.out.println("123");
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer verify_level = new Integer(request.getParameter("verify_level").trim());
				
				Integer chat_auth = null;
				try {
				    chat_auth = new Integer(request.getParameter("chat_auth").trim());
				}catch (NumberFormatException e) {
					chat_auth=0;
					errorMsgs.add("聊天權限請填數字.");
				}
//				System.out.println("456");
				Integer post_auth = null;
				try {
					post_auth = new Integer(request.getParameter("post_auth").trim());
				}catch (NumberFormatException e) {
					post_auth=0;
					errorMsgs.add("動態權限請填數字.");
				}	
//				System.out.println("789");
				Integer meat_auth = null;
				try {
					meat_auth = new Integer(request.getParameter("meat_auth").trim());
				}catch (NumberFormatException e) {
					meat_auth=0;
					errorMsgs.add("配對權限請填數字.");
				}
//				System.out.println("999");
				Integer point_auth = null;
				try {
					point_auth = new Integer(request.getParameter("point_auth").trim());
				}catch (NumberFormatException e) {
					point_auth=0;
					errorMsgs.add("點數權限請填數字.");
				}
//				System.out.println("888");
				Integer join_event_auth = null;
				try {
					join_event_auth = new Integer(request.getParameter("join_event_auth").trim());
				}catch (NumberFormatException e) {
					join_event_auth=0;
					errorMsgs.add("參加活動權限請填數字.");
				}
//System.out.println("777");
				Integer host_auth = null;
				try {
					host_auth = new Integer(request.getParameter("host_auth").trim());
				}catch (NumberFormatException e) {
					host_auth=0;
					errorMsgs.add("舉辦活動權限請填數字.");
				}
//				System.out.println("6666");
				Integer log_auth = null;
				try {
					log_auth = new Integer(request.getParameter("log_auth").trim());
				}catch (NumberFormatException e) {
					log_auth=0;
					errorMsgs.add("登入權限請填數字.");
				}
//				Integer deptno = new Integer(request.getParameter("deptno").trim());
//System.out.println("5555");
				Member_AuthroityVO member_authroityVO=new Member_AuthroityVO();
				member_authroityVO.setVerify_level(verify_level);
//				System.out.println(verify_level);
				member_authroityVO.setChat_auth(chat_auth);
				member_authroityVO.setPost_auth(post_auth);
				member_authroityVO.setMeat_auth(meat_auth);
				member_authroityVO.setPoint_auth(point_auth);
				member_authroityVO.setJoin_event_auth(join_event_auth);
				member_authroityVO.setHost_auth(host_auth);
				member_authroityVO.setLog_auth(log_auth);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					request.setAttribute("member_authroityVO", member_authroityVO); // 含有輸入格式錯誤的member_authroityVO物件,也存入req
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_authroity/update_auth.jsp");
					failureView.forward(request, response);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Member_AuthroityService authSvc = new Member_AuthroityService();
				member_authroityVO = authSvc.updateM_Auth(verify_level, chat_auth, post_auth, meat_auth, point_auth,join_event_auth, host_auth,log_auth);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				request.setAttribute("member_authroityVO", member_authroityVO); // 資料庫update成功後,正確的的member_authroityVO物件,存入request
				String url = "/back-end/member_authroity/listOneAuth.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(request, response);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_authroity/update_auth.jsp");
				failureView.forward(request, response);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求   
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
			
//			System.out.println("1");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer verify_level = null;
				try {
					verify_level = new Integer(request.getParameter("verify_level").trim());
				}catch (NumberFormatException e) {
					verify_level=0;
					errorMsgs.add("聊天權限請填數字.");
				}
				
				Integer chat_auth = null;
				try {
				    chat_auth = new Integer(request.getParameter("chat_auth").trim());
				}catch (NumberFormatException e) {
					chat_auth=0;
					errorMsgs.add("聊天權限請填數字.");
				}

//				System.out.println("2");
				Integer post_auth = null;
				try {
					post_auth = new Integer(request.getParameter("post_auth").trim());
				} catch (NumberFormatException e) {
					post_auth = 0;
					errorMsgs.add("動態權限請填數字.");
				}
//				System.out.println("3");
				Integer meat_auth = null;
				try {
					meat_auth = new Integer(request.getParameter("meat_auth").trim());
				} catch (NumberFormatException e) {
					meat_auth = 0;
					errorMsgs.add("配對權限請填數字.");
				}
//				System.out.println("4");
				Integer point_auth = null;
				try {
					point_auth = new Integer(request.getParameter("point_auth").trim());
				} catch (NumberFormatException e) {
					point_auth = 0;
					errorMsgs.add("點數權限請填數字.");
				}
				
//				System.out.println("5");
				Integer join_event_auth = null;
				try {
					join_event_auth = new Integer(request.getParameter("join_event_auth").trim());
				} catch (NumberFormatException e) {
					join_event_auth = 0;
					errorMsgs.add("參加揪團權限請填數字.");
				}
//				System.out.println("6");
				Integer host_auth = null;
				try {
					host_auth = new Integer(request.getParameter("host_auth").trim());
				} catch (NumberFormatException e) {
					host_auth = 0;
					errorMsgs.add("舉辦揪團權限請填數字.");
				}
//				System.out.println("7");
				Integer log_auth = null;
				try {
					log_auth = new Integer(request.getParameter("log_auth").trim());
				} catch (NumberFormatException e) {
					log_auth = 0;
					errorMsgs.add("登入權限請填數字.");
				}
				Member_AuthroityVO member_authroityVO=new Member_AuthroityVO();
				member_authroityVO.setVerify_level(verify_level);
				member_authroityVO.setChat_auth(chat_auth);
				member_authroityVO.setPost_auth(post_auth);
				member_authroityVO.setMeat_auth(meat_auth);
				member_authroityVO.setPoint_auth(point_auth);
				member_authroityVO.setJoin_event_auth(join_event_auth);
				member_authroityVO.setHost_auth(host_auth);
				member_authroityVO.setLog_auth(log_auth);
//				System.out.println("8");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					request.setAttribute("member_authroityVO", member_authroityVO); // 含有輸入格式錯誤的member_authroityVO物件,也存入req
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_authroity/addAuthroity.jsp");
					failureView.forward(request, response);
					return;
				}
//				System.out.println("9");
				/***************************2.開始新增資料***************************************/
				Member_AuthroityService authSvc = new Member_AuthroityService();
				member_authroityVO = authSvc.addM_Auth(verify_level,chat_auth, post_auth, meat_auth, point_auth, join_event_auth, host_auth,log_auth);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/member_authroity/listAllAuthroity.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(request, response);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_authroity/addAuthroity.jsp");
				failureView.forward(request, response);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			System.out.println("a");
			try {
				/***************************1.接收請求參數***************************************/
				Integer verify_level = new Integer(request.getParameter("verify_level"));
				System.out.println("b");
				System.out.println(verify_level);

				/***************************2.開始刪除資料***************************************/
				Member_AuthroityService authSvc = new Member_AuthroityService();
				System.out.println("c");
				authSvc.deleteM_Auth(verify_level);
				System.out.println(verify_level);
				System.out.println("d");

				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/member_authroity/listAllAuthroity.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(request, response);
				System.out.println("e");

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_authroity/addAuthroity.jsp");
				failureView.forward(request, response);
				System.out.println("f");

			}
		}
	}

}
