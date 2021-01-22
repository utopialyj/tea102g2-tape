<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.member_info.model.*"%>

<%
	Member_InfoVO member_infoVO = (Member_InfoVO) request.getAttribute("member_infoVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
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
	width: 600px;
	background-color: white;
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

img {
	width: 100px
}
</style>

</head>
<body bgcolor='white'>
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<table id="table-1">
					<tr>
						<td>
							<h3>會員資料 - ListOneMem.jsp</h3>
							<h4>
								<a
									href="<%=request.getContextPath()%>/back-end/member_info/mem_select_page.jsp">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

				<table>
					<tr>
						<!-- 		<th>會員編號</th>
 -->
						<th>信箱</th>
						<!-- 		<th>密碼</th>
 -->
						<th>電話號碼</th>
						<th>身分證字號</th>
						<th>身分證照片正面</th>
						<th>身分證照片反面</th>
						<th>會員權限等級</th>
						<th>會員名稱</th>
						<th>性別</th>
						<th>生日</th>
						<th>星座</th>
						<th>血型</th>
						<th>特長</th>
						<th>大頭照</th>
						<th>學校</th>
						<th>公司</th>
						<th>自我介紹</th>
						<th>地區代碼</th>
						<th>點數</th>
						<th>配對地區</th>
						<th>年齡低標</th>
						<th>年齡高標</th>


					</tr>
					<tr>
						<%-- 		<td><%=member_infoVO.getMem_id()%></td>
 --%>
						<td><%=member_infoVO.getM_email()%></td>
						<%-- 		<td><%=member_infoVO.getM_paswd()%></td>
 --%>
						<td><%=member_infoVO.getM_phone()%></td>
						<td><%=member_infoVO.getIdentity_number()%></td>
						<td class="td1"><img
							src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=idphoto_f"></td>
						<td class="td1"><img
							src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=idphoto_b"></td>
						<td><%=member_infoVO.getVerify_lv()%></td>
						<td><%=member_infoVO.getUser_name()%></td>
						<td><%=member_infoVO.getGender()%></td>
						<td><%=member_infoVO.getM_birthday()%></td>
						<td><%=member_infoVO.getHoroscope()%></td>
						<td><%=member_infoVO.getBlood_type()%></td>
						<td><%=member_infoVO.getSpecialty()%></td>
						<td class="td1"><img
							src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=headshot"></td>
						<td><%=member_infoVO.getSchool()%></td>
						<td><%=member_infoVO.getCompany()%></td>
						<td><%=member_infoVO.getIntro()%></td>
						<td><%=member_infoVO.getArea_code()%></td>
						<td><%=member_infoVO.getPoints()%></td>
						<td><%=member_infoVO.getMeat_area()%></td>
						<td><%=member_infoVO.getMeat_minage()%></td>
						<td><%=member_infoVO.getMeat_maxage()%></td>

					</tr>
				</table>
			</div>
		</div>
	</div>


	<%@ include file="../footer.jsp"%>
</body>
</html>