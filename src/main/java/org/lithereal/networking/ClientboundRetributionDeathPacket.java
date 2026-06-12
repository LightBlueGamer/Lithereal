package org.lithereal.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import org.lithereal.Lithereal;

public record ClientboundRetributionDeathPacket(Vec3 position) implements CustomPacketPayload {
    public static Type<ClientboundRetributionDeathPacket> TYPE = new Type<>(Lithereal.id("retribution_effect_death"));
    public static StreamCodec<RegistryFriendlyByteBuf, ClientboundRetributionDeathPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.fromCodecTrusted(Vec3.CODEC), ClientboundRetributionDeathPacket::position, ClientboundRetributionDeathPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
