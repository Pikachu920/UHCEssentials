package com.pikachu.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.lang.reflect.Field;

public final class Util {

    // to prevent instantiation
    private Util() {
    }

    private static String workingDir = new File("").getAbsolutePath();

    private static Minecraft mc =  Minecraft.getMinecraft();

    public static String getFPS() {
        return mc.debug.split(" fps")[0];
    }

    public static int widthOf(char... chars) {
        int length = 0;
        for (int i = 0; i < chars.length; i++) {
            length += mc.fontRendererObj.getCharWidth(chars[i]);
        }
        return length;
    }

    // basically a placeholder in case I/mojang want to do something with height in the future
    public static int heightOf(char... chars) {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    public static String getWorkingDir(boolean includeSeperator) {
        return includeSeperator ? workingDir + File.separator : workingDir;
    }

    public static int amountOfItemIn(int id, ItemStack... itemStacks) {
        int amount = 0;
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && Item.getIdFromItem(itemStack.getItem()) == id) {
                amount += itemStack.stackSize;
            }
        }
        return amount;
    }

    public static void drawItemStack(ItemStack stack, int x, int y, String altText)
    {
        RenderItem itemRender = mc.getRenderItem();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        itemRender.zLevel = -200F;
        net.minecraft.client.gui.FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = mc.fontRendererObj;
        itemRender.renderItemIntoGUI(stack, x, y);
    }

}
