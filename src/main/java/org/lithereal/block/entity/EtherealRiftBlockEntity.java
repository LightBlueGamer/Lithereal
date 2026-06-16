package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lithereal.block.EtherealRiftBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EtherealRiftBlockEntity extends EtherealCorePortalBlockEntity {
    private @Nullable ResourceKey<Level> destination = null;
    private long maxLifespan = 6000L;
    private long age;
    @Nullable
    private BlockPos exitPortal;
    private List<BlockPos> attached = new ArrayList<>();

    public EtherealRiftBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public EtherealRiftBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.ETHEREAL_RIFT.get(), blockPos, blockState);
    }

    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (destination != null) valueOutput.store("destination", ResourceKey.codec(Registries.DIMENSION), destination);
        valueOutput.putLong("age", this.age);
        valueOutput.putLong("max_lifespan", this.maxLifespan);
        if (this.exitPortal != null) {
            valueOutput.store("exit_portal", BlockPos.CODEC, this.exitPortal);
        }
    }

    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.destination = valueInput.read("destination", ResourceKey.codec(Registries.DIMENSION)).orElse(null);
        this.age = valueInput.getLongOr("age", 0);
        this.maxLifespan = Math.max(valueInput.getLongOr("max_lifespan", 6000), 1);
        valueInput.read("exit_portal", BlockPos.CODEC).filter(Level::isInSpawnableBounds).ifPresent(blockPos -> this.exitPortal = blockPos);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EtherealRiftBlockEntity etherealRiftBlockEntity) {
        if (etherealRiftBlockEntity.attached.isEmpty())
            EtherealRiftBlock.findAttached(level, blockPos, blockState, etherealRiftBlockEntity.attached);
        ++etherealRiftBlockEntity.age;
        if (etherealRiftBlockEntity.age % etherealRiftBlockEntity.maxLifespan == 0L && blockState.getBlock() instanceof EtherealRiftBlock) {
            EtherealRiftBlock.destroyAttachedBlocks(level, blockPos, blockState);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos blockPos, BlockState state) {
        getAttached().forEach(pos -> {
            if (!pos.equals(blockPos)) level.removeBlock(pos, true);
        });
        super.preRemoveSideEffects(blockPos, state);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider provider) {
        return this.saveCustomOnly(provider);
    }

    public BlockPos getPortalPosition(ServerLevel serverLevel, BlockPos blockPos) {
        if (this.exitPortal == null) {
            BlockPos pos = findOrCreateValidTeleportPos(serverLevel, blockPos);
            pos = pos.above(10);
            this.setExitPosition(pos);
        }

        return this.exitPortal;
    }

    private static BlockPos findOrCreateValidTeleportPos(ServerLevel serverLevel, BlockPos blockPos) {
        Vec3 vec3 = findExitPortalXZPosTentative(serverLevel, blockPos);
        LevelChunk levelChunk = getChunk(serverLevel, vec3);
        BlockPos pos = findValidSpawnInChunk(levelChunk);

        if (pos == null) pos = BlockPos.containing(vec3.x + (double)0.5F, 75.0F, vec3.z + (double)0.5F);

        return pos;
    }

    private static Vec3 findExitPortalXZPosTentative(ServerLevel serverLevel, BlockPos blockPos) {
        Vec3 posAsVec = new Vec3(blockPos.getX(), 0.0F, blockPos.getZ());
        double yRot = serverLevel.getRandom().nextDouble() * 2 * Math.PI;
        Vec3 posCorner = new Vec3(-Math.sin(yRot), 0, Math.cos(yRot));
        Vec3 pos = posCorner.scale(96);

        int cntA = 8;
        while (!isChunkEmpty(serverLevel, pos) && cntA-- > 0) {
            pos = pos.add(posCorner.scale(16));
        }

        int cntB = 8;
        while (isChunkEmpty(serverLevel, pos) && cntB-- > 0) {
            pos = pos.add(posCorner.scale(16));
        }
        return pos.add(posAsVec);
    }

    private static boolean isChunkEmpty(ServerLevel serverLevel, Vec3 vec3) {
        return getChunk(serverLevel, vec3).getHighestFilledSectionIndex() == -1;
    }

    private static LevelChunk getChunk(Level level, Vec3 vec3) {
        return level.getChunk(Mth.floor(vec3.x / (double)16.0F), Mth.floor(vec3.z / (double)16.0F));
    }

    @Nullable
    private static BlockPos findValidSpawnInChunk(LevelChunk levelChunk) {
        ChunkPos chunkPos = levelChunk.getPos();
        BlockPos minPosInChunk = new BlockPos(chunkPos.getMinBlockX(), 30, chunkPos.getMinBlockZ());
        @SuppressWarnings("removal")
        int position = levelChunk.getHighestSectionPosition() + 16 - 1;
        BlockPos maxPosInChunk = new BlockPos(chunkPos.getMaxBlockX(), position, chunkPos.getMaxBlockZ());
        BlockPos result = null;
        double lastDist = 0.0F;

        for(BlockPos currentPos : BlockPos.betweenClosed(minPosInChunk, maxPosInChunk)) {
            BlockPos abovePos = currentPos.above();
            BlockPos abovestPos = currentPos.above(2);
            if (!levelChunk.getBlockState(abovePos).isCollisionShapeFullBlock(levelChunk, abovePos) && !levelChunk.getBlockState(abovestPos).isCollisionShapeFullBlock(levelChunk, abovestPos)) {
                double distToCenterSqr = currentPos.distToCenterSqr(0.0F, 0.0F, 0.0F);
                if (result == null || distToCenterSqr < lastDist) {
                    result = currentPos;
                    lastDist = distToCenterSqr;
                }
            }
        }

        return result;
    }

    public void copyFrom(EtherealRiftBlockEntity etherealRiftBlockEntity) {
        this.exitPortal = etherealRiftBlockEntity.exitPortal;
        this.destination = etherealRiftBlockEntity.destination;
        this.setChanged();
    }

    public void setExitPosition(BlockPos blockPos) {
        attached.forEach(otherPos -> {
            if (this.level.getBlockEntity(otherPos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity) {
                etherealRiftBlockEntity.exitPortal = blockPos;
                etherealRiftBlockEntity.setChanged();
            }
        });
    }

    public Optional<ResourceKey<Level>> getDestination() {
        return Optional.ofNullable(destination);
    }

    public void setMaxLifespan(long maxLifespan) {
        this.maxLifespan = maxLifespan;
    }

    public void setAttached(List<BlockPos> attached) {
        this.attached = attached;
    }

    public void setDestination(@Nullable ResourceKey<Level> destination) {
        this.destination = destination;
        this.setChanged();
    }

    public void changeDestination(@Nullable ResourceKey<Level> destination) {
        attached.forEach(otherPos -> {
            if (this.level.getBlockEntity(otherPos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity)
                etherealRiftBlockEntity.setDestination(destination);
        });
    }

    public List<BlockPos> getAttached() {
        return attached;
    }
}
