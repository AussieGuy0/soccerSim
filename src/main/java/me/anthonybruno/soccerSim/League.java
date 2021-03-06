package me.anthonybruno.soccerSim;

import me.anthonybruno.soccerSim.match.Match;
import me.anthonybruno.soccerSim.match.MatchOptions;
import me.anthonybruno.soccerSim.team.Stats;
import me.anthonybruno.soccerSim.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by anthony on 10/07/16.
 */
public class League {


    private final List<Team> teams;
    private final List<List<Match>> schedule = new ArrayList<>();
    private int roundNumber = 0;

    private final MatchOptions matchOptions;

    public League(List<Team> teams) {
        this.teams = teams;
        matchOptions = new MatchOptions();
        matchOptions.setSeasonMatch(true);
        scheduleRounds();

    }

    //TODO: Fix this disgusting mess of a method
    private void scheduleRounds() {
        int numOfRounds = teams.size() - 1;
        int count = 1;
        for (int i = 0; i < numOfRounds; i++) {
            List<Match> round = new ArrayList<>();
            for (int j = 0; j < teams.size(); j++) {
                if (j + count >= teams.size()) {
                    break;
                }
                round.add(new Match(matchOptions, teams.get(j), teams.get(j + count)));
                if (count == 1) {
                    j++;
                } else if (count == 3) {
                    count = 1;
                }
            }
            schedule.add(round);
            count++;
        }
    }

    public void printTable() {
        System.out.println();
        System.out.println("Pos | Name | GF | GA | Points | Played");
        List<Team> tableTeams = new ArrayList<>(teams);
        int size = tableTeams.size();
        for (int i = 0; i < size; i++) {
            int max = 0;
            int count = 0;
            int found = 0;
            Team largestTeam = null;
            for (Team team : tableTeams) {
                if (team.getStats().getPoints() >= max) {
                    largestTeam = team;
                    found = count;
                    max = team.getStats().getPoints();
                }
                count++;
            }
            Stats teamStats = largestTeam.getStats();
            System.out.println(i + 1 + "   | " + largestTeam.getName() + " | " + teamStats.getGoalsScored() + " " +
                    "| " + teamStats.getGoalsAgainst() + " | " + teamStats.getPoints() + " | " + teamStats.getPlayed());
            tableTeams.remove(found);

        }
    }

    public void playRound() {
        CountDownLatch latch = new CountDownLatch(schedule.get(roundNumber).size());
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < schedule.get(roundNumber).size(); i++) {
            pool.execute(new MatchThread(schedule.get(roundNumber).get(i), latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printTable();
        roundNumber++;
        pool.shutdown();
    }

    private class MatchThread extends Thread {
        private final Match match;
        private final CountDownLatch latch;

        public MatchThread(Match match, CountDownLatch latch) {
            this.match = match;
            this.latch = latch;
        }

        @Override
        public void run() {
            match.playMatch();
            latch.countDown();
        }
    }

}
