package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class MatchEvent {

    private MatchData matchData;

    MatchEvent(MatchData matchData) {
        this.matchData = matchData;
    }

    public int getMinute() {
        return matchData.getMinute();
    }
}
