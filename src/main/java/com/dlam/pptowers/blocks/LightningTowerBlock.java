package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.LightningTowerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class LightningTowerBlock extends TowerBlock {
    public LightningTowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LightningTowerBlockEntity();
    }
}
