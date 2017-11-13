var signinlogin = $("#signinlogin");
var signinpassword = $("#signinpassword");

var signupfullname = $("#signupfullname");
var signuplogin = $("#signuplogin");
var signuppassword = $("#signuppassword");
var loginExists = $("#loginExists");

$(document).ready(function () {
    
    $('[data-toggle="popover"]').popover();
    
    loginExists.hide();
    
    $("#signinlogin").parent().change(function(){
	$("#signinlogin").parent().removeClass("has-error");
    });
    $("#signinpassword").parent().change(function(){
	$("#signinpassword").parent().removeClass("has-error");
    });
    
    $("#signuplogin").parent().change(function(){
	$("#signuplogin").parent().removeClass("has-error");
	loginExists.hide();
    });
    $("#signuppassword").parent().change(function(){
	$("#signuppassword").parent().removeClass("has-error");
    });
    $("#signupfullname").parent().change(function(){
	$("#signupfullname").parent().removeClass("has-error");
    });
    
});


function signin(){
    
    var fine = true;
    if(signinlogin.val() == ""){
	signinlogin.parent().addClass("has-error");
	fine = false;
    }
    if(signinpassword.val() == ""){
	signinpassword.parent().addClass("has-error");
	fine = false;
    }
    if(!fine){
	return false;
    }
    var request = $.post("../account",
		{
		    action: "signin",
		    login: signinlogin.val(),
		    password: encrypt(signinpassword.val())
		},
		function (data, status) {
		    if(data) {
			window.location.href = "../nav";
		    }else{
			$("#wrongcred").modal();
		    }
		    /*
		    if (data == "0") {
			fine = true;
		    }else if(data == "1"){
			fine = false;
			credwrong = true;
		    }
		    */
		});
		request.fail(function(){
		    $("#signuperror").modal();
		});
	    //$("#signuperror").modal();
    return false;
}



function signup(){
    
    var fine = true;
    if(signuplogin.val() == ""){
	signuplogin.parent().addClass("has-error");
	fine = false;
    }
    if(signuppassword.val() == ""){
	signuppassword.parent().addClass("has-error");
	fine = false;
    }
    if(signupfullname.val() == ""){
	signupfullname.parent().addClass("has-error");
	fine = false;
    }
    if(!fine){
	return false;
    }
    var success = false;
    var loginUnique = true;
    $.post("../account",
		{
		    action: "signup",
		    fullname: signupfullname.val(),
		    login: signuplogin.val(),
		    password: encrypt(signuppassword.val())
		},
		function (data, status) {
		    if(data) {
		    $("#signupsuccess").modal();
		    signupfullname.val("");
		    signuplogin.val("");
		    signuppassword.val("");
		} else {
			$("#wrongcred").modal();
		    }
	    });
    if (success) {
	$("#signupsuccess").modal();
	signupfullname.val("");
	signuplogin.val("");
	signuppassword.val("");
    } else {
       if(!loginUnique){
	   $("#loginexists").modal();
	   signuplogin.val("");
	   signuppassword.val("");
       }else{
	   $("#signuperror").modal();
	   signuppassword.val("");
       }
   }
   return false;
}

function encrypt(password){
    return md5(md5(password));
}