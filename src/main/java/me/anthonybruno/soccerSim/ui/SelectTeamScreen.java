package me.anthonybruno.soccerSim.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import me.anthonybruno.soccerSim.reader.XmlParser;

import java.io.File;

/**
 * Created by anthony on 24/01/17.
 */
public class SelectTeamScreen extends VBox {

    private ContainerController containerController;
    private Button startMatchBtn;
    private TeamPanel homeTeamPanel;
    private TeamPanel awayTeamPanel;

    public SelectTeamScreen(ContainerController containerController) {
        this.containerController = containerController;
        homeTeamPanel = new TeamPanel();
        VBox homeTeamBox = new VBox(getSelectTeamBtn(homeTeamPanel), homeTeamPanel);

        awayTeamPanel = new TeamPanel();
        VBox awayTeamBox = new VBox(getSelectTeamBtn(awayTeamPanel), awayTeamPanel);

        HBox.setHgrow(homeTeamBox, Priority.ALWAYS);
        HBox.setHgrow(awayTeamBox, Priority.ALWAYS);

        HBox hBox = new HBox(homeTeamBox, awayTeamBox);
        getChildren().addAll(hBox, getStartMatchBtn());
    }

    private Button getSelectTeamBtn(TeamPanel teamPanel) {
        Button selectTeamBtn = new Button("Select Team");
        selectTeamBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("src/main/resources/teams"));
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("xml files", "xml"));
            File result = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (result != null) {
                teamPanel.showTeam(XmlParser.parseXmlIntoTeam(result));
            }
        });
        return selectTeamBtn;
    }

    private Button getStartMatchBtn() {
        if (startMatchBtn == null) {
            startMatchBtn = new Button("Start Match");
            startMatchBtn.setOnAction(event -> containerController.setContainer(new FriendlyScreen(homeTeamPanel.getTeam(), awayTeamPanel.getTeam())));
        }
        return startMatchBtn;
    }
}
