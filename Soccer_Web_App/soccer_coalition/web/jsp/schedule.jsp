<%--
    Document   : schedule
    Created on : Mar 21, 2018, 09:10:13 AM
    Author     : carlitos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%@page import="util.Database" %>

<%
    Connection connection = Database.getConnection();
%>

    <table class="table table-striped" align="center">
        <tr>
            <th>Day</th>
            <th>Date</th>
            <th>Time</th>
            <th>Location</th>
            <th>Home</th>
            <th>Away</th>
        </tr>
        <%
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();

            //String myQuery = "SELECT * FROM GAME where comment!='BRACKET' AND played=0;";
            String myQuery = "SELECT * FROM GAME WHERE played=0 AND comment!='BRACKET';";
            ResultSet games = statement1.executeQuery(myQuery);
            String myQuery2 = "SELECT * FROM TEAM;";
            ResultSet teams = statement2.executeQuery(myQuery2);
         %>
         <%ArrayList<String> teamlist = new ArrayList<String>();
         while(teams.next()){
           teamlist.add(teams.getString("teamName"));
         }
         %>
         <%while(games.next()) {
             String hometeam = (String)teamlist.get(games.getInt("home")-1);
             String awayteam = (String)teamlist.get(games.getInt("away")-1);
             String location = games.getString("location");
             SimpleDateFormat ft = new SimpleDateFormat ("MM/dd");
             String date = ft.format(games.getDate("gameDate"));
             ft = new SimpleDateFormat ("HH:mm");
             String time = ft.format(games.getTime("gameDate"));
             ft = new SimpleDateFormat ("E");
             String day = ft.format(games.getDate("gameDate"));
             //String day = games.getDate("gameDate");
         %>

        <tr>
            <td><%=day%></td> <!-- need work -->
            <td><%=date%></td> <!-- need work -->
            <td><%=time%></td> <!-- need work -->
            <td><%=location%></td>
            <td><%=hometeam%></td>
            <td><%=awayteam%></td>
        </tr>

         <%}
         Database.close(connection);
         %>
    </table>
