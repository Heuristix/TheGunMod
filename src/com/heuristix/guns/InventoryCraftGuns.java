package com.heuristix.guns;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 8/19/12
 * Time: 8:39 PM
 */
public class InventoryCraftGuns extends InventoryBasic {

    public InventoryCraftGuns(String name, boolean localized, int size) {
        super(name, localized, size);
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        return getStackInSlot(index);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        ItemStack stack = super.getStackInSlot(index);
        if (stack != null) {
            return stack.copy();
        }
        return null;
    }
}
