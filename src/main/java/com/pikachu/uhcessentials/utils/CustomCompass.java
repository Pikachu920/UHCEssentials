package com.pikachu.uhcessentials.utils;

import com.pikachu.uhcessentials.gui.windows.Compass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * Almost an exact copy of Minecraft's compass class (pls no dmca)
 */
public class CustomCompass extends TextureAtlasSprite {
    /**
     * Current compass heading in radians
     */
    public double currentAngle;
    /**
     * Speed and direction of compass rotation
     */
    public double angleDelta;

    public CustomCompass() {
        super("items/compass");
    }

    public void updateAnimation() {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.thePlayer != null) {
            EntityPlayerSP player = minecraft.thePlayer;
            this.updateCompass(Compass.getTarget(), player.posX, player.posZ, player.rotationYaw, false, false);
        }
    }

    /**
     * Updates the compass based on the given x,z coords and camera direction
     */
    public void updateCompass(BlockPos blockPos, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_) {
        if (!this.framesTextureData.isEmpty()) {
            double d0 = 0.0D;

            double d1 = (double) blockPos.getX() - p_94241_2_;
            double d2 = (double) blockPos.getZ() - p_94241_4_;
            p_94241_6_ = p_94241_6_ % 360.0D;
            d0 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d2, d1));

            if (p_94241_9_) {
                this.currentAngle = d0;
            } else {
                double d3;

                for (d3 = d0 - this.currentAngle; d3 < -Math.PI; d3 += (Math.PI * 2D)) {
                }

                while (d3 >= Math.PI) {
                    d3 -= (Math.PI * 2D);
                }

                d3 = MathHelper.clamp_double(d3, -1.0D, 1.0D);
                this.angleDelta += d3 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;

            for (i = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {
            }

            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}