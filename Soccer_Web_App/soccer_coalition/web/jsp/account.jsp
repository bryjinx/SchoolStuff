<%-- 
    Document   : account
    Created on : Apr 11, 2018, 7:43:49 PM
    Author     : Mike
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.*" %>
<%@page import="java.sql.*" %>
<%@page import="util.Database" %>
<%@page import="model.User" %>
<%@page import="dao.UserDao" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Manage</title>
  </head>
  <%
      User user = (User) session.getAttribute("user");
      Connection conn = Database.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs_teams = stmt.executeQuery("SELECT teamID, teamName FROM TEAM");
  %>
  <nav id="side-nav" class="navbar navbar-dark bg-dark justify-content-left">
    <ul class ="nav flex-column nav-pills" style="width: 100%;">
      <li class ="nav-item">
        <a class="nav-link <%if (session.getAttribute("currentTab").equals("manageTeamGoal")) {%>active<%}%>" data-toggle="pill" href="#manage-team-goal" style="color:#e0e0e0;">Manage Teams/Goals</a>
      </li>
      <li class ="nav-item">
        <a class="nav-link  <%if (session.getAttribute("currentTab").equals(("modifyPlayer"))) {%>active<%}%>" data-toggle="pill" href="#modify-player" style="color:#e0e0e0">Modify Player</a>
      </li>
      <li class ="nav-item">
        <a class="nav-link <%if (session.getAttribute("currentTab").equals(("addPlayer"))) {%>active<%}%>" data-toggle="pill" href="#add-player" style="color:#e0e0e0">Add new Player</a>
      </li>
      <li class ="nav-item">
        <a class="nav-link <%if (session.getAttribute("currentTab").equals(("createUser"))) {%>active<%}%>" data-toggle="pill" href="#create-user" style="color:#e0e0e0">Create User Account</a>
      </li>
      <li class ="nav-item">
        <a class="nav-link <%if (session.getAttribute("currentTab").equals(("deleteUser"))) {%>active<%}%>" data-toggle="pill" href="#delete-user" style="color:#e0e0e0">Delete User Account</a>
      </li>
      <li class ="nav-item">
        <a class="nav-link <%if (session.getAttribute("currentTab").equals(("manageGame"))) {%>active<%}%>" data-toggle="pill" href="#manage-game" style="color:#e0e0e0">Manage Games</a>
      </li>
    </ul>
  </nav>
  <div class="tab-content">
    <div class="container <%if (session.getAttribute("currentTab").equals(("manageTeamGoal"))) {%>active<%}%> tab-pane" id="manage-team-goal">
      <form method="POST" action="UpdateTeams" name="updateTeamsForm">
        <input type="hidden" name="operation" value="updateTeams"/>
        <input type='submit' value='Update Goals'/>
      </form>
      <form method="POST" action="UpdateGoals" name="updateGoalsForm">
        <input type="hidden" name="operation" value="updateGoals"/>
        <input type='submit' value='Update Teams'/>
      </form>

    </div>

    <div class="container <%if (session.getAttribute("currentTab").equals(("addPlayer"))) {%>active<%}%> tab-pane" id="add-player">
      <!-- add player form -->
      <form method="GET" action="PlayerController" name="createPlayerForm">
        <input type="hidden" name="operation" value="add"/>
        <div class="container-fluid">
          <table style="width:100%; border: none;">
            <tr>
              <th style="text-align: left; font-size: 18px;">Add new player</th>
            </tr>
            <tr>
              <td>
                <table style="width:100%; border: none;">
                  <tr>
                    <td>Player Name :</td><td><input type="text" maxlength="32" name="playerName"/> (required)</td>
                  </tr>
                  <tr>
                    <td>Team :</td><td><select name="teamID">
                        <%
                            rs_teams.first();
                            do {
                        %>
                        <option value="<%=rs_teams.getString("teamID")%>"><%=rs_teams.getString("teamName")%></option>
                        <% } while (rs_teams.next());%>
                      </select> (required)</td>
                  </tr>
                  <tr>
                    <td>Goals :</td><td><input type="text" maxlength="3" name="goals"/></td>
                  </tr>
                  <tr>
                    <td>Captain? :</td><td><input type="radio" name="captain" value="true"/>Yes <input type="radio" name="captain" value="false" checked/>No</td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <textarea rows="5" cols="60" name="comment" form="createPlayerForm" placeholder="Enter your comment (optional)" maxlength="256"></textarea><br/>
              </td>
            </tr>
            <tr>
              <td>
                <input type="submit"/>
              </td>
            </tr>
          </table>
        </div>
      </form>
    </div>
    <div class="container <%if (session.getAttribute("currentTab").equals(("modifyPlayer"))) {%>active<%}%> tab-pane" id="modify-player">
      <form method="GET" action="PlayerController" name="searchPlayerForm">
        <input type="hidden" name="operation" value="search"/>
        <%
            ArrayList<Player> players = (ArrayList) session.getAttribute("players");
        %>

        <table style="width:100%; border: none;">
          <tr>
            <th style="text-align: left; font-size: 18px;">Filter players by</th>
          </tr>
          <tr>
            <td>
              <table style="width:100%; border: none;">
                <tr>
                  <td id="cells-left">
                    <input placeholder="Enter player's name" type="search" maxlength="32" name="playerName" autocomplete="on"/>
                    <select name="teamFilter">
                      <option value="any">Any team</option>
                      <%
                          rs_teams.first();
                          do {
                      %>
                      <option value="<%=rs_teams.getString("teamID")%>"><%=rs_teams.getString("teamName")%></option>
                      <% } while (rs_teams.next());%>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td id="cells-left">
                    <input type="submit" value="Load results"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </form>
      <%
          String modifyPlayerFeedback;
          if ((modifyPlayerFeedback = (String) session.getAttribute("modifyPlayerFeedback")) != null) {
      %>
      <p> <%=modifyPlayerFeedback%> </p>
      <%}%>
      <table>
        <tr>
          <th style='max-width:100px'>Player</th>
          <th>Team</th>
          <th></th>
          <th></th>
        </tr>
        <% if (players != null) { %>
        <%
            for (Player p : players) {

        %>
        <tr>
          <td style='max-width:100px'>
            <%=p.getPlayerName()%>
          </td>
          <td>
            <%=p.getTeamName()%>
          </td>
          <td>
            <!-- scripting for popover functions -->
            <script>
                // trigger and placement of the popover
                $('[data-toggle="popover"]').popover({trigger: "click", placement: "left"});
                // prevents page from resetting to the top when clicking the popover
                $('a[data-toggle=popover]').popover().click(function (e) {
                    e.preventDefault();
                });
                // creates a class='close' which can be used with a button to provide a way to close the popover from inside the popover
                $(document).on("click", ".popover .close", function () {
                    $(this).parents(".popover").popover('hide');
                });
            </script>
            <!-- the popover buttons -->
            <a class="btn-group btn-primary" data-toggle="popover" href='#' data-html="true"
               data-content="<form method='GET' action='PlayerController' name='updatePlayer'>
               <input type='hidden' name='operation' value='update'/>
               <input type='hidden' name='playerID' value='<%=p.getPlayerID()%>'/>
               <table>
               <tr>
               <td>Name :</td><td><input class='narrow' type='text' maxlength='32' name='playerName' value='<%=p.getPlayerName()%>'/></td>
               </tr>
               <tr>
               <td>Nick Name :</td><td><input class='narrow' type='text' maxlength='64' name='nickName' value='<%=p.getNickName()%>'/></td>
               </tr>
               <tr>
               <td>Team :</td>
               <td>
               <select class='narrow' name='teamID'>
               <%
                   rs_teams.first();
                   do {
               %>
               <option class='narrow' value='<%=rs_teams.getInt("teamID")%>' <% if (rs_teams.getInt("teamID") == p.getTeamID()) {%> selected<%} else {%>''<%}%>><%=rs_teams.getString("teamName")%></option>
               <% } while (rs_teams.next());%>
               </select>
               </tr>
               <tr>
               <td>Captain? :</td><td><input type='radio' name='captain' value='true'/>Yes <input type='radio' name='captain' value='false'/>No</td>
               </tr>
               <tr>
               <td>Comment :</td><td><input class='narrow' type='text' maxlength='256' name='comment' value='<%=p.getComment()%>'/></td>
               </tr>
               <td><input  class='narrow' type='submit' value='Apply'/></td>
               </table>
               </form>
               <button class='close'>Close</button>
               ">Update Player</a>
          </td>
        </tr>
        <%
            }
        } else {
        %>
        <td>No results</td>
        <%
            }
        %>
      </table>
    </div>
    <div class="container <%if (session.getAttribute("currentTab").equals(("createUser"))) {%>active<%}%> tab-pane" id="create-user">
      <form method="POST" action="UserController" name="createUserForm">
        <input type="hidden" name="operation" value="create"/>
        <div class="container-fluid">
          <table style="width:100%; border: none;">
            <tr>
              <td><input type="text" maxlength="32" name="firstname" placeholder="Enter first name" required/></td>
            </tr>
            <tr>
              <td><input type="text" maxlength="32" name="lastname" placeholder="Enter last name" required/></td>
            </tr>
            <tr>
              <td><input type="text" maxlength="32" name="username" placeholder="Enter a username" required/></td>
            </tr>
            <tr>
              <td><input type="text" maxlength="32" name="email" placeholder="Enter an email"  required/></td>
            </tr>
            <tr>
              <td><input type="password" maxlength="32" name="password" placeholder="Enter a password" required/></td>
            </tr>
            <tr>
              <td><input type="password" maxlength="32" name="confirm" placeholder="Confirm password" required/></td>
            </tr>
            <tr>
              <td>
                Access level 
                <input type="radio" name="access" value="1"/>Captain
                <input type="radio" name="access" value="2" checked/>Referee
                <input type="radio" name="access" value="3" checked/>Admin
              </td>
            </tr>
          </table>
          <input type="submit" value="Add User"/>
        </div>
      </form>
      <%
          String insertUserFeedback;
          if ((insertUserFeedback = (String) session.getAttribute("insertUserFeedback")) != null) {
      %>
      <p> <%=insertUserFeedback%> </p>
      <%}%>
    </div>
    <div class="container <%if (session.getAttribute("currentTab").equals(("deleteUser"))) {%>active<%}%> tab-pane" id="delete-user">
      <form method="POST" action="UserController" name="deleteUserForm">
        <input type="hidden" name="operation" value="delete"/>
        <div class="container-fluid">
          <table style="width:100%; border: none;">
            <tr>
              <td><input type="text" maxlength="32" name="username" placeholder="Enter a username" required/></td>
            </tr>
          </table>
          <input type="submit" value="Delete User"/>
        </div>
      </form>
      <%
          String deleteUserFeedback;
          if ((deleteUserFeedback = (String) session.getAttribute("deleteUserFeedback")) != null) {
      %>
      <p> <%=deleteUserFeedback%> </p>
      <%}%>
    </div>
  </div>
</body>
</html>
