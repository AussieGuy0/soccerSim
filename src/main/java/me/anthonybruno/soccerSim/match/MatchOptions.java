package me.anthonybruno.soccerSim.match;

/**
 * Created by anthony on 2/02/17.
 */
public class MatchOptions {

    static MatchOptions DEFAULT_OPTIONS = new MatchOptions();

    private boolean advancedRules = false;
    private boolean seasonMatch = false;
    private int matchDelay = 0; //time in ms between attempts/free kicks/etc.

    public MatchOptions() {

    }

    public void useAdvancedRules(boolean advancedRules) {
        this.advancedRules = advancedRules;
    }

    public boolean isUsingAdvancedRules() {
        return advancedRules;
    }

    public boolean isSeasonMatch() {
        return seasonMatch;
    }

    public void setSeasonMatch(boolean seasonMatch) {
        this.seasonMatch = seasonMatch;
    }

    public int getMatchDelay() {
        return matchDelay;
    }

    public void setMatchDelay(int matchDelay) {
        this.matchDelay = matchDelay;
    }
}
