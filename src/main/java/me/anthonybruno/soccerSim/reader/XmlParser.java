package me.anthonybruno.soccerSim.reader;

import me.anthonybruno.soccerSim.models.HalfAttributes;
import me.anthonybruno.soccerSim.models.Team;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by anthony on 15/12/16.
 */
public class XmlParser {

    private final static String TEAM_NAME = "name";
    private final static String GOAL_RATING = "goalRating";
    private final static String FORMATION = "formation";
    private final static String STRATEGY = "strategy";
    private final static String PLAYERS = "players";

    private final static String HALF_ATTRIBUTES = "halfStats";
    private final static String ATTEMPTS = "attempts";
    private final static String DEFENSIVE_ATTEMPTS = "defensiveAttempts";
    private final static String DEFENSIVE_SHOTS_ON_GOAL= "defensiveShotsOnGoal";

    private File file;

    public XmlParser(File file) {
        this.file = file;
    }

    public Team parseXmlIntoTeam() {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        Team.Builder teamBuilder = new Team.Builder();
        int halfStatsNum = 1;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node current = childNodes.item(i);
            if (!(current instanceof Text)) {
                String tagName = current.getNodeName();
                String tagValue = current.getTextContent();

                switch (tagName) {
                    case TEAM_NAME:
                        teamBuilder.name(tagValue);
                        break;
                    case GOAL_RATING:
                        teamBuilder.shotsGoal(Integer.parseInt(tagValue));
                        break;
                    case FORMATION:
                        teamBuilder.formation(tagValue);
                        break;
                    case STRATEGY:
                        teamBuilder.strategy(tagValue);
                        break;
                    case HALF_ATTRIBUTES:
                        handleHalfStats(teamBuilder, current, halfStatsNum++);
                        break;
                    case PLAYERS:
                        handlePlayers(teamBuilder, current);
                        break;
                }
            }
        }
        return teamBuilder.build();
    }

    private void handlePlayers(Team.Builder teamBuilder, Node playersNode) {
        //TODO: THIS
    }

    private void handleHalfStats(Team.Builder teamBuilder, Node halfNode, int halfNum) {
        int attempts = 0;
        int defensiveAttempts = 0;
        int defensiveShotsOnGoal = 0;
        for (int i = 0; i < halfNode.getChildNodes().getLength(); i++) {
            Node current = halfNode.getChildNodes().item(i);
            if (!(halfNode instanceof Text)) {
                String tagName = current.getNodeName();
                String tagValue = current.getTextContent();

                switch (tagName) {
                    case ATTEMPTS:
                        attempts = Integer.parseInt(tagValue);
                        break;
                    case DEFENSIVE_ATTEMPTS:
                        defensiveAttempts = Integer.parseInt(tagValue);
                        break;
                    case DEFENSIVE_SHOTS_ON_GOAL:
                        defensiveShotsOnGoal = Integer.parseInt(tagValue);
                        break;
                }

            }
        }

        HalfAttributes halfAttributes = new HalfAttributes(attempts, defensiveAttempts, defensiveShotsOnGoal);
        if (halfNum == 1) {
            teamBuilder.firstHalfAttributes(halfAttributes);
        } else {
            teamBuilder.secondHalfAttributes(halfAttributes);
        }
    }

    public static void main(String[] args) {
        XmlParser xmlParser = new XmlParser(new File("/home/anthony/Documents/Projects/soccerSim/src/main/resources/teams/Argentina.xml"));
        xmlParser.parseXmlIntoTeam();
    }
}
