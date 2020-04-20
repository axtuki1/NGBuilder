package io.github.axtuki1.ngbuilder.system;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.util.Utility;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "文字通り、ブロックの破壊は禁止です。";
        }

        @Override
        public double getDefaultBonus() {
            return 1.5;
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
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "クリエイティブモード時の飛行を禁止します。切り替えた瞬間にNGになります。";
        }

        @Override
        public double getDefaultBonus() {
            return 1.8;
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
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "水と溶岩の設置が禁止です。";
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
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "ネザーとエンドに存在するブロックのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 6;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return Arrays.asList(
                    new BlockData(Material.NETHER_BRICK),
                    new BlockData(Material.NETHER_BRICK_SLAB),
                    new BlockData(Material.NETHER_BRICK_FENCE),
                    new BlockData(Material.NETHER_BRICK_STAIRS),
                    new BlockData(Material.NETHER_BRICK_WALL),
                    new BlockData(Material.RED_NETHER_BRICK_SLAB),
                    new BlockData(Material.RED_NETHER_BRICK_STAIRS),
                    new BlockData(Material.RED_NETHER_BRICK_WALL),
                    new BlockData(Material.NETHER_BRICK_STAIRS, true),
                    new BlockData(Material.NETHERRACK),
                    new BlockData(Material.END_ROD, true),
                    new BlockData(Material.SOUL_SAND),
                    new BlockData(Material.GLOWSTONE),
                    new BlockData(Material.NETHER_WART_BLOCK),
                    new BlockData(Material.NETHER_QUARTZ_ORE),
                    new BlockData(Material.QUARTZ_BLOCK, true),
                    new BlockData(Material.QUARTZ_STAIRS, true),
                    new BlockData(Material.END_CRYSTAL),
                    new BlockData(Material.MAGMA_BLOCK),
                    new BlockData(Material.PURPUR_BLOCK),
                    new BlockData(Material.PURPUR_PILLAR),
                    new BlockData(Material.PURPUR_SLAB, true),
                    new BlockData(Material.PURPUR_STAIRS, true),
                    new BlockData(Material.CHORUS_PLANT),
                    new BlockData(Material.CHORUS_FLOWER),
                    new BlockData(Material.OBSIDIAN),
                    new BlockData(Material.END_STONE),
                    new BlockData(Material.END_STONE_BRICK_SLAB),
                    new BlockData(Material.END_STONE_BRICK_STAIRS),
                    new BlockData(Material.END_STONE_BRICK_WALL),
                    new BlockData(Material.END_STONE_BRICKS),
                    new BlockData(Material.LAVA)
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "ネザーとエンドに存在するブロックの使用が禁止されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "エンティティの召喚が禁止されます。アイテムドロップもNGとしています。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.EntityDeny;
        }

        @Override
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "階段ブロックの使用が禁止されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getDefaultPriority() {
            return 5;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_STAIRS") ) out.add( new BlockData(m) );
            }
            return out;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "階段ブロックのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 2;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return StairsDeny.getDenyMaterial();
        }

        @Override
        public double getDefaultBonus() {
            return 1.5;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "鉱石とその原石の使用が禁止されます。掘る/精錬で鉱石がドロップするブロック、それを9個でクラフトする事でできるブロックが対象です。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Deny;
        }

        @Override
        public int getDefaultPriority() {
            return 6;
        }

        @Override
        public List<BlockData> getDenyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_ORE") ) out.add( new BlockData(m) );
            }
            out.addAll(Arrays.asList(
                    new BlockData(Material.IRON_BLOCK),
                    new BlockData(Material.GOLD_BLOCK),
                    new BlockData(Material.COAL_BLOCK),
                    new BlockData(Material.DIAMOND_BLOCK),
                    new BlockData(Material.EMERALD_BLOCK),
                    new BlockData(Material.REDSTONE_BLOCK),
                    new BlockData(Material.LAPIS_BLOCK),
                    new BlockData(Material.QUARTZ_BLOCK),
                    new BlockData(Material.QUARTZ_STAIRS),
                    new BlockData(Material.QUARTZ_SLAB)
            ));
            return out;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "鉱石とその原石のみ使用が許可されます。掘る/精錬で鉱石がドロップするブロック、それを9個でクラフトする事でできるブロックが対象です。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            return OreDeny.getDenyMaterial();
        }

        @Override
        public double getDefaultBonus() {
            return 1.5;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "カーペットのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 1;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_CARPET") ) out.add( new BlockData(m) );
            }
            return out;
        }

        @Override
        public double getDefaultBonus() {
            return 1.8;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "羊毛ブロックのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_WOOL") ) out.add( new BlockData(m) );
            }
            return out;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "ガラス/色ガラス/板ガラス/色板ガラスのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 2;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_GLASS") ) out.add( new BlockData(m) );
            }
            out.addAll(Arrays.asList(
                    new BlockData(Material.GLASS, true),
                    new BlockData(Material.GLASS_PANE, true)
            ));
            return out;
        }

        @Override
        public double getDefaultBonus() {
            return 1.8;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "ハーフブロックのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 4;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {
            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if( m.toString().contains("_SLAB") ) out.add( new BlockData(m) );
            }
            return out;
        }

        @Override
        public double getDefaultBonus() {
            return 1.5;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "原木、木材、木系ドア、木系フェンスといった木系のブロックのみ使用が許可されます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.Only;
        }

        @Override
        public int getDefaultPriority() {
            return 5;
        }

        @Override
        public List<BlockData> getOnlyMaterial() {

            List<BlockData> out = new ArrayList<>();
            for( Material m : Material.values() ){
                if(
                        m.toString().contains("_LEAVES") ||
                        m.toString().contains("_LOG") ||
                        m.toString().contains("_WOOD") ||
                        m.toString().contains("_PLANKS") ||
                        m.toString().contains("_PRESSURE_PLATE")
                ) {
                    out.add( new BlockData(m) );
                }
            }

            out.remove(new BlockData(Material.IRON_TRAPDOOR));
            out.remove(new BlockData(Material.STONE_PRESSURE_PLATE));
            out.remove(new BlockData(Material.LIGHT_WEIGHTED_PRESSURE_PLATE));
            out.remove(new BlockData(Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
            out.remove(new BlockData(Material.STONE_BUTTON));


            out.addAll(Arrays.asList(
                    new BlockData(Material.OAK_STAIRS),
                    new BlockData(Material.DARK_OAK_STAIRS, true),
                    new BlockData(Material.DARK_OAK_STAIRS),
                    new BlockData(Material.ACACIA_STAIRS, true),
                    new BlockData(Material.OAK_STAIRS),

                    new BlockData(Material.CHEST, true),
                    new BlockData(Material.TRAPPED_CHEST, true),

                    new BlockData(Material.OAK_DOOR, true),
                    new BlockData(Material.SPRUCE_DOOR, true),
                    new BlockData(Material.BIRCH_DOOR, true),
                    new BlockData(Material.JUNGLE_DOOR, true),
                    new BlockData(Material.ACACIA_DOOR, true),
                    new BlockData(Material.DARK_OAK_DOOR, true),

                    new BlockData(Material.OAK_SLAB, true),
                    new BlockData(Material.SPRUCE_SLAB, true),
                    new BlockData(Material.BIRCH_SLAB, true),
                    new BlockData(Material.JUNGLE_SLAB, true),
                    new BlockData(Material.ACACIA_SLAB, true),
                    new BlockData(Material.DARK_OAK_SLAB, true),

                    new BlockData(Material.STRIPPED_OAK_LOG, true),
                    new BlockData(Material.STRIPPED_OAK_WOOD, true),
                    new BlockData(Material.STRIPPED_SPRUCE_LOG, true),
                    new BlockData(Material.STRIPPED_SPRUCE_WOOD, true),
                    new BlockData(Material.STRIPPED_BIRCH_LOG, true),
                    new BlockData(Material.STRIPPED_BIRCH_WOOD, true),
                    new BlockData(Material.STRIPPED_JUNGLE_LOG, true),
                    new BlockData(Material.STRIPPED_JUNGLE_WOOD, true),
                    new BlockData(Material.STRIPPED_ACACIA_LOG, true),
                    new BlockData(Material.STRIPPED_ACACIA_WOOD, true),
                    new BlockData(Material.STRIPPED_DARK_OAK_LOG, true),
                    new BlockData(Material.STRIPPED_DARK_OAK_WOOD, true),

                    new BlockData(Material.OAK_TRAPDOOR, true),
                    new BlockData(Material.SPRUCE_TRAPDOOR, true),
                    new BlockData(Material.BIRCH_TRAPDOOR, true),
                    new BlockData(Material.JUNGLE_TRAPDOOR, true),
                    new BlockData(Material.ACACIA_TRAPDOOR, true),
                    new BlockData(Material.DARK_OAK_TRAPDOOR, true),

                    new BlockData(Material.OAK_FENCE, true),
                    new BlockData(Material.SPRUCE_FENCE, true),
                    new BlockData(Material.BIRCH_FENCE, true),
                    new BlockData(Material.JUNGLE_FENCE, true),
                    new BlockData(Material.ACACIA_FENCE, true),
                    new BlockData(Material.DARK_OAK_FENCE, true),

                    new BlockData(Material.OAK_FENCE_GATE, true),
                    new BlockData(Material.SPRUCE_FENCE_GATE, true),
                    new BlockData(Material.BIRCH_FENCE_GATE, true),
                    new BlockData(Material.JUNGLE_FENCE_GATE, true),
                    new BlockData(Material.ACACIA_FENCE_GATE, true),
                    new BlockData(Material.DARK_OAK_FENCE_GATE, true)
                    ));

            return out;


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
        public String getStartDescription() {
            return "液体とピストンの伸びているブロックを含みません。";
        }

        @Override
        public String getDescription() {
            return "ランダムで変化するブロック数の間で建築してください。ノーマルは液体とピストンの伸びている部分をカウントしません。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getDefaultPriority() {
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
        public String getStartDescription() {
            return "液体は含みませんが、ピストンの起動状態では2ブロックとしてカウントされます。";
        }

        @Override
        public String getDescription() {
            return "ランダムで変化するブロック数の間で建築してください。ハードでは液体はカウントしませんが、ピストンが起動している状態では2ブロックとしてカウントされます。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getDefaultPriority() {
            return 2;
        }

        @Override
        public double getDefaultBonus() {
            return 1.5;
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
        public String getStartDescription() {
            return "水源/水流を含むずべてのブロックをカウントします。主に水の扱いには注意！";
        }

        @Override
        public String getDescription() {
            return "ランダムで変化するブロック数の間で建築してください。ハードコアでは如何なるブロックもカウントします。液体の扱いには十分注意！！";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.CountDeny;
        }

        @Override
        public int getDefaultPriority() {
            return 1;
        }

        @Override
        public CountDenyMode getCountDenyMode() {
            return CountDenyMode.Hardcore;
        }

        @Override
        public double getDefaultBonus() {
            return 1.8;
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
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "ランダムで変化する秒数以上静止する事が禁じられます。ブロックを選ぶ際はたまに動いてあげましょう。";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.StopTimeDeny;
        }

        @Override
        public int getDefaultPriority() {
            return 3;
        }

    },
    NoNG{
        @Override
        public String getName() {
            return "制約なし！";
        }

        @Override
        public String getShortName() {
            return ChatColor.YELLOW + "なし";
        }

        @Override
        public String getStartDescription() {
            return null;
        }

        @Override
        public String getDescription() {
            return "制約なし！！うれしい！！！！";
        }

        @Override
        public NGMode getNGMode() {
            return NGMode.None;
        }

        @Override
        public int getDefaultPriority() {
            return 2;
        }
    }
    ;

    private int count = 0;

    abstract public String getName();

    abstract public String getShortName();

    abstract public String getStartDescription();

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

    abstract public int getDefaultPriority();

    public int getPriority(){
        if(GameConfig.NGDataPrioritys.getConfigurationSection().get(name()) == null ){
            return getDefaultPriority();
        }
        return GameConfig.NGDataPrioritys.getConfigurationSection().getInt(name());
    }

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
    public double getDefaultBonus(){
        return 1;
    }

    public double getBonus(){
        if(GameConfig.NGDataBonus.getConfigurationSection().get(name()) == null ){
            return getDefaultBonus();
        }
        return GameConfig.NGDataBonus.getConfigurationSection().getDouble(name());
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

    public boolean canUse( BlockData bd ){
        if( getNGMode().equals(NGMode.Only) ){
            return contains(bd);
        } else if( getNGMode().equals(NGMode.Deny) ){
            return !contains(bd);
        } else {
            return true;
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
