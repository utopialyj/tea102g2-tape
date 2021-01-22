window.onload = function() {
	connectNTF();
}
window.onunload = function() {
	disconnectNTF();
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
		console.log("Connect Success!");
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

				liNTF.innerHTML = aNTFJson.message;
				liNTFT.innerHTML = aNTFTime.toLocaleString();

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
		console.log("Disconnected!");
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