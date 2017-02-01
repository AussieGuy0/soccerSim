package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class MatchEvent {

    private MatchData matchData;
    private final int minute;

    MatchEvent(MatchData matchData) {
        this.matchData = matchData;
        minute = matchData.getMinute();
    }

    public int getMinute() {
        return minute;
    }
}
