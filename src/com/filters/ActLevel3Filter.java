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
public class ActLevel3Filter implements Filter {

	private FilterConfig config;


   
	public void init(FilterConfig config) {
		this.config = config;
	}
	
	 
	public void destroy() {
		config = null;
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("進入Level3_Filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		session.setAttribute("location", req.getRequestURI());
		
		Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");
		if (member_infoVO == null) {
			
//確定從哪裡跳轉，登入完帶使用者回到那裏	
System.out.println("RequestURI = "+ req.getRequestURI());

			//隱藏物件的值
			String  action = request.getParameter("action");
			
//隱藏物件的值，由request帶來Filter，再由Filter帶去登入，最後再回到報名表單
System.out.println("action ?" + action );

				String url = req.getContextPath();
				url += "/front-end/login.jsp";
				res.sendRedirect(url);
			
			return;
		} else if(member_infoVO.getVerify_lv() < 3){
			String url = "/front-end/acts/lessthan2.jsp";
			RequestDispatcher lessThan2View = req.getRequestDispatcher(url);
			lessThan2View.forward(req, res);
			return;
		}
		else {
			chain.doFilter(request, response);
		}
	}
	

}