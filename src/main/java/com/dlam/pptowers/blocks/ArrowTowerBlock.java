package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.ArrowTowerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ArrowTowerBlock extends TowerBlock {
    public ArrowTowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ArrowTowerBlockEntity();
    }

}
