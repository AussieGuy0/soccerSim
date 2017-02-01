package me.anthonybruno.soccerSim.ui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import me.anthonybruno.soccerSim.match.Match;
import me.anthonybruno.soccerSim.match.MatchListener;
import me.anthonybruno.soccerSim.match.events.MatchEvent;
import me.anthonybruno.soccerSim.match.events.ScoringEvent;
import me.anthonybruno.soccerSim.models.Team;

import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Created by anthony on 31/01/17.
 */
public class FriendlyScreen extends BorderPane {

    private final Team homeTeam;
    private final Team awayTeam;

    private Match match;

    public FriendlyScreen(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.setLeft(new TeamPanel(homeTeam));
        this.setRight(new TeamPanel(homeTeam));

        TextArea matchLog = new TextArea();
        this.setCenter(matchLog);

        match = new Match(homeTeam, awayTeam);
        match.addMatchListener(event -> {
               Platform.runLater(() -> matchLog.appendText(event.getMinute() + "' goal by " + event.getScoringPlayer().getName() + "\n"));
        });

        new Thread(() -> {
           match.playMatch();
        }).start();

    }
}
