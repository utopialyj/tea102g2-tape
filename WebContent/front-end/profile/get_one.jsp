<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%@ page import="com.post_comment.model.*"%>
<%@ page import="com.like_post.model.Like_PostService"%>
<!DOCTYPE html>

<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	pageContext.setAttribute("mem_id", member_infoVO.getMem_id());
	Member_InfoService member_infoSvc = new Member_InfoService();
	//動態
	PostService postSvc = new PostService();
	String post_code = (String) request.getParameter("post_code");
	PostVO postVO = postSvc.getOnePost(post_code);
	pageContext.setAttribute("post_code", post_code);
	pageContext.setAttribute("postVO", postVO);

	//圖片	
	Post_PhotoService post_PhotoSvc = new Post_PhotoService();
	List<Post_PhotoVO> photoList = post_PhotoSvc.findByPostCode(post_code);
	pageContext.setAttribute("photoList", photoList);


	Like_PostService likePostSvc = new Like_PostService();
%>
<html>
<head>
<title>Get One Post</title>

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

</head>
<body bgcolor="white">

	<%@ include file="/front-end/nav.jsp"%>

	<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-2 box1">
			</div>
			<div class="col-8 box2">
			<div class="block hide">

 
				
						
						<article class="paper">
							<ul>
								<li class="p">
								<div class="poster_size">
										<div class="poster">
											<div class="person">
												<div class="photo">
<!--動態內容-->
													<%
														String post_mem = postVO.getPost_memid();
														Member_InfoVO memVO = member_infoSvc.getOneM_Info(post_mem);
														
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
													<a href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=memVO.getMem_id()%>">
													<%
														byte[] photoByte = null;
															String photoString = null;
															try {
																
								
																photoByte = memVO.getHeadshot();

																
																Base64.Encoder encoder = Base64.getEncoder();
																photoString = encoder.encodeToString(photoByte);

															} catch (Exception e) {
																e.printStackTrace();
															}
													%>
														<img src="data:image;base64,<%=photoString%>"
														class="rounded-circle main">
													</a>
												</div>
												<div class="info">
													<div style="font-size: 24px;"><%=memVO.getUser_name()%></div>
													<div style="font-size: 12px;">
														<fmt:formatDate value="${postVO.edit_date}"
															pattern="yyyy-MM-dd HH:mm:ss" />
													</div>
												</div>
											</div>

<!-- 動態點讚 -->
											<% pageContext.getAttribute("post_code");%>
											<div id="${post_code}" class="post_code">
											
												<input type="hidden" id="post_code" value="${post_code}">
												<input type="hidden" id="lk_memid" value="${mem_id}">
											
								
												<c:if test="${heart == 0}">
													<i class="far fa-heart" id="likepost"></i><p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
												</c:if>
												<c:if test="${heart == 1}">
													<i class="fas fa-heart" id="likepost"></i><p id="${post_code}" class="post_code">${likePostMembers.size()}</p>
												</c:if>

											</div>
<!-- 編輯個人動態-->
<!-- 比對貼文主人與登入者(注意!!) -->
											<c:if test="${friend_MemVO.mem_id eq mem_id}">
													
													<div><input type="submit" value="修改"></div>
											</c:if>
										</div>
										<div class="context">
											<p>${postVO.post_context}</p>
										</div>
<!-- 動態照片-->
										<div>
											<c:forEach var="post_PhotoVO" items="${photoList}">

												<%
													byte[] photo2Byte = null;
															String photo2String = null;
															try {
																Post_PhotoVO post_PhotoVO = (Post_PhotoVO) pageContext.getAttribute("post_PhotoVO");
																photo2Byte = post_PhotoVO.getPhoto();

																//photoByte = (byte[])pageContext.getAttribute("photo"); 
																Base64.Encoder encoder = Base64.getEncoder();
																photo2String = encoder.encodeToString(photo2Byte);

															} catch (Exception e) {
																e.printStackTrace();
															}
												%>

												<img src="data:image;base64,<%=photo2String%>">
												<br>
											</c:forEach>
										</div>
<!-- 回覆-->
										<div>
											<div class="line">
												<hr width="100%">
											</div>
											<div class="info">
											<%
												Post_CommentService post_CmntSvc = new Post_CommentService();
												List<Post_CommentVO> cmList = post_CmntSvc.findByPost_Code(post_code);
												pageContext.setAttribute("cmList", cmList);
							
											%>
											<c:forEach var="post_CommentVO" items="${cmList}">
											<%
												
												Post_CommentVO post_CommentVO = (Post_CommentVO)pageContext.getAttribute("post_CommentVO");
											    Member_InfoVO  com_memberVO = member_infoSvc.getOneM_Info(post_CommentVO.getCmnt_memid()); 
											%>
												<div class="reply_size">
													<div class="poster">
														<div class="person">
															<div class="photo">
																<a href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=com_memberVO.getMem_id()%>">
																	<%
																		String photo3String = null;
																				try {
																					Base64.Encoder encoder = Base64.getEncoder();
																					photo3String = encoder.encodeToString(com_memberVO.getHeadshot());
																				} catch (Exception e) {
																					e.printStackTrace();
																				}
																	%> <img src="data:image;base64,<%=photo3String%>" class="rounded-circle second">
																</a>
															</div>
															<div style="font-size: 18px;"><%=com_memberVO.getUser_name()%></div>
															<div style="font-size: 12px;">${post_CommentVO.cmnt_date}</div>
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
										</div> 
<!-- 新增回覆-->
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
														href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=member_infoVO.getMem_id()%>">
														<%
															String photo4String = null;
																try {
																	Base64.Encoder encoder = Base64.getEncoder();
																	photo4String = encoder.encodeToString(member_infoVO.getHeadshot());
																} catch (Exception e) {
																	e.printStackTrace();
																}
														%> <img src="data:image;base64,<%=photo4String%>"
														class="rounded-circle second">
													</a>
												</div>
												<div style="font-size: 18px; align: top;"><%=member_infoVO.getUser_name()%></div>
											</div>

											<div class="reply_context">
												<div>
													<textarea style="width: 400px; height: 80px;"
														name="cmnt_context" placeholder="回覆內容"></textarea>
												</div>
												<div>
	
													<input type="hidden" name="action"
														value="addCommentFromProfile"> <input type="submit"
														value="送出">
												</div>
											</div>
										</form>
									
										
										</div>
										</div>
										</div>
									</div>
								</li>
							</ul>
						</article>

			</div>
					
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

// 更換大頭照ajax	
	
	$("#gs_big_pic").on("change",function(event){
		var reader = new FileReader();
 		reader.readAsDataURL(this.files[0]);

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
		dataType : "JSON",

		success : function(result) {

			that.html(parseInt(result.likeSum));
			//$(that).parent().children("p#lk_num").prev().html("<font color='red' size='4'>"+parseInt(result.likeSum)+"</font>");				

		},
		error : function(err) {
		}

	});//AJAX-結束

	})
</script>



</body>
</html>