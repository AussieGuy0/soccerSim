import java.util.Random;

/**
 * Match is class that is used to simulate a game between two teams.
 */
public class Match {

    private int homeTeamGoals;
    private int awayTeamGoals;
    private int homeTeamAttemptsTotal;
    private int awayTeamAttemptsTotal;
    private int homeTeamShotsTotal;
    private int awayTeamShotsTotal;
    private final Team homeTeam;
    private final Team awayTeam;
    private boolean firstHalf;
    private final Random random;
    private int minute;

    /**
     * Creates and simulates a match between two teams.
     *
     * @param homeTeam The team that is playing at their home ground.
     * @param awayTeam The team that has travelled to play.
     */
    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        homeTeamGoals = 0;
        awayTeamGoals = 0;

        homeTeamAttemptsTotal = 0;
        homeTeamShotsTotal = 0;
        awayTeamAttemptsTotal = 0;
        awayTeamShotsTotal = 0;
        random = new Random();

    }

    public void playMatch() {
        System.out.println("Match starting: " + homeTeam.getName() + " vs " + awayTeam.getName());
        firstHalf = true; //Used to determine which half is currently being played
        playHalf();
        playHalf();
        System.out.println("There's the final whistle!");
        System.out.println(homeTeam.getName() + " |     Teams     | " + awayTeam.getName());
        System.out.println("    " + homeTeamGoals + " |     Goals     | "  + awayTeamGoals);
        System.out.println("   " + homeTeamAttemptsTotal + " |   Attempts    | "  + awayTeamAttemptsTotal);
        System.out.println("    " + homeTeamShotsTotal + " | Shots on Goal | "  + awayTeamShotsTotal);
    }

    /**
     * Determines the number of attempts (shots), the chance each attempt will be a shot on target, and the number
     * of shots on goal during a half.
     */
    private void playHalf() {
        int homeTeamAttempts, awayTeamAttempts, homeTeamSOGChance, awayTeamSOGChance;
        if (firstHalf) {
            minute = 1;
            System.out.println("Start of first half");
            homeTeamAttempts = homeTeam.getFirstHalfattempts() - awayTeam.getFirstHalfDefenseAttempts();
            awayTeamAttempts = awayTeam.getFirstHalfattempts() - homeTeam.getFirstHalfDefenseAttempts();
            homeTeamSOGChance = homeTeam.getShotsGoal() - awayTeam.getFirstHalfDefensiveShotOnGoal();
            awayTeamSOGChance = awayTeam.getShotsGoal() - homeTeam.getFirstHalfDefensiveShotOnGoal();
            firstHalf = false;
        } else {
            minute = 47;
            System.out.println("Start of second half");
            homeTeamAttempts = homeTeam.getSecondHalfattempts() - awayTeam.getSecondHalfDefenseAttempts();
            awayTeamAttempts = awayTeam.getSecondHalfattempts() - homeTeam.getSecondHalfDefenseAttempts();
            homeTeamSOGChance = homeTeam.getShotsGoal() - awayTeam.getSecondHalfDefensiveShotOnGoal();
            awayTeamSOGChance = awayTeam.getShotsGoal() - homeTeam.getSecondHalfDefensiveShotOnGoal();
        }
        homeTeamAttemptsTotal += homeTeamAttempts;
        awayTeamAttemptsTotal += awayTeamAttempts;

        alternateAttempts(homeTeamAttempts, awayTeamAttempts, homeTeamSOGChance, awayTeamSOGChance);
        System.out.println();
    }

    /**
     * Alternates checking both teams if an attempt is a shot on goal. If shot on goal, determines shooter and if they
     * score.
     *
     * @param homeTeamAttempts  The amount of attempts the home team has
     * @param awayTeamAttempts  The amount of attempts the away team has
     * @param homeTeamSOGChance The chance the home team will score
     * @param awayTeamSOGChance The chance the away team will score.
     */
    private void alternateAttempts(int homeTeamAttempts, int awayTeamAttempts, int homeTeamSOGChance, int awayTeamSOGChance) {
        int homeAttemptsSoFar = 0;
        int awayAttemptsSoFar = 0;
        for (int i = 0; i < Math.max(homeTeamAttempts, awayTeamAttempts); i++) {
            if (homeAttemptsSoFar < homeTeamAttempts) {
                minute += 2;
                if (random.nextInt(100) + 1 <= homeTeamSOGChance) {
                    determineShot(homeTeam, awayTeam.getGoalie());
                    homeTeamShotsTotal++;
                }
                homeAttemptsSoFar++;
            }

            if (awayAttemptsSoFar < awayTeamAttempts) {
                minute += 2;
                if (random.nextInt(100) + 1 < awayTeamSOGChance) {
                    determineShot(awayTeam, homeTeam.getGoalie());
                    awayTeamShotsTotal++;
                }
                awayAttemptsSoFar++;
            }
        }
    }

    /**
     * Determines the shot taker and if they score a goal
     *
     * @param team The team who is shooting
     */
    private void determineShot(Team team, Goalie goalie) {
        Player shooter = team.getShooter(random.nextInt(100) + 1);
        System.out.print(minute + "' ");
        int shotScore = (random.nextInt(10) + 1) + goalie.getRating();
        if (shotScore >= shooter.getGoal()) {
            if (team.equals(homeTeam)) {
                homeTeamGoals++;
            } else {
                awayTeamGoals++;
            }
            System.out.println("Goal for " + team.getName() + "! What a shot by " + shooter.getName() + "!");
            System.out.println("The score is now: " + homeTeamGoals + " - " + awayTeamGoals);
        } else {
            if (shooter.getGoal() - shotScore >= Math.abs(goalie.getRating())) { //used to determine if goalie saved the shot
                System.out.println("Shot by "+ shooter.getName() + " saved by " + goalie.getName() + "!");
            } else {
                System.out.println("Close miss by " + shooter.getName());
            }
        }
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }
}
