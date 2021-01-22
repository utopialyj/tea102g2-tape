<%@page import="org.apache.catalina.tribes.membership.Membership"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%@ page import="com.post_comment.model.*"%>
<%@ page import="com.like_post.model.Like_PostService"%>
<%@ page import="com.hobby_list.model.*"%>
<%@ page import="com.member_hobby.model.*"%>
<!DOCTYPE html>

<%
	Member_InfoVO mainMem_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

	Member_InfoService memberSvc = new Member_InfoService();
	String thisMemVO = (String) request.getParameter("Member");
	Member_InfoVO member_infoVO = (Member_InfoVO) memberSvc.getOneM_Info(thisMemVO);
	PostService postSvc = new PostService();
	List<PostVO> list = postSvc.getMemberPost(thisMemVO);
	pageContext.setAttribute("list", list);
	Post_PhotoService post_PhotoSvc = new Post_PhotoService();
	Post_CommentService post_CmSvc = new Post_CommentService();
	pageContext.setAttribute("thisMem", thisMemVO);
	pageContext.setAttribute("mainMem", mainMem_infoVO.getMem_id());
	Like_PostService likePostSvc = new Like_PostService();
	
	Hobby_ListService hobSvc = new Hobby_ListService();
	List<Hobby_ListVO> hobVO = hobSvc.getAll();
	pageContext.setAttribute("hobVO", hobVO);
	
	Member_HobbyService memHobSvc = new Member_HobbyService();
	List<Member_HobbyVO> memHobVO = memHobSvc.findHobCode(mainMem_infoVO.getMem_id());
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

ul{
list-style:none;
}

.displayNone {
	display: none;
}

/* 顯示新增動態用*/
.hide {
	display: none;
}

/* 貼文卡用 */
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

img.second {
	width: 70px;
	height: 70px;
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
							accept="image/*" style="width: 200px" style='width:200px' style='display:none'>
					</form><i class="fas fa-camera"></i>編輯頭像</label>
						<a
							href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${mainMem}">
							<i class="far fa-address-card" aria-hidden="true"></i> 個人簡介
						</a> <a
							href="<%=request.getContextPath()%>/front-end/profile/listAllPost.jsp?Member=${mainMem}">
							<i class="far fa-newspaper" aria-hidden="true"></i> 個人動態
						</a> <a href="<%=request.getContextPath()%>/front-end/profile/friendList.jsp?Member=${mainMem}"> <i class="far fa-list-alt"
							aria-hidden="true"></i> 好友列表
						</a> 
<!-- 這裡						 -->
						<a href="javascript:void(0)" id="float_btn" >
						<i class="far fa-list-alt" aria-hidden="true"></i> 過濾設定
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
											選取新增的興趣:<br>
											<c:forEach var="Hobby_ListVO" items="${hobVO}">
												<label><input type="checkbox" name="hobby"
													value="${Hobby_ListVO.hob_code}">${Hobby_ListVO.hob}</label>
												<br>

											</c:forEach>

											<input type="hidden" name="Member" value="${thisMem}">
											<input type="hidden" name="action" value="addHobby">
											<input type="submit" value="送出過濾條件"
												style="position: absolute;">
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
				<aside>
					<div>
					<img id="gs_big_pic_img"
						src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${thisMem}"
						class="rounded-circle main">
				</div>
						
						<a
							href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${thisMem}">
							<i class="far fa-address-card" aria-hidden="true"></i> 個人簡介
						</a> <a
							href="<%=request.getContextPath()%>/front-end/profile/listAllPost.jsp?Member=${thisMem}">
							<i class="far fa-newspaper" aria-hidden="true"></i> 個人動態
						</a> <a href="<%=request.getContextPath()%>/front-end/profile/friendList.jsp?Member=${thisMem}"> <i class="far fa-list-alt"
							aria-hidden="true"></i> 好友列表
						</a> 

					</aside>	


				</c:if>
			</div>
			<div class="col-8 box2">
				<table>
					<c:if test="${thisMem eq mainMem}">
						<!-- 開始 -->


						<button id="js-startbtn" class="btn btn-outline-secondary">新增動態</button>

						<div class="block hide">
							<article class="paper">
								<a class="modal-close js-modal-close">X</a>
								<form method="post"
									action="<%=request.getContextPath()%>/back-end/post/post"
									name="addform" enctype="multipart/form-data">

									<div>
										<div>

											<div class="photo">
												<a
													href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=member_infoVO.getMem_id()%>">
													<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${member_infoVO.mem_id}"
						class="rounded-circle main">
													
												</a>
											</div>
											<div>${member_infoVO.user_name}</div>


										</div>
										<div>
											<div>動態內容:</div>
											<div>
												<textarea style="width: 400px; height: 80px;"
													name="post_context" id="post_context" placeholder="回覆內容"></textarea>
											</div>
										</div>
										<div>
											<div>動態照片:</div>
											<div>
												<label class="btn btn-outline-secondary"><input type="file" name="photo" id="photo" accept="image/*"
													multiple="multiple" style='display:none'/><i class="fas fa-camera"></i>新增圖片</label>
											</div>
										</div>

									</div>

									<br> <input type="hidden" name="action" value="insert">
									<input class="btn btn-outline-secondary" type="submit" name="addpost" value="送出新增">
								</form>
							</article>
						</div>
						<!-- 尾巴 -->
					</c:if>
					<c:forEach var="postVO" items="${list}">
					
						<%
							PostVO postVO = (PostVO) pageContext.getAttribute("postVO");
								String post_code = postVO.getPost_uid();
								List<Post_PhotoVO> photoList = post_PhotoSvc.findByPostCode(post_code);
								pageContext.setAttribute("photoList", photoList);
								List<Post_CommentVO> cmList = post_CmSvc.findByPost_Code(post_code);
								pageContext.setAttribute("post_code", post_code);
								pageContext.setAttribute("cmList", cmList);

								List<String> likePostMembers = likePostSvc.getLikeMembersByPostcode(post_code);
								pageContext.setAttribute("likePostMembers", likePostMembers);

								if (likePostMembers.isEmpty()) {
									pageContext.setAttribute("heart", 0);
								} else if (likePostMembers.contains(mainMem_infoVO.getMem_id())) {
									pageContext.setAttribute("heart", 1);
								} else {
									pageContext.setAttribute("heart", 0);
								}
						%>
						<article class="paper">
							<ul>
								<li class="p">
									<form method="post"
										action="<%=request.getContextPath()%>/back-end/post/post">
										<div class="poster_size">
											<div class="poster">
												<div class="person">
													<div class="photo">
														<a
															href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=member_infoVO.getMem_id()%>">
															<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${member_infoVO.mem_id}"
						class="rounded-circle main">
														</a>
													</div>
													<div class="info">
														<div style="font-size: 24px;"><%=member_infoVO.getUser_name()%></div>
														<div style="font-size: 12px;">
															<fmt:formatDate value="${postVO.edit_date}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
												</div>
												<div id="${post_code}" class="post_code">

													<input type="hidden" id="post_code" value="${post_code}">
													<input type="hidden" id="lk_memid" value="${mainMem}">
													<c:if test="${heart == 0}">
														<i class="far fa-heart" id="likepost"></i>
														<p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
													</c:if>
													<c:if test="${heart == 1}">
														<i class="fas fa-heart" id="likepost"></i>
														<p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
													</c:if>

													<!-- here-->
												</div>
												<c:if test="${thisMem eq mainMem}">
												<div>
													<input class="btn btn-outline-secondary" type="submit" value="修改">
												</div>
												</c:if>
											</div>
											<div class="context">
												<p>${postVO.post_context}</p>
											</div>
											<div>
												<c:forEach var="post_PhotoVO" items="${photoList}">
<%Post_PhotoVO post_PhotoVO = (Post_PhotoVO) pageContext.getAttribute("post_PhotoVO");%>
<img src="<%=request.getContextPath()%>/back-end/post/post?action=display_postPhoto&photo_uid=<%=post_PhotoVO.getPhoto_uid()%>">
													
												</c:forEach>
											</div>
										</div>
										<input type="hidden" name="post_uid"
											value="${postVO.post_uid}"> <input type="hidden"
											name="action" value="getOne_For_Update_From_Profile">
									</form>
									<div class="line">
										<hr width="100%">
									</div>
									<div class="info">
										<c:forEach var="post_CommentVO" items="${cmList}">
											<%
												Post_CommentVO post_CommentVO = (Post_CommentVO) pageContext.getAttribute("post_CommentVO");
														Member_InfoVO com_memberVO = (Member_InfoVO) memberSvc.getOneM_Info(post_CommentVO.getCmnt_memid());
											%>
											<div class="reply_size">
												<div class="poster">
													<div class="person">
														<div class="photo">
															<a
																href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=com_memberVO.getMem_id()%>">
																<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=<%=com_memberVO.getMem_id()%>"
						class="rounded-circle second">
															</a>
														</div>
														<div style="font-size: 18px;"><%=com_memberVO.getUser_name()%></div>
														<div style="font-size: 12px;"><fmt:formatDate value="${post_CommentVO.cmnt_date}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</div>
														<div>
															<i class="far fa-heart">${post_CommentVO.lk_count}</i>
														</div>
													</div>
												</div>
											</div>

											<div class="reply_context">
												<p>${post_CommentVO.cmnt_context}</p>
											</div>
											<div class="line">
												<hr width="100%">
											</div>
										</c:forEach>
									</div> <!--新增回覆，會員為使用者 -->

									<form method="post"
										action="<%=request.getContextPath()%>/back-end/post/post"
										style="margin-bottom: 0px;">
										<%
											Post_CommentVO post_CommentVO = (Post_CommentVO) request.getAttribute("post_CommentVO");
										%>
										<input type="hidden" name="post_code" value="${post_code}">

										<div class="info">
											<div>
												<a
													href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=mainMem_infoVO.getMem_id()%>">
													<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=<%=mainMem_infoVO.getMem_id()%>"
						class="rounded-circle second">
													
												</a>
											</div>
											<div style="font-size: 18px; align: top;"><%=mainMem_infoVO.getUser_name()%></div>
										</div>

										<div class="reply_context">
											<div>
												<textarea style="width: 400px; height: 80px;"
													name="cmnt_context" placeholder="回覆內容"></textarea>
											</div>
											<div>

												<input type="hidden" name="action"
													value="addCommentFromProfile"> <input class="btn btn-outline-secondary" type="submit"
													value="送出">
											</div>
										</div>
									</form>

								</li>
							</ul>
						</article>

					</c:forEach>
					<c:if test="${empty list}">
					<div>
					<article class="paper">
					<p>
					歡迎使用Tape，快新增你的第一篇動態
					</p>
					</article>
					</div>
					</c:if>
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
	<script type="text/javascript">

// 新增貼文區域隱藏顯示用
	$(function(){
	
	
		  
		  $("#js-startbtn").on("click",function(){
			 
		    $("div.block").removeClass("hide");
		  });
		  
		  $(".js-modal-close").on("click",function(){
			  $("div.block").addClass("hide");
		  });
		});
		
		
	//點讚
	$(function(){
		
		$("i#likepost").click(function(){
			$(this).toggleClass("far fa-heart");
			$(this).toggleClass("fas fa-heart");
		})
	})		
	
	 $("div.post_code").on("click","i#likepost",function(){
		 	//alert("hiiii")
			// console.log($(this).next()) //取得p標籤
	let that = $(this).next();
	let post_code = that.attr("id");
	
	console.log(post_code);
	let likePostObj = {
			"action":"update_post_like",		
			"post_code" : post_code
			}
	
	
	$.ajax({
		url:"<%=request.getContextPath()%>/back-end/post/post",
		type : "POST",
		data : likePostObj,
		dataType:"JSON",
		
		
		success : function(result) {
					
				that.html(parseInt(result.likeSum));
//				$(that).parent().children("p#lk_num").prev().html("<font color='red' size='4'>"+parseInt(result.likeSum)+"</font>");				
			
		},
		error : function(err) {
		}
		
	});//AJAX-結束


	
	

})

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
					type : "POST",
					data : obj,
					contentType : false,
					processData : false,
					cache : false,

					success : function() {

						$("#gs_big_pic_img").attr("src", e.target.result);
					},
					error : function(err) {
						alert("大頭照更新錯誤");
					}
				})
			})
		})
	</script>
<!-- 更新 -->
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