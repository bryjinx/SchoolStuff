<%@page import="java.util.ArrayList"%>
<%@page import="util.Pair"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.Database"%>
<%
    Connection connection = Database.getConnection();
%>
<!--
<nav id="side-nav" class="navbar navbar-dark bg-dark justify-content-left">
-->
<nav id="side-nav" class="navbar navbar-dark justify-content-left" style="background-color: #012063;"
<nav id="side-nav" class="navbar navbar-dark bg-dark justify-content-left">
  <ul class ="nav flex-column nav-pills" style="width: 100%;">
    <li class ="nav-item">
      <a class="nav-link active" data-toggle="pill" href="#performance" style="color:#e0e0e0;">Performance</a>
    </li>
    <li class ="nav-item">
      <a class="nav-link" data-toggle="pill" href="#top-scorers" style="color:#e0e0e0">Top Scorers</a>
    </li>
    <li class ="nav-item">
      <a class="nav-link" data-toggle="pill" href="#discipline" style="color:#e0e0e0">Discipline</a>
    </li>
    <li class ="nav-item">
      <a class="nav-link" data-toggle="pill" href="#fair-play" style="color:#e0e0e0">Fair Play</a>
    </li>
  </ul>
</nav>
<!--
</div>
<div class="tab-content col-md-9 col-lg-8">
-->
<div class="tab-content">
  <div class="container active tab-pane" id="performance">
    <div class="row">
      <div class="col-md-6">
        <h4>Teams with the most goals scored</h4>
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>Rank</th>
              <th>Team</th>
              <th>Goals Scored</th>
            </tr>
          </thead>
          <%
              Statement statement = connection.createStatement();

              String myQuery = "SELECT * FROM TEAM ORDER BY gFavor DESC;";
              ResultSet teams = statement.executeQuery(myQuery);
          %>
          <%
              int count = 0;
              while (teams.next() && count < 3) {
                  String teamName = teams.getString("teamName");
                  String gf = Integer.toString(teams.getInt("gFavor"));
                  count += 1;
          %>
          <tr>
            <td><%=count%></td>
            <td><%=teamName%></td>
            <td><%=gf%></td>
          </tr>
          <%}%>
        </table>
        <br>
        <h4>Most goals scored in a single game</h4>
        <table style="text-align: center;" class="table table-striped">
          <thead>
            <tr>
              <th>Home</th>
              <th>vs.</th>
              <th>Away</th>
            </tr>
          </thead>
          <%
              String goals = "SELECT GAME.* FROM GAME;";
              ResultSet maxGoalsRS = statement.executeQuery(goals);
              int max = 0;
              int tmp = 0;
              int tmp2 = 0;

              int diff;
              int maxDiff = -1;
              int diffHome = -1;
              int diffAway = -1;
              int diffHomeg = -1;
              int diffAwayg = -1;

              int cghomeID = -1;
              int cgawayID = -1;
              int cgtmp = 0;
              Pair pair = null;
              ArrayList<Pair> cgList = new ArrayList<>();

              String homeID = "-1";
              String awayID = "-1";
              String hgoals = "-1";
              String agoals = "-1";

              while (maxGoalsRS.next()) {

                  tmp = maxGoalsRS.getInt("homeGoals");
                  tmp2 = maxGoalsRS.getInt("awayGoals");

                  diff = Math.abs(tmp - tmp2);

                  if (cghomeID == -1) {
                      cghomeID = maxGoalsRS.getInt("home");
                      cgawayID = maxGoalsRS.getInt("away");
                      pair = new Pair(cghomeID, maxGoalsRS.getInt("awayGoals"));
                      cgList.add(pair);
                      pair = new Pair(cgawayID, maxGoalsRS.getInt("homeGoals"));
                      cgList.add(pair);
                      pair = null;
                  } else {
                      cghomeID = maxGoalsRS.getInt("home");
                      cgawayID = maxGoalsRS.getInt("away");
                      for (Pair p : cgList) {
                          if (p.getKey() != cghomeID) {
                              pair = new Pair(cghomeID, maxGoalsRS.getInt("awayGoals"));
                          } else if (p.getKey() != cgawayID) {
                              pair = new Pair(cgawayID, maxGoalsRS.getInt("homeGoals"));
                          } else if (p.getKey() == cghomeID) {
                              cgtmp = p.getValue();
                              p.setValue(cgtmp + maxGoalsRS.getInt("awayGoals"));
                              pair = null;
                          } else if (p.getKey() == cgawayID) {
                              cgtmp = p.getValue();
                              p.setValue(cgtmp + maxGoalsRS.getInt("homeGoals"));
                              pair = null;
                          }
                      }
                      if (!pair.equals(null)) {
                          cgList.add(pair);
                      }
                  }
                  if (maxDiff == -1) {
                      maxDiff = diff = Math.abs(tmp - tmp2);
                      diffHome = maxGoalsRS.getInt("home");
                      diffAway = maxGoalsRS.getInt("away");
                      diffHomeg = maxGoalsRS.getInt("homeGoals");
                      diffAwayg = maxGoalsRS.getInt("awayGoals");
                  }

                  if (tmp2 > tmp) {
                      tmp = tmp2;
                  }
                  if (tmp > max) {
                      max = tmp;
                      homeID = maxGoalsRS.getString("home");
                      awayID = maxGoalsRS.getString("away");
                      hgoals = maxGoalsRS.getString("homeGoals");
                      agoals = maxGoalsRS.getString("awayGoals");
                  }

                  if (diff > maxDiff) {
                      maxDiff = diff;
                      diffHome = maxGoalsRS.getInt("home");
                      diffAway = maxGoalsRS.getInt("away");
                      diffHomeg = maxGoalsRS.getInt("homeGoals");
                      diffAwayg = maxGoalsRS.getInt("awayGoals");
                  }
              }
              maxGoalsRS.close();

              String homeTeamQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + homeID + "';";
              String awayTeamQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + awayID + "';";
              String diffHomeTeamQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + diffHome + "';";
              String diffAwayTeamQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + diffAway + "';";

              ResultSet getHomeTeam = statement.executeQuery(homeTeamQ);
              getHomeTeam.next();
              String homeTeam = getHomeTeam.getString("teamName");
              getHomeTeam.close();

              ResultSet getAwayTeam = statement.executeQuery(awayTeamQ);
              getAwayTeam.next();
              String awayTeam = getAwayTeam.getString("teamName");
              getAwayTeam.close();

              ResultSet getDiffHome = statement.executeQuery(diffHomeTeamQ);
              getDiffHome.next();
              String diffHomeStr = getDiffHome.getString("teamName");
              getDiffHome.close();

              ResultSet getAwayHome = statement.executeQuery(diffAwayTeamQ);
              getAwayHome.next();
              String diffAwayStr = getAwayHome.getString("teamName");
              getAwayHome.close();
          %>
          <tr>
            <td><%=homeTeam%></td>
            <td></td>
            <td><%=awayTeam%></td>
          </tr>
          <tr>
            <td><%=hgoals%></td>
            <td></td>
            <td><%=agoals%></td>
          </tr>
        </table>
        <br>
        <h4>Game with the highest total goals</h4>
        <table style="text-align: center;" class="table table-striped">
          <thead>
            <tr>
              <th>Home</th>
              <th>vs.</th>
              <th>Away</th>
            </tr>
          </thead>
          <%
              String maxGoals = "SELECT GAME.* FROM GAME;";
              ResultSet maxScoringGame = statement.executeQuery(maxGoals);
              max = 0;
              tmp = 0;
              String homeTeamID = "-1";
              String awayTeamID = "-1";
              String finalHome = "-1";
              String finalAway = "-1";
              while (maxScoringGame.next()) {
                  int homeGoals = maxScoringGame.getInt("homeGoals");
                  int awayGoals = maxScoringGame.getInt("awayGoals");
                  tmp = homeGoals + awayGoals;
                  if (tmp > max) {
                      max = tmp;
                      homeTeamID = maxScoringGame.getString("home");
                      awayTeamID = maxScoringGame.getString("away");
                      finalHome = maxScoringGame.getString("homeGoals");
                      finalAway = maxScoringGame.getString("awayGoals");
                  }
              }

              maxScoringGame.close();

              String homeQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + homeTeamID + "';";
              String awayQ = "SELECT TEAM.teamName FROM TEAM WHERE TEAM.teamID = '" + awayTeamID + "';";

              ResultSet getHome = statement.executeQuery(homeQ);
              getHome.next();
              String home = getHome.getString("teamName");
              getHome.close();

              ResultSet getAway = statement.executeQuery(awayQ);
              getAway.next();
              String away = getAway.getString("teamName");
              getAway.close();
          %>
          <tr>
            <td><%=home%></td>
            <td></td>
            <td><%=away%></td>
          </tr>
          <tr>
            <td><%=finalHome%></td>
            <td></td>
            <td><%=finalAway%></td>
          </tr>
        </table>
      </div>
      <div class="col-md-6">
        <h4>Teams that have conceded the most goals</h4>
        <table style="text-align: center;" class="table-bordered">
          <thead>
            <tr>
              <!--<th>Rank</th>-->
              <th>Team</th>
              <th>Goals Conceded</th>
            </tr>
          </thead>
          <%
              boolean start = true;
              int cg1 = 0;
              int cg2 = 0;
              int cg3 = 0;
              int cgteamID_1 = -1;
              int cgteamID_2 = -1;
              int cgteamID_3 = -1;

              for (Pair cg : cgList) {
                  if (start) {
                      cg1 = cg2 = cg3 = cg.getValue();
                      cgteamID_1 = cgteamID_2 = cgteamID_3 = cg.getKey();
                      start = false;
                  } else {
                      tmp = cg.getValue();
                      if (tmp > cg1) {
                          if (cgteamID_1 != cg.getKey()) {
                            cg3 = cg2;
                            cg2 = cg1;
                            cg1 = tmp;

                            cgteamID_3 = cgteamID_2;
                            cgteamID_2 = cgteamID_1;
                            cgteamID_1 = cg.getKey();
                          }
                          else {
                              cg1 += tmp;
                          }
                      } else if (tmp > cg2) {
                          if (cgteamID_2 != cg.getKey()) {
                            cg3 = cg2;
                            cg2 = tmp;

                            cgteamID_3 = cgteamID_2;
                            cgteamID_2 = cg.getKey();
                          }
                          else {
                              cg2 += tmp;
                          }
                      } else if (tmp > cg3) {
                          
                          if (cgteamID_3 != cg.getKey()) {
                                cg3 = tmp;

                                cgteamID_3 = cg.getKey();
                          }
                          else {
                              cg3 += tmp;
                          }
                        }
                  }
              }

              String cgQ1 = "SELECT TEAM.* FROM TEAM WHERE TEAM.teamID = '" + cgteamID_1 + "';";
              String cgQ2 = "SELECT TEAM.* FROM TEAM WHERE TEAM.teamID = '" + cgteamID_2 + "';";
              String cgQ3 = "SELECT TEAM.* FROM TEAM WHERE TEAM.teamID = '" + cgteamID_3 + "';";

              ResultSet rs1 = statement.executeQuery(cgQ1);
              rs1.next();
              String cgTeam1 = rs1.getString("teamName");
              rs1.close();

              ResultSet rs2 = statement.executeQuery(cgQ2);
              rs2.next();
              String cgTeam2 = rs2.getString("teamName");
              rs2.close();

              ResultSet rs3 = statement.executeQuery(cgQ3);
              rs3.next();
              String cgTeam3 = rs3.getString("teamName");
              rs3.close();

          %>
          <tr>
            <td><%=cgTeam1%></td>
            <td><%=cg1%></td>
          </tr>
          <tr>
            <td><%=cgTeam2%></td>
            <td><%=cg2%></td>
          </tr>
          <tr>
            <td><%=cgTeam3%></td>
            <td><%=cg3%></td>
          </tr>
        </table>
        <br>
        <!--
        maxDiff = diff;
        diffHome = maxGoalsRS.getInt("home");
        diffAway = maxGoalsRS.getInt("away");
        diffHomeg = maxGoalsRS.getInt("homeGoals");
        diffAwayg = maxGoalsRS.getInt("awayGoals");
        -->
        <h4>Largest goal difference in a game</h4>
        <table style="text-align: center;" class="table table-striped">
          <thead>
            <tr>
              <th><%=diffHomeStr%></th>
              <th>vs.</th>
              <th><%=diffAwayStr%></th>
            </tr>
          </thead>
          <tr>
            <td><%=diffHomeg%></td>
            <td></td>
            <td><%=diffAwayg%></td>
          </tr>
        </table>
        <br>
        <table style="text-align: left;" class="table">
          <thead>
            <tr>
              <th style="font-size:16pt">Miscellaneous</th>
            </tr>
          </thead>
          <%
              String average = "select sum(gFavor) from TEAM;";
              String numGames = "select count(played) from GAME where played=1;";

              ResultSet rs_avg = statement.executeQuery(average);
              rs_avg.next();
              int avg = Integer.parseInt(rs_avg.getString("sum(gFavor)"));
              ResultSet rs_ng = statement.executeQuery(numGames);
              rs_ng.next();
              int numg = Integer.parseInt(rs_ng.getString("count(played)"));
              avg = avg * 100 / numg;
              double res = avg / 100.0;
          %>
          <tr>
            <td>
              <table style="text-align: left;" class="table table-striped">
                <tr>
                  <td></td>
                  <td></td>
                  <td>Average Goals per game</td>
                  <td></td>
                  <td><%=res%></td>
                </tr>
                <!--<tr>
                  <td>Longest winning streak</td>
                  <td>Fish Tacos</td>
                  <td>8 games</td>
                </tr>
                <tr>
                  <td>Longest losing streak</td>
                  <td>D Class Heroes</td>
                  <td>9 games</td>
                </tr>-->
              </table>
            </td>
        </table>
      </div>
    </div>
  </div>
  <div class="container tab-pane" id="top-scorers">
    <div class="row">
      <div class="col-md-12">
        <table class="table-striped" style="width: 100%; height: 100%; text-align: center;">
          <thead>
            <tr>
              <th>Rank</th>
              <th>Player</th>
              <th># of Goals</th>
            <tr>
          </thead>
          <%
              String teamQ = "SELECT PLAYER.playerName, PLAYER.goals FROM PLAYER ORDER BY PLAYER.goals DESC;";
              ResultSet ts = statement.executeQuery(teamQ);
              String tsplayer = "";
              String tsgoal = "";
              int tscount = 0;
              while (ts.next() && (tscount <= 10)) {
                  tscount = tscount + 1;
                  tsplayer = ts.getString("playerName");
                  tsgoal = ts.getString("goals");
          %>
          <tr>
            <td><%=tscount%></td>
            <td><%=tsplayer%></td>
            <td><%=tsgoal%></td>
          </tr>
          <%}%>

        </table>
      </div>
    </div>
  </div>
  <div class="container tab-pane" id="discipline">
    <div class="row">
      <div class="col-md-12">
        <table class="table-striped" style="width: 100%; height: 100%; text-align: center;">
          <thead>
            <tr>
              <th>Player</th>
              <th>Number of Cards</th>
            <tr>
          </thead>
          <%
              String cardQ = "select PLAYER.playerName, count(*) as cardCount from PLAYER inner join CARD on CARD.player = PLAYER.playerId group by PLAYER.playerID order by cardCount desc;";
              ResultSet cs = statement.executeQuery(cardQ);
              while (cs.next()) {
                  String cardPlayer = cs.getString("playerName");
                  int cardCount = cs.getInt("cardCount");
          %>
          <tr>
            <td>
              <%=cardPlayer%>
            </td>
            <td>
              <%=cardCount%>
            </td>
          </tr>
          <% } 
          %>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div class="container tab-pane" id="fair-play">
    <div class="row">
      <div class="col-md-12">
        <table class="table-striped" style="width: 100%; height: 100%; text-align: center;">
          <thead>
            <tr>
              <th>Team</th>
              <th># of Cards</th>
            <tr>
          </thead>
          <% 
          String teamCardQ = "select TEAM.teamName,count(TEAM.teamName) as teamCount  from PLAYER inner join CARD on CARD.player = PLAYER.playerId inner join TEAM on TEAM.teamID = PLAYER.team group by TEAM.teamName order by teamCount desc;";
          ResultSet teamCardsRS = statement.executeQuery(teamCardQ);
          while (teamCardsRS.next()) {
              String cardTeamName = teamCardsRS.getString("teamName");
              int teamCards = teamCardsRS.getInt("teamCount");
          
          %> 
          <tr>
            <td><%=cardTeamName%></td>
            <td><%=teamCards%></td>
          </tr>
          <%
          }
          %>
        </table>
      </div>
    </div>
  </div>
</div>
</div>
<% Database.close(connection);%>
<!--</div>-->
