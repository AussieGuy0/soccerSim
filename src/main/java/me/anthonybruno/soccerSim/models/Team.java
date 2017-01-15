package me.anthonybruno.soccerSim.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores information about a team. A team is composed of players.
 */
public class Team {

    private final String name;
    private final int shotsGoal;
    private final HalfAttributes firstHalfAttributes;
    private final HalfAttributes secondHalfAttributes;
    private final String formation;
    private final String strategy;

    private final Stats stats = new Stats();
    private final List<Goalie> goalies;
    private final List<Player> players;

    private Team(String name, int shotsGoal, String formation, String strategy, HalfAttributes firstHalfAttributes, HalfAttributes secondHalfAttributes, List<Player> players, List<Goalie> goalies) {
        this.name = name;
        this.shotsGoal = shotsGoal;
        this.formation = formation;
        this.strategy = strategy;
        this.firstHalfAttributes = firstHalfAttributes;
        this.secondHalfAttributes = secondHalfAttributes;
        this.players = players;
        this.goalies = goalies;
    }


    public String getName() {
        return name;
    }

    public int getShotsGoal() {
        return shotsGoal;
    }

    public String getFormation() {
        return formation;
    }

    public String getStrategy() {
        return strategy;
    }

    public int getSecondHalfAttempts() {
        return secondHalfAttributes.getAttempts();
    }

    public int getFirstHalfAttempts() {
        return firstHalfAttributes.getAttempts();
    }

    public int getFirstHalfDefenseAttempts() {
        return firstHalfAttributes.getDefenseAttempts();
    }

    public int getSecondHalfDefenseAttempts() {
        return secondHalfAttributes.getDefenseAttempts();
    }

    public int getFirstHalfDefensiveShotOnGoal() {
        return firstHalfAttributes.getDefensiveShotOnGoal();
    }

    public int getSecondHalfDefensiveShotOnGoal() {
        return secondHalfAttributes.getDefensiveShotOnGoal();
    }

    public Stats getStats() {
        return stats;
    }


    /**
     * Returns the shooter based on generated value from Match.
     *
     * @param value The randomly generated value.
     * @return A Player who is considered the shooter.
     */
    public Player getShooter(int value) {
        for (Player player : players) {
            if (value >= player.getShotRange().getMin() && value <= player.getShotRange().getMax()) {
                return player;
            }
        }
        System.err.println("Couldn't find shooter, returning first player");
        return players.get(0);
    }

    public Goalie getGoalie() {
        if (goalies.size() > 0) {
            return goalies.get(0);
        } else {
            System.err.println("No goalies for team: " + name);
            return Goalie.noopGoalie;
        }
    }

    public Player getCardPlayer(int value) { //TODO: THIS
        return players.get(0);
    }

    public static class Builder {
        private String name;
        private int shotsGoal;
        private HalfAttributes firstHalfAttributes;
        private HalfAttributes secondHalfAttributes;
        private String formation;
        private String strategy;

        private final List<Player> players = new ArrayList<>();
        private final List<Goalie> goalies = new ArrayList<>();

        public void name(String name) {
            this.name = name;
        }

        public void shotsGoal(int shotsGoal) {
            this.shotsGoal = shotsGoal;
        }


        public void firstHalfAttributes(HalfAttributes firstHalfAttributes) {
            this.firstHalfAttributes = firstHalfAttributes;
        }


        public void secondHalfAttributes(HalfAttributes secondHalfAttributes) {
            this.secondHalfAttributes = secondHalfAttributes;
        }


        public void formation(String formation) {
            this.formation = formation;
        }

        public void strategy(String strategy) {
            this.strategy = strategy;
        }

        public void addPlayer(Player player) {
            players.add(player);
        }

        public void addGoalie(Goalie goalie) {
            goalies.add(goalie);
        }

        public Team build() {
            return new Team(name, shotsGoal, formation, strategy, firstHalfAttributes, secondHalfAttributes, players, goalies);
        }
    }
}
