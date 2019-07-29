package io.github.axtuki1.ngbuilder.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    // プレイヤーのUUID
    private final UUID player;
    // プレイヤーの名前
    private String name;
    // isHiddenInfo: サイドバーの表示を最小限にするか
    // isBuilder: 建築者か
    private boolean isHiddenInfo, isBuilder;
    // プレイヤーかGMか
    private PlayingType type;
    // ポイント
    private int point;
    // デバッグ項目
    private HashMap<String, Boolean> debug;

    public PlayerData(Player p ){
        this.player = p.getUniqueId();
        this.type = PlayingType.Player;
        point = 0;
        isHiddenInfo = false;
        name = p.getName();
        debug = new HashMap<>();
        debug.put("", false);
        isBuilder = false;
    }

    public PlayerData(UUID uuid) {
        this.player = uuid;
        this.type = PlayingType.Player;
        isHiddenInfo = false;
        Player p = getPlayer();
        if( p != null ) {
            name = p.getName();
        }
        debug = new HashMap<>();
        debug.put("", false);
        point = 0;
        isBuilder = false;
    }

    public PlayerData(UUID uuid, PlayingType type){
        this.player = uuid;
        this.type = type;
        point = 0;
        isHiddenInfo = false;
        Player p = getPlayer();
        if( p != null ) {
            name = p.getName();
        }
        debug = new HashMap<>();
        debug.put("", false);
        isBuilder = false;
    }

    public PlayerData(Player p, PlayingType type){
        this.player = p.getUniqueId();
        point = 0;
        this.type = type;
        isHiddenInfo = false;
        name = p.getName();
        debug = new HashMap<>();
        debug.put("", false);
        isBuilder = false;
    }

    public void dump() {
        System.out.print("=================================");
        System.out.print("isOnline: " + (getPlayer() != null));
        System.out.print("UUID: " + getUUID());
        System.out.print("Name: " + getName());
        System.out.print("PlayingType: " + getPlayingType());
        System.out.print("Point: " + getPoint());
        System.out.print("isBuilder: " + isBuilder());
        System.out.print("isHiddenInfo: " + isHiddenInfo());
        System.out.print("=================================");
    }

    public enum PlayingType {
        GameMaster, Spectator, Player
    }

    /**
     * このPlayerDataに振られているプレイヤーのUUIDを返す。
     * ログアウト等でPlayerが利用できなくなる事を防ぐ為。
     */
    public UUID getUUID() {
        return player;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public String getName(){
        Player p = getPlayer();
        if( p != null ) {
            name = p.getName();
        }
        return name;
    }

    public boolean isBuilder() {
        return isBuilder;
    }

    public void setBuilder(boolean builder) {
        isBuilder = builder;
    }

    public void setPlayingType(PlayingType type) {
        this.type = type;
    }

    public PlayingType getPlayingType() {
        return type;
    }

    /**
     * サイドバーの表示を最小限にするか
     * @return
     */
    public boolean isHiddenInfo() {
        return isHiddenInfo;
    }

    public void setHiddenInfo(boolean hiddenInfo) {
        isHiddenInfo = hiddenInfo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        if (point <= 0) {
            this.point = 0;
        } else {
            this.point = point;
        }
    }

    public void addPoint(int point){
        setPoint( getPoint() + point );
    }

    public void removePoint(int point){
        setPoint( getPoint() - point );
    }

    public void setDebug(HashMap<String, Boolean> debug) {
        this.debug.putAll(debug);
    }

    public HashMap<String, Boolean> getDebug() {
        return debug;
    }
}
