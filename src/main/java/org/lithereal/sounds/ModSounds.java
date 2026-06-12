package org.lithereal.sounds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ModSounds {

    public static final SoundEvent MUSIC_DISC_SPARKLE = register();
    public static final ResourceKey<JukeboxSong> SPARKLE = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath("lithereal", "sparkle"));
    private static SoundEvent register() {
        return register(Identifier.fromNamespaceAndPath("lithereal", "music_disc_sparkle"));
    }

    private static SoundEvent register(Identifier identifier) {
        return register(identifier, identifier);
    }

    private static SoundEvent register(Identifier identifier, Identifier identifier2) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier2));
    }
}