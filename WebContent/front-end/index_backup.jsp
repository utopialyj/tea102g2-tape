<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	List<Member_InfoVO> friends = (List<Member_InfoVO>) session.getAttribute("friends");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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



</style>






</head>
<body>






	<%@ include file="/front-end/nav.jsp"%>








	<div class="container">

		<div class="row">
			<div class="col-3 col-md-2">col-3 col-md-2</div>
			<div class="col-9 col-md-8">
				Hi ${member_infoVO.user_name}, this page is for test.<br>
				<hr>
				Friends:<br>
				<c:forEach var="friend" items="${friends}">
			${friend.mem_id}<br>
				</c:forEach>

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