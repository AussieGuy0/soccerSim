package me.anthonybruno.soccerSim.match;

import me.anthonybruno.soccerSim.match.events.MatchEvent;
import me.anthonybruno.soccerSim.match.events.ScoringEvent;

/**
 * Created by anthony on 1/02/17.
 */
public interface MatchListener {

    void handleScoringEvent(ScoringEvent event);

}
