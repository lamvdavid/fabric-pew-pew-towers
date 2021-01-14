package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.FireballTowerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class FireballTowerBlock extends TowerBlock{
    public FireballTowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new FireballTowerBlockEntity();
    }
}
