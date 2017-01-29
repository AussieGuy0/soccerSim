package me.anthonybruno.soccerSim.ui;

import com.itextpdf.kernel.color.Lab;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import me.anthonybruno.soccerSim.models.Goalie;
import me.anthonybruno.soccerSim.models.Player;
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
    private VBox playerBox;
    private VBox goalieBox;

    private LabelledText teamNameBox;
    private LabelledText goalRatingBox;
    private LabelledText formationBox;
    private LabelledText strategyBox;

    public TeamPanel() {
        Label playersTitle = new Label("Players");
        playersTitle.getStyleClass().add("strong");

        Label goaliesTitle = new Label("Goalies");
        goaliesTitle.getStyleClass().add("strong");

        getChildren().addAll(getSelectTeamBtn(), getTeamNameBox(), getGoalRatingBox(), getFormationBox(), getStrategyBox(),
                playersTitle, getPlayerBox(), goaliesTitle, getGoalieBox());
    }

    public void showTeam(Team team) {
        this.team = team;
        getTeamNameBox().setText(team.getName());
        getGoalRatingBox().setText(team.getShotsGoal() + "");
        getFormationBox().setText(team.getFormation());
        getStrategyBox().setText(team.getStrategy());

        getPlayerBox().getChildren().clear();
        for (Player player : team.getPlayers()) {
            getPlayerBox().getChildren().add(new Label(player.getName() + " " + player.getShotRange() + " " + player.getGoal()));
        }

        getGoalRatingBox().getChildren().clear();
        for (Goalie goalie : team.getGoalies()) {
           getGoalieBox().getChildren().add(new Label(goalie.getName() + " " + goalie.getRating()));
        }
    }

    private LabelledText getTeamNameBox() {
        if (teamNameBox == null) {
             teamNameBox = new LabelledText("Team name: ");
        }
        return teamNameBox;
    }

    private LabelledText getGoalRatingBox() {
        if (goalRatingBox == null) {
           goalRatingBox = new LabelledText("Goal Rating: ");
        }
        return goalRatingBox;
    }

    private LabelledText getFormationBox() {
        if (formationBox == null) {
           formationBox = new LabelledText("Formation: ");
        }
        return formationBox;
    }

    private LabelledText getStrategyBox() {
        if (strategyBox == null) {
            strategyBox = new LabelledText("Strategy: ");
        }
        return strategyBox;
    }

    private VBox getPlayerBox() {
        if (playerBox == null) {
           playerBox = new VBox();
        }
        return playerBox;
    }

    private VBox getGoalieBox() {
        if (goalieBox == null) {
           goalieBox = new VBox();
        }
        return goalieBox;
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
