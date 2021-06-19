-- I don't want to do this --

create database if not exists soccer;

use soccer;

drop table if exists CARD;
drop table if exists GOAL;
drop table if exists GAME;
drop table if exists PLAYER;
drop table if exists TEAM;
drop table if exists LEAGUE;

create table LEAGUE(
  leagueID int(6) NOT NULL AUTO_INCREMENT,
  leagueName varchar(100),
  maxNumPlayers int(2) default 12,
  champion varchar(100),
  leagueType int(2),
  PRIMARY KEY (leagueID)
);

create table TEAM(
  teamID int(6) NOT NULL AUTO_INCREMENT,
  teamName varchar(100),
  shorterName varchar(10),
  league int(6),
  played int(2) default 0,
  won int(2) default 0,
  draw int(2) default 0,
  lose int(2) default 0,
  gFavor int(3) default 0,
  gAgainst int(3) default 0,
  gDiff int(3) default 0,
  points int(3) default 0,
  PRIMARY KEY (teamID),
  FOREIGN KEY (league) REFERENCES LEAGUE(leagueID)
);

create table PLAYER(
  playerID int(6) NOT NULL AUTO_INCREMENT,
  playerName varchar(100),
  nickName varchar(100),
  comment varchar(500),
  goals int(3) default 0,
  team int(6),
  isCaptain tinyint(1) default 0,
  PRIMARY KEY (playerID),
  FOREIGN KEY (team) REFERENCES TEAM(teamID)
);

create table GAME(
  gameID int(6) NOT NULL AUTO_INCREMENT,
  location varchar(20),
  gameDate datetime,
  day int(1),
  comment varchar(500),
  homeGoals int(2) default NULL,
  awayGoals int(2) default NULL,
  league int(6),
  home int(6),
  away int(6),
  played tinyint(1) default 0,
  PRIMARY KEY (gameID),
  FOREIGN KEY (league) REFERENCES LEAGUE(leagueID),
  FOREIGN KEY (home) REFERENCES TEAM(teamID),
  FOREIGN KEY (away) REFERENCES TEAM(teamID)
);

create table CARD(
  cardID int(6) NOT NULL AUTO_INCREMENT,
  game int(6),
  player int(6),
  cardType tinyint(1),
  minute int(2),
  comment varchar(100),
  PRIMARY KEY(cardID),
  FOREIGN KEY (game) REFERENCES GAME(gameID),
  FOREIGN KEY (player) REFERENCES PLAYER(playerID)
);

create table GOAL(
  goalID int(6) NOT NULL AUTO_INCREMENT,
  game int(6),
  player int(6),
  minute int(2),
  comment varchar(100),
  PRIMARY KEY(goalID),
  FOREIGN KEY (game) REFERENCES GAME(gameID),
  FOREIGN KEY (player) REFERENCES PLAYER(playerID)
);

create table if not exists USERS (
  username varchar(16) NOT NULL,
  password varchar(20) NOT NULL,
  firstname varchar(20) NOT NULL,
  lastname varchar(20) NOT NULL,
  email varchar(64) NULL DEFAULT NULL,
  access int(1) DEFAULT 1,
  PRIMARY KEY (username),
  UNIQUE KEY (email)
);
