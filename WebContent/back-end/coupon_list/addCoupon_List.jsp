<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.coupon_list.model.*"%>

<%
Coupon_ListVO coupon_listVO = (Coupon_ListVO) request.getAttribute("coupon_listVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>優惠券序號新增 - addCoupon_List.jsp</title>

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
	width: 500px;
	background-color: lightpink;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
  textarea{
	resize:none;
  	width: 336px;
  	height: 100px;
  }
  div#preview{
  	border: 1px solid lightgrey;
  	width: 100px;
  	height: 100px;
  }
  img{
  	max-width: 100%;
  	max-height: 100%;
  }
</style>

</head>
<body bgcolor='lightyellow'>

<table id="table-1">
	<tr><td>
		 <h3>優惠券序號新增 - addCoupon_List.jsp</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="coupon_list.do" name="form1">
<table>
	<tr>
		<td>優惠券編號:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="cpon_code" size="45" 
			 value="<%= (coupon_listVO==null)? "1" : coupon_listVO.getCpon_code()%>" /></td>
	</tr>
	<tr>
		<td>序號產生日期:<font color=red><b>*</b></font></td>
		<td><input name="cpon_num_bdate" id="f_date1" type="text"></td>
	</tr>
	<tr>
		<td>兌換狀態:<font color=red><b>*</b></font></td>
		<td>
			<input type="radio" name="exc_sts" value="尚未兌換" ${(coupon_listVO.exc_sts==null || coupon_listVO.exc_sts=='尚未兌換')? 'checked':''}/>尚未兌換
			<input type="radio" name="exc_sts" value="已兌換" ${(coupon_listVO.exc_sts=='已兌換')? 'checked':''} />已兌換
		</td>
	</tr>
	<tr>
		<td>優惠券到期日:<font color=red><b>*</b></font></td>
		<td><input name="cpon_expirydate" id="f_date2" type="text"></td>
	</tr>
	<tr>
		<td>會員編號:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="exc_memid" size="45" 
			 value="<%= (coupon_listVO==null)? "MEM0000001" : coupon_listVO.getExc_memid()%>" /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date cpon_num_bdate = null;
  try {
	  cpon_num_bdate = coupon_listVO.getCpon_num_bdate();
   } catch (Exception e) {
	   cpon_num_bdate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<% 
  java.sql.Date cpon_expirydate = null;
  try {
	  cpon_expirydate = coupon_listVO.getCpon_expirydate();
   } catch (Exception e) {
	   cpon_expirydate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
	

        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
// 	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
// 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=cpon_num_bdate%>' // , value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $('#f_date2').datetimepicker({
	       timepicker:false,
	       format:'Y-m-d',
		   value: '<%=cpon_expirydate%>'
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>