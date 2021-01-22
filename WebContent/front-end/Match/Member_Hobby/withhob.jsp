<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.hobby_list.model.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.relationship.model.*"%>
<%
	Hobby_ListService hobSvc = new Hobby_ListService();
	List<Hobby_ListVO> list3 = hobSvc.getAll();
	pageContext.setAttribute("list3", list3);
%>
<html>
<head>
<meta charset="UTF-8">
<title>興趣過濾推薦</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/modal.css">
<style>
.displayNone {
	display: none;
}
</style>

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

</head>

<body>
	<%@ include file="/front-end/nav.jsp"%>

	<!-- --------------------------------------------------------------------------------------------------------------- -->
	<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-2 box1">
<!-- 改的			 -->
				<aside>
					<p></p>
					<a
						href="<%=request.getContextPath()%>/front-end/Relationship/relationship.do?action=listfriend">
						<i class="far fa-list-alt" aria-hidden="true"></i> 好友列表
					</a> <a
						href="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do?action=getHobby_For_Display">
						<i class="fas fa-user-friends" aria-hidden="true"></i> 好友推薦
					</a>
					
					<a href="javascript:void(0)" id="float_btn">
					<i class="fas fa-filter" aria-hidden="true"></i> 過濾設定
					</a> 
					
					<a
						href="<%=request.getContextPath()%>/front-end/Relationship/relationship.do?action=notice">
						<i class="fas fa-envelope-open-text" aria-hidden="true"></i> 通知列表
					</a>

				</aside>

					<div class="overlay" id="float_btn">
						<article>
							<h4>過濾設定</h4>
							<div id="join_col_btns">
								<button type="button" class="btn_modal_close"
									style="float: right">關閉</button>
								<form method="post"
									action="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do">
									<div style="float: left; margin-left: 10%;">
									<c:forEach var="Hobby_ListVO" items="${list3}">
										<label><input type="checkbox" name="hobby"
											value="${Hobby_ListVO.hob_code}">${Hobby_ListVO.hob}</label>
									<br>
								
									</c:forEach>
									
									<input type="hidden" name="action" value="recommendWithHob">
									<input type="submit" value="送出過濾條件" style="margin-botton = 5%">
									</div>
								</form>
							</div>
						</article>
					</div>


				
			</div>
			<%
				List<String> withHob = (List<String>) session.getAttribute("withHob");
				String memUid = (String) session.getAttribute("memUid");
				pageContext.setAttribute("withHob", withHob);
				Member_InfoService member_infoSvc = new Member_InfoService();
				RelationshipService relationshipSvc = new RelationshipService();
			%>


			<div class="col-8 box2">
			<h3>過濾興趣好友推薦</h3>
				<%@ include file="page1.file"%>
				<c:forEach var="memberid" items="${withHob}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<%-- <c:forEach var="memberid" items="${withHob}" end="5"> --%>
					<%
						// 							取得會員資料
							String memberid = (String) pageContext.getAttribute("memberid");
							Member_InfoVO memberVO = (Member_InfoVO) member_infoSvc.getOneM_Info(memberid);
							pageContext.setAttribute("memberVO", memberVO);
							// 							c:if排除交友紀錄
							List list1 = relationshipSvc.check(memUid, memberid);
							List list2 = relationshipSvc.check(memberid, memUid);
							pageContext.setAttribute("list1", list1);
							pageContext.setAttribute("list2", list2);
					%>
					<c:if test="${empty list1 && empty list2}">
						<%-- 	<c:if test="${memberVO.verify_lv >=2}"> --%>
						<div class="friend-box">
							<div class="memberleft">
								<div class="imghead">
									<!--               <input type="image" class="head" src="/img/java.jpg" onclick=""> -->
									<div>
									<a
										href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${memberVO.mem_id}">
										
											<img id="gs_big_pic_img"
												src="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do?action=display_pic&Member=${memberVO.mem_id}"
												class="rounded-circle second">
										
									</a>
									</div>
									<div class="membername">${memberVO.user_name}</div>
								</div>
							</div>

							<div class="memberright">
								<div class="membertop">
									<%--<%=request.getContextPath()%>/front-end/Relationship/relationship.do --%>
									<input type="button" class="invite" id="${memberVO.mem_id}"
										value="邀請"> <input type="hidden" name="action"
										value="add">
								</div>

								<div class="memberfoot">
									<input type="button" class="ignoreBtn" value="忽略">
								</div>
							</div>

							<div class="membermid">
								<div class="word">${memberVO.intro}</div>
							</div>
						</div>




						<%-- 	</c:if> --%>
					</c:if>

				</c:forEach>

			</div>
			<div style="width: 200px;"></div>
			<%@ include file="page2.file"%>
			<%@ include file="/front-end/chatroom.jsp"%>
		</div>
	</div>
	<!-- Optional JavaScript; choose one of the two! -->


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
 		$(".invite").click(function(){
 			//alert("hello");
 			let that = $(this);
			let memberid = $(this).attr("id");
			console.log(memberid);
			let invite = {
				"memberid":memberid,
				"action":"add"
			}
			
			$.ajax({
				url:"<%=request.getContextPath()%>/front-end/Relationship/relationship.do",
											type : "POST",
											data : invite,
											// 				dataType:"JSON",
											success : function(result) {

												that.attr("disabled", "true");

											}
										})
							})

		})

		$(".ignoreBtn").click(function() {
			$(this).parents(".friend-box").remove();
		})

	$(function() {

		//開啟Modal的方式 ，點擊 a_tag 開啟
		$("a#float_btn").on("click", function() {
// 			alert("hello World");
			let check_id = $(this).text();

		
			$("div.overlay").addClass("-on");


		})

		// 關閉 Modal
		$("button.btn_modal_close").on("click", function() {
			$("div.overlay").addClass("-opacity-zero");

			// 設定隔一秒後，移除相關 class
			setTimeout(function() {
				$("div.overlay").removeClass("-on -opacity-zero");
			}, 500);
		});
		
	  	//ESC也可關閉浮動視窗
		$(document).keydown(function(event){  
	     	var key = event.which;   
	     		console.log(key);//抓取鍵碼值
     
	     		
	       switch(key) { 
	        case 27: 
		        	 $("div.overlay").addClass("-opacity-zero");
		     	    
		     	    // 設定隔一秒後，移除相關 class
		     	   setTimeout(function(){
		     		      $("div.overlay").removeClass("-on -opacity-zero");
		     		    }, 500);
	  			break;
	      } 

     }); 

	});
	</script>
</body>
</html>
