<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon_list.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Coupon_ListVO coupon_listVO = (Coupon_ListVO) request.getAttribute("coupon_listVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>優惠券序號資料 - listOneCoupon_List.jsp</title>

<style>
  table#table-1 {
	background-color: lightgrey;
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
	width: 100%;
	background-color: lightblue;
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
    td#product_photo{
  	width: 100px;
  	height: 100px;
  }
  img{
  	max-width: 100px;
  	max-height: 100px;
  }
</style>

</head>
<body bgcolor='lightyellow'>

<!-- <h4>此頁暫練習採用 Script 的寫法取值:</h4> -->
<table id="table-1">
	<tr><td>
		 <h3>優惠券序號資料 - listOneCoupon_List.jsp</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>優惠券序號</th>
		<th>優惠券編號</th>
		<th>序號產生日期</th>
		<th>兌換狀態</th>
		<th>優惠券到期日</th>
		<th>會員編號</th>
	</tr>
	<tr>
		<td><%=coupon_listVO.getCpon_num()%></td>
		<td><%=coupon_listVO.getCpon_code()%></td>
		<td><%=coupon_listVO.getCpon_num_bdate()%></td>
		<td><%=coupon_listVO.getExc_sts()%></td>
		<td><%=coupon_listVO.getCpon_expirydate()%></td>
		<td><%=coupon_listVO.getExc_memid()%></td>
	</tr>
</table>

</body>
</html>