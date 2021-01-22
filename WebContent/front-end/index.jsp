<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%@ page import="com.post_comment.model.*"%>
<%@ page import="com.like_post.model.Like_PostService"%>
<%@ page import="com.like_comment.model.Like_CommentService"%>

<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	pageContext.setAttribute("mem_id", member_infoVO.getMem_id());
	Member_InfoService member_infoSvc = new Member_InfoService();
	List<Member_InfoVO> friends = (List<Member_InfoVO>) session.getAttribute("friends");

	// 0114修改 by J
	//	A段要改成
	List<Member_InfoVO> friendsAndMe = new ArrayList<Member_InfoVO>();
	friendsAndMe.addAll(friends);
	friendsAndMe.add(member_infoVO);
	// 	PostService postSvc = new PostService();
	// 	List<PostVO> list = postSvc.getMembers_Id(friendsAndMe);
	//  B段
	// 	friends.add(member_infoVO);
	PostService postSvc = new PostService();
	//1/13抽換
	List<PostVO> list = postSvc.getPostToWall(friendsAndMe);

	//     // 0114註解 by J	
	// //	A段要改成
	// // 	List<Member_InfoVO> friendsAndMe = new ArrayList<Member_InfoVO>();
	// // 	friendsAndMe.add(member_infoVO);
	// // 	friendsAndMe.addAll(friends);
	// // 	PostService postSvc = new PostService();
	// // 	List<PostVO> list = postSvc.getMembers_Id(friendsAndMe);
	// //  B段
	// 	friends.add(member_infoVO);
	// 	PostService postSvc = new PostService();
	// 	//1/13抽換
	// 	List<PostVO> list = postSvc.getPostToWall(friends);

	pageContext.setAttribute("list", list);
	Post_PhotoService post_PhotoSvc = new Post_PhotoService();
	Post_CommentService post_CmSvc = new Post_CommentService();
	Member_InfoVO friend_MemVO = new Member_InfoVO();

	Like_PostService likePostSvc = new Like_PostService();
%>
<!DOCTYPE html>
<html>
<head>
<title>Tape | Home</title>




<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">

<style>
.displayNone {
	display: none;
}

ul{
list-style:none;
}

/* 顯示新增動態用*/
div.hide {
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





.person {
	display: inline-block;
}
.person .photo {
	display: inline-block;
}
.person .info {
	display: inline-block;
	vertical-align: middle;
	padding: 14px;
}
.person + .post_code {
	display: inline-block;
    float: right;
    position: relative;
    top: 25px;
    right: 32px;
    margin: 0;
}
.post_code {
	display: inline-block;
}
.updatePost{
    float: right;
    position: relative;
    top: 53px;
}

.context {
	margin: 5px;
	padding: 5px;
}





</style>

</head>
<body bgcolor="white">




	<%@ include file="/front-end/nav.jsp"%>





	<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-0 col-md-1 box1"></div>
			<div class="col-12 col-md-8 box2">

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
									<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${member_infoVO.mem_id}"
						class="rounded-circle main">
										
									</div>
									<div>${member_infoVO.user_name}</div>
									
								</div>
								<div>
									<div>動態內容:</div>
									<div>
										<textarea style="width: 90%; height: 80px; resize:none"
											name="post_context" id="post_context" placeholder="輸入動態內容"></textarea>
									</div>
								</div>
								<div>
									<div>動態照片:</div>
									<div>
										<label class="btn btn-outline-secondary"><input type="file" name="photo" id="photo" accept="image/*"
											multiple="multiple" style='display:none' /><i class="fas fa-camera"></i>新增圖片</label>
									</div>
								</div>

							</div>

							<br> <input type="hidden" name="action"
								value="insertfromWall" > <input class="btn btn-outline-secondary" type="submit"
								name="addpost" value="送出新增">
						</form>
					</article>
				</div>
				<!-- 尾巴 -->

				<table>
					<c:forEach var="postVO" items="${list}">

						<%
							PostVO postVO = (PostVO) pageContext.getAttribute("postVO");
							String post_code = postVO.getPost_uid();
							List<Post_PhotoVO> photoList = post_PhotoSvc.findByPostCode(post_code);
							pageContext.setAttribute("photoList", photoList);
							List<Post_CommentVO> cmList = post_CmSvc.findByPost_Code(post_code);
							pageContext.setAttribute("post_code", post_code);
							pageContext.setAttribute("cmList", cmList);
							friend_MemVO = postSvc.getMemberByPostCode(post_code);
							pageContext.setAttribute("friend_MemVO", friend_MemVO);

							List<String> likePostMembers = likePostSvc.getLikeMembersByPostcode(post_code);
							pageContext.setAttribute("likePostMembers", likePostMembers);

							if (likePostMembers.isEmpty()) {
								pageContext.setAttribute("heart", 0);
							} else if (likePostMembers.contains(member_infoVO.getMem_id())) {
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
														<%
															Member_InfoVO memVO = (Member_InfoVO) pageContext.getAttribute("friend_MemVO");
														%>
														<a	href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=memVO.getMem_id()%>">
															
															<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=<%=memVO.getMem_id()%>"
						class="rounded-circle main">
						
														</a>
													</div>
													<div class="info">
														<div style="font-size: 24px;">${friend_MemVO.user_name}</div>
														<div style="font-size: 12px;">
															<fmt:formatDate value="${postVO.edit_date}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
												</div>

												<!-- 動態點讚 -->
												<div id="${post_code}" class="post_code">

													<input type="hidden" id="post_code" value="${post_code}">
													<input type="hidden" id="lk_memid" value="${mem_id}">
													<c:if test="${heart == 0}">
														<i class="far fa-heart" id="likepost"></i>
														<p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
													</c:if>
													<c:if test="${heart == 1}">
														<i class="fas fa-heart" id="likepost"></i>
														<p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
													</c:if>

												</div>
												<!-- 編輯個人動態											 -->
												<c:if test="${friend_MemVO.mem_id eq mem_id}">

													<div class="updatePost">
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
											name="action" value="getOne_For_Update_From_PostWall">
									</form>
									<div class="line">
										<hr width="100%">
									</div> <!-- 回覆	-->
									<div class="info">
										<c:forEach var="post_CommentVO" items="${cmList}">
											<% 
												Post_CommentVO post_CommentVO = (Post_CommentVO) pageContext.getAttribute("post_CommentVO");	
												Member_InfoVO comment_MemVO = member_infoSvc.getOneM_Info(post_CommentVO.getCmnt_memid());
												pageContext.setAttribute("comment_MemVO", comment_MemVO);
											%>

											<div class="reply_size">
												<div class="poster">
													<div class="person">
														<div class="photo">
															<a href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=comment_MemVO.getMem_id()%>">
															<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=<%=comment_MemVO.getMem_id()%>"
						class="rounded-circle second">
															</a>
														</div>
														<div style="font-size: 18px;"><%=comment_MemVO.getUser_name()%></div>
														<div style="font-size: 12px;"><fmt:formatDate value="${post_CommentVO.cmnt_date}"
																pattern="yyyy-MM-dd HH:mm:ss" /></div>

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
											<img id="gs_big_pic_img" src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${member_infoVO.mem_id}"
						class="rounded-circle second">
											</div>
											<div style="font-size: 18px; align: top;"><%=member_infoVO.getUser_name()%></div>
										</div>

										<div class="reply_context">
											<div>
												<textarea style="width: 90%; height: 80px; resize:none"
													name="cmnt_context" placeholder="動態內容"></textarea>
											</div>
										</div>

										<input type="hidden" name="action" value="addComment">
										<input class="btn btn-outline-secondary" type="submit" value="送出">
									</form>

								</li>
							</ul>
						</article>

					</c:forEach>

					<c:if test="${empty list}">
						<div>
							<article class="paper">
								<p>歡迎使用Tape，快到配對認識新朋友一起參加揪團活動吧</p>
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
// 		alert("hi");
	
		  
		  $("#js-startbtn").on("click",function(){
			 console.log($(this))
		    $("div.block").removeClass("hide");
		  });
		  
		  $(".js-modal-close").on("click",function(){
			  $("div.block").addClass("hide");
		  });
		});
		
//編輯貼文	 
	$(".button").on("click",function(){
		
	    $(this).next("div.block").removeAttr('style');
	  });
	  
	  $(".js-modal-close").on("click",function(){
		$(this).parent().parent("div.block").css('display','none');
	  });	
	
	
	
//點讚
	$(function(){
		
		$("i#likepost").click(function(){
			$(this).toggleClass("far fa-heart");
			$(this).toggleClass("fas fa-heart");
		})
		
// 		$("i#likepost").click(function(){
			
// 		})
	
	})




// //貼文點讚ajax
// 	$("i#likepost").click(function(event){
// 		let that = this;
// 		let post_code = $(this).parent().children("input#post_code").val();
// // 		console.log(post_code)
	

// // 		console.log($(that).parent().children("p#lk_num"));
// // 		console.log($(that).parent().children("p#lk_num"));

// // 		console.log(this.className)
// 		let likePostObj = {
// 				"action":"update_post_like",		
// 				"post_code" : post_code,
// 				"css":this.className
// 			}
		

 
 
 
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
// 					$(that).parent().children("p#lk_num").prev().html("<font color='red' size='4'>"+parseInt(result.likeSum)+"</font>");				
				
			},
			error : function(err) {
			}
			
		});//AJAX-結束
	 


	 
 })
 
		
// 		var reader = new FileReader();
//  		reader.readAsDataURL(this.files[0]);
// 		var file = event.target.files[0];
// 		reader.readAsDataURL(file);
// 	reader.addEventListener("load",function(e){
			
//             //觸發就清空img的src
// 			$("#gs_big_pic_img").attr("src","");
			
//  			let obj = new FormData($("#bigpic_form")[0]);
// 			console.log(obj)
// 			console.log($("bigpic_form")[0])
// 			obj.append("action","update_bigpic");
// 			$.ajax({
<%-- 				url:"<%=request.getContextPath()%>/back-end/post/post",	 --%>
// 				type:"POST",
// 				data:obj,
// 				contentType:false,
// 				processData:false,
// 				cache:false,
				
// 				success:function(){
												
// 					$("#gs_big_pic_img").attr("src", e.target.result);
// 				},
// 				error:function(err){
// 					alert("大頭照更新錯誤");
// 				}
// 			})
// 		})
	





//新增貼文送出資料後，直接顯示於頁面上		
// 	$(function(){
// 	$("input[name='addpost']").click(function(){
// 			alert("hello");//有這一行代表 最外層函數可以動
		
// 		let that = this;
// // 		let post_code = $(this).parent().children("input#post_code").val();//確認隱藏的input的報名表單編號有沒有抓到
//  		let post_public_lv = $("form#addform select#post_context").val();
//  		let post_context = $("form#addform textarea#post_context").val();
		
// 		//準備送去後端的資料
// 		let test = {
// 			"action":"insertfromWall",
			

// 		}
		
// 		$.ajax({
<%-- 			url:"<%=request.getContextPath()%>/back-end/post/post", --%>
// 			type : "POST",
// 			data : test,
// 			dataType:"JSON",
// 			success : function(result) {
			
// 			},
// 			error : function(err) {
// 			}
			
// 		});//AJAX-結束
		
// 	})
		
  
		
		
		
</script>





	<script>
	
// function add_comment(){
// 	let comment
// 	$(this).("div.info").append(`<div class="reply_size">
// 			<div class="poster">
// 			<div class="person">
// 				<div class="photo">

<%-- 					<% --%>
// 						String photo3String = null;
// 								try {
// 									Base64.Encoder encoder = Base64.getEncoder();
// 									photo3String = encoder.encodeToString(member_infoVO.getHeadshot());
// 								} catch (Exception e) {
// 									e.printStackTrace();
// 								}
<%-- 					%> --%>
<%-- 					<img src="data:image;base64,<%=photo3String%>" --%>
// 						class="rounded-circle second">
// 				</div>
<%-- 				<div style="font-size: 18px;"><%=member_infoVO.getUser_name()%></div> --%>
// 				<div style="font-size: 12px;">${post_CommentVO.cmnt_date}</div>
// 				<div>
// 					<i class="far fa-heart">${post_CommentVO.lk_count}</i>
// 				</div>
// 			</div>
// 		</div>
// 	</div>

// 	<div class="reply_context">
// 		<p>${post_CommentVO.cmnt_context}</p>
// 	</div>
// 	<div class="line">
// 		<hr width="100%">
// 	</div>`
// 			)
	
	
// }
	
	
	
	
	
	
	
	
	
	
	
	
// $(function(){
// 	$("input[name='like']").click(function(){
// 			alert("hello");//有這一行代表 最外層函數可以動
		
// 		let that = this;
// 		let post_code = $(this).parent().children("input#post_code").val();//確認隱藏的input的報名表單編號有沒有抓到
 

		
// 		//準備送去後端的資料
// 		let test = {
// 			"action":"test",

// 		}
		
// 		$.ajax({
<%-- 			url:"<%=request.getContextPath()%>/back-end/post/post", --%>
// 			type : "POST",
// 			data : test,
// 			dataType:"JSON",
// 			success : function(result) {
			
// 			},
// 			error : function(err) {
// 			}
			
// 		});//AJAX-結束
		
// 	})
	
	
// })



// $(function(){
// 	alert("hello");
// 	let that = this;
// 	let post_code = $(this).parent().children("input#post_code").val();
// 	console.log(post_code);
// 	$.ajax({
<%-- 		url:"<%=request.getContextPath()%>/back-end/post/post", --%>
// 		type : "POST",
// 		data : post_like_num,
// 		dataType:"JSON",
// 		success : function(result) {
// 			if(result.del=="removecss"){
// 				$(that).parent().children("i").removeClass("fas fa-heart").addClass("far fa-heart");
// 			}else if(result.add=="addcss"){
// 				$(that).parent().children("i").removeClass("far fa-heart").addClass("fas fa-heart");

// 			}
// 		},
// 		error : function(err) {
// 		}
		
// 	});//AJAX-結束
	
	
	
// })



</script>

</body>
</html>