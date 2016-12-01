package me.anthonybruno.soccerSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 15/07/16.
 */
public class Commentator {

    private static HashMap<String, List<String>> sayings = new HashMap<>();

    public static String announceGoal(Player scorer) {
        String chosenSaying = readAndGetSaying("goalAnnouncements");
        return chosenSaying.replace("[Player]",scorer.getName());
    }

    public static String announceSave(Player shooter, Goalie goalie) {
        String chosenSaying = readAndGetSaying("saveAnnouncements");
        chosenSaying = chosenSaying.replace("[Player]", shooter.getName());
        return chosenSaying.replace("[Goalie]",goalie.getName());
    }

    public static String announceMiss(Player shooter) {
        String chosenSaying = readAndGetSaying("missAnnouncements");
        return chosenSaying.replace("[Player]", shooter.getName());
    }

    public static String announceEndOMatch(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        return "";
    }

    public static String announceYellowCard(Team affectedTeam, Player yellowCardedPlayer) {
        return "";
    }

    public static String announceRedCard(Team affectedTeam, Player redCardedPlayer) {
        return "";
    }

    public static String announceInjury(Team affectedTeam, Player injuredPlayer) {
        return "";
    }

    private static String readAndGetSaying(String sayingType) {
        sayings.putIfAbsent(sayingType, readFile("src/main/resources/commentatorSayings/"+ sayingType+ ".txt"));
        Random random = new Random();
        List<String> sayingsList = sayings.get(sayingType);
        int randomNumber = random.nextInt(sayingsList.size());
        return sayingsList.get(randomNumber);
    }

    private static List<String> readFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String line = reader.readLine();
                list.add(line);
            }
        } catch (IOException e) {
           throw new IllegalStateException("error reading file ",e);
        }
        return list;
    }
}
