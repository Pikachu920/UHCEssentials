package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ArrowCounter extends MovableWindow {

    public ArrowCounter() {
        super();
    }

    @Override
    public int getHeight() {
        return 6;
    }

    @Override
    public int getWidth() {
        return 6;
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
        return "arrow counter";
    }

    public void render(RenderGameOverlayEvent.Text event) {
        //Util.renderItemTexture(getX(), getY(), 262, 16, 16);
        fontRenderer.drawStringWithShadow("" + Util.amountOfItemIn(262, mc.thePlayer.inventory.mainInventory), getX(), getY(), 0xfffff);
    }
}
