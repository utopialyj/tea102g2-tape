<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%@ page import="com.post_comment.model.*"%>
<%@ page import="com.hobby_list.model.*"%>
<%@ page import="com.member_hobby.model.*"%>

<!DOCTYPE html>

<%
	// 	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	// 要改成從進來的源頭取得member_infoVO	

	Member_InfoService memSvc = new Member_InfoService();
	Member_InfoVO thisMem_VO = (Member_InfoVO) memSvc.getOneM_Info((String) request.getParameter("Member"));
	pageContext.setAttribute("thisMem", thisMem_VO.getMem_id());
	pageContext.setAttribute("mainMem", ((Member_InfoVO) session.getAttribute("member_infoVO")).getMem_id());
	pageContext.setAttribute("thisMem_VO", thisMem_VO);

	Hobby_ListService hobSvc = new Hobby_ListService();
	List<Hobby_ListVO> hobVO = hobSvc.getAll();
	pageContext.setAttribute("hobVO", hobVO);

	Member_HobbyService memHobSvc = new Member_HobbyService();
	List<Member_HobbyVO> memHobVO = memHobSvc
			.findHobCode(((Member_InfoVO) session.getAttribute("member_infoVO")).getMem_id());
	pageContext.setAttribute("memHobVO", memHobVO);
%>
<html>
<head>
<title>All Post info</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/modal.css">
<style>
.displayNone {
	display: none;
}

article.paper {
	width: 100%;
	margin: 10px auto 0;
	border-radius: 5px;
	padding: 10px;
	background-color: white;
	box-shadow: 1px 1px 5px rgb(119, 120, 160);
}

img.main {
	width: 100px;
	height: 100px;
}

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
	padding: 5px;
	text-align: center;
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
<body bgcolor="white">

	<%@ include file="/front-end/nav.jsp"%>

	<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-2 box1">

				<c:if test="${thisMem eq mainMem}">

					<!-- 改的			 -->
					<aside>
						<div>
							<img id="gs_big_pic_img"
								src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${thisMem}"
								class="rounded-circle main" style="margin: 15px 0px 10px 0px">
						</div>
						<label class="btn btn-light"><form action="<%=request.getContextPath()%>/back-end/post/post"
						method="post" id="bigpic_form" enctype="multipart/form-data" style='display:none'>
						<input type="file" name="gs_big_pic" id="gs_big_pic"
							accept="image/*" style="width: 200px" style='width:200px' style='display:none'/>
					</form><i class="fas fa-camera"></i>編輯頭像</label>
						<a
							href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${mainMem}">
							<i class="far fa-address-card" aria-hidden="true"></i> 個人簡介
						</a> <a
							href="<%=request.getContextPath()%>/front-end/profile/listAllPost.jsp?Member=${mainMem}">
							<i class="far fa-newspaper" aria-hidden="true"></i> 個人動態
						</a> <a
							href="<%=request.getContextPath()%>/front-end/profile/friendList.jsp?Member=${mainMem}">
							<i class="far fa-list-alt" aria-hidden="true"></i> 好友列表
						</a> <a href="javascript:void(0)" id="float_btn"> <i
							class="far fa-list-alt" aria-hidden="true"></i> 過濾設定
						</a>

					</aside>
					<!-- 看要怎麼處理 -->
					<div class="overlay" id="float_btn">
						<article>
							<h4>過濾設定</h4>
							<div id="join_col_btns">
								<button type="button" class="btn_modal_close"
									style="float: right">關閉</button>
								<form method="post"
									action="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do">

									<div style="float: left; margin-left: 10%;">
										選取新增的興趣:<br>
										<c:forEach var="Hobby_ListVO" items="${hobVO}">
											<label><input type="checkbox" name="hobby"
												value="${Hobby_ListVO.hob_code}">${Hobby_ListVO.hob}</label>
											<br>

										</c:forEach>

										<input type="hidden" name="Member" value="${thisMem}">
										<input type="hidden" name="action" value="addHobby"> <input
											type="submit" value="送出過濾條件" style="position: absolute;">
									</div>
								</form>

								<form method="post"
									action="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do">
									<div style="float: right; margin-right: 10%;">
										選取要刪除的興趣:<br>
										<c:forEach var="memHobVO" items="${memHobVO}">
											<c:forEach var="Hobby_ListVO" items="${hobVO}">
												<c:if test="${memHobVO.hob_code == Hobby_ListVO.hob_code}">
													<label><input type="checkbox" name="delHob"
														value="${Hobby_ListVO.hob_code}">
														${Hobby_ListVO.hob}</label>
													<br>
												</c:if>
											</c:forEach>
										</c:forEach>

										<input type="hidden" name="Member" value="${thisMem}">
										<input type="hidden" name="action" value="deleteHob">
										<input type="submit" value="刪除" style="position: absolute;">
									</div>
								</form>

							</div>
						</article>
					</div>
			</c:if>
			<c:if test="${thisMem ne mainMem}">
				<!-- 改的			 -->
				<aside>
					<div>
						<img id="gs_big_pic_img"
							src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${thisMem}"
							class="rounded-circle main" style="margin: 15px 0px 10px 0px">
					</div>
					<a
						href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${thisMem}">
						<i class="far fa-address-card" aria-hidden="true"></i> 個人簡介
					</a> <a
						href="<%=request.getContextPath()%>/front-end/profile/listAllPost.jsp?Member=${thisMem}">
						<i class="far fa-newspaper" aria-hidden="true"></i> 個人動態
					</a> <a
						href="<%=request.getContextPath()%>/front-end/profile/friendList.jsp?Member=${thisMem}">
						<i class="far fa-list-alt" aria-hidden="true"></i> 好友列表
					</a>
				</aside>
				
			</c:if>
			</div>
		<div class="col-8 box2">
			<table>

				<article class="paper">
					<c:if test="${thisMem eq mainMem}">
						<a
							href="<%=request.getContextPath()%>/front-end/profile/update_introduction.jsp" class="btn btn-outline-secondary"> 編輯資料</a>
					</c:if>
					<div>
						<br>
					</div>
					<div>
						姓名:<%=thisMem_VO.getUser_name() == null ? "" : thisMem_VO.getUser_name()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						性別:<%=thisMem_VO.getGender()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						生日:<%=thisMem_VO.getM_birthday()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						星座:<%=thisMem_VO.getHoroscope()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						血型:<%=thisMem_VO.getBlood_type()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						專長:<%=thisMem_VO.getSpecialty() == null ? "" : thisMem_VO.getSpecialty()%></div>
					<div class="line">
						<hr width="100%">
					</div>

					<!-- 缺興趣-->
					<div>
						學校:<%=thisMem_VO.getSchool() == null ? "" : thisMem_VO.getSchool()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						公司:<%=thisMem_VO.getCompany() == null ? "" : thisMem_VO.getCompany()%></div>
					<div class="line">
						<hr width="100%">
					</div>
					<div>
						自我介紹:<%=thisMem_VO.getIntro() == null ? "快來新增你的自我介紹吧!" : thisMem_VO.getIntro()%></div>
					<div class="line">
						<hr width="100%">
					</div>

				</article>

			</table>
		</div>


		<%@ include file="/front-end/chatroom.jsp"%>


	</div>
	</div>

	<!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
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
// var a = new FormData($('#uploadForm')[0])
// console.log(a)
// console.log(new FormData($('#uploadForm')[0]))
// console.log('input#file')
// let data = new FormData)();
// data.append()
// 更換大頭照ajax
	
	
	$("#gs_big_pic").on("change",function(event){
		var reader = new FileReader();
 		reader.readAsDataURL(this.files[0]);
// 		var file = event.target.files[0];
// 		reader.readAsDataURL(file);
		reader.addEventListener("load",function(e){
			
            //觸發就清空img的src
			$("#gs_big_pic_img").attr("src","");
			
			let obj = new FormData($("#bigpic_form")[0]);
			console.log(obj)
			console.log($("bigpic_form")[0])
			obj.append("action","update_bigpic");
			$.ajax({
				url:"<%=request.getContextPath()%>/back-end/post/post",	
				type:"POST",
				data:obj,
				contentType:false,
				processData:false,
				cache:false,
				
				success:function(){
												
					$("#gs_big_pic_img").attr("src", e.target.result);
				},
				error:function(err){
					alert("大頭照更新錯誤");
				}
			})
		})
	})
	
	



</script>

	<script>
$(function() {

	  //開啟Modal的方式 ，點擊 a_tag 開啟
	  $("a#float_btn").on("click", function() {
//	    alert("hello World");
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