<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");
	List<Member_InfoVO> friends = (List<Member_InfoVO>) session.getAttribute("friends");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>MemberChat</title>
<!-- <link rel="stylesheet" href="front-end/chat/css/friendchat.css"	type="text/css" /> -->
<style>
* {
	margin: auto;
	padding: 0px;
}

html, body {
	font: 15px verdana, Times New Roman, arial, helvetica, sans-serif, Microsoft JhengHei;
	width: 90%;
	height: 90%;
	background: #eeeeda;
}

.panel {
	float: left;
	border: 2px solid #0078ae;
	border-radius: 5px;
	width: 50%;
}

.message-area {
	height: 70%;
	resize: none;
	box-sizing: border-box;
	overflow: auto;
	background-color: #ffffff;
}

.input-area {
	background: #0078ae;
	box-shadow: inset 0 0 10px #00568c;
}

.input-area input {
	margin: 0.5em 0em 0.5em 0.5em;
}

.text-field {
	border: 1px solid grey;
	padding: 0.2em;
	box-shadow: 0 0 5px #000000;
}

#message {
	min-width: 50%;
	max-width: 60%;
}

.statusOutput {
	background: #0078ae;
	text-align: center;
	color: #ffffff;
	border: 1px solid grey;
	padding: 0.2em;
	box-shadow: 0 0 5px #000000;
	width: 30%;
	margin-top: 10%;
	margin-right: 60%;
}

#fList {
	float: right;
	width: 50%;
}

.fLabel {
 	float: right; 
 	width: 50%; 
	padding: 5%;
	margin-bottom: 5px;
	background-color: #ffffff;
}

.-online {
	background-color: lightgreen;
}

.-offline {
	background-color: lightgrey;
}

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

.friend {
	background: #eee;
	float: left;
 	margin-left: 3px;
}

.me {
	float: right;
	background: #0084ff;
	color: #fff;
 	margin-right: 3px; 
}
.time {
	font-size:13px;
	color: lightgrey;
	background: white;
	padding: 0;
	margin-bottom: 3px;
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
	<div>
		hi ${member_infoVO.user_name},
	</div>
	<div id="fList">
		<c:forEach var="friend" items="${friends}">
			<div id="${friend.mem_id}" class="fLabel -offline">
				<span>${friend.user_name}</span><span class="notify -off">	•</span>
			</div>
		</c:forEach>
	</div>
	<h3 id="statusOutput" class="statusOutput"></h3>
	<div id="messagesArea" class="panel message-area"></div>
	<div class="panel input-area">
		<input id="message" class="text-field" type="text" placeholder="Message" onkeydown="if (event.keyCode == 13) sendMessage();" />
		<input type="submit" id="sendMessage" class="button" value="Send" onclick="sendMessage();" />
	</div>
	<script>
	var MyPoint = "/FriendWS/${member_infoVO.mem_id}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;

	var statusOutput = document.getElementById("statusOutput");
	var messagesArea = document.getElementById("messagesArea");
	var self = '${member_infoVO.mem_id}';
	var friends = document.getElementsByClassName("fLabel");
	var friend = "";

	addListener();
	
	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
				refreshFriendStatus(jsonObj);
				
			} else if ("new" === jsonObj.type) {
				document.getElementById(jsonObj.user).lastElementChild.classList.remove("-off");
				
			} else if ("sort" === jsonObj.type) {
// 				console.log(jsonObj);
				for (let i = 0; i < jsonObj.users.length; i++){
					var fList = document.getElementById("fList");
					var sortfriend = document.getElementById(jsonObj.users[i]);
					if (fList.firstChild) {
						fList.insertBefore(sortfriend, fList.firstChild);
					} else {
						fList.appendChild(sortfriend);
					}					
				}
				
			} else if ("history" === jsonObj.type) {
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				for (let i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var msgTime = new Date(historyData.time);
					var li = document.createElement('li');
					var liT = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className += 'me' : li.className += 'friend';
					historyData.sender === self ? liT.className += 'me time' : liT.className += 'friend time';
					li.innerHTML = showMsg;
					liT.innerHTML = msgTime.toLocaleString();
					ul.appendChild(li);
					ul.appendChild(liT);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
				
			} else if ("chat" === jsonObj.type) {
				if (jsonObj.sender == friend || jsonObj.sender == self){
					var li = document.createElement('li');
					var liT = document.createElement('li');
					var msgTime = new Date(jsonObj.time);
					jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
					jsonObj.sender === self ? liT.className += 'me time' : liT.className += 'friend time';
					li.innerHTML = jsonObj.message;
					liT.innerHTML = msgTime.toLocaleString()
// 					console.log(li);
					document.getElementById("area").appendChild(li);
					document.getElementById("area").appendChild(liT);
					messagesArea.scrollTop = messagesArea.scrollHeight;
					
					var jsonObj = {
							"type" : "read",
							"sender" : self,
							"receiver" : friend,
							"message" : "",
							"time" : Date.now(),
							"read" : ""
						};
					webSocket.send(JSON.stringify(jsonObj));
					
				} else if (document.querySelector("#" + jsonObj.sender) != null) {
					document.getElementById(jsonObj.sender).lastElementChild.classList.remove("-off");
				}
				
			} else if ("close" === jsonObj.type) {
				refreshFriendStatus(jsonObj);
			}
		};
		
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}

	// 有好友上線或離線就更新列表
	function refreshFriendStatus(jsonObj) {
		var users = jsonObj.users;
		for (let i = 0; i < friends.length; i++) {
			document.getElementById(friends[i].id).classList.remove("-online");	
			document.getElementById(friends[i].id).classList.add("-offline");			
			for (let j = 0; j < users.length; j++){
				if (friends[i].id == users[j]){
					document.getElementById(friends[i].id).classList.remove("-offline");			
					document.getElementById(friends[i].id).classList.add("-online");
				}
			}
		}
	}
	
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		var container = document.getElementById("fList");
		container.addEventListener("click", function(e) {
			// 判斷是否有點在好友上
			if (e.target.id != "fList"){
				// 判斷點擊的區域設定會員編號
				friend = e.target.parentNode.id == "fList" ? e.target.id : e.target.parentNode.id ;
				var fDiv = document.getElementById(friend);
				fDiv.lastElementChild.classList.add("-off");
				var fName = fDiv.firstElementChild.textContent;
				updateFriendName(fName);
				var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : "",
					"time" : Date.now(),
					"read" : ""
				};
				webSocket.send(JSON.stringify(jsonObj));
			}
		});
	}

	function sendMessage() {
		var inputMessage = document.getElementById("message");
		var message = inputMessage.value.trim();
		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			alert("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"message" : message,
				"time" : Date.now(),
				"read" : "no"
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}

	function updateFriendName(name) {
		statusOutput.innerHTML = name;
	}

	function disconnect() {
		webSocket.close();
	}

</script>
</body>
</html>