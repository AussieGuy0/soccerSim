package me.anthonybruno.soccerSim.match;

import me.anthonybruno.soccerSim.match.events.MatchEvent;
import me.anthonybruno.soccerSim.match.events.MinuteEvent;
import me.anthonybruno.soccerSim.match.events.ScoringEvent;

/**
 * Created by anthony on 1/02/17.
 */
public interface MatchListener {

    //todo: ShootEvent, HalfTime/FullTimeEvent, InjuryEvent, CardEvent, DeadBallEvent

    void handleScoringEvent(ScoringEvent event);

    void handleMinuteEvent(MinuteEvent event);

}
