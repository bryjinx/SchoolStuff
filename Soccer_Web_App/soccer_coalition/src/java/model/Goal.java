package model;

/**
 *
 * @author Mike
 * @author Bryonna
 */
public class Goal {
    
    private int gameID;
    private int player;
    
    public Goal(int gameID, int player) {
        this.gameID = gameID;
        this.player = player;
    }
    
    public Goal() {
        
    }
    
    public void setGameID(int id) {
        this.gameID = id;
    }
    
    public void setPlayer(int id) {
        this.player = id;
    }
    
    public int getGameID() {
        return this.gameID;
    }
    
    public int getPlayer() {
        return this.player;
    }
}
