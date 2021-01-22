$("#pwd_original").blur(function() {
	var pwd_original = $("#pwd_original").val();
	var obj = {
		"action" : "pwd_update",
		"pwd_original" : pwd_original
	}
	console.log("123");
	$.ajax({
		url : "/TEA102G2/Member_InfoServlet",
		type : "POST",
		data : obj,
		dataType:"JSON",

		success : function(result) {
			console.log(result.final)
            if(result.final=="error"){
            	$("#pwd_original_span").attr("style","color:red")
                $("#pwd_original_span").text("密碼輸入錯誤")
                console.log("1231231")
            }else if(result.final=="success"){
            	$("#pwd_original_span").attr("style","color:#00B2B2")
                $("#pwd_original_span").text("正確符合")
                console.log("321")
            }
		},
		error : function(err) {
		}
	})
})
$("#submit_btn").on("click", function () {
            var pwd_update_comfirm = $("#pwd_update_comfirm").val();

            var pwd_update = $("#pwd_update").val();

            if (pwd_update_comfirm == pwd_update) {
            	$("#pwd_update_span").attr("style","color:#00B2B2")
                $("#pwd_update_span").text("相符");
                if ($("#pwd_original_span").text() == "正確符合") {
                	$("#all_span").attr("style","color:green")
                    $("#all_span").text("密碼更改成功")

                    let obj={
                        "action":"pwd_update_comfirm",
                        "pwd_update":pwd_update,
                    }

                    $.ajax({
                		url : "/TEA102G2/Member_InfoServlet",
                        type: "POST",
                        data: obj,
                        dataType: "JSON",

                        success: function (result) {
                        	if(result.final="success"){
                        		window.location.replace("/TEA102G2/front-end/login.jsp");
                        		alert("密碼更改成功,請重新登入");
                        	}
                        },
                        error: function (err) {
                        }
                    })

                } else {
                	$("#all_span").attr("style","color:red")
                    $("#all_span").text("請確認舊密碼是否正確")
                }
            } else {
            	$("#pwd_update_span").attr("style","color:red")
                $("#pwd_update_span").text("二次密碼輸入不符");
            }

        })