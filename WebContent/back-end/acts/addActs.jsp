<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Base64"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.acts.model.*"%>
<%@ page import="com.member_info.model.*" %>
<%@ page import="com.acttype.model.*" %>

<%
	ActsVO actsVO = (ActsVO) request.getAttribute("actsVO");
%>

<jsp:usebean id="typeSvc" scope="page" class="com.acttype.model.ActTypeService" >

 
 

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>活動新增 - addActs.jsp</title>

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

div.hidden_for_users {
	/* position: fixed;
    opacity: 0;
    pointer-events: none; */
	-moz-appearance: none;
	-webkit-appearance: none;
	appearance: none;
}
</style>

<style>
 img{ 
   	max-width: 100%; 
   	max-height: 100%; 
   }
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	white-space: nowrap;
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
				<h3>活動新增 - addActs.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%= request.getContextPath()%>/front_end/acts/homePage.jsp"><img src="<%= request.getContextPath()%>/front_end/acts/images/kali.jpg"
						alt="找不到圖片" width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/front_end/acts/Acts_Back_Servlet" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>主辦者編號:</td>
				<td><input type="TEXT" name="memid" size="30"
					value="${memVO.mem_id}"/></td>
			</tr>

			<tr>
				<td>活動主題:</td>
				<td><input name="title" type="text" size="30"
					value="<%=(actsVO == null) ? "" : actsVO.getTitle()%>" /></td>
			</tr>

			<tr>
				<td>活動內容:</td>
				<td><textarea rows="10" cols="30" name="cont"><%=(actsVO == null) ? "" : actsVO.getCont()%></textarea></td>
			</tr>


			<tr>
				<td>活動時間:</td>
				<td><input name="time" id="f_date1" type="text"></td>
			</tr>
			
			<!-- 活動類型 下來式選單  -->
			<tr>
				<td>活動類型:</td>
				<td>
					<select name="actType">
						<c:forEach var="actTypeVO" items="${typeSvc.all}" >
							<option value="${actTypeVO.typecode}"> ${actTypeVO.acttype}
						</c:forEach>
					</select>
				</td>
			</tr>

			<tr>
				<td>活動圖片:</td>
				<td><input id="pic" name="pic" type="file"></td>
			</tr>

			<tr>
				<td></td>
				<td><div id="preview">
						<img>
					</div></td>
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


		</table>
		<br>
	<%-- 	// 第二輪取出上一輪使用者上傳的照片 
		<input type="hidden" name="picTemp" value="<%=(actsVO != null) && (actsVO.getPic() != null)? actsVO.getPic().toString() : ""%>">
	--%>
		
		<!-- 
			藏 一張圖傳到後端
		-->
		<%--

		String pic = null;
		byte[] buffer = null;
// 		System.out.println(request.getContextPath() + "/back_end/acts/images/noPic.jpg");
// 		File fi = new File( "images/noPic.jpg");
// 		InputStream in = null;
// 		in = new FileInputStream(fi);
// 		in.read(buffer);
		System.out.println("這裡這裡");
		System.out.println(this);
		InputStream in = this.getServletContext().getResourceAsStream("noPic.jpg");
 		in.read(buffer);
		pic = Base64.getEncoder().encodeToString(buffer);
		in.close();
		--%>
		
		
		<%--
						//前端也能塞資料入庫的
						InputStream in = null;
						byte[] buffer = null;
						in = getServletContext().getResourceAsStream("/back_end/acts/images/noPic.jpg");
						System.out.println("圖片卒1");
						System.out.println(in.available());
						System.out.println("圖片卒2");
						buffer = new byte[in.available()];
						System.out.println("圖片卒3");
						in.read(buffer);
						ActsService actsSvc = new ActsService();
						actsSvc.addAct("MEM0000002", "上架", new java.sql.Timestamp(System.currentTimeMillis()), "Scheep1", "未開始", "測試", "測試測試測試", buffer, 9999, "01", 0, "TEA102G2", "TibaMe", 99999, 99999, "無");
		 --%>
	<!-- 	
		<td>
		<tr>測試</tr>
		<img src=" images/noPic.jpg" alt="圖片找不到" >
		</td>
	-->
<%-- File(電腦硬體路徑) 		
<input type="hidden" name="pathNo" value="<%= request.getContextPath() %>/back_end/acts/images/noPic.jpg" > 
--%>
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增">
	</FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

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
		   value: '<%=time%>' /* value:new Date(),*/

	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});

	$("input#pic").change(function() {
		if (this.files.length == 1) {
			let reader = new FileReader();
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function() {
				$("div#preview img").attr("src", reader.result);
			});
		} else {
			$("div#preview").html("<img>");
		}
	});
</script>
</html>