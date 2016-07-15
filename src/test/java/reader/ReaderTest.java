package test.java.reader;

import main.java.reader.TeamReader;
import org.junit.Test;

/**
 * Created by anthony on 13/07/16.
 */
public class ReaderTest {

    @Test
    public void testTeamReader() {
        TeamReader teamReader = new TeamReader("src/main/resources/spain.xml");
        teamReader.read();

        teamReader = new TeamReader("src/main/resources/test.xml");
        teamReader.read();

    }

}
