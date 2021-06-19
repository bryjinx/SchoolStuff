
<%@page import="util.Database"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.io.*,java.util.*" %>
<%@page import="javax.servlet.*,java.text.*" %>

<%
    Connection connection = Database.getConnection();
%>

<table class="table table-striped" align="center">
  <tr>
    <th>Date</th>
    <th>Home</th>
    <th>Score</th>
    <th>Away</th>
  </tr>
  <%
      Statement statement1 = connection.createStatement();
      Statement statement2 = connection.createStatement();

      //String myQuery = "SELECT * FROM GAME where comment!='BRACKET' AND played=0;";
      String myQuery = "SELECT * FROM GAME WHERE played=1 AND comment!='BRACKET';";
      ResultSet games = statement1.executeQuery(myQuery);
      String myQuery2 = "SELECT * FROM TEAM;";
      ResultSet teams = statement2.executeQuery(myQuery2);
  %>
  <%
  ArrayList<String> teamlist = new ArrayList<String>();
  while(teams.next()){
    teamlist.add(teams.getString("teamName"));
  }
  %>
  <%
  while(games.next()) {
      String hometeam = (String)teamlist.get(games.getInt("home")-1);
      String awayteam = (String)teamlist.get(games.getInt("away")-1);
      SimpleDateFormat ft = new SimpleDateFormat ("MM/dd");
      String date = ft.format(games.getDate("gameDate"));
      int hg = games.getInt("homeGoals");
      int ag = games.getInt("awayGoals");
      String score = Integer.toString(hg) + " - " + Integer.toString(ag);
      //String day = games.getDate("gameDate");
  %>

  <tr>
    <td><%=date%></td> <!-- need work -->
    <td><%=hometeam%></td>
    <td><%=score%></td>
    <td><%=awayteam%></td>
  </tr>

  <%}
  Database.close(connection);
  %>
</table>