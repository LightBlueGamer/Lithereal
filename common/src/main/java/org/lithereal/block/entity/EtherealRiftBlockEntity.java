package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.EtherealRiftBlock;
import org.lithereal.block.ModBlocks;

import java.util.Optional;

public class EtherealRiftBlockEntity extends EtherealCorePortalBlockEntity {
    private @Nullable ResourceKey<Level> destination = null;
    private long age;
    @Nullable
    private BlockPos exitPortal;

    public EtherealRiftBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public EtherealRiftBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.ETHEREAL_RIFT.get(), blockPos, blockState);
    }

    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (destination != null) compoundTag.putString("destination", destination.location().toString());
        compoundTag.putLong("age", this.age);
        if (this.exitPortal != null) {
            compoundTag.put("exit_portal", NbtUtils.writeBlockPos(this.exitPortal));
        }
    }

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("destination")) this.destination = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(compoundTag.getString("destination")));
        this.age = compoundTag.getLong("age");
        NbtUtils.readBlockPos(compoundTag, "exit_portal").filter(Level::isInSpawnableBounds).ifPresent((blockPos) -> this.exitPortal = blockPos);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EtherealRiftBlockEntity etherealRiftBlockEntity) {
        ++etherealRiftBlockEntity.age;
        if (etherealRiftBlockEntity.age % 1200L == 0L && blockState.getBlock() instanceof EtherealRiftBlock) {
            EtherealRiftBlock.destroyAttachedBlocks(level, blockPos, blockState);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
    }

    public Vec3 getPortalPosition(ServerLevel serverLevel, BlockPos blockPos) {
        if (this.exitPortal == null) {
            BlockPos pos = findOrCreateValidTeleportPos(serverLevel, blockPos);
            pos = pos.above(10);
            this.setExitPosition(pos);
        }

        BlockPos portalPos = findExitPosition(serverLevel, this.exitPortal);
        return portalPos.getBottomCenter();
    }

    public static BlockPos findExitPosition(Level level, BlockPos blockPos) {
        BlockPos blockPos2 = findTallestBlock(level, blockPos.offset(0, 2, 0), 5);
        return blockPos2.above();
    }

    private static BlockPos findOrCreateValidTeleportPos(ServerLevel serverLevel, BlockPos blockPos) {
        Vec3 vec3 = findExitPortalXZPosTentative(serverLevel, blockPos);
        LevelChunk levelChunk = getChunk(serverLevel, vec3);
        BlockPos pos = findValidSpawnInChunk(levelChunk);

        if (pos == null) {
            BlockPos fallbackPos = BlockPos.containing(vec3.x + (double)0.5F, 75.0F, vec3.z + (double)0.5F);
            for (BlockPos newPos : BlockPos.betweenClosed(fallbackPos.west().north(), fallbackPos.east().south()))
                serverLevel.setBlock(newPos, ModBlocks.PURE_ETHEREAL_CRYSTAL_BLOCK.get().defaultBlockState(), 3);
            pos = fallbackPos;
        }

        return findTallestBlock(serverLevel, pos, 16);
    }

    private static Vec3 findExitPortalXZPosTentative(ServerLevel serverLevel, BlockPos blockPos) {
        Vec3 posAsVec = new Vec3(blockPos.getX(), 0.0F, blockPos.getZ());
        Vec3 posCorner = posAsVec.normalize();
        Vec3 pos = posCorner.scale(96);

        int cntA = 16;
        while (!isChunkEmpty(serverLevel, pos) && cntA-- > 0) {
            pos = pos.add(posCorner.scale(-16));
        }

        int cntB = 16;
        while (isChunkEmpty(serverLevel, pos) && cntB-- > 0) {
            pos = pos.add(posCorner.scale(16));
        }
        return pos.add(posAsVec.scale(-1));
    }

    private static boolean isChunkEmpty(ServerLevel serverLevel, Vec3 vec3) {
        return getChunk(serverLevel, vec3).getHighestFilledSectionIndex() == -1;
    }

    private static BlockPos findTallestBlock(BlockGetter blockGetter, BlockPos blockPos, int i) {
        BlockPos blockPos2 = null;

        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                if (j != 0 || k != 0) {
                    for(int l = blockGetter.getMaxBuildHeight() - 1; l > (blockPos2 == null ? blockGetter.getMinBuildHeight() : blockPos2.getY()); --l) {
                        BlockPos blockPos3 = new BlockPos(blockPos.getX() + j, l, blockPos.getZ() + k);
                        BlockState blockState = blockGetter.getBlockState(blockPos3);
                        if (blockState.isCollisionShapeFullBlock(blockGetter, blockPos3) && !blockState.is(Blocks.BEDROCK)) {
                            blockPos2 = blockPos3;
                            break;
                        }
                    }
                }
            }
        }

        return blockPos2 == null ? blockPos : blockPos2;
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

    public void setExitPosition(BlockPos blockPos) {
        this.exitPortal = blockPos;
        this.setChanged();
    }

    public Optional<ResourceKey<Level>> getDestination() {
        return Optional.ofNullable(destination);
    }

    public void setDestination(@Nullable ResourceKey<Level> destination) {
        this.destination = destination;
    }
}
