package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 10/07/16.
 */
public class Goalie extends TeamMember {

    private final int rating;

    public Goalie(String name, int rating, int multiplier) {
        super(name, multiplier);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

}
