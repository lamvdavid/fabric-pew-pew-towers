package com.dlam.pptowers;

import com.dlam.pptowers.items.ArrowTowerItem;
import com.dlam.pptowers.items.TowerItem;
import com.dlam.pptowers.registry.Blocks;
import com.dlam.pptowers.registry.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PPTowers implements ModInitializer {
    public static final String MOD_ID = "pptowers";

    @Override
    public void onInitialize() {
        Items.registerItems();
        Blocks.registerBlocks();
    }
}
