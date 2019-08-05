package io.github.axtuki1.ngbuilder;

import io.github.axtuki1.ngbuilder.system.NGData;
import io.github.axtuki1.ngbuilder.system.ThemeData;
import io.github.axtuki1.ngbuilder.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum GameConfig {
    RoundTime{
        @Override
        public String getName() {
            return "1ラウンドの時間";
        }

        @Override
        public String getValue() {
            if( getInt() == -1 ){
                return "無限";
            }
            return getInt() + getUnit();
        }

        @Override
        public String getUnit() {
            return "秒";
        }

        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    DifficultyMin{
        @Override
        public String getName() {
            return "最小難易度";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    DifficultyMax{
        @Override
        public String getName() {
            return "最大難易度";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    Cycle{
        @Override
        public String getName() {
            return "何巡するか";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    BlockCountMax{
        @Override
        public String getName() {
            return "ブロック数制限ランダム最大値";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    BlockCountMin{
        @Override
        public String getName() {
            return "ブロック数制限ランダム最小値";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    StopTimeMax{
        @Override
        public String getName() {
            return "静止可能時間ランダム最大値";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    StopTimeMin{
        @Override
        public String getName() {
            return "静止可能時間ランダム最小値";
        }

        @Override
        public String getValue() {
            return String.valueOf( getInt() );
        }
        @Override
        public boolean isBoolean() {
            return false;
        }
    },
    AllowAllGenre{
        @Override
        public String getName() {
            return "すべてのジャンルを出現させるか";
        }

        @Override
        public String getValue() {
            return String.valueOf(getBoolean());
        }
    },
    ShowGenre{
        @Override
        public String getName() {
            return "ジャンルを表示するか";
        }

        @Override
        public String getValue() {
            return String.valueOf(getBoolean());
        }
    },
    ShowDifficulty{
        @Override
        public String getName() {
            return "難易度を表示するか";
        }

        @Override
        public String getValue() {
            return String.valueOf(getBoolean());
        }
    },
    DuplicateTheme{
        @Override
        public String getName() {
            return "1セッション内のお題の重複を許可するか";
        }

        @Override
        public String getValue() {
            return String.valueOf(getBoolean());
        }
    },
    AllowGenre{
        @Override
        public String getName() {
            return "出現するジャンル";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }

        @Override
        public boolean isStringList() {
            return true;
        }
    },
    ThemeList{
        @Override
        public String getName() {
            return "旧お題リスト";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }

        @Override
        public boolean isStringList() {
            return true;
        }
    },
    canBuilderPlacePoint1{
        @Override
        public String getName() {
            return "建築可能エリアポイント1";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isLocation() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }
    },
    canBuilderPlacePoint2{
        @Override
        public String getName() {
            return "建築可能エリアポイント1";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isLocation() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }
    },
    WorldSpawnPoint{
        @Override
        public String getName() {
            return "スポーンポイント";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isLocation() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }
    },
    BuilderSpawnPoint{
        @Override
        public String getName() {
            return "建築者移動ポイント";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isLocation() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }
    },
    NGDataPrioritys{
        @Override
        public String getName() {
            return "各NGの優先度";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isThemeList() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }

        @Override
        public String getFileName() {
            return "NGDataSettings.yml";
        }
    },
    ThemeDataList{
        @Override
        public String getName() {
            return "お題リスト";
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isThemeList() {
            return true;
        }

        @Override
        public boolean canCommandChange() {
            return false;
        }

        @Override
        public String getFileName() {
            return "ThemeList.yml";
        }
    }
//    NGSetList{
//        @Override
//        public String getName() {
//            return "NGセットリスト";
//        }
//
//        @Override
//        public String getValue() {
//            return null;
//        }
//
//        @Override
//        public boolean canCommandChange() {
//            return false;
//        }
//
//        @Override
//        public boolean isStringList() {
//            return true;
//        }
//    }
    ;
    /**
     * Configのパスを返します。
     * @return
     */
    public String getPath(){
        return toString();
    }

    public String getString(){
        return getConfig().getString(getPath());
    }

    public boolean canCommandChange(){
        return true;
    }

    public int getInt(){
        return getConfig().getInt(getPath());
    }

    public long getLong(){
        return getConfig().getLong(getPath());
    }

    public boolean getBoolean(){
        return getConfig().getBoolean(getPath());
    }

    public void set(Object obj){
        if( getFileName() != null ){
            new Config(getFileName()).set(getPath(), obj);
        } else {
            getConfig().set(getPath(), obj);
        }
        save();
        reload();
    }

    public abstract String getName();

    public boolean isBoolean(){
        return true;
    }

    public boolean isLocation() { return false; }

    public boolean isLocationList() { return false; }

    public String getUnit(){
        return "";
    }

    public abstract String getValue();

    public boolean isStringList(){
        return false;
    }

    public List<String> getStringList(){
        if( !isStringList() ) return null;
        return getConfig().getStringList(getPath());
    }

    public ConfigurationSection getConfigurationSection(){
        return getConfig().getConfigurationSection(getPath());
    }

    public void addStringList(String string){
        if( isStringList() ){
            ArrayList<String> out = new ArrayList<>(getStringList());
            if( !out.contains(string) ){
                out.add(string);
            }
            set(out);
        }
    }

    public void removeStringList(String string){
        if( isStringList() ){
            ArrayList<String> out = new ArrayList<>(getStringList());
            if( out.contains(string) ){
                out.remove(string);
            }
            set(out);
        }
    }

    public Location getLocation(){
        if( isLocation() ){
            ConfigurationSection f = getConfig().getConfigurationSection(getPath());
            return new Location(
                    (Bukkit.getWorld(f.getString("world")) != null ?
                            Bukkit.getWorld(f.getString("world")) :
                            Bukkit.getWorlds().get(0)
                    ),
                    f.getDouble("x"),
                    f.getDouble("y"),
                    f.getDouble("z"),
                    Float.parseFloat(f.getString("yaw")),
                    Float.parseFloat(f.getString("pitch"))
            );
        }
        return null;
    }

    public List<Location> getLocationList(){
        if( isLocationList() ){
            ConfigurationSection f = getConfig().getConfigurationSection(getPath());
            List<Location> loc = new ArrayList<>();
            f.getKeys(false).forEach(k ->{
                ConfigurationSection af = f.getConfigurationSection(k);
                loc.add(
                        new Location(
                                (Bukkit.getWorld(af.getString("world")) != null ?
                                        Bukkit.getWorld(af.getString("world")) :
                                        Bukkit.getWorlds().get(0)
                                ),
                                af.getDouble("x"),
                                af.getDouble("y"),
                                af.getDouble("z"),
                                Float.parseFloat(af.getString("yaw")),
                                Float.parseFloat(af.getString("pitch"))
                        )
                );
            });
            return loc;
        }
        return null;
    }

    public HashMap<String,Location> getLocationHashMap(){
        if( isLocationList() ){
            ConfigurationSection f = getConfig().getConfigurationSection(getPath());
            HashMap<String, Location> loc = new HashMap<>();
            f.getKeys(false).forEach(k ->{
                ConfigurationSection af = f.getConfigurationSection(k);
                loc.put(
                        k
                        ,
                        new Location(
                                (Bukkit.getWorld(af.getString("world")) != null ?
                                        Bukkit.getWorld(af.getString("world")) :
                                        Bukkit.getWorlds().get(0)
                                ),
                                af.getDouble("x"),
                                af.getDouble("y"),
                                af.getDouble("z"),
                                Float.parseFloat(af.getString("yaw")),
                                Float.parseFloat(af.getString("pitch"))
                        )
                );
            });
            return loc;
        }
        return null;
    }

    public void addLocation(Location loc){
        if( isLocationList() ){
            HashMap<String, Location> a = getLocationHashMap();
            a.put(Utility.generateRandomString(6), loc);

            HashMap<String, HashMap<String,Object>> out = new HashMap<>();

            for( String k : a.keySet() ){
                Location l = a.get(k);
                HashMap<String, Object> out1 = new HashMap<>();
                out1.put("world", l.getWorld().getName());
                out1.put("x", l.getX());
                out1.put("y", l.getY());
                out1.put("z", l.getZ());
                out1.put("pitch", l.getPitch());
                out1.put("yaw", l.getYaw());
                out.put(k, out1);
            }

            set( out );
        }
    }

    public void removeLocation(String key){
        if( isLocationList() ){
            HashMap<String, Location> a = getLocationHashMap();
            a.remove(key);

            HashMap<String, HashMap<String,Object>> out = new HashMap<>();

            for( String k : a.keySet() ){
                Location l = a.get(k);
                HashMap<String, Object> out1 = new HashMap<>();
                out1.put("world", l.getWorld().getName());
                out1.put("x", l.getX());
                out1.put("y", l.getY());
                out1.put("z", l.getZ());
                out1.put("pitch", l.getPitch());
                out1.put("yaw", l.getYaw());
                out.put(k, out1);
            }

            set( out );
        }
    }


    public void setLocation(Location loc){
        if( isLocation() ){
            HashMap<String, Object> out = new HashMap<>();
            out.put("world", loc.getWorld().getName());
            out.put("x", loc.getX());
            out.put("y", loc.getY());
            out.put("z", loc.getZ());
            out.put("pitch", loc.getPitch());
            out.put("yaw", loc.getYaw());
            set( out );
        }
    }


    public boolean isThemeList(){
        return false;
    }

    public List<ThemeData> getThemeList(){
        if( isThemeList() ){
            ConfigurationSection f = getConfig().getConfigurationSection(getPath());
            List<ThemeData> td = new ArrayList<>();
            f.getKeys(false).forEach(k ->{
                ConfigurationSection af = f.getConfigurationSection(k);
                ThemeData tda = new ThemeData(
                        af.getString("g"),
                        af.getString("n"),
                        af.getInt("d"),
                        af.getDouble("bp"),
                        af.getInt("ba")
                );

                tda.setKey(k);
                td.add(
                        tda
                );
            });
            return td;
        }
        return null;
    }

    public HashMap<String,ThemeData> getThemeHashMap(){
        if( isThemeList() ){
            ConfigurationSection f = getConfig().getConfigurationSection(getPath());
            HashMap<String, ThemeData> td = new HashMap<>();
            f.getKeys(false).forEach(k ->{
                ConfigurationSection af = f.getConfigurationSection(k);
                ThemeData tda = new ThemeData(
                        af.getString("g"),
                        af.getString("n"),
                        af.getInt("d"),
                        af.getDouble("bp"),
                        af.getInt("ba")
                );

                tda.setKey(k);
                td.put(
                        k
                        ,
                        tda
                );
            });
            return td;
        }
        return null;
    }

    public void addTheme(ThemeData td){
        if( isThemeList() ){
            HashMap<String, ThemeData> a = getThemeHashMap();

            boolean found = false;
            String key = "";

            for( String k : a.keySet() ){
                if( a.get(k).getTheme().equalsIgnoreCase(td.getTheme()) ){
                    found = true;
                    key = k;
                }
            }

            if( !found ){
                key = Utility.generateRandomString(10);

                while ( a.keySet().contains(key) ){
                    key = Utility.generateRandomString(10);
                }
            }

            a.put(key, td);

            HashMap<String, HashMap<String,Object>> out = new HashMap<>();

            for( String k : a.keySet() ){
                ThemeData l = a.get(k);
                HashMap<String, Object> out1 = new HashMap<>();
                out1.put("n", l.getTheme());
                out1.put("g", l.getGenre());
                out1.put("d", l.getDifficulty());
                out1.put("bp", l.getBonusPer());
                out1.put("ba", l.getBonusAdd());
                out.put(k, out1);
            }

            set( out );
        }
    }

    public void removeTheme(String key){
        if( isThemeList() ){
            HashMap<String, ThemeData> a = getThemeHashMap();
            a.remove(key);

            HashMap<String, HashMap<String,Object>> out = new HashMap<>();

            for( String k : a.keySet() ){
                ThemeData l = a.get(k);
                HashMap<String, Object> out1 = new HashMap<>();
                out1.put("n", l.getTheme());
                out1.put("g", l.getGenre());
                out1.put("d", l.getDifficulty());
                out1.put("bp", l.getBonusPer());
                out1.put("ba", l.getBonusAdd());
                out.put(k, out1);
            }

            set( out );
        }
    }

    public List<ThemeData> getThemeListFromDifficulty(int i){
        if( isThemeList() ){
            List<ThemeData> out = new ArrayList<>();
            for( ThemeData td : getThemeList() ){
                if( td.getDifficulty() == i ){
                    out.add(td);
                }
            }
            return out;
        }
        return null;
    }

    public HashMap<String, HashMap<String, ThemeData>> getThemeHashMapSortGenre(){
        if( isThemeList() ){
            HashMap<String, HashMap<String, ThemeData>> out = new HashMap<>();
            HashMap<String, ThemeData> all = getThemeHashMap();
            for( String key : all.keySet() ){
                ThemeData td = all.get(key);
                HashMap<String, ThemeData> tmp = out.get(td.getGenre());
                if( tmp == null ){
                    tmp = new HashMap<>();
                }
                tmp.put(key, td);
                out.put( td.getGenre(), tmp);
            }
            return out;
        }
        return null;
    }

    public HashMap<Integer, HashMap<String, ThemeData>> getThemeHashMapSortDifficulty(){
        if( isThemeList() ){
            HashMap<Integer, HashMap<String, ThemeData>> out = new HashMap<>();
            HashMap<String, ThemeData> all = getThemeHashMap();
            for( String key : all.keySet() ){
                ThemeData td = all.get(key);
                HashMap<String, ThemeData> tmp = out.get(td.getDifficulty());
                if( tmp == null ){
                    tmp = new HashMap<>();
                }
                tmp.put(key, td);
                out.put( td.getDifficulty(), tmp);
            }
            return out;
        }
        return null;
    }

    public List<ThemeData> getThemeListFromGenre(List<String> genreList){
        if( isThemeList() ){
            List<ThemeData> out = new ArrayList<>();
            HashMap<String, HashMap<String, ThemeData>> t = getThemeHashMapSortGenre();
            for(String genre : genreList){
                HashMap<String, ThemeData> a = t.get(genre);
                if( a != null ){
                    out.addAll(a.values());
                }
            }

            return out;
        }
        return null;
    }

    public List<ThemeData> getThemeListFromDifficulty(int min, int max){
        if( isThemeList() ){
            List<ThemeData> out = new ArrayList<>();
            for( ThemeData td : getThemeList() ){
                if( min == td.getDifficulty() || max == td.getDifficulty() ){
                    out.add(td);
                } else if( Math.min(td.getDifficulty(),min) == min && Math.max(td.getDifficulty(),max) == max ){
                    out.add(td);
                }
            }
            return out;
        }
        return null;
    }

    public HashMap<NGData, Integer> getNGDataPrioritys(){
        HashMap<NGData, Integer> out = new HashMap<>();
        for( NGData ng : NGData.values() ){
            out.put(ng, ng.getPriority());
        }
        return out;
    }

    public void setNGDataPrioritys(HashMap<NGData, Integer> input){
        HashMap<String, Integer> out = new HashMap<>();
        for( NGData ng : input.keySet() ){
            out.put(ng.name(), input.get(ng));
        }
        set(out);
    }

    public String getFileName(){
        return null;
    }

    /**
     * Configを再読込します。
     */
    public void reload(){
        if( getFileName() != null ){
            new Config(getFileName()).reloadConfig();
        }
        NGBuilder.getMain().reloadConfig();
    }

    /**
     * Configを保存します。
     */
    public void save(){
        if( getFileName() != null ){
            new Config(getFileName()).saveConfig();
        }
        NGBuilder.getMain().saveConfig();
    }

    public FileConfiguration getConfig() {
        if( getFileName() != null ){
            return new Config(getFileName()).getConfig();
        }
        return NGBuilder.getMain().getConfig();
    }
}
