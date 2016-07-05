import java.util.Random;

/**
 * Match is class that is used to simulate a game between two teams.
 */
public class Match {

    private int homeTeamGoals;
    private int awayTeamGoals;
    private final Team homeTeam;
    private final Team awayTeam;
    private boolean firstHalf;
    private Random random;

    /**
     * Creates and simulates a match between two teams.
     * @param homeTeam The team that is playing at their home ground.
     * @param awayTeam The team that has travelled to play.
     */
    public Match(Team homeTeam, Team awayTeam) {
        System.out.println("Match starting: " + homeTeam.getName() + " vs " + awayTeam.getName());
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        homeTeamGoals = 0;
        awayTeamGoals = 0;
        firstHalf = true; //Used to determine which half is currently being played

        playHalf();
        playHalf();

        System.out.println("There's the final whistle!");
        System.out.println(homeTeam.getName() + " " + homeTeamGoals + " - " + awayTeam.getName() + " " + awayTeamGoals);
    }

    /**
     * Determines the number of attempts (shots), the chance each attempt will be a shot on target, and the number
     * of shots on goal during a half.
     */
    private void playHalf() {
        int homeTeamAttempts, awayTeamAttempts, homeTeamSOGChance, awayTeamSOGChance;
        if (firstHalf) {
            System.out.println("Start of first half");
            homeTeamAttempts = homeTeam.getFirstHalfattempts() - awayTeam.getFirstHalfDefenseAttempts();
            awayTeamAttempts = awayTeam.getFirstHalfattempts() - homeTeam.getFirstHalfDefenseAttempts();
            homeTeamSOGChance = homeTeam.getShotsGoal() - awayTeam.getFirstHalfDefensiveShotOnGoal();
            awayTeamSOGChance = awayTeam.getShotsGoal() - homeTeam.getFirstHalfDefensiveShotOnGoal();
            firstHalf = false;
        } else {
            System.out.println("Start of second half");
            homeTeamAttempts = homeTeam.getSecondHalfattempts() - awayTeam.getSecondHalfDefenseAttempts();
            awayTeamAttempts = awayTeam.getSecondHalfattempts() - homeTeam.getSecondHalfDefenseAttempts();
            homeTeamSOGChance = homeTeam.getShotsGoal() - awayTeam.getSecondHalfDefensiveShotOnGoal();
            awayTeamSOGChance = awayTeam.getShotsGoal() - homeTeam.getSecondHalfDefensiveShotOnGoal();
        }
        random = new Random();
        int homeTeamShots = 0;
        for (int i = 0; i < homeTeamAttempts; i++) {
            if (random.nextInt(100) + 1 <= homeTeamSOGChance) {
                homeTeamShots++;
            }
        }
        int awayTeamShots = 0;
        for (int i = 0; i < awayTeamAttempts; i++) {
            if (random.nextInt(100) + 1 < awayTeamSOGChance) {
                awayTeamShots++;
            }
        }
        determineShots(homeTeam, homeTeamShots);
        determineShots(awayTeam, awayTeamShots);
        System.out.println();
    }

    /**
     * Determines the shooter for each shot on goal and calculates if it is a goal or not.
     * @param team The team that is shooting.
     * @param numOfShots The number of shots that the team has had.
     */
    private void determineShots(Team team, int numOfShots) {
        for (int i = 0; i < numOfShots; i++) {
            Player shooter = team.getShooter(random.nextInt(100) + 1);
            if (random.nextInt(10) + 1 >= shooter.getGoal()) {
                if (team.equals(homeTeam)) {
                    homeTeamGoals++;
                } else {
                    awayTeamGoals++;
                }
                System.out.println("Goal for " + team.getName() + "! What a shot by " + shooter.getName() + "!");
                System.out.println("The score is now: " + homeTeamGoals + " - " + awayTeamGoals);
            } else {
                System.out.println("Close miss by " + shooter.getName());
            }
        }
    }
}
