package com.dlam.pptowers.registry;


import com.dlam.pptowers.PPTowers;
import com.dlam.pptowers.entities.ArrowTowerBlockEntity;
import com.dlam.pptowers.entities.FireballTowerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntities {
    public static BlockEntityType<ArrowTowerBlockEntity> ARROW_TOWER_BLOCK_ENTITY;
    public static BlockEntityType<FireballTowerBlockEntity> FIREBALL_TOWER_BLOCK_ENTITY;
    public static void registerBlockEntities() {
        ARROW_TOWER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PPTowers.MOD_ID + ":arrow_tower_block_entity", BlockEntityType.Builder.create(ArrowTowerBlockEntity::new, Blocks.ARROW_TOWER_BLOCK).build(null));
        FIREBALL_TOWER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PPTowers.MOD_ID + ":fireball_tower_block_entity", BlockEntityType.Builder.create(FireballTowerBlockEntity::new, Blocks.FIREBALL_TOWER_BLOCK).build(null));

    }
}
