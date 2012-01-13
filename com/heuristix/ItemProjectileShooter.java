package com.heuristix;

import com.heuristix.net.PacketFireProjectile;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 8/31/11
 * Time: 7:16 PM
 */
public abstract class ItemProjectileShooter extends ItemCustom {

    public static final int FIRE_MODE_SINGLE = 0;
    public static final int FIRE_MODE_AUTO = 1;
    public static final int FIRE_MODE_BURST = 2;

    private final ItemProjectile projectile;

    private long lastShot, lastRound;
    private boolean isBursting;
    private int bursts;

    public ItemProjectileShooter(int id, ItemProjectile projectile) {
        super(id);
        this.maxStackSize = 1;
        this.projectile = projectile;
    }

    /**
     * Realistic ceiling of 4200 rpm
     */
    public abstract int getShotsPerMinute();

    public abstract int getRoundsPerShot();

    public abstract int getRoundsPerMinute();

    public abstract int getFireMode();

    public abstract String getShootSound();

    protected boolean handleAmmunitionConsumption(EntityPlayer player) {
        return player.inventory.consumeInventoryItem(projectile.shiftedIndex);
    }

    public void fire(World world, EntityPlayer player) {
        if (canShoot() && handleAmmunitionConsumption(player)) {
            for (int i = 0; i < getRoundsPerShot(); i++) {
                if (!fireProjectile(world, player, i == 0)) {
                    break;
                }
                if (i == getRoundsPerShot() - 1) {
                    lastShot = System.currentTimeMillis();
                }
            }
        }
    }

    public void burst(World world, EntityPlayer player) {
        if (bursts < 2) {
            if(canFire()) {
                if(handleAmmunitionConsumption(player)) {
                    if (fireProjectile(world, player, true)) {
                        ++bursts;
                    }
                } else {
                    bursts = 0;
                    isBursting = false;
                }
            }
            return;
        }
        bursts = 0;
        isBursting = false;
    }

    public boolean fireProjectile(World world, EntityPlayer player, boolean playSound) {
        if (playSound) {
            float rand = itemRand.nextFloat();
            world.playSoundAtEntity(player, getShootSound(), rand + 0.5f, 1.0f / (rand * 0.4f + 0.8f));
        }
        if (!world.singleplayerWorld) {
            spawnProjectile(world, player);
        }
        lastRound = System.currentTimeMillis();
        onFire(world, player);
        if (getFireMode() == FIRE_MODE_BURST) {
            isBursting = true;
        }
        return true;
    }

    public void spawnProjectile(World world, EntityPlayer player) {
        world.spawnEntityInWorld(projectile.newProjectile(world, player, this));
    }

    public void onFire(World world, EntityPlayer player) {
    }

    public final ItemProjectile getProjectile() {
        return projectile;
    }

    public long getMillisSinceLastShot() {
        return System.currentTimeMillis() - getLastShot();
    }

    public long getLastShot() {
        return lastShot;
    }

    public long getMillisSinceLastRound() {
        return System.currentTimeMillis() - getLastRound();
    }

    public long getLastRound() {
        return lastRound;
    }

    public final boolean canShoot() {
        return (getMillisSinceLastShot() * (float) getShotsPerMinute() / 60 / 1000) >= 1;
    }

    public final boolean canFire() {
        return (getMillisSinceLastRound() * (float) getRoundsPerMinute() / 60 / 1000) >= 1;
    }

    public boolean isBursting() {
        return isBursting;
    }


}
