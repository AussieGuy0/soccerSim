package me.anthonybruno.soccerSim.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import me.anthonybruno.soccerSim.models.Team;
import me.anthonybruno.soccerSim.reader.XmlParser;
import org.xml.sax.XMLReader;

import java.io.File;

/**
 * Created by anthony on 24/01/17.
 */
public class TeamPanel extends VBox {
    private Team team;
    private Button selectTeamBtn;

    private LabelledText teamNameBox;

    public TeamPanel() {
        getChildren().addAll(getSelectTeamBtn(), getTeamNameBox());
    }

    public void showTeam(Team team) {
        this.team = team;
        getTeamNameBox().setText(team.getName());

    }

    private LabelledText getTeamNameBox() {
        if (teamNameBox == null) {
             teamNameBox = new LabelledText("Team name: ");
        }
        return teamNameBox;
    }



    private Button getSelectTeamBtn() {
        if (selectTeamBtn == null) {
            selectTeamBtn = new Button("Select Team");
            selectTeamBtn.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("src/main/resources/teams"));
                fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("xml files", "xml"));
                File result = fileChooser.showOpenDialog(this.getScene().getWindow());
                if (result != null) {
                   showTeam(XmlParser.parseXmlIntoTeam(result));
                }
            });
        }
        return selectTeamBtn;
    }
}
