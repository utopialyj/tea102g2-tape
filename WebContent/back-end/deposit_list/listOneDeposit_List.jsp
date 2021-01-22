<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.deposit_list.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Deposit_ListVO deposit_listVO = (Deposit_ListVO) request.getAttribute("deposit_listVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>儲值訂單資料 - listOneDeposit_List.jsp</title>

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
		 <h3>儲值訂單資料 - listOneDeposit_List.jsp</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>儲值訂單編號</th>
		<th>儲值金額</th>
		<th>儲值日期</th>
		<th>會員編號</th>
		<th>付款方式</th>
		<th>信用卡號</th>
		<th>信用卡到期（年）</th>
		<th>信用卡到期（月）</th>
		<th>信用卡安全碼</th>
		<th>ATM 虛擬帳戶</th>
		<th>收款狀態</th>
	</tr>
	<tr>
		<td><%=deposit_listVO.getDeposit_code()%></td>
		<td><%=deposit_listVO.getDeposit_num()%></td>
		<td><%=deposit_listVO.getDeposit_date()%></td>
		<td><%=deposit_listVO.getDeposit_memid()%></td>
		<td><%=deposit_listVO.getPay_type()%></td>
		<td><%=deposit_listVO.getCredit_num()%></td>
		<td><%=deposit_listVO.getCredit_expiry_yy()%></td>
		<td><%=deposit_listVO.getCredit_expiry_mm()%></td>
		<td><%=deposit_listVO.getCredit_security_num()%></td>
		<td><%=deposit_listVO.getAtm_id()%></td>
		<td><%=deposit_listVO.getPayment_sts()%></td>

	</tr>
</table>

</body>
</html>