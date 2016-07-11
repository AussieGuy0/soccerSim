package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by anthony on 10/07/16.
 */
public class League {


    private List<Team> teams;
    private List<List<Match>> schedule;
    private int roundNumber;

    public League(List<Team> teams) {
        this.teams = teams;
        schedule = new ArrayList<>();
        scheduleRounds();
        roundNumber = 0;
    }

    public void scheduleRounds() {
        //todo: not hardcode number of rounds
        List<Match> firstRound = new ArrayList<>();
        List<Match> secondRound = new ArrayList<>();
        List<Match> thirdRound = new ArrayList<>();
        /*
        for (int i = 0; i < teams.size(); i++) {
            main.java.Team firstTeam = teams.get(i);
            int count = 0;
            for (int j = i+1; j < teams.size(); j++) {
                if (count =)
            }
        }
        */
        firstRound.add(new Match(teams.get(0),teams.get(1)));
        firstRound.add(new Match(teams.get(2),teams.get(3)));
        secondRound.add(new Match(teams.get(0),teams.get(2)));
        secondRound.add(new Match(teams.get(1),teams.get(3)));
        thirdRound.add(new Match(teams.get(0),teams.get(3)));
        thirdRound.add(new Match(teams.get(2),teams.get(1)));
        schedule.add(firstRound);
        schedule.add(secondRound);
        schedule.add(thirdRound);
    }

    public void printTable() {
        System.out.println();
        System.out.println("Pos. | Name | GF | GA | Points");
        List<Team> tableTeams = new ArrayList<>(teams);
        int size = tableTeams.size();
        for (int i = 0; i < size; i++) {
            int max = 0;
            int count  = 0;
            int found = 0;
            Team largestTeam = null;
            for (Team team: tableTeams) {
               if (team.getStats().getPoints() >= max)  {
                   largestTeam = team;
                   found = count;
                   max = team.getStats().getPoints();
               }
                count++;
            }
            System.out.println(i+1 + " | " + largestTeam.getName() + " | " + largestTeam.getStats().getGoalsScored() + " | "+ largestTeam.getStats().getGoalsAgainst() + " | " + largestTeam.getStats().getPoints());
            tableTeams.remove(found);

        }
    }

    public void playRound() {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < schedule.get(roundNumber).size(); i++) {
            new MatchThread(schedule.get(roundNumber).get(i)).run();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printTable();
        roundNumber++;
    }

    private class MatchThread extends  Thread {
        private Match match;

        public MatchThread(Match match) {
           this.match = match;
        }
        @Override
        public void run() {
            match.playMatch();
        }
    }

}
