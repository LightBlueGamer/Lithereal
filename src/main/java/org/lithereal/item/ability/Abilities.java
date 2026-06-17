package org.lithereal.item.ability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public class Abilities {
    public static final ExtraCodecs.LateBoundIdMapper<@NotNull Identifier, @NotNull MapCodec<? extends IAbility>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<IAbility> CODEC = ID_MAPPER.codec(Identifier.CODEC)
            .dispatch(IAbility::type, mapCodec -> mapCodec);

    public static void bootstrap() {
        ID_MAPPER.put(InfusedAbility.ID, InfusedAbility.CODEC);
        ID_MAPPER.put(StandardAbility.ID, StandardAbility.CODEC);
        ID_MAPPER.put(ThermalAbility.ID, ThermalAbility.CODEC);
    }
}
