package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.ChainingTowerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ChainingTowerBlock extends TowerBlock{

    public ChainingTowerBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ChainingTowerBlockEntity();
    }
}
