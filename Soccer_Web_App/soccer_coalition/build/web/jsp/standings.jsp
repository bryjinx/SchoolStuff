<%--
    Document   : standings
    Created on : Mar 22, 2018, 11:16:43 AM
    Author     : carlitos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.Database" %>

<%
    Connection connection = Database.getConnection();
%>
<!--
<form action="UpdateTeams" id="sendIt" method="post"></form>
<button class="right-button" type="submit" form="sendIt" value="Submit">Update Teams</button>
-->
    <table class="table table-striped" align="center">
    	<thead>
	        <tr>
	            <th>Position</th>
	            <th>Team Name</th>
	            <th>P</th>
              <th>W</th>
              <th>D</th>
              <th>L</th>
              <th>GF</th>
              <th>GA</th>
              <th>GD</th>
              <th>Pts</th>
	        </tr>
	    </thead>
	    <tbody>
	    <%
            Statement statement = connection.createStatement();

            String myQuery = "SELECT * FROM TEAM ORDER BY points DESC, gDiff DESC, gFavor DESC, won DESC, played ASC;";
            ResultSet teams = statement.executeQuery(myQuery);
         %>

         <%
          int count = 0;
          while(teams.next()) {
             String teamName = teams.getString("teamName");
             String p = Integer.toString(teams.getInt("played"));
             String w = Integer.toString(teams.getInt("won"));
             String d = Integer.toString(teams.getInt("draw"));
             String l = Integer.toString(teams.getInt("lose"));
             String gf = Integer.toString(teams.getInt("gFavor"));
             String ga = Integer.toString(teams.getInt("gAgainst"));
             String gd = Integer.toString(teams.getInt("gDiff"));
             String pts = Integer.toString(teams.getInt("points"));

             count += 1;
         %>
        	<tr>
            <th><%=count%></th>
            <th><%=teamName%></th>
            <th><%=p%></th>
            <th><%=w%></th>
            <th><%=d%></th>
            <th><%=l%></th>
            <th><%=gf%></th>
            <th><%=ga%></th>
            <th><%=gd%></th>
            <th><%=pts%></th>
        	</tr>

         <%}
         Database.close(connection);
         %>
	    </tbody>
    </table>
