package org.lithereal.core.component.miner_shape;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import org.lithereal.core.component.miner_shape.condition.ValidityCondition;
import org.lithereal.registry.ModRegistries;

public record MinerShape(Component displayName, ValidityCondition validityCondition, boolean usesDepth) {
    public static final Codec<Holder<MinerShape>> CODEC = RegistryFixedCodec.create(ModRegistries.MINER_SHAPE_REGISTRY);
    public static final Codec<MinerShape> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ComponentSerialization.CODEC.fieldOf("display_name").forGetter(MinerShape::displayName),
                            ValidityCondition.CODEC.fieldOf("condition").forGetter(MinerShape::validityCondition),
                            Codec.BOOL.fieldOf("uses_depth").forGetter(MinerShape::usesDepth))
                    .apply(instance, MinerShape::new));
    public boolean isValid(BlockPos pos, BlockPos centerPos, Direction direction, double radius, int depth) {
        if (usesDepth()) {
            switch (direction.getAxis()) {
                case X -> {
                    if (Math.abs(pos.getX() - centerPos.getX()) > (depth - 1) || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
                        return false;
                }
                case Y -> {
                    if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > (depth - 1) || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
                        return false;
                }
                case Z -> {
                    if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > (depth - 1))
                        return false;
                }
            }
        } else if (Math.abs(pos.getX() - centerPos.getX()) > radius || Math.abs(pos.getY() - centerPos.getY()) > radius || Math.abs(pos.getZ() - centerPos.getZ()) > radius)
            return false;
        return this.validityCondition().isValid(pos, centerPos, direction, radius, depth);
    }
}
