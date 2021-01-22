<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO member_InfoVO = (Member_InfoVO) request.getAttribute("member_infoVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tape | Register</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<style>
.form {
	/* border: 1px solid black; */
	width: 360px;
	height: 250px;
	margin: 50px auto;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

form {
	width: 300px;
	height: 400px;
	margin: 50px auto;
}

form img {
	width: 100%;
	height: auto;
}
</style>
</head>
<body>
	<div class="container text-center">
		<div class="row">
			<div class="col-12">
				<div class="form">
					
						<h1 class="h3 mb-3 fw-normal">前往驗證信</h1>
						<div class="form-group">
							<label >驗證信已寄至您的信箱</label><br>
							<label >請前往收取驗證信</label><br>
							<label >並點擊登入連結</label> <br>
							<label >登入後即可取得使用權限</label> 
							 
							 
						</div>
						<a href="<%=request.getContextPath()%>/front-end/login.jsp"><button class="btn btn-primary">Home</button></a>
				<p class="mt-5 mb-3 text-muted">&copy; Tape 2021</p>
			</div>
		</div>
	</div>




	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
</body>
</html>