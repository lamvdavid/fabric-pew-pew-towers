package com.dlam.pptowers.registry;

import com.dlam.pptowers.PPTowers;
import com.dlam.pptowers.blocks.ArrowTowerBlock;
import com.dlam.pptowers.blocks.ChainingTowerBlock;
import com.dlam.pptowers.blocks.FireballTowerBlock;
import com.dlam.pptowers.blocks.LightningTowerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
    public static final ArrowTowerBlock ARROW_TOWER_BLOCK = new ArrowTowerBlock(FabricBlockSettings.of(Material.STONE));
    public static final FireballTowerBlock FIREBALL_TOWER_BLOCK = new FireballTowerBlock(FabricBlockSettings.of(Material.STONE));
    public static final LightningTowerBlock LIGHTNING_TOWER_BLOCK = new LightningTowerBlock(FabricBlockSettings.of(Material.STONE));
    public static final ChainingTowerBlock CHAINING_TOWER_BLOCK = new ChainingTowerBlock(FabricBlockSettings.of(Material.STONE));

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(PPTowers.MOD_ID, "arrow_tower_block"), ARROW_TOWER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(PPTowers.MOD_ID, "fireball_tower_block"), FIREBALL_TOWER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(PPTowers.MOD_ID, "lightning_tower_block"), LIGHTNING_TOWER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(PPTowers.MOD_ID, "chaining_tower_block"), CHAINING_TOWER_BLOCK);
    }
}
