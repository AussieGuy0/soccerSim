package me.anthonybruno.soccerSim;

import me.anthonybruno.soccerSim.models.Goalie;
import me.anthonybruno.soccerSim.models.Player;
import me.anthonybruno.soccerSim.models.Team;

import static me.anthonybruno.soccerSim.DiceRoller.rollD10;
import static me.anthonybruno.soccerSim.DiceRoller.rollD100;

/**
 * Match is class that is used to simulate a game between two teams.
 */
public class Match {
    private int homeTeamGoals;
    private int awayTeamGoals;
    private int homeTeamShotsTotal;
    private int awayTeamShotsTotal;
    private final Team homeTeam;
    private final Team awayTeam;
    private boolean firstHalf;
    private int minute;
    private AttemptsController attemptsController;

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

        homeTeamShotsTotal = 0;
        awayTeamShotsTotal = 0;

        attemptsController = new AttemptsController(homeTeam, awayTeam);

    }

    public void playMatch() {
        System.out.println("Match starting: " + homeTeam.getName() + " vs " + awayTeam.getName());
        firstHalf = true; //Used to determine which half is currently being played
        playHalf();
        cardCheckBothTeams();
        playHalf();
        cardCheckBothTeams();
        announceEndOfGame();
        finalizeGame();
    }

    public void announceEndOfGame() {
        int homeTeamAttemptsTotal = attemptsController.getHomeTeamAttempts().getFirstHalfAttempts() + attemptsController.getHomeTeamAttempts().getSecondHalfAttempts();
        int awayTeamAttemptsTotal = attemptsController.getAwayTeamAttempts().getFirstHalfAttempts() + attemptsController.getAwayTeamAttempts().getSecondHalfAttempts();
        System.out.println("There's the final whistle!");
        System.out.println(homeTeam.getName() + " |     Teams     | " + awayTeam.getName());
        System.out.println("    " + homeTeamGoals + " |     Goals     | " + awayTeamGoals);
        System.out.println("   " + homeTeamAttemptsTotal + " |   Attempts    | " + awayTeamAttemptsTotal);
        System.out.println("    " + homeTeamShotsTotal + " | Shots on Goal | " + awayTeamShotsTotal);
    }

    public void finalizeGame() {
        if (homeTeamGoals > awayTeamGoals) {
            homeTeam.getStats().addWin(homeTeamGoals, awayTeamGoals);
            awayTeam.getStats().addLoss(awayTeamGoals, homeTeamGoals);
        } else if (awayTeamGoals > homeTeamGoals) {
            homeTeam.getStats().addLoss(homeTeamGoals, awayTeamGoals);
            awayTeam.getStats().addWin(awayTeamGoals, homeTeamGoals);
        } else {
            homeTeam.getStats().addDraw(homeTeamGoals, awayTeamGoals);
            awayTeam.getStats().addDraw(awayTeamGoals, homeTeamGoals);
        }
    }

    private void cardCheckBothTeams() {
        cardCheck(homeTeam, awayTeam);
        cardCheck(awayTeam, homeTeam);
    }

    private void cardCheck(Team team, Team opposingTeam) {
        Player player = team.getCardPlayer(rollD100());
        int value = rollD10();
        if (value > 12) {
            giveRedCard(team, player);
            takePenalty(opposingTeam, team.getGoalie());
        } else if (value > 3) {
            determineYellowCardEvent(player, team, opposingTeam, value);
        } else if (value > 0) {
            takeFreeKick(team, opposingTeam.getGoalie());
        } else if (value > -2) {
            takeCorner(team, opposingTeam.getGoalie());
        } else if (value == -2) {
            takePenalty(team, opposingTeam.getGoalie());
        } else {
            takePenalty(team, opposingTeam.getGoalie());
            giveRedCard(opposingTeam, opposingTeam.getCardPlayer(rollD100()));
        }

    }

    private void determineYellowCardEvent(Player player, Team team, Team opposingTeam, int value) {
        giveYellowCard(team, player);
        switch (value) {
            case 12:
                determineInjury(opposingTeam.getCardPlayer(rollD10()));
                takePenalty(opposingTeam, team.getGoalie());
                break;
            case 11:
                determineInjury(opposingTeam.getCardPlayer(rollD10()));
                takeFreeKick(opposingTeam, team.getGoalie());
                break;
            case 10:
                determineInjury(player);
                takeCorner(opposingTeam, team.getGoalie());
                break;
            case 9:
                determineInjury(player);
                takeFreeKick(opposingTeam, team.getGoalie());
                break;
            case 8:
                takeFreeKick(opposingTeam, team.getGoalie());
                break;
            default: break;
        }
    }

    private void giveYellowCard(Team team, Player player) {

    }

    private void giveRedCard(Team team, Player player) {
        //opponent gets +2 attempts

    }

    private void determineInjury(Player player) {
        int value = rollD10();
        if (value > 10) {
            player.setInjury(Player.Injury.SEASON);
            //player out for game + tournement
        } else if (value == 10) {
            player.setInjury(Player.Injury.MAJOR);
        } else if (value == 9) {
            player.setInjury(Player.Injury.MATCH);
        } else if (value > 5) {
            //reduced goal rolls
            player.setInjury(Player.Injury.MINOR);
        } else if (value > 0) {
            player.setInjury(Player.Injury.MINOR);
        } else{
            //opponent gets red card
        }


    }


    /**
     * Determines the number of attempts (shots), the chance each attempt will be a shot on target, and the number
     * of shots on goal during a half.
     */
    private void playHalf() {
        if (firstHalf) {
            minute = 1;
            System.out.println("Start of first half");
            firstHalf = false;
            alternateAttempts(attemptsController.getHomeTeamAttempts().getFirstHalfAttempts(), attemptsController.getAwayTeamAttempts().getFirstHalfAttempts(),
                    attemptsController.getHomeTeamAttempts().getFirstHalfSOG(), attemptsController.getAwayTeamAttempts().getFirstHalfSOG());
        } else {
            minute = 47;
            System.out.println("Start of second half");
            alternateAttempts(attemptsController.getHomeTeamAttempts().getSecondHalfAttempts(), attemptsController.getAwayTeamAttempts().getSecondHalfAttempts(),
                    attemptsController.getHomeTeamAttempts().getSecondHalfSOG(), attemptsController.getAwayTeamAttempts().getSecondHalfSOG());
        }

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
                if (rollD100() <= homeTeamSOGChance) {
                    determineShot(homeTeam, awayTeam.getGoalie());
                    homeTeamShotsTotal++;
                }
                homeAttemptsSoFar++;
            }

            if (awayAttemptsSoFar < awayTeamAttempts) {
                minute += 2;
                if (rollD100() < awayTeamSOGChance) {
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
    private void determineShot(Team team, Goalie goalie, int bonus) {
        Player shooter = team.getShooter(rollD100());
        System.out.print(minute + "' ");
        int generatedNumber = rollD10();
        int shotScore = generatedNumber + goalie.getRating() + bonus;
        if (shotScore >= shooter.getGoal() || generatedNumber == 10) {
            if (team.equals(homeTeam)) {
                homeTeamGoals++;
            } else {
                awayTeamGoals++;
            }
            System.out.println(Commentator.announceGoal(shooter));
            System.out.println("The score is now " + homeTeam.getName() + ": " + homeTeamGoals + ",  " + awayTeam.getName() + ": " + awayTeamGoals);
        } else {
            if (shooter.getGoal() - shotScore >= Math.abs(goalie.getRating())) { //used to determine if goalie saved the shot
                System.out.println(Commentator.announceSave(shooter, goalie));
            } else {
                System.out.println(Commentator.announceMiss(shooter));
            }
        }
    }

    private void determineShot(Team team, Goalie goalie) {
        determineShot(team, goalie, 0);
    }

    private void takePenalty(Team team, Goalie goalie) {
        determineShot(team, goalie, 4);
    }

    private void takeCorner(Team team, Goalie goalie) {
        determineShot(team, goalie, -4);
    }

    private void takeFreeKick(Team team, Goalie goalie) {
        determineShot(team, goalie);
    }

}
