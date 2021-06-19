package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Player;
import util.Database;

/**
 * Functions involving database operations regarding player table of the
 * database.
 *
 * @author Michael Malett
 */
public class PlayerDao {

    private Connection connection;

    public PlayerDao() {
    }

    /**
     * adds a new player to the database from an initialized player object
     *
     * @param p player object with the data to insert
     * @return String indicating success or failure
     */
    public String addNewPlayer(Player p) {
        connection = Database.getConnection();
        // have player name, team id, number of goals, if is a captain, and a comment
        String queryMaxID = "SELECT MAX(playerID) as `maxID` FROM PLAYER;";
        int captain = 0;
        if (p.getIsCaptain()) {
            captain = 1;
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rsID = stmt.executeQuery(queryMaxID);
            while (rsID.next()) {
                p.setPlayerID(rsID.getInt("maxID") + 1);
            }
            System.out.println(p.getPlayerID());
            String insert = "INSERT INTO `PLAYER` VALUES (" + p.getPlayerID()
                    + ", '" + p.getPlayerName() + "', '" + p.getNickName()
                    + "', '" + p.getComment() + "', " + p.getGoals()
                    + ", " + p.getTeamID() + ", " + captain + ");";

            PreparedStatement ps = connection.prepareStatement(insert);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Database.close(connection);
            return "Unable to add new player.";
        }
        Database.close(connection);
        return p.getPlayerName() + " was added.";
    }

    /**
     * modifies player data in the database using a player object.
     *
     * @param p the player object with the appropriate data initialized
     * @return the player object after all modification is complete
     */
    public Player modifyPlayer(Player p) {
        connection = Database.getConnection();

        int captain = 0;
        if (p.getIsCaptain()) {
            captain = 1;
        }

        // get team name from DB
        String qTeamName = "SELECT teamName FROM TEAM WHERE teamID='" + p.getTeamID() + "'";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rsTeamName = stmt.executeQuery(qTeamName);
            if (rsTeamName.next()) {
                p.setTeamName(rsTeamName.getString("teamName"));
            }

            String updatePlayerName = "UPDATE PLAYER SET playerName='" + p.getPlayerName() + "' WHERE playerID='" + p.getPlayerID() + "'";
            String updateTeamID = "UPDATE PLAYER SET team='" + p.getTeamID() + "' WHERE playerID='" + p.getPlayerID() + "'";
            String updateNickName = "UPDATE PLAYER SET nickName='" + p.getNickName() + "' WHERE playerID='" + p.getPlayerID() + "'";
            String updateCaptain = "UPDATE PLAYER SET isCaptain='" + captain + "' WHERE playerID='" + p.getPlayerID() + "'";
            String updateComment = "UPDATE PLAYER SET comment='" + p.getComment() + "' WHERE playerID='" + p.getPlayerID() + "'";

            // perform DB updates
            stmt.executeUpdate(updatePlayerName);
            stmt.executeUpdate(updateTeamID);
            stmt.executeUpdate(updateNickName);
            stmt.executeUpdate(updateCaptain);
            stmt.executeUpdate(updateComment);

            Database.close(connection);
            return p;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Database.close(connection);
            return null;
        }
    }

    /**
     * I put this in for now, although it is not used. To delete a player like
     * this leaves dangling references in other portions of the database. The
     * database needs an inactive player table to store former players, to which
     * a deleted player gets inserted into.
     *
     * @param playerID the id of the player to delete
     */
    public void deletePlayer(int playerID) {
        connection = Database.getConnection();
        try {
            String delete = "DELETE FROM `PLAYER` WHERE playerID='" + playerID + "';";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(delete);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Database.close(connection);
        }
        Database.close(connection);
    }

    /**
     * creates a list of players according to filter criteria and instantiates
     * them into player objects
     *
     * @param team team filter
     * @param name player's name filter
     * @return list of players meeting search/filter criteria
     */
    public ArrayList<Player> listPlayers(String team, String name) {
        // build query string
        String clause;
        ArrayList<Player> players = new ArrayList();

        // if both filters are used
        if (!name.equals("") && !team.equals("any")) {
            clause = " WHERE team='" + team + "' AND playerName LIKE %" + name + "%";
        } // if only team filter is used
        else if (!team.equals("any")) {
            clause = " WHERE team='" + team + "'";
        } // if only name filter is used
        else if (!name.equals("")) {
            clause = " WHERE playerName LIKE '%" + name + "%'";
        } // if no filters are used
        else {
            clause = "";
        }
        String query = "SELECT * FROM PLAYER" + clause;
        connection = Database.getConnection();
        System.out.println(query);
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int teamID = rs.getInt("team");
                String teamName = "No Team";
                String q = "SELECT teamName FROM TEAM WHERE teamID='" + teamID + "'";
                PreparedStatement ps2 = connection.prepareStatement(q);
                ResultSet r = ps2.executeQuery();
                if (r.next()) {
                    teamName = r.getString("teamName");
                }
                Player p = new Player();
                p.setPlayerID(rs.getInt("playerID"));
                p.setPlayerName(rs.getString("playerName"));
                p.setNickName(rs.getString("nickName"));
                p.setComment(rs.getString("comment"));
                p.setGoals(rs.getInt("goals"));
                p.setTeamID(teamID);
                p.setTeamName(teamName);
                boolean captain = rs.getInt("isCaptain") > 0;
                p.setIsCaptain(captain);
                players.add(p);
            }
            System.out.println(players.size());
            Database.close(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            Database.close(connection);
        }
        return players;
    }

}
