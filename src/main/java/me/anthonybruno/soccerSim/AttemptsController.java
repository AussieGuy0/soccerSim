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

            firstHalfAttempts = determineFirstHalfAttempts(firstTeam, opposingTeam);
            secondHalfAttempts = determineSecondHalfAttempts(firstTeam, opposingTeam);
            firstHalfSOG =  determineFirstHalfSOG(firstTeam, opposingTeam);
            secondHalfSOG = determineSecondHalfSOG(firstTeam, opposingTeam);
        }

        private int determineSOG(int sog) {
            sog += strategyModifier + formationModifier;
            if (sog < 10) {
                sog = 10;
            }
            return sog;
        }

        private int determineFirstHalfSOG(Team firstTeam, Team opposingTeam) {
            return determineSOG(firstTeam.getShotsGoal() + opposingTeam.getFirstHalfDefensiveShotOnGoal());
        }

        private int determineSecondHalfSOG(Team firstTeam, Team opposingTeam) {
            return determineSOG(firstTeam.getShotsGoal() + opposingTeam.getSecondHalfDefensiveShotOnGoal());
        }

        private int determineHalfAttempts(int halfAttempts) {
            halfAttempts += strategyModifier + formationModifier;
            if (halfAttempts > 25) {
                halfAttempts = 25;
            } else if (halfAttempts < 5) {
                halfAttempts = 5;
            }
            return halfAttempts;
        }

        private int determineFirstHalfAttempts(Team firstTeam, Team opposingTeam) {
            return determineHalfAttempts(firstTeam.getFirstHalfAttempts() + opposingTeam.getFirstHalfDefenseAttempts());
        }

        private int determineSecondHalfAttempts(Team firstTeam, Team opposingTeam) {
            return determineHalfAttempts(firstTeam.getSecondHalfAttempts() + opposingTeam.getSecondHalfDefenseAttempts());
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
