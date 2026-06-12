package org.lithereal.util.ether;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EtherEnergyAbsorber implements IEnergyUser {
    public int oldEnergy;
    public int remainingEnergy;
    public int absorbRate;

    public EtherEnergyAbsorber(int absorbRate) {
        this.absorbRate = absorbRate;
    }

    public <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        IEnergyUser.super.tick(pEntity);
        this.oldEnergy = this.remainingEnergy;
        if (this.remainingEnergy > 0) this.remainingEnergy--;
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
        return this.remainingEnergy > 0 ? 0 : absorbed;
    }

    @Override
    public int getTransferRate() {
        return 0;
    }

    @Override
    public int getAbsorbRate() {
        return this.absorbRate;
    }

    @Override
    public void save(ValueOutput valueOutput) {
        valueOutput.putInt("absorb_rate", this.absorbRate);
        valueOutput.putInt("remaining_energy", this.remainingEnergy);
        valueOutput.putInt("old_energy", this.oldEnergy);
    }

    @Override
    public void load(ValueInput valueInput) {
        this.absorbRate = valueInput.getIntOr("absorb_rate", 0);
        this.remainingEnergy = valueInput.getIntOr("remaining_energy", 0);
        this.oldEnergy = valueInput.getIntOr("old_energy", 0);
    }
}
