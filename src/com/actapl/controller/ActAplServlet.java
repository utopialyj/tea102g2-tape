package com.actapl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.actapl.model.*;
import com.actrnk.model.ActRnkService;
import com.actrnk.model.ActRnkVO;
import com.acts.model.ActsService;
import com.acts.model.ActsVO;
import com.member_info.model.*;
import com.notification.controller.NotifyWS;
import com.google.gson.Gson;

//update部分出問題 ，更新時找不到主鍵的Exception浮不出水面
/* 以下SQL在資料庫有找不到主鍵的問題 但在前端無法顯示Exception 直接404*/
//UPDATE ACTAPL SET  MEMID = 'MEM00000015', ACTID = 'ACT0000043', RSON = 'No Reason', STS ='同意'  WHERE APLUID = 'APL0000001';

@WebServlet("/front-end/actapl/ActAplController")
public class ActAplServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		if (action == null)
			action = (String) session.getAttribute("action");
		Member_InfoVO memVO = (Member_InfoVO) session.getAttribute("member_infoVO");// insert 、 repeat_check 會用到

		System.out.println("------------------------------------");
		System.out.println("轉到ActAplController");
		System.out.println("這裡是Controller的開頭");
		System.out.println("action  = " + action);
		System.out.println("req.getServletPaht() = " + req.getServletPath());
		System.out.println("req.getContextPath() = " + req.getContextPath());
		System.out.println("------------------------------------");

		// =======================主辦者審核=========================================//
		if ("update_holder".equals(action)) {
			String apluid = req.getParameter("apluid");
			// System.out.println("Ajax 傳來的 apluid 是 " + apluid);//取到AJAX傳來的報名編號
			String sts = req.getParameter("sts");
			// System.out.println("Ajax 傳來的 sts 是" + sts);//前台傳來想要修改的報名狀態

			ActAplService aaSvc = new ActAplService();
			ActAplVO aavo = aaSvc.findByPK(apluid);// 拿到報名清單

			Member_InfoService mis = new Member_InfoService();
			Member_InfoVO join_memVO = mis.getOneM_Info(aavo.getMemid());//

			// Member_InfoVO test = mis.getOneM_Info(memVO.getMem_id());//和資料庫同步扣點

			ActsService as = new ActsService();
			ActsVO avo = as.getOneAct(aavo.getActid());

			boolean update_ok = aaSvc.update_sts(aavo, sts);
			System.out.println("報名狀態修改完成 ? " + update_ok);

			PrintWriter out = res.getWriter();
			Gson gson = new Gson();
			try {
				Map<String, String> msgs = new HashMap<String, String>();
				if ("同意".equals(sts)) {
					// System.out.println("AplController 扣點一次");
					// 現階段扣除使用者參與點數時機在此
					aaSvc.drop_apl_point(join_memVO, aavo.getActid());
					// System.out.println("join_memVO 剩下點數 ?" + (join_memVO.getPoints() -
					// avo.getPts() ));//得手動調整成 同步顯示剩餘點數

					// 雖然使用方法參數代入錯誤，但為什麼不會重複扣點?
					// memVO 存在 session 裡面，不是從資料庫撈出的，所以會出現第一次扣點成功之後就再也無法扣點
					// aaSvc.drop_apl_point(test, avo.getActid());
					// aaSvc.drop_apl_point(memVO, avo.getActid());
					// System.out.println("Gamma 剩下點數 ?" + (test.getPoints()-10));

					msgs.put("okay", "yes_to_in");
					out.write(gson.toJson(msgs));
					// ============通知用==================
					NotifyWS notify = new NotifyWS();
					
					String urlNotify5 = req.getContextPath() + "/front-end/acts/manage_join_event.jsp";
					// ActsVO avo的會員編號是＿主辦者, ActAplVO aavo 的會員編號是＿報名者編號
					notify.sendNotify(5, avo.getMemid(), aavo.getMemid(), urlNotify5, "", System.currentTimeMillis(), "no");

					// ============通知用==================
				} else {
					msgs.put("not_okay", "not_to_in");
					out.write(gson.toJson(msgs));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		///////////////////////////////////////////////////////////////

		if ("repeat_check".equals(action)) {
			String actid = req.getParameter("actid");
			System.out.println("repeat_check 取道的actid是 " + actid);
			System.out.println(req.getQueryString());
			PrintWriter out = res.getWriter();
			Gson gson = new Gson();

			ActAplService actAplSvc = new ActAplService();
			Map<String, String> msgs = new HashMap<String, String>();
			if (actAplSvc.isRepeat_APL(memVO.getMem_id(), actid)) {
				msgs.put("nope", "repeat_apl");
				out.write(gson.toJson(msgs));
			} else {
				msgs.put("yes", "ok_apl");
				out.write(gson.toJson(msgs));
			}

		}

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String apluid = req.getParameter("apluid");
				if (apluid == null || (apluid.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ActAplService actAplSvc = new ActAplService();
				ActAplVO actAplVO = actAplSvc.getOneActApl(apluid);
				if (actAplVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				System.out.println("準備轉交");
				req.setAttribute("actAplVO", actAplVO); // 資料庫取出的ActAplVO物件,存入req
				String url = "listOneActApl.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneActRnk.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/select_page.jsp");
				failureView.forward(req, res);
			}
		} // if getOne_For_Display -END

		if ("getOne_For_Update".equals(action)) { // 來自listAllActsApl.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String apluid = req.getParameter("apluid");

				/*************************** 2.開始查詢資料 ****************************************/
				ActAplService actAplSvc = new ActAplService();

				ActAplVO actAplVO = actAplSvc.getOneActApl(apluid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/

				req.setAttribute("actAplVO", actAplVO); // 資料庫取出的ActAplVO物件,存入req
				String url = "/back_end/actapl/update_actapl_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_actapl_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/listAllActsApl.jsp");
				failureView.forward(req, res);
			}
		} // getOne_For_Update-end

		if ("insert".equals(action)) { // 來自addActRnk.jsp的請求
			List<String> aplMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", aplMsgs);
			ActAplService actAplSvc = new ActAplService();
			ActsService as = new ActsService();

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String memid = memVO.getMem_id();// 會員登入直接撈他的資料

				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					aplMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					aplMsgs.add("會員編號以MEM開頭，長度10碼");
				}

				String actid = req.getParameter("actid");// 前端還是會傳活動編號過來
				if (actid == null || actid.trim().length() == 0) {
					aplMsgs.add("活動編號請勿空白");
				}

				ActsVO avo = as.getOneAct(actid);
				if (memVO.getPoints() < avo.getPts()) {
					aplMsgs.add("點數不足參與活動 ，請儲值");
				}

				// 檢查有沒有重複參加
				boolean OK = actAplSvc.isRepeat_APL(memVO.getMem_id(), actid);
				if (OK == true) {
					aplMsgs.add("已經報名過該活動");
				}

				String sts = "尚未回覆";

				String rson = req.getParameter("rson");
				if (rson == null || rson.trim().length() == 0) {
					rson = "No Reason";
					aplMsgs.add("請輸入報名理由");
				}

				ActAplVO actAplVO = new ActAplVO();
				actAplVO.setMemid(memid);
				actAplVO.setActid(actid);
				actAplVO.setRson(rson);
				actAplVO.setSts(sts);

				// Send the use back to the form, if there were errors
				if (!aplMsgs.isEmpty()) {
					req.setAttribute("actAplVO", actAplVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/actapl/addActApl.jsp");
					failureView.forward(req, res);
					return;
				}

//				/*************************** 2.開始新增資料 ***************************************/
				actAplVO = actAplSvc.addActApl(memid, actid, rson, sts);// 新增報名資料
				// 清乾淨session資料
				session.removeAttribute("actid");
				session.removeAttribute("action");
				// ============通知用==================
				NotifyWS notify = new NotifyWS();

				String urlNotify6 = req.getContextPath() + "/front-end/acts/manage_own_acts.jsp";

				// ActsVO avo的會員編號是＿主辦者, ActAplVO aavo 的會員編號是＿報名者編號
				notify.sendNotify(6, memid, avo.getMemid(), urlNotify6, "", System.currentTimeMillis(), "no");
				// ============通知用==================

//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				// 轉交前關閉開啟的Service
				actAplSvc = null;
				as = null;

				// 重導避免填表單
				String url = req.getContextPath();
				url += "/front-end/acts/manage_join_event.jsp";
				res.sendRedirect(url);
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				aplMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/actapl/addActApl.jsp");
				failureView.forward(req, res);
			}
		} // if-insert-end

		if ("delete".equals(action)) { // 來自listAllActsApl.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String apluid = req.getParameter("apluid");

				/*************************** 2.開始刪除資料 ***************************************/
				ActAplService actAplSvc = new ActAplService();
				actAplSvc.deleteActApl(apluid);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/actapl/listAllActsApl.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/listAllActsApl.jsp");
				failureView.forward(req, res);
			}
		} // delete-end

		if ("update".equals(action)) { // 來自update_actrnk_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			ActAplVO actAplVO = new ActAplVO();

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				// System.out.println("取參數前");
				String apluid = req.getParameter("apluid");

				String actid = req.getParameter("actid");
				if (actid == null || actid.trim().length() == 0) {
					errorMsgs.add("活動編號請勿空白");
				}

				String memid = req.getParameter("memid");
				String memidReg = "^[MEM + (0-9)]{10}$";
				if (memid == null || memid.trim().length() == 0) {
					errorMsgs.add("主辦者編號: 請勿空白");
				} else if (!memid.trim().matches(memidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號以MEM開頭，長度10碼");
				}

				String rson = req.getParameter("rson");
				if (rson == null || rson.trim().length() == 0) {
					rson = "No Reason";
				}

				String sts = req.getParameter("sts");
				if (sts == null || sts.trim().length() == 0) {
					errorMsgs.add("報名狀態請勿空白");
				}

				System.out.println("取參數後");

				actAplVO.setApluid(apluid);
				actAplVO.setMemid(memid);
				actAplVO.setActid(actid);
				actAplVO.setRson(rson);
				actAplVO.setSts(sts);
				System.out.println(actAplVO);
				req.setAttribute("actAplVO", actAplVO);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("有錯誤訊息 準備跳轉");
					req.setAttribute("actAplVO", actAplVO); // 含有輸入格式錯誤的actsVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/actapl/update_actapl_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ActAplService actAplSvc = new ActAplService();
				// APLUID = ?, MEMID = ?, ACTID = ?, RSON = ?, STS = ?
				actAplVO = actAplSvc.updateActApl(apluid, memid, actid, rson, sts);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/actapl/listAllActsApl.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			}
			// Exception
			catch (RuntimeException e) {
				req.setAttribute("actAplVO", actAplVO);// actAplService 方法也是會出現 SQL錯誤的
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/actapl/update_actapl_input.jsp");
				failureView.forward(req, res);
			}
		} // update-end

	}// doPost-end

}
