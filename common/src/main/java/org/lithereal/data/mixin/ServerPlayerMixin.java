package org.lithereal.data.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.lithereal.Lithereal;
import org.lithereal.world.feature.EtherealCoreArenaFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Shadow public abstract boolean setGameMode(GameType gameType);

    @Shadow @Final public ServerPlayerGameMode gameMode;

    @Shadow public abstract void onUpdateAbilities();

    @Unique
    public GameType lithereal$originalGameMode = null;

    @Inject(method = "tick", at = @At("HEAD"))
    public void injectEffectiveAdventure(CallbackInfo ci) {
        if (level().dimension().location().equals(Lithereal.id("ethereal_core")) && EtherealCoreArenaFeature.UNCHANGEABLE.isInside(blockPosition())) {
            if (!EtherealCoreArenaFeature.UPDATED.get()) synchronized (EtherealCoreArenaFeature.UPDATED) {
                EtherealCoreArenaFeature.UPDATED.set(true);
            }
            if (lithereal$originalGameMode == null) {
                lithereal$originalGameMode = gameMode.getGameModeForPlayer();
                boolean mayFly = getAbilities().mayfly;
                boolean invulnerable = getAbilities().invulnerable;
                setGameMode(GameType.ADVENTURE);
                getAbilities().mayfly = mayFly;
                getAbilities().invulnerable = invulnerable;
                onUpdateAbilities();
            }
        } else if (lithereal$originalGameMode != null) {
            if (setGameMode(lithereal$originalGameMode)) lithereal$originalGameMode = null;
        }
    }
}
