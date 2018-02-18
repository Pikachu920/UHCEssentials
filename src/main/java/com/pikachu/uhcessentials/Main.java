package com.pikachu.uhcessentials;

import com.pikachu.uhcessentials.features.Fullbright;
import com.pikachu.uhcessentials.gui.OptionScreen;
import com.pikachu.uhcessentials.gui.base.ItemWindow;
import com.pikachu.uhcessentials.gui.base.TextWindow;
import com.pikachu.uhcessentials.gui.elements.ArrowCounter;
import com.pikachu.uhcessentials.gui.elements.Coordinates;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import com.pikachu.uhcessentials.utils.Getter;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


@Mod(modid = Main.MOD_ID, version = Main.VERSION, acceptedMinecraftVersions = "[1.8.8,1.8.9]")
public class Main {

    public static final String MOD_ID = "uhc essentials";
    public static final String VERSION = "0.5";
    private static Configuration config = new Configuration(new File(Util.getWorkingDir(true) + "UHCEssentials.cfg"));

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static Logger getLogger() {
        return LOGGER;
    }

    private static final OptionScreen OPTION_SCREEN = new OptionScreen();
    private static final Fullbright FULLBRIGHT = new Fullbright();

    public static Fullbright getFullbright() {
        return FULLBRIGHT;
    }

    public static OptionScreen getOptionScreen() {
        return OPTION_SCREEN;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HotkeyStore());
        MinecraftForge.EVENT_BUS.register(new ArrowCounter());
        MinecraftForge.EVENT_BUS.register(new Coordinates());

        MinecraftForge.EVENT_BUS.register(new TextWindow("biome", new Getter<Minecraft, String>() {
            @Override
            public String get(Minecraft mc) {
                EntityPlayerSP player = mc.thePlayer;
                return Util.getBiomeAt(player.posX, player.posZ);
            }
        }, 1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new TextWindow("FPS", new Getter<Minecraft, String>() {
            @Override
            public String get(Minecraft mc) {
                return Util.getFPS() + " FPS";
            }
        }, 1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new TextWindow("entity counter", new Getter<Minecraft, String>() {
            @Override
            public String get(Minecraft mc) {
                return "Entities: " + Util.getRenderedEntityCount();
            }
        }, 1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("helmet", new Getter<Minecraft, ItemStack>() {
            @Override
            public ItemStack get(Minecraft mc) {
                return mc.thePlayer.getCurrentArmor(3);
            }
        }, new ItemStack(Items.iron_helmet), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("chestplate", new Getter<Minecraft, ItemStack>() {
            @Override
            public ItemStack get(Minecraft mc) {
                return mc.thePlayer.getCurrentArmor(2);
            }
        }, new ItemStack(Items.iron_chestplate), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("leggings", new Getter<Minecraft, ItemStack>() {
            @Override
            public ItemStack get(Minecraft mc) {
                return mc.thePlayer.getCurrentArmor(1);
            }
        }, new ItemStack(Items.iron_leggings), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("boots", new Getter<Minecraft, ItemStack>() {
            @Override
            public ItemStack get(Minecraft mc) {
                return mc.thePlayer.getCurrentArmor(0);

            }
        }, new ItemStack(Items.iron_boots), 150, 150));
    }

    public static Configuration getConfig() {
        return config;
    }
}

