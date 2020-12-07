package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;

import java.util.List;

public abstract class TowerBlockEntity extends BlockEntity implements Tickable {
    public Box range;
    protected boolean isRangeSet = false;
    public List<Entity> hostileEntities;
    public HostileEntity target = null;
    public double xRange;
    public double yRange;
    public double zRange;
    public int fireRate;
    public int fireRateCounter;
    public int checkTargetCounter = 0;


    public TowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type);
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
        this.fireRate = fireRate;
        this.fireRateCounter = fireRate;
    }

    public void shoot() {
        if(fireRateCounter == fireRate) {
            Position position = new PositionImpl(pos.getX(),pos.getY(),pos.getZ());
            ProjectileEntity proj = this.createProjectile(world, position, Items.ARROW);
            proj.setVelocity(0,5,10,1,0);
            world.spawnEntity(proj);
            fireRateCounter = 0;
        } else {
            fireRateCounter++;
        }
    }

    protected ProjectileEntity createProjectile(World world, Position position, Item item) {
        ArrowEntity arrow = new ArrowEntity(world, position.getX() + 0.5, position.getY() + 1, position.getZ() + 0.5);
        return arrow;
    }

    @Override
    public void tick() {
        if(!this.getWorld().isClient){
            if(target == null) {
                if(checkTargetCounter == 20) {
                    target = getClosestHostileEntity();
                    checkTargetCounter = 0;
                } else {
                    checkTargetCounter++;
                }

            }
            if(fireRateCounter == fireRate) {
                shoot();
                fireRateCounter = 0;
            } else {
                fireRateCounter++;
            }
        }
    }

    public List<Entity> getHostileEntities() {
        if(!isRangeSet) {
            this.range = new Box(this.getPos().getX() - xRange, this.getPos().getY() - yRange, this.getPos().getZ() - zRange, this.getPos().getX() + xRange, this.getPos().getY() + yRange, this.getPos().getZ() + zRange);
            isRangeSet = true;
        }
        List<Entity> list = this.getWorld().getEntitiesByClass(HostileEntity.class,range,null);
        return list;
    }

    public HostileEntity getClosestHostileEntity() {
        List<Entity> hostileEntities = getHostileEntities();
        HostileEntity target = null;
        HostileEntity test = null;
        double targetDistance = 10000.0;
        double testDistance;

        for(Entity e : hostileEntities) {
            testDistance = e.squaredDistanceTo(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
            if(testDistance < targetDistance) {
                target = (HostileEntity) e;
                targetDistance = testDistance;
            }
        }

        return target;
    }
}
