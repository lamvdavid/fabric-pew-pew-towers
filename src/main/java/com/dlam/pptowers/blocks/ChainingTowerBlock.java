package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.ChainingTowerBlockEntity;
import com.dlam.pptowers.entities.TrajectoryTowerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Position;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
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
