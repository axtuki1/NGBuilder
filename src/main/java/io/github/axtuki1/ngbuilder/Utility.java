package io.github.axtuki1.ngbuilder;

import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Pattern;

public class Utility {
    public static Player getPlayer(String Name) {
        if( Name == null ){
            return null;
        }
        return Bukkit.getPlayerExact(Name);
    }

    public static Player getUUIDPlayer(String UUID) {
        if( UUID == null ){
            return null;
        }
        return Bukkit.getPlayer(UUID);
    }
    /**
     * 大文字小文字を区別せずにreplaceAllします
     *
     * @param regex 置き換えたい文字列
     * @param reql  置換後文字列
     * @param text  置換対象文字列
     */
    public static String myReplaceAll(String regex, String reql, String text) {
        String retStr = "";
        retStr = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text).replaceAll(reql);
        return retStr;
    }

    /**
     * コマンド引数を文字列に変換します。
     * @param args コマンド引数
     * @param start 開始するindex []に指定する数字でOK
     * @return
     */
    public static String CommandText(String[] args, int start){
        StringBuilder out = new StringBuilder();
        for(int i = start; i < args.length ; i++){
            out.append(args[i]).append(" ");
        }
        if( (out.length() - 1) <= 0 ){
            return out.toString();
        } else {
            return out.toString().substring(0,out.length() - 1);
        }
    }

    public static void sendCmdHelp(Player p, String cmd, String help, boolean Prefix){
        String send = " ";
        if(Prefix){
            send = NGBuilder.getPrefix();
        }
        p.sendMessage(send + CmdColor(cmd));
        p.sendMessage(send + ChatColor.GREEN + "   " + help);
    }

    public static void sendCmdHelp(Player p, String cmd, String help){
        sendCmdHelp(p, cmd, help, false);
    }

    public static void sendCmdHelp(CommandSender p, String cmd, String help, boolean Prefix){
        String send = " ";
        if(Prefix){
            send = NGBuilder.getPrefix();
        }
        p.sendMessage(send + CmdColor(cmd));
        p.sendMessage(send + ChatColor.GREEN + "   " + help);
    }

    public static void sendCmdHelp(CommandSender p, String cmd, String help){
        String send = " ";
        p.sendMessage(send + CmdColor(cmd));
        p.sendMessage(send + ChatColor.GREEN + "   " + help);
    }

    public static String CmdColor(String s){
        return ChatColor.AQUA+s;
    }

    public static ChatColor getColor(float per){
        if( per <= 0.1 ){
            return ChatColor.RED;
        }
        if( per <= 0.25 ){
            return ChatColor.YELLOW;
        }
        return ChatColor.GREEN;
    }

    public static ChatColor getColor(float now, float max){
        float per = now / max;
        if( per <= 0.1 ){
            return ChatColor.RED;
        }
        if( per <= 0.25 ){
            return ChatColor.YELLOW;
        }
        return ChatColor.GREEN;
    }

    /**
     * 乱数を生成します 0 から max
     * @param max 生成される最大数値 - 1
     * @return 生成された整数
     */
    public static int generateRandom( int max ) {
        return (int)(Math.random() * max);
    }

    public static String getReason(EntityDamageEvent.DamageCause dc) {
        switch (dc){
            case FALL:
                return "落下死";
            case LAVA:
                return "溶岩死";
            case FIRE:
            case FIRE_TICK:
                return "焼死";
            case VOID:
                return "奈落の底";
            case MAGIC:
                return "魔法";
            case POISON:
                return "ポーション";
            case THORNS:
                return "棘の鎧";
            case WITHER:
                return "ウィザー";
            case DROWNING:
                return "溺死";
            case LIGHTNING:
                return "落雷";
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
                return "爆死";
            case FLY_INTO_WALL:
                return "頭蓋骨強打";
            case ENTITY_SWEEP_ATTACK:
                return "SWEEEEEEEEEP";
            case HOT_FLOOR:
                return "足裏の火傷";
            case MELTING:
                return "融解";
            case SUICIDE:
                return "自害";
            case STARVATION:
                return "餓死";
            case CRAMMING:
                return "漬物化";
            case PROJECTILE:
                return "飛翔体";
            case CONTACT:
                return "サボテン";
            case SUFFOCATION:
                return "圧死";
            case FALLING_BLOCK:
                return "後頭部強打";
            case DRAGON_BREATH:
                return "ドラゴンの息吹";
            case ENTITY_ATTACK:
                return "エンティティ";
            case CUSTOM:
                return "不明";

        }
        return "不明";
    }

    public static String getItemName(ItemStack item, String nullstr) {
        if (item == null || item.getType() == Material.AIR) {
            return nullstr;
        }
        if( item.hasItemMeta() ){
            ItemMeta itemMeta = item.getItemMeta();
            if( itemMeta.hasDisplayName() ){
                return item.getItemMeta().getDisplayName();
            } else if( itemMeta.hasLocalizedName() ){
                return item.getItemMeta().getLocalizedName();
            }
        }
        return item.getType().toString();
    }

    public static void SwitchConfig(String[] args, CommandSender sender, String ConfigKey, String TrueMsg, String FalseMsg){
        if( args.length == 2 ){
            NGBuilder.getMain().reloadConfig();
            boolean now_s = NGBuilder.getMain().getConfig().getBoolean(ConfigKey);
            if( now_s ){
                // trueのとき
                NGBuilder.getMain().getConfig().set(ConfigKey, false);
                NGBuilder.getMain().saveConfig();
                NGBuilder.getMain().reloadConfig();
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + FalseMsg);
            } else {
                // falseのとき
                NGBuilder.getMain().getConfig().set(ConfigKey, true);
                NGBuilder.getMain().saveConfig();
                NGBuilder.getMain().reloadConfig();
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + TrueMsg);
            }
        } else {
            NGBuilder.getMain().reloadConfig();
            if( args[2].equalsIgnoreCase("false") ){
                // trueのとき
                NGBuilder.getMain().getConfig().set(ConfigKey, false);
                NGBuilder.getMain().saveConfig();
                NGBuilder.getMain().reloadConfig();
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + FalseMsg);
            } else if(args[2].equalsIgnoreCase("true")) {
                // falseのとき
                NGBuilder.getMain().getConfig().set(ConfigKey, true);
                NGBuilder.getMain().saveConfig();
                NGBuilder.getMain().reloadConfig();
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + TrueMsg);
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "指定された文字列ではコマンドを実行しきれません....");
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.WHITE + "指定可能文字列: true false");
            }
        }
    }

    public static String TextToggle(String text, boolean flag){
        return TextToggle(text,flag,ChatColor.GREEN + "有効",ChatColor.RED + "無効");
    }

    public static String TextToggle(String text, boolean flag, String on, String off){
        String out = "";
        if(flag){
            out = text + ": " + on;
        } else {
            out = text + ": " + off;
        }
        return out;
    }

    public static List<String> convertPlayerName(List<Player> ps){
        List<String> out = new ArrayList<>();
        for( Player p : ps ){
            out.add(p.getName());
        }
        return out;
    }

    public static String listing(List<String> o){
        StringBuilder sb = new StringBuilder();
        String output = "";
        for( String a : o ){
            sb.append(a).append(", ");
        }
        if( (sb.length() - 2) >= 0 ){
            output = sb.substring(0, sb.length() - 2);
        } else {
            output = sb.toString();
        }
        return output;
    }


    /**
     * 偶数か確認島sう。
     * @param num
     * @return
     */
    public static boolean isEven(int num){
        if( num%2 == 0 ) return true;
        return false;
    }

    /**
     * 指定プレイヤーから半径range内にいるPlayerを列挙して"指定プレイヤーを削除して"返します。
     * ついでに距離もセット。
     * @param p 中心座標
     * @param range 範囲
     * @return 範囲内のプレイヤーリスト
     */
    public static HashMap<Player, Double> getWithinRangeList(Player p, int range){
        HashMap<Player, Double> out = getWithinRangeList(p.getLocation(), range);
        if( out.containsKey(p) ){
            out.remove(p);
        }
        return out;
    }

    /**
     * 入力座標から半径range内にいるPlayerを列挙して返します。
     * ついでに距離もセット。
     * @param loc 中心座標
     * @param range 範囲
     * @return 範囲内のプレイヤーリスト
     */
    public static HashMap<Player, Double> getWithinRangeList(Location loc, int range){
        HashMap<Player, Double> out = new HashMap<>();
        World world = loc.getWorld();
        for(LivingEntity entity : world.getLivingEntities()){
            if( entity instanceof Player ){
//                Bukkit.broadcastMessage( loc.distance(entity.getLocation()) + "<=" + range + " | " + (loc.distance(entity.getLocation()) <= range)  );
                double dis = loc.distance(entity.getLocation());
                if( dis <= range ){
                    out.put( (Player)entity, dis );
                }
            }
        }
        return out;
    }

    /**
     * 入力座標から半径range内に指定プレイヤーがいるか確認します。
     * @param loc 中心座標
     * @param range 半径
     * @param p プレイヤー
     * @return 範囲内にいるかいないか
     */
    public static boolean isWithinRange(Location loc, int range, Player p) {
        if( getWithinRangeList(loc, range).keySet().contains(p) ) return true;
        return false;
    }

    public static Map.Entry<Player, Double> getNearPlayer(Player p){
        HashMap<Player, Double> hashMap = Utility.getWithinRangeList(p, 100);

        if( hashMap.size() == 0 ){
            return null;
        }

        List<Map.Entry<Player,Double>> entries =
                new ArrayList<Map.Entry<Player,Double>>(hashMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Player,Double>>() {
            @Override
            public int compare(
                    Map.Entry<Player,Double> entry1, Map.Entry<Player,Double> entry2) {
                return ((Double)entry2.getValue()).compareTo((Double)entry1.getValue());
            }
        });
        return entries.get( entries.size() - 1 );
    }

    public static String convSecToTime(int sec){
        int forSec = (sec % 60) % 60;
        int min = sec / 60;
        return fillZero( min ) + ":"+fillZero( forSec );
    }

    public static String fillZero(int i){
        return ( i >= 10 ? String.valueOf(i) : "0" + i );
    }

    public static String convertScale(double num, int scale){
        BigDecimal bd = new BigDecimal(""+num);

        // 小数第一位を四捨五入
        bd = bd.setScale(scale, RoundingMode.HALF_UP);

        return bd.toPlainString();
    }

    public static String generateRandomString(int i) {
        return RandomStringUtils.randomAlphabetic(i);
    }

    public static int getPing(Player p) {
        Class<?> CPClass = Reflection.getClass("{cb}.entity.CraftPlayer");
        Object CP = CPClass.cast(p);
        Method getHandle = null;
        try {
            getHandle = CP.getClass().getMethod("getHandle", new Class[0]);

            Object EntityPlayer = null;
            EntityPlayer = getHandle.invoke(CP, new Object[0]);
            Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            return ping.getInt(EntityPlayer);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getPingString(Player p) {
        int ping = getPing(p);
        if(ping <= -1) return ChatColor.RED + "Errpr";
        return getPingColor(ping).toString() + ping + "ms";
    }

    public static ChatColor getPingColor(int ping_i){
        if (ping_i > 100) {
            return ChatColor.RED;
        } else if (ping_i > 50) {
            return ChatColor.YELLOW;
        } else {
            return ChatColor.GREEN;
        }
    }

    /**
     * 指定したプレイヤーが手に持っているアイテムを返す
     * @param player プレイヤー
     * @return 手に持っているアイテム
     */
    @SuppressWarnings("deprecation")
    public static ItemStack getItemInHand(Player player) {
        if ( Utility.isCB19orLater() ) {
            return player.getInventory().getItemInMainHand();
        } else {
            return player.getItemInHand();
        }
    }

    /**
     * 現在動作中のCraftBukkitが、v1.9 以上かどうかを確認する
     * @return v1.9以上ならtrue、そうでないならfalse
     */
    public static boolean isCB19orLater() {
        return isUpperVersion(Bukkit.getBukkitVersion(), "1.9");
    }

    /**
     * 現在動作中のCraftBukkitが、v1.11 以上かどうかを確認する
     * @return v1.11以上ならtrue、そうでないならfalse
     * form https://github.com/ucchyocean
     */
    public static boolean isCB111orLater() {
        return isUpperVersion(Bukkit.getBukkitVersion(), "1.11");
    }

    /**
     * 現在動作中のCraftBukkitが、v1.13 以上かどうかを確認する
     * @return v1.13以上ならtrue、そうでないならfalse
     * form https://github.com/ucchyocean
     */
    public static boolean isCB113orLater() {
        return isUpperVersion(Bukkit.getBukkitVersion(), "1.13");
    }

    public static boolean isLater(String border) {
        return isUpperVersion(Bukkit.getBukkitVersion(), border);
    }


    /**
     * 指定されたバージョンが、基準より新しいバージョンかどうかを確認する
     * @param version 確認するバージョン
     * @param border 基準のバージョン
     * @return 基準より確認対象の方が新しいバージョンかどうか<br/>
     * ただし、無効なバージョン番号（数値でないなど）が指定された場合はfalseに、
     * 2つのバージョンが完全一致した場合はtrueになる。
     * form https://github.com/ucchyocean
     */
    private static boolean isUpperVersion(String version, String border) {

        int hyphen = version.indexOf("-");
        if ( hyphen > 0 ) {
            version = version.substring(0, hyphen);
        }

        String[] versionArray = version.split("\\.");
        int[] versionNumbers = new int[versionArray.length];
        for ( int i=0; i<versionArray.length; i++ ) {
            if ( !versionArray[i].matches("[0-9]+") )
                return false;
            versionNumbers[i] = Integer.parseInt(versionArray[i]);
        }

        String[] borderArray = border.split("\\.");
        int[] borderNumbers = new int[borderArray.length];
        for ( int i=0; i<borderArray.length; i++ ) {
            if ( !borderArray[i].matches("[0-9]+") )
                return false;
            borderNumbers[i] = Integer.parseInt(borderArray[i]);
        }

        int index = 0;
        while ( (versionNumbers.length > index) && (borderNumbers.length > index) ) {
            if ( versionNumbers[index] > borderNumbers[index] ) {
                return true;
            } else if ( versionNumbers[index] < borderNumbers[index] ) {
                return false;
            }
            index++;
        }
        if ( borderNumbers.length == index ) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLocationArea( Location target, Location point1, Location point2 ){
        for (int xPoint = Math.min(point1.getBlockX(), point2.getBlockX()); xPoint <=
                Math.max(point1.getBlockX(), point2.getBlockX())
                ; xPoint++) {
            for (int yPoint = Math.min(point1.getBlockY(), point2.getBlockY()); yPoint <=
                    Math.max(point1.getBlockY(), point2.getBlockY())
                    ; yPoint++) {
                for (int zPoint = Math.min(point1.getBlockZ(), point2.getBlockZ()); zPoint <=
                        Math.max(point1.getBlockZ(), point2.getBlockZ())
                        ; zPoint++) {
                    if (xPoint == target.getBlockX()){
                        if (yPoint == target.getBlockY()){
                            if (zPoint == target.getBlockZ()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void AreaCleaner( Location point1, Location point2 ){
        for (int xPoint = Math.min(point1.getBlockX(), point2.getBlockX()); xPoint <=
                Math.max(point1.getBlockX(), point2.getBlockX())
                ; xPoint++) {
            for (int yPoint = Math.min(point1.getBlockY(), point2.getBlockY()); yPoint <=
                    Math.max(point1.getBlockY(), point2.getBlockY())
                    ; yPoint++) {
                for (int zPoint = Math.min(point1.getBlockZ(), point2.getBlockZ()); zPoint <=
                        Math.max(point1.getBlockZ(), point2.getBlockZ())
                        ; zPoint++) {
                    point1.getWorld().getBlockAt(xPoint, yPoint, zPoint).setType(Material.AIR);
                }
            }
        }
    }

    public static String listingByPlayerData(List<PlayerData> o){
        StringBuilder sb = new StringBuilder();
        String output = "";
        for( PlayerData a : o ){
            sb.append(a.getName()).append(", ");
        }
        if( (sb.length() - 2) >= 0 ){
            output = sb.substring(0, sb.length() - 2);
        } else {
            output = sb.toString();
        }
        return output;
    }

    public static void playSoundToAllPlayer(Sound sound, float vol, float pitch){
        for( Player p : Bukkit.getOnlinePlayers() ){
            p.playSound(p.getLocation(), sound, vol, pitch);
        }
    }

    private static int getMini(int x, int y) {
        if (x > y) {
            return y;
        }
        return x;
    }

    private static int getMax(int x, int y) {
        if (x < y) {
            return y;
        }
        return x;
    }

}
