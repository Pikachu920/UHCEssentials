package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class EntityCounter extends MovableWindow {

    private final int PADDING = 1;
    private String renderedEntityCount;

    public EntityCounter() {
        super();
    }

    @Override
    public int getHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(renderedEntityCount);
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
        return "entity counter";
    }

    public void render(RenderGameOverlayEvent.Text event) {
        renderedEntityCount = "Entities: " + Util.getRenderedEntityCount();
        Gui.drawRect(getX() - PADDING, getY(), getX() + getWidth() + PADDING, getY() + getHeight(), getColor());
        fontRenderer.drawStringWithShadow(renderedEntityCount, getX(), getY(), Util.WHITE);
    }

}
