<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>lessthan2.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">
	
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


<style>
/* 按會員權限隱藏部分內容使用 */
.displayNone {
	display: none;
}
</style>	

</head>
<body>
	<%@ include file="/front-end/nav.jsp"%>
	
	<div class="container mt-3 ">
		<!-- Content here -->
		<div class="row">


			<div class="col-2 box1">
				<aside>
					<p></p>
					<a href="<%=request.getContextPath()%>/front-end/acts/addActs.jsp">
						<i class="far fa-calendar-plus" aria-hidden="true"></i> 發起活動
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">
						<i class="fa fa-laptop" aria-hidden="true"></i> 活動預覽
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_own_acts.jsp">
						<i class="fas fa-tasks" aria-hidden="true"></i> 活動管理
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_join_event.jsp">
						<i class="fas fa-sign-in-alt" aria-hidden="true"></i> 活動報名
					</a> <a
						href="<%=request.getContextPath()%>/front-end/acts/manage_self_col.jsp">
						<i class="fa fa-star-o" aria-hidden="true"></i> 活動收藏
					</a>
				</aside>
			</div>
			<!-- 左側 col-2 -->




			<!--    中央  col-8  -->
			<div class="col-8 box2">
<!-- 				<br> -->
				<table cellpadding='5' cellspacing='0' width='100%' height='20%' style='position:relative; top: 10%'>
					<tr bgcolor='peachpuff' align='center' valign='middle' height='20'>
						 <td>   
							     <h3><font color=red>${member_infoVO.user_name} </font>信箱尚未驗證，請至信箱點選連結進行驗證</h3>
						 </td>
<!-- 						 <td> -->
			<!-- 			 	<a href="#">前往驗證頁面</a>  -->
<!-- 						 	<br> -->
<!-- 						 	<br> -->
<%-- 						 	<a href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">回揪團首頁</a> --%>
<!-- 						 </td> -->
					</tr>
				</table>
<!-- 				<b> <br> -->
<!-- 				<br>                ..... -->
<!-- 				</b> -->
			</div>
		<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.js"></script>

	<script>
		
	<%@ include file="/front-end/js/notification.js" %>
		
	</script>
</body>
</html>