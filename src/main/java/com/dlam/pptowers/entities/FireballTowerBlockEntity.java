package com.dlam.pptowers.entities;

import com.dlam.pptowers.registry.BlockEntities;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class FireballTowerBlockEntity extends LineTowerBlockEntity{
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int STRENGTH = 2;

    public FireballTowerBlockEntity() {
        super(BlockEntities.FIREBALL_TOWER_BLOCK_ENTITY, X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position) {
        AoEProjectileEntity fireball = new AoEProjectileEntity(world, position.getX(), position.getY(), position.getZ(), target.getX() - position.getX(), target.getY() - position.getY(), target.getZ() - position.getZ());
        fireball.explosionPower = STRENGTH;
        return fireball;
    }
}
