package me.anthonybruno.soccerSim;

import me.anthonybruno.soccerSim.models.Team;
import me.anthonybruno.soccerSim.reader.XmlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Simple {

    public static void main(String[] args) {
        ArrayList<Team> teams = new ArrayList<>();
        XmlParser parser = new XmlParser();
        File teamDir = new File("src/main/resources/teams");
        for (File file : teamDir.listFiles()) {
            if (file.getName().endsWith("xml")) {
                teams.add(parser.parseXmlIntoTeam(file));
            }
        }
        League league = new League(teams);
        league.printTable();
        league.playRound();
        league.printTable();

    }
}
