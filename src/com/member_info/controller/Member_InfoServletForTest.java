package com.member_info.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member_info.model.*;

@WebServlet("/Member_InfoServletForTest")
public class Member_InfoServletForTest extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		res.setContentType("text/html;charset=UTF-8");
		
		try {

			/*************************** 1.get mem_id **********************/

			String mem_id = req.getParameter("mem_id");

			/*************************** 2.select from DB *****************************************/
			Member_InfoService memSvc = new Member_InfoService();
			Member_InfoVO member_infoVO = memSvc.getOneM_Info(mem_id);
			List<Member_InfoVO> friends = memSvc.getFriends(mem_id);

			/*************************** 3.Send the Success view *************/
			session.setAttribute("member_infoVO", member_infoVO);
			session.setAttribute("friends", friends);
			
			String url = "/front-end/TestIndex.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);

			/*************************** exception *************************************/
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/TestLogin.jsp");
			failureView.forward(req, res);
		}
	}

}
