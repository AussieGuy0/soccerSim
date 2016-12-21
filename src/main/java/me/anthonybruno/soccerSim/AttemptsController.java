package me.anthonybruno.soccerSim;

import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 19/12/16.
 */
public class AttemptsController {

    private TeamAttempts homeTeamAttempts;
    private TeamAttempts awayTeamAttempts;

    public AttemptsController(Team homeTeam, Team awayTeam) {
        homeTeamAttempts =  new TeamAttempts(homeTeam, awayTeam);
        awayTeamAttempts =  new TeamAttempts(awayTeam, homeTeam);

    }

    public TeamAttempts getHomeTeamAttempts() {
       return homeTeamAttempts;
    }

    public TeamAttempts getAwayTeamAttempts() {
       return awayTeamAttempts;
    }


    public class TeamAttempts {
        private final int firstHalfAttempts;
        private final int secondHalfAttempts;
        private final int firstHalfSOG;
        private final int secondHalfSOG;
        private final int formationModifier;
        private final int strategyModifier;

        public TeamAttempts(Team firstTeam, Team opposingTeam) {
            formationModifier = determineFormationModifier();
            strategyModifier = determineStrategyModifier();

            firstHalfAttempts = firstTeam.getFirstHalfattempts() - opposingTeam.getFirstHalfDefenseAttempts() + strategyModifier + formationModifier;
            secondHalfAttempts = firstTeam.getSecondHalfattempts() - opposingTeam.getSecondHalfDefenseAttempts() + strategyModifier + formationModifier;
            firstHalfSOG = firstTeam.getShotsGoal() - opposingTeam.getFirstHalfDefensiveShotOnGoal() + strategyModifier + formationModifier;
            secondHalfSOG = firstTeam.getShotsGoal() - opposingTeam.getSecondHalfDefensiveShotOnGoal() + strategyModifier + formationModifier;
        }

        public int getFirstHalfAttempts() {
            return firstHalfAttempts;
        }

        public int getSecondHalfAttempts() {
            return secondHalfAttempts;
        }

        public int getFirstHalfSOG() {
            return firstHalfSOG;
        }

        public int getSecondHalfSOG() {
            return secondHalfSOG;
        }

        public int getFormationModifier() {
            return formationModifier;
        }

        public int getStrategyModifier() {
            return strategyModifier;
        }

        //todo: These
        private int determineFormationModifier() {
            return 0;
        }

        private int determineStrategyModifier() {
            return 0;
        }
    }
}
