package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class MatchEvent {

    private final int minute;
    private final int homeScore;
    private final int awayScore;

    MatchEvent(MatchData matchData) {
        minute = matchData.getMinute();
        homeScore = matchData.getHomeTeamGoals();
        awayScore = matchData.getAwayTeamGoals();
    }

    public int getMinute() {
        return minute;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
