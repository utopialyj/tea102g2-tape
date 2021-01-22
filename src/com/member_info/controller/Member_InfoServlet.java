package com.member_info.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.acts.model.ActsService;
import com.google.gson.Gson;
import com.member_authroity.model.Member_AuthroityService;
import com.member_authroity.model.Member_AuthroityVO;
import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import randomstring.RandomString;

/**
 * Servlet implementation class Member_InfoServlet
 */
@WebServlet("/Member_InfoServlet")
@MultipartConfig
public class Member_InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Member_InfoServlet() {
		super();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String action = request.getParameter("action");
//		===============會員登入驗證===========================
		if ("loginhandler".equals(action)) {
//			System.out.println("L1");

//			取得輸入的帳號密碼
			String m_email = request.getParameter("m_email").trim();
			System.out.println(m_email);

			String m_paswd = request.getParameter("m_paswd").trim();
			System.out.println(m_paswd);

			Member_InfoService member_infoService = new Member_InfoService();
			// 裝資料用的
			Member_InfoVO member_InfoVO = new Member_InfoVO();
			// 裝資料庫取出的正確帳密
			Member_InfoVO correct = member_infoService.findLogin(m_email);
			// 給錯誤訊息用的
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				// 第一關....先判斷帳號密碼輸入是否為空
				if (m_email.trim() == null || m_email.trim().length() == 0) {
					errorMsgs.put("account", "帳號不可為空,請輸入");
				}
				if (m_paswd.trim() == null || m_paswd.trim().length() == 0) {
					errorMsgs.put("pwd", "密碼不可為空,請輸入");
				}
				if (!errorMsgs.isEmpty()) {
					member_InfoVO.setM_email(m_email);
					request.setAttribute("member_InfoVO", member_InfoVO);
					String url = "/front-end/login.jsp";
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);
					return;
				}
//				System.out.println("L2");

				// 第二關....判斷有沒有這帳號 以及帳號是否有被刪除
				if (correct == null) {
					errorMsgs.put("account_error", "帳號或密碼錯誤,請重新輸入");

				} else if (correct.getVerify_lv() == 4) {// 帳號已刪除的情況，權限為4時是狀態是『刪除』
					errorMsgs.put("account_error", "帳號不存在,請重新輸入");
				}
				if (!errorMsgs.isEmpty()) {
					member_InfoVO.setM_email(m_email);
					request.setAttribute("member_InfoVO", member_InfoVO);
					String url = "/front-end/login.jsp";
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);
					return;
				}
//				System.out.println("L3");

				// 第三關....判斷密碼有無錯誤
				if (!m_paswd.equals(correct.getM_paswd())) {
					errorMsgs.put("account_error", "帳號或密碼錯誤,請重新輸入");

					// 有錯
					member_InfoVO.setM_email(m_email);
					request.setAttribute("member_InfoVO", member_InfoVO);
					String url = "/front-end/login.jsp";
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);
					return;
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("m_email", m_email);
//					System.out.println("L4");
//					   response.sendRedirect(request.getContextPath()+"/loginSuccess.jsp");
					// 裝使用者的會員編號及權限、帳號名稱
					Map<String, String> member_info = new LinkedHashMap<String, String>();
					member_info.put("m_email", correct.getM_email());// 存EMAIL
//						member_info.put("verify_lv", correct.getVerify_lv() + "");// 轉為字串
					member_info.put("user_name", correct.getUser_name() + "");// 轉為字串

//						HttpSession session = request.getSession();
//						session.setAttribute("m_email", member_info);
//						session.setAttribute("verify_lv", member_info);
					session.setAttribute("user_name", member_info);
					session.setAttribute("member_infoVO", correct);

					session.setAttribute("mem_id", correct.getMem_id());

					List<Member_InfoVO> friends = member_infoService.getFriends(correct.getMem_id());
					session.setAttribute("friends", friends);

					/*************************** 使用者報名成功的揪團 for 聊天室 *************/
					ActsService actSvc = new ActsService();
					Map<String, String> actsList = actSvc.getApprovedActs(correct.getMem_id());
					Set<String> actsKeySet = actsList.keySet();
					session.setAttribute("actsList", actsList);
					session.setAttribute("actsKeySet", actsKeySet);
					
					
					
					try {
//						System.out.println("L5");

						String location = (String) session.getAttribute("location");
//						System.out.println(location);

//						System.out.println("登入的location" + location);

						if (location != null) {
//							System.out.println(location);
							session.removeAttribute("location"); // *�u�@2: �ݬݦ��L�ӷ����� (-->�p���ӷ�����:�h���ɦܨӷ�����)
							 //0116_1603
						       if(location.equals(request.getContextPath() + "/front-end/profile/introduction.jsp")) {
						        location = location + "?Member=" + correct.getMem_id();
						       }
							response.sendRedirect(location);
//							System.out.println("L6");

							return;
						}
					} catch (Exception ignored) {
						ignored.printStackTrace();
					}

//			   
//					System.out.println("L7");
					String url = "/front-end/index.jsp";
//					System.out.println(url);
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);

				}
			} catch (Exception e) {
				errorMsgs.put("errorMsgs", "錯誤" + e.getMessage());
			}

		}
		// 登出帳號
		if ("LogOut".equals(action)) {
			HttpSession session = request.getSession();
			String location = request.getParameter("location");
			session.removeAttribute("member_infoVO");
//			System.out.println(location+"地點");
			session.removeAttribute("location");
			session.invalidate();
			

			response.sendRedirect("/TEA102G2/front-end/login.jsp");

		}

//		===============展示一個===========================
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = request.getParameter("mem_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_info/mem_select_page.jsp");
					failureView.forward(request, response);
					return;// 程式中斷
				}

				String mem_id = null;
				try {
					mem_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_info/mem_select_page.jsp");
					failureView.forward(request, response);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Member_InfoService memSvc = new Member_InfoService();
				Member_InfoVO member_infoVO = memSvc.getOneM_Info(mem_id);
				if (member_infoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/member_info/mem_select_page.jsp");
					failureView.forward(request, response);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				request.setAttribute("member_infoVO", member_infoVO); // 資料庫取出的member_authroityVO物件,存入req
				String url = "/back-end/member_info/listOneMem.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(request, response);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/member_info/mem_select_page.jsp");
				failureView.forward(request, response);
			}
		}

//		===============更新一個===========================
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mem_id = new String(request.getParameter("mem_id"));
//				System.out.println(verify_level+"aaaaaaa");
//				System.out.println("a");

				/*************************** 2.開始查詢資料 ****************************************/
				Member_InfoService memSvc = new Member_InfoService();
				Member_InfoVO member_infoVO = memSvc.getOneM_Info(mem_id);
//				System.out.println("b");

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				request.setAttribute("member_infoVO", member_infoVO); // 資料庫取出的member_authroityVO物件,存入req
				String url = "/front-end/member_info/updateMem.jsp";
//				System.out.println("c1");

				RequestDispatcher successView = request.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(request, response);
//				System.out.println("c");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/member_info/listAllMem.jsp");
				failureView.forward(request, response);
			}
		}
//		===============更新全部===========================
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			Member_InfoVO member_infoVO = new Member_InfoVO();

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
//			System.out.println("123");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mem_id = new String(request.getParameter("mem_id").trim());
				System.out.println(mem_id);
				String m_email = request.getParameter("m_email");
				System.out.println(m_email);
				String emailRule = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
				if (m_email == null || m_email.trim().length() == 0) {
					errorMsgs.add("E-mail: 請勿空白");
				} else if (!m_email.trim().matches(emailRule)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("E-mail格式不正確");
				}
//				System.out.println("d");
//				String m_phone = request.getParameter("m_phone").trim();
//				if (m_phone == null || m_phone.trim().length() == 0) {
//					errorMsgs.add("電話號碼請勿空白");
//				}

//				String m_paswd = request.getParameter("m_paswd").trim();
//				System.out.println(m_paswd);
//				if (m_paswd == null || m_paswd.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
//				System.out.println("e");

				String m_phone = request.getParameter("m_phone").trim();

				if (m_phone == null || m_phone.trim().length() == 0) {
					errorMsgs.add("電話號碼請勿空白");
				}
				System.out.print("f");
				String identity_number = request.getParameter("identity_number").trim();
				if (identity_number == null || identity_number.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
//				System.out.print("g");

//				//更新身分證圖片正面
				Part idphoto_f = request.getPart("idphoto_f");
				Member_InfoService member_InfoService = new Member_InfoService();
				InputStream in = idphoto_f.getInputStream();
				byte[] id_photo_f = new byte[in.available()];
				if (in.available() != 0) {
					id_photo_f = new byte[in.available()];
					in.read(id_photo_f);
				} else {
					id_photo_f = member_InfoService.findPic(mem_id).getIdphoto_b();
				}
				in.close();
//				System.out.print("h");

//				//更新身分證圖片反面
				Part idphoto_b = request.getPart("idphoto_b");
				InputStream in2 = idphoto_b.getInputStream();
				byte[] id_photo_b = null;
				if (in2.available() != 0) {
					id_photo_b = new byte[in2.available()];
					in2.read(id_photo_b);
				} else {
					id_photo_b = member_InfoService.findPic(mem_id).getIdphoto_b();
				}
				in2.close();
//				byte[] idphoto_b=null;
//				System.out.print("i");

				// 更新權限
				Integer verify_lv = 3;
//
//				try {
//					verify_lv = new Integer(request.getParameter("verify_lv").trim());
//					System.out.print("a7");
//
//				} catch (NumberFormatException e) {
//					verify_lv =0;
////					errorMsgs.add("權限請填數字.");
//				}
//				System.out.print("a7");

				// 新增使用者名稱
				String user_name = request.getParameter("user_name").trim();
				if (user_name == null || user_name.trim().length() == 0) {
					errorMsgs.add("請輸入使用者名稱");
				}
//				System.out.print("j");

				// 新增性別
				String gender = request.getParameter("gender").trim();
				if (gender == null || gender.trim().length() == 0) {
					errorMsgs.add("請選擇性別");
				}
//				System.out.print("k");

				// 新增生日
				java.sql.Date m_birthday = null;
				try {
					m_birthday = java.sql.Date.valueOf(request.getParameter("m_birthday").trim());
				} catch (IllegalArgumentException e) {
					m_birthday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請選擇生日日期!");
				}
//				System.out.print("L");

				// 新增星座
				String horoscope = request.getParameter("horoscope").trim();
//				if (horoscope == null || horoscope.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
//				System.out.print("M");

				// 新增血型
				String blood_type = request.getParameter("blood_type").trim();
//				if (blood_type == null || blood_type.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
//				System.out.print("n");

				// 新增特長
				String specialty = request.getParameter("specialty").trim();
//				if (specialty == null || specialty.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
//				System.out.print("O");

				// 新增大頭照
				Part headshot = request.getPart("headshot");
				InputStream in3 = headshot.getInputStream();
				byte[] headshot1 = null;
				if (in3.available() != 0) {
					headshot1 = new byte[in3.available()];
					in3.read(headshot1);
				} else {
					headshot1 = member_InfoService.findPic(mem_id).getHeadshot();

				}
				in3.close();
//				byte[] headshot=null;
//				System.out.print("P");

				// 新增學歷
				String school = request.getParameter("school").trim();
//				if (school == null || school.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增公司
				String company = request.getParameter("company").trim();
//				if (company == null || company.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增自我介紹
				String intro = request.getParameter("intro").trim();
//				if (intro == null || intro.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增地區代碼
				String area_code = request.getParameter("area_code").trim();
//				System.out.println("\n" + area_code);
//				if (area_code == null || area_code.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增點數
				Integer points = null;
				try {
					points = new Integer(request.getParameter("points").trim());
				} catch (NumberFormatException e) {
					points = 0;
					errorMsgs.add("薪水請填數字.");
				}
				// 新增配對地區
				String meat_area = request.getParameter("meat_area").trim();
				if (meat_area == null || meat_area.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
				// 新增配對年齡下限
				Integer meat_minage = null;
				try {
					meat_minage = new Integer(request.getParameter("meat_minage").trim());
//					System.out.println(meat_minage.getClass());
//					System.out.println(meat_minage);
				} catch (NumberFormatException e) {
					meat_minage = 0;
					errorMsgs.add("薪水請填數字.");
				}
//				Integer meat_minage = request.getParameter("meat_minage").trim();
//				if (meat_minage == null || meat_minage.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增配對年齡上限
				Integer meat_maxage = null;
				try {
					meat_maxage = new Integer(request.getParameter("meat_maxage").trim());
				} catch (NumberFormatException e) {
					meat_maxage = 0;
					errorMsgs.add("薪水請填數字.");
				}
//				Integer meat_maxage = request.getParameter("meat_maxage").trim();
//				if (meat_maxage == null || meat_maxage.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}

//				Member_InfoVO member_infoVO = new Member_InfoVO();
				member_infoVO.setMem_id(mem_id);
				member_infoVO.setM_email(m_email);
//				member_infoVO.setM_paswd(m_paswd);
				member_infoVO.setM_phone(m_phone);
				member_infoVO.setIdentity_number(identity_number);
				member_infoVO.setIdphoto_f(id_photo_f);
				member_infoVO.setIdphoto_b(id_photo_b);
				member_infoVO.setVerify_lv(verify_lv);
				member_infoVO.setUser_name(user_name);
				member_infoVO.setGender(gender);
				member_infoVO.setM_birthday(m_birthday);
				member_infoVO.setHoroscope(horoscope);
				member_infoVO.setBlood_type(blood_type);
				member_infoVO.setSpecialty(specialty);
				member_infoVO.setHeadshot(headshot1);
				member_infoVO.setSchool(school);
				member_infoVO.setCompany(company);
				member_infoVO.setIntro(intro);
				member_infoVO.setArea_code(area_code);
				member_infoVO.setPoints(points);
				member_infoVO.setMeat_area(meat_area);
				member_infoVO.setMeat_minage(meat_minage);
				member_infoVO.setMeat_maxage(meat_maxage);
//				System.out.print("a3");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					request.setAttribute("member_infoVO", member_infoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = request
							.getRequestDispatcher("/front-end/member_info/updateMem.jsp");
					failureView.forward(request, response);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
//				System.out.println(member_infoVO);

//				System.out.println("before");
				Member_InfoService memSvc = new Member_InfoService();
				memSvc.updateM_Info(mem_id, m_email, m_phone, identity_number, id_photo_f, id_photo_b, verify_lv,
						user_name, gender, m_birthday, horoscope, blood_type, specialty, headshot1, school, company,
						intro, area_code, points, meat_area, meat_minage, meat_maxage);

//				member_infoVO = memSvc.updateM_Info(mem_id, m_email, m_paswd, m_phone, identity_number, id_photo_f, id_photo_b, verify_lv, user_name, gender, m_birthday, horoscope, blood_type, specialty, headshot1, school, company, intro, area_code, points, meat_area, meat_minage, meat_maxage);

//				System.out.println("after");

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				request.setAttribute("member_infoVO", member_infoVO); // 資料庫update成功後,正確的的member_authroityVO物件,存入request
				String url = "/back-end/member_info/listOneMem.jsp";

				RequestDispatcher successView = request.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(request, response);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/member_info/updateMem.jsp");
				failureView.forward(request, response);
			}
		}
//		===============新增===========================
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求
			// 裝錯誤訊息用
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
//			System.out.println("i2");

			// 裝資料
			Member_InfoVO member_infoVO = new Member_InfoVO();
//			System.out.println("i1");
			// 找全部資料
			Member_InfoService memSVC = new Member_InfoService();
			List<Member_InfoVO> member_List = memSVC.getAll();
//			System.out.println("i3");

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String m_email = request.getParameter("m_email");
				String emailRule = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
				if (m_email == null || m_email.trim().length() == 0) {
//					System.out.println("i4");

					errorMsgs.add("E-mail: 請勿空白");
//					System.out.println("i5");

				} else if (!m_email.trim().matches(emailRule)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("E-mail格式不正確");
//					System.out.println("i6");

				} else {
					for (Member_InfoVO list : member_List) {
						if (m_email.trim().equals(list.getM_email())) {
							errorMsgs.add("E-mail已註冊，請重新輸入");
//							System.out.println("i7");

						}
					}
				}

				String m_phone = request.getParameter("m_phone").trim();
				if (m_phone == null || m_phone.trim().length() == 0) {
					errorMsgs.add("電話號碼請勿空白");
				} else {
					for (Member_InfoVO list : member_List) {
						if (m_phone.trim().equals(list.getM_phone())) {
							errorMsgs.add("電話號碼已註冊,請重新輸入");
//							System.out.println("i8");

						}
					}
				}

				// 新增密碼與密碼二次輸入

//				String m_paswd = request.getParameter("m_paswd").trim();
//				if (m_paswd == null || m_paswd.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				String m_paswd = request.getParameter("m_paswd").trim();
				String m_paswd_2 = request.getParameter("m_paswd_2").trim();

				String paswdReg = "^[a-zA-Z0-9]{2,15}$";// 正則表達式

//				System.out.println("密碼長度"+(m_paswd.trim()).length());
				if (m_paswd == null || (m_paswd.trim()).length() == 0) {
//					System.out.println("空密碼");
					errorMsgs.add("密碼不可為空");
				} else if (!m_paswd.trim().matches(paswdReg)) {
					System.out.println("密碼格式錯");
					errorMsgs.add("密碼格式錯誤");
					System.out.println("i9");

				} else if (m_paswd_2 == null || (m_paswd_2.trim()).length() == 0) {
//					System.out.println("二次密碼為空");

					errorMsgs.add("二次密碼不可為空");
				} else if (!(m_paswd_2.trim()).equals(m_paswd)) {
					errorMsgs.add("二次密碼輸入不相同");
//					System.out.println("二次密碼輸入不相同");

				}
				// 新增身分證字號
				String identity_number = request.getParameter("identity_number").trim();
				if (identity_number == null || identity_number.trim().length() == 0) {
//					System.out.println(identity_number);

//					System.out.println("身分證字號請勿空白");

					errorMsgs.add("身分證字號請勿空白");
//					msgj.put("ID", "success");
//					out.write(gson.toJson(msgj));
				} else {
					for (Member_InfoVO list : member_List) {
						if (identity_number.trim().equals(list.getIdentity_number())) {
//							System.out.println("身分證字號已註冊,請重新輸入");

							errorMsgs.add("身分證字號已註冊,請重新輸入");
//							System.out.println("i12");

						}
					}
				}

//				//新增身分證圖片正面
////				System.out.println("圖片讀取前");
////				Part idphoto_f = request.getPart("idphoto_f");
////				InputStream in = idphoto_f.getInputStream();
////				byte[] id_photo_f = new byte[in.available()];
//				
//
//				in.read(id_photo_f);
//				System.out.println("圖片讀取後");
//
//				in.close();

//				//新增身分證圖片反面
//				System.out.println("圖片2讀取前");
//				Part idphoto_b = request.getPart("idphoto_b");
//				InputStream in2 = idphoto_b.getInputStream();
//				byte[] id_photo_b = new byte[in2.available()];
//				in2.read(id_photo_b);
//				
//
//				in2.close();
//				System.out.println("圖片2讀取後");

//				byte[] idphoto_b=null;

				// 新增權限
//				Integer verify_lv = 1;
//
//				try {
//					verify_lv = new Integer(request.getParameter("verify_lv").trim());
//					System.out.print("a7");
//
//				} catch (NumberFormatException e) {
//					verify_lv =0;
////					errorMsgs.add("權限請填數字.");
//				}
//				System.out.print("a7");

				// 新增使用者名稱
				String user_name = request.getParameter("user_name").trim();
				if (user_name == null || user_name.trim().length() == 0) {
//					System.out.println("請輸入使用者名稱");
					errorMsgs.add("請輸入使用者名稱");
				}
				// 新增性別
				String gender = request.getParameter("gender").trim();
				if (gender == null || gender.trim().length() == 0) {
//					System.out.println("請選擇性別");

					errorMsgs.add("請選擇性別");
				}
				// 新增生日
				java.sql.Date m_birthday = null;
				try {
					m_birthday = java.sql.Date.valueOf(request.getParameter("m_birthday").trim());
				} catch (IllegalArgumentException e) {
//					System.out.println("請選擇生日日期!");

					m_birthday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請選擇生日日期!");
				}
				// 新增星座
				String horoscope = request.getParameter("horoscope").trim();
//				System.out.println(	"星座"+horoscope);

//				if (horoscope == null || horoscope.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增血型
				String blood_type = request.getParameter("blood_type").trim();
//				System.out.println("血型"+blood_type);

//				if (blood_type == null || blood_type.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增特長
				String specialty = request.getParameter("specialty").trim();
//				System.out.println("特長"+specialty);

//				if (specialty == null || specialty.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增大頭照
//				System.out.println("圖片3讀取前");

				Part headshot = request.getPart("headshot");
//				System.out.println("part長度："+headshot.getSize());

				InputStream in3 = headshot.getInputStream();
				byte[] headshot1 = new byte[in3.available()];
				in3.read(headshot1);
				in3.close();
//				byte[] headshot=null;
//				System.out.println("圖片3讀取後");

				// 新增學歷
				String school = request.getParameter("school").trim();
//				if (school == null || school.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增公司
				String company = request.getParameter("company").trim();
//				if (company == null || company.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增自我介紹
				String intro = request.getParameter("intro").trim();
//				if (intro == null || intro.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增地區代碼
				String area_code = request.getParameter("area_code").trim();
//				if (area_code == null || area_code.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增點數
//				Integer points = null;
//				try {
//					points = new Integer(request.getParameter("points").trim());
//				} catch (NumberFormatException e) {
//					points = 0;
//					errorMsgs.add("薪水請填數字.");
//				}
				// 新增配對地區
				String meat_area = request.getParameter("meat_area").trim();
//				if (meat_area == null || meat_area.trim().length() == 0) {
//					errorMsgs.add("配對地區請勿空白");
//				}
				// 新增配對年齡下限
				Integer meat_minage = null;
				try {
					meat_minage = new Integer(request.getParameter("meat_minage").trim());
				} catch (NumberFormatException e) {
					meat_minage = 0;
					errorMsgs.add("配對年齡下限請填數字.");
				}
//				Integer meat_minage = request.getParameter("meat_minage").trim();
//				if (meat_minage == null || meat_minage.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
				// 新增配對年齡上限
				Integer meat_maxage = null;
				try {
					meat_maxage = new Integer(request.getParameter("meat_maxage").trim());
				} catch (NumberFormatException e) {
					meat_maxage = 0;
					errorMsgs.add("配對年齡上限請填數字.");
				}
//				Integer meat_maxage = request.getParameter("meat_maxage").trim();
//				if (meat_maxage == null || meat_maxage.trim().length() == 0) {
//					errorMsgs.add("密碼請勿空白");
//				}
//				

//				Member_InfoVO member_infoVO = new Member_InfoVO();
				member_infoVO.setM_email(m_email);
				member_infoVO.setM_paswd(m_paswd);
				member_infoVO.setM_phone(m_phone);
				member_infoVO.setIdentity_number(identity_number);
//				member_infoVO.setIdphoto_f(id_photo_f);
//				member_infoVO.setIdphoto_b(id_photo_b);
//				member_infoVO.setVerify_lv(verify_lv);
				member_infoVO.setUser_name(user_name);
				member_infoVO.setGender(gender);
				member_infoVO.setM_birthday(m_birthday);
				member_infoVO.setHoroscope(horoscope);
				member_infoVO.setBlood_type(blood_type);
				member_infoVO.setSpecialty(specialty);
				member_infoVO.setHeadshot(headshot1);
				member_infoVO.setSchool(school);
				member_infoVO.setCompany(company);
				member_infoVO.setIntro(intro);
				member_infoVO.setArea_code(area_code);
//				member_infoVO.setPoints(points);
				member_infoVO.setMeat_area(meat_area);
				member_infoVO.setMeat_minage(meat_minage);
				member_infoVO.setMeat_maxage(meat_maxage);
//				System.out.print("a3");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					request.setAttribute("member_infoVO", member_infoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = request
							.getRequestDispatcher("/front-end/member_info/addMember.jsp");
					failureView.forward(request, response);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				Member_InfoService memSvc = new Member_InfoService();
//				System.out.println("a2");
//				System.out.println(memSvc);

				member_infoVO = memSvc.addM_Info(m_email, m_paswd, m_phone, identity_number, user_name, gender,
						m_birthday, horoscope, blood_type, specialty, headshot1, school, company, intro, area_code,
						meat_area, meat_minage, meat_maxage);
//	            雞哥用來更新權限的方法
//				compy_TableService.updateFromBack(cp_contact_email);

//				System.out.println("a1");
				String link = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getRequestURI() + "?action=verify_form_email_confirm" + "&email=" + m_email;
//				System.out.println(link);
//問雞哥這串導向哪
//						request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI()
//				+"?method=verify_form_email_confirm"
//				+"&email="+m_email;

				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("親愛的" + user_name + "<br>");
				stringBuffer.append("您好,謝謝您的使用" + "<br>");
				stringBuffer.append("經驗證您的資料無誤" + "<br>");
				stringBuffer.append("請您點選以下連結,以便啟動您的帳號" + "<br>");
				stringBuffer.append("**點選後會自動將您導向登入頁面**" + "<br>");
				stringBuffer.append("<a href=\"" + link + "\">請您點擊我<a>" + "<br>");
				stringBuffer.append("Tape在此謝謝您的愛戴<br>");

				String msgs = stringBuffer.toString();

				new SendVerifyEmail().sendEmail(m_email, msgs, user_name);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/member_info/insertSuccess.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(request, response);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/member_info/addMember.jsp");
				failureView.forward(request, response);
			}
		}
		// 新增-信箱驗證是否重複============================================================================================
				// =======================================================================================================================
				if ("email_check".equals(action)) {
//							System.out.println("p1");

					PrintWriter out = response.getWriter();
					Gson gson = new Gson();

//							System.out.println(map);
//							System.out.println("pt-1");

//							System.out.println(mem_id);

//							String m_paswd = request.getParameter("pwd_original");
//							System.out.println(m_paswd);

//							System.out.println(member_infoVO);
					// 找全部資料
					Member_InfoService memSVC = new Member_InfoService();
					List<Member_InfoVO> member_List = memSVC.getAll();
					List<String> emailList = new ArrayList<String>();
					for (Member_InfoVO one : member_List) {
						emailList.add(one.getM_email());
					}

					String m_email = request.getParameter("InputEmail1").trim();
					System.out.println(m_email);
					// 裝給AJAX的資料
					Map<String, String> msgs_mail = new HashMap<String, String>();
					if (emailList.contains(m_email)) {
						msgs_mail.put("fina", "error");
						out.write(gson.toJson(msgs_mail));

					} else if (m_email.length() == 0) {
						msgs_mail.put("fina", "empty");
						out.write(gson.toJson(msgs_mail));

					} else {
						msgs_mail.put("fina", "success");
						out.write(gson.toJson(msgs_mail));

					}
				}
				
				// 新增-手機號碼驗證============================================================================================
				// =======================================================================================================================
				if ("phone_check".equals(action)) {
//							System.out.println("p1");

					PrintWriter out = response.getWriter();
					Gson gson = new Gson();

//							System.out.println(map);
//							System.out.println("pt-1");

//							System.out.println(mem_id);

//							String m_paswd = request.getParameter("pwd_original");
//							System.out.println(m_paswd);

//							System.out.println(member_infoVO);
					// 找全部資料
					Member_InfoService memSVC = new Member_InfoService();
					List<Member_InfoVO> member_List = memSVC.getAll();
					List<String> phoneList = new ArrayList<String>();
					for (Member_InfoVO one : member_List) {
						phoneList.add(one.getM_phone());
					}

					String m_phone = request.getParameter("InputPhone").trim();
					System.out.println(m_phone);
					// 裝給AJAX的資料
					Map<String, String> msgs_phone = new HashMap<String, String>();
					if (phoneList.contains(m_phone)) {
						msgs_phone.put("fina", "error");
						out.write(gson.toJson(msgs_phone));

					} else if (m_phone.length() == 0) {

					} else {
						msgs_phone.put("fina", "success");
						out.write(gson.toJson(msgs_phone));

					}
				}
		// 新增-身分證驗證============================================================================================
		// =======================================================================================================================
		if ("id_check".equals(action)) {
//					System.out.println("p1");

			PrintWriter out = response.getWriter();
			Gson gson = new Gson();

//					System.out.println(map);
//					System.out.println("pt-1");

//					System.out.println(mem_id);

//					String m_paswd = request.getParameter("pwd_original");
//					System.out.println(m_paswd);

//					System.out.println(member_infoVO);
			// 找全部資料
			Member_InfoService memSVC = new Member_InfoService();
			List<Member_InfoVO> member_List = memSVC.getAll();
			List<String> idList = new ArrayList<String>();
			for (Member_InfoVO one : member_List) {
				idList.add(one.getIdentity_number());
			}

			String identity_number = request.getParameter("InputID").trim();
			System.out.println(identity_number);
			// 裝給AJAX的資料
			Map<String, String> msgs_id = new HashMap<String, String>();
			if (idList.contains(identity_number)) {
				msgs_id.put("fina", "error");
				out.write(gson.toJson(msgs_id));

			} else if (identity_number.length() == 0) {

			} else {
				msgs_id.put("fina", "success");
				out.write(gson.toJson(msgs_id));

			}
		}

//會員EMAIL點選驗證後========================================================================================================
//=================================================================================================================================
		if ("verify_form_email_confirm".equals(action)) {
			String m_email = request.getParameter("email");
//			System.out.println(m_email);

			Member_InfoService memSvc = new Member_InfoService();
			memSvc.updateFromEMAILBack(m_email);
//			System.out.println("m1");

			HttpSession session = request.getSession();
			session.invalidate();

			response.sendRedirect(request.getContextPath() + "/front-end/index.jsp");
//			System.out.println("m2");

		}

		// 密碼更改-密碼驗證============================================================================================
		// =======================================================================================================================
		if ("pwd_update".equals(action)) {
			System.out.println("p1");

			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			HttpSession session = request.getSession();
//			Map<String, String> map = (Map) session.getAttribute("user_name");
//			System.out.println(map);
//			System.out.println("pt-1");
            String m_email = (String)session.getAttribute("m_email"); 
			System.out.println(m_email);


			String m_paswd = request.getParameter("pwd_original");
//			System.out.println(m_paswd);

			Member_InfoService member_infoService = new Member_InfoService();
			Member_InfoVO member_infoVO = member_infoService.findPaswd(m_email);
//			System.out.println(member_infoVO);

			Map<String, String> msgs = new HashMap<String, String>();
			if (!member_infoVO.getM_paswd().equals(m_paswd)) {
				msgs.put("fina", "error");
				out.write(gson.toJson(msgs));
//				System.out.println("p2");

			} else {
				msgs.put("fina", "success");
				out.write(gson.toJson(msgs));
//				System.out.println("p3");

			}

		}
		// 密碼更改-密碼驗證完更改============================================================================================
		// =======================================================================================================================
		if ("pwd_update_comfirm".equals(action)) {
//			System.out.println("p1");

			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			Gson gson = new Gson();
			Map<String, String> map = (Map) session.getAttribute("user_name");
//			System.out.println(map);

			String m_email = (String) session.getAttribute("m_email");
//			System.out.println(m_email);

			String m_paswd = request.getParameter("pwd_update");
//			System.out.println(m_paswd);

			Member_InfoService member_infoService = new Member_InfoService();
			member_infoService.updatePaswd(m_paswd, m_email);
			Map<String, String> msgs = new HashMap<String, String>();
			msgs.put("fina", "success");
			out.write(gson.toJson(msgs));

			session.invalidate();
//			System.out.println("p4");

		}
		// 忘記密碼-信箱驗證是否存在============================================================================================
		// =======================================================================================================================
		if ("email_check2".equals(action)) {
//					System.out.println("p1");
			
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();

//					System.out.println(map);
//					System.out.println("pt-1");

//					System.out.println(mem_id);

//					String m_paswd = request.getParameter("pwd_original");
//					System.out.println(m_paswd);

//					System.out.println(member_infoVO);
			// 找全部資料
			Member_InfoService memSVC = new Member_InfoService();
			List<Member_InfoVO> member_List = memSVC.getAll();
			List<String> emailList = new ArrayList<String>();
			for (Member_InfoVO one : member_List) {
				emailList.add(one.getM_email());
			}
		

			String m_email = request.getParameter("InputEmail2").trim();
			//System.out.println(m_email);
			// 裝給AJAX的資料
			Map<String, String> msgs_mail = new HashMap<String, String>();
			if (!emailList.contains(m_email)) {
				msgs_mail.put("fina", "error");
				out.write(gson.toJson(msgs_mail));
			} else if (m_email.length() == 0) {
				msgs_mail.put("fina", "empty");
				out.write(gson.toJson(msgs_mail));
			} else {
				HttpSession session = request.getSession(); 
				session.setAttribute("m_email",m_email);
				
				String user_name = memSVC.findLogin(m_email).getUser_name();
				msgs_mail.put("fina", "success");
				out.write(gson.toJson(msgs_mail));
				//生成亂數驗證碼
				String r_number=RandomString.generatePassword(8);
				//System.out.println(r_number);

				//去資料庫把亂碼改成密碼
				memSVC.updatePaswd(r_number,m_email);
				//生成信件內容
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("親愛的" + user_name + "<br>");
				stringBuffer.append("您好,謝謝您的使用" + "<br>");
				stringBuffer.append("經驗證您的資料無誤" + "<br>");
				stringBuffer.append("以下為您的臨時驗證碼" + "<br>");
				stringBuffer.append("**驗證碼："+r_number+"**" + "<br>");
				stringBuffer.append("登入後請記得修改密碼<br>");

				String msgs = stringBuffer.toString();

				new SendVerifyEmail().sendEmail(m_email, msgs, user_name);
				
			}
		}
//====================顯示圖片在頁面=========================
		if ("getPic".equals(action)) {
			String mem_id = request.getParameter("mem_id");

			String which_one = request.getParameter("which_one");
			Member_InfoService member_infoService = new Member_InfoService();
			Member_InfoVO member_infoVO = member_infoService.findPic(mem_id);
			OutputStream out = response.getOutputStream();
			switch (which_one) {
			case "idphoto_f":
				response.setContentLength(member_infoVO.getIdphoto_f().length);
				out.write(member_infoVO.getIdphoto_f());
				break;
			case "idphoto_b":
				response.setContentLength(member_infoVO.getIdphoto_b().length);
				out.write(member_infoVO.getIdphoto_b());
				break;
			case "headshot":
				byte[] pic = null;
				if (member_infoVO != null && member_infoVO.getHeadshot() != null) {
					pic = member_infoVO.getHeadshot();
					response.setContentLength(pic.length);
					out.write(pic);
				} else {
					InputStream in = getServletContext().getResourceAsStream("/front-end/acts/images/kali.jpg");
					response.setContentLength(in.available());
					pic = new byte[in.available()];
					in.read(pic);
					out.write(pic);
				}

				break;
			default:
				out.close();				
			}
		}

	}

	// 照片資料處理==========================================================================================================
	// =======================================================================================================================
	private byte[] memPicToBytes(Part part) {
		byte[] pic = null;
		try (InputStream in = part.getInputStream()) {
			pic = new byte[in.available()];
			in.read(pic);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pic;
	}
}
