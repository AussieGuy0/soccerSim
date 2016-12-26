package me.anthonybruno.soccerSim.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    private final ArrayList<Goalie> goalies = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();

    public Team(String name, int firstHalfAttempts, int secondHalfAttempts, int shotsGoal, int firstHalfDefenseAttempts, int secondHalfDefenseAttempts, int firstHalfDefensiveShotOnGoal, int secondHalfDefensiveShotOnGoal, String formation, @SuppressWarnings("SameParameterValue") String strategy) {
        this.firstHalfAttributes = new HalfAttributes(firstHalfAttempts, firstHalfDefenseAttempts, firstHalfDefensiveShotOnGoal);
        this.secondHalfAttributes = new HalfAttributes(secondHalfAttempts, secondHalfDefenseAttempts, secondHalfDefensiveShotOnGoal);
        this.shotsGoal = shotsGoal;
        this.formation = formation;
        this.strategy = strategy;
        this.name = name;
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

    public Player getCardPlayer(int value) {
        return players.get(0);
    }

    public class CarBuilder {

    }
}
