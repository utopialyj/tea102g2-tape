package com.acts.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acts.model.ActsService;
import com.acts.model.ActsVO;

import imageutil.ImageUtil;

@WebServlet("/front-end/acts/Acts_Search_Servlet")
public class Acts_Front_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 String path = this.getServletContext().getRealPath("/front-end/images/person1.jpg");
// System.out.println(path);
//	     FileInputStream fis = new FileInputStream(path);
//	     byte[] pic = new byte[fis.available()];
//	     fis.read(pic);
//	     pic = ImageUtil.shrink(pic, 80);
//	     
//	     ServletOutputStream outputStream = response.getOutputStream();
//	     String fileName = path.substring(path.lastIndexOf("\\")+1);
//System.out.println(fileName);  
//		fileName = URLEncoder.encode(fileName,"UTF-8");
//	  response.setHeader("content-disposition", "attachment;filename="+fileName);
//        response.setHeader("content-type", "image/jpeg");
//        
//        outputStream.write(pic);
//        outputStream.close();
//        fis.close();
		//System.out.println("doGet執行");
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//System.out.println("------------------------------------");
//System.out.println("這裡是Acts_Search_Servlet");
//System.out.println("action = " + action);
//System.out.println("------------------------------------");

		if("search".equals(action)) {
			
			//這種錯誤儲存方式取出數量不受限制
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			//存取前端參數
			String type = request.getParameter("type");
			String areaCode = request.getParameter("area");
			String start_time = request.getParameter("time1");
			String end_time = request.getParameter("time2");
			
//System.out.println("1.type =" + type + " 2.areaCode = " + areaCode + 
//		" 3.start_time = " + start_time + " 4.end_time = " + end_time);
			
			
	
			/**********************開啟服務準備搜尋*******************/
			ActsService actSvc = new ActsService();
			List<ActsVO> resultList = null;
			/***********由參數的值，決定用哪一個搜尋方法****************/
			if("請選擇".equals(type)) {//不限定類型
				switch(areaCode) {
				case "請選擇"://不限定區域
					resultList = actSvc.noLimOnTypeArea(start_time, end_time);
					System.out.println("noLimOnTypeArea");
					System.out.println("------------------------");
					break;
				default:
					resultList = actSvc.noLimOnType(start_time,end_time,areaCode);
					System.out.println("noLimOnType");
					System.out.println("------------------------");
					break;
				}	
			}else {
				switch(areaCode) {
				case "請選擇"://不限定區域
					resultList = actSvc.noLimOnArea(start_time, end_time, type);
					System.out.println("noLimOnArea");
					System.out.println("------------------------");
					break;
				default:
					resultList = actSvc.allLimSearch(start_time,end_time,areaCode,type);
					System.out.println("allLimSearch");
					System.out.println("------------------------");
					break;
				}
			}
			
		
			
			/*********************進入查詢***********************************/
//System.out.println("資料有幾筆 = " + resultList.size());
			request.getSession().setAttribute("actList", resultList);
			String url = "act_search_result.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
		}//if-search-end
		
		
		
		/** 不管是活動照片 還是 主辦者照片 **/
		/** 都會用到 活動編號  **/
		String actid = request.getParameter("actid");
		
		/**********************顯示活動圖片*************************/
		String getPic = request.getParameter("getPic");
		
		
		
		if("getPicture".equals(getPic)) {
//					System.out.println("getPic = " + getPic);
//					System.out.println("actid = " + actid);
//					System.out.println("動作 = " + getPic);
//					System.out.println("******************************");
			OutputStream out = response.getOutputStream();
			ActsService actsSvc = new ActsService();
			byte[] pic = actsSvc.getOneAct(actid).getPic();
			if(pic != null) {
				//System.out.println("縮圖前pic"+ pic.length);
				pic = ImageUtil.shrink(pic, 400);
				response.setContentLength(pic.length);//設定長度讓大圖正常顯示
				//System.out.println("縮圖後pic"+ pic.length);
				out.write(pic);
			}else {
//System.out.println("活動沒有圖片");
					InputStream in = getServletContext().getResourceAsStream("/front-end/acts/images/kali.jpg");
					pic = new byte[in.available()];
					in.read(pic);
//System.out.println("縮圖前pic"+ pic.length);
					pic = ImageUtil.shrink(pic, 400);
//System.out.println("縮圖後pic"+ pic.length);
					out.write(pic);
				
			}
			
		}//if-end : 活動圖片處理區塊結束
		
		
		
		
		/**********************顯示主辦者圖片*************************/
		String headShot = request.getParameter("headShot");
		if("getHeadShot".equals(headShot)) {
				//System.out.println("getHeadShot ? " + headShot);
			
			OutputStream out = response.getOutputStream();
			ActsService actsSvc = new ActsService();
			
			//由活動編號給出 actVO 再轉而得到 主辦者的大頭貼
			byte[] pic = actsSvc.getHeadShot_FromAct(actsSvc.getOneAct(actid));
			if(pic != null) {
				response.setContentLength(pic.length);//設定長度讓大圖正常顯示
//				pic = ImageUtil.shrink(pic, 200);
				out.write(pic);
			}else {
//System.out.println("大頭貼沒有圖片");
				InputStream in = getServletContext().getResourceAsStream("/front-end/acts/images/kali.jpg");
				pic = new byte[in.available()];
//System.out.println("縮圖前pic"+ pic.length);
				in.read(pic);
				pic = ImageUtil.shrink(pic, 200);
//System.out.println("縮圖後pic"+ pic.length);
				in.close();
				
				out.write(pic);
			
			}
			out.close();
		}
		
		
	}//doPost-end :
	

}
