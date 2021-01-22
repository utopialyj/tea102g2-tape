<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*, java.util.Base64"%>
<%@ page import="java.text.DateFormat, java.text.SimpleDateFormat"%>
<%@ page import="com.acts.model.*"%>
<%@ page import="com.actcol.model.*"%>
<%@ page import="com.actapl.model.*"%>
<%@ page import="com.member_info.model.*"%>


<%
	ActsService actSvc = new ActsService();
	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

	List<ActsVO> list = actSvc.getHoldActs(member_infoVO.getMem_id());

	for (ActsVO avo : list) {
		System.out.println("avo's actid ? " + avo.getActid() + " avo's time ?" + avo.getTime());
	}
	System.out.println("====================================== ");

	//移除過期的"主辦活動"
	List<ActsVO> passList = actSvc.overTime_own_Act(member_infoVO.getMem_id());
	for (ActsVO avo : passList) {
		System.out.println("avo's actid ? " + avo.getActid() + " avo's time ?" + avo.getTime());
	}
	list.removeAll(passList);

	pageContext.setAttribute("list", list);
	pageContext.setAttribute("actSvc", actSvc);
%>

<jsp:useBean id="memSvc" scope="page"
	class="com.member_info.model.Member_InfoService" />

<!-- typecode 和 acttype 連動用 -->
<jsp:useBean id="atSvc" scope="page"
	class="com.acttype.model.ActTypeService" />


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>主辦頁面</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">


<!-- BootStrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<!-- 浮動視窗用 CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/acts/css/modal.css">
<!-- 浮動視窗用 CSS -->

<!-- 活動表用用 CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/acts/css/show_table.css">

<!-- RWD 區塊切割提示 CSS-->
<!-- 	<link rel="stylesheet" href="css/style.css"> -->


<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>


<!-- 報名表的部分 CSS -->
<style>
tr.mem_join {
	background-color: #B8B8FF;
}
</style>
<!-- 報名表的部分 CSS -->

<!-- 活動表格 CSS -->
<style>
tr.act_part {
	background-color: #EBEBFF;
}
</style>

<!-- 活動表格 CSS -->
<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	text-align: center;
}

td.maxwidth {
	max-width: 120px;
}

td.Pic {
	white-space: normal;
}
</style>

<!-- 超連結顏色 -->
<style>
td.maxwidth {
	text-decoration: none;
	cursor: pointer;
}
</style>
<!-- 超連結顏色 -->


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
	<!-- Navbar -->
	<%@ include file="/front-end/nav.jsp"%>
	<!-- --------------------------------------------------------------------------------------------------------------- -->






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
			<div class="col-8 box2 table-responsive">

				<h1>${member_infoVO.user_name}主辦的活動</h1>
				<a
					href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">回到揪團首頁</a>

				<table>
					<c:choose>
						<c:when test="${list.size() != 0}">
							<!-- 有辦活動才會有表頭 -->
							<tr class="act_par1t">
								<th>活動主題</th>
								<th>活動時間</th>
								<th>活動類型</th>
								<th>活動內容</th>
								<th>地點</th>
								<th>活動圖片</th>
								<th>修改活動</th>
								<%-- 
				<th>人數</th>
				<th>地區</th>
				<th>熱度</th>
				<th>店家資訊</th>
				<th>預算</th>
				<th>參與所需點數</th>
				--%>
							</tr>
						</c:when>
					</c:choose>
					<%@ include file="page1.file"%>
					<c:forEach var="actVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<tr class="modal_out_tr act_part">
							<!-- 
					主辦活動列出來
				 -->
							<%
								ActsVO actVO = (ActsVO) pageContext.getAttribute("actVO");
							%>

							<td><a class="title"
								href="<%= request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${actVO.memid}"
								alt="主辦者的頁面">${actVO.title}</a></td>
							<td><%=sdf.format(actVO.getTime())%></td>
							<td><c:forEach var="atVO" items="${atSvc.all}">
									<c:if test="${ actVO.type == atVO.typecode}">
											${atVO.acttype}
										</c:if>
								</c:forEach></td>

							<!-- 多埋一個活動編號 -->
							<td style="display: none">${actVO.actid}</td>
							<!-- 多埋一個活動編號 -->


							<td class="maxwidth modal_inner_td">${actVO.cont}</td>

							<!--  和 table's tr 不同層 ，要先埋入活動編號 -->
							<!-- 在JQuery的地方用filter() 取出 -->
							<%-- <div class="overlay" id="${actVO.actid}"> --%>
							<!--   <article> -->
							<%--     <h1>活動主題 : ${actVO.title}</h1> --%>
							<!--     <h3>活動內容: </h3> -->
							<%--     <p>${actVO.cont}</p> --%>
							<!--     <button type="button" class="btn_modal_close">關閉</button> -->
							<!--   </article> -->
							<!-- </div> -->



							<!-- 和 table's tr 不同層 ，要先埋入活動編號-->
							<!-- 在JQuery的地方用filter() 取出-->
							<div class="overlay" id="${actVO.actid}">
								<article>
									<h1>活動主題 : ${actVO.title}</h1>
									<h3>
										時間:<%=sdf.format(actVO.getTime())%></h3>
									<div>
										<font color="red"><span>熱度: <%=actSvc.getHot(actVO)%></span>
											&nbsp <span>人數: ${actVO.peop} </font></span>
									</div>
									<c:forEach var="atVO" items="${atSvc.all}">
										<c:if test="${ actVO.type == atVO.typecode}">
			類型: ${atVO.acttype}
		</c:if>
									</c:forEach>
									<br> 地點:<a
										href="<%= request.getContextPath()%>/front-end/acts/googleMap.jsp?actid=${actVO.actid}"
										target="_blank">${actVO.loc}</a><br> 店家資訊:${actVO.store}<br>
									<font color="red"> 預算:${actVO.bgt}</font> &nbsp <font
										color="red">點數:${actVO.pts}<br></font>
									<h4>活動內容:</h4>
									<p>${actVO.cont}</p>
									<div id="join_col_btns">
										<button type="button" class="btn_modal_close"
											style="float: right">關閉</button>
									</div>
								</article>
							</div>

							<td class="maxwidth"><a
								href="<%= request.getContextPath()%>/front-end/acts/googleMap.jsp?actid=${actVO.actid}"
								target="_blank">${actVO.loc}</a></td>
							<td class="Pic"><img
								src="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?getPic=getPicture&actid=${actVO.actid}"
								alt="這裡放圖片顯示" height="50" weight="50"></td>
							<td><a href="<%=request.getContextPath()%>/front-end/acts/update_act.jsp?actid=${actVO.actid}"><button>修改</button></a></td>
							<%-- 
							<td>${actVO.peop}</td>
							<td>${actVO.areacd}</td>
							<td><%=actSvc.getHot(actVO) %></td>
							<td>${actVO.store}</td>
							<td>${actVO.bgt}</td>
							<td>${actVO.pts}</td>
							--%>
						</tr>
						<!-- 
				forEach 取出一個活動， 再由這個活動的編號去跑一個 forEach 做打印報名活動的所有會員
			 -->
						<%
							List<ActAplVO> aplArr = actSvc.getAllAplFromAct(actVO.getActid());
								List<ActAplVO> aplDisagreeArr = actSvc.get_All_Disagree(actVO.getActid());
								if (aplArr != null && aplDisagreeArr != null) {
									aplArr.removeAll(aplDisagreeArr);
								}
								pageContext.setAttribute("aplArr", aplArr);
						%>
						<c:choose>
							<c:when test="<%=aplArr.size() != 0%>">
								<!-- 活動有人參加才會有表頭 -->
								<tr class="table-warning">
									<th>報名會員</th>
									<th>報名理由</th>
									<th>報名狀態</th>
									<th>手動調整</th>
								</tr>
								<c:forEach var="aplVO" items="${aplArr}">
									<%
										ActAplVO aplVO = (ActAplVO) pageContext.getAttribute("aplVO");
									%>
									<tr class="table-warning">

										<td><c:forEach var="member_invite" items="${memSvc.all}">
												<c:if test="${aplVO.memid == member_invite.mem_id}">
													<a class="title"
														href="<%= request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${member_invite.mem_id}"
														alt="報名人ㄉㄜ"> <font color="blue" size="4">${member_invite.user_name}
													</font> <!-- 											</a> -->
												</c:if>
											</c:forEach></td>
										<td class="modal_inner_apl_td" id="${aplVO.apluid}">${aplVO.rson}</td>

										<!-- 和 table's tr 不同層 ，要先埋入活動編號-->
										<!-- 在JQuery的地方用filter() 取出-->
										<div class="overlay" id="${aplVO.apluid}">
											<article>
												<h1>報名理由:</h1>
												<p>${aplVO.rson}</p>
												<button type="button" class="btn_modal_close">關閉</button>
											</article>
										</div>
										<td id="sts_column"><font color="red" size="4">${aplVO.sts}</font></td>
										<td><input type="radio" id="sent_uid_agree" value="同意"
											name="sent_uid">同意 <input type="radio"
											id="sent_uid_disagree" value="不同意" name="sent_uid">不同意
											<input class="test" id="uid" type="hidden"
											value="${aplVO.apluid}"></td>
									</tr>

								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
					<!-- 一個 actVO結束  -->
				</table>
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


	<script>
	$(function(){
		$("input[name='sent_uid']").change(function(){
				//alert("hello");//有這一行代表 最外層函數可以動
			
			let that = this;//確認觸發的東西是不是 radio button
			let uid = $(this).parent().children("input#uid").val();//確認隱藏的input_帶者的報名表單編號有沒有抓到
			let sts = $(this).val();//抓到點選的radio button值
				console.log(uid + "__" + sts);
			
			//準備送去後端的資料
			let actObj = {
				"action":"update_holder",
				"apluid" : uid,
				"sts":sts
				
			}
			
			
			$.ajax({
				url:"<%=request.getContextPath()%>/front-end/actapl/ActAplController",
											type : "POST",
											data : actObj,
											dataType : "JSON",
											success : function(result) {
												if (result.okay === "yes_to_in") {
													$(that)
															.parent()
															.prev()
															.html(
																	"<font color='red' size='4'>同意</font>");
													console.log("同意")
												}
												if (result.not_okay === "not_to_in") {
													$(that)
															.parent()
															.prev()
															.html(
																	"<font color='red' size='4'>不同意</font>");
													console.log("不同意")

												}
											},
											error : function(err) {
												console.log(err)
											}

										});//AJAX-結束

							})

		});
	</script>
	<!-- 浮動視窗Script  -->
	<%-- 	<%@ include file="lightbox.file" %> --%>



	<script>
		$(function() {

			//開啟Modal的方式 ，點活動內容的區塊可以觸發
			$("tr.modal_out_tr").on("click", "td.modal_inner_td", function() {
				//alert("hello World");
				console.log($(this).prev().text());
				let check_id = $(this).prev().text();//取出我埋進去的 "活動編號"

				//因為很多個 div.overlay 而且 又何 tr不同層 ，所以只能用 filter()過濾出來
				$("div.overlay").filter(function(index) {
					return $(this).attr("id") == check_id; //左邊:動態生成時我埋進去的"活動編號" || 右邊:被點擊的活動編號
				}).addClass("-on");

				//back_up
				// 			$("div.overlay").filter(function(index){
				// 				return $(this).attr("id") == check_id; //左邊:動態生成時我埋進去的"活動編號" || 右邊:被點擊的活動編號
				// 			}).addClass("-on");

			})

			//點報名理由觸發
			$(document).on("click", "td.modal_inner_apl_td", function() {
				//alert("hello World");
				//console.log($(this).attr("id"));
				let apl_id = $(this).attr("id");//取出我埋進去的 "活動編號"

				//因為很多個 div.overlay 而且 又何 tr不同層 ，所以只能用 filter()過濾出來
				$("div.overlay").filter(function(index) {
					return $(this).attr("id") == apl_id; //左邊:動態生成時我埋進去的"活動編號" || 右邊:被點擊的活動編號
				}).addClass("-on");

				//程式碼_back_up
				// 			$("div.overlay").filter(function(index){
				// 				return $(this).attr("id") == check_id; //左邊:動態生成時我埋進去的"活動編號" || 右邊:被點擊的活動編號
				// 			}).addClass("-on");

			})

			//移動到該欄位 變紅色
			$("td.modal_inner_apl_td").on("mouseenter", function() {
				//alert("hello World");
				$(this).attr("style", "color:red");

			})

			//移動到該欄位 變黑色
			$("td.modal_inner_apl_td").on("mouseleave", function() {
				//alert("hello World");
				$(this).removeAttr("style");

			})

			//移動到該欄位 變紅色
			$("tr.modal_out_tr").on("mouseenter", "td.modal_inner_td",
					function() {
						//alert("hello World");
						$(this).attr("style", "color:red");

					})

			//移動到該欄位 變黑色
			$("tr.modal_out_tr").on("mouseleave", "td.modal_inner_td",
					function() {
						//alert("hello World");
						$(this).removeAttr("style");

					})

			//按下ESC 也可以關閉浮動視窗
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

			// 關閉 Modal
			$("button.btn_modal_close").on("click", function() {
				$("div.overlay").addClass("-opacity-zero");

				// 設定隔一秒後，移除相關 class
				setTimeout(function() {
					$("div.overlay").removeClass("-on -opacity-zero");
				}, 500);
			});

		});
	</script>



</body>
</html>