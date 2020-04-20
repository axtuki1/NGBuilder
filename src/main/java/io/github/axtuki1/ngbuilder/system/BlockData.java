package io.github.axtuki1.ngbuilder.system;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockData {

    private Material material;
    private int dataValue;
    private boolean nonDataValue;

    public BlockData(ItemStack item){
        material = item.getType();
        dataValue = item.getData().getData();
        nonDataValue = true;
    }

    public BlockData(Block block){
        material = block.getType();
        dataValue = block.getData();
        nonDataValue = true;
    }

    public BlockData( Material material, int dataValue ){
        this.material = material;
        this.dataValue = dataValue;
        nonDataValue = true;
    }

    public BlockData(Material material) {
        this.material = material;
        this.dataValue = 0;
        nonDataValue = true;
    }

    public BlockData(ItemStack item, boolean isNonDataValue){
        material = item.getType();
        dataValue = item.getData().getData();
        nonDataValue = isNonDataValue;
    }

    public BlockData(Block block, boolean isNonDataValue){
        material = block.getType();
        dataValue = block.getData();
        nonDataValue = isNonDataValue;
    }

    public BlockData( Material material, int dataValue , boolean isNonDataValue){
        this.material = material;
        this.dataValue = dataValue;
        nonDataValue = isNonDataValue;
    }

    public BlockData(Material material, boolean isNonDataValue) {
        this.material = material;
        this.dataValue = 0;
        nonDataValue = isNonDataValue;
    }

    public int getDataValue() {
        return dataValue;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isNonDataValue() {
        return nonDataValue;
    }
}
