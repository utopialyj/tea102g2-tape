package com.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member_info.model.Member_InfoVO;


//@WebFilter("/LoginFilter")
public class PostLoginFilter implements Filter {

	private FilterConfig config;


   
	public void init(FilterConfig config) {
		this.config = config;
	}
	
	 
	public void destroy() {
		config = null;
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("POST_Filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		session.setAttribute("location", req.getRequestURI());
		
		Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");
		if (member_infoVO == null) {
			
			
			String url = req.getContextPath();
			url += "/front-end/login.jsp";
			res.sendRedirect(url);
			return;
			
		}
		else {
			chain.doFilter(request, response);
		}
	}
	

}