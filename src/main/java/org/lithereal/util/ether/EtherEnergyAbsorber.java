package org.lithereal.util.ether;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EtherEnergyAbsorber implements IEnergyUser {
    public int oldEnergy;
    public int remainingEnergy;
    public int levelCharge;
    public int maxChargeLevel;
    public int baseCharge;

    public EtherEnergyAbsorber(int levelCharge, int maxChargeLevel, int baseCharge) {
        this.levelCharge = levelCharge;
        this.maxChargeLevel = maxChargeLevel;
        this.baseCharge = baseCharge;
    }

    public <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        this.oldEnergy = this.remainingEnergy;
        if (this.remainingEnergy > 0) this.remainingEnergy -= restrictTransferred(this.levelCharge / 5);
        IEnergyUser.super.tick(pEntity);
    }

    @Override
    public float transferPreferenceProgress(EnergyRange requiredForThisConnection) {
        return 0;
    }

    @Override
    public int transferPreference(EnergyRange requiredForThisConnection) {
        return requiredForThisConnection.min();
    }

    public int getChargeLevel() {
        if (this.remainingEnergy == 0) return 0;
        return Math.min(this.remainingEnergy / this.levelCharge + this.baseCharge, this.maxChargeLevel);
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        return false;
    }

    @Override
    public void absorbEnergy(int transferred) {
        this.oldEnergy = this.remainingEnergy;
        this.remainingEnergy += transferred;
    }

    @Override
    public void removeEnergy(int transferred) {
        this.oldEnergy = this.remainingEnergy;
        this.remainingEnergy -= transferred;
    }

    @Override
    public int restrictTransferred(int transferred) {
        return Math.min(this.remainingEnergy, transferred);
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        return Math.min((this.levelCharge * this.maxChargeLevel - this.remainingEnergy), absorbed);
    }

    @Override
    public EnergyRange getTransferRate() {
        return EnergyRange.of(0);
    }

    @Override
    public EnergyRange getAbsorbRate() {
        return EnergyRange.of(this.levelCharge, this.levelCharge * this.maxChargeLevel);
    }

    @Override
    public void save(ValueOutput valueOutput) {
        valueOutput.putInt("level_charge", this.levelCharge);
        valueOutput.putInt("max_charge_level", this.maxChargeLevel);
        valueOutput.putInt("base_charge", this.baseCharge);
        valueOutput.putInt("remaining_energy", this.remainingEnergy);
        valueOutput.putInt("old_energy", this.oldEnergy);
    }

    @Override
    public void load(ValueInput valueInput) {
        this.levelCharge = valueInput.getIntOr("level_charge", this.levelCharge);
        this.maxChargeLevel = valueInput.getIntOr("max_charge_level", this.maxChargeLevel);
        this.baseCharge = valueInput.getIntOr("base_charge", this.baseCharge);
        this.remainingEnergy = valueInput.getIntOr("remaining_energy", 0);
        this.oldEnergy = valueInput.getIntOr("old_energy", 0);
    }
}
