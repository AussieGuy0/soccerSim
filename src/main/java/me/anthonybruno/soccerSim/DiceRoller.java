package me.anthonybruno.soccerSim;

import java.util.Random;

/**
 * Created by anthony on 21/12/16.
 */
public class DiceRoller {
    private static final Random random = new Random();

    public static int rollD10() {
        return random.nextInt(10) + 1;
    }

    public static int rollD100() {
        return random.nextInt(100) + 1;
    }
}
