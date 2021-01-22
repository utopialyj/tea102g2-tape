<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.hobby_list.model.*"%>
<%@ page import="java.util.*"%>

<%
	Hobby_ListService hobSvc = new Hobby_ListService();
	List<Hobby_ListVO> list = hobSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="<%=request.getContextPath()%>/front-end/Match/Member_Hobby/member_Hobby.do">
<c:forEach var="Hobby_ListVO" items="${list}">


	<input type="checkbox" name="hobby" value="${Hobby_ListVO.hob_code}">${Hobby_ListVO.hob}
	<br>
<%-- 	<input type="hidden" name="hob" value="${Hobby.hob_code}"> --%>
</c:forEach>
<input type="hidden" name="action"	value="recommendWithHob">
<input type="submit" value="送出過濾條件">
</form>
</body>
</html>