package com.pikachu.uhcessentials.gui.windows;

import com.pikachu.uhcessentials.gui.base.MovableWindow;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ArrowCounter extends MovableWindow {

    private final int LOW_INDICATOR = Util.RED;
    private ItemStack arrow = new ItemStack(Items.arrow, 1);
    private String amount;

    public ArrowCounter() {
        super("arrow counter", 110, 110);
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public int getWidth() {
        return 15;
    }

    public void render(RenderGameOverlayEvent.Text event) {
        amount = String.valueOf(Util.amountOfItemIn(262, mc.thePlayer.inventory.mainInventory));
        Util.drawItemStack(arrow, getX(), getY(), false);
        fontRenderer.drawStringWithShadow(amount, getX() + (amount.length() == 1 ? 12 : 9), getY() + 8,
                (Integer.valueOf(amount) <= 10 ? LOW_INDICATOR : Util.WHITE));
    }

}
