<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member_authroity.model.*"%>    
    
<%
  Member_AuthroityVO member_authroityVO = (Member_AuthroityVO) request.getAttribute("member_authroityVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>列出一筆權限資料</title>

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
	width: 600px;
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
</head >
<body bgcolor='white'>
<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneAuth.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/member_authroity/auth_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>權限等級</th>
		<th>聊天權限</th>
		<th>動態權限</th>
		<th>配對權限</th>
		<th>點數權限</th>
		<th>參加揪團權限</th>
		<th>舉辦揪團權限</th>
		<th>登入權限權限</th>
		
	</tr>
	<tr>
		<td><%=member_authroityVO.getVerify_level()%></td>
		<td><%=member_authroityVO.getChat_auth()%></td>
		<td><%=member_authroityVO.getPost_auth()%></td>
		<td><%=member_authroityVO.getMeat_auth()%></td>
		<td><%=member_authroityVO.getPoint_auth()%></td>
		<td><%=member_authroityVO.getJoin_event_auth()%></td>
		<td><%=member_authroityVO.getHost_auth()%></td>
		<td><%=member_authroityVO.getLog_auth()%></td>
	</tr>
</table>

</body>
</html>