// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Item, EntityPlayer, EntityPainting, World, 
//            ItemStack

public class ItemPainting extends Item
{

    public ItemPainting(int i)
    {
        super(i);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            return false;
        }
        if(l == 1)
        {
            return false;
        }
        byte byte0 = 0;
        if(l == 4)
        {
            byte0 = 1;
        }
        if(l == 3)
        {
            byte0 = 2;
        }
        if(l == 5)
        {
            byte0 = 3;
        }
        if(!entityplayer.canPlayerEdit(i, j, k))
        {
            return false;
        }
        EntityPainting entitypainting = new EntityPainting(world, i, j, k, byte0);
        if(entitypainting.onValidSurface())
        {
            if(!world.singleplayerWorld)
            {
                world.spawnEntityInWorld(entitypainting);
            }
            itemstack.stackSize--;
        }
        return true;
    }
}
