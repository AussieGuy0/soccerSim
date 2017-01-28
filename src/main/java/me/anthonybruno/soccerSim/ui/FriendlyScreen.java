package me.anthonybruno.soccerSim.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by anthony on 24/01/17.
 */
public class FriendlyScreen extends VBox {


    public FriendlyScreen() {
       getChildren().addAll(new HBox(new TeamPanel(), new TeamPanel()));
    }
}
