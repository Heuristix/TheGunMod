// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            ItemBlock, Block, ItemStack

public class ItemColored extends ItemBlock
{

    private final Block field_35421_a;
    private String field_41041_b[];

    public ItemColored(int i, boolean flag)
    {
        super(i);
        field_35421_a = Block.blocksList[getBlockID()];
        if(flag)
        {
            setMaxDamage(0);
            setHasSubtypes(true);
        }
    }

    public int getMetadata(int i)
    {
        return i;
    }

    public ItemColored func_41040_a(String as[])
    {
        field_41041_b = as;
        return this;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        if(field_41041_b == null)
        {
            return super.getItemNameIS(itemstack);
        }
        int i = itemstack.getItemDamage();
        if(i >= 0 && i < field_41041_b.length)
        {
            return (new StringBuilder()).append(super.getItemNameIS(itemstack)).append(".").append(field_41041_b[i]).toString();
        } else
        {
            return super.getItemNameIS(itemstack);
        }
    }
}
