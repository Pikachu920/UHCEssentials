package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Coordinates extends MovableWindow {

    private final int RIGHT_PADDING = 15;
    private final int LEFT_PADDING = 1;
    private String longestString;

    public Coordinates() {
        super();
    }

    @Override
    public int getHeight() {
        return ((fontRenderer.FONT_HEIGHT * 3) + 3);
    }

    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(longestString) + RIGHT_PADDING;
    }

    @Override
    public int getDefaultX() {
        return 110;
    }

    @Override
    public int getDefaultY() {
        return 110;
    }

    @Override
    public String getName() {
        return "coordinates";
    }

    public void render(RenderGameOverlayEvent.Text event) {
        EntityPlayerSP player = mc.thePlayer;
        String x = String.valueOf(Util.trimmed(Math.floor(player.posX)));
        String y = String.valueOf(Util.trimmed(Math.floor(player.posY)));
        String z = String.valueOf(Util.trimmed(Math.floor(player.posZ)));
        longestString = Util.findLongestString(x, y, z);
        double rotation = MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);
        int var25 = MathHelper.floor_double((this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        String direction = "";
        String headingZ = "";
        String headingX = "";
        if (rotation > -22.5 && rotation <= 22.5) {
            direction = "S";
            headingZ = "+";
        } else if (rotation > 22.5 && rotation <= 22.5 * 3) {
            direction = "SW";
            headingX = "+";
            headingX = "-";
        } else if (rotation > 22.5 * 3 && rotation <= 22.5 * 5) {
            direction = "W";
            headingX = "-";
        } else if (rotation > 22.5 * 5 && rotation <= 22.5 * 7) {
            direction = "NW";
            headingZ = "-";
            headingX = "-";
        } else if ((rotation > 22.5 * 7 && rotation <= 22.5 * 9) || (rotation > -180 && rotation <= -180 + 22.5)) {
            direction = "N";
            headingZ = "-";
        } else if (rotation > -180 + 22.5 && rotation <= -180 + 22.5 * 3) {
            direction = "NE";
            headingZ = "-";
            headingX = "+";
        } else if (rotation > -180 + 22.5 * 3 && rotation <= -180 + 22.5 * 5) {
            direction = "E";
            headingX = "+";
        } else if (rotation > -180 + 22.5 * 5 && rotation <= -180 + 22.5 * 7) {
            direction = "SE";
            headingZ = "+";
            headingX = "+";
        }

        Gui.drawRect(getX() - LEFT_PADDING, getY(), getX() + getWidth() + RIGHT_PADDING, getY() + getHeight(), getColor());

        int rightX = getX() + getWidth() + RIGHT_PADDING;
        // x coord and x dir
        fontRenderer.drawStringWithShadow("X: " + x, getX(), getY() + 1, Util.WHITE);
        fontRenderer.drawStringWithShadow(headingX, rightX - fontRenderer.getStringWidth(headingX) - 1,
                getY() + 1, Util.WHITE);

        // y coord and facing dir
        fontRenderer.drawStringWithShadow("Y: " + y, getX(), getY() + fontRenderer.FONT_HEIGHT + 2, Util.WHITE);
        fontRenderer.drawStringWithShadow(direction, rightX - fontRenderer.getStringWidth(direction) - 1,
                getY() + fontRenderer.FONT_HEIGHT + 2, Util.WHITE);

        // z coord and z dir
        fontRenderer.drawStringWithShadow("Z: " + z, getX(), getY() + (fontRenderer.FONT_HEIGHT * 2) + 3, Util.WHITE);
        fontRenderer.drawStringWithShadow(headingZ,
                rightX - fontRenderer.getStringWidth(headingZ) - 1,
                getY() + (fontRenderer.FONT_HEIGHT * 2) + 3, Util.WHITE);


    }

}
