package com.pikachu.uhcessentials.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Util {

    private Util() {
    }

    public static final int WHITE = new Color(255, 255, 255).getRGB();
    public static final int RED = new Color(255, 85, 85).getRGB();

    private static String workingDir = new File("").getAbsolutePath();
    private static final DecimalFormat TRIM_FORMAT = new DecimalFormat("0");

    private static Minecraft mc =  Minecraft.getMinecraft();

    public static String getFPS() {
        return mc.debug.split(" fps")[0];
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

    public static void drawItemStack(ItemStack stack, int x, int y, boolean overlay, String altText) {
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem itemRender = mc.getRenderItem();
        FontRenderer font = mc.fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        if (overlay) {
            itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        }
        RenderHelper.disableStandardItemLighting();
    }

    public static void drawItemStack(ItemStack stack, int x, int y, boolean drawOverlay) {
        drawItemStack(stack, x, y, drawOverlay, null);
    }

    public static String getBiomeAt(double x, double z) {
        return mc.theWorld.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(mc.thePlayer.posX), 64,
                MathHelper.floor_double(mc.thePlayer.posZ))).biomeName;
    }

    public static <T> List<T> reverse(final List<T> list) {
        final int last = list.size() - 1;
        return IntStream.rangeClosed(0, last)
                .map(i -> (last - i))
                .mapToObj(list::get)
                .collect(Collectors.toList());
    }

    public static String findLongestString(String... strings) {
        String longestString = strings[0];
        for (String string : strings) {
            if (string.length() > longestString.length()) {
                longestString = string;
            }
        }
        return longestString;
    }

    public static String trimmed(Double d) {
        return TRIM_FORMAT.format(d);
    }

    public static String getRenderedEntityCount() {
        return mc.renderGlobal.getDebugInfoEntities().split("/")[0].split(" ")[1];
    }

    public static boolean endsWithAny(String check, String... potentialEnds) {
        for (String potentialEnd : potentialEnds) {
            if (check.endsWith(potentialEnd)) {
                return true;
            }
        }
        return false;
    }

}
