package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;

public abstract class ProjectileTowerBlockEntity extends TowerBlockEntity {
    public ProjectileTowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }

    @Override
    protected abstract void shoot();
    protected abstract ProjectileEntity createProjectile(World world, Position position);
}
