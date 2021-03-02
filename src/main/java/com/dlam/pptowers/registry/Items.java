package com.dlam.pptowers.registry;

import com.dlam.pptowers.PPTowers;
import com.dlam.pptowers.items.ArrowTowerItem;
import com.dlam.pptowers.items.FireballTowerItem;
import com.dlam.pptowers.items.LightningTowerItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final ItemGroup TOWER_GROUP = FabricItemGroupBuilder.build(
            new Identifier("pptowers", "general"),
            (() -> new ItemStack(Blocks.ARROW_TOWER_BLOCK)));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "arrow_tower_item"), new BlockItem(Blocks.ARROW_TOWER_BLOCK, new Item.Settings().group(TOWER_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "fireball_tower_item"), new BlockItem(Blocks.FIREBALL_TOWER_BLOCK, new Item.Settings().group(TOWER_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "lightning_tower_item"), new BlockItem(Blocks.LIGHTNING_TOWER_BLOCK, new Item.Settings().group(TOWER_GROUP)));
    }
}
