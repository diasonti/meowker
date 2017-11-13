<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../css/homestyle.css" rel="stylesheet" type="text/css"/>
    <%@page import="util.Utility" %>
    <%@page import="entities.User" %>
</head>
<body>
<%
    User loggedin = Utility.getSession(request);
    if (loggedin == null) {
	response.sendRedirect("../nav");
    }
    String ownerslogin = request.getParameter("u");
    if(loggedin.getLogin().equals(ownerslogin) || ownerslogin == null){
	response.sendRedirect("../nav");
    }else{
	User owner = Utility.getUser(ownerslogin);
	request.getSession().setAttribute("owner", owner);
    }
%>
<jsp:useBean id="owner" scope="session" class="entities.User" />
<span id='owner' login='<%= ownerslogin %>' style='display: none'></span>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="../nav">(=^･ω･^=)</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="../nav"><span class="glyphicon glyphicon-home"></span> Home</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
		<li>
		    <div class="input-group searchBox">
			<input id='search-field' type="text" class="form-control" placeholder="Search">
			<span class="input-group-btn">
			    <button id='search-button' class="btn btn-default" type="button">
				<i class="glyphicon glyphicon-search"></i>
				&nbsp;
			    </button>
			</span>
		    </div>
		</li>
                <li><a href="../account?action=signout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="container-fluid">
            <div class="container-fluid center-block outterwrapper">
                <div class="col-md-2 sidenav sidewrapper">

                    <ul class="list-group">
                        <li class="list-group-item bg-tabby"></li>
                        <li class="list-group-item">
                            <strong><jsp:getProperty name="owner" property="fullName" /></strong><br/>
                            @<jsp:getProperty name="owner" property="login" /><br/>
                            <span class="text-muted"></span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item text-left">
                            <h5><b>About</b></h5>
                        </li>
                    </ul>

                </div>
                <div class="col-md-8 text-left mainwrapper">

                    <ul class="list-group posts">
                        <span id="refresher"></span>
			<span class="meow" meow="0" style="display: none"></span>
			<li class="list-group-item">
			    <div class="list-group-item-text">
				User did not say meow yet :(
			    </div>
			</li>
                    </ul>
                </div>
                <div class="col-md-2 sidenav sidewrapper">

                    <ul class="list-group">
                        <li class="list-group-item">&copy; 2017 Vladimir Danilov</li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="../js/libs/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="../js/libs/bootstrap.min.js" type="text/javascript"></script>
<script src="../js/libs/jquery.inview.min.js" type="text/javascript"></script>
<script src="../js/meow.js" type="text/javascript"></script>
<script src="../js/guestPageScript.js" type="text/javascript"></script>
<script src="../js/searchField.js" type="text/javascript"></script>
</body>
</html>
