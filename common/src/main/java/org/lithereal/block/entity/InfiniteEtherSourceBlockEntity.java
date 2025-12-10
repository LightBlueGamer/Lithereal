package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.util.ether.IEnergyUser;
import org.lithereal.util.ether.IEnergyUserProvider;
import org.lithereal.util.ether.InfiniteEtherGenerator;

public class InfiniteEtherSourceBlockEntity extends BlockEntity implements IEnergyUserProvider {
    @Nullable
    private TagKey<Biome> inBiomes;
    private final InfiniteEtherGenerator generator;

    @Override
    public IEnergyUser getEnergyUser() {
        return generator;
    }

    @Override
    public TransferMode getTransferModeForDirection(Direction direction) {
        if (!isOn()) return TransferMode.NONE;
        return TransferMode.BOTH;
    }

    public InfiniteEtherSourceBlockEntity(BlockEntityType<? extends InfiniteEtherSourceBlockEntity> entityType, BlockPos blockPos, BlockState blockState, @Nullable TagKey<Biome> inBiomes, int transferRate) {
        super(entityType, blockPos, blockState);
        this.inBiomes = inBiomes;
        this.generator = new InfiniteEtherGenerator(transferRate);
    }

    public InfiniteEtherSourceBlockEntity(BlockPos blockPos, BlockState blockState, @Nullable TagKey<Biome> inBiomes, int transferRate) {
        this(ModBlockEntities.INFINITE_ETHER_GENERATOR.get(), blockPos, blockState, inBiomes, transferRate);
    }

    public InfiniteEtherSourceBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.INFINITE_ETHER_GENERATOR.get(), blockPos, blockState, null, 10000);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, InfiniteEtherSourceBlockEntity pEntity) {
        if (level.isClientSide()) return;

        pEntity.generator.tick(pEntity);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbt, provider);
        saveEnergy(nbt, provider);
        if (inBiomes != null) nbt.putString("biomes", inBiomes.location().toString());
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        loadEnergy(nbt, provider);
        if (nbt.contains("biomes")) this.inBiomes = TagKey.create(Registries.BIOME, ResourceLocation.parse(nbt.getString("biomes")));
    }

    public boolean isOn() {
        if (inBiomes != null) {
            return this.level.getBiome(getBlockPos()).is(inBiomes);
        }
        return true;
    }
}
