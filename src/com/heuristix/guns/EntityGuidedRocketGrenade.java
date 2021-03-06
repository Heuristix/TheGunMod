package com.heuristix.guns;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.heuristix.guns.util.Quaternion;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 11/22/11
 * Time: 7:17 AM
 */
public class EntityGuidedRocketGrenade extends EntityRocketGrenade {

    public EntityGuidedRocketGrenade(World world, EntityLiving living) {
        super(world, living);
    }

    public void onUpdate() {
        super.onUpdate();
        if (getOwner() != null) {
            Vec3 ownerPos = getOwner().getPosition(1);
            Vec3 projectedPos = Util.getProjectedPoint(ownerPos, getOwner().getLook(1), 1000);
            MovingObjectPosition rayTrace = worldObj.rayTraceBlocks(ownerPos, projectedPos);
            Vec3 vec = null;
            if (rayTrace != null) {
                vec = rayTrace.hitVec;
            }
            if (vec == null) {
                vec = projectedPos;
            }
            Vec3 position = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 direction = vec.subtract(position);
            Vec3 rotationAxis = vec.crossProduct(direction).normalize();
            float theta = (float) Math.acos(direction.dotProduct(vec));
            Quaternion quaternion = new Quaternion(0.0f, rotationPitch, rotationYaw);
            quaternion.rotate(rotationAxis, theta);
            float[] angles = quaternion.getEulerAngles();
            rotationPitch = angles[1];
            rotationYaw = angles[2];
            System.out.println("P: " + rotationPitch + " ; Y: " + rotationYaw);
        }
    }

    public float getSpeed() {
        return 0.001f;
    }

}
