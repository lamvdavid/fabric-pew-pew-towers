package com.dlam.pptowers.entities;

import com.dlam.pptowers.registry.BlockEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;

import java.util.List;

public class LightningTowerBlockEntity extends InstantTowerBlockEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int NUM_OF_ATTACKS = 5;

    public List<Entity> hostiles;

    public LightningTowerBlockEntity() {
        super(BlockEntities.LIGHTNING_TOWER_BLOCK_ENTITY, X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected void shoot() {
        hostiles = getHostileEntities();
        for (int i = 0; i < NUM_OF_ATTACKS; i++) {
            int targetIndex = (int) (Math.random() * hostiles.size());
            LightningEntity lightning = (LightningEntity) EntityType.LIGHTNING_BOLT.create(this.world);
            lightning.refreshPositionAfterTeleport(hostiles.get(targetIndex).getPos());
            this.world.spawnEntity(lightning);
        }
    }
}
