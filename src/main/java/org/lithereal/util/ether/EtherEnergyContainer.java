package org.lithereal.util.ether;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EtherEnergyContainer implements IEnergyUser {
    public int energy;
    public int maxEnergy;
    public int transferRate;

    public EtherEnergyContainer(int energy, int maxEnergy, int transferRate) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.transferRate = transferRate;
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        int requiredTransfer = ((IEnergyUserProvider)pEntity).requiredEnergy();
        return this.energy >= requiredTransfer;
    }

    @Override
    public float transferPreferenceProgress(EnergyRange requiredForThisConnection) {
        return requiredForThisConnection.max() - this.transferRate <= 0 ? 1 : Math.min((requiredForThisConnection.progressOf(this.transferRate)) * 1.5F, 1);
    }

    @Override
    public void absorbEnergy(int transferred) {
        this.energy += transferred;
    }

    @Override
    public void removeEnergy(int transferred) {
        this.energy -= transferred;
    }

    @Override
    public int restrictTransferred(int transferred) {
        return Math.min(this.energy, transferred);
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        if (maxEnergy < energy + absorbed) absorbed = maxEnergy - energy;
        return absorbed;
    }

    @Override
    public EnergyRange getTransferRate() {
        return EnergyRange.of(this.transferRate);
    }

    @Override
    public EnergyRange getAbsorbRate() {
        return EnergyRange.of(-1);
    }

    @Override
    public void save(ValueOutput valueOutput) {
        valueOutput.putInt("transfer_rate", this.transferRate);
        valueOutput.putInt("energy", this.energy);
        valueOutput.putInt("max_energy", this.maxEnergy);
    }

    @Override
    public void load(ValueInput valueInput) {
        this.transferRate = valueInput.getIntOr("transfer_rate", 0);
        this.energy = valueInput.getIntOr("energy", 0);
        this.maxEnergy = valueInput.getIntOr("max_energy", 0);
    }
}
