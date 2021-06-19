/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author bryonnaklumker
 */
public class Game {
    private int gameID;
    private String location;
    private Date date;
    private String comment;
    private int homeGoals;
    private int awayGoals;
    private int leagueID;
    private int homeID;
    private int awayID;
    
    public Game() {
        
    }
    
    public Game(int gid, String loc, Date date, String com, int homeg, int awayg, int lid, int hid, int aid) {
        this.gameID = gid;
        this.location = loc;
        this.date = date;
        this.comment = com;
        this.homeGoals = homeg;
        this.awayGoals = awayg;
        this.leagueID = lid;
        this.homeID = hid;
        this.awayID = aid;
    }
    
    public int getGameID() {
        return this.gameID;
    }
    
    public void setGameID(int id) {
        this.gameID = id;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String loc) {
        this.location = loc;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setComment(String com) {
        this.comment = com;
    }
    
    public String getComment() {
        return this.comment;
    }
    
    public int getHomeGoals() {
        return this.homeGoals;
    }
    
    public void setHomeGoals(int g) {
        this.homeGoals = g;
    }
    
    public int getAwayGoals() {
        return this.awayGoals;
    }
    
    public void setAwayGoals(int g) {
        this.awayGoals = g;
    }
    
    public int getLeagueID() {
        return this.leagueID;
    }
    
    public void setLeagueID(int id) {
        this.leagueID = id;
    }
    public int getHomeID() {
        return this.homeID;
    }
    
    public void setHomeID(int id) {
        this.homeID = id;
    }
    public int getAwayID() {
        return this.awayID;
    }
    
    public void setAwayID(int id) {
        this.awayID = id;
    }
    
    
    
    
}
