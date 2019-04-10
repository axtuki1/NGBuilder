package io.github.axtuki1.ngbuilder.system;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class GameData {

    private static int cycle = 0, maxCycle = 0, round = 0;

    private static ThemeData beforeThemeData = null;
    private static NGData beforeNGData = null;

    public static int getCycle() {
        return cycle;
    }

    public static void setCycle(int cycle) {
        GameData.cycle = cycle;
        maxCycle = cycle;
    }

    public static void decrementCycle() {
        cycle = cycle - 1;
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int round) {
        GameData.round = round;
    }

    public static ThemeData getBeforeThemeData() {
        return beforeThemeData;
    }

    public static void setBeforeThemeData(ThemeData beforeThemeDataa) {
        beforeThemeData = beforeThemeDataa;
    }

    public static NGData getBeforeNGData() {
        return beforeNGData;
    }

    public static void setBeforeNGData(NGData beforeNGData) {
        GameData.beforeNGData = beforeNGData;
    }

    public static int getMaxCycle() {
        return maxCycle;
    }
}
