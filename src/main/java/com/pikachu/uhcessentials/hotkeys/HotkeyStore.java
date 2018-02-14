package com.pikachu.uhcessentials.hotkeys;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotkeyStore {

    private static ArrayList<Hotkey> hotkeys = new ArrayList<Hotkey>();

    public static List<Hotkey> getHotkeys() {
        return hotkeys;
    }

    public static void add(Hotkey hotkey) {
        hotkeys.add(hotkey);
    }

    public static Hotkey get(Object owner) {
        return hotkeys.stream()
                .filter(h -> h.getOwner() == owner)
                .findFirst()
                .orElse(null);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        hotkeys.stream()
                .filter(h -> h.binding.isPressed())
                .forEach(Hotkey::onPress);
    }

}
