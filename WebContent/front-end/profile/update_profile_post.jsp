<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%
	PostVO postVO = (PostVO) request.getAttribute("postVO");
	Post_PhotoService post_PhotoSvc = new Post_PhotoService();
	List<Post_PhotoVO> photoList = post_PhotoSvc.findByPostCode(postVO.getPost_uid());
	pageContext.setAttribute("photoList", photoList);
%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>個人動態資料修改 -update_post_input.jsp</title>
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

table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

table {
	border-collapse: collapse;
	border: 1px solid black;
}

td {
	border: 1px solid black;
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
				<h3>編輯貼文:</h3>


				<article class="paper">
					<form method="post"
						action="<%=request.getContextPath()%>/back-end/post/post"
						name="form1" enctype="multipart/form-data">

						<div>
<!-- 							<div> -->
<!-- 								<div>公開程度:</div> -->
<!-- 								<div> -->
<!-- 									<select name="post_public_lv"> -->
<!-- 										<option value=1>公開</option> -->
<!-- 										<option value=2>只限好友</option> -->
<!-- 										<option value=3>只限本人</option> -->
<!-- 									</select> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div>
								<div>動態內容:</div>
								<p> </p>
								<div>
									<input type="text" name="post_context" size="45"
										value="<%=postVO.getPost_context()%>" />
								</div>
							</div>
							<p> </p>
							<div>
								<div>新增動態照片:</div>
								<p> </p>
								<div>
									<input type="file" name="photo" id="photo" accept="image/*"
										multiple="multiple" />
								</div>
							</div>
							<p> </p>
<!-- 							<div> -->
<!-- 								<div>動態地點:</div> -->
<!-- 								<div> -->
<!-- 									<input type="text" name="post_location" size="45" -->
<%-- 										value="<%=postVO.getPost_location()%>" /> --%>
<!-- 								</div> -->
<!-- 							</div> -->
							<div>
		<br> <input type="hidden" name="action"
			value="update_profile_post"> <input type="hidden"
			name="post_uid" value="<%=postVO.getPost_uid()%>"> <input
			type="submit" value="送出修改">
		</div>
						</div>
					</form>
					<div class="line">
								<hr width="100%">
							</div>
	<c:forEach var="post_PhotoVO" items="${photoList}">
						<div>
							
							<div>
								<%
									byte[] photoByte = null;
										String photoString = null;
										try {
											Post_PhotoVO post_PhotoVO = (Post_PhotoVO) pageContext.getAttribute("post_PhotoVO");
											photoByte = post_PhotoVO.getPhoto();

											Base64.Encoder encoder = Base64.getEncoder();
											photoString = encoder.encodeToString(photoByte);
										} catch (Exception e) {
											e.printStackTrace();
										}
								%>
								<img src="data:image;base64,<%=photoString%>" style="max-width:100%">
							</div>
							</div>
							<div>
				<form method="post"
					action="<%=request.getContextPath()%>/back-end/post/post"
					style="margin-bottom: 0px;">
					<input type="submit" value="刪除"> <input type="hidden"
						name="photo_uid" value="${post_PhotoVO.photo_uid}"> <input
						type="hidden" name="action" value="photo_delete">
				</form>
	
						</div>
					</c:forEach>
				</article>
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
</body>
</html>