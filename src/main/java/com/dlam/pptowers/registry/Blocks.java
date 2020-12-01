package com.dlam.pptowers.registry;

import com.dlam.pptowers.blocks.ArrowTowerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
    public static final ArrowTowerBlock ARROW_TOWER_BLOCK = new ArrowTowerBlock(FabricBlockSettings.of(Material.STONE));

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier("pptowers", "arrow_tower_block"), ARROW_TOWER_BLOCK);
    }
}
