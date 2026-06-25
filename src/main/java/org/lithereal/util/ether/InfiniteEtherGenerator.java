package org.lithereal.util.ether;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class InfiniteEtherGenerator implements IEnergyUser {
    public int storage = 0;
    public int capacity = 1000;
    public int transferRate;
    public int storeCount = 0;

    public InfiniteEtherGenerator(int transferRate) {
        this.transferRate = transferRate;
    }

    @Override
    public <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        if (this.storeCount % 10 == 0) {
            this.storage += restrictAbsorbed(this.transferRate);
            this.storeCount = 0;
        }
        this.storeCount++;
    }

    @Override
    public float transferPreferenceProgress(EnergyRange requiredForThisConnection) {
        return requiredForThisConnection.max() - this.transferRate <= 0 ? 1 : Math.min((requiredForThisConnection.progressOf(this.transferRate)) * 1.2F, 1);
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        return this.transferRate + this.storage >= ((IEnergyUserProvider)pEntity).requiredEnergy();
    }

    @Override
    public boolean couldProvideEnergy(BlockEntity pEntity) {
        return true;
    }

    @Override
    public void absorbEnergy(int transferred) {
        this.storage += transferred;
    }

    @Override
    public void removeEnergy(int transferred) {
        this.storage -= transferred;
    }

    @Override
    public int restrictTransferred(int transferred) {
        return transferred;
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        if (capacity < storage + absorbed) absorbed = capacity - storage;
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
        valueOutput.putInt("storage", storage);
        valueOutput.putInt("capacity", capacity);
        valueOutput.putInt("transfer_rate", transferRate);
    }

    @Override
    public void load(ValueInput valueInput) {
        storage = valueInput.getIntOr("storage", 0);
        capacity = valueInput.getIntOr("capacity", 0);
        transferRate = valueInput.getIntOr("transfer_rate", 0);
    }
}
