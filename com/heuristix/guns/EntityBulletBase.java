package com.heuristix.guns;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 12/30/11
 * Time: 11:49 AM
 */
public class EntityBulletBase extends EntityBullet {

    public EntityBulletBase(World world) {
        super(world);
    }

    public EntityBulletBase(World world, EntityLiving entityliving) {
        super(world, entityliving);
    }

    @Override
    public float getSpread() {
        return 0;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public float getEffectiveRange() {
        return 0;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int getDamage() {
        return 0;    //To change body of overridden methods use File | Settings | File Templates.
    }
}