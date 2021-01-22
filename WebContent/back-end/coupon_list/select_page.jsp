<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Coupon_List: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: lightgrey;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='lightyellow'>

<table id="table-1">
   <tr><td><h3>Coupon_List: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Coupon_List: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllCoupon_List.jsp'>List</a> all Coupon_List.  <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="coupon_list.do" >
        <b>輸入優惠券序號 (如1):</b>
        <input type="text" name="cpon_num">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="coupon_listSvc" scope="page" class="com.coupon_list.model.Coupon_ListService" />
  <li>
     <FORM METHOD="post" ACTION="coupon_list.do" >
       <b>選擇優惠券序號:</b>
       <select size="1" name="cpon_num">
         <c:forEach var="coupon_listVO" items="${coupon_listSvc.all}" > 
          <option value="${coupon_listVO.cpon_num}">${coupon_listVO.cpon_num}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
</ul>


<h3>優惠券序號管理</h3>

<ul>
  <li><a href='addCoupon_List.jsp'>Add</a> a new Coupon_List.</li>
</ul>

</body>
</html>