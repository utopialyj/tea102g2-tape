<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_authroity.model.*"%>
<%
Member_AuthroityService authroitySvc = new Member_AuthroityService();
//因為要使用EmpService()，所以要先宣告該類別物件
    List<Member_AuthroityVO> list = authroitySvc.getAll();
//用上面的物件呼叫getAll()取得所有值
    pageContext.setAttribute("list",list);
//pageContext是該頁面的內容，這裏用上面取得的資料設定頁面的屬性
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有權限資料</title>

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
		 <h3>所有員工資料 - listAllAuth.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/member_authroity/auth_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}"> <!-- if條件式，參考：p.252 -->

 	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<table>
	<tr>
		<th>權限等級</th>
		<th>聊天權限</th>
		<th>動態權限</th>
		<th>配對權限</th>
		<th>點數權限</th>
		<th>參與揪團權限</th>
		<th>舉辦揪團權限</th>
		<th>登入權限</th>
	    <th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="member_authroityVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
<!-- 		pageIndex跟rowsperpage在page.file1裡面 -->
		<tr>
			<td>${member_authroityVO.verify_level}</td>
			<td>${member_authroityVO.chat_auth}</td>
			<td>${member_authroityVO.post_auth}</td>
			<td>${member_authroityVO.meat_auth}</td>
			<td>${member_authroityVO.point_auth}</td>
			<td>${member_authroityVO.join_event_auth}</td> 
			<td>${member_authroityVO.host_auth}</td>
			<td>${member_authroityVO.log_auth}</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member_AuthroityServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="verify_level"  value="${member_authroityVO.verify_level}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member_AuthroityServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="verify_level"  value="${member_authroityVO.verify_level}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>