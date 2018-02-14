package com.pikachu.uhcessentials.hotkeys;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class Hotkey {

    public KeyBinding binding = getKeyBinding();
    private Object owner;

    public Hotkey(Object owner) {
        this.owner = owner;
        HotkeyStore.getHotkeys().add(this);
    }

    public abstract void onPress();

    public abstract KeyBinding getKeyBinding();

    public Object getOwner() {
        return owner;
    }

}
