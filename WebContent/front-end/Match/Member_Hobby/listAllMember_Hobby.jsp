<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_hobby.model.*"%>

<%
	Member_HobbyService member_hobbySvc = new Member_HobbyService();
    List<Member_HobbyVO> list = member_hobbySvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<meta charset="UTF-8">
<title>所有會員興趣資料 - listAllMember_Hobby.jsp</title>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>
<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有黑名單資料 - listAllBlack_List.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/pic.jpg" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>


<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>會員興趣編號</th>
		<th>會員編號</th>
		<th>興趣</th>
		<th>修改</th>
		<th>刪除</th>
		
	</tr>
<%-- 	<%@ include file="page1.file" %>  --%>
	<c:forEach var="member_hobbyVO" items="${list}">
		
		<tr>
			<td>${member_hobbyVO.mem_hob_uid}</td>
			<td>${member_hobbyVO.hob_memid}</td>
			<td>${member_hobbyVO.hob_code}</td>
		
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Match/Member_Hobby/member_Hobby.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="mem_hob_uid"  value="${member_hobbyVO.mem_hob_uid}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Match/Member_Hobby/member_Hobby.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="mem_hob_uid"  value="${member_hobbyVO.mem_hob_uid}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>


</body>
</html>