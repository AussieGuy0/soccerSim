package main.java;

import java.util.ArrayList;
import java.util.List;

public class Simple {

    public static void main(String[] args) {
        Team spain = new Team("Spain", 9, 11, 14, 6, 3, 5, 2, "433", "normal");
        spain.loadPlayers("src/main/resources/spain.txt");
        Team italy = new Team("Italy", 11, 14, 17, 4, 5, 4, 5, "451", "normal");
        italy.loadPlayers("src/main/resources/italy.txt");

        //new main.java.Match(spain, italy).playMatch();

        Team spainCopy = new Team("SpainB", 9, 11, 14, 6, 3, 5, 2, "433", "normal");
        spainCopy.loadPlayers("src/main/resources/spain.txt");
        Team italyCopy = new Team("ItalyB", 11, 14, 17, 4, 5, 4, 5, "451", "normal");
        italyCopy.loadPlayers("src/main/resources/italy.txt");

        List<Team> teams = new ArrayList<>();
        teams.add(spain);
        teams.add(italy);
        teams.add(spainCopy);
        teams.add(italyCopy);
        League league = new League(teams);
        league.printTable();

        league.playRound();
        league.playRound();
        league.playRound();
    }
}
