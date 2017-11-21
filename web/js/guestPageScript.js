
$(document).ready(function(){
    $('#refresher').on('inview', function (event, isInView) {
	if(isInView){
	    loadPosts();
	    refresher = setInterval(loadPosts, 20000);
	}else{
	    clearTimeout(refresher);
	}
    });
    
    followcheck()
    
});

function loadPosts(){
    var login = $("#owner").attr("login");
    var lastMeow = $(".meow").first().attr("meow");
     $.get("../meows", { target: "own", login: login, from: lastMeow }, function (data, status) {
	if (data) {
	    console.log(data);
	    var posts = eval(data);
	    for (var i = 0; i < posts.length; i++) {
		var post = posts[i];
		$("#nomeows").remove();
		displayMeow(formatMeow(post.name, post.login, post.id, post.text));
	    }
	}
    });
}
function follow(){
    var follow = $("#owner").attr("login");
    $.post("../account", { action: "follow", login: follow }, function(data, status){
	if(data == "true"){
	    followed();
	}
    });
}
function unfollow(){
    var unfollow = $("#owner").attr("login");
    $.post("../account", { action: "unfollow", login: unfollow }, function(data, status){
	if(data == "true"){
	    unfollowed();
	}
    });
}
function followed(){
    var followingButton = '<button id="following" class="btn btn-success followbutton" onclick="return unfollow()">Following</button>';
    $("#follow").replaceWith(followingButton);
    $("#following").hover(
    function(){
	$("#following").removeClass("btn-success");
	$("#following").addClass("btn-danger");
	$("#following").html("Unfollow");
    },
    function(){
	$("#following").removeClass("btn-danger");
	$("#following").addClass("btn-success");
	$("#following").html("Following");
    });
}
function unfollowed(){
    var followButton = '<button id="follow" class="btn btn-default followbutton" onclick="return follow()">Follow</button>';
    $("#following").replaceWith(followButton);
}
function followcheck(){
    var check = $("#owner").attr("login");
    $.post("../account", { action: "followcheck", login: check }, function(data, status){
	if(data == "true"){
	    followed();
	}
    });
}
