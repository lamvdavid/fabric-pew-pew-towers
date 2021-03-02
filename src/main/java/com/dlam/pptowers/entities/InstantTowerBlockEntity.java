package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntityType;

/*  Used for non-projectile entities
 */
public abstract class InstantTowerBlockEntity extends TowerBlockEntity {
    public InstantTowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }
}
