<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO member_infoVO = (Member_InfoVO) session.getAttribute("member_infoVO");

	Map<String, String> actsList = (Map<String, String>) session.getAttribute("actsList");
	Set<String> actsKeySet = (Set<String>)session.getAttribute("actsKeySet");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>ActivityChat</title>
<style>
* {
	margin: auto;
	padding: 0px;
}

html, body {
	font: 15px verdana, Times New Roman, arial, helvetica, sans-serif,
		Microsoft JhengHei;
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

#messageActs {
	min-width: 50%;
	max-width: 60%;
}

.statusOutputActs {
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

#acts {
	float: right;
	width: 50%;
}

.activity {
	float: right;
	width: 50%;
	padding: 5%;
	margin-bottom: 5px;
	background-color: #ffffff;
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

.others {
	background: #eee;
	float: left;
	margin-left: 5px;
}

.names {
	font-size: 12px;
	color: grey;
	background: white;
	padding: 0;
	margin: 0;
}

.me {
	float: right;
	background: #0084ff;
	color: #fff;
	margin-right: 5px;
}

.time {
	font-size: 13px;
	color: lightgrey;
	background: white;
	padding: 0;
	margin-bottom: 5px;
}

.-off {
	visibility: hidden;
}
</style>
</head>
<body onload="connectActs();" onunload="disconnectActs();">
	<div id="acts">

		<c:forEach var="key" items="${actsKeySet}">
			<div id="${key}" class="activity">
				<h2>${actsList[key]}</h2>
			</div>
		</c:forEach>
	</div>
	<h3 id="statusOutputActs" class="statusOutputActs"></h3>
	<div id="messagesAreaActs" class="panel message-area"></div>
	<div class="panel input-area">
		<input id="messageActs" class="text-field" type="text"
			placeholder="Message"
			onkeydown="if (event.keyCode == 13) sendMessageActs();" /> <input
			type="submit" id="sendMessageActs" class="button" value="Send"
			onclick="sendMessageActs();" />
	</div>
	<script>
		var MyPointActs = "/ActsWS/${member_infoVO.user_name}";
		var pathActs = window.location.pathname;
		var webCtxActs = pathActs.substring(0, pathActs.indexOf('/', 1));
		var endPointURLActs = "ws://" + window.location.host + webCtxActs + MyPointActs;
		var webSocketActs;

		var statusOutputActs = document.getElementById("statusOutputActs");
		var messagesAreaActs = document.getElementById("messagesAreaActs");
		var selfActs = '${member_infoVO.user_name}';
		var actid = "";
		// 	const map = ${allMemSet};
		// 	console.log(map);

		addListener();

		function connectActs() {
			// create a websocket
			webSocketActs = new WebSocket(endPointURLActs);

			webSocketActs.onopen = function(event) {
				console.log("ActsChat Connect Success!");
			};

			webSocketActs.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("history" === jsonObj.type) {
					console.log(jsonObj);
					messagesAreaActs.innerHTML = '';
					var ul = document.createElement('ul');
					ul.id = "area";
					messagesAreaActs.appendChild(ul);
					// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
					var messages = JSON.parse(jsonObj.message);
					for (var i = 0; i < messages.length; i++) {
						var historyData = JSON.parse(messages[i]);
						var showMsg = historyData.message;
						var msgTime = new Date(historyData.time);

						// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
						if (historyData.sender == selfActs) {
							var li = document.createElement('li');
							var liT = document.createElement('li');
							li.className += 'me';
							liT.className += 'me time';
							li.innerHTML = showMsg;
							liT.innerHTML = msgTime.toLocaleString();
							ul.appendChild(li);
							ul.appendChild(liT);
						} else {
							var liN = document.createElement('li');
							var li = document.createElement('li');
							var liT = document.createElement('li');
							liN.className += 'others names'
							li.className += 'others';
							liT.className += 'others time';

							liN.innerHTML = historyData.sender;
							li.innerHTML = showMsg;
							liT.innerHTML = msgTime.toLocaleString();
							ul.appendChild(liN);
							ul.appendChild(li);
							ul.appendChild(liT);
						}
						// 					(String)pageContext.getAttribute("otherName").getUser_name()

					}
					messagesAreaActs.scrollTop = messagesAreaActs.scrollHeight;

				} else if ("chat" === jsonObj.type) {
					if (actid == jsonObj.activity || selfActs == jsonObj.sender) {
						if (jsonObj.sender != selfActs) {
							var liN = document.createElement('li');
							liN.className += 'others names';
							liN.innerHTML = jsonObj.sender;
							document.getElementById("area").appendChild(liN);
						}
						var li = document.createElement('li');
						var liT = document.createElement('li');
						var msgTime = new Date(jsonObj.time);
						jsonObj.sender === selfActs ? li.className += 'me'
								: li.className += 'others';
						jsonObj.sender === selfActs ? liT.className += 'me time'
								: liT.className += 'others time';
						li.innerHTML = jsonObj.message;
						liT.innerHTML = msgTime.toLocaleString()
						document.getElementById("area").appendChild(li);
						document.getElementById("area").appendChild(liT);
						messagesAreaActs.scrollTop = messagesAreaActs.scrollHeight;

						document.getElementById("acts").insertBefore(
								document.getElementById(actid),
								document.getElementById("acts").firstChild);

					}
				}
			};

			webSocketActs.onclose = function(event) {
				console.log("ActsChat Disconnected!");
			};
		}

		// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
		function addListener() {
			var container = document.getElementById("acts");
			container.addEventListener("click", function(e) {
				if (e.target.id != "acts") {
					actid = e.target.id == "" ? e.target.parentNode.id
							: e.target.id;
					var actName = e.target.textContent.trim();
					updateActsName(actName);
					var jsonObj = {
						"type" : "history",
						"activity" : actid,
						"sender" : selfActs,
						"message" : "",
						"time" : ""
					};
					webSocketActs.send(JSON.stringify(jsonObj));
				}
			});
		}

		function sendMessageActs() {
			var inputMessage = document.getElementById("messageActs");
			var message = inputMessage.value.trim();
			if (message === "") {
				alert("Input a message");
				inputMessage.focus();
			} else if (actid === "") {
				alert("Choose a activity");
			} else {
				var jsonObj = {
					"type" : "chat",
					"activity" : actid,
					"sender" : selfActs,
					"message" : message,
					"time" : Date.now()
				};
				webSocketActs.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
			}
		}

		function updateActsName(name) {
			statusOutputActs.innerHTML = name;
		}

		function disconnectActs() {
			webSocketActs.close();
		}
	</script>
</body>
</html>