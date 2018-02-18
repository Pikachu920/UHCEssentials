package com.pikachu.uhcessentials.gui.base;

import com.pikachu.uhcessentials.utils.Getter;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class TextWindow extends MovableWindow {

    private Getter<Minecraft, String> textGetter;
    private int padding;
    private String text;

    public TextWindow(String name, Getter<Minecraft, String> textGetter, int padding, int defaultX, int defaultY) {
        super(name, defaultX, defaultY);
        this.textGetter = textGetter;
        this.padding = padding;
    }

    @Override
    public int getHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(text);
    }

    @Override
    public void render(RenderGameOverlayEvent.Text event) {
        text = textGetter.get(mc);
        Gui.drawRect(getX() - padding, getY(), getX() + getWidth() + padding, getY() + getHeight(), getColor());
        fontRenderer.drawStringWithShadow(text, getX(), getY(), Util.WHITE);
    }

}
