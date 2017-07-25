package me.anthonybruno.soccerSim.team.member;

/**
 * Created by anthony on 10/07/16.
 */
public class Goalie extends TeamMember {

    public static final Goalie noopGoalie = new Goalie("unnamed", 0, 0);
    private final int rating;

    public Goalie(String name, int rating, int multiplier) {
        super(name, multiplier);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

}
