package me.anthonybruno.soccerSim.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import me.anthonybruno.soccerSim.models.Team;

/**
 * Created by anthony on 24/01/17.
 */
public class TeamPanel extends VBox {
    private Team team;
    private Label teamNameLbl;

    public TeamPanel() {
        getChildren().add(getTeamNameBox());

    }

    public void showTeam(Team team) {
        getTeamNameLbl().setText(team.getName());

    }

    private HBox getTeamNameBox() {
        return new HBox(new Label("Team name: "), getTeamNameLbl());
    }


    private Label getTeamNameLbl() {
        if (teamNameLbl == null) {
            teamNameLbl = new Label();
        }
        return teamNameLbl;
    }
}
