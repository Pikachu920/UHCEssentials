package com.pikachu.uhcessentials;

import com.pikachu.uhcessentials.features.Fullbright;
import com.pikachu.uhcessentials.gui.OptionScreen;
import com.pikachu.uhcessentials.gui.elements.ArrowCounter;
import com.pikachu.uhcessentials.gui.elements.Biome;
import com.pikachu.uhcessentials.gui.elements.Coordinates;
import com.pikachu.uhcessentials.gui.elements.EntityCounter;
import com.pikachu.uhcessentials.gui.elements.FPS;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
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
        MinecraftForge.EVENT_BUS.register(new FPS());
        MinecraftForge.EVENT_BUS.register(new HotkeyStore());
        MinecraftForge.EVENT_BUS.register(new ArrowCounter());
        MinecraftForge.EVENT_BUS.register(new Biome());
        MinecraftForge.EVENT_BUS.register(new Coordinates());
        MinecraftForge.EVENT_BUS.register(new EntityCounter());
    }

    public static Configuration getConfig() {
        return config;
    }
}

