<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.post.model.*"%>


<%
	PostVO postVO = (PostVO) request.getAttribute("postVO");
%>
<%=postVO == null%>
--${postVO.lk_num}--
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>動態新增 -addPost.jsp</title>
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
</style>
</head>
<%@ include file="/front-end/nav.jsp"%>
<body bgcolor="white">

	<table id="table-1">
		<tr>
			<td>
				<h3>動態新增-addPost.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>
	<h3>資料新增:</h3>
	<!-- 錯誤列表 -->
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<form method="post"
		action="<%=request.getContextPath()%>/back-end/post/post" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>會員編號:</td>
				<td><input type="text" name="post_memid" size="45"
					value="<%=(postVO == null) ? "MEM0000001" : postVO.getPost_memid()%>" /></td>
			</tr>
			<tr>
				<td>動態狀態:</td>
				<td><select name="post_sts">
						<option value=1>已發布</option>
						<option value=2>已刪除</option>
						<option value=3>被檢舉，處理中</option>
				</select></td>
			</tr>
			<tr>
				<td>公開程度:</td>
				<td><select name="post_public_lv">
						<option value=1>公開</option>
						<option value=2>只限好友</option>
						<option value=3>只限本人</option>
				</select></td>
			</tr>

			<%
				java.sql.Timestamp edit_date = new java.sql.Timestamp(System.currentTimeMillis());
			%>

			<tr>
				<td>發文時間:</td>
				<td><input name="edit_date" id="f_date1" type="text"
					value="<fmt:formatDate value ="<%=edit_date%>" pattern = "yyyy-MM-dd HH:mm:ss"/>"></td>
			</tr>

			<tr>
				<td>動態內容:</td>
				<td><input type="text" name="post_context" size="45"
					value="<%=(postVO == null) ? "請輸入動態內容" : postVO.getPost_context()%>" /></td>
			</tr>
			<tr>
				<td>動態照片:</td>
				<td><input type="file" name="photo" id="photo" accept="image/*"
					multiple="multiple" /></td>
			</tr>
			<tr>
				<td>動態地點:</td>
				<td><input type="text" name="post_location" size="45"
					value="<%=(postVO == null) ? "請輸入地點" : postVO.getPost_location()%>" /></td>
			</tr>
			<tr>
				<td>動態讚數:</td>
				<td><input type="hidden" name="lk_num" value="0">0</td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</form>
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