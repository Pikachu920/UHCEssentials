package com.pikachu.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.lang.reflect.Field;

public class Util {

    private static String workingDir = new File("").getAbsolutePath();

    private static Minecraft mc =  Minecraft.getMinecraft();

    public static int getFPS() {
        return Integer.valueOf(mc.debug.split(" fps")[0]);
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

    public static void renderItemTexture(int x, int y, int id, int width, int height)
    {
        IBakedModel iBakedModel = mc.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(Item.getItemById(id)));
        TextureAtlasSprite textureAtlasSprite = mc.getTextureMapBlocks().getAtlasSprite(iBakedModel.getTexture().getIconName());
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        RenderTexture(x, y, textureAtlasSprite, width, height, 0);
    }

    private static void RenderTexture(int x, int y, TextureAtlasSprite textureAtlasSprite, int width, int height, double zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);	//I have no clue what the DefaultVertexFormats are, but field_181707_g works

        worldrenderer.func_181662_b((double)(x), 			(double)(y + height), 	(double)zLevel).func_181673_a((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV()).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), 	(double)(y + height), 	(double)zLevel).func_181673_a((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV()).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), 	(double)(y), 			(double)zLevel).func_181673_a((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV()).func_181675_d();
        worldrenderer.func_181662_b((double)(x), 			(double)(y), 			(double)zLevel).func_181673_a((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV()).func_181675_d();

        tessellator.draw();
    }

}
