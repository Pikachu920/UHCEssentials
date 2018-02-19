package com.pikachu.uhcessentials.gui.base;

import com.pikachu.uhcessentials.Main;
import com.pikachu.uhcessentials.gui.screens.OptionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.Color;

public abstract class Window extends Gui {

    private final int DISABLED_COLOR = new Color(255, 0, 25).getRGB();
    protected Minecraft mc = Minecraft.getMinecraft();
    protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private String name;
    private int defaultX;
    private int defaultY;
    private int x;
    private Color color = new Color(69, 69, 69, 150);
    private int y;
    private boolean enabled;
    private boolean clicked;
    public Window(String name, int defaultX, int defaultY) {
        this.name = name;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        OptionScreen.windows.add(this);
        x = Main.getConfig().getInt("x", name, defaultX, Integer.MIN_VALUE, Integer.MAX_VALUE,
                "Controls the x coordinate of the " + name + " window");
        y = Main.getConfig().getInt("y", name, defaultY, Integer.MIN_VALUE, Integer.MAX_VALUE,
                "Controls the x coordinate of the " + name + " window");
        enabled = Main.getConfig().getBoolean("enabled", name, true,
                "Controls whether or not the " + name + " window is shown");
        if (x > mc.displayWidth) {
            x = mc.displayWidth / 2;
        }
        if (y > mc.displayHeight) {
            y = mc.displayHeight / 2;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < mc.displayWidth) {
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < mc.displayHeight) {
            this.y = y;
        }
    }

    public int getColor() {
        return color.getRGB();
    }

    public void reset() {
        setX(defaultX);
        setY(defaultY);
    }

    public void save(boolean alsoSaveConfig) {
        Main.getConfig().get(getName(), "x", getDefaultX(),
                "Controls the x coordinate of the " + getName() + " window").set(getX());
        Main.getConfig().get(getName(), "y", getDefaultY(),
                "Controls the y coordinate of the " + getName() + " window").set(getY());
        Main.getConfig().get(getName(), "enabled", true,
                "Controls whether or not the " + getName() + " window is enabled").set(isEnabled());
        if (alsoSaveConfig) {
            Main.getConfig().save();
        }
    }

    public int getDefaultY() {
        return defaultY;
    }

    public int getDefaultX() {
        return defaultX;
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

    public boolean drawWhenDebug() {
        return false;
    }

    public boolean shouldRender() {
        return ((isEnabled() || drawWhenDisabled()) || mc.currentScreen instanceof OptionScreen) && (drawWhenDebug() || !mc.gameSettings.showDebugInfo);
    }

    @SubscribeEvent
    public void onTextRender(RenderGameOverlayEvent.Text event) {
        if (shouldRender()) {
            render(event);
            if (!isEnabled() && displayDisabledIndicator()) {
                drawDisabledIndicator();
            }
        }
    }

    public void drawDisabledIndicator() {
        fontRenderer.drawStringWithShadow("X", getX() - 2, getY() - 4, DISABLED_COLOR);
    }

    public String getName() {
        return this.name;
    }

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract void render(RenderGameOverlayEvent.Text event);


}
