package org.lithereal.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Optional;
import java.util.function.Consumer;

public record Enhanced(boolean showInTooltip, Optional<Component> tooltip) implements TooltipProvider {
    public static final Codec<Enhanced> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(Enhanced::showInTooltip),
                            ComponentSerialization.FLAT_CODEC.optionalFieldOf("tooltip").forGetter(Enhanced::tooltip))
                    .apply(instance, Enhanced::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Enhanced> STREAM_CODEC = StreamCodec.of(Enhanced::encode, Enhanced::decode);
    public static final Component defaultComponent = Component.translatable("item.enhanced").withStyle(ChatFormatting.BLUE);

    public static Enhanced decode(RegistryFriendlyByteBuf buf) {
        Component tooltip = ComponentSerialization.STREAM_CODEC.decode(buf);
        boolean showInTooltip = ByteBufCodecs.BOOL.decode(buf);
        return new Enhanced(showInTooltip, Optional.of(tooltip));
    }

    public static void encode(RegistryFriendlyByteBuf buf, Enhanced enhanced) {
        ComponentSerialization.STREAM_CODEC.encode(buf, enhanced.tooltip.orElse(defaultComponent));
        ByteBufCodecs.BOOL.encode(buf, enhanced.showInTooltip);
    }

    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (this.showInTooltip)
            consumer.accept(tooltip.orElse(defaultComponent));
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }
}