
$(document).ready(function(){
    
    $("#search-button").click(function () {
	var query = $("#search-field").val();
	if (query == "") {
	    $(".searchBox").popover('show');
	    return;
	}else{
	    window.location.href = "../nav?u=" + query;
	}
    });
    $("#search-field").change(function () {
	$(".searchBox").popover('hide');
    });
    
});
