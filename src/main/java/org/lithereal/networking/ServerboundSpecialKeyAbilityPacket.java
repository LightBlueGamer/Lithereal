package org.lithereal.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;

import java.util.function.Function;

public record ServerboundSpecialKeyAbilityPacket(SpecialKeyType specialKeyType) implements CustomPacketPayload {
    public static CustomPacketPayload.Type<ServerboundSpecialKeyAbilityPacket> TYPE = new CustomPacketPayload.Type<>(Lithereal.id("special_key_ability"));
    public static StreamCodec<RegistryFriendlyByteBuf, ServerboundSpecialKeyAbilityPacket> STREAM_CODEC = ByteBufCodecs.VAR_INT
            .map(integer -> SpecialKeyType.values()[integer], Enum::ordinal)
            .map(ServerboundSpecialKeyAbilityPacket::new, ServerboundSpecialKeyAbilityPacket::specialKeyType)
            .mapStream(Function.identity());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public void handleAbility(Level level, Player user) {
        BlockPos belowPos = user.blockPosition().below();
        switch (specialKeyType) {
            case FREEZE -> {
                for (int x = -4; x <= 4; x++) {
                    for (int z = -4; z <= 4; z++) {
                        BlockPos checkPos = belowPos.offset(x, 0, z);
                        if (level.getBlockState(checkPos).getBlock() == Blocks.WATER) {
                            level.setBlockAndUpdate(checkPos, Blocks.FROSTED_ICE.defaultBlockState());
                        }
                    }
                }
            }
            case SCORCH -> {
                BlockState blockBelowState = level.getBlockState(belowPos);
                Block blockBelow = blockBelowState.getBlock();
                if (blockBelow == Blocks.NETHERRACK)
                    level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_NETHERRACK.get().defaultBlockState());
                else if (blockBelow == Blocks.CRIMSON_NYLIUM)
                    level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_CRIMSON_NYLIUM.get().defaultBlockState());
                else if (blockBelow == Blocks.WARPED_NYLIUM)
                    level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_WARPED_NYLIUM.get().defaultBlockState());
            }
        }
    }
    public enum SpecialKeyType {
        FREEZE,
        SCORCH
    }
}
