package org.lithereal.neoforge;

//? neoforge {
import net.minecraft.client.gui.Gui;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import org.lithereal.Lithereal;

public class ModEnumParams {
    public static final EnumProxy<Gui.HeartType> RETRIBUTED_ENUM_PROXY = new EnumProxy<>(
            Gui.HeartType.class, Lithereal.id("hud/heart/retributed_full"), Lithereal.id("hud/heart/retributed_full_blinking"), Lithereal.id("hud/heart/retributed_half"), Lithereal.id("hud/heart/retributed_half_blinking"), Lithereal.id("hud/heart/retributed_hardcore_full"), Lithereal.id("hud/heart/retributed_hardcore_full_blinking"), Lithereal.id("hud/heart/retributed_hardcore_half"), Lithereal.id("hud/heart/retributed_hardcore_half_blinking")
    );
}
//?}