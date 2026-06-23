package org.lithereal.data.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.lithereal.Lithereal;
import org.lithereal.mob_effect.ModMobEffects;
//? neoforge {
import org.lithereal.neoforge.ModEnumParams;
//?}
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.HeartType.class)
public class HeartTypeMixin {
    @WrapMethod(method = "forPlayer")
    private static Gui.HeartType forPlayer(Player player, Operation<Gui.HeartType> original) {
        if (player.hasEffect(Lithereal.asHolder(ModMobEffects.RETRIBUTION)))
        //? fabric {
            /*return Gui.HeartType.RETRIBUTED;
        *///?}
        //? neoforge {
            return ModEnumParams.RETRIBUTED_ENUM_PROXY.getValue();
        //?}
        return original.call(player);
    }
}