<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.acts.model.*"%>

<%
	ActsVO actsVO = (ActsVO) request.getAttribute("actsVO"); //ActsServlet.java (Concroller) 存入req的actsVO物件 (包括幫忙取出的actsVO, 也包括輸入資料錯誤時的actsVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>活動資料修改 - update_act_input.jsp</title>

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

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>活動資料修改 - update_act_input.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="ActsController" name="form1"  enctype="multipart/form-data">
		<table>
			<tr>
				<td>活動編號:<font color=red><b>*</b></font></td>
				<td><%=actsVO.getActid()%></td>
			</tr>
			
			<tr>
				<td>主辦者編號:</td>
				<td><%=actsVO.getMemid()%></td>
			</tr>
		
			<tr>
				<td>活動主題:</td>
				<td><input type="TEXT" name="title" size="45"
					value="<%=actsVO.getTitle()%>" /></td>
			</tr>
			
			<tr>
				<td>活動內容:</td>
				<td><textarea rows="10" cols="30" name="cont"><%=actsVO.getCont()%></textarea></td>
			</tr>
			<tr>
				<td>活動時間:</td>
				<td><input name="time" id="f_date1" type="text"></td>
			</tr>
			<tr>
				<td>活動類型:</td>
				<td><input name="type" type="text"
					value="<%=actsVO.getType()%>"></td>
			</tr>
			<tr>
				<td>上下架狀態:</td>
				<td><label for="shsts_on">上架</label> <input id="shsts_on"
					name="shsts" type="radio" value="上架"
					<%="上架".equals(actsVO.getShsts()) ? "checked" : ""%>> <label
					for="shsts_down">下架</label> <input id="shsts_down" name="shsts"
					type="radio" value="下架"
					<%="下架".equals(actsVO.getShsts()) ? "checked" : ""%>></td>
			</tr>

			<tr>
				<td>狀態:</td>
				<td><select name="sts">
						<option>活動狀態</option>
						<option value="未開始"
							<%="未開始".equals(actsVO.getSts()) ? "selected" : ""%>>未開始</option>
						<option style="color: red; font-weight: bold;" value="檢舉中"
							<%="檢舉中".equals(actsVO.getSts()) ? "selected" : ""%>>檢舉中</option>
						<option value="活動中"
							<%="活動中".equals(actsVO.getSts()) ? "selected" : ""%>>活動中</option>
						<option value="已結束"
							<%="已結束".equals(actsVO.getSts()) ? "selected" : ""%>>已結束</option>
				</select></td>
			</tr>

			<tr>
				<td>活動圖片:</td>
				<%
					String pic = null;
					if (actsVO.getPic() != null) {
						pic = actsVO.getBase64Image();
					}

				%>
				<td><img src="data:image;base64,<%=pic%>" width="200"
					height="100"></td>
				<td><input name="pic" type="file"></td>
			</tr>


			<tr>
				<td>人數:</td>
				<td><input name="peop" type="text"
					value="<%=(actsVO == null) ? 0 : actsVO.getPeop()%>"></td>
			</tr>


			<tr>
				<td>地區:</td>
				<td><select name="areacd">
						<option value="地區">地區編號</option>
						<option value="01"
							<%=(actsVO != null) && "01".equals(actsVO.getAreacd()) ? "selected" : ""%>>台北市</option>
						<option value="02"
							<%=(actsVO != null) && "02".equals(actsVO.getAreacd()) ? "selected" : ""%>>新北市</option>
						<option value="03"
							<%=(actsVO != null) && "03".equals(actsVO.getAreacd()) ? "selected" : ""%>>桃園市</option>
						<option value="04"
							<%=(actsVO != null) && "04".equals(actsVO.getAreacd()) ? "selected" : ""%>>新竹市</option>
						<option value="05"
							<%=(actsVO != null) && "05".equals(actsVO.getAreacd()) ? "selected" : ""%>>苗栗市</option>
						<option value="06"
							<%=(actsVO != null) && "06".equals(actsVO.getAreacd()) ? "selected" : ""%>>台中市</option>
						<option value="07"
							<%=(actsVO != null) && "07".equals(actsVO.getAreacd()) ? "selected" : ""%>>彰化市</option>
						<option value="08"
							<%=(actsVO != null) && "08".equals(actsVO.getAreacd()) ? "selected" : ""%>>雲林市</option>
						<option value="09"
							<%=(actsVO != null) && "09".equals(actsVO.getAreacd()) ? "selected" : ""%>>嘉義市</option>
						<option value="10"
							<%=(actsVO != null) && "10".equals(actsVO.getAreacd()) ? "selected" : ""%>>台南市</option>
						<option value="11"
							<%=(actsVO != null) && "11".equals(actsVO.getAreacd()) ? "selected" : ""%>>高雄市</option>
						<option value="12"
							<%=(actsVO != null) && "12".equals(actsVO.getAreacd()) ? "selected" : ""%>>屏東市</option>
						<option value="13"
							<%=(actsVO != null) && "13".equals(actsVO.getAreacd()) ? "selected" : ""%>>台東市</option>
						<option value="14"
							<%=(actsVO != null) && "14".equals(actsVO.getAreacd()) ? "selected" : ""%>>花蓮市</option>
						<option value="15"
							<%=(actsVO != null) && "15".equals(actsVO.getAreacd()) ? "selected" : ""%>>宜蘭市</option>
						<option value="16"
							<%=(actsVO != null) && "16".equals(actsVO.getAreacd()) ? "selected" : ""%>>基隆市</option>
						<option value="17"
							<%=(actsVO != null) && "17".equals(actsVO.getAreacd()) ? "selected" : ""%>>全台</option>
				</select></td>
			</tr>


			<tr>
				<td>熱度:</td>
				<td>${actsVO.hot}</td>
				<td><input name="hot" type="hidden" value="0"></td>
			</tr>

			<tr>
				<td>地點:</td>
				<td><input name="loc" type="text"
					value="<%=(actsVO == null) ? "" : actsVO.getLoc()%>"></td>
			</tr>

			<tr>
				<td>店家資訊:</td>
				<td><input name="store" type="text"
					value="<%=(actsVO != null) && (actsVO.getStore() != null) ? actsVO.getStore() : ""%>"></td>
			</tr>


			<tr>
				<td>預算:</td>
				<td><input name="bgt" type="text"
					value="<%=(actsVO == null) ? 0 : actsVO.getBgt()%>"></td>
			</tr>

			<tr>
				<td>參與所需點數:</td>
				<td><input name="pts" type="text"
					value="<%=(actsVO == null) ? 0 : actsVO.getPts()%>"></td>
			</tr>

			<tr>
				<td>檢舉狀態:</td>
				<td><select name="rpsts">
						<option value="無"
							<%=(actsVO != null) && "無".equals(actsVO.getSts()) ? "selected" : ""%>>無</option>
						<option value="官方尚未回應"
							<%=(actsVO != null) && "官方尚未回應".equals(actsVO.getSts()) ? "selected" : ""%>>官方尚未回應</option>
						<option value="撤銷"
							<%=(actsVO != null) && "撤銷".equals(actsVO.getSts()) ? "selected" : ""%>>撤銷</option>
						<option value="已下架"
							<%=(actsVO != null) && "已下架".equals(actsVO.getSts()) ? "selected" : ""%>>已下架</option>
				</select></td>
			</tr>

<%-- 			<jsp:useBean id="actsSvc" scope="page" --%>
<%-- 				class="com.acts.model.ActsService" /> --%>
	

		</table>
		<br> <input type="hidden" name="action" value="update"> 
		<input type="hidden" name="actid" value="<%=actsVO.getActid()%>"> 
		<input type="hidden" name="memid" value="<%=actsVO.getMemid()%>"> 
		<input type="submit" value="送出修改">
	</FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

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
 		   value: '<%=actsVO.getTime()%>'
	});

</script>
					
</html>