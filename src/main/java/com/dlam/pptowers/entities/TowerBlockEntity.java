package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public abstract class TowerBlockEntity extends BlockEntity implements Tickable {
    public Box range;
    protected boolean isRangeSet = false;
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
            ProjectileEntity proj = this.createProjectile(world, position);
            double x = target.getX() - proj.getX();
            double y = target.getBodyY(0.33333333D) - proj.getY();
            double z = target.getZ() - proj.getZ();
            double d = (double) MathHelper.sqrt(x * x + z * z);
            proj.setVelocity(x,y + d * 0.20000000298023224D,z,1.6F,0);
            world.spawnEntity(proj);
            fireRateCounter = 0;
        } else {
            fireRateCounter++;
        }
    }

    protected ProjectileEntity createProjectile(World world, Position position) {
        double x = target.getX() - position.getX();
        double y = target.getY() - position.getY();
        double z = target.getZ() - position.getZ();
        double xFace = 0;
        double yFace = y >= -1 ? 0.5 : 0;
        double zFace = 0;
        if(Math.abs(x) > Math.abs(z)) {
            xFace = x > 0 ? 1.05 : -0.1;
            zFace = 0.5;
        } else {
            zFace = z > 0 ? 1.05 : -0.1;
            xFace = 0.5;
        }
        ArrowEntity arrow = new ArrowEntity(world, (position.getX() + xFace), (position.getY() + yFace), (position.getZ() + zFace));
        return arrow;
    }

    @Override
    public void tick() {
        if(!this.getWorld().isClient){
                if(checkTargetCounter == 20) {
                    target = getClosestHostileEntity();
                    checkTargetCounter = 0;
                } else {
                    checkTargetCounter++;
                }

            }
            if(target != null) {
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
