package com.heuristix;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureStitched;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.heuristix.guns.ItemCustom;
import com.heuristix.guns.client.render.TextureManager;
import com.heuristix.guns.client.render.TextureStitchedCustom;
import com.heuristix.guns.util.Log;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 8/31/11
 * Time: 7:19 PM
 */
public abstract class ItemProjectile extends ItemCustom {

    private final Map<ItemProjectileShooter, Class<? extends EntityProjectile>> classes;

    public ItemProjectile(int id) {
        super(id);
        this.classes = new HashMap<ItemProjectileShooter, Class<? extends EntityProjectile>>();
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public Class<? extends EntityProjectile> getProjectileClass(ItemProjectileShooter shooter) {
        return classes.get(shooter);
    }

    public void putProjectileClass(ItemProjectileShooter shooter, Class<? extends EntityProjectile> clazz) {
        classes.put(shooter, clazz);
    }

    public EntityProjectile newProjectile(World world, EntityPlayer player, ItemProjectileShooter shooter) {
        try {
            return getProjectileClass(shooter).getDeclaredConstructor(World.class, EntityLiving.class).newInstance(world, player);
        } catch (Exception e) {
            Log.throwing(getClass(), "newProjectile(World world, EntityPlayer player, ItemProjectileShooter shooter)", e, TheGunMod.class);
            return null;
        }
    }

    public boolean hasWorkbenchRecipe() {
        return false;
    }

    public Object[] getCraftingRecipe() {
        return new Object[]{ingotIron, 1 / 8f, gunpowder, 1 / 16f};
    }

    public boolean isShapelessRecipe() {
        return false;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
    	if (iconRegister instanceof TextureMap && TheGunMod.instance != null) {
			String name = getUnlocalizedName().substring(getUnlocalizedName().indexOf('.') + 1);
			TextureStitched texture = new TextureStitchedCustom(name, TheGunMod.instance);
			((TextureMap) iconRegister).setTextureEntry(name, texture);
			iconIndex = texture;
			//registerIcon("guns:" + getUnlocalizedName().substring(getUnlocalizedName().indexOf('.') + 1));
		}
    }

}
