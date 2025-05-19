package org.lithereal.item.obscured;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MysteriousBlockItem extends BlockItem implements ObscuredItem {
    public final ResourceLocation advancement;
    public MysteriousBlockItem(Block block, Properties properties, ResourceLocation advancement) {
        super(block, properties);
        this.advancement = advancement;
    }
    public MysteriousBlockItem(Block block, Properties properties) {
        this(block, properties, null);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return getObscuredInstance();
    }

    @Override
    public @NotNull String getDescriptionId() {
        return super.getDescriptionId() + ".revealed";
    }

    @Override
    public Item self() {
        return this;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        super.inventoryTick(itemStack, level, entity, i, bl);
        if (level.isClientSide)
            return;
        boolean reveal = entity instanceof Player player && player.isCreative();
        if (!reveal && advancement != null && entity instanceof ServerPlayer player)
            reveal = player.getAdvancements().getOrStartProgress(Objects.requireNonNull(player.getServer()).getAdvancements().get(advancement)).isDone();

        if (ObscuredItem.isRevealed(itemStack) != reveal)
            ObscuredItem.setRevealed(itemStack, reveal);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return ObscuredItem.isRevealed(itemStack) ? super.getName(itemStack) : Component.translatable(getBlock().getDescriptionId() + ".obscured");
    }
}
