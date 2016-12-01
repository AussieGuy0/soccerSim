package me.anthonybruno.soccerSim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class stores information about a team. A team is composed of players.
 */
public class Team {

    private final ArrayList<Goalie> goalies;
    private final ArrayList<Player> players;
    private final String name;

    private final int firstHalfattempts;
    private final int secondHalfattempts;
    private final int shotsGoal;
    private final int firstHalfDefenseAttempts;
    private final int secondHalfDefenseAttempts;
    private final int firstHalfDefensiveShotOnGoal;
    private final int secondHalfDefensiveShotOnGoal;
    private final String formation;
    private final String strategy;
    private final Stats stats;

    public Team(String name, int firstHalfattempts, int secondHalfAttempts, int shotsGoal, int firstHalfDefenseAttempts, int secondHalfDefenseAttempts, int firstHalfDefensiveShotOnGoal, int secondHalfDefensiveShotOnGoal, String formation, @SuppressWarnings("SameParameterValue") String strategy) {
        this.firstHalfattempts = firstHalfattempts;
        this.secondHalfattempts = secondHalfAttempts;
        this.shotsGoal = shotsGoal;
        this.firstHalfDefenseAttempts = firstHalfDefenseAttempts;
        this.secondHalfDefenseAttempts = secondHalfDefenseAttempts;
        this.firstHalfDefensiveShotOnGoal = firstHalfDefensiveShotOnGoal;
        this.secondHalfDefensiveShotOnGoal = secondHalfDefensiveShotOnGoal;
        this.formation = formation;
        this.strategy = strategy;
        this.name = name;
        players = new ArrayList<>();
        goalies = new ArrayList<>();
        stats = new Stats();
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

    public int getSecondHalfattempts() {
        return secondHalfattempts;
    }

    public int getFirstHalfattempts() {
        return firstHalfattempts;
    }

    public int getFirstHalfDefenseAttempts() {
        return firstHalfDefenseAttempts;
    }

    public int getSecondHalfDefenseAttempts() {
        return secondHalfDefenseAttempts;
    }

    public int getFirstHalfDefensiveShotOnGoal() {
        return firstHalfDefensiveShotOnGoal;
    }

    public int getSecondHalfDefensiveShotOnGoal() {
        return secondHalfDefensiveShotOnGoal;
    }

    public Stats getStats() {
        return stats;
    }

    public void loadPlayers(String s) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(s));
            while (reader.ready()) {
                String[] line = reader.readLine().split("\\|");
                if (line.length == 3) { //is player
                    players.add(new Player(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])));
                } else { //is Goalie
                    goalies.add(new Goalie(line[0], Integer.parseInt(line[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the shooter based on generated value from Match.
     *
     * @param value The randomly generated value.
     * @return A Player who is considered the shooter.
     */
    public Player getShooter(int value) {
        for (Player player : players) {
            if (player.getShotRange() >= value) {
                return player;
            }
        }
        System.err.println("Couldn't find player, returning first player");
        return players.get(0);
    }

    public Goalie getGoalie() {
        return goalies.get(0);
    }
}
