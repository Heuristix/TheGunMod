package net.minecraft.src;

public abstract class EntityFlying extends EntityLiving
{
    public EntityFlying(World par1World)
    {
        super(par1World);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float f)
    {
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        if (isInWater())
        {
            moveFlying(par1, par2, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.8D;
            motionY *= 0.8D;
            motionZ *= 0.8D;
        }
        else if (handleLavaMovement())
        {
            moveFlying(par1, par2, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        }
        else
        {
            float f = 0.91F;

            if (onGround)
            {
                f = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));

                if (i > 0)
                {
                    f = Block.blocksList[i].slipperiness * 0.91F;
                }
            }

            float f1 = 0.1627714F / (f * f * f);
            moveFlying(par1, par2, onGround ? 0.1F * f1 : 0.02F);
            f = 0.91F;

            if (onGround)
            {
                f = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));

                if (j > 0)
                {
                    f = Block.blocksList[j].slipperiness * 0.91F;
                }
            }

            moveEntity(motionX, motionY, motionZ);
            motionX *= f;
            motionY *= f;
            motionZ *= f;
        }

        field_9142_bc = field_9141_bd;
        double d = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1) * 4F;

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        field_9141_bd += (f2 - field_9141_bd) * 0.4F;
        field_386_ba += field_9141_bd;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return false;
    }
}
