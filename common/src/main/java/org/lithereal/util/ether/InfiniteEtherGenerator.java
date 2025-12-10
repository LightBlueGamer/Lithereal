package org.lithereal.util.ether;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InfiniteEtherGenerator implements IEnergyUser {
    public int transferRate;

    public InfiniteEtherGenerator(int transferRate) {
        this.transferRate = transferRate;
    }

    @Override
    public boolean canProvideEnergy(BlockEntity pEntity) {
        return true;
    }

    @Override
    public void absorbEnergy(int transferred) {

    }

    @Override
    public void removeEnergy(int transferred) {

    }

    @Override
    public int restrictTransferred(int transferred) {
        return transferred;
    }

    @Override
    public int restrictAbsorbed(int absorbed) {
        return absorbed;
    }

    @Override
    public int getTransferRate() {
        return transferRate;
    }

    @Override
    public int getAbsorbRate() {
        return -1;
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("transfer_rate", transferRate);
    }

    @Override
    public void load(CompoundTag energyUser, HolderLookup.Provider provider) {
        transferRate = energyUser.getInt("transfer_rate");
    }
}
