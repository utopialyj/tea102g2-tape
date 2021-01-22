<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO memberVO = (Member_InfoVO)session.getAttribute("memberVO");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>login_success.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
    integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<link rel="stylesheet" href="front-end/notify/css/notify.css">
<script src="https://kit.fontawesome.com/e66ce32cfd.js" crossorigin="anonymous"></script>
<style>
ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

ul li {
	display: inline-block;
	clear: both;
	padding: 8px;
	border-radius: 15px;
	font-family: Helvetica, Arial, sans-serif;
}
.notify {
	color: red;
}
.-off {
	visibility:hidden;
}
</style>
</head>
<body onload="connect();" onunload="disconnect();">
<nav class="navbar navbar-expand-lg navbar-dark " style="background-color:#3559be">
    <a class="navbar-brand" href="#"><i class="fas fa-tape"></i> Tape</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="#"><i class="fas fa-home"></i> 首頁<span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#"><i class="fas fa-hand-holding-heart"></i> 配對</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#"><i class="fas fa-glass-cheers"></i> 揪團</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#"><i class="fas fa-donate"></i> 點數</a>
        </li>
        <li>
          <form class="form-inline my-2 my-lg-0 text-right">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" weight="100px">
            <button class="btn btn-outline-light" type="submit"><i class="fas fa-search"></i></button>
          </form>
        </li>


        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
            Peter
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <a class="dropdown-item" href="#">個人頁面</a>
            <a class="dropdown-item" href="#">設定</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">登出</a>
          </div>
        </li>

      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="#"><i class="far fa-comment-dots"></i></a>
        </li>
        <li class="nav-item" id="bell">
<!--           <a class="nav-link" href="#"><i class="fas fa-bell"></i></a>
 -->
 <a class="nav-link dropdown-toggle test1" href="#" id="alertsDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="getNotify()">
                                 <div id="B1">
                                <i class="fas fa-bell fa-fw"></i>
                                <!-- Counter - Alerts -->
                               
                                <span class="notify -off" id="number">+</span>
                                </div>
                            </a>
                            <!-- Dropdown - Alerts -->
                            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="messagesDropdown" id="messagesArea" >
                                <h6 class="dropdown-header">
                                    Alerts Center
                                </h6>
                                <div id="messagesArea">
                                <!-- <a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="mr-3">
                                        <div class="icon-circle bg-primary">
                                            <i class="fas fa-file-alt text-white"></i>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="small text-gray-500">December 12, 2019</div>
                                        <span class="font-weight-bold">A new monthly report is ready to download!</span>
                                    </div>
                                </a>
                                <a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="mr-3">
                                        <div class="icon-circle bg-success">
                                            <i class="fas fa-donate text-white"></i>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="small text-gray-500">December 7, 2019</div>
                                        $290.29 has been deposited into your account!
                                    </div>
                                </a>
                                <a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="mr-3">
                                        <div class="icon-circle bg-warning">
                                            <i class="fas fa-exclamation-triangle text-white"></i>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="small text-gray-500">December 2, 2019</div>
                                        Spending Alert: We've noticed unusually high spending for your account.
                                    </div>
                                </a> -->
                                <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                                </div>
                            </div>
 
 
 
         </li>
      </ul>
    </div>

  </nav>
	<br>
	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='orange' align='center' valign='middle' height='20'>
			 <td>   
			       <h3> 登入成功的頁面 - login_success.jsp           </h3> 
				     <h3> 歡迎:<font color=red>${user_name.user_name} </font>您好</h3>
			 </td>
		</tr>
	</table>
	<b> <br>
	<button id="getHistoryNTF">歷史通知</button>
	<br>                以下留空....
	</b>
	
	
	
	
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
    integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
    crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
    crossorigin="anonymous"></script>
	
	<script>
	var historybtn = document.getElementById("bell");
	historybtn.addEventListener("click", function(){
		var jsonObj = {
				"type" : 0,
				"sender" : "",
				"receiver" : "${memberVO.mem_id}",
				"postid": "",
				"actid": "",
				"message" : "",
				"time": Date.now(),
				"read" : "yes"
			};
			webSocket.send(JSON.stringify(jsonObj));
		
	});
	
	
	var MyPoint = "/NotifyWS/${memberVO.mem_id}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

// 	var statusOutput = document.getElementById("statusOutput");
// 	var messagesArea = document.getElementById("messagesArea");
	var self = '${userName}';
	var webSocket;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);
		console.log(endPointURL);
		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			document.getElementById("number").classList.add('-off');
 
// 			document.getElementById('sendMessage').disabled = false;
// 			document.getElementById('connect').disabled = true;
// 			document.getElementById('disconnect').disabled = false;
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			
			if (0 === jsonObj.type) {
				var messagesArea=document.getElementById("messagesArea");
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "A2";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
// 				var messages = JSON.parse(jsonObj.message);
				var messages=JSON.parse(jsonObj.message);
				console.log(messages)
				for (var i = 0; i < messages.length; i++) {
					var historyData = messages[i];
					var message=JSON.parse(historyData);
					console.log(message.sender+message.message);
					var msgTime = new Date(message.time);

// 					var showMsg = historyData.message;
					var li = document.createElement('li');
					var liT = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					message.read === "no" ? li.className += 'mark' : li.className += 'unmark';
					li.innerHTML = message.message;
					liT.innerHTML = msgTime.toLocaleString();
					console.log(li);
					console.log(liT);
					document.getElementById("A2").appendChild(li);

/* 					ul.appendChild(li);
 */					ul.appendChild(liT);
	document.getElementById("A2").appendChild(document.createElement("br"));
	document.getElementById("number").classList.add("-off");


				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if(0!=jsonObj.type){
				var T=T++;
				document.getElementById("number").innerHTML='T';

				document.getElementById("number").classList.remove("-off");

				/* var messagesArea=document.getElementById("messagesArea");

				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "A2";
				messagesArea.appendChild(ul);
				var li = document.createElement('li');
				var liT = document.createElement('li');
				var msgTime = new Date(jsonObj.time);
				/* jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
				jsonObj.sender === self ? liT.className += 'me time' : liT.className += 'friend time'; */
				/* li.innerHTML = jsonObj.message;
				liT.innerHTML = msgTime.toLocaleString()
					console.log(li);
				document.getElementById("A2").appendChild(li);
				document.getElementById("A2").appendChild(liT);
				document.getElementById("A2").appendChild(document.createElement("br"));
				messagesArea.scrollTop = messagesArea.scrollHeight;
				
				var jsonObj = {
						"type" :0,
						"sender" : "mem_id1",
						"receiver" : "mem_id2",
						"message" : "",
						"time" : Date.now(),
						"read" : "no"
					};
				webSocket.send(JSON.stringify(jsonObj)); */
				
				
			}

			
				
// 			
			
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	

	
	function getNotify() {
		console.log(1);
		var jsonObj = {
			"type" : 0,
			"sender" : "",
			"receiver" : "${memberVO.mem_id}",
			"postid": "",
			"actid": "",
			"message" : "",
			"time": Date.now(),
			"read":"no"
		};
		webSocket.send(JSON.stringify(jsonObj));
	}
	

	
	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
		document.getElementById('connect').disabled = false;
		document.getElementById('disconnect').disabled = true;
	}
	

</script>
</body>
</html>