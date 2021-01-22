<%-- <%@page import="com.acts.model.ActsVO"%> --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.acts.model.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  ActsVO actsVO = (ActsVO) request.getAttribute("actsVO"); //ActsServlet.java(Concroller), 存入req的actsVO物件
%>

<html>
<head>
<title>活動管理資料 - listOneEmp.jsp</title>

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
		 <h3>員工資料 - ListOneEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>
<%
DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

%>

<table>
	<tr>
		<th>活動編號</th>
		<th>主辦者編號</th>
		<th>上架狀態</th>
		<th>活動時間</th>
		<th>活動狀態</th>
		<th>活動主題</th>
		<th>活動類型</th>
		<th>活動文字內容</th>
		<th>活動照片</th>
		<th>人數</th>
		<th>地區編號</th>
		<th>熱度</th>
		<th>地點</th>
		<th>店家資訊</th>
		<th>預算</th>
		<th>參與所需點數</th>
		<th>檢舉狀態</th>
	</tr>
	<tr>
		<td><%=actsVO.getActid()%></td>
		<td><%=actsVO.getMemid()%></td>
		<td><%=actsVO.getShsts()%></td>
		<td><%=sdf.format(actsVO.getTime())%></td>
		<td><%=actsVO.getSts()%></td>
		<td><%=actsVO.getTitle()%></td>
		<td><%=actsVO.getType()%></td>
		<td><%=actsVO.getCont()%></td>
<%-- 		<td><%=actsVO.getPic()%></td> --%>
		<td><img src="data:image;base64,<%= (actsVO.getPic() != null) ? actsVO.getBase64Image() : "" %>" width="200" height="100"></td>
		<td><%=actsVO.getPeop()%></td>
		<td><%=actsVO.getAreacd()%></td>
		<td><%=actsVO.getHot()%></td>
		<td><%=actsVO.getLoc()%></td>
		<td><%=actsVO.getStore()%></td>
		<td><%=actsVO.getBgt()%></td>
		<td><%=actsVO.getPts()%></td>
		<td><%=actsVO.getRpsts()%></td>
		
			
	</tr>
</table>

</body>
</html>