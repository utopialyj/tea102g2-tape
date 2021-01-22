<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import="com.post.model.*"%>
<%@ page import="com.post_photo.model.*"%>
<%@ page import="com.post_comment.model.*"%>
<!DOCTYPE html>

<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	pageContext.setAttribute("member_infoVO", member_infoVO);
	pageContext.setAttribute("mainMem",((Member_InfoVO) session.getAttribute("member_infoVO")).getMem_id());
	
%>
<html>
<head>
<title>All Post info</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">
<style>
.displayNone {
	display: none;
}
article.paper {
	width: 100%;
	margin: 10px auto 0;
	border-radius: 5px;
	padding: 10px;
	background-color: white;
	box-shadow: 1px 1px 5px rgb(119, 120, 160);
}

  img.main{
  	width: 100px;
  	height: 100px;
  }

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
	width: 800px;
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
</style>

<!-- 側邊攔的css 開始-->
<style>

aside {
 color: #fff;
 width: 100%;
 padding-left: 20px;
 height: 500px;
 background-image: linear-gradient(30deg, #ffffff, #ff8a8f);
    border-top-right-radius: 80px;
    -webkit-filter: drop-shadow(12px 12px 7px rgba(0, 0, 0, 0.7));
    filter: drop-shadow(-12px 5px 8px rgba(0, 0, 0, 0.3));
}

aside a {
 font-size: 16px;
 font-size: 16px;
 color:#565c5f;
 display: block;
 padding: 12px;
  padding-left: 15px; 
 text-decoration: none;
 -webkit-tap-highlight-color: transparent;
}

aside a:hover {
 color: #6c757d;
 background: #fff;
 outline: none;
 position: relative;
 background-color: #fff;
 border-top-left-radius: 20px;
 border-bottom-left-radius: 20px;
 text-decoration:none;
}

aside a i {
 margin-right: 5px;
}

aside a:hover::after {
 content: "";
 position: absolute;
 background-color: transparent;
 bottom: 100%;
 right: 0;
 height: 35px;
 width: 35px;
 border-bottom-right-radius: 18px;
 box-shadow: 0 20px 0 0 #fff;
}

aside a:hover::before {
 content: "";
 position: absolute;
 background-color: transparent;
 top: 38px;
 right: 0;
 height: 35px;
 width: 35px;
 border-top-right-radius: 18px;
 box-shadow: 0 -20px 0 0 #fff;
}

aside p {
 margin: 0;
 padding: 40px 0;
}

body {

 width: 100%;
 height: 100vh;
 margin: 0;
}
</style>
<!-- 側邊攔的css 尾 -->


</head>
<body bgcolor="white">

	<%@ include file="/front-end/nav.jsp"%>
	<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-2 box1">
					
					
					
			<!-- 改的			 -->
					<aside>
						<div>
					<img id="gs_big_pic_img"
						src="<%=request.getContextPath()%>/back-end/post/post?action=display_pic&Member=${mainMem}"
						class="rounded-circle main" style="margin: 15px 0px 10px 0px">
				</div>
						<label class="btn btn-light"><form action="<%=request.getContextPath()%>/back-end/post/post"
						method="post" id="bigpic_form" enctype="multipart/form-data" style='display:none'>
						<input type="file" name="gs_big_pic" id="gs_big_pic"
							accept="image/*" style="width: 200px" style='width:200px' style='display:none'>
					</form><i class="fas fa-camera"></i>編輯頭像</label>
						<a
							href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=${mainMem}">
							<i class="far fa-address-card" aria-hidden="true"></i> 個人簡介
						</a> <a
							href="<%=request.getContextPath()%>/front-end/profile/listAllPost.jsp?Member=${mainMem}">
							<i class="far fa-newspaper" aria-hidden="true"></i> 個人動態
						</a> <a href="<%=request.getContextPath()%>/front-end/profile/friendList.jsp?Member=${mainMem}"> <i class="far fa-list-alt"
							aria-hidden="true"></i> 好友列表
						</a> 

					</aside>
			</div>
			<div class="col-8 box2">
				<table>
				
						<article class="paper">
			<form method="post"	action="<%=request.getContextPath()%>/back-end/post/post" name="form1">
					
							<div><br></div>
							<div>姓名:</div>
							<div><input type="text" name="user_name" size="45" class="form-control form-control-sm" placeholder="<%=member_infoVO.getUser_name()%>"/></div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>性別:</div>
<%-- 							<input type="text" name="blood_type" size="45" value="<%=member_infoVO.getBlood_type()%>" /> --%>
							<select name="gender">
　									<option value="Male">Male</option>
　									<option value="Female">Female</option>

							</select>
								<hr width="100%">
							<div class="line">
							</div>
							<div>生日:<%=member_infoVO.getM_birthday()%></div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>星座:</div>
							<div>
							<select name="horoscope">
　									<option value="摩羯座">摩羯座</option>
　									<option value="水瓶座">水瓶座</option>
　									<option value="雙魚座">雙魚座</option>
　									<option value="牡羊座">牡羊座</option>
									<option value="金牛座">金牛座</option>
　									<option value="雙子座">雙子座</option>
　									<option value="巨蟹座">巨蟹座</option>
　									<option value="獅子座">獅子座</option>
									<option value="處女座">處女座</option>
　									<option value="天秤座">天秤座</option>
　									<option value="天蠍座">天蠍座</option>
　									<option value="射手座">射手座</option>
							</select>
							</div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>血型:</div>
<%-- 							<input type="text" name="blood_type" size="45" value="<%=member_infoVO.getBlood_type()%>" /> --%>
							<select name="blood_type">
　									<option value="A">A</option>
　									<option value="B">B</option>
　									<option value="AB">AB</option>
　									<option value="O">O</option>

							</select>
							<div class="line">
								<hr width="100%">
							</div>
							<div>專長:</div>
							<div><input type="text" name="specialty" size="45" class="form-control form-control-sm" placeholder="<%=member_infoVO.getSpecialty()==null?" ":member_infoVO.getSpecialty()%>"/></div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>學校:</div>
							<div><input type="text" name="school" size="45" class="form-control form-control-sm" placeholder="<%=member_infoVO.getSchool()==null?" ":member_infoVO.getSchool()%>"/></div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>公司:</div>
							<div><input type="text" name="company" size="45" class="form-control form-control-sm" placeholder="<%=member_infoVO.getCompany()==null?" ":member_infoVO.getCompany()%>"/></div>
							<div class="line">
								<hr width="100%">
							</div>
							<div>自我介紹:</div>
							<div><input type="text" name="intro" size="45" class="form-control form-control-sm" placeholder="<%=member_infoVO.getIntro()==null?"快來新增你的自我介紹吧!":member_infoVO.getIntro()%>"/></div>
							<div class="line">
								<hr width="100%">
							</div>
							
				<input type="hidden" name="member_id" value="<%=member_infoVO.getMem_id() %>"> 
				<input type="hidden" name="action" value="update_memberinfo">
				<input type="submit" value="送出"> 
				</form>
						</article>

				</table>
								
			</div>
			<%@ include file="/front-end/chatroom.jsp"%>

		</div>
	</div>

	<!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	<script>
		
	<%@ include file="/front-end/js/notification.js" %>
		
	</script>


</body>

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date hiredate = null;
  try {
	    hiredate = java.sql.Date.valueOf(request.getParameter("hiredate").trim());
   } catch (Exception e) {
	    hiredate = new java.sql.Date(System.currentTimeMillis());
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
<script src="<%=request.getContextPath() %>/front-end/js/jquery/jquery-3.5.1.min.js"></script>	
<script>
$("#gs_big_pic").on("change",function(event){
	var reader = new FileReader();
		reader.readAsDataURL(this.files[0]);
//		var file = event.target.files[0];
//		reader.readAsDataURL(file);
	reader.addEventListener("load",function(e){
		
        //觸發就清空img的src
		$("#gs_big_pic_img").attr("src","");
		
		let obj = new FormData($("#bigpic_form")[0]);
		console.log(obj)
		console.log($("bigpic_form")[0])
		obj.append("action","update_bigpic");
		$.ajax({
			url:"<%=request.getContextPath()%>/back-end/post/post",	
			type:"POST",
			data:obj,
			contentType:false,
			processData:false,
			cache:false,
			
			success:function(){
											
				$("#gs_big_pic_img").attr("src", e.target.result);
			},
			error:function(err){
				alert("大頭照更新錯誤");
			}
		})
	})
})








</script>



<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
 	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value:new Date(),
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