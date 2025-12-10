package org.lithereal.util.ether;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EtherEnergyAbsorber implements IEnergyUser {
    public int oldEnergy;
    public int remainingEnergy;
    public int absorbRate;

    public EtherEnergyAbsorber(int absorbRate) {
        this.absorbRate = absorbRate;
    }

    public <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
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
    public void save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("remaining_energy", this.remainingEnergy);
        tag.putInt("old_energy", this.oldEnergy);
    }

    @Override
    public void load(CompoundTag energyUser, HolderLookup.Provider provider) {
        this.remainingEnergy = energyUser.getInt("remaining_energy");
        this.oldEnergy = energyUser.getInt("old_energy");
    }
}
