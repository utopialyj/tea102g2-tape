<%@page import="com.member_info.model.Member_InfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Map"%>

<%
	Member_InfoVO member_infoVO = new Member_InfoVO();
	Map<String, String> map = (Map) session.getAttribute("user_name");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Tape | Admin</title>
<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/css/sb-admin-2.min.css"
	rel="stylesheet">


<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"> -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/vendor/bootstrap/icons-main/font/bootstrap-icons.css">
<style>
table#table-1 {
	width: 450px;
	background-color: gainsboro;
	margin-top: 5px;
	margin-bottom: 10px;
	/* 	border: 3px ridge Gray; */
	height: 80px;
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
</head>
<body id="page-top">
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<table id="table-1">
					<tr>
						<td><h3>會員管理</h3> <!-- 							<h4>( MVC )</h4></td> -->
					</tr>
				</table>

				<!-- 				<p>This is the Home page for Member Info: Home</p> -->
				<h3>資料查詢:</h3>
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
				<ul>
					<li><a href='listAllMem.jsp'>List</a> all members. <br> <br></li>


					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/Member_InfoServlet">
							<b>輸入會員編號 (如1):</b> <input type="text" name="mem_id"> <input
								type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="送出">
						</FORM>
					</li>

					<jsp:useBean id="memSvc" scope="page"
						class="com.member_info.model.Member_InfoService" />

					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/Member_InfoServlet">
							<b>選擇會員編號:</b> <select size="1" name="mem_id">
								<c:forEach var="member_infoVO" items="${memSvc.all}">
									<option value="${member_infoVO.mem_id}">${member_infoVO.mem_id}
								</c:forEach>
							</select> <input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="送出">
						</FORM>
					</li>

					<li><a
						href="<%=request.getContextPath()%>/front-end/member_info/u_paswd.jsp">密碼更改</a>

					</li>

					<%--<li>
      <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_AuthroityServlet" >
       <b>選擇員工姓名:</b>
       <select size="1" name="verify_level">
         <c:forEach var="member_authroityVO" items="${authSvc.all}" > 
          <option value="${member_authroityVO.verify_level}">${member_authroityVO.verify_level}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li> --%>

				</ul>


				<h3>新增會員:</h3>

				<ul>
					<li><a
						href='<%=request.getContextPath()%>/front-end/member_info/addMember.jsp'>Add</a>
						a new member.</li>
				</ul>
			</div>
		</div>
	</div>


	<%@ include file="../footer.jsp"%>
</body>
</html>