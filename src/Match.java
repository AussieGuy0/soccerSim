import java.util.Random;

/**
 * Created by anthony on 1/07/16.
 */
public class Match {

    private int homeTeamGoals;
    private int awayTeamGoals;
    private Team homeTeam;
    private Team awayTeam;
    private boolean firstHalf;

    public Match(Team homeTeam, Team awayTeam) {
        System.out.println("Match starting: "+homeTeam.getName()+ " vs "+awayTeam.getName());
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        homeTeamGoals = 0;
        awayTeamGoals = 0;
        firstHalf = true;

        playHalf();
        playHalf();

        System.out.println("There's the final whistle!");
        System.out.println(homeTeam.getName() + " " +homeTeamGoals+ " - " + awayTeam.getName() + " " + awayTeamGoals);
    }

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
            homeTeamAttempts = homeTeam.getSecondHalfattempts()  - awayTeam.getSecondHalfDefenseAttempts();
            awayTeamAttempts = awayTeam.getSecondHalfattempts()  - homeTeam.getSecondHalfDefenseAttempts();
            homeTeamSOGChance = homeTeam.getShotsGoal() - awayTeam.getSecondHalfDefensiveShotOnGoal();
            awayTeamSOGChance = awayTeam.getShotsGoal() - homeTeam.getSecondHalfDefensiveShotOnGoal();

        }

        Random random =  new Random();

        int homeTeamShots = 0;
        for (int i = 0; i < homeTeamAttempts; i++) {
            if ( random.nextInt(100)+ 1 <= homeTeamSOGChance) {
                homeTeamShots++;
            }
        }
        int awayTeamShots = 0;
        for (int i = 0; i < awayTeamAttempts; i++) {
            if(random.nextInt(100) + 1 < awayTeamSOGChance) {
                awayTeamShots++;
            }
        }
        determineShots(homeTeam, homeTeamShots);
        determineShots(awayTeam, awayTeamShots);
        System.out.println();
    }

    private void determineShots(Team team, int numOfShots) {
        Random random = new Random();
        for (int i = 0; i < numOfShots; i++) {
            Player shooter = team.getShooter(random.nextInt(100)+1);
            if (random.nextInt(10)+1 >= shooter.getGoal()) {
                if (team.equals(homeTeam)) {
                    homeTeamGoals++;
                } else {
                    awayTeamGoals++;
                }
                System.out.println("Goal for "+team.getName()+"! What a shot by "+shooter.getName() + "!");
                System.out.println("The score is now: " +homeTeamGoals+ " - " +awayTeamGoals);
            } else {
                System.out.println("Close miss by " +shooter.getName());
            }
        }
    }
}
