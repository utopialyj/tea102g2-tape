<%@ page  contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.black_list.model.*"%>

<%
Black_ListVO black_listVO = (Black_ListVO) request.getAttribute("black_listVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>黑名單資料 - listOneBlack_List.jsp</title>

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

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>黑名單資料 - ListOneBlack_List.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/pic.jpg" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>黑單編號</th>
		<th>會員編號</th>
		<th>被黑會員</th>
	
	</tr>
	<tr>
		<td><%=black_listVO.getBlk_uid()%></td>
		<td><%=black_listVO.getBlk_memid()%></td>
		<td><%=black_listVO.getBeblk_memid()%></td>
	
	</tr>
</table>

</body>
</html>