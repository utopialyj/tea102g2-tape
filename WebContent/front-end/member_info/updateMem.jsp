<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>
<%
  Member_InfoVO member_infoVO = (Member_InfoVO) request.getAttribute("member_infoVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<title>會員資料修改-updatMem.jsp</title>
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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>
</head >
<body bgcolor='white'>
<table id="table-1">
	<tr><td>
		 <h3>會員資料修改 - updateMem.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/member_info/mem_select_page.jsp">回首頁</a></h4>
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
<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_InfoServlet" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>id:<font color=red><b>*</b></font></td>
		<td><%=member_infoVO.getMem_id()%></td>
	</tr>
	<tr>
		<td>信箱:<font color=red><b>*</b></font></td>
		<td><%=member_infoVO.getM_email()%></td>
	</tr>
	<%-- <tr>
		<td>密碼:</td>
		<td><input type="TEXT" name="m_paswd" size="45" value="<%=member_infoVO.getM_paswd()%>" /></td>
		<td><input type="hidden" name="action" value="update_paswd">
                <input type="button" value="修改"></td>
	</tr> --%>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="m_phone" size="45"	value="<%=member_infoVO.getM_phone()%>" /></td>
	</tr>
	<tr>
		<td>身分證字號:</td>
		<td><input type="TEXT" name="identity_number" size="45"	value="<%=member_infoVO.getIdentity_number()%>" /></td>
	</tr>
	<tr>
		<td>身分證正面照:</td>
		<td><input type="file" name="idphoto_f" size="45"	value="<%=member_infoVO.getIdphoto_f()%>" /></td>
	</tr>
	<tr>
		<td>身分證反面照:</td>
		<td><input type="file" name="idphoto_b" size="45"	value="<%=member_infoVO.getIdphoto_b()%>" /></td>
	</tr>
	<%-- <tr>
		<td>權限等級:</td>
		<td><input type="TEXT" name="verify_lv" size="45"	value="<%=member_infoVO.getVerify_lv()%>" /></td>
	</tr> --%>
	<tr>
		<td>使用者名稱:</td>
		<td><input type="TEXT" name="user_name" size="45"	value="<%=member_infoVO.getUser_name()%>" /></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><input type="TEXT" name="gender" size="45"	value="<%=member_infoVO.getGender()%>" /></td>
	</tr>
	<tr>
		<td>生日:</td>
		<td><input name="m_birthday" id="f_date1" type="text" ></td>
	</tr>
	<tr>
		<td>星座:</td>
		<td><input type="TEXT" name="horoscope" size="45"	value="<%=member_infoVO.getHoroscope()%>" /></td>
	</tr>
	<tr>
		<td>血型:</td>
		<td><input type="TEXT" name="blood_type" size="45"	value="<%=member_infoVO.getBlood_type()%>" /></td>
	</tr>
	<tr>
		<td>特長:</td>
		<td><input type="TEXT" name="specialty" size="45"	value="<%=member_infoVO.getSpecialty()%>" /></td>
	</tr>
	<tr>
		<td>大頭照:</td>
		<td><input type="file" name="headshot" size="45"	value="<%=member_infoVO.getHeadshot()%>" /></td>
	</tr> 
	<tr>
		<td>學歷:</td>
		<td><input type="TEXT" name="school" size="45"	value="<%=member_infoVO.getSchool()%>" /></td>
	</tr>
	<tr>
		<td>公司:</td>
		<td><input type="TEXT" name="company" size="45"	value="<%=member_infoVO.getCompany()%>" /></td>
	</tr>
	<tr>
		<td>自我介紹:</td>
		<td><input type="TEXT" name="intro" size="45"	value="<%=member_infoVO.getIntro()%>" /></td>
	</tr>
	<tr>
		<td>地區:</td>
		<td><input type="TEXT" name="area_code" size="45"	value="<%=member_infoVO.getArea_code()%>" /></td>
	</tr>
	<tr>
		<td>點數:</td>
		<td><input type="TEXT" name="points" size="45"	value="<%=member_infoVO.getPoints()%>" /></td>
	</tr>
	<tr>
		<td>配對地區:</td>
		<td><input type="TEXT" name="meat_area" size="45"	value="<%=member_infoVO.getMeat_area()%>" /></td>
	</tr>
	<tr>
		<td>配對年齡下限:</td>
		<td><input type="TEXT" name="meat_minage" size="45"	value="<%=member_infoVO.getMeat_minage()%>" /></td>
	</tr>
	<tr>
		<td>配對年齡上限:</td>
		<td><input type="TEXT" name="meat_maxage" size="45"	value="<%=member_infoVO.getMeat_maxage()%>" /></td>
	</tr>

	<%-- <jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" />
	<tr>
		<td>部門:<font color=red><b>*</b></font></td>
		<td><select size="1" name="deptno">
			<c:forEach var="deptVO" items="${deptSvc.all}">
				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)?'selected':'' } >${deptVO.dname}
			</c:forEach>
		</select></td>
	</tr> --%>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="mem_id" value="<%=member_infoVO.getMem_id()%>" />
<input type="hidden" name="m_email" value="<%=member_infoVO.getM_email()%>" />
<input type="hidden" name="verify_lv" value="<%=member_infoVO.getVerify_lv()%>" />
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
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=member_infoVO.getM_birthday()%>', // value:   new Date(),
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