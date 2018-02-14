package com.pikachu.uhcessentials.gui.base;

import net.minecraftforge.client.event.RenderGameOverlayEvent;

public abstract class MovableWindow extends Window {

    public MovableWindow() {
        super();
    }

    public abstract int getDefaultX();

    public abstract int getDefaultY();

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract String getName();

    public abstract void render(RenderGameOverlayEvent.Text event);

}
