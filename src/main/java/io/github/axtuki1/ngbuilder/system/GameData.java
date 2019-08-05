package io.github.axtuki1.ngbuilder.system;

import org.bukkit.ChatColor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameData {

    private static int cycle = 0, maxCycle = 0, round = 0;
    private static GameStyle style = GameStyle.SOLO;

    private static ThemeData beforeThemeData = null;
    private static NGData beforeNGData = null;
    private static HashMap<ChatColor, TeamData> teamDataList = null;
    private static List<String> playedThemeList = null;

    public static void init(){
        setBeforeThemeData(null);
        setBeforeNGData(null);
        setRound(0);
        setCycle(0);
        teamDataList = new HashMap<ChatColor, TeamData>();
        playedThemeList = new ArrayList<>();
    }

    public enum GameStyle {
        SOLO, TEAM
    }

    public static GameStyle getStyle() {
        return style;
    }

    public static void setStyle(GameStyle style) {
        GameData.style = style;
    }

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

//    public static HashMap<ChatColor, TeamData> getTeamDataList() {
//        return teamDataList;
//    }
//
//    public static void addTeamDataList(TeamData team){
//        teamDataList.put(team.getColor(),team);
//    }
//
//    public static void removeTeamDataList(TeamData team){
//        teamDataList.remove(team.getColor());
//    }
//
//    public static void removeTeamDataList(ChatColor color){
//        teamDataList.remove(color);
//    }

    public static List<String> getPlayedThemeList() {
        return playedThemeList;
    }

    public static void addPlayedTheme(ThemeData themeData){
        playedThemeList.add(themeData.getTheme());
    }

    public static void removePlayedTheme(ThemeData themeData){
        playedThemeList.remove(themeData.getTheme());
    }


}
