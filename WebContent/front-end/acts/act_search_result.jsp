<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.acts.model.*"%>
<%@ page import="com.area_list.model.*"%>
<%@ page import="com.member_info.model.*"%>



<%
	ActsService actsSvc = new ActsService();

	//Controller回傳的搜尋結果
	List<ActsVO> list = (List<ActsVO>) session.getAttribute("actList");

	Member_InfoVO member_infoVO = null;

	//空指標問題要小心
	if (list != null) {//搜尋無果 
		list.removeAll(actsSvc.overtime_Act());

		//取出會員資料
		member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

		//登入的情況下 去掉已參加過的活動 && 去除 自己主辦的活動
		if (member_infoVO != null && actsSvc.get_all_inAct(member_infoVO.getMem_id()) != null) {
			list.removeAll(actsSvc.get_all_inAct(member_infoVO.getMem_id()));
			//排除會員自己主辦的活動
			list.removeAll(actsSvc.getHoldActs(member_infoVO.getMem_id()));

		}

	}
	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	pageContext.setAttribute("list", list);
	System.out.println("這裡是 act_search_result.jsp ");
%>

<jsp:useBean id="arlSvc" scope="page"
	class="com.area_list.model.AreaListService" />

<!-- typecode 和 acttype 連動用 -->
<jsp:useBean id="atSvc" scope="page"
	class="com.acttype.model.ActTypeService" />


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>活動搜尋頁面</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">

<!-- 浮動視窗用 CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/acts/css/modal.css">
<!-- 浮動視窗用 CSS -->

<!-- 活動表用用 CSS -->
<%-- 	<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/show_table.css"> --%>



<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">




<!-- RWD 區塊切割提示 CSS-->
<!-- 	<link rel="stylesheet" href="css/style.css"> -->


<!-- 卡片活動 CSS -->
<link rel="stylesheet" href="css/myCard.css">

<script src="https://kit.fontawesome.com/e66ce32cfd.js"
	crossorigin="anonymous"></script>

<!-- 搜尋 submit 按鈕 -->
<style>
.my_submit_btn {
	float: right;
	position: relative;
	top: -45px;
	left: 5px;
}
}
</style>

<!-- 圖片放大燈箱 -->
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.css" />
<script
	src="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.js"></script>
<!-- 圖片放大燈箱 -->
<!--  <a data-fancybox="gallery" href="big_1.jpg">-->


<!-- 側邊攔的css 開始-->
<style>

aside {
 color: #fff;
 width: 100%;
 padding-left: 20px;
 height: 500px;
 background-image: linear-gradient(30deg, #ffffff, #ff8a8f);
    border-top-right-radius: 80px;
    -webkit-filter: drop-shadow(12px 12px 7px rgba(0, 0, 0, 0.7));
    filter: drop-shadow(-12px 5px 8px rgba(0, 0, 0, 0.3));
}

aside a {
 font-size: 16px;
 font-size: 16px;
 color:#565c5f;
 display: block;
 padding: 12px;
  padding-left: 15px; 
 text-decoration: none;
 -webkit-tap-highlight-color: transparent;
}

aside a:hover {
 color: #6c757d;
 background: #fff;
 outline: none;
 position: relative;
 background-color: #fff;
 border-top-left-radius: 20px;
 border-bottom-left-radius: 20px;
 text-decoration:none;
}

aside a i {
 margin-right: 5px;
}

aside a:hover::after {
 content: "";
 position: absolute;
 background-color: transparent;
 bottom: 100%;
 right: 0;
 height: 35px;
 width: 35px;
 border-bottom-right-radius: 18px;
 box-shadow: 0 20px 0 0 #fff;
}

aside a:hover::before {
 content: "";
 position: absolute;
 background-color: transparent;
 top: 38px;
 right: 0;
 height: 35px;
 width: 35px;
 border-top-right-radius: 18px;
 box-shadow: 0 -20px 0 0 #fff;
}

aside p {
 margin: 0;
 padding: 40px 0;
}

body {

 width: 100%;
 height: 100vh;
 margin: 0;
}
</style>
<!-- 側邊攔的css 尾 -->



<style>
/* 按會員權限隱藏部分內容使用 */
.displayNone {
	display: none;
}
</style>
</head>

<body>


	<%@ include file="/front-end/nav.jsp"%>



	<div class="container mt-3 ">
		<!-- Content here -->
		<div class="row">


		<div class="col-2 box1">
				<aside>
					<p></p>
					<a href="<%=request.getContextPath()%>/front-end/acts/addActs.jsp">
						<i class="far fa-calendar-plus" aria-hidden="true"></i> 發起活動
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">
						<i class="fa fa-laptop" aria-hidden="true"></i> 活動預覽
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_own_acts.jsp">
						<i class="fas fa-tasks" aria-hidden="true"></i> 活動管理
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_join_event.jsp">
						<i class="fas fa-sign-in-alt" aria-hidden="true"></i> 活動報名
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_self_col.jsp">
						<i class="fa fa-star-o" aria-hidden="true"></i> 活動收藏
					</a>
				</aside>
			</div>
			<!-- 左側 col-2 -->


			<!--    中央  col-8  -->
			<div class="col-8 box2">

				<!-- class="form-inline" -->
				<form method="post"
					action="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet">
					<!-- 搜尋框 -->
					<label class="my-1 mr-2" for="inlineFormCustomSelectPref">搜尋活動偏好</label>
					<select class="custom-select my-1 mr-sm-2"
						id="inlineFormCustomSelectPref" name ="type">
						<option selected>請選擇</option>
						<c:forEach var="typeVO" items="${atSvc.all}">
							<option value="${typeVO.typecode}">${typeVO.acttype}</option>
						</c:forEach>
					</select> 
					 <label for="f_date1">起算時間</label> <input name="time1" id="f_date1"
						type="text" autocomplete="off"> <label for="f_date2">結束時間</label>
					<input name="time2" id="f_date2" type="text" autocomplete="off">
					<br> <label id="area"> 地點</label> <select id="area"
						name="area">
						<option value="請選擇">請選擇
							<c:forEach var="alVO" items="${arlSvc.all}">
								<option value="${alVO.area_code}">${alVO.area}
							</c:forEach>
					</select> <br> <input type="hidden" name="action" value="search" />
					<button type="submit" class="btn btn-primary my-1 my_submit_btn">Submit</button>
				</form>

				<%@ include file="page1.file"%>
				<div class="row row-cols-1 row-cols-md-2">
					<c:forEach var="actVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<%
							ActsVO actVO = (ActsVO) pageContext.getAttribute("actVO");
								request.setAttribute("actVO", actVO);
						%>
						<div class="col mb-4">
							<div class="card">
								<a data-fancybox="gallery"
									href="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?getPic=getPicture&actid=${actVO.actid}">
									<img id="viewer"
									src="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?getPic=getPicture&actid=${actVO.actid}"
									alt="這裡放圖片顯示" class="card-img-top" alt="..." height="200"
									weight="100">
								</a>
								<div class="card-body">
									<h6>
										<span> <c:forEach var="areaListVO"
												items="${arlSvc.all}">
												<c:if test="${actVO.areacd == areaListVO.area_code}">
													<font size="3">${areaListVO.area}</font>
												</c:if>
											</c:forEach>
										</span>
									</h6>
									<h6 class="card-title">
										<a
											href="<%= request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${actVO.memid}">
											<img
											src="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?headShot=getHeadShot&actid=${actVO.actid}"
											class="rounded-circle" style="width: 35px;">
										</a> &nbsp ${actVO.title} &nbsp
									</h6>
									<span style="display: none">${actVO.actid}</span>
									<p class="card-text actCont">${actVO.cont}</p>

									<!--和 table's tr 不同層 ，要先埋入活動編號-->
									<!--在JQuery的地方用filter() 取出-->
									<div class="overlay" id="${actVO.actid}">
										<article>
											<h1>活動主題 : ${actVO.title}</h1>
											<h3>
												時間:<%=sdf.format(actVO.getTime())%></h3>
											<div>
												<font color="red"><span>熱度: <%=actsSvc.getHot(actVO)%></span>
													&nbsp <span>人數: ${actVO.peop} </font></span>
											</div>
											<c:forEach var="atVO" items="${atSvc.all}">
												<c:if test="${ actVO.type == atVO.typecode}">
											類型: ${atVO.acttype}
										</c:if>
											</c:forEach>
											<br> 地點:<a
												href="<%= request.getContextPath()%>/front-end/acts/googleMap.jsp?actid=${actVO.actid}"
												target="_blank">${actVO.loc}</a><br>
											店家資訊:${actVO.store}<br> <font color="red">
												預算:${actVO.bgt}</font> &nbsp <font color="red">點數:${actVO.pts}<br></font>
											<h4>活動內容:</h4>
											<p>${actVO.cont}</p>
											<div id="join_col_btns">
												<button type="button" class="btn_modal_close"
													style="float: right">關閉</button>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/front-end/actapl/addActApl.jsp"
													style="margin-bottom: 0px;">
													<input type="hidden" name="actid" value="${actVO.actid}">
													<input type="hidden" name="action" value="insert">
													<input type="submit" value="報名">
												</FORM>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/front-end/actcol/ActColController"
													style="margin-bottom: 0px;">
													<input type="hidden" name="actid" value="${actVO.actid}">
													<input type="hidden" name="action" value="insert">
													<input type="submit" value="收藏">
												</FORM>
											</div>
										</article>
									</div>

								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<%@ include file="page2.file"%>

			</div>
			<!-- 中央 col-8 -->


			<!-- 右側 col-2 -->
			<%@ include file="/front-end/chatroom.jsp"%>
			<!-- 右側 col-2-->
		</div>
		<!-- row_end -->
	</div>
	<!-- container mt-3 _end -->



	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>

	<script>
  
 <%@ include file="/front-end/js/notification.js" %>

 </script>

</body>


<%
	java.sql.Timestamp time = null;
	time = new java.sql.Timestamp(System.currentTimeMillis());
%>



<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>


<script>
	         $.datetimepicker.setLocale('zh'); 
	         $('#f_date1').datetimepicker({
	 	       theme: '',              //theme: 'dark', 
	 	       timepicker:true,       //timepicker:true, 
	 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘) 
	 	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s', 
			   value: '<%=time%>'
	 		}); 

	         $('#f_date2').datetimepicker({
	  	       theme: '',              //theme: 'dark',
	 	       timepicker:true,       //timepicker:true,
	  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	  	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
	 		   value: '<%=time%>'
	});
</script>

<script>
	$(function() {

		//開啟Modal的方式 ，點活動內容的區塊可以觸發
		$("div.card").on("click", "p.card-text.actCont", function() {
			//alert("hello World");
			let check_id = $(this).prev().text();//取出我埋進去的 "活動編號"

			//因為很多個 div.overlay 而且 又何 tr不同層 ，所以只能用 filter()過濾出來
			$("div.overlay").filter(function(index) {
				return $(this).attr("id") == check_id; //左邊:動態生成時我埋進去的"活動編號" || 右邊:被點擊的活動編號
			}).delay("fast").addClass("-on");

		})

		// 關閉 Modal
		$("button.btn_modal_close").on("click", function() {
			$("div.overlay").delay("fast").addClass("-opacity-zero");

			// 設定隔一秒後，移除相關 class
			setTimeout(function() {
				$("div.overlay").removeClass("-on -opacity-zero");
			}, 500);
		});

		//ESC也可關閉浮動視窗
		$(document).keydown(function(event) {
			var key = event.which;
			console.log(key);//抓取鍵碼值

			switch (key) {
			case 27:
				$("div.overlay").addClass("-opacity-zero");

				// 設定隔一秒後，移除相關 class
				setTimeout(function() {
					$("div.overlay").removeClass("-on -opacity-zero");
				}, 500);
				break;
			}

		});

	});
</script>




</html>