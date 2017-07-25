package me.anthonybruno.soccerSim.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import me.anthonybruno.soccerSim.match.Match;
import me.anthonybruno.soccerSim.match.MatchListener;
import me.anthonybruno.soccerSim.match.MatchOptions;
import me.anthonybruno.soccerSim.match.events.BreakEvent;
import me.anthonybruno.soccerSim.match.events.MinuteEvent;
import me.anthonybruno.soccerSim.match.events.ScoringEvent;
import me.anthonybruno.soccerSim.team.Team;

/**
 * Created by anthony on 31/01/17.
 */
public class FriendlyScreen extends BorderPane {

    private final Team homeTeam;
    private final Team awayTeam;
    private final TextArea matchLog;

    private Thread matchThread;
    private Match match;

    private Label minuteLbl;
    private Label homeTeamScoreLbl;
    private Label awayTeamScoreLbl;
    private HBox scoringBar;

    public FriendlyScreen(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.setLeft(new TeamPanel(homeTeam));
        this.setRight(new TeamPanel(awayTeam));

        matchLog = new TextArea();
        matchLog.setEditable(false);
        this.setCenter(matchLog);
        this.setTop(getScoringBar());

        this.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            newScene.getWindow().setOnCloseRequest(event -> {
                matchThread.interrupt();
            });

        });
        setUpMatch();
    }

    private HBox getScoringBar() {
        if (scoringBar == null) {
            scoringBar = new HBox(getHomeTeamScoreLbl(), getExpandingHorizontalBox(), getMinuteLbl(), getExpandingHorizontalBox(), getAwayTeamScoreLbl());
        }
        return scoringBar;
    }

    private Label getMinuteLbl() {
        if (minuteLbl == null) {
            minuteLbl = new Label("0'");
            minuteLbl.getStyleClass().add("h1");
        }
        return minuteLbl;
    }

    private Label getHomeTeamScoreLbl() {
        if (homeTeamScoreLbl == null) {
            homeTeamScoreLbl = new Label("0");
            homeTeamScoreLbl.getStyleClass().add("h1");
        }
        return homeTeamScoreLbl;
    }

    private Label getAwayTeamScoreLbl() {
        if (awayTeamScoreLbl == null) {
            awayTeamScoreLbl = new Label("0");
            awayTeamScoreLbl.getStyleClass().add("h1");
        }
        return awayTeamScoreLbl;
    }


    private void setUpMatch() {
        MatchOptions matchOptions = new MatchOptions();
        matchOptions.setMatchDelay(1000);
        match = new Match(matchOptions, homeTeam, awayTeam);

        match.addMatchListener(getMatchListener());
        matchThread = new Thread(() -> {
            match.playMatch();
        });
        matchThread.start();
    }

    private HBox getExpandingHorizontalBox() {
        HBox expandingBox = new HBox();
        HBox.setHgrow(expandingBox, Priority.ALWAYS);
        return expandingBox;
    }

    private MatchListener getMatchListener() {
        return new MatchListener() {
            @Override
            public void handleScoringEvent(ScoringEvent event) {
                Platform.runLater(() -> {
                    homeTeamScoreLbl.setText(event.getHomeScore() + "");
                    awayTeamScoreLbl.setText(event.getAwayScore() + "");
                    matchLog.appendText(event.getMinute() + "' goal by " + event.getScoringPlayer().getName() + " for " + event.getScoringTeam().getName() + "\n");
                });
            }

            @Override
            public void handleMinuteEvent(MinuteEvent event) {
                Platform.runLater(() -> getMinuteLbl().setText(event.getMinute() + "'"));
            }

            @Override
            public void handleBreakEvent(BreakEvent event) {
                if (event.isHalfTime()) {
                    Platform.runLater(() -> matchLog.appendText("It's now half time \n"));
                } else {
                    Platform.runLater(() -> matchLog.appendText("There's the final whistle! \n"));
                }

            }
        };
    }

}
