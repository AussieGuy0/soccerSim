package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 10/07/16.
 */
public class Goalie {

    private final String name;
    private final int rating;

    public Goalie(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

}
