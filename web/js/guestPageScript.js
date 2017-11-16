
$(document).ready(function(){
    $('#refresher').on('inview', function (event, isInView) {
	if(isInView){
	    loadPosts();
	    refresher = setInterval(loadPosts, 20000);
	}else{
	    clearTimeout(refresher);
	}
    });
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
		displayMeow(formatMeow(post.name, post.login, post.id, post.text));
	    }
	}
    });
}
