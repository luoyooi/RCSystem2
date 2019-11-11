$(".txtb input").on("focus",function () {
    $(this).addClass("focus");
})

$(".txtb input").on("blur",function () {
    if($(this).val() == "")
        $(this).removeClass("focus");
});

$(".bottom-txt").hide();

function sigin() {
	
	var email = $("input[name='email']").val();
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    var cfm_password = $("input[name='cfm-password']").val();

    if(username == "" || password == "" || cfm_password == "")
    {
        $("#tip").text("Please enter your registration information");
    }

    if(password != ""&&password != ""&&password != cfm_password)
    {
        $("#tip").text("Inconsistent password");
    }
    else if(password != ""&&password != ""&&password == cfm_password)
    {
    	var params = {username:username, password:password, email:email};
        var URL = "http://localhost:8080/RcSystem/SiginServlet";
        $.post(URL,params,function (data,status) {
            if (data == 'SIGIN_SUCCESS')
            {
            	$("#tip").hide();
            	$(".bottom-txt").css("margin-top","20px").show();
            }
            else if(data == 'SIGIN_FAILED')
            {
            	$("#tip").text("unknown mistake");
            }
            else if(data == 'ACOUNT_EXIST')
            {
            	$("#tip").text("Account already exists");
            }	
        });
    }
    
}