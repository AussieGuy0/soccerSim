package me.anthonybruno.soccerSim.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import me.anthonybruno.soccerSim.models.Goalie;
import me.anthonybruno.soccerSim.models.Player;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 24/01/17.
 */
public class TeamPanel extends VBox {
    private Team team;

    private TableView<Player> playerTable;
    private TableView<Goalie> goalieTable;
    private LabelledText teamNameBox;
    private LabelledText goalRatingBox;
    private LabelledText formationBox;
    private LabelledText strategyBox;
    private LabelledText attemptsBox;
    private LabelledText defensiveAttemptsBox;
    private LabelledText defensiveShotsBox;
    private VBox halfAttributesBox;

    public TeamPanel() {
        Label playersTitle = new Label("Players");
        playersTitle.getStyleClass().add("strong");

        Label goaliesTitle = new Label("Goalies");
        goaliesTitle.getStyleClass().add("strong");

        getChildren().addAll(getTeamNameBox(), getGoalRatingBox(), getFormationBox(), getStrategyBox(),
                getHalfAttributesBox(), playersTitle, getPlayerTable(), goaliesTitle, getGoalieTable());

    }

    public TeamPanel(Team team) {
        this();
        showTeam(team);
    }

    public Team getTeam() {
        return team;
    }

    public void showTeam(Team team) {
        this.team = team;
        getTeamNameBox().setText(team.getName());
        getGoalRatingBox().setText(team.getShotsGoal() + "");
        getFormationBox().setText(team.getFormation());
        getStrategyBox().setText(team.getStrategy());
        getAttemptsBox().setText(team.getFirstHalfAttempts() + " | " + team.getSecondHalfAttempts());
        getDefensiveAttemptsBox().setText(team.getFirstHalfDefenseAttempts() + " | " + team.getSecondHalfDefenseAttempts());
        getDefensiveShotsBox().setText(team.getFirstHalfDefensiveShotOnGoal() + " | " + team.getSecondHalfDefensiveShotOnGoal());

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
            ratingColumn.setCellValueFactory(cellDataFeature -> new SimpleObjectProperty<>(cellDataFeature.getValue().getRating()));
            goalieTable.getColumns().addAll(nameColumn, ratingColumn);
        }
        return goalieTable;
    }



    //TODO: Probably change next 3 components to something that looks nicer
    private LabelledText getAttemptsBox() {
        if (attemptsBox == null) {
            attemptsBox = new LabelledText("Attempts: ");
        }
        return attemptsBox;
    }

    private LabelledText getDefensiveAttemptsBox() {
        if (defensiveAttemptsBox == null) {
            defensiveAttemptsBox = new LabelledText("Defensive Attempts: ");
        }
        return defensiveAttemptsBox;
    }

    private LabelledText getDefensiveShotsBox() {
        if (defensiveShotsBox == null) {
            defensiveShotsBox = new LabelledText("Defensive Shots: ");
        }
        return defensiveShotsBox;
    }


    private VBox getHalfAttributesBox() {
        if (halfAttributesBox == null) {
            Label heading = new Label("First Half | Second Half");
            heading.getStyleClass().addAll("strong");
            heading.setPadding(new Insets(0,0,0,80));
            halfAttributesBox = new VBox(heading,  getAttemptsBox(),
                    getDefensiveAttemptsBox(), getDefensiveShotsBox());
        }
        return halfAttributesBox;
    }
}
