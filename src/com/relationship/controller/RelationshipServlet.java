package com.relationship.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.relationship.model.RelationshipService;
import com.relationship.model.RelationshipVO;
import com.member_hobby.model.Member_HobbyVO;
import com.member_info.model.*;
import com.notification.controller.NotifyWS;

@WebServlet("/RelationshipServlet")
public class RelationshipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("notice".equals(action)) {
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
			RelationshipService relationshipSvc = new RelationshipService();
			List<RelationshipVO> list = relationshipSvc.notice(member_infoVO.getMem_id());
			session.setAttribute("list", list);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Relationship/notice.jsp");
			successView.forward(req, res);
			return;
		}
		
		//發出好友邀請
		if ("add".equals(action)) { // 來自addEmp.jsp的請求

			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
			String frdinv_memid = member_infoVO.getMem_id();

			String frdbeinv_memid = req.getParameter("memberid");

			Integer frdinv_sts = 0;

			RelationshipVO relationshipVO = new RelationshipVO();
			relationshipVO.setFrdinv_memid(frdinv_memid);
			relationshipVO.setFrdbeinv_memid(frdbeinv_memid);
			relationshipVO.setFrdinv_sts(frdinv_sts);

			RelationshipService relationshipSvc = new RelationshipService();
			relationshipVO = relationshipSvc.addRelationship(frdinv_memid, frdbeinv_memid, frdinv_sts);
			
			NotifyWS notify = new NotifyWS();
			String url =  req.getContextPath() + "/front-end/Relationship/relationship.do?action=notice";
			notify.sendNotify(1,frdinv_memid, frdbeinv_memid, url, "", System.currentTimeMillis(),"no");

			session.setAttribute("frdrela_uid", relationshipVO.getFrdrela_uid());

			req.setAttribute("relationshipVO", relationshipVO);

			return;
		}

		//接受好友
		if ("accept".equals(action)) {
			if ("accept".equals(action)) {
				
				HttpSession session = req.getSession();
				Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
				
				String frdinv_memid = req.getParameter("frdinv_memid");
				String frdbeinv_memid = member_infoVO.getMem_id();
				Integer frdinv_sts = 1;


				RelationshipService relationshipSvc = new RelationshipService();

				relationshipSvc.updateSts(frdinv_memid, frdbeinv_memid);
				relationshipSvc.addRelationship(frdbeinv_memid, frdinv_memid, frdinv_sts);
				
				NotifyWS notify = new NotifyWS();
				String url =  req.getContextPath() + "/front-end/Relationship/relationship.do?action=listfriend";
				
				notify.sendNotify(2,frdbeinv_memid, frdinv_memid, url, "", System.currentTimeMillis(),"no");
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/Relationship/relationship.do?action=notice");
				
				successView.forward(req, res);
				return;
			}
		}
		//列出所有好友
//		meminfojdbc
		if ("listfriend".equals(action)) {

			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

			Member_InfoService memSvc = new Member_InfoService();
			List<Member_InfoVO> list = memSvc.getFriends(member_infoVO.getMem_id());
			String memUid = member_infoVO.getMem_id();
			req.setAttribute("memUid", memUid);
			session.setAttribute("list", list);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Relationship/listFriends.jsp");
			successView.forward(req, res);
			return;
		}
//		刪除好友
		if ("delete".equals(action)) {
			System.out.println("進入delete");
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

			RelationshipService relationshipSvc = new RelationshipService();

			String memid = req.getParameter("memid");
			System.out.println(memid);
			
			String url = "/front-end/Relationship/listFriends.jsp";
			relationshipSvc.del(member_infoVO.getMem_id(), memid);
			relationshipSvc.del(memid, member_infoVO.getMem_id());
			System.out.println("執行完資料庫");
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
//		刪除好友邀請通知
		if ("deleteNotice".equals(action)) {
			HttpSession session = req.getSession();
			Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

			RelationshipService relationshipSvc = new RelationshipService();

			String memid = req.getParameter("frdinv_memid");
			
			String url = "/front-end/Relationship/relationship.do?action=notice";
			relationshipSvc.del(member_infoVO.getMem_id(), memid);
			relationshipSvc.del(memid, member_infoVO.getMem_id());

			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
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
				String str = req.getParameter("frdrela_uid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸交友編號");

				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Relationship/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String frdrela_uid = null;
				try {
					frdrela_uid = new String(str);

				} catch (Exception e) {
					errorMsgs.add("格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Relationship/select_page.jsp");
					failureView.forward(req, res);

					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/

				RelationshipService relationshipSvc = new RelationshipService();
				RelationshipVO relationshipVO = relationshipSvc.getOneRelationship(frdrela_uid);
				if (relationshipVO == null) {
					errorMsgs.add("查無資料");

				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Relationship/select_page.jsp");
					failureView.forward(req, res);

					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/

				req.setAttribute("relationshipVO", relationshipVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/Relationship/listOneRelationship.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Relationship/select_page.jsp");
				failureView.forward(req, res);

			}
		}
//		來自listAllBlack_List.jsp的請求
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/

				String frdrela_uid = new String(req.getParameter("frdrela_uid"));
				/*************************** 2.開始查詢資料 ****************************************/

				RelationshipService relationshipSvc = new RelationshipService();
				RelationshipVO relationshipVO = relationshipSvc.getOneRelationship(frdrela_uid);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/

				req.setAttribute("relationshipVO", relationshipVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/Relationship/update_Relationship_input.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Relationship/listAllRelationship.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String frdrela_uid = new String(req.getParameter("frdrela_uid").trim());
				String frdinv_memid = req.getParameter("frdinv_memid");

				String frdinv_memidReg = "[M][E][M][0-9]{7}";
				if (frdinv_memid == null || frdinv_memid.trim().length() == 0) {
					errorMsgs.add("發出邀請會員: 請勿空白");

				} else if (!frdinv_memid.trim().matches(frdinv_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("邀請會員編號必須開頭為大寫MEM與7碼數字");

				}

				String frdbeinv_memid = req.getParameter("frdbeinv_memid").trim();
				String frdbeinv_memidReg = "[M][E][M][0-9]{7}";
				if (frdbeinv_memid == null || frdbeinv_memid.trim().length() == 0) {
					errorMsgs.add("受邀會員: 請勿空白");

				} else if (!frdbeinv_memid.trim().matches(frdbeinv_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("受邀會員編號必須開頭為大寫MEM與7碼數字");

				}
				Integer frdinv_sts = new Integer(req.getParameter("frdinv_sts").trim());
				if (frdinv_sts == null) {
					errorMsgs.add("狀態請勿空白");
				}

				RelationshipVO relationshipVO = new RelationshipVO();
				relationshipVO.setFrdrela_uid(frdrela_uid);
				relationshipVO.setFrdinv_memid(frdinv_memid);
				relationshipVO.setFrdbeinv_memid(frdbeinv_memid);
				relationshipVO.setFrdinv_sts(frdinv_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("relationshipVO", relationshipVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Relationship/update_Relationship_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				RelationshipService RelationshipSvc = new RelationshipService();
				relationshipVO = RelationshipSvc.updateRelationship(frdrela_uid, frdinv_memid, frdbeinv_memid,
						frdinv_sts);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("relationshipVO", relationshipVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/Relationship/listOneRelationship.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Relationship/update_Relationship_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String frdinv_memid = req.getParameter("frdinv_memid").trim();
				String frdinv_memidReg = "[M][E][M][0-9]{7}";
				if (frdinv_memid == null || frdinv_memid.trim().length() == 0) {
					errorMsgs.add("邀請會員: 請勿空白");

				} else if (!frdinv_memid.trim().matches(frdinv_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("邀請會員編號必須開頭為大寫MEM與7碼數字");
				}

				String frdbeinv_memid = req.getParameter("frdbeinv_memid").trim();
				String frdbeinv_memidReg = "[M][E][M][0-9]{7}";
				if (frdbeinv_memid == null || frdbeinv_memid.trim().length() == 0) {
					errorMsgs.add("受邀會員: 請勿空白");
				} else if (!frdbeinv_memid.trim().matches(frdbeinv_memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("受邀會員編號必須開頭為大寫MEM與7碼數字");
				}

				Integer frdinv_sts = new Integer(req.getParameter("frdinv_sts").trim());

				RelationshipVO relationshipVO = new RelationshipVO();

				relationshipVO.setFrdinv_memid(frdinv_memid);
				relationshipVO.setFrdbeinv_memid(frdbeinv_memid);
				relationshipVO.setFrdinv_sts(frdinv_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("relationshipVO", relationshipVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/Relationship/addRelationship.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				RelationshipService relationshipSvc = new RelationshipService();
				relationshipVO = relationshipSvc.addRelationship(frdinv_memid, frdbeinv_memid, frdinv_sts);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/Relationship/listAllRelationship.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Relationship/addRelationship.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ***************************************/
//				String frdrela_uid = new String(req.getParameter("frdrela_uid"));
//
//				/*************************** 2.開始刪除資料 ***************************************/
//				RelationshipService relationshipSvc = new RelationshipService();
//				relationshipSvc.deleteRelationship(frdrela_uid);
//
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/front-end/Relationship/listAllRelationship.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/Relationship/listAllRelationship.jsp");
//				failureView.forward(req, res);
//			}
//		}

	}

}
