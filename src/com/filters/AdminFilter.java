package com.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member_info.model.Member_InfoVO;


//@WebFilter("/LoginFilter")
public class AdminFilter implements Filter {

	private FilterConfig config;


   
	public void init(FilterConfig config) {
		this.config = config;
	}
	
	 
	public void destroy() {
		config = null;
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// �i���o session�j
		HttpSession session = req.getSession();
		// �i�q session �P�_��user�O�_�n�J�L�j
		Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");
		if (member_infoVO.getVerify_lv()==9) {
			chain.doFilter(request, response);
		} else {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/login.jsp");
			return;
		}
	}
	

}
