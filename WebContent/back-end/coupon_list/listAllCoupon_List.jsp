<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon_list.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	Coupon_ListService coupon_listSvc = new Coupon_ListService();
    List<Coupon_ListVO> list = coupon_listSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有優惠券序號資料 - listAllCoupon_List.jsp</title>

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
		 <h3>所有優惠券序號資料 - listAllCoupon_List.jsp</h3>
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
		<th>優惠券序號</th>
		<th>優惠券編號</th>
		<th>序號產生日期</th>
		<th>兌換狀態</th>
		<th>優惠券到期日</th>
		<th>會員編號</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="coupon_listVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${coupon_listVO.cpon_num}</td>
			<td>${coupon_listVO.cpon_code}</td>
			<td>${coupon_listVO.cpon_num_bdate}</td>
			<td>${coupon_listVO.exc_sts}</td>
			<td>${coupon_listVO.cpon_expirydate}</td>
			<td>${coupon_listVO.exc_memid}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/coupon_list/coupon_list.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="cpon_num"  value="${coupon_listVO.cpon_num}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/coupon_list/coupon_list.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="cpon_num"  value="${coupon_listVO.cpon_num}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>