<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>  
<%
  Member_InfoVO member_infoVO = (Member_InfoVO) request.getAttribute("member_infoVO");
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料新增-addMember.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<style>
  .form {
	/* border: 1px solid black; */
	
	
	
}

form {
	width: 300px;
	height: 400px;
	margin: 50px auto;
}

form img {
	width: 100%;
	height: auto;
}
</style>


</head>




<%-- 錯誤表列 --%>
 <c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if> 

<body>
	<div class="container text-center">
		<div class="row">
			<div class="col-12">
				<div class="form">
					<form action="<%=request.getContextPath()%>/Member_InfoServlet"
						method="post" enctype="multipart/form-data">
						<h1 class="h3 mb-3 fw-normal">Welcome</h1>
						<div class="form-group">
							<label for="InputEmail">電子信箱</label> <input
								type="email" class="form-control" id="InputEmail1"
								name="m_email" value="<%=(member_infoVO == null) ? "" : member_infoVO.getM_email()%>" >
						<span id="InputEmail_span"></span>
						</div>
						<div class="form-group">
							<label for="InputPassword1">密碼輸入</label> <input
								type="password" class="form-control" id="InputPassword1"
								name="m_paswd" value="<%=(member_infoVO == null) ? "" : member_infoVO.getM_paswd()%>">
						</div>
						<div class="form-group">
							<label for="InputPassword1">密碼輸入確認</label> <input
								type="password" class="form-control" id="InputPassword2"
								name="m_paswd_2" >
						</div>
						<div class="form-group">
							<label for="InputPhone">手機號碼</label> <input
								type="phone" class="form-control" id="InputPhone"
								name="m_phone" value="<%=(member_infoVO == null) ? "" : member_infoVO.getM_phone()%>">
						<span id="InputPhone_span"></span>
						</div>
						<div class="form-group">
							<label for="InputID">身分證字號</label> <input
								type="ID" class="form-control" id="InputID"
								name="identity_number" value="<%=(member_infoVO == null) ? "" : member_infoVO.getIdentity_number()%>">
						<span id="InputID_span"></span>
						</div>
						<div class="form-group">
							<label for="InputName">使用者名稱</label> <input
								type="name" class="form-control" id="InputName"
								name="user_name" value="<%=(member_infoVO == null) ? "" : member_infoVO.getUser_name()%>">
						</div>
						<div class="form-group">
							<label for="InputGender">性別</label> <!-- <input
								type="gender" class="form-control" id="InputGender"
								name="gender"> -->
								<select name="gender">
　									<option value="male">male</option>
　									<option value="female">female</option>
　									

							</select>
						</div>
						<div class="form-group">
							<label for="InputBirthday">生日</label> <input
								type="birthday" class="form-control" id="f_date1"
								name="m_birthday">
						</div>
						<div class="form-group">
							<label for="InputHoroscope">星座</label> <!-- <input
								type="horoscope" class="form-control" id="InputHoroscope"
								name="horoscope"> -->
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
						<div class="form-group">
							<label for="InputBlood_type">血型</label> <!-- <input
								type="blood_type" class="form-control" id="InputBlood_type"
								name="blood_type"> -->
								<select name="blood_type">
　									<option value="A">A</option>
　									<option value="B">B</option>
　									<option value="AB">AB</option>
　									<option value="O">O</option>

							</select>
						</div>
						<div class="form-group">
							<label for="InputSpecialty">特長</label> <input
								type="specialty" class="form-control" id="InputSpecialty"
								name="specialty" value="<%=(member_infoVO == null) ? "" : member_infoVO.getSpecialty()%>">
						</div>
						<div class="form-group">
							<label for="InputHeadshot">大頭照</label> 
							<!-- <input
								type="file" class="form-control" id="pohto3"
								name="headshot" accept="image/*"> -->
								<input type="file" name="headshot" id="pohto3" accept="image/*">
								
						</div>
						<div id="preview">
						          <img>
					            </div>
						
						<div class="form-group">
							<label for="InputSchool">學歷</label> <input
								type="school" class="form-control" id="InputSchool"
								name="school" value="<%=(member_infoVO == null) ? "" : member_infoVO.getSchool()%>">
						</div>
						<div class="form-group">
							<label for="InputCompany">公司</label> <input
								type="company" class="form-control" id="InputCompany"
								name="company" value="<%=(member_infoVO == null) ? "" : member_infoVO.getCompany()%>">
						</div>
						<div class="form-group">
							<label for="InputIntro">自我介紹</label> <input
								type="intro" class="form-control" id="InputIntro"
								name="intro" value="<%=(member_infoVO == null) ? "" : member_infoVO.getIntro()%>">
						</div>
						<div class="form-group">
							<label for="InputArea_code">所在地區</label> <!-- <input
								type="area_code" class="form-control" id="InputArea_code"
								name="area_code"> -->
								<select name="area_code">
　									<option value="01">台北市</option>
　									<option value="02">新北市</option>
　									<option value="03">桃園市</option>
　									<option value="04">新竹市</option>
                                    <option value="05">苗栗市</option>
　									<option value="06">台中市</option>
　									<option value="07">彰化市</option>
　									<option value="08">雲林市</option>
                                    <option value="09">嘉義市</option>
　									<option value="10">台南市</option>
　									<option value="11">高雄市</option>
　									<option value="12">屏東市</option>
                                    <option value="13">台東市</option>
　									<option value="14">花蓮市</option>
　									<option value="15">宜蘭市</option>
　									<option value="16">基隆市</option>
                                    <option value="17">全台</option>
                                    

							</select>
						</div>
						<div class="form-group">
							<label for="InputMeat_area">配對地區</label> <!-- <input
								type="meat_area" class="form-control" id="InputMeat_area"
								name="meat_area"> -->
								<select name="meat_area">
　									<option value="01">台北市</option>
　									<option value="02">新北市</option>
　									<option value="03">桃園市</option>
　									<option value="04">新竹市</option>
                                    <option value="05">苗栗市</option>
　									<option value="06">台中市</option>
　									<option value="07">彰化市</option>
　									<option value="08">雲林市</option>
                                    <option value="09">嘉義市</option>
　									<option value="10">台南市</option>
　									<option value="11">高雄市</option>
　									<option value="12">屏東市</option>
                                    <option value="13">台東市</option>
　									<option value="14">花蓮市</option>
　									<option value="15">宜蘭市</option>
　									<option value="16">基隆市</option>
                                    <option value="17">全台</option>
                             </select>
						</div>
						
						<div class="form-group">
							<label for="InputMeat_minage">配對最低年齡</label> <input
								type="meat_minage" class="form-control" id="InputMeat_minage"
								name="meat_minage" value="<%=(member_infoVO == null) ? "" : member_infoVO.getMeat_minage()%>">
						</div>
						<div class="form-group">
							<label for="InputMeat_maxage">配對最高年齡</label> <input
								type="meat_maxage" class="form-control" id="InputMeat_maxage"
								name="meat_maxage" value="<%=(member_infoVO == null) ? "" : member_infoVO.getMeat_maxage()%>">
						</div>
					<%-- 	<div class="in_div_errorMsgs" style="color: red">${errorMsg}</div> --%>

						<input type="hidden" name="action" value="insert">
						<button class="w-40 btn btn-outline-success" type="submit">Register</button>
						
					</form>
				</div>
				
			</div>
		</div>
	</div>




<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date m_birthday = null;
  try {
	  m_birthday = member_infoVO.getM_birthday();
   } catch (Exception e) {
	   m_birthday = new java.sql.Date(System.currentTimeMillis());
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
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=m_birthday%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        $("input#pohto3").change(function() {
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
        //AJAX用
        //信箱
        $("#InputEmail1").blur(function() {
        	var InputEmail1 = $("#InputEmail1").val();
        	var obj = {
        		"action" : "email_check",
        		"InputEmail1" : InputEmail1
        	}
        	console.log($("#InputEmail").val());
        	$.ajax({
        		url : "<%= request.getContextPath()%>/Member_InfoServlet",
        		type : "POST",
        		data : obj,
        		dataType:"JSON",

        		success : function(result) {
        			console.log(result.fina)
                    if(result.fina=="error"){
                    	$("#InputEmail_span").attr("style","color:red")
                        $("#InputEmail_span").text("信箱已註冊過")
                        console.log("1231231")
                    }else if(result.fina=="success"){
                    	$("#InputEmail_span").attr("style","color:#00B2B2")
                        $("#InputEmail_span").text("okay!")
                        console.log("321")
                    }
        		},
        		error : function(err) {
        		}
        	})
        })
        
        //手機
        $("#InputPhone").blur(function() {
        	var InputPhone = $("#InputPhone").val();
        	var obj = {
        		"action" : "phone_check",
        		"InputPhone" : InputPhone
        	}
        	console.log($("#InputPhone").val());
        	$.ajax({
        		url : "<%= request.getContextPath()%>/Member_InfoServlet",
        		type : "POST",
        		data : obj,
        		dataType:"JSON",

        		success : function(result) {
        			console.log(result.fina)
                    if(result.fina=="error"){
                    	$("#InputPhone_span").attr("style","color:red")
                        $("#InputPhone_span").text("手機已註冊過")
                        console.log("1231231")
                    }else if(result.fina=="success"){
                    	$("#InputPhone_span").attr("style","color:#00B2B2")
                        $("#InputPhone_span").text("okay!")
                        console.log("321")
                    }
        		},
        		error : function(err) {
        		}
        	})
        })
         //身份證字號
        $("#InputID").blur(function() {
        	var InputID = $("#InputID").val();
        	var obj = {
        		"action" : "id_check",
        		"InputID" : InputID
        	}
        	console.log($("#InputID").val());
        	$.ajax({
        		url : "<%= request.getContextPath()%>/Member_InfoServlet",
        		type : "POST",
        		data : obj,
        		dataType:"JSON",

        		success : function(result) {
        			console.log(result.fina)
                    if(result.fina=="error"){
                    	$("#InputID_span").attr("style","color:red")
                        $("#InputID_span").text("身分證資料已註冊過")
                        console.log("1231231")
                    }else if(result.fina=="success"){
                    	$("#InputID_span").attr("style","color:#00B2B2")
                        $("#InputID_span").text("okay!")
                        console.log("321")
                    }
        		},
        		error : function(err) {
        		}
        	})
        })
        </script>
</body>
</html>