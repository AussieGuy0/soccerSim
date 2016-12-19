package me.anthonybruno.soccerSim;

import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 19/12/16.
 */
public class AttemptsController {

    private Team homeTeam;
    private Team awayTeam;
    private TeamAttempts homeTeamAttempts;
    private TeamAttempts awayTeamAttempts;

    public AttemptsController(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

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
            firstHalfAttempts = firstTeam.getFirstHalfattempts() - opposingTeam.getFirstHalfDefenseAttempts();
            secondHalfAttempts = firstTeam.getSecondHalfattempts() - opposingTeam.getSecondHalfDefenseAttempts();
            firstHalfSOG = firstTeam.getShotsGoal() - opposingTeam.getFirstHalfDefensiveShotOnGoal();
            secondHalfSOG = firstTeam.getShotsGoal() - opposingTeam.getSecondHalfDefensiveShotOnGoal();

            //todo: These
            formationModifier = 0;
            strategyModifier = 0;
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
    }
}
