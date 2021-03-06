package io.github.axtuki1.ngbuilder.system;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.util.Config;
import io.github.axtuki1.ngbuilder.util.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NGData {

    public NGData(String id){
        Config dataConfig = new Config("NGData.yml");
        ConfigurationSection root = dataConfig.getConfigurationSection(id);
        this.id = id;
        if( root != null ){
            name = root.getString("name");
            shortName = root.getString("shortName");
            startDesc = root.getString("startDesc");
            desc = root.getString("desc");
            mode = NGMode.valueOf((root.getString("mode") != null ? root.getString("mode") : "None"));
            priority = root.getInt("priority");
            bonus = root.getInt("bonus");
            penalty = root.getInt("penalty");
            countMode = CountDenyMode.valueOf((root.getString("countMode") != null ? root.getString("countMode") : "None"));
            List<Material> data = new ArrayList<>();
            for( String s : root.getStringList("materials") ){
                data.add(Material.getMaterial(s));
            }
            blockDataList = data;
            isNotFound = false;
        }
    }

    private int count = 0, priority = 1, bonus = 0, penalty = 50;
    private String id, name = "名称未設定", shortName = "未設定", startDesc = "", desc = "";
    private NGMode mode = NGMode.None;
    private CountDenyMode countMode = CountDenyMode.None;
    private boolean isNotFound = true;
    private List<Material> blockDataList = new ArrayList<>();

    public boolean isNotFound(){
        return isNotFound;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName(){
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStartDescription(){
        return startDesc;
    }

    public void setStartDescription(String startDesc) {
        this.startDesc = startDesc;
    }

    public String getDescription(){
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public enum NGMode{
        None, Deny, Only, BlockBreakDeny, FlyingDeny, LiquidDeny, EntityDeny, CountDeny, StopTimeDeny
    }

    public enum CountDenyMode{
        None, Normal, Hard, Hardcore
    }

    public NGMode getNGMode(){
        return mode;
    }

    public void setNGMode(NGMode mode) {
        this.mode = mode;
    }

    public CountDenyMode getCountDenyMode(){
        return countMode;
    }

    public void setCountDenyMode(CountDenyMode countMode) {
        this.countMode = countMode;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Bonus倍率を返す。
     * @return
     */
    public double getBonus(){
        return 1;
    }

    public int getBonusPoint(){
        return bonus;
    }

    public void setBonusPoint(int bonus) {
        this.bonus = bonus;
    }

    /**
     * NGをした時のペナルティーを返す。
     * @return
     */
    public int getPenalty(){
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getCount(){
        if( count == 0 ){
            genCount();
        }
        return count;
    }

    public void genCount(){
        if( getNGMode().equals(NGMode.CountDeny) ){
            count = Utility.generateRandom(GameConfig.BlockCountMax.getInt() - GameConfig.BlockCountMin.getInt()) + GameConfig.BlockCountMin.getInt();
        } else if( getNGMode().equals(NGMode.StopTimeDeny) ){
            count = Utility.generateRandom(GameConfig.StopTimeMax.getInt() - GameConfig.StopTimeMin.getInt()) + GameConfig.StopTimeMin.getInt();
        }
    }

    public List<Material> getBlockDataList(){
        return blockDataList;
    }

    public void setBlockDataList(List<Material> blockDataList) {
        this.blockDataList = blockDataList;
    }

    public float getPriorityPer(){
        float num = 0;
        int allPriority = 0;
        for( NGData ngData : getAllNGData() ){
            allPriority = allPriority + ngData.getPriority();
        }
        return ((float)getPriority() / (float)allPriority);
    }

    public void save(){
        Config dataConfig = new Config("NGData.yml");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("shortName",shortName);
        map.put("startDesc",startDesc);
        map.put("desc",desc);
        map.put("mode",mode.toString());
        map.put("priority",priority);
        map.put("bonus",bonus);
        map.put("penalty",penalty);
        map.put("countMode",countMode.toString());
        List<String> m = new ArrayList<>();
        for( Material s : getBlockDataList() ){
            m.add(s.toString());
        }
        map.put("materials",m);
        dataConfig.set(id, map);
    }

    public void remove(){
        Config dataConfig = new Config("NGData.yml");
        dataConfig.set(id,null);
    }

    public boolean contains(ItemStack item){
        for( Material m : getBlockDataList() ){
            if( m.equals(item.getType()) ){
                return true;
            }
        }
        return false;
    }

    public boolean contains(Block block){
        for( Material m : getBlockDataList() ){
            if( m.equals(block.getType()) ){
                return true;
            }
        }
        return false;
    }

    public boolean canUse( ItemStack item ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(item);
        } else if( getNGMode().equals(NGMode.Deny) ){
            return !contains(item);
        } else {
            return true;
        }
    }

    public boolean canUse( Block block ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(block);
        } else if( getNGMode().equals(NGMode.Deny) ){
            return !contains(block);
        } else {
            return true;
        }
    }


    public static HashMap<NGData, Float> getPriorityList(){
        int allPriority = 0;
        for( NGData item : getAllNGData() ){
            allPriority = allPriority + item.getPriority();
        }
        HashMap<NGData, Float> perList = new HashMap<NGData, Float>();
        for( NGData item : getAllNGData() ){
            perList.put(item,  ((float)item.getPriority() / (float)allPriority));
        }
        return perList;
    }

    public static List<NGData> getAllNGData(){
        List<NGData> out = new ArrayList<>();
        Config dataConfig = new Config("NGData.yml");
        dataConfig.getConfig().getValues(false).keySet().forEach(key -> {
            out.add(new NGData(key));
        });
        return out;
    }

}
