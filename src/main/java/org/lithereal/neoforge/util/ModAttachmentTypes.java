package org.lithereal.neoforge.util;

//? neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.lithereal.Lithereal;
import org.lithereal.util.ChillData;

import java.util.function.Function;

public class ModAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Lithereal.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ChillData>> CHILL_DATA_ATTACHMENT_TYPE = ATTACHMENT_TYPES.register("chilled", () -> AttachmentType.builder(() -> ChillData.ZERO).serialize(ChillData.CODEC.fieldOf("lithereal:chilled")).sync(ChillData.STREAM_CODEC.mapStream(Function.identity())).build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
//?}