package io.github.axtuki1.ngbuilder.player;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.system.NGData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GamePlayers {

    static HashMap<UUID, PlayerData> players;
    static ArrayList<UUID> builtPlayers, settingPlayers, debuggingPlayers;

    static NGData currentDebuggingNGData;

    public static void init() {
        players = new HashMap<>();
        builtPlayers = new ArrayList<>();
        settingPlayers = new ArrayList<>();
        debuggingPlayers = new ArrayList<>();
        currentDebuggingNGData = null;
    }


    public static HashMap<UUID, PlayerData> getPlayers() {
        return players;
    }

    public static void addPlayer(UUID uuid , PlayerData.PlayingType playerType ) {
        players.put(uuid, new PlayerData(uuid, playerType));
    }

    public static void addPlayer(Player p , PlayerData.PlayingType playerType ) {
        addPlayer(p.getUniqueId(), playerType);
    }

    public static void addPlayer( UUID uuid , PlayerData playerData ) {
        players.put(uuid, playerData);
    }

    public static void removePlayer( UUID u ) {
        players.remove( u );
    }

    public static void removePlayer( Player p ) {
        removePlayer( p.getUniqueId() );
    }

    public static void addBuiltPlayer(Player p ) {
        addBuiltPlayer(p.getUniqueId());
    }

    public static void addBuiltPlayer( UUID uuid ) {
        builtPlayers.add(uuid);
    }

    public static void removeBuiltPlayer( UUID u ) {
        builtPlayers.remove( u );
    }

    public static void removeBuiltPlayer( Player p ) {
        removeBuiltPlayer( p.getUniqueId() );
    }

    public static ArrayList<UUID> getBuiltPlayers() {
        return builtPlayers;
    }

    public static void resetBuiltPlayers() {
        builtPlayers = new ArrayList<>();
    }

    public static void addSettingPlayer(Player p ) {
        addSettingPlayer(p.getUniqueId());
    }

    public static void addSettingPlayer( UUID uuid ) {
        builtPlayers.add(uuid);
    }

    public static void removeSettingPlayer( UUID u ) {
        builtPlayers.remove( u );
    }

    public static void removeSettingPlayer( Player p ) {
        removeSettingPlayer( p.getUniqueId() );
    }

    public static ArrayList<UUID> getSettingPlayers() {
        return builtPlayers;
    }

    public static void addDebugginggPlayer(Player p ) {
        addDebuggingPlayer(p.getUniqueId());
    }

    public static void addDebuggingPlayer( UUID uuid ) {
        debuggingPlayers.add(uuid);
    }

    public static void removeDebuggingPlayer( UUID u ) {
        debuggingPlayers.remove( u );
    }

    public static void removeDebuggingPlayer( Player p ) {
        removeDebuggingPlayer( p.getUniqueId() );
    }

    public static ArrayList<UUID> getDebuggingPlayers() {
        return debuggingPlayers;
    }

    public static NGData getCurrentDebuggingNGData() {
        return currentDebuggingNGData;
    }

    public static void setCurrentDebuggingNGData(NGData currentDebuggingNGData) {
        GamePlayers.currentDebuggingNGData = currentDebuggingNGData;
    }


    public static ArrayList<PlayerData> getPlayersFromPlayingType(PlayerData.PlayingType playingType) {
        ArrayList<PlayerData> p = new ArrayList<>();
        for( PlayerData pd : getPlayers().values() ){
            if( pd.getPlayingType() != null ){
                if( pd.getPlayingType().equals(playingType) ){
                    p.add( pd );
                }
            }
        }
        return p;
    }

    public static PlayerData getData(Player p){
        PlayerData pd = players.get(p.getUniqueId());
        if( pd == null ) return new PlayerData(p.getUniqueId());
        return pd;
    }

    public static PlayerData getData(Player p, boolean isRaw){
        if(isRaw) return players.get(p.getUniqueId());
        return getData(p);
    }

    public static PlayerData getData(UUID u){
        PlayerData pd = players.get(u);
        if( pd == null ) return new PlayerData( u );
        return pd;
    }

    public static PlayerData getData(UUID u, boolean isRaw){
        if(isRaw) return players.get(u);
        return getData(u);
    }

    public static void setData(Player p, PlayerData pd){
        setData(p.getUniqueId(), pd);
    }

    public static void setData(UUID u, PlayerData pd){
        players.put(u, pd);
    }

    public static boolean isGameMaster(Player p) {
        return isGameMaster( GamePlayers.getData(p.getUniqueId()) );
    }

    public static boolean isGameMaster(PlayerData pd) {
        if (pd.getPlayingType().equals(PlayerData.PlayingType.GameMaster)
                || ((GameStatus.getStatus().equals(GameStatus.Ready) || GameStatus.getStatus().equals(GameStatus.End)) && pd.getPlayer().hasPermission("Oni.GameMaster"))) {
            return true;
        }
        return false;
    }

    public static boolean isSpectator(Player p) {
        return isSpectator( GamePlayers.getData(p.getUniqueId()) );
    }


    public static boolean isSpectator(PlayerData pd) {
        if (pd.getPlayingType().equals(PlayerData.PlayingType.Spectator)) {
            return true;
        }
        return false;
    }

}
