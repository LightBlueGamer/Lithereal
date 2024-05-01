package org.lithereal.block.entity.LitherealVault;

import net.minecraft.util.Mth;

public class LitherealVaultClientData {
    public static final float ROTATION_SPEED = 10.0F;
    private float currentSpin;
    private float previousSpin;

    LitherealVaultClientData() {
    }

    public float currentSpin() {
        return this.currentSpin;
    }

    public float previousSpin() {
        return this.previousSpin;
    }

    void updateDisplayItemSpin() {
        this.previousSpin = this.currentSpin;
        this.currentSpin = Mth.wrapDegrees(this.currentSpin + 10.0F);
    }
}