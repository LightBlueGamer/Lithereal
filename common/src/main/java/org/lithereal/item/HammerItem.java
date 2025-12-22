package org.lithereal.item;

import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.lithereal.data.compat.ModWeaponType;

import java.util.List;
import java.util.function.BiFunction;

// Credits to ErrorMikey for hammer code https://github.com/ErrorMikey/JustHammers
public class HammerItem extends DiggerItem implements MultiMiningItem {
    protected final int depth = 1;
    protected final int radius = 3;
    public HammerItem(Tier tier, int damage, float attackSpeed, int weaponLevel, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties.attributes(createAttributes(tier, damage, attackSpeed, weaponLevel)));
    }
    public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed, int weaponLevel) {
        if (Platform.isModLoaded("combatify")) {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            ModWeaponType.HAMMER.addCombatAttributes(weaponLevel, tier, builder);
            return builder.build();
        }
        return AxeItem.createAttributes(tier, damage, speed);
    }

    @Override
    public boolean mineBlock(ItemStack hammerStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (level.isClientSide || blockState.getDestroySpeed(level, blockPos) == 0.0F) return true;

        HitResult pick = livingEntity.pick(livingEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);

        // Not possible?
        if (!(pick instanceof BlockHitResult)) return super.mineBlock(hammerStack, level, blockState, blockPos, livingEntity);


        this.findAndBreakNearBlocks(pick, blockPos, hammerStack, level, livingEntity);
        return super.mineBlock(hammerStack, level, blockState, blockPos, livingEntity);
    }

    public void findAndBreakNearBlocks(HitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        final int[] damage = {0};

        forEachMineableBlock(level, (BlockHitResult) pick, blockPos, hammerStack, (pos, targetState) -> {
            if (damage[0] >= (hammerStack.getMaxDamage() - hammerStack.getDamageValue() - 1)) {
                return true;
            }
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack);
                    drops.forEach(e ->
                            Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e));
                }
            }
            damage[0]++;
            return false;
        });

        if (damage[0] != 0 && !player.isCreative()) {
            Tool tool = hammerStack.get(DataComponents.TOOL);
            if (tool != null) hammerStack.hurtAndBreak(damage[0] * tool.damagePerBlock(), livingEntity, EquipmentSlot.MAINHAND);
        }
    }

    public void forEachMineableBlock(BlockGetter blockGetter, BlockHitResult pick, BlockPos blockPos, ItemStack hammerStack, BiFunction<BlockPos, BlockState, Boolean> function) {
        var boundingBox = getBoundingBox(pick, blockPos);
        for (BlockPos pos : BlockPos.betweenClosed(Math.min(boundingBox.minX(), boundingBox.maxX()), Math.min(boundingBox.minY(), boundingBox.maxY()), Math.min(boundingBox.minZ(), boundingBox.maxZ()), Math.max(boundingBox.minX(), boundingBox.maxX()), Math.max(boundingBox.minY(), boundingBox.maxY()), Math.max(boundingBox.minZ(), boundingBox.maxZ()))) {
            BlockState targetState = blockGetter.getBlockState(pos);
            if (pos.equals(blockPos) || cannotDestroy(targetState, blockGetter, pos) || !hammerStack.isCorrectToolForDrops(targetState)) {
                continue;
            }
            if (function.apply(pos, targetState)) break;
        }
    }

    @NotNull
    protected BoundingBox getBoundingBox(BlockHitResult pick, BlockPos blockPos) {
        var size = (radius / 2);
        var offset = 0;

        Direction direction = pick.getDirection();
        return switch (direction) {
            case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size);
            case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
        };
    }

    protected boolean cannotDestroy(BlockState targetState, BlockGetter blockGetter, BlockPos pos) {
        if (targetState.getDestroySpeed(blockGetter, pos) <= 0) {
            return true;
        }

        return blockGetter.getBlockEntity(pos) != null;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos, TriFunction<BlockState, BlockGetter, BlockPos, Float> destroySpeedFunc) {
        HitResult pick = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + 2, 1F, false);
        float[] totalDestroySpeed = new float[] {destroySpeedFunc.apply(blockState, blockGetter, blockPos)};
        forEachMineableBlock(blockGetter, (BlockHitResult) pick, blockPos, itemStack, (pos, targetState) -> {
            totalDestroySpeed[0] += destroySpeedFunc.apply(targetState, blockGetter, pos);
            return false;
        });
        return totalDestroySpeed[0] * 0.75F;
    }
}