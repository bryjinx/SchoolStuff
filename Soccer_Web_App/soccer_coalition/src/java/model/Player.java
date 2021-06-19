package model;

/**
 *
 * @author Mike
 */
public class Player {

    private int playerID;
    private String playerName;
    private String nickName;
    private String comment;
    private int goals;
    private int teamID;
    private String teamName;
    private boolean isCaptain;

    public Player() {
    }

    public Player(Player player) {
        this.playerName = player.playerName;
        this.nickName = player.nickName;
        this.comment = player.comment;
        this.teamID = player.teamID;
        this.isCaptain = player.isCaptain;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public boolean getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
    }
}
