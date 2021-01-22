//window.onload = function() {
//	connect();
//}
//window.onunload = function() {
//	disconnect();
//}
//var MyPoint = "/FriendWS/${member_infoVO.mem_id}";
//var host = window.location.host;
//var path = window.location.pathname;
//var webCtx = path.substring(0, path.indexOf('/', 1));
//var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
//var webSocket;
//
//var friendName = document.getElementById("friendName");
//var messagesArea = document.getElementById("messagesArea");
//var self = '${member_infoVO.mem_id}';
//var friends = document.getElementsByClassName("fLabel");
//var friend = "";
//
//addListener();
//
//function connect() {
//	console.log(endPointURL);
//	// create a websocket
//	webSocket = new WebSocket(endPointURL);
//
//	webSocket.onopen = function(event) {
//		console.log("Connect Success!");
//	};
//
//	webSocket.onmessage = function(event) {
//		var jsonObj = JSON.parse(event.data);
//		if ("online" === jsonObj.type) {
//
//			if (jsonObj.user === self) {
//				document.getElementById("selfStatus").innerHTML = "<span style='color: lightgreen'>online</span>"
//			}
//			updateFriendStatus(jsonObj);
//
//		} else if ("busy" === jsonObj.type) {
//			if (jsonObj.user === self) {
//				document.getElementById("selfStatus").innerHTML = "<span style='color: red'>busy</span>"
//			} else {
//				updateFriendStatus(jsonObj);
//			}
//
//		} else if ("offline" === jsonObj.type) {
//			if (jsonObj.user === self) {
//				document.getElementById("selfStatus").innerHTML = "<span style='color: grey'>offline</span>"
//			}
//			updateFriendStatus(jsonObj);
//
//		} else if ("new" === jsonObj.type) {
//			document.getElementById(jsonObj.user).lastElementChild.classList
//					.remove("-off");
//
//		} else if ("sort" === jsonObj.type) {
//			// console.log(jsonObj);
//			for (let i = 0; i < jsonObj.users.length; i++) {
//				var fList = document.getElementById("fList");
//				var sortfriend = document.getElementById(jsonObj.users[i]);
//				if (fList.firstChild) {
//					fList.insertBefore(sortfriend, fList.firstChild);
//				} else {
//					fList.appendChild(sortfriend);
//				}
//			}
//
//		} else if ("history" === jsonObj.type) {
//			messagesArea.innerHTML = '';
//			var ul = document.createElement('ul');
//			ul.id = "area";
//			messagesArea.appendChild(ul);
//			// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
//			var messages = JSON.parse(jsonObj.message);
//			for (let i = 0; i < messages.length; i++) {
//				var historyData = JSON.parse(messages[i]);
//				var showMsg = historyData.message;
//				var msgTime = new Date(historyData.time);
//				var li = document.createElement('li');
//				var liT = document.createElement('li');
//				// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
//				historyData.sender === self ? li.className += 'me'
//						: li.className += 'friend';
//				historyData.sender === self ? liT.className += 'me time'
//						: liT.className += 'friend time';
//				if (showMsg.indexOf('data:image/') == 0) {
//					li.innerHTML = "<img src=" + showMsg
//							+ " style='width:200px;'>";
//				} else {
//					li.innerHTML = showMsg;
//				}
//				liT.innerHTML = msgTime.toLocaleString();
//				ul.appendChild(li);
//				ul.appendChild(liT);
//			}
//			messagesArea.scrollTop = messagesArea.scrollHeight;
//
//		} else if ("chat" === jsonObj.type) {
//			if (jsonObj.sender == friend || jsonObj.sender == self) {
//				var li = document.createElement('li');
//				var liT = document.createElement('li');
//				var msgTime = new Date(jsonObj.time);
//				jsonObj.sender === self ? li.className += 'me'
//						: li.className += 'friend';
//				jsonObj.sender === self ? liT.className += 'me time'
//						: liT.className += 'friend time';
//				if (jsonObj.message.indexOf('data:image/') == 0) {
//					li.innerHTML = "<img src=" + jsonObj.message
//							+ " style='width:200px;'>";
//					console.log(li);
//				} else {
//					li.innerHTML = jsonObj.message;
//				}
//				liT.innerHTML = msgTime.toLocaleString();
//				// console.log(msgTime.toLocaleString());
//				document.getElementById("area").appendChild(li);
//				document.getElementById("area").appendChild(liT);
//				messagesArea.scrollTop = messagesArea.scrollHeight;
//
//				var jsonObj = {
//					"type" : "read",
//					"sender" : self,
//					"receiver" : friend,
//					"message" : "",
//					"time" : Date.now(),
//					"read" : ""
//				};
//				// setInterval(function(){
//				webSocket.send(JSON.stringify(jsonObj));
//				// }, 500);
//			} else if (document.querySelector("#" + jsonObj.sender) != null) {
//				document.getElementById(jsonObj.sender).lastElementChild.classList
//						.remove("-off");
//			}
//
//		}
//	};
//
//	webSocket.onclose = function(event) {
//		console.log("Disconnected!");
//		connect();
//	};
//}
//
//// 有好友上線或離線就更新列表
//function updateFriendStatus(jsonObj) {
//	var users = jsonObj.users;
//	if (jsonObj.type === "online" || jsonObj.type === "offline") {
//		for (let i = 0; i < friends.length; i++) {
//			document.getElementById(friends[i].id).children[0].classList
//					.remove("-online");
//			document.getElementById(friends[i].id).children[0].classList
//					.add("-offline");
//			for (let j = 0; j < users.length; j++) {
//				if (friends[i].id == users[j]) {
//					document.getElementById(friends[i].id).children[0].classList
//							.remove("-offline");
//					document.getElementById(friends[i].id).children[0].classList
//							.add("-online");
//				}
//			}
//		}
//	}
//}
//
//// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
//function addListener() {
//	var container = document.getElementById("fList");
//	container.addEventListener("click", function(e) {
//		// 判斷是否有點在好友上
//		if (e.target.id != "fList") {
//			document.getElementById("chatBox").classList.remove("-off");
//			// 判斷點擊的區域設定會員編號
//			if (e.target.parentNode.id == "fList") {
//				friend = e.target.id;
//			} else if (e.target.parentNode.parentNode.id == "fList") {
//				friend = e.target.parentNode.id;
//			} else {
//				friend = e.target.parentNode.parentNode.parentNode.id;
//			}
//			var fDiv = document.getElementById(friend);
//			fDiv.lastElementChild.classList.add("-off");
//			var fName = fDiv.children[1].textContent;
//			updateFriendName(fName);
//			var jsonObj = {
//				"type" : "history",
//				"sender" : self,
//				"receiver" : friend,
//				"message" : "",
//				"time" : Date.now(),
//				"read" : ""
//			};
//			webSocket.send(JSON.stringify(jsonObj));
//		}
//	});
//}
//
//function sendMessage() {
//	var inputMessage = document.getElementById("message");
//	var message = inputMessage.value.trim();
//	if (message === "") {
//		alert("Input a message");
//		inputMessage.focus();
//	} else if (friend === "") {
//		alert("Choose a friend");
//	} else {
//		var jsonObj = {
//			"type" : "chat",
//			"sender" : self,
//			"receiver" : friend,
//			"message" : message,
//			"time" : Date.now(),
//			"read" : "no"
//		};
//		webSocket.send(JSON.stringify(jsonObj));
//		document.getElementById("filename").innerText = "";
//		document.getElementById("filename").style.display = "none";
//		inputMessage.style.display = "inline";
//		inputMessage.value = "";
//		inputMessage.focus();
//	}
//}
//
//function updateFriendName(name) {
//	friendName.innerHTML = name;
//}
//
//function disconnect() {
//	webSocket.close();
//}
//
//// change online status, not finished...
//function changeChatStatus(status) {
//	fetch("ActsChatStatus?mem_id=${member_infoVO.mem_id}&status=" + status + "");
//	var friendsMemid = [];
//	for (let i = 0; i < friends.length; i++) {
//		friendsMemid.push(friends[i].id);
//	}
//	var jsonObj = {
//		"type" : status,
//		"user" : self,
//		"users" : friendsMemid
//	};
//	webSocket.send(JSON.stringify(jsonObj));
//}
//
//document.getElementById("upload").addEventListener("change", function(e) {
//	if (this.files.length == 0) {
//		document.getElementById("filename").innerText = "";
//		document.getElementById("filename").style.display = "none";
//		document.getElementById("message").value = "";
//		document.getElementById("message").style.display = "inline";
//		document.getElementById("message").focus();
//	} else {
//		var filename = this.files[0].name;
//		let reader = new FileReader();
//		reader.readAsDataURL(this.files[0]);
//		reader.addEventListener("load", function(e) {
//			document.getElementById("message").value = reader.result;
//			document.getElementById("message").style.display = "none";
//			document.getElementById("filename").innerText = filename;
//			document.getElementById("filename").style.display = "inline";
//			// var filenameLabel = document.createElement("span");
//			// filenameLabel.id = "filename";
//			// filenameLabel.innerText = filename;
//			// var inputArea = document.getElementsByClassName("input-area")[0]
//			// inputArea.insertBefore(filenameLabel, inputArea.children[1]);
//
//		});
//	}
//
//});
//
//document.getElementById("closeChatBox").onclick = function() {
//	document.getElementById("chatBox").classList.add("-off");
//	friend = "";
//};