package com.post.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.catalina.tribes.membership.Membership;

import com.google.gson.Gson;
import com.like_post.model.Like_PostService;
import com.like_post.model.Like_PostVO;
import com.member_info.model.Member_InfoService;
import com.member_info.model.Member_InfoVO;
import com.notification.controller.NotifyWS;
import com.post.model.*;
import com.post_comment.model.Post_CommentService;
import com.post_comment.model.Post_CommentVO;
import com.post_photo.model.Post_PhotoService;
import com.post_photo.model.Post_PhotoVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 來自select_page.jsp的請求
		// 顯示一筆資料
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("post_uid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入動態編號");

				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String post_uid = null;
				try {
					post_uid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("動態編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/select_page.jsp");

					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始查詢資料 *****************************************/
				PostService postSvc = new PostService();
				PostVO postVO = postSvc.getOnePost(post_uid);
				if (postVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/select_page.jsp");

					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("postVO", postVO);
				String url = "/back-end/post/listOnePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/select_page.jsp");

				failureView.forward(req, res);
			}
		}

		// 來自listAllPost.jsp的請求
		// 選擇要更新的資料，並進入更新頁面
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String post_uid = new String(req.getParameter("post_uid"));

				/*************************** 2.開始查詢資料 ****************************************/
				PostService postSvc = new PostService();
				PostVO postVO = postSvc.getOnePost(post_uid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("postVO", postVO);
				String url = "/back-end/post/update_post_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/listAllPost.jsp");
				failureView.forward(req, res);
			}
		}

		// 來自update_post_input.jsp的請求
		// 更新一筆資料
		if ("update".equals(action)) {
			// 確認更新哪一筆資料
			String post_uid = new String(req.getParameter("post_uid").trim());
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PostService postService = new PostService();
			PostVO postVO = postService.getOnePost(post_uid);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String post_memid = new String(req.getParameter("post_memid").trim());

				Integer post_sts = new Integer(req.getParameter("post_sts").trim());

				Integer post_public_lv = new Integer(req.getParameter("post_public_lv").trim());

				java.sql.Timestamp edit_date = java.sql.Timestamp.valueOf(req.getParameter("edit_date").trim());

				String post_context = req.getParameter("post_context").trim();
				if (post_context == null || post_context.trim().length() == 0) {
					post_context = postVO.getPost_context();
					errorMsgs.add("動態內容請勿空白");
				}

				String post_location = null;
				post_location = new String(req.getParameter("post_location").trim());

				Integer lk_num = new Integer(req.getParameter("lk_num").trim());

				postVO.setPost_uid(post_uid);
				postVO.setPost_memid(post_memid);
				postVO.setPost_sts(post_sts);
				postVO.setPost_public_lv(post_public_lv);
				postVO.setEdit_date(edit_date);
				postVO.setPost_context(post_context);
				postVO.setPost_location(post_location);
				postVO.setLk_num(lk_num);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("postVO", postVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/update_post_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				postVO = postService.updatePost(post_uid, post_memid, post_sts, post_public_lv, edit_date, post_context,
						post_location, lk_num);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				req.setAttribute("postVO", postVO);
				String url = "/back-end/post/listOnePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {

				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/update_post_input.jsp");
				failureView.forward(req, res);
			}
		}
//1/13
		// 刪除一筆資料
		if ("delete".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String post_uid = new String(req.getParameter("post_uid"));
				/*************************** 2.開始刪除資料 ***************************************/

				PostService postSvc = new PostService();
				postSvc.deletePost(post_uid);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/

				String url = "/back-end/post/listAllPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/listAllPost.jsp");
				failureView.forward(req, res);
			}

		}
		
		
		
		
		// 來自index.jsp的請求
		// 從動態牆新增回覆
		if ("addComment".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String cmnt_memid = (String) session.getAttribute("mem_id");

				Timestamp cmnt_date = new java.sql.Timestamp(System.currentTimeMillis());

				String cmnt_context = req.getParameter("cmnt_context");

				Integer lk_count = 0;

				String post_code = req.getParameter("post_code");

				Integer cmnt_sts = 1;

				Post_CommentVO pcVO = new Post_CommentVO();
				
				
				PostService postSvc = new PostService();
				PostVO postVO = postSvc.getOnePost(post_code);
				String postMaster = postVO.getPost_memid();

				pcVO.setCmnt_memid(cmnt_memid);
				pcVO.setCmnt_date(cmnt_date);
				pcVO.setCmnt_context(cmnt_context);
				pcVO.setLk_count(lk_count);
				pcVO.setPost_code(post_code);
				pcVO.setCmnt_sts(cmnt_sts);

				/*************************** 2.開始新增資料 ***************************************/

				Post_CommentService post_CommentSvc = new Post_CommentService();
				
				String url_web = req.getContextPath()+"/front-end/profile/get_one.jsp?post_code="+post_code;

				post_CommentSvc.addPostCom(cmnt_memid, cmnt_date, cmnt_context, lk_count, post_code, cmnt_sts);
				System.out.println("-------------發送通知前--------------");
//				if(cmnt_memid!=session.getAttribute("mem_id")) {
				NotifyWS notify = new NotifyWS();
				notify.sendNotify(3,cmnt_memid, postMaster, url_web, "", System.currentTimeMillis(),"no");
//				}
				System.out.println("-------------發送通知後--------------");

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
		
//1/13修改
				String url = req.getContextPath();
				url += "/front-end/index.jsp";
				res.sendRedirect(url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		

		// ==========================================================================================================================


		
		
		// 來自index.jsp的請求
		// 從動態牆新增貼文愛心
				if ("update_post_like".equals(action)) {
				
					Like_PostService likePostSvc = new Like_PostService();
					
					
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
									
						String lk_memid = (String)session.getAttribute("mem_id");
				
						String post_code = req.getParameter("post_code");
				
						Timestamp lk_date = new java.sql.Timestamp(System.currentTimeMillis());
						
						PostService postSvc = new PostService();
						String postMaster = (postSvc.getMemberByPostCode(post_code)).getMem_id();
						
						List<String> member = likePostSvc.getLikeMembersByPostcode(post_code);
						if(member.contains(lk_memid)) {
							likePostSvc.deleteLikePost(post_code, lk_memid);
						}else {
							likePostSvc.addLikePost(lk_memid, post_code, lk_date);
							NotifyWS notify = new NotifyWS();
							notify.sendNotify(4,lk_memid, postMaster, "", "", System.currentTimeMillis(),"no");
						}
						
						
						
						Like_PostService seLikePoSvc = new Like_PostService();
						int likeSum = seLikePoSvc.getLikeNum(post_code);			
			
						PrintWriter out = res.getWriter();
						Gson gson = new Gson();

						Map<String, String> msgs = new HashMap<String, String>();
						
						msgs.put("likeSum", likeSum+"");
						out.write(gson.toJson(msgs));
						
						

					}
				
				
				

				

				// 來自index.jsp的請求
				// 從動態牆新增一筆資料
				if ("insertfromWall".equals(action)) {
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
						String post_memid = ((Member_InfoVO) session.getAttribute("member_infoVO")).getMem_id();

						Integer post_sts = 1;

						Integer post_public_lv = 1;

						java.sql.Timestamp edit_date = new java.sql.Timestamp(System.currentTimeMillis());

						String post_context = req.getParameter("post_context").trim();
						if (post_context == null || post_context.trim().length() == 0) {
							errorMsgs.add("動態內容請勿空白");
						}

						String post_location = null;

						Integer lk_num = 0;

						PostVO postVO = new PostVO();

						postVO.setPost_memid(post_memid);
						postVO.setPost_sts(post_sts);
						postVO.setPost_public_lv(post_public_lv);
						postVO.setEdit_date(edit_date);
						postVO.setPost_context(post_context);
						postVO.setPost_location(post_location);
						postVO.setLk_num(lk_num);

						// 新增多張動態照片
						byte[] picture = null;
						List<Post_PhotoVO> post_PhotoVOs = new ArrayList<Post_PhotoVO>();

						InputStream in = null;
						try {
							List<Part> formElements = (List<Part>) req.getParts();

							for (Part part : formElements) {
		
								in = part.getInputStream();
								if (part.getName().equals("photo")) {
			
									picture = new byte[in.available()];
									in.read(picture);

									Integer photo_sts = 1;

									Post_PhotoVO post_PhotoVO = new Post_PhotoVO();

									post_PhotoVO.setPhoto(picture);
									post_PhotoVO.setPhoto_sts(photo_sts);
									post_PhotoVOs.add(post_PhotoVO);
								}

							}
						} catch (Exception e) {
							e.printStackTrace();

						} finally {
							in.close();

						}

						if (!errorMsgs.isEmpty()) {
							req.setAttribute("postVO", postVO);
							RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
							failureView.forward(req, res);

							return;
						}
						/*************************** 2.開始新增資料 ***************************************/
						PostService postSvc = new PostService();
						postSvc.addPost(postVO, post_PhotoVOs);

						/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//1/13修改						
						String url = req.getContextPath();
						url += "/front-end/index.jsp";
						
						res.sendRedirect(url);

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
						failureView.forward(req, res);
					}
				}
				
				
				// 來自post的listAllPost.jsp的請求
				// 從動態牆選擇要更新的資料，並進入更新頁面
				if ("getOne_For_Update_From_PostWall".equals(action)) {

					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ****************************************/
						String post_uid = new String(req.getParameter("post_uid"));

						/*************************** 2.開始查詢資料 ****************************************/
						PostService postSvc = new PostService();
						Post_PhotoService post_PhotoSvc = new Post_PhotoService();
						PostVO postVO = postSvc.getOnePost(post_uid);
						List<Post_PhotoVO> post_PhotoVO = post_PhotoSvc.findByPostCode(post_uid);

						/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
						req.setAttribute("postVO", postVO);
						req.setAttribute("post_PhotoVO", post_PhotoVO);
						String url = "/front-end/post_wall/update_post.jsp?postVO="+postVO+"&post_PhotoVO="+post_PhotoVO+"";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/listAllPost.jsp");
						failureView.forward(req, res);
					}
				}

				// 來自動態牆update_post.jsp的請求
				// 更新一筆資料
				if ("update_post".equals(action)) {
					// 確認更新哪一筆資料
					String post_uid = new String(req.getParameter("post_uid").trim());
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					PostService postService = new PostService();
					PostVO postVO = postService.getOnePost(post_uid);

					try {
						/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

						String post_memid = postVO.getPost_memid();
			
						Integer post_sts =1;
						
						Integer post_public_lv =1;
					
						java.sql.Timestamp edit_date = postVO.getEdit_date();
					
						String post_context = req.getParameter("post_context").trim();
						if (post_context == null || post_context.trim().length() == 0) {
							post_context = postVO.getPost_context();
						}
					
						String post_location = null;
									

						Integer lk_num = postVO.getLk_num();
					
						postVO.setPost_uid(post_uid);
						postVO.setPost_memid(post_memid);
						postVO.setPost_sts(post_sts);
						postVO.setPost_public_lv(post_public_lv);
						postVO.setEdit_date(edit_date);
						postVO.setPost_context(post_context);
						postVO.setPost_location(post_location);
						postVO.setLk_num(lk_num);

						// 新增多張動態照片
						byte[] picture = null;
						List<byte[]> photos = new ArrayList<byte[]>();

						InputStream in = null;
						try {
							List<Part> formElements = (List<Part>) req.getParts();

							for (Part part : formElements) {
		
								in = part.getInputStream();

								if (part.getName().equals("photo") && part.getSize() != 0) {

									picture = new byte[in.available()];
									in.read(picture);

									photos.add(picture);

								}

							}
						} catch (Exception e) {
							e.printStackTrace();

						} finally {
							in.close();

						}

						if (!errorMsgs.isEmpty()) {
							req.setAttribute("postVO", postVO);
							RequestDispatcher failureView = req
									.getRequestDispatcher("/front-end/profile/update_post.jsp");
							failureView.forward(req, res);
							return;
			
						}
						/*************************** 2.開始修改資料 *****************************************/

						postVO = postService.updatePost(post_uid, post_memid, post_sts, post_public_lv, edit_date, post_context,
								post_location, lk_num);

						Post_PhotoService post_PhotoSvc = new Post_PhotoService();
						post_PhotoSvc.addPostPhoto(photos, post_uid, 1);

						/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//1/13修改
					
						req.setAttribute("postVO", postVO);
						String url = req.getContextPath();
						url += "/front-end/index.jsp";
						
						res.sendRedirect(url);
				
						/*************************** 其他可能的錯誤處理 *************************************/

					} catch (Exception e) {


						errorMsgs.add("修改資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/update_post_input.jsp");
						failureView.forward(req, res);
					}
				}

				// 個人頁面，來自update_post.jsp的請求
				// 刪除所選的照片
				if ("photo_delete_Post".contentEquals(action)) {
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
						String photo_uid = new String(req.getParameter("photo_uid"));
						/*************************** 2.開始刪除資料 ***************************************/

						Post_PhotoService post_PhotoSvc = new Post_PhotoService();
						post_PhotoSvc.deletePostPhoto(photo_uid);

						/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				// 要回到同一頁所以需要下列參數完成轉交
						/***************************
						 * 3-1.接收請求參數
						 ****************************************/
						Post_PhotoVO post_PhotoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);

						String post_uid = new String(post_PhotoVO.getPost_code());

						/***************************
						 * 3-2.開始查詢資料
						 ****************************************/
						PostService postSvc = new PostService();
						PostVO postVO = postSvc.getOnePost(post_uid);

						/*************************** 3-3.查詢完成,準備轉交(Send the Success view) ************/
						req.setAttribute("postVO", postVO);
						String url = "/front-end/post_wall/update_post.jsp";

						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);

						/*************************** 其他可能的錯誤處理 **********************************/

					} catch (Exception e) {
						errorMsgs.add("刪除資料失敗" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/listAllPost.jsp");
						failureView.forward(req, res);
					}

				}		
				
				
				
				
				
				
				
				
//		=====================================================================================================
		// 個人頁面ACTION
		
		
		// 來自addPost.jsp的請求
		// 從個人頁面新增一筆資料//test
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String post_memid = ((Member_InfoVO) session.getAttribute("member_infoVO")).getMem_id();

				Integer post_sts = 1;

				Integer post_public_lv = 1;

				java.sql.Timestamp edit_date = new java.sql.Timestamp(System.currentTimeMillis());

				String post_context = req.getParameter("post_context").trim();
				if (post_context == null || post_context.trim().length() == 0) {
					errorMsgs.add("動態內容請勿空白");
				}

				String post_location = null;

				Integer lk_num = 0;

				PostVO postVO = new PostVO();

				postVO.setPost_memid(post_memid);
				postVO.setPost_sts(post_sts);
				postVO.setPost_public_lv(post_public_lv);
				postVO.setEdit_date(edit_date);
				postVO.setPost_context(post_context);
				postVO.setPost_location(post_location);
				postVO.setLk_num(lk_num);

				// 新增多張動態照片
				byte[] picture = null;
				List<Post_PhotoVO> post_PhotoVOs = new ArrayList<Post_PhotoVO>();

				InputStream in = null;
				try {
					List<Part> formElements = (List<Part>) req.getParts();

					for (Part part : formElements) {

						in = part.getInputStream();
						if (part.getName().equals("photo")) {
	
							picture = new byte[in.available()];
							in.read(picture);

							Integer photo_sts = 1;

							Post_PhotoVO post_PhotoVO = new Post_PhotoVO();

							post_PhotoVO.setPhoto(picture);
							post_PhotoVO.setPhoto_sts(photo_sts);
							post_PhotoVOs.add(post_PhotoVO);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					in.close();

				}


				if (!errorMsgs.isEmpty()) {
					req.setAttribute("postVO", postVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
					failureView.forward(req, res);

					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				PostService postSvc = new PostService();
				postSvc.addPost(postVO, post_PhotoVOs);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//1/13修改				
				String url = req.getContextPath();
				url += "/front-end/profile/listAllPost.jsp?Member="+post_memid;
				
				res.sendRedirect(url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
				failureView.forward(req, res);
			}
		}

		// 來自profile的listAllPost.jsp的請求
		// 從個人頁面選擇要更新的動態，並進入更新頁面
		if ("getOne_For_Update_From_Profile".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String post_uid = new String(req.getParameter("post_uid"));

				/*************************** 2.開始查詢資料 ****************************************/
				PostService postSvc = new PostService();
				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				PostVO postVO = postSvc.getOnePost(post_uid);
				List<Post_PhotoVO> post_PhotoVO = post_PhotoSvc.findByPostCode(post_uid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("postVO", postVO);
				req.setAttribute("post_PhotoVO", post_PhotoVO);
				String url = "/front-end/profile/update_profile_post.jsp?postVO="+postVO+"&post_PhotoVO="+post_PhotoVO+"";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/listAllPost.jsp");
				failureView.forward(req, res);
			}
		}

		// 來自個人頁面update_profile_post.jsp的請求
		// 更新一筆資料
		if ("update_profile_post".equals(action)) {
			// 確認更新哪一筆資料
			String post_uid = new String(req.getParameter("post_uid").trim());
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PostService postService = new PostService();
			PostVO postVO = postService.getOnePost(post_uid);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String post_memid = postVO.getPost_memid();

				Integer post_sts = 1;

				Integer post_public_lv = 1;

				java.sql.Timestamp edit_date = postVO.getEdit_date();

				String post_context = req.getParameter("post_context").trim();
				if (post_context == null || post_context.trim().length() == 0) {
					post_context = postVO.getPost_context();
				}

				String post_location = null;

				Integer lk_num = postVO.getLk_num();

				postVO.setPost_uid(post_uid);
				postVO.setPost_memid(post_memid);
				postVO.setPost_sts(post_sts);
				postVO.setPost_public_lv(post_public_lv);
				postVO.setEdit_date(edit_date);
				postVO.setPost_context(post_context);
				postVO.setPost_location(post_location);
				postVO.setLk_num(lk_num);

				// 新增多張動態照片
				byte[] picture = null;
				List<byte[]> photos = new ArrayList<byte[]>();

				InputStream in = null;
				try {
					List<Part> formElements = (List<Part>) req.getParts();

					for (Part part : formElements) {
					
						in = part.getInputStream();

						if (part.getName().equals("photo") && part.getSize() != 0) {

							picture = new byte[in.available()];
							in.read(picture);

							photos.add(picture);

						}

					}
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					in.close();

				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("postVO", postVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/profile/update_post.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				postVO = postService.updatePost(post_uid, post_memid, post_sts, post_public_lv, edit_date, post_context,
						post_location, lk_num);

				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				post_PhotoSvc.addPostPhoto(photos, post_uid, 1);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//1/13修改
				req.setAttribute("postVO", postVO);
		
				String url = req.getContextPath();
				url += "/front-end/profile/listAllPost.jsp?Member="+post_memid;
				

				res.sendRedirect(url);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {

				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/update_post_input.jsp");
				failureView.forward(req, res);
			}
		}

		// 個人頁面，來自update_profile_post.jsp的請求
		// 刪除所選的照片
		if ("photo_delete".contentEquals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String photo_uid = new String(req.getParameter("photo_uid"));
				/*************************** 2.開始刪除資料 ***************************************/

				Post_PhotoService post_PhotoSvc = new Post_PhotoService();
				post_PhotoSvc.deletePostPhoto(photo_uid);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				// 要回到同一頁所以需要下列參數完成轉交
				/***************************
				 * 3-1.接收請求參數
				 ****************************************/
				Post_PhotoVO post_PhotoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);

				String post_uid = new String(post_PhotoVO.getPost_code());

				/***************************
				 * 3-2.開始查詢資料
				 ****************************************/
				PostService postSvc = new PostService();
				PostVO postVO = postSvc.getOnePost(post_uid);

				/*************************** 3-3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("postVO", postVO);
				String url = "/front-end/profile/update_profile_post.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/listAllPost.jsp");
				failureView.forward(req, res);
			}

		}

		// 來自listAllPost.jsp的請求
		// 從個人頁面新增回覆
		if ("addCommentFromProfile".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
							
				String cmnt_memid = (String)session.getAttribute("mem_id");

				Timestamp cmnt_date = new java.sql.Timestamp(System.currentTimeMillis());

				String cmnt_context = req.getParameter("cmnt_context");

				Integer lk_count = 0;
				
				String post_code = req.getParameter("post_code");

				Integer cmnt_sts = 1;
				
				Post_CommentVO  pcVO= new Post_CommentVO();
				
				pcVO.setCmnt_memid(cmnt_memid);
				pcVO.setCmnt_date(cmnt_date);
				pcVO.setCmnt_context(cmnt_context);
				pcVO.setLk_count(lk_count);
				pcVO.setPost_code(post_code);
				pcVO.setCmnt_sts(cmnt_sts);
				
				PostService postSvc = new PostService();
				String postMaster = (postSvc.getMemberByPostCode(post_code)).getMem_id();

				/*************************** 2.開始新增資料 ***************************************/
		
				Post_CommentService post_CommentSvc = new Post_CommentService();
				
				String url_web = req.getContextPath()+"/front-end/profile/get_one.jsp?post_code="+post_code;
				
				post_CommentSvc.addPostCom(cmnt_memid, cmnt_date, cmnt_context, lk_count, post_code, cmnt_sts);	
				if(cmnt_memid != session.getAttribute("mem_id")) {
				NotifyWS notify = new NotifyWS();
				notify.sendNotify(3, cmnt_memid, postMaster, url_web, "", System.currentTimeMillis(),"no");
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				
//1/13修改				
				String url = req.getContextPath();
				url += "/front-end/profile/listAllPost.jsp?Member="+cmnt_memid;

				res.sendRedirect(url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/profile/addNewPost.jsp");
				failureView.forward(req, res);
			}
		}				
		
		
		// 來自個人頁面update_introduction.jsp的請求
		// 更新一筆個人資料
				if ("update_memberinfo".equals(action)) {
					// 確認更新哪一筆資料
					String member_uid = (String)session.getAttribute("mem_id");
				
					Member_InfoService memberSvc = new Member_InfoService();
					Member_InfoVO memberVO = memberSvc.getOneM_Info(member_uid);

					try {
						/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
						
						String user_name = new String(req.getParameter("user_name").trim());
						if (user_name == null || user_name.trim().length() == 0) {
							user_name = memberVO.getUser_name();
						}
					
						String gender = new String(req.getParameter("gender").trim());
						if (gender == null || gender.trim().length() == 0) {
							gender = memberVO.getGender();
						}


						String horoscope = new String(req.getParameter("horoscope").trim());
						if (horoscope == null || horoscope.trim().length() == 0) {
							horoscope = memberVO.getHoroscope();
						}
				
						String blood_type = new String(req.getParameter("blood_type"));
						if (blood_type == null || blood_type.trim().length() == 0) {
							blood_type = memberVO.getBlood_type();
						}
	
						String specialty = new String(req.getParameter("specialty").trim());
						if (specialty == null || specialty.trim().length() == 0) {
							specialty = memberVO.getSpecialty();
						}
		
						String school = new String(req.getParameter("school").trim());
						if (school == null || school.trim().length() == 0) {
							school = memberVO.getSchool();
						}
				
						String company = new String(req.getParameter("company").trim());
						if (company == null || company.trim().length() == 0) {
							company = memberVO.getCompany();
						}
				
						String intro = new String(req.getParameter("intro").trim());
						if (intro == null || intro.trim().length() == 0) {
							intro = memberVO.getIntro();
						}
					
						Member_InfoVO member_infoVO = new Member_InfoVO();
						member_infoVO.setMem_id(member_uid);

						member_infoVO.setUser_name(user_name);
						member_infoVO.setGender(gender);
						member_infoVO.setHoroscope(horoscope);
						member_infoVO.setBlood_type(blood_type);
						member_infoVO.setSpecialty(specialty);
						member_infoVO.setSchool(school);
						member_infoVO.setCompany(company);
						member_infoVO.setIntro(intro);
						
						/*************************** 2.開始修改資料 *****************************************/

						memberSvc.update_profile(member_infoVO);

						/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//1/13修改
						req.setAttribute("Member", member_uid);										
						String url = req.getContextPath();
						url += "/front-end/profile/introduction.jsp?Member="+member_uid;
						

						res.sendRedirect(url);
						/*************************** 其他可能的錯誤處理 *************************************/

					} catch (Exception e) {

						
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/post/update_post_input.jsp");
						failureView.forward(req, res);
					}
				}

		


	//處理大頭照	
		if("display_pic".equals(action)) {
	
			Member_InfoService memberSvc = new Member_InfoService();
			String member_id = req.getParameter("Member");
			OutputStream op = res.getOutputStream();

			
			byte[] pic = null;
			if(memberSvc.getOneM_Info(member_id).getHeadshot() != null)		
				pic =memberSvc.getOneM_Info(member_id).getHeadshot();
			else {
				InputStream in = getServletContext().getResourceAsStream("/front-end/acts/images/person1.jpg");
				pic = new byte[in.available()]; 
				res.setContentLengthLong(pic.length);
				in.read(pic);
				in.close();
			}
			op.write(pic);
			op.close();
		}
		
		
		// 處理動態照片
		if ("display_postPhoto".equals(action)) {

			Post_PhotoService post_PhotoSvc = new Post_PhotoService();
			String photo_uid = req.getParameter("photo_uid");
			
			OutputStream op = res.getOutputStream();
			Post_PhotoVO photoVO = post_PhotoSvc.getOnePostPhoto(photo_uid);
			byte[] pic = null;

	
			if (photoVO.getPhoto() != null) {
					pic = photoVO.getPhoto();
					
			} else {
				InputStream in = getServletContext().getResourceAsStream("/front-end/acts/images/kali2.jpg");
				pic = new byte[in.available()];
				res.setContentLengthLong(pic.length);
				System.out.println("no");
				in.read(pic);
				in.close();
			}


			op.write(pic);
			op.close();
		}
		
		
		
		
		if("update_bigpic".equals(action)) {
			
			Part part = req.getPart("gs_big_pic");
		
			String member_id = (String)session.getAttribute("mem_id");
		
			byte[] gs_big_pic = guestPicToBytes(part);
			
		
			Member_InfoService memberSvc = new Member_InfoService();
			memberSvc.update_headshot(member_id, gs_big_pic);	
			
		}
		
		
	}
		private byte[] guestPicToBytes(Part part) {
			byte[] pic = null;

			try(InputStream in = part.getInputStream()){
				pic = new byte[in.available()];
				in.read(pic);
		
			}catch(IOException e) {
				
				System.out.println(e.getMessage());
			}
			return pic;
		}
		

}
