package net.minecraft.src;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean field_48196_i;
    int field_48195_j;

    public EntityAIOpenDoor(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving);
        field_48192_a = par1EntityLiving;
        field_48196_i = par2;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48196_i && field_48195_j > 0 && super.continueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48195_j = 20;
        field_48189_e.onPoweredBlockChange(field_48192_a.worldObj, field_48190_b, field_48191_c, field_48188_d, true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        if (field_48196_i)
        {
            field_48189_e.onPoweredBlockChange(field_48192_a.worldObj, field_48190_b, field_48191_c, field_48188_d, false);
        }
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_48195_j--;
        super.updateTask();
    }
}
