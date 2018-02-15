package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ArrowCounter extends MovableWindow {

    private ItemStack arrow = new ItemStack(Items.arrow, 1);
    private String amount;

    public ArrowCounter() {
        super();
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public int getWidth() {
        return 15;
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
        amount = String.valueOf(Util.amountOfItemIn(262, mc.thePlayer.inventory.mainInventory));
        Util.drawItemStack(arrow, getX(), getY(), "");
        fontRenderer.drawStringWithShadow(amount, getX() + (amount.length() == 1 ? 12 : 9), getY() + 8, (Integer.valueOf(amount) <= 10 ? 0xff5555 : 0xffffffff));
    }

}
