package com.dlam.pptowers.entities;

import com.dlam.pptowers.registry.BlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class LightningTowerBlockEntity extends InstantTowerBlockEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;

    public LightningTowerBlockEntity() {
        super(BlockEntities.LIGHTNING_TOWER_BLOCK_ENTITY, X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected void shoot() {
        if(fireRateCounter == fireRate) {
            LightningEntity lightning = (LightningEntity) EntityType.LIGHTNING_BOLT.create(this.world);
            lightning.refreshPositionAfterTeleport(target.getPos());
            this.world.spawnEntity(lightning);
            fireRateCounter = 0;
        } else {
            fireRateCounter++;
        }
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position) {
        return null;
    }
}
