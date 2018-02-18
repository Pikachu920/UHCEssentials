package com.pikachu.uhcessentials.gui.base;

import com.pikachu.uhcessentials.gui.OptionScreen;
import com.pikachu.uhcessentials.utils.Getter;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ItemWindow extends MovableWindow {

    private Getter<Minecraft, ItemStack> itemStackGetter;
    private ItemStack defaultItem;
    private int height;
    private int width;

    public ItemWindow(String name, Getter<Minecraft, ItemStack> itemStackGetter, ItemStack defaultItem,
                      int defaultX, int defaultY, int height, int width) {
        super(name, defaultX, defaultY);
        this.itemStackGetter = itemStackGetter;
        this.defaultItem = defaultItem;
        this.height = height;
        this.width = width;
    }

    public ItemWindow(String name, Getter<Minecraft, ItemStack> itemStackGetter, ItemStack defaultItem,
                      int defaultX, int defaultY) {
        super(name, defaultX, defaultY);
        this.itemStackGetter = itemStackGetter;
        this.defaultItem = defaultItem;
        this.height = 15;
        this.width = 15;
    }

    public ItemStack getItem() {
        return itemStackGetter.get(mc);
    }

    public ItemStack getDefaultItem() {
        return defaultItem;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void render(RenderGameOverlayEvent.Text event) {
        if (getItem() != null) {
            Util.drawItemStack(getItem(), getX(), getY(), true, "");
        } else if (mc.currentScreen instanceof OptionScreen) {
            Util.drawItemStack(getDefaultItem(), getX(), getY(), true, "");
        }
    }
}
