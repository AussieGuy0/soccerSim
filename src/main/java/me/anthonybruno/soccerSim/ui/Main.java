package me.anthonybruno.soccerSim.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by anthony on 24/01/17.
 */
public class Main extends Application {
    private VBox parent;
    private Scene main;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Soccer Sim");
        stage.setScene(getMain());
        stage.show();
    }

    private Scene getMain() {
        if (main == null) {
            main = new Scene(getParent());
            main.getStylesheets().add("style.css");
        }
        return main;
    }

    private VBox getParent() {
        if (parent == null) {
            Button createFriendlyBtn = new Button("Create Friendly");
            Button createLeagueBtn = new Button("Create League");
            Button loadLeagueBtn = new Button("Load League");
            parent = new VBox(createFriendlyBtn, createLeagueBtn, loadLeagueBtn);
        }
        return parent;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
