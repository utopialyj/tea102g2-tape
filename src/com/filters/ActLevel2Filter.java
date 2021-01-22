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
public class ActLevel2Filter implements Filter {

	private FilterConfig config;


   
	public void init(FilterConfig config) {
		this.config = config;
	}
	
	 
	public void destroy() {
		config = null;
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
System.out.println("進入Level2_Filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		session.setAttribute("location", req.getRequestURI());
		
		Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");
		
		System.out.println("----    從Session取\"活動編號\"          --------");
		String actid = (String) session.getAttribute("actid");
		System.out.println(actid == null ? "從Session裡沒取到活動編號\n": "從Session有取到活動便號\n");
		
		System.out.println("----    從Session取\"action\"          --------");
		String action = (String) session.getAttribute("action");
		System.out.println(action == null ? "從Session裡沒取到action\n": "從Session有取到action\n");
		
		
		if(request.getParameter("actid") != null) {
			System.out.println("++++++從Form表單取到\"活動編號\"+++++++ ");
			 actid = request.getParameter("actid");//只有從Form表單傳過來，才取得到
		}
		
		if(request.getParameter("action") != null) {
			System.out.println("++++++從Form表單取到\"action\"+++++++ ");
			 action = request.getParameter("action");//只有從Form表單傳過來，才取得到
		}
		System.out.println("存Session之前的 actid?" + actid);
		session.setAttribute("actid", actid);
		System.out.println("存後的 acontid?" + actid);
		
		System.out.println("存Session之前的 action?" + action);
		session.setAttribute("action", action);
		System.out.println("存後的 action?" + action);
	
		if (member_infoVO == null) {

System.out.println("沒有登入");
//確定從哪裡跳轉，登入完帶使用者回到那裏	
//System.out.println("RequestURI = "+ req.getRequestURI());
System.out.println("等級不夠，帶去驗證");
String url = req.getContextPath();
url += "/front-end/login.jsp";
res.sendRedirect(url);

			return;
		} else if(member_infoVO.getVerify_lv() < 2){
				System.out.println("等級不夠，帶去驗證");
				String url = req.getContextPath();
				url += "/front-end/actapl/lessthan2.jsp";
				res.sendRedirect(url);
			return;
		}
		else {
System.out.println("有登入，等級高，帶去報名活動");
System.out.println("RequestURI = "+ req.getRequestURI());
			chain.doFilter(request, response);
		}
	}
	

}