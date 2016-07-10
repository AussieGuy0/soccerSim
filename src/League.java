import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 10/07/16.
 */
public class League {


    private List<Team> teams;
    private List<List<Match>> schedule;

    public League(List<Team> teams) {
        this.teams = teams;
        schedule = new ArrayList<>();
        scheduleRounds();
    }

    public void scheduleRounds() {
        //todo: not hardcode number of rounds
        List<Match> firstRound = new ArrayList<>();
        List<Match> secondRound = new ArrayList<>();
        List<Match> thirdRound = new ArrayList<>();
        /*
        for (int i = 0; i < teams.size(); i++) {
            Team firstTeam = teams.get(i);
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
               }
                count++;
            }
            System.out.println(i+1 + " | " + largestTeam.getName() + " | - | - | " + largestTeam.getStats().getPoints());
            tableTeams.remove(found);

        }
    }
}
