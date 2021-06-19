<%--
    Document   : index
    Created on : Mar 22, 2018, 11:16:43 AM
    Author     : Michael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*" %>
<%@page import="model.User" %>
<%@page import="util.Database" %>

<%
    Connection connection = Database.getConnection();
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NMT Intramural Soccer</title>

    <!-- Bootstrap 4 CDNs. May not need all of these. -->
    <!-- Bootstrap 4 CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <!-- Bootstrap 4 jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Bootstrap 4 Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <!-- Bootstrap 4 JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>


    <!-- page build script -->
    <script src="/soccer/js/build.js"></script>

    <link id="page-css" rel="stylesheet" href="">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Slab:300,400|Roboto:300,400,700"/>

    <link rel="stylesheet" href="/soccer/css/simple-footer.css"/>

    <!--
    <link rel="stylesheet" href="/css/sticky-dark-top-nav-with-dropdown.css"/>
    -->
    <link rel="stylesheet" href="/soccer/css/styles.css"/>
    <link rel="stylesheet" href="/soccer/css/index.css"/>
    <link rel="stylesheet" href="/soccer/css/tabs-Menu.css"/>
  </head>
  <body onload="initPageContent()" class="bg" style="height:100%;">
    <div class="container-fluid">
      <nav id="navbar-main" class="navbar navbar-dark navbar-expand-md" style="background-color: #012063;">
        <a class="navbar-brand" href="#"><span><img src="/soccer/img/soccerballl.png" alt="" style="width:55px;"/>Indoor Soccer Spring 2018</span></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navcol">
          <span class="sr-only">Toggle navigation</span>
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navcol">
          <ul class="nav navbar-nav" id="main-nav">
            <li class="nav-item" id="home-nav"><a class="nav-link" onClick="getPageContent('home')" href="#">HOME</a></li>
            <li class="nav-item" id="results-nav"><a class="nav-link" onClick="getPageContent('results')" href="#">RESULTS</a></li>
            <li class="nav-item" id="schedule-nav"><a class="nav-link right-tab" onClick="getPageContent('schedule')" href="#">CALENDAR</a></li>
            <li class="nav-item" id="standings-nav"><a class="nav-link" onClick="getPageContent('standings')" href="#">STANDINGS</a></li>
            <li class="nav-item" id="statistics-nav"><a class="nav-link" onClick="getPageContent('statistics')" href="#">STATISTICS</a></li>
            <li class="nav-item" id="teams-nav"><a class="nav-link" onClick="getPageContent('teams')" href="#">TEAMS</a></li>
          </ul>
        </div>
      </nav>
     
     <!--
     <div id="page-content" class="" href="#"><span><img src="/soccer/img/soccer_background.jpg;"></div>
     -->
     <!--
     <div id="page-content" class="bg"></div>
      -->
      <div id="page-content"></div>
      
      <div id="footer-main" class="footer-2">
        <div class="container">
          <div class="row">
            <div class="col-sm-6 col-md-6">
              <p class="text-left" style="margin-top:10px;margin-bottom:0px;">© 2018 Soccer Coalition</p>
            </div>
            <div class="col-sm-6 col-md-6">
              <p class="text-right" style="margin-top:10px;margin-bottom:0px;font-size:1em;">NMT Soccer Intramural</p>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <ul id="main-nav" style="list-style-type: none;">
                <%
                    User user = null;
                    if (session != null) {
                        user = (User) session.getAttribute("user");
                    }
                    if (user != null) {
                %>
                <li class="nav-item" id="account-nav">
                  <a class="nav-link" href="#" onClick="getPageContent('account')"style="margin-bottom:0px; font-size: 12px; color: white; float: right;">Manage</a>
                  <a class="nav-link" href="#" onClick="document.forms['logout'].submit(); getPageContent('home')"style="margin-bottom:0px; font-size: 12px; color: white; float: right;">Logout</a>
                  <form method="POST" action="UserController" name="logout">
                    <input type="hidden" name="operation" value="logout"/>
                  </form>
                </li>
                <%	} else {
                %>
                <li class="nav-item" id="login-nav">
                  <a class="nav-link" href="#" onClick="getPageContent('login')"style="margin-bottom:0px; font-size: 12px; color: white; float: right;">Admin Login</a>
                </li>
                <% }
                    Database.close(connection);
                %>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>