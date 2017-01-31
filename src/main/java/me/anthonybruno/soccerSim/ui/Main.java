package me.anthonybruno.soccerSim.ui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by anthony on 24/01/17.
 */
public class Main extends Application implements ContainerController {
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
            createFriendlyBtn.setOnAction(event -> setContainer(new SelectTeamScreen(this)));
            Button createLeagueBtn = new Button("Create League");
            Button loadLeagueBtn = new Button("Load League");
            parent = new VBox(createFriendlyBtn, createLeagueBtn, loadLeagueBtn);
        }
        return parent;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void setContainer(Node node) {
        parent.getChildren().clear();
        parent.getChildren().add(node);
    }
}
