<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.acts.model.*"%>

<%
	ActsVO actsVO = (ActsVO) request.getAttribute("actsVO");
%>
<%= "---這裡是header---" %>
<%--=request.getContextPath()--%>
<%--=actsVO == null--%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- bootstrap 4.5.3 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<script src="https://kit.fontawesome.com/e66ce32cfd.js" crossorigin="anonymous"></script>

<style>
</style>
</head>

<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark "
		style="background-color: #3559be">
		<a class="navbar-brand" href="#"><i class="fas fa-tape"></i> Tape</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#"><i
						class="fas fa-home"></i> 首頁<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#"><i
						class="fas fa-hand-holding-heart"></i> 配對</a></li>
				<li class="nav-item"><a class="nav-link" href="#"><i
						class="fas fa-glass-cheers"></i> 揪團</a></li>
				<li class="nav-item"><a class="nav-link" href="#"><i
						class="fas fa-donate"></i> 點數</a></li>

				<form class="form-inline my-2 my-lg-0 text-right">
					<input class="form-control mr-sm-2" type="search"
						placeholder="Search" aria-label="Search" weight="100px">
					<button class="btn btn-outline-light" type="submit">
						<i class="fas fa-search"></i>
					</button>
				</form>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Peter </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="#">個人頁面</a> <a
							class="dropdown-item" href="#">設定</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">登出</a>
					</div></li>
			</ul>
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" href="#"><i
						class="far fa-comment-dots"></i></a></li>
				<li class="nav-item"><a class="nav-link" href="#"><i
						class="fas fa-bell"></i></a></li>
			</ul>
		</div>

	</nav>
	<!-- --------------------------------------------------------------------------------------------------------------- -->









	<script src="https://code.jquery.com/jquery-3.5.1.js"
		integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>



</body>

</html>