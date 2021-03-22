package com.dlam.pptowers.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/*
    Custom projectile that creates another projectile after hitting an entity
    Uses ArrowEntity as base
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
        MobEntity target = getClosestHostileEntity(sourceEntity);
        if(target != null && numOfChains > 0) {

            ChainingProjectileEntity chainedArrow = new ChainingProjectileEntity(world, sourceEntity.getX() + targetX, sourceEntity.getEyeY() + targetY, sourceEntity.getZ() + targetZ, numOfChains - 1);
            double x = target.getX() - chainedArrow.getX();
            double y = target.getBodyY(0.33333333D) - chainedArrow.getY();
            double z = target.getZ() - chainedArrow.getZ();
            double d = (double) MathHelper.sqrt(x * x + z * z);
            chainedArrow.setVelocity(x, y + d * 0.1D, z, 1.6F, 0);
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
    public List<MobEntity> getHostileEntities() {
        List<Entity> hostiles = this.getEntityWorld().getEntitiesByClass(HostileEntity.class,range,null);
        hostiles.addAll(this.getEntityWorld().getEntitiesByClass(MobEntity.class,range,null));
        return this.getEntityWorld().getEntitiesByClass(MobEntity.class,range,null);
    }

    //Gets the closest hostile mob in sight that isn't the source entity from the position from the source entity
    public MobEntity getClosestHostileEntity(Entity sourceEntity) {
        List<MobEntity> hostileEntities = getHostileEntities();
        hostileEntities.remove(sourceEntity);
        hostileEntities = hostileEntities.stream().filter(e -> e.hurtTime == 0).collect(Collectors.toList());

        MobEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(MobEntity e : hostileEntities) {
            testDistance = e.squaredDistanceTo(sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(sourceEntity, e);
                if(checkSightLine(sourceEntity,offset[0], offset[1], offset[2])) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }

    //Checks if the tower block can see the mob
    public boolean checkSightLine(Entity entity, double x, double y, double z) {
        Vec3d vec3d = new Vec3d(this.getX() + x, this.getY() + y, this.getZ() + z);
        Vec3d vec3d2 = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
        HitResult.Type test = this.world.raycast(new RaycastContext(vec3d2, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity)).getType();
        return test == HitResult.Type.MISS;
    }
}
