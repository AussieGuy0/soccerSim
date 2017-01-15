package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 29/12/16.
 */
public abstract class TeamMember {
    private final String name;
    private Injury injuryStatus = Injury.NONE;
    private final int multiplier;

    protected TeamMember(String name, int multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public enum Injury {
        NONE,
        MINOR,
        MATCH,
        MAJOR,
        SEASON
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    public void setInjury(Injury injury) {
        this.injuryStatus = injury;
    }

    public Injury getInjury() {
       return injuryStatus;
    }
}
