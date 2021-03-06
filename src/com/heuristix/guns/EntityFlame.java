package com.heuristix.guns;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.heuristix.guns.helper.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 12/24/11
 * Time: 12:19 PM
 */
public class EntityFlame extends EntityIncendiaryBullet {

    public EntityFlame(World world) {
        super(world);
        this.width = 0.5f;
        this.height = 0.5f;
    }

    public EntityFlame(World world, EntityLiving entityliving) {
        super(world, entityliving);
        this.width = 0.5f;
        this.height = 0.5f;
    }

    public EntityFlame(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.width = 0.5f;
        this.height = 0.5f;
    }

    @Override
    public boolean onEntityHit(Entity hit) {
        if(super.onEntityHit(hit)) {
            return onBlockHit(new MovingObjectPosition((int) Math.round(hit.posX), (int) Math.round(hit.posY), (int) Math.round(hit.posZ), 6, Vec3.createVectorHelper(0, 0, 0)));
        }
        return false;
    }

    @Override
    public boolean onBlockHit(MovingObjectPosition position) {
        int x = position.blockX, y = position.blockY, z = position.blockZ;
        switch (position.sideHit) {
            case 0:
                y--;
                break;
            case 1:
                y++;
                break;
            case 2:
                z--;
                break;
            case 3:
                z++;
                break;
            case 4:
                x--;
                break;
            case 5:
                x++;
                break;
            default:
                break;
        }
        if (worldObj.getBlockId(x, y, z) == 0) {
            worldObj.playSoundEffect(posX, posY, posZ, "fire.ignite", 1.0f, MathHelper.nextFloat() * 0.25f + 0.8f);
            worldObj.setBlock(x, y, z, Block.fire.blockID);
            return true;
        }
        return false;
    }
    
    

    @Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire() {
		return !isWithinDistanceOfOwner(2);
	}

	public float getSpeed() {
        return 1;
    }

    @Override
    public int getMaxGroundTicks() {
        return 0;
    }

    public float getMass() {
        return 0.1f;
    }

    @Override
    public float getEffectiveRange() {
        return 0;
    }

    @Override
    public float getSpread() {
        return 0;
    }

    @Override
    public int getDamage() {
        return 0;
    }
}
