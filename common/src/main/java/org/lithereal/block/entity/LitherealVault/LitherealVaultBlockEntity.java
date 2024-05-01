package org.lithereal.block.entity.LitherealVault;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import net.minecraft.world.level.block.entity.vault.VaultSharedData;
import net.minecraft.world.level.block.entity.vault.VaultState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.custom.LitherealVaultBlock;
import org.slf4j.Logger;

import java.util.*;

public class LitherealVaultBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final LitherealVaultServerData serverData = new LitherealVaultServerData();
    private final LitherealVaultSharedData sharedData = new LitherealVaultSharedData();
    private final LitherealVaultClientData clientData = new LitherealVaultClientData();
    private Object config;

    public LitherealVaultBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityType.VAULT, blockPos, blockState);
        this.config = LitherealVaultConfig.DEFAULT;
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return (CompoundTag)Util.make(new CompoundTag(), (compoundTag) -> {
            compoundTag.put("shared_data", encode(LitherealVaultSharedData.CODEC, this.sharedData, provider));
        });
    }

    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.put("config", encode(LitherealVaultConfig.CODEC, this.config, provider));
        compoundTag.put("shared_data", encode(LitherealVaultSharedData.CODEC, this.sharedData, provider));
        compoundTag.put("server_data", encode(LitherealVaultServerData.CODEC, this.serverData, provider));
    }

    private static <T> Tag encode(Codec<LitherealVaultConfig> codec, Object object, HolderLookup.Provider provider) {
        return (Tag)codec.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), (LitherealVaultConfig) object).getOrThrow();
    }

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        DynamicOps<Tag> dynamicOps = provider.createSerializationContext(NbtOps.INSTANCE);
        DataResult var10000;
        Logger var10001;
        Optional var4;
        if (compoundTag.contains("server_data")) {
            var10000 = LitherealVaultServerData.CODEC.parse(dynamicOps, compoundTag.get("server_data"));
            var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var4 = var10000.resultOrPartial(var10001::error);
            LitherealVaultServerData var5 = this.serverData;
            Objects.requireNonNull(var5);
            var4.ifPresent(var5::set);
        }

        if (compoundTag.contains("config")) {
            var10000 = LitherealVaultConfig.CODEC.parse(dynamicOps, compoundTag.get("config"));
            var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial(var10001::error).ifPresent((litherealVaultConfig) -> {
                this.config = litherealVaultConfig;
            });
        }

        if (compoundTag.contains("shared_data")) {
            var10000 = LitherealVaultSharedData.CODEC.parse(dynamicOps, compoundTag.get("shared_data"));
            var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var4 = var10000.resultOrPartial(var10001::error);
            VaultSharedData var6 = this.sharedData;
            Objects.requireNonNull(var6);
            var4.ifPresent(var6::set);
        }

    }

    public VaultServerData getServerData() {
        return this.level != null && !this.level.isClientSide ? this.serverData : null;
    }

    public VaultSharedData getSharedData() {
        return this.sharedData;
    }

    public LitherealVaultClientData getClientData() {
        return this.clientData;
    }

    public LitherealVaultConfig getConfig() {
        return (LitherealVaultConfig) this.config;
    }

    @VisibleForTesting
    public void setConfig(Object vaultConfig) {
        this.config = vaultConfig;
    }

    public static final class Client {
        private static final int PARTICLE_TICK_RATE = 20;
        private static final float IDLE_PARTICLE_CHANCE = 0.5F;
        private static final float AMBIENT_SOUND_CHANCE = 0.02F;
        private static final int ACTIVATION_PARTICLE_COUNT = 20;
        private static final int DEACTIVATION_PARTICLE_COUNT = 20;

        public Client() {
        }

        public static void tick(Level level, BlockPos blockPos, BlockState blockState, LitherealVaultClientData litherealVaultClientData, LitherealVaultSharedData litherealVaultSharedData) {
            LitherealVaultClientData.updateDisplayItemSpin();
            if (level.getGameTime() % 20L == 0L) {
                emitConnectionParticlesForNearbyPlayers(level, blockPos, blockState, litherealVaultSharedData);
            }

            emitIdleParticles(level, blockPos, litherealVaultSharedData, (Boolean)blockState.getValue(VaultBlock.OMINOUS) ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.SMALL_FLAME);
            playIdleSounds(level, blockPos, litherealVaultSharedData);
        }

        public static void emitActivationParticles(Level level, BlockPos blockPos, BlockState blockState, LitherealVaultSharedData litherealVaultSharedData, ParticleOptions particleOptions) {
            emitConnectionParticlesForNearbyPlayers(level, blockPos, blockState, litherealVaultSharedData);
            RandomSource randomSource = level.random;

            for(int i = 0; i < 20; ++i) {
                Vec3 vec3 = randomPosInsideCage(blockPos, randomSource);
                level.addParticle(ParticleTypes.SMOKE, vec3.x(), vec3.y(), vec3.z(), 0.0, 0.0, 0.0);
                level.addParticle(particleOptions, vec3.x(), vec3.y(), vec3.z(), 0.0, 0.0, 0.0);
            }

        }

        public static void emitDeactivationParticles(Level level, BlockPos blockPos, ParticleOptions particleOptions) {
            RandomSource randomSource = level.random;

            for(int i = 0; i < 20; ++i) {
                Vec3 vec3 = randomPosCenterOfCage(blockPos, randomSource);
                Vec3 vec32 = new Vec3(randomSource.nextGaussian() * 0.02, randomSource.nextGaussian() * 0.02, randomSource.nextGaussian() * 0.02);
                level.addParticle(particleOptions, vec3.x(), vec3.y(), vec3.z(), vec32.x(), vec32.y(), vec32.z());
            }

        }

        private static void emitIdleParticles(Level level, BlockPos blockPos, LitherealVaultSharedData litherealVaultSharedData, ParticleOptions particleOptions) {
            RandomSource randomSource = level.getRandom();
            if (randomSource.nextFloat() <= 0.5F) {
                Vec3 vec3 = randomPosInsideCage(blockPos, randomSource);
                level.addParticle(ParticleTypes.SMOKE, vec3.x(), vec3.y(), vec3.z(), 0.0, 0.0, 0.0);
                if (shouldDisplayActiveEffects(litherealVaultSharedData)) {
                    level.addParticle(particleOptions, vec3.x(), vec3.y(), vec3.z(), 0.0, 0.0, 0.0);
                }
            }

        }

        private static void emitConnectionParticlesForPlayer(Level level, Vec3 vec3, Player player) {
            RandomSource randomSource = level.random;
            Vec3 vec32 = vec3.vectorTo(player.position().add(0.0, (double)(player.getBbHeight() / 2.0F), 0.0));
            int i = Mth.nextInt(randomSource, 2, 5);

            for(int j = 0; j < i; ++j) {
                Vec3 vec33 = vec32.offsetRandom(randomSource, 1.0F);
                level.addParticle(ParticleTypes.VAULT_CONNECTION, vec3.x(), vec3.y(), vec3.z(), vec33.x(), vec33.y(), vec33.z());
            }

        }

        private static void emitConnectionParticlesForNearbyPlayers(Level level, BlockPos blockPos, BlockState blockState, LitherealVaultSharedData litherealVaultSharedData) {
            Set<UUID> set = litherealVaultSharedData.getConnectedPlayers();
            if (!set.isEmpty()) {
                Vec3 vec3 = keyholePos(blockPos, (Direction)blockState.getValue(LitherealVaultBlock.FACING));
                Iterator var6 = set.iterator();

                while(var6.hasNext()) {
                    UUID uUID = (UUID)var6.next();
                    Player player = level.getPlayerByUUID(uUID);
                    if (player != null && isWithinConnectionRange(blockPos, litherealVaultSharedData, player)) {
                        emitConnectionParticlesForPlayer(level, vec3, player);
                    }
                }

            }
        }

        private static boolean isWithinConnectionRange(BlockPos blockPos, LitherealVaultSharedData litherealVaultSharedData, Player player) {
            return player.blockPosition().distSqr(blockPos) <= Mth.square(litherealVaultSharedData.connectedParticlesRange());
        }

        private static void playIdleSounds(Level level, BlockPos blockPos, LitherealVaultSharedData litherealVaultSharedData) {
            if (shouldDisplayActiveEffects(litherealVaultSharedData)) {
                RandomSource randomSource = level.getRandom();
                if (randomSource.nextFloat() <= 0.02F) {
                    level.playLocalSound(blockPos, SoundEvents.VAULT_AMBIENT, SoundSource.BLOCKS, randomSource.nextFloat() * 0.25F + 0.75F, randomSource.nextFloat() + 0.5F, false);
                }

            }
        }

        public static boolean shouldDisplayActiveEffects(LitherealVaultSharedData litherealVaultSharedData) {
            return litherealVaultSharedData.hasDisplayItem();
        }

        private static Vec3 randomPosCenterOfCage(BlockPos blockPos, RandomSource randomSource) {
            return Vec3.atLowerCornerOf(blockPos).add(Mth.nextDouble(randomSource, 0.4, 0.6), Mth.nextDouble(randomSource, 0.4, 0.6), Mth.nextDouble(randomSource, 0.4, 0.6));
        }

        private static Vec3 randomPosInsideCage(BlockPos blockPos, RandomSource randomSource) {
            return Vec3.atLowerCornerOf(blockPos).add(Mth.nextDouble(randomSource, 0.1, 0.9), Mth.nextDouble(randomSource, 0.25, 0.75), Mth.nextDouble(randomSource, 0.1, 0.9));
        }

        private static Vec3 keyholePos(BlockPos blockPos, Direction direction) {
            return Vec3.atBottomCenterOf(blockPos).add((double)direction.getStepX() * 0.5, 1.75, (double)direction.getStepZ() * 0.5);
        }
    }

    public static final class Server {
        private static final int UNLOCKING_DELAY_TICKS = 14;
        private static final int DISPLAY_CYCLE_TICK_RATE = 20;
        private static final int INSERT_FAIL_SOUND_BUFFER_TICKS = 15;

        public Server() {
        }

        public static void tick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, LitherealVaultConfig litherealVaultConfig, LitherealVaultServerData litherealVaultServerData, LitherealVaultSharedData litherealVaultSharedData) {
            VaultState litherealVaultState = blockState.getValue(LitherealVaultBlock.STATE);
            if (shouldCycleDisplayItem(serverLevel.getGameTime(), litherealVaultState)) {
                cycleDisplayItemFromLootTable(serverLevel, litherealVaultState, litherealVaultConfig, litherealVaultSharedData, blockPos);
            }

            BlockState blockState2 = blockState;
            if (serverLevel.getGameTime() >= litherealVaultServerData.stateUpdatingResumesAt()) {
                blockState2 = (BlockState)blockState2.setValue(LitherealVaultBlock.STATE, litherealVaultState.tickAndGetNext(serverLevel, blockPos, litherealVaultConfig, litherealVaultServerData, litherealVaultSharedData));
                if (!blockState.equals(blockState2)) {
                    setLitherealVaultState(serverLevel, blockPos, blockState, blockState2, litherealVaultConfig, litherealVaultSharedData);
                }
            }

            if (litherealVaultServerData.isDirty || litherealVaultSharedData.isDirty) {
                LitherealVaultBlockEntity.setChanged(serverLevel, blockPos, blockState);
                if (litherealVaultSharedData.isDirty) {
                    serverLevel.sendBlockUpdated(blockPos, blockState, blockState2, 2);
                }

                litherealVaultServerData.isDirty = false;
                litherealVaultSharedData.isDirty = false;
            }

        }

        public static void tryInsertKey(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, LitherealVaultConfig litherealVaultConfig, LitherealVaultServerData litherealVaultServerData, LitherealVaultSharedData litherealVaultSharedData, Player player, ItemStack itemStack) {
            VaultState litherealVaultState = (VaultState) blockState.getValue(LitherealVaultBlock.STATE);
            if (canEjectReward(litherealVaultConfig, litherealVaultState)) {
                if (!isValidToInsert(litherealVaultConfig, itemStack)) {
                    playInsertFailSound(serverLevel, litherealVaultServerData, blockPos);
                } else if (litherealVaultServerData.hasRewardedPlayer(player)) {
                    playInsertFailSound(serverLevel, litherealVaultServerData, blockPos);
                } else {
                    List<ItemStack> list = resolveItemsToEject(serverLevel, litherealVaultConfig, blockPos, player);
                    if (!list.isEmpty()) {
                        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                        if (!player.isCreative()) {
                            itemStack.shrink(litherealVaultConfig.keyItem().getCount());
                        }

                        unlock(serverLevel, blockState, blockPos, litherealVaultConfig, litherealVaultServerData, litherealVaultSharedData, list);
                        litherealVaultServerData.addToRewardedPlayers(player);
                        litherealVaultSharedData.updateConnectedPlayersWithinRange(serverLevel, blockPos, litherealVaultServerData, litherealVaultConfig, litherealVaultConfig.deactivationRange());
                    }
                }
            }
        }

        static void setLitherealVaultState(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, BlockState blockState2, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData) {
            VaultState litherealVaultState = blockState.getValue(LitherealVaultBlock.STATE);
            VaultState litherealVaultState2 = blockState2.getValue(LitherealVaultBlock.STATE);
            serverLevel.setBlock(blockPos, blockState2, 3);
            litherealVaultState.onTransition(serverLevel, blockPos, litherealVaultState2, litherealVaultConfig, litherealVaultSharedData, (Boolean)blockState2.getValue(VaultBlock.OMINOUS));
        }

        static void cycleDisplayItemFromLootTable(ServerLevel serverLevel, VaultState litherealVaultState, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, BlockPos blockPos) {
            if (!canEjectReward(litherealVaultConfig, litherealVaultState)) {
                litherealVaultSharedData.setDisplayItem(ItemStack.EMPTY);
            } else {
                ItemStack itemStack = getRandomDisplayItemFromLootTable(serverLevel, blockPos, (ResourceKey) litherealVaultConfig.overrideLootTableToDisplay().orElse(litherealVaultConfig.lootTable()));
                litherealVaultSharedData.setDisplayItem(itemStack);
            }
        }

        private static ItemStack getRandomDisplayItemFromLootTable(ServerLevel serverLevel, BlockPos blockPos, ResourceKey<LootTable> resourceKey) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourceKey);
            LootParams lootParams = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).create(LootContextParamSets.VAULT);
            List<ItemStack> list = lootTable.getRandomItems(lootParams);
            return list.isEmpty() ? ItemStack.EMPTY : (ItemStack)Util.getRandom(list, serverLevel.getRandom());
        }

        private static void unlock(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultServerData litherealVaultServerData, LitherealVaultSharedData litherealVaultSharedData, List<ItemStack> list) {
            litherealVaultServerData.setItemsToEject(list);
            litherealVaultSharedData.setDisplayItem(litherealVaultServerData.getNextItemToEject());
            litherealVaultServerData.pauseStateUpdatingUntil(serverLevel.getGameTime() + 14L);
            setLitherealVaultState(serverLevel, blockPos, blockState, (BlockState)blockState.setValue(LitherealVaultBlock.STATE, LitherealVaultBlock.UNLOCKING), litherealVaultConfig, litherealVaultSharedData);
        }

        private static List<ItemStack> resolveItemsToEject(ServerLevel serverLevel, LitherealVaultConfig vaultConfig, BlockPos blockPos, Player player) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(vaultConfig.lootTable());
            LootParams lootParams = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).create(LootContextParamSets.VAULT);
            return lootTable.getRandomItems(lootParams);
        }

        private static boolean canEjectReward(LitherealVaultConfig litherealVaultConfig, VaultState litherealVaultState) {
            return litherealVaultConfig.lootTable() != BuiltInLootTables.EMPTY && !litherealVaultConfig.keyItem().isEmpty() && litherealVaultState != LitherealVaultState.INACTIVE;
        }

        private static boolean isValidToInsert(LitherealVaultConfig litherealVaultConfig, ItemStack itemStack) {
            return ItemStack.isSameItemSameComponents(itemStack, litherealVaultConfig.keyItem()) && itemStack.getCount() >= litherealVaultConfig.keyItem().getCount();
        }

        private static boolean shouldCycleDisplayItem(long l, VaultState litherealVaultState) {
            return l % 20L == 0L && litherealVaultState == LitherealVaultState.ACTIVE;
        }

        private static void playInsertFailSound(ServerLevel serverLevel, LitherealVaultServerData litherealVaultServerData, BlockPos blockPos) {
            if (serverLevel.getGameTime() >= litherealVaultServerData.getLastInsertFailTimestamp() + 15L) {
                serverLevel.playSound((Player)null, blockPos, SoundEvents.VAULT_INSERT_ITEM_FAIL, SoundSource.BLOCKS);
                litherealVaultServerData.setLastInsertFailTimestamp(serverLevel.getGameTime());
            }

        }
    }
}