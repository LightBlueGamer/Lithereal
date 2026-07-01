package org.lithereal.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.lithereal.Lithereal;
import org.lithereal.LitherealPlatform;
import org.lithereal.mob_effect.ModMobEffects;
//? fabric {
/*import org.lithereal.fabric.LitherealFabric;
*///?}
//? neoforge {
import org.lithereal.neoforge.util.ModAttachmentTypes;
//?}

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record ChillData(ChillState chillState, float chillSpeedMod, int chill) {
    public static final Codec<ChillData> CODEC = Codec.INT.xmap(ChillData::fromTicks, ChillData::chill);
    public static final StreamCodec<ByteBuf, ChillData> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(ChillData::fromTicks, ChillData::chill);
    public static final Map<Integer, ChillData> ALL_CHILL_DATAS = new ConcurrentHashMap<>();
    public static final ChillData ZERO = new ChillData();

    public ChillData() {
        this(ChillState.NONE, 0, 0);
    }

    public ChillData(int chill) {
        this(ChillState.fromTicks(chill), ChillState.fromTicks(chill).getSpeed(chill), chill);
    }

    public static ChillData fromTicks(int ticks) {
        return ALL_CHILL_DATAS.computeIfAbsent(ticks, ChillData::new);
    }

    public static ChillData get(LivingEntity livingEntity) {
        return LitherealPlatform.INSTANCE.getChillData(livingEntity);
    }

    public void clearChill(LivingEntity entity) {
        updateChill(entity, 0);
    }

    public void tickChill(LivingEntity entity) {
        if (this.chill == 0) return;
        if (entity.hasEffect(Lithereal.asHolder(ModMobEffects.FREEZE_RESISTANCE))) {
            updateChill(entity, 0);
            return;
        }
        updateChill(entity, Math.max(this.chill - this.chillState.getConsumeRate(), 0));
    }

    public void addChill(int chill, LivingEntity entity) {
        if (entity.hasEffect(Lithereal.asHolder(ModMobEffects.FREEZE_RESISTANCE))) return;
        updateChill(entity, Mth.clamp(this.chillState.add(this.chill, chill), 0, 900));
    }

    public void updateChill(LivingEntity entity, int chill) {
        if (this.chill == chill) return;
        ChillData newData = ChillData.fromTicks(chill);
        if (this.chillState != newData.chillState
                || (newData.chillState == ChillState.CHILLED && this.chillSpeedMod != newData.chillState.getSpeed(chill))) {
            var movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed != null) {
                if (newData.chillState == ChillState.NONE) movementSpeed.removeModifier(ChillState.CHILLED_SPEED_MODIFIER);
                else movementSpeed.addOrReplacePermanentModifier(new AttributeModifier(ChillState.CHILLED_SPEED_MODIFIER, -newData.chillSpeedMod, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
        }
        //? fabric {
        /*entity.setAttached(LitherealFabric.CHILL_DATA_ATTACHMENT_TYPE, newData);
        *///?}
        //? neoforge {
        entity.setData(ModAttachmentTypes.CHILL_DATA_ATTACHMENT_TYPE.get(), newData);
        //?}
    }

    static {
        ALL_CHILL_DATAS.put(0, ZERO);
    }
}
