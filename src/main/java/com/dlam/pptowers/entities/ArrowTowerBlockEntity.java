package com.dlam.pptowers.entities;

import com.dlam.pptowers.registry.BlockEntities;

public class ArrowTowerBlockEntity extends TowerBlockEntity{
    //Hard coded range. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 4.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;

    public ArrowTowerBlockEntity() {
        super(BlockEntities.ARROW_TOWER_BLOCK_ENTITY, X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }
}
