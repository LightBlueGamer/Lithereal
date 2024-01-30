package org.lithereal.util;

public class LitherEnergyContainer {
    public int energy;
    public int maxEnergy;
    public int transferRate;

    public LitherEnergyContainer(int energy, int maxEnergy, int transferRate) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.transferRate = transferRate;
    }

    public void transferEnergy(LitherEnergyContainer energyContainer) {
        int toTransfer = Math.min(energyContainer.transferRate, this.transferRate);
        if(energyContainer.maxEnergy < energyContainer.energy + toTransfer) toTransfer = maxEnergy - energy;
        energyContainer.energy += toTransfer;
        this.energy -= toTransfer;
    }
}
