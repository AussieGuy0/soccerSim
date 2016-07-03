/**
 * Created by anthony on 1/07/16.
 */
public class Player {

    private String name;
    private int shotRange;
    private int goal;

    public Player(String name, int shotRange, int goal) {
        this.name = name;
        this.shotRange = shotRange;
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public int getShotRange() {
        return shotRange;
    }

    public int getGoal() {
        return goal;
    }


}
