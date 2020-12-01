package com.dlam.pptowers.registry;

import com.dlam.pptowers.PPTowers;
import com.dlam.pptowers.items.ArrowTowerItem;
import com.dlam.pptowers.items.TowerItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final TowerItem TOWER_ITEM = new TowerItem(new FabricItemSettings().group(ItemGroup.MISC));
    public static final ArrowTowerItem ARROW_TOWER_ITEM = new ArrowTowerItem(new FabricItemSettings().group(ItemGroup.MISC));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "tower_item"), TOWER_ITEM);
        Registry.register(Registry.ITEM, new Identifier(PPTowers.MOD_ID, "arrow_tower_item"), ARROW_TOWER_ITEM);
    }
}
