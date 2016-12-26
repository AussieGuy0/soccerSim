package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 26/12/16.
 */
public class HalfAttributes {

    private final int attempts;
    private final int defenseAttempts;
    private final int defensiveShotOnGoal;

    public HalfAttributes(int attempts, int defenseAttempts, int defensiveShotOnGoal) {
        this.attempts = attempts;
        this.defenseAttempts = defenseAttempts;
        this.defensiveShotOnGoal = defensiveShotOnGoal;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getDefenseAttempts() {
        return defenseAttempts;
    }

    public int getDefensiveShotOnGoal() {
        return defensiveShotOnGoal;
    }
}
