package me.anthonybruno.soccerSim.match.events;

import me.anthonybruno.soccerSim.match.MatchData;
import me.anthonybruno.soccerSim.models.Player;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 1/02/17.
 */
public class ScoringEvent extends MatchEvent {

    private Team scoringTeam;
    private Player scoringPlayer;

    ScoringEvent(MatchData matchData, Team scoringTeam, Player scoringPlayer) {
        super(matchData);
        this.scoringTeam = scoringTeam;
        this.scoringPlayer = scoringPlayer;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public Player getScoringPlayer() {
        return scoringPlayer;
    }


}
