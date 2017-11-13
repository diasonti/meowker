function formatMeow(name, login, id, text){
    
    var html = "<li class='list-group-item meow' meow='"+id+"'>";
    html += "<div class='media'><div class='media-body'>";
    html += "<h4 class='media-heading'>"+name+" <a href='../nav?u="+login+"'><small>@"+login+"</small></a></h4>";
    html += "<p>"+text+"</p>";
    html += "</div></div></li>";
    return html;
}

function displayMeow(formattedMeow){
    $("#refresher").after(formattedMeow);
}