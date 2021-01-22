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
<title>Tape | Login</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<style>
.bg {
	border: 1px solid black;
	width: 100%;
	height: 600px;
	visibility: hidden;
	z-index: 1;
	display: none;
}

.fixed {
	/* position: fixed; */
	z-index: 2;
}

.logo {
	width: 350px;
	margin: auto;
}

.logo img {
	max-width: 100%;
}

.form {
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

form div.logosi {
	width: 150px;
	height: 100px;
	margin: auto;
	visibility: hidden;
}

form img {
	width: 85%;
	height: auto;
	position: relative;
	top: 23px;
}

.activity {
	/* border: 1px solid blue; */
	width: 200px;
	height: 270px;
	margin: 10px;
}

.photoframe1 {
	width: 200px;
	height: 250px;
	position: relative;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	transform: rotate(30deg);
}

.photoframe2 {
	width: 200px;
	height: 250px;
	position: relative;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	transform: rotate(-20deg);
}

.photoframe3 {
	width: 200px;
	height: 250px;
	position: relative;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	transform: rotate(5deg);
}

.photo {
	position: relative;
	width: 180px;
	height: 180px;
}

div.photo img {
	width: 100%;
	position: relative;
	margin: 10px;
	clip: rect(10px, 170px, 170px, 10px);
}

p.title {
	position: relative;
}

.coupon {
	/* border: 1px solid red; */
	width: 150px;
	height: 150px;
	margin: 10px;
}

.coupon img {
	max-width: 100%;
	border-radius: 50%;
}

footer {
	border: 1px solid green;
}

@media ( max-width : 767px) {
	form div.logosi {
		visibility: visible;
	}
}
</style>
</head>
<body>
	<div class="container text-center">
		<div class="row bg">
			<div class="col-md-12"></div>
		</div>
		<div class="row align-items-center fixed">
			<div class="col col-md-6 d-none d-md-block">
				<div class="logo">
					<img src="<%=request.getContextPath()%>/front-end/images/tape.png" alt="logo">
				</div>
			</div>
			<div class="col col-md-6">
				<div class="form">
					<form action="<%=request.getContextPath()%>/Member_InfoServlet"
						method="post">
						<div class="logosi">
							<img src="images/tape.png">
						</div>
						<h1 class="h3 mb-3 fw-normal">Welcome</h1>
						<label for="inputEmail" class="visually-hidden">Account</label> <input
							type="email" id="inputEmail" class="form-control"
							placeholder="Email address" required autofocus name="m_email"
							value="<%=(member_InfoVO == null) ? "" : member_InfoVO.getM_email()%>">
						<div class="in_div_errorMsgs" style="color: red">${errorMsgs.account}</div>
						<label for="inputPassword" class="visually-hidden">Password</label>
						<input type="password" id="inputPassword" class="form-control"
							placeholder="Password" required name="m_paswd">
						<div class="in_div_errorMsgs" style="color: red">${errorMsgs.pwd}</div>
						<div class="in_div_errorMsgs" style="color: red">${errorMsgs.account_error}</div>
						<div>
							<label> <a href="<%=request.getContextPath()%>/front-end/member_info/forgetPaswd.jsp" value="forget">
								Forget Password
							</label>
						</div>
						<input type="hidden" name="action" value="loginhandler">
						<button class="w-40 btn btn-info" type="submit">Sign
							in</button>
						<button class="w-40 btn btn-outline-info" id="register">Register</button>
						<hr>
						<button class="w-40 btn btn-outline-success btn-sm" id="guest">Be a guest!</button>						
						<p class="mt-5 mb-3 text-muted fixed-bottom">&copy; Tape 2021</p>
					</form>
				</div>
			</div>
		</div>
		<!--         <div class="row align-items-center"> -->
		<!--             <div class="col-md-3"> -->
		<!--                 <div class="activity mx-auto offset-md-1"> -->
		<!--                     <div class="photoframe1"> -->
		<!--                         <div class="photo"> -->
		<!--                             <img src="test.jpg" alt=""> -->
		<!--                             <p class="title">title</p> -->
		<!--                         </div>         -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--             <div class="col-md-3 offset-md-3"> -->
		<!--                 <div class="coupon mx-auto"> -->
		<!--                     <div class="couponframe"> -->
		<!--                         <img src="coupon.jpg"> -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--         </div> -->
		<!--         <div class="row align-items-center"> -->
		<!--             <div class="col-md-3 offset-md-4"> -->
		<!--                 <div class="coupon mx-auto"> -->
		<!--                     <div class="couponframe"> -->
		<!--                         <img src="coupon.jpg"> -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--             <div class="col-md-3 offset-md-1"> -->
		<!--                 <div class="activity mx-auto"> -->
		<!--                     <div class="photoframe2"> -->
		<!--                         <div class="photo"> -->
		<!--                             <img src="test.jpg" alt=""> -->
		<!--                             <p class="title">title</p> -->
		<!--                         </div>         -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--         </div> -->
		<!--         <div class="row align-items-center"> -->
		<!--             <div class="col-md-3 offset-md-2"> -->
		<!--                 <div class="activity mx-auto"> -->
		<!--                     <div class="photoframe3"> -->
		<!--                         <div class="photo"> -->
		<!--                             <img src="test.jpg" alt=""> -->
		<!--                             <p class="title">title</p> -->
		<!--                         </div>         -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--             <div class="col-md-3 offset-md-4"> -->
		<!--                 <div class="coupon mx-auto"> -->
		<!--                     <div class="couponframe"> -->
		<!--                         <img src="coupon.jpg"> -->
		<!--                     </div> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--         </div> -->
		<!--         <div class="row"> -->
		<!--             <div class="col"> -->
		<!--                 <footer> -->
		<!--                     footer -->
		<!--                 </footer> -->
		<!--             </div> -->
		<!--         </div> -->
	</div>

	<!-- Register Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Register</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="<%=request.getContextPath()%>/Member_InfoServlet"
						type="POST">
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
						<input type="hidden" name="action" value="register">
						<button type="submit" class="btn btn-primary">Register</button>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>


	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	<script>
		document.getElementById("register").onclick = function() {
			window.location.href = "member_info/addMember.jsp";
		}
		document.getElementById("guest").onclick = function() {
			window.location.href = "acts/cardHomePage.jsp";
		}
	</script>
</body>
</html>