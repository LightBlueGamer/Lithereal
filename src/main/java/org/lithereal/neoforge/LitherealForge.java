package org.lithereal.neoforge;

//? neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.util.ModAttachmentTypes;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge(IEventBus modEventBus) {
        ModAttachmentTypes.register(modEventBus);
        Events.ModBusEvents.register(modEventBus);
        Events.ForgeBusEvents.register();

        Lithereal.init();
    }
}
//?}
