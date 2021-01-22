package com.member_hobby.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.member_hobby.model.Member_HobbyService;
import com.member_hobby.model.Member_HobbyVO;
import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;

@WebServlet("/Member_HobbyServlet")
public class Member_HobbyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
		if("deleteHob".equals(action)) {
			
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO"); 
			String memUid = member_infoVO.getMem_id();
			Member_HobbyService memHobSvc = new Member_HobbyService();
			String[] delHob = req.getParameterValues("delHob");
			for(String key : delHob) {
				memHobSvc.delete(memUid, key);
			}
			String url = req.getContextPath();
			url += "/front-end/index.jsp";
			res.sendRedirect(url);
		}
		
		
//		會員新增興趣
		if("addHobby".equals(action)) {

			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO"); 
			String memUid = member_infoVO.getMem_id();
			Member_HobbyService memHobSvc = new Member_HobbyService();
					
			String[] hobby =  req.getParameterValues("hobby");
			
			List<String> befhobby = memHobSvc.findmemhob(memUid);
			
			for(int i = 0 ; i < hobby.length ; i++) {
				if(!befhobby.contains(hobby[i])) {
					memHobSvc.addMember_Hobby(memUid, hobby[i]);
				}
				
			}
			
			String url = req.getContextPath();
			url += "/front-end/index.jsp";
			res.sendRedirect(url);
		
	}
		
//		好友推薦(無過濾興趣)
		if ("getHobby_For_Display".equals(action)) {
			
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO"); 
			Member_HobbyService memHobSvc = new Member_HobbyService();
			String memUid = member_infoVO.getMem_id();
			
			try {
				List<String> list = memHobSvc.getMatchMemberList(member_infoVO.getMem_id());
				session.setAttribute("list", list);
				session.setAttribute("memUid", memUid);
				
			}catch(RuntimeException e) {
				String url_NoHobby = req.getContextPath() + "/front-end/profile/introduction.jsp?Member="+memUid;
				res.sendRedirect(url_NoHobby);
				return;
			}
			// 用目前會員ID找自己的興趣，找出符合興趣的會員list
			
			String url = "/front-end/Match/Member_Hobby/listOneMember_Hobby.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
			return;
		}

//		好友推薦(過濾興趣)
		if("recommendWithHob".equals(action)) {
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
			Member_HobbyService memHobSvc = new Member_HobbyService();
			String memUid = member_infoVO.getMem_id();
			
			//取得要過濾的興趣
			String[] hobby =  req.getParameterValues("hobby");
			List<String> hoblist = null;
			List<String> withHob = null;
			try{
				if(hobby != null) {
//				沒有填選過濾興趣
				hoblist = Arrays.asList(hobby);
				withHob = memHobSvc.getMatchWithHobby(memUid, hoblist);
			}else {
				
				withHob = memHobSvc.getMatchMemberList(member_infoVO.getMem_id());
			}
			
			//用過濾興趣找對象的ID
			
			session.setAttribute("withHob", withHob);
			session.setAttribute("memUid", memUid);
			}catch(RuntimeException e) {
				String url_NoHobby = req.getContextPath() +  "/front-end/profile/introduction.jsp?Member="+memUid;
				res.sendRedirect(url_NoHobby);
				return;
			}
			String url = "/front-end/Match/Member_Hobby/withhob.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
			return;
		}
		
		
		if("refresh".equals(action)) {
			String url = "/front-end/Match/Member_Hobby/listOneMember_Hobby.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}
		
//		推薦首頁按鍵
		if("recommendHome".equals(action)) {
			System.out.println("進入home");
			String url = "/front-end/Match/Member_Hobby/listOneMember_Hobby.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}
		
//		顯示頭貼
		if("display_pic".equals(action)) {
			
			Member_InfoService memberSvc = new Member_InfoService();
			String member_id = req.getParameter("Member");
			OutputStream op = res.getOutputStream();
			byte[] pic = null;
			
			if( memberSvc.getOneM_Info(member_id).getHeadshot() != null) {
				pic  = memberSvc.getOneM_Info(member_id).getHeadshot();
				
			}else {
				InputStream in =  getServletContext().getResourceAsStream("/front-end/acts/images/kali.jpg");
				pic = new byte[in.available()];
				res.setContentLengthLong(pic.length);
				in.read(pic);
				
				in.close();
			}
			
			op.write(pic);
			op.close();
	}
		
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			try {
				String str = req.getParameter("mem_hob_uid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸會員興趣編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Member_Hobby/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String mem_hob_uid = null;
				try {
					mem_hob_uid = new String(str);

				} catch (Exception e) {
					errorMsgs.add("格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Member_Hobby/select_page.jsp");
					failureView.forward(req, res);

					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/

				Member_HobbyService member_hobbySvc = new Member_HobbyService();
				Member_HobbyVO member_hobbyVO = member_hobbySvc.getOneMember_Hobby(mem_hob_uid);
				if (member_hobbyVO == null) {
					errorMsgs.add("查無資料");

				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Member_Hobby/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/

				req.setAttribute("member_hobbyVO", member_hobbyVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/Match/Member_Hobby/listOneMember_Hobby_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Member_Hobby/select_page.jsp");
				failureView.forward(req, res);

			}
		}

//		來自listAllBlack_List.jsp的請求

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/

				String mem_hob_uid = new String(req.getParameter("mem_hob_uid"));
				/*************************** 2.開始查詢資料 ****************************************/

				Member_HobbyService member_hobbySvc = new Member_HobbyService();
				Member_HobbyVO member_hobbyVO = member_hobbySvc.getOneMember_Hobby(mem_hob_uid);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("member_hobbyVO", member_hobbyVO);
				String url = "/front-end/Match/Member_Hobby/update_Member_Hobby_input.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Member_Hobby/listAllMember_Hobby.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mem_hob_uid = new String(req.getParameter("mem_hob_uid").trim());

				String hob_memid = req.getParameter("hob_memid");

				String hob_memidReg = "[M][E][M][0-9]{7}";

				if (hob_memid == null || hob_memid.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");

				} else if (!hob_memid.trim().matches(hob_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號必須開頭為大寫MEM_HOB與7碼數字");

				}

				String hob_code = req.getParameter("hob_code").trim();
				String hob_codeReg = req.getParameter("[1-25]{2}");
				if (hob_memid == null || hob_memid.trim().length() == 0) {
					errorMsgs.add("興趣編號: 請勿空白");

				} else if (!hob_code.trim().matches(hob_codeReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("興趣編號必須1~25與2碼數字");
				}

				Member_HobbyVO member_hobbyVO = new Member_HobbyVO();
				member_hobbyVO.setMem_hob_uid(mem_hob_uid);
				member_hobbyVO.setHob_memid(hob_memid);
				member_hobbyVO.setHob_code(hob_code);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member_hobbyVO", member_hobbyVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Member_Hobby/update_Member_Hobby_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				Member_HobbyService Member_HobbySvc = new Member_HobbyService();
				member_hobbyVO = Member_HobbySvc.updateMember_Hobby(mem_hob_uid, hob_memid, hob_code);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_hobbyVO", member_hobbyVO);
				String url = "/front-end/Match/Member_Hobby/listOneMember_Hobby.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Member_Hobby/update_Member_Hobby_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			try {

				String hob_memid = req.getParameter("hob_memid");

				String hob_memidReg = "[M][E][M][0-9]{7}";

				if (hob_memid == null || hob_memid.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");

				} else if (!hob_memid.trim().matches(hob_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號必須開頭為大寫MEM與7碼數字");

				}

				String hob_code = req.getParameter("hob_code").trim();
				String hob_codeReg = req.getParameter("[1-25]{2}");
				if (hob_memid == null || hob_memid.trim().length() == 0) {
					errorMsgs.add("興趣編號: 請勿空白");

				} else if (!hob_code.trim().matches(hob_codeReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("興趣編號必須1~25與2碼數字");

				}

				Member_HobbyVO member_hobbyVO = new Member_HobbyVO();
				member_hobbyVO.setHob_memid(hob_memid);
				member_hobbyVO.setHob_code(hob_code);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member_hobbyVO", member_hobbyVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Match/Member_Hobby/update_Member_Hobby_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				Member_HobbyService Member_HobbySvc = new Member_HobbyService();
				member_hobbyVO = Member_HobbySvc.addMember_Hobby(hob_memid, hob_code);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_hobbyVO", member_hobbyVO);
				String url = "/front-end/Match/Member_Hobby/addMember_Hobby.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Match/Member_Hobby/addMember_Hobby.jsp");
				failureView.forward(req, res);
			}
		}
//		if ("delete".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ***************************************/
//				String mem_hob_uid = new String(req.getParameter("mem_hob_uid"));
//				/*************************** 2.開始刪除資料 ***************************************/
//				Member_HobbyService member_hobbySvc = new Member_HobbyService();
////				member_hobbySvc.deleteMember_Hobby(mem_hob_uid);
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/front-end/Match/Black_List/listAllMember_Hobby.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/Match/Black_List/listAllMember_Hobby.jsp");
//				failureView.forward(req, res);
//			}
//		}	

	}

}
