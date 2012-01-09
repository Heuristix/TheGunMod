// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            ModelZombie, ModelRenderer

public class ModelSkeleton extends ModelZombie
{

    public ModelSkeleton()
    {
        float f = 0.0F;
        bipedRightArm = new ModelRenderer(this, 40, 16);
        bipedRightArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
        bipedLeftArm = new ModelRenderer(this, 40, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
        bipedRightLeg = new ModelRenderer(this, 0, 16);
        bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 12, 2, f);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0.0F);
        bipedLeftLeg = new ModelRenderer(this, 0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 12, 2, f);
        bipedLeftLeg.setRotationPoint(2.0F, 12F, 0.0F);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        aimedBow = true;
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }
}
