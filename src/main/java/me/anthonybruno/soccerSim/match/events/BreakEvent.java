package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;

public class BreakEvent extends MatchEvent {

    private final boolean halfTime;

    BreakEvent(MatchData matchData) {
        super(matchData);
        halfTime = matchData.isFirstHalf();
    }

    public boolean isHalfTime() {
        return halfTime;
    }

    public boolean isFullTime() {
        return !halfTime;
    }
}
