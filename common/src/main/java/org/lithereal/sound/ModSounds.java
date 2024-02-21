package org.lithereal.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModSounds {

    public static final SoundEvent MUSIC_DISC_SPARKLE = register("music_disc_sparkle");
    private static SoundEvent register(String string) {
        return register(new ResourceLocation(string));
    }

    private static SoundEvent register(ResourceLocation resourceLocation) {
        return register(resourceLocation, resourceLocation);
    }
    
    private static SoundEvent register(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
    }
}
