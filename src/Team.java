import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class stores information about a team. A team is composed of players.
 */
public class Team {

    //TODO: Add goalies
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
                players.add(new Player(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the shooter based on generated value from Match.
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
}
