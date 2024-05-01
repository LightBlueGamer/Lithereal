package org.lithereal.block.entity.LitherealVault;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class LitherealVaultServerData {
    static final String TAG_NAME = "server_data";
    static Codec<LitherealVaultConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(UUIDUtil.CODEC_LINKED_SET.lenientOptionalFieldOf("rewarded_players", Set.of()).forGetter((litherealVaultServerData) -> {
            return litherealVaultServerData.rewardedPlayers;
        }), Codec.LONG.lenientOptionalFieldOf("state_updating_resumes_at", 0L).forGetter((litherealVaultServerData) -> {
            return litherealVaultServerData.stateUpdatingResumesAt;
        }), ItemStack.CODEC.listOf().lenientOptionalFieldOf("items_to_eject", List.of()).forGetter((litherealVaultServerData) -> {
            return litherealVaultServerData.itemsToEject;
        }), Codec.INT.lenientOptionalFieldOf("total_ejections_needed", 0).forGetter((litherealVaultServerData) -> {
            return litherealVaultServerData.totalEjectionsNeeded;
        })).apply(instance, LitherealVaultServerData::new);
    });
    private static final int MAX_REWARD_PLAYERS = 128;
    private final Set<UUID> rewardedPlayers = new ObjectLinkedOpenHashSet();
    private long stateUpdatingResumesAt;
    private final List<ItemStack> itemsToEject = new ObjectArrayList();
    private long lastInsertFailTimestamp;
    private int totalEjectionsNeeded;
    boolean isDirty;

    LitherealVaultServerData(Set<UUID> set, long l, List<ItemStack> list, int i) {
        this.rewardedPlayers.addAll(set);
        this.stateUpdatingResumesAt = l;
        this.itemsToEject.addAll(list);
        this.totalEjectionsNeeded = i;
    }

    LitherealVaultServerData() {
    }

    void setLastInsertFailTimestamp(long l) {
        this.lastInsertFailTimestamp = l;
    }

    long getLastInsertFailTimestamp() {
        return this.lastInsertFailTimestamp;
    }

    Set<UUID> getRewardedPlayers() {
        return this.rewardedPlayers;
    }

    boolean hasRewardedPlayer(Player player) {
        return this.rewardedPlayers.contains(player.getUUID());
    }

    @VisibleForTesting
    public void addToRewardedPlayers(Player player) {
        this.rewardedPlayers.add(player.getUUID());
        if (this.rewardedPlayers.size() > 128) {
            Iterator<UUID> iterator = this.rewardedPlayers.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }

        this.markChanged();
    }

    long stateUpdatingResumesAt() {
        return this.stateUpdatingResumesAt;
    }

    void pauseStateUpdatingUntil(long l) {
        this.stateUpdatingResumesAt = l;
        this.markChanged();
    }

    List<ItemStack> getItemsToEject() {
        return this.itemsToEject;
    }

    void markEjectionFinished() {
        this.totalEjectionsNeeded = 0;
        this.markChanged();
    }

    void setItemsToEject(List<ItemStack> list) {
        this.itemsToEject.clear();
        this.itemsToEject.addAll(list);
        this.totalEjectionsNeeded = this.itemsToEject.size();
        this.markChanged();
    }

    ItemStack getNextItemToEject() {
        return this.itemsToEject.isEmpty() ? ItemStack.EMPTY : (ItemStack)Objects.requireNonNullElse((ItemStack)this.itemsToEject.get(this.itemsToEject.size() - 1), ItemStack.EMPTY);
    }

    ItemStack popNextItemToEject() {
        if (this.itemsToEject.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.markChanged();
            return (ItemStack)Objects.requireNonNullElse((ItemStack)this.itemsToEject.remove(this.itemsToEject.size() - 1), ItemStack.EMPTY);
        }
    }

    void set(LitherealVaultServerData litherealVaultServerData) {
        this.stateUpdatingResumesAt = litherealVaultServerData.stateUpdatingResumesAt();
        this.itemsToEject.clear();
        this.itemsToEject.addAll(litherealVaultServerData.itemsToEject);
        this.rewardedPlayers.clear();
        this.rewardedPlayers.addAll(litherealVaultServerData.rewardedPlayers);
    }

    private void markChanged() {
        this.isDirty = true;
    }

    public float ejectionProgress() {
        return this.totalEjectionsNeeded == 1 ? 1.0F : 1.0F - Mth.inverseLerp((float)this.getItemsToEject().size(), 1.0F, (float)this.totalEjectionsNeeded);
    }
}