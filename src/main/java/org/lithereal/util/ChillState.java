package org.lithereal.util;

import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.lithereal.Lithereal;

import java.util.function.Function;

public enum ChillState {
    NONE(0, 0, 1, _ -> 0F),
    CHILLED(1, 180, 1, ticks -> Math.max((ticks - 100F) * 0.0025F + 0.1F, 0.1F)),
    FROZEN(181, Integer.MAX_VALUE, 1, _ -> 1F);
    public static final Identifier CHILLED_SPEED_MODIFIER = Lithereal.id("chilled_speed_modifier");
    private final int minTicks;
    private final int maxTicks;
    private final int consumeRate;
    private final Function<Integer, Float> speedFunc;

    ChillState(int minTicks, int maxTicks, int consumeRate, Function<Integer, Float> speedFunc) {
        this.minTicks = minTicks;
        this.maxTicks = maxTicks;
        this.consumeRate = consumeRate;
        this.speedFunc = speedFunc;
    }

    public int getMinTicks() {
        return this.minTicks;
    }

    public int getMaxTicks() {
        return this.maxTicks;
    }

    public int getConsumeRate() {
        return this.consumeRate;
    }

    public float getSpeed(int ticks) {
        return this.speedFunc.apply(ticks);
    }

    public static int modifyColor(int color, int ticks) {
        boolean isNonTinted = color == -1;
        float alpha = isNonTinted ? 1 : ARGB.alphaFloat(color);
        float r = isNonTinted ? 1 : ARGB.redFloat(color);
        float g = isNonTinted ? 1 : ARGB.greenFloat(color);
        float b = isNonTinted ? 1 : ARGB.blueFloat(color);
        if (ticks > 180) {
            r *= 0.25F;
            g *= 0.4F;
        } else {
            float progress = 1 - ((ticks / 180F) * 0.325F + 0.1F);
            r *= 0.7F * progress;
            g *= 0.9F * progress;
        }
        return ARGB.colorFromFloat(alpha, r, g, b);
    }

    public static ChillState fromTicks(int ticks) {
        ChillState ret = NONE;
        for (ChillState state : ChillState.values()) {
            if (state.getMinTicks() <= ticks && state.getMaxTicks() >= ticks) ret = state;
        }
        return ret;
    }

    public int add(int originalChill, int chill) {
        if (this == FROZEN) return originalChill;
        else if (this == CHILLED && (originalChill + (chill / 2)) > 180) return 210;
        else if (this == CHILLED) return originalChill + (chill / 2);
        return originalChill + chill;
    }
}
