package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;

public abstract class LineTowerBlockEntity extends ProjectileTowerBlockEntity {

    public LineTowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }

    @Override
    protected void shoot() {
            Position position = new PositionImpl(pos.getX() + xFace,pos.getY() + yFace,pos.getZ() + zFace);
            world.spawnEntity(this.createProjectile(world, position));
    }

    protected abstract ProjectileEntity createProjectile(World world, Position position);
}
