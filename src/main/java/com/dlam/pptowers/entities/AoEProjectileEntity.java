package com.dlam.pptowers.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

/* Copied from FireballEntity class
   Removed the block destruction and fire creation
 */

public class AoEProjectileEntity extends AbstractFireballEntity {
    public int explosionPower = 1;

    public AoEProjectileEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public AoEProjectileEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(EntityType.FIREBALL, x, y, z, velocityX, velocityY, velocityZ, world);
    }

    public AoEProjectileEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityType.FIREBALL, owner, velocityX, velocityY, velocityZ, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, Explosion.DestructionType.NONE);
            this.remove();
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.damage(DamageSource.fireball(this, entity2), 6.0F);
            if (entity2 instanceof LivingEntity) {
                this.dealDamage((LivingEntity)entity2, entity);
            }

        }
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("ExplosionPower", this.explosionPower);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("ExplosionPower", 99)) {
            this.explosionPower = tag.getInt("ExplosionPower");
        }

    }
}
