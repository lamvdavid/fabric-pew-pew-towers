package com.dlam.pptowers.entities;

import com.dlam.pptowers.registry.BlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ChainingTowerBlockEntity extends TrajectoryTowerBlockEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int NUM_OF_CHAINS = 3;
    public ChainingTowerBlockEntity() {
        super(BlockEntities.CHAINING_TOWER_BLOCK_ENTITY, X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position) {
        ChainingProjectileEntity arrow = new ChainingProjectileEntity(world, position.getX(), position.getY(), position.getZ(), NUM_OF_CHAINS);
        return arrow;
    }
}
