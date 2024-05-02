package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.vault.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LitherealVaultBlockEntity extends BlockEntity {
    private final @NotNull VaultBlockEntity surrogate;

    public LitherealVaultBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.LITHEREAL_VAULT.get(), blockPos, blockState);
        surrogate = new VaultBlockEntity(BlockPos.ZERO, blockState);
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return surrogate.getUpdateTag(provider);
    }

    public void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        surrogate.saveAdditional(compoundTag, provider);
    }

    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        surrogate.loadAdditional(compoundTag, provider);
    }

    public void setConfig(VaultConfig vaultConfig) {
        surrogate.setConfig(vaultConfig);
    }

    public VaultConfig getConfig() {
        return surrogate.getConfig();
    }

    public VaultSharedData getSharedData() {
        return surrogate.getSharedData();
    }

    @Nullable
    public VaultServerData getServerData() {
        return surrogate.getServerData();
    }

    public VaultClientData getClientData() {
        return surrogate.getClientData();
    }
}
