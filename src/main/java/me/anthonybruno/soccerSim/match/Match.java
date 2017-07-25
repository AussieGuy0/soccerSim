package me.anthonybruno.soccerSim.match;

import me.anthonybruno.soccerSim.AttemptsController;
import me.anthonybruno.soccerSim.Commentator;
import me.anthonybruno.soccerSim.match.events.BreakEvent;
import me.anthonybruno.soccerSim.match.events.MatchEventFactory;
import me.anthonybruno.soccerSim.match.events.MinuteEvent;
import me.anthonybruno.soccerSim.match.events.ScoringEvent;
import me.anthonybruno.soccerSim.team.member.Goalie;
import me.anthonybruno.soccerSim.team.member.Player;
import me.anthonybruno.soccerSim.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static me.anthonybruno.soccerSim.DiceRoller.rollD10;
import static me.anthonybruno.soccerSim.DiceRoller.rollD100;

/**
 * Match is class that is used to simulate a game between two teams.
 */
public class Match {
    private final AttemptsController attemptsController;

    private final Team homeTeam;
    private final Team awayTeam;

    private final MatchOptions matchOptions;
    private final MatchData matchData;
    private final List<MatchListener> listeners = new ArrayList<>();
    private final MatchEventFactory matchEventFactory;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates and simulates a match between two teams.
     *
     * @param homeTeam The team that is playing at their home ground.
     * @param awayTeam The team that has travelled to play.
     */
    public Match(Team homeTeam, Team awayTeam) {
        this(null, homeTeam, awayTeam);
    }

    public Match(MatchOptions matchOptions, Team homeTeam, Team awayTeam) {
        if (matchOptions == null) {
            this.matchOptions = MatchOptions.DEFAULT_OPTIONS;
        } else {
            this.matchOptions = matchOptions;
        }
        matchData = new MatchData(homeTeam, awayTeam);
        matchEventFactory = new MatchEventFactory(matchData);
        attemptsController = new AttemptsController(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void playMatch() {
        log.info("Match started between " + matchData.getHomeTeam() + " and " + matchData.getAwayTeam());
        System.out.println("Match starting: " + matchData.getHomeTeam().getName() + " vs " + matchData.getAwayTeam().getName());
        playHalf();

        if (matchOptions.isUsingAdvancedRules()) {
            cardCheckBothTeams();
        }
        BreakEvent halfTime = matchEventFactory.createBreakEvent();
        listeners.forEach(matchListener -> matchListener.handleBreakEvent(halfTime));

        matchData.startSecondHalf();
        playHalf();

        if (matchOptions.isUsingAdvancedRules()) {
            cardCheckBothTeams();
        }
        BreakEvent fullTime = matchEventFactory.createBreakEvent();
        listeners.forEach(matchListener -> matchListener.handleBreakEvent(fullTime));
        announceEndOfGame();

        if (matchOptions.isSeasonMatch()) {
            finalizeGame();
        }
    }

    private void announceEndOfGame() {
        int homeTeamAttemptsTotal = attemptsController.getHomeTeamAttempts().getFirstHalfAttempts() + attemptsController.getHomeTeamAttempts().getSecondHalfAttempts();
        int awayTeamAttemptsTotal = attemptsController.getAwayTeamAttempts().getFirstHalfAttempts() + attemptsController.getAwayTeamAttempts().getSecondHalfAttempts();
        System.out.println("There's the final whistle!");
        System.out.println(homeTeam.getName() + " |     Teams     | " + awayTeam.getName());
        System.out.println("    " + matchData.getHomeTeamGoals() + " |     Goals     | " + matchData.getAwayTeamGoals());
        System.out.println("   " + homeTeamAttemptsTotal + " |   Attempts    | " + awayTeamAttemptsTotal);
        System.out.println("    " +  matchData.getAwayTeamGoals() + " | Shots on Goal | " + matchData.getAwayTeamShotsTotal());
    }

    private void finalizeGame() {
        int homeTeamGoals = matchData.getHomeTeamGoals();
        int awayTeamGoals = matchData.getAwayTeamGoals();
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
            takePenalty(opposingTeam, team);
        } else if (value > 3) {
            determineYellowCardEvent(player, team, opposingTeam, value);
        } else if (value > 0) {
            takeFreeKick(team, opposingTeam);
        } else if (value > -2) {
            takeCorner(team, opposingTeam);
        } else if (value == -2) {
            takePenalty(team, opposingTeam);
        } else {
            takePenalty(team, opposingTeam);
            giveRedCard(opposingTeam, opposingTeam.getCardPlayer(rollD100()));
        }

    }

    private void determineYellowCardEvent(Player player, Team team, Team opposingTeam, int value) {
        giveYellowCard(team, player);
        switch (value) {
            case 12:
                determineInjury(opposingTeam.getCardPlayer(rollD10()));
                takePenalty(opposingTeam, team);
                break;
            case 11:
                determineInjury(opposingTeam.getCardPlayer(rollD10()));
                takeFreeKick(opposingTeam, team);
                break;
            case 10:
                determineInjury(player);
                takeCorner(opposingTeam, team);
                break;
            case 9:
                determineInjury(player);
                takeFreeKick(opposingTeam, team);
                break;
            case 8:
                takeFreeKick(opposingTeam, team);
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
        if (matchData.isFirstHalf()) {
            System.out.println("Start of first half");
            alternateAttempts(attemptsController.getHomeTeamAttempts().getFirstHalfAttempts(), attemptsController.getAwayTeamAttempts().getFirstHalfAttempts(),
                    attemptsController.getHomeTeamAttempts().getFirstHalfSOG(), attemptsController.getAwayTeamAttempts().getFirstHalfSOG());
        } else {
            matchData.setMinute(47);
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
                delay();
                matchData.addMinutes(2);
                MinuteEvent minuteEvent = matchEventFactory.createMinuteEvent();
                listeners.forEach(matchListener -> matchListener.handleMinuteEvent(minuteEvent));
                if (rollD100() <= homeTeamSOGChance) {
                    determineShot(homeTeam, awayTeam);
                    matchData.incrementHomeTeamShotsTotal();
                }
                homeAttemptsSoFar++;
            }

            if (awayAttemptsSoFar < awayTeamAttempts) {
                delay();
                matchData.addMinutes(2);
                MinuteEvent minuteEvent = matchEventFactory.createMinuteEvent();
                listeners.forEach(matchListener -> matchListener.handleMinuteEvent(minuteEvent));
                if (rollD100() < awayTeamSOGChance) {
                    determineShot(awayTeam, homeTeam);
                    matchData.incrementAwayTeamShotsTotal();
                }
                awayAttemptsSoFar++;
            }
        }
    }

    /**
     * Determines the shot taker and if they score a goal
     *
     * @param shootingTeam The team who is shooting
     */
    private void determineShot(Team shootingTeam, Team opposingTeam, int bonus) {
        Goalie goalie = opposingTeam.getGoalie();
        Player shooter = shootingTeam.getShooter(rollD100());
        System.out.print(matchData.getMinute() + "' ");
        int generatedNumber = rollD10();
        int shotScore = generatedNumber + goalie.getRating() + bonus;
        if (shotScore >= shooter.getGoal() || generatedNumber == 10) {
            if (shootingTeam.equals(homeTeam)) {
                matchData.incrementHomeTeamGoals();
            } else {
                matchData.incrementAwayTeamGoals();
            }
            ScoringEvent event = matchEventFactory.createScoringEvent(shootingTeam, shooter);
            listeners.forEach(matchListener -> matchListener.handleScoringEvent(event));
            System.out.println(Commentator.announceGoal(shooter));
            System.out.println("The score is now " + homeTeam.getName() + ": " + matchData.getHomeTeamGoals() + ",  " + awayTeam.getName() + ": " + matchData.getAwayTeamGoals());
        } else {
            if (shooter.getGoal() - shotScore >= Math.abs(goalie.getRating())) { //used to determine if goalie saved the shot
                System.out.println(Commentator.announceSave(shooter, goalie));
            } else {
                System.out.println(Commentator.announceMiss(shooter));
            }
        }
    }

    private void determineShot(Team shootingTeam, Team opposingTeam) {
        determineShot(shootingTeam, opposingTeam, 0);
    }

    private void takePenalty(Team shootingTeam, Team opposingTeam) {
        determineShot(shootingTeam, opposingTeam, 4);
    }

    private void takeCorner(Team shootingTeam, Team opposingTeam) {
        determineShot(shootingTeam, opposingTeam, -4);
    }

    private void takeFreeKick(Team shootingTeam, Team opposingTeam) {
        determineShot(shootingTeam, opposingTeam);
    }

    public void addMatchListener(MatchListener matchListener) {
        listeners.add(matchListener);
    }

    private void delay() {
        if (matchOptions.getMatchDelay() > 0) {
            try {
                Thread.sleep(matchOptions.getMatchDelay());
            } catch (InterruptedException e) {
                log.debug("Sleep was interrupted"); //Probably can safely ignore this
                Thread.currentThread().interrupt();
            }
        }

    }

}
