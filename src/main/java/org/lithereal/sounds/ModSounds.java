package org.lithereal.sounds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import org.lithereal.Lithereal;

public class ModSounds {

    public static final SoundEvent MUSIC_DISC_SPARKLE = register();
    public static final ResourceKey<JukeboxSong> SPARKLE = Lithereal.key(Registries.JUKEBOX_SONG, "sparkle");
    private static SoundEvent register() {
        return register(Lithereal.id("music_disc_sparkle"));
    }

    private static SoundEvent register(Identifier identifier) {
        return register(identifier, identifier);
    }

    private static SoundEvent register(Identifier identifier, Identifier identifier2) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier2));
    }
}