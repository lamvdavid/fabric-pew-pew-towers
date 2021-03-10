package com.dlam.pptowers.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/*
    Custom projectile that creates another projectile after hitting an entity
    Based on
 */
public class ChainingProjectileEntity extends ArrowEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public double targetX;
    public double targetY;
    public double targetZ;
    public Box range = null;
    public int numOfChains = 3;

    public ChainingProjectileEntity(World world, double x, double y, double z, int numOfChains) {
        super(world, x, y, z);
        this.numOfChains = numOfChains;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        range = new Box(this.getPos().getX() - X_RANGE, this.getPos().getY() - Y_RANGE, this.getPos().getZ() - Z_RANGE, this.getPos().getX() + X_RANGE, this.getPos().getY() + Y_RANGE, this.getPos().getZ() + Z_RANGE);
        Entity sourceEntity = entityHitResult.getEntity();
        HostileEntity target = getClosestHostileEntity(sourceEntity);
        if(target != null && numOfChains > 0) {

            ChainingProjectileEntity chainedArrow = new ChainingProjectileEntity(world, sourceEntity.getX() + targetX, sourceEntity.getEyeY() + targetY, sourceEntity.getZ() + targetZ, numOfChains - 1);
            double x = target.getX() - chainedArrow.getX();
            double y = target.getBodyY(0.33333333D) - chainedArrow.getY();
            double z = target.getZ() - chainedArrow.getZ();
            double d = (double) MathHelper.sqrt(x * x + z * z);
            chainedArrow.setVelocity(x, y + d * 0.20000000298023224D, z, 1.6F, 0);
            world.spawnEntity(chainedArrow);
        }

    }

    //Offsets the position of the entity for calculations
    public double[] getTargetDirection(Entity source, Entity target) {
        double[] xyz = new double[3];
        double x = target.getX() - source.getX();
        double y = target.getY() - source.getY();
        double z = target.getZ() - source.getZ();

        xyz[1] = 0.5;

        if(y >= 3) {
            xyz[0] = 0.5;
            xyz[1] = 1.05;
            xyz[2] = 0.5;
        } else if(y <= -3) {
            xyz[0] = 0.5;
            xyz[1] = -0.1;
            xyz[2] = 0.5;
        } else {
            if(Math.abs(x) > Math.abs(z)) {
                xyz[0] = x > 0 ? 1.05 : -0.1;
                xyz[2] = 0.5;
            } else {
                xyz[2] = z > 0 ? 1.05 : -0.1;
                xyz[0] = 0.5;
            }
        }

        return xyz;
    }

    //Offsets the direction of the entity for spawning projectiles
    public void setTargetDirection(double x, double y, double z) {
        targetX = x;
        targetY = y;
        targetZ = z;
    }

    //Returns list of HostileEntities in range
    public List<HostileEntity> getHostileEntities() {
        return this.getEntityWorld().getEntitiesByClass(HostileEntity.class,range,null);
    }

    //Gets the closest hostile mob in sight that isn't the source entity from the position from the source entity
    public HostileEntity getClosestHostileEntity(Entity sourceEntity) {
        List<HostileEntity> hostileEntities = getHostileEntities();
        hostileEntities.remove(sourceEntity);
        hostileEntities = hostileEntities.stream().filter(e -> e.hurtTime == 0).collect(Collectors.toList());

        HostileEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(HostileEntity e : hostileEntities) {
            testDistance = e.squaredDistanceTo(sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(sourceEntity, e);
                if(e.canSee(sourceEntity)) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }
}
