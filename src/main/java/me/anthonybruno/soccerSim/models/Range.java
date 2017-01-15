package me.anthonybruno.soccerSim.models;

/**
 * Created by anthony on 15/01/17.
 */
public class Range {

    private final int min;
    private final int max;

    public Range(String range) {
        int hyphenIndex = range.indexOf("-");
        if (hyphenIndex > -1) {
            min = Integer.parseInt(range.substring(0, hyphenIndex));
            max = Integer.parseInt(range.substring(hyphenIndex + 1, range.length()));
        } else {
            min = Integer.parseInt(range);
            max = min;
        }
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
