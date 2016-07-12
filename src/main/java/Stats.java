package main.java;

/**
 * Created by anthony on 10/07/16.
 */
public class Stats {
    private int goalsScored = 0;
    private int goalsAgainst = 0;
    private int wins = 0;
    private int draws = 0;
    private int losses = 0;
    private int points = 0;
    private int played = 0;

    public void addWin(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        wins++;
        points += 3;
        played++;
    }

    public void addDraw(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        draws++;
        points += 1;
        played++;
    }

    public void addLoss(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        losses++;
        played++;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalsScored() {
        return  goalsScored;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getPlayed() {
        return played;
    }
}
