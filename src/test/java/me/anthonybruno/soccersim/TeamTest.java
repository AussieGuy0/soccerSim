package me.anthonybruno.soccersim;

import me.anthonybruno.soccerSim.AttemptsController;
import me.anthonybruno.soccerSim.match.Match;
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
        Team france = XmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/France.xml"));
        assertEquals(11, france.getFirstHalfAttempts());
        assertEquals(-4, france.getFirstHalfDefenseAttempts());

        Team turkey = XmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Turkey.xml"));
        assertEquals(14, turkey.getFirstHalfAttempts());
        assertEquals(-5, turkey.getFirstHalfDefenseAttempts());

        AttemptsController attemptsController = new AttemptsController(france, turkey);
        assertEquals(6, attemptsController.getHomeTeamAttempts().getFirstHalfAttempts());
        assertEquals(10, attemptsController.getAwayTeamAttempts().getFirstHalfAttempts());
    }

    @Test
    public void loadCubaAndAustria() {
        Team cuba = XmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Cuba.xml"));

        Team austria = XmlParser.parseXmlIntoTeam(new File("src/main/resources/teams/Austria.xml"));

        AttemptsController attemptsController = new AttemptsController(cuba, austria);
        assertEquals(12, attemptsController.getHomeTeamAttempts().getFirstHalfAttempts());
        assertEquals(25, attemptsController.getAwayTeamAttempts().getFirstHalfAttempts());

        assertEquals(19, attemptsController.getHomeTeamAttempts().getFirstHalfSOG());
        assertEquals(30, attemptsController.getAwayTeamAttempts().getFirstHalfSOG());
    }
}
