<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.actapl.model.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  ActAplVO actAplVO = (ActAplVO) request.getAttribute("actAplVO"); //ActAplServlet.java(Concroller), 存入req的ActAplVO物件
%>

<html>
<head>
<title>報名清單資料 - listOneActApl.jsp</title>

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
		 <h3>員工資料 - ListOneRnk.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>AplUID</th>
		<th>會員編號</th>
		<th>活動編號</th>
		<th>報名理由</th>
		<th>評語文字</th>
		<th>報名狀態(同意、不同意、下架)</th>
	</tr>
	<tr>
		<td><%=actAplVO.getApluid()%></td>
		<td><%=actAplVO.getMemid()%></td>
		<td><%=actAplVO.getActid()%></td>
		<td><%=actAplVO.getRson()%></td>
		<td><%=actAplVO.getSts()%></td>
			
	</tr>
</table>

</body>
</html>