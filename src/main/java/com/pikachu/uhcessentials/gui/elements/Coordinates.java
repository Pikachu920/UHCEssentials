package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.elements.base.MovableWindow;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Coordinates extends MovableWindow {

    public Coordinates() {
        super();
    }

    @Override
    public int getHeight() {
        return 6;
    }

    @Override
    public int getWidth() {
        return 13;
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
        fontRenderer.drawStringWithShadow("" + Util.getFPS(), getX(), getY(), 0xfffff);
    }

}