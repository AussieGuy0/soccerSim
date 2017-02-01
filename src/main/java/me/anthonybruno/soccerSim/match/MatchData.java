package me.anthonybruno.soccerSim.match;

import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class MatchData {

    private int homeTeamGoals = 0;
    private int awayTeamGoals = 0;
    private int homeTeamShotsTotal = 0;
    private int awayTeamShotsTotal = 0;
    private final Team homeTeam;
    private final Team awayTeam;
    private boolean firstHalf = true;
    private int minute = 0;

    public MatchData(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void incrementHomeTeamGoals() {
        homeTeamGoals++;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void incrementAwayTeamGoals() {
        awayTeamGoals++;
    }


    public int getHomeTeamShotsTotal() {
        return homeTeamShotsTotal;
    }

    public void setHomeTeamShotsTotal(int homeTeamShotsTotal) {
        this.homeTeamShotsTotal = homeTeamShotsTotal;
    }

    public void incrementHomeTeamShotsTotal() {
        homeTeamShotsTotal++;
    }

    public int getAwayTeamShotsTotal() {
        return awayTeamShotsTotal;
    }

    public void setAwayTeamShotsTotal(int awayTeamShotsTotal) {
        this.awayTeamShotsTotal = awayTeamShotsTotal;
    }

    public void incrementAwayTeamShotsTotal() {
        awayTeamShotsTotal++;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public boolean isFirstHalf() {
        return firstHalf;
    }

    public void startSecondHalf() {
        firstHalf = false;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void addMinutes(int minutes) {
        minute += minutes;
    }
}
