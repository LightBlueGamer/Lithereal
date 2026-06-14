package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.EtherealCorePortalBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.world.feature.ModFeatures;

import java.util.Set;

public class EtherealCorePortalBlock extends BaseEntityBlock implements Portal {
    public static final BlockPos SPAWN_POINT = new BlockPos(0, 250, 0);
    public static final ResourceKey<Level> ETHEREAL_CORE = Lithereal.key(Registries.DIMENSION, "ethereal_core");
    public static final MapCodec<EtherealCorePortalBlock> CODEC = simpleCodec(EtherealCorePortalBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0F, 7.0F, 0.0F, 16.0F, 9.0F, 16.0F);

    @Override
    public @NotNull MapCodec<? extends EtherealCorePortalBlock> codec() {
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
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity.canUsePortal(false) && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())), state.getShape(level, pos), BooleanOp.AND))
            entity.setAsInsidePortal(this, pos);
    }

    @Override
    public TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos blockPos) {
        LevelData.RespawnData respawnData = currentLevel.getRespawnData();
        ResourceKey<Level> resourceKey = currentLevel.dimension().identifier().equals(ETHEREAL_CORE.identifier()) ? respawnData.dimension() : ETHEREAL_CORE;
        ServerLevel newLevel = currentLevel.getServer().getLevel(resourceKey);
        if (newLevel == null) {
            return null;
        } else {
            boolean isEtherealCore = resourceKey == ETHEREAL_CORE;
            BlockPos tpPos = isEtherealCore ? SPAWN_POINT : respawnData.pos();
            Vec3 newPos = tpPos.getBottomCenter();
            float yRot;
            float xRot;
            Set<Relative> relatives;
            if (isEtherealCore) {
                yRot = Direction.NORTH.toYRot();
                xRot = 0.0F;
                relatives = Relative.union(Relative.DELTA, Set.of(Relative.X_ROT));
                ModFeatures.ETHEREAL_CORE_ARENA.get().place(NoneFeatureConfiguration.INSTANCE, newLevel, newLevel.getChunkSource().getGenerator(), newLevel.getRandom(), SPAWN_POINT.below(16));
            } else {
                yRot = respawnData.yaw();
                xRot = respawnData.pitch();
                relatives = Relative.union(Relative.DELTA, Relative.ROTATION);
                if (entity instanceof ServerPlayer serverPlayer) return serverPlayer.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);

                newPos = entity.adjustSpawnLocation(newLevel, tpPos).getBottomCenter();
            }

            return new TeleportTransition(newLevel, newPos, Vec3.ZERO, yRot, xRot, relatives, TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        double xPos = randomSource.nextDouble();
        double yPos = randomSource.nextDouble();
        double zPos = randomSource.nextDouble();
        double xDir = randomSource.nextDouble() * 0.05;
        double yDir = randomSource.nextDouble() * 0.05;
        double zDir = randomSource.nextDouble() * 0.05;
        switch (getAxis(blockState)) {
            case X -> {
                zPos = 0.8;
                xDir = randomSource.nextDouble() * 0.01;
                yDir = randomSource.nextDouble() * 0.01;
            }
            case Y -> {
                yPos = 0.8;
                xDir = randomSource.nextDouble() * 0.01;
                zDir = randomSource.nextDouble() * 0.01;
            }
            case Z -> {
                xPos = 0.8;
                yDir = randomSource.nextDouble() * 0.01;
                zDir = randomSource.nextDouble() * 0.01;
            }
        }
        level.addParticle(ModParticles.PORTAL_SPARKLE.get(),
                blockPos.getX() + xPos,
                blockPos.getY() + yPos,
                blockPos.getZ() + zPos,
                xDir,
                yDir,
                zDir);
        level.addParticle(ModParticles.PORTAL_SPARKLE.get(),
                blockPos.getX() + 1 - xPos,
                blockPos.getY() + 1 - yPos,
                blockPos.getZ() + 1 - zPos,
                -xDir,
                -yDir,
                -zDir);
        if (isTransportPortal(blockPos, level)) {
            for (int cnt = 0; cnt < 4; cnt++) {
                xPos = blockPos.getX() + randomSource.nextDouble();
                yPos = blockPos.getY() + randomSource.nextDouble();
                zPos = blockPos.getZ() + randomSource.nextDouble();
                xDir = (randomSource.nextFloat() - 0.5) * 0.5;
                yDir = (randomSource.nextFloat() - 0.5) * 0.5;
                zDir = (randomSource.nextFloat() - 0.5) * 0.5;
                int rand = randomSource.nextInt(2) * 2 - 1;
                switch (getAxis(blockState)) {
                    case X -> {
                        zPos = blockPos.getZ() + 0.5 + 0.25 * rand;
                        zDir = randomSource.nextFloat() * 2.0F * rand;
                    }
                    case Y -> {
                        yPos = blockPos.getY() + 0.25 * rand;
                        yDir = randomSource.nextFloat() * rand;
                    }
                    case Z -> {
                        xPos = blockPos.getX() + 0.5 + 0.25 * rand;
                        xDir = randomSource.nextFloat() * 2.0F * rand;
                    }
                }
                level.addParticle(ModParticles.PORTAL_EMISSION.get(),
                        xPos,
                        yPos,
                        zPos,
                        xDir,
                        yDir,
                        zDir);
            }
        }
    }

    public Direction.Axis getAxis(BlockState blockState) {
        return Direction.Axis.Y;
    }

    public boolean isTransportPortal(BlockPos blockPos, Level level) {
        return true;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
