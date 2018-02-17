package com.pikachu.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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

    public static String getBiomeAt(BlockPos pos, World world) {
        if (world != null && pos != null) {
            return world.getWorldChunkManager().getBiomeGenerator(pos).biomeName;
        }
        return null;
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

}
