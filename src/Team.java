import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anthony on 1/07/16.
 */
public class Team {

    private ArrayList<Player> players;
    private String name;

    private int firstHalfattempts;
    private int secondHalfattempts;
    private int shotsGoal;
    private int firstHalfDefenseAttempts;
    private int secondHalfDefenseAttempts;
    private int firstHalfDefensiveShotOnGoal;
    private int secondHalfDefensiveShotOnGoal;
    private String formation;
    private String strategy;

    public Team(String name, int firstHalfattempts, int secondHalfAttempts, int shotsGoal, int firstHalfDefenseAttempts, int secondHalfDefenseAttempts, int firstHalfDefensiveShotOnGoal, int secondHalfDefensiveShotOnGoal, String formation, String strategy) {
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

    public void loadPlayers(String s) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(s));
            while (reader.ready()) {
                String[] line = reader.readLine().split("\\|");
                players.add(new Player(line[0],Integer.parseInt(line[1]),Integer.parseInt(line[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getShooter(int value) {
        for (Player player : players) {
            if (player.getShotRange() >= value) {
                return player;
            }
        }
        System.err.println("Couldn't find player, returning first player");
        return players.get(0);
    }
}
