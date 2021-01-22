package com.notification.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestNotify")
public class TestNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestNotify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String announce = request.getParameter("announce");
		NotifyWS notify = new NotifyWS();
//		notify.sendNotify(1, "MEM0000002", mem_id, "", "", System.currentTimeMillis(),"no");
//		notify.sendNotify(2, "MEM0000002", mem_id, "", "", System.currentTimeMillis(),"no");
//		notify.sendNotify(3, "MEM0000002", mem_id, "", "", System.currentTimeMillis(),"no");
//		notify.sendNotify(4, "MEM0000002", mem_id, "", "", System.currentTimeMillis(),"no");
//		notify.sendNotify(5, "MEM0000002", mem_id, "", "", System.currentTimeMillis(),"no");
		notify.sendNotify(8, "", announce, "", "", System.currentTimeMillis(),"no");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
