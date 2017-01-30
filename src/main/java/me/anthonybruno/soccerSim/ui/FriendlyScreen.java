package me.anthonybruno.soccerSim.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by anthony on 24/01/17.
 */
public class FriendlyScreen extends VBox {


    public FriendlyScreen() {
        TeamPanel homeTeamPanel = new TeamPanel();
        TeamPanel awayTeamPanel = new TeamPanel();

        HBox.setHgrow(homeTeamPanel, Priority.ALWAYS);
        HBox.setHgrow(awayTeamPanel, Priority.ALWAYS);

        HBox hBox = new HBox(homeTeamPanel, awayTeamPanel);
        getChildren().addAll(hBox);
    }
}
