package io.github.axtuki1.ngbuilder.system;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum NGData {
    BlockBreakDeny{
        @Override
        public String getName() {
            return "ブロックの破壊禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.LIGHT_PURPLE + "ブロック破壊 "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public double getBonus() {
            return 2;
        }

        @Override
        public int getPenalty() {
            return 150;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.BlockBreakDeny;
        }

        @Override
        public int getPriority() {
            return 3;
        }
    },
    FlyingDeny{
        @Override
        public String getName() {
            return "ジャンプダブルタップの飛行禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.LIGHT_PURPLE + "飛行"+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public double getBonus() {
            return 1.6;
        }

        @Override
        public int getPenalty() {
            return 150;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.FlyingDeny;
        }

        @Override
        public int getPriority() {
            return 3;
        }
    },
    Liquideny{
        @Override
        public String getName() {
            return "液体の使用禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.AQUA + "液体 "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public double getBonus() {
            return 1.4;
        }

        @Override
        public int getPenalty() {
            return 200;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.LiquidDeny;
        }

        @Override
        public int getPriority() {
            return 4;
        }
    },
    NetherAndEndOnly{
        @Override
        public String getName() {
            return "ネザー&エンド系ブロックのみ使用可能";
        }

        @Override
        public String getShortName() {
            return ChatColor.LIGHT_PURPLE + "ﾈｻﾞｰ/ｴﾝﾄﾞ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 6;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.NETHER_BRICK),
                    new BlockData(Material.STEP, 6),
                    new BlockData(Material.STEP, 7),
                    new BlockData(Material.STEP, 14),
                    new BlockData(Material.STEP, 15),
                    new BlockData(Material.DOUBLE_STEP, 6),
                    new BlockData(Material.DOUBLE_STEP, 7),
                    new BlockData(Material.DOUBLE_STEP, 14),
                    new BlockData(Material.DOUBLE_STEP, 15),
                    new BlockData(Material.NETHER_BRICK_STAIRS, true),
                    new BlockData(Material.NETHERRACK),
                    new BlockData(Material.RED_NETHER_BRICK),
                    new BlockData(Material.END_ROD, true),
                    new BlockData(Material.SOUL_SAND),
                    new BlockData(Material.GLOWSTONE),
                    new BlockData(Material.NETHER_WART_BLOCK),
                    new BlockData(Material.QUARTZ_BLOCK, true),
                    new BlockData(Material.QUARTZ_STAIRS, true),
                    new BlockData(Material.END_CRYSTAL),
                    new BlockData(Material.MAGMA),
                    new BlockData(Material.PURPUR_BLOCK),
                    new BlockData(Material.PURPUR_PILLAR),
                    new BlockData(Material.PURPUR_SLAB, true),
                    new BlockData(Material.PURPUR_DOUBLE_SLAB, true),
                    new BlockData(Material.PURPUR_STAIRS, true),
                    new BlockData(Material.CHORUS_PLANT),
                    new BlockData(Material.CHORUS_FLOWER),
                    new BlockData(Material.NETHER_FENCE),
                    new BlockData(Material.OBSIDIAN),
                    new BlockData(Material.ENDER_STONE),
                    new BlockData(Material.END_BRICKS),
                    new BlockData(Material.LAVA, true),
                    new BlockData(Material.LAVA_BUCKET, true),
                    new BlockData(Material.STATIONARY_LAVA, true)
//                    new BlockData(Material)
            );
        }
    },
    NetherAndEndDeny{
        @Override
        public String getName() {
            return "ネザー&エンド系ブロックの使用禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.LIGHT_PURPLE + "ﾈｻﾞｰ/ｴﾝﾄﾞ "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getPriority() {
            return 6;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            return NetherAndEndOnly.getOnlyMaterial();
        }
    },
    EntityDeny{
        @Override
        public String getName() {
            return "ｴﾝﾃｨﾃｨの召喚禁止(ｱｲﾃﾑﾄﾞﾛｯﾌﾟ含む)";
        }

        @Override
        public String getShortName() {
            return ChatColor.LIGHT_PURPLE + "ｴﾝﾃｨﾃｨ召喚 "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.EntityDeny;
        }

        @Override
        public int getPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            return NetherAndEndOnly.getOnlyMaterial();
        }
    },
    StairsDeny{
        @Override
        public String getName() {
            return "階段ブロックの使用禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.GOLD + "階段ﾌﾞﾛｯｸ "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getPriority() {
            return 5;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.COBBLESTONE_STAIRS, true),
                    new BlockData(Material.SMOOTH_STAIRS, true),
                    new BlockData(Material.WOOD_STAIRS, true),
                    new BlockData(Material.BIRCH_WOOD_STAIRS, true),
                    new BlockData(Material.JUNGLE_WOOD_STAIRS, true),
                    new BlockData(Material.SPRUCE_WOOD_STAIRS, true),
                    new BlockData(Material.ACACIA_STAIRS, true),
                    new BlockData(Material.DARK_OAK_STAIRS, true),
                    new BlockData(Material.RED_SANDSTONE_STAIRS, true),
                    new BlockData(Material.SANDSTONE_STAIRS, true),
                    new BlockData(Material.NETHER_BRICK_STAIRS, true),
                    new BlockData(Material.QUARTZ_STAIRS, true),
                    new BlockData(Material.BRICK_STAIRS, true),
                    new BlockData(Material.PURPUR_STAIRS, true)

            );
        }
    },
    StairsOnly{
        @Override
        public String getName() {
            return "階段ブロックのみ使用可能";
        }

        @Override
        public String getShortName() {
            return ChatColor.GOLD + "階段ﾌﾞﾛｯｸ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return StairsDeny.getDenyMaterial();
        }

        @Override
        public double getBonus() {
            return 1.7;
        }
    },
    OreDeny{
        @Override
        public String getName() {
            return "鉱石/原石ブロックの使用禁止";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "鉱石/原石 "+ ChatColor.RED +"禁止";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getPriority() {
            return 6;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.IRON_ORE),
                    new BlockData(Material.GOLD_ORE),
                    new BlockData(Material.COAL_ORE),
                    new BlockData(Material.EMERALD_ORE),
                    new BlockData(Material.REDSTONE_ORE),
                    new BlockData(Material.DIAMOND_ORE),
                    new BlockData(Material.LAPIS_ORE),
                    new BlockData(Material.QUARTZ_ORE),
                    new BlockData(Material.IRON_BLOCK),
                    new BlockData(Material.GOLD_BLOCK),
                    new BlockData(Material.COAL_BLOCK),
                    new BlockData(Material.DIAMOND_BLOCK),
                    new BlockData(Material.EMERALD_BLOCK),
                    new BlockData(Material.REDSTONE_BLOCK),
                    new BlockData(Material.LAPIS_BLOCK),
                    new BlockData(Material.QUARTZ_BLOCK)
            );
        }
    },
    OreOnly{
        @Override
        public String getName() {
            return "鉱石/原石ブロックのみ使用可能";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "鉱石/原石 "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return OreDeny.getDenyMaterial();
        }

        @Override
        public double getBonus() {
            return 1.2;
        }
    },
    CarpetOnly{
        @Override
        public String getName() {
            return "カーペットのみ使用可能(ゲキムズ！)";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "ｶｰﾍﾟｯﾄ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.CARPET, true)
            );
        }

        @Override
        public double getBonus() {
            return 2;
        }
    },
    WoolOnly{
        @Override
        public String getName() {
            return "羊毛ブロックのみ使用可能(ｶｰﾍﾟｯﾄNG)";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "羊毛ﾌﾞﾛｯｸ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.WOOL, true)
            );
        }

        @Override
        public double getBonus() {
            return 1.1;
        }
    },
    GlassOnly{
        @Override
        public String getName() {
            return "ガラスのみ使用可能(ゲキムズ！)";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "ガラス "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.GLASS, true),
                    new BlockData(Material.STAINED_GLASS, true),
                    new BlockData(Material.STAINED_GLASS_PANE, true),
                    new BlockData(Material.THIN_GLASS, true)
            );
        }

        @Override
        public double getBonus() {
            return 1.5;
        }
    },
    HalfBlockOnly{
        @Override
        public String getName() {
            return "ハーフブロックのみ使用可能";
        }

        @Override
        public String getShortName() {
            return ChatColor.GREEN + "ﾊｰﾌﾌﾞﾛｯｸ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.STEP, true),
                    new BlockData(Material.WOOD_STEP, true),
                    new BlockData(Material.WOOD_DOUBLE_STEP, true),
                    new BlockData(Material.DOUBLE_STEP, true),
                    new BlockData(Material.PURPUR_SLAB, true),
                    new BlockData(Material.PURPUR_DOUBLE_SLAB, true)
            );
        }

        @Override
        public double getBonus() {
            return 2;
        }
    },
    WoodOnly{
        @Override
        public String getName() {
            return "木系ブロックのみ使用可能";
        }

        @Override
        public String getShortName() {
            return ChatColor.GOLD + "木系ﾌﾞﾛｯｸ "+ ChatColor.AQUA +"Only";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getPriority() {
            return 5;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.LOG, true),
                    new BlockData(Material.LOG_2, true),

                    new BlockData(Material.WOOD, true),

                    new BlockData(Material.WOOD_STEP, true),
                    new BlockData(Material.WOOD_DOUBLE_STEP, true),

                    new BlockData(Material.WOOD_STAIRS, true),
                    new BlockData(Material.DARK_OAK_STAIRS, true),
                    new BlockData(Material.JUNGLE_WOOD_STAIRS, true),
                    new BlockData(Material.ACACIA_STAIRS, true),
                    new BlockData(Material.SPRUCE_WOOD_STAIRS, true),
                    new BlockData(Material.BIRCH_WOOD_STAIRS, true),

                    new BlockData(Material.TRAP_DOOR, true),

                    new BlockData(Material.CHEST, true),
                    new BlockData(Material.TRAPPED_CHEST, true),

                    new BlockData(Material.WOODEN_DOOR, true),
                    new BlockData(Material.SPRUCE_DOOR, true),
                    new BlockData(Material.BIRCH_DOOR, true),
                    new BlockData(Material.JUNGLE_DOOR, true),
                    new BlockData(Material.ACACIA_DOOR, true),
                    new BlockData(Material.DARK_OAK_DOOR, true),

                    new BlockData(Material.FENCE, true),
                    new BlockData(Material.SPRUCE_FENCE, true),
                    new BlockData(Material.BIRCH_FENCE, true),
                    new BlockData(Material.JUNGLE_FENCE, true),
                    new BlockData(Material.ACACIA_FENCE, true),
                    new BlockData(Material.DARK_OAK_FENCE, true),

                    new BlockData(Material.FENCE_GATE, true),
                    new BlockData(Material.SPRUCE_FENCE_GATE, true),
                    new BlockData(Material.BIRCH_FENCE_GATE, true),
                    new BlockData(Material.JUNGLE_FENCE_GATE, true),
                    new BlockData(Material.ACACIA_FENCE_GATE, true),
                    new BlockData(Material.DARK_OAK_FENCE_GATE, true),

                    new BlockData(Material.WOOD_PLATE, true),
                    new BlockData(Material.WOOD_BUTTON, true),

                    new BlockData(Material.NETHER_FENCE, true)
            );
        }

        @Override
        public double getBonus() {
            return 2;
        }
    },
    CountDenyNormal{
        @Override
        public String getName() {
            return "ﾌﾞﾛｯｸ数制限: " + getCount() + "ﾌﾞﾛｯｸ";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "ブロック数(ﾉｰﾏﾙ)";
        }

        @Override
        public String getDescription() {
            return "液体とピストンの伸びているブロックを含みません。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getPriority() {
            return 5;
        }

        @Override
        public CountDenyMode getCountDenyMode() {
            return CountDenyMode.Normal;
        }
    },
    CountDenyHard{
        @Override
        public String getName() {
            return "ﾌﾞﾛｯｸ数制限(ﾊｰﾄﾞ): " + getCount() + "ﾌﾞﾛｯｸ";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "ブロック数(ﾊｰﾄﾞ)";
        }

        @Override
        public String getDescription() {
            return "液体は含みませんが、ピストンの起動状態では2ブロックとしてカウントされます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public CountDenyMode getCountDenyMode() {
            return CountDenyMode.Hard;
        }
    },
    CountDenyHardCore{
        @Override
        public String getName() {
            return "ﾌﾞﾛｯｸ数制限(ﾊｰﾄﾞｺｱ): " + getCount() + "ﾌﾞﾛｯｸ";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "ブロック数(ﾊｰﾄﾞｺｱ)";
        }

        @Override
        public String getDescription() {
            return "水源/水流を含むずべてのブロックをカウントします。主に水の扱いには注意！";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public CountDenyMode getCountDenyMode() {
            return CountDenyMode.Hardcore;
        }
    },
    StopTimeDeny{
        @Override
        public String getName() {
            return "静止時間制限: " + getCount() + "秒";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "静止時間制限";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.StopTimeDeny;
        }

        @Override
        public int getPriority() {
            return 2;
        }

    },
    NoNG{
        @Override
        public String getName() {
            return "制約なし！(ﾗｯｷｰ)";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "なし";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.None;
        }

        @Override
        public int getPriority() {
            return 2;
        }
    }
    ;

    private int count = 0;

    abstract public String getName();

    abstract public String getShortName();

    abstract public String getDescription();

    public enum NGMode{
        None, Deny, Only, BlockBreakDeny, FlyingDeny, LiquidDeny, EntityDeny, CountDeny, StopTimeDeny
    }

    public enum CountDenyMode{
        None, Normal, Hard, Hardcore
    }

    abstract public NGMode getNGMode();

    public CountDenyMode getCountDenyMode(){
        return CountDenyMode.None;
    }

    abstract public int getPriority();

    public List<BlockData> getDenyMaterial(){
        return new ArrayList<>();
    }

    public List<BlockData> getOnlyMaterial(){
        return new ArrayList<>();
    }

    /**
     * Bonus倍率を返す。
     * @return
     */
    public double getBonus(){
        return 1;
    }

    /**
     * NGをした時のペナルティーを返す。
     * @return
     */
    public int getPenalty(){
        return 200;
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

    public List<BlockData> getList(){
        if( getNGMode().equals(NGMode.Only) ){
            return getOnlyMaterial();
        } else {
            return getDenyMaterial();
        }
    }

    public float getPriorityPer(){
        float num = 0;
        int allPriority = 0;
        for( NGData ngData : NGData.values() ){
            allPriority = allPriority + ngData.getPriority();
        }
        return ((float)getPriority() / (float)allPriority);
    }

    public boolean contains(ItemStack item){
        BlockData blockData = new BlockData(item);
        for( BlockData bdl: getList() ){
            if( bdl.getMaterial().equals(blockData .getMaterial()) ){
                if( bdl.isNonDataValue() || bdl.getDataValue() == blockData .getDataValue() ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(Block block){
        BlockData blockData = new BlockData(block);
        for( BlockData bdl: getList() ){
            if( bdl.getMaterial().equals(blockData .getMaterial()) ){
                if( bdl.isNonDataValue() || bdl.getDataValue() == blockData .getDataValue() ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(BlockData bd){
        for( BlockData bdl: getList() ){
            if( bdl.getMaterial().equals(bd.getMaterial()) ){
                if( bdl.isNonDataValue() || bdl.getDataValue() == bd.getDataValue() ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canUse( ItemStack item ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(item);
        } else {
            return !contains(item);
        }
    }

    public boolean canUse( Block block ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(block);
        } else {
            return !contains(block);
        }
    }

    public boolean canUse( BlockData bd ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(bd);
        } else {
            return !contains(bd);
        }
    }

    public static HashMap<NGData, Float> getPriorityList(){
        int allPriority = 0;
        for( NGData item : NGData.values() ){
            allPriority = allPriority + item.getPriority();
        }
        HashMap<NGData, Float> perList = new HashMap<NGData, Float>();
        for( NGData item : NGData.values() ){
            perList.put(item,  ((float)item.getPriority() / (float)allPriority));
        }
        return perList;
    }

}
