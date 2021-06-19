<%--
    Document   : teams
    Created on : Mar 20, 2018, 11:51:13 PM
    Author     : grime
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.Database"%>

<%
    Connection connection = Database.getConnection();
%>

    <table class="table table-striped" align="center">
        <tr>
            <th>Position&nbsp;&nbsp;</th>
            <th>Team Name</th>
        </tr>
        <%
            Statement statement = connection.createStatement();

            String myQuery = "SELECT * FROM TEAM ORDER BY points DESC, gDiff DESC, gFavor DESC, won DESC, played ASC;";
            ResultSet teams = statement.executeQuery(myQuery);
         %>

         <%int count = 0;
         while(teams.next()) {
             String teamName = teams.getString("teamName");
             String teamID = Integer.toString(teams.getInt("teamID"));
             count += 1;
         %>
        <tr>
            <td><%=count%></td> <!-- taem position tbd later -->
            <td>(Team <%=teamID%>) <%=teamName%></td>
        </tr>

         <%}
         Database.close(connection);
         %>
    </table>
