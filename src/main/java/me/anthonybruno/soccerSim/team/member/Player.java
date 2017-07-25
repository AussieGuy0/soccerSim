package me.anthonybruno.soccerSim.team.member;

import me.anthonybruno.soccerSim.models.Range;

/**
 * Player is a class that contains information about a player.
 */
public class Player extends TeamMember {

    private final Range shotRange;
    private final int goal;


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
    public Player(String name, Range shotRange, int goal, int multiplier) {
        super(name, multiplier);
        this.shotRange = shotRange;
        this.goal = goal;
    }

    /**
     * Returns the shot range of player.
     *
     * @return the player's shot range.
     */
    public Range getShotRange() {
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



}
