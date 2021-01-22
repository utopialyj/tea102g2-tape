<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.deposit_list.model.*"%>

<%
	Deposit_ListVO deposit_listVO = (Deposit_ListVO) request.getAttribute("deposit_listVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>儲值訂單資料修改 - update_deposit_list_input.jsp</title>

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
		 <h3>儲值訂單資料修改 - update_deposit_list_input.jsp</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="deposit_list.do" name="form1">
<table>
	<tr>
		<td>儲值訂單編號:</td>
		<td><%=deposit_listVO.getDeposit_code()%></td>
	</tr>
	<tr>
		<td>儲值金額:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="deposit_num" size="45" value="<%=deposit_listVO.getDeposit_num()%>" /></td>
	</tr>
	<tr>
		<td>儲值日期:<font color=red><b>*</b></font></td>
		<td><input name="deposit_date" id="f_date" type="text"></td>
	</tr>
	<tr>
		<td>會員編號:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="deposit_memid" size="45" value="<%=deposit_listVO.getDeposit_memid()%>" /></td>
	</tr>
	<tr>
		<td>付款方式:<font color=red><b>*</b></font></td>
		<td>
			<input type="radio" name="pay_type" value="信用卡" <%= (deposit_listVO.getPay_type().equals("信用卡")? "checked" : "") %> />信用卡
			<input type="radio" name="pay_type" value="ATM" <%= (deposit_listVO.getPay_type().equals("ATM")? "checked" : "") %> />ATM
		</td>
	</tr>
	<tr>
		<td>信用卡號:<font color=red><b></b></font></td>
		<td><input type="TEXT" name="credit_num" size="45" value="<%=deposit_listVO.getCredit_num()%>" /></td>
	</tr>
	<tr>
		<td>信用卡到期（年）:<font color=red><b></b></font></td>
		<td><input type="TEXT" name="credit_expiry_yy" size="45" value="<%=deposit_listVO.getCredit_expiry_yy()%>" /></td>
	</tr>
	<tr>
		<td>信用卡到期（月）:<font color=red><b></b></font></td>
		<td><input type="TEXT" name="credit_expiry_mm" size="45" value="<%=deposit_listVO.getCredit_expiry_mm()%>" /></td>
	</tr>
	<tr>
		<td>信用卡安全碼:<font color=red><b></b></font></td>
		<td><input type="TEXT" name="credit_security_num" size="45" value="<%=deposit_listVO.getCredit_security_num()%>" /></td>
	</tr>
	<tr>
		<td>ATM 虛擬帳戶:<font color=red><b></b></font></td>
		<td><input type="TEXT" name="atm_id" size="45" value="<%=deposit_listVO.getAtm_id()%>" /></td>
	</tr>
	<tr>
		<td>收款狀態:<font color=red><b>*</b></font></td>
		<td>
			<input type="radio" name="payment_sts" value="待付款" <%=(deposit_listVO.getPayment_sts().equals("待付款")? "checked" : "") %> />待付款
			<input type="radio" name="payment_sts" value="已付款" <%=(deposit_listVO.getPayment_sts().equals("已付款")? "checked" : "") %> />已付款
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="deposit_code" value="<%=deposit_listVO.getDeposit_code()%>">
<input type="submit" value="送出修改"></FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

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
        $('#f_date').datetimepicker({
//            theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
//  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=deposit_listVO.getDeposit_date()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
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