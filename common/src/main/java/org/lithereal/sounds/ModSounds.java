package org.lithereal.sounds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {

    public static final SoundEvent MUSIC_DISC_SPARKLE = register();
    private static SoundEvent register() {
        return register(new ResourceLocation("lithereal", "music_disc_sparkle"));
    }

    private static SoundEvent register(ResourceLocation resourceLocation) {
        return register(resourceLocation, resourceLocation);
    }

    private static SoundEvent register(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
    }
}