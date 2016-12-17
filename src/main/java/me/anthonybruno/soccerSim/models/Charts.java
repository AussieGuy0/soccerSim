package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 15/07/16.
 */
public class Charts {

    private static Charts chartsSingleton = null;
    private attemptsAndSOGModifier[][] formationChart = new attemptsAndSOGModifier[3][3];
    private attemptsAndSOGModifier[][] strategyChart;

    private Charts() {

    }

    public static Charts getInstance() {
        if (chartsSingleton == null) {
            chartsSingleton = new Charts();
        }
        return chartsSingleton;
    }


    private class attemptsAndSOGModifier {
        final int homeAttemptModifier;
        final int awayAttemptModifier;
        final int homeSOGModifier;
        final int awaySOGModifier;

        public attemptsAndSOGModifier(int homeAttemptModifier, int awayAttemptModifier, int homeSOGModifier, int awaySOGModifier) {
            this.homeAttemptModifier = homeAttemptModifier;
            this.awayAttemptModifier = awayAttemptModifier;
            this.homeSOGModifier = homeSOGModifier;
            this.awaySOGModifier = awaySOGModifier;
        }

        public int getHomeAttemptModifier() {
            return homeAttemptModifier;
        }

        public int getAwayAttemptModifier() {
            return awayAttemptModifier;
        }

        public int getHomeSOGModifier() {
            return homeSOGModifier;
        }

        public int getAwaySOGModifier() {
            return awaySOGModifier;
        }
    }
}
