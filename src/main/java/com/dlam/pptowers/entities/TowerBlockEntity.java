package com.dlam.pptowers.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

/*Base Class for creating TowerBlockEntities
*
* Contains logic for finding HostileMob targets and shooting a projectile
 */
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
    public double xFace;
    public double yFace;
    public double zFace;

    public TowerBlockEntity(BlockEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type);
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
        this.fireRate = fireRate;
        this.fireRateCounter = fireRate;
    }

    //Shoot a projectile at the target mob
    protected abstract void shoot();

    //Checks if the tower block can see the mob
    public boolean checkSightLine(Entity entity, double x, double y, double z) {
        Vec3d vec3d = new Vec3d(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        Vec3d vec3d2 = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
        HitResult.Type test = this.world.raycast(new RaycastContext(vec3d2, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity)).getType();
        return test == HitResult.Type.MISS;
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

    //Offsets the position of the block for calculations
    public double[] getTargetDirection(Entity e) {
        double[] xyz = new double[3];
        double x = e.getX() - pos.getX();
        double y = e.getY() - pos.getY();
        double z = e.getZ() - pos.getZ();

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

    //Offsets the direction of the block for spawning projectiles
    public void setTargetDirection(double x, double y, double z) {
        xFace = x;
        yFace = y;
        zFace = z;
    }

    //Retrieves all hostiles entities within range of the block
    public List<Entity> getHostileEntities() {
        if(!isRangeSet) {
            this.range = new Box(this.getPos().getX() - xRange, this.getPos().getY() - yRange, this.getPos().getZ() - zRange, this.getPos().getX() + xRange, this.getPos().getY() + yRange, this.getPos().getZ() + zRange);
            isRangeSet = true;
        }
        return this.getWorld().getEntitiesByClass(HostileEntity.class,range,null);

    }

    //Gets the closest hostile mob in sight
    public HostileEntity getClosestHostileEntity() {
        List<Entity> hostileEntities = getHostileEntities();
        HostileEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(Entity e : hostileEntities) {
            testDistance = e.squaredDistanceTo(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(e);
                if(checkSightLine(e, offset[0], offset[1], offset[2])) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = (HostileEntity) e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }
}
