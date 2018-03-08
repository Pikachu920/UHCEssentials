package com.pikachu.uhcessentials;

import com.mojang.authlib.GameProfile;
import com.pikachu.uhcessentials.features.Fullbright;
import com.pikachu.uhcessentials.gui.base.ItemWindow;
import com.pikachu.uhcessentials.gui.base.TextWindow;
import com.pikachu.uhcessentials.gui.screens.OptionScreen;
import com.pikachu.uhcessentials.gui.windows.ArrowCounter;
import com.pikachu.uhcessentials.gui.windows.Compass;
import com.pikachu.uhcessentials.gui.windows.Coordinates;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


@Mod(modid = UHCEssentials.MOD_ID, version = UHCEssentials.VERSION, acceptedMinecraftVersions = "[1.8.8,1.8.9]", clientSideOnly = true)
public class UHCEssentials {

    public static final String MOD_ID = "uhcessentials";
    public static final String VERSION = "0.5";
    public static final Item CUSTOM_COMPASS = new Item().setUnlocalizedName("customcompass");
    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final OptionScreen OPTION_SCREEN = new OptionScreen();
    private static Configuration config = new Configuration(new File(Util.getWorkingDir(true) + "UHCEssentials.cfg"));
    private static final Fullbright FULLBRIGHT = new Fullbright();

    public static Logger getLogger() {
        return LOGGER;
    }

    public static OptionScreen getOptionScreen() {
        return OPTION_SCREEN;
    }

    public static Fullbright getFullbright() {
        return FULLBRIGHT;
    }

    public static Configuration getConfig() {
        return config;
    }

    private ItemStack clock = new ItemStack(Items.clock);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerItem(CUSTOM_COMPASS, "customcompass");
        ModelLoader.setCustomModelResourceLocation(CUSTOM_COMPASS, 0,
                new ModelResourceLocation(MOD_ID + ":customcompass", "inventory"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        FULLBRIGHT.init();
        MinecraftForge.EVENT_BUS.register(new HotkeyStore());
        MinecraftForge.EVENT_BUS.register(new ArrowCounter());
        MinecraftForge.EVENT_BUS.register(new Coordinates());
        MinecraftForge.EVENT_BUS.register(new Compass());

        MinecraftForge.EVENT_BUS.register(new TextWindow("biome", mc -> {
                EntityPlayerSP player = mc.thePlayer;
                return Util.getBiomeAt(player.posX, player.posZ);
        }
                , 1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new TextWindow("FPS", mc -> Util.getFPS() + " FPS",
                1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new TextWindow("entity counter", mc -> "Entities: " + Util.getRenderedEntityCount(),
                1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new TextWindow("ping", mc -> {
            // No way all these null checks are required but i'll put them anyway
                NetHandlerPlayClient netHandler = mc.getNetHandler();
                if (netHandler != null) {
                    GameProfile gameProfile = mc.thePlayer.getGameProfile();
                    if (gameProfile != null) {
                        NetworkPlayerInfo playerInfo = netHandler.getPlayerInfo(gameProfile.getId());
                        if (playerInfo != null) {
                            return "Ping: " + playerInfo.getResponseTime();
                        }
                    }
                }
                return "Ping: Unknown";
            }
                , 1, 110, 110));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("helmet", mc -> mc.thePlayer.getCurrentArmor(3),
                new ItemStack(Items.iron_helmet), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("chestplate", mc -> mc.thePlayer.getCurrentArmor(2),
                new ItemStack(Items.iron_chestplate), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("leggings", mc -> mc.thePlayer.getCurrentArmor(1),
                new ItemStack(Items.iron_leggings), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("boots", mc -> mc.thePlayer.getCurrentArmor(0),
                new ItemStack(Items.iron_boots), 150, 150));

        MinecraftForge.EVENT_BUS.register(new ItemWindow("clock", mc -> clock
                , null, 150, 150));

    }
}

