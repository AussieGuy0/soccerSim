package me.anthonybruno.soccerSim.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 31/01/17.
 */
public class FriendlyScreen extends BorderPane {

    private Team homeTeam;
    private Team awayTeam;

    public FriendlyScreen(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.setLeft(new TeamPanel(homeTeam));
        this.setRight(new TeamPanel(homeTeam));
        this.setCenter(new TextArea());

    }
}
