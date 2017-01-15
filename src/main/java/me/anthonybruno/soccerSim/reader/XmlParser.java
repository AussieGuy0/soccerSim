package me.anthonybruno.soccerSim.reader;

import me.anthonybruno.soccerSim.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

    private final static String NAME = "name";
    private final static String GOAL_RATING = "goalRating";
    private final static String FORMATION = "formation";
    private final static String STRATEGY = "strategy";
    private final static String PLAYERS = "players";

    private final static String PLAYER = "player";
    private final static String GOALIE = "goalie";
    private final static String MULTIPLIER = "multiplier";
    private final static String RATING = "rating";
    private final static String SHOT_RANGE = "shotRange";
    private final static String PLAYER_GOAL_RATING = "goal";

    private final static String HALF_ATTRIBUTES = "halfStats";
    private final static String ATTEMPTS = "attempts";
    private final static String DEFENSIVE_ATTEMPTS = "defensiveAttempts";
    private final static String DEFENSIVE_SHOTS_ON_GOAL = "defensiveShotsOnGoal";

    public Team parseXmlIntoTeam(File file) {
        Element root = getDocumentRoot(file);
        Team.Builder teamBuilder = new Team.Builder();
        new NodeTraversor(root) {
            int halfStatsNum = 1;

            @Override
            public void nodeVisited(Node current, String tagName, String tagValue) {
                switch (tagName) {
                    case NAME:
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
                        NodeList playersNodeChildren = current.getChildNodes();
                        for (int i = 0; i < playersNodeChildren.getLength(); i++) {
                            Node child = playersNodeChildren.item(i);
                            if (child.getNodeName().equals(PLAYER)) {
                                teamBuilder.addPlayer(parsePlayer(child));
                            } else if (child.getNodeName().equals(GOALIE)) {
                                teamBuilder.addGoalie(parseGoalie(child));
                            }
                        }
                        break;
                }
            }
        }.traverseNodes();
        return teamBuilder.build();
    }

    private Element getDocumentRoot(File file) {
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
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return document.getDocumentElement();
    }

    private TeamMember parseTeamMember(Node current, boolean goalie) {
        String name = "";
        int rating = 0;
        int multiplier = 0;
        Range shotRange = null;
        int goalRating = 0;
        for (int i = 0; i < current.getChildNodes().getLength(); i++) {
            Node node = current.getChildNodes().item(i);
            String nodeName = node.getNodeName();
            if (nodeName.equals(NAME)) {
                name = node.getTextContent();
            } else if (nodeName.equals(RATING)) {
                rating = Integer.parseInt(node.getTextContent());
            } else if (nodeName.equals(MULTIPLIER)) {
                multiplier = Integer.parseInt(node.getTextContent());
            } else if (nodeName.equals(SHOT_RANGE)) {
                shotRange = new Range(node.getTextContent());
            } else if (nodeName.equals(PLAYER_GOAL_RATING)) {
                goalRating = new Range(node.getTextContent()).getMin();
            }
        }
        if (goalie) {
           return new Goalie(name, rating, multiplier);
        } else {
           return new Player(name, shotRange, goalRating, multiplier);
        }

    }

    private Goalie parseGoalie(Node current) {
        return (Goalie) parseTeamMember(current, true);
    }

    private Player parsePlayer(Node current) {
        return (Player) parseTeamMember(current, false);
    }

    private void handleHalfStats(Team.Builder teamBuilder, Node halfNode, int halfNum) {
        final int[] attempts = {0};
        final int[] defensiveAttempts = {0};
        final int[] defensiveShotsOnGoal = {0};
        new NodeTraversor(halfNode) {
            @Override
            public void nodeVisited(Node current, String tagName, String tagValue) {
                switch (tagName) {
                    case ATTEMPTS:
                        attempts[0] = Integer.parseInt(tagValue);
                        break;
                    case DEFENSIVE_ATTEMPTS:
                        defensiveAttempts[0] = Integer.parseInt(tagValue);
                        break;
                    case DEFENSIVE_SHOTS_ON_GOAL:
                        defensiveShotsOnGoal[0] = Integer.parseInt(tagValue);
                        break;
                }
            }
        }.traverseNodes();

        HalfAttributes halfAttributes = new HalfAttributes(attempts[0], defensiveAttempts[0], defensiveShotsOnGoal[0]);
        if (halfNum == 1) {
            teamBuilder.firstHalfAttributes(halfAttributes);
        } else {
            teamBuilder.secondHalfAttributes(halfAttributes);
        }
    }
}
