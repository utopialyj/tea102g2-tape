package com.member_info.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.actapl.model.ActAplService;
import com.acts.model.ActsService;
import com.member_info.model.*;

@WebServlet("/MemberChat")
public class Member_InfoServletForChat extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		res.setContentType("text/html;charset=UTF-8");
		
		try {

			/*************************** 1.get mem_id**********************/

			String mem_id = req.getParameter("mem_id");
			String chatType = req.getParameter("chatType");
			
			if ("friend".equals(chatType)) {
				/*************************** 2.select from DB*****************************************/
				Member_InfoService memSvc = new Member_InfoService();
				Member_InfoVO member_infoVO = memSvc.getOneM_Info(mem_id);
				List<Member_InfoVO> friends = memSvc.getFriends(mem_id);
				
				/*************************** 3.Send the Success view *************/
				session.setAttribute("member_infoVO", member_infoVO);
				session.setAttribute("friends", friends);
				
				String url = "/front-end/chat/chat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
			}
			
			if ("activity".equals(chatType)) {
				/*************************** 2.select from DB*****************************************/
				Member_InfoService memSvc = new Member_InfoService();
//				List<Member_InfoVO> allMemVO = memSvc.getAll();
//				Map<String, String> allMemSet = new Hashtable<String, String>();
//				for (Member_InfoVO aMember : allMemVO) {
//					allMemSet.put(aMember.getMem_id(), aMember.getUser_name());
//				}
				
				Member_InfoVO member_infoVO = memSvc.getOneM_Info(mem_id);
				
				ActsService actSvc = new ActsService();
				Map<String, String> actsList = actSvc.getApprovedActs(mem_id);
				Set<String> actsKeySet = actsList.keySet();
				
				/*************************** 3.Send the Success view *************/
				session.setAttribute("member_infoVO", member_infoVO);
				session.setAttribute("actsList", actsList);
				session.setAttribute("actsKeySet", actsKeySet);
				
				String url = "/front-end/chat/chatActs.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
			}

			/*************************** exception *************************************/
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/chat/login.jsp");
			failureView.forward(req, res);
		}
	}

}
