package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;
import me.anthonybruno.soccerSim.team.member.Player;
import me.anthonybruno.soccerSim.team.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class MatchEventFactory {

    private MatchData matchData;

    public MatchEventFactory(MatchData matchData) {
        this.matchData = matchData;
    }

    public ScoringEvent createScoringEvent(Team scoringTeam, Player scoringPlayer) {
        return new ScoringEvent(matchData, scoringTeam, scoringPlayer);
    }

    public MinuteEvent createMinuteEvent() {
        return new MinuteEvent(matchData);
    }

    public BreakEvent createBreakEvent() {
        return new BreakEvent(matchData);
    }
}
