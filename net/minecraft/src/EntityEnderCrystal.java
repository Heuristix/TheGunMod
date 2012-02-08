package net.minecraft.src;

import java.util.Random;

public class EntityEnderCrystal extends Entity
{
    public int field_41023_a;
    public int field_41022_b;

    public EntityEnderCrystal(World world)
    {
        super(world);
        field_41023_a = 0;
        preventEntitySpawning = true;
        setSize(2.0F, 2.0F);
        yOffset = height / 2.0F;
        field_41022_b = 5;
        field_41023_a = rand.nextInt(0x186a0);
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        dataWatcher.addObject(8, Integer.valueOf(field_41022_b));
    }

    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        field_41023_a++;
        dataWatcher.updateObject(8, Integer.valueOf(field_41022_b));
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if (worldObj.getBlockId(i, j, k) != Block.fire.blockID)
        {
            worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (!isDead && !worldObj.isRemote)
        {
            field_41022_b = 0;
            if (field_41022_b <= 0)
            {
                if (!worldObj.isRemote)
                {
                    setEntityDead();
                    worldObj.createExplosion(null, posX, posY, posZ, 6F);
                }
                else
                {
                    setEntityDead();
                }
            }
        }
        return true;
    }
}
