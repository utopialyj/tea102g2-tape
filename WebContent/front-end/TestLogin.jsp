<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoService memberSvc = new Member_InfoService();
	List<Member_InfoVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<title>Login for Test</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<table>
		<tr>
			<th>mem_id</th>
			<th>select</th>
		</tr>
		<c:forEach var="member_infoVO" items="${list}">
			<tr>
				<td>${member_infoVO.mem_id}</td>
				<td>
					<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/Member_InfoServletForTest">
						<input type="hidden" name="mem_id" value="${member_infoVO.mem_id}">
						<input type="submit" value="select">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

  <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="https://kit.fontawesome.com/e66ce32cfd.js" crossorigin="anonymous"></script>
</body>
</html>