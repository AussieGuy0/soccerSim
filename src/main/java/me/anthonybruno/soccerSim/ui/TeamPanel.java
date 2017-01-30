package me.anthonybruno.soccerSim.ui;

import com.itextpdf.kernel.color.Lab;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
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

    private TableView<Player> playerTable;
    private TableView<Goalie> goalieTable;
    private LabelledText teamNameBox;
    private LabelledText goalRatingBox;
    private LabelledText formationBox;
    private LabelledText strategyBox;
    private VBox halfAttributesBox;

    public TeamPanel() {
        Label playersTitle = new Label("Players");
        playersTitle.getStyleClass().add("strong");

        Label goaliesTitle = new Label("Goalies");
        goaliesTitle.getStyleClass().add("strong");

        getChildren().addAll(getSelectTeamBtn(), getTeamNameBox(), getGoalRatingBox(), getFormationBox(), getStrategyBox(),
                getHalfAttributesBox(), playersTitle, getPlayerTable(), goaliesTitle, getGoalieTable());
    }

    public void showTeam(Team team) {
        this.team = team;
        getTeamNameBox().setText(team.getName());
        getGoalRatingBox().setText(team.getShotsGoal() + "");
        getFormationBox().setText(team.getFormation());
        getStrategyBox().setText(team.getStrategy());

        getPlayerTable().setItems(FXCollections.observableArrayList(team.getPlayers()));

        getGoalieTable().setItems(FXCollections.observableArrayList(team.getGoalies()));

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

    private TableView<Player> getPlayerTable() {
        if (playerTable == null) {
            playerTable = new TableView<>();
            playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cellDataFeature -> new SimpleStringProperty(cellDataFeature.getValue().getName()));
            TableColumn<Player, String> shotRangeColumn = new TableColumn<>("Shot Range");
            shotRangeColumn.setCellValueFactory(cellDataFeature -> new SimpleStringProperty(cellDataFeature.getValue().getShotRange().toString()));
            TableColumn<Player, Integer> goalRatingColumn = new TableColumn<>("Goal Rating");
            goalRatingColumn.setCellValueFactory(cellDataFeature -> new SimpleObjectProperty<>(cellDataFeature.getValue().getGoal()));
            playerTable.getColumns().addAll(nameColumn, shotRangeColumn, goalRatingColumn);
        }
        return playerTable;
    }

    private TableView<Goalie> getGoalieTable() {
        if (goalieTable == null) {
            goalieTable = new TableView<>();
            goalieTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn<Goalie, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cellDataFeature -> new SimpleStringProperty(cellDataFeature.getValue().getName()));
            TableColumn<Goalie, Integer> ratingColumn = new TableColumn<>("Rating");
            ratingColumn.setCellValueFactory(cellDataFeature -> new SimpleObjectProperty<Integer>(cellDataFeature.getValue().getRating()));
            goalieTable.getColumns().addAll(nameColumn, ratingColumn);
        }
        return goalieTable;
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

    public VBox getHalfAttributesBox() { //TODO: This
        if (halfAttributesBox == null) {
            halfAttributesBox = new VBox(new Label("First Half | Second Half"), new Label("Attempts"),
                    new Label("Defensive Attempts"), new Label("Defensive Shots On Goal"));
        }
        return halfAttributesBox;
    }
}
