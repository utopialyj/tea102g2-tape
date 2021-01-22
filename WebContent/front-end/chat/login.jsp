<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<meta charset="UTF-8">
<title>Login for Test</title>
</head>
<body>
	<table>
		<tr>
			<th>mem_id</th>
			<th>friend</th>
			<th>activity</th>
		</tr>
		<c:forEach var="member_infoVO" items="${list}">
			<tr>
				<td>${member_infoVO.mem_id}</td>
				<td>
					<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/MemberChat">
						<input type="hidden" name="mem_id" value="${member_infoVO.mem_id}">
						<input type="hidden" name="chatType" value="friend">
						<input type="submit" value="select">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/MemberChat">
						<input type="hidden" name="mem_id" value="${member_infoVO.mem_id}">
						<input type="hidden" name="chatType" value="activity">
						<input type="submit" value="select">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>