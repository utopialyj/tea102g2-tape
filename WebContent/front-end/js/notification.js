if ("${member_infoVO.mem_id}" != "") {
	window.onload = function() {
		connectNTF();
		connectChat();
		connectActs();
	}
	window.onunload = function() {
		disconnectNTF();
		disconnectChat();
		disconnectActs();
	}
}

var MyPointNTF = "/NotifyWS/${member_infoVO.mem_id}";
var pathNTF = window.location.pathname;
var webCtxNTF = pathNTF.substring(0, pathNTF.indexOf('/', 1));
var endPointURLNTF = "ws://" + window.location.host + webCtxNTF + MyPointNTF;

var webSocketNTF;

function connectNTF() {
	// create a websocket
	webSocketNTF = new WebSocket(endPointURLNTF);
	console.log(endPointURLNTF);
	webSocketNTF.onopen = function(event) {
		console.log("NTF Connect Success!");
	};

	webSocketNTF.onmessage = function(event) {
		var jsonObj = JSON.parse(event.data);

		if (0 === jsonObj.type) {
			var notificationArea = document.getElementById("notificationArea");
			var allNTF = document.getElementById("allNTF");
			allNTF.innerHTML = '';
			// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
			var notifications = JSON.parse(jsonObj.message);
			// console.log(notifications)

			for (var i = 0; i < notifications.length; i++) {
				var aNotification = notifications[i];
				var aNTFJson = JSON.parse(aNotification);
				var aNTFTime = new Date(aNTFJson.time);

				var liNTF = document.createElement('li');
				var liNTFT = document.createElement('li');
				liNTFT.className += 'NTFTime';
				// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
				aNTFJson.read === "no" ? liNTF.className += 'mark'
						: liNTF.className += 'unmark';
				
				
				
				
				
				// 通知超連結設定
				var aTag = document.createElement('a');
				var test = aNTFJson.url;
//				aTag.target = "_blank";
				aTag.style = "color: white;"
				aTag.href = test;
				
				aTag.innerText = aNTFJson.message;
				

				
				
				
				
				
				
				
				liNTFT.innerHTML = aNTFTime.toLocaleString();
				
				liNTF.appendChild(aTag);
				
//				console.log(liNTF);
				
				
				if (allNTF.firstChild) {
					allNTF.insertBefore(document.createElement('hr'),
							allNTF.firstChild);
					allNTF.insertBefore(liNTFT, allNTF.firstChild);
					allNTF.insertBefore(document.createElement('br'),
							allNTF.firstChild);
					allNTF.insertBefore(liNTF, allNTF.firstChild);
				} else {
					allNTF.appendChild(liNTF);
					allNTF.appendChild(document.createElement('br'));
					allNTF.appendChild(liNTFT);
					allNTF.appendChild(document.createElement('hr'));
				}

				document.getElementById("badgeUnread").classList.add("-hide");
			}
		} else if (9 === jsonObj.type) {
			document.getElementById("badgeUnread").innerHTML = jsonObj.message;
			document.getElementById("badgeUnread").classList.remove("-hide");
		}

	};

	webSocketNTF.onclose = function(event) {
		console.log("NTF Disconnected!");
	};
}

function disconnectNTF() {
	webSocketNTF.close();
}

var btnNTF = document.getElementById("notificationDropdown");
btnNTF.addEventListener("click", function() {
	if (this.parentNode.classList.contains("show") == false) {
		var jsonObj = {
			"type" : 0,
			"sender" : "",
			"receiver" : "${member_infoVO.mem_id}",
			"postid" : "",
			"actid" : "",
			"message" : "",
			"time" : Date.now(),
			"read" : "yes"
		};
		webSocketNTF.send(JSON.stringify(jsonObj));
	}
});














//聊天室js 暫時寫在這裡
//window.onload = function() {
//	connectChat();
//}
//window.onunload = function() {
//	disconnectChat();
//}
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

function connectChat() {
	console.log(endPointURL);
	// create a websocket
	webSocket = new WebSocket(endPointURL);
	webSocket.onopen = function(event) {
		console.log("Chatroom Connect Success!");
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
					
					var filenameIndex = showMsg.lastIndexOf('filename');
					var imageBase64 = showMsg.slice(0, filenameIndex);
					var filename = showMsg.slice(filenameIndex + 8);
					
					var aLink = document.createElement('a');
					aLink.href = imageBase64;
					aLink.download = filename;
					aLink.innerHTML = "<img src=" + imageBase64 + " style='width:200px;'>";
					li.appendChild(aLink);
					
				} else if (showMsg.indexOf('data:') == 0){
					
					var filenameIndex = showMsg.lastIndexOf('filename');
					var fileBase64 = showMsg.slice(0, filenameIndex);
					var filename = showMsg.slice(filenameIndex + 8);
					
					var aLink = document.createElement('a');
					aLink.href = fileBase64;
					aLink.download = filename;
					aLink.innerText = filename;
					li.appendChild(aLink);
					
					
				} else {
					li.innerHTML = showMsg;
				}
				liT.innerHTML = msgTime.toLocaleString();
				ul.appendChild(li);
				ul.appendChild(liT);
			}
			messagesArea.scrollTop = messagesArea.scrollHeight;

		} else if ("chat" === jsonObj.type) {
			// 判斷是否為當下聊天對象或自己
			if (jsonObj.sender == friend || jsonObj.sender == self) {
				var li = document.createElement('li');
				var liT = document.createElement('li');
				var msgTime = new Date(jsonObj.time);
				jsonObj.sender === self ? li.className += 'me'
						: li.className += 'friend';
				jsonObj.sender === self ? liT.className += 'me time'
						: liT.className += 'friend time';
				if (jsonObj.message.indexOf('data:image/') == 0) {
					
					
					var filenameIndex = jsonObj.message.lastIndexOf('filename');
					var imageBase64 = jsonObj.message.slice(0, filenameIndex);
					var filename = jsonObj.message.slice(filenameIndex + 8);
					
					var aLink = document.createElement('a');
					aLink.href = imageBase64;
					aLink.download = filename;
					aLink.innerHTML = "<img src=" + imageBase64 + " style='width:200px;'>";
					li.appendChild(aLink);
					
//					li.innerHTML = "<img src=" + jsonObj.message + " style='width:200px;'>";
//					console.log(li);
				} else if (jsonObj.message.indexOf('data:') == 0) {
					
					var filenameIndex = jsonObj.message.lastIndexOf('filename');
					var fileBase64 = jsonObj.message.slice(0, filenameIndex);
					var filename = jsonObj.message.slice(filenameIndex + 8);
					
					var aLink = document.createElement('a');
					aLink.href = fileBase64;
					aLink.download = filename;
					aLink.innerText = filename;
					li.appendChild(aLink);
					
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
				
			// 若非當下聊天視窗的好友好友傳訊過來, 則顯示有新訊息
			} else if (document.querySelector("#" + jsonObj.sender) != null) {
				document.getElementById(jsonObj.sender).lastElementChild.classList
						.remove("-off");
			}

		}
	};

	webSocket.onclose = function(event) {
		console.log("Chatroom Disconnected!");
		console.log(event)
		connectChat();
	};
}

function disconnectChat() {
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
			let base64WithName = reader.result + "filename" + showImageName;
			inputMessage.value = base64WithName;
			inputMessage.style.display = "none";
			imageName.innerText = showImageName;
			imageName.style.display = "inline";
		});
	}
});

// change online status, not finished...
//	function changeChatStatus(status) {
//		fetch("ActsChatStatus?mem_id=${member_infoVO.mem_id}&status="
//				+ status + "");
//		var friendsMemid = [];
//		for (let i = 0; i < friends.length; i++) {
//			friendsMemid.push(friends[i].id);
//		}
//		var jsonObj = {
//			"type" : status,
//			"user" : self,
//			"users" : friendsMemid
//		};
//		webSocket.send(JSON.stringify(jsonObj));
//	}

document.getElementById("closeChatBox").onclick = function() {
	document.getElementById("chatBox").classList.add("-off");
	friend = "";
};










// 揪團群聊js
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

addListenerActs();

function connectActs() {
	// create a websocket
	webSocketActs = new WebSocket(endPointURLActs);

	webSocketActs.onopen = function(event) {
		console.log("ActsChat Connect Success!");
	};

	webSocketActs.onmessage = function(event) {
		var jsonObj = JSON.parse(event.data);
		if ("history" === jsonObj.type) {
//			console.log(jsonObj);
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

				document.getElementById("chatActsList").insertBefore(
						document.getElementById(actid),
						document.getElementById("chatActsList").firstChild);

			}
		}
	};

	webSocketActs.onclose = function(event) {
		console.log("ActsChat Disconnected!");
		connectActs
	};
}

// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
function addListenerActs() {
	var container = document.getElementById("chatActsList");
	container.addEventListener("click", function(e) {
		if (e.target.id != "chatActsList") {
			document.getElementById("chatBoxActs").classList.remove("-off");
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
	var inputMessageActs = document.getElementById("inputMessageActs");
//	console.log(inputMessageActs);
	var messageActs = inputMessageActs.value.trim();
	if (messageActs === "") {
		alert("Input a message");
		inputMessageActs.focus();
	} else if (actid === "") {
		alert("Choose a activity");
	} else {
		var jsonObj = {
			"type" : "chat",
			"activity" : actid,
			"sender" : selfActs,
			"message" : messageActs,
			"time" : Date.now()
		};
		webSocketActs.send(JSON.stringify(jsonObj));
		inputMessageActs.value = "";
		inputMessageActs.focus();
	}
}

function updateActsName(name) {
	actsNameSpan.innerHTML = name;
}

function disconnectActs() {
	webSocketActs.close();
}


document.getElementById("closeChatBoxActs").onclick = function() {
	document.getElementById("chatBoxActs").classList.add("-off");
	actid = "";
};