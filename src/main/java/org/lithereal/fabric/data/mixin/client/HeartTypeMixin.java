package org.lithereal.fabric.data.mixin.client;

//? fabric {
/*import net.minecraft.client.gui.Gui;
import net.minecraft.resources.Identifier;
import org.lithereal.Lithereal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Gui.HeartType.class)
enum HeartTypeMixin {
    RETRIBUTED(Lithereal.id("hud/heart/retributed_full"), Lithereal.id("hud/heart/retributed_full_blinking"), Lithereal.id("hud/heart/retributed_half"), Lithereal.id("hud/heart/retributed_half_blinking"), Lithereal.id("hud/heart/retributed_hardcore_full"), Lithereal.id("hud/heart/retributed_hardcore_full_blinking"), Lithereal.id("hud/heart/retributed_hardcore_half"), Lithereal.id("hud/heart/retributed_hardcore_half_blinking"));
    
    @Shadow
    HeartTypeMixin(final Identifier full, final Identifier fullBlinking, final Identifier half, final Identifier halfBlinking, final Identifier hardcoreFull, final Identifier hardcoreFullBlinking, final Identifier hardcoreHalf, final Identifier hardcoreHalfBlinking) {
    }
}
*///?}