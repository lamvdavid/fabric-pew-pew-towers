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

    protected abstract ProjectileEntity createProjectile(World world, Position position);

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

        xyz[1] = y >= -1 ? 0.5 : 0;

        if(Math.abs(x) > Math.abs(z)) {
            xyz[0] = x > 0 ? 1.05 : -0.1;
            xyz[2] = 0.5;
        } else {
            xyz[2] = z > 0 ? 1.05 : -0.1;
            xyz[0] = 0.5;
        }

        return xyz;
    }

    //Offsets the direction of the block for spawning projectiles
    public void setTargetDirection(Entity e) {
        double x = e.getX() - pos.getX();
        double y = e.getY() - pos.getY();
        double z = e.getZ() - pos.getZ();
        xFace = 0;
        yFace = y >= -1 ? 0.5 : 0;
        zFace = 0;
        if(Math.abs(x) > Math.abs(z)) {
            xFace = x > 0 ? 1.05 : -0.1;
            zFace = 0.5;
        } else {
            zFace = z > 0 ? 1.05 : -0.1;
            xFace = 0.5;
        }
    }

    //Retrieves all hostiles entities within range of the block
    public List<Entity> getHostileEntities() {
        if(!isRangeSet) {
            this.range = new Box(this.getPos().getX() - xRange, this.getPos().getY() - yRange, this.getPos().getZ() - zRange, this.getPos().getX() + xRange, this.getPos().getY() + yRange, this.getPos().getZ() + zRange);
            isRangeSet = true;
        }
        List<Entity> list = this.getWorld().getEntitiesByClass(HostileEntity.class,range,null);

        return list;
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
                    setTargetDirection(e);
                    target = (HostileEntity) e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }
}
