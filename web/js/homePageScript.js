
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
	    var premeows = [];
	    premeows = data.split('\n');
	    for (var i = 0; i < premeows.length - 1; i++) {
		var tempmeow = premeows[i].split(" ");
		var id = tempmeow[0];
		var login = tempmeow[1];
		var fullname = tempmeow[2] + " " + tempmeow[3];
		var text = tempmeow[4];
		for (var j = 5; j < tempmeow.length; j++) {
		    text += " " + tempmeow[j];
		}
		displayMeow(formatMeow(fullname, login, id, text));
	    }
	}
    });
}
