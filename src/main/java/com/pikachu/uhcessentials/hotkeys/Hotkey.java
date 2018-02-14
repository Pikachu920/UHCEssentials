package com.pikachu.uhcessentials.hotkeys;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class Hotkey {

    public KeyBinding binding = getKeyBinding();
    public Object owner;

    public Hotkey(Object owner) {
        this.owner = owner;
        HotkeyStore.getHotkeys().add(this);
    }

    public abstract void onPress();

    public abstract KeyBinding getKeyBinding();
}
