<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.acts.model.*"%>

<%
	ActsVO actsVO = (ActsVO) request.getAttribute("actsVO");
%>
<%=actsVO == null%>
<%=request.getContextPath()%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>

	<input name="time1" id="f_date1" type="text">
	<input name="time2" id="f_date2" type="text">
</body>

	<%
		java.sql.Timestamp time = null;
		try {
			// 	   time = new java.sql.Timestamp(System.currentTimeMillis());
			time = actsVO.getTime();
		} catch (Exception e) {
			time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>

	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

	<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

	<script>
$.datetimepicker.setLocale('zh');
$('#f_date1').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:true,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
   value: '<%=time%>'
		 /* value:new Date(),*/

		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});
		
$('#f_date2').datetimepicker({
	   theme: '',              //theme: 'dark',
	   timepicker:true,       //timepicker:true,
	   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	   format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
	   value: '<%=time%>'
			 /* value:new Date(),*/

			//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
			//startDate:	            '2017/07/10',  // 起始日
			//minDate:               '-1970-01-01', // 去除今日(不含)之前
			//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
			});
	</script>

</html>