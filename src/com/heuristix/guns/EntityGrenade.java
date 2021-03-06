package com.heuristix.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.heuristix.EntityProjectile;
import com.heuristix.guns.helper.MathHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 10/23/11
 * Time: 6:10 PM
 */
public class EntityGrenade extends EntityProjectile {

    public EntityGrenade(World world) {
        super(world);
    }

    public EntityGrenade(World world, EntityLiving owner) {
        super(world, owner);
    }

    public EntityGrenade(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (isEntityAlive()) {
            worldObj.spawnParticle("largesmoke", posX + MathHelper.randomFloat(-1, 1), posY + MathHelper.randomFloat(-1, 1), posZ + MathHelper.randomFloat(-1, 1), 0, 0, 0);
        }
    }

    @Override
    public boolean onEntityHit(Entity hit) {
        if(super.onEntityHit(hit)) {
            return onBlockHit(null);
        }
        return false;
    }

    @Override
    public boolean onBlockHit(MovingObjectPosition position) {
        worldObj.createExplosion(this, posX, posY, posZ, getDamage(), true);
        return true;
    }

    @Override
    public float getSpeed() {
        return 1.0f;
    }

    @Override
    public int getMaxGroundTicks() {
        return 0;
    }

    @Override
    public String getHitSound() {
        return "guns.hit";
    }

    @Override
    public String getMoveSound() {
        return "guns.move";
    }

    @Override
    public float getEffectiveRange() {
        return 500;
    }

    @Override
    public float getMass() {
        return 0.01f;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getSpread() {
        return 0;
    }

    public int getDamage() {
        return 5;
    }


}
