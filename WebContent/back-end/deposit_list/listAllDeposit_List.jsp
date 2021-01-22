<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.deposit_list.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	Deposit_ListService deposit_listSvc = new Deposit_ListService();
    List<Deposit_ListVO> list = deposit_listSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有儲值訂單資料 - listAllDeposit_List.jsp</title>

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

<!-- <h4>此頁練習採用 EL 的寫法取值:</h4> -->
<table id="table-1">
	<tr><td>
		 <h3>所有儲值訂單資料 - listAllDeposit_List.jsp</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
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
	<%@ include file="page1.file" %> 
	<c:forEach var="deposit_listVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${deposit_listVO.deposit_code}</td>
			<td>${deposit_listVO.deposit_num}</td>
			<td>${deposit_listVO.deposit_date}</td>
			<td>${deposit_listVO.deposit_memid}</td>
			<td>${deposit_listVO.pay_type}</td>
			<td>${deposit_listVO.credit_num}</td>
			<td>${deposit_listVO.credit_expiry_yy}</td>
			<td>${deposit_listVO.credit_expiry_mm}</td>
			<td>${deposit_listVO.credit_security_num}</td>
			<td>${deposit_listVO.atm_id}</td>
			<td>${deposit_listVO.payment_sts}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/deposit_list/deposit_list.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="deposit_code"  value="${deposit_listVO.deposit_code}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/deposit_list/deposit_list.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="deposit_code"  value="${deposit_listVO.deposit_code}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>