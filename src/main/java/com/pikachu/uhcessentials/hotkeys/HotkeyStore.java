package com.pikachu.uhcessentials.hotkeys;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class HotkeyStore {

    public static ArrayList<Hotkey> hotkeys = new ArrayList<Hotkey>();

    public static List<Hotkey> getHotkeys() {
        return hotkeys;
    }

    public static void add(Hotkey hotkey) {
        hotkeys.add(hotkey);
    }

    public static Hotkey get(Object owner) {
        for (Hotkey hotkey : hotkeys) {
            if (hotkey.owner == owner) {
                return hotkey;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        for (Hotkey hotkey : hotkeys) {
            if (hotkey.binding.isPressed()) {
                hotkey.onPress();
            }
        }
    }

}
