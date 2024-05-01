package org.lithereal.block.entity.LitherealVault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity.Server;
import net.minecraft.world.level.block.entity.vault.VaultState;
import net.minecraft.world.phys.Vec3;

public enum LitherealVaultState implements StringRepresentable {
    INACTIVE("inactive", LitherealVaultState.LightLevel.HALF_LIT) {
        protected void onEnter(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
            litherealVaultSharedData.setDisplayItem(ItemStack.EMPTY);
            serverLevel.levelEvent(3016, blockPos, bl ? 1 : 0);
        }
    },
    ACTIVE("active", LitherealVaultState.LightLevel.LIT) {
        protected void onEnter(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
            if (!litherealVaultSharedData.hasDisplayItem()) {
                Server.cycleDisplayItemFromLootTable(serverLevel, this, litherealVaultConfig, litherealVaultSharedData, blockPos);
            }

            serverLevel.levelEvent(3015, blockPos, bl ? 1 : 0);
        }
    },
    UNLOCKING("unlocking", LitherealVaultState.LightLevel.LIT) {
        protected void onEnter(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
            serverLevel.playSound((Player)null, blockPos, SoundEvents.VAULT_INSERT_ITEM, SoundSource.BLOCKS);
        }
    },
    EJECTING("ejecting", LitherealVaultState.LightLevel.LIT) {
        protected void onEnter(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
            serverLevel.playSound((Player)null, blockPos, SoundEvents.VAULT_OPEN_SHUTTER, SoundSource.BLOCKS);
        }

        protected void onExit(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData) {
            serverLevel.playSound((Player)null, blockPos, SoundEvents.VAULT_CLOSE_SHUTTER, SoundSource.BLOCKS);
        }
    };

    private static final int UPDATE_CONNECTED_PLAYERS_TICK_RATE = 20;
    private static final int DELAY_BETWEEN_EJECTIONS_TICKS = 20;
    private static final int DELAY_AFTER_LAST_EJECTION_TICKS = 20;
    private static final int DELAY_BEFORE_FIRST_EJECTION_TICKS = 20;
    private final String stateName;
    private final LightLevel lightLevel;

    LitherealVaultState(final String string2, final LightLevel lightLevel) {
        this.stateName = string2;
        this.lightLevel = lightLevel;
    }

    public String getSerializedName() {
        return this.stateName;
    }

    public int lightLevel() {
        return this.lightLevel.value;
    }

    public LitherealVaultState tickAndGetNext(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultServerData litherealVaultServerData, LitherealVaultSharedData litherealVaultSharedData) {
        LitherealVaultState var10000;
        switch (this.ordinal()) {
            case 0:
                var10000 = updateStateForConnectedPlayers(serverLevel, blockPos, litherealVaultConfig, litherealVaultServerData, litherealVaultSharedData, litherealVaultConfig.activationRange());
                break;
            case 1:
                var10000 = updateStateForConnectedPlayers(serverLevel, blockPos, litherealVaultConfig, litherealVaultServerData, litherealVaultSharedData, litherealVaultConfig.deactivationRange());
                break;
            case 2:
                litherealVaultServerData.pauseStateUpdatingUntil(serverLevel.getGameTime() + 20L);
                var10000 = EJECTING;
                break;
            case 3:
                if (litherealVaultServerData.getItemsToEject().isEmpty()) {
                    litherealVaultServerData.markEjectionFinished();
                    var10000 = updateStateForConnectedPlayers(serverLevel, blockPos, litherealVaultConfig, litherealVaultServerData, litherealVaultSharedData, litherealVaultConfig.deactivationRange());
                } else {
                    float f = litherealVaultServerData.ejectionProgress();
                    this.ejectResultItem(serverLevel, blockPos, litherealVaultServerData.popNextItemToEject(), f);
                    litherealVaultServerData.setDisplayItem(litherealVaultServerData.getNextItemToEject());
                    boolean bl = litherealVaultServerData.getItemsToEject().isEmpty();
                    int i = bl ? 20 : 20;
                    litherealVaultServerData.pauseStateUpdatingUntil(serverLevel.getGameTime() + (long)i);
                    var10000 = EJECTING;
                }
                break;
            default:
                throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    private static LitherealVaultState updateStateForConnectedPlayers(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultServerData litherealVaultServerData, LitherealVaultSharedData litherealVaultSharedData, double d) {
        litherealVaultSharedData.updateConnectedPlayersWithinRange(serverLevel, blockPos, litherealVaultServerData, litherealVaultConfig, d);
        litherealVaultServerData.pauseStateUpdatingUntil(serverLevel.getGameTime() + 20L);
        return litherealVaultSharedData.hasConnectedPlayers() ? ACTIVE : INACTIVE;
    }

    public void onTransition(ServerLevel serverLevel, BlockPos blockPos, VaultState litherealVaultState2, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
        this.onExit(serverLevel, blockPos, litherealVaultConfig, litherealVaultSharedData);
        litherealVaultState2.onEnter(serverLevel, blockPos, litherealVaultConfig, litherealVaultSharedData, bl);
    }

    protected void onEnter(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig litherealVaultConfig, LitherealVaultSharedData litherealVaultSharedData, boolean bl) {
    }

    protected void onExit(ServerLevel serverLevel, BlockPos blockPos, LitherealVaultConfig vaultConfig, LitherealVaultSharedData vaultSharedData) {
    }

    private void ejectResultItem(ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, float f) {
        DefaultDispenseItemBehavior.spawnItem(serverLevel, itemStack, 2, Direction.UP, Vec3.atBottomCenterOf(blockPos).relative(Direction.UP, 1.2));
        serverLevel.levelEvent(3017, blockPos, 0);
        serverLevel.playSound((Player)null, blockPos, SoundEvents.VAULT_EJECT_ITEM, SoundSource.BLOCKS, 1.0F, 0.8F + 0.4F * f);
    }

    private static enum LightLevel {
        HALF_LIT(6),
        LIT(12);

        final int value;

        private LightLevel(final int j) {
            this.value = j;
        }
    }
}