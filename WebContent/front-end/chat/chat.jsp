<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>MemberChat</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<style>
* {
	/* 	margin: auto; */
	/* 	padding: 0px; */
	
}

html, body {
	/* 	font: 15px verdana, Times New Roman, arial, helvetica, sans-serif, Microsoft JhengHei; */
	/* 	width: 90%; */
	/* 	height: 90%; */
	/* 	background: #eeeeda; */
	
}

.friendIcon img {
	max-width: 100%;
	position: relative;
	top: 50%;
	transform: translateY(-50%);
}

#chatBox {
	position: fixed;
	right: 20%;
	bottom: 10%;
	height: 400px;
	width: 400px;
	border: solid teal;
	border-radius: 3px;
}

#chatTitle {
	background: teal;
	height: 15%;
}

#titlePhoto {
	display: inline-block;
	vertical-align: middle;
	width: 50px;
	height: 50px;
	border-radius: 50%;
	overflow: hidden;
	margin-top: 4px;
	margin-left: 4px
}

#friendNameSpan {
	/* 	background: teal; */
	/* 	text-align: center; */
	color: #ffffff;
	/* 	border: 1px solid grey; */
	padding: 0.2em;
	/* 	box-shadow: 0 0 5px #000000; */
	/* 	width: 30%; */
	/* 	height: 15%; */
	/* 	margin-top: 10%; */
	/* 	margin-right: 60%; */
}

.panel {
	/* 	float: left; */
	/* 	border: 2px solid #0078ae; */
	/* 	border-radius: 5px; */
	/* 	width: 50%; */
	
}

.message-area {
	height: 65%;
	resize: none;
	box-sizing: border-box;
	overflow: auto;
	background-color: #ffffff;
}

.input-area {
	background: teal;
	height: 20%;
	/* 	box-shadow: inset 0 0 10px #00568c; */
}

.input-area input {
	margin: 0.5em 0em 0.5em 0.5em;
}

.text-field {
	/*  	border: 1px solid grey;  */
	padding: 0.2em;
	/* 	box-shadow: 0 0 5px #000000; */
}

#inputMessage {
	min-width: 50%;
	max-width: 60%;
}

#chatFriendsList {
	/* 	float: right; */
	/* 	width: 50%; */
	background: lightgrey;
}

.friendLabel {
	/*  	float: right;  */
	/*  	width: 50%;  */
	padding: 5%;
	margin-top: 5px;
	background-color: whitesmoke;
}

.friendStatus {
	display: inline-block;
	width: 53px;
	height: 53px;
	border-radius: 50%;
	vertical-align: middle;
	position: relative;
}

.friendIcon {
	width: 45px;
	height: 45px;
	border-radius: 50%;
	overflow: hidden;
	position: absolute;
	top: 0;
	bottom: 0;
	right: 0;
	left: 0;
	margin: auto;
	top: 0;
}

.-online {
	background-color: lime;
}

.-offline {
	background-color: lightgrey;
}

.message-area ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

.message-area ul li {
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
	font-size: 13px;
	color: lightgrey;
	background: white;
	padding: 0;
	margin-bottom: 3px;
}

.notify {
	color: red;
}

.-off {
	visibility: hidden;
}
</style>
</head>
<body>
	<!-- 	chat status changing button -->
	<!-- 	<div class="btn-group btn-group-sm no-arrow" -->
	<!-- 		style="position: absolute; top: 0; right: 0;"> -->
	<!-- 		<button id="btnGroupDrop1" type="button" -->
	<!-- 			class="btn btn-secondary dropdown-toggle" data-toggle="dropdown"> -->
	<!-- 			<i class="fas fa-cog"></i> -->
	<!-- 		</button> -->
	<!-- 		<div class="dropdown-menu"> -->
	<!-- 			<a class="dropdown-item" onclick="changeChatStatus('online')">Online</a> -->
	<!-- 			<a class="dropdown-item" onclick="changeChatStatus('busy')">Busy</a> -->
	<!-- 			<a class="dropdown-item" onclick="changeChatStatus('offline')">Offline</a> -->
	<!-- 		</div> -->
	<!-- 	</div> -->


	<div>
		<!-- 	for chat status use -->
		<!-- 		<div id="selfStatus" style="position: relative; left: 80%;"> -->
		<!-- 			<span style="color: lightgreen">online</span> -->
		<!-- 		</div> -->



		<div id="chatFriendsList">
			<c:forEach var="friend" items="${friends}">
				<div id="${friend.mem_id}" class="friendLabel">
					<div class="friendStatus -offline">
						<div class="friendIcon">
							<img
								src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${friend.mem_id}&which_one=headshot">
						</div>
					</div>
					<span>${friend.user_name}</span><span class="notify -off"> •</span>
				</div>
			</c:forEach>
		</div>
		<div id="chatBox" class="-off">
			<div id="chatTitle">
				<div id="titlePhoto">
					<img src="" alt="N/A">
				</div>
				<span id="friendNameSpan" class="friendNameSpan"></span>
				<button type="button" id="closeChatBox" class="close">
					<span>&times;</span>
				</button>
			</div>
			<div id="messagesArea" class="message-area"></div>
			<div class="input-area">
				<label class="btn btn-secondary"> <input id="sendImage"
					style="display: none;" type="file" accept="image/*"> <i
					class="fa fa-photo"></i>
				</label> <span id="imageName" style="display: none"></span> <input
					type="text" id="inputMessage" class="text-field" placeholder="Message"
					onkeydown="if (event.keyCode == 13) sendMessage();" /> <input
					type="submit" id="sendMessage" class="btn btn-secondary"
					value="Send" onclick="sendMessage();" />
			</div>
		</div>
	</div>






	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	<!-- 	<script -->
	<%-- 		src="<%=request.getContextPath()%>/front-end/chat/js/chat.js"></script> --%>
	<script>
		window.onload = function() {
			connect();
		}
		window.onunload = function() {
			disconnect();
		}
		var MyPoint = "/FriendWS/${member_infoVO.mem_id}";
		// 		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		var webSocket;

		var self = '${member_infoVO.mem_id}';
		var friend = "";
		var friends = document.getElementsByClassName("friendLabel");
		var friendNameSpan = document.getElementById("friendNameSpan");
		var chatFriendsList = document.getElementById("chatFriendsList");
		var messagesArea = document.getElementById("messagesArea");
		var inputMessage = document.getElementById("inputMessage");

		addListener();

		function connect() {
			console.log(endPointURL);
			// create a websocket
			webSocket = new WebSocket(endPointURL);
			webSocket.onopen = function(event) {
				console.log("Connect Success!");
			};

			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("online" === jsonObj.type) {
					// 					if (jsonObj.user === self) {
					// 						document.getElementById("selfStatus").innerHTML = "<span style='color: lightgreen'>online</span>"
					// 					}
					updateFriendStatus(jsonObj);
					// 				} else if ("busy" === jsonObj.type) {
					// 					if (jsonObj.user === self) {
					// 						document.getElementById("selfStatus").innerHTML = "<span style='color: red'>busy</span>"
					// 					} else {
					// 						updateFriendStatus(jsonObj);
					// 					}
					// 				} else if ("offline" === jsonObj.type) {
					// 					if (jsonObj.user === self) {
					// 						document.getElementById("selfStatus").innerHTML = "<span style='color: grey'>offline</span>"
					// 					}
					// 					updateFriendStatus(jsonObj);
				} else if ("new" === jsonObj.type) {
					document.getElementById(jsonObj.user).lastElementChild.classList
							.remove("-off");

				} else if ("sort" === jsonObj.type) {
					// 				console.log(jsonObj);
					for (let i = 0; i < jsonObj.users.length; i++) {
						var sortfriend = document
								.getElementById(jsonObj.users[i]);
						if (chatFriendsList.firstChild) {
							chatFriendsList.insertBefore(sortfriend, chatFriendsList.firstChild);
						} else {
							chatFriendsList.appendChild(sortfriend);
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
						historyData.sender === self ? li.className += 'me'
								: li.className += 'friend';
						historyData.sender === self ? liT.className += 'me time'
								: liT.className += 'friend time';
						if (showMsg.indexOf('data:image/') == 0) {
							li.innerHTML = "<img src=" + showMsg + " style='width:200px;'>";
						} else {
							li.innerHTML = showMsg;
						}
						liT.innerHTML = msgTime.toLocaleString();
						ul.appendChild(li);
						ul.appendChild(liT);
					}
					messagesArea.scrollTop = messagesArea.scrollHeight;

				} else if ("chat" === jsonObj.type) {
					// 判斷是否為當下聊天對象
					if (jsonObj.sender == friend || jsonObj.sender == self) {
						var li = document.createElement('li');
						var liT = document.createElement('li');
						var msgTime = new Date(jsonObj.time);
						jsonObj.sender === self ? li.className += 'me'
								: li.className += 'friend';
						jsonObj.sender === self ? liT.className += 'me time'
								: liT.className += 'friend time';
						if (jsonObj.message.indexOf('data:image/') == 0) {
							li.innerHTML = "<img src=" + jsonObj.message + " style='width:200px;'>";
							console.log(li);
						} else {
							li.innerHTML = jsonObj.message;
						}
						liT.innerHTML = msgTime.toLocaleString();
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
						document.getElementById(jsonObj.sender).lastElementChild.classList
								.remove("-off");
					}

				}
			};

			webSocket.onclose = function(event) {
				console.log("Disconnected!");
				console.log(event)
				// 				connect();
			};
		}

		function disconnect() {
			webSocket.close();
		}

		// 有好友上線或離線就更新列表
		function updateFriendStatus(jsonObj) {
			var users = jsonObj.users;
			if (jsonObj.type === "online") {
				for (let i = 0; i < friends.length; i++) {
					document.getElementById(friends[i].id).children[0].classList
							.remove("-online");
					document.getElementById(friends[i].id).children[0].classList
							.add("-offline");
					for (let j = 0; j < users.length; j++) {
						if (friends[i].id == users[j]) {
							document.getElementById(friends[i].id).children[0].classList
									.remove("-offline");
							document.getElementById(friends[i].id).children[0].classList
									.add("-online");
						}
					}
				}
			}
		}

		// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
		function addListener() {
			chatFriendsList.addEventListener("click", function(e) {
				// 判斷是否有點在好友上
				if (e.target.id != "chatFriendsList") {
					document.getElementById("chatBox").classList.remove("-off");
					// 判斷點擊的區域設定會員編號
					if (e.target.parentNode.id == "chatFriendsList") {
						friend = e.target.id;
					} else if (e.target.parentNode.parentNode.id == "chatFriendsList") {
						friend = e.target.parentNode.id;
					} else {
						friend = e.target.parentNode.parentNode.parentNode.id;
					}
					var friendDiv = document.getElementById(friend);
					friendDiv.lastElementChild.classList.add("-off");
					var friendName = friendDiv.children[1].textContent;
					updateFriendName(friendName);
					updateFriendImg(friend);
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

		function updateFriendName(name) {
			friendNameSpan.innerHTML = name;
		}

		function updateFriendImg(friend) {
			document.getElementById("titlePhoto").children[0].src = 
				document.getElementById(friend).children[0].children[0].children[0].src;
		}

		function sendMessage() {
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
				imageName.innerText = "";
				imageName.style.display = "none";
				inputMessage.style.display = "inline";
				inputMessage.value = "";
				inputMessage.focus();
			}
		}

		var sendImage = document.getElementById("sendImage");
		var imageName = document.getElementById("imageName");
		sendImage.addEventListener("change", function(e) {
			if (this.files.length == 0) {
				imageName.innerText = "";
				imageName.style.display = "none";
				inputMessage.value = "";
				inputMessage.style.display = "inline";
				inputMessage.focus();
			} else {
				var showImageName = this.files[0].name;
				let reader = new FileReader();
				reader.readAsDataURL(this.files[0]);
				reader.addEventListener("load", function(e) {
					inputMessage.value = reader.result;
					inputMessage.style.display = "none";
					imageName.innerText = showImageName;
					imageName.style.display = "inline";
				});
			}
		});

		// change online status, not finished...
// 		function changeChatStatus(status) {
// 			fetch("ActsChatStatus?mem_id=${member_infoVO.mem_id}&status="
// 					+ status + "");
// 			var friendsMemid = [];
// 			for (let i = 0; i < friends.length; i++) {
// 				friendsMemid.push(friends[i].id);
// 			}
// 			var jsonObj = {
// 				"type" : status,
// 				"user" : self,
// 				"users" : friendsMemid
// 			};
// 			webSocket.send(JSON.stringify(jsonObj));
// 		}

		document.getElementById("closeChatBox").onclick = function() {
			document.getElementById("chatBox").classList.add("-off");
			friend = "";
		};
	</script>
</body>
</html>