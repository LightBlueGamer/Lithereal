package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.EtherealCorePortalBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

public class EtherealCorePortalBlock extends BaseEntityBlock implements Portal {
    public static final BlockPos SPAWN_POINT = new BlockPos(0, 250, 0);
    public static final ResourceKey<Level> ETHEREAL_CORE = ResourceKey.create(Registries.DIMENSION, Lithereal.id("ethereal_core"));
    public static final MapCodec<EtherealCorePortalBlock> CODEC = simpleCodec(EtherealCorePortalBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0F, 6.0F, 0.0F, 16.0F, 12.0F, 16.0F);

    @Override
    public @NotNull MapCodec<EtherealCorePortalBlock> codec() {
        return CODEC;
    }

    public EtherealCorePortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EtherealCorePortalBlockEntity(ModBlockEntities.ETHEREAL_CORE_PORTAL.get(), blockPos, blockState);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity.canUsePortal(false) && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())), blockState.getShape(level, blockPos), BooleanOp.AND)) entity.setAsInsidePortal(this, blockPos);
    }

    @Override
    public DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> resourceKey = serverLevel.dimension().location().equals(ETHEREAL_CORE.location()) ? Level.OVERWORLD : ETHEREAL_CORE;
        ServerLevel toMoveTo = serverLevel.getServer().getLevel(resourceKey);
        if (toMoveTo == null) {
            return null;
        } else {
            boolean destinationIsNotOverworld = resourceKey == ETHEREAL_CORE;
            BlockPos blockPos2 = destinationIsNotOverworld ? SPAWN_POINT : toMoveTo.getSharedSpawnPos();
            Vec3 vec3 = blockPos2.getBottomCenter();
            float newYRot = entity.getYRot();
            if (destinationIsNotOverworld) newYRot = Direction.NORTH.toYRot();
            else {
                if (entity instanceof ServerPlayer serverPlayer) return serverPlayer.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING);

                vec3 = entity.adjustSpawnLocation(toMoveTo, blockPos2).getBottomCenter();
            }

            return new DimensionTransition(toMoveTo, vec3, entity.getDeltaMovement(), newYRot, entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET));
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        double d = (double)blockPos.getX() + randomSource.nextDouble();
        double e = (double)blockPos.getY() + 0.8;
        double f = (double)blockPos.getZ() + randomSource.nextDouble();
        level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState blockState, Fluid fluid) {
        return false;
    }
}
