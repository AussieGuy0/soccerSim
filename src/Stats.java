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

    public void addWin(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        wins++;
        points += 3;
    }

    public void addDraw(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        draws++;
        points += 1;
    }

    public void addLoss(int goalsScored, int goalsAgainst) {
        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;
        losses++;
    }

    public int getPoints() {
        return points;
    }
}
