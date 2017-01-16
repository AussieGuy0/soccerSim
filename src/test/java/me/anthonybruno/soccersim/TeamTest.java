package me.anthonybruno.soccersim;

import me.anthonybruno.soccerSim.AttemptsController;
import me.anthonybruno.soccerSim.Match;
import me.anthonybruno.soccerSim.models.Team;
import me.anthonybruno.soccerSim.reader.XmlParser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by anthony on 16/01/17.
 */
public class TeamTest {

    @Test
    public void loadFranceAndTurkey() {
        XmlParser xmlParser = new XmlParser();

        Team france = xmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/France.xml"));
        assertEquals(11, france.getFirstHalfAttempts());
        assertEquals(-4, france.getFirstHalfDefenseAttempts());

        Team turkey = xmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Turkey.xml"));
        assertEquals(14, turkey.getFirstHalfAttempts());
        assertEquals(-5, turkey.getFirstHalfDefenseAttempts());

        AttemptsController attemptsController = new AttemptsController(france, turkey);
        assertEquals(6, attemptsController.getHomeTeamAttempts().getFirstHalfAttempts());
        assertEquals(10, attemptsController.getAwayTeamAttempts().getFirstHalfAttempts());
    }

    @Test
    public void loadCubaAndAustria() {
        XmlParser xmlParser = new XmlParser();

        Team cuba = xmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Cuba.xml"));

        Team austria = xmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Austria.xml"));

        AttemptsController attemptsController = new AttemptsController(cuba, austria);
        assertEquals(12, attemptsController.getHomeTeamAttempts().getFirstHalfAttempts());
        assertEquals(25, attemptsController.getAwayTeamAttempts().getFirstHalfAttempts());

        assertEquals(19, attemptsController.getHomeTeamAttempts().getFirstHalfSOG());
        assertEquals(30, attemptsController.getAwayTeamAttempts().getFirstHalfSOG());
        new Match(cuba, austria).playMatch();
    }
}
