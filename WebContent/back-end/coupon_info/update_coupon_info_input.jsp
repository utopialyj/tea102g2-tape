<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.coupon_info.model.*"%>

<%
	Coupon_InfoVO coupon_infoVO = (Coupon_InfoVO) request.getAttribute("coupon_infoVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Tape | Admin</title>
<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/css/sb-admin-2.min.css"
	rel="stylesheet">


<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"> -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/vendor/bootstrap/icons-main/font/bootstrap-icons.css">
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

textarea {
	width: 336px;
	height: 100px;
}

div#preview {
	border: 1px solid lightgrey;
	width: 100px;
	height: 100px;
}

img {
	max-width: 100%;
	max-height: 100%;
}
</style>

</head>
<body bgcolor='lightyellow'>
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<table id="table-1">
					<tr>
						<td>
							<h3>優惠券資料修改 - update_coupon_info_input.jsp</h3>
							<h4>
								<a href="select_page.jsp">回首頁</a>
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

				<FORM METHOD="post" ACTION="coupon_info.do" name="form1"
					enctype="multipart/form-data">
					<table>
						<tr>
							<td>優惠券編號:</td>
							<td><%=coupon_infoVO.getCpon_code()%></td>
						</tr>
						<tr>
							<td>商品名稱:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="product_name" size="45"
								value="<%=coupon_infoVO.getProduct_name()%>" /></td>
						</tr>
						<tr>
							<td>商品品牌名稱:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="product_brand" size="45"
								value="<%=coupon_infoVO.getProduct_brand()%>" /></td>
						</tr>
						<tr>
							<td>兌換條件:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="exc_condition" size="45"
								value="<%=coupon_infoVO.getExc_condition()%>" /></td>
						</tr>
						<tr>
							<td>兌換期限:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="exe_deadline" size="45"
								value="<%=coupon_infoVO.getExe_deadline()%>" /></td>
						</tr>
						<tr>
							<td>商品圖片:</td>
							<td><input type="File" name="product_photo"
								id="product_photo" accept="image/*" /></td>
							<%-- 		<td><input type="TEXT" name="product_photo" size="45" value="<%=coupon_infoVO.getProduct_photo()%>" /></td> --%>
						</tr>
						<tr>
							<td></td>
							<td><div id="preview">
									<img>
								</div></td>
						</tr>
						<tr>
							<td>商品文字描述:</td>
							<td><textarea name="product_context"><%=(coupon_infoVO == null) ? "星巴克那堤" : coupon_infoVO.getProduct_context()%></textarea>
							</td>
							<%-- 		<td><input type="TEXT" name="product_context" size="45" value="<%=coupon_infoVO.getProduct_context()%>" /></td> --%>
						</tr>
						<tr>
							<td>優惠類別:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="cpon_type" size="45"
								value="<%=coupon_infoVO.getCpon_type()%>" /></td>
						</tr>
						<jsp:useBean id="areaListSvc" scope="page"
							class="com.area_list.model.AreaListService" />
						<tr>
							<td>可兌換地區代號:<font color=red><b>*</b></font></td>
							<td><select size="1" name="cpon_area_code">
									<c:forEach var="areaListVO" items="${areaListSvc.all}">
										<option value="${areaListVO.area_code}"
											${(coupon_infoVO.cpon_area_code==areaListVO.area_code)?'selected':'' }>${areaListVO.area}
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td>可兌換數量:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="exc_count" size="45"
								value="<%=coupon_infoVO.getExc_count()%>" /></td>
						</tr>
						<tr>
							<td>已被兌換次數:<font color=red><b>*</b></font></td>
							<td><input type="TEXT" name="exced_count" size="45"
								value="<%=coupon_infoVO.getExced_count()%>" /></td>
						</tr>
						<tr>
							<td>商品上架狀態:<font color=red><b>*</b></font></td>
							<%-- 		<td><input type="TEXT" name="shelf_sts" size="45" value="<%=coupon_infoVO.getShelf_sts()%>" /></td> --%>
							<td><input type="radio" name="shelf_sts" value="下架"
								<%=(coupon_infoVO.getShelf_sts().equals("下架") ? "checked" : "")%> />下架
								<input type="radio" name="shelf_sts" value="上架"
								<%=(coupon_infoVO.getShelf_sts().equals("上架") ? "checked" : "")%> />上架
							</td>
						</tr>
						<tr>
							<td>商品上架日期:<font color=red><b>*</b></font></td>
							<td><input name="onshelf_date" id="f_date1" type="text"></td>
						</tr>
						<tr>
							<td>商品下架日期:</td>
							<td><input name="cpon_offshelf_date" id="f_date2"
								type="text"></td>
						</tr>


					</table>
					<br> <input type="hidden" name="action" value="update">
					<input type="hidden" name="cpon_code"
						value="<%=coupon_infoVO.getCpon_code()%>"> <input
						type="submit" value="送出修改">
				</FORM>
			</div>
		</div>
	</div>


	<%@ include file="../footer.jsp"%>
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
//            theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
//  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=coupon_infoVO.getOnshelf_date()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $('#f_date2').datetimepicker({
 	       timepicker:false,
 	       format:'Y-m-d',
 		   value: '<%=(coupon_infoVO.getCpon_offshelf_date() == null) ? "" : coupon_infoVO.getCpon_offshelf_date()%>
	',
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
<script>
	$("input#product_photo").change(function() {
		if (this.files.length == 1) {
			let reader = new FileReader();
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function() {
				$("img").attr("src", reader.result);
			});
		} else {
			$("div#preview").html("<img>");
		}
	});
</script>
</html>