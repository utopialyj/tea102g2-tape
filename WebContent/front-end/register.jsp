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
	height: 500px;
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
					<form action="<%=request.getContextPath()%>/Member_InfoServlet"
						method="post">
						<h1 class="h3 mb-3 fw-normal">Welcome</h1>
						<div class="form-group">
							<label for="InputEmail1">Email address</label> <input
								type="email" class="form-control" id="InputEmail1"
								name="m_email">
						</div>
						<div class="form-group">
							<label for="InputPassword1">Password</label> <input
								type="password" class="form-control" id="InputPassword1"
								name="m_paswd">
						</div>
						<div class="form-group">
							<label for="InputPassword1">Password Confirm</label> <input
								type="password" class="form-control" id="InputPassword2"
								name="m_paswd_2">
						</div>
						<div class="in_div_errorMsgs" style="color: red">${errorMsg}</div>

						<input type="hidden" name="action" value="register">
						<button class="w-40 btn btn-outline-success" type="submit">Register</button>
					</form>
				</div>
				<p class="mt-5 mb-3 text-muted">&copy; 2020</p>
			</div>
		</div>
	</div>




	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
</body>
</html>