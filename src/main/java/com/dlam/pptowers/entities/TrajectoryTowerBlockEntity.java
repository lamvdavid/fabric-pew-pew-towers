package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;

/*
    Used for towers that fire projectiles that are affected by gravity
 */
public abstract class TrajectoryTowerBlockEntity extends ProjectileTowerBlockEntity {
    public TrajectoryTowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }

    @Override
    protected void shoot() {
        Position position = new PositionImpl(pos.getX() + xFace, pos.getY() + yFace, pos.getZ() + zFace);
        ProjectileEntity proj = this.createProjectile(world, position);
        double x = target.getX() - proj.getX();
        double y = target.getBodyY(0.33333333D) - proj.getY();
        double z = target.getZ() - proj.getZ();
        double d = (double) MathHelper.sqrt(x * x + z * z);
        proj.setVelocity(x, y + d * 0.1D, z, 1.6F, 0);
        world.spawnEntity(proj);
    }

}
