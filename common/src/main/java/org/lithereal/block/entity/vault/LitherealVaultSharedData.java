package org.lithereal.block.entity.vault;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.vault.VaultSharedData;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LitherealVaultSharedData extends VaultSharedData {
    static final String TAG_NAME = "shared_data";
    static Codec<LitherealVaultConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ItemStack.lenientOptionalFieldOf("display_item").forGetter((litherealVaultSharedData) -> {
            return litherealVaultSharedData.displayItem;
        }), UUIDUtil.CODEC_LINKED_SET.lenientOptionalFieldOf("connected_players", Set.of()).forGetter((litherealVaultSharedData) -> {
            return litherealVaultSharedData.connectedPlayers;
        }), Codec.DOUBLE.lenientOptionalFieldOf("connected_particles_range", LitherealVaultConfig.DEFAULT.deactivationRange()).forGetter((litherealVaultSharedData) -> {
            return litherealVaultSharedData.connectedParticlesRange;
        })).apply(instance, LitherealVaultSharedData::new);
    });
    private ItemStack displayItem;
    private Set<UUID> connectedPlayers;
    private double connectedParticlesRange;
    boolean isDirty;

    LitherealVaultSharedData(ItemStack itemStack, Set<UUID> set, double d) {
        this();
        this.displayItem = ItemStack.EMPTY;
        this.connectedPlayers = new ObjectLinkedOpenHashSet();
        this.connectedParticlesRange = LitherealVaultConfig.DEFAULT.deactivationRange();
        this.displayItem = itemStack;
        this.connectedPlayers.addAll(set);
        this.connectedParticlesRange = d;
    }

    LitherealVaultSharedData() {
        this();
        this.displayItem = ItemStack.EMPTY;
        this.connectedPlayers = new ObjectLinkedOpenHashSet();
        this.connectedParticlesRange = LitherealVaultConfig.DEFAULT.deactivationRange();
    }

    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public boolean hasDisplayItem() {
        return !this.displayItem.isEmpty();
    }

    public void setDisplayItem(ItemStack itemStack) {
        if (!ItemStack.matches(this.displayItem, itemStack)) {
            this.displayItem = itemStack.copy();
            this.markDirty();
        }
    }

    boolean hasConnectedPlayers() {
        return !this.connectedPlayers.isEmpty();
    }

    Set<UUID> getConnectedPlayers() {
        return this.connectedPlayers;
    }

    double connectedParticlesRange() {
        return this.connectedParticlesRange;
    }

    void updateConnectedPlayersWithinRange(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultServerData litherealVaultServerData, LitherealVaultConfig litherealVaultConfig, double d) {
        Set<UUID> set = (Set)litherealVaultConfig.playerDetector().detect(serverLevel, litherealVaultConfig.entitySelector(), blockPos, d, false).stream().filter((uUID) -> {
            return !litherealVaultServerData.getRewardedPlayers().contains(uUID);
        }).collect(Collectors.toSet());
        if (!this.connectedPlayers.equals(set)) {
            this.connectedPlayers = set;
            this.markDirty();
        }

    }

    private void markDirty() {
        this.isDirty = true;
    }

    void set(LitherealVaultSharedData litherealVaultSharedData) {
        this.displayItem = litherealVaultSharedData.displayItem;
        this.connectedPlayers = litherealVaultSharedData.connectedPlayers;
        this.connectedParticlesRange = litherealVaultSharedData.connectedParticlesRange;
    }
}