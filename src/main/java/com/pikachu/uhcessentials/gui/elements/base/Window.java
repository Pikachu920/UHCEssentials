package com.pikachu.uhcessentials.gui.elements.base;

import com.pikachu.uhcessentials.Main;
import com.pikachu.uhcessentials.gui.OptionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class Window extends Gui {

    public Window() {
        OptionScreen.windows.add(this);
    }

    private int x = Main.getConfig().getInt("x", getName(), getDefaultX(), Integer.MIN_VALUE, Integer.MAX_VALUE,
            "Controls the x coordinate of the " + getName() + " window");

    private int y = Main.getConfig().getInt("y", getName(), getDefaultY(), Integer.MIN_VALUE, Integer.MAX_VALUE,
            "Controls the x coordinate of the " + getName() + " window");

    private boolean enabled = Main.getConfig().getBoolean("enabled", getName(), true,
            "Controls whether or not the " + getName() + " window is shown");

    protected Minecraft mc = Minecraft.getMinecraft();
    private boolean clicked;
    protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (x < mc.displayWidth) {
            this.x = x;
        }
    }

    public void setY(int y) {
        if (y < mc.displayHeight) {
            this.y = y;
        }
    }

    public void reset() {
        setX(getDefaultX());
        setY(getDefaultY());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean displayDisabledIndicator() {
        return true;
    }

    public boolean drawWhenDisabled() {
        return false;
    }

    @SubscribeEvent
    public void onTextRender(RenderGameOverlayEvent.Text event) {
        if (((isEnabled() || drawWhenDisabled()) || mc.currentScreen instanceof OptionScreen) && !mc.gameSettings.showDebugInfo) {
            render(event);
            if (!isEnabled() && displayDisabledIndicator()) {
                drawDisabledIndicator();
            }
        }
    }

    public void drawDisabledIndicator() {
        fontRenderer.drawStringWithShadow("X", getX() - (getWidth() / 2), getY() - (getHeight() / 2), 0xffffff);
    }

    public abstract int getDefaultX();

    public abstract int getDefaultY();

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract String getName();

    public abstract void render(RenderGameOverlayEvent.Text event);

}
