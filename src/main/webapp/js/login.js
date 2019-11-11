$(document).ready(function () {
	$(".txtb input").on("focus",function () {
	    $(this).addClass("focus");
	});
	
	$(".txtb input").on("blur",function () {
	    if($(this).val() == "")
	        $(this).removeClass("focus");
	});
});

function login() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();

    if(username == "" || password == "")
    {
        $("#tip").text("Please enter the correct account information");
    }
    else
    {
    	var params = {username:username, password:password};
        var URL = "http://localhost:8080/RcSystem/LoginServlet";
        
        $.post(URL,params,function (data,status) {
            if(data == 'TRUE')
            {
            	window.location.href = "http://localhost:8080/RcSystem/index.html";
            }
            else
            {
            	$("#tip").text("Incorrect username or password");
            }
        });
    }
}