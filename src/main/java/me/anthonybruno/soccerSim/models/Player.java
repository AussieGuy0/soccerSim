package me.anthonybruno.soccerSim.models;

/**
 * Player is a class that contains information about a player.
 */
public class Player {

    private final String name;
    private final int shotRange;
    private final int goal;
    private Injury injuryStatus;

    public enum Injury {
        NONE,
        MINOR,
        MATCH,
        MAJOR,
        SEASON
    }

    /**
     * Creates a new player with a name, shot range (how likely a shot will be attributed to the player) and a goal
     * rating (how likely they are to score a goal).
     *
     * @param name      The name of the player
     * @param shotRange Shot range of player. Defines how likely a shot on goal will be attributed to this player (see
     *                  rule files for more information. Is the maximum value when lower and upper bounds given by the
     *                  rules.
     * @param goal      How likely a shot from the player will go in. When shot is taken, a number from 1-10 is generated.
     *                  If the generated number is above or equal to goal rating, the player scores.
     */
    public Player(String name, int shotRange, int goal) {
        this.name = name;
        this.shotRange = shotRange;
        this.goal = goal;
        this.injuryStatus = Injury.NONE;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the shot range of player.
     *
     * @return the player's shot range.
     */
    public int getShotRange() {
        return shotRange;
    }

    /**
     * Returns the goal rating of the player.
     *
     * @return the player's goal range.
     */
    public int getGoal() {
        return goal;
    }

    public void setInjury(Injury injury) {
        this.injuryStatus = injury;
    }


}
