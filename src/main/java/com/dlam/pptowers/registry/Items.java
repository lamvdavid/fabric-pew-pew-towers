package com.dlam.pptowers.registry;

import com.dlam.pptowers.PPTowers;
import com.dlam.pptowers.items.ArrowTowerItem;
import com.dlam.pptowers.items.TowerItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final ItemGroup TOWER_GROUP = FabricItemGroupBuilder.build(
            new Identifier("pptowers", "general"),
            () -> new ItemStack(Items.ARROW_TOWER_ITEM));
    public static final ArrowTowerItem ARROW_TOWER_ITEM = new ArrowTowerItem(new FabricItemSettings().group(TOWER_GROUP));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "arrow_tower_item"), ARROW_TOWER_ITEM);
    }
}
