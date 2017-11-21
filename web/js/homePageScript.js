
$(document).ready(function(){
    $('#refresher').on('inview', function (event, isInView) {
	if(isInView){
	    loadFeed()
	    refresher = setInterval(loadFeed, 20000);
	}else{
	    clearTimeout(refresher);
	}
    });
});

function sendPost(){
    var text = $("#messagetext").val();
    if (text !== "") {
	$.post("../meows",
		{
		    text: text
		},
		function (data, status) {
		    if (data) {
			$("#messagetext").val("");
			loadFeed();
		    }
		});
    }
    return false;
}

function loadFeed(){
    var lastMeow = $(".meow").first().attr("meow");
     $.get("../meows", { target: "feed", from: lastMeow }, function (data, status) {
	if (data) {
	    console.log(data);
	    var posts = eval(data);
	    for (var i = 0; i < posts.length; i++) {
		var post = posts[i];
		console.log("printed: " + post.text);
		displayMeow(formatMeow(post.name, post.login, post.id, post.text));
	    }
	}
    });
}
